package com.metrofico.pulsar.engine.v189.pack;

import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;

public final class LegacyChannel {
   public static final String ID = "PULSARV1";
   public static final String REGISTER = "REGISTER";
   public static final int MAX_BYTES = 1048576;

   private LegacyChannel() {
   }

   public static boolean isPulsar(String channel) {
      return "PULSARV1".equals(channel);
   }

   public static void send(String json) {
      sendRaw("PULSARV1", json.getBytes(StandardCharsets.UTF_8));
   }

   public static void register() {
      sendRaw("REGISTER", "PULSARV1\u0000".getBytes(StandardCharsets.UTF_8));
   }

   private static void sendRaw(String channel, byte[] data) {
      bcy connection = ave.A().u();
      if (connection != null) {
         connection.a(new im(channel, new em(Unpooled.wrappedBuffer(data))));
      }
   }

   public static byte[] payload(String channel, em buf) {
      if (isPulsar(channel) && buf != null) {
         int n = buf.readableBytes();
         if (n > 0 && n <= 1048576) {
            byte[] data = new byte[n];
            buf.getBytes(buf.readerIndex(), data);
            return data;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }
}
