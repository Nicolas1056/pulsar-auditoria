package com.metrofico.pulsar.bootstrap.mixin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.spongepowered.asm.service.IGlobalPropertyService;
import org.spongepowered.asm.service.IPropertyKey;

public final class PulsarGlobalPropertyService implements IGlobalPropertyService {
   private static final Map<String, Object> PROPERTIES = new ConcurrentHashMap<>();

   public IPropertyKey resolveKey(String name) {
      return new PulsarGlobalPropertyService.Key(name);
   }

   public <T> T getProperty(IPropertyKey key) {
      return (T)PROPERTIES.get(key.toString());
   }

   public void setProperty(IPropertyKey key, Object value) {
      if (value == null) {
         PROPERTIES.remove(key.toString());
      } else {
         PROPERTIES.put(key.toString(), value);
      }
   }

   public <T> T getProperty(IPropertyKey key, T defaultValue) {
      return (T)PROPERTIES.getOrDefault(key.toString(), defaultValue);
   }

   public String getPropertyString(IPropertyKey key, String defaultValue) {
      Object value = PROPERTIES.get(key.toString());
      return value != null ? value.toString() : defaultValue;
   }

   private record Key(String name) implements IPropertyKey {
      @Override
      public String toString() {
         return this.name;
      }
   }
}
