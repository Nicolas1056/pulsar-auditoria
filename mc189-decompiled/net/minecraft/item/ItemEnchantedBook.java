package net.minecraft.item;

import java.util.List;
import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.WeightedRandomChestContent;

public class ItemEnchantedBook extends Item {
   @Override
   public boolean f(ItemStack var1) {
      return true;
   }

   @Override
   public boolean f_(ItemStack var1) {
      return false;
   }

   @Override
   public EnumRarity g(ItemStack var1) {
      return this.h(var1).c() > 0 ? EnumRarity.b : super.g(var1);
   }

   public NBTTagList h(ItemStack var1) {
      NBTTagCompound var2 = var1.o();
      return var2 != null && var2.b("StoredEnchantments", 9) ? (NBTTagList)var2.a("StoredEnchantments") : new NBTTagList();
   }

   @Override
   public void a(ItemStack var1, EntityPlayer var2, List<String> var3, boolean var4) {
      super.a(var1, var2, var3, var4);
      NBTTagList var5 = this.h(var1);
      if (var5 != null) {
         for (int var6 = 0; var6 < var5.c(); var6++) {
            short var7 = var5.b(var6).e("id");
            short var8 = var5.b(var6).e("lvl");
            if (Enchantment.c(var7) != null) {
               var3.add(Enchantment.c(var7).d(var8));
            }
         }
      }
   }

   public void a(ItemStack var1, EnchantmentData var2) {
      NBTTagList var3 = this.h(var1);
      boolean var4 = true;

      for (int var5 = 0; var5 < var3.c(); var5++) {
         NBTTagCompound var6 = var3.b(var5);
         if (var6.e("id") == var2.b.B) {
            if (var6.e("lvl") < var2.c) {
               var6.a("lvl", (short)var2.c);
            }

            var4 = false;
            break;
         }
      }

      if (var4) {
         NBTTagCompound var7 = new NBTTagCompound();
         var7.a("id", (short)var2.b.B);
         var7.a("lvl", (short)var2.c);
         var3.a(var7);
      }

      if (!var1.n()) {
         var1.d(new NBTTagCompound());
      }

      var1.o().a("StoredEnchantments", var3);
   }

   public ItemStack a(EnchantmentData var1) {
      ItemStack var2 = new ItemStack(this);
      this.a(var2, var1);
      return var2;
   }

   public void a(Enchantment var1, List<ItemStack> var2) {
      for (int var3 = var1.e(); var3 <= var1.b(); var3++) {
         var2.add(this.a(new EnchantmentData(var1, var3)));
      }
   }

   public WeightedRandomChestContent b(Random var1) {
      return this.a(var1, 1, 1, 1);
   }

   public WeightedRandomChestContent a(Random var1, int var2, int var3, int var4) {
      ItemStack var5 = new ItemStack(Items.aL, 1, 0);
      EnchantmentHelper.a(var1, var5, 30);
      return new WeightedRandomChestContent(var5, var2, var3, var4);
   }
}
