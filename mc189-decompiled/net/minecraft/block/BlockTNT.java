package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockTNT extends Block {
   public static final PropertyBool a = PropertyBool.a("explode");

   public BlockTNT() {
      super(Material.u);
      this.j(this.M.b().a(a, false));
      this.a(CreativeTabs.d);
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      super.c(var1, var2, var3);
      if (var1.z(var2)) {
         this.d(var1, var2, var3.a(a, true));
         var1.g(var2);
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (var1.z(var2)) {
         this.d(var1, var2, var3.a(a, true));
         var1.g(var2);
      }
   }

   @Override
   public void a(World var1, BlockPos var2, Explosion var3) {
      if (!var1.D) {
         EntityTNTPrimed var4 = new EntityTNTPrimed(var1, var2.n() + 0.5F, var2.o(), var2.p() + 0.5F, var3.c());
         var4.a = var1.s.nextInt(var4.a / 4) + var4.a / 8;
         var1.d(var4);
      }
   }

   @Override
   public void d(World var1, BlockPos var2, IBlockState var3) {
      this.a(var1, var2, var3, null);
   }

   public void a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4) {
      if (!var1.D) {
         if (var3.b(a)) {
            EntityTNTPrimed var5 = new EntityTNTPrimed(var1, var2.n() + 0.5F, var2.o(), var2.p() + 0.5F, var4);
            var1.d(var5);
            var1.a(var5, "game.tnt.primed", 1.0F, 1.0F);
         }
      }
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var4.bZ() != null) {
         Item var9 = var4.bZ().b();
         if (var9 == Items.d || var9 == Items.bL) {
            this.a(var1, var2, var3.a(a, true), (EntityLivingBase)var4);
            var1.g(var2);
            if (var9 == Items.d) {
               var4.bZ().a(1, var4);
            } else if (!var4.bA.d) {
               var4.bZ().b--;
            }

            return true;
         }
      }

      return super.a(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      if (!var1.D && var4 instanceof EntityArrow) {
         EntityArrow var5 = (EntityArrow)var4;
         if (var5.at()) {
            this.a(var1, var2, var1.p(var2).a(a, true), var5.c instanceof EntityLivingBase ? (EntityLivingBase)var5.c : null);
            var1.g(var2);
         }
      }
   }

   @Override
   public boolean a(Explosion var1) {
      return false;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, (var1 & 1) > 0);
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a) ? 1 : 0;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }
}
