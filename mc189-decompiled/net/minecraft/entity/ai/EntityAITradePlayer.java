package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class EntityAITradePlayer extends EntityAIBase {
   private EntityVillager a;

   public EntityAITradePlayer(EntityVillager var1) {
      this.a = var1;
      this.a(5);
   }

   @Override
   public boolean a() {
      if (!this.a.ai()) {
         return false;
      } else if (this.a.V()) {
         return false;
      } else if (!this.a.C) {
         return false;
      } else if (this.a.G) {
         return false;
      } else {
         EntityPlayer var1 = this.a.v_();
         if (var1 == null) {
            return false;
         } else {
            return this.a.h(var1) > 16.0 ? false : var1.bk instanceof Container;
         }
      }
   }

   @Override
   public void c() {
      this.a.s().n();
   }

   @Override
   public void d() {
      this.a.a_(null);
   }
}
