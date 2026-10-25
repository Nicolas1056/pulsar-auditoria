package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLever extends Block {
   public static final PropertyEnum<BlockLever.EnumOrientation> a = PropertyEnum.a("facing", BlockLever.EnumOrientation.class);
   public static final PropertyBool b = PropertyBool.a("powered");

   protected BlockLever() {
      super(Material.q);
      this.j(this.M.b().a(a, BlockLever.EnumOrientation.e).a(b, false));
      this.a(CreativeTabs.d);
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
   public boolean b(World var1, BlockPos var2, EnumFacing var3) {
      return a(var1, var2, var3.d());
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      for (EnumFacing var6 : EnumFacing.values()) {
         if (a(var1, var2, var6)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean a(World var0, BlockPos var1, EnumFacing var2) {
      return BlockButton.a(var0, var1, var2);
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = this.Q().a(b, false);
      if (a(var1, var2, var3.d())) {
         return var9.a(a, BlockLever.EnumOrientation.a(var3, var8.aP()));
      } else {
         for (EnumFacing var11 : EnumFacing.Plane.a) {
            if (var11 != var3 && a(var1, var2, var11.d())) {
               return var9.a(a, BlockLever.EnumOrientation.a(var11, var8.aP()));
            }
         }

         return World.a(var1, var2.b()) ? var9.a(a, BlockLever.EnumOrientation.a(EnumFacing.b, var8.aP())) : var9;
      }
   }

   public static int a(EnumFacing var0) {
      switch (var0) {
         case a:
            return 0;
         case b:
            return 5;
         case c:
            return 4;
         case d:
            return 3;
         case e:
            return 2;
         case f:
            return 1;
         default:
            return -1;
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (this.e(var1, var2, var3) && !a(var1, var2, var3.b(a).c().d())) {
         this.b(var1, var2, var3, 0);
         var1.g(var2);
      }
   }

   private boolean e(World var1, BlockPos var2, IBlockState var3) {
      if (this.d(var1, var2)) {
         return true;
      } else {
         this.b(var1, var2, var3, 0);
         var1.g(var2);
         return false;
      }
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      float var3 = 0.1875F;
      switch ((BlockLever.EnumOrientation)var1.p(var2).b(a)) {
         case b:
            this.a(0.0F, 0.2F, 0.5F - var3, var3 * 2.0F, 0.8F, 0.5F + var3);
            break;
         case c:
            this.a(1.0F - var3 * 2.0F, 0.2F, 0.5F - var3, 1.0F, 0.8F, 0.5F + var3);
            break;
         case d:
            this.a(0.5F - var3, 0.2F, 0.0F, 0.5F + var3, 0.8F, var3 * 2.0F);
            break;
         case e:
            this.a(0.5F - var3, 0.2F, 1.0F - var3 * 2.0F, 0.5F + var3, 0.8F, 1.0F);
            break;
         case f:
         case g:
            var3 = 0.25F;
            this.a(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.6F, 0.5F + var3);
            break;
         case a:
         case h:
            var3 = 0.25F;
            this.a(0.5F - var3, 0.4F, 0.5F - var3, 0.5F + var3, 1.0F, 0.5F + var3);
      }
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.D) {
         return true;
      } else {
         var3 = var3.a(b);
         var1.a(var2, var3, 3);
         var1.a(var2.n() + 0.5, var2.o() + 0.5, var2.p() + 0.5, "random.click", 0.3F, var3.b(b) ? 0.6F : 0.5F);
         var1.c(var2, this);
         EnumFacing var9 = var3.b(a).c();
         var1.c(var2.a(var9.d()), this);
         return true;
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      if (var3.b(b)) {
         var1.c(var2, this);
         EnumFacing var4 = var3.b(a).c();
         var1.c(var2.a(var4.d()), this);
      }

      super.b(var1, var2, var3);
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return var3.b(b) ? 15 : 0;
   }

   @Override
   public int b(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      if (!var3.b(b)) {
         return 0;
      } else {
         return var3.b(a).c() == var4 ? 15 : 0;
      }
   }

   @Override
   public boolean i() {
      return true;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, BlockLever.EnumOrientation.a(var1 & 7)).a(b, (var1 & 8) > 0);
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(a).a();
      if (var1.b(b)) {
         var2 |= 8;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b);
   }

   public static enum EnumOrientation implements IStringSerializable {
      a(0, "down_x", EnumFacing.a),
      b(1, "east", EnumFacing.f),
      c(2, "west", EnumFacing.e),
      d(3, "south", EnumFacing.d),
      e(4, "north", EnumFacing.c),
      f(5, "up_z", EnumFacing.b),
      g(6, "up_x", EnumFacing.b),
      h(7, "down_z", EnumFacing.a);

      private static final BlockLever.EnumOrientation[] i = new BlockLever.EnumOrientation[values().length];
      private final int j;
      private final String k;
      private final EnumFacing l;

      private EnumOrientation(int var3, String var4, EnumFacing var5) {
         this.j = var3;
         this.k = var4;
         this.l = var5;
      }

      public int a() {
         return this.j;
      }

      public EnumFacing c() {
         return this.l;
      }

      @Override
      public String toString() {
         return this.k;
      }

      public static BlockLever.EnumOrientation a(int var0) {
         if (var0 < 0 || var0 >= i.length) {
            var0 = 0;
         }

         return i[var0];
      }

      public static BlockLever.EnumOrientation a(EnumFacing var0, EnumFacing var1) {
         switch (var0) {
            case a:
               switch (var1.k()) {
                  case a:
                     return a;
                  case c:
                     return h;
                  default:
                     throw new IllegalArgumentException("Invalid entityFacing " + var1 + " for facing " + var0);
               }
            case b:
               switch (var1.k()) {
                  case a:
                     return g;
                  case c:
                     return f;
                  default:
                     throw new IllegalArgumentException("Invalid entityFacing " + var1 + " for facing " + var0);
               }
            case c:
               return e;
            case d:
               return d;
            case e:
               return c;
            case f:
               return b;
            default:
               throw new IllegalArgumentException("Invalid facing: " + var0);
         }
      }

      @Override
      public String l() {
         return this.k;
      }

      static {
         for (BlockLever.EnumOrientation var3 : values()) {
            i[var3.a()] = var3;
         }
      }
   }
}
