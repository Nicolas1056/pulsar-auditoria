package net.minecraft.client.stream;

import net.minecraft.stats.Achievement;

public class MetadataAchievement extends Metadata {
   public MetadataAchievement(Achievement var1) {
      super("achievement");
      this.a("achievement_id", var1.e);
      this.a("achievement_name", var1.e().c());
      this.a("achievement_description", var1.f());
      this.a("Achievement '" + var1.e().c() + "' obtained!");
   }
}
