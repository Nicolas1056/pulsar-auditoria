package net.minecraft.item;

import com.mojang.authlib.GameProfile;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemSkull extends Item {
   private static final String[] a = new String[]{"skeleton", "wither", "zombie", "char", "creeper"};

   public ItemSkull() {
      this.a(CreativeTabs.c);
      this.d(0);
      this.a(true);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var5 == EnumFacing.a) {
         return false;
      } else {
         IBlockState var9 = var3.p(var4);
         Block var10 = var9.c();
         boolean var11 = var10.a(var3, var4);
         if (!var11) {
            if (!var3.p(var4).c().t().a()) {
               return false;
            }

            var4 = var4.a(var5);
         }

         if (!var2.a(var4, var5, var1)) {
            return false;
         } else if (!Blocks.ce.d(var3, var4)) {
            return false;
         } else {
            if (!var3.D) {
               var3.a(var4, Blocks.ce.Q().a(BlockSkull.a, var5), 3);
               int var12 = 0;
               if (var5 == EnumFacing.b) {
                  var12 = MathHelper.c(var2.y * 16.0F / 360.0F + 0.5) & 15;
               }

               TileEntity var13 = var3.s(var4);
               if (var13 instanceof TileEntitySkull) {
                  TileEntitySkull var14 = (TileEntitySkull)var13;
                  if (var1.i() == 3) {
                     GameProfile var15 = null;
                     if (var1.n()) {
                        NBTTagCompound var16 = var1.o();
                        if (var16.b("SkullOwner", 10)) {
                           var15 = NBTUtil.a(var16.m("SkullOwner"));
                        } else if (var16.b("SkullOwner", 8) && var16.j("SkullOwner").length() > 0) {
                           var15 = new GameProfile(null, var16.j("SkullOwner"));
                        }
                     }

                     var14.a(var15);
                  } else {
                     var14.a(var1.i());
                  }

                  var14.b(var12);
                  Blocks.ce.a(var3, var4, var14);
               }

               var1.b--;
            }

            return true;
         }
      }
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (int var4 = 0; var4 < a.length; var4++) {
         var3.add(new ItemStack(var1, 1, var4));
      }
   }

   @Override
   public int a(int var1) {
      return var1;
   }

   @Override
   public String e_(ItemStack var1) {
      int var2 = var1.i();
      if (var2 < 0 || var2 >= a.length) {
         var2 = 0;
      }

      return super.a() + "." + a[var2];
   }

   @Override
   public String a(ItemStack var1) {
      if (var1.i() == 3 && var1.n()) {
         if (var1.o().b("SkullOwner", 8)) {
            return StatCollector.a("item.skull.player.name", var1.o().j("SkullOwner"));
         }

         if (var1.o().b("SkullOwner", 10)) {
            NBTTagCompound var2 = var1.o().m("SkullOwner");
            if (var2.b("Name", 8)) {
               return StatCollector.a("item.skull.player.name", var2.j("Name"));
            }
         }
      }

      return super.a(var1);
   }

   @Override
   public boolean a(NBTTagCompound var1) {
      super.a(var1);
      if (var1.b("SkullOwner", 8) && var1.j("SkullOwner").length() > 0) {
         GameProfile var2 = new GameProfile(null, var1.j("SkullOwner"));
         var2 = TileEntitySkull.b(var2);
         var1.a("SkullOwner", NBTUtil.a(new NBTTagCompound(), var2));
         return true;
      } else {
         return false;
      }
   }
}
