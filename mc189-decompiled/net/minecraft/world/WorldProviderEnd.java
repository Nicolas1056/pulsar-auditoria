package net.minecraft.world;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;

public class WorldProviderEnd extends WorldProvider {
   @Override
   public void b() {
      this.c = new WorldChunkManagerHell(BiomeGenBase.y, 0.0F);
      this.g = 1;
      this.e = true;
   }

   @Override
   public IChunkProvider c() {
      return new ChunkProviderEnd(this.b, this.b.J());
   }

   @Override
   public float a(long var1, float var3) {
      return 0.0F;
   }

   @Override
   public float[] a(float var1, float var2) {
      return null;
   }

   @Override
   public Vec3 b(float var1, float var2) {
      int var3 = 10518688;
      float var4 = MathHelper.b(var1 * (float) Math.PI * 2.0F) * 2.0F + 0.5F;
      var4 = MathHelper.a(var4, 0.0F, 1.0F);
      float var5 = (var3 >> 16 & 0xFF) / 255.0F;
      float var6 = (var3 >> 8 & 0xFF) / 255.0F;
      float var7 = (var3 & 0xFF) / 255.0F;
      var5 *= var4 * 0.0F + 0.15F;
      var6 *= var4 * 0.0F + 0.15F;
      var7 *= var4 * 0.0F + 0.15F;
      return new Vec3(var5, var6, var7);
   }

   @Override
   public boolean g() {
      return false;
   }

   @Override
   public boolean e() {
      return false;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public float f() {
      return 8.0F;
   }

   @Override
   public boolean a(int var1, int var2) {
      return this.b.c(new BlockPos(var1, 0, var2)).t().c();
   }

   @Override
   public BlockPos h() {
      return new BlockPos(100, 50, 0);
   }

   @Override
   public int i() {
      return 50;
   }

   @Override
   public boolean b(int var1, int var2) {
      return true;
   }

   @Override
   public String k() {
      return "The End";
   }

   @Override
   public String l() {
      return "_end";
   }
}
