package net.minecraft.entity;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class Entity implements ICommandSender {
   private static final AxisAlignedBB a = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   private static int b;
   private int c;
   public double j;
   public boolean k;
   public Entity l;
   public Entity m;
   public boolean n;
   public World o;
   public double p;
   public double q;
   public double r;
   public double s;
   public double t;
   public double u;
   public double v;
   public double w;
   public double x;
   public float y;
   public float z;
   public float A;
   public float B;
   private AxisAlignedBB f;
   public boolean C;
   public boolean D;
   public boolean E;
   public boolean F;
   public boolean G;
   protected boolean H;
   private boolean g;
   public boolean I;
   public float J;
   public float K;
   public float L;
   public float M;
   public float N;
   public float O;
   private int h;
   public double P;
   public double Q;
   public double R;
   public float S;
   public boolean T;
   public float U;
   protected Random V;
   public int W;
   public int X;
   private int i;
   protected boolean Y;
   public int Z;
   protected boolean aa;
   protected boolean ab;
   protected DataWatcher ac;
   private double ar;
   private double as;
   public boolean ad;
   public int ae;
   public int af;
   public int ag;
   public int bW;
   public int bX;
   public int bY;
   public boolean ah;
   public boolean ai;
   public int aj;
   protected boolean ak;
   protected int al;
   public int am;
   protected BlockPos an;
   protected Vec3 ao;
   protected EnumFacing ap;
   private boolean at;
   protected UUID aq;
   private final CommandResultStats au;

   public int F() {
      return this.c;
   }

   public void d(int var1) {
      this.c = var1;
   }

   public void G() {
      this.J();
   }

   public Entity(World var1) {
      this.c = b++;
      this.j = 1.0;
      this.f = a;
      this.J = 0.6F;
      this.K = 1.8F;
      this.h = 1;
      this.V = new Random();
      this.X = 1;
      this.aa = true;
      this.aq = MathHelper.a(this.V);
      this.au = new CommandResultStats();
      this.o = var1;
      this.b(0.0, 0.0, 0.0);
      if (var1 != null) {
         this.am = var1.t.q();
      }

      this.ac = new DataWatcher(this);
      this.ac.a(0, Byte.valueOf((byte)0));
      this.ac.a(1, Short.valueOf((short)300));
      this.ac.a(3, Byte.valueOf((byte)0));
      this.ac.a(2, "");
      this.ac.a(4, Byte.valueOf((byte)0));
      this.h();
   }

   protected abstract void h();

   public DataWatcher H() {
      return this.ac;
   }

   @Override
   public boolean equals(Object var1) {
      return var1 instanceof Entity ? ((Entity)var1).c == this.c : false;
   }

   @Override
   public int hashCode() {
      return this.c;
   }

   protected void I() {
      if (this.o != null) {
         while (this.t > 0.0 && this.t < 256.0) {
            this.b(this.s, this.t, this.u);
            if (this.o.a(this, this.aR()).isEmpty()) {
               break;
            }

            this.t++;
         }

         this.v = this.w = this.x = 0.0;
         this.z = 0.0F;
      }
   }

   public void J() {
      this.I = true;
   }

   protected void a(float var1, float var2) {
      if (var1 != this.J || var2 != this.K) {
         float var3 = this.J;
         this.J = var1;
         this.K = var2;
         this.a(new AxisAlignedBB(this.aR().a, this.aR().b, this.aR().c, this.aR().a + this.J, this.aR().b + this.K, this.aR().c + this.J));
         if (this.J > var3 && !this.aa && !this.o.D) {
            this.d(var3 - this.J, 0.0, var3 - this.J);
         }
      }
   }

   protected void b(float var1, float var2) {
      this.y = var1 % 360.0F;
      this.z = var2 % 360.0F;
   }

   public void b(double var1, double var3, double var5) {
      this.s = var1;
      this.t = var3;
      this.u = var5;
      float var7 = this.J / 2.0F;
      float var8 = this.K;
      this.a(new AxisAlignedBB(var1 - var7, var3, var5 - var7, var1 + var7, var3 + var8, var5 + var7));
   }

   public void c(float var1, float var2) {
      float var3 = this.z;
      float var4 = this.y;
      this.y = (float)(this.y + var1 * 0.15);
      this.z = (float)(this.z - var2 * 0.15);
      this.z = MathHelper.a(this.z, -90.0F, 90.0F);
      this.B = this.B + (this.z - var3);
      this.A = this.A + (this.y - var4);
   }

   public void t_() {
      this.K();
   }

   public void K() {
      this.o.B.a("entityBaseTick");
      if (this.m != null && this.m.I) {
         this.m = null;
      }

      this.L = this.M;
      this.p = this.s;
      this.q = this.t;
      this.r = this.u;
      this.B = this.z;
      this.A = this.y;
      if (!this.o.D && this.o instanceof WorldServer) {
         this.o.B.a("portal");
         MinecraftServer var1 = ((WorldServer)this.o).r();
         int var2 = this.L();
         if (this.ak) {
            if (var1.C()) {
               if (this.m == null && this.al++ >= var2) {
                  this.al = var2;
                  this.aj = this.aq();
                  byte var3;
                  if (this.o.t.q() == -1) {
                     var3 = 0;
                  } else {
                     var3 = -1;
                  }

                  this.c((int)var3);
               }

               this.ak = false;
            }
         } else {
            if (this.al > 0) {
               this.al -= 4;
            }

            if (this.al < 0) {
               this.al = 0;
            }
         }

         if (this.aj > 0) {
            this.aj--;
         }

         this.o.B.b();
      }

      this.Y();
      this.W();
      if (this.o.D) {
         this.i = 0;
      } else if (this.i > 0) {
         if (this.ab) {
            this.i -= 4;
            if (this.i < 0) {
               this.i = 0;
            }
         } else {
            if (this.i % 20 == 0) {
               this.a(DamageSource.c, 1.0F);
            }

            this.i--;
         }
      }

      if (this.ab()) {
         this.M();
         this.O *= 0.5F;
      }

      if (this.t < -64.0) {
         this.O();
      }

      if (!this.o.D) {
         this.b(0, this.i > 0);
      }

      this.aa = false;
      this.o.B.b();
   }

   public int L() {
      return 0;
   }

   protected void M() {
      if (!this.ab) {
         this.a(DamageSource.d, 4.0F);
         this.e(15);
      }
   }

   public void e(int var1) {
      int var2 = var1 * 20;
      var2 = EnchantmentProtection.a(this, var2);
      if (this.i < var2) {
         this.i = var2;
      }
   }

   public void N() {
      this.i = 0;
   }

   protected void O() {
      this.J();
   }

   public boolean c(double var1, double var3, double var5) {
      AxisAlignedBB var7 = this.aR().c(var1, var3, var5);
      return this.b(var7);
   }

   private boolean b(AxisAlignedBB var1) {
      return this.o.a(this, var1).isEmpty() && !this.o.d(var1);
   }

   public void d(double var1, double var3, double var5) {
      if (this.T) {
         this.a(this.aR().c(var1, var3, var5));
         this.m();
      } else {
         this.o.B.a("move");
         double var7 = this.s;
         double var9 = this.t;
         double var11 = this.u;
         if (this.H) {
            this.H = false;
            var1 *= 0.25;
            var3 *= 0.05F;
            var5 *= 0.25;
            this.v = 0.0;
            this.w = 0.0;
            this.x = 0.0;
         }

         double var13 = var1;
         double var15 = var3;
         double var17 = var5;
         boolean var19 = this.C && this.av() && this instanceof EntityPlayer;
         if (var19) {
            double var20;
            for (var20 = 0.05; var1 != 0.0 && this.o.a(this, this.aR().c(var1, -1.0, 0.0)).isEmpty(); var13 = var1) {
               if (var1 < var20 && var1 >= -var20) {
                  var1 = 0.0;
               } else if (var1 > 0.0) {
                  var1 -= var20;
               } else {
                  var1 += var20;
               }
            }

            for (; var5 != 0.0 && this.o.a(this, this.aR().c(0.0, -1.0, var5)).isEmpty(); var17 = var5) {
               if (var5 < var20 && var5 >= -var20) {
                  var5 = 0.0;
               } else if (var5 > 0.0) {
                  var5 -= var20;
               } else {
                  var5 += var20;
               }
            }

            for (; var1 != 0.0 && var5 != 0.0 && this.o.a(this, this.aR().c(var1, -1.0, var5)).isEmpty(); var17 = var5) {
               if (var1 < var20 && var1 >= -var20) {
                  var1 = 0.0;
               } else if (var1 > 0.0) {
                  var1 -= var20;
               } else {
                  var1 += var20;
               }

               var13 = var1;
               if (var5 < var20 && var5 >= -var20) {
                  var5 = 0.0;
               } else if (var5 > 0.0) {
                  var5 -= var20;
               } else {
                  var5 += var20;
               }
            }
         }

         List var54 = this.o.a(this, this.aR().a(var1, var3, var5));
         AxisAlignedBB var21 = this.aR();

         for (AxisAlignedBB var23 : var54) {
            var3 = var23.b(this.aR(), var3);
         }

         this.a(this.aR().c(0.0, var3, 0.0));
         boolean var55 = this.C || var15 != var3 && var15 < 0.0;

         for (AxisAlignedBB var24 : var54) {
            var1 = var24.a(this.aR(), var1);
         }

         this.a(this.aR().c(var1, 0.0, 0.0));

         for (AxisAlignedBB var60 : var54) {
            var5 = var60.c(this.aR(), var5);
         }

         this.a(this.aR().c(0.0, 0.0, var5));
         if (this.S > 0.0F && var55 && (var13 != var1 || var17 != var5)) {
            double var58 = var1;
            double var25 = var3;
            double var27 = var5;
            AxisAlignedBB var29 = this.aR();
            this.a(var21);
            var3 = this.S;
            List var30 = this.o.a(this, this.aR().a(var13, var3, var17));
            AxisAlignedBB var31 = this.aR();
            AxisAlignedBB var32 = var31.a(var13, 0.0, var17);
            double var33 = var3;

            for (AxisAlignedBB var36 : var30) {
               var33 = var36.b(var32, var33);
            }

            var31 = var31.c(0.0, var33, 0.0);
            double var73 = var13;

            for (AxisAlignedBB var38 : var30) {
               var73 = var38.a(var31, var73);
            }

            var31 = var31.c(var73, 0.0, 0.0);
            double var74 = var17;

            for (AxisAlignedBB var40 : var30) {
               var74 = var40.c(var31, var74);
            }

            var31 = var31.c(0.0, 0.0, var74);
            AxisAlignedBB var75 = this.aR();
            double var79 = var3;

            for (AxisAlignedBB var43 : var30) {
               var79 = var43.b(var75, var79);
            }

            var75 = var75.c(0.0, var79, 0.0);
            double var80 = var13;

            for (AxisAlignedBB var45 : var30) {
               var80 = var45.a(var75, var80);
            }

            var75 = var75.c(var80, 0.0, 0.0);
            double var81 = var17;

            for (AxisAlignedBB var47 : var30) {
               var81 = var47.c(var75, var81);
            }

            var75 = var75.c(0.0, 0.0, var81);
            double var82 = var73 * var73 + var74 * var74;
            double var48 = var80 * var80 + var81 * var81;
            if (var82 > var48) {
               var1 = var73;
               var5 = var74;
               var3 = -var33;
               this.a(var31);
            } else {
               var1 = var80;
               var5 = var81;
               var3 = -var79;
               this.a(var75);
            }

            for (AxisAlignedBB var51 : var30) {
               var3 = var51.b(this.aR(), var3);
            }

            this.a(this.aR().c(0.0, var3, 0.0));
            if (var58 * var58 + var27 * var27 >= var1 * var1 + var5 * var5) {
               var1 = var58;
               var3 = var25;
               var5 = var27;
               this.a(var29);
            }
         }

         this.o.B.b();
         this.o.B.a("rest");
         this.m();
         this.D = var13 != var1 || var17 != var5;
         this.E = var15 != var3;
         this.C = this.E && var15 < 0.0;
         this.F = this.D || this.E;
         int var59 = MathHelper.c(this.s);
         int var61 = MathHelper.c(this.t - 0.2F);
         int var62 = MathHelper.c(this.u);
         BlockPos var26 = new BlockPos(var59, var61, var62);
         Block var63 = this.o.p(var26).c();
         if (var63.t() == Material.a) {
            Block var28 = this.o.p(var26.b()).c();
            if (var28 instanceof BlockFence || var28 instanceof BlockWall || var28 instanceof BlockFenceGate) {
               var63 = var28;
               var26 = var26.b();
            }
         }

         this.a(var3, this.C, var63, var26);
         if (var13 != var1) {
            this.v = 0.0;
         }

         if (var17 != var5) {
            this.x = 0.0;
         }

         if (var15 != var3) {
            var63.a(this.o, this);
         }

         if (this.s_() && !var19 && this.m == null) {
            double var64 = this.s - var7;
            double var67 = this.t - var9;
            double var72 = this.u - var11;
            if (var63 != Blocks.au) {
               var67 = 0.0;
            }

            if (var63 != null && this.C) {
               var63.a(this.o, var26, this);
            }

            this.M = (float)(this.M + MathHelper.a(var64 * var64 + var72 * var72) * 0.6);
            this.N = (float)(this.N + MathHelper.a(var64 * var64 + var67 * var67 + var72 * var72) * 0.6);
            if (this.N > this.h && var63.t() != Material.a) {
               this.h = (int)this.N + 1;
               if (this.V()) {
                  float var34 = MathHelper.a(this.v * this.v * 0.2F + this.w * this.w + this.x * this.x * 0.2F) * 0.35F;
                  if (var34 > 1.0F) {
                     var34 = 1.0F;
                  }

                  this.a(this.P(), var34, 1.0F + (this.V.nextFloat() - this.V.nextFloat()) * 0.4F);
               }

               this.a(var26, var63);
            }
         }

         try {
            this.Q();
         } catch (Throwable var52) {
            CrashReport var66 = CrashReport.a(var52, "Checking entity block collision");
            CrashReportCategory var68 = var66.a("Entity being checked for collision");
            this.a(var68);
            throw new ReportedException(var66);
         }

         boolean var65 = this.U();
         if (this.o.e(this.aR().d(0.001, 0.001, 0.001))) {
            this.f(1);
            if (!var65) {
               this.i++;
               if (this.i == 0) {
                  this.e(8);
               }
            }
         } else if (this.i <= 0) {
            this.i = -this.X;
         }

         if (var65 && this.i > 0) {
            this.a("random.fizz", 0.7F, 1.6F + (this.V.nextFloat() - this.V.nextFloat()) * 0.4F);
            this.i = -this.X;
         }

         this.o.B.b();
      }
   }

   private void m() {
      this.s = (this.aR().a + this.aR().d) / 2.0;
      this.t = this.aR().b;
      this.u = (this.aR().c + this.aR().f) / 2.0;
   }

   protected String P() {
      return "game.neutral.swim";
   }

   protected void Q() {
      BlockPos var1 = new BlockPos(this.aR().a + 0.001, this.aR().b + 0.001, this.aR().c + 0.001);
      BlockPos var2 = new BlockPos(this.aR().d - 0.001, this.aR().e - 0.001, this.aR().f - 0.001);
      if (this.o.a(var1, var2)) {
         for (int var3 = var1.n(); var3 <= var2.n(); var3++) {
            for (int var4 = var1.o(); var4 <= var2.o(); var4++) {
               for (int var5 = var1.p(); var5 <= var2.p(); var5++) {
                  BlockPos var6 = new BlockPos(var3, var4, var5);
                  IBlockState var7 = this.o.p(var6);

                  try {
                     var7.c().a(this.o, var6, var7, this);
                  } catch (Throwable var11) {
                     CrashReport var9 = CrashReport.a(var11, "Colliding entity with block");
                     CrashReportCategory var10 = var9.a("Block being collided with");
                     CrashReportCategory.a(var10, var6, var7);
                     throw new ReportedException(var9);
                  }
               }
            }
         }
      }
   }

   protected void a(BlockPos var1, Block var2) {
      Block.SoundType var3 = var2.H;
      if (this.o.p(var1.a()).c() == Blocks.aH) {
         var3 = Blocks.aH.H;
         this.a(var3.c(), var3.d() * 0.15F, var3.e());
      } else if (!var2.t().d()) {
         this.a(var3.c(), var3.d() * 0.15F, var3.e());
      }
   }

   public void a(String var1, float var2, float var3) {
      if (!this.R()) {
         this.o.a(this, var1, var2, var3);
      }
   }

   public boolean R() {
      return this.ac.a(4) == 1;
   }

   public void b(boolean var1) {
      this.ac.b(4, Byte.valueOf((byte)(var1 ? 1 : 0)));
   }

   protected boolean s_() {
      return true;
   }

   protected void a(double var1, boolean var3, Block var4, BlockPos var5) {
      if (var3) {
         if (this.O > 0.0F) {
            if (var4 != null) {
               var4.a(this.o, var5, this, this.O);
            } else {
               this.e(this.O, 1.0F);
            }

            this.O = 0.0F;
         }
      } else if (var1 < 0.0) {
         this.O = (float)(this.O - var1);
      }
   }

   public AxisAlignedBB S() {
      return null;
   }

   protected void f(int var1) {
      if (!this.ab) {
         this.a(DamageSource.a, (float)var1);
      }
   }

   public final boolean T() {
      return this.ab;
   }

   public void e(float var1, float var2) {
      if (this.l != null) {
         this.l.e(var1, var2);
      }
   }

   public boolean U() {
      return this.Y || this.o.C(new BlockPos(this.s, this.t, this.u)) || this.o.C(new BlockPos(this.s, this.t + this.K, this.u));
   }

   public boolean V() {
      return this.Y;
   }

   public boolean W() {
      if (this.o.a(this.aR().b(0.0, -0.4F, 0.0).d(0.001, 0.001, 0.001), Material.h, this)) {
         if (!this.Y && !this.aa) {
            this.X();
         }

         this.O = 0.0F;
         this.Y = true;
         this.i = 0;
      } else {
         this.Y = false;
      }

      return this.Y;
   }

   protected void X() {
      float var1 = MathHelper.a(this.v * this.v * 0.2F + this.w * this.w + this.x * this.x * 0.2F) * 0.2F;
      if (var1 > 1.0F) {
         var1 = 1.0F;
      }

      this.a(this.aa(), var1, 1.0F + (this.V.nextFloat() - this.V.nextFloat()) * 0.4F);
      float var2 = MathHelper.c(this.aR().b);

      for (int var3 = 0; var3 < 1.0F + this.J * 20.0F; var3++) {
         float var4 = (this.V.nextFloat() * 2.0F - 1.0F) * this.J;
         float var5 = (this.V.nextFloat() * 2.0F - 1.0F) * this.J;
         this.o.a(EnumParticleTypes.e, this.s + var4, var2 + 1.0F, this.u + var5, this.v, this.w - this.V.nextFloat() * 0.2F, this.x);
      }

      for (int var6 = 0; var6 < 1.0F + this.J * 20.0F; var6++) {
         float var7 = (this.V.nextFloat() * 2.0F - 1.0F) * this.J;
         float var8 = (this.V.nextFloat() * 2.0F - 1.0F) * this.J;
         this.o.a(EnumParticleTypes.f, this.s + var7, var2 + 1.0F, this.u + var8, this.v, this.w, this.x);
      }
   }

   public void Y() {
      if (this.aw() && !this.V()) {
         this.Z();
      }
   }

   protected void Z() {
      int var1 = MathHelper.c(this.s);
      int var2 = MathHelper.c(this.t - 0.2F);
      int var3 = MathHelper.c(this.u);
      BlockPos var4 = new BlockPos(var1, var2, var3);
      IBlockState var5 = this.o.p(var4);
      Block var6 = var5.c();
      if (var6.b() != -1) {
         this.o
            .a(
               EnumParticleTypes.L,
               this.s + (this.V.nextFloat() - 0.5) * this.J,
               this.aR().b + 0.1,
               this.u + (this.V.nextFloat() - 0.5) * this.J,
               -this.v * 4.0,
               1.5,
               -this.x * 4.0,
               Block.f(var5)
            );
      }
   }

   protected String aa() {
      return "game.neutral.swim.splash";
   }

   public boolean a(Material var1) {
      double var2 = this.t + this.aS();
      BlockPos var4 = new BlockPos(this.s, var2, this.u);
      IBlockState var5 = this.o.p(var4);
      Block var6 = var5.c();
      if (var6.t() == var1) {
         float var7 = BlockLiquid.b(var5.c().c(var5)) - 0.11111111F;
         float var8 = var4.o() + 1 - var7;
         boolean var9 = var2 < var8;
         return !var9 && this instanceof EntityPlayer ? false : var9;
      } else {
         return false;
      }
   }

   public boolean ab() {
      return this.o.a(this.aR().b(-0.1F, -0.4F, -0.1F), Material.i);
   }

   public void a(float var1, float var2, float var3) {
      float var4 = var1 * var1 + var2 * var2;
      if (!(var4 < 1.0E-4F)) {
         var4 = MathHelper.c(var4);
         if (var4 < 1.0F) {
            var4 = 1.0F;
         }

         var4 = var3 / var4;
         var1 *= var4;
         var2 *= var4;
         float var5 = MathHelper.a(this.y * (float) Math.PI / 180.0F);
         float var6 = MathHelper.b(this.y * (float) Math.PI / 180.0F);
         this.v += var1 * var6 - var2 * var5;
         this.x += var2 * var6 + var1 * var5;
      }
   }

   public int b(float var1) {
      BlockPos var2 = new BlockPos(this.s, this.t + this.aS(), this.u);
      return this.o.e(var2) ? this.o.b(var2, 0) : 0;
   }

   public float c(float var1) {
      BlockPos var2 = new BlockPos(this.s, this.t + this.aS(), this.u);
      return this.o.e(var2) ? this.o.o(var2) : 0.0F;
   }

   public void a(World var1) {
      this.o = var1;
   }

   public void a(double var1, double var3, double var5, float var7, float var8) {
      this.p = this.s = var1;
      this.q = this.t = var3;
      this.r = this.u = var5;
      this.A = this.y = var7;
      this.B = this.z = var8;
      double var9 = this.A - var7;
      if (var9 < -180.0) {
         this.A += 360.0F;
      }

      if (var9 >= 180.0) {
         this.A -= 360.0F;
      }

      this.b(this.s, this.t, this.u);
      this.b(var7, var8);
   }

   public void a(BlockPos var1, float var2, float var3) {
      this.b(var1.n() + 0.5, var1.o(), var1.p() + 0.5, var2, var3);
   }

   public void b(double var1, double var3, double var5, float var7, float var8) {
      this.P = this.p = this.s = var1;
      this.Q = this.q = this.t = var3;
      this.R = this.r = this.u = var5;
      this.y = var7;
      this.z = var8;
      this.b(this.s, this.t, this.u);
   }

   public float g(Entity var1) {
      float var2 = (float)(this.s - var1.s);
      float var3 = (float)(this.t - var1.t);
      float var4 = (float)(this.u - var1.u);
      return MathHelper.c(var2 * var2 + var3 * var3 + var4 * var4);
   }

   public double e(double var1, double var3, double var5) {
      double var7 = this.s - var1;
      double var9 = this.t - var3;
      double var11 = this.u - var5;
      return var7 * var7 + var9 * var9 + var11 * var11;
   }

   public double b(BlockPos var1) {
      return var1.c(this.s, this.t, this.u);
   }

   public double c(BlockPos var1) {
      return var1.d(this.s, this.t, this.u);
   }

   public double f(double var1, double var3, double var5) {
      double var7 = this.s - var1;
      double var9 = this.t - var3;
      double var11 = this.u - var5;
      return MathHelper.a(var7 * var7 + var9 * var9 + var11 * var11);
   }

   public double h(Entity var1) {
      double var2 = this.s - var1.s;
      double var4 = this.t - var1.t;
      double var6 = this.u - var1.u;
      return var2 * var2 + var4 * var4 + var6 * var6;
   }

   public void d(EntityPlayer var1) {
   }

   public void i(Entity var1) {
      if (var1.l != this && var1.m != this) {
         if (!var1.T && !this.T) {
            double var2 = var1.s - this.s;
            double var4 = var1.u - this.u;
            double var6 = MathHelper.a(var2, var4);
            if (var6 >= 0.01F) {
               var6 = MathHelper.a(var6);
               var2 /= var6;
               var4 /= var6;
               double var8 = 1.0 / var6;
               if (var8 > 1.0) {
                  var8 = 1.0;
               }

               var2 *= var8;
               var4 *= var8;
               var2 *= 0.05F;
               var4 *= 0.05F;
               var2 *= 1.0F - this.U;
               var4 *= 1.0F - this.U;
               if (this.l == null) {
                  this.g(-var2, 0.0, -var4);
               }

               if (var1.l == null) {
                  var1.g(var2, 0.0, var4);
               }
            }
         }
      }
   }

   public void g(double var1, double var3, double var5) {
      this.v += var1;
      this.w += var3;
      this.x += var5;
      this.ai = true;
   }

   protected void ac() {
      this.G = true;
   }

   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else {
         this.ac();
         return false;
      }
   }

   public Vec3 d(float var1) {
      if (var1 == 1.0F) {
         return this.f(this.z, this.y);
      } else {
         float var2 = this.B + (this.z - this.B) * var1;
         float var3 = this.A + (this.y - this.A) * var1;
         return this.f(var2, var3);
      }
   }

   protected final Vec3 f(float var1, float var2) {
      float var3 = MathHelper.b(-var2 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var4 = MathHelper.a(-var2 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var5 = -MathHelper.b(-var1 * (float) (Math.PI / 180.0));
      float var6 = MathHelper.a(-var1 * (float) (Math.PI / 180.0));
      return new Vec3(var4 * var5, var6, var3 * var5);
   }

   public Vec3 e(float var1) {
      if (var1 == 1.0F) {
         return new Vec3(this.s, this.t + this.aS(), this.u);
      } else {
         double var2 = this.p + (this.s - this.p) * var1;
         double var4 = this.q + (this.t - this.q) * var1 + this.aS();
         double var6 = this.r + (this.u - this.r) * var1;
         return new Vec3(var2, var4, var6);
      }
   }

   public MovingObjectPosition a(double var1, float var3) {
      Vec3 var4 = this.e(var3);
      Vec3 var5 = this.d(var3);
      Vec3 var6 = var4.b(var5.a * var1, var5.b * var1, var5.c * var1);
      return this.o.a(var4, var6, false, false, true);
   }

   public boolean ad() {
      return false;
   }

   public boolean ae() {
      return false;
   }

   public void b(Entity var1, int var2) {
   }

   public boolean h(double var1, double var3, double var5) {
      double var7 = this.s - var1;
      double var9 = this.t - var3;
      double var11 = this.u - var5;
      double var13 = var7 * var7 + var9 * var9 + var11 * var11;
      return this.a(var13);
   }

   public boolean a(double var1) {
      double var3 = this.aR().a();
      if (Double.isNaN(var3)) {
         var3 = 1.0;
      }

      var3 *= 64.0 * this.j;
      return var1 < var3 * var3;
   }

   public boolean c(NBTTagCompound var1) {
      String var2 = this.ag();
      if (!this.I && var2 != null) {
         var1.a("id", var2);
         this.e(var1);
         return true;
      } else {
         return false;
      }
   }

   public boolean d(NBTTagCompound var1) {
      String var2 = this.ag();
      if (!this.I && var2 != null && this.l == null) {
         var1.a("id", var2);
         this.e(var1);
         return true;
      } else {
         return false;
      }
   }

   public void e(NBTTagCompound var1) {
      try {
         var1.a("Pos", this.a(this.s, this.t, this.u));
         var1.a("Motion", this.a(this.v, this.w, this.x));
         var1.a("Rotation", this.a(this.y, this.z));
         var1.a("FallDistance", this.O);
         var1.a("Fire", (short)this.i);
         var1.a("Air", (short)this.az());
         var1.a("OnGround", this.C);
         var1.a("Dimension", this.am);
         var1.a("Invulnerable", this.at);
         var1.a("PortalCooldown", this.aj);
         var1.a("UUIDMost", this.aK().getMostSignificantBits());
         var1.a("UUIDLeast", this.aK().getLeastSignificantBits());
         if (this.aM() != null && this.aM().length() > 0) {
            var1.a("CustomName", this.aM());
            var1.a("CustomNameVisible", this.aN());
         }

         this.au.b(var1);
         if (this.R()) {
            var1.a("Silent", this.R());
         }

         this.b(var1);
         if (this.m != null) {
            NBTTagCompound var2 = new NBTTagCompound();
            if (this.m.c(var2)) {
               var1.a("Riding", var2);
            }
         }
      } catch (Throwable var5) {
         CrashReport var3 = CrashReport.a(var5, "Saving entity NBT");
         CrashReportCategory var4 = var3.a("Entity being saved");
         this.a(var4);
         throw new ReportedException(var3);
      }
   }

   public void f(NBTTagCompound var1) {
      try {
         NBTTagList var2 = var1.c("Pos", 6);
         NBTTagList var6 = var1.c("Motion", 6);
         NBTTagList var7 = var1.c("Rotation", 5);
         this.v = var6.d(0);
         this.w = var6.d(1);
         this.x = var6.d(2);
         if (Math.abs(this.v) > 10.0) {
            this.v = 0.0;
         }

         if (Math.abs(this.w) > 10.0) {
            this.w = 0.0;
         }

         if (Math.abs(this.x) > 10.0) {
            this.x = 0.0;
         }

         this.p = this.P = this.s = var2.d(0);
         this.q = this.Q = this.t = var2.d(1);
         this.r = this.R = this.u = var2.d(2);
         this.A = this.y = var7.e(0);
         this.B = this.z = var7.e(1);
         this.f(this.y);
         this.g(this.y);
         this.O = var1.h("FallDistance");
         this.i = var1.e("Fire");
         this.h(var1.e("Air"));
         this.C = var1.n("OnGround");
         this.am = var1.f("Dimension");
         this.at = var1.n("Invulnerable");
         this.aj = var1.f("PortalCooldown");
         if (var1.b("UUIDMost", 4) && var1.b("UUIDLeast", 4)) {
            this.aq = new UUID(var1.g("UUIDMost"), var1.g("UUIDLeast"));
         } else if (var1.b("UUID", 8)) {
            this.aq = UUID.fromString(var1.j("UUID"));
         }

         this.b(this.s, this.t, this.u);
         this.b(this.y, this.z);
         if (var1.b("CustomName", 8) && var1.j("CustomName").length() > 0) {
            this.a(var1.j("CustomName"));
         }

         this.g(var1.n("CustomNameVisible"));
         this.au.a(var1);
         this.b(var1.n("Silent"));
         this.a(var1);
         if (this.af()) {
            this.b(this.s, this.t, this.u);
         }
      } catch (Throwable var5) {
         CrashReport var3 = CrashReport.a(var5, "Loading entity NBT");
         CrashReportCategory var4 = var3.a("Entity being loaded");
         this.a(var4);
         throw new ReportedException(var3);
      }
   }

   protected boolean af() {
      return true;
   }

   protected final String ag() {
      return EntityList.b(this);
   }

   protected abstract void a(NBTTagCompound var1);

   protected abstract void b(NBTTagCompound var1);

   public void ah() {
   }

   protected NBTTagList a(double... var1) {
      NBTTagList var2 = new NBTTagList();

      for (double var6 : var1) {
         var2.a(new NBTTagDouble(var6));
      }

      return var2;
   }

   protected NBTTagList a(float... var1) {
      NBTTagList var2 = new NBTTagList();

      for (float var6 : var1) {
         var2.a(new NBTTagFloat(var6));
      }

      return var2;
   }

   public EntityItem a(Item var1, int var2) {
      return this.a(var1, var2, 0.0F);
   }

   public EntityItem a(Item var1, int var2, float var3) {
      return this.a(new ItemStack(var1, var2, 0), var3);
   }

   public EntityItem a(ItemStack var1, float var2) {
      if (var1.b != 0 && var1.b() != null) {
         EntityItem var3 = new EntityItem(this.o, this.s, this.t + var2, this.u, var1);
         var3.p();
         this.o.d(var3);
         return var3;
      } else {
         return null;
      }
   }

   public boolean ai() {
      return !this.I;
   }

   public boolean aj() {
      if (this.T) {
         return false;
      } else {
         BlockPos.MutableBlockPos var1 = new BlockPos.MutableBlockPos(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

         for (int var2 = 0; var2 < 8; var2++) {
            int var3 = MathHelper.c(this.t + ((var2 >> 0) % 2 - 0.5F) * 0.1F + this.aS());
            int var4 = MathHelper.c(this.s + ((var2 >> 1) % 2 - 0.5F) * this.J * 0.8F);
            int var5 = MathHelper.c(this.u + ((var2 >> 2) % 2 - 0.5F) * this.J * 0.8F);
            if (var1.n() != var4 || var1.o() != var3 || var1.p() != var5) {
               var1.c(var4, var3, var5);
               if (this.o.p(var1).c().w()) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean e(EntityPlayer var1) {
      return false;
   }

   public AxisAlignedBB j(Entity var1) {
      return null;
   }

   public void ak() {
      if (this.m.I) {
         this.m = null;
      } else {
         this.v = 0.0;
         this.w = 0.0;
         this.x = 0.0;
         this.t_();
         if (this.m != null) {
            this.m.al();
            this.as = this.as + (this.m.y - this.m.A);
            this.ar = this.ar + (this.m.z - this.m.B);

            while (this.as >= 180.0) {
               this.as -= 360.0;
            }

            while (this.as < -180.0) {
               this.as += 360.0;
            }

            while (this.ar >= 180.0) {
               this.ar -= 360.0;
            }

            while (this.ar < -180.0) {
               this.ar += 360.0;
            }

            double var1 = this.as * 0.5;
            double var3 = this.ar * 0.5;
            float var5 = 10.0F;
            if (var1 > var5) {
               var1 = var5;
            }

            if (var1 < -var5) {
               var1 = -var5;
            }

            if (var3 > var5) {
               var3 = var5;
            }

            if (var3 < -var5) {
               var3 = -var5;
            }

            this.as -= var1;
            this.ar -= var3;
         }
      }
   }

   public void al() {
      if (this.l != null) {
         this.l.b(this.s, this.t + this.an() + this.l.am(), this.u);
      }
   }

   public double am() {
      return 0.0;
   }

   public double an() {
      return this.K * 0.75;
   }

   public void a(Entity var1) {
      this.ar = 0.0;
      this.as = 0.0;
      if (var1 == null) {
         if (this.m != null) {
            this.b(this.m.s, this.m.aR().b + this.m.K, this.m.u, this.y, this.z);
            this.m.l = null;
         }

         this.m = null;
      } else {
         if (this.m != null) {
            this.m.l = null;
         }

         if (var1 != null) {
            for (Entity var2 = var1.m; var2 != null; var2 = var2.m) {
               if (var2 == this) {
                  return;
               }
            }
         }

         this.m = var1;
         var1.l = this;
      }
   }

   public void a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.b(var1, var3, var5);
      this.b(var7, var8);
      List var11 = this.o.a(this, this.aR().d(0.03125, 0.0, 0.03125));
      if (!var11.isEmpty()) {
         double var12 = 0.0;

         for (AxisAlignedBB var15 : var11) {
            if (var15.e > var12) {
               var12 = var15.e;
            }
         }

         var3 += var12 - this.aR().b;
         this.b(var1, var3, var5);
      }
   }

   public float ao() {
      return 0.1F;
   }

   public Vec3 ap() {
      return null;
   }

   public void d(BlockPos var1) {
      if (this.aj > 0) {
         this.aj = this.aq();
      } else {
         if (!this.o.D && !var1.equals(this.an)) {
            this.an = var1;
            BlockPattern.PatternHelper var2 = Blocks.aY.f(this.o, var1);
            double var3 = var2.b().k() == EnumFacing.Axis.a ? var2.a().p() : var2.a().n();
            double var5 = var2.b().k() == EnumFacing.Axis.a ? this.u : this.s;
            var5 = Math.abs(MathHelper.c(var5 - (var2.b().e().c() == EnumFacing.AxisDirection.b ? 1 : 0), var3, var3 - var2.d()));
            double var7 = MathHelper.c(this.t - 1.0, (double)var2.a().o(), (double)(var2.a().o() - var2.e()));
            this.ao = new Vec3(var5, var7, 0.0);
            this.ap = var2.b();
         }

         this.ak = true;
      }
   }

   public int aq() {
      return 300;
   }

   public void i(double var1, double var3, double var5) {
      this.v = var1;
      this.w = var3;
      this.x = var5;
   }

   public void a(byte var1) {
   }

   public void ar() {
   }

   public ItemStack[] as() {
      return null;
   }

   public void c(int var1, ItemStack var2) {
   }

   public boolean at() {
      boolean var1 = this.o != null && this.o.D;
      return !this.ab && (this.i > 0 || var1 && this.g(0));
   }

   public boolean au() {
      return this.m != null;
   }

   public boolean av() {
      return this.g(1);
   }

   public void c(boolean var1) {
      this.b(1, var1);
   }

   public boolean aw() {
      return this.g(3);
   }

   public void d(boolean var1) {
      this.b(3, var1);
   }

   public boolean ax() {
      return this.g(5);
   }

   public boolean f(EntityPlayer var1) {
      return var1.v() ? false : this.ax();
   }

   public void e(boolean var1) {
      this.b(5, var1);
   }

   public boolean ay() {
      return this.g(4);
   }

   public void f(boolean var1) {
      this.b(4, var1);
   }

   protected boolean g(int var1) {
      return (this.ac.a(0) & 1 << var1) != 0;
   }

   protected void b(int var1, boolean var2) {
      byte var3 = this.ac.a(0);
      if (var2) {
         this.ac.b(0, (byte)(var3 | 1 << var1));
      } else {
         this.ac.b(0, (byte)(var3 & ~(1 << var1)));
      }
   }

   public int az() {
      return this.ac.b(1);
   }

   public void h(int var1) {
      this.ac.b(1, (short)var1);
   }

   public void a(EntityLightningBolt var1) {
      this.a(DamageSource.b, 5.0F);
      this.i++;
      if (this.i == 0) {
         this.e(8);
      }
   }

   public void a(EntityLivingBase var1) {
   }

   protected boolean j(double var1, double var3, double var5) {
      BlockPos var7 = new BlockPos(var1, var3, var5);
      double var8 = var1 - var7.n();
      double var10 = var3 - var7.o();
      double var12 = var5 - var7.p();
      List var14 = this.o.a(this.aR());
      if (var14.isEmpty() && !this.o.u(var7)) {
         return false;
      } else {
         byte var15 = 3;
         double var16 = 9999.0;
         if (!this.o.u(var7.e()) && var8 < var16) {
            var16 = var8;
            var15 = 0;
         }

         if (!this.o.u(var7.f()) && 1.0 - var8 < var16) {
            var16 = 1.0 - var8;
            var15 = 1;
         }

         if (!this.o.u(var7.a()) && 1.0 - var10 < var16) {
            var16 = 1.0 - var10;
            var15 = 3;
         }

         if (!this.o.u(var7.c()) && var12 < var16) {
            var16 = var12;
            var15 = 4;
         }

         if (!this.o.u(var7.d()) && 1.0 - var12 < var16) {
            var16 = 1.0 - var12;
            var15 = 5;
         }

         float var18 = this.V.nextFloat() * 0.2F + 0.1F;
         if (var15 == 0) {
            this.v = -var18;
         }

         if (var15 == 1) {
            this.v = var18;
         }

         if (var15 == 3) {
            this.w = var18;
         }

         if (var15 == 4) {
            this.x = -var18;
         }

         if (var15 == 5) {
            this.x = var18;
         }

         return true;
      }
   }

   public void aA() {
      this.H = true;
      this.O = 0.0F;
   }

   @Override
   public String e_() {
      if (this.l_()) {
         return this.aM();
      } else {
         String var1 = EntityList.b(this);
         if (var1 == null) {
            var1 = "generic";
         }

         return StatCollector.a("entity." + var1 + ".name");
      }
   }

   public Entity[] aB() {
      return null;
   }

   public boolean k(Entity var1) {
      return this == var1;
   }

   public float aC() {
      return 0.0F;
   }

   public void f(float var1) {
   }

   public void g(float var1) {
   }

   public boolean aD() {
      return true;
   }

   public boolean l(Entity var1) {
      return false;
   }

   @Override
   public String toString() {
      return String.format(
         "%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]",
         this.getClass().getSimpleName(),
         this.e_(),
         this.c,
         this.o == null ? "~NULL~" : this.o.P().k(),
         this.s,
         this.t,
         this.u
      );
   }

   public boolean b(DamageSource var1) {
      return this.at && var1 != DamageSource.j && !var1.u();
   }

   public void m(Entity var1) {
      this.b(var1.s, var1.t, var1.u, var1.y, var1.z);
   }

   public void n(Entity var1) {
      NBTTagCompound var2 = new NBTTagCompound();
      var1.e(var2);
      this.f(var2);
      this.aj = var1.aj;
      this.an = var1.an;
      this.ao = var1.ao;
      this.ap = var1.ap;
   }

   public void c(int var1) {
      if (!this.o.D && !this.I) {
         this.o.B.a("changeDimension");
         MinecraftServer var2 = MinecraftServer.N();
         int var3 = this.am;
         WorldServer var4 = var2.a(var3);
         WorldServer var5 = var2.a(var1);
         this.am = var1;
         if (var3 == 1 && var1 == 1) {
            var5 = var2.a(0);
            this.am = 0;
         }

         this.o.e(this);
         this.I = false;
         this.o.B.a("reposition");
         var2.ap().a(this, var3, var4, var5);
         this.o.B.c("reloading");
         Entity var6 = EntityList.a(EntityList.b(this), var5);
         if (var6 != null) {
            var6.n(this);
            if (var3 == 1 && var1 == 1) {
               BlockPos var7 = this.o.r(var5.M());
               var6.a(var7, var6.y, var6.z);
            }

            var5.d(var6);
         }

         this.I = true;
         this.o.B.b();
         var4.j();
         var5.j();
         this.o.B.b();
      }
   }

   public float a(Explosion var1, World var2, BlockPos var3, IBlockState var4) {
      return var4.c().a(this);
   }

   public boolean a(Explosion var1, World var2, BlockPos var3, IBlockState var4, float var5) {
      return true;
   }

   public int aE() {
      return 3;
   }

   public Vec3 aG() {
      return this.ao;
   }

   public EnumFacing aH() {
      return this.ap;
   }

   public boolean aI() {
      return false;
   }

   public void a(CrashReportCategory var1) {
      var1.a("Entity Type", new Callable<String>() {
         public String a() throws Exception {
            return EntityList.b(Entity.this) + " (" + Entity.this.getClass().getCanonicalName() + ")";
         }
      });
      var1.a("Entity ID", this.c);
      var1.a("Entity Name", new Callable<String>() {
         public String a() throws Exception {
            return Entity.this.e_();
         }
      });
      var1.a("Entity's Exact location", String.format("%.2f, %.2f, %.2f", this.s, this.t, this.u));
      var1.a("Entity's Block location", CrashReportCategory.a(MathHelper.c(this.s), MathHelper.c(this.t), MathHelper.c(this.u)));
      var1.a("Entity's Momentum", String.format("%.2f, %.2f, %.2f", this.v, this.w, this.x));
      var1.a("Entity's Rider", new Callable<String>() {
         public String a() throws Exception {
            return Entity.this.l.toString();
         }
      });
      var1.a("Entity's Vehicle", new Callable<String>() {
         public String a() throws Exception {
            return Entity.this.m.toString();
         }
      });
   }

   public boolean aJ() {
      return this.at();
   }

   public UUID aK() {
      return this.aq;
   }

   public boolean aL() {
      return true;
   }

   @Override
   public IChatComponent f_() {
      ChatComponentText var1 = new ChatComponentText(this.e_());
      var1.b().a(this.aQ());
      var1.b().a(this.aK().toString());
      return var1;
   }

   public void a(String var1) {
      this.ac.b(2, var1);
   }

   public String aM() {
      return this.ac.e(2);
   }

   public boolean l_() {
      return this.ac.e(2).length() > 0;
   }

   public void g(boolean var1) {
      this.ac.b(3, Byte.valueOf((byte)(var1 ? 1 : 0)));
   }

   public boolean aN() {
      return this.ac.a(3) == 1;
   }

   public void a(double var1, double var3, double var5) {
      this.b(var1, var3, var5, this.y, this.z);
   }

   public boolean aO() {
      return this.aN();
   }

   public void i(int var1) {
   }

   public EnumFacing aP() {
      return EnumFacing.b(MathHelper.c(this.y * 4.0F / 360.0F + 0.5) & 3);
   }

   protected HoverEvent aQ() {
      NBTTagCompound var1 = new NBTTagCompound();
      String var2 = EntityList.b(this);
      var1.a("id", this.aK().toString());
      if (var2 != null) {
         var1.a("type", var2);
      }

      var1.a("name", this.e_());
      return new HoverEvent(HoverEvent.Action.d, new ChatComponentText(var1.toString()));
   }

   public boolean a(EntityPlayerMP var1) {
      return true;
   }

   public AxisAlignedBB aR() {
      return this.f;
   }

   public void a(AxisAlignedBB var1) {
      this.f = var1;
   }

   public float aS() {
      return this.K * 0.85F;
   }

   public boolean aT() {
      return this.g;
   }

   public void h(boolean var1) {
      this.g = var1;
   }

   public boolean d(int var1, ItemStack var2) {
      return false;
   }

   @Override
   public void a(IChatComponent var1) {
   }

   @Override
   public boolean a(int var1, String var2) {
      return true;
   }

   @Override
   public BlockPos c() {
      return new BlockPos(this.s, this.t + 0.5, this.u);
   }

   @Override
   public Vec3 d() {
      return new Vec3(this.s, this.t, this.u);
   }

   @Override
   public World e() {
      return this.o;
   }

   @Override
   public Entity f() {
      return this;
   }

   @Override
   public boolean u_() {
      return false;
   }

   @Override
   public void a(CommandResultStats.Type var1, int var2) {
      this.au.a(this, var1, var2);
   }

   public CommandResultStats aU() {
      return this.au;
   }

   public void o(Entity var1) {
      this.au.a(var1.aU());
   }

   public NBTTagCompound aV() {
      return null;
   }

   public void g(NBTTagCompound var1) {
   }

   public boolean a(EntityPlayer var1, Vec3 var2) {
      return false;
   }

   public boolean aW() {
      return false;
   }

   protected void a(EntityLivingBase var1, Entity var2) {
      if (var2 instanceof EntityLivingBase) {
         EnchantmentHelper.a((EntityLivingBase)var2, var1);
      }

      EnchantmentHelper.b(var1, var2);
   }
}
