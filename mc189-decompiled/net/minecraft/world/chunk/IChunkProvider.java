package net.minecraft.world.chunk;

import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public interface IChunkProvider {
   boolean a(int var1, int var2);

   Chunk d(int var1, int var2);

   Chunk a(BlockPos var1);

   void a(IChunkProvider var1, int var2, int var3);

   boolean a(IChunkProvider var1, Chunk var2, int var3, int var4);

   boolean a(boolean var1, IProgressUpdate var2);

   boolean d();

   boolean e();

   String f();

   List<BiomeGenBase.SpawnListEntry> a(EnumCreatureType var1, BlockPos var2);

   BlockPos a(World var1, String var2, BlockPos var3);

   int g();

   void a(Chunk var1, int var2, int var3);

   void c();
}
