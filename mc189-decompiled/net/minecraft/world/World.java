package net.minecraft.world;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;

public abstract class World implements IBlockAccess {
   private int a = 63;
   protected boolean e;
   public final List<Entity> f = Lists.newArrayList();
   protected final List<Entity> g = Lists.newArrayList();
   public final List<TileEntity> h = Lists.newArrayList();
   public final List<TileEntity> i = Lists.newArrayList();
   private final List<TileEntity> b = Lists.newArrayList();
   private final List<TileEntity> c = Lists.newArrayList();
   public final List<EntityPlayer> j = Lists.newArrayList();
   public final List<Entity> k = Lists.newArrayList();
   protected final IntHashMap<Entity> l = new IntHashMap<>();
   private long d = 16777215L;
   private int I;
   protected int m = new Random().nextInt();
   protected final int n = 1013904223;
   protected float o;
   protected float p;
   protected float q;
   protected float r;
   private int J;
   public final Random s = new Random();
   public final WorldProvider t;
   protected List<IWorldAccess> u = Lists.newArrayList();
   protected IChunkProvider v;
   protected final ISaveHandler w;
   protected WorldInfo x;
   protected boolean y;
   protected MapStorage z;
   protected VillageCollection A;
   public final Profiler B;
   private final Calendar K = Calendar.getInstance();
   protected Scoreboard C = new Scoreboard();
   public final boolean D;
   protected Set<ChunkCoordIntPair> E = Sets.newHashSet();
   private int L = this.s.nextInt(12000);
   protected boolean F = true;
   protected boolean G = true;
   private boolean M;
   private final WorldBorder N;
   int[] H = new int[32768];

   protected World(ISaveHandler var1, WorldInfo var2, WorldProvider var3, Profiler var4, boolean var5) {
      this.w = var1;
      this.B = var4;
      this.x = var2;
      this.t = var3;
      this.D = var5;
      this.N = var3.r();
   }

   public World b() {
      return this;
   }

   @Override
   public BiomeGenBase b(final BlockPos var1) {
      if (this.e(var1)) {
         Chunk var2 = this.f(var1);

         try {
            return var2.a(var1, this.t.m());
         } catch (Throwable var6) {
            CrashReport var4 = CrashReport.a(var6, "Getting biome");
            CrashReportCategory var5 = var4.a("Coordinates of biome request");
            var5.a("Location", new Callable<String>() {
               public String a() throws Exception {
                  return CrashReportCategory.a(var1);
               }
            });
            throw new ReportedException(var4);
         }
      } else {
         return this.t.m().a(var1, BiomeGenBase.q);
      }
   }

   public WorldChunkManager v() {
      return this.t.m();
   }

   protected abstract IChunkProvider k();

   public void a(WorldSettings var1) {
      this.x.d(true);
   }

   public void g() {
      this.B(new BlockPos(8, 64, 8));
   }

   public Block c(BlockPos var1) {
      BlockPos var2 = new BlockPos(var1.n(), this.F(), var1.p());

      while (!this.d(var2.a())) {
         var2 = var2.a();
      }

      return this.p(var2).c();
   }

   private boolean a(BlockPos var1) {
      return var1.n() >= -30000000 && var1.p() >= -30000000 && var1.n() < 30000000 && var1.p() < 30000000 && var1.o() >= 0 && var1.o() < 256;
   }

   @Override
   public boolean d(BlockPos var1) {
      return this.p(var1).c().t() == Material.a;
   }

   public boolean e(BlockPos var1) {
      return this.a(var1, true);
   }

   public boolean a(BlockPos var1, boolean var2) {
      return !this.a(var1) ? false : this.a(var1.n() >> 4, var1.p() >> 4, var2);
   }

   public boolean a(BlockPos var1, int var2) {
      return this.a(var1, var2, true);
   }

   public boolean a(BlockPos var1, int var2, boolean var3) {
      return this.a(var1.n() - var2, var1.o() - var2, var1.p() - var2, var1.n() + var2, var1.o() + var2, var1.p() + var2, var3);
   }

   public boolean a(BlockPos var1, BlockPos var2) {
      return this.a(var1, var2, true);
   }

   public boolean a(BlockPos var1, BlockPos var2, boolean var3) {
      return this.a(var1.n(), var1.o(), var1.p(), var2.n(), var2.o(), var2.p(), var3);
   }

   public boolean a(StructureBoundingBox var1) {
      return this.b(var1, true);
   }

   public boolean b(StructureBoundingBox var1, boolean var2) {
      return this.a(var1.a, var1.b, var1.c, var1.d, var1.e, var1.f, var2);
   }

   private boolean a(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      if (var5 >= 0 && var2 < 256) {
         var1 >>= 4;
         var3 >>= 4;
         var4 >>= 4;
         var6 >>= 4;

         for (int var8 = var1; var8 <= var4; var8++) {
            for (int var9 = var3; var9 <= var6; var9++) {
               if (!this.a(var8, var9, var7)) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean a(int var1, int var2, boolean var3) {
      return this.v.a(var1, var2) && (var3 || !this.v.d(var1, var2).f());
   }

   public Chunk f(BlockPos var1) {
      return this.a(var1.n() >> 4, var1.p() >> 4);
   }

   public Chunk a(int var1, int var2) {
      return this.v.d(var1, var2);
   }

   public boolean a(BlockPos var1, IBlockState var2, int var3) {
      if (!this.a(var1)) {
         return false;
      } else if (!this.D && this.x.u() == WorldType.g) {
         return false;
      } else {
         Chunk var4 = this.f(var1);
         Block var5 = var2.c();
         IBlockState var6 = var4.a(var1, var2);
         if (var6 == null) {
            return false;
         } else {
            Block var7 = var6.c();
            if (var5.p() != var7.p() || var5.r() != var7.r()) {
               this.B.a("checkLight");
               this.x(var1);
               this.B.b();
            }

            if ((var3 & 2) != 0 && (!this.D || (var3 & 4) == 0) && var4.i()) {
               this.h(var1);
            }

            if (!this.D && (var3 & 1) != 0) {
               this.b(var1, var6.c());
               if (var5.O()) {
                  this.e(var1, var5);
               }
            }

            return true;
         }
      }
   }

   public boolean g(BlockPos var1) {
      return this.a(var1, Blocks.a.Q(), 3);
   }

   public boolean b(BlockPos var1, boolean var2) {
      IBlockState var3 = this.p(var1);
      Block var4 = var3.c();
      if (var4.t() == Material.a) {
         return false;
      } else {
         this.b(2001, var1, Block.f(var3));
         if (var2) {
            var4.b(this, var1, var3, 0);
         }

         return this.a(var1, Blocks.a.Q(), 3);
      }
   }

   public boolean a(BlockPos var1, IBlockState var2) {
      return this.a(var1, var2, 3);
   }

   public void h(BlockPos var1) {
      for (int var2 = 0; var2 < this.u.size(); var2++) {
         this.u.get(var2).a(var1);
      }
   }

   public void b(BlockPos var1, Block var2) {
      if (this.x.u() != WorldType.g) {
         this.c(var1, var2);
      }
   }

   public void a(int var1, int var2, int var3, int var4) {
      if (var3 > var4) {
         int var5 = var4;
         var4 = var3;
         var3 = var5;
      }

      if (!this.t.o()) {
         for (int var6 = var3; var6 <= var4; var6++) {
            this.c(EnumSkyBlock.a, new BlockPos(var1, var6, var2));
         }
      }

      this.b(var1, var3, var2, var1, var4, var2);
   }

   public void b(BlockPos var1, BlockPos var2) {
      this.b(var1.n(), var1.o(), var1.p(), var2.n(), var2.o(), var2.p());
   }

   public void b(int var1, int var2, int var3, int var4, int var5, int var6) {
      for (int var7 = 0; var7 < this.u.size(); var7++) {
         this.u.get(var7).a(var1, var2, var3, var4, var5, var6);
      }
   }

   public void c(BlockPos var1, Block var2) {
      this.d(var1.e(), var2);
      this.d(var1.f(), var2);
      this.d(var1.b(), var2);
      this.d(var1.a(), var2);
      this.d(var1.c(), var2);
      this.d(var1.d(), var2);
   }

   public void a(BlockPos var1, Block var2, EnumFacing var3) {
      if (var3 != EnumFacing.e) {
         this.d(var1.e(), var2);
      }

      if (var3 != EnumFacing.f) {
         this.d(var1.f(), var2);
      }

      if (var3 != EnumFacing.a) {
         this.d(var1.b(), var2);
      }

      if (var3 != EnumFacing.b) {
         this.d(var1.a(), var2);
      }

      if (var3 != EnumFacing.c) {
         this.d(var1.c(), var2);
      }

      if (var3 != EnumFacing.d) {
         this.d(var1.d(), var2);
      }
   }

   public void d(BlockPos var1, final Block var2) {
      if (!this.D) {
         IBlockState var3 = this.p(var1);

         try {
            var3.c().a(this, var1, var3, var2);
         } catch (Throwable var7) {
            CrashReport var5 = CrashReport.a(var7, "Exception while updating neighbours");
            CrashReportCategory var6 = var5.a("Block being updated");
            var6.a("Source block type", new Callable<String>() {
               public String a() throws Exception {
                  try {
                     return String.format("ID #%d (%s // %s)", Block.a(var2), var2.a(), var2.getClass().getCanonicalName());
                  } catch (Throwable var2x) {
                     return "ID #" + Block.a(var2);
                  }
               }
            });
            CrashReportCategory.a(var6, var1, var3);
            throw new ReportedException(var5);
         }
      }
   }

   public boolean a(BlockPos var1, Block var2) {
      return false;
   }

   public boolean i(BlockPos var1) {
      return this.f(var1).d(var1);
   }

   public boolean j(BlockPos var1) {
      if (var1.o() >= this.F()) {
         return this.i(var1);
      } else {
         BlockPos var2 = new BlockPos(var1.n(), this.F(), var1.p());
         if (!this.i(var2)) {
            return false;
         } else {
            for (BlockPos var4 = var2.b(); var4.o() > var1.o(); var4 = var4.b()) {
               Block var3 = this.p(var4).c();
               if (var3.p() > 0 && !var3.t().d()) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public int k(BlockPos var1) {
      if (var1.o() < 0) {
         return 0;
      } else {
         if (var1.o() >= 256) {
            var1 = new BlockPos(var1.n(), 255, var1.p());
         }

         return this.f(var1).a(var1, 0);
      }
   }

   public int l(BlockPos var1) {
      return this.c(var1, true);
   }

   public int c(BlockPos var1, boolean var2) {
      if (var1.n() < -30000000 || var1.p() < -30000000 || var1.n() >= 30000000 || var1.p() >= 30000000) {
         return 15;
      } else if (var2 && this.p(var1).c().s()) {
         int var8 = this.c(var1.a(), false);
         int var4 = this.c(var1.f(), false);
         int var5 = this.c(var1.e(), false);
         int var6 = this.c(var1.d(), false);
         int var7 = this.c(var1.c(), false);
         if (var4 > var8) {
            var8 = var4;
         }

         if (var5 > var8) {
            var8 = var5;
         }

         if (var6 > var8) {
            var8 = var6;
         }

         if (var7 > var8) {
            var8 = var7;
         }

         return var8;
      } else if (var1.o() < 0) {
         return 0;
      } else {
         if (var1.o() >= 256) {
            var1 = new BlockPos(var1.n(), 255, var1.p());
         }

         Chunk var3 = this.f(var1);
         return var3.a(var1, this.I);
      }
   }

   public BlockPos m(BlockPos var1) {
      int var2;
      if (var1.n() >= -30000000 && var1.p() >= -30000000 && var1.n() < 30000000 && var1.p() < 30000000) {
         if (this.a(var1.n() >> 4, var1.p() >> 4, true)) {
            var2 = this.a(var1.n() >> 4, var1.p() >> 4).b(var1.n() & 15, var1.p() & 15);
         } else {
            var2 = 0;
         }
      } else {
         var2 = this.F() + 1;
      }

      return new BlockPos(var1.n(), var2, var1.p());
   }

   public int b(int var1, int var2) {
      if (var1 >= -30000000 && var2 >= -30000000 && var1 < 30000000 && var2 < 30000000) {
         if (!this.a(var1 >> 4, var2 >> 4, true)) {
            return 0;
         } else {
            Chunk var3 = this.a(var1 >> 4, var2 >> 4);
            return var3.v();
         }
      } else {
         return this.F() + 1;
      }
   }

   public int a(EnumSkyBlock var1, BlockPos var2) {
      if (this.t.o() && var1 == EnumSkyBlock.a) {
         return 0;
      } else {
         if (var2.o() < 0) {
            var2 = new BlockPos(var2.n(), 0, var2.p());
         }

         if (!this.a(var2)) {
            return var1.c;
         } else if (!this.e(var2)) {
            return var1.c;
         } else if (this.p(var2).c().s()) {
            int var8 = this.b(var1, var2.a());
            int var4 = this.b(var1, var2.f());
            int var5 = this.b(var1, var2.e());
            int var6 = this.b(var1, var2.d());
            int var7 = this.b(var1, var2.c());
            if (var4 > var8) {
               var8 = var4;
            }

            if (var5 > var8) {
               var8 = var5;
            }

            if (var6 > var8) {
               var8 = var6;
            }

            if (var7 > var8) {
               var8 = var7;
            }

            return var8;
         } else {
            Chunk var3 = this.f(var2);
            return var3.a(var1, var2);
         }
      }
   }

   public int b(EnumSkyBlock var1, BlockPos var2) {
      if (var2.o() < 0) {
         var2 = new BlockPos(var2.n(), 0, var2.p());
      }

      if (!this.a(var2)) {
         return var1.c;
      } else if (!this.e(var2)) {
         return var1.c;
      } else {
         Chunk var3 = this.f(var2);
         return var3.a(var1, var2);
      }
   }

   public void a(EnumSkyBlock var1, BlockPos var2, int var3) {
      if (this.a(var2)) {
         if (this.e(var2)) {
            Chunk var4 = this.f(var2);
            var4.a(var1, var2, var3);
            this.n(var2);
         }
      }
   }

   public void n(BlockPos var1) {
      for (int var2 = 0; var2 < this.u.size(); var2++) {
         this.u.get(var2).b(var1);
      }
   }

   @Override
   public int b(BlockPos var1, int var2) {
      int var3 = this.a(EnumSkyBlock.a, var1);
      int var4 = this.a(EnumSkyBlock.b, var1);
      if (var4 < var2) {
         var4 = var2;
      }

      return var3 << 20 | var4 << 4;
   }

   public float o(BlockPos var1) {
      return this.t.p()[this.l(var1)];
   }

   @Override
   public IBlockState p(BlockPos var1) {
      if (!this.a(var1)) {
         return Blocks.a.Q();
      } else {
         Chunk var2 = this.f(var1);
         return var2.g(var1);
      }
   }

   public boolean w() {
      return this.I < 4;
   }

   public MovingObjectPosition a(Vec3 var1, Vec3 var2) {
      return this.a(var1, var2, false, false, false);
   }

   public MovingObjectPosition a(Vec3 var1, Vec3 var2, boolean var3) {
      return this.a(var1, var2, var3, false, false);
   }

   public MovingObjectPosition a(Vec3 var1, Vec3 var2, boolean var3, boolean var4, boolean var5) {
      if (Double.isNaN(var1.a) || Double.isNaN(var1.b) || Double.isNaN(var1.c)) {
         return null;
      } else if (!Double.isNaN(var2.a) && !Double.isNaN(var2.b) && !Double.isNaN(var2.c)) {
         int var6 = MathHelper.c(var2.a);
         int var7 = MathHelper.c(var2.b);
         int var8 = MathHelper.c(var2.c);
         int var9 = MathHelper.c(var1.a);
         int var10 = MathHelper.c(var1.b);
         int var11 = MathHelper.c(var1.c);
         BlockPos var12 = new BlockPos(var9, var10, var11);
         IBlockState var13 = this.p(var12);
         Block var14 = var13.c();
         if ((!var4 || var14.a(this, var12, var13) != null) && var14.a(var13, var3)) {
            MovingObjectPosition var15 = var14.a(this, var12, var1, var2);
            if (var15 != null) {
               return var15;
            }
         }

         MovingObjectPosition var41 = null;
         int var42 = 200;

         while (var42-- >= 0) {
            if (Double.isNaN(var1.a) || Double.isNaN(var1.b) || Double.isNaN(var1.c)) {
               return null;
            }

            if (var9 == var6 && var10 == var7 && var11 == var8) {
               return var5 ? var41 : null;
            }

            boolean var43 = true;
            boolean var16 = true;
            boolean var17 = true;
            double var18 = 999.0;
            double var20 = 999.0;
            double var22 = 999.0;
            if (var6 > var9) {
               var18 = var9 + 1.0;
            } else if (var6 < var9) {
               var18 = var9 + 0.0;
            } else {
               var43 = false;
            }

            if (var7 > var10) {
               var20 = var10 + 1.0;
            } else if (var7 < var10) {
               var20 = var10 + 0.0;
            } else {
               var16 = false;
            }

            if (var8 > var11) {
               var22 = var11 + 1.0;
            } else if (var8 < var11) {
               var22 = var11 + 0.0;
            } else {
               var17 = false;
            }

            double var24 = 999.0;
            double var26 = 999.0;
            double var28 = 999.0;
            double var30 = var2.a - var1.a;
            double var32 = var2.b - var1.b;
            double var34 = var2.c - var1.c;
            if (var43) {
               var24 = (var18 - var1.a) / var30;
            }

            if (var16) {
               var26 = (var20 - var1.b) / var32;
            }

            if (var17) {
               var28 = (var22 - var1.c) / var34;
            }

            if (var24 == -0.0) {
               var24 = -1.0E-4;
            }

            if (var26 == -0.0) {
               var26 = -1.0E-4;
            }

            if (var28 == -0.0) {
               var28 = -1.0E-4;
            }

            EnumFacing var36;
            if (var24 < var26 && var24 < var28) {
               var36 = var6 > var9 ? EnumFacing.e : EnumFacing.f;
               var1 = new Vec3(var18, var1.b + var32 * var24, var1.c + var34 * var24);
            } else if (var26 < var28) {
               var36 = var7 > var10 ? EnumFacing.a : EnumFacing.b;
               var1 = new Vec3(var1.a + var30 * var26, var20, var1.c + var34 * var26);
            } else {
               var36 = var8 > var11 ? EnumFacing.c : EnumFacing.d;
               var1 = new Vec3(var1.a + var30 * var28, var1.b + var32 * var28, var22);
            }

            var9 = MathHelper.c(var1.a) - (var36 == EnumFacing.f ? 1 : 0);
            var10 = MathHelper.c(var1.b) - (var36 == EnumFacing.b ? 1 : 0);
            var11 = MathHelper.c(var1.c) - (var36 == EnumFacing.d ? 1 : 0);
            var12 = new BlockPos(var9, var10, var11);
            IBlockState var37 = this.p(var12);
            Block var38 = var37.c();
            if (!var4 || var38.a(this, var12, var37) != null) {
               if (var38.a(var37, var3)) {
                  MovingObjectPosition var39 = var38.a(this, var12, var1, var2);
                  if (var39 != null) {
                     return var39;
                  }
               } else {
                  var41 = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.a, var1, var36, var12);
               }
            }
         }

         return var5 ? var41 : null;
      } else {
         return null;
      }
   }

   public void a(Entity var1, String var2, float var3, float var4) {
      for (int var5 = 0; var5 < this.u.size(); var5++) {
         this.u.get(var5).a(var2, var1.s, var1.t, var1.u, var3, var4);
      }
   }

   public void a(EntityPlayer var1, String var2, float var3, float var4) {
      for (int var5 = 0; var5 < this.u.size(); var5++) {
         this.u.get(var5).a(var1, var2, var1.s, var1.t, var1.u, var3, var4);
      }
   }

   public void a(double var1, double var3, double var5, String var7, float var8, float var9) {
      for (int var10 = 0; var10 < this.u.size(); var10++) {
         this.u.get(var10).a(var7, var1, var3, var5, var8, var9);
      }
   }

   public void a(double var1, double var3, double var5, String var7, float var8, float var9, boolean var10) {
   }

   public void a(BlockPos var1, String var2) {
      for (int var3 = 0; var3 < this.u.size(); var3++) {
         this.u.get(var3).a(var2, var1);
      }
   }

   public void a(EnumParticleTypes var1, double var2, double var4, double var6, double var8, double var10, double var12, int... var14) {
      this.a(var1.c(), var1.e(), var2, var4, var6, var8, var10, var12, var14);
   }

   public void a(EnumParticleTypes var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
      this.a(var1.c(), var1.e() | var2, var3, var5, var7, var9, var11, var13, var15);
   }

   private void a(int var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
      for (int var16 = 0; var16 < this.u.size(); var16++) {
         this.u.get(var16).a(var1, var2, var3, var5, var7, var9, var11, var13, var15);
      }
   }

   public boolean c(Entity var1) {
      this.k.add(var1);
      return true;
   }

   public boolean d(Entity var1) {
      int var2 = MathHelper.c(var1.s / 16.0);
      int var3 = MathHelper.c(var1.u / 16.0);
      boolean var4 = var1.n;
      if (var1 instanceof EntityPlayer) {
         var4 = true;
      }

      if (!var4 && !this.a(var2, var3, true)) {
         return false;
      } else {
         if (var1 instanceof EntityPlayer) {
            EntityPlayer var5 = (EntityPlayer)var1;
            this.j.add(var5);
            this.d();
         }

         this.a(var2, var3).a(var1);
         this.f.add(var1);
         this.a(var1);
         return true;
      }
   }

   protected void a(Entity var1) {
      for (int var2 = 0; var2 < this.u.size(); var2++) {
         this.u.get(var2).a(var1);
      }
   }

   protected void b(Entity var1) {
      for (int var2 = 0; var2 < this.u.size(); var2++) {
         this.u.get(var2).b(var1);
      }
   }

   public void e(Entity var1) {
      if (var1.l != null) {
         var1.l.a(null);
      }

      if (var1.m != null) {
         var1.a(null);
      }

      var1.J();
      if (var1 instanceof EntityPlayer) {
         this.j.remove(var1);
         this.d();
         this.b(var1);
      }
   }

   public void f(Entity var1) {
      var1.J();
      if (var1 instanceof EntityPlayer) {
         this.j.remove(var1);
         this.d();
      }

      int var2 = var1.ae;
      int var3 = var1.ag;
      if (var1.ad && this.a(var2, var3, true)) {
         this.a(var2, var3).b(var1);
      }

      this.f.remove(var1);
      this.b(var1);
   }

   public void a(IWorldAccess var1) {
      this.u.add(var1);
   }

   public void b(IWorldAccess var1) {
      this.u.remove(var1);
   }

   public List<AxisAlignedBB> a(Entity var1, AxisAlignedBB var2) {
      ArrayList var3 = Lists.newArrayList();
      int var4 = MathHelper.c(var2.a);
      int var5 = MathHelper.c(var2.d + 1.0);
      int var6 = MathHelper.c(var2.b);
      int var7 = MathHelper.c(var2.e + 1.0);
      int var8 = MathHelper.c(var2.c);
      int var9 = MathHelper.c(var2.f + 1.0);
      WorldBorder var10 = this.af();
      boolean var11 = var1.aT();
      boolean var12 = this.a(var10, var1);
      IBlockState var13 = Blocks.b.Q();
      BlockPos.MutableBlockPos var14 = new BlockPos.MutableBlockPos();

      for (int var15 = var4; var15 < var5; var15++) {
         for (int var16 = var8; var16 < var9; var16++) {
            if (this.e(var14.c(var15, 64, var16))) {
               for (int var17 = var6 - 1; var17 < var7; var17++) {
                  var14.c(var15, var17, var16);
                  if (var11 && var12) {
                     var1.h(false);
                  } else if (!var11 && !var12) {
                     var1.h(true);
                  }

                  IBlockState var18 = var13;
                  if (var10.a(var14) || !var12) {
                     var18 = this.p(var14);
                  }

                  var18.c().a(this, var14, var18, var2, var3, var1);
               }
            }
         }
      }

      double var20 = 0.25;
      List var21 = this.b(var1, var2.b(var20, var20, var20));

      for (int var22 = 0; var22 < var21.size(); var22++) {
         if (var1.l != var21 && var1.m != var21) {
            AxisAlignedBB var19 = ((Entity)var21.get(var22)).S();
            if (var19 != null && var19.b(var2)) {
               var3.add(var19);
            }

            var19 = var1.j((Entity)var21.get(var22));
            if (var19 != null && var19.b(var2)) {
               var3.add(var19);
            }
         }
      }

      return var3;
   }

   public boolean a(WorldBorder var1, Entity var2) {
      double var3 = var1.b();
      double var5 = var1.c();
      double var7 = var1.d();
      double var9 = var1.e();
      if (var2.aT()) {
         var3++;
         var5++;
         var7--;
         var9--;
      } else {
         var3--;
         var5--;
         var7++;
         var9++;
      }

      return var2.s > var3 && var2.s < var7 && var2.u > var5 && var2.u < var9;
   }

   public List<AxisAlignedBB> a(AxisAlignedBB var1) {
      ArrayList var2 = Lists.newArrayList();
      int var3 = MathHelper.c(var1.a);
      int var4 = MathHelper.c(var1.d + 1.0);
      int var5 = MathHelper.c(var1.b);
      int var6 = MathHelper.c(var1.e + 1.0);
      int var7 = MathHelper.c(var1.c);
      int var8 = MathHelper.c(var1.f + 1.0);
      BlockPos.MutableBlockPos var9 = new BlockPos.MutableBlockPos();

      for (int var10 = var3; var10 < var4; var10++) {
         for (int var11 = var7; var11 < var8; var11++) {
            if (this.e(var9.c(var10, 64, var11))) {
               for (int var12 = var5 - 1; var12 < var6; var12++) {
                  var9.c(var10, var12, var11);
                  IBlockState var13;
                  if (var10 >= -30000000 && var10 < 30000000 && var11 >= -30000000 && var11 < 30000000) {
                     var13 = this.p(var9);
                  } else {
                     var13 = Blocks.h.Q();
                  }

                  var13.c().a(this, var9, var13, var1, var2, null);
               }
            }
         }
      }

      return var2;
   }

   public int a(float var1) {
      float var2 = this.c(var1);
      float var3 = 1.0F - (MathHelper.b(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.5F);
      var3 = MathHelper.a(var3, 0.0F, 1.0F);
      var3 = 1.0F - var3;
      var3 = (float)(var3 * (1.0 - this.j(var1) * 5.0F / 16.0));
      var3 = (float)(var3 * (1.0 - this.h(var1) * 5.0F / 16.0));
      var3 = 1.0F - var3;
      return (int)(var3 * 11.0F);
   }

   public float b(float var1) {
      float var2 = this.c(var1);
      float var3 = 1.0F - (MathHelper.b(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.2F);
      var3 = MathHelper.a(var3, 0.0F, 1.0F);
      var3 = 1.0F - var3;
      var3 = (float)(var3 * (1.0 - this.j(var1) * 5.0F / 16.0));
      var3 = (float)(var3 * (1.0 - this.h(var1) * 5.0F / 16.0));
      return var3 * 0.8F + 0.2F;
   }

   public Vec3 a(Entity var1, float var2) {
      float var3 = this.c(var2);
      float var4 = MathHelper.b(var3 * (float) Math.PI * 2.0F) * 2.0F + 0.5F;
      var4 = MathHelper.a(var4, 0.0F, 1.0F);
      int var5 = MathHelper.c(var1.s);
      int var6 = MathHelper.c(var1.t);
      int var7 = MathHelper.c(var1.u);
      BlockPos var8 = new BlockPos(var5, var6, var7);
      BiomeGenBase var9 = this.b(var8);
      float var10 = var9.a(var8);
      int var11 = var9.a(var10);
      float var12 = (var11 >> 16 & 0xFF) / 255.0F;
      float var13 = (var11 >> 8 & 0xFF) / 255.0F;
      float var14 = (var11 & 0xFF) / 255.0F;
      var12 *= var4;
      var13 *= var4;
      var14 *= var4;
      float var15 = this.j(var2);
      if (var15 > 0.0F) {
         float var16 = (var12 * 0.3F + var13 * 0.59F + var14 * 0.11F) * 0.6F;
         float var17 = 1.0F - var15 * 0.75F;
         var12 = var12 * var17 + var16 * (1.0F - var17);
         var13 = var13 * var17 + var16 * (1.0F - var17);
         var14 = var14 * var17 + var16 * (1.0F - var17);
      }

      float var23 = this.h(var2);
      if (var23 > 0.0F) {
         float var24 = (var12 * 0.3F + var13 * 0.59F + var14 * 0.11F) * 0.2F;
         float var18 = 1.0F - var23 * 0.75F;
         var12 = var12 * var18 + var24 * (1.0F - var18);
         var13 = var13 * var18 + var24 * (1.0F - var18);
         var14 = var14 * var18 + var24 * (1.0F - var18);
      }

      if (this.J > 0) {
         float var25 = this.J - var2;
         if (var25 > 1.0F) {
            var25 = 1.0F;
         }

         var25 *= 0.45F;
         var12 = var12 * (1.0F - var25) + 0.8F * var25;
         var13 = var13 * (1.0F - var25) + 0.8F * var25;
         var14 = var14 * (1.0F - var25) + 1.0F * var25;
      }

      return new Vec3(var12, var13, var14);
   }

   public float c(float var1) {
      return this.t.a(this.x.g(), var1);
   }

   public int x() {
      return this.t.a(this.x.g());
   }

   public float y() {
      return WorldProvider.a[this.t.a(this.x.g())];
   }

   public float d(float var1) {
      float var2 = this.c(var1);
      return var2 * (float) Math.PI * 2.0F;
   }

   public Vec3 e(float var1) {
      float var2 = this.c(var1);
      float var3 = MathHelper.b(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.5F;
      var3 = MathHelper.a(var3, 0.0F, 1.0F);
      float var4 = (float)(this.d >> 16 & 255L) / 255.0F;
      float var5 = (float)(this.d >> 8 & 255L) / 255.0F;
      float var6 = (float)(this.d & 255L) / 255.0F;
      float var7 = this.j(var1);
      if (var7 > 0.0F) {
         float var8 = (var4 * 0.3F + var5 * 0.59F + var6 * 0.11F) * 0.6F;
         float var9 = 1.0F - var7 * 0.95F;
         var4 = var4 * var9 + var8 * (1.0F - var9);
         var5 = var5 * var9 + var8 * (1.0F - var9);
         var6 = var6 * var9 + var8 * (1.0F - var9);
      }

      var4 *= var3 * 0.9F + 0.1F;
      var5 *= var3 * 0.9F + 0.1F;
      var6 *= var3 * 0.85F + 0.15F;
      float var15 = this.h(var1);
      if (var15 > 0.0F) {
         float var16 = (var4 * 0.3F + var5 * 0.59F + var6 * 0.11F) * 0.2F;
         float var10 = 1.0F - var15 * 0.95F;
         var4 = var4 * var10 + var16 * (1.0F - var10);
         var5 = var5 * var10 + var16 * (1.0F - var10);
         var6 = var6 * var10 + var16 * (1.0F - var10);
      }

      return new Vec3(var4, var5, var6);
   }

   public Vec3 f(float var1) {
      float var2 = this.c(var1);
      return this.t.b(var2, var1);
   }

   public BlockPos q(BlockPos var1) {
      return this.f(var1).h(var1);
   }

   public BlockPos r(BlockPos var1) {
      Chunk var2 = this.f(var1);
      BlockPos var3 = new BlockPos(var1.n(), var2.g() + 16, var1.p());

      while (var3.o() >= 0) {
         BlockPos var4 = var3.b();
         Material var5 = var2.a(var4).t();
         if (var5.c() && var5 != Material.j) {
            break;
         }

         var3 = var4;
      }

      return var3;
   }

   public float g(float var1) {
      float var2 = this.c(var1);
      float var3 = 1.0F - (MathHelper.b(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);
      var3 = MathHelper.a(var3, 0.0F, 1.0F);
      return var3 * var3 * 0.5F;
   }

   public void a(BlockPos var1, Block var2, int var3) {
   }

   public void a(BlockPos var1, Block var2, int var3, int var4) {
   }

   public void b(BlockPos var1, Block var2, int var3, int var4) {
   }

   public void i() {
      this.B.a("entities");
      this.B.a("global");

      for (int var1 = 0; var1 < this.k.size(); var1++) {
         Entity var2 = this.k.get(var1);

         try {
            var2.W++;
            var2.t_();
         } catch (Throwable var9) {
            CrashReport var4 = CrashReport.a(var9, "Ticking entity");
            CrashReportCategory var5 = var4.a("Entity being ticked");
            if (var2 == null) {
               var5.a("Entity", "~~NULL~~");
            } else {
               var2.a(var5);
            }

            throw new ReportedException(var4);
         }

         if (var2.I) {
            this.k.remove(var1--);
         }
      }

      this.B.c("remove");
      this.f.removeAll(this.g);

      for (int var10 = 0; var10 < this.g.size(); var10++) {
         Entity var14 = this.g.get(var10);
         int var3 = var14.ae;
         int var21 = var14.ag;
         if (var14.ad && this.a(var3, var21, true)) {
            this.a(var3, var21).b(var14);
         }
      }

      for (int var11 = 0; var11 < this.g.size(); var11++) {
         this.b(this.g.get(var11));
      }

      this.g.clear();
      this.B.c("regular");

      for (int var12 = 0; var12 < this.f.size(); var12++) {
         Entity var15 = this.f.get(var12);
         if (var15.m != null) {
            if (!var15.m.I && var15.m.l == var15) {
               continue;
            }

            var15.m.l = null;
            var15.m = null;
         }

         this.B.a("tick");
         if (!var15.I) {
            try {
               this.g(var15);
            } catch (Throwable var8) {
               CrashReport var22 = CrashReport.a(var8, "Ticking entity");
               CrashReportCategory var24 = var22.a("Entity being ticked");
               var15.a(var24);
               throw new ReportedException(var22);
            }
         }

         this.B.b();
         this.B.a("remove");
         if (var15.I) {
            int var18 = var15.ae;
            int var23 = var15.ag;
            if (var15.ad && this.a(var18, var23, true)) {
               this.a(var18, var23).b(var15);
            }

            this.f.remove(var12--);
            this.b(var15);
         }

         this.B.b();
      }

      this.B.c("blockEntities");
      this.M = true;
      Iterator var13 = this.i.iterator();

      while (var13.hasNext()) {
         TileEntity var16 = (TileEntity)var13.next();
         if (!var16.x() && var16.t()) {
            BlockPos var19 = var16.v();
            if (this.e(var19) && this.N.a(var19)) {
               try {
                  ((ITickable)var16).c();
               } catch (Throwable var7) {
                  CrashReport var25 = CrashReport.a(var7, "Ticking block entity");
                  CrashReportCategory var6 = var25.a("Block entity being ticked");
                  var16.a(var6);
                  throw new ReportedException(var25);
               }
            }
         }

         if (var16.x()) {
            var13.remove();
            this.h.remove(var16);
            if (this.e(var16.v())) {
               this.f(var16.v()).e(var16.v());
            }
         }
      }

      this.M = false;
      if (!this.c.isEmpty()) {
         this.i.removeAll(this.c);
         this.h.removeAll(this.c);
         this.c.clear();
      }

      this.B.c("pendingBlockEntities");
      if (!this.b.isEmpty()) {
         for (int var17 = 0; var17 < this.b.size(); var17++) {
            TileEntity var20 = this.b.get(var17);
            if (!var20.x()) {
               if (!this.h.contains(var20)) {
                  this.a(var20);
               }

               if (this.e(var20.v())) {
                  this.f(var20.v()).a(var20.v(), var20);
               }

               this.h(var20.v());
            }
         }

         this.b.clear();
      }

      this.B.b();
      this.B.b();
   }

   public boolean a(TileEntity var1) {
      boolean var2 = this.h.add(var1);
      if (var2 && var1 instanceof ITickable) {
         this.i.add(var1);
      }

      return var2;
   }

   public void a(Collection<TileEntity> var1) {
      if (this.M) {
         this.b.addAll(var1);
      } else {
         for (TileEntity var3 : var1) {
            this.h.add(var3);
            if (var3 instanceof ITickable) {
               this.i.add(var3);
            }
         }
      }
   }

   public void g(Entity var1) {
      this.a(var1, true);
   }

   public void a(Entity var1, boolean var2) {
      int var3 = MathHelper.c(var1.s);
      int var4 = MathHelper.c(var1.u);
      byte var5 = 32;
      if (!var2 || this.a(var3 - var5, 0, var4 - var5, var3 + var5, 0, var4 + var5, true)) {
         var1.P = var1.s;
         var1.Q = var1.t;
         var1.R = var1.u;
         var1.A = var1.y;
         var1.B = var1.z;
         if (var2 && var1.ad) {
            var1.W++;
            if (var1.m != null) {
               var1.ak();
            } else {
               var1.t_();
            }
         }

         this.B.a("chunkCheck");
         if (Double.isNaN(var1.s) || Double.isInfinite(var1.s)) {
            var1.s = var1.P;
         }

         if (Double.isNaN(var1.t) || Double.isInfinite(var1.t)) {
            var1.t = var1.Q;
         }

         if (Double.isNaN(var1.u) || Double.isInfinite(var1.u)) {
            var1.u = var1.R;
         }

         if (Double.isNaN(var1.z) || Double.isInfinite(var1.z)) {
            var1.z = var1.B;
         }

         if (Double.isNaN(var1.y) || Double.isInfinite(var1.y)) {
            var1.y = var1.A;
         }

         int var6 = MathHelper.c(var1.s / 16.0);
         int var7 = MathHelper.c(var1.t / 16.0);
         int var8 = MathHelper.c(var1.u / 16.0);
         if (!var1.ad || var1.ae != var6 || var1.af != var7 || var1.ag != var8) {
            if (var1.ad && this.a(var1.ae, var1.ag, true)) {
               this.a(var1.ae, var1.ag).a(var1, var1.af);
            }

            if (this.a(var6, var8, true)) {
               var1.ad = true;
               this.a(var6, var8).a(var1);
            } else {
               var1.ad = false;
            }
         }

         this.B.b();
         if (var2 && var1.ad && var1.l != null) {
            if (!var1.l.I && var1.l.m == var1) {
               this.g(var1.l);
            } else {
               var1.l.m = null;
               var1.l = null;
            }
         }
      }
   }

   public boolean b(AxisAlignedBB var1) {
      return this.a(var1, null);
   }

   public boolean a(AxisAlignedBB var1, Entity var2) {
      List var3 = this.b(null, var1);

      for (int var4 = 0; var4 < var3.size(); var4++) {
         Entity var5 = (Entity)var3.get(var4);
         if (!var5.I && var5.k && var5 != var2 && (var2 == null || var2.m != var5 && var2.l != var5)) {
            return false;
         }
      }

      return true;
   }

   public boolean c(AxisAlignedBB var1) {
      int var2 = MathHelper.c(var1.a);
      int var3 = MathHelper.c(var1.d);
      int var4 = MathHelper.c(var1.b);
      int var5 = MathHelper.c(var1.e);
      int var6 = MathHelper.c(var1.c);
      int var7 = MathHelper.c(var1.f);
      BlockPos.MutableBlockPos var8 = new BlockPos.MutableBlockPos();

      for (int var9 = var2; var9 <= var3; var9++) {
         for (int var10 = var4; var10 <= var5; var10++) {
            for (int var11 = var6; var11 <= var7; var11++) {
               Block var12 = this.p(var8.c(var9, var10, var11)).c();
               if (var12.t() != Material.a) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean d(AxisAlignedBB var1) {
      int var2 = MathHelper.c(var1.a);
      int var3 = MathHelper.c(var1.d);
      int var4 = MathHelper.c(var1.b);
      int var5 = MathHelper.c(var1.e);
      int var6 = MathHelper.c(var1.c);
      int var7 = MathHelper.c(var1.f);
      BlockPos.MutableBlockPos var8 = new BlockPos.MutableBlockPos();

      for (int var9 = var2; var9 <= var3; var9++) {
         for (int var10 = var4; var10 <= var5; var10++) {
            for (int var11 = var6; var11 <= var7; var11++) {
               Block var12 = this.p(var8.c(var9, var10, var11)).c();
               if (var12.t().d()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean e(AxisAlignedBB var1) {
      int var2 = MathHelper.c(var1.a);
      int var3 = MathHelper.c(var1.d + 1.0);
      int var4 = MathHelper.c(var1.b);
      int var5 = MathHelper.c(var1.e + 1.0);
      int var6 = MathHelper.c(var1.c);
      int var7 = MathHelper.c(var1.f + 1.0);
      if (this.a(var2, var4, var6, var3, var5, var7, true)) {
         BlockPos.MutableBlockPos var8 = new BlockPos.MutableBlockPos();

         for (int var9 = var2; var9 < var3; var9++) {
            for (int var10 = var4; var10 < var5; var10++) {
               for (int var11 = var6; var11 < var7; var11++) {
                  Block var12 = this.p(var8.c(var9, var10, var11)).c();
                  if (var12 == Blocks.ab || var12 == Blocks.k || var12 == Blocks.l) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public boolean a(AxisAlignedBB var1, Material var2, Entity var3) {
      int var4 = MathHelper.c(var1.a);
      int var5 = MathHelper.c(var1.d + 1.0);
      int var6 = MathHelper.c(var1.b);
      int var7 = MathHelper.c(var1.e + 1.0);
      int var8 = MathHelper.c(var1.c);
      int var9 = MathHelper.c(var1.f + 1.0);
      if (!this.a(var4, var6, var8, var5, var7, var9, true)) {
         return false;
      } else {
         boolean var10 = false;
         Vec3 var11 = new Vec3(0.0, 0.0, 0.0);
         BlockPos.MutableBlockPos var12 = new BlockPos.MutableBlockPos();

         for (int var13 = var4; var13 < var5; var13++) {
            for (int var14 = var6; var14 < var7; var14++) {
               for (int var15 = var8; var15 < var9; var15++) {
                  var12.c(var13, var14, var15);
                  IBlockState var16 = this.p(var12);
                  Block var17 = var16.c();
                  if (var17.t() == var2) {
                     double var18 = var14 + 1 - BlockLiquid.b(var16.b(BlockLiquid.b));
                     if (var7 >= var18) {
                        var10 = true;
                        var11 = var17.a(this, var12, var3, var11);
                     }
                  }
               }
            }
         }

         if (var11.b() > 0.0 && var3.aL()) {
            var11 = var11.a();
            double var21 = 0.014;
            var3.v = var3.v + var11.a * var21;
            var3.w = var3.w + var11.b * var21;
            var3.x = var3.x + var11.c * var21;
         }

         return var10;
      }
   }

   public boolean a(AxisAlignedBB var1, Material var2) {
      int var3 = MathHelper.c(var1.a);
      int var4 = MathHelper.c(var1.d + 1.0);
      int var5 = MathHelper.c(var1.b);
      int var6 = MathHelper.c(var1.e + 1.0);
      int var7 = MathHelper.c(var1.c);
      int var8 = MathHelper.c(var1.f + 1.0);
      BlockPos.MutableBlockPos var9 = new BlockPos.MutableBlockPos();

      for (int var10 = var3; var10 < var4; var10++) {
         for (int var11 = var5; var11 < var6; var11++) {
            for (int var12 = var7; var12 < var8; var12++) {
               if (this.p(var9.c(var10, var11, var12)).c().t() == var2) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean b(AxisAlignedBB var1, Material var2) {
      int var3 = MathHelper.c(var1.a);
      int var4 = MathHelper.c(var1.d + 1.0);
      int var5 = MathHelper.c(var1.b);
      int var6 = MathHelper.c(var1.e + 1.0);
      int var7 = MathHelper.c(var1.c);
      int var8 = MathHelper.c(var1.f + 1.0);
      BlockPos.MutableBlockPos var9 = new BlockPos.MutableBlockPos();

      for (int var10 = var3; var10 < var4; var10++) {
         for (int var11 = var5; var11 < var6; var11++) {
            for (int var12 = var7; var12 < var8; var12++) {
               IBlockState var13 = this.p(var9.c(var10, var11, var12));
               Block var14 = var13.c();
               if (var14.t() == var2) {
                  int var15 = var13.b(BlockLiquid.b);
                  double var16 = var11 + 1;
                  if (var15 < 8) {
                     var16 = var11 + 1 - var15 / 8.0;
                  }

                  if (var16 >= var1.b) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public Explosion a(Entity var1, double var2, double var4, double var6, float var8, boolean var9) {
      return this.a(var1, var2, var4, var6, var8, false, var9);
   }

   public Explosion a(Entity var1, double var2, double var4, double var6, float var8, boolean var9, boolean var10) {
      Explosion var11 = new Explosion(this, var1, var2, var4, var6, var8, var9, var10);
      var11.a();
      var11.a(true);
      return var11;
   }

   public float a(Vec3 var1, AxisAlignedBB var2) {
      double var3 = 1.0 / ((var2.d - var2.a) * 2.0 + 1.0);
      double var5 = 1.0 / ((var2.e - var2.b) * 2.0 + 1.0);
      double var7 = 1.0 / ((var2.f - var2.c) * 2.0 + 1.0);
      double var9 = (1.0 - Math.floor(1.0 / var3) * var3) / 2.0;
      double var11 = (1.0 - Math.floor(1.0 / var7) * var7) / 2.0;
      if (!(var3 < 0.0) && !(var5 < 0.0) && !(var7 < 0.0)) {
         int var13 = 0;
         int var14 = 0;

         for (float var15 = 0.0F; var15 <= 1.0F; var15 = (float)(var15 + var3)) {
            for (float var16 = 0.0F; var16 <= 1.0F; var16 = (float)(var16 + var5)) {
               for (float var17 = 0.0F; var17 <= 1.0F; var17 = (float)(var17 + var7)) {
                  double var18 = var2.a + (var2.d - var2.a) * var15;
                  double var20 = var2.b + (var2.e - var2.b) * var16;
                  double var22 = var2.c + (var2.f - var2.c) * var17;
                  if (this.a(new Vec3(var18 + var9, var20, var22 + var11), var1) == null) {
                     var13++;
                  }

                  var14++;
               }
            }
         }

         return (float)var13 / var14;
      } else {
         return 0.0F;
      }
   }

   public boolean a(EntityPlayer var1, BlockPos var2, EnumFacing var3) {
      var2 = var2.a(var3);
      if (this.p(var2).c() == Blocks.ab) {
         this.a(var1, 1004, var2, 0);
         this.g(var2);
         return true;
      } else {
         return false;
      }
   }

   public String z() {
      return "All: " + this.f.size();
   }

   public String A() {
      return this.v.f();
   }

   @Override
   public TileEntity s(BlockPos var1) {
      if (!this.a(var1)) {
         return null;
      } else {
         TileEntity var2 = null;
         if (this.M) {
            for (int var3 = 0; var3 < this.b.size(); var3++) {
               TileEntity var4 = this.b.get(var3);
               if (!var4.x() && var4.v().equals(var1)) {
                  var2 = var4;
                  break;
               }
            }
         }

         if (var2 == null) {
            var2 = this.f(var1).a(var1, Chunk.EnumCreateEntityType.a);
         }

         if (var2 == null) {
            for (int var5 = 0; var5 < this.b.size(); var5++) {
               TileEntity var6 = this.b.get(var5);
               if (!var6.x() && var6.v().equals(var1)) {
                  var2 = var6;
                  break;
               }
            }
         }

         return var2;
      }
   }

   public void a(BlockPos var1, TileEntity var2) {
      if (var2 != null && !var2.x()) {
         if (this.M) {
            var2.a(var1);
            Iterator var3 = this.b.iterator();

            while (var3.hasNext()) {
               TileEntity var4 = (TileEntity)var3.next();
               if (var4.v().equals(var1)) {
                  var4.y();
                  var3.remove();
               }
            }

            this.b.add(var2);
         } else {
            this.a(var2);
            this.f(var1).a(var1, var2);
         }
      }
   }

   public void t(BlockPos var1) {
      TileEntity var2 = this.s(var1);
      if (var2 != null && this.M) {
         var2.y();
         this.b.remove(var2);
      } else {
         if (var2 != null) {
            this.b.remove(var2);
            this.h.remove(var2);
            this.i.remove(var2);
         }

         this.f(var1).e(var1);
      }
   }

   public void b(TileEntity var1) {
      this.c.add(var1);
   }

   public boolean u(BlockPos var1) {
      IBlockState var2 = this.p(var1);
      AxisAlignedBB var3 = var2.c().a(this, var1, var2);
      return var3 != null && var3.a() >= 1.0;
   }

   public static boolean a(IBlockAccess var0, BlockPos var1) {
      IBlockState var2 = var0.p(var1);
      Block var3 = var2.c();
      if (var3.t().k() && var3.d()) {
         return true;
      } else if (var3 instanceof BlockStairs) {
         return var2.b(BlockStairs.b) == BlockStairs.EnumHalf.a;
      } else if (var3 instanceof BlockSlab) {
         return var2.b(BlockSlab.a) == BlockSlab.EnumBlockHalf.a;
      } else if (var3 instanceof BlockHopper) {
         return true;
      } else {
         return var3 instanceof BlockSnow ? var2.b(BlockSnow.a) == 7 : false;
      }
   }

   public boolean d(BlockPos var1, boolean var2) {
      if (!this.a(var1)) {
         return var2;
      } else {
         Chunk var3 = this.v.a(var1);
         if (var3.f()) {
            return var2;
         } else {
            Block var4 = this.p(var1).c();
            return var4.t().k() && var4.d();
         }
      }
   }

   public void B() {
      int var1 = this.a(1.0F);
      if (var1 != this.I) {
         this.I = var1;
      }
   }

   public void a(boolean var1, boolean var2) {
      this.F = var1;
      this.G = var2;
   }

   public void c() {
      this.p();
   }

   protected void C() {
      if (this.x.p()) {
         this.p = 1.0F;
         if (this.x.n()) {
            this.r = 1.0F;
         }
      }
   }

   protected void p() {
      if (!this.t.o()) {
         if (!this.D) {
            int var1 = this.x.A();
            if (var1 > 0) {
               this.x.i(--var1);
               this.x.f(this.x.n() ? 1 : 2);
               this.x.g(this.x.p() ? 1 : 2);
            }

            int var2 = this.x.o();
            if (var2 <= 0) {
               if (this.x.n()) {
                  this.x.f(this.s.nextInt(12000) + 3600);
               } else {
                  this.x.f(this.s.nextInt(168000) + 12000);
               }
            } else {
               this.x.f(--var2);
               if (var2 <= 0) {
                  this.x.a(!this.x.n());
               }
            }

            this.q = this.r;
            if (this.x.n()) {
               this.r = (float)(this.r + 0.01);
            } else {
               this.r = (float)(this.r - 0.01);
            }

            this.r = MathHelper.a(this.r, 0.0F, 1.0F);
            int var3 = this.x.q();
            if (var3 <= 0) {
               if (this.x.p()) {
                  this.x.g(this.s.nextInt(12000) + 12000);
               } else {
                  this.x.g(this.s.nextInt(168000) + 12000);
               }
            } else {
               this.x.g(--var3);
               if (var3 <= 0) {
                  this.x.b(!this.x.p());
               }
            }

            this.o = this.p;
            if (this.x.p()) {
               this.p = (float)(this.p + 0.01);
            } else {
               this.p = (float)(this.p - 0.01);
            }

            this.p = MathHelper.a(this.p, 0.0F, 1.0F);
         }
      }
   }

   protected void D() {
      this.E.clear();
      this.B.a("buildList");

      for (int var1 = 0; var1 < this.j.size(); var1++) {
         EntityPlayer var2 = this.j.get(var1);
         int var3 = MathHelper.c(var2.s / 16.0);
         int var4 = MathHelper.c(var2.u / 16.0);
         int var5 = this.q();

         for (int var6 = -var5; var6 <= var5; var6++) {
            for (int var7 = -var5; var7 <= var5; var7++) {
               this.E.add(new ChunkCoordIntPair(var6 + var3, var7 + var4));
            }
         }
      }

      this.B.b();
      if (this.L > 0) {
         this.L--;
      }

      this.B.a("playerCheckLight");
      if (!this.j.isEmpty()) {
         int var8 = this.s.nextInt(this.j.size());
         EntityPlayer var9 = this.j.get(var8);
         int var10 = MathHelper.c(var9.s) + this.s.nextInt(11) - 5;
         int var11 = MathHelper.c(var9.t) + this.s.nextInt(11) - 5;
         int var12 = MathHelper.c(var9.u) + this.s.nextInt(11) - 5;
         this.x(new BlockPos(var10, var11, var12));
      }

      this.B.b();
   }

   protected abstract int q();

   protected void a(int var1, int var2, Chunk var3) {
      this.B.c("moodSound");
      if (this.L == 0 && !this.D) {
         this.m = this.m * 3 + 1013904223;
         int var4 = this.m >> 2;
         int var5 = var4 & 15;
         int var6 = var4 >> 8 & 15;
         int var7 = var4 >> 16 & 0xFF;
         BlockPos var8 = new BlockPos(var5, var7, var6);
         Block var9 = var3.a(var8);
         var5 += var1;
         var6 += var2;
         if (var9.t() == Material.a && this.k(var8) <= this.s.nextInt(8) && this.b(EnumSkyBlock.a, var8) <= 0) {
            EntityPlayer var10 = this.a(var5 + 0.5, var7 + 0.5, var6 + 0.5, 8.0);
            if (var10 != null && var10.e(var5 + 0.5, var7 + 0.5, var6 + 0.5) > 4.0) {
               this.a(var5 + 0.5, var7 + 0.5, var6 + 0.5, "ambient.cave.cave", 0.7F, 0.8F + this.s.nextFloat() * 0.2F);
               this.L = this.s.nextInt(12000) + 6000;
            }
         }
      }

      this.B.c("checkLight");
      var3.m();
   }

   protected void h() {
      this.D();
   }

   public void a(Block var1, BlockPos var2, Random var3) {
      this.e = true;
      var1.b(this, var2, this.p(var2), var3);
      this.e = false;
   }

   public boolean v(BlockPos var1) {
      return this.e(var1, false);
   }

   public boolean w(BlockPos var1) {
      return this.e(var1, true);
   }

   public boolean e(BlockPos var1, boolean var2) {
      BiomeGenBase var3 = this.b(var1);
      float var4 = var3.a(var1);
      if (var4 > 0.15F) {
         return false;
      } else {
         if (var1.o() >= 0 && var1.o() < 256 && this.b(EnumSkyBlock.b, var1) < 10) {
            IBlockState var5 = this.p(var1);
            Block var6 = var5.c();
            if ((var6 == Blocks.j || var6 == Blocks.i) && var5.b(BlockLiquid.b) == 0) {
               if (!var2) {
                  return true;
               }

               boolean var7 = this.F(var1.e()) && this.F(var1.f()) && this.F(var1.c()) && this.F(var1.d());
               if (!var7) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean F(BlockPos var1) {
      return this.p(var1).c().t() == Material.h;
   }

   public boolean f(BlockPos var1, boolean var2) {
      BiomeGenBase var3 = this.b(var1);
      float var4 = var3.a(var1);
      if (var4 > 0.15F) {
         return false;
      } else if (!var2) {
         return true;
      } else {
         if (var1.o() >= 0 && var1.o() < 256 && this.b(EnumSkyBlock.b, var1) < 10) {
            Block var5 = this.p(var1).c();
            if (var5.t() == Material.a && Blocks.aH.d(this, var1)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean x(BlockPos var1) {
      boolean var2 = false;
      if (!this.t.o()) {
         var2 |= this.c(EnumSkyBlock.a, var1);
      }

      return var2 | this.c(EnumSkyBlock.b, var1);
   }

   private int a(BlockPos var1, EnumSkyBlock var2) {
      if (var2 == EnumSkyBlock.a && this.i(var1)) {
         return 15;
      } else {
         Block var3 = this.p(var1).c();
         int var4 = var2 == EnumSkyBlock.a ? 0 : var3.r();
         int var5 = var3.p();
         if (var5 >= 15 && var3.r() > 0) {
            var5 = 1;
         }

         if (var5 < 1) {
            var5 = 1;
         }

         if (var5 >= 15) {
            return 0;
         } else if (var4 >= 14) {
            return var4;
         } else {
            for (EnumFacing var9 : EnumFacing.values()) {
               BlockPos var10 = var1.a(var9);
               int var11 = this.b(var2, var10) - var5;
               if (var11 > var4) {
                  var4 = var11;
               }

               if (var4 >= 14) {
                  return var4;
               }
            }

            return var4;
         }
      }
   }

   public boolean c(EnumSkyBlock var1, BlockPos var2) {
      if (!this.a(var2, 17, false)) {
         return false;
      } else {
         int var3 = 0;
         int var4 = 0;
         this.B.a("getBrightness");
         int var5 = this.b(var1, var2);
         int var6 = this.a(var2, var1);
         int var7 = var2.n();
         int var8 = var2.o();
         int var9 = var2.p();
         if (var6 > var5) {
            this.H[var4++] = 133152;
         } else if (var6 < var5) {
            this.H[var4++] = 133152 | var5 << 18;

            while (var3 < var4) {
               int var10 = this.H[var3++];
               int var11 = (var10 & 63) - 32 + var7;
               int var12 = (var10 >> 6 & 63) - 32 + var8;
               int var13 = (var10 >> 12 & 63) - 32 + var9;
               int var14 = var10 >> 18 & 15;
               BlockPos var15 = new BlockPos(var11, var12, var13);
               int var16 = this.b(var1, var15);
               if (var16 == var14) {
                  this.a(var1, var15, 0);
                  if (var14 > 0) {
                     int var17 = MathHelper.a(var11 - var7);
                     int var18 = MathHelper.a(var12 - var8);
                     int var19 = MathHelper.a(var13 - var9);
                     if (var17 + var18 + var19 < 17) {
                        BlockPos.MutableBlockPos var20 = new BlockPos.MutableBlockPos();

                        for (EnumFacing var24 : EnumFacing.values()) {
                           int var25 = var11 + var24.g();
                           int var26 = var12 + var24.h();
                           int var27 = var13 + var24.i();
                           var20.c(var25, var26, var27);
                           int var28 = Math.max(1, this.p(var20).c().p());
                           var16 = this.b(var1, var20);
                           if (var16 == var14 - var28 && var4 < this.H.length) {
                              this.H[var4++] = var25 - var7 + 32 | var26 - var8 + 32 << 6 | var27 - var9 + 32 << 12 | var14 - var28 << 18;
                           }
                        }
                     }
                  }
               }
            }

            var3 = 0;
         }

         this.B.b();
         this.B.a("checkedPosition < toCheckCount");

         while (var3 < var4) {
            int var29 = this.H[var3++];
            int var30 = (var29 & 63) - 32 + var7;
            int var31 = (var29 >> 6 & 63) - 32 + var8;
            int var32 = (var29 >> 12 & 63) - 32 + var9;
            BlockPos var33 = new BlockPos(var30, var31, var32);
            int var34 = this.b(var1, var33);
            int var36 = this.a(var33, var1);
            if (var36 != var34) {
               this.a(var1, var33, var36);
               if (var36 > var34) {
                  int var37 = Math.abs(var30 - var7);
                  int var38 = Math.abs(var31 - var8);
                  int var39 = Math.abs(var32 - var9);
                  boolean var40 = var4 < this.H.length - 6;
                  if (var37 + var38 + var39 < 17 && var40) {
                     if (this.b(var1, var33.e()) < var36) {
                        this.H[var4++] = var30 - 1 - var7 + 32 + (var31 - var8 + 32 << 6) + (var32 - var9 + 32 << 12);
                     }

                     if (this.b(var1, var33.f()) < var36) {
                        this.H[var4++] = var30 + 1 - var7 + 32 + (var31 - var8 + 32 << 6) + (var32 - var9 + 32 << 12);
                     }

                     if (this.b(var1, var33.b()) < var36) {
                        this.H[var4++] = var30 - var7 + 32 + (var31 - 1 - var8 + 32 << 6) + (var32 - var9 + 32 << 12);
                     }

                     if (this.b(var1, var33.a()) < var36) {
                        this.H[var4++] = var30 - var7 + 32 + (var31 + 1 - var8 + 32 << 6) + (var32 - var9 + 32 << 12);
                     }

                     if (this.b(var1, var33.c()) < var36) {
                        this.H[var4++] = var30 - var7 + 32 + (var31 - var8 + 32 << 6) + (var32 - 1 - var9 + 32 << 12);
                     }

                     if (this.b(var1, var33.d()) < var36) {
                        this.H[var4++] = var30 - var7 + 32 + (var31 - var8 + 32 << 6) + (var32 + 1 - var9 + 32 << 12);
                     }
                  }
               }
            }
         }

         this.B.b();
         return true;
      }
   }

   public boolean a(boolean var1) {
      return false;
   }

   public List<NextTickListEntry> a(Chunk var1, boolean var2) {
      return null;
   }

   public List<NextTickListEntry> a(StructureBoundingBox var1, boolean var2) {
      return null;
   }

   public List<Entity> b(Entity var1, AxisAlignedBB var2) {
      return this.a(var1, var2, EntitySelectors.d);
   }

   public List<Entity> a(Entity var1, AxisAlignedBB var2, Predicate<? super Entity> var3) {
      ArrayList var4 = Lists.newArrayList();
      int var5 = MathHelper.c((var2.a - 2.0) / 16.0);
      int var6 = MathHelper.c((var2.d + 2.0) / 16.0);
      int var7 = MathHelper.c((var2.c - 2.0) / 16.0);
      int var8 = MathHelper.c((var2.f + 2.0) / 16.0);

      for (int var9 = var5; var9 <= var6; var9++) {
         for (int var10 = var7; var10 <= var8; var10++) {
            if (this.a(var9, var10, true)) {
               this.a(var9, var10).a(var1, var2, var4, var3);
            }
         }
      }

      return var4;
   }

   public <T extends Entity> List<T> a(Class<? extends T> var1, Predicate<? super T> var2) {
      ArrayList var3 = Lists.newArrayList();

      for (Entity var5 : this.f) {
         if (var1.isAssignableFrom(var5.getClass()) && var2.apply(var5)) {
            var3.add(var5);
         }
      }

      return var3;
   }

   public <T extends Entity> List<T> b(Class<? extends T> var1, Predicate<? super T> var2) {
      ArrayList var3 = Lists.newArrayList();

      for (Entity var5 : this.j) {
         if (var1.isAssignableFrom(var5.getClass()) && var2.apply(var5)) {
            var3.add(var5);
         }
      }

      return var3;
   }

   public <T extends Entity> List<T> a(Class<? extends T> var1, AxisAlignedBB var2) {
      return this.a(var1, var2, EntitySelectors.d);
   }

   public <T extends Entity> List<T> a(Class<? extends T> var1, AxisAlignedBB var2, Predicate<? super T> var3) {
      int var4 = MathHelper.c((var2.a - 2.0) / 16.0);
      int var5 = MathHelper.c((var2.d + 2.0) / 16.0);
      int var6 = MathHelper.c((var2.c - 2.0) / 16.0);
      int var7 = MathHelper.c((var2.f + 2.0) / 16.0);
      ArrayList var8 = Lists.newArrayList();

      for (int var9 = var4; var9 <= var5; var9++) {
         for (int var10 = var6; var10 <= var7; var10++) {
            if (this.a(var9, var10, true)) {
               this.a(var9, var10).a(var1, var2, var8, var3);
            }
         }
      }

      return var8;
   }

   public <T extends Entity> T a(Class<? extends T> var1, AxisAlignedBB var2, T var3) {
      List var4 = this.a(var1, var2);
      Entity var5 = null;
      double var6 = Double.MAX_VALUE;

      for (int var8 = 0; var8 < var4.size(); var8++) {
         Entity var9 = (Entity)var4.get(var8);
         if (var9 != var3 && EntitySelectors.d.apply(var9)) {
            double var10 = var3.h(var9);
            if (!(var10 > var6)) {
               var5 = var9;
               var6 = var10;
            }
         }
      }

      return (T)var5;
   }

   public Entity a(int var1) {
      return this.l.a(var1);
   }

   public List<Entity> E() {
      return this.f;
   }

   public void b(BlockPos var1, TileEntity var2) {
      if (this.e(var1)) {
         this.f(var1).e();
      }
   }

   public int a(Class<?> var1) {
      int var2 = 0;

      for (Entity var4 : this.f) {
         if ((!(var4 instanceof EntityLiving) || !((EntityLiving)var4).bZ()) && var1.isAssignableFrom(var4.getClass())) {
            var2++;
         }
      }

      return var2;
   }

   public void b(Collection<Entity> var1) {
      this.f.addAll(var1);

      for (Entity var3 : var1) {
         this.a(var3);
      }
   }

   public void c(Collection<Entity> var1) {
      this.g.addAll(var1);
   }

   public boolean a(Block var1, BlockPos var2, boolean var3, EnumFacing var4, Entity var5, ItemStack var6) {
      Block var7 = this.p(var2).c();
      AxisAlignedBB var8 = var3 ? null : var1.a(this, var2, var1.Q());
      if (var8 != null && !this.a(var8, var5)) {
         return false;
      } else {
         return var7.t() == Material.q && var1 == Blocks.cf ? true : var7.t().j() && var1.a(this, var2, var4, var6);
      }
   }

   public int F() {
      return this.a;
   }

   public void b(int var1) {
      this.a = var1;
   }

   @Override
   public int a(BlockPos var1, EnumFacing var2) {
      IBlockState var3 = this.p(var1);
      return var3.c().b(this, var1, var3, var2);
   }

   @Override
   public WorldType G() {
      return this.x.u();
   }

   public int y(BlockPos var1) {
      int var2 = 0;
      var2 = Math.max(var2, this.a(var1.b(), EnumFacing.a));
      if (var2 >= 15) {
         return var2;
      } else {
         var2 = Math.max(var2, this.a(var1.a(), EnumFacing.b));
         if (var2 >= 15) {
            return var2;
         } else {
            var2 = Math.max(var2, this.a(var1.c(), EnumFacing.c));
            if (var2 >= 15) {
               return var2;
            } else {
               var2 = Math.max(var2, this.a(var1.d(), EnumFacing.d));
               if (var2 >= 15) {
                  return var2;
               } else {
                  var2 = Math.max(var2, this.a(var1.e(), EnumFacing.e));
                  if (var2 >= 15) {
                     return var2;
                  } else {
                     var2 = Math.max(var2, this.a(var1.f(), EnumFacing.f));
                     return var2 >= 15 ? var2 : var2;
                  }
               }
            }
         }
      }
   }

   public boolean b(BlockPos var1, EnumFacing var2) {
      return this.c(var1, var2) > 0;
   }

   public int c(BlockPos var1, EnumFacing var2) {
      IBlockState var3 = this.p(var1);
      Block var4 = var3.c();
      return var4.v() ? this.y(var1) : var4.a(this, var1, var3, var2);
   }

   public boolean z(BlockPos var1) {
      if (this.c(var1.b(), EnumFacing.a) > 0) {
         return true;
      } else if (this.c(var1.a(), EnumFacing.b) > 0) {
         return true;
      } else if (this.c(var1.c(), EnumFacing.c) > 0) {
         return true;
      } else if (this.c(var1.d(), EnumFacing.d) > 0) {
         return true;
      } else {
         return this.c(var1.e(), EnumFacing.e) > 0 ? true : this.c(var1.f(), EnumFacing.f) > 0;
      }
   }

   public int A(BlockPos var1) {
      int var2 = 0;

      for (EnumFacing var6 : EnumFacing.values()) {
         int var7 = this.c(var1.a(var6), var6);
         if (var7 >= 15) {
            return 15;
         }

         if (var7 > var2) {
            var2 = var7;
         }
      }

      return var2;
   }

   public EntityPlayer a(Entity var1, double var2) {
      return this.a(var1.s, var1.t, var1.u, var2);
   }

   public EntityPlayer a(double var1, double var3, double var5, double var7) {
      double var9 = -1.0;
      EntityPlayer var11 = null;

      for (int var12 = 0; var12 < this.j.size(); var12++) {
         EntityPlayer var13 = this.j.get(var12);
         if (EntitySelectors.d.apply(var13)) {
            double var14 = var13.e(var1, var3, var5);
            if ((var7 < 0.0 || var14 < var7 * var7) && (var9 == -1.0 || var14 < var9)) {
               var9 = var14;
               var11 = var13;
            }
         }
      }

      return var11;
   }

   public boolean b(double var1, double var3, double var5, double var7) {
      for (int var9 = 0; var9 < this.j.size(); var9++) {
         EntityPlayer var10 = this.j.get(var9);
         if (EntitySelectors.d.apply(var10)) {
            double var11 = var10.e(var1, var3, var5);
            if (var7 < 0.0 || var11 < var7 * var7) {
               return true;
            }
         }
      }

      return false;
   }

   public EntityPlayer a(String var1) {
      for (int var2 = 0; var2 < this.j.size(); var2++) {
         EntityPlayer var3 = this.j.get(var2);
         if (var1.equals(var3.e_())) {
            return var3;
         }
      }

      return null;
   }

   public EntityPlayer b(UUID var1) {
      for (int var2 = 0; var2 < this.j.size(); var2++) {
         EntityPlayer var3 = this.j.get(var2);
         if (var1.equals(var3.aK())) {
            return var3;
         }
      }

      return null;
   }

   public void H() {
   }

   public void I() throws MinecraftException {
      this.w.c();
   }

   public void a(long var1) {
      this.x.b(var1);
   }

   public long J() {
      return this.x.b();
   }

   public long K() {
      return this.x.f();
   }

   public long L() {
      return this.x.g();
   }

   public void b(long var1) {
      this.x.c(var1);
   }

   public BlockPos M() {
      BlockPos var1 = new BlockPos(this.x.c(), this.x.d(), this.x.e());
      if (!this.af().a(var1)) {
         var1 = this.m(new BlockPos(this.af().f(), 0.0, this.af().g()));
      }

      return var1;
   }

   public void B(BlockPos var1) {
      this.x.a(var1);
   }

   public void h(Entity var1) {
      int var2 = MathHelper.c(var1.s / 16.0);
      int var3 = MathHelper.c(var1.u / 16.0);
      byte var4 = 2;

      for (int var5 = var2 - var4; var5 <= var2 + var4; var5++) {
         for (int var6 = var3 - var4; var6 <= var3 + var4; var6++) {
            this.a(var5, var6);
         }
      }

      if (!this.f.contains(var1)) {
         this.f.add(var1);
      }
   }

   public boolean a(EntityPlayer var1, BlockPos var2) {
      return true;
   }

   public void a(Entity var1, byte var2) {
   }

   public IChunkProvider N() {
      return this.v;
   }

   public void c(BlockPos var1, Block var2, int var3, int var4) {
      var2.a(this, var1, this.p(var1), var3, var4);
   }

   public ISaveHandler O() {
      return this.w;
   }

   public WorldInfo P() {
      return this.x;
   }

   public GameRules Q() {
      return this.x.x();
   }

   public void d() {
   }

   public float h(float var1) {
      return (this.q + (this.r - this.q) * var1) * this.j(var1);
   }

   public void i(float var1) {
      this.q = var1;
      this.r = var1;
   }

   public float j(float var1) {
      return this.o + (this.p - this.o) * var1;
   }

   public void k(float var1) {
      this.o = var1;
      this.p = var1;
   }

   public boolean R() {
      return this.h(1.0F) > 0.9;
   }

   public boolean S() {
      return this.j(1.0F) > 0.2;
   }

   public boolean C(BlockPos var1) {
      if (!this.S()) {
         return false;
      } else if (!this.i(var1)) {
         return false;
      } else if (this.q(var1).o() > var1.o()) {
         return false;
      } else {
         BiomeGenBase var2 = this.b(var1);
         if (var2.d()) {
            return false;
         } else {
            return this.f(var1, false) ? false : var2.e();
         }
      }
   }

   public boolean D(BlockPos var1) {
      BiomeGenBase var2 = this.b(var1);
      return var2.f();
   }

   public MapStorage T() {
      return this.z;
   }

   public void a(String var1, WorldSavedData var2) {
      this.z.a(var1, var2);
   }

   public WorldSavedData a(Class<? extends WorldSavedData> var1, String var2) {
      return this.z.a(var1, var2);
   }

   public int b(String var1) {
      return this.z.a(var1);
   }

   public void a(int var1, BlockPos var2, int var3) {
      for (int var4 = 0; var4 < this.u.size(); var4++) {
         this.u.get(var4).a(var1, var2, var3);
      }
   }

   public void b(int var1, BlockPos var2, int var3) {
      this.a(null, var1, var2, var3);
   }

   public void a(EntityPlayer var1, int var2, BlockPos var3, int var4) {
      try {
         for (int var5 = 0; var5 < this.u.size(); var5++) {
            this.u.get(var5).a(var1, var2, var3, var4);
         }
      } catch (Throwable var8) {
         CrashReport var6 = CrashReport.a(var8, "Playing level event");
         CrashReportCategory var7 = var6.a("Level event being played");
         var7.a("Block coordinates", CrashReportCategory.a(var3));
         var7.a("Event source", var1);
         var7.a("Event type", var2);
         var7.a("Event data", var4);
         throw new ReportedException(var6);
      }
   }

   public int U() {
      return 256;
   }

   public int V() {
      return this.t.o() ? 128 : 256;
   }

   public Random a(int var1, int var2, int var3) {
      long var4 = var1 * 341873128712L + var2 * 132897987541L + this.P().b() + var3;
      this.s.setSeed(var4);
      return this.s;
   }

   public BlockPos a(String var1, BlockPos var2) {
      return this.N().a(this, var1, var2);
   }

   @Override
   public boolean W() {
      return false;
   }

   public double X() {
      return this.x.u() == WorldType.c ? 0.0 : 63.0;
   }

   public CrashReportCategory a(CrashReport var1) {
      CrashReportCategory var2 = var1.a("Affected level", 1);
      var2.a("Level name", this.x == null ? "????" : this.x.k());
      var2.a("All players", new Callable<String>() {
         public String a() {
            return World.this.j.size() + " total; " + World.this.j.toString();
         }
      });
      var2.a("Chunk stats", new Callable<String>() {
         public String a() {
            return World.this.v.f();
         }
      });

      try {
         this.x.a(var2);
      } catch (Throwable var4) {
         var2.a("Level Data Unobtainable", var4);
      }

      return var2;
   }

   public void c(int var1, BlockPos var2, int var3) {
      for (int var4 = 0; var4 < this.u.size(); var4++) {
         IWorldAccess var5 = this.u.get(var4);
         var5.b(var1, var2, var3);
      }
   }

   public Calendar Y() {
      if (this.K() % 600L == 0L) {
         this.K.setTimeInMillis(MinecraftServer.az());
      }

      return this.K;
   }

   public void a(double var1, double var3, double var5, double var7, double var9, double var11, NBTTagCompound var13) {
   }

   public Scoreboard Z() {
      return this.C;
   }

   public void e(BlockPos var1, Block var2) {
      for (EnumFacing var4 : EnumFacing.Plane.a) {
         BlockPos var5 = var1.a(var4);
         if (this.e(var5)) {
            IBlockState var6 = this.p(var5);
            if (Blocks.cj.e(var6.c())) {
               var6.c().a(this, var5, var6, var2);
            } else if (var6.c().v()) {
               var5 = var5.a(var4);
               var6 = this.p(var5);
               if (Blocks.cj.e(var6.c())) {
                  var6.c().a(this, var5, var6, var2);
               }
            }
         }
      }
   }

   public DifficultyInstance E(BlockPos var1) {
      long var2 = 0L;
      float var4 = 0.0F;
      if (this.e(var1)) {
         var4 = this.y();
         var2 = this.f(var1).w();
      }

      return new DifficultyInstance(this.aa(), this.L(), var2, var4);
   }

   public EnumDifficulty aa() {
      return this.P().y();
   }

   public int ab() {
      return this.I;
   }

   public void c(int var1) {
      this.I = var1;
   }

   public int ac() {
      return this.J;
   }

   public void d(int var1) {
      this.J = var1;
   }

   public boolean ad() {
      return this.y;
   }

   public VillageCollection ae() {
      return this.A;
   }

   public WorldBorder af() {
      return this.N;
   }

   public boolean c(int var1, int var2) {
      BlockPos var3 = this.M();
      int var4 = var1 * 16 + 8 - var3.n();
      int var5 = var2 * 16 + 8 - var3.p();
      short var6 = 128;
      return var4 >= -var6 && var4 <= var6 && var5 >= -var6 && var5 <= var6;
   }
}
