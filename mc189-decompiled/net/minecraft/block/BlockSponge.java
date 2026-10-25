package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;

public class BlockSponge extends Block {
   public static final PropertyBool a = PropertyBool.a("wet");

   protected BlockSponge() {
      super(Material.m);
      this.j(this.M.b().a(a, false));
      this.a(CreativeTabs.b);
   }

   @Override
   public String f() {
      return StatCollector.a(this.a() + ".dry.name");
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(a) ? 1 : 0;
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      this.e(var1, var2, var3);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.e(var1, var2, var3);
      super.a(var1, var2, var3, var4);
   }

   protected void e(World var1, BlockPos var2, IBlockState var3) {
      if (!var3.b(a) && this.e(var1, var2)) {
         var1.a(var2, var3.a(a, true), 2);
         var1.b(2001, var2, Block.a(Blocks.j));
      }
   }

   private boolean e(World var1, BlockPos var2) {
      LinkedList var3 = Lists.newLinkedList();
      ArrayList var4 = Lists.newArrayList();
      var3.add(new Tuple<>(var2, 0));
      int var5 = 0;

      while (!var3.isEmpty()) {
         Tuple var6 = (Tuple)var3.poll();
         BlockPos var7 = (BlockPos)var6.a();
         int var8 = (Integer)var6.b();

         for (EnumFacing var12 : EnumFacing.values()) {
            BlockPos var13 = var7.a(var12);
            if (var1.p(var13).c().t() == Material.h) {
               var1.a(var13, Blocks.a.Q(), 2);
               var4.add(var13);
               var5++;
               if (var8 < 6) {
                  var3.add(new Tuple<>(var13, var8 + 1));
               }
            }
         }

         if (var5 > 64) {
            break;
         }
      }

      for (BlockPos var15 : var4) {
         var1.c(var15, Blocks.a);
      }

      return var5 > 0;
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      var3.add(new ItemStack(var1, 1, 0));
      var3.add(new ItemStack(var1, 1, 1));
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, (var1 & 1) == 1);
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a) ? 1 : 0;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (var3.b(a)) {
         EnumFacing var5 = EnumFacing.a(var4);
         if (var5 != EnumFacing.b && !World.a(var1, var2.a(var5))) {
            double var6 = var2.n();
            double var8 = var2.o();
            double var10 = var2.p();
            if (var5 == EnumFacing.a) {
               var8 -= 0.05;
               var6 += var4.nextDouble();
               var10 += var4.nextDouble();
            } else {
               var8 += var4.nextDouble() * 0.8;
               if (var5.k() == EnumFacing.Axis.a) {
                  var10 += var4.nextDouble();
                  if (var5 == EnumFacing.f) {
                     var6++;
                  } else {
                     var6 += 0.05;
                  }
               } else {
                  var6 += var4.nextDouble();
                  if (var5 == EnumFacing.d) {
                     var10++;
                  } else {
                     var10 += 0.05;
                  }
               }
            }

            var1.a(EnumParticleTypes.s, var6, var8, var10, 0.0, 0.0, 0.0);
         }
      }
   }
}
