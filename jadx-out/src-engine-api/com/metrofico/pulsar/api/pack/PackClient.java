package com.metrofico.pulsar.api.pack;

import com.metrofico.pulsar.api.hud.CompassDefinition;
import com.metrofico.pulsar.api.hud.PanelDefinition;
import com.metrofico.pulsar.api.hud.PanelPlacements;
import com.metrofico.pulsar.api.hud.PanelRegistry;
import com.metrofico.pulsar.api.hud.Placement;
import com.metrofico.pulsar.api.hud.ToastRegistry;
import com.metrofico.pulsar.api.hud.Zone;
import com.metrofico.pulsar.api.legit.ConnectionIdentity;
import com.metrofico.pulsar.api.legit.EntryPass;
import com.metrofico.pulsar.api.legit.GameSignals;
import com.metrofico.pulsar.api.legit.LegitSession;
import com.metrofico.pulsar.api.legit.LiveSignals;
import com.metrofico.pulsar.api.legit.SignalsChoice;
import com.metrofico.pulsar.api.staff.StaffXrayGate;
import com.metrofico.pulsar.api.ui.KeyBindings;
import com.metrofico.pulsar.api.ui.ScreenRegistry;
import com.metrofico.pulsar.api.ui.TabListState;
import com.metrofico.pulsar.api.world.MarkerRegistry;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PublicKey;
import java.time.Instant;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public final class PackClient {
   private volatile int revision;
   private static final int MAX_MESSAGES_PER_SECOND = 200;
   private final Path dataDir = Path.of(System.getProperty("pulsar.data.dir", "pulsar"));
   private final String serviceUrl = System.getProperty("pulsar.service.url", "").replaceAll("/+$", "");
   private final String clientVersion = System.getProperty("pulsar.client.version", "dev");
   private final HttpClient http = HttpClient.newBuilder().followRedirects(Redirect.NORMAL).build();
   private final PackHost host;
   private final ConsentStore consent = new ConsentStore(this.dataDir);
   private final ResourceCache cache = new ResourceCache(this.dataDir, this.http);
   private final PackSession session = new PackSession();
   private final PanelRegistry panels = new PanelRegistry();
   private final PanelPlacements placements = new PanelPlacements(this.dataDir);
   private final KeyBindings keys = new KeyBindings(this.dataDir);
   private final MarkerRegistry markers = new MarkerRegistry();
   private final ScreenRegistry screens = new ScreenRegistry();
   private final ToastRegistry toasts = new ToastRegistry(System::currentTimeMillis);
   private final TabListState tabList = new TabListState();
   private volatile boolean compassEnabled;
   private volatile CompassDefinition compass = CompassDefinition.defaults();
   private Fragmenter inbound = new Fragmenter(200);
   private final Fragmenter outbound = new Fragmenter(Integer.MAX_VALUE);
   private final SignalsChoice signalsChoice = SignalsChoice.mandatory();
   private EntryPass entryPass = new EntryPass(LegitSession::current, this.signalsChoice);
   private volatile int dropped;

   public int revision() {
      return this.revision;
   }

   public PackClient(PackHost host) {
      this.host = host;
      LiveSignals.install(this::send);
   }

   public PackHost host() {
      return this.host;
   }

   public PackSession session() {
      return this.session;
   }

   public void onJoined() {
      this.revision++;
      this.host.joined();
      this.inbound = new Fragmenter(200);
      this.session.ended();
      this.dropped = 0;
      this.host.registerChannel();
      this.entryPass = new EntryPass(LegitSession::current, this.signalsChoice);
      this.send(Handshake.hello(this.clientVersion, Locale.getDefault().toLanguageTag(), this.host.capabilities(), LegitSession.current() != null));
      this.host.log("pulsar:v1 hello sent");
   }

   public void onLeft() {
      this.revision++;
      if (this.session.state() != PackSession.State.NONE) {
         this.host.log("pulsar:v1 session ended (" + this.session.state() + ")");
      }

      this.session.ended();
      this.panels.clear();
      this.markers.clear();
      this.screens.clear();
      this.toasts.clear();
      this.tabList.clear();
      this.host.left();
      GameSignals.stop();
      EntryPass.left();
      StaffXrayGate.clear();
      ConnectionIdentity.left();
      this.compassEnabled = false;
      this.compass = CompassDefinition.defaults();
   }

   public void onMessage(String raw) {
      this.revision++;

      Optional<String> complete;
      try {
         complete = this.inbound.receive(raw, System.currentTimeMillis());
      } catch (Fragmenter.LimitExceeded var7) {
         this.host.log("pulsar:v1 limit exceeded, pack disabled: " + var7.getMessage());
         this.session.ended();
         return;
      } catch (RuntimeException var8) {
         this.dropped++;
         return;
      }

      if (!complete.isEmpty()) {
         Envelope envelope;
         try {
            envelope = Envelope.parse(complete.get());
         } catch (RuntimeException var6) {
            this.dropped++;
            return;
         }

         String e = envelope.type();
         switch (e) {
            case "pack":
               Handshake.offer(envelope).ifPresentOrElse(this::onOffer, () -> this.dropped++);
               break;
            case "none":
               this.host.log("pulsar:v1 server has no pack");
               break;
            case "apply":
            case "event":
               this.onApply(envelope);
               break;
            case "legit.challenge":
            case "legit.renew":
               this.onLegit(envelope);
               break;
            case "staff.xray":
               this.onStaffXray(envelope);
               break;
            default:
               this.dropped++;
         }
      }
   }

   private void onLegit(Envelope envelope) {
      EntryPass pass = this.entryPass;
      pass.respond(envelope.type(), envelope.fields()).thenAccept(reply -> this.host.runOnMain(() -> {
         if (pass == this.entryPass) {
            this.send(reply);
         }
      }));
   }

   private void onStaffXray(Envelope envelope) {
      if (Boolean.TRUE.equals(envelope.fields().get("off"))) {
         if (StaffXrayGate.active()) {
            this.host.log("staff x-ray: turned off by the server");
         }

         StaffXrayGate.clear();
      } else {
         String account = this.host.accountUuid();
         String typed = ConnectionIdentity.typedHost();
         String keyHash = ConnectionIdentity.keyHash();
         long arrival = System.currentTimeMillis();
         int generation = StaffXrayGate.generation();
         CompletableFuture.runAsync(() -> {
            try {
               StaffXrayGate.Order order = StaffXrayGate.verify(envelope.fields(), this::serverRecord, this.pulsarKey(), account, typed, keyHash, arrival);
               this.host.runOnMain(() -> {
                  boolean was = StaffXrayGate.active();
                  if (StaffXrayGate.accept(order, arrival, generation)) {
                     if (!was) {
                        this.host.log("staff x-ray: on by " + order.network() + " (" + order.hide().size() + " blocks offered)");
                     }
                  }
               });
            } catch (StaffXrayGate.Rejected var9) {
               this.host.log("staff x-ray: order rejected: " + var9.code() + " · " + var9.getMessage());
            } catch (Exception var10) {
               this.host.log("staff x-ray: order check failed: " + var10);
            }
         });
      }
   }

   private void onOffer(Handshake.Offer offer) {
      this.host.log("pulsar:v1 pack offered: " + offer.packId() + " v" + offer.packVersion() + " by " + offer.serverId());
      CompletableFuture.runAsync(() -> {
         try {
            PublicKey pulsarKey = this.pulsarKey();
            PackVerifier verifier = new PackVerifier(pulsarKey);
            ServerRecord server = verifier.verifyServer(this.serverRecord(offer.serverId()));
            PackVerifier.verifyDestination(server, ConnectionIdentity.typedHost());
            String manifestJson = offer.manifestJson() != null ? offer.manifestJson() : this.fetch(offer.manifestUrl());
            PackManifest manifest = verifier.verifyPack(manifestJson, server);
            this.host.runOnMain(() -> this.offered(server, manifest));
         } catch (PackVerifier.RejectedException var7) {
            this.host.log("pulsar:v1 pack rejected: " + var7.code() + " · " + var7.getMessage());
            this.host.runOnMain(() -> {
               this.session.rejected(var7.getMessage());
               this.send(Handshake.declined(offer.packId(), var7.code()));
            });
         } catch (Exception var8) {
            this.host.log("pulsar:v1 pack verification failed: " + var8);
            this.host.runOnMain(() -> {
               this.session.rejected(var8.toString());
               this.send(Handshake.declined(offer.packId(), "error"));
            });
         }
      });
   }

   private void offered(ServerRecord server, PackManifest manifest) {
      this.session.offered(server, manifest);
      Optional<ConsentStore.Decision> remembered = this.consent.lookup(server.id(), manifest.capabilities());
      if (remembered.isPresent()) {
         this.decide(remembered.get().consent(), false);
      } else {
         this.session.consenting();
         this.host.showConsent(server, manifest, c -> this.decide(c, true));
      }
   }

   public void decide(PackSession.Consent choice, boolean remember) {
      this.revision++;
      PackManifest manifest = this.session.manifest();
      ServerRecord server = this.session.server();
      if (manifest != null && server != null) {
         if (remember) {
            this.consent
               .remember(
                  new ConsentStore.Decision(
                     server.id(), server.name(), manifest.packVersion(), EnumSet.copyOf(manifest.capabilities()), choice, Instant.now().toString()
                  )
               );
         }

         this.session.decide(choice);
         if (choice == PackSession.Consent.NONE) {
            this.send(Handshake.declined(manifest.id(), "declined"));
            this.host.log("pulsar:v1 pack declined by player");
         } else {
            CompletableFuture.runAsync(() -> {
               try {
                  this.cache.fetchAll(manifest.resources(), (done, total) -> {});
                  this.host.runOnMain(() -> {
                     if (this.session.state() == PackSession.State.DOWNLOADING) {
                        this.session.downloaded();
                        this.loadPanelDefinitions(manifest);
                        Set<Capability> granted = this.session.granted();
                        this.send(Handshake.ready(manifest.id(), manifest.packVersion(), granted));
                        this.host.log("pulsar:v1 pack active: " + manifest.id() + " with " + granted.size() + " capabilities");
                     }
                  });
               } catch (Exception var3x) {
                  this.host.log("pulsar:v1 pack download failed: " + var3x);
                  this.host.runOnMain(() -> {
                     this.session.rejected("download");
                     this.send(Handshake.declined(manifest.id(), "download_failed"));
                  });
               }
            });
         }
      }
   }

   private void onApply(Envelope envelope) {
      Object cap = envelope.fields().get("cap");
      Optional<Capability> capability = Capability.byId(String.valueOf(cap));
      if (!capability.isEmpty() && this.session.allows(capability.get())) {
         switch ((Capability)capability.get()) {
            case UI_PANELS:
               if (!this.panels.apply(envelope.fields())) {
                  this.dropped++;
               }
               break;
            case WORLD_MARKERS:
               if (!this.markers.apply(envelope.fields())) {
                  this.dropped++;
               }
               break;
            case UI_MENU:
               if (!this.screens.apply(envelope.fields())) {
                  this.dropped++;
                  this.host
                     .log(
                        "pulsar:v1 ui.menu rejected: "
                           + Json.write(envelope.fields()).replaceAll("\\s+", " ").substring(0, Math.min(300, Json.write(envelope.fields()).length()))
                     );
               } else if (envelope.fields().containsKey("root")) {
                  this.screens
                     .get(String.valueOf(envelope.fields().get("screen")))
                     .ifPresent(sd -> this.host.log("pulsar:v1 screen defined: " + sd.id() + " (" + sd.root().count() + " nodes, key " + sd.key() + ")"));
               }

               this.screens.takeOpenRequest().ifPresent(this.host::openMenu);
               if (this.screens.takeCloseRequest()) {
                  this.host.closeMenu();
               }
               break;
            case UI_TABLIST:
               if (!this.tabList.apply(envelope.fields())) {
                  this.dropped++;
               }
               break;
            case UI_NOTIFICATIONS:
               if (!this.toasts.apply(envelope.fields())) {
                  this.dropped++;
               }
               break;
            case UI_COMPASS:
               this.compass = CompassDefinition.merge(this.compass, envelope.fields());
               if (envelope.fields().get("enabled") instanceof Boolean b) {
                  this.compassEnabled = b;
               }
               break;
            default:
               if (!this.host.applyExtra(capability.get(), envelope.fields())) {
                  this.dropped++;
               }
         }
      } else {
         this.dropped++;
         this.host.log("pulsar:v1 apply dropped (not granted): " + cap);
      }
   }

   private void loadPanelDefinitions(PackManifest manifest) {
      this.panels.clear();
      this.panels.placements(PanelPlacements.zones(this.placements.forServer(manifest.serverId())));
      this.screens.chosenKeys(this.keys.forServer(manifest.serverId()));

      for (PackManifest.Resource r : manifest.resources()) {
         if (r.path().startsWith("ui/") && r.path().endsWith(".json")) {
            try {
               Map<String, Object> o = Json.object(Json.parse(Files.readString(this.cache.pathFor(r.sha256()), StandardCharsets.UTF_8)), r.path());
               if (CompassDefinition.isCompass(o)) {
                  this.compass = CompassDefinition.merge(CompassDefinition.defaults(), o);
               } else {
                  this.panels.define(PanelDefinition.parse(o));
               }
            } catch (RuntimeException | IOException var5) {
               this.host.log("pulsar:v1 panel resource ignored " + r.path() + ": " + var5.getMessage());
            }
         }
      }
   }

   public PanelRegistry panels() {
      return this.panels;
   }

   public MarkerRegistry markers() {
      return this.markers;
   }

   public boolean compassEnabled() {
      return this.compassEnabled;
   }

   public Path dataDir() {
      return this.dataDir;
   }

   public TabListState tabList() {
      return this.tabList;
   }

   public ToastRegistry toasts() {
      return this.toasts;
   }

   public ScreenRegistry screens() {
      return this.screens;
   }

   public boolean granted(Capability c) {
      return this.active() && this.session.allows(c);
   }

   public String resourceSha(String path) {
      PackManifest manifest = this.session.manifest();
      if (manifest == null) {
         return null;
      } else {
         for (PackManifest.Resource r : manifest.resources()) {
            if (r.path().equals(path)) {
               return r.sha256();
            }
         }

         return null;
      }
   }

   public ResourceCache cache() {
      return this.cache;
   }

   public String serviceUrl() {
      return this.serviceUrl;
   }

   public Path resourceFile(String sha256) {
      Path f = this.cache.pathFor(sha256);
      return Files.isRegularFile(f) ? f : null;
   }

   public void sendEvent(Map<String, Object> event) {
      this.revision++;
      if (this.active()) {
         this.send(Json.write(event));
      }
   }

   public CompassDefinition compass() {
      CompassDefinition d = this.compass;
      PackManifest manifest = this.session.manifest();
      Placement pl = manifest == null ? null : this.placements.forServer(manifest.serverId()).get(d.id());
      Zone chosen = pl == null ? null : pl.anchor();
      return chosen != null && chosen != d.zone()
         ? new CompassDefinition(d.id(), chosen, d.form(), d.size(), d.span(), d.cardinals(), d.distances(), d.target(), d.color(), d.accent(), d.background())
         : d;
   }

   public boolean active() {
      return this.session.state() == PackSession.State.ACTIVE;
   }

   public void movePanel(String panelId, Zone zone) {
      this.revision++;
      this.movePanel(panelId, Placement.zone(zone));
   }

   public void movePanel(String panelId, Placement placement) {
      this.revision++;
      if (!this.host.placeModule(panelId, placement)) {
         PackManifest manifest = this.session.manifest();
         if (manifest != null) {
            this.panels.place(panelId, placement.anchor());
            this.placements.move(manifest.serverId(), panelId, placement);
         }
      }
   }

   public Map<String, Placement> freePlacements() {
      PackManifest manifest = this.session.manifest();
      if (manifest == null) {
         return Map.of();
      } else {
         Map<String, Placement> out = new LinkedHashMap<>();
         this.placements.forServer(manifest.serverId()).forEach((k, v) -> {
            if (v.free()) {
               out.put(k, v);
            }
         });
         return out;
      }
   }

   public void bindKey(String screenId, String key) {
      this.revision++;
      PackManifest manifest = this.session.manifest();
      if (manifest != null) {
         this.keys.bind(manifest.serverId(), screenId, key);
         this.screens.chosenKeys(this.keys.forServer(manifest.serverId()));
      }
   }

   public void resetKeys() {
      this.revision++;
      PackManifest manifest = this.session.manifest();
      if (manifest != null) {
         this.keys.reset(manifest.serverId());
         this.screens.chosenKeys(Map.of());
      }
   }

   public String serverName() {
      return this.session.server() == null ? "" : this.session.server().name();
   }

   public void resetLayout() {
      this.revision++;
      PackManifest manifest = this.session.manifest();
      if (manifest != null) {
         this.panels.placements(Map.of());
         this.placements.reset(manifest.serverId());
      }
   }

   public int dropped() {
      return this.dropped;
   }

   private void send(String json) {
      for (String part : this.outbound.split(json)) {
         this.host.send(part);
      }
   }

   private String serverRecord(String serverId) throws IOException, InterruptedException {
      if (this.serviceUrl.isEmpty()) {
         throw new IOException("no service url");
      } else {
         Path cached = this.dataDir.resolve("packs").resolve("servers").resolve(serverId + ".json");

         try {
            if (Files.isRegularFile(cached) && Files.getLastModifiedTime(cached).toMillis() > System.currentTimeMillis() - 86400000L) {
               return Files.readString(cached, StandardCharsets.UTF_8);
            }
         } catch (IOException var4) {
         }

         String json = this.fetch(this.serviceUrl + "/api/v1/servers/" + serverId);
         Files.createDirectories(cached.getParent());
         Files.writeString(cached, json, StandardCharsets.UTF_8);
         return json;
      }
   }

   private String fetch(String url) throws IOException, InterruptedException {
      HttpResponse<String> r = this.http.send(HttpRequest.newBuilder(URI.create(url)).GET().build(), BodyHandlers.ofString());
      if (r.statusCode() != 200) {
         throw new IOException("HTTP " + r.statusCode() + " " + url);
      } else {
         return r.body();
      }
   }

   public PublicKey pulsarKey() throws Exception {
      return pulsarKey(this.host.getClass());
   }

   public static PublicKey pulsarKey(Class<?> anchor) throws Exception {
      PublicKey var2;
      try (InputStream in = anchor.getResourceAsStream("/pulsar-release.pub")) {
         if (in == null) {
            throw new IOException("missing pulsar-release.pub next to " + anchor.getName());
         }

         var2 = PackVerifier.publicKeyFromPem(new String(in.readAllBytes(), StandardCharsets.UTF_8));
      }

      return var2;
   }

   public Map<String, Object> debugState() {
      return Map.of("state", this.session.state().name(), "dropped", this.dropped);
   }
}
