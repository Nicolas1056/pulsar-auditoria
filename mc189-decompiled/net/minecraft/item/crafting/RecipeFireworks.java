package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class RecipeFireworks implements IRecipe {
   private ItemStack a;

   @Override
   public boolean a(InventoryCrafting var1, World var2) {
      this.a = null;
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;
      int var7 = 0;
      int var8 = 0;

      for (int var9 = 0; var9 < var1.o_(); var9++) {
         ItemStack var10 = var1.a(var9);
         if (var10 != null) {
            if (var10.b() == Items.H) {
               var4++;
            } else if (var10.b() == Items.cc) {
               var6++;
            } else if (var10.b() == Items.aW) {
               var5++;
            } else if (var10.b() == Items.aK) {
               var3++;
            } else if (var10.b() == Items.aT) {
               var7++;
            } else if (var10.b() == Items.i) {
               var7++;
            } else if (var10.b() == Items.bL) {
               var8++;
            } else if (var10.b() == Items.G) {
               var8++;
            } else if (var10.b() == Items.bx) {
               var8++;
            } else {
               if (var10.b() != Items.bX) {
                  return false;
               }

               var8++;
            }
         }
      }

      var7 += var5 + var8;
      if (var4 > 3 || var3 > 1) {
         return false;
      } else if (var4 >= 1 && var3 == 1 && var7 == 0) {
         this.a = new ItemStack(Items.cb);
         if (var6 > 0) {
            NBTTagCompound var18 = new NBTTagCompound();
            NBTTagCompound var22 = new NBTTagCompound();
            NBTTagList var26 = new NBTTagList();

            for (int var27 = 0; var27 < var1.o_(); var27++) {
               ItemStack var29 = var1.a(var27);
               if (var29 != null && var29.b() == Items.cc && var29.n() && var29.o().b("Explosion", 10)) {
                  var26.a(var29.o().m("Explosion"));
               }
            }

            var22.a("Explosions", var26);
            var22.a("Flight", (byte)var4);
            var18.a("Fireworks", var22);
            this.a.d(var18);
         }

         return true;
      } else if (var4 == 1 && var3 == 0 && var6 == 0 && var5 > 0 && var8 <= 1) {
         this.a = new ItemStack(Items.cc);
         NBTTagCompound var17 = new NBTTagCompound();
         NBTTagCompound var21 = new NBTTagCompound();
         byte var25 = 0;
         ArrayList var12 = Lists.newArrayList();

         for (int var13 = 0; var13 < var1.o_(); var13++) {
            ItemStack var14 = var1.a(var13);
            if (var14 != null) {
               if (var14.b() == Items.aW) {
                  var12.add(ItemDye.a[var14.i() & 15]);
               } else if (var14.b() == Items.aT) {
                  var21.a("Flicker", true);
               } else if (var14.b() == Items.i) {
                  var21.a("Trail", true);
               } else if (var14.b() == Items.bL) {
                  var25 = 1;
               } else if (var14.b() == Items.G) {
                  var25 = 4;
               } else if (var14.b() == Items.bx) {
                  var25 = 2;
               } else if (var14.b() == Items.bX) {
                  var25 = 3;
               }
            }
         }

         int[] var28 = new int[var12.size()];

         for (int var30 = 0; var30 < var28.length; var30++) {
            var28[var30] = (Integer)var12.get(var30);
         }

         var21.a("Colors", var28);
         var21.a("Type", var25);
         var17.a("Explosion", var21);
         this.a.d(var17);
         return true;
      } else if (var4 == 0 && var3 == 0 && var6 == 1 && var5 > 0 && var5 == var7) {
         ArrayList var16 = Lists.newArrayList();

         for (int var19 = 0; var19 < var1.o_(); var19++) {
            ItemStack var11 = var1.a(var19);
            if (var11 != null) {
               if (var11.b() == Items.aW) {
                  var16.add(ItemDye.a[var11.i() & 15]);
               } else if (var11.b() == Items.cc) {
                  this.a = var11.k();
                  this.a.b = 1;
               }
            }
         }

         int[] var20 = new int[var16.size()];

         for (int var23 = 0; var23 < var20.length; var23++) {
            var20[var23] = (Integer)var16.get(var23);
         }

         if (this.a != null && this.a.n()) {
            NBTTagCompound var24 = this.a.o().m("Explosion");
            if (var24 == null) {
               return false;
            } else {
               var24.a("FadeColors", var20);
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Override
   public ItemStack a(InventoryCrafting var1) {
      return this.a.k();
   }

   @Override
   public int a() {
      return 10;
   }

   @Override
   public ItemStack b() {
      return this.a;
   }

   @Override
   public ItemStack[] b(InventoryCrafting var1) {
      ItemStack[] var2 = new ItemStack[var1.o_()];

      for (int var3 = 0; var3 < var2.length; var3++) {
         ItemStack var4 = var1.a(var3);
         if (var4 != null && var4.b().r()) {
            var2[var3] = new ItemStack(var4.b().q());
         }
      }

      return var2;
   }
}
