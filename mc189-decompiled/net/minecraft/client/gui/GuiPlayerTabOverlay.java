package net.minecraft.client.gui;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettings;

public class GuiPlayerTabOverlay extends Gui {
   private static final Ordering<NetworkPlayerInfo> a = Ordering.from(new GuiPlayerTabOverlay.PlayerComparator());
   private final Minecraft f;
   private final GuiIngame g;
   private IChatComponent h;
   private IChatComponent i;
   private long j;
   private boolean k;

   public GuiPlayerTabOverlay(Minecraft var1, GuiIngame var2) {
      this.f = var1;
      this.g = var2;
   }

   public String a(NetworkPlayerInfo var1) {
      return var1.k() != null ? var1.k().d() : ScorePlayerTeam.a(var1.i(), var1.a().getName());
   }

   public void a(boolean var1) {
      if (var1 && !this.k) {
         this.j = Minecraft.J();
      }

      this.k = var1;
   }

   public void a(int var1, Scoreboard var2, ScoreObjective var3) {
      NetHandlerPlayClient var4 = this.f.h.a;
      List var5 = a.sortedCopy(var4.d());
      int var6 = 0;
      int var7 = 0;

      for (NetworkPlayerInfo var9 : var5) {
         int var10 = this.f.k.a(this.a(var9));
         var6 = Math.max(var6, var10);
         if (var3 != null && var3.e() != IScoreObjectiveCriteria.EnumRenderType.b) {
            var10 = this.f.k.a(" " + var2.c(var9.a().getName(), var3).c());
            var7 = Math.max(var7, var10);
         }
      }

      var5 = var5.subList(0, Math.min(var5.size(), 80));
      int var34 = var5.size();
      int var35 = var34;

      int var37;
      for (var37 = 1; var35 > 20; var35 = (var34 + var37 - 1) / var37) {
         var37++;
      }

      boolean var11 = this.f.E() || this.f.u().a().f();
      int var12;
      if (var3 != null) {
         if (var3.e() == IScoreObjectiveCriteria.EnumRenderType.b) {
            var12 = 90;
         } else {
            var12 = var7;
         }
      } else {
         var12 = 0;
      }

      int var13 = Math.min(var37 * ((var11 ? 9 : 0) + var6 + var12 + 13), var1 - 50) / var37;
      int var14 = var1 / 2 - (var13 * var37 + (var37 - 1) * 5) / 2;
      int var15 = 10;
      int var16 = var13 * var37 + (var37 - 1) * 5;
      List var17 = null;
      List var18 = null;
      if (this.i != null) {
         var17 = this.f.k.c(this.i.d(), var1 - 50);

         for (String var20 : var17) {
            var16 = Math.max(var16, this.f.k.a(var20));
         }
      }

      if (this.h != null) {
         var18 = this.f.k.c(this.h.d(), var1 - 50);

         for (String var43 : var18) {
            var16 = Math.max(var16, this.f.k.a(var43));
         }
      }

      if (var17 != null) {
         a(var1 / 2 - var16 / 2 - 1, var15 - 1, var1 / 2 + var16 / 2 + 1, var15 + var17.size() * this.f.k.a, Integer.MIN_VALUE);

         for (String var44 : var17) {
            int var21 = this.f.k.a(var44);
            this.f.k.a(var44, (float)(var1 / 2 - var21 / 2), (float)var15, -1);
            var15 += this.f.k.a;
         }

         var15++;
      }

      a(var1 / 2 - var16 / 2 - 1, var15 - 1, var1 / 2 + var16 / 2 + 1, var15 + var35 * 9, Integer.MIN_VALUE);

      for (int var41 = 0; var41 < var34; var41++) {
         int var45 = var41 / var35;
         int var47 = var41 % var35;
         int var22 = var14 + var45 * var13 + var45 * 5;
         int var23 = var15 + var47 * 9;
         a(var22, var23, var22 + var13, var23 + 8, 553648127);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.d();
         GlStateManager.l();
         GlStateManager.a(770, 771, 1, 0);
         if (var41 < var5.size()) {
            NetworkPlayerInfo var24 = (NetworkPlayerInfo)var5.get(var41);
            Object var25 = this.a(var24);
            GameProfile var26 = var24.a();
            if (var11) {
               EntityPlayer var27 = this.f.f.b(var26.getId());
               boolean var28 = var27 != null && var27.a(EnumPlayerModelParts.a) && (var26.getName().equals("Dinnerbone") || var26.getName().equals("Grumm"));
               this.f.P().a(var24.g());
               int var29 = 8 + (var28 ? 8 : 0);
               int var30 = 8 * (var28 ? -1 : 1);
               Gui.a(var22, var23, 8.0F, var29, 8, var30, 8, 8, 64.0F, 64.0F);
               if (var27 != null && var27.a(EnumPlayerModelParts.g)) {
                  int var31 = 8 + (var28 ? 8 : 0);
                  int var32 = 8 * (var28 ? -1 : 1);
                  Gui.a(var22, var23, 40.0F, var31, 8, var32, 8, 8, 64.0F, 64.0F);
               }

               var22 += 9;
            }

            if (var24.b() == WorldSettings.GameType.e) {
               var25 = EnumChatFormatting.u + var25;
               this.f.k.a((String)var25, (float)var22, (float)var23, -1862270977);
            } else {
               this.f.k.a((String)var25, (float)var22, (float)var23, -1);
            }

            if (var3 != null && var24.b() != WorldSettings.GameType.e) {
               int var50 = var22 + var6 + 1;
               int var51 = var50 + var12;
               if (var51 - var50 > 5) {
                  this.a(var3, var23, var26.getName(), var50, var51, var24);
               }
            }

            this.a(var13, var22 - (var11 ? 9 : 0), var23, var24);
         }
      }

      if (var18 != null) {
         var15 += var35 * 9 + 1;
         a(var1 / 2 - var16 / 2 - 1, var15 - 1, var1 / 2 + var16 / 2 + 1, var15 + var18.size() * this.f.k.a, Integer.MIN_VALUE);

         for (String var46 : var18) {
            int var48 = this.f.k.a(var46);
            this.f.k.a(var46, (float)(var1 / 2 - var48 / 2), (float)var15, -1);
            var15 += this.f.k.a;
         }
      }
   }

   protected void a(int var1, int var2, int var3, NetworkPlayerInfo var4) {
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.f.P().a(d);
      byte var5 = 0;
      byte var6 = 0;
      if (var4.c() < 0) {
         var6 = 5;
      } else if (var4.c() < 150) {
         var6 = 0;
      } else if (var4.c() < 300) {
         var6 = 1;
      } else if (var4.c() < 600) {
         var6 = 2;
      } else if (var4.c() < 1000) {
         var6 = 3;
      } else {
         var6 = 4;
      }

      this.e += 100.0F;
      this.b(var2 + var1 - 11, var3, 0 + var5 * 10, 176 + var6 * 8, 10, 8);
      this.e -= 100.0F;
   }

   private void a(ScoreObjective var1, int var2, String var3, int var4, int var5, NetworkPlayerInfo var6) {
      int var7 = var1.a().c(var3, var1).c();
      if (var1.e() == IScoreObjectiveCriteria.EnumRenderType.b) {
         this.f.P().a(d);
         if (this.j == var6.p()) {
            if (var7 < var6.l()) {
               var6.a(Minecraft.J());
               var6.b((long)(this.g.e() + 20));
            } else if (var7 > var6.l()) {
               var6.a(Minecraft.J());
               var6.b((long)(this.g.e() + 10));
            }
         }

         if (Minecraft.J() - var6.n() > 1000L || this.j != var6.p()) {
            var6.b(var7);
            var6.c(var7);
            var6.a(Minecraft.J());
         }

         var6.c(this.j);
         var6.b(var7);
         int var8 = MathHelper.f(Math.max(var7, var6.m()) / 2.0F);
         int var9 = Math.max(MathHelper.f((float)(var7 / 2)), Math.max(MathHelper.f((float)(var6.m() / 2)), 10));
         boolean var10 = var6.o() > this.g.e() && (var6.o() - this.g.e()) / 3L % 2L == 1L;
         if (var8 > 0) {
            float var11 = Math.min((float)(var5 - var4 - 4) / var9, 9.0F);
            if (var11 > 3.0F) {
               for (int var12 = var8; var12 < var9; var12++) {
                  this.a(var4 + var12 * var11, var2, var10 ? 25 : 16, 0, 9, 9);
               }

               for (int var16 = 0; var16 < var8; var16++) {
                  this.a(var4 + var16 * var11, var2, var10 ? 25 : 16, 0, 9, 9);
                  if (var10) {
                     if (var16 * 2 + 1 < var6.m()) {
                        this.a(var4 + var16 * var11, var2, 70, 0, 9, 9);
                     }

                     if (var16 * 2 + 1 == var6.m()) {
                        this.a(var4 + var16 * var11, var2, 79, 0, 9, 9);
                     }
                  }

                  if (var16 * 2 + 1 < var7) {
                     this.a(var4 + var16 * var11, var2, var16 >= 10 ? 160 : 52, 0, 9, 9);
                  }

                  if (var16 * 2 + 1 == var7) {
                     this.a(var4 + var16 * var11, var2, var16 >= 10 ? 169 : 61, 0, 9, 9);
                  }
               }
            } else {
               float var17 = MathHelper.a(var7 / 20.0F, 0.0F, 1.0F);
               int var13 = (int)((1.0F - var17) * 255.0F) << 16 | (int)(var17 * 255.0F) << 8;
               String var14 = "" + var7 / 2.0F;
               if (var5 - this.f.k.a(var14 + "hp") >= var4) {
                  var14 = var14 + "hp";
               }

               this.f.k.a(var14, (float)((var5 + var4) / 2 - this.f.k.a(var14) / 2), (float)var2, var13);
            }
         }
      } else {
         String var15 = EnumChatFormatting.o + "" + var7;
         this.f.k.a(var15, (float)(var5 - this.f.k.a(var15)), (float)var2, 16777215);
      }
   }

   public void a(IChatComponent var1) {
      this.h = var1;
   }

   public void b(IChatComponent var1) {
      this.i = var1;
   }

   public void a() {
      this.i = null;
      this.h = null;
   }

   static class PlayerComparator implements Comparator<NetworkPlayerInfo> {
      private PlayerComparator() {
      }

      public int a(NetworkPlayerInfo var1, NetworkPlayerInfo var2) {
         ScorePlayerTeam var3 = var1.i();
         ScorePlayerTeam var4 = var2.i();
         return ComparisonChain.start()
            .compareTrueFirst(var1.b() != WorldSettings.GameType.e, var2.b() != WorldSettings.GameType.e)
            .compare(var3 != null ? var3.b() : "", var4 != null ? var4.b() : "")
            .compare(var1.a().getName(), var2.a().getName())
            .result();
      }
   }
}
