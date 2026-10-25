package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public class BlockHugeMushroom extends Block {
   public static final PropertyEnum<BlockHugeMushroom.EnumType> a = PropertyEnum.a("variant", BlockHugeMushroom.EnumType.class);
   private final Block b;

   public BlockHugeMushroom(Material var1, MapColor var2, Block var3) {
      super(var1, var2);
      this.j(this.M.b().a(a, BlockHugeMushroom.EnumType.l));
      this.b = var3;
   }

   @Override
   public int a(Random var1) {
      return Math.max(0, var1.nextInt(10) - 7);
   }

   @Override
   public MapColor g(IBlockState var1) {
      switch ((BlockHugeMushroom.EnumType)var1.b(a)) {
         case m:
            return MapColor.e;
         case k:
            return MapColor.d;
         case j:
            return MapColor.d;
         default:
            return super.g(var1);
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Item.a(this.b);
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Item.a(this.b);
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.Q();
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, BlockHugeMushroom.EnumType.a(var1));
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }

   public static enum EnumType implements IStringSerializable {
      a(1, "north_west"),
      b(2, "north"),
      c(3, "north_east"),
      d(4, "west"),
      e(5, "center"),
      f(6, "east"),
      g(7, "south_west"),
      h(8, "south"),
      i(9, "south_east"),
      j(10, "stem"),
      k(0, "all_inside"),
      l(14, "all_outside"),
      m(15, "all_stem");

      private static final BlockHugeMushroom.EnumType[] n = new BlockHugeMushroom.EnumType[16];
      private final int o;
      private final String p;

      private EnumType(int var3, String var4) {
         this.o = var3;
         this.p = var4;
      }

      public int a() {
         return this.o;
      }

      @Override
      public String toString() {
         return this.p;
      }

      public static BlockHugeMushroom.EnumType a(int var0) {
         if (var0 < 0 || var0 >= n.length) {
            var0 = 0;
         }

         BlockHugeMushroom.EnumType var1 = n[var0];
         return var1 == null ? n[0] : var1;
      }

      @Override
      public String l() {
         return this.p;
      }

      static {
         for (BlockHugeMushroom.EnumType var3 : values()) {
            n[var3.a()] = var3;
         }
      }
   }
}
