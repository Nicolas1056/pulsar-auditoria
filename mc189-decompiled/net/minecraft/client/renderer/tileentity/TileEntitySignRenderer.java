package net.minecraft.client.renderer.tileentity;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntitySignRenderer extends TileEntitySpecialRenderer<TileEntitySign> {
   private static final ResourceLocation c = new ResourceLocation("textures/entity/sign.png");
   private final ModelSign d = new ModelSign();

   public void a(TileEntitySign var1, double var2, double var4, double var6, float var8, int var9) {
      Block var10 = var1.w();
      GlStateManager.E();
      float var11 = 0.6666667F;
      if (var10 == Blocks.an) {
         GlStateManager.b((float)var2 + 0.5F, (float)var4 + 0.75F * var11, (float)var6 + 0.5F);
         float var12 = var1.u() * 360 / 16.0F;
         GlStateManager.b(-var12, 0.0F, 1.0F, 0.0F);
         this.d.b.j = true;
      } else {
         int var19 = var1.u();
         float var13 = 0.0F;
         if (var19 == 2) {
            var13 = 180.0F;
         }

         if (var19 == 4) {
            var13 = 90.0F;
         }

         if (var19 == 5) {
            var13 = -90.0F;
         }

         GlStateManager.b((float)var2 + 0.5F, (float)var4 + 0.75F * var11, (float)var6 + 0.5F);
         GlStateManager.b(-var13, 0.0F, 1.0F, 0.0F);
         GlStateManager.b(0.0F, -0.3125F, -0.4375F);
         this.d.b.j = false;
      }

      if (var9 >= 0) {
         this.a(a[var9]);
         GlStateManager.n(5890);
         GlStateManager.E();
         GlStateManager.a(4.0F, 2.0F, 1.0F);
         GlStateManager.b(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.n(5888);
      } else {
         this.a(c);
      }

      GlStateManager.B();
      GlStateManager.E();
      GlStateManager.a(var11, -var11, -var11);
      this.d.a();
      GlStateManager.F();
      FontRenderer var20 = this.c();
      float var21 = 0.015625F * var11;
      GlStateManager.b(0.0F, 0.5F * var11, 0.07F * var11);
      GlStateManager.a(var21, -var21, var21);
      GL11.glNormal3f(0.0F, 0.0F, -1.0F * var21);
      GlStateManager.a(false);
      byte var14 = 0;
      if (var9 < 0) {
         for (int var15 = 0; var15 < var1.a.length; var15++) {
            if (var1.a[var15] != null) {
               IChatComponent var16 = var1.a[var15];
               List var17 = GuiUtilRenderComponents.a(var16, 90, var20, false, true);
               String var18 = var17 != null && var17.size() > 0 ? ((IChatComponent)var17.get(0)).d() : "";
               if (var15 == var1.f) {
                  var18 = "> " + var18 + " <";
                  var20.a(var18, -var20.a(var18) / 2, var15 * 10 - var1.a.length * 5, var14);
               } else {
                  var20.a(var18, -var20.a(var18) / 2, var15 * 10 - var1.a.length * 5, var14);
               }
            }
         }
      }

      GlStateManager.a(true);
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.F();
      if (var9 >= 0) {
         GlStateManager.n(5890);
         GlStateManager.F();
         GlStateManager.n(5888);
      }
   }
}
