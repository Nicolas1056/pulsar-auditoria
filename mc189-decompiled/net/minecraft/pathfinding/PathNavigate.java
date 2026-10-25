package net.minecraft.pathfinding;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;

public abstract class PathNavigate {
   protected EntityLiving b;
   protected World c;
   protected PathEntity d;
   protected double e;
   private final IAttributeInstance a;
   private int f;
   private int g;
   private Vec3 h = new Vec3(0.0, 0.0, 0.0);
   private float i = 1.0F;
   private final PathFinder j;

   public PathNavigate(EntityLiving var1, World var2) {
      this.b = var1;
      this.c = var2;
      this.a = var1.a(SharedMonsterAttributes.b);
      this.j = this.a();
   }

   protected abstract PathFinder a();

   public void a(double var1) {
      this.e = var1;
   }

   public float i() {
      return (float)this.a.e();
   }

   public final PathEntity a(double var1, double var3, double var5) {
      return this.a(new BlockPos(MathHelper.c(var1), (int)var3, MathHelper.c(var5)));
   }

   public PathEntity a(BlockPos var1) {
      if (!this.b()) {
         return null;
      } else {
         float var2 = this.i();
         this.c.B.a("pathfind");
         BlockPos var3 = new BlockPos(this.b);
         int var4 = (int)(var2 + 8.0F);
         ChunkCache var5 = new ChunkCache(this.c, var3.a(-var4, -var4, -var4), var3.a(var4, var4, var4), 0);
         PathEntity var6 = this.j.a(var5, this.b, var1, var2);
         this.c.B.b();
         return var6;
      }
   }

   public boolean a(double var1, double var3, double var5, double var7) {
      PathEntity var9 = this.a(MathHelper.c(var1), (int)var3, MathHelper.c(var5));
      return this.a(var9, var7);
   }

   public void a(float var1) {
      this.i = var1;
   }

   public PathEntity a(Entity var1) {
      if (!this.b()) {
         return null;
      } else {
         float var2 = this.i();
         this.c.B.a("pathfind");
         BlockPos var3 = new BlockPos(this.b).a();
         int var4 = (int)(var2 + 16.0F);
         ChunkCache var5 = new ChunkCache(this.c, var3.a(-var4, -var4, -var4), var3.a(var4, var4, var4), 0);
         PathEntity var6 = this.j.a(var5, this.b, var1, var2);
         this.c.B.b();
         return var6;
      }
   }

   public boolean a(Entity var1, double var2) {
      PathEntity var4 = this.a(var1);
      return var4 != null ? this.a(var4, var2) : false;
   }

   public boolean a(PathEntity var1, double var2) {
      if (var1 == null) {
         this.d = null;
         return false;
      } else {
         if (!var1.a(this.d)) {
            this.d = var1;
         }

         this.d();
         if (this.d.d() == 0) {
            return false;
         } else {
            this.e = var2;
            Vec3 var4 = this.c();
            this.g = this.f;
            this.h = var4;
            return true;
         }
      }
   }

   public PathEntity j() {
      return this.d;
   }

   public void k() {
      this.f++;
      if (!this.m()) {
         if (this.b()) {
            this.l();
         } else if (this.d != null && this.d.e() < this.d.d()) {
            Vec3 var1 = this.c();
            Vec3 var2 = this.d.a(this.b, this.d.e());
            if (var1.b > var2.b && !this.b.C && MathHelper.c(var1.a) == MathHelper.c(var2.a) && MathHelper.c(var1.c) == MathHelper.c(var2.c)) {
               this.d.c(this.d.e() + 1);
            }
         }

         if (!this.m()) {
            Vec3 var8 = this.d.a(this.b);
            if (var8 != null) {
               AxisAlignedBB var9 = new AxisAlignedBB(var8.a, var8.b, var8.c, var8.a, var8.b, var8.c).b(0.5, 0.5, 0.5);
               List var3 = this.c.a(this.b, var9.a(0.0, -1.0, 0.0));
               double var4 = -1.0;
               var9 = var9.c(0.0, 1.0, 0.0);

               for (AxisAlignedBB var7 : var3) {
                  var4 = var7.b(var9, var4);
               }

               this.b.q().a(var8.a, var8.b + var4, var8.c, this.e);
            }
         }
      }
   }

   protected void l() {
      Vec3 var1 = this.c();
      int var2 = this.d.d();

      for (int var3 = this.d.e(); var3 < this.d.d(); var3++) {
         if (this.d.a(var3).b != (int)var1.b) {
            var2 = var3;
            break;
         }
      }

      float var8 = this.b.J * this.b.J * this.i;

      for (int var4 = this.d.e(); var4 < var2; var4++) {
         Vec3 var5 = this.d.a(this.b, var4);
         if (var1.g(var5) < var8) {
            this.d.c(var4 + 1);
         }
      }

      int var9 = MathHelper.f(this.b.J);
      int var10 = (int)this.b.K + 1;
      int var6 = var9;

      for (int var7 = var2 - 1; var7 >= this.d.e(); var7--) {
         if (this.a(var1, this.d.a(this.b, var7), var9, var10, var6)) {
            this.d.c(var7);
            break;
         }
      }

      this.a(var1);
   }

   protected void a(Vec3 var1) {
      if (this.f - this.g > 100) {
         if (var1.g(this.h) < 2.25) {
            this.n();
         }

         this.g = this.f;
         this.h = var1;
      }
   }

   public boolean m() {
      return this.d == null || this.d.b();
   }

   public void n() {
      this.d = null;
   }

   protected abstract Vec3 c();

   protected abstract boolean b();

   protected boolean o() {
      return this.b.V() || this.b.ab();
   }

   protected void d() {
   }

   protected abstract boolean a(Vec3 var1, Vec3 var2, int var3, int var4, int var5);
}
