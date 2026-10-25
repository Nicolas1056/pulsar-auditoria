package net.minecraft.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDamage extends Enchantment {
   private static final String[] E = new String[]{"all", "undead", "arthropods"};
   private static final int[] F = new int[]{1, 5, 5};
   private static final int[] G = new int[]{11, 8, 8};
   private static final int[] H = new int[]{20, 20, 20};
   public final int a;

   public EnchantmentDamage(int var1, ResourceLocation var2, int var3, int var4) {
      super(var1, var2, var3, EnumEnchantmentType.g);
      this.a = var4;
   }

   @Override
   public int a(int var1) {
      return F[this.a] + (var1 - 1) * G[this.a];
   }

   @Override
   public int b(int var1) {
      return this.a(var1) + H[this.a];
   }

   @Override
   public int b() {
      return 5;
   }

   @Override
   public float a(int var1, EnumCreatureAttribute var2) {
      if (this.a == 0) {
         return var1 * 1.25F;
      } else if (this.a == 1 && var2 == EnumCreatureAttribute.b) {
         return var1 * 2.5F;
      } else {
         return this.a == 2 && var2 == EnumCreatureAttribute.c ? var1 * 2.5F : 0.0F;
      }
   }

   @Override
   public String a() {
      return "enchantment.damage." + E[this.a];
   }

   @Override
   public boolean a(Enchantment var1) {
      return !(var1 instanceof EnchantmentDamage);
   }

   @Override
   public boolean a(ItemStack var1) {
      return var1.b() instanceof ItemAxe ? true : super.a(var1);
   }

   @Override
   public void a(EntityLivingBase var1, Entity var2, int var3) {
      if (var2 instanceof EntityLivingBase) {
         EntityLivingBase var4 = (EntityLivingBase)var2;
         if (this.a == 2 && var4.bz() == EnumCreatureAttribute.c) {
            int var5 = 20 + var1.bc().nextInt(10 * var3);
            var4.c(new PotionEffect(Potion.d.H, var5, 3));
         }
      }
   }
}
