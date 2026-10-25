package net.minecraft.server;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.imageio.ImageIO;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkSystem;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Util;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MinecraftServer implements Runnable, ICommandSender, IThreadListener, IPlayerUsage {
   private static final Logger k = LogManager.getLogger();
   public static final File a = new File("usercache.json");
   private static MinecraftServer l;
   private final ISaveFormat m;
   private final PlayerUsageSnooper n = new PlayerUsageSnooper("server", this, az());
   private final File o;
   private final List<ITickable> p = Lists.newArrayList();
   protected final ICommandManager b;
   public final Profiler c = new Profiler();
   private final NetworkSystem q;
   private final ServerStatusResponse r = new ServerStatusResponse();
   private final Random s = new Random();
   private int u = -1;
   public WorldServer[] d;
   private ServerConfigurationManager v;
   private boolean w = true;
   private boolean x;
   private int y;
   protected final Proxy e;
   public String f;
   public int g;
   private boolean z;
   private boolean A;
   private boolean B;
   private boolean C;
   private boolean D;
   private String E;
   private int F;
   private int G = 0;
   public final long[] h = new long[100];
   public long[][] i;
   private KeyPair H;
   private String I;
   private String J;
   private String K;
   private boolean L;
   private boolean M;
   private boolean N;
   private String O = "";
   private String P = "";
   private boolean Q;
   private long R;
   private String S;
   private boolean T;
   private boolean U;
   private final YggdrasilAuthenticationService V;
   private final MinecraftSessionService W;
   private long X = 0L;
   private final GameProfileRepository Y;
   private final PlayerProfileCache Z;
   protected final Queue<FutureTask<?>> j = Queues.newArrayDeque();
   private Thread aa;
   private long ab = az();

   public MinecraftServer(Proxy var1, File var2) {
      this.e = var1;
      l = this;
      this.o = null;
      this.q = null;
      this.Z = new PlayerProfileCache(this, var2);
      this.b = null;
      this.m = null;
      this.V = new YggdrasilAuthenticationService(var1, UUID.randomUUID().toString());
      this.W = this.V.createMinecraftSessionService();
      this.Y = this.V.createProfileRepository();
   }

   public MinecraftServer(File var1, Proxy var2, File var3) {
      this.e = var2;
      l = this;
      this.o = var1;
      this.q = new NetworkSystem(this);
      this.Z = new PlayerProfileCache(this, var3);
      this.b = this.h();
      this.m = new AnvilSaveConverter(var1);
      this.V = new YggdrasilAuthenticationService(var2, UUID.randomUUID().toString());
      this.W = this.V.createMinecraftSessionService();
      this.Y = this.V.createProfileRepository();
   }

   protected ServerCommandManager h() {
      return new ServerCommandManager();
   }

   protected abstract boolean i() throws IOException;

   protected void a(String var1) {
      if (this.Y().b(var1)) {
         k.info("Converting map!");
         this.b("menu.convertingLevel");
         this.Y().a(var1, new IProgressUpdate() {
            private long b = MinecraftServer.az();

            @Override
            public void a(String var1) {
            }

            @Override
            public void b(String var1) {
            }

            @Override
            public void a(int var1) {
               if (MinecraftServer.az() - this.b >= 1000L) {
                  this.b = MinecraftServer.az();
                  MinecraftServer.k.info("Converting... " + var1 + "%");
               }
            }

            @Override
            public void a() {
            }

            @Override
            public void c(String var1) {
            }
         });
      }
   }

   protected synchronized void b(String var1) {
      this.S = var1;
   }

   public synchronized String j() {
      return this.S;
   }

   protected void a(String var1, String var2, long var3, WorldType var5, String var6) {
      this.a(var1);
      this.b("menu.loadingLevel");
      this.d = new WorldServer[3];
      this.i = new long[this.d.length][100];
      ISaveHandler var7 = this.m.a(var1, true);
      this.a(this.U(), var7);
      WorldInfo var9 = var7.d();
      WorldSettings var8;
      if (var9 == null) {
         if (this.X()) {
            var8 = DemoWorldServer.a;
         } else {
            var8 = new WorldSettings(var3, this.m(), this.l(), this.o(), var5);
            var8.a(var6);
            if (this.M) {
               var8.a();
            }
         }

         var9 = new WorldInfo(var8, var2);
      } else {
         var9.a(var2);
         var8 = new WorldSettings(var9);
      }

      for (int var10 = 0; var10 < this.d.length; var10++) {
         byte var11 = 0;
         if (var10 == 1) {
            var11 = -1;
         }

         if (var10 == 2) {
            var11 = 1;
         }

         if (var10 == 0) {
            if (this.X()) {
               this.d[var10] = (WorldServer)new DemoWorldServer(this, var7, var9, var11, this.c).b();
            } else {
               this.d[var10] = (WorldServer)new WorldServer(this, var7, var9, var11, this.c).b();
            }

            this.d[var10].a(var8);
         } else {
            this.d[var10] = (WorldServer)new WorldServerMulti(this, var7, var11, this.d[0], this.c).b();
         }

         this.d[var10].a(new WorldManager(this, this.d[var10]));
         if (!this.T()) {
            this.d[var10].P().a(this.m());
         }
      }

      this.v.a(this.d);
      this.a(this.n());
      this.k();
   }

   protected void k() {
      byte var1 = 16;
      byte var2 = 4;
      short var3 = 192;
      short var4 = 625;
      int var5 = 0;
      this.b("menu.generatingTerrain");
      byte var6 = 0;
      k.info("Preparing start region for level " + var6);
      WorldServer var7 = this.d[var6];
      BlockPos var8 = var7.M();
      long var9 = az();

      for (short var11 = -192; var11 <= 192 && this.v(); var11 += 16) {
         for (short var12 = -192; var12 <= 192 && this.v(); var12 += 16) {
            long var13 = az();
            if (var13 - var9 > 1000L) {
               this.a_("Preparing spawn area", var5 * 100 / 625);
               var9 = var13;
            }

            var5++;
            var7.b.c(var8.n() + var11 >> 4, var8.p() + var12 >> 4);
         }
      }

      this.s();
   }

   protected void a(String var1, ISaveHandler var2) {
      File var3 = new File(var2.b(), "resources.zip");
      if (var3.isFile()) {
         this.a_("level://" + var1 + "/" + var3.getName(), "");
      }
   }

   public abstract boolean l();

   public abstract WorldSettings.GameType m();

   public abstract EnumDifficulty n();

   public abstract boolean o();

   public abstract int p();

   public abstract boolean q();

   public abstract boolean r();

   protected void a_(String var1, int var2) {
      this.f = var1;
      this.g = var2;
      k.info(var1 + ": " + var2 + "%");
   }

   protected void s() {
      this.f = null;
      this.g = 0;
   }

   protected void a(boolean var1) {
      if (!this.N) {
         for (WorldServer var5 : this.d) {
            if (var5 != null) {
               if (!var1) {
                  k.info("Saving chunks for level '" + var5.P().k() + "'/" + var5.t.k());
               }

               try {
                  var5.a(true, null);
               } catch (MinecraftException var7) {
                  k.warn(var7.getMessage());
               }
            }
         }
      }
   }

   public void t() {
      if (!this.N) {
         k.info("Stopping server");
         if (this.aq() != null) {
            this.aq().b();
         }

         if (this.v != null) {
            k.info("Saving players");
            this.v.j();
            this.v.u();
         }

         if (this.d != null) {
            k.info("Saving worlds");
            this.a(false);

            for (int var1 = 0; var1 < this.d.length; var1++) {
               WorldServer var2 = this.d[var1];
               var2.o();
            }
         }

         if (this.n.d()) {
            this.n.e();
         }
      }
   }

   public boolean v() {
      return this.w;
   }

   public void w() {
      this.w = false;
   }

   protected void x() {
      l = this;
   }

   @Override
   public void run() {
      try {
         if (this.i()) {
            this.ab = az();
            long var1 = 0L;
            this.r.a(new ChatComponentText(this.E));
            this.r.a(new ServerStatusResponse.MinecraftProtocolVersionIdentifier("1.8.9", 47));
            this.a(this.r);

            while (this.w) {
               long var49 = az();
               long var5 = var49 - this.ab;
               if (var5 > 2000L && this.ab - this.R >= 15000L) {
                  k.warn(
                     "Can't keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)",
                     new Object[]{var5, var5 / 50L}
                  );
                  var5 = 2000L;
                  this.R = this.ab;
               }

               if (var5 < 0L) {
                  k.warn("Time ran backwards! Did the system time change?");
                  var5 = 0L;
               }

               var1 += var5;
               this.ab = var49;
               if (this.d[0].f()) {
                  this.A();
                  var1 = 0L;
               } else {
                  while (var1 > 50L) {
                     var1 -= 50L;
                     this.A();
                  }
               }

               Thread.sleep(Math.max(1L, 50L - var1));
               this.Q = true;
            }
         } else {
            this.a(null);
         }
      } catch (Throwable var46) {
         k.error("Encountered an unexpected exception", var46);
         Object var2 = null;
         if (var46 instanceof ReportedException) {
            var2 = this.b(((ReportedException)var46).a());
         } else {
            var2 = this.b(new CrashReport("Exception in server tick loop", var46));
         }

         File var3 = new File(new File(this.y(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt");
         if (((CrashReport)var2).a(var3)) {
            k.error("This crash report has been saved to: " + var3.getAbsolutePath());
         } else {
            k.error("We were unable to save this crash report to disk.");
         }

         this.a((CrashReport)var2);
      } finally {
         try {
            this.x = true;
            this.t();
         } catch (Throwable var44) {
            k.error("Exception stopping the server", var44);
         } finally {
            this.z();
         }
      }
   }

   private void a(ServerStatusResponse var1) {
      File var2 = this.d("server-icon.png");
      if (var2.isFile()) {
         ByteBuf var3 = Unpooled.buffer();

         try {
            BufferedImage var4 = ImageIO.read(var2);
            Validate.validState(var4.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
            Validate.validState(var4.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
            ImageIO.write(var4, "PNG", new ByteBufOutputStream(var3));
            ByteBuf var5 = Base64.encode(var3);
            var1.a("data:image/png;base64," + var5.toString(Charsets.UTF_8));
         } catch (Exception var9) {
            k.error("Couldn't load server icon", var9);
         } finally {
            var3.release();
         }
      }
   }

   public File y() {
      return new File(".");
   }

   protected void a(CrashReport var1) {
   }

   protected void z() {
   }

   public void A() {
      long var1 = System.nanoTime();
      this.y++;
      if (this.T) {
         this.T = false;
         this.c.a = true;
         this.c.a();
      }

      this.c.a("root");
      this.B();
      if (var1 - this.X >= 5000000000L) {
         this.X = var1;
         this.r.a(new ServerStatusResponse.PlayerCountData(this.J(), this.I()));
         GameProfile[] var3 = new GameProfile[Math.min(this.I(), 12)];
         int var4 = MathHelper.a(this.s, 0, this.I() - var3.length);

         for (int var5 = 0; var5 < var3.length; var5++) {
            var3[var5] = this.v.v().get(var4 + var5).cd();
         }

         Collections.shuffle(Arrays.asList(var3));
         this.r.b().a(var3);
      }

      if (this.y % 900 == 0) {
         this.c.a("save");
         this.v.j();
         this.a(true);
         this.c.b();
      }

      this.c.a("tallying");
      this.h[this.y % 100] = System.nanoTime() - var1;
      this.c.b();
      this.c.a("snooper");
      if (!this.n.d() && this.y > 100) {
         this.n.a();
      }

      if (this.y % 6000 == 0) {
         this.n.b();
      }

      this.c.b();
      this.c.b();
   }

   public void B() {
      this.c.a("jobs");
      synchronized (this.j) {
         while (!this.j.isEmpty()) {
            Util.a(this.j.poll(), k);
         }
      }

      this.c.c("levels");

      for (int var10 = 0; var10 < this.d.length; var10++) {
         long var2 = System.nanoTime();
         if (var10 == 0 || this.C()) {
            WorldServer var4 = this.d[var10];
            this.c.a(var4.P().k());
            if (this.y % 20 == 0) {
               this.c.a("timeSync");
               this.v.a(new S03PacketTimeUpdate(var4.K(), var4.L(), var4.Q().b("doDaylightCycle")), var4.t.q());
               this.c.b();
            }

            this.c.a("tick");

            try {
               var4.c();
            } catch (Throwable var8) {
               CrashReport var6 = CrashReport.a(var8, "Exception ticking world");
               var4.a(var6);
               throw new ReportedException(var6);
            }

            try {
               var4.i();
            } catch (Throwable var7) {
               CrashReport var12 = CrashReport.a(var7, "Exception ticking world entities");
               var4.a(var12);
               throw new ReportedException(var12);
            }

            this.c.b();
            this.c.a("tracker");
            var4.s().a();
            this.c.b();
            this.c.b();
         }

         this.i[var10][this.y % 100] = System.nanoTime() - var2;
      }

      this.c.c("connection");
      this.aq().c();
      this.c.c("players");
      this.v.e();
      this.c.c("tickables");

      for (int var11 = 0; var11 < this.p.size(); var11++) {
         this.p.get(var11).c();
      }

      this.c.b();
   }

   public boolean C() {
      return true;
   }

   public void D() {
      this.aa = new Thread(this, "Server thread");
      this.aa.start();
   }

   public File d(String var1) {
      return new File(this.y(), var1);
   }

   public void f(String var1) {
      k.warn(var1);
   }

   public WorldServer a(int var1) {
      if (var1 == -1) {
         return this.d[1];
      } else {
         return var1 == 1 ? this.d[2] : this.d[0];
      }
   }

   public String H() {
      return "1.8.9";
   }

   public int I() {
      return this.v.o();
   }

   public int J() {
      return this.v.p();
   }

   public String[] K() {
      return this.v.f();
   }

   public GameProfile[] L() {
      return this.v.g();
   }

   public String getServerModName() {
      return "vanilla";
   }

   public CrashReport b(CrashReport var1) {
      var1.g().a("Profiler Position", new Callable<String>() {
         public String a() throws Exception {
            return MinecraftServer.this.c.a ? MinecraftServer.this.c.c() : "N/A (disabled)";
         }
      });
      if (this.v != null) {
         var1.g().a("Player Count", new Callable<String>() {
            public String a() {
               return MinecraftServer.this.v.o() + " / " + MinecraftServer.this.v.p() + "; " + MinecraftServer.this.v.v();
            }
         });
      }

      return var1;
   }

   public List<String> a(ICommandSender var1, String var2, BlockPos var3) {
      ArrayList var4 = Lists.newArrayList();
      if (var2.startsWith("/")) {
         var2 = var2.substring(1);
         boolean var12 = !var2.contains(" ");
         List var13 = this.b.a(var1, var2, var3);
         if (var13 != null) {
            for (String var15 : var13) {
               if (var12) {
                  var4.add("/" + var15);
               } else {
                  var4.add(var15);
               }
            }
         }

         return var4;
      } else {
         String[] var5 = var2.split(" ", -1);
         String var6 = var5[var5.length - 1];

         for (String var10 : this.v.f()) {
            if (CommandBase.a(var6, var10)) {
               var4.add(var10);
            }
         }

         return var4;
      }
   }

   public static MinecraftServer N() {
      return l;
   }

   public boolean O() {
      return this.o != null;
   }

   @Override
   public String e_() {
      return "Server";
   }

   @Override
   public void a(IChatComponent var1) {
      k.info(var1.c());
   }

   @Override
   public boolean a(int var1, String var2) {
      return true;
   }

   public ICommandManager P() {
      return this.b;
   }

   public KeyPair Q() {
      return this.H;
   }

   public String S() {
      return this.I;
   }

   public void i(String var1) {
      this.I = var1;
   }

   public boolean T() {
      return this.I != null;
   }

   public String U() {
      return this.J;
   }

   public void j(String var1) {
      this.J = var1;
   }

   public void k(String var1) {
      this.K = var1;
   }

   public String V() {
      return this.K;
   }

   public void a(KeyPair var1) {
      this.H = var1;
   }

   public void a(EnumDifficulty var1) {
      for (int var2 = 0; var2 < this.d.length; var2++) {
         WorldServer var3 = this.d[var2];
         if (var3 != null) {
            if (var3.P().t()) {
               var3.P().a(EnumDifficulty.d);
               var3.a(true, true);
            } else if (this.T()) {
               var3.P().a(var1);
               var3.a(var3.aa() != EnumDifficulty.a, true);
            } else {
               var3.P().a(var1);
               var3.a(this.W(), this.A);
            }
         }
      }
   }

   protected boolean W() {
      return true;
   }

   public boolean X() {
      return this.L;
   }

   public void b(boolean var1) {
      this.L = var1;
   }

   public void c(boolean var1) {
      this.M = var1;
   }

   public ISaveFormat Y() {
      return this.m;
   }

   public void aa() {
      this.N = true;
      this.Y().d();

      for (int var1 = 0; var1 < this.d.length; var1++) {
         WorldServer var2 = this.d[var1];
         if (var2 != null) {
            var2.o();
         }
      }

      this.Y().e(this.d[0].O().g());
      this.w();
   }

   public String ab() {
      return this.O;
   }

   public String ac() {
      return this.P;
   }

   public void a_(String var1, String var2) {
      this.O = var1;
      this.P = var2;
   }

   @Override
   public void a(PlayerUsageSnooper var1) {
      var1.a("whitelist_enabled", false);
      var1.a("whitelist_count", 0);
      if (this.v != null) {
         var1.a("players_current", this.I());
         var1.a("players_max", this.J());
         var1.a("players_seen", this.v.q().length);
      }

      var1.a("uses_auth", this.z);
      var1.a("gui_state", this.as() ? "enabled" : "disabled");
      var1.a("run_time", (az() - var1.g()) / 60L * 1000L);
      var1.a("avg_tick_ms", (int)(MathHelper.a(this.h) * 1.0E-6));
      int var2 = 0;
      if (this.d != null) {
         for (int var3 = 0; var3 < this.d.length; var3++) {
            if (this.d[var3] != null) {
               WorldServer var4 = this.d[var3];
               WorldInfo var5 = var4.P();
               var1.a("world[" + var2 + "][dimension]", var4.t.q());
               var1.a("world[" + var2 + "][mode]", var5.r());
               var1.a("world[" + var2 + "][difficulty]", var4.aa());
               var1.a("world[" + var2 + "][hardcore]", var5.t());
               var1.a("world[" + var2 + "][generator_name]", var5.u().a());
               var1.a("world[" + var2 + "][generator_version]", var5.u().d());
               var1.a("world[" + var2 + "][height]", this.F);
               var1.a("world[" + var2 + "][chunks_loaded]", var4.N().g());
               var2++;
            }
         }
      }

      var1.a("worlds", var2);
   }

   @Override
   public void b(PlayerUsageSnooper var1) {
      var1.b("singleplayer", this.T());
      var1.b("server_brand", this.getServerModName());
      var1.b("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
      var1.b("dedicated", this.ae());
   }

   @Override
   public boolean ad() {
      return true;
   }

   public abstract boolean ae();

   public boolean af() {
      return this.z;
   }

   public void d(boolean var1) {
      this.z = var1;
   }

   public boolean ag() {
      return this.A;
   }

   public void e(boolean var1) {
      this.A = var1;
   }

   public boolean ah() {
      return this.B;
   }

   public abstract boolean ai();

   public void f(boolean var1) {
      this.B = var1;
   }

   public boolean aj() {
      return this.C;
   }

   public void g(boolean var1) {
      this.C = var1;
   }

   public boolean ak() {
      return this.D;
   }

   public void h(boolean var1) {
      this.D = var1;
   }

   public abstract boolean al();

   public String am() {
      return this.E;
   }

   public void l(String var1) {
      this.E = var1;
   }

   public int an() {
      return this.F;
   }

   public void c(int var1) {
      this.F = var1;
   }

   public boolean ao() {
      return this.x;
   }

   public ServerConfigurationManager ap() {
      return this.v;
   }

   public void a(ServerConfigurationManager var1) {
      this.v = var1;
   }

   public void a(WorldSettings.GameType var1) {
      for (int var2 = 0; var2 < this.d.length; var2++) {
         N().d[var2].P().a(var1);
      }
   }

   public NetworkSystem aq() {
      return this.q;
   }

   public boolean ar() {
      return this.Q;
   }

   public boolean as() {
      return false;
   }

   public abstract String a(WorldSettings.GameType var1, boolean var2);

   public int at() {
      return this.y;
   }

   public void au() {
      this.T = true;
   }

   public PlayerUsageSnooper av() {
      return this.n;
   }

   @Override
   public BlockPos c() {
      return BlockPos.a;
   }

   @Override
   public Vec3 d() {
      return new Vec3(0.0, 0.0, 0.0);
   }

   @Override
   public World e() {
      return this.d[0];
   }

   @Override
   public Entity f() {
      return null;
   }

   public int aw() {
      return 16;
   }

   public boolean a(World var1, BlockPos var2, EntityPlayer var3) {
      return false;
   }

   public boolean ax() {
      return this.U;
   }

   public Proxy ay() {
      return this.e;
   }

   public static long az() {
      return System.currentTimeMillis();
   }

   public int aA() {
      return this.G;
   }

   public void d(int var1) {
      this.G = var1;
   }

   @Override
   public IChatComponent f_() {
      return new ChatComponentText(this.e_());
   }

   public boolean aB() {
      return true;
   }

   public MinecraftSessionService aD() {
      return this.W;
   }

   public GameProfileRepository aE() {
      return this.Y;
   }

   public PlayerProfileCache aF() {
      return this.Z;
   }

   public ServerStatusResponse aG() {
      return this.r;
   }

   public void aH() {
      this.X = 0L;
   }

   public Entity a(UUID var1) {
      for (WorldServer var5 : this.d) {
         if (var5 != null) {
            Entity var6 = var5.a(var1);
            if (var6 != null) {
               return var6;
            }
         }
      }

      return null;
   }

   @Override
   public boolean u_() {
      return N().d[0].Q().b("sendCommandFeedback");
   }

   @Override
   public void a(CommandResultStats.Type var1, int var2) {
   }

   public int aI() {
      return 29999984;
   }

   public <V> ListenableFuture<V> a(Callable<V> var1) {
      Validate.notNull(var1);
      if (!this.aJ() && !this.ao()) {
         ListenableFutureTask var2 = ListenableFutureTask.create(var1);
         synchronized (this.j) {
            this.j.add(var2);
            return var2;
         }
      } else {
         try {
            return Futures.immediateFuture(var1.call());
         } catch (Exception var6) {
            return Futures.immediateFailedCheckedFuture(var6);
         }
      }
   }

   @Override
   public ListenableFuture<Object> a(Runnable var1) {
      Validate.notNull(var1);
      return this.a(Executors.callable(var1));
   }

   @Override
   public boolean aJ() {
      return Thread.currentThread() == this.aa;
   }

   public int aK() {
      return 256;
   }
}
