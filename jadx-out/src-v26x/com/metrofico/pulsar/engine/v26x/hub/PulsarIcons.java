package com.metrofico.pulsar.engine.v26x.hub;

import com.metrofico.pulsar.api.pack.Json;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.mojang.blaze3d.platform.NativeImage;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;

public final class PulsarIcons {
   private static Identifier texture;
   private static int cell;
   private static Map<String, Integer> index;
   private static int texW;
   private static int texH;
   private static Identifier logo;
   private static int logoW;
   private static int logoH;

   private PulsarIcons() {
   }

   private static boolean load() {
      if (texture != null) {
         return true;
      } else {
         try {
            byte[] pngBytes = PulsarType.resource("/pulsar/icons.png");
            byte[] jsonBytes = PulsarType.resource("/pulsar/icons.json");
            if (pngBytes != null && jsonBytes != null) {
               NativeImage image = NativeImage.read(new ByteArrayInputStream(pngBytes));
               texW = image.getWidth();
               texH = image.getHeight();
               Identifier id = Identifier.fromNamespaceAndPath("pulsar", "type/icons");
               DynamicTexture dyn = new DynamicTexture(() -> "pulsar icons", image);
               PulsarType.linear(dyn);
               Minecraft.getInstance().getTextureManager().register(id, dyn);
               Map<String, Object> root = Json.object(Json.parse(new String(jsonBytes, StandardCharsets.UTF_8)), "icons");
               cell = (int)Json.integer(root, "cell", "icons");
               index = new HashMap<>();
               Json.object(root.get("icons"), "icons").forEach((k, v) -> index.put(k, ((Number)v).intValue()));
               texture = id;
               return true;
            } else {
               return false;
            }
         } catch (Exception var6) {
            BootstrapLog.info("pulsar icons failed: " + var6);
            return false;
         }
      }
   }

   public static void drawLogo(GuiGraphicsExtractor g, int x, int y, int size) {
      if (logo == null) {
         try {
            byte[] png = PulsarType.resource("/pulsar/logo.png");
            if (png == null) {
               return;
            }

            NativeImage image = NativeImage.read(new ByteArrayInputStream(png));
            logoW = image.getWidth();
            logoH = image.getHeight();
            Identifier id = Identifier.fromNamespaceAndPath("pulsar", "type/logo");
            DynamicTexture dyn = new DynamicTexture(() -> "pulsar logo", image);
            PulsarType.linear(dyn);
            Minecraft.getInstance().getTextureManager().register(id, dyn);
            logo = id;
         } catch (Exception var8) {
            BootstrapLog.info("pulsar logo failed: " + var8);
            return;
         }
      }

      g.blit(RenderPipelines.GUI_TEXTURED, logo, x, y, 0.0F, 0.0F, size, size, logoW, logoH, logoW, logoH);
   }

   public static void draw(GuiGraphicsExtractor g, String name, int x, int y, int size, int argb) {
      if (load() && index.containsKey(name)) {
         int scale = Math.max(1, Minecraft.getInstance().getWindow().getGuiScale());
         g.pose().pushMatrix();
         g.pose().scale(1.0F / scale, 1.0F / scale);
         g.blit(
            RenderPipelines.GUI_TEXTURED, texture, x * scale, y * scale, index.get(name) * cell, 0.0F, size * scale, size * scale, cell, cell, texW, texH, argb
         );
         g.pose().popMatrix();
      }
   }
}
