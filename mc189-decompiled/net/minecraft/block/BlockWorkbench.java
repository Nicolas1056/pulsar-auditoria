package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockWorkbench extends Block {
   protected BlockWorkbench() {
      super(Material.d);
      this.a(CreativeTabs.c);
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.D) {
         return true;
      } else {
         var4.a(new BlockWorkbench.InterfaceCraftingTable(var1, var2));
         var4.b(StatList.Z);
         return true;
      }
   }

   public static class InterfaceCraftingTable implements IInteractionObject {
      private final World a;
      private final BlockPos b;

      public InterfaceCraftingTable(World var1, BlockPos var2) {
         this.a = var1;
         this.b = var2;
      }

      @Override
      public String e_() {
         return null;
      }

      @Override
      public boolean l_() {
         return false;
      }

      @Override
      public IChatComponent f_() {
         return new ChatComponentTranslation(Blocks.ai.a() + ".name");
      }

      @Override
      public Container a(InventoryPlayer var1, EntityPlayer var2) {
         return new ContainerWorkbench(var1, this.a, this.b);
      }

      @Override
      public String k() {
         return "minecraft:crafting_table";
      }
   }
}
