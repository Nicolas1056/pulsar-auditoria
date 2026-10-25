package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentLootBonus extends Enchantment {
   protected EnchantmentLootBonus(int var1, ResourceLocation var2, int var3, EnumEnchantmentType var4) {
      super(var1, var2, var3, var4);
      if (var4 == EnumEnchantmentType.h) {
         this.c("lootBonusDigger");
      } else if (var4 == EnumEnchantmentType.i) {
         this.c("lootBonusFishing");
      } else {
         this.c("lootBonus");
      }
   }

   @Override
   public int a(int var1) {
      return 15 + (var1 - 1) * 9;
   }

   @Override
   public int b(int var1) {
      return super.a(var1) + 50;
   }

   @Override
   public int b() {
      return 3;
   }

   @Override
   public boolean a(Enchantment var1) {
      return super.a(var1) && var1.B != s.B;
   }
}
