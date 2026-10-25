package net.minecraft.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderDebug;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.FlatGeneratorInfo;

public abstract class WorldProvider {
   public static final float[] a = new float[]{1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
   protected World b;
   private WorldType h;
   private String i;
   protected WorldChunkManager c;
   protected boolean d;
   protected boolean e;
   protected final float[] f = new float[16];
   protected int g;
   private final float[] j = new float[4];

   public final void a(World var1) {
      this.b = var1;
      this.h = var1.P().u();
      this.i = var1.P().B();
      this.b();
      this.a();
   }

   protected void a() {
      float var1 = 0.0F;

      for (int var2 = 0; var2 <= 15; var2++) {
         float var3 = 1.0F - var2 / 15.0F;
         this.f[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
      }
   }

   protected void b() {
      WorldType var1 = this.b.P().u();
      if (var1 == WorldType.c) {
         FlatGeneratorInfo var2 = FlatGeneratorInfo.a(this.b.P().B());
         this.c = new WorldChunkManagerHell(BiomeGenBase.a(var2.a(), BiomeGenBase.ad), 0.5F);
      } else if (var1 == WorldType.g) {
         this.c = new WorldChunkManagerHell(BiomeGenBase.q, 0.0F);
      } else {
         this.c = new WorldChunkManager(this.b);
      }
   }

   public IChunkProvider c() {
      if (this.h == WorldType.c) {
         return new ChunkProviderFlat(this.b, this.b.J(), this.b.P().s(), this.i);
      } else if (this.h == WorldType.g) {
         return new ChunkProviderDebug(this.b);
      } else {
         return this.h == WorldType.f
            ? new ChunkProviderGenerate(this.b, this.b.J(), this.b.P().s(), this.i)
            : new ChunkProviderGenerate(this.b, this.b.J(), this.b.P().s(), this.i);
      }
   }

   public boolean a(int var1, int var2) {
      return this.b.c(new BlockPos(var1, 0, var2)) == Blocks.c;
   }

   public float a(long var1, float var3) {
      int var4 = (int)(var1 % 24000L);
      float var5 = (var4 + var3) / 24000.0F - 0.25F;
      if (var5 < 0.0F) {
         var5++;
      }

      if (var5 > 1.0F) {
         var5--;
      }

      float var7 = 1.0F - (float)((Math.cos(var5 * Math.PI) + 1.0) / 2.0);
      return var5 + (var7 - var5) / 3.0F;
   }

   public int a(long var1) {
      return (int)(var1 / 24000L % 8L + 8L) % 8;
   }

   public boolean d() {
      return true;
   }

   public float[] a(float var1, float var2) {
      float var3 = 0.4F;
      float var4 = MathHelper.b(var1 * (float) Math.PI * 2.0F) - 0.0F;
      float var5 = -0.0F;
      if (var4 >= var5 - var3 && var4 <= var5 + var3) {
         float var6 = (var4 - var5) / var3 * 0.5F + 0.5F;
         float var7 = 1.0F - (1.0F - MathHelper.a(var6 * (float) Math.PI)) * 0.99F;
         var7 *= var7;
         this.j[0] = var6 * 0.3F + 0.7F;
         this.j[1] = var6 * var6 * 0.7F + 0.2F;
         this.j[2] = var6 * var6 * 0.0F + 0.2F;
         this.j[3] = var7;
         return this.j;
      } else {
         return null;
      }
   }

   public Vec3 b(float var1, float var2) {
      float var3 = MathHelper.b(var1 * (float) Math.PI * 2.0F) * 2.0F + 0.5F;
      var3 = MathHelper.a(var3, 0.0F, 1.0F);
      float var4 = 0.7529412F;
      float var5 = 0.84705883F;
      float var6 = 1.0F;
      var4 *= var3 * 0.94F + 0.06F;
      var5 *= var3 * 0.94F + 0.06F;
      var6 *= var3 * 0.91F + 0.09F;
      return new Vec3(var4, var5, var6);
   }

   public boolean e() {
      return true;
   }

   public static WorldProvider a(int var0) {
      if (var0 == -1) {
         return new WorldProviderHell();
      } else if (var0 == 0) {
         return new WorldProviderSurface();
      } else {
         return var0 == 1 ? new WorldProviderEnd() : null;
      }
   }

   public float f() {
      return 128.0F;
   }

   public boolean g() {
      return true;
   }

   public BlockPos h() {
      return null;
   }

   public int i() {
      return this.h == WorldType.c ? 4 : this.b.F() + 1;
   }

   public double j() {
      return this.h == WorldType.c ? 1.0 : 0.03125;
   }

   public boolean b(int var1, int var2) {
      return false;
   }

   public abstract String k();

   public abstract String l();

   public WorldChunkManager m() {
      return this.c;
   }

   public boolean n() {
      return this.d;
   }

   public boolean o() {
      return this.e;
   }

   public float[] p() {
      return this.f;
   }

   public int q() {
      return this.g;
   }

   public WorldBorder r() {
      return new WorldBorder();
   }
}
