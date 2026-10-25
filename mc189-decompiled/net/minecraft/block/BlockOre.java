package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockOre extends Block {
   public BlockOre() {
      this(Material.e.r());
   }

   public BlockOre(MapColor var1) {
      super(Material.e, var1);
      this.a(CreativeTabs.b);
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      if (this == Blocks.q) {
         return Items.h;
      } else if (this == Blocks.ag) {
         return Items.i;
      } else if (this == Blocks.x) {
         return Items.aW;
      } else if (this == Blocks.bP) {
         return Items.bO;
      } else {
         return this == Blocks.co ? Items.cg : Item.a(this);
      }
   }

   @Override
   public int a(Random var1) {
      return this == Blocks.x ? 4 + var1.nextInt(5) : 1;
   }

   @Override
   public int a(int var1, Random var2) {
      if (var1 > 0 && Item.a(this) != this.a((IBlockState)this.P().a().iterator().next(), var2, var1)) {
         int var3 = var2.nextInt(var1 + 2) - 1;
         if (var3 < 0) {
            var3 = 0;
         }

         return this.a(var2) * (var3 + 1);
      } else {
         return this.a(var2);
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      super.a(var1, var2, var3, var4, var5);
      if (this.a(var3, var1.s, var5) != Item.a(this)) {
         int var6 = 0;
         if (this == Blocks.q) {
            var6 = MathHelper.a(var1.s, 0, 2);
         } else if (this == Blocks.ag) {
            var6 = MathHelper.a(var1.s, 3, 7);
         } else if (this == Blocks.bP) {
            var6 = MathHelper.a(var1.s, 3, 7);
         } else if (this == Blocks.x) {
            var6 = MathHelper.a(var1.s, 2, 5);
         } else if (this == Blocks.co) {
            var6 = MathHelper.a(var1.s, 2, 5);
         }

         this.b(var1, var2, var6);
      }
   }

   @Override
   public int j(World var1, BlockPos var2) {
      return 0;
   }

   @Override
   public int a(IBlockState var1) {
      return this == Blocks.x ? EnumDyeColor.l.b() : 0;
   }
}
