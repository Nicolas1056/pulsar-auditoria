package net.minecraft.inventory;

import java.util.List;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerEnchantment extends Container {
   public IInventory a = new InventoryBasic("Enchant", true, 2) {
      @Override
      public int q_() {
         return 64;
      }

      @Override
      public void p_() {
         super.p_();
         ContainerEnchantment.this.a(this);
      }
   };
   private World i;
   private BlockPos j;
   private Random k = new Random();
   public int f;
   public int[] g = new int[3];
   public int[] h = new int[]{-1, -1, -1};

   public ContainerEnchantment(InventoryPlayer var1, World var2) {
      this(var1, var2, BlockPos.a);
   }

   public ContainerEnchantment(InventoryPlayer var1, World var2, BlockPos var3) {
      this.i = var2;
      this.j = var3;
      this.f = var1.d.cj();
      this.a(new Slot(this.a, 0, 15, 47) {
         @Override
         public boolean a(ItemStack var1) {
            return true;
         }

         @Override
         public int a() {
            return 1;
         }
      });
      this.a(new Slot(this.a, 1, 35, 47) {
         @Override
         public boolean a(ItemStack var1) {
            return var1.b() == Items.aW && EnumDyeColor.a(var1.i()) == EnumDyeColor.l;
         }
      });

      for (int var4 = 0; var4 < 3; var4++) {
         for (int var5 = 0; var5 < 9; var5++) {
            this.a(new Slot(var1, var5 + var4 * 9 + 9, 8 + var5 * 18, 84 + var4 * 18));
         }
      }

      for (int var6 = 0; var6 < 9; var6++) {
         this.a(new Slot(var1, var6, 8 + var6 * 18, 142));
      }
   }

   @Override
   public void a(ICrafting var1) {
      super.a(var1);
      var1.a(this, 0, this.g[0]);
      var1.a(this, 1, this.g[1]);
      var1.a(this, 2, this.g[2]);
      var1.a(this, 3, this.f & -16);
      var1.a(this, 4, this.h[0]);
      var1.a(this, 5, this.h[1]);
      var1.a(this, 6, this.h[2]);
   }

   @Override
   public void b() {
      super.b();

      for (int var1 = 0; var1 < this.e.size(); var1++) {
         ICrafting var2 = this.e.get(var1);
         var2.a(this, 0, this.g[0]);
         var2.a(this, 1, this.g[1]);
         var2.a(this, 2, this.g[2]);
         var2.a(this, 3, this.f & -16);
         var2.a(this, 4, this.h[0]);
         var2.a(this, 5, this.h[1]);
         var2.a(this, 6, this.h[2]);
      }
   }

   @Override
   public void b(int var1, int var2) {
      if (var1 >= 0 && var1 <= 2) {
         this.g[var1] = var2;
      } else if (var1 == 3) {
         this.f = var2;
      } else if (var1 >= 4 && var1 <= 6) {
         this.h[var1 - 4] = var2;
      } else {
         super.b(var1, var2);
      }
   }

   @Override
   public void a(IInventory var1) {
      if (var1 == this.a) {
         ItemStack var2 = var1.a(0);
         if (var2 != null && var2.v()) {
            if (!this.i.D) {
               int var7 = 0;

               for (int var4 = -1; var4 <= 1; var4++) {
                  for (int var5 = -1; var5 <= 1; var5++) {
                     if ((var4 != 0 || var5 != 0) && this.i.d(this.j.a(var5, 0, var4)) && this.i.d(this.j.a(var5, 1, var4))) {
                        if (this.i.p(this.j.a(var5 * 2, 0, var4 * 2)).c() == Blocks.X) {
                           var7++;
                        }

                        if (this.i.p(this.j.a(var5 * 2, 1, var4 * 2)).c() == Blocks.X) {
                           var7++;
                        }

                        if (var5 != 0 && var4 != 0) {
                           if (this.i.p(this.j.a(var5 * 2, 0, var4)).c() == Blocks.X) {
                              var7++;
                           }

                           if (this.i.p(this.j.a(var5 * 2, 1, var4)).c() == Blocks.X) {
                              var7++;
                           }

                           if (this.i.p(this.j.a(var5, 0, var4 * 2)).c() == Blocks.X) {
                              var7++;
                           }

                           if (this.i.p(this.j.a(var5, 1, var4 * 2)).c() == Blocks.X) {
                              var7++;
                           }
                        }
                     }
                  }
               }

               this.k.setSeed(this.f);

               for (int var8 = 0; var8 < 3; var8++) {
                  this.g[var8] = EnchantmentHelper.a(this.k, var8, var7, var2);
                  this.h[var8] = -1;
                  if (this.g[var8] < var8 + 1) {
                     this.g[var8] = 0;
                  }
               }

               for (int var9 = 0; var9 < 3; var9++) {
                  if (this.g[var9] > 0) {
                     List var10 = this.a(var2, var9, this.g[var9]);
                     if (var10 != null && !var10.isEmpty()) {
                        EnchantmentData var6 = (EnchantmentData)var10.get(this.k.nextInt(var10.size()));
                        this.h[var9] = var6.b.B | var6.c << 8;
                     }
                  }
               }

               this.b();
            }
         } else {
            for (int var3 = 0; var3 < 3; var3++) {
               this.g[var3] = 0;
               this.h[var3] = -1;
            }
         }
      }
   }

   @Override
   public boolean a(EntityPlayer var1, int var2) {
      ItemStack var3 = this.a.a(0);
      ItemStack var4 = this.a.a(1);
      int var5 = var2 + 1;
      if ((var4 == null || var4.b < var5) && !var1.bA.d) {
         return false;
      } else if (this.g[var2] > 0 && var3 != null && (var1.bB >= var5 && var1.bB >= this.g[var2] || var1.bA.d)) {
         if (!this.i.D) {
            List var6 = this.a(var3, var2, this.g[var2]);
            boolean var7 = var3.b() == Items.aL;
            if (var6 != null) {
               var1.b(var5);
               if (var7) {
                  var3.a(Items.cd);
               }

               for (int var8 = 0; var8 < var6.size(); var8++) {
                  EnchantmentData var9 = (EnchantmentData)var6.get(var8);
                  if (var7) {
                     Items.cd.a(var3, var9);
                  } else {
                     var3.a(var9.b, var9.c);
                  }
               }

               if (!var1.bA.d) {
                  var4.b -= var5;
                  if (var4.b <= 0) {
                     this.a.a(1, null);
                  }
               }

               var1.b(StatList.W);
               this.a.p_();
               this.f = var1.cj();
               this.a(this.a);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private List<EnchantmentData> a(ItemStack var1, int var2, int var3) {
      this.k.setSeed(this.f + var2);
      List var4 = EnchantmentHelper.b(this.k, var1, var3);
      if (var1.b() == Items.aL && var4 != null && var4.size() > 1) {
         var4.remove(this.k.nextInt(var4.size()));
      }

      return var4;
   }

   public int e() {
      ItemStack var1 = this.a.a(1);
      return var1 == null ? 0 : var1.b;
   }

   @Override
   public void b(EntityPlayer var1) {
      super.b(var1);
      if (!this.i.D) {
         for (int var2 = 0; var2 < this.a.o_(); var2++) {
            ItemStack var3 = this.a.b(var2);
            if (var3 != null) {
               var1.a(var3, false);
            }
         }
      }
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return this.i.p(this.j).c() != Blocks.bC ? false : !(var1.e(this.j.n() + 0.5, this.j.o() + 0.5, this.j.p() + 0.5) > 64.0);
   }

   @Override
   public ItemStack b(EntityPlayer var1, int var2) {
      ItemStack var3 = null;
      Slot var4 = this.c.get(var2);
      if (var4 != null && var4.e()) {
         ItemStack var5 = var4.d();
         var3 = var5.k();
         if (var2 == 0) {
            if (!this.a(var5, 2, 38, true)) {
               return null;
            }
         } else if (var2 == 1) {
            if (!this.a(var5, 2, 38, true)) {
               return null;
            }
         } else if (var5.b() == Items.aW && EnumDyeColor.a(var5.i()) == EnumDyeColor.l) {
            if (!this.a(var5, 1, 2, true)) {
               return null;
            }
         } else {
            if (this.c.get(0).e() || !this.c.get(0).a(var5)) {
               return null;
            }

            if (var5.n() && var5.b == 1) {
               this.c.get(0).d(var5.k());
               var5.b = 0;
            } else if (var5.b >= 1) {
               this.c.get(0).d(new ItemStack(var5.b(), 1, var5.i()));
               var5.b--;
            }
         }

         if (var5.b == 0) {
            var4.d(null);
         } else {
            var4.f();
         }

         if (var5.b == var3.b) {
            return null;
         }

         var4.a(var1, var5);
      }

      return var3;
   }
}
