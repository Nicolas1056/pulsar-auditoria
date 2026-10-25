package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDaylightDetector;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDaylightDetector extends BlockContainer {
   public static final PropertyInteger a = PropertyInteger.a("power", 0, 15);
   private final boolean b;

   public BlockDaylightDetector(boolean var1) {
      super(Material.d);
      this.b = var1;
      this.j(this.M.b().a(a, 0));
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
      this.a(CreativeTabs.d);
      this.c(0.2F);
      this.a(f);
      this.c("daylightDetector");
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return var3.b(a);
   }

   public void f(World var1, BlockPos var2) {
      if (!var1.t.o()) {
         IBlockState var3 = var1.p(var2);
         int var4 = var1.b(EnumSkyBlock.a, var2) - var1.ab();
         float var5 = var1.d(1.0F);
         float var6 = var5 < (float) Math.PI ? 0.0F : (float) (Math.PI * 2);
         var5 += (var6 - var5) * 0.2F;
         var4 = Math.round(var4 * MathHelper.b(var5));
         var4 = MathHelper.a(var4, 0, 15);
         if (this.b) {
            var4 = 15 - var4;
         }

         if (var3.b(a) != var4) {
            var1.a(var2, var3.a(a, var4), 3);
         }
      }
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var4.cn()) {
         if (var1.D) {
            return true;
         } else {
            if (this.b) {
               var1.a(var2, Blocks.cl.Q().a(a, var3.b(a)), 4);
               Blocks.cl.f(var1, var2);
            } else {
               var1.a(var2, Blocks.cm.Q().a(a, var3.b(a)), 4);
               Blocks.cm.f(var1, var2);
            }

            return true;
         }
      } else {
         return super.a(var1, var2, var3, var4, var5, var6, var7, var8);
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Item.a(Blocks.cl);
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Item.a(Blocks.cl);
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public int b() {
      return 3;
   }

   @Override
   public boolean i() {
      return true;
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntityDaylightDetector();
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, var1);
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a);
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      if (!this.b) {
         super.a(var1, var2, var3);
      }
   }
}
