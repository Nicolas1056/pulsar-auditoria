package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IWorldNameable;

public interface IInventory extends IWorldNameable {
   int o_();

   ItemStack a(int var1);

   ItemStack a(int var1, int var2);

   ItemStack b(int var1);

   void a(int var1, ItemStack var2);

   int q_();

   void p_();

   boolean a(EntityPlayer var1);

   void b(EntityPlayer var1);

   void c(EntityPlayer var1);

   boolean b(int var1, ItemStack var2);

   int a_(int var1);

   void b(int var1, int var2);

   int g();

   void l();
}
