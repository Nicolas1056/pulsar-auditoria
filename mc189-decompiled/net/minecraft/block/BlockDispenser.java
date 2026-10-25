package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.PositionImpl;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.RegistryDefaulted;
import net.minecraft.world.World;

public class BlockDispenser extends BlockContainer {
   public static final PropertyDirection a = PropertyDirection.a("facing");
   public static final PropertyBool b = PropertyBool.a("triggered");
   public static final RegistryDefaulted<Item, IBehaviorDispenseItem> N = new RegistryDefaulted<>(new BehaviorDefaultDispenseItem());
   protected Random O = new Random();

   protected BlockDispenser() {
      super(Material.e);
      this.j(this.M.b().a(a, EnumFacing.c).a(b, false));
      this.a(CreativeTabs.d);
   }

   @Override
   public int a(World var1) {
      return 4;
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      super.c(var1, var2, var3);
      this.e(var1, var2, var3);
   }

   private void e(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.D) {
         EnumFacing var4 = var3.b(a);
         boolean var5 = var1.p(var2.c()).c().o();
         boolean var6 = var1.p(var2.d()).c().o();
         if (var4 == EnumFacing.c && var5 && !var6) {
            var4 = EnumFacing.d;
         } else if (var4 == EnumFacing.d && var6 && !var5) {
            var4 = EnumFacing.c;
         } else {
            boolean var7 = var1.p(var2.e()).c().o();
            boolean var8 = var1.p(var2.f()).c().o();
            if (var4 == EnumFacing.e && var7 && !var8) {
               var4 = EnumFacing.f;
            } else if (var4 == EnumFacing.f && var8 && !var7) {
               var4 = EnumFacing.e;
            }
         }

         var1.a(var2, var3.a(a, var4).a(b, false), 2);
      }
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.D) {
         return true;
      } else {
         TileEntity var9 = var1.s(var2);
         if (var9 instanceof TileEntityDispenser) {
            var4.a((IInventory)((TileEntityDispenser)var9));
            if (var9 instanceof TileEntityDropper) {
               var4.b(StatList.O);
            } else {
               var4.b(StatList.Q);
            }
         }

         return true;
      }
   }

   protected void f(World var1, BlockPos var2) {
      BlockSourceImpl var3 = new BlockSourceImpl(var1, var2);
      TileEntityDispenser var4 = var3.h();
      if (var4 != null) {
         int var5 = var4.m();
         if (var5 < 0) {
            var1.b(1001, var2, 0);
         } else {
            ItemStack var6 = var4.a(var5);
            IBehaviorDispenseItem var7 = this.a(var6);
            if (var7 != IBehaviorDispenseItem.a) {
               ItemStack var8 = var7.a(var3, var6);
               var4.a(var5, var8.b <= 0 ? null : var8);
            }
         }
      }
   }

   protected IBehaviorDispenseItem a(ItemStack var1) {
      return N.a(var1 == null ? null : var1.b());
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      boolean var5 = var1.z(var2) || var1.z(var2.a());
      boolean var6 = var3.b(b);
      if (var5 && !var6) {
         var1.a(var2, this, this.a(var1));
         var1.a(var2, var3.a(b, true), 4);
      } else if (!var5 && var6) {
         var1.a(var2, var3.a(b, false), 4);
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.D) {
         this.f(var1, var2);
      }
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntityDispenser();
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.Q().a(a, BlockPistonBase.a(var1, var2, var8)).a(b, false);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      var1.a(var2, var3.a(a, BlockPistonBase.a(var1, var2, var4)), 2);
      if (var5.s()) {
         TileEntity var6 = var1.s(var2);
         if (var6 instanceof TileEntityDispenser) {
            ((TileEntityDispenser)var6).a(var5.q());
         }
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      TileEntity var4 = var1.s(var2);
      if (var4 instanceof TileEntityDispenser) {
         InventoryHelper.a(var1, var2, (TileEntityDispenser)var4);
         var1.e(var2, this);
      }

      super.b(var1, var2, var3);
   }

   public static IPosition a(IBlockSource var0) {
      EnumFacing var1 = b(var0.f());
      double var2 = var0.a() + 0.7 * var1.g();
      double var4 = var0.b() + 0.7 * var1.h();
      double var6 = var0.c() + 0.7 * var1.i();
      return new PositionImpl(var2, var4, var6);
   }

   public static EnumFacing b(int var0) {
      return EnumFacing.a(var0 & 7);
   }

   @Override
   public boolean O() {
      return true;
   }

   @Override
   public int l(World var1, BlockPos var2) {
      return Container.a(var1.s(var2));
   }

   @Override
   public int b() {
      return 3;
   }

   @Override
   public IBlockState b(IBlockState var1) {
      return this.Q().a(a, EnumFacing.d);
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, b(var1)).a(b, (var1 & 8) > 0);
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
}
