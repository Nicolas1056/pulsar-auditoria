package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

public class BlockBeacon extends BlockContainer {
   public BlockBeacon() {
      super(Material.s, MapColor.G);
      this.c(3.0F);
      this.a(CreativeTabs.f);
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntityBeacon();
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.D) {
         return true;
      } else {
         TileEntity var9 = var1.s(var2);
         if (var9 instanceof TileEntityBeacon) {
            var4.a((IInventory)((TileEntityBeacon)var9));
            var4.b(StatList.N);
         }

         return true;
      }
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
   public int b() {
      return 3;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      super.a(var1, var2, var3, var4, var5);
      if (var5.s()) {
         TileEntity var6 = var1.s(var2);
         if (var6 instanceof TileEntityBeacon) {
            ((TileEntityBeacon)var6).a(var5.q());
         }
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      TileEntity var5 = var1.s(var2);
      if (var5 instanceof TileEntityBeacon) {
         ((TileEntityBeacon)var5).m();
         var1.c(var2, this, 1, 0);
      }
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }

   public static void f(final World var0, final BlockPos var1) {
      HttpUtil.a.submit(new Runnable() {
         @Override
         public void run() {
            Chunk var1x = var0.f(var1);

            for (int var2 = var1.o() - 1; var2 >= 0; var2--) {
               final BlockPos var3 = new BlockPos(var1.n(), var2, var1.p());
               if (!var1x.d(var3)) {
                  break;
               }

               IBlockState var4 = var0.p(var3);
               if (var4.c() == Blocks.bY) {
                  ((WorldServer)var0).a(new Runnable() {
                     @Override
                     public void run() {
                        TileEntity var1x = var0.s(var3);
                        if (var1x instanceof TileEntityBeacon) {
                           ((TileEntityBeacon)var1x).m();
                           var0.c(var3, Blocks.bY, 1, 0);
                        }
                     }
                  });
               }
            }
         }
      });
   }
}
