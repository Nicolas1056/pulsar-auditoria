package net.minecraft.client.renderer.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TextureCompass extends TextureAtlasSprite {
   public double j;
   public double k;
   public static String l;

   public TextureCompass(String var1) {
      super(var1);
      l = var1;
   }

   @Override
   public void j() {
      Minecraft var1 = Minecraft.A();
      if (var1.f != null && var1.h != null) {
         this.a(var1.f, var1.h.s, var1.h.u, var1.h.y, false, false);
      } else {
         this.a(null, 0.0, 0.0, 0.0, true, false);
      }
   }

   public void a(World var1, double var2, double var4, double var6, boolean var8, boolean var9) {
      if (!this.a.isEmpty()) {
         double var10 = 0.0;
         if (var1 != null && !var8) {
            BlockPos var12 = var1.M();
            double var13 = var12.n() - var2;
            double var15 = var12.p() - var4;
            var6 %= 360.0;
            var10 = -((var6 - 90.0) * Math.PI / 180.0 - Math.atan2(var15, var13));
            if (!var1.t.d()) {
               var10 = Math.random() * (float) Math.PI * 2.0;
            }
         }

         if (var9) {
            this.j = var10;
         } else {
            double var18 = var10 - this.j;

            while (var18 < -Math.PI) {
               var18 += Math.PI * 2;
            }

            while (var18 >= Math.PI) {
               var18 -= Math.PI * 2;
            }

            var18 = MathHelper.a(var18, -1.0, 1.0);
            this.k += var18 * 0.1;
            this.k *= 0.8;
            this.j = this.j + this.k;
         }

         int var20 = (int)((this.j / (Math.PI * 2) + 1.0) * this.a.size()) % this.a.size();

         while (var20 < 0) {
            var20 = (var20 + this.a.size()) % this.a.size();
         }

         if (var20 != this.h) {
            this.h = var20;
            TextureUtil.a(this.a.get(this.h), this.f, this.g, this.d, this.e, false, false);
         }
      }
   }
}
