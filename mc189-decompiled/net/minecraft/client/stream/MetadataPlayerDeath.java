package net.minecraft.client.stream;

import net.minecraft.entity.EntityLivingBase;

public class MetadataPlayerDeath extends Metadata {
   public MetadataPlayerDeath(EntityLivingBase var1, EntityLivingBase var2) {
      super("player_death");
      if (var1 != null) {
         this.a("player", var1.e_());
      }

      if (var2 != null) {
         this.a("killer", var2.e_());
      }
   }
}
