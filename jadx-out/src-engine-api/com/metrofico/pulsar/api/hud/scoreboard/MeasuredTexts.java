package com.metrofico.pulsar.api.hud.scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class MeasuredTexts<K, V extends SidebarText> {
   private static final int MAX_ENTRIES = 512;
   private final Function<K, V> measure;
   private final Map<K, V> measured = new HashMap<>();

   public MeasuredTexts(Function<K, V> measure) {
      this.measure = measure;
   }

   public V get(K raw) {
      V text = this.measured.get(raw);
      if (text == null) {
         if (this.measured.size() >= 512) {
            this.measured.clear();
         }

         text = this.measure.apply(raw);
         this.measured.put(raw, text);
      }

      return text;
   }
}
