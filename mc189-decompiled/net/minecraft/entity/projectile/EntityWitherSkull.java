package net.minecraft.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityWitherSkull extends EntityFireball {
   public EntityWitherSkull(World var1) {
      super(var1);
      this.a(0.3125F, 0.3125F);
   }

   public EntityWitherSkull(World var1, EntityLivingBase var2, double var3, double var5, double var7) {
      super(var1, var2, var3, var5, var7);
      this.a(0.3125F, 0.3125F);
   }

   @Override
   protected float j() {
      return this.l() ? 0.73F : super.j();
   }

   public EntityWitherSkull(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1, var2, var4, var6, var8, var10, var12);
      this.a(0.3125F, 0.3125F);
   }

   @Override
   public boolean at() {
      return false;
   }

   @Override
   public float a(Explosion var1, World var2, BlockPos var3, IBlockState var4) {
      float var5 = super.a(var1, var2, var3, var4);
      Block var6 = var4.c();
      if (this.l() && EntityWither.a(var6)) {
         var5 = Math.min(0.8F, var5);
      }

      return var5;
   }

   @Override
   protected void a(MovingObjectPosition var1) {
      if (!this.o.D) {
         if (var1.d != null) {
            if (this.a != null) {
               if (var1.d.a(DamageSource.a(this.a), 8.0F)) {
                  if (!var1.d.ai()) {
                     this.a.h(5.0F);
                  } else {
                     this.a(this.a, var1.d);
                  }
               }
            } else {
               var1.d.a(DamageSource.l, 5.0F);
            }

            if (var1.d instanceof EntityLivingBase) {
               byte var2 = 0;
               if (this.o.aa() == EnumDifficulty.c) {
                  var2 = 10;
               } else if (this.o.aa() == EnumDifficulty.d) {
                  var2 = 40;
               }

               if (var2 > 0) {
                  ((EntityLivingBase)var1.d).c(new PotionEffect(Potion.v.H, 20 * var2, 1));
               }
            }
         }

         this.o.a(this, this.s, this.t, this.u, 1.0F, false, this.o.Q().b("mobGriefing"));
         this.J();
      }
   }

   @Override
   public boolean ad() {
      return false;
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      return false;
   }

   @Override
   protected void h() {
      this.ac.a(10, Byte.valueOf((byte)0));
   }

   public boolean l() {
      return this.ac.a(10) == 1;
   }

   public void a(boolean var1) {
      this.ac.b(10, Byte.valueOf((byte)(var1 ? 1 : 0)));
   }
}
