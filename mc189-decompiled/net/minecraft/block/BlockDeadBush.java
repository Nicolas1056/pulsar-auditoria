package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockDeadBush extends BlockBush {
   protected BlockDeadBush() {
      super(Material.l);
      float var1 = 0.4F;
      this.a(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.8F, 0.5F + var1);
   }

   @Override
   public MapColor g(IBlockState var1) {
      return MapColor.o;
   }

   @Override
   protected boolean c(Block var1) {
      return var1 == Blocks.m || var1 == Blocks.cz || var1 == Blocks.cu || var1 == Blocks.d;
   }

   @Override
   public boolean a(World var1, BlockPos var2) {
      return true;
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return null;
   }

   @Override
   public void a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      if (!var1.D && var2.bZ() != null && var2.bZ().b() == Items.be) {
         var2.b(StatList.ab[Block.a(this)]);
         a(var1, var3, new ItemStack(Blocks.I, 1, 0));
      } else {
         super.a(var1, var2, var3, var4, var5);
      }
   }
}
