package net.minecraft.client.gui;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class GuiSleepMP extends GuiChat {
   @Override
   public void b() {
      super.b();
      this.n.add(new GuiButton(1, this.l / 2 - 100, this.m - 40, I18n.a("multiplayer.stopSleeping")));
   }

   @Override
   protected void a(char var1, int var2) {
      if (var2 == 1) {
         this.f();
      } else if (var2 != 28 && var2 != 156) {
         super.a(var1, var2);
      } else {
         String var3 = this.a.b().trim();
         if (!var3.isEmpty()) {
            this.j.h.e(var3);
         }

         this.a.a("");
         this.j.q.d().d();
      }
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.k == 1) {
         this.f();
      } else {
         super.a(var1);
      }
   }

   private void f() {
      NetHandlerPlayClient var1 = this.j.h.a;
      var1.a(new C0BPacketEntityAction(this.j.h, C0BPacketEntityAction.Action.c));
   }
}
