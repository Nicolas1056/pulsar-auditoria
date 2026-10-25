package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySmallFireball extends EntityFireball {
   public EntitySmallFireball(World var1) {
      super(var1);
      this.a(0.3125F, 0.3125F);
   }

   public EntitySmallFireball(World var1, EntityLivingBase var2, double var3, double var5, double var7) {
      super(var1, var2, var3, var5, var7);
      this.a(0.3125F, 0.3125F);
   }

   public EntitySmallFireball(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1, var2, var4, var6, var8, var10, var12);
      this.a(0.3125F, 0.3125F);
   }

   @Override
   protected void a(MovingObjectPosition var1) {
      if (!this.o.D) {
         if (var1.d != null) {
            boolean var2 = var1.d.a(DamageSource.a((EntityFireball)this, this.a), 5.0F);
            if (var2) {
               this.a(this.a, var1.d);
               if (!var1.d.T()) {
                  var1.d.e(5);
               }
            }
         } else {
            boolean var4 = true;
            if (this.a != null && this.a instanceof EntityLiving) {
               var4 = this.o.Q().b("mobGriefing");
            }

            if (var4) {
               BlockPos var3 = var1.a().a(var1.b);
               if (this.o.d(var3)) {
                  this.o.a(var3, Blocks.ab.Q());
               }
            }
         }

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
}
