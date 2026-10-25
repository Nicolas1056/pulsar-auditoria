package net.minecraft.realms;

import com.mojang.util.UUIDTypeAdapter;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreenRealmsProxy;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RealmsScreen {
   public static final int SKIN_HEAD_U = 8;
   public static final int SKIN_HEAD_V = 8;
   public static final int SKIN_HEAD_WIDTH = 8;
   public static final int SKIN_HEAD_HEIGHT = 8;
   public static final int SKIN_HAT_U = 40;
   public static final int SKIN_HAT_V = 8;
   public static final int SKIN_HAT_WIDTH = 8;
   public static final int SKIN_HAT_HEIGHT = 8;
   public static final int SKIN_TEX_WIDTH = 64;
   public static final int SKIN_TEX_HEIGHT = 64;
   protected Minecraft minecraft;
   public int width;
   public int height;
   private GuiScreenRealmsProxy proxy = new GuiScreenRealmsProxy(this);

   public GuiScreenRealmsProxy getProxy() {
      return this.proxy;
   }

   public void init() {
   }

   public void init(Minecraft var1, int var2, int var3) {
   }

   public void drawCenteredString(String var1, int var2, int var3, int var4) {
      this.proxy.a(var1, var2, var3, var4);
   }

   public void drawString(String var1, int var2, int var3, int var4) {
      this.drawString(var1, var2, var3, var4, true);
   }

   public void drawString(String var1, int var2, int var3, int var4, boolean var5) {
      this.proxy.a(var1, var2, var3, var4, false);
   }

   public void blit(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.proxy.b(var1, var2, var3, var4, var5, var6);
   }

   public static void blit(int var0, int var1, float var2, float var3, int var4, int var5, int var6, int var7, float var8, float var9) {
      Gui.a(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   public static void blit(int var0, int var1, float var2, float var3, int var4, int var5, float var6, float var7) {
      Gui.a(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   public void fillGradient(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.proxy.a(var1, var2, var3, var4, var5, var6);
   }

   public void renderBackground() {
      this.proxy.c();
   }

   public boolean isPauseScreen() {
      return this.proxy.d();
   }

   public void renderBackground(int var1) {
      this.proxy.b_(var1);
   }

   public void render(int var1, int var2, float var3) {
      for (int var4 = 0; var4 < this.proxy.j().size(); var4++) {
         this.proxy.j().get(var4).render(var1, var2);
      }
   }

   public void renderTooltip(ItemStack var1, int var2, int var3) {
      this.proxy.a(var1, var2, var3);
   }

   public void renderTooltip(String var1, int var2, int var3) {
      this.proxy.a(var1, var2, var3);
   }

   public void renderTooltip(List<String> var1, int var2, int var3) {
      this.proxy.a(var1, var2, var3);
   }

   public static void bindFace(String var0, String var1) {
      ResourceLocation var2 = AbstractClientPlayer.c(var1);
      if (var2 == null) {
         var2 = DefaultPlayerSkin.a(UUIDTypeAdapter.fromString(var0));
      }

      AbstractClientPlayer.a(var2, var1);
      Minecraft.A().P().a(var2);
   }

   public static void bind(String var0) {
      ResourceLocation var1 = new ResourceLocation(var0);
      Minecraft.A().P().a(var1);
   }

   public void tick() {
   }

   public int width() {
      return this.proxy.l;
   }

   public int height() {
      return this.proxy.m;
   }

   public int fontLineHeight() {
      return this.proxy.h();
   }

   public int fontWidth(String var1) {
      return this.proxy.c(var1);
   }

   public void fontDrawShadow(String var1, int var2, int var3, int var4) {
      this.proxy.b(var1, var2, var3, var4);
   }

   public List<String> fontSplit(String var1, int var2) {
      return this.proxy.a(var1, var2);
   }

   public void buttonClicked(RealmsButton var1) {
   }

   public static RealmsButton newButton(int var0, int var1, int var2, String var3) {
      return new RealmsButton(var0, var1, var2, var3);
   }

   public static RealmsButton newButton(int var0, int var1, int var2, int var3, int var4, String var5) {
      return new RealmsButton(var0, var1, var2, var3, var4, var5);
   }

   public void buttonsClear() {
      this.proxy.i();
   }

   public void buttonsAdd(RealmsButton var1) {
      this.proxy.a(var1);
   }

   public List<RealmsButton> buttons() {
      return this.proxy.j();
   }

   public void buttonsRemove(RealmsButton var1) {
      this.proxy.b(var1);
   }

   public RealmsEditBox newEditBox(int var1, int var2, int var3, int var4, int var5) {
      return new RealmsEditBox(var1, var2, var3, var4, var5);
   }

   public void mouseClicked(int var1, int var2, int var3) {
   }

   public void mouseEvent() {
   }

   public void keyboardEvent() {
   }

   public void mouseReleased(int var1, int var2, int var3) {
   }

   public void mouseDragged(int var1, int var2, int var3, long var4) {
   }

   public void keyPressed(char var1, int var2) {
   }

   public void confirmResult(boolean var1, int var2) {
   }

   public static String getLocalizedString(String var0) {
      return I18n.a(var0);
   }

   public static String getLocalizedString(String var0, Object... var1) {
      return I18n.a(var0, var1);
   }

   public RealmsAnvilLevelStorageSource getLevelStorageSource() {
      return new RealmsAnvilLevelStorageSource(Minecraft.A().f());
   }

   public void removed() {
   }
}
