package net.minecraft.entity.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityExpBottle extends EntityThrowable {
   public EntityExpBottle(World var1) {
      super(var1);
   }

   public EntityExpBottle(World var1, EntityLivingBase var2) {
      super(var1, var2);
   }

   public EntityExpBottle(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   @Override
   protected float m() {
      return 0.07F;
   }

   @Override
   protected float j() {
      return 0.7F;
   }

   @Override
   protected float l() {
      return -20.0F;
   }

   @Override
   protected void a(MovingObjectPosition var1) {
      if (!this.o.D) {
         this.o.b(2002, new BlockPos(this), 0);
         int var2 = 3 + this.o.s.nextInt(5) + this.o.s.nextInt(5);

         while (var2 > 0) {
            int var3 = EntityXPOrb.a(var2);
            var2 -= var3;
            this.o.d(new EntityXPOrb(this.o, this.s, this.t, this.u, var3));
         }

         this.J();
      }
   }
}
