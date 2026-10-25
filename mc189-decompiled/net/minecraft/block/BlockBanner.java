package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBanner extends BlockContainer {
   public static final PropertyDirection a = PropertyDirection.a("facing", EnumFacing.Plane.a);
   public static final PropertyInteger b = PropertyInteger.a("rotation", 0, 15);

   protected BlockBanner() {
      super(Material.d);
      float var1 = 0.25F;
      float var2 = 1.0F;
      this.a(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, var2, 0.5F + var1);
   }

   @Override
   public String f() {
      return StatCollector.a("item.banner.white.name");
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   @Override
   public AxisAlignedBB b(World var1, BlockPos var2) {
      this.a((IBlockAccess)var1, var2);
      return super.b(var1, var2);
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean b(IBlockAccess var1, BlockPos var2) {
      return true;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean g() {
      return true;
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntityBanner();
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Items.cE;
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Items.cE;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      TileEntity var6 = var1.s(var2);
      if (var6 instanceof TileEntityBanner) {
         ItemStack var7 = new ItemStack(Items.cE, 1, ((TileEntityBanner)var6).b());
         NBTTagCompound var8 = new NBTTagCompound();
         var6.b(var8);
         var8.o("x");
         var8.o("y");
         var8.o("z");
         var8.o("id");
         var7.a("BlockEntityTag", var8);
         a(var1, var2, var7);
      } else {
         super.a(var1, var2, var3, var4, var5);
      }
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return !this.e(var1, var2) && super.d(var1, var2);
   }

   @Override
   public void a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      if (var5 instanceof TileEntityBanner) {
         TileEntityBanner var6 = (TileEntityBanner)var5;
         ItemStack var7 = new ItemStack(Items.cE, 1, ((TileEntityBanner)var5).b());
         NBTTagCompound var8 = new NBTTagCompound();
         TileEntityBanner.a(var8, var6.b(), var6.d());
         var7.a("BlockEntityTag", var8);
         a(var1, var3, var7);
      } else {
         super.a(var1, var2, var3, var4, null);
      }
   }

   public static class BlockBannerHanging extends BlockBanner {
      public BlockBannerHanging() {
         this.j(this.M.b().a(a, EnumFacing.c));
      }

      @Override
      public void a(IBlockAccess var1, BlockPos var2) {
         EnumFacing var3 = var1.p(var2).b(a);
         float var4 = 0.0F;
         float var5 = 0.78125F;
         float var6 = 0.0F;
         float var7 = 1.0F;
         float var8 = 0.125F;
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
         switch (var3) {
            case c:
            default:
               this.a(var6, var4, 1.0F - var8, var7, var5, 1.0F);
               break;
            case d:
               this.a(var6, var4, 0.0F, var7, var5, var8);
               break;
            case e:
               this.a(1.0F - var8, var4, var6, 1.0F, var5, var7);
               break;
            case f:
               this.a(0.0F, var4, var6, var8, var5, var7);
         }
      }

      @Override
      public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
         EnumFacing var5 = var3.b(a);
         if (!var1.p(var2.a(var5.d())).c().t().a()) {
            this.b(var1, var2, var3, 0);
            var1.g(var2);
         }

         super.a(var1, var2, var3, var4);
      }

      @Override
      public IBlockState a(int var1) {
         EnumFacing var2 = EnumFacing.a(var1);
         if (var2.k() == EnumFacing.Axis.b) {
            var2 = EnumFacing.c;
         }

         return this.Q().a(a, var2);
      }

      @Override
      public int c(IBlockState var1) {
         return var1.b(a).a();
      }

      @Override
      protected BlockState e() {
         return new BlockState(this, a);
      }
   }

   public static class BlockBannerStanding extends BlockBanner {
      public BlockBannerStanding() {
         this.j(this.M.b().a(b, 0));
      }

      @Override
      public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
         if (!var1.p(var2.b()).c().t().a()) {
            this.b(var1, var2, var3, 0);
            var1.g(var2);
         }

         super.a(var1, var2, var3, var4);
      }

      @Override
      public IBlockState a(int var1) {
         return this.Q().a(b, var1);
      }

      @Override
      public int c(IBlockState var1) {
         return var1.b(b);
      }

      @Override
      protected BlockState e() {
         return new BlockState(this, b);
      }
   }
}
