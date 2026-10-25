package com.metrofico.pulsar.api.legit;

import com.metrofico.pulsar.api.pack.Json;
import com.metrofico.pulsar.api.social.PulsarPlayers;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public final class LegitSession {
   static final long HEARTBEAT_MS = 15000L;
   static final long REPORT_MS = 5000L;
   static final long SIGNALS_MS = 10000L;
   static final long ATTEST_TIMEOUT_MS = 4000L;
   private static final long MAX_BACKOFF_MS = 60000L;
   private static final HexFormat HEX = HexFormat.of();
   private static volatile LegitSession current;
   private final LegitSession.Links links;
   private final CompletableFuture<LegitSession.Exam> exam;
   private final ProbeEngine probes;
   private final Consumer<String> log;
   private final Supplier<Map<String, Object>> report;
   private final ScheduledExecutorService thread;
   private final AtomicLong ids = new AtomicLong();
   private final Map<Long, CompletableFuture<LegitSession.Attestation>> pending = new ConcurrentHashMap<>();
   private final List<Consumer<LegitState>> listeners = new CopyOnWriteArrayList<>();
   private volatile LegitState state = LegitState.OFFLINE;
   private volatile int score;
   private CloudLink link;
   private long generation;
   private long backoffMs = 1000L;
   private ScheduledFuture<?> heartbeat;
   private ScheduledFuture<?> reporting;
   private ScheduledFuture<?> signals;
   private boolean stopped;
   private String room;
   private boolean roomHidden;
   private static final Pattern ROOM = Pattern.compile("[a-z0-9.-]{1,253}");
   private final Consumer<String> roomJoiner = this::onServer;

   public LegitSession(LegitSession.Links links, CompletableFuture<LegitSession.Exam> exam, ProbeEngine probes, Consumer<String> log) {
      this(links, exam, probes, log, null);
   }

   public LegitSession(
      LegitSession.Links links, CompletableFuture<LegitSession.Exam> exam, ProbeEngine probes, Consumer<String> log, Supplier<Map<String, Object>> report
   ) {
      this.links = links;
      this.exam = exam;
      this.probes = probes;
      this.log = log;
      this.report = report;
      this.thread = Executors.newSingleThreadScheduledExecutor(r -> {
         Thread t = new Thread(r, "pulsar-legit");
         t.setDaemon(true);
         return t;
      });
   }

   public static LegitSession current() {
      return current;
   }

   public static void install(LegitSession session) {
      current = session;
      session.start();
   }

   public void start() {
      PulsarPlayers.link(this.roomJoiner);
      this.thread.execute(this::connect);
   }

   private void onServer(String host) {
      try {
         this.thread.execute(() -> this.moveTo(host));
      } catch (RejectedExecutionException var3) {
      }
   }

   private void moveTo(String host) {
      String next = host != null && ROOM.matcher(host).matches() ? host : null;
      if (Objects.equals(next, this.room)) {
         if (next != null) {
            this.joinRoom();
         }
      } else {
         if (this.room != null && this.link != null) {
            this.send("relay.leave", 0L, Map.of("room", this.room));
         }

         this.room = next;
         this.joinRoom();
      }
   }

   private void joinRoom() {
      PulsarPlayers.clear();
      if (this.room != null && this.link != null) {
         this.roomHidden = !PulsarPlayers.visibleToOthers();
         Map<String, Object> d = new LinkedHashMap<>();
         d.put("room", this.room);
         if (this.roomHidden) {
            d.put("hidden", true);
         }

         this.send("relay.join", 0L, d);
      }
   }

   private void checkRoomVisibility() {
      if (this.room != null && this.link != null && this.roomHidden == PulsarPlayers.visibleToOthers()) {
         this.joinRoom();
      }
   }

   public void stop() {
      PulsarPlayers.unlink(this.roomJoiner);
      this.thread.execute(() -> {
         this.stopped = true;
         this.disconnect("stopped");
      });
      this.thread.shutdown();
   }

   public LegitState state() {
      return this.state;
   }

   public int score() {
      return this.score;
   }

   public void onState(Consumer<LegitState> listener) {
      this.listeners.add(listener);
   }

   public CompletableFuture<LegitSession.Attestation> attest(String host, String keyHash, String nonce, boolean sensors) {
      CompletableFuture<LegitSession.Attestation> result = new CompletableFuture<>();
      long id = this.ids.incrementAndGet();
      this.thread.execute(() -> {
         if (this.link == null) {
            result.complete(LegitSession.Attestation.failed("cloud_unavailable"));
         } else {
            Map<String, Object> d = new LinkedHashMap<>();
            d.put("host", host);
            d.put("keyHash", keyHash);
            d.put("nonce", nonce);
            d.put("sensors", sensors);
            this.pending.put(id, result);
            this.send("legit.attest.request", id, d);
         }
      });
      this.thread.schedule(() -> {
         if (this.pending.remove(id) != null) {
            result.complete(LegitSession.Attestation.failed("cloud_unavailable"));
         }
      }, 4000L, TimeUnit.MILLISECONDS);
      return result;
   }

   private void connect() {
      if (!this.stopped && this.link == null) {
         CloudLink l;
         try {
            l = this.links.create();
         } catch (Exception var6) {
            this.log.accept("legit: cloud connection failed (" + var6.getMessage() + "), retry in " + this.backoffMs / 1000L + " s");
            this.retry();
            return;
         }

         if (l == null) {
            this.retry();
         } else {
            final long gen = ++this.generation;

            try {
               l.open(new CloudLink.Listener() {
                  {
                     Objects.requireNonNull(LegitSession.this);
                  }

                  @Override
                  public void onText(String frame) {
                     LegitSession.this.thread.execute(() -> {
                        if (gen == LegitSession.this.generation) {
                           LegitSession.this.onFrame(frame);
                        }
                     });
                  }

                  @Override
                  public void onClosed(String reason) {
                     LegitSession.this.thread.execute(() -> {
                        if (gen == LegitSession.this.generation && LegitSession.this.link != null) {
                           LegitSession.this.log.accept("legit: cloud connection closed (" + reason + ")");
                           LegitSession.this.disconnect(reason);
                           LegitSession.this.retry();
                        }
                     });
                  }
               });
            } catch (Exception var5) {
               this.log.accept("legit: cloud connection failed (" + var5.getMessage() + "), retry in " + this.backoffMs / 1000L + " s");
               this.retry();
               return;
            }

            this.link = l;
            this.backoffMs = 1000L;
            this.setState(LegitState.UNKNOWN, 0);
            this.heartbeat = this.thread.scheduleAtFixedRate(() -> {
               this.send("ping", 0L, null);
               this.checkRoomVisibility();
            }, 15000L, 15000L, TimeUnit.MILLISECONDS);
            this.joinRoom();
            this.exam.thenAccept(e -> this.thread.execute(() -> {
               if (gen == this.generation && this.link != null) {
                  this.send("legit.exam.begin", 0L, examData(e));
                  if (this.report != null) {
                     this.reporting = this.thread.scheduleAtFixedRate(this::sendReport, 5000L, 5000L, TimeUnit.MILLISECONDS);
                  }

                  this.signals = this.thread.scheduleAtFixedRate(this::sendSignals, 10000L, 10000L, TimeUnit.MILLISECONDS);
               }
            }));
            this.log.accept("legit: cloud connected");
         }
      }
   }

   private void retry() {
      if (!this.stopped) {
         long wait = this.backoffMs;
         this.backoffMs = Math.min(60000L, this.backoffMs * 2L);
         this.thread.schedule(this::connect, wait, TimeUnit.MILLISECONDS);
      }
   }

   private void disconnect(String reason) {
      this.generation++;
      if (this.heartbeat != null) {
         this.heartbeat.cancel(false);
         this.heartbeat = null;
      }

      if (this.reporting != null) {
         this.reporting.cancel(false);
         this.reporting = null;
      }

      if (this.signals != null) {
         this.signals.cancel(false);
         this.signals = null;
      }

      if (this.link != null) {
         try {
            this.link.close();
         } catch (RuntimeException var3) {
         }

         this.link = null;
      }

      this.setState(LegitState.OFFLINE, 0);
      PulsarPlayers.clear();
      this.pending.values().forEach(f -> f.complete(LegitSession.Attestation.failed("cloud_unavailable")));
      this.pending.clear();
   }

   private void onFrame(String frame) {
      Map<String, Object> envelope;
      try {
         envelope = Json.object(Json.parse(frame), "frame");
      } catch (RuntimeException var12) {
         return;
      }

      Object type = envelope.get("t");
      Map<String, Object> d = envelope.get("d") instanceof Map<?, ?> m ? cast(m) : Map.of();
      if ("legit.status".equals(type)) {
         this.setState(parseState(d.get("state")), d.get("score") instanceof Number n ? n.intValue() : 0);
      } else if ("legit.watch".equals(type)) {
         LegitWatch.set(Boolean.TRUE.equals(d.get("on")));
         this.log.accept("legit: watch " + (LegitWatch.watched() ? "on" : "off"));
      } else if ("relay.roster".equals(type) && this.room != null && this.room.equals(d.get("room"))) {
         List<String> uuids = new ArrayList<>();
         Object var21 = d.get("players");
         if (var21 instanceof List) {
            for (Object o : (List)var21) {
               if (o instanceof String u) {
                  uuids.add(u);
               }
            }
         }

         PulsarPlayers.roster(uuids);
         this.log.accept("pulsar mark: " + uuids.size() + " con Pulsar en " + this.room);
      } else if ("relay.member".equals(type) && this.room != null && this.room.equals(d.get("room"))) {
         PulsarPlayers.member(d.get("uuid") instanceof String u ? u : null, Boolean.TRUE.equals(d.get("present")));
      } else if ("legit.probe".equals(type)) {
         this.answer(d);
      } else if ("legit.attestation".equals(type)) {
         long id = envelope.get("id") instanceof Number n ? n.longValue() : 0L;
         CompletableFuture<LegitSession.Attestation> f = this.pending.remove(id);
         if (f != null) {
            Object token = d.get("token");
            Object error = d.get("error");
            f.complete(
               token instanceof String t && !t.isEmpty()
                  ? new LegitSession.Attestation(t, null)
                  : LegitSession.Attestation.failed(error instanceof String s ? s : LegitState.NOT_INTEGRAL.name().toLowerCase())
            );
         }
      }
   }

   private void answer(Map<String, Object> d) {
      long start = System.nanoTime();
      String probeId = d.get("probeId") instanceof String s ? s : "";

      List<String> answers;
      try {
         byte[] salt = HEX.parseHex((String)d.get("salt"));
         Map<String, Object> params = d.get("params") instanceof Map<?, ?> m ? cast(m) : Map.of();
         if ("class-bytes".equals(d.get("kind"))) {
            List<String> classes = new ArrayList<>();

            for (Object c : Json.array(params.get("classes"), "classes")) {
               classes.add((String)c);
            }

            answers = this.probes.classBytes(salt, classes);
         } else {
            List<ProbeEngine.Range> ranges = new ArrayList<>();

            for (Object o : Json.array(params.get("ranges"), "ranges")) {
               Map<String, Object> r = Json.object(o, "range");
               ranges.add(new ProbeEngine.Range(Json.string(r, "jar", "range"), Json.integer(r, "offset", "range"), (int)Json.integer(r, "length", "range")));
            }

            answers = this.probes.jarRanges(salt, ranges);
         }
      } catch (Exception var12) {
         this.log.accept("legit: probe " + probeId + " failed: " + var12);
         return;
      }

      Map<String, Object> out = new LinkedHashMap<>();
      out.put("probeId", probeId);
      out.put("answers", answers);
      out.put("elapsedMs", (System.nanoTime() - start) / 1000000L);
      this.send("legit.probe.answer", 0L, out);
   }

   private void sendReport() {
      try {
         this.send("legit.report", 0L, this.report.get());
      } catch (RuntimeException var2) {
         this.log.accept("legit: report failed: " + var2);
      }
   }

   private void sendSignals() {
      Map<String, Object> d = GameSignals.drain();
      if (d != null) {
         this.send("legit.signals", 0L, d);
      }
   }

   private void send(String type, long id, Object d) {
      if (this.link != null) {
         Map<String, Object> envelope = new LinkedHashMap<>();
         envelope.put("t", type);
         if (id != 0L) {
            envelope.put("id", id);
         }

         if (d != null) {
            envelope.put("d", d);
         }

         try {
            this.link.send(Json.write(envelope));
         } catch (RuntimeException var7) {
            this.log.accept("legit: send failed: " + var7.getMessage());
         }
      }
   }

   private void setState(LegitState next, int nextScore) {
      this.score = nextScore;
      if (next != this.state) {
         this.state = next;
         this.log.accept("legit: state " + next + " (score " + nextScore + ")");

         for (Consumer<LegitState> l : this.listeners) {
            l.accept(next);
         }
      }
   }

   private static Map<String, Object> examData(LegitSession.Exam e) {
      Map<String, Object> d = new LinkedHashMap<>();
      d.put("clientVersion", e.clientVersion());
      d.put("mcVersion", e.mcVersion());
      d.put("buildId", e.buildId());
      d.put("os", e.os());
      d.put("bootHash", e.bootHash());
      d.put("mojangSha1", e.mojangSha1());
      d.put("origins", e.origins());
      return d;
   }

   private static LegitState parseState(Object s) {
      try {
         return s instanceof String v ? LegitState.valueOf(v) : LegitState.UNKNOWN;
      } catch (IllegalArgumentException var2) {
         return LegitState.UNKNOWN;
      }
   }

   private static Map<String, Object> cast(Map<?, ?> m) {
      return (Map<String, Object>)m;
   }

   public record Attestation(String token, String error) {
      static LegitSession.Attestation failed(String error) {
         return new LegitSession.Attestation(null, error);
      }
   }

   public record Exam(String clientVersion, String mcVersion, String buildId, String os, String bootHash, String mojangSha1, List<String> origins) {
   }

   public interface Links {
      CloudLink create() throws Exception;
   }
}
