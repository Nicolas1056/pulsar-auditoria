package net.minecraft.world.storage;

import net.minecraft.world.WorldSavedData;

public class SaveDataMemoryStorage extends MapStorage {
   public SaveDataMemoryStorage() {
      super(null);
   }

   @Override
   public WorldSavedData a(Class<? extends WorldSavedData> var1, String var2) {
      return this.a.get(var2);
   }

   @Override
   public void a(String var1, WorldSavedData var2) {
      this.a.put(var1, var2);
   }

   @Override
   public void a() {
   }

   @Override
   public int a(String var1) {
      return 0;
   }
}
