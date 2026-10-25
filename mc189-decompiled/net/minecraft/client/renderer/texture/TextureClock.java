package net.minecraft.client.renderer.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class TextureClock extends TextureAtlasSprite {
   private double j;
   private double k;

   public TextureClock(String var1) {
      super(var1);
   }

   @Override
   public void j() {
      if (!this.a.isEmpty()) {
         Minecraft var1 = Minecraft.A();
         double var2 = 0.0;
         if (var1.f != null && var1.h != null) {
            var2 = var1.f.c(1.0F);
            if (!var1.f.t.d()) {
               var2 = Math.random();
            }
         }

         double var4 = var2 - this.j;

         while (var4 < -0.5) {
            var4++;
         }

         while (var4 >= 0.5) {
            var4--;
         }

         var4 = MathHelper.a(var4, -1.0, 1.0);
         this.k += var4 * 0.1;
         this.k *= 0.8;
         this.j = this.j + this.k;
         int var6 = (int)((this.j + 1.0) * this.a.size()) % this.a.size();

         while (var6 < 0) {
            var6 = (var6 + this.a.size()) % this.a.size();
         }

         if (var6 != this.h) {
            this.h = var6;
            TextureUtil.a(this.a.get(this.h), this.f, this.g, this.d, this.e, false, false);
         }
      }
   }
}
