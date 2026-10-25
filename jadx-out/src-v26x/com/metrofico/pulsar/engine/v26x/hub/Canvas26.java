package com.metrofico.pulsar.engine.v26x.hub;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.engine.v26x.ui.Images;
import com.metrofico.pulsar.engine.v26x.ui.PackTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;

public final class Canvas26 implements Canvas {
   private final GuiGraphicsExtractor g;
   private final Font font;

   public Canvas26(GuiGraphicsExtractor g) {
      this.font = Minecraft.getInstance().font;
      this.g = g;
   }

   public static Canvas26 measure() {
      return new Canvas26(null);
   }

   public GuiGraphicsExtractor gfx() {
      return this.g;
   }

   public int width() {
      return this.g != null ? this.g.guiWidth() : Minecraft.getInstance().getWindow().getGuiScaledWidth();
   }

   public int height() {
      return this.g != null ? this.g.guiHeight() : Minecraft.getInstance().getWindow().getGuiScaledHeight();
   }

   public int vtext(String text, int x, int y, int argb, boolean shadow) {
      this.g.text(this.font, text, x, y, argb, shadow);
      return this.font.width(text);
   }

   public int guiScale() {
      return Math.max(1, Minecraft.getInstance().getWindow().getGuiScale());
   }

   public int vwidth(String text) {
      return this.font.width(text);
   }

   public int vlineHeight() {
      return 9;
   }

   public boolean image(String ref, int x, int y, int w, int h) {
      return Images.draw(this.g, ref, x, y, w, h);
   }

   public boolean imageInline(String key, String base64, int x, int y, int w, int h) {
      PackTextures.Texture t = PackTextures.inline(key, base64);
      if (t == null) {
         return false;
      } else {
         this.g.blit(RenderPipelines.GUI_TEXTURED, t.id(), x, y, 0.0F, 0.0F, w, h, t.width(), t.height(), t.width(), t.height());
         return true;
      }
   }

   public void scale(float sx, float sy) {
      this.g.pose().scale(sx, sy);
   }

   public void fill(int x0, int y0, int x1, int y1, int argb) {
      this.g.fill(x0, y0, x1, y1, argb);
   }

   private static PulsarType type(com.metrofico.pulsar.api.gfx.Font font) {
      return font == com.metrofico.pulsar.api.gfx.Font.BOLD
         ? PulsarType.BOLD
         : (font == com.metrofico.pulsar.api.gfx.Font.MEDIUM ? PulsarType.MEDIUM : PulsarType.REGULAR);
   }

   public int text(com.metrofico.pulsar.api.gfx.Font font, String text, int x, int y, float size, int argb) {
      return type(font).draw(this.g, text, x, y, size, argb);
   }

   public int textWidth(com.metrofico.pulsar.api.gfx.Font font, String text, float size) {
      return type(font).width(text, size);
   }

   public int lineHeight(com.metrofico.pulsar.api.gfx.Font font, float size) {
      return type(font).lineHeight(size);
   }

   public void icon(String name, int x, int y, int size, int argb) {
      PulsarIcons.draw(this.g, name, x, y, size, argb);
   }

   public void logo(int x, int y, int size) {
      PulsarIcons.drawLogo(this.g, x, y, size);
   }

   public void clip(int x0, int y0, int x1, int y1) {
      this.g.enableScissor(x0, y0, x1, y1);
   }

   public void unclip() {
      this.g.disableScissor();
   }

   public void push() {
      this.g.pose().pushMatrix();
   }

   public void translate(float dx, float dy) {
      this.g.pose().translate(dx, dy);
   }

   public void pop() {
      this.g.pose().popMatrix();
   }

   public void pointer(boolean pointing) {
      PulsarCursor.set(pointing);
   }
}
