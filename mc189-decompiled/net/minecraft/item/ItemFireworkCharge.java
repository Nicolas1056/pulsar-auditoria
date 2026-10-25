package net.minecraft.item;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.StatCollector;

public class ItemFireworkCharge extends Item {
   @Override
   public int a(ItemStack var1, int var2) {
      if (var2 != 1) {
         return super.a(var1, var2);
      } else {
         NBTBase var3 = a(var1, "Colors");
         if (!(var3 instanceof NBTTagIntArray)) {
            return 9079434;
         } else {
            NBTTagIntArray var4 = (NBTTagIntArray)var3;
            int[] var5 = var4.c();
            if (var5.length == 1) {
               return var5[0];
            } else {
               int var6 = 0;
               int var7 = 0;
               int var8 = 0;

               for (int var12 : var5) {
                  var6 += (var12 & 0xFF0000) >> 16;
                  var7 += (var12 & 0xFF00) >> 8;
                  var8 += (var12 & 0xFF) >> 0;
               }

               var6 /= var5.length;
               var7 /= var5.length;
               var8 /= var5.length;
               return var6 << 16 | var7 << 8 | var8;
            }
         }
      }
   }

   public static NBTBase a(ItemStack var0, String var1) {
      if (var0.n()) {
         NBTTagCompound var2 = var0.o().m("Explosion");
         if (var2 != null) {
            return var2.a(var1);
         }
      }

      return null;
   }

   @Override
   public void a(ItemStack var1, EntityPlayer var2, List<String> var3, boolean var4) {
      if (var1.n()) {
         NBTTagCompound var5 = var1.o().m("Explosion");
         if (var5 != null) {
            a(var5, var3);
         }
      }
   }

   public static void a(NBTTagCompound var0, List<String> var1) {
      byte var2 = var0.d("Type");
      if (var2 >= 0 && var2 <= 4) {
         var1.add(StatCollector.a("item.fireworksCharge.type." + var2).trim());
      } else {
         var1.add(StatCollector.a("item.fireworksCharge.type").trim());
      }

      int[] var3 = var0.l("Colors");
      if (var3.length > 0) {
         boolean var4 = true;
         String var5 = "";

         for (int var9 : var3) {
            if (!var4) {
               var5 = var5 + ", ";
            }

            var4 = false;
            boolean var10 = false;

            for (int var11 = 0; var11 < ItemDye.a.length; var11++) {
               if (var9 == ItemDye.a[var11]) {
                  var10 = true;
                  var5 = var5 + StatCollector.a("item.fireworksCharge." + EnumDyeColor.a(var11).d());
                  break;
               }
            }

            if (!var10) {
               var5 = var5 + StatCollector.a("item.fireworksCharge.customColor");
            }
         }

         var1.add(var5);
      }

      int[] var13 = var0.l("FadeColors");
      if (var13.length > 0) {
         boolean var14 = true;
         String var16 = StatCollector.a("item.fireworksCharge.fadeTo") + " ";

         for (int var21 : var13) {
            if (!var14) {
               var16 = var16 + ", ";
            }

            var14 = false;
            boolean var22 = false;

            for (int var12 = 0; var12 < 16; var12++) {
               if (var21 == ItemDye.a[var12]) {
                  var22 = true;
                  var16 = var16 + StatCollector.a("item.fireworksCharge." + EnumDyeColor.a(var12).d());
                  break;
               }
            }

            if (!var22) {
               var16 = var16 + StatCollector.a("item.fireworksCharge.customColor");
            }
         }

         var1.add(var16);
      }

      boolean var15 = var0.n("Trail");
      if (var15) {
         var1.add(StatCollector.a("item.fireworksCharge.trail"));
      }

      boolean var17 = var0.n("Flicker");
      if (var17) {
         var1.add(StatCollector.a("item.fireworksCharge.flicker"));
      }
   }
}
