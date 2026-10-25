package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTripWire extends Block {
   public static final PropertyBool a = PropertyBool.a("powered");
   public static final PropertyBool b = PropertyBool.a("suspended");
   public static final PropertyBool N = PropertyBool.a("attached");
   public static final PropertyBool O = PropertyBool.a("disarmed");
   public static final PropertyBool P = PropertyBool.a("north");
   public static final PropertyBool Q = PropertyBool.a("east");
   public static final PropertyBool R = PropertyBool.a("south");
   public static final PropertyBool S = PropertyBool.a("west");

   public BlockTripWire() {
      super(Material.q);
      this.j(this.M.b().a(a, false).a(b, false).a(N, false).a(O, false).a(P, false).a(Q, false).a(R, false).a(S, false));
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
      this.a(true);
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1.a(P, c(var2, var3, var1, EnumFacing.c))
         .a(Q, c(var2, var3, var1, EnumFacing.f))
         .a(R, c(var2, var3, var1, EnumFacing.d))
         .a(S, c(var2, var3, var1, EnumFacing.e));
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.d;
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Items.F;
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Items.F;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      boolean var5 = var3.b(b);
      boolean var6 = !World.a(var1, var2.b());
      if (var5 != var6) {
         this.b(var1, var2, var3, 0);
         var1.g(var2);
      }
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      boolean var4 = var3.b(N);
      boolean var5 = var3.b(b);
      if (!var5) {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
      } else if (!var4) {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      } else {
         this.a(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      var3 = var3.a(b, !World.a(var1, var2.b()));
      var1.a(var2, var3, 3);
      this.e(var1, var2, var3);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      this.e(var1, var2, var3.a(a, true));
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if (!var1.D) {
         if (var4.bZ() != null && var4.bZ().b() == Items.be) {
            var1.a(var2, var3.a(O, true), 4);
         }
      }
   }

   private void e(World var1, BlockPos var2, IBlockState var3) {
      for (EnumFacing var7 : new EnumFacing[]{EnumFacing.d, EnumFacing.e}) {
         for (int var8 = 1; var8 < 42; var8++) {
            BlockPos var9 = var2.a(var7, var8);
            IBlockState var10 = var1.p(var9);
            if (var10.c() == Blocks.bR) {
               if (var10.b(BlockTripWireHook.a) == var7.d()) {
                  Blocks.bR.a(var1, var9, var10, false, true, var8, var3);
               }
               break;
            }

            if (var10.c() != Blocks.bS) {
               break;
            }
         }
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      if (!var1.D) {
         if (!var3.b(a)) {
            this.e(var1, var2);
         }
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.D) {
         if (var1.p(var2).b(a)) {
            this.e(var1, var2);
         }
      }
   }

   private void e(World var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      boolean var4 = var3.b(a);
      boolean var5 = false;
      List var6 = var1.b(
         null, new AxisAlignedBB(var2.n() + this.B, var2.o() + this.C, var2.p() + this.D, var2.n() + this.E, var2.o() + this.F, var2.p() + this.G)
      );
      if (!var6.isEmpty()) {
         for (Entity var8 : var6) {
            if (!var8.aI()) {
               var5 = true;
               break;
            }
         }
      }

      if (var5 != var4) {
         var3 = var3.a(a, var5);
         var1.a(var2, var3, 3);
         this.e(var1, var2, var3);
      }

      if (var5) {
         var1.a(var2, this, this.a(var1));
      }
   }

   public static boolean c(IBlockAccess var0, BlockPos var1, IBlockState var2, EnumFacing var3) {
      BlockPos var4 = var1.a(var3);
      IBlockState var5 = var0.p(var4);
      Block var6 = var5.c();
      if (var6 == Blocks.bR) {
         EnumFacing var9 = var3.d();
         return var5.b(BlockTripWireHook.a) == var9;
      } else if (var6 == Blocks.bS) {
         boolean var7 = var2.b(b);
         boolean var8 = var5.b(b);
         return var7 == var8;
      } else {
         return false;
      }
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, (var1 & 1) > 0).a(b, (var1 & 2) > 0).a(N, (var1 & 4) > 0).a(O, (var1 & 8) > 0);
   }

   @Override
   public int c(IBlockState var1) {
      byte var2 = 0;
      if (var1.b(a)) {
         var2 |= 1;
      }

      if (var1.b(b)) {
         var2 |= 2;
      }

      if (var1.b(N)) {
         var2 |= 4;
      }

      if (var1.b(O)) {
         var2 |= 8;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b, N, O, P, Q, S, R);
   }
}
