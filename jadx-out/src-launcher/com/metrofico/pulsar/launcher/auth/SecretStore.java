package com.metrofico.pulsar.launcher.auth;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public interface SecretStore {
   String describe();

   boolean persistent();

   Optional<String> get(String var1);

   void put(String var1, String var2);

   void delete(String var1);

   public static final class InMemory implements SecretStore {
      private final Map<String, String> map = new ConcurrentHashMap<>();

      @Override
      public String describe() {
         return "memoria (solo esta sesión)";
      }

      @Override
      public boolean persistent() {
         return false;
      }

      @Override
      public Optional<String> get(String key) {
         return Optional.ofNullable(this.map.get(key));
      }

      @Override
      public void put(String key, String value) {
         this.map.put(key, value);
      }

      @Override
      public void delete(String key) {
         this.map.remove(key);
      }
   }
}
