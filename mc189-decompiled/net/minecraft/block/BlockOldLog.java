package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockOldLog extends BlockLog {
   public static final PropertyEnum<BlockPlanks.EnumType> b = PropertyEnum.a("variant", BlockPlanks.EnumType.class, new Predicate<BlockPlanks.EnumType>() {
      public boolean a(BlockPlanks.EnumType var1) {
         return var1.a() < 4;
      }
   });

   public BlockOldLog() {
      this.j(this.M.b().a(b, BlockPlanks.EnumType.a).a(a, BlockLog.EnumAxis.b));
   }

   @Override
   public MapColor g(IBlockState var1) {
      BlockPlanks.EnumType var2 = var1.b(b);
      switch ((BlockLog.EnumAxis)var1.b(a)) {
         case a:
         case c:
         case d:
         default:
            switch (var2) {
               case a:
               default:
                  return BlockPlanks.EnumType.b.c();
               case b:
                  return BlockPlanks.EnumType.f.c();
               case c:
                  return MapColor.p;
               case d:
                  return BlockPlanks.EnumType.b.c();
            }
         case b:
            return var2.c();
      }
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      var3.add(new ItemStack(var1, 1, BlockPlanks.EnumType.a.a()));
      var3.add(new ItemStack(var1, 1, BlockPlanks.EnumType.b.a()));
      var3.add(new ItemStack(var1, 1, BlockPlanks.EnumType.c.a()));
      var3.add(new ItemStack(var1, 1, BlockPlanks.EnumType.d.a()));
   }

   @Override
   public IBlockState a(int var1) {
      IBlockState var2 = this.Q().a(b, BlockPlanks.EnumType.a((var1 & 3) % 4));
      switch (var1 & 12) {
         case 0:
            var2 = var2.a(a, BlockLog.EnumAxis.b);
            break;
         case 4:
            var2 = var2.a(a, BlockLog.EnumAxis.a);
            break;
         case 8:
            var2 = var2.a(a, BlockLog.EnumAxis.c);
            break;
         default:
            var2 = var2.a(a, BlockLog.EnumAxis.d);
      }

      return var2;
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(b).a();
      switch ((BlockLog.EnumAxis)var1.b(a)) {
         case a:
            var2 |= 4;
            break;
         case c:
            var2 |= 8;
            break;
         case d:
            var2 |= 12;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, b, a);
   }

   @Override
   protected ItemStack i(IBlockState var1) {
      return new ItemStack(Item.a(this), 1, var1.b(b).a());
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(b).a();
   }
}
