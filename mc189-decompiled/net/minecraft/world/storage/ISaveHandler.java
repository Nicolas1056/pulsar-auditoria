package net.minecraft.world.storage;

import java.io.File;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;

public interface ISaveHandler {
   WorldInfo d();

   void c() throws MinecraftException;

   IChunkLoader a(WorldProvider var1);

   void a(WorldInfo var1, NBTTagCompound var2);

   void a(WorldInfo var1);

   IPlayerFileData e();

   void a();

   File b();

   File a(String var1);

   String g();
}
