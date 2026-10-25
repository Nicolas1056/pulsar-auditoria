package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAILookAtTradePlayer extends EntityAIWatchClosest {
   private final EntityVillager e;

   public EntityAILookAtTradePlayer(EntityVillager var1) {
      super(var1, EntityPlayer.class, 8.0F);
      this.e = var1;
   }

   @Override
   public boolean a() {
      if (this.e.co()) {
         this.b = this.e.v_();
         return true;
      } else {
         return false;
      }
   }
}
