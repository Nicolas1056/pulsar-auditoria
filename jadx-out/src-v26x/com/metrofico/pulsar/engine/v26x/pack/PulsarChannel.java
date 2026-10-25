package com.metrofico.pulsar.engine.v26x.pack;

import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.DiscardedPayload;
import net.minecraft.resources.Identifier;

public final class PulsarChannel {
   public static final Identifier ID = Identifier.fromNamespaceAndPath("pulsar", "v1");
   public static final Identifier REGISTER = Identifier.fromNamespaceAndPath("minecraft", "register");
   public static final int MAX_BYTES = 1048576;
   private static final Deque<byte[]> INBOUND = new ArrayDeque<>();
   private static final Map<DiscardedPayload, byte[]> OUTBOUND = new IdentityHashMap<>();

   public static boolean isPulsar(Identifier id) {
      return ID.equals(id);
   }

   public static void onDecoded(byte[] data) {
      synchronized (INBOUND) {
         if (INBOUND.size() < 256) {
            INBOUND.addLast(data);
         }
      }
   }

   public static String takeInbound() {
      byte[] data;
      synchronized (INBOUND) {
         data = INBOUND.pollFirst();
      }

      return data == null ? null : new String(data, StandardCharsets.UTF_8);
   }

   public static void clearInbound() {
      synchronized (INBOUND) {
         INBOUND.clear();
      }
   }

   public static void send(String json) {
      sendRaw(ID, json.getBytes(StandardCharsets.UTF_8));
   }

   public static void register() {
      sendRaw(REGISTER, (ID.toString() + "\u0000").getBytes(StandardCharsets.UTF_8));
   }

   private static void sendRaw(Identifier channel, byte[] data) {
      Minecraft mc = Minecraft.getInstance();
      ClientPacketListener connection = mc.getConnection();
      if (connection != null) {
         DiscardedPayload payload = new DiscardedPayload(channel);
         synchronized (OUTBOUND) {
            OUTBOUND.put(payload, data);
         }

         connection.send(new ServerboundCustomPayloadPacket(payload));
      }
   }

   public static byte[] takeOutbound(DiscardedPayload payload) {
      synchronized (OUTBOUND) {
         return OUTBOUND.remove(payload);
      }
   }
}
