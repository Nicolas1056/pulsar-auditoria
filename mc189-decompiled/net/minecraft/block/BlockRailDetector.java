package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRailDetector extends BlockRailBase {
   public static final PropertyEnum<BlockRailBase.EnumRailDirection> b = PropertyEnum.a(
      "shape",
      BlockRailBase.EnumRailDirection.class,
      new Predicate<BlockRailBase.EnumRailDirection>() {
         public boolean a(BlockRailBase.EnumRailDirection var1) {
            return var1 != BlockRailBase.EnumRailDirection.j
               && var1 != BlockRailBase.EnumRailDirection.i
               && var1 != BlockRailBase.EnumRailDirection.g
               && var1 != BlockRailBase.EnumRailDirection.h;
         }
      }
   );
   public static final PropertyBool N = PropertyBool.a("powered");

   public BlockRailDetector() {
      super(true);
      this.j(this.M.b().a(N, false).a(b, BlockRailBase.EnumRailDirection.a));
      this.a(true);
   }

   @Override
   public int a(World var1) {
      return 20;
   }

   @Override
   public boolean i() {
      return true;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      if (!var1.D) {
         if (!var3.b(N)) {
            this.e(var1, var2, var3);
         }
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.D && var3.b(N)) {
         this.e(var1, var2, var3);
      }
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return var3.b(N) ? 15 : 0;
   }

   @Override
   public int b(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      if (!var3.b(N)) {
         return 0;
      } else {
         return var4 == EnumFacing.b ? 15 : 0;
      }
   }

   private void e(World var1, BlockPos var2, IBlockState var3) {
      boolean var4 = var3.b(N);
      boolean var5 = false;
      List var6 = this.a(var1, var2, EntityMinecart.class);
      if (!var6.isEmpty()) {
         var5 = true;
      }

      if (var5 && !var4) {
         var1.a(var2, var3.a(N, true), 3);
         var1.c(var2, this);
         var1.c(var2.b(), this);
         var1.b(var2, var2);
      }

      if (!var5 && var4) {
         var1.a(var2, var3.a(N, false), 3);
         var1.c(var2, this);
         var1.c(var2.b(), this);
         var1.b(var2, var2);
      }

      if (var5) {
         var1.a(var2, this, this.a(var1));
      }

      var1.e(var2, this);
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      super.c(var1, var2, var3);
      this.e(var1, var2, var3);
   }

   @Override
   public IProperty<BlockRailBase.EnumRailDirection> n() {
      return b;
   }

   @Override
   public boolean O() {
      return true;
   }

   @Override
   public int l(World var1, BlockPos var2) {
      if (var1.p(var2).b(N)) {
         List var3 = this.a(var1, var2, EntityMinecartCommandBlock.class);
         if (!var3.isEmpty()) {
            return ((EntityMinecartCommandBlock)var3.get(0)).j().j();
         }

         List var4 = this.a(var1, var2, EntityMinecart.class, EntitySelectors.c);
         if (!var4.isEmpty()) {
            return Container.b((IInventory)var4.get(0));
         }
      }

      return 0;
   }

   protected <T extends EntityMinecart> List<T> a(World var1, BlockPos var2, Class<T> var3, Predicate<Entity>... var4) {
      AxisAlignedBB var5 = this.a(var2);
      return var4.length != 1 ? var1.a(var3, var5) : var1.a(var3, var5, var4[0]);
   }

   private AxisAlignedBB a(BlockPos var1) {
      float var2 = 0.2F;
      return new AxisAlignedBB(var1.n() + 0.2F, var1.o(), var1.p() + 0.2F, var1.n() + 1 - 0.2F, var1.o() + 1 - 0.2F, var1.p() + 1 - 0.2F);
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(b, BlockRailBase.EnumRailDirection.a(var1 & 7)).a(N, (var1 & 8) > 0);
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(b).a();
      if (var1.b(N)) {
         var2 |= 8;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, b, N);
   }
}
