package net.minecraft.tileentity;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHopper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityHopper extends TileEntityLockable implements IHopper, ITickable {
   private ItemStack[] a = new ItemStack[5];
   private String f;
   private int g = -1;

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      NBTTagList var2 = var1.c("Items", 10);
      this.a = new ItemStack[this.o_()];
      if (var1.b("CustomName", 8)) {
         this.f = var1.j("CustomName");
      }

      this.g = var1.f("TransferCooldown");

      for (int var3 = 0; var3 < var2.c(); var3++) {
         NBTTagCompound var4 = var2.b(var3);
         byte var5 = var4.d("Slot");
         if (var5 >= 0 && var5 < this.a.length) {
            this.a[var5] = ItemStack.a(var4);
         }
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      NBTTagList var2 = new NBTTagList();

      for (int var3 = 0; var3 < this.a.length; var3++) {
         if (this.a[var3] != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            var4.a("Slot", (byte)var3);
            this.a[var3].b(var4);
            var2.a(var4);
         }
      }

      var1.a("Items", var2);
      var1.a("TransferCooldown", this.g);
      if (this.l_()) {
         var1.a("CustomName", this.f);
      }
   }

   @Override
   public void p_() {
      super.p_();
   }

   @Override
   public int o_() {
      return this.a.length;
   }

   @Override
   public ItemStack a(int var1) {
      return this.a[var1];
   }

   @Override
   public ItemStack a(int var1, int var2) {
      if (this.a[var1] != null) {
         if (this.a[var1].b <= var2) {
            ItemStack var4 = this.a[var1];
            this.a[var1] = null;
            return var4;
         } else {
            ItemStack var3 = this.a[var1].a(var2);
            if (this.a[var1].b == 0) {
               this.a[var1] = null;
            }

            return var3;
         }
      } else {
         return null;
      }
   }

   @Override
   public ItemStack b(int var1) {
      if (this.a[var1] != null) {
         ItemStack var2 = this.a[var1];
         this.a[var1] = null;
         return var2;
      } else {
         return null;
      }
   }

   @Override
   public void a(int var1, ItemStack var2) {
      this.a[var1] = var2;
      if (var2 != null && var2.b > this.q_()) {
         var2.b = this.q_();
      }
   }

   @Override
   public String e_() {
      return this.l_() ? this.f : "container.hopper";
   }

   @Override
   public boolean l_() {
      return this.f != null && this.f.length() > 0;
   }

   public void a(String var1) {
      this.f = var1;
   }

   @Override
   public int q_() {
      return 64;
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
      return true;
   }

   @Override
   public void c() {
      if (this.b != null && !this.b.D) {
         this.g--;
         if (!this.n()) {
            this.d(0);
            this.m();
         }
      }
   }

   public boolean m() {
      if (this.b != null && !this.b.D) {
         if (!this.n() && BlockHopper.f(this.u())) {
            boolean var1 = false;
            if (!this.p()) {
               var1 = this.r();
            }

            if (!this.q()) {
               var1 = a(this) || var1;
            }

            if (var1) {
               this.d(8);
               this.p_();
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean p() {
      for (ItemStack var4 : this.a) {
         if (var4 != null) {
            return false;
         }
      }

      return true;
   }

   private boolean q() {
      for (ItemStack var4 : this.a) {
         if (var4 == null || var4.b != var4.c()) {
            return false;
         }
      }

      return true;
   }

   private boolean r() {
      IInventory var1 = this.H();
      if (var1 == null) {
         return false;
      } else {
         EnumFacing var2 = BlockHopper.b(this.u()).d();
         if (this.a(var1, var2)) {
            return false;
         } else {
            for (int var3 = 0; var3 < this.o_(); var3++) {
               if (this.a(var3) != null) {
                  ItemStack var4 = this.a(var3).k();
                  ItemStack var5 = a(var1, this.a(var3, 1), var2);
                  if (var5 == null || var5.b == 0) {
                     var1.p_();
                     return true;
                  }

                  this.a(var3, var4);
               }
            }

            return false;
         }
      }
   }

   private boolean a(IInventory var1, EnumFacing var2) {
      if (var1 instanceof ISidedInventory) {
         ISidedInventory var3 = (ISidedInventory)var1;
         int[] var4 = var3.a(var2);

         for (int var5 = 0; var5 < var4.length; var5++) {
            ItemStack var6 = var3.a(var4[var5]);
            if (var6 == null || var6.b != var6.c()) {
               return false;
            }
         }
      } else {
         int var7 = var1.o_();

         for (int var8 = 0; var8 < var7; var8++) {
            ItemStack var9 = var1.a(var8);
            if (var9 == null || var9.b != var9.c()) {
               return false;
            }
         }
      }

      return true;
   }

   private static boolean b(IInventory var0, EnumFacing var1) {
      if (var0 instanceof ISidedInventory) {
         ISidedInventory var2 = (ISidedInventory)var0;
         int[] var3 = var2.a(var1);

         for (int var4 = 0; var4 < var3.length; var4++) {
            if (var2.a(var3[var4]) != null) {
               return false;
            }
         }
      } else {
         int var5 = var0.o_();

         for (int var6 = 0; var6 < var5; var6++) {
            if (var0.a(var6) != null) {
               return false;
            }
         }
      }

      return true;
   }

   public static boolean a(IHopper var0) {
      IInventory var1 = b(var0);
      if (var1 != null) {
         EnumFacing var2 = EnumFacing.a;
         if (b(var1, var2)) {
            return false;
         }

         if (var1 instanceof ISidedInventory) {
            ISidedInventory var3 = (ISidedInventory)var1;
            int[] var4 = var3.a(var2);

            for (int var5 = 0; var5 < var4.length; var5++) {
               if (a(var0, var1, var4[var5], var2)) {
                  return true;
               }
            }
         } else {
            int var7 = var1.o_();

            for (int var9 = 0; var9 < var7; var9++) {
               if (a(var0, var1, var9, var2)) {
                  return true;
               }
            }
         }
      } else {
         for (EntityItem var8 : a(var0.z(), var0.A(), var0.B() + 1.0, var0.C())) {
            if (a(var0, var8)) {
               return true;
            }
         }
      }

      return false;
   }

   private static boolean a(IHopper var0, IInventory var1, int var2, EnumFacing var3) {
      ItemStack var4 = var1.a(var2);
      if (var4 != null && b(var1, var4, var2, var3)) {
         ItemStack var5 = var4.k();
         ItemStack var6 = a(var0, var1.a(var2, 1), null);
         if (var6 == null || var6.b == 0) {
            var1.p_();
            return true;
         }

         var1.a(var2, var5);
      }

      return false;
   }

   public static boolean a(IInventory var0, EntityItem var1) {
      boolean var2 = false;
      if (var1 == null) {
         return false;
      } else {
         ItemStack var3 = var1.l().k();
         ItemStack var4 = a(var0, var3, null);
         if (var4 != null && var4.b != 0) {
            var1.a(var4);
         } else {
            var2 = true;
            var1.J();
         }

         return var2;
      }
   }

   public static ItemStack a(IInventory var0, ItemStack var1, EnumFacing var2) {
      if (var0 instanceof ISidedInventory && var2 != null) {
         ISidedInventory var6 = (ISidedInventory)var0;
         int[] var7 = var6.a(var2);

         for (int var5 = 0; var5 < var7.length && var1 != null && var1.b > 0; var5++) {
            var1 = c(var0, var1, var7[var5], var2);
         }
      } else {
         int var3 = var0.o_();

         for (int var4 = 0; var4 < var3 && var1 != null && var1.b > 0; var4++) {
            var1 = c(var0, var1, var4, var2);
         }
      }

      if (var1 != null && var1.b == 0) {
         var1 = null;
      }

      return var1;
   }

   private static boolean a(IInventory var0, ItemStack var1, int var2, EnumFacing var3) {
      return !var0.b(var2, var1) ? false : !(var0 instanceof ISidedInventory) || ((ISidedInventory)var0).a(var2, var1, var3);
   }

   private static boolean b(IInventory var0, ItemStack var1, int var2, EnumFacing var3) {
      return !(var0 instanceof ISidedInventory) || ((ISidedInventory)var0).b(var2, var1, var3);
   }

   private static ItemStack c(IInventory var0, ItemStack var1, int var2, EnumFacing var3) {
      ItemStack var4 = var0.a(var2);
      if (a(var0, var1, var2, var3)) {
         boolean var5 = false;
         if (var4 == null) {
            var0.a(var2, var1);
            var1 = null;
            var5 = true;
         } else if (a(var4, var1)) {
            int var6 = var1.c() - var4.b;
            int var7 = Math.min(var1.b, var6);
            var1.b -= var7;
            var4.b += var7;
            var5 = var7 > 0;
         }

         if (var5) {
            if (var0 instanceof TileEntityHopper) {
               TileEntityHopper var8 = (TileEntityHopper)var0;
               if (var8.o()) {
                  var8.d(8);
               }

               var0.p_();
            }

            var0.p_();
         }
      }

      return var1;
   }

   private IInventory H() {
      EnumFacing var1 = BlockHopper.b(this.u());
      return b(this.z(), this.c.n() + var1.g(), this.c.o() + var1.h(), this.c.p() + var1.i());
   }

   public static IInventory b(IHopper var0) {
      return b(var0.z(), var0.A(), var0.B() + 1.0, var0.C());
   }

   public static List<EntityItem> a(World var0, double var1, double var3, double var5) {
      return var0.a(EntityItem.class, new AxisAlignedBB(var1 - 0.5, var3 - 0.5, var5 - 0.5, var1 + 0.5, var3 + 0.5, var5 + 0.5), EntitySelectors.a);
   }

   public static IInventory b(World var0, double var1, double var3, double var5) {
      Object var7 = null;
      int var8 = MathHelper.c(var1);
      int var9 = MathHelper.c(var3);
      int var10 = MathHelper.c(var5);
      BlockPos var11 = new BlockPos(var8, var9, var10);
      Block var12 = var0.p(var11).c();
      if (var12.z()) {
         TileEntity var13 = var0.s(var11);
         if (var13 instanceof IInventory) {
            var7 = (IInventory)var13;
            if (var7 instanceof TileEntityChest && var12 instanceof BlockChest) {
               var7 = ((BlockChest)var12).f(var0, var11);
            }
         }
      }

      if (var7 == null) {
         List var14 = var0.a(null, new AxisAlignedBB(var1 - 0.5, var3 - 0.5, var5 - 0.5, var1 + 0.5, var3 + 0.5, var5 + 0.5), EntitySelectors.c);
         if (var14.size() > 0) {
            var7 = (IInventory)var14.get(var0.s.nextInt(var14.size()));
         }
      }

      return (IInventory)var7;
   }

   private static boolean a(ItemStack var0, ItemStack var1) {
      if (var0.b() != var1.b()) {
         return false;
      } else if (var0.i() != var1.i()) {
         return false;
      } else {
         return var0.b > var0.c() ? false : ItemStack.a(var0, var1);
      }
   }

   @Override
   public double A() {
      return this.c.n() + 0.5;
   }

   @Override
   public double B() {
      return this.c.o() + 0.5;
   }

   @Override
   public double C() {
      return this.c.p() + 0.5;
   }

   public void d(int var1) {
      this.g = var1;
   }

   public boolean n() {
      return this.g > 0;
   }

   public boolean o() {
      return this.g <= 1;
   }

   @Override
   public String k() {
      return "minecraft:hopper";
   }

   @Override
   public Container a(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerHopper(var1, this, var2);
   }

   @Override
   public int a_(int var1) {
      return 0;
   }

   @Override
   public void b(int var1, int var2) {
   }

   @Override
   public int g() {
      return 0;
   }

   @Override
   public void l() {
      for (int var1 = 0; var1 < this.a.length; var1++) {
         this.a[var1] = null;
      }
   }
}
