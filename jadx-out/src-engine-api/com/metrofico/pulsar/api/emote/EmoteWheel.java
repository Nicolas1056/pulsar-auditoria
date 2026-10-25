package com.metrofico.pulsar.api.emote;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.keys.KeyCode;
import com.metrofico.pulsar.api.keys.PulsarKeys;
import java.util.ArrayList;
import java.util.List;

public final class EmoteWheel {
   public static final int SLOTS = 8;

   private EmoteWheel() {
   }

   public static boolean enabled() {
      return ClientOptions.get().enabled("emotes");
   }

   public static String keyLabel() {
      return KeyCode.label(PulsarKeys.key("emotes"));
   }

   public static boolean isKey(String key) {
      return PulsarKeys.is("emotes", key);
   }

   public static String[] slots() {
      String[] out = new String[8];
      List<Object> saved = ClientOptions.get().list("emotes.wheel");
      boolean any = saved.stream().anyMatch(v -> v instanceof String s && !s.isEmpty());
      if (any) {
         for (int i = 0; i < 8 && i < saved.size(); i++) {
            out[i] = saved.get(i) instanceof String s && !s.isEmpty() ? s : null;
         }

         return out;
      } else {
         List<EmoteLibrary.Entry> all = EmoteLibrary.all();

         for (int i = 0; i < 8 && i < all.size(); i++) {
            out[i] = all.get(i).id();
         }

         return out;
      }
   }

   public static void assign(int slot, String id) {
      String[] s = slots();
      if (id != null && !id.isEmpty()) {
         for (int i = 0; i < 8; i++) {
            if (i != slot && id.equals(s[i])) {
               s[i] = null;
            }
         }
      }

      s[slot] = id;
      List<Object> saved = new ArrayList<>(8);

      for (String v : s) {
         saved.add(v == null ? "" : v);
      }

      ClientOptions.get().set("emotes.wheel", saved);
   }

   public static boolean handleKey(String key) {
      if (enabled() && isKey(key)) {
         Emotes.host().openWheel(true);
         return true;
      } else {
         return false;
      }
   }

   public static int slotOf(String id) {
      String[] s = slots();

      for (int i = 0; i < 8; i++) {
         if (id != null && id.equals(s[i])) {
            return i;
         }
      }

      return -1;
   }

   public static boolean play(int slot) {
      String[] s = slots();
      EmoteLibrary.Entry e = slot >= 0 && slot < 8 ? EmoteLibrary.byId(s[slot]) : null;
      if (e == null) {
         return false;
      } else {
         EmotePlayer.playOwn(e);
         return true;
      }
   }
}
