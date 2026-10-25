package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.util.MathHelper;

public class Stitcher {
   private final int a;
   private final Set<Stitcher.Holder> b = Sets.newHashSetWithExpectedSize(256);
   private final List<Stitcher.Slot> c = Lists.newArrayListWithCapacity(256);
   private int d;
   private int e;
   private final int f;
   private final int g;
   private final boolean h;
   private final int i;

   public Stitcher(int var1, int var2, boolean var3, int var4, int var5) {
      this.a = var5;
      this.f = var1;
      this.g = var2;
      this.h = var3;
      this.i = var4;
   }

   public int a() {
      return this.d;
   }

   public int b() {
      return this.e;
   }

   public void a(TextureAtlasSprite var1) {
      Stitcher.Holder var2 = new Stitcher.Holder(var1, this.a);
      if (this.i > 0) {
         var2.a(this.i);
      }

      this.b.add(var2);
   }

   public void c() {
      Stitcher.Holder[] var1 = this.b.toArray(new Stitcher.Holder[this.b.size()]);
      Arrays.sort((Object[])var1);

      for (Stitcher.Holder var5 : var1) {
         if (!this.a(var5)) {
            String var6 = String.format("Unable to fit: %s - size: %dx%d - Maybe try a lowerresolution resourcepack?", var5.a().i(), var5.a().c(), var5.a().d());
            throw new StitcherException(var5, var6);
         }
      }

      if (this.h) {
         this.d = MathHelper.b(this.d);
         this.e = MathHelper.b(this.e);
      }
   }

   public List<TextureAtlasSprite> d() {
      ArrayList var1 = Lists.newArrayList();

      for (Stitcher.Slot var3 : this.c) {
         var3.a(var1);
      }

      ArrayList var7 = Lists.newArrayList();

      for (Stitcher.Slot var4 : var1) {
         Stitcher.Holder var5 = var4.a();
         TextureAtlasSprite var6 = var5.a();
         var6.a(this.d, this.e, var4.b(), var4.c(), var5.e());
         var7.add(var6);
      }

      return var7;
   }

   private static int b(int var0, int var1) {
      return (var0 >> var1) + ((var0 & (1 << var1) - 1) == 0 ? 0 : 1) << var1;
   }

   private boolean a(Stitcher.Holder var1) {
      for (int var2 = 0; var2 < this.c.size(); var2++) {
         if (this.c.get(var2).a(var1)) {
            return true;
         }

         var1.d();
         if (this.c.get(var2).a(var1)) {
            return true;
         }

         var1.d();
      }

      return this.b(var1);
   }

   private boolean b(Stitcher.Holder var1) {
      int var2 = Math.min(var1.b(), var1.c());
      boolean var3 = this.d == 0 && this.e == 0;
      boolean var4;
      if (this.h) {
         int var5 = MathHelper.b(this.d);
         int var6 = MathHelper.b(this.e);
         int var7 = MathHelper.b(this.d + var2);
         int var8 = MathHelper.b(this.e + var2);
         boolean var9 = var7 <= this.f;
         boolean var10 = var8 <= this.g;
         if (!var9 && !var10) {
            return false;
         }

         boolean var11 = var5 != var7;
         boolean var12 = var6 != var8;
         if (var11 ^ var12) {
            var4 = !var11;
         } else {
            var4 = var9 && var5 <= var6;
         }
      } else {
         boolean var13 = this.d + var2 <= this.f;
         boolean var15 = this.e + var2 <= this.g;
         if (!var13 && !var15) {
            return false;
         }

         var4 = var13 && (var3 || this.d <= this.e);
      }

      int var14 = Math.max(var1.b(), var1.c());
      if (MathHelper.b((var4 ? this.e : this.d) + var14) > (var4 ? this.g : this.f)) {
         return false;
      } else {
         Stitcher.Slot var16;
         if (var4) {
            if (var1.b() > var1.c()) {
               var1.d();
            }

            if (this.e == 0) {
               this.e = var1.c();
            }

            var16 = new Stitcher.Slot(this.d, 0, var1.b(), this.e);
            this.d = this.d + var1.b();
         } else {
            var16 = new Stitcher.Slot(0, this.e, this.d, var1.c());
            this.e = this.e + var1.c();
         }

         var16.a(var1);
         this.c.add(var16);
         return true;
      }
   }

   public static class Holder implements Comparable<Stitcher.Holder> {
      private final TextureAtlasSprite a;
      private final int b;
      private final int c;
      private final int d;
      private boolean e;
      private float f = 1.0F;

      public Holder(TextureAtlasSprite var1, int var2) {
         this.a = var1;
         this.b = var1.c();
         this.c = var1.d();
         this.d = var2;
         this.e = Stitcher.b(this.c, var2) > Stitcher.b(this.b, var2);
      }

      public TextureAtlasSprite a() {
         return this.a;
      }

      public int b() {
         return this.e ? Stitcher.b((int)(this.c * this.f), this.d) : Stitcher.b((int)(this.b * this.f), this.d);
      }

      public int c() {
         return this.e ? Stitcher.b((int)(this.b * this.f), this.d) : Stitcher.b((int)(this.c * this.f), this.d);
      }

      public void d() {
         this.e = !this.e;
      }

      public boolean e() {
         return this.e;
      }

      public void a(int var1) {
         if (this.b > var1 && this.c > var1) {
            this.f = (float)var1 / Math.min(this.b, this.c);
         }
      }

      @Override
      public String toString() {
         return "Holder{width=" + this.b + ", height=" + this.c + '}';
      }

      public int a(Stitcher.Holder var1) {
         int var2;
         if (this.c() == var1.c()) {
            if (this.b() == var1.b()) {
               if (this.a.i() == null) {
                  return var1.a.i() == null ? 0 : -1;
               }

               return this.a.i().compareTo(var1.a.i());
            }

            var2 = this.b() < var1.b() ? 1 : -1;
         } else {
            var2 = this.c() < var1.c() ? 1 : -1;
         }

         return var2;
      }
   }

   public static class Slot {
      private final int a;
      private final int b;
      private final int c;
      private final int d;
      private List<Stitcher.Slot> e;
      private Stitcher.Holder f;

      public Slot(int var1, int var2, int var3, int var4) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
         this.d = var4;
      }

      public Stitcher.Holder a() {
         return this.f;
      }

      public int b() {
         return this.a;
      }

      public int c() {
         return this.b;
      }

      public boolean a(Stitcher.Holder var1) {
         if (this.f != null) {
            return false;
         } else {
            int var2 = var1.b();
            int var3 = var1.c();
            if (var2 <= this.c && var3 <= this.d) {
               if (var2 == this.c && var3 == this.d) {
                  this.f = var1;
                  return true;
               } else {
                  if (this.e == null) {
                     this.e = Lists.newArrayListWithCapacity(1);
                     this.e.add(new Stitcher.Slot(this.a, this.b, var2, var3));
                     int var4 = this.c - var2;
                     int var5 = this.d - var3;
                     if (var5 > 0 && var4 > 0) {
                        int var6 = Math.max(this.d, var4);
                        int var7 = Math.max(this.c, var5);
                        if (var6 >= var7) {
                           this.e.add(new Stitcher.Slot(this.a, this.b + var3, var2, var5));
                           this.e.add(new Stitcher.Slot(this.a + var2, this.b, var4, this.d));
                        } else {
                           this.e.add(new Stitcher.Slot(this.a + var2, this.b, var4, var3));
                           this.e.add(new Stitcher.Slot(this.a, this.b + var3, this.c, var5));
                        }
                     } else if (var4 == 0) {
                        this.e.add(new Stitcher.Slot(this.a, this.b + var3, var2, var5));
                     } else if (var5 == 0) {
                        this.e.add(new Stitcher.Slot(this.a + var2, this.b, var4, var3));
                     }
                  }

                  for (Stitcher.Slot var9 : this.e) {
                     if (var9.a(var1)) {
                        return true;
                     }
                  }

                  return false;
               }
            } else {
               return false;
            }
         }
      }

      public void a(List<Stitcher.Slot> var1) {
         if (this.f != null) {
            var1.add(this);
         } else if (this.e != null) {
            for (Stitcher.Slot var3 : this.e) {
               var3.a(var1);
            }
         }
      }

      @Override
      public String toString() {
         return "Slot{originX="
            + this.a
            + ", originY="
            + this.b
            + ", width="
            + this.c
            + ", height="
            + this.d
            + ", texture="
            + this.f
            + ", subSlots="
            + this.e
            + '}';
      }
   }
}
