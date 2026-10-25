package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOldLeaf extends BlockLeaves {
   public static final PropertyEnum<BlockPlanks.EnumType> Q = PropertyEnum.a("variant", BlockPlanks.EnumType.class, new Predicate<BlockPlanks.EnumType>() {
      public boolean a(BlockPlanks.EnumType var1) {
         return var1.a() < 4;
      }
   });

   public BlockOldLeaf() {
      this.j(this.M.b().a(Q, BlockPlanks.EnumType.a).a(b, true).a(a, true));
   }

   @Override
   public int h(IBlockState var1) {
      if (var1.c() != this) {
         return super.h(var1);
      } else {
         BlockPlanks.EnumType var2 = var1.b(Q);
         if (var2 == BlockPlanks.EnumType.b) {
            return ColorizerFoliage.a();
         } else {
            return var2 == BlockPlanks.EnumType.c ? ColorizerFoliage.b() : super.h(var1);
         }
      }
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, int var3) {
      IBlockState var4 = var1.p(var2);
      if (var4.c() == this) {
         BlockPlanks.EnumType var5 = var4.b(Q);
         if (var5 == BlockPlanks.EnumType.b) {
            return ColorizerFoliage.a();
         }

         if (var5 == BlockPlanks.EnumType.c) {
            return ColorizerFoliage.b();
         }
      }

      return super.a(var1, var2, var3);
   }

   @Override
   protected void a(World var1, BlockPos var2, IBlockState var3, int var4) {
      if (var3.b(Q) == BlockPlanks.EnumType.a && var1.s.nextInt(var4) == 0) {
         a(var1, var2, new ItemStack(Items.e, 1, 0));
      }
   }

   @Override
   protected int d(IBlockState var1) {
      return var1.b(Q) == BlockPlanks.EnumType.d ? 40 : super.d(var1);
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      var3.add(new ItemStack(var1, 1, BlockPlanks.EnumType.a.a()));
      var3.add(new ItemStack(var1, 1, BlockPlanks.EnumType.b.a()));
      var3.add(new ItemStack(var1, 1, BlockPlanks.EnumType.c.a()));
      var3.add(new ItemStack(var1, 1, BlockPlanks.EnumType.d.a()));
   }

   @Override
   protected ItemStack i(IBlockState var1) {
      return new ItemStack(Item.a(this), 1, var1.b(Q).a());
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(Q, this.b(var1)).a(a, (var1 & 4) == 0).a(b, (var1 & 8) > 0);
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(Q).a();
      if (!var1.b(a)) {
         var2 |= 4;
      }

      if (var1.b(b)) {
         var2 |= 8;
      }

      return var2;
   }

   @Override
   public BlockPlanks.EnumType b(int var1) {
      return BlockPlanks.EnumType.a((var1 & 3) % 4);
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, Q, b, a);
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(Q).a();
   }

   @Override
   public void a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      if (!var1.D && var2.bZ() != null && var2.bZ().b() == Items.be) {
         var2.b(StatList.ab[Block.a(this)]);
         a(var1, var3, new ItemStack(Item.a(this), 1, var4.b(Q).a()));
      } else {
         super.a(var1, var2, var3, var4, var5);
      }
   }
}
