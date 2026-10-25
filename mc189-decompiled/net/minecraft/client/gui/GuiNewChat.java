package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiNewChat extends Gui {
   private static final Logger a = LogManager.getLogger();
   private final Minecraft f;
   private final List<String> g = Lists.newArrayList();
   private final List<ChatLine> h = Lists.newArrayList();
   private final List<ChatLine> i = Lists.newArrayList();
   private int j;
   private boolean k;

   public GuiNewChat(Minecraft var1) {
      this.f = var1;
   }

   public void a(int var1) {
      if (this.f.t.m != EntityPlayer.EnumChatVisibility.c) {
         int var2 = this.i();
         boolean var3 = false;
         int var4 = 0;
         int var5 = this.i.size();
         float var6 = this.f.t.q * 0.9F + 0.1F;
         if (var5 > 0) {
            if (this.e()) {
               var3 = true;
            }

            float var7 = this.h();
            int var8 = MathHelper.f(this.f() / var7);
            GlStateManager.E();
            GlStateManager.b(2.0F, 20.0F, 0.0F);
            GlStateManager.a(var7, var7, 1.0F);

            for (int var9 = 0; var9 + this.j < this.i.size() && var9 < var2; var9++) {
               ChatLine var10 = this.i.get(var9 + this.j);
               if (var10 != null) {
                  int var11 = var1 - var10.b();
                  if (var11 < 200 || var3) {
                     double var12 = var11 / 200.0;
                     var12 = 1.0 - var12;
                     var12 *= 10.0;
                     var12 = MathHelper.a(var12, 0.0, 1.0);
                     var12 *= var12;
                     int var14 = (int)(255.0 * var12);
                     if (var3) {
                        var14 = 255;
                     }

                     var14 = (int)(var14 * var6);
                     var4++;
                     if (var14 > 3) {
                        byte var15 = 0;
                        int var16 = -var9 * 9;
                        a(var15, var16 - 9, var15 + var8 + 4, var16, var14 / 2 << 24);
                        String var17 = var10.a().d();
                        GlStateManager.l();
                        this.f.k.a(var17, (float)var15, (float)(var16 - 8), 16777215 + (var14 << 24));
                        GlStateManager.c();
                        GlStateManager.k();
                     }
                  }
               }
            }

            if (var3) {
               int var18 = this.f.k.a;
               GlStateManager.b(-3.0F, 0.0F, 0.0F);
               int var19 = var5 * var18 + var5;
               int var20 = var4 * var18 + var4;
               int var25 = this.j * var20 / var5;
               int var13 = var20 * var20 / var19;
               if (var19 != var20) {
                  int var27 = var25 > 0 ? 170 : 96;
                  int var28 = this.k ? 13382451 : 3355562;
                  a(0, -var25, 2, -var25 - var13, var28 + (var27 << 24));
                  a(2, -var25, 1, -var25 - var13, 13421772 + (var27 << 24));
               }
            }

            GlStateManager.F();
         }
      }
   }

   public void a() {
      this.i.clear();
      this.h.clear();
      this.g.clear();
   }

   public void a(IChatComponent var1) {
      this.a(var1, 0);
   }

   public void a(IChatComponent var1, int var2) {
      this.a(var1, var2, this.f.q.e(), false);
      a.info("[CHAT] " + var1.c());
   }

   private void a(IChatComponent var1, int var2, int var3, boolean var4) {
      if (var2 != 0) {
         this.c(var2);
      }

      int var5 = MathHelper.d(this.f() / this.h());
      List var6 = GuiUtilRenderComponents.a(var1, var5, this.f.k, false, false);
      boolean var7 = this.e();

      for (IChatComponent var9 : var6) {
         if (var7 && this.j > 0) {
            this.k = true;
            this.b(1);
         }

         this.i.add(0, new ChatLine(var3, var9, var2));
      }

      while (this.i.size() > 100) {
         this.i.remove(this.i.size() - 1);
      }

      if (!var4) {
         this.h.add(0, new ChatLine(var3, var1, var2));

         while (this.h.size() > 100) {
            this.h.remove(this.h.size() - 1);
         }
      }
   }

   public void b() {
      this.i.clear();
      this.d();

      for (int var1 = this.h.size() - 1; var1 >= 0; var1--) {
         ChatLine var2 = this.h.get(var1);
         this.a(var2.a(), var2.c(), var2.b(), true);
      }
   }

   public List<String> c() {
      return this.g;
   }

   public void a(String var1) {
      if (this.g.isEmpty() || !this.g.get(this.g.size() - 1).equals(var1)) {
         this.g.add(var1);
      }
   }

   public void d() {
      this.j = 0;
      this.k = false;
   }

   public void b(int var1) {
      this.j += var1;
      int var2 = this.i.size();
      if (this.j > var2 - this.i()) {
         this.j = var2 - this.i();
      }

      if (this.j <= 0) {
         this.j = 0;
         this.k = false;
      }
   }

   public IChatComponent a(int var1, int var2) {
      if (!this.e()) {
         return null;
      } else {
         ScaledResolution var3 = new ScaledResolution(this.f);
         int var4 = var3.e();
         float var5 = this.h();
         int var6 = var1 / var4 - 3;
         int var7 = var2 / var4 - 27;
         var6 = MathHelper.d(var6 / var5);
         var7 = MathHelper.d(var7 / var5);
         if (var6 >= 0 && var7 >= 0) {
            int var8 = Math.min(this.i(), this.i.size());
            if (var6 <= MathHelper.d(this.f() / this.h()) && var7 < this.f.k.a * var8 + var8) {
               int var9 = var7 / this.f.k.a + this.j;
               if (var9 >= 0 && var9 < this.i.size()) {
                  ChatLine var10 = this.i.get(var9);
                  int var11 = 0;

                  for (IChatComponent var13 : var10.a()) {
                     if (var13 instanceof ChatComponentText) {
                        var11 += this.f.k.a(GuiUtilRenderComponents.a(((ChatComponentText)var13).g(), false));
                        if (var11 > var6) {
                           return var13;
                        }
                     }
                  }
               }

               return null;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public boolean e() {
      return this.f.m instanceof GuiChat;
   }

   public void c(int var1) {
      Iterator var2 = this.i.iterator();

      while (var2.hasNext()) {
         ChatLine var3 = (ChatLine)var2.next();
         if (var3.c() == var1) {
            var2.remove();
         }
      }

      var2 = this.h.iterator();

      while (var2.hasNext()) {
         ChatLine var5 = (ChatLine)var2.next();
         if (var5.c() == var1) {
            var2.remove();
            break;
         }
      }
   }

   public int f() {
      return a(this.f.t.F);
   }

   public int g() {
      return b(this.e() ? this.f.t.H : this.f.t.G);
   }

   public float h() {
      return this.f.t.E;
   }

   public static int a(float var0) {
      short var1 = 320;
      byte var2 = 40;
      return MathHelper.d(var0 * (var1 - var2) + var2);
   }

   public static int b(float var0) {
      short var1 = 180;
      byte var2 = 20;
      return MathHelper.d(var0 * (var1 - var2) + var2);
   }

   public int i() {
      return this.g() / 9;
   }
}
