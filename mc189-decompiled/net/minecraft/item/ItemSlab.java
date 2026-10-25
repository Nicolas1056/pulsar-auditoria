package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSlab extends ItemBlock {
   private final BlockSlab b;
   private final BlockSlab c;

   public ItemSlab(Block var1, BlockSlab var2, BlockSlab var3) {
      super(var1);
      this.b = var2;
      this.c = var3;
      this.d(0);
      this.a(true);
   }

   @Override
   public int a(int var1) {
      return var1;
   }

   @Override
   public String e_(ItemStack var1) {
      return this.b.b(var1.i());
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.b == 0) {
         return false;
      } else if (!var2.a(var4.a(var5), var5, var1)) {
         return false;
      } else {
         Object var9 = this.b.a(var1);
         IBlockState var10 = var3.p(var4);
         if (var10.c() == this.b) {
            IProperty var11 = this.b.n();
            Comparable var12 = var10.b(var11);
            BlockSlab.EnumBlockHalf var13 = var10.b(BlockSlab.a);
            if ((var5 == EnumFacing.b && var13 == BlockSlab.EnumBlockHalf.b || var5 == EnumFacing.a && var13 == BlockSlab.EnumBlockHalf.a) && var12 == var9) {
               IBlockState var14 = this.c.Q().a(var11, var12);
               if (var3.b(this.c.a(var3, var4, var14)) && var3.a(var4, var14, 3)) {
                  var3.a(var4.n() + 0.5F, var4.o() + 0.5F, var4.p() + 0.5F, this.c.H.b(), (this.c.H.d() + 1.0F) / 2.0F, this.c.H.e() * 0.8F);
                  var1.b--;
               }

               return true;
            }
         }

         return this.a(var1, var3, var4.a(var5), var9) ? true : super.a(var1, var2, var3, var4, var5, var6, var7, var8);
      }
   }

   @Override
   public boolean a(World var1, BlockPos var2, EnumFacing var3, EntityPlayer var4, ItemStack var5) {
      IProperty var7 = this.b.n();
      Object var8 = this.b.a(var5);
      IBlockState var9 = var1.p(var2);
      if (var9.c() == this.b) {
         boolean var10 = var9.b(BlockSlab.a) == BlockSlab.EnumBlockHalf.a;
         if ((var3 == EnumFacing.b && !var10 || var3 == EnumFacing.a && var10) && var8 == var9.b(var7)) {
            return true;
         }
      }

      BlockPos var11 = var2.a(var3);
      IBlockState var12 = var1.p(var11);
      return var12.c() == this.b && var8 == var12.b(var7) ? true : super.a(var1, var2, var3, var4, var5);
   }

   private boolean a(ItemStack var1, World var2, BlockPos var3, Object var4) {
      IBlockState var5 = var2.p(var3);
      if (var5.c() == this.b) {
         Comparable var6 = var5.b((IProperty<Comparable>)this.b.n());
         if (var6 == var4) {
            IBlockState var7 = this.c.Q().a(this.b.n(), var6);
            if (var2.b(this.c.a(var2, var3, var7)) && var2.a(var3, var7, 3)) {
               var2.a(var3.n() + 0.5F, var3.o() + 0.5F, var3.p() + 0.5F, this.c.H.b(), (this.c.H.d() + 1.0F) / 2.0F, this.c.H.e() * 0.8F);
               var1.b--;
            }

            return true;
         }
      }

      return false;
   }
}
