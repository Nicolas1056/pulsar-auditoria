package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockRailPowered extends BlockRailBase {
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

   protected BlockRailPowered() {
      super(true);
      this.j(this.M.b().a(b, BlockRailBase.EnumRailDirection.a).a(N, false));
   }

   protected boolean a(World var1, BlockPos var2, IBlockState var3, boolean var4, int var5) {
      if (var5 >= 8) {
         return false;
      } else {
         int var6 = var2.n();
         int var7 = var2.o();
         int var8 = var2.p();
         boolean var9 = true;
         BlockRailBase.EnumRailDirection var10 = var3.b(b);
         switch (var10) {
            case a:
               if (var4) {
                  var8++;
               } else {
                  var8--;
               }
               break;
            case b:
               if (var4) {
                  var6--;
               } else {
                  var6++;
               }
               break;
            case c:
               if (var4) {
                  var6--;
               } else {
                  var6++;
                  var7++;
                  var9 = false;
               }

               var10 = BlockRailBase.EnumRailDirection.b;
               break;
            case d:
               if (var4) {
                  var6--;
                  var7++;
                  var9 = false;
               } else {
                  var6++;
               }

               var10 = BlockRailBase.EnumRailDirection.b;
               break;
            case e:
               if (var4) {
                  var8++;
               } else {
                  var8--;
                  var7++;
                  var9 = false;
               }

               var10 = BlockRailBase.EnumRailDirection.a;
               break;
            case f:
               if (var4) {
                  var8++;
                  var7++;
                  var9 = false;
               } else {
                  var8--;
               }

               var10 = BlockRailBase.EnumRailDirection.a;
         }

         return this.a(var1, new BlockPos(var6, var7, var8), var4, var5, var10)
            ? true
            : var9 && this.a(var1, new BlockPos(var6, var7 - 1, var8), var4, var5, var10);
      }
   }

   protected boolean a(World var1, BlockPos var2, boolean var3, int var4, BlockRailBase.EnumRailDirection var5) {
      IBlockState var6 = var1.p(var2);
      if (var6.c() != this) {
         return false;
      } else {
         BlockRailBase.EnumRailDirection var7 = var6.b(b);
         if (var5 != BlockRailBase.EnumRailDirection.b
            || var7 != BlockRailBase.EnumRailDirection.a && var7 != BlockRailBase.EnumRailDirection.e && var7 != BlockRailBase.EnumRailDirection.f) {
            if (var5 != BlockRailBase.EnumRailDirection.a
               || var7 != BlockRailBase.EnumRailDirection.b && var7 != BlockRailBase.EnumRailDirection.c && var7 != BlockRailBase.EnumRailDirection.d) {
               if (!var6.b(N)) {
                  return false;
               } else {
                  return var1.z(var2) ? true : this.a(var1, var2, var6, var3, var4 + 1);
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Override
   protected void b(World var1, BlockPos var2, IBlockState var3, Block var4) {
      boolean var5 = var3.b(N);
      boolean var6 = var1.z(var2) || this.a(var1, var2, var3, true, 0) || this.a(var1, var2, var3, false, 0);
      if (var6 != var5) {
         var1.a(var2, var3.a(N, var6), 3);
         var1.c(var2.b(), this);
         if (var3.b(b).c()) {
            var1.c(var2.a(), this);
         }
      }
   }

   @Override
   public IProperty<BlockRailBase.EnumRailDirection> n() {
      return b;
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
