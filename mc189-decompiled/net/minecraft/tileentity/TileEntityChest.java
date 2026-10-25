package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityChest extends TileEntityLockable implements ITickable, IInventory {
   private ItemStack[] m = new ItemStack[27];
   public boolean a;
   public TileEntityChest f;
   public TileEntityChest g;
   public TileEntityChest h;
   public TileEntityChest i;
   public float j;
   public float k;
   public int l;
   private int n;
   private int o;
   private String p;

   public TileEntityChest() {
      this.o = -1;
   }

   public TileEntityChest(int var1) {
      this.o = var1;
   }

   @Override
   public int o_() {
      return 27;
   }

   @Override
   public ItemStack a(int var1) {
      return this.m[var1];
   }

   @Override
   public ItemStack a(int var1, int var2) {
      if (this.m[var1] != null) {
         if (this.m[var1].b <= var2) {
            ItemStack var4 = this.m[var1];
            this.m[var1] = null;
            this.p_();
            return var4;
         } else {
            ItemStack var3 = this.m[var1].a(var2);
            if (this.m[var1].b == 0) {
               this.m[var1] = null;
            }

            this.p_();
            return var3;
         }
      } else {
         return null;
      }
   }

   @Override
   public ItemStack b(int var1) {
      if (this.m[var1] != null) {
         ItemStack var2 = this.m[var1];
         this.m[var1] = null;
         return var2;
      } else {
         return null;
      }
   }

   @Override
   public void a(int var1, ItemStack var2) {
      this.m[var1] = var2;
      if (var2 != null && var2.b > this.q_()) {
         var2.b = this.q_();
      }

      this.p_();
   }

   @Override
   public String e_() {
      return this.l_() ? this.p : "container.chest";
   }

   @Override
   public boolean l_() {
      return this.p != null && this.p.length() > 0;
   }

   public void a(String var1) {
      this.p = var1;
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      NBTTagList var2 = var1.c("Items", 10);
      this.m = new ItemStack[this.o_()];
      if (var1.b("CustomName", 8)) {
         this.p = var1.j("CustomName");
      }

      for (int var3 = 0; var3 < var2.c(); var3++) {
         NBTTagCompound var4 = var2.b(var3);
         int var5 = var4.d("Slot") & 255;
         if (var5 >= 0 && var5 < this.m.length) {
            this.m[var5] = ItemStack.a(var4);
         }
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      NBTTagList var2 = new NBTTagList();

      for (int var3 = 0; var3 < this.m.length; var3++) {
         if (this.m[var3] != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            var4.a("Slot", (byte)var3);
            this.m[var3].b(var4);
            var2.a(var4);
         }
      }

      var1.a("Items", var2);
      if (this.l_()) {
         var1.a("CustomName", this.p);
      }
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
   public void E() {
      super.E();
      this.a = false;
   }

   private void a(TileEntityChest var1, EnumFacing var2) {
      if (var1.x()) {
         this.a = false;
      } else if (this.a) {
         switch (var2) {
            case c:
               if (this.f != var1) {
                  this.a = false;
               }
               break;
            case d:
               if (this.i != var1) {
                  this.a = false;
               }
               break;
            case f:
               if (this.g != var1) {
                  this.a = false;
               }
               break;
            case e:
               if (this.h != var1) {
                  this.a = false;
               }
         }
      }
   }

   public void m() {
      if (!this.a) {
         this.a = true;
         this.h = this.a(EnumFacing.e);
         this.g = this.a(EnumFacing.f);
         this.f = this.a(EnumFacing.c);
         this.i = this.a(EnumFacing.d);
      }
   }

   protected TileEntityChest a(EnumFacing var1) {
      BlockPos var2 = this.c.a(var1);
      if (this.b(var2)) {
         TileEntity var3 = this.b.s(var2);
         if (var3 instanceof TileEntityChest) {
            TileEntityChest var4 = (TileEntityChest)var3;
            var4.a(this, var1.d());
            return var4;
         }
      }

      return null;
   }

   private boolean b(BlockPos var1) {
      if (this.b == null) {
         return false;
      } else {
         Block var2 = this.b.p(var1).c();
         return var2 instanceof BlockChest && ((BlockChest)var2).b == this.n();
      }
   }

   @Override
   public void c() {
      this.m();
      int var1 = this.c.n();
      int var2 = this.c.o();
      int var3 = this.c.p();
      this.n++;
      if (!this.b.D && this.l != 0 && (this.n + var1 + var2 + var3) % 200 == 0) {
         this.l = 0;
         float var4 = 5.0F;

         for (EntityPlayer var7 : this.b
            .a(EntityPlayer.class, new AxisAlignedBB(var1 - var4, var2 - var4, var3 - var4, var1 + 1 + var4, var2 + 1 + var4, var3 + 1 + var4))) {
            if (var7.bk instanceof ContainerChest) {
               IInventory var8 = ((ContainerChest)var7.bk).e();
               if (var8 == this || var8 instanceof InventoryLargeChest && ((InventoryLargeChest)var8).a(this)) {
                  this.l++;
               }
            }
         }
      }

      this.k = this.j;
      float var11 = 0.1F;
      if (this.l > 0 && this.j == 0.0F && this.f == null && this.h == null) {
         double var12 = var1 + 0.5;
         double var15 = var3 + 0.5;
         if (this.i != null) {
            var15 += 0.5;
         }

         if (this.g != null) {
            var12 += 0.5;
         }

         this.b.a(var12, var2 + 0.5, var15, "random.chestopen", 0.5F, this.b.s.nextFloat() * 0.1F + 0.9F);
      }

      if (this.l == 0 && this.j > 0.0F || this.l > 0 && this.j < 1.0F) {
         float var13 = this.j;
         if (this.l > 0) {
            this.j += var11;
         } else {
            this.j -= var11;
         }

         if (this.j > 1.0F) {
            this.j = 1.0F;
         }

         float var14 = 0.5F;
         if (this.j < var14 && var13 >= var14 && this.f == null && this.h == null) {
            double var16 = var1 + 0.5;
            double var9 = var3 + 0.5;
            if (this.i != null) {
               var9 += 0.5;
            }

            if (this.g != null) {
               var16 += 0.5;
            }

            this.b.a(var16, var2 + 0.5, var9, "random.chestclosed", 0.5F, this.b.s.nextFloat() * 0.1F + 0.9F);
         }

         if (this.j < 0.0F) {
            this.j = 0.0F;
         }
      }
   }

   @Override
   public boolean c(int var1, int var2) {
      if (var1 == 1) {
         this.l = var2;
         return true;
      } else {
         return super.c(var1, var2);
      }
   }

   @Override
   public void b(EntityPlayer var1) {
      if (!var1.v()) {
         if (this.l < 0) {
            this.l = 0;
         }

         this.l++;
         this.b.c(this.c, this.w(), 1, this.l);
         this.b.c(this.c, this.w());
         this.b.c(this.c.b(), this.w());
      }
   }

   @Override
   public void c(EntityPlayer var1) {
      if (!var1.v() && this.w() instanceof BlockChest) {
         this.l--;
         this.b.c(this.c, this.w(), 1, this.l);
         this.b.c(this.c, this.w());
         this.b.c(this.c.b(), this.w());
      }
   }

   @Override
   public boolean b(int var1, ItemStack var2) {
      return true;
   }

   @Override
   public void y() {
      super.y();
      this.E();
      this.m();
   }

   public int n() {
      if (this.o == -1) {
         if (this.b == null || !(this.w() instanceof BlockChest)) {
            return 0;
         }

         this.o = ((BlockChest)this.w()).b;
      }

      return this.o;
   }

   @Override
   public String k() {
      return "minecraft:chest";
   }

   @Override
   public Container a(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerChest(var1, this, var2);
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
      for (int var1 = 0; var1 < this.m.length; var1++) {
         this.m[var1] = null;
      }
   }
}
