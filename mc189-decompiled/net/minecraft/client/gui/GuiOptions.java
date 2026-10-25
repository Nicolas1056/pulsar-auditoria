package net.minecraft.client.gui;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.stream.GuiStreamOptions;
import net.minecraft.client.gui.stream.GuiStreamUnavailable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.stream.IStream;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.EnumDifficulty;

public class GuiOptions extends GuiScreen implements GuiYesNoCallback {
   private static final GameSettings.Options[] f = new GameSettings.Options[]{GameSettings.Options.c};
   private final GuiScreen g;
   private final GameSettings h;
   private GuiButton i;
   private GuiLockIconButton r;
   protected String a = "Options";

   public GuiOptions(GuiScreen var1, GameSettings var2) {
      this.g = var1;
      this.h = var2;
   }

   @Override
   public void b() {
      int var1 = 0;
      this.a = I18n.a("options.title");

      for (GameSettings.Options var5 : f) {
         if (var5.a()) {
            this.n.add(new GuiOptionSlider(var5.c(), this.l / 2 - 155 + var1 % 2 * 160, this.m / 6 - 12 + 24 * (var1 >> 1), var5));
         } else {
            GuiOptionButton var6 = new GuiOptionButton(var5.c(), this.l / 2 - 155 + var1 % 2 * 160, this.m / 6 - 12 + 24 * (var1 >> 1), var5, this.h.c(var5));
            this.n.add(var6);
         }

         var1++;
      }

      if (this.j.f != null) {
         EnumDifficulty var7 = this.j.f.aa();
         this.i = new GuiButton(108, this.l / 2 - 155 + var1 % 2 * 160, this.m / 6 - 12 + 24 * (var1 >> 1), 150, 20, this.a(var7));
         this.n.add(this.i);
         if (this.j.F() && !this.j.f.P().t()) {
            this.i.a(this.i.b() - 20);
            this.r = new GuiLockIconButton(109, this.i.h + this.i.b(), this.i.i);
            this.n.add(this.r);
            this.r.b(this.j.f.P().z());
            this.r.l = !this.r.c();
            this.i.l = !this.r.c();
         } else {
            this.i.l = false;
         }
      } else {
         GuiOptionButton var8 = new GuiOptionButton(
            GameSettings.Options.S.c(),
            this.l / 2 - 155 + var1 % 2 * 160,
            this.m / 6 - 12 + 24 * (var1 >> 1),
            GameSettings.Options.S,
            this.h.c(GameSettings.Options.S)
         );
         this.n.add(var8);
      }

      this.n.add(new GuiButton(110, this.l / 2 - 155, this.m / 6 + 48 - 6, 150, 20, I18n.a("options.skinCustomisation")));
      this.n.add(new GuiButton(8675309, this.l / 2 + 5, this.m / 6 + 48 - 6, 150, 20, "Super Secret Settings...") {
         @Override
         public void a(SoundHandler var1) {
            SoundEventAccessorComposite var2 = var1.a(SoundCategory.g, SoundCategory.e, SoundCategory.f, SoundCategory.h, SoundCategory.d);
            if (var2 != null) {
               var1.a(PositionedSoundRecord.a(var2.c(), 0.5F));
            }
         }
      });
      this.n.add(new GuiButton(106, this.l / 2 - 155, this.m / 6 + 72 - 6, 150, 20, I18n.a("options.sounds")));
      this.n.add(new GuiButton(107, this.l / 2 + 5, this.m / 6 + 72 - 6, 150, 20, I18n.a("options.stream")));
      this.n.add(new GuiButton(101, this.l / 2 - 155, this.m / 6 + 96 - 6, 150, 20, I18n.a("options.video")));
      this.n.add(new GuiButton(100, this.l / 2 + 5, this.m / 6 + 96 - 6, 150, 20, I18n.a("options.controls")));
      this.n.add(new GuiButton(102, this.l / 2 - 155, this.m / 6 + 120 - 6, 150, 20, I18n.a("options.language")));
      this.n.add(new GuiButton(103, this.l / 2 + 5, this.m / 6 + 120 - 6, 150, 20, I18n.a("options.chat.title")));
      this.n.add(new GuiButton(105, this.l / 2 - 155, this.m / 6 + 144 - 6, 150, 20, I18n.a("options.resourcepack")));
      this.n.add(new GuiButton(104, this.l / 2 + 5, this.m / 6 + 144 - 6, 150, 20, I18n.a("options.snooper.view")));
      this.n.add(new GuiButton(200, this.l / 2 - 100, this.m / 6 + 168, I18n.a("gui.done")));
   }

   public String a(EnumDifficulty var1) {
      ChatComponentText var2 = new ChatComponentText("");
      var2.a(new ChatComponentTranslation("options.difficulty"));
      var2.a(": ");
      var2.a(new ChatComponentTranslation(var1.b()));
      return var2.d();
   }

   @Override
   public void a(boolean var1, int var2) {
      this.j.a(this);
      if (var2 == 109 && var1 && this.j.f != null) {
         this.j.f.P().e(true);
         this.r.b(true);
         this.r.l = false;
         this.i.l = false;
      }
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k < 100 && var1 instanceof GuiOptionButton) {
            GameSettings.Options var2 = ((GuiOptionButton)var1).c();
            this.h.a(var2, 1);
            var1.j = this.h.c(GameSettings.Options.a(var1.k));
         }

         if (var1.k == 108) {
            this.j.f.P().a(EnumDifficulty.a(this.j.f.aa().a() + 1));
            this.i.j = this.a(this.j.f.aa());
         }

         if (var1.k == 109) {
            this.j
               .a(
                  new GuiYesNo(
                     this,
                     new ChatComponentTranslation("difficulty.lock.title").d(),
                     new ChatComponentTranslation("difficulty.lock.question", new ChatComponentTranslation(this.j.f.P().y().b())).d(),
                     109
                  )
               );
         }

         if (var1.k == 110) {
            this.j.t.b();
            this.j.a(new GuiCustomizeSkin(this));
         }

         if (var1.k == 8675309) {
            this.j.o.d();
         }

         if (var1.k == 101) {
            this.j.t.b();
            this.j.a(new GuiVideoSettings(this, this.h));
         }

         if (var1.k == 100) {
            this.j.t.b();
            this.j.a(new GuiControls(this, this.h));
         }

         if (var1.k == 102) {
            this.j.t.b();
            this.j.a(new GuiLanguage(this, this.h, this.j.S()));
         }

         if (var1.k == 103) {
            this.j.t.b();
            this.j.a(new ScreenChatOptions(this, this.h));
         }

         if (var1.k == 104) {
            this.j.t.b();
            this.j.a(new GuiSnooper(this, this.h));
         }

         if (var1.k == 200) {
            this.j.t.b();
            this.j.a(this.g);
         }

         if (var1.k == 105) {
            this.j.t.b();
            this.j.a(new GuiScreenResourcePacks(this));
         }

         if (var1.k == 106) {
            this.j.t.b();
            this.j.a(new GuiScreenOptionsSounds(this, this.h));
         }

         if (var1.k == 107) {
            this.j.t.b();
            IStream var3 = this.j.Y();
            if (var3.i() && var3.A()) {
               this.j.a(new GuiStreamOptions(this, this.h));
            } else {
               GuiStreamUnavailable.a(this);
            }
         }
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.a(this.q, this.a, this.l / 2, 15, 16777215);
      super.a(var1, var2, var3);
   }
}
