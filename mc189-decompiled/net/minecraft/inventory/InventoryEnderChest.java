package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityEnderChest;

public class InventoryEnderChest extends InventoryBasic {
   private TileEntityEnderChest a;

   public InventoryEnderChest() {
      super("container.enderchest", false, 27);
   }

   public void a(TileEntityEnderChest var1) {
      this.a = var1;
   }

   public void a(NBTTagList var1) {
      for (int var2 = 0; var2 < this.o_(); var2++) {
         this.a(var2, null);
      }

      for (int var5 = 0; var5 < var1.c(); var5++) {
         NBTTagCompound var3 = var1.b(var5);
         int var4 = var3.d("Slot") & 255;
         if (var4 >= 0 && var4 < this.o_()) {
            this.a(var4, ItemStack.a(var3));
         }
      }
   }

   public NBTTagList h() {
      NBTTagList var1 = new NBTTagList();

      for (int var2 = 0; var2 < this.o_(); var2++) {
         ItemStack var3 = this.a(var2);
         if (var3 != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            var4.a("Slot", (byte)var2);
            var3.b(var4);
            var1.a(var4);
         }
      }

      return var1;
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return this.a != null && !this.a.a(var1) ? false : super.a(var1);
   }

   @Override
   public void b(EntityPlayer var1) {
      if (this.a != null) {
         this.a.b();
      }

      super.b(var1);
   }

   @Override
   public void c(EntityPlayer var1) {
      if (this.a != null) {
         this.a.d();
      }

      super.c(var1);
      this.a = null;
   }
}
