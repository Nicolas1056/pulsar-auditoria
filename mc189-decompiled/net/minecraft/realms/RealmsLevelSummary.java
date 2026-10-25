package net.minecraft.realms;

import net.minecraft.world.storage.SaveFormatComparator;

public class RealmsLevelSummary implements Comparable<RealmsLevelSummary> {
   private SaveFormatComparator levelSummary;

   public RealmsLevelSummary(SaveFormatComparator var1) {
      this.levelSummary = var1;
   }

   public int getGameMode() {
      return this.levelSummary.f().a();
   }

   public String getLevelId() {
      return this.levelSummary.a();
   }

   public boolean hasCheats() {
      return this.levelSummary.h();
   }

   public boolean isHardcore() {
      return this.levelSummary.g();
   }

   public boolean isRequiresConversion() {
      return this.levelSummary.d();
   }

   public String getLevelName() {
      return this.levelSummary.b();
   }

   public long getLastPlayed() {
      return this.levelSummary.e();
   }

   public int compareTo(SaveFormatComparator var1) {
      return this.levelSummary.a(var1);
   }

   public long getSizeOnDisk() {
      return this.levelSummary.c();
   }

   public int compareTo(RealmsLevelSummary var1) {
      if (this.levelSummary.e() < var1.getLastPlayed()) {
         return 1;
      } else {
         return this.levelSummary.e() > var1.getLastPlayed() ? -1 : this.levelSummary.a().compareTo(var1.getLevelId());
      }
   }
}
