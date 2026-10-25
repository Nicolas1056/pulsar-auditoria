package net.minecraft.world.demo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class DemoWorldManager extends ItemInWorldManager {
   private boolean c;
   private boolean d;
   private int e;
   private int f;

   public DemoWorldManager(World var1) {
      super(var1);
   }

   @Override
   public void a() {
      super.a();
      this.f++;
      long var1 = this.a.K();
      long var3 = var1 / 24000L + 1L;
      if (!this.c && this.f > 20) {
         this.c = true;
         this.b.a.a(new S2BPacketChangeGameState(5, 0.0F));
      }

      this.d = var1 > 120500L;
      if (this.d) {
         this.e++;
      }

      if (var1 % 24000L == 500L) {
         if (var3 <= 6L) {
            this.b.a(new ChatComponentTranslation("demo.day." + var3));
         }
      } else if (var3 == 1L) {
         if (var1 == 100L) {
            this.b.a.a(new S2BPacketChangeGameState(5, 101.0F));
         } else if (var1 == 175L) {
            this.b.a.a(new S2BPacketChangeGameState(5, 102.0F));
         } else if (var1 == 250L) {
            this.b.a.a(new S2BPacketChangeGameState(5, 103.0F));
         }
      } else if (var3 == 5L && var1 % 24000L == 22000L) {
         this.b.a(new ChatComponentTranslation("demo.day.warning"));
      }
   }

   private void f() {
      if (this.e > 100) {
         this.b.a(new ChatComponentTranslation("demo.reminder"));
         this.e = 0;
      }
   }

   @Override
   public void a(BlockPos var1, EnumFacing var2) {
      if (this.d) {
         this.f();
      } else {
         super.a(var1, var2);
      }
   }

   @Override
   public void a(BlockPos var1) {
      if (!this.d) {
         super.a(var1);
      }
   }

   @Override
   public boolean b(BlockPos var1) {
      return this.d ? false : super.b(var1);
   }

   @Override
   public boolean a(EntityPlayer var1, World var2, ItemStack var3) {
      if (this.d) {
         this.f();
         return false;
      } else {
         return super.a(var1, var2, var3);
      }
   }

   @Override
   public boolean a(EntityPlayer var1, World var2, ItemStack var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (this.d) {
         this.f();
         return false;
      } else {
         return super.a(var1, var2, var3, var4, var5, var6, var7, var8);
      }
   }
}
