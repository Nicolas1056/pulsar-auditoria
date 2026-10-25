package net.minecraft.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Explosion {
   private final boolean a;
   private final boolean b;
   private final Random c = new Random();
   private final World d;
   private final double e;
   private final double f;
   private final double g;
   private final Entity h;
   private final float i;
   private final List<BlockPos> j = Lists.newArrayList();
   private final Map<EntityPlayer, Vec3> k = Maps.newHashMap();

   public Explosion(World var1, Entity var2, double var3, double var5, double var7, float var9, List<BlockPos> var10) {
      this(var1, var2, var3, var5, var7, var9, false, true, var10);
   }

   public Explosion(World var1, Entity var2, double var3, double var5, double var7, float var9, boolean var10, boolean var11, List<BlockPos> var12) {
      this(var1, var2, var3, var5, var7, var9, var10, var11);
      this.j.addAll(var12);
   }

   public Explosion(World var1, Entity var2, double var3, double var5, double var7, float var9, boolean var10, boolean var11) {
      this.d = var1;
      this.h = var2;
      this.i = var9;
      this.e = var3;
      this.f = var5;
      this.g = var7;
      this.a = var10;
      this.b = var11;
   }

   public void a() {
      HashSet var1 = Sets.newHashSet();
      byte var2 = 16;

      for (int var3 = 0; var3 < 16; var3++) {
         for (int var4 = 0; var4 < 16; var4++) {
            for (int var5 = 0; var5 < 16; var5++) {
               if (var3 == 0 || var3 == 15 || var4 == 0 || var4 == 15 || var5 == 0 || var5 == 15) {
                  double var6 = var3 / 15.0F * 2.0F - 1.0F;
                  double var8 = var4 / 15.0F * 2.0F - 1.0F;
                  double var10 = var5 / 15.0F * 2.0F - 1.0F;
                  double var12 = Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
                  var6 /= var12;
                  var8 /= var12;
                  var10 /= var12;
                  float var14 = this.i * (0.7F + this.d.s.nextFloat() * 0.6F);
                  double var15 = this.e;
                  double var17 = this.f;
                  double var19 = this.g;

                  for (float var21 = 0.3F; var14 > 0.0F; var14 -= 0.22500001F) {
                     BlockPos var22 = new BlockPos(var15, var17, var19);
                     IBlockState var23 = this.d.p(var22);
                     if (var23.c().t() != Material.a) {
                        float var24 = this.h != null ? this.h.a(this, this.d, var22, var23) : var23.c().a(null);
                        var14 -= (var24 + 0.3F) * 0.3F;
                     }

                     if (var14 > 0.0F && (this.h == null || this.h.a(this, this.d, var22, var23, var14))) {
                        var1.add(var22);
                     }

                     var15 += var6 * 0.3F;
                     var17 += var8 * 0.3F;
                     var19 += var10 * 0.3F;
                  }
               }
            }
         }
      }

      this.j.addAll(var1);
      float var30 = this.i * 2.0F;
      int var31 = MathHelper.c(this.e - var30 - 1.0);
      int var32 = MathHelper.c(this.e + var30 + 1.0);
      int var34 = MathHelper.c(this.f - var30 - 1.0);
      int var7 = MathHelper.c(this.f + var30 + 1.0);
      int var36 = MathHelper.c(this.g - var30 - 1.0);
      int var9 = MathHelper.c(this.g + var30 + 1.0);
      List var38 = this.d.b(this.h, new AxisAlignedBB(var31, var34, var36, var32, var7, var9));
      Vec3 var11 = new Vec3(this.e, this.f, this.g);

      for (int var39 = 0; var39 < var38.size(); var39++) {
         Entity var13 = (Entity)var38.get(var39);
         if (!var13.aW()) {
            double var40 = var13.f(this.e, this.f, this.g) / var30;
            if (var40 <= 1.0) {
               double var16 = var13.s - this.e;
               double var18 = var13.t + var13.aS() - this.f;
               double var20 = var13.u - this.g;
               double var44 = MathHelper.a(var16 * var16 + var18 * var18 + var20 * var20);
               if (var44 != 0.0) {
                  var16 /= var44;
                  var18 /= var44;
                  var20 /= var44;
                  double var45 = this.d.a(var11, var13.aR());
                  double var26 = (1.0 - var40) * var45;
                  var13.a(DamageSource.a(this), (float)((int)((var26 * var26 + var26) / 2.0 * 8.0 * var30 + 1.0)));
                  double var28 = EnchantmentProtection.a(var13, var26);
                  var13.v += var16 * var28;
                  var13.w += var18 * var28;
                  var13.x += var20 * var28;
                  if (var13 instanceof EntityPlayer && !((EntityPlayer)var13).bA.a) {
                     this.k.put((EntityPlayer)var13, new Vec3(var16 * var26, var18 * var26, var20 * var26));
                  }
               }
            }
         }
      }
   }

   public void a(boolean var1) {
      this.d.a(this.e, this.f, this.g, "random.explode", 4.0F, (1.0F + (this.d.s.nextFloat() - this.d.s.nextFloat()) * 0.2F) * 0.7F);
      if (!(this.i < 2.0F) && this.b) {
         this.d.a(EnumParticleTypes.c, this.e, this.f, this.g, 1.0, 0.0, 0.0);
      } else {
         this.d.a(EnumParticleTypes.b, this.e, this.f, this.g, 1.0, 0.0, 0.0);
      }

      if (this.b) {
         for (BlockPos var3 : this.j) {
            Block var4 = this.d.p(var3).c();
            if (var1) {
               double var5 = var3.n() + this.d.s.nextFloat();
               double var7 = var3.o() + this.d.s.nextFloat();
               double var9 = var3.p() + this.d.s.nextFloat();
               double var11 = var5 - this.e;
               double var13 = var7 - this.f;
               double var15 = var9 - this.g;
               double var17 = MathHelper.a(var11 * var11 + var13 * var13 + var15 * var15);
               var11 /= var17;
               var13 /= var17;
               var15 /= var17;
               double var19 = 0.5 / (var17 / this.i + 0.1);
               var19 *= this.d.s.nextFloat() * this.d.s.nextFloat() + 0.3F;
               var11 *= var19;
               var13 *= var19;
               var15 *= var19;
               this.d.a(EnumParticleTypes.a, (var5 + this.e * 1.0) / 2.0, (var7 + this.f * 1.0) / 2.0, (var9 + this.g * 1.0) / 2.0, var11, var13, var15);
               this.d.a(EnumParticleTypes.l, var5, var7, var9, var11, var13, var15);
            }

            if (var4.t() != Material.a) {
               if (var4.a(this)) {
                  var4.a(this.d, var3, this.d.p(var3), 1.0F / this.i, 0);
               }

               this.d.a(var3, Blocks.a.Q(), 3);
               var4.a(this.d, var3, this);
            }
         }
      }

      if (this.a) {
         for (BlockPos var22 : this.j) {
            if (this.d.p(var22).c().t() == Material.a && this.d.p(var22.b()).c().o() && this.c.nextInt(3) == 0) {
               this.d.a(var22, Blocks.ab.Q());
            }
         }
      }
   }

   public Map<EntityPlayer, Vec3> b() {
      return this.k;
   }

   public EntityLivingBase c() {
      if (this.h == null) {
         return null;
      } else if (this.h instanceof EntityTNTPrimed) {
         return ((EntityTNTPrimed)this.h).j();
      } else {
         return this.h instanceof EntityLivingBase ? (EntityLivingBase)this.h : null;
      }
   }

   public void d() {
      this.j.clear();
   }

   public List<BlockPos> e() {
      return this.j;
   }
}
