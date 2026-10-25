package net.minecraft.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public abstract class Container {
   public List<ItemStack> b = Lists.newArrayList();
   public List<Slot> c = Lists.newArrayList();
   public int d;
   private short a;
   private int f = -1;
   private int g;
   private final Set<Slot> h = Sets.newHashSet();
   protected List<ICrafting> e = Lists.newArrayList();
   private Set<EntityPlayer> i = Sets.newHashSet();

   protected Slot a(Slot var1) {
      var1.e = this.c.size();
      this.c.add(var1);
      this.b.add(null);
      return var1;
   }

   public void a(ICrafting var1) {
      if (this.e.contains(var1)) {
         throw new IllegalArgumentException("Listener already listening");
      } else {
         this.e.add(var1);
         var1.a(this, this.a());
         this.b();
      }
   }

   public void b(ICrafting var1) {
      this.e.remove(var1);
   }

   public List<ItemStack> a() {
      ArrayList var1 = Lists.newArrayList();

      for (int var2 = 0; var2 < this.c.size(); var2++) {
         var1.add(this.c.get(var2).d());
      }

      return var1;
   }

   public void b() {
      for (int var1 = 0; var1 < this.c.size(); var1++) {
         ItemStack var2 = this.c.get(var1).d();
         ItemStack var3 = this.b.get(var1);
         if (!ItemStack.b(var3, var2)) {
            var3 = var2 == null ? null : var2.k();
            this.b.set(var1, var3);

            for (int var4 = 0; var4 < this.e.size(); var4++) {
               this.e.get(var4).a(this, var1, var3);
            }
         }
      }
   }

   public boolean a(EntityPlayer var1, int var2) {
      return false;
   }

   public Slot a(IInventory var1, int var2) {
      for (int var3 = 0; var3 < this.c.size(); var3++) {
         Slot var4 = this.c.get(var3);
         if (var4.a(var1, var2)) {
            return var4;
         }
      }

      return null;
   }

   public Slot a(int var1) {
      return this.c.get(var1);
   }

   public ItemStack b(EntityPlayer var1, int var2) {
      Slot var3 = this.c.get(var2);
      return var3 != null ? var3.d() : null;
   }

   public ItemStack a(int var1, int var2, int var3, EntityPlayer var4) {
      ItemStack var5 = null;
      InventoryPlayer var6 = var4.bi;
      if (var3 == 5) {
         int var7 = this.g;
         this.g = c(var2);
         if ((var7 != 1 || this.g != 2) && var7 != this.g) {
            this.d();
         } else if (var6.p() == null) {
            this.d();
         } else if (this.g == 0) {
            this.f = b(var2);
            if (a(this.f, var4)) {
               this.g = 1;
               this.h.clear();
            } else {
               this.d();
            }
         } else if (this.g == 1) {
            Slot var8 = this.c.get(var1);
            if (var8 != null && a(var8, var6.p(), true) && var8.a(var6.p()) && var6.p().b > this.h.size() && this.b(var8)) {
               this.h.add(var8);
            }
         } else if (this.g == 2) {
            if (!this.h.isEmpty()) {
               ItemStack var22 = var6.p().k();
               int var9 = var6.p().b;

               for (Slot var11 : this.h) {
                  if (var11 != null && a(var11, var6.p(), true) && var11.a(var6.p()) && var6.p().b >= this.h.size() && this.b(var11)) {
                     ItemStack var12 = var22.k();
                     int var13 = var11.e() ? var11.d().b : 0;
                     a(this.h, this.f, var12, var13);
                     if (var12.b > var12.c()) {
                        var12.b = var12.c();
                     }

                     if (var12.b > var11.b(var12)) {
                        var12.b = var11.b(var12);
                     }

                     var9 -= var12.b - var13;
                     var11.d(var12);
                  }
               }

               var22.b = var9;
               if (var22.b <= 0) {
                  var22 = null;
               }

               var6.b(var22);
            }

            this.d();
         } else {
            this.d();
         }
      } else if (this.g != 0) {
         this.d();
      } else if ((var3 == 0 || var3 == 1) && (var2 == 0 || var2 == 1)) {
         if (var1 == -999) {
            if (var6.p() != null) {
               if (var2 == 0) {
                  var4.a(var6.p(), true);
                  var6.b(null);
               }

               if (var2 == 1) {
                  var4.a(var6.p().a(1), true);
                  if (var6.p().b == 0) {
                     var6.b(null);
                  }
               }
            }
         } else if (var3 == 1) {
            if (var1 < 0) {
               return null;
            }

            Slot var20 = this.c.get(var1);
            if (var20 != null && var20.a(var4)) {
               ItemStack var27 = this.b(var4, var1);
               if (var27 != null) {
                  Item var32 = var27.b();
                  var5 = var27.k();
                  if (var20.d() != null && var20.d().b() == var32) {
                     this.a(var1, var2, true, var4);
                  }
               }
            }
         } else {
            if (var1 < 0) {
               return null;
            }

            Slot var21 = this.c.get(var1);
            if (var21 != null) {
               ItemStack var28 = var21.d();
               ItemStack var33 = var6.p();
               if (var28 != null) {
                  var5 = var28.k();
               }

               if (var28 == null) {
                  if (var33 != null && var21.a(var33)) {
                     int var39 = var2 == 0 ? var33.b : 1;
                     if (var39 > var21.b(var33)) {
                        var39 = var21.b(var33);
                     }

                     if (var33.b >= var39) {
                        var21.d(var33.a(var39));
                     }

                     if (var33.b == 0) {
                        var6.b(null);
                     }
                  }
               } else if (var21.a(var4)) {
                  if (var33 == null) {
                     int var38 = var2 == 0 ? var28.b : (var28.b + 1) / 2;
                     ItemStack var42 = var21.a(var38);
                     var6.b(var42);
                     if (var28.b == 0) {
                        var21.d(null);
                     }

                     var21.a(var4, var6.p());
                  } else if (var21.a(var33)) {
                     if (var28.b() == var33.b() && var28.i() == var33.i() && ItemStack.a(var28, var33)) {
                        int var37 = var2 == 0 ? var33.b : 1;
                        if (var37 > var21.b(var33) - var28.b) {
                           var37 = var21.b(var33) - var28.b;
                        }

                        if (var37 > var33.c() - var28.b) {
                           var37 = var33.c() - var28.b;
                        }

                        var33.a(var37);
                        if (var33.b == 0) {
                           var6.b(null);
                        }

                        var28.b += var37;
                     } else if (var33.b <= var21.b(var33)) {
                        var21.d(var33);
                        var6.b(var28);
                     }
                  } else if (var28.b() == var33.b() && var33.c() > 1 && (!var28.f() || var28.i() == var33.i()) && ItemStack.a(var28, var33)) {
                     int var36 = var28.b;
                     if (var36 > 0 && var36 + var33.b <= var33.c()) {
                        var33.b += var36;
                        var28 = var21.a(var36);
                        if (var28.b == 0) {
                           var21.d(null);
                        }

                        var21.a(var4, var6.p());
                     }
                  }
               }

               var21.f();
            }
         }
      } else if (var3 == 2 && var2 >= 0 && var2 < 9) {
         Slot var19 = this.c.get(var1);
         if (var19.a(var4)) {
            ItemStack var26 = var6.a(var2);
            boolean var31 = var26 == null || var19.d == var6 && var19.a(var26);
            int var35 = -1;
            if (!var31) {
               var35 = var6.j();
               var31 |= var35 > -1;
            }

            if (var19.e() && var31) {
               ItemStack var41 = var19.d();
               var6.a(var2, var41.k());
               if ((var19.d != var6 || !var19.a(var26)) && var26 != null) {
                  if (var35 > -1) {
                     var6.a(var26);
                     var19.a(var41.b);
                     var19.d(null);
                     var19.a(var4, var41);
                  }
               } else {
                  var19.a(var41.b);
                  var19.d(var26);
                  var19.a(var4, var41);
               }
            } else if (!var19.e() && var26 != null && var19.a(var26)) {
               var6.a(var2, null);
               var19.d(var26);
            }
         }
      } else if (var3 == 3 && var4.bA.d && var6.p() == null && var1 >= 0) {
         Slot var18 = this.c.get(var1);
         if (var18 != null && var18.e()) {
            ItemStack var25 = var18.d().k();
            var25.b = var25.c();
            var6.b(var25);
         }
      } else if (var3 == 4 && var6.p() == null && var1 >= 0) {
         Slot var17 = this.c.get(var1);
         if (var17 != null && var17.e() && var17.a(var4)) {
            ItemStack var24 = var17.a(var2 == 0 ? 1 : var17.d().b);
            var17.a(var4, var24);
            var4.a(var24, true);
         }
      } else if (var3 == 6 && var1 >= 0) {
         Slot var16 = this.c.get(var1);
         ItemStack var23 = var6.p();
         if (var23 != null && (var16 == null || !var16.e() || !var16.a(var4))) {
            int var30 = var2 == 0 ? 0 : this.c.size() - 1;
            int var34 = var2 == 0 ? 1 : -1;

            for (int var40 = 0; var40 < 2; var40++) {
               for (int var43 = var30; var43 >= 0 && var43 < this.c.size() && var23.b < var23.c(); var43 += var34) {
                  Slot var44 = this.c.get(var43);
                  if (var44.e() && a(var44, var23, true) && var44.a(var4) && this.a(var23, var44) && (var40 != 0 || var44.d().b != var44.d().c())) {
                     int var14 = Math.min(var23.c() - var23.b, var44.d().b);
                     ItemStack var15 = var44.a(var14);
                     var23.b += var14;
                     if (var15.b <= 0) {
                        var44.d(null);
                     }

                     var44.a(var4, var15);
                  }
               }
            }
         }

         this.b();
      }

      return var5;
   }

   public boolean a(ItemStack var1, Slot var2) {
      return true;
   }

   protected void a(int var1, int var2, boolean var3, EntityPlayer var4) {
      this.a(var1, var2, 1, var4);
   }

   public void b(EntityPlayer var1) {
      InventoryPlayer var2 = var1.bi;
      if (var2.p() != null) {
         var1.a(var2.p(), false);
         var2.b(null);
      }
   }

   public void a(IInventory var1) {
      this.b();
   }

   public void a(int var1, ItemStack var2) {
      this.a(var1).d(var2);
   }

   public void a(ItemStack[] var1) {
      for (int var2 = 0; var2 < var1.length; var2++) {
         this.a(var2).d(var1[var2]);
      }
   }

   public void b(int var1, int var2) {
   }

   public short a(InventoryPlayer var1) {
      this.a++;
      return this.a;
   }

   public boolean c(EntityPlayer var1) {
      return !this.i.contains(var1);
   }

   public void a(EntityPlayer var1, boolean var2) {
      if (var2) {
         this.i.remove(var1);
      } else {
         this.i.add(var1);
      }
   }

   public abstract boolean a(EntityPlayer var1);

   protected boolean a(ItemStack var1, int var2, int var3, boolean var4) {
      boolean var5 = false;
      int var6 = var2;
      if (var4) {
         var6 = var3 - 1;
      }

      if (var1.d()) {
         while (var1.b > 0 && (!var4 && var6 < var3 || var4 && var6 >= var2)) {
            Slot var7 = this.c.get(var6);
            ItemStack var8 = var7.d();
            if (var8 != null && var8.b() == var1.b() && (!var1.f() || var1.i() == var8.i()) && ItemStack.a(var1, var8)) {
               int var9 = var8.b + var1.b;
               if (var9 <= var1.c()) {
                  var1.b = 0;
                  var8.b = var9;
                  var7.f();
                  var5 = true;
               } else if (var8.b < var1.c()) {
                  var1.b = var1.b - (var1.c() - var8.b);
                  var8.b = var1.c();
                  var7.f();
                  var5 = true;
               }
            }

            if (var4) {
               var6--;
            } else {
               var6++;
            }
         }
      }

      if (var1.b > 0) {
         if (var4) {
            var6 = var3 - 1;
         } else {
            var6 = var2;
         }

         while (!var4 && var6 < var3 || var4 && var6 >= var2) {
            Slot var11 = this.c.get(var6);
            ItemStack var12 = var11.d();
            if (var12 == null) {
               var11.d(var1.k());
               var11.f();
               var1.b = 0;
               var5 = true;
               break;
            }

            if (var4) {
               var6--;
            } else {
               var6++;
            }
         }
      }

      return var5;
   }

   public static int b(int var0) {
      return var0 >> 2 & 3;
   }

   public static int c(int var0) {
      return var0 & 3;
   }

   public static int d(int var0, int var1) {
      return var0 & 3 | (var1 & 3) << 2;
   }

   public static boolean a(int var0, EntityPlayer var1) {
      if (var0 == 0) {
         return true;
      } else {
         return var0 == 1 ? true : var0 == 2 && var1.bA.d;
      }
   }

   protected void d() {
      this.g = 0;
      this.h.clear();
   }

   public static boolean a(Slot var0, ItemStack var1, boolean var2) {
      boolean var3 = var0 == null || !var0.e();
      if (var0 != null && var0.e() && var1 != null && var1.a(var0.d()) && ItemStack.a(var0.d(), var1)) {
         int var10002 = var2 ? 0 : var1.b;
         var3 |= var0.d().b + var10002 <= var1.c();
      }

      return var3;
   }

   public static void a(Set<Slot> var0, int var1, ItemStack var2, int var3) {
      switch (var1) {
         case 0:
            var2.b = MathHelper.d((float)var2.b / var0.size());
            break;
         case 1:
            var2.b = 1;
            break;
         case 2:
            var2.b = var2.b().j();
      }

      var2.b += var3;
   }

   public boolean b(Slot var1) {
      return true;
   }

   public static int a(TileEntity var0) {
      return var0 instanceof IInventory ? b((IInventory)var0) : 0;
   }

   public static int b(IInventory var0) {
      if (var0 == null) {
         return 0;
      } else {
         int var1 = 0;
         float var2 = 0.0F;

         for (int var3 = 0; var3 < var0.o_(); var3++) {
            ItemStack var4 = var0.a(var3);
            if (var4 != null) {
               var2 += (float)var4.b / Math.min(var0.q_(), var4.c());
               var1++;
            }
         }

         var2 /= var0.o_();
         return MathHelper.d(var2 * 14.0F) + (var1 > 0 ? 1 : 0);
      }
   }
}
