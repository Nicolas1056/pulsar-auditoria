package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.world.WorldSavedData;

public class MapStorage {
   private ISaveHandler b;
   protected Map<String, WorldSavedData> a = Maps.newHashMap();
   private List<WorldSavedData> c = Lists.newArrayList();
   private Map<String, Short> d = Maps.newHashMap();

   public MapStorage(ISaveHandler var1) {
      this.b = var1;
      this.b();
   }

   public WorldSavedData a(Class<? extends WorldSavedData> var1, String var2) {
      WorldSavedData var3 = this.a.get(var2);
      if (var3 != null) {
         return var3;
      } else {
         if (this.b != null) {
            try {
               File var4 = this.b.a(var2);
               if (var4 != null && var4.exists()) {
                  try {
                     var3 = (WorldSavedData)var1.getConstructor(String.class).newInstance(var2);
                  } catch (Exception var7) {
                     throw new RuntimeException("Failed to instantiate " + var1.toString(), var7);
                  }

                  FileInputStream var5 = new FileInputStream(var4);
                  NBTTagCompound var6 = CompressedStreamTools.a(var5);
                  var5.close();
                  var3.a(var6.m("data"));
               }
            } catch (Exception var8) {
               var8.printStackTrace();
            }
         }

         if (var3 != null) {
            this.a.put(var2, var3);
            this.c.add(var3);
         }

         return var3;
      }
   }

   public void a(String var1, WorldSavedData var2) {
      if (this.a.containsKey(var1)) {
         this.c.remove(this.a.remove(var1));
      }

      this.a.put(var1, var2);
      this.c.add(var2);
   }

   public void a() {
      for (int var1 = 0; var1 < this.c.size(); var1++) {
         WorldSavedData var2 = this.c.get(var1);
         if (var2.d()) {
            this.a(var2);
            var2.a(false);
         }
      }
   }

   private void a(WorldSavedData var1) {
      if (this.b != null) {
         try {
            File var2 = this.b.a(var1.a);
            if (var2 != null) {
               NBTTagCompound var3 = new NBTTagCompound();
               var1.b(var3);
               NBTTagCompound var4 = new NBTTagCompound();
               var4.a("data", var3);
               FileOutputStream var5 = new FileOutputStream(var2);
               CompressedStreamTools.a(var4, var5);
               var5.close();
            }
         } catch (Exception var6) {
            var6.printStackTrace();
         }
      }
   }

   private void b() {
      try {
         this.d.clear();
         if (this.b == null) {
            return;
         }

         File var1 = this.b.a("idcounts");
         if (var1 != null && var1.exists()) {
            DataInputStream var2 = new DataInputStream(new FileInputStream(var1));
            NBTTagCompound var3 = CompressedStreamTools.a(var2);
            var2.close();

            for (String var5 : var3.c()) {
               NBTBase var6 = var3.a(var5);
               if (var6 instanceof NBTTagShort) {
                  NBTTagShort var7 = (NBTTagShort)var6;
                  short var9 = var7.e();
                  this.d.put(var5, var9);
               }
            }
         }
      } catch (Exception var10) {
         var10.printStackTrace();
      }
   }

   public int a(String var1) {
      Short var2 = this.d.get(var1);
      if (var2 == null) {
         var2 = (short)0;
      } else {
         var2 = (short)(var2 + 1);
      }

      this.d.put(var1, var2);
      if (this.b == null) {
         return var2;
      } else {
         try {
            File var3 = this.b.a("idcounts");
            if (var3 != null) {
               NBTTagCompound var4 = new NBTTagCompound();

               for (String var6 : this.d.keySet()) {
                  short var7 = this.d.get(var6);
                  var4.a(var6, var7);
               }

               DataOutputStream var10 = new DataOutputStream(new FileOutputStream(var3));
               CompressedStreamTools.a(var4, (DataOutput)var10);
               var10.close();
            }
         } catch (Exception var8) {
            var8.printStackTrace();
         }

         return var2;
      }
   }
}
