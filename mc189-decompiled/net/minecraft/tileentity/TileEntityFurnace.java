package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;

public class TileEntityFurnace extends TileEntityLockable implements ITickable, ISidedInventory {
   private static final int[] a = new int[]{0};
   private static final int[] f = new int[]{2, 1};
   private static final int[] g = new int[]{1};
   private ItemStack[] h = new ItemStack[3];
   private int i;
   private int j;
   private int k;
   private int l;
   private String m;

   @Override
   public int o_() {
      return this.h.length;
   }

   @Override
   public ItemStack a(int var1) {
      return this.h[var1];
   }

   @Override
   public ItemStack a(int var1, int var2) {
      if (this.h[var1] != null) {
         if (this.h[var1].b <= var2) {
            ItemStack var4 = this.h[var1];
            this.h[var1] = null;
            return var4;
         } else {
            ItemStack var3 = this.h[var1].a(var2);
            if (this.h[var1].b == 0) {
               this.h[var1] = null;
            }

            return var3;
         }
      } else {
         return null;
      }
   }

   @Override
   public ItemStack b(int var1) {
      if (this.h[var1] != null) {
         ItemStack var2 = this.h[var1];
         this.h[var1] = null;
         return var2;
      } else {
         return null;
      }
   }

   @Override
   public void a(int var1, ItemStack var2) {
      boolean var3 = var2 != null && var2.a(this.h[var1]) && ItemStack.a(var2, this.h[var1]);
      this.h[var1] = var2;
      if (var2 != null && var2.b > this.q_()) {
         var2.b = this.q_();
      }

      if (var1 == 0 && !var3) {
         this.l = this.a(var2);
         this.k = 0;
         this.p_();
      }
   }

   @Override
   public String e_() {
      return this.l_() ? this.m : "container.furnace";
   }

   @Override
   public boolean l_() {
      return this.m != null && this.m.length() > 0;
   }

   public void a(String var1) {
      this.m = var1;
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      NBTTagList var2 = var1.c("Items", 10);
      this.h = new ItemStack[this.o_()];

      for (int var3 = 0; var3 < var2.c(); var3++) {
         NBTTagCompound var4 = var2.b(var3);
         byte var5 = var4.d("Slot");
         if (var5 >= 0 && var5 < this.h.length) {
            this.h[var5] = ItemStack.a(var4);
         }
      }

      this.i = var1.e("BurnTime");
      this.k = var1.e("CookTime");
      this.l = var1.e("CookTimeTotal");
      this.j = b(this.h[1]);
      if (var1.b("CustomName", 8)) {
         this.m = var1.j("CustomName");
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("BurnTime", (short)this.i);
      var1.a("CookTime", (short)this.k);
      var1.a("CookTimeTotal", (short)this.l);
      NBTTagList var2 = new NBTTagList();

      for (int var3 = 0; var3 < this.h.length; var3++) {
         if (this.h[var3] != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            var4.a("Slot", (byte)var3);
            this.h[var3].b(var4);
            var2.a(var4);
         }
      }

      var1.a("Items", var2);
      if (this.l_()) {
         var1.a("CustomName", this.m);
      }
   }

   @Override
   public int q_() {
      return 64;
   }

   public boolean m() {
      return this.i > 0;
   }

   public static boolean a(IInventory var0) {
      return var0.a_(0) > 0;
   }

   @Override
   public void c() {
      boolean var1 = this.m();
      boolean var2 = false;
      if (this.m()) {
         this.i--;
      }

      if (!this.b.D) {
         if (this.m() || this.h[1] != null && this.h[0] != null) {
            if (!this.m() && this.o()) {
               this.j = this.i = b(this.h[1]);
               if (this.m()) {
                  var2 = true;
                  if (this.h[1] != null) {
                     this.h[1].b--;
                     if (this.h[1].b == 0) {
                        Item var3 = this.h[1].b().q();
                        this.h[1] = var3 != null ? new ItemStack(var3) : null;
                     }
                  }
               }
            }

            if (this.m() && this.o()) {
               this.k++;
               if (this.k == this.l) {
                  this.k = 0;
                  this.l = this.a(this.h[0]);
                  this.n();
                  var2 = true;
               }
            } else {
               this.k = 0;
            }
         } else if (!this.m() && this.k > 0) {
            this.k = MathHelper.a(this.k - 2, 0, this.l);
         }

         if (var1 != this.m()) {
            var2 = true;
            BlockFurnace.a(this.m(), this.b, this.c);
         }
      }

      if (var2) {
         this.p_();
      }
   }

   public int a(ItemStack var1) {
      return 200;
   }

   private boolean o() {
      if (this.h[0] == null) {
         return false;
      } else {
         ItemStack var1 = FurnaceRecipes.a().a(this.h[0]);
         if (var1 == null) {
            return false;
         } else if (this.h[2] == null) {
            return true;
         } else if (!this.h[2].a(var1)) {
            return false;
         } else {
            return this.h[2].b < this.q_() && this.h[2].b < this.h[2].c() ? true : this.h[2].b < var1.c();
         }
      }
   }

   public void n() {
      if (this.o()) {
         ItemStack var1 = FurnaceRecipes.a().a(this.h[0]);
         if (this.h[2] == null) {
            this.h[2] = var1.k();
         } else if (this.h[2].b() == var1.b()) {
            this.h[2].b++;
         }

         if (this.h[0].b() == Item.a(Blocks.v) && this.h[0].i() == 1 && this.h[1] != null && this.h[1].b() == Items.aw) {
            this.h[1] = new ItemStack(Items.ax);
         }

         this.h[0].b--;
         if (this.h[0].b <= 0) {
            this.h[0] = null;
         }
      }
   }

   public static int b(ItemStack var0) {
      if (var0 == null) {
         return 0;
      } else {
         Item var1 = var0.b();
         if (var1 instanceof ItemBlock && Block.a(var1) != Blocks.a) {
            Block var2 = Block.a(var1);
            if (var2 == Blocks.bM) {
               return 150;
            }

            if (var2.t() == Material.d) {
               return 300;
            }

            if (var2 == Blocks.cA) {
               return 16000;
            }
         }

         if (var1 instanceof ItemTool && ((ItemTool)var1).h().equals("WOOD")) {
            return 200;
         } else if (var1 instanceof ItemSword && ((ItemSword)var1).h().equals("WOOD")) {
            return 200;
         } else if (var1 instanceof ItemHoe && ((ItemHoe)var1).g().equals("WOOD")) {
            return 200;
         } else if (var1 == Items.y) {
            return 100;
         } else if (var1 == Items.h) {
            return 1600;
         } else if (var1 == Items.ay) {
            return 20000;
         } else if (var1 == Item.a(Blocks.g)) {
            return 100;
         } else {
            return var1 == Items.bv ? 2400 : 0;
         }
      }
   }

   public static boolean c(ItemStack var0) {
      return b(var0) > 0;
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return this.b.s(this.c) != this ? false : !(var1.e(this.c.n() + 0.5, this.c.o() + 0.5, this.c.p() + 0.5) > 64.0);
   }

   @Override
   public void b(EntityPlayer var1) {
   }

   @Override
   public void c(EntityPlayer var1) {
   }

   @Override
   public boolean b(int var1, ItemStack var2) {
      if (var1 == 2) {
         return false;
      } else {
         return var1 != 1 ? true : c(var2) || SlotFurnaceFuel.c_(var2);
      }
   }

   @Override
   public int[] a(EnumFacing var1) {
      if (var1 == EnumFacing.a) {
         return f;
      } else {
         return var1 == EnumFacing.b ? a : g;
      }
   }

   @Override
   public boolean a(int var1, ItemStack var2, EnumFacing var3) {
      return this.b(var1, var2);
   }

   @Override
   public boolean b(int var1, ItemStack var2, EnumFacing var3) {
      if (var3 == EnumFacing.a && var1 == 1) {
         Item var4 = var2.b();
         if (var4 != Items.ax && var4 != Items.aw) {
            return false;
         }
      }

      return true;
   }

   @Override
   public String k() {
      return "minecraft:furnace";
   }

   @Override
   public Container a(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerFurnace(var1, this);
   }

   @Override
   public int a_(int var1) {
      switch (var1) {
         case 0:
            return this.i;
         case 1:
            return this.j;
         case 2:
            return this.k;
         case 3:
            return this.l;
         default:
            return 0;
      }
   }

   @Override
   public void b(int var1, int var2) {
      switch (var1) {
         case 0:
            this.i = var2;
            break;
         case 1:
            this.j = var2;
            break;
         case 2:
            this.k = var2;
            break;
         case 3:
            this.l = var2;
      }
   }

   @Override
   public int g() {
      return 4;
   }

   @Override
   public void l() {
      for (int var1 = 0; var1 < this.h.length; var1++) {
         this.h[var1] = null;
      }
   }
}
