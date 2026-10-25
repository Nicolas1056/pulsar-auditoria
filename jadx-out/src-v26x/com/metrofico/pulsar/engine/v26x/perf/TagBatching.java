package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.engine.v26x.mixin.RenderTypeAccessor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import net.minecraft.client.renderer.feature.RenderTypeFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;

public final class TagBatching {
   private static final Map<RenderType, Boolean> TEXT = new ConcurrentHashMap<>();

   private TagBatching() {
   }

   public static boolean enabled() {
      ClientOptions o = ClientOptions.get();
      return o.enabled("perf") && o.bool("perf.tagBatching", true);
   }

   public static boolean canReorder(RenderTypeFeatureRenderer<?> renderer, boolean vanilla) {
      return vanilla || renderer instanceof NameTagFeatureRenderer && enabled();
   }

   public static boolean sortOnUpload(RenderType type) {
      if (!enabled()) {
         return type.sortOnUpload();
      } else {
         Boolean text = TEXT.get(type);
         if (text == null) {
            text = ((RenderTypeAccessor)type).pulsar$name().startsWith("text");
            TEXT.put(type, text);
         }

         return !text && type.sortOnUpload();
      }
   }
}
