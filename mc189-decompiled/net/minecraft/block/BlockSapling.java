package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockSapling extends BlockBush implements IGrowable {
   public static final PropertyEnum<BlockPlanks.EnumType> a = PropertyEnum.a("type", BlockPlanks.EnumType.class);
   public static final PropertyInteger b = PropertyInteger.a("stage", 0, 1);

   protected BlockSapling() {
      this.j(this.M.b().a(a, BlockPlanks.EnumType.a).a(b, 0));
      float var1 = 0.4F;
      this.a(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, var1 * 2.0F, 0.5F + var1);
      this.a(CreativeTabs.c);
   }

   @Override
   public String f() {
      return StatCollector.a(this.a() + "." + BlockPlanks.EnumType.a.d() + ".name");
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.D) {
         super.b(var1, var2, var3, var4);
         if (var1.l(var2.a()) >= 9 && var4.nextInt(7) == 0) {
            this.d(var1, var2, var3, var4);
         }
      }
   }

   public void d(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (var3.b(b) == 0) {
         var1.a(var2, var3.a(b), 4);
      } else {
         this.e(var1, var2, var3, var4);
      }
   }

   public void e(World var1, BlockPos var2, IBlockState var3, Random var4) {
      Object var5 = var4.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true);
      int var6 = 0;
      int var7 = 0;
      boolean var8 = false;
      switch ((BlockPlanks.EnumType)var3.b(a)) {
         case b:
            label68:
            for (var6 = 0; var6 >= -1; var6--) {
               for (var7 = 0; var7 >= -1; var7--) {
                  if (this.a(var1, var2, var6, var7, BlockPlanks.EnumType.b)) {
                     var5 = new WorldGenMegaPineTree(false, var4.nextBoolean());
                     var8 = true;
                     break label68;
                  }
               }
            }

            if (!var8) {
               var7 = 0;
               var6 = 0;
               var5 = new WorldGenTaiga2(true);
            }
            break;
         case c:
            var5 = new WorldGenForest(true, false);
            break;
         case d:
            IBlockState var9 = Blocks.r.Q().a(BlockOldLog.b, BlockPlanks.EnumType.d);
            IBlockState var10 = Blocks.t.Q().a(BlockOldLeaf.Q, BlockPlanks.EnumType.d).a(BlockLeaves.b, false);

            label82:
            for (var6 = 0; var6 >= -1; var6--) {
               for (var7 = 0; var7 >= -1; var7--) {
                  if (this.a(var1, var2, var6, var7, BlockPlanks.EnumType.d)) {
                     var5 = new WorldGenMegaJungle(true, 10, 20, var9, var10);
                     var8 = true;
                     break label82;
                  }
               }
            }

            if (!var8) {
               var7 = 0;
               var6 = 0;
               var5 = new WorldGenTrees(true, 4 + var4.nextInt(7), var9, var10, false);
            }
            break;
         case e:
            var5 = new WorldGenSavannaTree(true);
            break;
         case f:
            label96:
            for (var6 = 0; var6 >= -1; var6--) {
               for (var7 = 0; var7 >= -1; var7--) {
                  if (this.a(var1, var2, var6, var7, BlockPlanks.EnumType.f)) {
                     var5 = new WorldGenCanopyTree(true);
                     var8 = true;
                     break label96;
                  }
               }
            }

            if (!var8) {
               return;
            }
         case a:
      }

      IBlockState var11 = Blocks.a.Q();
      if (var8) {
         var1.a(var2.a(var6, 0, var7), var11, 4);
         var1.a(var2.a(var6 + 1, 0, var7), var11, 4);
         var1.a(var2.a(var6, 0, var7 + 1), var11, 4);
         var1.a(var2.a(var6 + 1, 0, var7 + 1), var11, 4);
      } else {
         var1.a(var2, var11, 4);
      }

      if (!((WorldGenerator)var5).b(var1, var4, var2.a(var6, 0, var7))) {
         if (var8) {
            var1.a(var2.a(var6, 0, var7), var3, 4);
            var1.a(var2.a(var6 + 1, 0, var7), var3, 4);
            var1.a(var2.a(var6, 0, var7 + 1), var3, 4);
            var1.a(var2.a(var6 + 1, 0, var7 + 1), var3, 4);
         } else {
            var1.a(var2, var3, 4);
         }
      }
   }

   private boolean a(World var1, BlockPos var2, int var3, int var4, BlockPlanks.EnumType var5) {
      return this.a(var1, var2.a(var3, 0, var4), var5)
         && this.a(var1, var2.a(var3 + 1, 0, var4), var5)
         && this.a(var1, var2.a(var3, 0, var4 + 1), var5)
         && this.a(var1, var2.a(var3 + 1, 0, var4 + 1), var5);
   }

   public boolean a(World var1, BlockPos var2, BlockPlanks.EnumType var3) {
      IBlockState var4 = var1.p(var2);
      return var4.c() == this && var4.b(a) == var3;
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (BlockPlanks.EnumType var7 : BlockPlanks.EnumType.values()) {
         var3.add(new ItemStack(var1, 1, var7.a()));
      }
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return true;
   }

   @Override
   public boolean a(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return var1.s.nextFloat() < 0.45;
   }

   @Override
   public void b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      this.d(var1, var3, var4, var2);
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, BlockPlanks.EnumType.a(var1 & 7)).a(b, (var1 & 8) >> 3);
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(a).a();
      return var2 | var1.b(b) << 3;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b);
   }
}
