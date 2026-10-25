package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;

public class GuiDisconnected extends GuiScreen {
   private String a;
   private IChatComponent f;
   private List<String> g;
   private final GuiScreen h;
   private int i;

   public GuiDisconnected(GuiScreen var1, String var2, IChatComponent var3) {
      this.h = var1;
      this.a = I18n.a(var2);
      this.f = var3;
   }

   @Override
   protected void a(char var1, int var2) {
   }

   @Override
   public void b() {
      this.n.clear();
      this.g = this.q.c(this.f.d(), this.l - 50);
      this.i = this.g.size() * this.q.a;
      this.n.add(new GuiButton(0, this.l / 2 - 100, this.m / 2 + this.i / 2 + this.q.a, I18n.a("gui.toMenu")));
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.k == 0) {
         this.j.a(this.h);
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.a(this.q, this.a, this.l / 2, this.m / 2 - this.i / 2 - this.q.a * 2, 11184810);
      int var4 = this.m / 2 - this.i / 2;
      if (this.g != null) {
         for (String var6 : this.g) {
            this.a(this.q, var6, this.l / 2, var4, 16777215);
            var4 += this.q.a;
         }
      }

      super.a(var1, var2, var3);
   }
}
