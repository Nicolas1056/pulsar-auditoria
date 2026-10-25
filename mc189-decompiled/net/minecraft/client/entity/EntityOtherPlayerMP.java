package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityOtherPlayerMP extends AbstractClientPlayer {
   private boolean a;
   private int b;
   private double c;
   private double d;
   private double e;
   private double f;
   private double g;

   public EntityOtherPlayerMP(World var1, GameProfile var2) {
      super(var1, var2);
      this.S = 0.0F;
      this.T = true;
      this.bZ = 0.25F;
      this.j = 10.0;
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      return true;
   }

   @Override
   public void a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.c = var1;
      this.d = var3;
      this.e = var5;
      this.f = var7;
      this.g = var8;
      this.b = var9;
   }

   @Override
   public void t_() {
      this.bZ = 0.0F;
      super.t_();
      this.aA = this.aB;
      double var1 = this.s - this.p;
      double var3 = this.u - this.r;
      float var5 = MathHelper.a(var1 * var1 + var3 * var3) * 4.0F;
      if (var5 > 1.0F) {
         var5 = 1.0F;
      }

      this.aB = this.aB + (var5 - this.aB) * 0.4F;
      this.aC = this.aC + this.aB;
      if (!this.a && this.ay() && this.bi.a[this.bi.c] != null) {
         ItemStack var6 = this.bi.a[this.bi.c];
         this.a(this.bi.a[this.bi.c], var6.b().d(var6));
         this.a = true;
      } else if (this.a && !this.ay()) {
         this.bV();
         this.a = false;
      }
   }

   @Override
   public void m() {
      if (this.b > 0) {
         double var1 = this.s + (this.c - this.s) / this.b;
         double var3 = this.t + (this.d - this.t) / this.b;
         double var5 = this.u + (this.e - this.u) / this.b;
         double var7 = this.f - this.y;

         while (var7 < -180.0) {
            var7 += 360.0;
         }

         while (var7 >= 180.0) {
            var7 -= 360.0;
         }

         this.y = (float)(this.y + var7 / this.b);
         this.z = (float)(this.z + (this.g - this.z) / this.b);
         this.b--;
         this.b(var1, var3, var5);
         this.b(this.y, this.z);
      }

      this.bn = this.bo;
      this.bx();
      float var9 = MathHelper.a(this.v * this.v + this.x * this.x);
      float var2 = (float)Math.atan(-this.w * 0.2F) * 15.0F;
      if (var9 > 0.1F) {
         var9 = 0.1F;
      }

      if (!this.C || this.bn() <= 0.0F) {
         var9 = 0.0F;
      }

      if (this.C || this.bn() <= 0.0F) {
         var2 = 0.0F;
      }

      this.bo = this.bo + (var9 - this.bo) * 0.4F;
      this.aF = this.aF + (var2 - this.aF) * 0.8F;
   }

   @Override
   public void c(int var1, ItemStack var2) {
      if (var1 == 0) {
         this.bi.a[this.bi.c] = var2;
      } else {
         this.bi.b[var1 - 1] = var2;
      }
   }

   @Override
   public void a(IChatComponent var1) {
      Minecraft.A().q.d().a(var1);
   }

   @Override
   public boolean a(int var1, String var2) {
      return false;
   }

   @Override
   public BlockPos c() {
      return new BlockPos(this.s + 0.5, this.t + 0.5, this.u + 0.5);
   }
}
