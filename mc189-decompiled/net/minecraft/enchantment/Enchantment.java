package net.minecraft.enchantment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public abstract class Enchantment {
   private static final Enchantment[] a = new Enchantment[256];
   public static final Enchantment[] b;
   private static final Map<ResourceLocation, Enchantment> E = Maps.newHashMap();
   public static final Enchantment c = new EnchantmentProtection(0, new ResourceLocation("protection"), 10, 0);
   public static final Enchantment d = new EnchantmentProtection(1, new ResourceLocation("fire_protection"), 5, 1);
   public static final Enchantment e = new EnchantmentProtection(2, new ResourceLocation("feather_falling"), 5, 2);
   public static final Enchantment f = new EnchantmentProtection(3, new ResourceLocation("blast_protection"), 2, 3);
   public static final Enchantment g = new EnchantmentProtection(4, new ResourceLocation("projectile_protection"), 5, 4);
   public static final Enchantment h = new EnchantmentOxygen(5, new ResourceLocation("respiration"), 2);
   public static final Enchantment i = new EnchantmentWaterWorker(6, new ResourceLocation("aqua_affinity"), 2);
   public static final Enchantment j = new EnchantmentThorns(7, new ResourceLocation("thorns"), 1);
   public static final Enchantment k = new EnchantmentWaterWalker(8, new ResourceLocation("depth_strider"), 2);
   public static final Enchantment l = new EnchantmentDamage(16, new ResourceLocation("sharpness"), 10, 0);
   public static final Enchantment m = new EnchantmentDamage(17, new ResourceLocation("smite"), 5, 1);
   public static final Enchantment n = new EnchantmentDamage(18, new ResourceLocation("bane_of_arthropods"), 5, 2);
   public static final Enchantment o = new EnchantmentKnockback(19, new ResourceLocation("knockback"), 5);
   public static final Enchantment p = new EnchantmentFireAspect(20, new ResourceLocation("fire_aspect"), 2);
   public static final Enchantment q = new EnchantmentLootBonus(21, new ResourceLocation("looting"), 2, EnumEnchantmentType.g);
   public static final Enchantment r = new EnchantmentDigging(32, new ResourceLocation("efficiency"), 10);
   public static final Enchantment s = new EnchantmentUntouching(33, new ResourceLocation("silk_touch"), 1);
   public static final Enchantment t = new EnchantmentDurability(34, new ResourceLocation("unbreaking"), 5);
   public static final Enchantment u = new EnchantmentLootBonus(35, new ResourceLocation("fortune"), 2, EnumEnchantmentType.h);
   public static final Enchantment v = new EnchantmentArrowDamage(48, new ResourceLocation("power"), 10);
   public static final Enchantment w = new EnchantmentArrowKnockback(49, new ResourceLocation("punch"), 2);
   public static final Enchantment x = new EnchantmentArrowFire(50, new ResourceLocation("flame"), 2);
   public static final Enchantment y = new EnchantmentArrowInfinite(51, new ResourceLocation("infinity"), 1);
   public static final Enchantment z = new EnchantmentLootBonus(61, new ResourceLocation("luck_of_the_sea"), 2, EnumEnchantmentType.i);
   public static final Enchantment A = new EnchantmentFishingSpeed(62, new ResourceLocation("lure"), 2, EnumEnchantmentType.i);
   public final int B;
   private final int F;
   public EnumEnchantmentType C;
   protected String D;

   public static Enchantment c(int var0) {
      return var0 >= 0 && var0 < a.length ? a[var0] : null;
   }

   protected Enchantment(int var1, ResourceLocation var2, int var3, EnumEnchantmentType var4) {
      this.B = var1;
      this.F = var3;
      this.C = var4;
      if (a[var1] != null) {
         throw new IllegalArgumentException("Duplicate enchantment id!");
      } else {
         a[var1] = this;
         E.put(var2, this);
      }
   }

   public static Enchantment b(String var0) {
      return E.get(new ResourceLocation(var0));
   }

   public static Set<ResourceLocation> c() {
      return E.keySet();
   }

   public int d() {
      return this.F;
   }

   public int e() {
      return 1;
   }

   public int b() {
      return 1;
   }

   public int a(int var1) {
      return 1 + var1 * 10;
   }

   public int b(int var1) {
      return this.a(var1) + 5;
   }

   public int a(int var1, DamageSource var2) {
      return 0;
   }

   public float a(int var1, EnumCreatureAttribute var2) {
      return 0.0F;
   }

   public boolean a(Enchantment var1) {
      return this != var1;
   }

   public Enchantment c(String var1) {
      this.D = var1;
      return this;
   }

   public String a() {
      return "enchantment." + this.D;
   }

   public String d(int var1) {
      String var2 = StatCollector.a(this.a());
      return var2 + " " + StatCollector.a("enchantment.level." + var1);
   }

   public boolean a(ItemStack var1) {
      return this.C.a(var1.b());
   }

   public void a(EntityLivingBase var1, Entity var2, int var3) {
   }

   public void b(EntityLivingBase var1, Entity var2, int var3) {
   }

   static {
      ArrayList var0 = Lists.newArrayList();

      for (Enchantment var4 : a) {
         if (var4 != null) {
            var0.add(var4);
         }
      }

      b = var0.toArray(new Enchantment[var0.size()]);
   }
}
