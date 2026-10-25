package net.minecraft.potion;

import net.minecraft.util.ResourceLocation;

public class PotionHealth extends Potion {
   public PotionHealth(int var1, ResourceLocation var2, boolean var3, int var4) {
      super(var1, var2, var3, var4);
   }

   @Override
   public boolean b() {
      return true;
   }

   @Override
   public boolean a(int var1, int var2) {
      return var1 >= 1;
   }
}
