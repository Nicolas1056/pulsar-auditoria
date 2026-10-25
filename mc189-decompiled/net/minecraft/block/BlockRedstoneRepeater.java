package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneRepeater extends BlockRedstoneDiode {
   public static final PropertyBool a = PropertyBool.a("locked");
   public static final PropertyInteger b = PropertyInteger.a("delay", 1, 4);

   protected BlockRedstoneRepeater(boolean var1) {
      super(var1);
      this.j(this.M.b().a(O, EnumFacing.c).a(b, 1).a(a, false));
   }

   @Override
   public String f() {
      return StatCollector.a("item.diode.name");
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1.a(a, this.b(var2, var3, var1));
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (!var4.bA.e) {
         return false;
      } else {
         var1.a(var2, var3.a(b), 3);
         return true;
      }
   }

   @Override
   protected int d(IBlockState var1) {
      return var1.b(b) * 2;
   }

   @Override
   protected IBlockState e(IBlockState var1) {
      Integer var2 = var1.b(b);
      Boolean var3 = var1.b(a);
      EnumFacing var4 = var1.b(O);
      return Blocks.bc.Q().a(O, var4).a(b, var2).a(a, var3);
   }

   @Override
   protected IBlockState k(IBlockState var1) {
      Integer var2 = var1.b(b);
      Boolean var3 = var1.b(a);
      EnumFacing var4 = var1.b(O);
      return Blocks.bb.Q().a(O, var4).a(b, var2).a(a, var3);
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Items.bb;
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Items.bb;
   }

   @Override
   public boolean b(IBlockAccess var1, BlockPos var2, IBlockState var3) {
      return this.c(var1, var2, var3) > 0;
   }

   @Override
   protected boolean c(Block var1) {
      return d(var1);
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (this.N) {
         EnumFacing var5 = var3.b(O);
         double var6 = var2.n() + 0.5F + (var4.nextFloat() - 0.5F) * 0.2;
         double var8 = var2.o() + 0.4F + (var4.nextFloat() - 0.5F) * 0.2;
         double var10 = var2.p() + 0.5F + (var4.nextFloat() - 0.5F) * 0.2;
         float var12 = -5.0F;
         if (var4.nextBoolean()) {
            var12 = var3.b(b) * 2 - 1;
         }

         var12 /= 16.0F;
         double var13 = var12 * var5.g();
         double var15 = var12 * var5.i();
         var1.a(EnumParticleTypes.E, var6 + var13, var8, var10 + var15, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      super.b(var1, var2, var3);
      this.h(var1, var2, var3);
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(O, EnumFacing.b(var1)).a(a, false).a(b, 1 + (var1 >> 2));
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(O).b();
      return var2 | var1.b(b) - 1 << 2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, O, b, a);
   }
}
