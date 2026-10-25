package net.minecraft.entity.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityMinecartEmpty extends EntityMinecart {
   public EntityMinecartEmpty(World var1) {
      super(var1);
   }

   public EntityMinecartEmpty(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   @Override
   public boolean e(EntityPlayer var1) {
      if (this.l != null && this.l instanceof EntityPlayer && this.l != var1) {
         return true;
      } else if (this.l != null && this.l != var1) {
         return false;
      } else {
         if (!this.o.D) {
            var1.a(this);
         }

         return true;
      }
   }

   @Override
   public void a(int var1, int var2, int var3, boolean var4) {
      if (var4) {
         if (this.l != null) {
            this.l.a(null);
         }

         if (this.q() == 0) {
            this.k(-this.r());
            this.j(10);
            this.a(50.0F);
            this.ac();
         }
      }
   }

   @Override
   public EntityMinecart.EnumMinecartType s() {
      return EntityMinecart.EnumMinecartType.a;
   }
}
