package net.minecraft.entity.item;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityMinecart extends Entity implements IWorldNameable {
   private boolean a;
   private String b;
   private static final int[][][] c = new int[][][]{
      {{0, 0, -1}, {0, 0, 1}},
      {{-1, 0, 0}, {1, 0, 0}},
      {{-1, -1, 0}, {1, 0, 0}},
      {{-1, 0, 0}, {1, -1, 0}},
      {{0, 0, -1}, {0, -1, 1}},
      {{0, -1, -1}, {0, 0, 1}},
      {{0, 0, 1}, {1, 0, 0}},
      {{0, 0, 1}, {-1, 0, 0}},
      {{0, 0, -1}, {-1, 0, 0}},
      {{0, 0, -1}, {1, 0, 0}}
   };
   private int d;
   private double e;
   private double f;
   private double g;
   private double h;
   private double i;
   private double ar;
   private double as;
   private double at;

   public EntityMinecart(World var1) {
      super(var1);
      this.k = true;
      this.a(0.98F, 0.7F);
   }

   public static EntityMinecart a(World var0, double var1, double var3, double var5, EntityMinecart.EnumMinecartType var7) {
      switch (var7) {
         case b:
            return new EntityMinecartChest(var0, var1, var3, var5);
         case c:
            return new EntityMinecartFurnace(var0, var1, var3, var5);
         case d:
            return new EntityMinecartTNT(var0, var1, var3, var5);
         case e:
            return new EntityMinecartMobSpawner(var0, var1, var3, var5);
         case f:
            return new EntityMinecartHopper(var0, var1, var3, var5);
         case g:
            return new EntityMinecartCommandBlock(var0, var1, var3, var5);
         default:
            return new EntityMinecartEmpty(var0, var1, var3, var5);
      }
   }

   @Override
   protected boolean s_() {
      return false;
   }

   @Override
   protected void h() {
      this.ac.a(17, new Integer(0));
      this.ac.a(18, new Integer(1));
      this.ac.a(19, new Float(0.0F));
      this.ac.a(20, new Integer(0));
      this.ac.a(21, new Integer(6));
      this.ac.a(22, Byte.valueOf((byte)0));
   }

   @Override
   public AxisAlignedBB j(Entity var1) {
      return var1.ae() ? var1.aR() : null;
   }

   @Override
   public AxisAlignedBB S() {
      return null;
   }

   @Override
   public boolean ae() {
      return true;
   }

   public EntityMinecart(World var1, double var2, double var4, double var6) {
      this(var1);
      this.b(var2, var4, var6);
      this.v = 0.0;
      this.w = 0.0;
      this.x = 0.0;
      this.p = var2;
      this.q = var4;
      this.r = var6;
   }

   @Override
   public double an() {
      return 0.0;
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.o.D || this.I) {
         return true;
      } else if (this.b(var1)) {
         return false;
      } else {
         this.k(-this.r());
         this.j(10);
         this.ac();
         this.a(this.p() + var2 * 10.0F);
         boolean var3 = var1.j() instanceof EntityPlayer && ((EntityPlayer)var1.j()).bA.d;
         if (var3 || this.p() > 40.0F) {
            if (this.l != null) {
               this.l.a(null);
            }

            if (var3 && !this.l_()) {
               this.J();
            } else {
               this.a(var1);
            }
         }

         return true;
      }
   }

   public void a(DamageSource var1) {
      this.J();
      if (this.o.Q().b("doEntityDrops")) {
         ItemStack var2 = new ItemStack(Items.az, 1);
         if (this.b != null) {
            var2.c(this.b);
         }

         this.a(var2, 0.0F);
      }
   }

   @Override
   public void ar() {
      this.k(-this.r());
      this.j(10);
      this.a(this.p() + this.p() * 10.0F);
   }

   @Override
   public boolean ad() {
      return !this.I;
   }

   @Override
   public void J() {
      super.J();
   }

   @Override
   public void t_() {
      if (this.q() > 0) {
         this.j(this.q() - 1);
      }

      if (this.p() > 0.0F) {
         this.a(this.p() - 1.0F);
      }

      if (this.t < -64.0) {
         this.O();
      }

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

                  this.c(var3);
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

      if (this.o.D) {
         if (this.d > 0) {
            double var15 = this.s + (this.e - this.s) / this.d;
            double var18 = this.t + (this.f - this.t) / this.d;
            double var19 = this.u + (this.g - this.u) / this.d;
            double var7 = MathHelper.g(this.h - this.y);
            this.y = (float)(this.y + var7 / this.d);
            this.z = (float)(this.z + (this.i - this.z) / this.d);
            this.d--;
            this.b(var15, var18, var19);
            this.b(this.y, this.z);
         } else {
            this.b(this.s, this.t, this.u);
            this.b(this.y, this.z);
         }
      } else {
         this.p = this.s;
         this.q = this.t;
         this.r = this.u;
         this.w -= 0.04F;
         int var14 = MathHelper.c(this.s);
         int var16 = MathHelper.c(this.t);
         int var17 = MathHelper.c(this.u);
         if (BlockRailBase.e(this.o, new BlockPos(var14, var16 - 1, var17))) {
            var16--;
         }

         BlockPos var4 = new BlockPos(var14, var16, var17);
         IBlockState var5 = this.o.p(var4);
         if (BlockRailBase.d(var5)) {
            this.a(var4, var5);
            if (var5.c() == Blocks.cs) {
               this.a(var14, var16, var17, var5.b(BlockRailPowered.N));
            }
         } else {
            this.n();
         }

         this.Q();
         this.z = 0.0F;
         double var6 = this.p - this.s;
         double var8 = this.r - this.u;
         if (var6 * var6 + var8 * var8 > 0.001) {
            this.y = (float)(MathHelper.b(var8, var6) * 180.0 / Math.PI);
            if (this.a) {
               this.y += 180.0F;
            }
         }

         double var10 = MathHelper.g(this.y - this.A);
         if (var10 < -170.0 || var10 >= 170.0) {
            this.y += 180.0F;
            this.a = !this.a;
         }

         this.b(this.y, this.z);

         for (Entity var13 : this.o.b(this, this.aR().b(0.2F, 0.0, 0.2F))) {
            if (var13 != this.l && var13.ae() && var13 instanceof EntityMinecart) {
               var13.i(this);
            }
         }

         if (this.l != null && this.l.I) {
            if (this.l.m == this) {
               this.l.m = null;
            }

            this.l = null;
         }

         this.W();
      }
   }

   protected double m() {
      return 0.4;
   }

   public void a(int var1, int var2, int var3, boolean var4) {
   }

   protected void n() {
      double var1 = this.m();
      this.v = MathHelper.a(this.v, -var1, var1);
      this.x = MathHelper.a(this.x, -var1, var1);
      if (this.C) {
         this.v *= 0.5;
         this.w *= 0.5;
         this.x *= 0.5;
      }

      this.d(this.v, this.w, this.x);
      if (!this.C) {
         this.v *= 0.95F;
         this.w *= 0.95F;
         this.x *= 0.95F;
      }
   }

   protected void a(BlockPos var1, IBlockState var2) {
      this.O = 0.0F;
      Vec3 var3 = this.k(this.s, this.t, this.u);
      this.t = var1.o();
      boolean var4 = false;
      boolean var5 = false;
      BlockRailBase var6 = (BlockRailBase)var2.c();
      if (var6 == Blocks.D) {
         var4 = var2.b(BlockRailPowered.N);
         var5 = !var4;
      }

      double var7 = 0.0078125;
      BlockRailBase.EnumRailDirection var9 = var2.b(var6.n());
      switch (var9) {
         case c:
            this.v -= 0.0078125;
            this.t++;
            break;
         case d:
            this.v += 0.0078125;
            this.t++;
            break;
         case e:
            this.x += 0.0078125;
            this.t++;
            break;
         case f:
            this.x -= 0.0078125;
            this.t++;
      }

      int[][] var10 = c[var9.a()];
      double var11 = var10[1][0] - var10[0][0];
      double var13 = var10[1][2] - var10[0][2];
      double var15 = Math.sqrt(var11 * var11 + var13 * var13);
      double var17 = this.v * var11 + this.x * var13;
      if (var17 < 0.0) {
         var11 = -var11;
         var13 = -var13;
      }

      double var19 = Math.sqrt(this.v * this.v + this.x * this.x);
      if (var19 > 2.0) {
         var19 = 2.0;
      }

      this.v = var19 * var11 / var15;
      this.x = var19 * var13 / var15;
      if (this.l instanceof EntityLivingBase) {
         double var21 = ((EntityLivingBase)this.l).ba;
         if (var21 > 0.0) {
            double var23 = -Math.sin(this.l.y * (float) Math.PI / 180.0F);
            double var25 = Math.cos(this.l.y * (float) Math.PI / 180.0F);
            double var27 = this.v * this.v + this.x * this.x;
            if (var27 < 0.01) {
               this.v += var23 * 0.1;
               this.x += var25 * 0.1;
               var5 = false;
            }
         }
      }

      if (var5) {
         double var48 = Math.sqrt(this.v * this.v + this.x * this.x);
         if (var48 < 0.03) {
            this.v *= 0.0;
            this.w *= 0.0;
            this.x *= 0.0;
         } else {
            this.v *= 0.5;
            this.w *= 0.0;
            this.x *= 0.5;
         }
      }

      double var49 = 0.0;
      double var51 = var1.n() + 0.5 + var10[0][0] * 0.5;
      double var52 = var1.p() + 0.5 + var10[0][2] * 0.5;
      double var53 = var1.n() + 0.5 + var10[1][0] * 0.5;
      double var29 = var1.p() + 0.5 + var10[1][2] * 0.5;
      var11 = var53 - var51;
      var13 = var29 - var52;
      if (var11 == 0.0) {
         this.s = var1.n() + 0.5;
         var49 = this.u - var1.p();
      } else if (var13 == 0.0) {
         this.u = var1.p() + 0.5;
         var49 = this.s - var1.n();
      } else {
         double var31 = this.s - var51;
         double var33 = this.u - var52;
         var49 = (var31 * var11 + var33 * var13) * 2.0;
      }

      this.s = var51 + var11 * var49;
      this.u = var52 + var13 * var49;
      this.b(this.s, this.t, this.u);
      double var54 = this.v;
      double var56 = this.x;
      if (this.l != null) {
         var54 *= 0.75;
         var56 *= 0.75;
      }

      double var35 = this.m();
      var54 = MathHelper.a(var54, -var35, var35);
      var56 = MathHelper.a(var56, -var35, var35);
      this.d(var54, 0.0, var56);
      if (var10[0][1] != 0 && MathHelper.c(this.s) - var1.n() == var10[0][0] && MathHelper.c(this.u) - var1.p() == var10[0][2]) {
         this.b(this.s, this.t + var10[0][1], this.u);
      } else if (var10[1][1] != 0 && MathHelper.c(this.s) - var1.n() == var10[1][0] && MathHelper.c(this.u) - var1.p() == var10[1][2]) {
         this.b(this.s, this.t + var10[1][1], this.u);
      }

      this.o();
      Vec3 var37 = this.k(this.s, this.t, this.u);
      if (var37 != null && var3 != null) {
         double var38 = (var3.b - var37.b) * 0.05;
         var19 = Math.sqrt(this.v * this.v + this.x * this.x);
         if (var19 > 0.0) {
            this.v = this.v / var19 * (var19 + var38);
            this.x = this.x / var19 * (var19 + var38);
         }

         this.b(this.s, var37.b, this.u);
      }

      int var58 = MathHelper.c(this.s);
      int var39 = MathHelper.c(this.u);
      if (var58 != var1.n() || var39 != var1.p()) {
         var19 = Math.sqrt(this.v * this.v + this.x * this.x);
         this.v = var19 * (var58 - var1.n());
         this.x = var19 * (var39 - var1.p());
      }

      if (var4) {
         double var40 = Math.sqrt(this.v * this.v + this.x * this.x);
         if (var40 > 0.01) {
            double var42 = 0.06;
            this.v = this.v + this.v / var40 * var42;
            this.x = this.x + this.x / var40 * var42;
         } else if (var9 == BlockRailBase.EnumRailDirection.b) {
            if (this.o.p(var1.e()).c().v()) {
               this.v = 0.02;
            } else if (this.o.p(var1.f()).c().v()) {
               this.v = -0.02;
            }
         } else if (var9 == BlockRailBase.EnumRailDirection.a) {
            if (this.o.p(var1.c()).c().v()) {
               this.x = 0.02;
            } else if (this.o.p(var1.d()).c().v()) {
               this.x = -0.02;
            }
         }
      }
   }

   protected void o() {
      if (this.l != null) {
         this.v *= 0.997F;
         this.w *= 0.0;
         this.x *= 0.997F;
      } else {
         this.v *= 0.96F;
         this.w *= 0.0;
         this.x *= 0.96F;
      }
   }

   @Override
   public void b(double var1, double var3, double var5) {
      this.s = var1;
      this.t = var3;
      this.u = var5;
      float var7 = this.J / 2.0F;
      float var8 = this.K;
      this.a(new AxisAlignedBB(var1 - var7, var3, var5 - var7, var1 + var7, var3 + var8, var5 + var7));
   }

   public Vec3 a(double var1, double var3, double var5, double var7) {
      int var9 = MathHelper.c(var1);
      int var10 = MathHelper.c(var3);
      int var11 = MathHelper.c(var5);
      if (BlockRailBase.e(this.o, new BlockPos(var9, var10 - 1, var11))) {
         var10--;
      }

      IBlockState var12 = this.o.p(new BlockPos(var9, var10, var11));
      if (BlockRailBase.d(var12)) {
         BlockRailBase.EnumRailDirection var13 = var12.b(((BlockRailBase)var12.c()).n());
         var3 = var10;
         if (var13.c()) {
            var3 = var10 + 1;
         }

         int[][] var14 = c[var13.a()];
         double var15 = var14[1][0] - var14[0][0];
         double var17 = var14[1][2] - var14[0][2];
         double var19 = Math.sqrt(var15 * var15 + var17 * var17);
         var15 /= var19;
         var17 /= var19;
         var1 += var15 * var7;
         var5 += var17 * var7;
         if (var14[0][1] != 0 && MathHelper.c(var1) - var9 == var14[0][0] && MathHelper.c(var5) - var11 == var14[0][2]) {
            var3 += var14[0][1];
         } else if (var14[1][1] != 0 && MathHelper.c(var1) - var9 == var14[1][0] && MathHelper.c(var5) - var11 == var14[1][2]) {
            var3 += var14[1][1];
         }

         return this.k(var1, var3, var5);
      } else {
         return null;
      }
   }

   public Vec3 k(double var1, double var3, double var5) {
      int var7 = MathHelper.c(var1);
      int var8 = MathHelper.c(var3);
      int var9 = MathHelper.c(var5);
      if (BlockRailBase.e(this.o, new BlockPos(var7, var8 - 1, var9))) {
         var8--;
      }

      IBlockState var10 = this.o.p(new BlockPos(var7, var8, var9));
      if (BlockRailBase.d(var10)) {
         BlockRailBase.EnumRailDirection var11 = var10.b(((BlockRailBase)var10.c()).n());
         int[][] var12 = c[var11.a()];
         double var13 = 0.0;
         double var15 = var7 + 0.5 + var12[0][0] * 0.5;
         double var17 = var8 + 0.0625 + var12[0][1] * 0.5;
         double var19 = var9 + 0.5 + var12[0][2] * 0.5;
         double var21 = var7 + 0.5 + var12[1][0] * 0.5;
         double var23 = var8 + 0.0625 + var12[1][1] * 0.5;
         double var25 = var9 + 0.5 + var12[1][2] * 0.5;
         double var27 = var21 - var15;
         double var29 = (var23 - var17) * 2.0;
         double var31 = var25 - var19;
         if (var27 == 0.0) {
            var1 = var7 + 0.5;
            var13 = var5 - var9;
         } else if (var31 == 0.0) {
            var5 = var9 + 0.5;
            var13 = var1 - var7;
         } else {
            double var33 = var1 - var15;
            double var35 = var5 - var19;
            var13 = (var33 * var27 + var35 * var31) * 2.0;
         }

         var1 = var15 + var27 * var13;
         var3 = var17 + var29 * var13;
         var5 = var19 + var31 * var13;
         if (var29 < 0.0) {
            var3++;
         }

         if (var29 > 0.0) {
            var3 += 0.5;
         }

         return new Vec3(var1, var3, var5);
      } else {
         return null;
      }
   }

   @Override
   protected void a(NBTTagCompound var1) {
      if (var1.n("CustomDisplayTile")) {
         int var2 = var1.f("DisplayData");
         if (var1.b("DisplayTile", 8)) {
            Block var3 = Block.b(var1.j("DisplayTile"));
            if (var3 == null) {
               this.a(Blocks.a.Q());
            } else {
               this.a(var3.a(var2));
            }
         } else {
            Block var4 = Block.c(var1.f("DisplayTile"));
            if (var4 == null) {
               this.a(Blocks.a.Q());
            } else {
               this.a(var4.a(var2));
            }
         }

         this.l(var1.f("DisplayOffset"));
      }

      if (var1.b("CustomName", 8) && var1.j("CustomName").length() > 0) {
         this.b = var1.j("CustomName");
      }
   }

   @Override
   protected void b(NBTTagCompound var1) {
      if (this.x()) {
         var1.a("CustomDisplayTile", true);
         IBlockState var2 = this.t();
         ResourceLocation var3 = Block.c.c(var2.c());
         var1.a("DisplayTile", var3 == null ? "" : var3.toString());
         var1.a("DisplayData", var2.c().c(var2));
         var1.a("DisplayOffset", this.v());
      }

      if (this.b != null && this.b.length() > 0) {
         var1.a("CustomName", this.b);
      }
   }

   @Override
   public void i(Entity var1) {
      if (!this.o.D) {
         if (!var1.T && !this.T) {
            if (var1 != this.l) {
               if (var1 instanceof EntityLivingBase
                  && !(var1 instanceof EntityPlayer)
                  && !(var1 instanceof EntityIronGolem)
                  && this.s() == EntityMinecart.EnumMinecartType.a
                  && this.v * this.v + this.x * this.x > 0.01
                  && this.l == null
                  && var1.m == null) {
                  var1.a(this);
               }

               double var2 = var1.s - this.s;
               double var4 = var1.u - this.u;
               double var6 = var2 * var2 + var4 * var4;
               if (var6 >= 1.0E-4F) {
                  var6 = MathHelper.a(var6);
                  var2 /= var6;
                  var4 /= var6;
                  double var8 = 1.0 / var6;
                  if (var8 > 1.0) {
                     var8 = 1.0;
                  }

                  var2 *= var8;
                  var4 *= var8;
                  var2 *= 0.1F;
                  var4 *= 0.1F;
                  var2 *= 1.0F - this.U;
                  var4 *= 1.0F - this.U;
                  var2 *= 0.5;
                  var4 *= 0.5;
                  if (var1 instanceof EntityMinecart) {
                     double var10 = var1.s - this.s;
                     double var12 = var1.u - this.u;
                     Vec3 var14 = new Vec3(var10, 0.0, var12).a();
                     Vec3 var15 = new Vec3(MathHelper.b(this.y * (float) Math.PI / 180.0F), 0.0, MathHelper.a(this.y * (float) Math.PI / 180.0F)).a();
                     double var16 = Math.abs(var14.b(var15));
                     if (var16 < 0.8F) {
                        return;
                     }

                     double var18 = var1.v + this.v;
                     double var20 = var1.x + this.x;
                     if (((EntityMinecart)var1).s() == EntityMinecart.EnumMinecartType.c && this.s() != EntityMinecart.EnumMinecartType.c) {
                        this.v *= 0.2F;
                        this.x *= 0.2F;
                        this.g(var1.v - var2, 0.0, var1.x - var4);
                        var1.v *= 0.95F;
                        var1.x *= 0.95F;
                     } else if (((EntityMinecart)var1).s() != EntityMinecart.EnumMinecartType.c && this.s() == EntityMinecart.EnumMinecartType.c) {
                        var1.v *= 0.2F;
                        var1.x *= 0.2F;
                        var1.g(this.v + var2, 0.0, this.x + var4);
                        this.v *= 0.95F;
                        this.x *= 0.95F;
                     } else {
                        var18 /= 2.0;
                        var20 /= 2.0;
                        this.v *= 0.2F;
                        this.x *= 0.2F;
                        this.g(var18 - var2, 0.0, var20 - var4);
                        var1.v *= 0.2F;
                        var1.x *= 0.2F;
                        var1.g(var18 + var2, 0.0, var20 + var4);
                     }
                  } else {
                     this.g(-var2, 0.0, -var4);
                     var1.g(var2 / 4.0, 0.0, var4 / 4.0);
                  }
               }
            }
         }
      }
   }

   @Override
   public void a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.e = var1;
      this.f = var3;
      this.g = var5;
      this.h = var7;
      this.i = var8;
      this.d = var9 + 2;
      this.v = this.ar;
      this.w = this.as;
      this.x = this.at;
   }

   @Override
   public void i(double var1, double var3, double var5) {
      this.ar = this.v = var1;
      this.as = this.w = var3;
      this.at = this.x = var5;
   }

   public void a(float var1) {
      this.ac.b(19, var1);
   }

   public float p() {
      return this.ac.d(19);
   }

   public void j(int var1) {
      this.ac.b(17, var1);
   }

   public int q() {
      return this.ac.c(17);
   }

   public void k(int var1) {
      this.ac.b(18, var1);
   }

   public int r() {
      return this.ac.c(18);
   }

   public abstract EntityMinecart.EnumMinecartType s();

   public IBlockState t() {
      return !this.x() ? this.u() : Block.d(this.H().c(20));
   }

   public IBlockState u() {
      return Blocks.a.Q();
   }

   public int v() {
      return !this.x() ? this.w() : this.H().c(21);
   }

   public int w() {
      return 6;
   }

   public void a(IBlockState var1) {
      this.H().b(20, Block.f(var1));
      this.a(true);
   }

   public void l(int var1) {
      this.H().b(21, var1);
      this.a(true);
   }

   public boolean x() {
      return this.H().a(22) == 1;
   }

   public void a(boolean var1) {
      this.H().b(22, (byte)(var1 ? 1 : 0));
   }

   @Override
   public void a(String var1) {
      this.b = var1;
   }

   @Override
   public String e_() {
      return this.b != null ? this.b : super.e_();
   }

   @Override
   public boolean l_() {
      return this.b != null;
   }

   @Override
   public String aM() {
      return this.b;
   }

   @Override
   public IChatComponent f_() {
      if (this.l_()) {
         ChatComponentText var2 = new ChatComponentText(this.b);
         var2.b().a(this.aQ());
         var2.b().a(this.aK().toString());
         return var2;
      } else {
         ChatComponentTranslation var1 = new ChatComponentTranslation(this.e_());
         var1.b().a(this.aQ());
         var1.b().a(this.aK().toString());
         return var1;
      }
   }

   public static enum EnumMinecartType {
      a(0, "MinecartRideable"),
      b(1, "MinecartChest"),
      c(2, "MinecartFurnace"),
      d(3, "MinecartTNT"),
      e(4, "MinecartSpawner"),
      f(5, "MinecartHopper"),
      g(6, "MinecartCommandBlock");

      private static final Map<Integer, EntityMinecart.EnumMinecartType> h = Maps.newHashMap();
      private final int i;
      private final String j;

      private EnumMinecartType(int var3, String var4) {
         this.i = var3;
         this.j = var4;
      }

      public int a() {
         return this.i;
      }

      public String b() {
         return this.j;
      }

      public static EntityMinecart.EnumMinecartType a(int var0) {
         EntityMinecart.EnumMinecartType var1 = h.get(var0);
         return var1 == null ? a : var1;
      }

      static {
         for (EntityMinecart.EnumMinecartType var3 : values()) {
            h.put(var3.a(), var3);
         }
      }
   }
}
