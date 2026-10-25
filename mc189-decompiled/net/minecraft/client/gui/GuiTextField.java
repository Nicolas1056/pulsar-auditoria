package net.minecraft.client.gui;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.MathHelper;

public class GuiTextField extends Gui {
   private final int g;
   private final FontRenderer h;
   public int a;
   public int f;
   private final int i;
   private final int j;
   private String k = "";
   private int l = 32;
   private int m;
   private boolean n = true;
   private boolean o = true;
   private boolean p;
   private boolean q = true;
   private int r;
   private int s;
   private int t;
   private int u = 14737632;
   private int v = 7368816;
   private boolean w = true;
   private GuiPageButtonList.GuiResponder x;
   private Predicate<String> y = Predicates.alwaysTrue();

   public GuiTextField(int var1, FontRenderer var2, int var3, int var4, int var5, int var6) {
      this.g = var1;
      this.h = var2;
      this.a = var3;
      this.f = var4;
      this.i = var5;
      this.j = var6;
   }

   public void a(GuiPageButtonList.GuiResponder var1) {
      this.x = var1;
   }

   public void a() {
      this.m++;
   }

   public void a(String var1) {
      if (this.y.apply(var1)) {
         if (var1.length() > this.l) {
            this.k = var1.substring(0, this.l);
         } else {
            this.k = var1;
         }

         this.f();
      }
   }

   public String b() {
      return this.k;
   }

   public String c() {
      int var1 = this.s < this.t ? this.s : this.t;
      int var2 = this.s < this.t ? this.t : this.s;
      return this.k.substring(var1, var2);
   }

   public void a(Predicate<String> var1) {
      this.y = var1;
   }

   public void b(String var1) {
      String var2 = "";
      String var3 = ChatAllowedCharacters.a(var1);
      int var4 = this.s < this.t ? this.s : this.t;
      int var5 = this.s < this.t ? this.t : this.s;
      int var6 = this.l - this.k.length() - (var4 - var5);
      int var7 = 0;
      if (this.k.length() > 0) {
         var2 = var2 + this.k.substring(0, var4);
      }

      if (var6 < var3.length()) {
         var2 = var2 + var3.substring(0, var6);
         var7 = var6;
      } else {
         var2 = var2 + var3;
         var7 = var3.length();
      }

      if (this.k.length() > 0 && var5 < this.k.length()) {
         var2 = var2 + this.k.substring(var5);
      }

      if (this.y.apply(var2)) {
         this.k = var2;
         this.d(var4 - this.t + var7);
         if (this.x != null) {
            this.x.a(this.g, this.k);
         }
      }
   }

   public void a(int var1) {
      if (this.k.length() != 0) {
         if (this.t != this.s) {
            this.b("");
         } else {
            this.b(this.c(var1) - this.s);
         }
      }
   }

   public void b(int var1) {
      if (this.k.length() != 0) {
         if (this.t != this.s) {
            this.b("");
         } else {
            boolean var2 = var1 < 0;
            int var3 = var2 ? this.s + var1 : this.s;
            int var4 = var2 ? this.s : this.s + var1;
            String var5 = "";
            if (var3 >= 0) {
               var5 = this.k.substring(0, var3);
            }

            if (var4 < this.k.length()) {
               var5 = var5 + this.k.substring(var4);
            }

            if (this.y.apply(var5)) {
               this.k = var5;
               if (var2) {
                  this.d(var1);
               }

               if (this.x != null) {
                  this.x.a(this.g, this.k);
               }
            }
         }
      }
   }

   public int d() {
      return this.g;
   }

   public int c(int var1) {
      return this.a(var1, this.i());
   }

   public int a(int var1, int var2) {
      return this.a(var1, var2, true);
   }

   public int a(int var1, int var2, boolean var3) {
      int var4 = var2;
      boolean var5 = var1 < 0;
      int var6 = Math.abs(var1);

      for (int var7 = 0; var7 < var6; var7++) {
         if (!var5) {
            int var8 = this.k.length();
            var4 = this.k.indexOf(32, var4);
            if (var4 == -1) {
               var4 = var8;
            } else {
               while (var3 && var4 < var8 && this.k.charAt(var4) == ' ') {
                  var4++;
               }
            }
         } else {
            while (var3 && var4 > 0 && this.k.charAt(var4 - 1) == ' ') {
               var4--;
            }

            while (var4 > 0 && this.k.charAt(var4 - 1) != ' ') {
               var4--;
            }
         }
      }

      return var4;
   }

   public void d(int var1) {
      this.e(this.t + var1);
   }

   public void e(int var1) {
      this.s = var1;
      int var2 = this.k.length();
      this.s = MathHelper.a(this.s, 0, var2);
      this.i(this.s);
   }

   public void e() {
      this.e(0);
   }

   public void f() {
      this.e(this.k.length());
   }

   public boolean a(char var1, int var2) {
      if (!this.p) {
         return false;
      } else if (GuiScreen.g(var2)) {
         this.f();
         this.i(0);
         return true;
      } else if (GuiScreen.f(var2)) {
         GuiScreen.e(this.c());
         return true;
      } else if (GuiScreen.e(var2)) {
         if (this.q) {
            this.b(GuiScreen.o());
         }

         return true;
      } else if (GuiScreen.d(var2)) {
         GuiScreen.e(this.c());
         if (this.q) {
            this.b("");
         }

         return true;
      } else {
         switch (var2) {
            case 14:
               if (GuiScreen.q()) {
                  if (this.q) {
                     this.a(-1);
                  }
               } else if (this.q) {
                  this.b(-1);
               }

               return true;
            case 199:
               if (GuiScreen.r()) {
                  this.i(0);
               } else {
                  this.e();
               }

               return true;
            case 203:
               if (GuiScreen.r()) {
                  if (GuiScreen.q()) {
                     this.i(this.a(-1, this.o()));
                  } else {
                     this.i(this.o() - 1);
                  }
               } else if (GuiScreen.q()) {
                  this.e(this.c(-1));
               } else {
                  this.d(-1);
               }

               return true;
            case 205:
               if (GuiScreen.r()) {
                  if (GuiScreen.q()) {
                     this.i(this.a(1, this.o()));
                  } else {
                     this.i(this.o() + 1);
                  }
               } else if (GuiScreen.q()) {
                  this.e(this.c(1));
               } else {
                  this.d(1);
               }

               return true;
            case 207:
               if (GuiScreen.r()) {
                  this.i(this.k.length());
               } else {
                  this.f();
               }

               return true;
            case 211:
               if (GuiScreen.q()) {
                  if (this.q) {
                     this.a(1);
                  }
               } else if (this.q) {
                  this.b(1);
               }

               return true;
            default:
               if (ChatAllowedCharacters.a(var1)) {
                  if (this.q) {
                     this.b(Character.toString(var1));
                  }

                  return true;
               } else {
                  return false;
               }
         }
      }
   }

   public void a(int var1, int var2, int var3) {
      boolean var4 = var1 >= this.a && var1 < this.a + this.i && var2 >= this.f && var2 < this.f + this.j;
      if (this.o) {
         this.b(var4);
      }

      if (this.p && var4 && var3 == 0) {
         int var5 = var1 - this.a;
         if (this.n) {
            var5 -= 4;
         }

         String var6 = this.h.a(this.k.substring(this.r), this.p());
         this.e(this.h.a(var6, var5).length() + this.r);
      }
   }

   public void g() {
      if (this.r()) {
         if (this.j()) {
            a(this.a - 1, this.f - 1, this.a + this.i + 1, this.f + this.j + 1, -6250336);
            a(this.a, this.f, this.a + this.i, this.f + this.j, -16777216);
         }

         int var1 = this.q ? this.u : this.v;
         int var2 = this.s - this.r;
         int var3 = this.t - this.r;
         String var4 = this.h.a(this.k.substring(this.r), this.p());
         boolean var5 = var2 >= 0 && var2 <= var4.length();
         boolean var6 = this.p && this.m / 6 % 2 == 0 && var5;
         int var7 = this.n ? this.a + 4 : this.a;
         int var8 = this.n ? this.f + (this.j - 8) / 2 : this.f;
         int var9 = var7;
         if (var3 > var4.length()) {
            var3 = var4.length();
         }

         if (var4.length() > 0) {
            String var10 = var5 ? var4.substring(0, var2) : var4;
            var9 = this.h.a(var10, (float)var7, (float)var8, var1);
         }

         boolean var14 = this.s < this.k.length() || this.k.length() >= this.h();
         int var11 = var9;
         if (!var5) {
            var11 = var2 > 0 ? var7 + this.i : var7;
         } else if (var14) {
            var11 = var9 - 1;
            var9--;
         }

         if (var4.length() > 0 && var5 && var2 < var4.length()) {
            var9 = this.h.a(var4.substring(var2), (float)var9, (float)var8, var1);
         }

         if (var6) {
            if (var14) {
               Gui.a(var11, var8 - 1, var11 + 1, var8 + 1 + this.h.a, -3092272);
            } else {
               this.h.a("_", (float)var11, (float)var8, var1);
            }
         }

         if (var3 != var2) {
            int var12 = var7 + this.h.a(var4.substring(0, var3));
            this.c(var11, var8 - 1, var12 - 1, var8 + 1 + this.h.a);
         }
      }
   }

   private void c(int var1, int var2, int var3, int var4) {
      if (var1 < var3) {
         int var5 = var1;
         var1 = var3;
         var3 = var5;
      }

      if (var2 < var4) {
         int var7 = var2;
         var2 = var4;
         var4 = var7;
      }

      if (var3 > this.a + this.i) {
         var3 = this.a + this.i;
      }

      if (var1 > this.a + this.i) {
         var1 = this.a + this.i;
      }

      Tessellator var8 = Tessellator.a();
      WorldRenderer var6 = var8.c();
      GlStateManager.c(0.0F, 0.0F, 255.0F, 255.0F);
      GlStateManager.x();
      GlStateManager.u();
      GlStateManager.f(5387);
      var6.a(7, DefaultVertexFormats.e);
      var6.b((double)var1, (double)var4, 0.0).d();
      var6.b((double)var3, (double)var4, 0.0).d();
      var6.b((double)var3, (double)var2, 0.0).d();
      var6.b((double)var1, (double)var2, 0.0).d();
      var8.b();
      GlStateManager.v();
      GlStateManager.w();
   }

   public void f(int var1) {
      this.l = var1;
      if (this.k.length() > var1) {
         this.k = this.k.substring(0, var1);
      }
   }

   public int h() {
      return this.l;
   }

   public int i() {
      return this.s;
   }

   public boolean j() {
      return this.n;
   }

   public void a(boolean var1) {
      this.n = var1;
   }

   public void g(int var1) {
      this.u = var1;
   }

   public void h(int var1) {
      this.v = var1;
   }

   public void b(boolean var1) {
      if (var1 && !this.p) {
         this.m = 0;
      }

      this.p = var1;
   }

   public boolean m() {
      return this.p;
   }

   public void c(boolean var1) {
      this.q = var1;
   }

   public int o() {
      return this.t;
   }

   public int p() {
      return this.j() ? this.i - 8 : this.i;
   }

   public void i(int var1) {
      int var2 = this.k.length();
      if (var1 > var2) {
         var1 = var2;
      }

      if (var1 < 0) {
         var1 = 0;
      }

      this.t = var1;
      if (this.h != null) {
         if (this.r > var2) {
            this.r = var2;
         }

         int var3 = this.p();
         String var4 = this.h.a(this.k.substring(this.r), var3);
         int var5 = var4.length() + this.r;
         if (var1 == this.r) {
            this.r = this.r - this.h.a(this.k, var3, true).length();
         }

         if (var1 > var5) {
            this.r += var1 - var5;
         } else if (var1 <= this.r) {
            this.r = this.r - (this.r - var1);
         }

         this.r = MathHelper.a(this.r, 0, var2);
      }
   }

   public void d(boolean var1) {
      this.o = var1;
   }

   public boolean r() {
      return this.w;
   }

   public void e(boolean var1) {
      this.w = var1;
   }
}
