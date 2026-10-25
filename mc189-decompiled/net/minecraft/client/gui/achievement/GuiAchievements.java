package net.minecraft.client.gui.achievement;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class GuiAchievements extends GuiScreen implements IProgressMeter {
   private static final int y = AchievementList.a * 24 - 112;
   private static final int z = AchievementList.b * 24 - 112;
   private static final int A = AchievementList.c * 24 - 77;
   private static final int B = AchievementList.d * 24 - 77;
   private static final ResourceLocation C = new ResourceLocation("textures/gui/achievement/achievement_background.png");
   protected GuiScreen a;
   protected int f = 256;
   protected int g = 202;
   protected int h;
   protected int i;
   protected float r = 1.0F;
   protected double s;
   protected double t;
   protected double u;
   protected double v;
   protected double w;
   protected double x;
   private int D;
   private StatFileWriter E;
   private boolean F = true;

   public GuiAchievements(GuiScreen var1, StatFileWriter var2) {
      this.a = var1;
      this.E = var2;
      short var3 = 141;
      short var4 = 141;
      this.s = this.u = this.w = AchievementList.f.a * 24 - var3 / 2 - 12;
      this.t = this.v = this.x = AchievementList.f.b * 24 - var4 / 2;
   }

   @Override
   public void b() {
      this.j.u().a(new C16PacketClientStatus(C16PacketClientStatus.EnumState.b));
      this.n.clear();
      this.n.add(new GuiOptionButton(1, this.l / 2 + 24, this.m / 2 + 74, 80, 20, I18n.a("gui.done")));
   }

   @Override
   protected void a(GuiButton var1) {
      if (!this.F) {
         if (var1.k == 1) {
            this.j.a(this.a);
         }
      }
   }

   @Override
   protected void a(char var1, int var2) {
      if (var2 == this.j.t.af.i()) {
         this.j.a(null);
         this.j.n();
      } else {
         super.a(var1, var2);
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      if (this.F) {
         this.c();
         this.a(this.q, I18n.a("multiplayer.downloadingStats"), this.l / 2, this.m / 2, 16777215);
         this.a(this.q, c_[(int)(Minecraft.J() / 150L % c_.length)], this.l / 2, this.m / 2 + this.q.a * 2, 16777215);
      } else {
         if (Mouse.isButtonDown(0)) {
            int var4 = (this.l - this.f) / 2;
            int var5 = (this.m - this.g) / 2;
            int var6 = var4 + 8;
            int var7 = var5 + 17;
            if ((this.D == 0 || this.D == 1) && var1 >= var6 && var1 < var6 + 224 && var2 >= var7 && var2 < var7 + 155) {
               if (this.D == 0) {
                  this.D = 1;
               } else {
                  this.u = this.u - (var1 - this.h) * this.r;
                  this.v = this.v - (var2 - this.i) * this.r;
                  this.w = this.s = this.u;
                  this.x = this.t = this.v;
               }

               this.h = var1;
               this.i = var2;
            }
         } else {
            this.D = 0;
         }

         int var11 = Mouse.getDWheel();
         float var12 = this.r;
         if (var11 < 0) {
            this.r += 0.25F;
         } else if (var11 > 0) {
            this.r -= 0.25F;
         }

         this.r = MathHelper.a(this.r, 1.0F, 2.0F);
         if (this.r != var12) {
            float var13 = var12 - this.r;
            float var14 = var12 * this.f;
            float var8 = var12 * this.g;
            float var9 = this.r * this.f;
            float var10 = this.r * this.g;
            this.u -= (var9 - var14) * 0.5F;
            this.v -= (var10 - var8) * 0.5F;
            this.w = this.s = this.u;
            this.x = this.t = this.v;
         }

         if (this.w < y) {
            this.w = y;
         }

         if (this.x < z) {
            this.x = z;
         }

         if (this.w >= A) {
            this.w = A - 1;
         }

         if (this.x >= B) {
            this.x = B - 1;
         }

         this.c();
         this.b(var1, var2, var3);
         GlStateManager.f();
         GlStateManager.i();
         this.f();
         GlStateManager.e();
         GlStateManager.j();
      }
   }

   @Override
   public void a() {
      if (this.F) {
         this.F = false;
      }
   }

   @Override
   public void e() {
      if (!this.F) {
         this.s = this.u;
         this.t = this.v;
         double var1 = this.w - this.u;
         double var3 = this.x - this.v;
         if (var1 * var1 + var3 * var3 < 4.0) {
            this.u += var1;
            this.v += var3;
         } else {
            this.u += var1 * 0.85;
            this.v += var3 * 0.85;
         }
      }
   }

   protected void f() {
      int var1 = (this.l - this.f) / 2;
      int var2 = (this.m - this.g) / 2;
      this.q.a(I18n.a("gui.achievements"), var1 + 15, var2 + 5, 4210752);
   }

   protected void b(int var1, int var2, float var3) {
      int var4 = MathHelper.c(this.s + (this.u - this.s) * var3);
      int var5 = MathHelper.c(this.t + (this.v - this.t) * var3);
      if (var4 < y) {
         var4 = y;
      }

      if (var5 < z) {
         var5 = z;
      }

      if (var4 >= A) {
         var4 = A - 1;
      }

      if (var5 >= B) {
         var5 = B - 1;
      }

      int var6 = (this.l - this.f) / 2;
      int var7 = (this.m - this.g) / 2;
      int var8 = var6 + 16;
      int var9 = var7 + 17;
      this.e = 0.0F;
      GlStateManager.c(518);
      GlStateManager.E();
      GlStateManager.b((float)var8, (float)var9, -200.0F);
      GlStateManager.a(1.0F / this.r, 1.0F / this.r, 0.0F);
      GlStateManager.w();
      GlStateManager.f();
      GlStateManager.B();
      GlStateManager.g();
      int var10 = var4 + 288 >> 4;
      int var11 = var5 + 288 >> 4;
      int var12 = (var4 + 288) % 16;
      int var13 = (var5 + 288) % 16;
      byte var14 = 4;
      byte var15 = 8;
      byte var16 = 10;
      byte var17 = 22;
      byte var18 = 37;
      Random var19 = new Random();
      float var20 = 16.0F / this.r;
      float var21 = 16.0F / this.r;

      for (int var22 = 0; var22 * var20 - var13 < 155.0F; var22++) {
         float var23 = 0.6F - (var11 + var22) / 25.0F * 0.3F;
         GlStateManager.c(var23, var23, var23, 1.0F);

         for (int var24 = 0; var24 * var21 - var12 < 224.0F; var24++) {
            var19.setSeed(this.j.L().b().hashCode() + var10 + var24 + (var11 + var22) * 16);
            int var25 = var19.nextInt(1 + var11 + var22) + (var11 + var22) / 2;
            TextureAtlasSprite var26 = this.a(Blocks.m);
            if (var25 > 37 || var11 + var22 == 35) {
               Block var27 = Blocks.h;
               var26 = this.a(var27);
            } else if (var25 == 22) {
               if (var19.nextInt(2) == 0) {
                  var26 = this.a(Blocks.ag);
               } else {
                  var26 = this.a(Blocks.aC);
               }
            } else if (var25 == 10) {
               var26 = this.a(Blocks.p);
            } else if (var25 == 8) {
               var26 = this.a(Blocks.q);
            } else if (var25 > 4) {
               var26 = this.a(Blocks.b);
            } else if (var25 > 0) {
               var26 = this.a(Blocks.d);
            }

            this.j.P().a(TextureMap.g);
            this.a(var24 * 16 - var12, var22 * 16 - var13, var26, 16, 16);
         }
      }

      GlStateManager.j();
      GlStateManager.c(515);
      this.j.P().a(C);

      for (int var33 = 0; var33 < AchievementList.e.size(); var33++) {
         Achievement var35 = AchievementList.e.get(var33);
         if (var35.c != null) {
            int var37 = var35.a * 24 - var4 + 11;
            int var39 = var35.b * 24 - var5 + 11;
            int var42 = var35.c.a * 24 - var4 + 11;
            int var45 = var35.c.b * 24 - var5 + 11;
            boolean var28 = this.E.a(var35);
            boolean var29 = this.E.b(var35);
            int var30 = this.E.c(var35);
            if (var30 <= 4) {
               int var31 = -16777216;
               if (var28) {
                  var31 = -6250336;
               } else if (var29) {
                  var31 = -16711936;
               }

               this.a(var37, var42, var39, var31);
               this.b(var42, var39, var45, var31);
               if (var37 > var42) {
                  this.b(var37 - 11 - 7, var39 - 5, 114, 234, 7, 11);
               } else if (var37 < var42) {
                  this.b(var37 + 11, var39 - 5, 107, 234, 7, 11);
               } else if (var39 > var45) {
                  this.b(var37 - 5, var39 - 11 - 7, 96, 234, 11, 7);
               } else if (var39 < var45) {
                  this.b(var37 - 5, var39 + 11, 96, 241, 11, 7);
               }
            }
         }
      }

      Achievement var34 = null;
      float var36 = (var1 - var8) * this.r;
      float var38 = (var2 - var9) * this.r;
      RenderHelper.c();
      GlStateManager.f();
      GlStateManager.B();
      GlStateManager.g();

      for (int var40 = 0; var40 < AchievementList.e.size(); var40++) {
         Achievement var43 = AchievementList.e.get(var40);
         int var46 = var43.a * 24 - var4;
         int var48 = var43.b * 24 - var5;
         if (var46 >= -24 && var48 >= -24 && var46 <= 224.0F * this.r && var48 <= 155.0F * this.r) {
            int var50 = this.E.c(var43);
            if (this.E.a(var43)) {
               float var52 = 0.75F;
               GlStateManager.c(var52, var52, var52, 1.0F);
            } else if (this.E.b(var43)) {
               float var53 = 1.0F;
               GlStateManager.c(var53, var53, var53, 1.0F);
            } else if (var50 < 3) {
               float var54 = 0.3F;
               GlStateManager.c(var54, var54, var54, 1.0F);
            } else if (var50 == 3) {
               float var55 = 0.2F;
               GlStateManager.c(var55, var55, var55, 1.0F);
            } else {
               if (var50 != 4) {
                  continue;
               }

               float var56 = 0.1F;
               GlStateManager.c(var56, var56, var56, 1.0F);
            }

            this.j.P().a(C);
            if (var43.g()) {
               this.b(var46 - 2, var48 - 2, 26, 202, 26, 26);
            } else {
               this.b(var46 - 2, var48 - 2, 0, 202, 26, 26);
            }

            if (!this.E.b(var43)) {
               float var57 = 0.1F;
               GlStateManager.c(var57, var57, var57, 1.0F);
               this.k.a(false);
            }

            GlStateManager.e();
            GlStateManager.o();
            this.k.b(var43.d, var46 + 3, var48 + 3);
            GlStateManager.b(770, 771);
            GlStateManager.f();
            if (!this.E.b(var43)) {
               this.k.a(true);
            }

            GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
            if (var36 >= var46 && var36 <= var46 + 22 && var38 >= var48 && var38 <= var48 + 22) {
               var34 = var43;
            }
         }
      }

      GlStateManager.i();
      GlStateManager.l();
      GlStateManager.F();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.j.P().a(C);
      this.b(var6, var7, 0, 0, this.f, this.g);
      this.e = 0.0F;
      GlStateManager.c(515);
      GlStateManager.i();
      GlStateManager.w();
      super.a(var1, var2, var3);
      if (var34 != null) {
         String var41 = var34.e().c();
         String var44 = var34.f();
         int var47 = var1 + 12;
         int var49 = var2 - 4;
         int var51 = this.E.c(var34);
         if (this.E.b(var34)) {
            int var58 = Math.max(this.q.a(var41), 120);
            int var61 = this.q.b(var44, var58);
            if (this.E.a(var34)) {
               var61 += 12;
            }

            this.a(var47 - 3, var49 - 3, var47 + var58 + 3, var49 + var61 + 3 + 12, -1073741824, -1073741824);
            this.q.a(var44, var47, var49 + 12, var58, -6250336);
            if (this.E.a(var34)) {
               this.q.a(I18n.a("achievement.taken"), (float)var47, (float)(var49 + var61 + 4), -7302913);
            }
         } else if (var51 == 3) {
            var41 = I18n.a("achievement.unknown");
            int var59 = Math.max(this.q.a(var41), 120);
            String var62 = new ChatComponentTranslation("achievement.requires", var34.c.e()).c();
            int var32 = this.q.b(var62, var59);
            this.a(var47 - 3, var49 - 3, var47 + var59 + 3, var49 + var32 + 12 + 3, -1073741824, -1073741824);
            this.q.a(var62, var47, var49 + 12, var59, -9416624);
         } else if (var51 < 3) {
            int var60 = Math.max(this.q.a(var41), 120);
            String var63 = new ChatComponentTranslation("achievement.requires", var34.c.e()).c();
            int var64 = this.q.b(var63, var60);
            this.a(var47 - 3, var49 - 3, var47 + var60 + 3, var49 + var64 + 12 + 3, -1073741824, -1073741824);
            this.q.a(var63, var47, var49 + 12, var60, -9416624);
         } else {
            var41 = null;
         }

         if (var41 != null) {
            this.q.a(var41, (float)var47, (float)var49, this.E.b(var34) ? (var34.g() ? -128 : -1) : (var34.g() ? -8355776 : -8355712));
         }
      }

      GlStateManager.j();
      GlStateManager.e();
      RenderHelper.a();
   }

   private TextureAtlasSprite a(Block var1) {
      return Minecraft.A().ae().a().a(var1.Q());
   }

   @Override
   public boolean d() {
      return !this.F;
   }
}
