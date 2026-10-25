package net.minecraft.util;

import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput {
   private final GameSettings e;

   public MovementInputFromOptions(GameSettings var1) {
      this.e = var1;
   }

   @Override
   public void a() {
      this.a = 0.0F;
      this.b = 0.0F;
      if (this.e.Y.d()) {
         this.b++;
      }

      if (this.e.aa.d()) {
         this.b--;
      }

      if (this.e.Z.d()) {
         this.a++;
      }

      if (this.e.ab.d()) {
         this.a--;
      }

      this.c = this.e.ac.d();
      this.d = this.e.ad.d();
      if (this.d) {
         this.a = (float)(this.a * 0.3);
         this.b = (float)(this.b * 0.3);
      }
   }
}
