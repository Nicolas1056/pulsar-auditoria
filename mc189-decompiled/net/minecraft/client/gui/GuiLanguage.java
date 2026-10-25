package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;

public class GuiLanguage extends GuiScreen {
   protected GuiScreen a;
   private GuiLanguage.List f;
   private final GameSettings g;
   private final LanguageManager h;
   private GuiOptionButton i;
   private GuiOptionButton r;

   public GuiLanguage(GuiScreen var1, GameSettings var2, LanguageManager var3) {
      this.a = var1;
      this.g = var2;
      this.h = var3;
   }

   @Override
   public void b() {
      this.n.add(this.i = new GuiOptionButton(100, this.l / 2 - 155, this.m - 38, GameSettings.Options.E, this.g.c(GameSettings.Options.E)));
      this.n.add(this.r = new GuiOptionButton(6, this.l / 2 - 155 + 160, this.m - 38, I18n.a("gui.done")));
      this.f = new GuiLanguage.List(this.j);
      this.f.d(7, 8);
   }

   @Override
   public void k() {
      super.k();
      this.f.p();
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         switch (var1.k) {
            case 5:
               break;
            case 6:
               this.j.a(this.a);
               break;
            case 100:
               if (var1 instanceof GuiOptionButton) {
                  this.g.a(((GuiOptionButton)var1).c(), 1);
                  var1.j = this.g.c(GameSettings.Options.E);
                  ScaledResolution var2 = new ScaledResolution(this.j);
                  int var3 = var2.a();
                  int var4 = var2.b();
                  this.a(this.j, var3, var4);
               }
               break;
            default:
               this.f.a(var1);
         }
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.f.a(var1, var2, var3);
      this.a(this.q, I18n.a("options.language"), this.l / 2, 16, 16777215);
      this.a(this.q, "(" + I18n.a("options.languageWarning") + ")", this.l / 2, this.m - 56, 8421504);
      super.a(var1, var2, var3);
   }

   class List extends GuiSlot {
      private final java.util.List<String> v = Lists.newArrayList();
      private final Map<String, Language> w = Maps.newHashMap();

      public List(Minecraft var2) {
         super(var2, GuiLanguage.this.l, GuiLanguage.this.m, 32, GuiLanguage.this.m - 65 + 4, 18);

         for (Language var4 : GuiLanguage.this.h.d()) {
            this.w.put(var4.a(), var4);
            this.v.add(var4.a());
         }
      }

      @Override
      protected int b() {
         return this.v.size();
      }

      @Override
      protected void a(int var1, boolean var2, int var3, int var4) {
         Language var5 = this.w.get(this.v.get(var1));
         GuiLanguage.this.h.a(var5);
         GuiLanguage.this.g.aN = var5.a();
         this.a.e();
         GuiLanguage.this.q.a(GuiLanguage.this.h.a() || GuiLanguage.this.g.aO);
         GuiLanguage.this.q.b(GuiLanguage.this.h.b());
         GuiLanguage.this.r.j = I18n.a("gui.done");
         GuiLanguage.this.i.j = GuiLanguage.this.g.c(GameSettings.Options.E);
         GuiLanguage.this.g.b();
      }

      @Override
      protected boolean a(int var1) {
         return this.v.get(var1).equals(GuiLanguage.this.h.c().a());
      }

      @Override
      protected int k() {
         return this.b() * 18;
      }

      @Override
      protected void a() {
         GuiLanguage.this.c();
      }

      @Override
      protected void a(int var1, int var2, int var3, int var4, int var5, int var6) {
         GuiLanguage.this.q.b(true);
         GuiLanguage.this.a(GuiLanguage.this.q, this.w.get(this.v.get(var1)).toString(), this.b / 2, var3 + 1, 16777215);
         GuiLanguage.this.q.b(GuiLanguage.this.h.c().b());
      }
   }
}
