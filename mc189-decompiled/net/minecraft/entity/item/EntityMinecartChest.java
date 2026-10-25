package net.minecraft.entity.item;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityMinecartChest extends EntityMinecartContainer {
   public EntityMinecartChest(World var1) {
      super(var1);
   }

   public EntityMinecartChest(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   @Override
   public void a(DamageSource var1) {
      super.a(var1);
      if (this.o.Q().b("doEntityDrops")) {
         this.a(Item.a(Blocks.ae), 1, 0.0F);
      }
   }

   @Override
   public int o_() {
      return 27;
   }

   @Override
   public EntityMinecart.EnumMinecartType s() {
      return EntityMinecart.EnumMinecartType.b;
   }

   @Override
   public IBlockState u() {
      return Blocks.ae.Q().a(BlockChest.a, EnumFacing.c);
   }

   @Override
   public int w() {
      return 8;
   }

   @Override
   public String k() {
      return "minecraft:chest";
   }

   @Override
   public Container a(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerChest(var1, this, var2);
   }
}
