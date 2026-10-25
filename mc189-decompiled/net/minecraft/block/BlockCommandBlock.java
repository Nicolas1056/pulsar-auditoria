package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockCommandBlock extends BlockContainer {
   public static final PropertyBool a = PropertyBool.a("triggered");

   public BlockCommandBlock() {
      super(Material.f, MapColor.q);
      this.j(this.M.b().a(a, false));
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntityCommandBlock();
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!var1.D) {
         boolean var5 = var1.z(var2);
         boolean var6 = var3.b(a);
         if (var5 && !var6) {
            var1.a(var2, var3.a(a, true), 4);
            var1.a(var2, this, this.a(var1));
         } else if (!var5 && var6) {
            var1.a(var2, var3.a(a, false), 4);
         }
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      TileEntity var5 = var1.s(var2);
      if (var5 instanceof TileEntityCommandBlock) {
         ((TileEntityCommandBlock)var5).b().a(var1);
         var1.e(var2, this);
      }
   }

   @Override
   public int a(World var1) {
      return 1;
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      TileEntity var9 = var1.s(var2);
      return var9 instanceof TileEntityCommandBlock ? ((TileEntityCommandBlock)var9).b().a(var4) : false;
   }

   @Override
   public boolean O() {
      return true;
   }

   @Override
   public int l(World var1, BlockPos var2) {
      TileEntity var3 = var1.s(var2);
      return var3 instanceof TileEntityCommandBlock ? ((TileEntityCommandBlock)var3).b().j() : 0;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      TileEntity var6 = var1.s(var2);
      if (var6 instanceof TileEntityCommandBlock) {
         CommandBlockLogic var7 = ((TileEntityCommandBlock)var6).b();
         if (var5.s()) {
            var7.b(var5.q());
         }

         if (!var1.D) {
            var7.a(var1.Q().b("sendCommandFeedback"));
         }
      }
   }

   @Override
   public int a(Random var1) {
      return 0;
   }

   @Override
   public int b() {
      return 3;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, (var1 & 1) > 0);
   }

   @Override
   public int c(IBlockState var1) {
      byte var2 = 0;
      if (var1.b(a)) {
         var2 |= 1;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.Q().a(a, false);
   }
}
