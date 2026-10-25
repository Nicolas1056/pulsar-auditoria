package net.minecraft.world.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveHandler implements ISaveHandler, IPlayerFileData {
   private static final Logger a = LogManager.getLogger();
   private final File b;
   private final File c;
   private final File d;
   private final long e = MinecraftServer.az();
   private final String f;

   public SaveHandler(File var1, String var2, boolean var3) {
      this.b = new File(var1, var2);
      this.b.mkdirs();
      this.c = new File(this.b, "playerdata");
      this.d = new File(this.b, "data");
      this.d.mkdirs();
      this.f = var2;
      if (var3) {
         this.c.mkdirs();
      }

      this.h();
   }

   private void h() {
      try {
         File var1 = new File(this.b, "session.lock");
         DataOutputStream var2 = new DataOutputStream(new FileOutputStream(var1));

         try {
            var2.writeLong(this.e);
         } finally {
            var2.close();
         }
      } catch (IOException var7) {
         var7.printStackTrace();
         throw new RuntimeException("Failed to check session lock, aborting");
      }
   }

   @Override
   public File b() {
      return this.b;
   }

   @Override
   public void c() throws MinecraftException {
      try {
         File var1 = new File(this.b, "session.lock");
         DataInputStream var2 = new DataInputStream(new FileInputStream(var1));

         try {
            if (var2.readLong() != this.e) {
               throw new MinecraftException("The save is being accessed from another location, aborting");
            }
         } finally {
            var2.close();
         }
      } catch (IOException var7) {
         throw new MinecraftException("Failed to check session lock, aborting");
      }
   }

   @Override
   public IChunkLoader a(WorldProvider var1) {
      throw new RuntimeException("Old Chunk Storage is no longer supported.");
   }

   @Override
   public WorldInfo d() {
      File var1 = new File(this.b, "level.dat");
      if (var1.exists()) {
         try {
            NBTTagCompound var7 = CompressedStreamTools.a(new FileInputStream(var1));
            NBTTagCompound var8 = var7.m("Data");
            return new WorldInfo(var8);
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }

      var1 = new File(this.b, "level.dat_old");
      if (var1.exists()) {
         try {
            NBTTagCompound var2 = CompressedStreamTools.a(new FileInputStream(var1));
            NBTTagCompound var3 = var2.m("Data");
            return new WorldInfo(var3);
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      return null;
   }

   @Override
   public void a(WorldInfo var1, NBTTagCompound var2) {
      NBTTagCompound var3 = var1.a(var2);
      NBTTagCompound var4 = new NBTTagCompound();
      var4.a("Data", var3);

      try {
         File var5 = new File(this.b, "level.dat_new");
         File var6 = new File(this.b, "level.dat_old");
         File var7 = new File(this.b, "level.dat");
         CompressedStreamTools.a(var4, new FileOutputStream(var5));
         if (var6.exists()) {
            var6.delete();
         }

         var7.renameTo(var6);
         if (var7.exists()) {
            var7.delete();
         }

         var5.renameTo(var7);
         if (var5.exists()) {
            var5.delete();
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }
   }

   @Override
   public void a(WorldInfo var1) {
      NBTTagCompound var2 = var1.a();
      NBTTagCompound var3 = new NBTTagCompound();
      var3.a("Data", var2);

      try {
         File var4 = new File(this.b, "level.dat_new");
         File var5 = new File(this.b, "level.dat_old");
         File var6 = new File(this.b, "level.dat");
         CompressedStreamTools.a(var3, new FileOutputStream(var4));
         if (var5.exists()) {
            var5.delete();
         }

         var6.renameTo(var5);
         if (var6.exists()) {
            var6.delete();
         }

         var4.renameTo(var6);
         if (var4.exists()) {
            var4.delete();
         }
      } catch (Exception var7) {
         var7.printStackTrace();
      }
   }

   @Override
   public void a(EntityPlayer var1) {
      try {
         NBTTagCompound var2 = new NBTTagCompound();
         var1.e(var2);
         File var3 = new File(this.c, var1.aK().toString() + ".dat.tmp");
         File var4 = new File(this.c, var1.aK().toString() + ".dat");
         CompressedStreamTools.a(var2, new FileOutputStream(var3));
         if (var4.exists()) {
            var4.delete();
         }

         var3.renameTo(var4);
      } catch (Exception var5) {
         a.warn("Failed to save player data for " + var1.e_());
      }
   }

   @Override
   public NBTTagCompound b(EntityPlayer var1) {
      NBTTagCompound var2 = null;

      try {
         File var3 = new File(this.c, var1.aK().toString() + ".dat");
         if (var3.exists() && var3.isFile()) {
            var2 = CompressedStreamTools.a(new FileInputStream(var3));
         }
      } catch (Exception var4) {
         a.warn("Failed to load player data for " + var1.e_());
      }

      if (var2 != null) {
         var1.f(var2);
      }

      return var2;
   }

   @Override
   public IPlayerFileData e() {
      return this;
   }

   @Override
   public String[] f() {
      String[] var1 = this.c.list();
      if (var1 == null) {
         var1 = new String[0];
      }

      for (int var2 = 0; var2 < var1.length; var2++) {
         if (var1[var2].endsWith(".dat")) {
            var1[var2] = var1[var2].substring(0, var1[var2].length() - 4);
         }
      }

      return var1;
   }

   @Override
   public void a() {
   }

   @Override
   public File a(String var1) {
      return new File(this.d, var1 + ".dat");
   }

   @Override
   public String g() {
      return this.f;
   }
}
