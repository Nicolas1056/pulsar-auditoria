package net.minecraft.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemBlock extends Item {
   protected final Block a;

   public ItemBlock(Block var1) {
      this.a = var1;
   }

   public ItemBlock b(String var1) {
      super.c(var1);
      return this;
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      IBlockState var9 = var3.p(var4);
      Block var10 = var9.c();
      if (!var10.a(var3, var4)) {
         var4 = var4.a(var5);
      }

      if (var1.b == 0) {
         return false;
      } else if (!var2.a(var4, var5, var1)) {
         return false;
      } else if (var3.a(this.a, var4, false, var5, null, var1)) {
         int var11 = this.a(var1.i());
         IBlockState var12 = this.a.a(var3, var4, var5, var6, var7, var8, var11, var2);
         if (var3.a(var4, var12, 3)) {
            var12 = var3.p(var4);
            if (var12.c() == this.a) {
               a(var3, var2, var4, var1);
               this.a.a(var3, var4, var12, var2, var1);
            }

            var3.a(var4.n() + 0.5F, var4.o() + 0.5F, var4.p() + 0.5F, this.a.H.b(), (this.a.H.d() + 1.0F) / 2.0F, this.a.H.e() * 0.8F);
            var1.b--;
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean a(World var0, EntityPlayer var1, BlockPos var2, ItemStack var3) {
      MinecraftServer var4 = MinecraftServer.N();
      if (var4 == null) {
         return false;
      } else {
         if (var3.n() && var3.o().b("BlockEntityTag", 10)) {
            TileEntity var5 = var0.s(var2);
            if (var5 != null) {
               if (!var0.D && var5.F() && !var4.ap().h(var1.cd())) {
                  return false;
               }

               NBTTagCompound var6 = new NBTTagCompound();
               NBTTagCompound var7 = (NBTTagCompound)var6.b();
               var5.b(var6);
               NBTTagCompound var8 = (NBTTagCompound)var3.o().a("BlockEntityTag");
               var6.a(var8);
               var6.a("x", var2.n());
               var6.a("y", var2.o());
               var6.a("z", var2.p());
               if (!var6.equals(var7)) {
                  var5.a(var6);
                  var5.p_();
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean a(World var1, BlockPos var2, EnumFacing var3, EntityPlayer var4, ItemStack var5) {
      Block var6 = var1.p(var2).c();
      if (var6 == Blocks.aH) {
         var3 = EnumFacing.b;
      } else if (!var6.a(var1, var2)) {
         var2 = var2.a(var3);
      }

      return var1.a(this.a, var2, false, var3, null, var5);
   }

   @Override
   public String e_(ItemStack var1) {
      return this.a.a();
   }

   @Override
   public String a() {
      return this.a.a();
   }

   @Override
   public CreativeTabs c() {
      return this.a.L();
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      this.a.a(var1, var2, var3);
   }

   public Block d() {
      return this.a;
   }
}
