package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.resources.I18n;

public class GuiYesNo extends GuiScreen {
   protected GuiYesNoCallback a;
   protected String f;
   private String r;
   private final List<String> s = Lists.newArrayList();
   protected String g;
   protected String h;
   protected int i;
   private int t;

   public GuiYesNo(GuiYesNoCallback var1, String var2, String var3, int var4) {
      this.a = var1;
      this.f = var2;
      this.r = var3;
      this.i = var4;
      this.g = I18n.a("gui.yes");
      this.h = I18n.a("gui.no");
   }

   public GuiYesNo(GuiYesNoCallback var1, String var2, String var3, String var4, String var5, int var6) {
      this.a = var1;
      this.f = var2;
      this.r = var3;
      this.g = var4;
      this.h = var5;
      this.i = var6;
   }

   @Override
   public void b() {
      this.n.add(new GuiOptionButton(0, this.l / 2 - 155, this.m / 6 + 96, this.g));
      this.n.add(new GuiOptionButton(1, this.l / 2 - 155 + 160, this.m / 6 + 96, this.h));
      this.s.clear();
      this.s.addAll(this.q.c(this.r, this.l - 50));
   }

   @Override
   protected void a(GuiButton var1) {
      this.a.a(var1.k == 0, this.i);
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.a(this.q, this.f, this.l / 2, 70, 16777215);
      int var4 = 90;

      for (String var6 : this.s) {
         this.a(this.q, var6, this.l / 2, var4, 16777215);
         var4 += this.q.a;
      }

      super.a(var1, var2, var3);
   }

   public void b(int var1) {
      this.t = var1;

      for (GuiButton var3 : this.n) {
         var3.l = false;
      }
   }

   @Override
   public void e() {
      super.e();
      if (--this.t == 0) {
         for (GuiButton var2 : this.n) {
            var2.l = true;
         }
      }
   }
}
