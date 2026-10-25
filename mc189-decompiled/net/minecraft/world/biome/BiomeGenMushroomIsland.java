package net.minecraft.world.biome;

import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.init.Blocks;

public class BiomeGenMushroomIsland extends BiomeGenBase {
   public BiomeGenMushroomIsland(int var1) {
      super(var1);
      this.as.A = -100;
      this.as.B = -100;
      this.as.C = -100;
      this.as.E = 1;
      this.as.K = 1;
      this.ak = Blocks.bw.Q();
      this.at.clear();
      this.au.clear();
      this.av.clear();
      this.au.add(new BiomeGenBase.SpawnListEntry(EntityMooshroom.class, 8, 4, 8));
   }
}
