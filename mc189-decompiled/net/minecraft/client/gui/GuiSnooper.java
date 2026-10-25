package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map.Entry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiSnooper extends GuiScreen {
   private final GuiScreen a;
   private final GameSettings f;
   private final java.util.List<String> g = Lists.newArrayList();
   private final java.util.List<String> h = Lists.newArrayList();
   private String i;
   private String[] r;
   private GuiSnooper.List s;
   private GuiButton t;

   public GuiSnooper(GuiScreen var1, GameSettings var2) {
      this.a = var1;
      this.f = var2;
   }

   @Override
   public void b() {
      this.i = I18n.a("options.snooper.title");
      String var1 = I18n.a("options.snooper.desc");
      ArrayList var2 = Lists.newArrayList();

      for (String var4 : this.q.c(var1, this.l - 30)) {
         var2.add(var4);
      }

      this.r = var2.toArray(new String[var2.size()]);
      this.g.clear();
      this.h.clear();
      this.n.add(this.t = new GuiButton(1, this.l / 2 - 152, this.m - 30, 150, 20, this.f.c(GameSettings.Options.u)));
      this.n.add(new GuiButton(2, this.l / 2 + 2, this.m - 30, 150, 20, I18n.a("gui.done")));
      boolean var6 = this.j.G() != null && this.j.G().av() != null;

      for (Entry var5 : new TreeMap<>(this.j.I().c()).entrySet()) {
         this.g.add((var6 ? "C " : "") + (String)var5.getKey());
         this.h.add(this.q.a((String)var5.getValue(), this.l - 220));
      }

      if (var6) {
         for (Entry var9 : new TreeMap<>(this.j.G().av().c()).entrySet()) {
            this.g.add("S " + (String)var9.getKey());
            this.h.add(this.q.a((String)var9.getValue(), this.l - 220));
         }
      }

      this.s = new GuiSnooper.List();
   }

   @Override
   public void k() {
      super.k();
      this.s.p();
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k == 2) {
            this.f.b();
            this.f.b();
            this.j.a(this.a);
         }

         if (var1.k == 1) {
            this.f.a(GameSettings.Options.u, 1);
            this.t.j = this.f.c(GameSettings.Options.u);
         }
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.s.a(var1, var2, var3);
      this.a(this.q, this.i, this.l / 2, 8, 16777215);
      int var4 = 22;

      for (String var8 : this.r) {
         this.a(this.q, var8, this.l / 2, var4, 8421504);
         var4 += this.q.a;
      }

      super.a(var1, var2, var3);
   }

   class List extends GuiSlot {
      public List() {
         super(GuiSnooper.this.j, GuiSnooper.this.l, GuiSnooper.this.m, 80, GuiSnooper.this.m - 40, GuiSnooper.this.q.a + 1);
      }

      @Override
      protected int b() {
         return GuiSnooper.this.g.size();
      }

      @Override
      protected void a(int var1, boolean var2, int var3, int var4) {
      }

      @Override
      protected boolean a(int var1) {
         return false;
      }

      @Override
      protected void a() {
      }

      @Override
      protected void a(int var1, int var2, int var3, int var4, int var5, int var6) {
         GuiSnooper.this.q.a(GuiSnooper.this.g.get(var1), 10, var3, 16777215);
         GuiSnooper.this.q.a(GuiSnooper.this.h.get(var1), 230, var3, 16777215);
      }

      @Override
      protected int d() {
         return this.b - 10;
      }
   }
}
