package net.minecraft.client.gui;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.border.WorldBorder;

public class GuiIngame extends Gui {
   private static final ResourceLocation f = new ResourceLocation("textures/misc/vignette.png");
   private static final ResourceLocation g = new ResourceLocation("textures/gui/widgets.png");
   private static final ResourceLocation h = new ResourceLocation("textures/misc/pumpkinblur.png");
   private final Random i = new Random();
   private final Minecraft j;
   private final RenderItem k;
   private final GuiNewChat l;
   private final GuiStreamIndicator m;
   private int n;
   private String o = "";
   private int p;
   private boolean q;
   public float a = 1.0F;
   private int r;
   private ItemStack s;
   private final GuiOverlayDebug t;
   private final GuiSpectator u;
   private final GuiPlayerTabOverlay v;
   private int w;
   private String x = "";
   private String y = "";
   private int z;
   private int A;
   private int B;
   private int C = 0;
   private int D = 0;
   private long E = 0L;
   private long F = 0L;

   public GuiIngame(Minecraft var1) {
      this.j = var1;
      this.k = var1.ag();
      this.t = new GuiOverlayDebug(var1);
      this.u = new GuiSpectator(var1);
      this.l = new GuiNewChat(var1);
      this.m = new GuiStreamIndicator(var1);
      this.v = new GuiPlayerTabOverlay(var1, this);
      this.a();
   }

   public void a() {
      this.z = 10;
      this.A = 70;
      this.B = 20;
   }

   public void a(float var1) {
      ScaledResolution var2 = new ScaledResolution(this.j);
      int var3 = var2.a();
      int var4 = var2.b();
      this.j.o.j();
      GlStateManager.l();
      if (Minecraft.w()) {
         this.a(this.j.h.c(var1), var2);
      } else {
         GlStateManager.a(770, 771, 1, 0);
      }

      ItemStack var5 = this.j.h.bi.e(3);
      if (this.j.t.aB == 0 && var5 != null && var5.b() == Item.a(Blocks.aU)) {
         this.e(var2);
      }

      if (!this.j.h.a(Potion.k)) {
         float var6 = this.j.h.bI + (this.j.h.bH - this.j.h.bI) * var1;
         if (var6 > 0.0F) {
            this.b(var6, var2);
         }
      }

      if (this.j.c.a()) {
         this.u.a(var2, var1);
      } else {
         this.a(var2, var1);
      }

      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.j.P().a(d);
      GlStateManager.l();
      if (this.b()) {
         GlStateManager.a(775, 769, 1, 0);
         GlStateManager.d();
         this.b(var3 / 2 - 7, var4 / 2 - 7, 0, 0, 16, 16);
      }

      GlStateManager.a(770, 771, 1, 0);
      this.j.A.a("bossHealth");
      this.j();
      this.j.A.b();
      if (this.j.c.b()) {
         this.d(var2);
      }

      GlStateManager.k();
      if (this.j.h.cg() > 0) {
         this.j.A.a("sleep");
         GlStateManager.i();
         GlStateManager.c();
         int var11 = this.j.h.cg();
         float var7 = var11 / 100.0F;
         if (var7 > 1.0F) {
            var7 = 1.0F - (var11 - 100) / 10.0F;
         }

         int var8 = (int)(220.0F * var7) << 24 | 1052704;
         a(0, 0, var3, var4, var8);
         GlStateManager.d();
         GlStateManager.j();
         this.j.A.b();
      }

      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      int var12 = var3 / 2 - 91;
      if (this.j.h.y()) {
         this.a(var2, var12);
      } else if (this.j.c.f()) {
         this.b(var2, var12);
      }

      if (this.j.t.D && !this.j.c.a()) {
         this.a(var2);
      } else if (this.j.h.v()) {
         this.u.a(var2);
      }

      if (this.j.t()) {
         this.b(var2);
      }

      if (this.j.t.aC) {
         this.t.a(var2);
      }

      if (this.p > 0) {
         this.j.A.a("overlayMessage");
         float var13 = this.p - var1;
         int var16 = (int)(var13 * 255.0F / 20.0F);
         if (var16 > 255) {
            var16 = 255;
         }

         if (var16 > 8) {
            GlStateManager.E();
            GlStateManager.b((float)(var3 / 2), (float)(var4 - 68), 0.0F);
            GlStateManager.l();
            GlStateManager.a(770, 771, 1, 0);
            int var9 = 16777215;
            if (this.q) {
               var9 = MathHelper.c(var13 / 50.0F, 0.7F, 0.6F) & 16777215;
            }

            this.f().a(this.o, -this.f().a(this.o) / 2, -4, var9 + (var16 << 24 & 0xFF000000));
            GlStateManager.k();
            GlStateManager.F();
         }

         this.j.A.b();
      }

      if (this.w > 0) {
         this.j.A.a("titleAndSubtitle");
         float var14 = this.w - var1;
         int var17 = 255;
         if (this.w > this.B + this.A) {
            float var20 = this.z + this.A + this.B - var14;
            var17 = (int)(var20 * 255.0F / this.z);
         }

         if (this.w <= this.B) {
            var17 = (int)(var14 * 255.0F / this.B);
         }

         var17 = MathHelper.a(var17, 0, 255);
         if (var17 > 8) {
            GlStateManager.E();
            GlStateManager.b((float)(var3 / 2), (float)(var4 / 2), 0.0F);
            GlStateManager.l();
            GlStateManager.a(770, 771, 1, 0);
            GlStateManager.E();
            GlStateManager.a(4.0F, 4.0F, 4.0F);
            int var21 = var17 << 24 & 0xFF000000;
            this.f().a(this.x, -this.f().a(this.x) / 2, -10.0F, 16777215 | var21, true);
            GlStateManager.F();
            GlStateManager.E();
            GlStateManager.a(2.0F, 2.0F, 2.0F);
            this.f().a(this.y, -this.f().a(this.y) / 2, 5.0F, 16777215 | var21, true);
            GlStateManager.F();
            GlStateManager.k();
            GlStateManager.F();
         }

         this.j.A.b();
      }

      Scoreboard var15 = this.j.f.Z();
      ScoreObjective var19 = null;
      ScorePlayerTeam var22 = var15.h(this.j.h.e_());
      if (var22 != null) {
         int var10 = var22.l().b();
         if (var10 >= 0) {
            var19 = var15.a(3 + var10);
         }
      }

      ScoreObjective var23 = var19 != null ? var19 : var15.a(1);
      if (var23 != null) {
         this.a(var23, var2);
      }

      GlStateManager.l();
      GlStateManager.a(770, 771, 1, 0);
      GlStateManager.c();
      GlStateManager.E();
      GlStateManager.b(0.0F, (float)(var4 - 48), 0.0F);
      this.j.A.a("chat");
      this.l.a(this.n);
      this.j.A.b();
      GlStateManager.F();
      var23 = var15.a(0);
      if (!this.j.t.al.d() || this.j.E() && this.j.h.a.d().size() <= 1 && var23 == null) {
         this.v.a(false);
      } else {
         this.v.a(true);
         this.v.a(var3, var15, var23);
      }

      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.f();
      GlStateManager.d();
   }

   protected void a(ScaledResolution var1, float var2) {
      if (this.j.ac() instanceof EntityPlayer) {
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         this.j.P().a(g);
         EntityPlayer var3 = (EntityPlayer)this.j.ac();
         int var4 = var1.a() / 2;
         float var5 = this.e;
         this.e = -90.0F;
         this.b(var4 - 91, var1.b() - 22, 0, 0, 182, 22);
         this.b(var4 - 91 - 1 + var3.bi.c * 20, var1.b() - 22 - 1, 0, 22, 24, 22);
         this.e = var5;
         GlStateManager.B();
         GlStateManager.l();
         GlStateManager.a(770, 771, 1, 0);
         RenderHelper.c();

         for (int var6 = 0; var6 < 9; var6++) {
            int var7 = var1.a() / 2 - 90 + var6 * 20 + 2;
            int var8 = var1.b() - 16 - 3;
            this.a(var6, var7, var8, var2, var3);
         }

         RenderHelper.a();
         GlStateManager.C();
         GlStateManager.k();
      }
   }

   public void a(ScaledResolution var1, int var2) {
      this.j.A.a("jumpBar");
      this.j.P().a(Gui.d);
      float var3 = this.j.h.z();
      short var4 = 182;
      int var5 = (int)(var3 * (var4 + 1));
      int var6 = var1.b() - 32 + 3;
      this.b(var2, var6, 0, 84, var4, 5);
      if (var5 > 0) {
         this.b(var2, var6, 0, 89, var5, 5);
      }

      this.j.A.b();
   }

   public void b(ScaledResolution var1, int var2) {
      this.j.A.a("expBar");
      this.j.P().a(Gui.d);
      int var3 = this.j.h.ck();
      if (var3 > 0) {
         short var4 = 182;
         int var5 = (int)(this.j.h.bD * (var4 + 1));
         int var6 = var1.b() - 32 + 3;
         this.b(var2, var6, 0, 64, var4, 5);
         if (var5 > 0) {
            this.b(var2, var6, 0, 69, var5, 5);
         }
      }

      this.j.A.b();
      if (this.j.h.bB > 0) {
         this.j.A.a("expLevel");
         int var9 = 8453920;
         String var10 = "" + this.j.h.bB;
         int var11 = (var1.a() - this.f().a(var10)) / 2;
         int var7 = var1.b() - 31 - 4;
         boolean var8 = false;
         this.f().a(var10, var11 + 1, var7, 0);
         this.f().a(var10, var11 - 1, var7, 0);
         this.f().a(var10, var11, var7 + 1, 0);
         this.f().a(var10, var11, var7 - 1, 0);
         this.f().a(var10, var11, var7, var9);
         this.j.A.b();
      }
   }

   public void a(ScaledResolution var1) {
      this.j.A.a("selectedItemName");
      if (this.r > 0 && this.s != null) {
         Object var2 = this.s.q();
         if (this.s.s()) {
            var2 = EnumChatFormatting.u + var2;
         }

         int var3 = (var1.a() - this.f().a((String)var2)) / 2;
         int var4 = var1.b() - 59;
         if (!this.j.c.b()) {
            var4 += 14;
         }

         int var5 = (int)(this.r * 256.0F / 10.0F);
         if (var5 > 255) {
            var5 = 255;
         }

         if (var5 > 0) {
            GlStateManager.E();
            GlStateManager.l();
            GlStateManager.a(770, 771, 1, 0);
            this.f().a((String)var2, (float)var3, (float)var4, 16777215 + (var5 << 24));
            GlStateManager.k();
            GlStateManager.F();
         }
      }

      this.j.A.b();
   }

   public void b(ScaledResolution var1) {
      this.j.A.a("demo");
      String var2 = "";
      if (this.j.f.K() >= 120500L) {
         var2 = I18n.a("demo.demoExpired");
      } else {
         var2 = I18n.a("demo.remainingTime", StringUtils.a((int)(120500L - this.j.f.K())));
      }

      int var3 = this.f().a(var2);
      this.f().a(var2, (float)(var1.a() - var3 - 10), 5.0F, 16777215);
      this.j.A.b();
   }

   protected boolean b() {
      if (this.j.t.aC && !this.j.h.cq() && !this.j.t.w) {
         return false;
      } else if (this.j.c.a()) {
         if (this.j.i != null) {
            return true;
         } else {
            if (this.j.s != null && this.j.s.a == MovingObjectPosition.MovingObjectType.b) {
               BlockPos var1 = this.j.s.a();
               if (this.j.f.s(var1) instanceof IInventory) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return true;
      }
   }

   public void c(ScaledResolution var1) {
      this.m.a(var1.a() - 10, 10);
   }

   private void a(ScoreObjective var1, ScaledResolution var2) {
      Scoreboard var3 = var1.a();
      Collection var4 = var3.i(var1);
      ArrayList var5 = Lists.newArrayList(Iterables.filter(var4, new Predicate<Score>() {
         public boolean a(Score var1) {
            return var1.e() != null && !var1.e().startsWith("#");
         }
      }));
      ArrayList var21;
      if (var5.size() > 15) {
         var21 = Lists.newArrayList(Iterables.skip(var5, var4.size() - 15));
      } else {
         var21 = var5;
      }

      int var6 = this.f().a(var1.d());

      for (Score var8 : var21) {
         ScorePlayerTeam var9 = var3.h(var8.e());
         String var10 = ScorePlayerTeam.a(var9, var8.e()) + ": " + EnumChatFormatting.m + var8.c();
         var6 = Math.max(var6, this.f().a(var10));
      }

      int var22 = var21.size() * this.f().a;
      int var23 = var2.b() / 2 + var22 / 3;
      byte var24 = 3;
      int var25 = var2.a() - var6 - var24;
      int var11 = 0;

      for (Score var13 : var21) {
         var11++;
         ScorePlayerTeam var14 = var3.h(var13.e());
         String var15 = ScorePlayerTeam.a(var14, var13.e());
         String var16 = EnumChatFormatting.m + "" + var13.c();
         int var18 = var23 - var11 * this.f().a;
         int var19 = var2.a() - var24 + 2;
         a(var25 - 2, var18, var19, var18 + this.f().a, 1342177280);
         this.f().a(var15, var25, var18, 553648127);
         this.f().a(var16, var19 - this.f().a(var16), var18, 553648127);
         if (var11 == var21.size()) {
            String var20 = var1.d();
            a(var25 - 2, var18 - this.f().a - 1, var19, var18 - 1, 1610612736);
            a(var25 - 2, var18 - 1, var19, var18, 1342177280);
            this.f().a(var20, var25 + var6 / 2 - this.f().a(var20) / 2, var18 - this.f().a, 553648127);
         }
      }
   }

   private void d(ScaledResolution var1) {
      if (this.j.ac() instanceof EntityPlayer) {
         EntityPlayer var2 = (EntityPlayer)this.j.ac();
         int var3 = MathHelper.f(var2.bn());
         boolean var4 = this.F > this.n && (this.F - this.n) / 3L % 2L == 1L;
         if (var3 < this.C && var2.Z > 0) {
            this.E = Minecraft.J();
            this.F = this.n + 20;
         } else if (var3 > this.C && var2.Z > 0) {
            this.E = Minecraft.J();
            this.F = this.n + 10;
         }

         if (Minecraft.J() - this.E > 1000L) {
            this.C = var3;
            this.D = var3;
            this.E = Minecraft.J();
         }

         this.C = var3;
         int var5 = this.D;
         this.i.setSeed(this.n * 312871);
         boolean var6 = false;
         FoodStats var7 = var2.cl();
         int var8 = var7.a();
         int var9 = var7.b();
         IAttributeInstance var10 = var2.a(SharedMonsterAttributes.a);
         int var11 = var1.a() / 2 - 91;
         int var12 = var1.a() / 2 + 91;
         int var13 = var1.b() - 39;
         float var14 = (float)var10.e();
         float var15 = var2.bN();
         int var16 = MathHelper.f((var14 + var15) / 2.0F / 10.0F);
         int var17 = Math.max(10 - (var16 - 2), 3);
         int var18 = var13 - (var16 - 1) * var17 - 10;
         float var19 = var15;
         int var20 = var2.br();
         int var21 = -1;
         if (var2.a(Potion.l)) {
            var21 = this.n % MathHelper.f(var14 + 5.0F);
         }

         this.j.A.a("armor");

         for (int var22 = 0; var22 < 10; var22++) {
            if (var20 > 0) {
               int var23 = var11 + var22 * 8;
               if (var22 * 2 + 1 < var20) {
                  this.b(var23, var18, 34, 9, 9, 9);
               }

               if (var22 * 2 + 1 == var20) {
                  this.b(var23, var18, 25, 9, 9, 9);
               }

               if (var22 * 2 + 1 > var20) {
                  this.b(var23, var18, 16, 9, 9, 9);
               }
            }
         }

         this.j.A.c("health");

         for (int var34 = MathHelper.f((var14 + var15) / 2.0F) - 1; var34 >= 0; var34--) {
            byte var36 = 16;
            if (var2.a(Potion.u)) {
               var36 += 36;
            } else if (var2.a(Potion.v)) {
               var36 += 72;
            }

            byte var24 = 0;
            if (var4) {
               var24 = 1;
            }

            int var25 = MathHelper.f((var34 + 1) / 10.0F) - 1;
            int var26 = var11 + var34 % 10 * 8;
            int var27 = var13 - var25 * var17;
            if (var3 <= 4) {
               var27 += this.i.nextInt(2);
            }

            if (var34 == var21) {
               var27 -= 2;
            }

            byte var28 = 0;
            if (var2.o.P().t()) {
               var28 = 5;
            }

            this.b(var26, var27, 16 + var24 * 9, 9 * var28, 9, 9);
            if (var4) {
               if (var34 * 2 + 1 < var5) {
                  this.b(var26, var27, var36 + 54, 9 * var28, 9, 9);
               }

               if (var34 * 2 + 1 == var5) {
                  this.b(var26, var27, var36 + 63, 9 * var28, 9, 9);
               }
            }

            if (var19 > 0.0F) {
               if (var19 == var15 && var15 % 2.0F == 1.0F) {
                  this.b(var26, var27, var36 + 153, 9 * var28, 9, 9);
               } else {
                  this.b(var26, var27, var36 + 144, 9 * var28, 9, 9);
               }

               var19 -= 2.0F;
            } else {
               if (var34 * 2 + 1 < var3) {
                  this.b(var26, var27, var36 + 36, 9 * var28, 9, 9);
               }

               if (var34 * 2 + 1 == var3) {
                  this.b(var26, var27, var36 + 45, 9 * var28, 9, 9);
               }
            }
         }

         Entity var35 = var2.m;
         if (var35 == null) {
            this.j.A.c("food");

            for (int var37 = 0; var37 < 10; var37++) {
               int var40 = var13;
               byte var43 = 16;
               byte var46 = 0;
               if (var2.a(Potion.s)) {
                  var43 += 36;
                  var46 = 13;
               }

               if (var2.cl().e() <= 0.0F && this.n % (var8 * 3 + 1) == 0) {
                  var40 = var13 + (this.i.nextInt(3) - 1);
               }

               if (var6) {
                  var46 = 1;
               }

               int var49 = var12 - var37 * 8 - 9;
               this.b(var49, var40, 16 + var46 * 9, 27, 9, 9);
               if (var6) {
                  if (var37 * 2 + 1 < var9) {
                     this.b(var49, var40, var43 + 54, 27, 9, 9);
                  }

                  if (var37 * 2 + 1 == var9) {
                     this.b(var49, var40, var43 + 63, 27, 9, 9);
                  }
               }

               if (var37 * 2 + 1 < var8) {
                  this.b(var49, var40, var43 + 36, 27, 9, 9);
               }

               if (var37 * 2 + 1 == var8) {
                  this.b(var49, var40, var43 + 45, 27, 9, 9);
               }
            }
         } else if (var35 instanceof EntityLivingBase) {
            this.j.A.c("mountHealth");
            EntityLivingBase var38 = (EntityLivingBase)var35;
            int var41 = (int)Math.ceil(var38.bn());
            float var44 = var38.bu();
            int var47 = (int)(var44 + 0.5F) / 2;
            if (var47 > 30) {
               var47 = 30;
            }

            int var50 = var13;

            for (byte var51 = 0; var47 > 0; var51 += 20) {
               int var29 = Math.min(var47, 10);
               var47 -= var29;

               for (int var30 = 0; var30 < var29; var30++) {
                  byte var31 = 52;
                  byte var32 = 0;
                  if (var6) {
                     var32 = 1;
                  }

                  int var33 = var12 - var30 * 8 - 9;
                  this.b(var33, var50, var31 + var32 * 9, 9, 9, 9);
                  if (var30 * 2 + 1 + var51 < var41) {
                     this.b(var33, var50, var31 + 36, 9, 9, 9);
                  }

                  if (var30 * 2 + 1 + var51 == var41) {
                     this.b(var33, var50, var31 + 45, 9, 9, 9);
                  }
               }

               var50 -= 10;
            }
         }

         this.j.A.c("air");
         if (var2.a(Material.h)) {
            int var39 = this.j.h.az();
            int var42 = MathHelper.f((var39 - 2) * 10.0 / 300.0);
            int var45 = MathHelper.f(var39 * 10.0 / 300.0) - var42;

            for (int var48 = 0; var48 < var42 + var45; var48++) {
               if (var48 < var42) {
                  this.b(var12 - var48 * 8 - 9, var18, 16, 18, 9, 9);
               } else {
                  this.b(var12 - var48 * 8 - 9, var18, 25, 18, 9, 9);
               }
            }
         }

         this.j.A.b();
      }
   }

   private void j() {
      if (BossStatus.c != null && BossStatus.b > 0) {
         BossStatus.b--;
         FontRenderer var1 = this.j.k;
         ScaledResolution var2 = new ScaledResolution(this.j);
         int var3 = var2.a();
         short var4 = 182;
         int var5 = var3 / 2 - var4 / 2;
         int var6 = (int)(BossStatus.a * (var4 + 1));
         byte var7 = 12;
         this.b(var5, var7, 0, 74, var4, 5);
         this.b(var5, var7, 0, 74, var4, 5);
         if (var6 > 0) {
            this.b(var5, var7, 0, 79, var6, 5);
         }

         String var8 = BossStatus.c;
         this.f().a(var8, (float)(var3 / 2 - this.f().a(var8) / 2), (float)(var7 - 10), 16777215);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         this.j.P().a(d);
      }
   }

   private void e(ScaledResolution var1) {
      GlStateManager.i();
      GlStateManager.a(false);
      GlStateManager.a(770, 771, 1, 0);
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.c();
      this.j.P().a(h);
      Tessellator var2 = Tessellator.a();
      WorldRenderer var3 = var2.c();
      var3.a(7, DefaultVertexFormats.g);
      var3.b(0.0, (double)var1.b(), -90.0).a(0.0, 1.0).d();
      var3.b((double)var1.a(), (double)var1.b(), -90.0).a(1.0, 1.0).d();
      var3.b((double)var1.a(), 0.0, -90.0).a(1.0, 0.0).d();
      var3.b(0.0, 0.0, -90.0).a(0.0, 0.0).d();
      var2.b();
      GlStateManager.a(true);
      GlStateManager.j();
      GlStateManager.d();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void a(float var1, ScaledResolution var2) {
      var1 = 1.0F - var1;
      var1 = MathHelper.a(var1, 0.0F, 1.0F);
      WorldBorder var3 = this.j.f.af();
      float var4 = (float)var3.a(this.j.h);
      double var5 = Math.min(var3.o() * var3.p() * 1000.0, Math.abs(var3.j() - var3.h()));
      double var7 = Math.max((double)var3.q(), var5);
      if (var4 < var7) {
         var4 = 1.0F - (float)(var4 / var7);
      } else {
         var4 = 0.0F;
      }

      this.a = (float)(this.a + (var1 - this.a) * 0.01);
      GlStateManager.i();
      GlStateManager.a(false);
      GlStateManager.a(0, 769, 1, 0);
      if (var4 > 0.0F) {
         GlStateManager.c(0.0F, var4, var4, 1.0F);
      } else {
         GlStateManager.c(this.a, this.a, this.a, 1.0F);
      }

      this.j.P().a(f);
      Tessellator var9 = Tessellator.a();
      WorldRenderer var10 = var9.c();
      var10.a(7, DefaultVertexFormats.g);
      var10.b(0.0, (double)var2.b(), -90.0).a(0.0, 1.0).d();
      var10.b((double)var2.a(), (double)var2.b(), -90.0).a(1.0, 1.0).d();
      var10.b((double)var2.a(), 0.0, -90.0).a(1.0, 0.0).d();
      var10.b(0.0, 0.0, -90.0).a(0.0, 0.0).d();
      var9.b();
      GlStateManager.a(true);
      GlStateManager.j();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.a(770, 771, 1, 0);
   }

   private void b(float var1, ScaledResolution var2) {
      if (var1 < 1.0F) {
         var1 *= var1;
         var1 *= var1;
         var1 = var1 * 0.8F + 0.2F;
      }

      GlStateManager.c();
      GlStateManager.i();
      GlStateManager.a(false);
      GlStateManager.a(770, 771, 1, 0);
      GlStateManager.c(1.0F, 1.0F, 1.0F, var1);
      this.j.P().a(TextureMap.g);
      TextureAtlasSprite var3 = this.j.ae().a().a(Blocks.aY.Q());
      float var4 = var3.e();
      float var5 = var3.g();
      float var6 = var3.f();
      float var7 = var3.h();
      Tessellator var8 = Tessellator.a();
      WorldRenderer var9 = var8.c();
      var9.a(7, DefaultVertexFormats.g);
      var9.b(0.0, (double)var2.b(), -90.0).a((double)var4, (double)var7).d();
      var9.b((double)var2.a(), (double)var2.b(), -90.0).a((double)var6, (double)var7).d();
      var9.b((double)var2.a(), 0.0, -90.0).a((double)var6, (double)var5).d();
      var9.b(0.0, 0.0, -90.0).a((double)var4, (double)var5).d();
      var8.b();
      GlStateManager.a(true);
      GlStateManager.j();
      GlStateManager.d();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void a(int var1, int var2, int var3, float var4, EntityPlayer var5) {
      ItemStack var6 = var5.bi.a[var1];
      if (var6 != null) {
         float var7 = var6.c - var4;
         if (var7 > 0.0F) {
            GlStateManager.E();
            float var8 = 1.0F + var7 / 5.0F;
            GlStateManager.b((float)(var2 + 8), (float)(var3 + 12), 0.0F);
            GlStateManager.a(1.0F / var8, (var8 + 1.0F) / 2.0F, 1.0F);
            GlStateManager.b((float)(-(var2 + 8)), (float)(-(var3 + 12)), 0.0F);
         }

         this.k.b(var6, var2, var3);
         if (var7 > 0.0F) {
            GlStateManager.F();
         }

         this.k.a(this.j.k, var6, var2, var3);
      }
   }

   public void c() {
      if (this.p > 0) {
         this.p--;
      }

      if (this.w > 0) {
         this.w--;
         if (this.w <= 0) {
            this.x = "";
            this.y = "";
         }
      }

      this.n++;
      this.m.a();
      if (this.j.h != null) {
         ItemStack var1 = this.j.h.bi.h();
         if (var1 == null) {
            this.r = 0;
         } else if (this.s != null && var1.b() == this.s.b() && ItemStack.a(var1, this.s) && (var1.e() || var1.i() == this.s.i())) {
            if (this.r > 0) {
               this.r--;
            }
         } else {
            this.r = 40;
         }

         this.s = var1;
      }
   }

   public void a(String var1) {
      this.a(I18n.a("record.nowPlaying", var1), true);
   }

   public void a(String var1, boolean var2) {
      this.o = var1;
      this.p = 60;
      this.q = var2;
   }

   public void a(String var1, String var2, int var3, int var4, int var5) {
      if (var1 == null && var2 == null && var3 < 0 && var4 < 0 && var5 < 0) {
         this.x = "";
         this.y = "";
         this.w = 0;
      } else if (var1 != null) {
         this.x = var1;
         this.w = this.z + this.A + this.B;
      } else if (var2 != null) {
         this.y = var2;
      } else {
         if (var3 >= 0) {
            this.z = var3;
         }

         if (var4 >= 0) {
            this.A = var4;
         }

         if (var5 >= 0) {
            this.B = var5;
         }

         if (this.w > 0) {
            this.w = this.z + this.A + this.B;
         }
      }
   }

   public void a(IChatComponent var1, boolean var2) {
      this.a(var1.c(), var2);
   }

   public GuiNewChat d() {
      return this.l;
   }

   public int e() {
      return this.n;
   }

   public FontRenderer f() {
      return this.j.k;
   }

   public GuiSpectator g() {
      return this.u;
   }

   public GuiPlayerTabOverlay h() {
      return this.v;
   }

   public void i() {
      this.v.a();
   }
}
