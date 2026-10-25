package net.minecraft.server.management;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;

public class ItemInWorldManager {
   public World a;
   public EntityPlayerMP b;
   private WorldSettings.GameType c = WorldSettings.GameType.a;
   private boolean d;
   private int e;
   private BlockPos f = BlockPos.a;
   private int g;
   private boolean h;
   private BlockPos i = BlockPos.a;
   private int j;
   private int k = -1;

   public ItemInWorldManager(World var1) {
      this.a = var1;
   }

   public void a(WorldSettings.GameType var1) {
      this.c = var1;
      var1.a(this.b.bA);
      this.b.t();
      this.b.b.ap().a(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.b, this.b));
   }

   public WorldSettings.GameType b() {
      return this.c;
   }

   public boolean c() {
      return this.c.e();
   }

   public boolean d() {
      return this.c.d();
   }

   public void b(WorldSettings.GameType var1) {
      if (this.c == WorldSettings.GameType.a) {
         this.c = var1;
      }

      this.a(this.c);
   }

   public void a() {
      this.g++;
      if (this.h) {
         int var1 = this.g - this.j;
         Block var2 = this.a.p(this.i).c();
         if (var2.t() == Material.a) {
            this.h = false;
         } else {
            float var3 = var2.a(this.b, this.b.o, this.i) * (var1 + 1);
            int var4 = (int)(var3 * 10.0F);
            if (var4 != this.k) {
               this.a.c(this.b.F(), this.i, var4);
               this.k = var4;
            }

            if (var3 >= 1.0F) {
               this.h = false;
               this.b(this.i);
            }
         }
      } else if (this.d) {
         Block var5 = this.a.p(this.f).c();
         if (var5.t() == Material.a) {
            this.a.c(this.b.F(), this.f, -1);
            this.k = -1;
            this.d = false;
         } else {
            int var6 = this.g - this.e;
            float var7 = var5.a(this.b, this.b.o, this.i) * (var6 + 1);
            int var8 = (int)(var7 * 10.0F);
            if (var8 != this.k) {
               this.a.c(this.b.F(), this.f, var8);
               this.k = var8;
            }
         }
      }
   }

   public void a(BlockPos var1, EnumFacing var2) {
      if (this.d()) {
         if (!this.a.a(null, var1, var2)) {
            this.b(var1);
         }
      } else {
         Block var3 = this.a.p(var1).c();
         if (this.c.c()) {
            if (this.c == WorldSettings.GameType.e) {
               return;
            }

            if (!this.b.cn()) {
               ItemStack var4 = this.b.bZ();
               if (var4 == null) {
                  return;
               }

               if (!var4.c(var3)) {
                  return;
               }
            }
         }

         this.a.a(null, var1, var2);
         this.e = this.g;
         float var6 = 1.0F;
         if (var3.t() != Material.a) {
            var3.a(this.a, var1, (EntityPlayer)this.b);
            var6 = var3.a(this.b, this.b.o, var1);
         }

         if (var3.t() != Material.a && var6 >= 1.0F) {
            this.b(var1);
         } else {
            this.d = true;
            this.f = var1;
            int var5 = (int)(var6 * 10.0F);
            this.a.c(this.b.F(), var1, var5);
            this.k = var5;
         }
      }
   }

   public void a(BlockPos var1) {
      if (var1.equals(this.f)) {
         int var2 = this.g - this.e;
         Block var3 = this.a.p(var1).c();
         if (var3.t() != Material.a) {
            float var4 = var3.a(this.b, this.b.o, var1) * (var2 + 1);
            if (var4 >= 0.7F) {
               this.d = false;
               this.a.c(this.b.F(), var1, -1);
               this.b(var1);
            } else if (!this.h) {
               this.d = false;
               this.h = true;
               this.i = var1;
               this.j = this.e;
            }
         }
      }
   }

   public void e() {
      this.d = false;
      this.a.c(this.b.F(), this.f, -1);
   }

   private boolean c(BlockPos var1) {
      IBlockState var2 = this.a.p(var1);
      var2.c().a(this.a, var1, var2, (EntityPlayer)this.b);
      boolean var3 = this.a.g(var1);
      if (var3) {
         var2.c().d(this.a, var1, var2);
      }

      return var3;
   }

   public boolean b(BlockPos var1) {
      if (this.c.d() && this.b.bA() != null && this.b.bA().b() instanceof ItemSword) {
         return false;
      } else {
         IBlockState var2 = this.a.p(var1);
         TileEntity var3 = this.a.s(var1);
         if (this.c.c()) {
            if (this.c == WorldSettings.GameType.e) {
               return false;
            }

            if (!this.b.cn()) {
               ItemStack var4 = this.b.bZ();
               if (var4 == null) {
                  return false;
               }

               if (!var4.c(var2.c())) {
                  return false;
               }
            }
         }

         this.a.a(this.b, 2001, var1, Block.f(var2));
         boolean var7 = this.c(var1);
         if (this.d()) {
            this.b.a.a(new S23PacketBlockChange(this.a, var1));
         } else {
            ItemStack var5 = this.b.bZ();
            boolean var6 = this.b.b(var2.c());
            if (var5 != null) {
               var5.a(this.a, var2.c(), var1, this.b);
               if (var5.b == 0) {
                  this.b.ca();
               }
            }

            if (var7 && var6) {
               var2.c().a(this.a, this.b, var1, var2, var3);
            }
         }

         return var7;
      }
   }

   public boolean a(EntityPlayer var1, World var2, ItemStack var3) {
      if (this.c == WorldSettings.GameType.e) {
         return false;
      } else {
         int var4 = var3.b;
         int var5 = var3.i();
         ItemStack var6 = var3.a(var2, var1);
         if (var6 != var3 || var6 != null && (var6.b != var4 || var6.l() > 0 || var6.i() != var5)) {
            var1.bi.a[var1.bi.c] = var6;
            if (this.d()) {
               var6.b = var4;
               if (var6.e()) {
                  var6.b(var5);
               }
            }

            if (var6.b == 0) {
               var1.bi.a[var1.bi.c] = null;
            }

            if (!var1.bS()) {
               ((EntityPlayerMP)var1).a(var1.bj);
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public boolean a(EntityPlayer var1, World var2, ItemStack var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (this.c == WorldSettings.GameType.e) {
         TileEntity var13 = var2.s(var4);
         if (var13 instanceof ILockableContainer) {
            Block var14 = var2.p(var4).c();
            ILockableContainer var15 = (ILockableContainer)var13;
            if (var15 instanceof TileEntityChest && var14 instanceof BlockChest) {
               var15 = ((BlockChest)var14).f(var2, var4);
            }

            if (var15 != null) {
               var1.a((IInventory)var15);
               return true;
            }
         } else if (var13 instanceof IInventory) {
            var1.a((IInventory)var13);
            return true;
         }

         return false;
      } else {
         if (!var1.av() || var1.bA() == null) {
            IBlockState var9 = var2.p(var4);
            if (var9.c().a(var2, var4, var9, var1, var5, var6, var7, var8)) {
               return true;
            }
         }

         if (var3 == null) {
            return false;
         } else if (this.d()) {
            int var12 = var3.i();
            int var10 = var3.b;
            boolean var11 = var3.a(var1, var2, var4, var5, var6, var7, var8);
            var3.b(var12);
            var3.b = var10;
            return var11;
         } else {
            return var3.a(var1, var2, var4, var5, var6, var7, var8);
         }
      }
   }

   public void a(WorldServer var1) {
      this.a = var1;
   }
}
