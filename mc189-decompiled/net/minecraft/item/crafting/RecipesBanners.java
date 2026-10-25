package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.world.World;

public class RecipesBanners {
   void a(CraftingManager var1) {
      for (EnumDyeColor var5 : EnumDyeColor.values()) {
         var1.a(new ItemStack(Items.cE, 1, var5.b()), "###", "###", " | ", '#', new ItemStack(Blocks.L, 1, var5.a()), '|', Items.y);
      }

      var1.a(new RecipesBanners.RecipeDuplicatePattern());
      var1.a(new RecipesBanners.RecipeAddPattern());
   }

   static class RecipeAddPattern implements IRecipe {
      private RecipeAddPattern() {
      }

      @Override
      public boolean a(InventoryCrafting var1, World var2) {
         boolean var3 = false;

         for (int var4 = 0; var4 < var1.o_(); var4++) {
            ItemStack var5 = var1.a(var4);
            if (var5 != null && var5.b() == Items.cE) {
               if (var3) {
                  return false;
               }

               if (TileEntityBanner.c(var5) >= 6) {
                  return false;
               }

               var3 = true;
            }
         }

         return !var3 ? false : this.c(var1) != null;
      }

      @Override
      public ItemStack a(InventoryCrafting var1) {
         ItemStack var2 = null;

         for (int var3 = 0; var3 < var1.o_(); var3++) {
            ItemStack var4 = var1.a(var3);
            if (var4 != null && var4.b() == Items.cE) {
               var2 = var4.k();
               var2.b = 1;
               break;
            }
         }

         TileEntityBanner.EnumBannerPattern var8 = this.c(var1);
         if (var8 != null) {
            int var9 = 0;

            for (int var5 = 0; var5 < var1.o_(); var5++) {
               ItemStack var6 = var1.a(var5);
               if (var6 != null && var6.b() == Items.aW) {
                  var9 = var6.i();
                  break;
               }
            }

            NBTTagCompound var10 = var2.a("BlockEntityTag", true);
            NBTTagList var11 = null;
            if (var10.b("Patterns", 9)) {
               var11 = var10.c("Patterns", 10);
            } else {
               var11 = new NBTTagList();
               var10.a("Patterns", var11);
            }

            NBTTagCompound var7 = new NBTTagCompound();
            var7.a("Pattern", var8.b());
            var7.a("Color", var9);
            var11.a(var7);
         }

         return var2;
      }

      @Override
      public int a() {
         return 10;
      }

      @Override
      public ItemStack b() {
         return null;
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

      private TileEntityBanner.EnumBannerPattern c(InventoryCrafting var1) {
         for (TileEntityBanner.EnumBannerPattern var5 : TileEntityBanner.EnumBannerPattern.values()) {
            if (var5.d()) {
               boolean var6 = true;
               if (var5.e()) {
                  boolean var7 = false;
                  boolean var8 = false;

                  for (int var9 = 0; var9 < var1.o_() && var6; var9++) {
                     ItemStack var10 = var1.a(var9);
                     if (var10 != null && var10.b() != Items.cE) {
                        if (var10.b() == Items.aW) {
                           if (var8) {
                              var6 = false;
                              break;
                           }

                           var8 = true;
                        } else {
                           if (var7 || !var10.a(var5.f())) {
                              var6 = false;
                              break;
                           }

                           var7 = true;
                        }
                     }
                  }

                  if (!var7) {
                     var6 = false;
                  }
               } else if (var1.o_() == var5.c().length * var5.c()[0].length()) {
                  int var12 = -1;

                  for (int var13 = 0; var13 < var1.o_() && var6; var13++) {
                     int var14 = var13 / 3;
                     int var15 = var13 % 3;
                     ItemStack var11 = var1.a(var13);
                     if (var11 != null && var11.b() != Items.cE) {
                        if (var11.b() != Items.aW) {
                           var6 = false;
                           break;
                        }

                        if (var12 != -1 && var12 != var11.i()) {
                           var6 = false;
                           break;
                        }

                        if (var5.c()[var14].charAt(var15) == ' ') {
                           var6 = false;
                           break;
                        }

                        var12 = var11.i();
                     } else if (var5.c()[var14].charAt(var15) != ' ') {
                        var6 = false;
                        break;
                     }
                  }
               } else {
                  var6 = false;
               }

               if (var6) {
                  return var5;
               }
            }
         }

         return null;
      }
   }

   static class RecipeDuplicatePattern implements IRecipe {
      private RecipeDuplicatePattern() {
      }

      @Override
      public boolean a(InventoryCrafting var1, World var2) {
         ItemStack var3 = null;
         ItemStack var4 = null;

         for (int var5 = 0; var5 < var1.o_(); var5++) {
            ItemStack var6 = var1.a(var5);
            if (var6 != null) {
               if (var6.b() != Items.cE) {
                  return false;
               }

               if (var3 != null && var4 != null) {
                  return false;
               }

               int var7 = TileEntityBanner.b(var6);
               boolean var8 = TileEntityBanner.c(var6) > 0;
               if (var3 != null) {
                  if (var8) {
                     return false;
                  }

                  if (var7 != TileEntityBanner.b(var3)) {
                     return false;
                  }

                  var4 = var6;
               } else if (var4 != null) {
                  if (!var8) {
                     return false;
                  }

                  if (var7 != TileEntityBanner.b(var4)) {
                     return false;
                  }

                  var3 = var6;
               } else if (var8) {
                  var3 = var6;
               } else {
                  var4 = var6;
               }
            }
         }

         return var3 != null && var4 != null;
      }

      @Override
      public ItemStack a(InventoryCrafting var1) {
         for (int var2 = 0; var2 < var1.o_(); var2++) {
            ItemStack var3 = var1.a(var2);
            if (var3 != null && TileEntityBanner.c(var3) > 0) {
               ItemStack var4 = var3.k();
               var4.b = 1;
               return var4;
            }
         }

         return null;
      }

      @Override
      public int a() {
         return 2;
      }

      @Override
      public ItemStack b() {
         return null;
      }

      @Override
      public ItemStack[] b(InventoryCrafting var1) {
         ItemStack[] var2 = new ItemStack[var1.o_()];

         for (int var3 = 0; var3 < var2.length; var3++) {
            ItemStack var4 = var1.a(var3);
            if (var4 != null) {
               if (var4.b().r()) {
                  var2[var3] = new ItemStack(var4.b().q());
               } else if (var4.n() && TileEntityBanner.c(var4) > 0) {
                  var2[var3] = var4.k();
                  var2[var3].b = 1;
               }
            }
         }

         return var2;
      }
   }
}
