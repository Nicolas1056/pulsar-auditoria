package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class EntityItemFrame extends EntityHanging {
   private float c = 1.0F;

   public EntityItemFrame(World var1) {
      super(var1);
   }

   public EntityItemFrame(World var1, BlockPos var2, EnumFacing var3) {
      super(var1, var2);
      this.a(var3);
   }

   @Override
   protected void h() {
      this.H().a(8, 5);
      this.H().a(9, Byte.valueOf((byte)0));
   }

   @Override
   public float ao() {
      return 0.0F;
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else if (!var1.c() && this.o() != null) {
         if (!this.o.D) {
            this.a(var1.j(), false);
            this.a(null);
         }

         return true;
      } else {
         return super.a(var1, var2);
      }
   }

   @Override
   public int l() {
      return 12;
   }

   @Override
   public int m() {
      return 12;
   }

   @Override
   public boolean a(double var1) {
      double var3 = 16.0;
      var3 *= 64.0 * this.j;
      return var1 < var3 * var3;
   }

   @Override
   public void b(Entity var1) {
      this.a(var1, true);
   }

   public void a(Entity var1, boolean var2) {
      if (this.o.Q().b("doEntityDrops")) {
         ItemStack var3 = this.o();
         if (var1 instanceof EntityPlayer) {
            EntityPlayer var4 = (EntityPlayer)var1;
            if (var4.bA.d) {
               this.b(var3);
               return;
            }
         }

         if (var2) {
            this.a(new ItemStack(Items.bP), 0.0F);
         }

         if (var3 != null && this.V.nextFloat() < this.c) {
            var3 = var3.k();
            this.b(var3);
            this.a(var3, 0.0F);
         }
      }
   }

   private void b(ItemStack var1) {
      if (var1 != null) {
         if (var1.b() == Items.bd) {
            MapData var2 = ((ItemMap)var1.b()).a(var1, this.o);
            var2.h.remove("frame-" + this.F());
         }

         var1.a(null);
      }
   }

   public ItemStack o() {
      return this.H().f(8);
   }

   public void a(ItemStack var1) {
      this.a(var1, true);
   }

   private void a(ItemStack var1, boolean var2) {
      if (var1 != null) {
         var1 = var1.k();
         var1.b = 1;
         var1.a(this);
      }

      this.H().b(8, var1);
      this.H().i(8);
      if (var2 && this.a != null) {
         this.o.e(this.a, Blocks.a);
      }
   }

   public int p() {
      return this.H().a(9);
   }

   public void a(int var1) {
      this.a(var1, true);
   }

   private void a(int var1, boolean var2) {
      this.H().b(9, (byte)(var1 % 8));
      if (var2 && this.a != null) {
         this.o.e(this.a, Blocks.a);
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      if (this.o() != null) {
         var1.a("Item", this.o().b(new NBTTagCompound()));
         var1.a("ItemRotation", (byte)this.p());
         var1.a("ItemDropChance", this.c);
      }

      super.b(var1);
   }

   @Override
   public void a(NBTTagCompound var1) {
      NBTTagCompound var2 = var1.m("Item");
      if (var2 != null && !var2.c_()) {
         this.a(ItemStack.a(var2), false);
         this.a(var1.d("ItemRotation"), false);
         if (var1.b("ItemDropChance", 99)) {
            this.c = var1.h("ItemDropChance");
         }

         if (var1.c("Direction")) {
            this.a(this.p() * 2, false);
         }
      }

      super.a(var1);
   }

   @Override
   public boolean e(EntityPlayer var1) {
      if (this.o() == null) {
         ItemStack var2 = var1.bA();
         if (var2 != null && !this.o.D) {
            this.a(var2);
            if (!var1.bA.d && --var2.b <= 0) {
               var1.bi.a(var1.bi.c, null);
            }
         }
      } else if (!this.o.D) {
         this.a(this.p() + 1);
      }

      return true;
   }

   public int q() {
      return this.o() == null ? 0 : this.p() % 8 + 1;
   }
}
