package net.minecraft.server.integrated;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.CryptManager;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.Util;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegratedServer extends MinecraftServer {
   private static final Logger k = LogManager.getLogger();
   private final Minecraft l;
   private final WorldSettings m;
   private boolean n;
   private boolean o;
   private ThreadLanServerPing p;

   public IntegratedServer(Minecraft var1) {
      super(var1.O(), new File(var1.v, a.getName()));
      this.l = var1;
      this.m = null;
   }

   public IntegratedServer(Minecraft var1, String var2, String var3, WorldSettings var4) {
      super(new File(var1.v, "saves"), var1.O(), new File(var1.v, a.getName()));
      this.i(var1.L().c());
      this.j(var2);
      this.k(var3);
      this.b(var1.t());
      this.c(var4.c());
      this.c(256);
      this.a(new IntegratedPlayerList(this));
      this.l = var1;
      this.m = this.X() ? DemoWorldServer.a : var4;
   }

   @Override
   protected ServerCommandManager h() {
      return new IntegratedServerCommandManager();
   }

   @Override
   protected void a(String var1, String var2, long var3, WorldType var5, String var6) {
      this.a(var1);
      this.d = new WorldServer[3];
      this.i = new long[this.d.length][100];
      ISaveHandler var7 = this.Y().a(var1, true);
      this.a(this.U(), var7);
      WorldInfo var8 = var7.d();
      if (var8 == null) {
         var8 = new WorldInfo(this.m, var2);
      } else {
         var8.a(var2);
      }

      for (int var9 = 0; var9 < this.d.length; var9++) {
         byte var10 = 0;
         if (var9 == 1) {
            var10 = -1;
         }

         if (var9 == 2) {
            var10 = 1;
         }

         if (var9 == 0) {
            if (this.X()) {
               this.d[var9] = (WorldServer)new DemoWorldServer(this, var7, var8, var10, this.c).b();
            } else {
               this.d[var9] = (WorldServer)new WorldServer(this, var7, var8, var10, this.c).b();
            }

            this.d[var9].a(this.m);
         } else {
            this.d[var9] = (WorldServer)new WorldServerMulti(this, var7, var10, this.d[0], this.c).b();
         }

         this.d[var9].a(new WorldManager(this, this.d[var9]));
      }

      this.ap().a(this.d);
      if (this.d[0].P().y() == null) {
         this.a(this.l.t.az);
      }

      this.k();
   }

   @Override
   protected boolean i() throws IOException {
      k.info("Starting integrated minecraft server version 1.8.9");
      this.d(true);
      this.e(true);
      this.f(true);
      this.g(true);
      this.h(true);
      k.info("Generating keypair");
      this.a(CryptManager.b());
      this.a(this.U(), this.V(), this.m.d(), this.m.h(), this.m.j());
      this.l(this.S() + " - " + this.d[0].P().k());
      return true;
   }

   @Override
   protected void A() {
      boolean var1 = this.n;
      this.n = Minecraft.A().u() != null && Minecraft.A().V();
      if (!var1 && this.n) {
         k.info("Saving and pausing game...");
         this.ap().j();
         this.a(false);
      }

      if (this.n) {
         synchronized (this.j) {
            while (!this.j.isEmpty()) {
               Util.a(this.j.poll(), k);
            }
         }
      } else {
         super.A();
         if (this.l.t.c != this.ap().s()) {
            k.info("Changing view distance to {}, from {}", new Object[]{this.l.t.c, this.ap().s()});
            this.ap().a(this.l.t.c);
         }

         if (this.l.f != null) {
            WorldInfo var9 = this.d[0].P();
            WorldInfo var3 = this.l.f.P();
            if (!var9.z() && var3.y() != var9.y()) {
               k.info("Changing difficulty to {}, from {}", new Object[]{var3.y(), var9.y()});
               this.a(var3.y());
            } else if (var3.z() && !var9.z()) {
               k.info("Locking difficulty to {}", new Object[]{var3.y()});

               for (WorldServer var7 : this.d) {
                  if (var7 != null) {
                     var7.P().e(true);
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean l() {
      return false;
   }

   @Override
   public WorldSettings.GameType m() {
      return this.m.e();
   }

   @Override
   public EnumDifficulty n() {
      return this.l.f.P().y();
   }

   @Override
   public boolean o() {
      return this.m.f();
   }

   @Override
   public boolean q() {
      return true;
   }

   @Override
   public boolean r() {
      return true;
   }

   @Override
   public File y() {
      return this.l.v;
   }

   @Override
   public boolean ae() {
      return false;
   }

   @Override
   public boolean ai() {
      return false;
   }

   @Override
   protected void a(CrashReport var1) {
      this.l.a(var1);
   }

   @Override
   public CrashReport b(CrashReport var1) {
      var1 = super.b(var1);
      var1.g().a("Type", new Callable<String>() {
         public String a() throws Exception {
            return "Integrated Server (map_client.txt)";
         }
      });
      var1.g()
         .a(
            "Is Modded",
            new Callable<String>() {
               public String a() throws Exception {
                  String var1 = ClientBrandRetriever.getClientModName();
                  if (!var1.equals("vanilla")) {
                     return "Definitely; Client brand changed to '" + var1 + "'";
                  } else {
                     var1 = IntegratedServer.this.getServerModName();
                     if (!var1.equals("vanilla")) {
                        return "Definitely; Server brand changed to '" + var1 + "'";
                     } else {
                        return Minecraft.class.getSigners() == null
                           ? "Very likely; Jar signature invalidated"
                           : "Probably not. Jar signature remains and both client + server brands are untouched.";
                     }
                  }
               }
            }
         );
      return var1;
   }

   @Override
   public void a(EnumDifficulty var1) {
      super.a(var1);
      if (this.l.f != null) {
         this.l.f.P().a(var1);
      }
   }

   @Override
   public void a(PlayerUsageSnooper var1) {
      super.a(var1);
      var1.a("snooper_partner", this.l.I().f());
   }

   @Override
   public boolean ad() {
      return Minecraft.A().ad();
   }

   @Override
   public String a(WorldSettings.GameType var1, boolean var2) {
      try {
         int var3 = -1;

         try {
            var3 = HttpUtil.a();
         } catch (IOException var5) {
         }

         if (var3 <= 0) {
            var3 = 25564;
         }

         this.aq().a(null, var3);
         k.info("Started on " + var3);
         this.o = true;
         this.p = new ThreadLanServerPing(this.am(), var3 + "");
         this.p.start();
         this.ap().a(var1);
         this.ap().c(var2);
         return var3 + "";
      } catch (IOException var6) {
         return null;
      }
   }

   @Override
   public void t() {
      super.t();
      if (this.p != null) {
         this.p.interrupt();
         this.p = null;
      }
   }

   @Override
   public void w() {
      Futures.getUnchecked(this.a(new Runnable() {
         @Override
         public void run() {
            for (EntityPlayerMP var3 : Lists.newArrayList(IntegratedServer.this.ap().v())) {
               IntegratedServer.this.ap().e(var3);
            }
         }
      }));
      super.w();
      if (this.p != null) {
         this.p.interrupt();
         this.p = null;
      }
   }

   public void a() {
      this.x();
   }

   public boolean b() {
      return this.o;
   }

   @Override
   public void a(WorldSettings.GameType var1) {
      this.ap().a(var1);
   }

   @Override
   public boolean al() {
      return true;
   }

   @Override
   public int p() {
      return 4;
   }
}
