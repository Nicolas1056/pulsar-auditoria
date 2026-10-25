package com.metrofico.pulsar.api.social;

import com.metrofico.pulsar.api.hub.ClientOptions;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public final class PulsarPlayers {
   public static final int MARK_SIZE = 7;
   private static volatile Set<UUID> players = Set.of();
   private static volatile Consumer<String> rooms;
   private static int seen = -1;
   private static boolean tab;
   private static boolean nametag;
   private static boolean visible;

   private PulsarPlayers() {
   }

   private static void refresh() {
      int rev = ClientOptions.revision;
      if (rev != seen) {
         ClientOptions o = ClientOptions.get();
         boolean on = o.enabled("pulsarMark");
         tab = on && o.bool("pulsarMark.tab", true);
         nametag = on && o.bool("pulsarMark.nametag", true);
         visible = o.bool("pulsarMark.visible", true);
         seen = rev;
      }
   }

   public static boolean tab(UUID player) {
      refresh();
      return tab && player != null && players.contains(player);
   }

   public static Set<UUID> inTab() {
      refresh();
      return tab ? players : Set.of();
   }

   public static boolean anyInTab() {
      refresh();
      return tab && !players.isEmpty();
   }

   public static boolean nametag(UUID player) {
      refresh();
      return nametag && player != null && players.contains(player);
   }

   public static boolean visibleToOthers() {
      return ClientOptions.get().bool("pulsarMark.visible", true);
   }

   public static void link(Consumer<String> joiner) {
      rooms = joiner;
   }

   public static void unlink(Consumer<String> joiner) {
      if (rooms == joiner) {
         rooms = null;
         players = Set.of();
      }
   }

   public static void room(String host) {
      players = Set.of();
      Consumer<String> r = rooms;
      if (r != null) {
         r.accept(host);
      }
   }

   public static void roster(List<String> uuids) {
      Set<UUID> next = new HashSet<>();

      for (String u : uuids) {
         UUID id = parse(u);
         if (id != null) {
            next.add(id);
         }
      }

      players = Set.copyOf(next);
   }

   public static void member(String uuid, boolean present) {
      UUID id = parse(uuid);
      if (id != null && players.contains(id) != present) {
         Set<UUID> next = new HashSet<>(players);
         if (present) {
            next.add(id);
         } else {
            next.remove(id);
         }

         players = Set.copyOf(next);
      }
   }

   public static void clear() {
      players = Set.of();
   }

   static Set<UUID> current() {
      return players;
   }

   private static UUID parse(String uuid) {
      try {
         return uuid == null ? null : UUID.fromString(uuid);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }
}
