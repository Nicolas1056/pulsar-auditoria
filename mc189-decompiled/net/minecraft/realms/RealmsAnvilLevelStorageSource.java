package net.minecraft.realms;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.SaveFormatComparator;

public class RealmsAnvilLevelStorageSource {
   private ISaveFormat levelStorageSource;

   public RealmsAnvilLevelStorageSource(ISaveFormat var1) {
      this.levelStorageSource = var1;
   }

   public String getName() {
      return this.levelStorageSource.a();
   }

   public boolean levelExists(String var1) {
      return this.levelStorageSource.f(var1);
   }

   public boolean convertLevel(String var1, IProgressUpdate var2) {
      return this.levelStorageSource.a(var1, var2);
   }

   public boolean requiresConversion(String var1) {
      return this.levelStorageSource.b(var1);
   }

   public boolean isNewLevelIdAcceptable(String var1) {
      return this.levelStorageSource.d(var1);
   }

   public boolean deleteLevel(String var1) {
      return this.levelStorageSource.e(var1);
   }

   public boolean isConvertible(String var1) {
      return this.levelStorageSource.a(var1);
   }

   public void renameLevel(String var1, String var2) {
      this.levelStorageSource.a(var1, var2);
   }

   public void clearAll() {
      this.levelStorageSource.d();
   }

   public List<RealmsLevelSummary> getLevelList() throws AnvilConverterException {
      ArrayList var1 = Lists.newArrayList();

      for (SaveFormatComparator var3 : this.levelStorageSource.b()) {
         var1.add(new RealmsLevelSummary(var3));
      }

      return var1;
   }
}
