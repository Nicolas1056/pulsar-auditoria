package net.minecraft.client.multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadLanServerPing extends Thread {
   private static final AtomicInteger a = new AtomicInteger(0);
   private static final Logger b = LogManager.getLogger();
   private final String c;
   private final DatagramSocket d;
   private boolean e = true;
   private final String f;

   public ThreadLanServerPing(String var1, String var2) throws IOException {
      super("LanServerPinger #" + a.incrementAndGet());
      this.c = var1;
      this.f = var2;
      this.setDaemon(true);
      this.d = new DatagramSocket();
   }

   @Override
   public void run() {
      String var1 = a(this.c, this.f);
      byte[] var2 = var1.getBytes();

      while (!this.isInterrupted() && this.e) {
         try {
            InetAddress var3 = InetAddress.getByName("224.0.2.60");
            DatagramPacket var4 = new DatagramPacket(var2, var2.length, var3, 4445);
            this.d.send(var4);
         } catch (IOException var6) {
            b.warn("LanServerPinger: " + var6.getMessage());
            break;
         }

         try {
            sleep(1500L);
         } catch (InterruptedException var5) {
         }
      }
   }

   @Override
   public void interrupt() {
      super.interrupt();
      this.e = false;
   }

   public static String a(String var0, String var1) {
      return "[MOTD]" + var0 + "[/MOTD][AD]" + var1 + "[/AD]";
   }

   public static String a(String var0) {
      int var1 = var0.indexOf("[MOTD]");
      if (var1 < 0) {
         return "missing no";
      } else {
         int var2 = var0.indexOf("[/MOTD]", var1 + "[MOTD]".length());
         return var2 < var1 ? "missing no" : var0.substring(var1 + "[MOTD]".length(), var2);
      }
   }

   public static String b(String var0) {
      int var1 = var0.indexOf("[/MOTD]");
      if (var1 < 0) {
         return null;
      } else {
         int var2 = var0.indexOf("[/MOTD]", var1 + "[/MOTD]".length());
         if (var2 >= 0) {
            return null;
         } else {
            int var3 = var0.indexOf("[AD]", var1 + "[/MOTD]".length());
            if (var3 < 0) {
               return null;
            } else {
               int var4 = var0.indexOf("[/AD]", var3 + "[AD]".length());
               return var4 < var3 ? null : var0.substring(var3 + "[AD]".length(), var4);
            }
         }
      }
   }
}
