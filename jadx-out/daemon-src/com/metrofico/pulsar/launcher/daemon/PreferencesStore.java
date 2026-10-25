package com.metrofico.pulsar.launcher.daemon;

import java.nio.file.Path;
import java.util.function.UnaryOperator;

public final class PreferencesStore {
   private final JsonStore<Preferences> store;
   private Preferences current;

   public PreferencesStore(Path dataDir) {
      this.store = new JsonStore<>(dataDir.resolve("preferences.json"), Preferences.class, Preferences::defaults);
      this.current = this.store.load();
   }

   public synchronized Preferences current() {
      return this.current;
   }

   public synchronized Preferences update(UnaryOperator<Preferences> change) {
      this.current = change.apply(this.current);
      this.store.save(this.current);
      return this.current;
   }
}
