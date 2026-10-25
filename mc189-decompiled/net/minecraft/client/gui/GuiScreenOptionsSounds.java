package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiScreenOptionsSounds extends GuiScreen {
   private final GuiScreen f;
   private final GameSettings g;
   protected String a = "Options";
   private String h;

   public GuiScreenOptionsSounds(GuiScreen var1, GameSettings var2) {
      this.f = var1;
      this.g = var2;
   }

   @Override
   public void b() {
      int var1 = 0;
      this.a = I18n.a("options.sounds.title");
      this.h = I18n.a("options.off");
      this.n
         .add(
            new GuiScreenOptionsSounds.Button(SoundCategory.a.b(), this.l / 2 - 155 + var1 % 2 * 160, this.m / 6 - 12 + 24 * (var1 >> 1), SoundCategory.a, true)
         );
      var1 += 2;

      for (SoundCategory var5 : SoundCategory.values()) {
         if (var5 != SoundCategory.a) {
            this.n.add(new GuiScreenOptionsSounds.Button(var5.b(), this.l / 2 - 155 + var1 % 2 * 160, this.m / 6 - 12 + 24 * (var1 >> 1), var5, false));
            var1++;
         }
      }

      this.n.add(new GuiButton(200, this.l / 2 - 100, this.m / 6 + 168, I18n.a("gui.done")));
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k == 200) {
            this.j.t.b();
            this.j.a(this.f);
         }
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.a(this.q, this.a, this.l / 2, 15, 16777215);
      super.a(var1, var2, var3);
   }

   protected String a(SoundCategory var1) {
      float var2 = this.g.a(var1);
      return var2 == 0.0F ? this.h : (int)(var2 * 100.0F) + "%";
   }

   class Button extends GuiButton {
      private final SoundCategory r;
      private final String s;
      public float o = 1.0F;
      public boolean p;

      public Button(int var2, int var3, int var4, SoundCategory var5, boolean var6) {
         super(var2, var3, var4, var6 ? 310 : 150, 20, "");
         this.r = var5;
         this.s = I18n.a("soundCategory." + var5.a());
         this.j = this.s + ": " + GuiScreenOptionsSounds.this.a(var5);
         this.o = GuiScreenOptionsSounds.this.g.a(var5);
      }

      @Override
      protected int a(boolean var1) {
         return 0;
      }

      @Override
      protected void b(Minecraft var1, int var2, int var3) {
         if (this.m) {
            if (this.p) {
               this.o = (float)(var2 - (this.h + 4)) / (this.f - 8);
               this.o = MathHelper.a(this.o, 0.0F, 1.0F);
               var1.t.a(this.r, this.o);
               var1.t.b();
               this.j = this.s + ": " + GuiScreenOptionsSounds.this.a(this.r);
            }

            GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
            this.b(this.h + (int)(this.o * (this.f - 8)), this.i, 0, 66, 4, 20);
            this.b(this.h + (int)(this.o * (this.f - 8)) + 4, this.i, 196, 66, 4, 20);
         }
      }

      @Override
      public boolean c(Minecraft var1, int var2, int var3) {
         if (super.c(var1, var2, var3)) {
            this.o = (float)(var2 - (this.h + 4)) / (this.f - 8);
            this.o = MathHelper.a(this.o, 0.0F, 1.0F);
            var1.t.a(this.r, this.o);
            var1.t.b();
            this.j = this.s + ": " + GuiScreenOptionsSounds.this.a(this.r);
            this.p = true;
            return true;
         } else {
            return false;
         }
      }

      @Override
      public void a(SoundHandler var1) {
      }

      @Override
      public void a(int var1, int var2) {
         if (this.p) {
            if (this.r == SoundCategory.a) {
               float var10000 = 1.0F;
            } else {
               GuiScreenOptionsSounds.this.g.a(this.r);
            }

            GuiScreenOptionsSounds.this.j.W().a(PositionedSoundRecord.a(new ResourceLocation("gui.button.press"), 1.0F));
         }

         this.p = false;
      }
   }
}
