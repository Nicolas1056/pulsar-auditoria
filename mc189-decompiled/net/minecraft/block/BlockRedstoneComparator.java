package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityComparator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneComparator extends BlockRedstoneDiode implements ITileEntityProvider {
   public static final PropertyBool a = PropertyBool.a("powered");
   public static final PropertyEnum<BlockRedstoneComparator.Mode> b = PropertyEnum.a("mode", BlockRedstoneComparator.Mode.class);

   public BlockRedstoneComparator(boolean var1) {
      super(var1);
      this.j(this.M.b().a(O, EnumFacing.c).a(a, false).a(b, BlockRedstoneComparator.Mode.a));
      this.A = true;
   }

   @Override
   public String f() {
      return StatCollector.a("item.comparator.name");
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Items.ce;
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Items.ce;
   }

   @Override
   protected int d(IBlockState var1) {
      return 2;
   }

   @Override
   protected IBlockState e(IBlockState var1) {
      Boolean var2 = var1.b(a);
      BlockRedstoneComparator.Mode var3 = var1.b(b);
      EnumFacing var4 = var1.b(O);
      return Blocks.ck.Q().a(O, var4).a(a, var2).a(b, var3);
   }

   @Override
   protected IBlockState k(IBlockState var1) {
      Boolean var2 = var1.b(a);
      BlockRedstoneComparator.Mode var3 = var1.b(b);
      EnumFacing var4 = var1.b(O);
      return Blocks.cj.Q().a(O, var4).a(a, var2).a(b, var3);
   }

   @Override
   protected boolean l(IBlockState var1) {
      return this.N || var1.b(a);
   }

   @Override
   protected int a(IBlockAccess var1, BlockPos var2, IBlockState var3) {
      TileEntity var4 = var1.s(var2);
      return var4 instanceof TileEntityComparator ? ((TileEntityComparator)var4).b() : 0;
   }

   private int j(World var1, BlockPos var2, IBlockState var3) {
      return var3.b(b) == BlockRedstoneComparator.Mode.b ? Math.max(this.f(var1, var2, var3) - this.c(var1, var2, var3), 0) : this.f(var1, var2, var3);
   }

   @Override
   protected boolean e(World var1, BlockPos var2, IBlockState var3) {
      int var4 = this.f(var1, var2, var3);
      if (var4 >= 15) {
         return true;
      } else if (var4 == 0) {
         return false;
      } else {
         int var5 = this.c(var1, var2, var3);
         return var5 == 0 ? true : var4 >= var5;
      }
   }

   @Override
   protected int f(World var1, BlockPos var2, IBlockState var3) {
      int var4 = super.f(var1, var2, var3);
      EnumFacing var5 = var3.b(O);
      BlockPos var6 = var2.a(var5);
      Block var7 = var1.p(var6).c();
      if (var7.O()) {
         var4 = var7.l(var1, var6);
      } else if (var4 < 15 && var7.v()) {
         var6 = var6.a(var5);
         var7 = var1.p(var6).c();
         if (var7.O()) {
            var4 = var7.l(var1, var6);
         } else if (var7.t() == Material.a) {
            EntityItemFrame var8 = this.a(var1, var5, var6);
            if (var8 != null) {
               var4 = var8.q();
            }
         }
      }

      return var4;
   }

   private EntityItemFrame a(World var1, final EnumFacing var2, BlockPos var3) {
      List var4 = var1.a(
         EntityItemFrame.class, new AxisAlignedBB(var3.n(), var3.o(), var3.p(), var3.n() + 1, var3.o() + 1, var3.p() + 1), new Predicate<Entity>() {
            public boolean a(Entity var1) {
               return var1 != null && var1.aP() == var2;
            }
         }
      );
      return var4.size() == 1 ? (EntityItemFrame)var4.get(0) : null;
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (!var4.bA.e) {
         return false;
      } else {
         var3 = var3.a(b);
         var1.a(var2.n() + 0.5, var2.o() + 0.5, var2.p() + 0.5, "random.click", 0.3F, var3.b(b) == BlockRedstoneComparator.Mode.b ? 0.55F : 0.5F);
         var1.a(var2, var3, 2);
         this.k(var1, var2, var3);
         return true;
      }
   }

   @Override
   protected void g(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.a(var2, this)) {
         int var4 = this.j(var1, var2, var3);
         TileEntity var5 = var1.s(var2);
         int var6 = var5 instanceof TileEntityComparator ? ((TileEntityComparator)var5).b() : 0;
         if (var4 != var6 || this.l(var3) != this.e(var1, var2, var3)) {
            if (this.i(var1, var2, var3)) {
               var1.a(var2, this, 2, -1);
            } else {
               var1.a(var2, this, 2, 0);
            }
         }
      }
   }

   private void k(World var1, BlockPos var2, IBlockState var3) {
      int var4 = this.j(var1, var2, var3);
      TileEntity var5 = var1.s(var2);
      int var6 = 0;
      if (var5 instanceof TileEntityComparator) {
         TileEntityComparator var7 = (TileEntityComparator)var5;
         var6 = var7.b();
         var7.a(var4);
      }

      if (var6 != var4 || var3.b(b) == BlockRedstoneComparator.Mode.a) {
         boolean var9 = this.e(var1, var2, var3);
         boolean var8 = this.l(var3);
         if (var8 && !var9) {
            var1.a(var2, var3.a(a, false), 2);
         } else if (!var8 && var9) {
            var1.a(var2, var3.a(a, true), 2);
         }

         this.h(var1, var2, var3);
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (this.N) {
         var1.a(var2, this.k(var3).a(a, true), 4);
      }

      this.k(var1, var2, var3);
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      super.c(var1, var2, var3);
      var1.a(var2, this.a(var1, 0));
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      super.b(var1, var2, var3);
      var1.t(var2);
      this.h(var1, var2, var3);
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, int var4, int var5) {
      super.a(var1, var2, var3, var4, var5);
      TileEntity var6 = var1.s(var2);
      return var6 == null ? false : var6.c(var4, var5);
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntityComparator();
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(O, EnumFacing.b(var1)).a(a, (var1 & 8) > 0).a(b, (var1 & 4) > 0 ? BlockRedstoneComparator.Mode.b : BlockRedstoneComparator.Mode.a);
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(O).b();
      if (var1.b(a)) {
         var2 |= 8;
      }

      if (var1.b(b) == BlockRedstoneComparator.Mode.b) {
         var2 |= 4;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, O, b, a);
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.Q().a(O, var8.aP().d()).a(a, false).a(b, BlockRedstoneComparator.Mode.a);
   }

   public static enum Mode implements IStringSerializable {
      a("compare"),
      b("subtract");

      private final String c;

      private Mode(String var3) {
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
