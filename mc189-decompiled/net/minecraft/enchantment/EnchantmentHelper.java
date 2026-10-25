package net.minecraft.enchantment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandom;

public class EnchantmentHelper {
   private static final Random a = new Random();
   private static final EnchantmentHelper.ModifierDamage b = new EnchantmentHelper.ModifierDamage();
   private static final EnchantmentHelper.ModifierLiving c = new EnchantmentHelper.ModifierLiving();
   private static final EnchantmentHelper.HurtIterator d = new EnchantmentHelper.HurtIterator();
   private static final EnchantmentHelper.DamageIterator e = new EnchantmentHelper.DamageIterator();

   public static int a(int var0, ItemStack var1) {
      if (var1 == null) {
         return 0;
      } else {
         NBTTagList var2 = var1.p();
         if (var2 == null) {
            return 0;
         } else {
            for (int var3 = 0; var3 < var2.c(); var3++) {
               short var4 = var2.b(var3).e("id");
               short var5 = var2.b(var3).e("lvl");
               if (var4 == var0) {
                  return var5;
               }
            }

            return 0;
         }
      }
   }

   public static Map<Integer, Integer> a(ItemStack var0) {
      LinkedHashMap var1 = Maps.newLinkedHashMap();
      NBTTagList var2 = var0.b() == Items.cd ? Items.cd.h(var0) : var0.p();
      if (var2 != null) {
         for (int var3 = 0; var3 < var2.c(); var3++) {
            short var4 = var2.b(var3).e("id");
            short var5 = var2.b(var3).e("lvl");
            var1.put(Integer.valueOf(var4), Integer.valueOf(var5));
         }
      }

      return var1;
   }

   public static void a(Map<Integer, Integer> var0, ItemStack var1) {
      NBTTagList var2 = new NBTTagList();

      for (int var4 : var0.keySet()) {
         Enchantment var5 = Enchantment.c(var4);
         if (var5 != null) {
            NBTTagCompound var6 = new NBTTagCompound();
            var6.a("id", (short)var4);
            var6.a("lvl", (short)((Integer)var0.get(var4)).intValue());
            var2.a(var6);
            if (var1.b() == Items.cd) {
               Items.cd.a(var1, new EnchantmentData(var5, (Integer)var0.get(var4)));
            }
         }
      }

      if (var2.c() > 0) {
         if (var1.b() != Items.cd) {
            var1.a("ench", var2);
         }
      } else if (var1.n()) {
         var1.o().o("ench");
      }
   }

   public static int a(int var0, ItemStack[] var1) {
      if (var1 == null) {
         return 0;
      } else {
         int var2 = 0;

         for (ItemStack var6 : var1) {
            int var7 = a(var0, var6);
            if (var7 > var2) {
               var2 = var7;
            }
         }

         return var2;
      }
   }

   private static void a(EnchantmentHelper.IModifier var0, ItemStack var1) {
      if (var1 != null) {
         NBTTagList var2 = var1.p();
         if (var2 != null) {
            for (int var3 = 0; var3 < var2.c(); var3++) {
               short var4 = var2.b(var3).e("id");
               short var5 = var2.b(var3).e("lvl");
               if (Enchantment.c(var4) != null) {
                  var0.a(Enchantment.c(var4), var5);
               }
            }
         }
      }
   }

   private static void a(EnchantmentHelper.IModifier var0, ItemStack[] var1) {
      for (ItemStack var5 : var1) {
         a(var0, var5);
      }
   }

   public static int a(ItemStack[] var0, DamageSource var1) {
      b.a = 0;
      b.b = var1;
      a(b, var0);
      if (b.a > 25) {
         b.a = 25;
      } else if (b.a < 0) {
         b.a = 0;
      }

      return (b.a + 1 >> 1) + a.nextInt((b.a >> 1) + 1);
   }

   public static float a(ItemStack var0, EnumCreatureAttribute var1) {
      c.a = 0.0F;
      c.b = var1;
      a(c, var0);
      return c.a;
   }

   public static void a(EntityLivingBase var0, Entity var1) {
      d.b = var1;
      d.a = var0;
      if (var0 != null) {
         a(d, var0.as());
      }

      if (var1 instanceof EntityPlayer) {
         a(d, var0.bA());
      }
   }

   public static void b(EntityLivingBase var0, Entity var1) {
      e.a = var0;
      e.b = var1;
      if (var0 != null) {
         a(e, var0.as());
      }

      if (var0 instanceof EntityPlayer) {
         a(e, var0.bA());
      }
   }

   public static int a(EntityLivingBase var0) {
      return a(Enchantment.o.B, var0.bA());
   }

   public static int b(EntityLivingBase var0) {
      return a(Enchantment.p.B, var0.bA());
   }

   public static int a(Entity var0) {
      return a(Enchantment.h.B, var0.as());
   }

   public static int b(Entity var0) {
      return a(Enchantment.k.B, var0.as());
   }

   public static int c(EntityLivingBase var0) {
      return a(Enchantment.r.B, var0.bA());
   }

   public static boolean e(EntityLivingBase var0) {
      return a(Enchantment.s.B, var0.bA()) > 0;
   }

   public static int f(EntityLivingBase var0) {
      return a(Enchantment.u.B, var0.bA());
   }

   public static int g(EntityLivingBase var0) {
      return a(Enchantment.z.B, var0.bA());
   }

   public static int h(EntityLivingBase var0) {
      return a(Enchantment.A.B, var0.bA());
   }

   public static int i(EntityLivingBase var0) {
      return a(Enchantment.q.B, var0.bA());
   }

   public static boolean j(EntityLivingBase var0) {
      return a(Enchantment.i.B, var0.as()) > 0;
   }

   public static ItemStack a(Enchantment var0, EntityLivingBase var1) {
      for (ItemStack var5 : var1.as()) {
         if (var5 != null && a(var0.B, var5) > 0) {
            return var5;
         }
      }

      return null;
   }

   public static int a(Random var0, int var1, int var2, ItemStack var3) {
      Item var4 = var3.b();
      int var5 = var4.b();
      if (var5 <= 0) {
         return 0;
      } else {
         if (var2 > 15) {
            var2 = 15;
         }

         int var6 = var0.nextInt(8) + 1 + (var2 >> 1) + var0.nextInt(var2 + 1);
         if (var1 == 0) {
            return Math.max(var6 / 3, 1);
         } else {
            return var1 == 1 ? var6 * 2 / 3 + 1 : Math.max(var6, var2 * 2);
         }
      }
   }

   public static ItemStack a(Random var0, ItemStack var1, int var2) {
      List var3 = b(var0, var1, var2);
      boolean var4 = var1.b() == Items.aL;
      if (var4) {
         var1.a(Items.cd);
      }

      if (var3 != null) {
         for (EnchantmentData var6 : var3) {
            if (var4) {
               Items.cd.a(var1, var6);
            } else {
               var1.a(var6.b, var6.c);
            }
         }
      }

      return var1;
   }

   public static List<EnchantmentData> b(Random var0, ItemStack var1, int var2) {
      Item var3 = var1.b();
      int var4 = var3.b();
      if (var4 <= 0) {
         return null;
      } else {
         var4 /= 2;
         var4 = 1 + var0.nextInt((var4 >> 1) + 1) + var0.nextInt((var4 >> 1) + 1);
         int var5 = var4 + var2;
         float var6 = (var0.nextFloat() + var0.nextFloat() - 1.0F) * 0.15F;
         int var7 = (int)(var5 * (1.0F + var6) + 0.5F);
         if (var7 < 1) {
            var7 = 1;
         }

         ArrayList var8 = null;
         Map var9 = b(var7, var1);
         if (var9 != null && !var9.isEmpty()) {
            EnchantmentData var10 = WeightedRandom.a(var0, var9.values());
            if (var10 != null) {
               var8 = Lists.newArrayList();
               var8.add(var10);

               for (int var11 = var7; var0.nextInt(50) <= var11; var11 >>= 1) {
                  Iterator var12 = var9.keySet().iterator();

                  while (var12.hasNext()) {
                     Integer var13 = (Integer)var12.next();
                     boolean var14 = true;

                     for (EnchantmentData var16 : var8) {
                        if (!var16.b.a(Enchantment.c(var13))) {
                           var14 = false;
                           break;
                        }
                     }

                     if (!var14) {
                        var12.remove();
                     }
                  }

                  if (!var9.isEmpty()) {
                     EnchantmentData var19 = WeightedRandom.a(var0, var9.values());
                     var8.add(var19);
                  }
               }
            }
         }

         return var8;
      }
   }

   public static Map<Integer, EnchantmentData> b(int var0, ItemStack var1) {
      Item var2 = var1.b();
      HashMap var3 = null;
      boolean var4 = var1.b() == Items.aL;

      for (Enchantment var8 : Enchantment.b) {
         if (var8 != null && (var8.C.a(var2) || var4)) {
            for (int var9 = var8.e(); var9 <= var8.b(); var9++) {
               if (var0 >= var8.a(var9) && var0 <= var8.b(var9)) {
                  if (var3 == null) {
                     var3 = Maps.newHashMap();
                  }

                  var3.put(var8.B, new EnchantmentData(var8, var9));
               }
            }
         }
      }

      return var3;
   }

   static final class DamageIterator implements EnchantmentHelper.IModifier {
      public EntityLivingBase a;
      public Entity b;

      private DamageIterator() {
      }

      @Override
      public void a(Enchantment var1, int var2) {
         var1.a(this.a, this.b, var2);
      }
   }

   static final class HurtIterator implements EnchantmentHelper.IModifier {
      public EntityLivingBase a;
      public Entity b;

      private HurtIterator() {
      }

      @Override
      public void a(Enchantment var1, int var2) {
         var1.b(this.a, this.b, var2);
      }
   }

   interface IModifier {
      void a(Enchantment var1, int var2);
   }

   static final class ModifierDamage implements EnchantmentHelper.IModifier {
      public int a;
      public DamageSource b;

      private ModifierDamage() {
      }

      @Override
      public void a(Enchantment var1, int var2) {
         this.a = this.a + var1.a(var2, this.b);
      }
   }

   static final class ModifierLiving implements EnchantmentHelper.IModifier {
      public float a;
      public EnumCreatureAttribute b;

      private ModifierLiving() {
      }

      @Override
      public void a(Enchantment var1, int var2) {
         this.a = this.a + var1.a(var2, this.b);
      }
   }
}
