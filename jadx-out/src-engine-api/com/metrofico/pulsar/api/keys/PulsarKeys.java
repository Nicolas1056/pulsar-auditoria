package com.metrofico.pulsar.api.keys;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hub.HubOption;
import com.metrofico.pulsar.api.hub.HubOptions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class PulsarKeys {
   public static final String HUB = "hub";
   public static final String HUD_EDIT = "hudEdit";
   public static final String EMOTES = "emotes";
   public static final String ZOOM = "zoom";
   public static final String FREECAM = "freecam";
   public static final String TOGGLE = "toggle:";
   public static final String[] GROUPS = new String[]{"Menú", "Emotes", "Cámara", "Módulos"};
   private static final List<PulsarKeys.Action> FIXED = List.of(
      new PulsarKeys.Action("hub", "Menú", "Abrir Pulsar", "hub.key", "O", true),
      new PulsarKeys.Action("hudEdit", "Menú", "Editar HUD", "hudEdit.key", "H", false),
      new PulsarKeys.Action("emotes", "Emotes", "Rueda de emotes", "emotes.key", "G", false),
      new PulsarKeys.Action("zoom", "Cámara", "Zoom", "zoom.key", "C", false),
      new PulsarKeys.Action("freecam", "Cámara", "Freecam", "freecam.key", "LALT", false)
   );
   private static volatile PulsarKeys.VanillaKeys vanilla = name -> List.of();
   private static final Map<String, List<String>> VANILLA_CACHE = new HashMap<>();
   private static long vanillaCachedAt;
   private static final long VANILLA_TTL_MS = 1000L;
   private static int builtAt = Integer.MIN_VALUE;
   private static List<PulsarKeys.Action> actions = List.of();
   private static Map<String, String> keyOf = Map.of();
   private static Map<String, List<PulsarKeys.Action>> byKey = Map.of();

   private PulsarKeys() {
   }

   public static synchronized List<PulsarKeys.Action> actions() {
      rebuild();
      return actions;
   }

   public static PulsarKeys.Action byId(String id) {
      for (PulsarKeys.Action a : actions()) {
         if (a.id().equals(id)) {
            return a;
         }
      }

      return null;
   }

   public static synchronized String key(String actionId) {
      rebuild();
      return keyOf.get(actionId);
   }

   public static boolean is(String actionId, String name) {
      return name != null && name.equals(key(actionId));
   }

   public static synchronized List<PulsarKeys.Action> on(String name) {
      rebuild();
      List<PulsarKeys.Action> list = name == null ? null : byKey.get(name);
      return list == null ? List.of() : list;
   }

   public static List<PulsarKeys.Action> sharing(PulsarKeys.Action a) {
      String k = key(a.id());
      List<PulsarKeys.Action> out = new ArrayList<>();

      for (PulsarKeys.Action o : on(k)) {
         if (!o.id().equals(a.id())) {
            out.add(o);
         }
      }

      return out;
   }

   public static void vanilla(PulsarKeys.VanillaKeys keys) {
      vanilla = keys;
   }

   public static synchronized List<String> vanillaUsers(String name) {
      if (name == null) {
         return List.of();
      } else {
         long now = System.currentTimeMillis();
         if (now - vanillaCachedAt > 1000L) {
            VANILLA_CACHE.clear();
            vanillaCachedAt = now;
         }

         return VANILLA_CACHE.computeIfAbsent(name, n -> {
            try {
               return List.copyOf(vanilla.users(n));
            } catch (RuntimeException var2) {
               return List.of();
            }
         });
      }
   }

   public static void assign(PulsarKeys.Action a, String name) {
      if (name == null || KeyCode.byName(name) != null) {
         if (name != null || !a.required()) {
            ClientOptions.get().set(a.option(), name == null ? "" : name);
         }
      }
   }

   public static List<String> resetIds() {
      List<String> ids = new ArrayList<>();

      for (PulsarKeys.Action a : actions()) {
         ids.add(a.option());
      }

      return ids;
   }

   public static boolean toggleModule(String name) {
      for (PulsarKeys.Action a : on(name)) {
         if (a.id().startsWith("toggle:")) {
            String module = a.id().substring("toggle:".length());
            ClientOptions.get().set(module + ".enabled", !ClientOptions.get().enabled(module));
            ClientOptions.LOG.accept("pulsar keys: " + module + " toggled with " + name);
            return true;
         }
      }

      return false;
   }

   private static void rebuild() {
      int rev = ClientOptions.revision;
      if (rev != builtAt || actions.isEmpty()) {
         List<PulsarKeys.Action> list = new ArrayList<>(FIXED);

         for (HubOption o : HubOptions.all()) {
            if (o.kind() == HubOption.Kind.TOGGLE) {
               list.add(new PulsarKeys.Action("toggle:" + o.id(), "Módulos", o.title(), o.id() + ".toggleKey", "", false));
            }
         }

         Map<String, String> keys = new HashMap<>();
         Map<String, List<PulsarKeys.Action>> users = new HashMap<>();

         for (PulsarKeys.Action a : list) {
            String k = stored(a);
            if (k != null) {
               keys.put(a.id(), k);
               users.computeIfAbsent(k, x -> new ArrayList<>()).add(a);
            }
         }

         users.replaceAll((kx, v) -> Collections.unmodifiableList((List<? extends PulsarKeys.Action>)v));
         actions = Collections.unmodifiableList(list);
         keyOf = keys;
         byKey = users;
         builtAt = rev;
      }
   }

   private static String stored(PulsarKeys.Action a) {
      String raw = ClientOptions.get().string(a.option(), a.def());
      if (raw.isEmpty()) {
         return a.required() ? a.def() : null;
      } else {
         String name = raw.toUpperCase(Locale.ROOT);
         if (KeyCode.byName(name) == null) {
            return a.def().isEmpty() ? null : a.def();
         } else {
            return name;
         }
      }
   }

   public record Action(String id, String group, String label, String option, String def, boolean required) {
   }

   public interface VanillaKeys {
      List<String> users(String var1);
   }
}
