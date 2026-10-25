package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class GuiLabel extends Gui {
   protected int a = 200;
   protected int f = 20;
   public int g;
   public int h;
   private List<String> k;
   public int i;
   private boolean l;
   public boolean j = true;
   private boolean m;
   private int n;
   private int o;
   private int p;
   private int q;
   private FontRenderer r;
   private int s;

   public GuiLabel(FontRenderer var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      this.r = var1;
      this.i = var2;
      this.g = var3;
      this.h = var4;
      this.a = var5;
      this.f = var6;
      this.k = Lists.newArrayList();
      this.l = false;
      this.m = false;
      this.n = var7;
      this.o = -1;
      this.p = -1;
      this.q = -1;
      this.s = 0;
   }

   public void a(String var1) {
      this.k.add(I18n.a(var1));
   }

   public GuiLabel a() {
      this.l = true;
      return this;
   }

   public void a(Minecraft var1, int var2, int var3) {
      if (this.j) {
         GlStateManager.l();
         GlStateManager.a(770, 771, 1, 0);
         this.b(var1, var2, var3);
         int var4 = this.h + this.f / 2 + this.s / 2;
         int var5 = var4 - this.k.size() * 10 / 2;

         for (int var6 = 0; var6 < this.k.size(); var6++) {
            if (this.l) {
               this.a(this.r, this.k.get(var6), this.g + this.a / 2, var5 + var6 * 10, this.n);
            } else {
               this.c(this.r, this.k.get(var6), this.g, var5 + var6 * 10, this.n);
            }
         }
      }
   }

   protected void b(Minecraft var1, int var2, int var3) {
      if (this.m) {
         int var4 = this.a + this.s * 2;
         int var5 = this.f + this.s * 2;
         int var6 = this.g - this.s;
         int var7 = this.h - this.s;
         a(var6, var7, var6 + var4, var7 + var5, this.o);
         this.a(var6, var6 + var4, var7, this.p);
         this.a(var6, var6 + var4, var7 + var5, this.q);
         this.b(var6, var7, var7 + var5, this.p);
         this.b(var6 + var4, var7, var7 + var5, this.q);
      }
   }
}
