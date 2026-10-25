package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public abstract class BlockLog extends BlockRotatedPillar {
   public static final PropertyEnum<BlockLog.EnumAxis> a = PropertyEnum.a("axis", BlockLog.EnumAxis.class);

   public BlockLog() {
      super(Material.d);
      this.a(CreativeTabs.b);
      this.c(2.0F);
      this.a(f);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      byte var4 = 4;
      int var5 = var4 + 1;
      if (var1.a(var2.a(-var5, -var5, -var5), var2.a(var5, var5, var5))) {
         for (BlockPos var7 : BlockPos.a(var2.a(-var4, -var4, -var4), var2.a((int)var4, (int)var4, (int)var4))) {
            IBlockState var8 = var1.p(var7);
            if (var8.c().t() == Material.j && !var8.b(BlockLeaves.b)) {
               var1.a(var7, var8.a(BlockLeaves.b, true), 4);
            }
         }
      }
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return super.a(var1, var2, var3, var4, var5, var6, var7, var8).a(a, BlockLog.EnumAxis.a(var3.k()));
   }

   public static enum EnumAxis implements IStringSerializable {
      a("x"),
      b("y"),
      c("z"),
      d("none");

      private final String e;

      private EnumAxis(String var3) {
         this.e = var3;
      }

      @Override
      public String toString() {
         return this.e;
      }

      public static BlockLog.EnumAxis a(EnumFacing.Axis var0) {
         switch (var0) {
            case a:
               return a;
            case b:
               return b;
            case c:
               return c;
            default:
               return d;
         }
      }

      @Override
      public String l() {
         return this.e;
      }
   }
}
