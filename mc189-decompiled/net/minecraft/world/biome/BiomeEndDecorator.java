package net.minecraft.world.biome;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeEndDecorator extends BiomeDecorator {
   protected WorldGenerator M = new WorldGenSpikes(Blocks.bH);

   @Override
   protected void a(BiomeGenBase var1) {
      this.a();
      if (this.b.nextInt(5) == 0) {
         int var2 = this.b.nextInt(16) + 8;
         int var3 = this.b.nextInt(16) + 8;
         this.M.b(this.a, this.b, this.a.r(this.c.a(var2, 0, var3)));
      }

      if (this.c.n() == 0 && this.c.p() == 0) {
         EntityDragon var4 = new EntityDragon(this.a);
         var4.b(0.0, 128.0, 0.0, this.b.nextFloat() * 360.0F, 0.0F);
         this.a.d(var4);
      }
   }
}
