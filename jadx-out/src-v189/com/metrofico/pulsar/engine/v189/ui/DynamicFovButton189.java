package com.metrofico.pulsar.engine.v189.ui;

import com.metrofico.pulsar.api.hud.modules.DynamicFov;

public final class DynamicFovButton189 extends avs {
   static final int ID = 20550;

   public DynamicFovButton189(int x, int y) {
      super(20550, x, y, 150, 20, label());
   }

   private static String label() {
      return "Dynamic FOV: " + bnq.a(DynamicFov.enabled() ? "options.on" : "options.off", new Object[0]);
   }

   public boolean c(ave client, int mouseX, int mouseY) {
      if (!super.c(client, mouseX, mouseY)) {
         return false;
      } else {
         DynamicFov.toggle();
         this.j = label();
         return true;
      }
   }
}
