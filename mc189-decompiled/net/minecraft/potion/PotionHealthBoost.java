package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.util.ResourceLocation;

public class PotionHealthBoost extends Potion {
   public PotionHealthBoost(int var1, ResourceLocation var2, boolean var3, int var4) {
      super(var1, var2, var3, var4);
   }

   @Override
   public void a(EntityLivingBase var1, BaseAttributeMap var2, int var3) {
      super.a(var1, var2, var3);
      if (var1.bn() > var1.bu()) {
         var1.i(var1.bu());
      }
   }
}
