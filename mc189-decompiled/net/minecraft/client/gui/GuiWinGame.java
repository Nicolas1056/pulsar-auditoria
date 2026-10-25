package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiWinGame extends GuiScreen {
   private static final Logger a = LogManager.getLogger();
   private static final ResourceLocation f = new ResourceLocation("textures/gui/title/minecraft.png");
   private static final ResourceLocation g = new ResourceLocation("textures/misc/vignette.png");
   private int h;
   private List<String> i;
   private int r;
   private float s = 0.5F;

   @Override
   public void e() {
      MusicTicker var1 = this.j.r();
      SoundHandler var2 = this.j.W();
      if (this.h == 0) {
         var1.a();
         var1.a(MusicTicker.MusicType.d);
         var2.e();
      }

      var2.c();
      this.h++;
      float var3 = (this.r + this.m + this.m + 24) / this.s;
      if (this.h > var3) {
         this.a();
      }
   }

   @Override
   protected void a(char var1, int var2) {
      if (var2 == 1) {
         this.a();
      }
   }

   private void a() {
      this.j.h.a.a(new C16PacketClientStatus(C16PacketClientStatus.EnumState.a));
      this.j.a(null);
   }

   @Override
   public boolean d() {
      return true;
   }

   @Override
   public void b() {
      if (this.i == null) {
         this.i = Lists.newArrayList();

         try {
            String var1 = "";
            String var2 = "" + EnumChatFormatting.p + EnumChatFormatting.q + EnumChatFormatting.k + EnumChatFormatting.l;
            short var3 = 274;
            InputStream var4 = this.j.Q().a(new ResourceLocation("texts/end.txt")).b();
            BufferedReader var5 = new BufferedReader(new InputStreamReader(var4, Charsets.UTF_8));
            Random var6 = new Random(8124371L);

            while ((var1 = var5.readLine()) != null) {
               var1 = var1.replaceAll("PLAYERNAME", this.j.L().c());

               while (var1.contains(var2)) {
                  int var7 = var1.indexOf(var2);
                  Object var8 = var1.substring(0, var7);
                  String var9 = var1.substring(var7 + var2.length());
                  var1 = var8 + EnumChatFormatting.p + EnumChatFormatting.q + "XXXXXXXX".substring(0, var6.nextInt(4) + 3) + var9;
               }

               this.i.addAll(this.j.k.c(var1, var3));
               this.i.add("");
            }

            var4.close();

            for (int var18 = 0; var18 < 8; var18++) {
               this.i.add("");
            }

            var4 = this.j.Q().a(new ResourceLocation("texts/credits.txt")).b();
            var5 = new BufferedReader(new InputStreamReader(var4, Charsets.UTF_8));

            while ((var1 = var5.readLine()) != null) {
               var1 = var1.replaceAll("PLAYERNAME", this.j.L().c());
               var1 = var1.replaceAll("\t", "    ");
               this.i.addAll(this.j.k.c(var1, var3));
               this.i.add("");
            }

            var4.close();
            this.r = this.i.size() * 12;
         } catch (Exception var10) {
            a.error("Couldn't load credits", var10);
         }
      }
   }

   private void b(int var1, int var2, float var3) {
      Tessellator var4 = Tessellator.a();
      WorldRenderer var5 = var4.c();
      this.j.P().a(Gui.b);
      var5.a(7, DefaultVertexFormats.i);
      int var6 = this.l;
      float var7 = 0.0F - (this.h + var3) * 0.5F * this.s;
      float var8 = this.m - (this.h + var3) * 0.5F * this.s;
      float var9 = 0.015625F;
      float var10 = (this.h + var3 - 0.0F) * 0.02F;
      float var11 = (this.r + this.m + this.m + 24) / this.s;
      float var12 = (var11 - 20.0F - (this.h + var3)) * 0.005F;
      if (var12 < var10) {
         var10 = var12;
      }

      if (var10 > 1.0F) {
         var10 = 1.0F;
      }

      var10 *= var10;
      var10 = var10 * 96.0F / 255.0F;
      var5.b(0.0, (double)this.m, (double)this.e).a(0.0, (double)(var7 * var9)).a(var10, var10, var10, 1.0F).d();
      var5.b((double)var6, (double)this.m, (double)this.e).a((double)(var6 * var9), (double)(var7 * var9)).a(var10, var10, var10, 1.0F).d();
      var5.b((double)var6, 0.0, (double)this.e).a((double)(var6 * var9), (double)(var8 * var9)).a(var10, var10, var10, 1.0F).d();
      var5.b(0.0, 0.0, (double)this.e).a(0.0, (double)(var8 * var9)).a(var10, var10, var10, 1.0F).d();
      var4.b();
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.b(var1, var2, var3);
      Tessellator var4 = Tessellator.a();
      WorldRenderer var5 = var4.c();
      short var6 = 274;
      int var7 = this.l / 2 - var6 / 2;
      int var8 = this.m + 50;
      float var9 = -(this.h + var3) * this.s;
      GlStateManager.E();
      GlStateManager.b(0.0F, var9, 0.0F);
      this.j.P().a(f);
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.b(var7, var8, 0, 0, 155, 44);
      this.b(var7 + 155, var8, 0, 45, 155, 44);
      int var10 = var8 + 200;

      for (int var11 = 0; var11 < this.i.size(); var11++) {
         if (var11 == this.i.size() - 1) {
            float var12 = var10 + var9 - (this.m / 2 - 6);
            if (var12 < 0.0F) {
               GlStateManager.b(0.0F, -var12, 0.0F);
            }
         }

         if (var10 + var9 + 12.0F + 8.0F > 0.0F && var10 + var9 < this.m) {
            String var14 = this.i.get(var11);
            if (var14.startsWith("[C]")) {
               this.q.a(var14.substring(3), (float)(var7 + (var6 - this.q.a(var14.substring(3))) / 2), (float)var10, 16777215);
            } else {
               this.q.b.setSeed(var11 * 4238972211L + this.h / 4);
               this.q.a(var14, (float)var7, (float)var10, 16777215);
            }
         }

         var10 += 12;
      }

      GlStateManager.F();
      this.j.P().a(g);
      GlStateManager.l();
      GlStateManager.b(0, 769);
      int var13 = this.l;
      int var15 = this.m;
      var5.a(7, DefaultVertexFormats.i);
      var5.b(0.0, (double)var15, (double)this.e).a(0.0, 1.0).a(1.0F, 1.0F, 1.0F, 1.0F).d();
      var5.b((double)var13, (double)var15, (double)this.e).a(1.0, 1.0).a(1.0F, 1.0F, 1.0F, 1.0F).d();
      var5.b((double)var13, 0.0, (double)this.e).a(1.0, 0.0).a(1.0F, 1.0F, 1.0F, 1.0F).d();
      var5.b(0.0, 0.0, (double)this.e).a(0.0, 0.0).a(1.0F, 1.0F, 1.0F, 1.0F).d();
      var4.b();
      GlStateManager.k();
      super.a(var1, var2, var3);
   }
}
