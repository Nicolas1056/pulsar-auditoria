package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCauldron extends Block {
   public static final PropertyInteger a = PropertyInteger.a("level", 0, 3);

   public BlockCauldron() {
      super(Material.f, MapColor.m);
      this.j(this.M.b().a(a, 0));
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List<AxisAlignedBB> var5, Entity var6) {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
      super.a(var1, var2, var3, var4, var5, var6);
      float var7 = 0.125F;
      this.a(0.0F, 0.0F, 0.0F, var7, 1.0F, 1.0F);
      super.a(var1, var2, var3, var4, var5, var6);
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var7);
      super.a(var1, var2, var3, var4, var5, var6);
      this.a(1.0F - var7, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      super.a(var1, var2, var3, var4, var5, var6);
      this.a(0.0F, 0.0F, 1.0F - var7, 1.0F, 1.0F, 1.0F);
      super.a(var1, var2, var3, var4, var5, var6);
      this.j();
   }

   @Override
   public void j() {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
   public void a(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      int var5 = var3.b(a);
      float var6 = var2.o() + (6.0F + 3 * var5) / 16.0F;
      if (!var1.D && var4.at() && var5 > 0 && var4.aR().b <= var6) {
         var4.N();
         this.a(var1, var2, var3, var5 - 1);
      }
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.D) {
         return true;
      } else {
         ItemStack var9 = var4.bi.h();
         if (var9 == null) {
            return true;
         } else {
            int var10 = var3.b(a);
            Item var11 = var9.b();
            if (var11 == Items.ax) {
               if (var10 < 3) {
                  if (!var4.bA.d) {
                     var4.bi.a(var4.bi.c, new ItemStack(Items.aw));
                  }

                  var4.b(StatList.I);
                  this.a(var1, var2, var3, 3);
               }

               return true;
            } else if (var11 == Items.bA) {
               if (var10 > 0) {
                  if (!var4.bA.d) {
                     ItemStack var14 = new ItemStack(Items.bz, 1, 0);
                     if (!var4.bi.a(var14)) {
                        var1.d(new EntityItem(var1, var2.n() + 0.5, var2.o() + 1.5, var2.p() + 0.5, var14));
                     } else if (var4 instanceof EntityPlayerMP) {
                        ((EntityPlayerMP)var4).a(var4.bj);
                     }

                     var4.b(StatList.J);
                     var9.b--;
                     if (var9.b <= 0) {
                        var4.bi.a(var4.bi.c, null);
                     }
                  }

                  this.a(var1, var2, var3, var10 - 1);
               }

               return true;
            } else {
               if (var10 > 0 && var11 instanceof ItemArmor) {
                  ItemArmor var12 = (ItemArmor)var11;
                  if (var12.x_() == ItemArmor.ArmorMaterial.a && var12.d_(var9)) {
                     var12.c(var9);
                     this.a(var1, var2, var3, var10 - 1);
                     var4.b(StatList.K);
                     return true;
                  }
               }

               if (var10 > 0 && var11 instanceof ItemBanner && TileEntityBanner.c(var9) > 0) {
                  ItemStack var13 = var9.k();
                  var13.b = 1;
                  TileEntityBanner.e(var13);
                  if (var9.b <= 1 && !var4.bA.d) {
                     var4.bi.a(var4.bi.c, var13);
                  } else {
                     if (!var4.bi.a(var13)) {
                        var1.d(new EntityItem(var1, var2.n() + 0.5, var2.o() + 1.5, var2.p() + 0.5, var13));
                     } else if (var4 instanceof EntityPlayerMP) {
                        ((EntityPlayerMP)var4).a(var4.bj);
                     }

                     var4.b(StatList.L);
                     if (!var4.bA.d) {
                        var9.b--;
                     }
                  }

                  if (!var4.bA.d) {
                     this.a(var1, var2, var3, var10 - 1);
                  }

                  return true;
               } else {
                  return false;
               }
            }
         }
      }
   }

   public void a(World var1, BlockPos var2, IBlockState var3, int var4) {
      var1.a(var2, var3.a(a, MathHelper.a(var4, 0, 3)), 2);
      var1.e(var2, this);
   }

   @Override
   public void k(World var1, BlockPos var2) {
      if (var1.s.nextInt(20) == 1) {
         IBlockState var3 = var1.p(var2);
         if (var3.b(a) < 3) {
            var1.a(var2, var3.a(a), 2);
         }
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Items.bG;
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Items.bG;
   }

   @Override
   public boolean O() {
      return true;
   }

   @Override
   public int l(World var1, BlockPos var2) {
      return var1.p(var2).b(a);
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
}
