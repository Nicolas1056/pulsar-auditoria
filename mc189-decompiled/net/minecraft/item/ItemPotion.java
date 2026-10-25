package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemPotion extends Item {
   private Map<Integer, List<PotionEffect>> a = Maps.newHashMap();
   private static final Map<List<PotionEffect>, Integer> b = Maps.newLinkedHashMap();

   public ItemPotion() {
      this.c(1);
      this.a(true);
      this.d(0);
      this.a(CreativeTabs.k);
   }

   public List<PotionEffect> h(ItemStack var1) {
      if (var1.n() && var1.o().b("CustomPotionEffects", 9)) {
         ArrayList var7 = Lists.newArrayList();
         NBTTagList var3 = var1.o().c("CustomPotionEffects", 10);

         for (int var4 = 0; var4 < var3.c(); var4++) {
            NBTTagCompound var5 = var3.b(var4);
            PotionEffect var6 = PotionEffect.b(var5);
            if (var6 != null) {
               var7.add(var6);
            }
         }

         return var7;
      } else {
         List var2 = this.a.get(var1.i());
         if (var2 == null) {
            var2 = PotionHelper.b(var1.i(), false);
            this.a.put(var1.i(), var2);
         }

         return var2;
      }
   }

   public List<PotionEffect> e(int var1) {
      List var2 = this.a.get(var1);
      if (var2 == null) {
         var2 = PotionHelper.b(var1, false);
         this.a.put(var1, var2);
      }

      return var2;
   }

   @Override
   public ItemStack b(ItemStack var1, World var2, EntityPlayer var3) {
      if (!var3.bA.d) {
         var1.b--;
      }

      if (!var2.D) {
         List var4 = this.h(var1);
         if (var4 != null) {
            for (PotionEffect var6 : var4) {
               var3.c(new PotionEffect(var6));
            }
         }
      }

      var3.b(StatList.ad[Item.b(this)]);
      if (!var3.bA.d) {
         if (var1.b <= 0) {
            return new ItemStack(Items.bA);
         }

         var3.bi.a(new ItemStack(Items.bA));
      }

      return var1;
   }

   @Override
   public int d(ItemStack var1) {
      return 32;
   }

   @Override
   public EnumAction e(ItemStack var1) {
      return EnumAction.c;
   }

   @Override
   public ItemStack a(ItemStack var1, World var2, EntityPlayer var3) {
      if (f(var1.i())) {
         if (!var3.bA.d) {
            var1.b--;
         }

         var2.a((Entity)var3, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
         if (!var2.D) {
            var2.d(new EntityPotion(var2, var3, var1));
         }

         var3.b(StatList.ad[Item.b(this)]);
         return var1;
      } else {
         var3.a(var1, this.d(var1));
         return var1;
      }
   }

   public static boolean f(int var0) {
      return (var0 & 16384) != 0;
   }

   public int g(int var1) {
      return PotionHelper.a(var1, false);
   }

   @Override
   public int a(ItemStack var1, int var2) {
      return var2 > 0 ? 16777215 : this.g(var1.i());
   }

   public boolean h(int var1) {
      List var2 = this.e(var1);
      if (var2 != null && !var2.isEmpty()) {
         for (PotionEffect var4 : var2) {
            if (Potion.a[var4.a()].b()) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @Override
   public String a(ItemStack var1) {
      if (var1.i() == 0) {
         return StatCollector.a("item.emptyPotion.name").trim();
      } else {
         String var2 = "";
         if (f(var1.i())) {
            var2 = StatCollector.a("potion.prefix.grenade").trim() + " ";
         }

         List var3 = Items.bz.h(var1);
         if (var3 != null && !var3.isEmpty()) {
            String var5 = ((PotionEffect)var3.get(0)).g();
            var5 = var5 + ".postfix";
            return var2 + StatCollector.a(var5).trim();
         } else {
            String var4 = PotionHelper.c(var1.i());
            return StatCollector.a(var4).trim() + " " + super.a(var1);
         }
      }
   }

   @Override
   public void a(ItemStack var1, EntityPlayer var2, List<String> var3, boolean var4) {
      if (var1.i() != 0) {
         List var5 = Items.bz.h(var1);
         HashMultimap var6 = HashMultimap.create();
         if (var5 != null && !var5.isEmpty()) {
            for (PotionEffect var8 : var5) {
               Object var9 = StatCollector.a(var8.g()).trim();
               Potion var10 = Potion.a[var8.a()];
               Map var11 = var10.l();
               if (var11 != null && var11.size() > 0) {
                  for (Entry var13 : var11.entrySet()) {
                     AttributeModifier var14 = (AttributeModifier)var13.getValue();
                     AttributeModifier var15 = new AttributeModifier(var14.b(), var10.a(var8.c(), var14), var14.c());
                     var6.put(((IAttribute)var13.getKey()).a(), var15);
                  }
               }

               if (var8.c() > 0) {
                  var9 = var9 + " " + StatCollector.a("potion.potency." + var8.c()).trim();
               }

               if (var8.b() > 20) {
                  var9 = var9 + " (" + Potion.a(var8) + ")";
               }

               if (var10.g()) {
                  var3.add(EnumChatFormatting.m + var9);
               } else {
                  var3.add(EnumChatFormatting.h + var9);
               }
            }
         } else {
            Object var7 = StatCollector.a("potion.empty").trim();
            var3.add(EnumChatFormatting.h + var7);
         }

         if (!var6.isEmpty()) {
            var3.add("");
            var3.add(EnumChatFormatting.f + StatCollector.a("potion.effects.whenDrank"));

            for (Entry var18 : var6.entries()) {
               AttributeModifier var19 = (AttributeModifier)var18.getValue();
               double var20 = var19.d();
               double var21;
               if (var19.c() != 1 && var19.c() != 2) {
                  var21 = var19.d();
               } else {
                  var21 = var19.d() * 100.0;
               }

               if (var20 > 0.0) {
                  var3.add(
                     EnumChatFormatting.j
                        + StatCollector.a(
                           "attribute.modifier.plus." + var19.c(), ItemStack.a.format(var21), StatCollector.a("attribute.name." + (String)var18.getKey())
                        )
                  );
               } else if (var20 < 0.0) {
                  var21 *= -1.0;
                  var3.add(
                     EnumChatFormatting.m
                        + StatCollector.a(
                           "attribute.modifier.take." + var19.c(), ItemStack.a.format(var21), StatCollector.a("attribute.name." + (String)var18.getKey())
                        )
                  );
               }
            }
         }
      }
   }

   @Override
   public boolean f(ItemStack var1) {
      List var2 = this.h(var1);
      return var2 != null && !var2.isEmpty();
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      super.a(var1, var2, var3);
      if (b.isEmpty()) {
         for (int var4 = 0; var4 <= 15; var4++) {
            for (int var5 = 0; var5 <= 1; var5++) {
               int var6;
               if (var5 == 0) {
                  var6 = var4 | 8192;
               } else {
                  var6 = var4 | 16384;
               }

               for (int var7 = 0; var7 <= 2; var7++) {
                  int var8 = var6;
                  if (var7 != 0) {
                     if (var7 == 1) {
                        var8 = var6 | 32;
                     } else if (var7 == 2) {
                        var8 = var6 | 64;
                     }
                  }

                  List var9 = PotionHelper.b(var8, false);
                  if (var9 != null && !var9.isEmpty()) {
                     b.put(var9, var8);
                  }
               }
            }
         }
      }

      for (int var11 : b.values()) {
         var3.add(new ItemStack(var1, 1, var11));
      }
   }
}
