package net.minecraft.client.renderer.tileentity;

import java.util.Calendar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;

public class TileEntityChestRenderer extends TileEntitySpecialRenderer<TileEntityChest> {
   private static final ResourceLocation c = new ResourceLocation("textures/entity/chest/trapped_double.png");
   private static final ResourceLocation d = new ResourceLocation("textures/entity/chest/christmas_double.png");
   private static final ResourceLocation e = new ResourceLocation("textures/entity/chest/normal_double.png");
   private static final ResourceLocation f = new ResourceLocation("textures/entity/chest/trapped.png");
   private static final ResourceLocation g = new ResourceLocation("textures/entity/chest/christmas.png");
   private static final ResourceLocation h = new ResourceLocation("textures/entity/chest/normal.png");
   private ModelChest i = new ModelChest();
   private ModelChest j = new ModelLargeChest();
   private boolean k;

   public TileEntityChestRenderer() {
      Calendar var1 = Calendar.getInstance();
      if (var1.get(2) + 1 == 12 && var1.get(5) >= 24 && var1.get(5) <= 26) {
         this.k = true;
      }
   }

   public void a(TileEntityChest var1, double var2, double var4, double var6, float var8, int var9) {
      GlStateManager.j();
      GlStateManager.c(515);
      GlStateManager.a(true);
      int var10;
      if (!var1.t()) {
         var10 = 0;
      } else {
         Block var11 = var1.w();
         var10 = var1.u();
         if (var11 instanceof BlockChest && var10 == 0) {
            ((BlockChest)var11).e(var1.z(), var1.v(), var1.z().p(var1.v()));
            var10 = var1.u();
         }

         var1.m();
      }

      if (var1.f == null && var1.h == null) {
         ModelChest var15;
         if (var1.g == null && var1.i == null) {
            var15 = this.i;
            if (var9 >= 0) {
               this.a(a[var9]);
               GlStateManager.n(5890);
               GlStateManager.E();
               GlStateManager.a(4.0F, 4.0F, 1.0F);
               GlStateManager.b(0.0625F, 0.0625F, 0.0625F);
               GlStateManager.n(5888);
            } else if (this.k) {
               this.a(g);
            } else if (var1.n() == 1) {
               this.a(f);
            } else {
               this.a(h);
            }
         } else {
            var15 = this.j;
            if (var9 >= 0) {
               this.a(a[var9]);
               GlStateManager.n(5890);
               GlStateManager.E();
               GlStateManager.a(8.0F, 4.0F, 1.0F);
               GlStateManager.b(0.0625F, 0.0625F, 0.0625F);
               GlStateManager.n(5888);
            } else if (this.k) {
               this.a(d);
            } else if (var1.n() == 1) {
               this.a(c);
            } else {
               this.a(e);
            }
         }

         GlStateManager.E();
         GlStateManager.B();
         if (var9 < 0) {
            GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         }

         GlStateManager.b((float)var2, (float)var4 + 1.0F, (float)var6 + 1.0F);
         GlStateManager.a(1.0F, -1.0F, -1.0F);
         GlStateManager.b(0.5F, 0.5F, 0.5F);
         short var12 = 0;
         if (var10 == 2) {
            var12 = 180;
         }

         if (var10 == 3) {
            var12 = 0;
         }

         if (var10 == 4) {
            var12 = 90;
         }

         if (var10 == 5) {
            var12 = -90;
         }

         if (var10 == 2 && var1.g != null) {
            GlStateManager.b(1.0F, 0.0F, 0.0F);
         }

         if (var10 == 5 && var1.i != null) {
            GlStateManager.b(0.0F, 0.0F, -1.0F);
         }

         GlStateManager.b((float)var12, 0.0F, 1.0F, 0.0F);
         GlStateManager.b(-0.5F, -0.5F, -0.5F);
         float var13 = var1.k + (var1.j - var1.k) * var8;
         if (var1.f != null) {
            float var14 = var1.f.k + (var1.f.j - var1.f.k) * var8;
            if (var14 > var13) {
               var13 = var14;
            }
         }

         if (var1.h != null) {
            float var18 = var1.h.k + (var1.h.j - var1.h.k) * var8;
            if (var18 > var13) {
               var13 = var18;
            }
         }

         var13 = 1.0F - var13;
         var13 = 1.0F - var13 * var13 * var13;
         var15.a.f = -(var13 * (float) Math.PI / 2.0F);
         var15.a();
         GlStateManager.C();
         GlStateManager.F();
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         if (var9 >= 0) {
            GlStateManager.n(5890);
            GlStateManager.F();
            GlStateManager.n(5888);
         }
      }
   }
}
