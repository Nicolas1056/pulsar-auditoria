package net.minecraft.world.storage;

import java.io.File;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;

public class SaveHandlerMP implements ISaveHandler {
   @Override
   public WorldInfo d() {
      return null;
   }

   @Override
   public void c() throws MinecraftException {
   }

   @Override
   public IChunkLoader a(WorldProvider var1) {
      return null;
   }

   @Override
   public void a(WorldInfo var1, NBTTagCompound var2) {
   }

   @Override
   public void a(WorldInfo var1) {
   }

   @Override
   public IPlayerFileData e() {
      return null;
   }

   @Override
   public void a() {
   }

   @Override
   public File a(String var1) {
      return null;
   }

   @Override
   public String g() {
      return "none";
   }

   @Override
   public File b() {
      return null;
   }
}
