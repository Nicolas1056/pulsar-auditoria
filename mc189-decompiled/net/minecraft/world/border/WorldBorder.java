package net.minecraft.world.border;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;

public class WorldBorder {
   private final List<IBorderListener> a = Lists.newArrayList();
   private double b = 0.0;
   private double c = 0.0;
   private double d = 6.0E7;
   private double e = this.d;
   private long f;
   private long g;
   private int h = 29999984;
   private double i = 0.2;
   private double j = 5.0;
   private int k = 15;
   private int l = 5;

   public boolean a(BlockPos var1) {
      return var1.n() + 1 > this.b() && var1.n() < this.d() && var1.p() + 1 > this.c() && var1.p() < this.e();
   }

   public boolean a(ChunkCoordIntPair var1) {
      return var1.e() > this.b() && var1.c() < this.d() && var1.f() > this.c() && var1.d() < this.e();
   }

   public boolean a(AxisAlignedBB var1) {
      return var1.d > this.b() && var1.a < this.d() && var1.f > this.c() && var1.c < this.e();
   }

   public double a(Entity var1) {
      return this.b(var1.s, var1.u);
   }

   public double b(double var1, double var3) {
      double var5 = var3 - this.c();
      double var7 = this.e() - var3;
      double var9 = var1 - this.b();
      double var11 = this.d() - var1;
      double var13 = Math.min(var9, var11);
      var13 = Math.min(var13, var5);
      return Math.min(var13, var7);
   }

   public EnumBorderStatus a() {
      if (this.e < this.d) {
         return EnumBorderStatus.b;
      } else {
         return this.e > this.d ? EnumBorderStatus.a : EnumBorderStatus.c;
      }
   }

   public double b() {
      double var1 = this.f() - this.h() / 2.0;
      if (var1 < -this.h) {
         var1 = -this.h;
      }

      return var1;
   }

   public double c() {
      double var1 = this.g() - this.h() / 2.0;
      if (var1 < -this.h) {
         var1 = -this.h;
      }

      return var1;
   }

   public double d() {
      double var1 = this.f() + this.h() / 2.0;
      if (var1 > this.h) {
         var1 = this.h;
      }

      return var1;
   }

   public double e() {
      double var1 = this.g() + this.h() / 2.0;
      if (var1 > this.h) {
         var1 = this.h;
      }

      return var1;
   }

   public double f() {
      return this.b;
   }

   public double g() {
      return this.c;
   }

   public void c(double var1, double var3) {
      this.b = var1;
      this.c = var3;

      for (IBorderListener var6 : this.k()) {
         var6.a(this, var1, var3);
      }
   }

   public double h() {
      if (this.a() != EnumBorderStatus.c) {
         double var1 = (float)(System.currentTimeMillis() - this.g) / (float)(this.f - this.g);
         if (!(var1 >= 1.0)) {
            return this.d + (this.e - this.d) * var1;
         }

         this.a(this.e);
      }

      return this.d;
   }

   public long i() {
      return this.a() != EnumBorderStatus.c ? this.f - System.currentTimeMillis() : 0L;
   }

   public double j() {
      return this.e;
   }

   public void a(double var1) {
      this.d = var1;
      this.e = var1;
      this.f = System.currentTimeMillis();
      this.g = this.f;

      for (IBorderListener var4 : this.k()) {
         var4.a(this, var1);
      }
   }

   public void a(double var1, double var3, long var5) {
      this.d = var1;
      this.e = var3;
      this.g = System.currentTimeMillis();
      this.f = this.g + var5;

      for (IBorderListener var8 : this.k()) {
         var8.a(this, var1, var3, var5);
      }
   }

   protected List<IBorderListener> k() {
      return Lists.newArrayList(this.a);
   }

   public void a(IBorderListener var1) {
      this.a.add(var1);
   }

   public void a(int var1) {
      this.h = var1;
   }

   public int l() {
      return this.h;
   }

   public double m() {
      return this.j;
   }

   public void b(double var1) {
      this.j = var1;

      for (IBorderListener var4 : this.k()) {
         var4.c(this, var1);
      }
   }

   public double n() {
      return this.i;
   }

   public void c(double var1) {
      this.i = var1;

      for (IBorderListener var4 : this.k()) {
         var4.b(this, var1);
      }
   }

   public double o() {
      return this.f == this.g ? 0.0 : Math.abs(this.d - this.e) / (this.f - this.g);
   }

   public int p() {
      return this.k;
   }

   public void b(int var1) {
      this.k = var1;

      for (IBorderListener var3 : this.k()) {
         var3.a(this, var1);
      }
   }

   public int q() {
      return this.l;
   }

   public void c(int var1) {
      this.l = var1;

      for (IBorderListener var3 : this.k()) {
         var3.b(this, var1);
      }
   }
}
