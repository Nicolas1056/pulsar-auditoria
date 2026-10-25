package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTrapDoor extends Block {
   public static final PropertyDirection a = PropertyDirection.a("facing", EnumFacing.Plane.a);
   public static final PropertyBool b = PropertyBool.a("open");
   public static final PropertyEnum<BlockTrapDoor.DoorHalf> N = PropertyEnum.a("half", BlockTrapDoor.DoorHalf.class);

   protected BlockTrapDoor(Material var1) {
      super(var1);
      this.j(this.M.b().a(a, EnumFacing.c).a(b, false).a(N, BlockTrapDoor.DoorHalf.b));
      float var2 = 0.5F;
      float var3 = 1.0F;
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.a(CreativeTabs.d);
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
   public boolean b(IBlockAccess var1, BlockPos var2) {
      return !var1.p(var2).b(b);
   }

   @Override
   public AxisAlignedBB b(World var1, BlockPos var2) {
      this.a(var1, var2);
      return super.b(var1, var2);
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      this.a(var1, var2);
      return super.a(var1, var2, var3);
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      this.d(var1.p(var2));
   }

   @Override
   public void j() {
      float var1 = 0.1875F;
      this.a(0.0F, 0.40625F, 0.0F, 1.0F, 0.59375F, 1.0F);
   }

   public void d(IBlockState var1) {
      if (var1.c() == this) {
         boolean var2 = var1.b(N) == BlockTrapDoor.DoorHalf.a;
         Boolean var3 = var1.b(b);
         EnumFacing var4 = var1.b(a);
         float var5 = 0.1875F;
         if (var2) {
            this.a(0.0F, 0.8125F, 0.0F, 1.0F, 1.0F, 1.0F);
         } else {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
         }

         if (var3) {
            if (var4 == EnumFacing.c) {
               this.a(0.0F, 0.0F, 0.8125F, 1.0F, 1.0F, 1.0F);
            }

            if (var4 == EnumFacing.d) {
               this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1875F);
            }

            if (var4 == EnumFacing.e) {
               this.a(0.8125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }

            if (var4 == EnumFacing.f) {
               this.a(0.0F, 0.0F, 0.0F, 0.1875F, 1.0F, 1.0F);
            }
         }
      }
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (this.J == Material.f) {
         return true;
      } else {
         var3 = var3.a(b);
         var1.a(var2, var3, 2);
         var1.a(var4, var3.b(b) ? 1003 : 1006, var2, 0);
         return true;
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!var1.D) {
         BlockPos var5 = var2.a(var3.b(a).d());
         if (!c(var1.p(var5).c())) {
            var1.g(var2);
            this.b(var1, var2, var3, 0);
         } else {
            boolean var6 = var1.z(var2);
            if (var6 || var4.i()) {
               boolean var7 = var3.b(b);
               if (var7 != var6) {
                  var1.a(var2, var3.a(b, var6), 2);
                  var1.a(null, var6 ? 1003 : 1006, var2, 0);
               }
            }
         }
      }
   }

   @Override
   public MovingObjectPosition a(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      this.a(var1, var2);
      return super.a(var1, var2, var3, var4);
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = this.Q();
      if (var3.k().c()) {
         var9 = var9.a(a, var3).a(b, false);
         var9 = var9.a(N, var5 > 0.5F ? BlockTrapDoor.DoorHalf.a : BlockTrapDoor.DoorHalf.b);
      }

      return var9;
   }

   @Override
   public boolean b(World var1, BlockPos var2, EnumFacing var3) {
      return !var3.k().b() && c(var1.p(var2.a(var3.d())).c());
   }

   protected static EnumFacing b(int var0) {
      switch (var0 & 3) {
         case 0:
            return EnumFacing.c;
         case 1:
            return EnumFacing.d;
         case 2:
            return EnumFacing.e;
         case 3:
         default:
            return EnumFacing.f;
      }
   }

   protected static int a(EnumFacing var0) {
      switch (var0) {
         case c:
            return 0;
         case d:
            return 1;
         case e:
            return 2;
         case f:
         default:
            return 3;
      }
   }

   private static boolean c(Block var0) {
      return var0.J.k() && var0.d() || var0 == Blocks.aX || var0 instanceof BlockSlab || var0 instanceof BlockStairs;
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, b(var1)).a(b, (var1 & 4) != 0).a(N, (var1 & 8) == 0 ? BlockTrapDoor.DoorHalf.b : BlockTrapDoor.DoorHalf.a);
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= a(var1.b(a));
      if (var1.b(b)) {
         var2 |= 4;
      }

      if (var1.b(N) == BlockTrapDoor.DoorHalf.a) {
         var2 |= 8;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b, N);
   }

   public static enum DoorHalf implements IStringSerializable {
      a("top"),
      b("bottom");

      private final String c;

      private DoorHalf(String var3) {
         this.c = var3;
      }

      @Override
      public String toString() {
         return this.c;
      }

      @Override
      public String l() {
         return this.c;
      }
   }
}
