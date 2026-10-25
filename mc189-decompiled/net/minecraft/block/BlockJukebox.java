package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockJukebox extends BlockContainer {
   public static final PropertyBool a = PropertyBool.a("has_record");

   protected BlockJukebox() {
      super(Material.d, MapColor.l);
      this.j(this.M.b().a(a, false));
      this.a(CreativeTabs.c);
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var3.b(a)) {
         this.e(var1, var2, var3);
         var3 = var3.a(a, false);
         var1.a(var2, var3, 2);
         return true;
      } else {
         return false;
      }
   }

   public void a(World var1, BlockPos var2, IBlockState var3, ItemStack var4) {
      if (!var1.D) {
         TileEntity var5 = var1.s(var2);
         if (var5 instanceof BlockJukebox.TileEntityJukebox) {
            ((BlockJukebox.TileEntityJukebox)var5).a(new ItemStack(var4.b(), 1, var4.i()));
            var1.a(var2, var3.a(a, true), 2);
         }
      }
   }

   private void e(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.D) {
         TileEntity var4 = var1.s(var2);
         if (var4 instanceof BlockJukebox.TileEntityJukebox) {
            BlockJukebox.TileEntityJukebox var5 = (BlockJukebox.TileEntityJukebox)var4;
            ItemStack var6 = var5.a();
            if (var6 != null) {
               var1.b(1005, var2, 0);
               var1.a(var2, null);
               var5.a(null);
               float var7 = 0.7F;
               double var8 = var1.s.nextFloat() * var7 + (1.0F - var7) * 0.5;
               double var10 = var1.s.nextFloat() * var7 + (1.0F - var7) * 0.2 + 0.6;
               double var12 = var1.s.nextFloat() * var7 + (1.0F - var7) * 0.5;
               ItemStack var14 = var6.k();
               EntityItem var15 = new EntityItem(var1, var2.n() + var8, var2.o() + var10, var2.p() + var12, var14);
               var15.p();
               var1.d(var15);
            }
         }
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      this.e(var1, var2, var3);
      super.b(var1, var2, var3);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if (!var1.D) {
         super.a(var1, var2, var3, var4, 0);
      }
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new BlockJukebox.TileEntityJukebox();
   }

   @Override
   public boolean O() {
      return true;
   }

   @Override
   public int l(World var1, BlockPos var2) {
      TileEntity var3 = var1.s(var2);
      if (var3 instanceof BlockJukebox.TileEntityJukebox) {
         ItemStack var4 = ((BlockJukebox.TileEntityJukebox)var3).a();
         if (var4 != null) {
            return Item.b(var4.b()) + 1 - Item.b(Items.cq);
         }
      }

      return 0;
   }

   @Override
   public int b() {
      return 3;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, var1 > 0);
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a) ? 1 : 0;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }

   public static class TileEntityJukebox extends TileEntity {
      private ItemStack a;

      @Override
      public void a(NBTTagCompound var1) {
         super.a(var1);
         if (var1.b("RecordItem", 10)) {
            this.a(ItemStack.a(var1.m("RecordItem")));
         } else if (var1.f("Record") > 0) {
            this.a(new ItemStack(Item.b(var1.f("Record")), 1, 0));
         }
      }

      @Override
      public void b(NBTTagCompound var1) {
         super.b(var1);
         if (this.a() != null) {
            var1.a("RecordItem", this.a().b(new NBTTagCompound()));
         }
      }

      public ItemStack a() {
         return this.a;
      }

      public void a(ItemStack var1) {
         this.a = var1;
         this.p_();
      }
   }
}
