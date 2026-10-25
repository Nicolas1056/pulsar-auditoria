package net.minecraft.world;

import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderHell;

public class WorldProviderHell extends WorldProvider {
   @Override
   public void b() {
      this.c = new WorldChunkManagerHell(BiomeGenBase.x, 0.0F);
      this.d = true;
      this.e = true;
      this.g = -1;
   }

   @Override
   public Vec3 b(float var1, float var2) {
      return new Vec3(0.2F, 0.03F, 0.03F);
   }

   @Override
   protected void a() {
      float var1 = 0.1F;

      for (int var2 = 0; var2 <= 15; var2++) {
         float var3 = 1.0F - var2 / 15.0F;
         this.f[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
      }
   }

   @Override
   public IChunkProvider c() {
      return new ChunkProviderHell(this.b, this.b.P().s(), this.b.J());
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean a(int var1, int var2) {
      return false;
   }

   @Override
   public float a(long var1, float var3) {
      return 0.5F;
   }

   @Override
   public boolean e() {
      return false;
   }

   @Override
   public boolean b(int var1, int var2) {
      return true;
   }

   @Override
   public String k() {
      return "Nether";
   }

   @Override
   public String l() {
      return "_nether";
   }

   @Override
   public WorldBorder r() {
      return new WorldBorder() {
         @Override
         public double f() {
            return super.f() / 8.0;
         }

         @Override
         public double g() {
            return super.g() / 8.0;
         }
      };
   }
}
