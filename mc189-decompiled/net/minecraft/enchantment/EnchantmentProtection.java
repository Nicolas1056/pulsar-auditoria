package net.minecraft.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class EnchantmentProtection extends Enchantment {
   private static final String[] E = new String[]{"all", "fire", "fall", "explosion", "projectile"};
   private static final int[] F = new int[]{1, 10, 5, 5, 3};
   private static final int[] G = new int[]{11, 8, 6, 8, 6};
   private static final int[] H = new int[]{20, 12, 10, 12, 15};
   public final int a;

   public EnchantmentProtection(int var1, ResourceLocation var2, int var3, int var4) {
      super(var1, var2, var3, EnumEnchantmentType.b);
      this.a = var4;
      if (var4 == 2) {
         this.C = EnumEnchantmentType.c;
      }
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
      return 4;
   }

   @Override
   public int a(int var1, DamageSource var2) {
      if (var2.g()) {
         return 0;
      } else {
         float var3 = (6 + var1 * var1) / 3.0F;
         if (this.a == 0) {
            return MathHelper.d(var3 * 0.75F);
         } else if (this.a == 1 && var2.o()) {
            return MathHelper.d(var3 * 1.25F);
         } else if (this.a == 2 && var2 == DamageSource.i) {
            return MathHelper.d(var3 * 2.5F);
         } else if (this.a == 3 && var2.c()) {
            return MathHelper.d(var3 * 1.5F);
         } else {
            return this.a == 4 && var2.a() ? MathHelper.d(var3 * 1.5F) : 0;
         }
      }
   }

   @Override
   public String a() {
      return "enchantment.protect." + E[this.a];
   }

   @Override
   public boolean a(Enchantment var1) {
      if (var1 instanceof EnchantmentProtection) {
         EnchantmentProtection var2 = (EnchantmentProtection)var1;
         return var2.a == this.a ? false : this.a == 2 || var2.a == 2;
      } else {
         return super.a(var1);
      }
   }

   public static int a(Entity var0, int var1) {
      int var2 = EnchantmentHelper.a(Enchantment.d.B, var0.as());
      if (var2 > 0) {
         var1 -= MathHelper.d(var1 * (var2 * 0.15F));
      }

      return var1;
   }

   public static double a(Entity var0, double var1) {
      int var3 = EnchantmentHelper.a(Enchantment.f.B, var0.as());
      if (var3 > 0) {
         var1 -= MathHelper.c(var1 * (var3 * 0.15F));
      }

      return var1;
   }
}
