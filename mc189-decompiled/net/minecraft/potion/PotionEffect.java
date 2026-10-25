package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PotionEffect {
   private static final Logger a = LogManager.getLogger();
   private int b;
   private int c;
   private int d;
   private boolean e;
   private boolean f;
   private boolean g;
   private boolean h;

   public PotionEffect(int var1, int var2) {
      this(var1, var2, 0);
   }

   public PotionEffect(int var1, int var2, int var3) {
      this(var1, var2, var3, false, true);
   }

   public PotionEffect(int var1, int var2, int var3, boolean var4, boolean var5) {
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.f = var4;
      this.h = var5;
   }

   public PotionEffect(PotionEffect var1) {
      this.b = var1.b;
      this.c = var1.c;
      this.d = var1.d;
      this.f = var1.f;
      this.h = var1.h;
   }

   public void a(PotionEffect var1) {
      if (this.b != var1.b) {
         a.warn("This method should only be called for matching effects!");
      }

      if (var1.d > this.d) {
         this.d = var1.d;
         this.c = var1.c;
      } else if (var1.d == this.d && this.c < var1.c) {
         this.c = var1.c;
      } else if (!var1.f && this.f) {
         this.f = var1.f;
      }

      this.h = var1.h;
   }

   public int a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   public void a(boolean var1) {
      this.e = var1;
   }

   public boolean e() {
      return this.f;
   }

   public boolean f() {
      return this.h;
   }

   public boolean a(EntityLivingBase var1) {
      if (this.c > 0) {
         if (Potion.a[this.b].a(this.c, this.d)) {
            this.b(var1);
         }

         this.i();
      }

      return this.c > 0;
   }

   private int i() {
      return --this.c;
   }

   public void b(EntityLivingBase var1) {
      if (this.c > 0) {
         Potion.a[this.b].a(var1, this.d);
      }
   }

   public String g() {
      return Potion.a[this.b].a();
   }

   @Override
   public int hashCode() {
      return this.b;
   }

   @Override
   public String toString() {
      String var1 = "";
      if (this.c() > 0) {
         var1 = this.g() + " x " + (this.c() + 1) + ", Duration: " + this.b();
      } else {
         var1 = this.g() + ", Duration: " + this.b();
      }

      if (this.e) {
         var1 = var1 + ", Splash: true";
      }

      if (!this.h) {
         var1 = var1 + ", Particles: false";
      }

      return Potion.a[this.b].j() ? "(" + var1 + ")" : var1;
   }

   @Override
   public boolean equals(Object var1) {
      if (!(var1 instanceof PotionEffect)) {
         return false;
      } else {
         PotionEffect var2 = (PotionEffect)var1;
         return this.b == var2.b && this.d == var2.d && this.c == var2.c && this.e == var2.e && this.f == var2.f;
      }
   }

   public NBTTagCompound a(NBTTagCompound var1) {
      var1.a("Id", (byte)this.a());
      var1.a("Amplifier", (byte)this.c());
      var1.a("Duration", this.b());
      var1.a("Ambient", this.e());
      var1.a("ShowParticles", this.f());
      return var1;
   }

   public static PotionEffect b(NBTTagCompound var0) {
      byte var1 = var0.d("Id");
      if (var1 >= 0 && var1 < Potion.a.length && Potion.a[var1] != null) {
         byte var2 = var0.d("Amplifier");
         int var3 = var0.f("Duration");
         boolean var4 = var0.n("Ambient");
         boolean var5 = true;
         if (var0.b("ShowParticles", 1)) {
            var5 = var0.n("ShowParticles");
         }

         return new PotionEffect(var1, var3, var2, var4, var5);
      } else {
         return null;
      }
   }

   public void b(boolean var1) {
      this.g = var1;
   }

   public boolean h() {
      return this.g;
   }
}
