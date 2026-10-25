package net.minecraft.entity.item;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityXPOrb extends Entity {
   public int a;
   public int b;
   public int c;
   private int d = 5;
   private int e;
   private EntityPlayer f;
   private int g;

   public EntityXPOrb(World var1, double var2, double var4, double var6, int var8) {
      super(var1);
      this.a(0.5F, 0.5F);
      this.b(var2, var4, var6);
      this.y = (float)(Math.random() * 360.0);
      this.v = (float)(Math.random() * 0.2F - 0.1F) * 2.0F;
      this.w = (float)(Math.random() * 0.2) * 2.0F;
      this.x = (float)(Math.random() * 0.2F - 0.1F) * 2.0F;
      this.e = var8;
   }

   @Override
   protected boolean s_() {
      return false;
   }

   public EntityXPOrb(World var1) {
      super(var1);
      this.a(0.25F, 0.25F);
   }

   @Override
   protected void h() {
   }

   @Override
   public int b(float var1) {
      float var2 = 0.5F;
      var2 = MathHelper.a(var2, 0.0F, 1.0F);
      int var3 = super.b(var1);
      int var4 = var3 & 0xFF;
      int var5 = var3 >> 16 & 0xFF;
      var4 += (int)(var2 * 15.0F * 16.0F);
      if (var4 > 240) {
         var4 = 240;
      }

      return var4 | var5 << 16;
   }

   @Override
   public void t_() {
      super.t_();
      if (this.c > 0) {
         this.c--;
      }

      this.p = this.s;
      this.q = this.t;
      this.r = this.u;
      this.w -= 0.03F;
      if (this.o.p(new BlockPos(this)).c().t() == Material.i) {
         this.w = 0.2F;
         this.v = (this.V.nextFloat() - this.V.nextFloat()) * 0.2F;
         this.x = (this.V.nextFloat() - this.V.nextFloat()) * 0.2F;
         this.a("random.fizz", 0.4F, 2.0F + this.V.nextFloat() * 0.4F);
      }

      this.j(this.s, (this.aR().b + this.aR().e) / 2.0, this.u);
      double var1 = 8.0;
      if (this.g < this.a - 20 + this.F() % 100) {
         if (this.f == null || this.f.h(this) > var1 * var1) {
            this.f = this.o.a(this, var1);
         }

         this.g = this.a;
      }

      if (this.f != null && this.f.v()) {
         this.f = null;
      }

      if (this.f != null) {
         double var3 = (this.f.s - this.s) / var1;
         double var5 = (this.f.t + this.f.aS() - this.t) / var1;
         double var7 = (this.f.u - this.u) / var1;
         double var9 = Math.sqrt(var3 * var3 + var5 * var5 + var7 * var7);
         double var11 = 1.0 - var9;
         if (var11 > 0.0) {
            var11 *= var11;
            this.v += var3 / var9 * var11 * 0.1;
            this.w += var5 / var9 * var11 * 0.1;
            this.x += var7 / var9 * var11 * 0.1;
         }
      }

      this.d(this.v, this.w, this.x);
      float var13 = 0.98F;
      if (this.C) {
         var13 = this.o.p(new BlockPos(MathHelper.c(this.s), MathHelper.c(this.aR().b) - 1, MathHelper.c(this.u))).c().L * 0.98F;
      }

      this.v *= var13;
      this.w *= 0.98F;
      this.x *= var13;
      if (this.C) {
         this.w *= -0.9F;
      }

      this.a++;
      this.b++;
      if (this.b >= 6000) {
         this.J();
      }
   }

   @Override
   public boolean W() {
      return this.o.a(this.aR(), Material.h, this);
   }

   @Override
   protected void f(int var1) {
      this.a(DamageSource.a, var1);
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else {
         this.ac();
         this.d = (int)(this.d - var2);
         if (this.d <= 0) {
            this.J();
         }

         return false;
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      var1.a("Health", (short)((byte)this.d));
      var1.a("Age", (short)this.b);
      var1.a("Value", (short)this.e);
   }

   @Override
   public void a(NBTTagCompound var1) {
      this.d = var1.e("Health") & 255;
      this.b = var1.e("Age");
      this.e = var1.e("Value");
   }

   @Override
   public void d(EntityPlayer var1) {
      if (!this.o.D) {
         if (this.c == 0 && var1.bp == 0) {
            var1.bp = 2;
            this.o.a((Entity)var1, "random.orb", 0.1F, 0.5F * ((this.V.nextFloat() - this.V.nextFloat()) * 0.7F + 1.8F));
            var1.a(this, 1);
            var1.u(this.e);
            this.J();
         }
      }
   }

   public int j() {
      return this.e;
   }

   public int l() {
      if (this.e >= 2477) {
         return 10;
      } else if (this.e >= 1237) {
         return 9;
      } else if (this.e >= 617) {
         return 8;
      } else if (this.e >= 307) {
         return 7;
      } else if (this.e >= 149) {
         return 6;
      } else if (this.e >= 73) {
         return 5;
      } else if (this.e >= 37) {
         return 4;
      } else if (this.e >= 17) {
         return 3;
      } else if (this.e >= 7) {
         return 2;
      } else {
         return this.e >= 3 ? 1 : 0;
      }
   }

   public static int a(int var0) {
      if (var0 >= 2477) {
         return 2477;
      } else if (var0 >= 1237) {
         return 1237;
      } else if (var0 >= 617) {
         return 617;
      } else if (var0 >= 307) {
         return 307;
      } else if (var0 >= 149) {
         return 149;
      } else if (var0 >= 73) {
         return 73;
      } else if (var0 >= 37) {
         return 37;
      } else if (var0 >= 17) {
         return 17;
      } else if (var0 >= 7) {
         return 7;
      } else {
         return var0 >= 3 ? 3 : 1;
      }
   }

   @Override
   public boolean aD() {
      return false;
   }
}
