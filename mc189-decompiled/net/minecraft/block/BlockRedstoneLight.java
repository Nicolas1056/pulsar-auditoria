package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockRedstoneLight extends Block {
   private final boolean a;

   public BlockRedstoneLight(boolean var1) {
      super(Material.t);
      this.a = var1;
      if (var1) {
         this.a(1.0F);
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.D) {
         if (this.a && !var1.z(var2)) {
            var1.a(var2, Blocks.bJ.Q(), 2);
         } else if (!this.a && var1.z(var2)) {
            var1.a(var2, Blocks.bK.Q(), 2);
         }
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!var1.D) {
         if (this.a && !var1.z(var2)) {
            var1.a(var2, this, 4);
         } else if (!this.a && var1.z(var2)) {
            var1.a(var2, Blocks.bK.Q(), 2);
         }
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.D) {
         if (this.a && !var1.z(var2)) {
            var1.a(var2, Blocks.bJ.Q(), 2);
         }
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Item.a(Blocks.bJ);
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Item.a(Blocks.bJ);
   }

   @Override
   protected ItemStack i(IBlockState var1) {
      return new ItemStack(Blocks.bJ);
   }
}
