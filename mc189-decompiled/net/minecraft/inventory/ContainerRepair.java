package net.minecraft.inventory;

import java.util.Map;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContainerRepair extends Container {
   private static final Logger f = LogManager.getLogger();
   private IInventory g = new InventoryCraftResult();
   private IInventory h = new InventoryBasic("Repair", true, 2) {
      @Override
      public void p_() {
         super.p_();
         ContainerRepair.this.a(this);
      }
   };
   private World i;
   private BlockPos j;
   public int a;
   private int k;
   private String l;
   private final EntityPlayer m;

   public ContainerRepair(InventoryPlayer var1, World var2, EntityPlayer var3) {
      this(var1, var2, BlockPos.a, var3);
   }

   public ContainerRepair(InventoryPlayer var1, final World var2, final BlockPos var3, EntityPlayer var4) {
      this.j = var3;
      this.i = var2;
      this.m = var4;
      this.a(new Slot(this.h, 0, 27, 47));
      this.a(new Slot(this.h, 1, 76, 47));
      this.a(new Slot(this.g, 2, 134, 47) {
         @Override
         public boolean a(ItemStack var1) {
            return false;
         }

         @Override
         public boolean a(EntityPlayer var1) {
            return (var1.bA.d || var1.bB >= ContainerRepair.this.a) && ContainerRepair.this.a > 0 && this.e();
         }

         @Override
         public void a(EntityPlayer var1, ItemStack var2x) {
            if (!var1.bA.d) {
               var1.a(-ContainerRepair.this.a);
            }

            ContainerRepair.this.h.a(0, null);
            if (ContainerRepair.this.k > 0) {
               ItemStack var3x = ContainerRepair.this.h.a(1);
               if (var3x != null && var3x.b > ContainerRepair.this.k) {
                  var3x.b = var3x.b - ContainerRepair.this.k;
                  ContainerRepair.this.h.a(1, var3x);
               } else {
                  ContainerRepair.this.h.a(1, null);
               }
            } else {
               ContainerRepair.this.h.a(1, null);
            }

            ContainerRepair.this.a = 0;
            IBlockState var5 = var2.p(var3);
            if (!var1.bA.d && !var2.D && var5.c() == Blocks.cf && var1.bc().nextFloat() < 0.12F) {
               int var4x = var5.b(BlockAnvil.b);
               if (++var4x > 2) {
                  var2.g(var3);
                  var2.b(1020, var3, 0);
               } else {
                  var2.a(var3, var5.a(BlockAnvil.b, var4x), 2);
                  var2.b(1021, var3, 0);
               }
            } else if (!var2.D) {
               var2.b(1021, var3, 0);
            }
         }
      });

      for (int var5 = 0; var5 < 3; var5++) {
         for (int var6 = 0; var6 < 9; var6++) {
            this.a(new Slot(var1, var6 + var5 * 9 + 9, 8 + var6 * 18, 84 + var5 * 18));
         }
      }

      for (int var7 = 0; var7 < 9; var7++) {
         this.a(new Slot(var1, var7, 8 + var7 * 18, 142));
      }
   }

   @Override
   public void a(IInventory var1) {
      super.a(var1);
      if (var1 == this.h) {
         this.e();
      }
   }

   public void e() {
      boolean var1 = false;
      boolean var2 = true;
      boolean var3 = true;
      boolean var4 = true;
      byte var5 = 2;
      boolean var6 = true;
      boolean var7 = true;
      ItemStack var8 = this.h.a(0);
      this.a = 1;
      int var9 = 0;
      int var10 = 0;
      byte var11 = 0;
      if (var8 == null) {
         this.g.a(0, null);
         this.a = 0;
      } else {
         ItemStack var12 = var8.k();
         ItemStack var13 = this.h.a(1);
         Map var14 = EnchantmentHelper.a(var12);
         boolean var15 = false;
         var10 += var8.A() + (var13 == null ? 0 : var13.A());
         this.k = 0;
         if (var13 != null) {
            var15 = var13.b() == Items.cd && Items.cd.h(var13).c() > 0;
            if (var12.e() && var12.b().a(var8, var13)) {
               int var28 = Math.min(var12.h(), var12.j() / 4);
               if (var28 <= 0) {
                  this.g.a(0, null);
                  this.a = 0;
                  return;
               }

               int var32;
               for (var32 = 0; var28 > 0 && var32 < var13.b; var32++) {
                  int var34 = var12.h() - var28;
                  var12.b(var34);
                  var9++;
                  var28 = Math.min(var12.h(), var12.j() / 4);
               }

               this.k = var32;
            } else {
               if (!var15 && (var12.b() != var13.b() || !var12.e())) {
                  this.g.a(0, null);
                  this.a = 0;
                  return;
               }

               if (var12.e() && !var15) {
                  int var16 = var8.j() - var8.h();
                  int var17 = var13.j() - var13.h();
                  int var18 = var17 + var12.j() * 12 / 100;
                  int var19 = var16 + var18;
                  int var20 = var12.j() - var19;
                  if (var20 < 0) {
                     var20 = 0;
                  }

                  if (var20 < var12.i()) {
                     var12.b(var20);
                     var9 += 2;
                  }
               }

               Map var27 = EnchantmentHelper.a(var13);

               for (int var33 : var27.keySet()) {
                  Enchantment var35 = Enchantment.c(var33);
                  if (var35 != null) {
                     int var36 = var14.containsKey(var33) ? (Integer)var14.get(var33) : 0;
                     int var21 = (Integer)var27.get(var33);
                     var21 = var36 == var21 ? ++var21 : Math.max(var21, var36);
                     boolean var22 = var35.a(var8);
                     if (this.m.bA.d || var8.b() == Items.cd) {
                        var22 = true;
                     }

                     for (int var24 : var14.keySet()) {
                        if (var24 != var33 && !var35.a(Enchantment.c(var24))) {
                           var22 = false;
                           var9++;
                        }
                     }

                     if (var22) {
                        if (var21 > var35.b()) {
                           var21 = var35.b();
                        }

                        var14.put(var33, var21);
                        int var39 = 0;
                        switch (var35.d()) {
                           case 1:
                              var39 = 8;
                              break;
                           case 2:
                              var39 = 4;
                           case 3:
                           case 4:
                           case 6:
                           case 7:
                           case 8:
                           case 9:
                           default:
                              break;
                           case 5:
                              var39 = 2;
                              break;
                           case 10:
                              var39 = 1;
                        }

                        if (var15) {
                           var39 = Math.max(1, var39 / 2);
                        }

                        var9 += var39 * var21;
                     }
                  }
               }
            }
         }

         if (StringUtils.isBlank(this.l)) {
            if (var8.s()) {
               var11 = 1;
               var9 += var11;
               var12.r();
            }
         } else if (!this.l.equals(var8.q())) {
            var11 = 1;
            var9 += var11;
            var12.c(this.l);
         }

         this.a = var10 + var9;
         if (var9 <= 0) {
            var12 = null;
         }

         if (var11 == var9 && var11 > 0 && this.a >= 40) {
            this.a = 39;
         }

         if (this.a >= 40 && !this.m.bA.d) {
            var12 = null;
         }

         if (var12 != null) {
            int var29 = var12.A();
            if (var13 != null && var29 < var13.A()) {
               var29 = var13.A();
            }

            var29 = var29 * 2 + 1;
            var12.c(var29);
            EnchantmentHelper.a(var14, var12);
         }

         this.g.a(0, var12);
         this.b();
      }
   }

   @Override
   public void a(ICrafting var1) {
      super.a(var1);
      var1.a(this, 0, this.a);
   }

   @Override
   public void b(int var1, int var2) {
      if (var1 == 0) {
         this.a = var2;
      }
   }

   @Override
   public void b(EntityPlayer var1) {
      super.b(var1);
      if (!this.i.D) {
         for (int var2 = 0; var2 < this.h.o_(); var2++) {
            ItemStack var3 = this.h.b(var2);
            if (var3 != null) {
               var1.a(var3, false);
            }
         }
      }
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return this.i.p(this.j).c() != Blocks.cf ? false : !(var1.e(this.j.n() + 0.5, this.j.o() + 0.5, this.j.p() + 0.5) > 64.0);
   }

   @Override
   public ItemStack b(EntityPlayer var1, int var2) {
      ItemStack var3 = null;
      Slot var4 = this.c.get(var2);
      if (var4 != null && var4.e()) {
         ItemStack var5 = var4.d();
         var3 = var5.k();
         if (var2 == 2) {
            if (!this.a(var5, 3, 39, true)) {
               return null;
            }

            var4.a(var5, var3);
         } else if (var2 != 0 && var2 != 1) {
            if (var2 >= 3 && var2 < 39 && !this.a(var5, 0, 2, false)) {
               return null;
            }
         } else if (!this.a(var5, 3, 39, false)) {
            return null;
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

   public void a(String var1) {
      this.l = var1;
      if (this.a(2).e()) {
         ItemStack var2 = this.a(2).d();
         if (StringUtils.isBlank(var1)) {
            var2.r();
         } else {
            var2.c(this.l);
         }
      }

      this.e();
   }
}
