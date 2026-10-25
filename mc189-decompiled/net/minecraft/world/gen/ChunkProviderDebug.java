package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

public class ChunkProviderDebug implements IChunkProvider {
   private static final List<IBlockState> a = Lists.newArrayList();
   private static final int b;
   private static final int c;
   private final World d;

   public ChunkProviderDebug(World var1) {
      this.d = var1;
   }

   @Override
   public Chunk d(int var1, int var2) {
      ChunkPrimer var3 = new ChunkPrimer();

      for (int var4 = 0; var4 < 16; var4++) {
         for (int var5 = 0; var5 < 16; var5++) {
            int var6 = var1 * 16 + var4;
            int var7 = var2 * 16 + var5;
            var3.a(var4, 60, var5, Blocks.cv.Q());
            IBlockState var8 = b(var6, var7);
            if (var8 != null) {
               var3.a(var4, 70, var5, var8);
            }
         }
      }

      Chunk var9 = new Chunk(this.d, var3, var1, var2);
      var9.b();
      BiomeGenBase[] var10 = this.d.v().b(null, var1 * 16, var2 * 16, 16, 16);
      byte[] var11 = var9.k();

      for (int var12 = 0; var12 < var11.length; var12++) {
         var11[var12] = (byte)var10[var12].az;
      }

      var9.b();
      return var9;
   }

   public static IBlockState b(int var0, int var1) {
      IBlockState var2 = null;
      if (var0 > 0 && var1 > 0 && var0 % 2 != 0 && var1 % 2 != 0) {
         var0 /= 2;
         var1 /= 2;
         if (var0 <= b && var1 <= c) {
            int var3 = MathHelper.a(var0 * b + var1);
            if (var3 < a.size()) {
               var2 = a.get(var3);
            }
         }
      }

      return var2;
   }

   @Override
   public boolean a(int var1, int var2) {
      return true;
   }

   @Override
   public void a(IChunkProvider var1, int var2, int var3) {
   }

   @Override
   public boolean a(IChunkProvider var1, Chunk var2, int var3, int var4) {
      return false;
   }

   @Override
   public boolean a(boolean var1, IProgressUpdate var2) {
      return true;
   }

   @Override
   public void c() {
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean e() {
      return true;
   }

   @Override
   public String f() {
      return "DebugLevelSource";
   }

   @Override
   public List<BiomeGenBase.SpawnListEntry> a(EnumCreatureType var1, BlockPos var2) {
      BiomeGenBase var3 = this.d.b(var2);
      return var3.a(var1);
   }

   @Override
   public BlockPos a(World var1, String var2, BlockPos var3) {
      return null;
   }

   @Override
   public int g() {
      return 0;
   }

   @Override
   public void a(Chunk var1, int var2, int var3) {
   }

   @Override
   public Chunk a(BlockPos var1) {
      return this.d(var1.n() >> 4, var1.p() >> 4);
   }

   static {
      for (Block var1 : Block.c) {
         a.addAll(var1.P().a());
      }

      b = MathHelper.f(MathHelper.c((float)a.size()));
      c = MathHelper.f((float)a.size() / b);
   }
}
