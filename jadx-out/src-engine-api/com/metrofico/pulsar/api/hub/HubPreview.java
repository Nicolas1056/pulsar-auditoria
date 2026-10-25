package com.metrofico.pulsar.api.hub;

import com.metrofico.pulsar.api.gfx.Canvas;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface HubPreview {
   Map<String, HubPreview> REGISTRY = new ConcurrentHashMap<>();

   void draw(Canvas var1, int var2, int var3, int var4, int var5);

   static void register(String optionId, HubPreview preview) {
      REGISTRY.put(optionId, preview);
   }

   static HubPreview of(String optionId) {
      return REGISTRY.get(optionId);
   }
}
