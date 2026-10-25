package net.minecraft.world.storage;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

public class WorldInfo {
   public static final EnumDifficulty a = EnumDifficulty.c;
   private long b;
   private WorldType c = WorldType.b;
   private String d = "";
   private int e;
   private int f;
   private int g;
   private long h;
   private long i;
   private long j;
   private long k;
   private NBTTagCompound l;
   private int m;
   private String n;
   private int o;
   private int p;
   private boolean q;
   private int r;
   private boolean s;
   private int t;
   private WorldSettings.GameType u;
   private boolean v;
   private boolean w;
   private boolean x;
   private boolean y;
   private EnumDifficulty z;
   private boolean A;
   private double B = 0.0;
   private double C = 0.0;
   private double D = 6.0E7;
   private long E = 0L;
   private double F = 0.0;
   private double G = 5.0;
   private double H = 0.2;
   private int I = 5;
   private int J = 15;
   private GameRules K = new GameRules();

   protected WorldInfo() {
   }

   public WorldInfo(NBTTagCompound var1) {
      this.b = var1.g("RandomSeed");
      if (var1.b("generatorName", 8)) {
         String var2 = var1.j("generatorName");
         this.c = WorldType.a(var2);
         if (this.c == null) {
            this.c = WorldType.b;
         } else if (this.c.f()) {
            int var3 = 0;
            if (var1.b("generatorVersion", 99)) {
               var3 = var1.f("generatorVersion");
            }

            this.c = this.c.a(var3);
         }

         if (var1.b("generatorOptions", 8)) {
            this.d = var1.j("generatorOptions");
         }
      }

      this.u = WorldSettings.GameType.a(var1.f("GameType"));
      if (var1.b("MapFeatures", 99)) {
         this.v = var1.n("MapFeatures");
      } else {
         this.v = true;
      }

      this.e = var1.f("SpawnX");
      this.f = var1.f("SpawnY");
      this.g = var1.f("SpawnZ");
      this.h = var1.g("Time");
      if (var1.b("DayTime", 99)) {
         this.i = var1.g("DayTime");
      } else {
         this.i = this.h;
      }

      this.j = var1.g("LastPlayed");
      this.k = var1.g("SizeOnDisk");
      this.n = var1.j("LevelName");
      this.o = var1.f("version");
      this.p = var1.f("clearWeatherTime");
      this.r = var1.f("rainTime");
      this.q = var1.n("raining");
      this.t = var1.f("thunderTime");
      this.s = var1.n("thundering");
      this.w = var1.n("hardcore");
      if (var1.b("initialized", 99)) {
         this.y = var1.n("initialized");
      } else {
         this.y = true;
      }

      if (var1.b("allowCommands", 99)) {
         this.x = var1.n("allowCommands");
      } else {
         this.x = this.u == WorldSettings.GameType.c;
      }

      if (var1.b("Player", 10)) {
         this.l = var1.m("Player");
         this.m = this.l.f("Dimension");
      }

      if (var1.b("GameRules", 10)) {
         this.K.a(var1.m("GameRules"));
      }

      if (var1.b("Difficulty", 99)) {
         this.z = EnumDifficulty.a(var1.d("Difficulty"));
      }

      if (var1.b("DifficultyLocked", 1)) {
         this.A = var1.n("DifficultyLocked");
      }

      if (var1.b("BorderCenterX", 99)) {
         this.B = var1.i("BorderCenterX");
      }

      if (var1.b("BorderCenterZ", 99)) {
         this.C = var1.i("BorderCenterZ");
      }

      if (var1.b("BorderSize", 99)) {
         this.D = var1.i("BorderSize");
      }

      if (var1.b("BorderSizeLerpTime", 99)) {
         this.E = var1.g("BorderSizeLerpTime");
      }

      if (var1.b("BorderSizeLerpTarget", 99)) {
         this.F = var1.i("BorderSizeLerpTarget");
      }

      if (var1.b("BorderSafeZone", 99)) {
         this.G = var1.i("BorderSafeZone");
      }

      if (var1.b("BorderDamagePerBlock", 99)) {
         this.H = var1.i("BorderDamagePerBlock");
      }

      if (var1.b("BorderWarningBlocks", 99)) {
         this.I = var1.f("BorderWarningBlocks");
      }

      if (var1.b("BorderWarningTime", 99)) {
         this.J = var1.f("BorderWarningTime");
      }
   }

   public WorldInfo(WorldSettings var1, String var2) {
      this.a(var1);
      this.n = var2;
      this.z = a;
      this.y = false;
   }

   public void a(WorldSettings var1) {
      this.b = var1.d();
      this.u = var1.e();
      this.v = var1.g();
      this.w = var1.f();
      this.c = var1.h();
      this.d = var1.j();
      this.x = var1.i();
   }

   public WorldInfo(WorldInfo var1) {
      this.b = var1.b;
      this.c = var1.c;
      this.d = var1.d;
      this.u = var1.u;
      this.v = var1.v;
      this.e = var1.e;
      this.f = var1.f;
      this.g = var1.g;
      this.h = var1.h;
      this.i = var1.i;
      this.j = var1.j;
      this.k = var1.k;
      this.l = var1.l;
      this.m = var1.m;
      this.n = var1.n;
      this.o = var1.o;
      this.r = var1.r;
      this.q = var1.q;
      this.t = var1.t;
      this.s = var1.s;
      this.w = var1.w;
      this.x = var1.x;
      this.y = var1.y;
      this.K = var1.K;
      this.z = var1.z;
      this.A = var1.A;
      this.B = var1.B;
      this.C = var1.C;
      this.D = var1.D;
      this.E = var1.E;
      this.F = var1.F;
      this.G = var1.G;
      this.H = var1.H;
      this.J = var1.J;
      this.I = var1.I;
   }

   public NBTTagCompound a() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.a(var1, this.l);
      return var1;
   }

   public NBTTagCompound a(NBTTagCompound var1) {
      NBTTagCompound var2 = new NBTTagCompound();
      this.a(var2, var1);
      return var2;
   }

   private void a(NBTTagCompound var1, NBTTagCompound var2) {
      var1.a("RandomSeed", this.b);
      var1.a("generatorName", this.c.a());
      var1.a("generatorVersion", this.c.d());
      var1.a("generatorOptions", this.d);
      var1.a("GameType", this.u.a());
      var1.a("MapFeatures", this.v);
      var1.a("SpawnX", this.e);
      var1.a("SpawnY", this.f);
      var1.a("SpawnZ", this.g);
      var1.a("Time", this.h);
      var1.a("DayTime", this.i);
      var1.a("SizeOnDisk", this.k);
      var1.a("LastPlayed", MinecraftServer.az());
      var1.a("LevelName", this.n);
      var1.a("version", this.o);
      var1.a("clearWeatherTime", this.p);
      var1.a("rainTime", this.r);
      var1.a("raining", this.q);
      var1.a("thunderTime", this.t);
      var1.a("thundering", this.s);
      var1.a("hardcore", this.w);
      var1.a("allowCommands", this.x);
      var1.a("initialized", this.y);
      var1.a("BorderCenterX", this.B);
      var1.a("BorderCenterZ", this.C);
      var1.a("BorderSize", this.D);
      var1.a("BorderSizeLerpTime", this.E);
      var1.a("BorderSafeZone", this.G);
      var1.a("BorderDamagePerBlock", this.H);
      var1.a("BorderSizeLerpTarget", this.F);
      var1.a("BorderWarningBlocks", (double)this.I);
      var1.a("BorderWarningTime", (double)this.J);
      if (this.z != null) {
         var1.a("Difficulty", (byte)this.z.a());
      }

      var1.a("DifficultyLocked", this.A);
      var1.a("GameRules", this.K.a());
      if (var2 != null) {
         var1.a("Player", var2);
      }
   }

   public long b() {
      return this.b;
   }

   public int c() {
      return this.e;
   }

   public int d() {
      return this.f;
   }

   public int e() {
      return this.g;
   }

   public long f() {
      return this.h;
   }

   public long g() {
      return this.i;
   }

   public long h() {
      return this.k;
   }

   public NBTTagCompound i() {
      return this.l;
   }

   public void a(int var1) {
      this.e = var1;
   }

   public void b(int var1) {
      this.f = var1;
   }

   public void c(int var1) {
      this.g = var1;
   }

   public void b(long var1) {
      this.h = var1;
   }

   public void c(long var1) {
      this.i = var1;
   }

   public void a(BlockPos var1) {
      this.e = var1.n();
      this.f = var1.o();
      this.g = var1.p();
   }

   public String k() {
      return this.n;
   }

   public void a(String var1) {
      this.n = var1;
   }

   public int l() {
      return this.o;
   }

   public void e(int var1) {
      this.o = var1;
   }

   public long m() {
      return this.j;
   }

   public int A() {
      return this.p;
   }

   public void i(int var1) {
      this.p = var1;
   }

   public boolean n() {
      return this.s;
   }

   public void a(boolean var1) {
      this.s = var1;
   }

   public int o() {
      return this.t;
   }

   public void f(int var1) {
      this.t = var1;
   }

   public boolean p() {
      return this.q;
   }

   public void b(boolean var1) {
      this.q = var1;
   }

   public int q() {
      return this.r;
   }

   public void g(int var1) {
      this.r = var1;
   }

   public WorldSettings.GameType r() {
      return this.u;
   }

   public boolean s() {
      return this.v;
   }

   public void f(boolean var1) {
      this.v = var1;
   }

   public void a(WorldSettings.GameType var1) {
      this.u = var1;
   }

   public boolean t() {
      return this.w;
   }

   public void g(boolean var1) {
      this.w = var1;
   }

   public WorldType u() {
      return this.c;
   }

   public void a(WorldType var1) {
      this.c = var1;
   }

   public String B() {
      return this.d;
   }

   public boolean v() {
      return this.x;
   }

   public void c(boolean var1) {
      this.x = var1;
   }

   public boolean w() {
      return this.y;
   }

   public void d(boolean var1) {
      this.y = var1;
   }

   public GameRules x() {
      return this.K;
   }

   public double C() {
      return this.B;
   }

   public double D() {
      return this.C;
   }

   public double E() {
      return this.D;
   }

   public void a(double var1) {
      this.D = var1;
   }

   public long F() {
      return this.E;
   }

   public void e(long var1) {
      this.E = var1;
   }

   public double G() {
      return this.F;
   }

   public void b(double var1) {
      this.F = var1;
   }

   public void c(double var1) {
      this.C = var1;
   }

   public void d(double var1) {
      this.B = var1;
   }

   public double H() {
      return this.G;
   }

   public void e(double var1) {
      this.G = var1;
   }

   public double I() {
      return this.H;
   }

   public void f(double var1) {
      this.H = var1;
   }

   public int J() {
      return this.I;
   }

   public int K() {
      return this.J;
   }

   public void j(int var1) {
      this.I = var1;
   }

   public void k(int var1) {
      this.J = var1;
   }

   public EnumDifficulty y() {
      return this.z;
   }

   public void a(EnumDifficulty var1) {
      this.z = var1;
   }

   public boolean z() {
      return this.A;
   }

   public void e(boolean var1) {
      this.A = var1;
   }

   public void a(CrashReportCategory var1) {
      var1.a("Level seed", new Callable<String>() {
         public String a() throws Exception {
            return String.valueOf(WorldInfo.this.b());
         }
      });
      var1.a(
         "Level generator",
         new Callable<String>() {
            public String a() throws Exception {
               return String.format(
                  "ID %02d - %s, ver %d. Features enabled: %b", WorldInfo.this.c.g(), WorldInfo.this.c.a(), WorldInfo.this.c.d(), WorldInfo.this.v
               );
            }
         }
      );
      var1.a("Level generator options", new Callable<String>() {
         public String a() throws Exception {
            return WorldInfo.this.d;
         }
      });
      var1.a("Level spawn location", new Callable<String>() {
         public String a() throws Exception {
            return CrashReportCategory.a(WorldInfo.this.e, WorldInfo.this.f, WorldInfo.this.g);
         }
      });
      var1.a("Level time", new Callable<String>() {
         public String a() throws Exception {
            return String.format("%d game time, %d day time", WorldInfo.this.h, WorldInfo.this.i);
         }
      });
      var1.a("Level dimension", new Callable<String>() {
         public String a() throws Exception {
            return String.valueOf(WorldInfo.this.m);
         }
      });
      var1.a("Level storage version", new Callable<String>() {
         public String a() throws Exception {
            String var1 = "Unknown?";

            try {
               switch (WorldInfo.this.o) {
                  case 19132:
                     var1 = "McRegion";
                     break;
                  case 19133:
                     var1 = "Anvil";
               }
            } catch (Throwable var3) {
            }

            return String.format("0x%05X - %s", WorldInfo.this.o, var1);
         }
      });
      var1.a("Level weather", new Callable<String>() {
         public String a() throws Exception {
            return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", WorldInfo.this.r, WorldInfo.this.q, WorldInfo.this.t, WorldInfo.this.s);
         }
      });
      var1.a(
         "Level game mode",
         new Callable<String>() {
            public String a() throws Exception {
               return String.format(
                  "Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", WorldInfo.this.u.b(), WorldInfo.this.u.a(), WorldInfo.this.w, WorldInfo.this.x
               );
            }
         }
      );
   }
}
