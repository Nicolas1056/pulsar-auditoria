package net.minecraft.client.network;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanServerDetector {
   private static final AtomicInteger a = new AtomicInteger(0);
   private static final Logger b = LogManager.getLogger();

   public static class LanServer {
      private String a;
      private String b;
      private long c;

      public LanServer(String var1, String var2) {
         this.a = var1;
         this.b = var2;
         this.c = Minecraft.J();
      }

      public String a() {
         return this.a;
      }

      public String b() {
         return this.b;
      }

      public void c() {
         this.c = Minecraft.J();
      }
   }

   public static class LanServerList {
      private List<LanServerDetector.LanServer> b = Lists.newArrayList();
      boolean a;

      public synchronized boolean a() {
         return this.a;
      }

      public synchronized void b() {
         this.a = false;
      }

      public synchronized List<LanServerDetector.LanServer> c() {
         return Collections.unmodifiableList(this.b);
      }

      public synchronized void a(String var1, InetAddress var2) {
         String var3 = ThreadLanServerPing.a(var1);
         String var4 = ThreadLanServerPing.b(var1);
         if (var4 != null) {
            var4 = var2.getHostAddress() + ":" + var4;
            boolean var5 = false;

            for (LanServerDetector.LanServer var7 : this.b) {
               if (var7.b().equals(var4)) {
                  var7.c();
                  var5 = true;
                  break;
               }
            }

            if (!var5) {
               this.b.add(new LanServerDetector.LanServer(var3, var4));
               this.a = true;
            }
         }
      }
   }

   public static class ThreadLanServerFind extends Thread {
      private final LanServerDetector.LanServerList a;
      private final InetAddress b;
      private final MulticastSocket c;

      public ThreadLanServerFind(LanServerDetector.LanServerList var1) throws IOException {
         super("LanServerDetector #" + LanServerDetector.a.incrementAndGet());
         this.a = var1;
         this.setDaemon(true);
         this.c = new MulticastSocket(4445);
         this.b = InetAddress.getByName("224.0.2.60");
         this.c.setSoTimeout(5000);
         this.c.joinGroup(this.b);
      }

      @Override
      public void run() {
         byte[] var2 = new byte[1024];

         while (!this.isInterrupted()) {
            DatagramPacket var1 = new DatagramPacket(var2, var2.length);

            try {
               this.c.receive(var1);
            } catch (SocketTimeoutException var5) {
               continue;
            } catch (IOException var6) {
               LanServerDetector.b.error("Couldn't ping server", var6);
               break;
            }

            String var3 = new String(var1.getData(), var1.getOffset(), var1.getLength());
            LanServerDetector.b.debug(var1.getAddress() + ": " + var3);
            this.a.a(var3, var1.getAddress());
         }

         try {
            this.c.leaveGroup(this.b);
         } catch (IOException var4) {
         }

         this.c.close();
      }
   }
}
