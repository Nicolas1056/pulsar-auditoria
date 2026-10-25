package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDoor extends Block {
   public static final PropertyDirection a = PropertyDirection.a("facing", EnumFacing.Plane.a);
   public static final PropertyBool b = PropertyBool.a("open");
   public static final PropertyEnum<BlockDoor.EnumHingePosition> N = PropertyEnum.a("hinge", BlockDoor.EnumHingePosition.class);
   public static final PropertyBool O = PropertyBool.a("powered");
   public static final PropertyEnum<BlockDoor.EnumDoorHalf> P = PropertyEnum.a("half", BlockDoor.EnumDoorHalf.class);

   protected BlockDoor(Material var1) {
      super(var1);
      this.j(this.M.b().a(a, EnumFacing.c).a(b, false).a(N, BlockDoor.EnumHingePosition.a).a(O, false).a(P, BlockDoor.EnumDoorHalf.b));
   }

   @Override
   public String f() {
      return StatCollector.a((this.a() + ".name").replaceAll("tile", "item"));
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean b(IBlockAccess var1, BlockPos var2) {
      return g(e(var1, var2));
   }

   @Override
   public boolean d() {
      return false;
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
      this.k(e(var1, var2));
   }

   private void k(int var1) {
      float var2 = 0.1875F;
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
      EnumFacing var3 = f(var1);
      boolean var4 = g(var1);
      boolean var5 = j(var1);
      if (var4) {
         if (var3 == EnumFacing.f) {
            if (!var5) {
               this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
            } else {
               this.a(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
            }
         } else if (var3 == EnumFacing.d) {
            if (!var5) {
               this.a(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else {
               this.a(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
            }
         } else if (var3 == EnumFacing.e) {
            if (!var5) {
               this.a(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
            } else {
               this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
            }
         } else if (var3 == EnumFacing.c) {
            if (!var5) {
               this.a(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
            } else {
               this.a(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
         }
      } else if (var3 == EnumFacing.f) {
         this.a(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
      } else if (var3 == EnumFacing.d) {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
      } else if (var3 == EnumFacing.e) {
         this.a(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else if (var3 == EnumFacing.c) {
         this.a(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
      }
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (this.J == Material.f) {
         return true;
      } else {
         BlockPos var9 = var3.b(P) == BlockDoor.EnumDoorHalf.b ? var2 : var2.b();
         IBlockState var10 = var2.equals(var9) ? var3 : var1.p(var9);
         if (var10.c() != this) {
            return false;
         } else {
            var3 = var10.a(b);
            var1.a(var9, var3, 2);
            var1.b(var9, var2);
            var1.a(var4, var3.b(b) ? 1003 : 1006, var2, 0);
            return true;
         }
      }
   }

   public void a(World var1, BlockPos var2, boolean var3) {
      IBlockState var4 = var1.p(var2);
      if (var4.c() == this) {
         BlockPos var5 = var4.b(P) == BlockDoor.EnumDoorHalf.b ? var2 : var2.b();
         IBlockState var6 = var2 == var5 ? var4 : var1.p(var5);
         if (var6.c() == this && var6.b(b) != var3) {
            var1.a(var5, var6.a(b, var3), 2);
            var1.b(var5, var2);
            var1.a(null, var3 ? 1003 : 1006, var2, 0);
         }
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (var3.b(P) == BlockDoor.EnumDoorHalf.a) {
         BlockPos var5 = var2.b();
         IBlockState var6 = var1.p(var5);
         if (var6.c() != this) {
            var1.g(var2);
         } else if (var4 != this) {
            this.a(var1, var5, var6, var4);
         }
      } else {
         boolean var9 = false;
         BlockPos var10 = var2.a();
         IBlockState var7 = var1.p(var10);
         if (var7.c() != this) {
            var1.g(var2);
            var9 = true;
         }

         if (!World.a(var1, var2.b())) {
            var1.g(var2);
            var9 = true;
            if (var7.c() == this) {
               var1.g(var10);
            }
         }

         if (var9) {
            if (!var1.D) {
               this.b(var1, var2, var3, 0);
            }
         } else {
            boolean var8 = var1.z(var2) || var1.z(var10);
            if ((var8 || var4.i()) && var4 != this && var8 != var7.b(O)) {
               var1.a(var10, var7.a(O, var8), 2);
               if (var8 != var3.b(b)) {
                  var1.a(var2, var3.a(b, var8), 2);
                  var1.b(var2, var2);
                  var1.a(null, var8 ? 1003 : 1006, var2, 0);
               }
            }
         }
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return var1.b(P) == BlockDoor.EnumDoorHalf.a ? null : this.l();
   }

   @Override
   public MovingObjectPosition a(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      this.a(var1, var2);
      return super.a(var1, var2, var3, var4);
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return var2.o() >= 255 ? false : World.a(var1, var2.b()) && super.d(var1, var2) && super.d(var1, var2.a());
   }

   @Override
   public int k() {
      return 1;
   }

   public static int e(IBlockAccess var0, BlockPos var1) {
      IBlockState var2 = var0.p(var1);
      int var3 = var2.c().c(var2);
      boolean var4 = i(var3);
      IBlockState var5 = var0.p(var1.b());
      int var6 = var5.c().c(var5);
      int var7 = var4 ? var6 : var3;
      IBlockState var8 = var0.p(var1.a());
      int var9 = var8.c().c(var8);
      int var10 = var4 ? var3 : var9;
      boolean var11 = (var10 & 1) != 0;
      boolean var12 = (var10 & 2) != 0;
      return b(var7) | (var4 ? 8 : 0) | (var11 ? 16 : 0) | (var12 ? 32 : 0);
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return this.l();
   }

   private Item l() {
      if (this == Blocks.aA) {
         return Items.aB;
      } else if (this == Blocks.ap) {
         return Items.ar;
      } else if (this == Blocks.aq) {
         return Items.as;
      } else if (this == Blocks.ar) {
         return Items.at;
      } else if (this == Blocks.as) {
         return Items.au;
      } else {
         return this == Blocks.at ? Items.av : Items.aq;
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      BlockPos var5 = var2.b();
      if (var4.bA.d && var3.b(P) == BlockDoor.EnumDoorHalf.a && var1.p(var5).c() == this) {
         var1.g(var5);
      }
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      if (var1.b(P) == BlockDoor.EnumDoorHalf.b) {
         IBlockState var4 = var2.p(var3.a());
         if (var4.c() == this) {
            var1 = var1.a(N, var4.b(N)).a(O, var4.b(O));
         }
      } else {
         IBlockState var5 = var2.p(var3.b());
         if (var5.c() == this) {
            var1 = var1.a(a, var5.b(a)).a(b, var5.b(b));
         }
      }

      return var1;
   }

   @Override
   public IBlockState a(int var1) {
      return (var1 & 8) > 0
         ? this.Q().a(P, BlockDoor.EnumDoorHalf.a).a(N, (var1 & 1) > 0 ? BlockDoor.EnumHingePosition.b : BlockDoor.EnumHingePosition.a).a(O, (var1 & 2) > 0)
         : this.Q().a(P, BlockDoor.EnumDoorHalf.b).a(a, EnumFacing.b(var1 & 3).f()).a(b, (var1 & 4) > 0);
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      if (var1.b(P) == BlockDoor.EnumDoorHalf.a) {
         var2 |= 8;
         if (var1.b(N) == BlockDoor.EnumHingePosition.b) {
            var2 |= 1;
         }

         if (var1.b(O)) {
            var2 |= 2;
         }
      } else {
         var2 |= var1.b(a).e().b();
         if (var1.b(b)) {
            var2 |= 4;
         }
      }

      return var2;
   }

   protected static int b(int var0) {
      return var0 & 7;
   }

   public static boolean f(IBlockAccess var0, BlockPos var1) {
      return g(e(var0, var1));
   }

   public static EnumFacing h(IBlockAccess var0, BlockPos var1) {
      return f(e(var0, var1));
   }

   public static EnumFacing f(int var0) {
      return EnumFacing.b(var0 & 3).f();
   }

   protected static boolean g(int var0) {
      return (var0 & 4) != 0;
   }

   protected static boolean i(int var0) {
      return (var0 & 8) != 0;
   }

   protected static boolean j(int var0) {
      return (var0 & 16) != 0;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, P, a, b, N, O);
   }

   public static enum EnumDoorHalf implements IStringSerializable {
      a,
      b;

      @Override
      public String toString() {
         return this.l();
      }

      @Override
      public String l() {
         return this == a ? "upper" : "lower";
      }
   }

   public static enum EnumHingePosition implements IStringSerializable {
      a,
      b;

      @Override
      public String toString() {
         return this.l();
      }

      @Override
      public String l() {
         return this == a ? "left" : "right";
      }
   }
}
