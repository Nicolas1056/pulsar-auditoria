package com.metrofico.pulsar.engine.v26x.ui;

import com.metrofico.pulsar.api.pack.ImageRef;
import com.metrofico.pulsar.api.pack.PackClient;
import com.metrofico.pulsar.api.pack.RemoteImages;
import com.metrofico.pulsar.api.pack.ImageRef.Kind;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.metrofico.pulsar.engine.v26x.pack.PackRuntime;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public final class Images {
   private Images() {
   }

   public static boolean draw(GuiGraphicsExtractor g, String ref, int x, int y, int w, int h) {
      Optional<ImageRef> parsed = ImageRef.parse(ref);
      if (parsed.isEmpty()) {
         return false;
      } else {
         ImageRef r = parsed.get();
         int[] region = r.region();
         switch (r.kind()) {
            case PLAYER:
               Identifier skin = skinOf(UUID.fromString(r.value()));
               if (skin == null) {
                  return false;
               }

               g.blit(RenderPipelines.GUI_TEXTURED, skin, x, y, region[0], region[1], w, h, region[2], region[3], 64, 64);
               if (r.isFace()) {
                  g.blit(RenderPipelines.GUI_TEXTURED, skin, x, y, 40.0F, 8.0F, w, h, 8, 8, 64, 64);
               }

               return true;
            case RESOURCE:
            case URL:
               PackTextures.Texture t;
               if (r.kind() == Kind.RESOURCE) {
                  PackClient runtime = PackRuntime.get();
                  String sha = runtime.resourceSha(r.value());
                  Path file = sha == null ? null : runtime.resourceFile(sha);
                  t = file == null ? null : PackTextures.get(sha, file);
               } else {
                  Path file = remote(r.value());
                  t = file == null ? null : PackTextures.get("url:" + sha256(r.value()), file);
               }

               if (t == null) {
                  return false;
               }

               int u = region == null ? 0 : Math.min(region[0], t.width());
               int v = region == null ? 0 : Math.min(region[1], t.height());
               int uw = region == null ? t.width() : Math.min(region[2], t.width() - u);
               int vh = region == null ? t.height() : Math.min(region[3], t.height() - v);
               g.blit(RenderPipelines.GUI_TEXTURED, t.id(), x, y, u, v, w, h, uw, vh, t.width(), t.height());
               return true;
            default:
               return false;
         }
      }
   }

   private static Identifier skinOf(UUID id) {
      if (Minecraft.getInstance().getConnection() == null) {
         return null;
      } else {
         PlayerInfo info = Minecraft.getInstance().getConnection().getPlayerInfo(id);
         return info != null && info.getSkin() != null && info.getSkin().body() != null ? info.getSkin().body().texturePath() : null;
      }
   }

   private static Path remote(String url) {
      return RemoteImages.remote(PackRuntime.get().dataDir(), url, BootstrapLog::info);
   }

   private static String sha256(String s) {
      return RemoteImages.sha256(s);
   }
}
