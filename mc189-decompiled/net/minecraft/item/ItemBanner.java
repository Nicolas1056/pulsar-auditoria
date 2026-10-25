package net.minecraft.item;

import java.util.List;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemBanner extends ItemBlock {
   public ItemBanner() {
      super(Blocks.cK);
      this.h = 16;
      this.a(CreativeTabs.c);
      this.a(true);
      this.d(0);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var5 == EnumFacing.a) {
         return false;
      } else if (!var3.p(var4).c().t().a()) {
         return false;
      } else {
         var4 = var4.a(var5);
         if (!var2.a(var4, var5, var1)) {
            return false;
         } else if (!Blocks.cK.d(var3, var4)) {
            return false;
         } else if (var3.D) {
            return true;
         } else {
            if (var5 == EnumFacing.b) {
               int var9 = MathHelper.c((var2.y + 180.0F) * 16.0F / 360.0F + 0.5) & 15;
               var3.a(var4, Blocks.cK.Q().a(BlockStandingSign.a, var9), 3);
            } else {
               var3.a(var4, Blocks.cL.Q().a(BlockWallSign.a, var5), 3);
            }

            var1.b--;
            TileEntity var11 = var3.s(var4);
            if (var11 instanceof TileEntityBanner) {
               ((TileEntityBanner)var11).a(var1);
            }

            return true;
         }
      }
   }

   @Override
   public String a(ItemStack var1) {
      String var2 = "item.banner.";
      EnumDyeColor var3 = this.h(var1);
      var2 = var2 + var3.d() + ".name";
      return StatCollector.a(var2);
   }

   @Override
   public void a(ItemStack var1, EntityPlayer var2, List<String> var3, boolean var4) {
      NBTTagCompound var5 = var1.a("BlockEntityTag", false);
      if (var5 != null && var5.c("Patterns")) {
         NBTTagList var6 = var5.c("Patterns", 10);

         for (int var7 = 0; var7 < var6.c() && var7 < 6; var7++) {
            NBTTagCompound var8 = var6.b(var7);
            EnumDyeColor var9 = EnumDyeColor.a(var8.f("Color"));
            TileEntityBanner.EnumBannerPattern var10 = TileEntityBanner.EnumBannerPattern.a(var8.j("Pattern"));
            if (var10 != null) {
               var3.add(StatCollector.a("item.banner." + var10.a() + "." + var9.d()));
            }
         }
      }
   }

   @Override
   public int a(ItemStack var1, int var2) {
      if (var2 == 0) {
         return 16777215;
      } else {
         EnumDyeColor var3 = this.h(var1);
         return var3.e().L;
      }
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (EnumDyeColor var7 : EnumDyeColor.values()) {
         NBTTagCompound var8 = new NBTTagCompound();
         TileEntityBanner.a(var8, var7.b(), null);
         NBTTagCompound var9 = new NBTTagCompound();
         var9.a("BlockEntityTag", var8);
         ItemStack var10 = new ItemStack(var1, 1, var7.b());
         var10.d(var9);
         var3.add(var10);
      }
   }

   @Override
   public CreativeTabs c() {
      return CreativeTabs.c;
   }

   private EnumDyeColor h(ItemStack var1) {
      NBTTagCompound var2 = var1.a("BlockEntityTag", false);
      EnumDyeColor var3 = null;
      if (var2 != null && var2.c("Base")) {
         var3 = EnumDyeColor.a(var2.f("Base"));
      } else {
         var3 = EnumDyeColor.a(var1.i());
      }

      return var3;
   }
}
