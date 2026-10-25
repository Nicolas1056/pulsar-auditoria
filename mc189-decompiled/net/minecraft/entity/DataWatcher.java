package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Rotations;
import org.apache.commons.lang3.ObjectUtils;

public class DataWatcher {
   private final Entity a;
   private boolean b = true;
   private static final Map<Class<?>, Integer> c = Maps.newHashMap();
   private final Map<Integer, DataWatcher.WatchableObject> d = Maps.newHashMap();
   private boolean e;
   private ReadWriteLock f = new ReentrantReadWriteLock();

   public DataWatcher(Entity var1) {
      this.a = var1;
   }

   public <T> void a(int var1, T var2) {
      Integer var3 = c.get(var2.getClass());
      if (var3 == null) {
         throw new IllegalArgumentException("Unknown data type: " + var2.getClass());
      } else if (var1 > 31) {
         throw new IllegalArgumentException("Data value id is too big with " + var1 + "! (Max is " + 31 + ")");
      } else if (this.d.containsKey(var1)) {
         throw new IllegalArgumentException("Duplicate id value for " + var1 + "!");
      } else {
         DataWatcher.WatchableObject var4 = new DataWatcher.WatchableObject(var3, var1, var2);
         this.f.writeLock().lock();
         this.d.put(var1, var4);
         this.f.writeLock().unlock();
         this.b = false;
      }
   }

   public void a(int var1, int var2) {
      DataWatcher.WatchableObject var3 = new DataWatcher.WatchableObject(var2, var1, null);
      this.f.writeLock().lock();
      this.d.put(var1, var3);
      this.f.writeLock().unlock();
      this.b = false;
   }

   public byte a(int var1) {
      return (Byte)this.j(var1).b();
   }

   public short b(int var1) {
      return (Short)this.j(var1).b();
   }

   public int c(int var1) {
      return (Integer)this.j(var1).b();
   }

   public float d(int var1) {
      return (Float)this.j(var1).b();
   }

   public String e(int var1) {
      return (String)this.j(var1).b();
   }

   public ItemStack f(int var1) {
      return (ItemStack)this.j(var1).b();
   }

   private DataWatcher.WatchableObject j(int var1) {
      this.f.readLock().lock();

      DataWatcher.WatchableObject var2;
      try {
         var2 = this.d.get(var1);
      } catch (Throwable var6) {
         CrashReport var4 = CrashReport.a(var6, "Getting synched entity data");
         CrashReportCategory var5 = var4.a("Synched entity data");
         var5.a("Data ID", var1);
         throw new ReportedException(var4);
      }

      this.f.readLock().unlock();
      return var2;
   }

   public Rotations h(int var1) {
      return (Rotations)this.j(var1).b();
   }

   public <T> void b(int var1, T var2) {
      DataWatcher.WatchableObject var3 = this.j(var1);
      if (ObjectUtils.notEqual(var2, var3.b())) {
         var3.a(var2);
         this.a.i(var1);
         var3.a(true);
         this.e = true;
      }
   }

   public void i(int var1) {
      this.j(var1).d = true;
      this.e = true;
   }

   public boolean a() {
      return this.e;
   }

   public static void a(List<DataWatcher.WatchableObject> var0, PacketBuffer var1) throws IOException {
      if (var0 != null) {
         for (DataWatcher.WatchableObject var3 : var0) {
            a(var1, var3);
         }
      }

      var1.writeByte(127);
   }

   public List<DataWatcher.WatchableObject> b() {
      ArrayList var1 = null;
      if (this.e) {
         this.f.readLock().lock();

         for (DataWatcher.WatchableObject var3 : this.d.values()) {
            if (var3.d()) {
               var3.a(false);
               if (var1 == null) {
                  var1 = Lists.newArrayList();
               }

               var1.add(var3);
            }
         }

         this.f.readLock().unlock();
      }

      this.e = false;
      return var1;
   }

   public void a(PacketBuffer var1) throws IOException {
      this.f.readLock().lock();

      for (DataWatcher.WatchableObject var3 : this.d.values()) {
         a(var1, var3);
      }

      this.f.readLock().unlock();
      var1.writeByte(127);
   }

   public List<DataWatcher.WatchableObject> c() {
      ArrayList var1 = null;
      this.f.readLock().lock();

      for (DataWatcher.WatchableObject var3 : this.d.values()) {
         if (var1 == null) {
            var1 = Lists.newArrayList();
         }

         var1.add(var3);
      }

      this.f.readLock().unlock();
      return var1;
   }

   private static void a(PacketBuffer var0, DataWatcher.WatchableObject var1) throws IOException {
      int var2 = (var1.c() << 5 | var1.a() & 31) & 0xFF;
      var0.writeByte(var2);
      switch (var1.c()) {
         case 0:
            var0.writeByte((Byte)var1.b());
            break;
         case 1:
            var0.writeShort((Short)var1.b());
            break;
         case 2:
            var0.writeInt((Integer)var1.b());
            break;
         case 3:
            var0.writeFloat((Float)var1.b());
            break;
         case 4:
            var0.a((String)var1.b());
            break;
         case 5:
            ItemStack var3 = (ItemStack)var1.b();
            var0.a(var3);
            break;
         case 6:
            BlockPos var4 = (BlockPos)var1.b();
            var0.writeInt(var4.n());
            var0.writeInt(var4.o());
            var0.writeInt(var4.p());
            break;
         case 7:
            Rotations var5 = (Rotations)var1.b();
            var0.writeFloat(var5.b());
            var0.writeFloat(var5.c());
            var0.writeFloat(var5.d());
      }
   }

   public static List<DataWatcher.WatchableObject> b(PacketBuffer var0) throws IOException {
      ArrayList var1 = null;

      for (byte var2 = var0.readByte(); var2 != 127; var2 = var0.readByte()) {
         if (var1 == null) {
            var1 = Lists.newArrayList();
         }

         int var3 = (var2 & 224) >> 5;
         int var4 = var2 & 31;
         DataWatcher.WatchableObject var5 = null;
         switch (var3) {
            case 0:
               var5 = new DataWatcher.WatchableObject(var3, var4, var0.readByte());
               break;
            case 1:
               var5 = new DataWatcher.WatchableObject(var3, var4, var0.readShort());
               break;
            case 2:
               var5 = new DataWatcher.WatchableObject(var3, var4, var0.readInt());
               break;
            case 3:
               var5 = new DataWatcher.WatchableObject(var3, var4, var0.readFloat());
               break;
            case 4:
               var5 = new DataWatcher.WatchableObject(var3, var4, var0.c(32767));
               break;
            case 5:
               var5 = new DataWatcher.WatchableObject(var3, var4, var0.i());
               break;
            case 6:
               int var6 = var0.readInt();
               int var7 = var0.readInt();
               int var8 = var0.readInt();
               var5 = new DataWatcher.WatchableObject(var3, var4, new BlockPos(var6, var7, var8));
               break;
            case 7:
               float var9 = var0.readFloat();
               float var10 = var0.readFloat();
               float var11 = var0.readFloat();
               var5 = new DataWatcher.WatchableObject(var3, var4, new Rotations(var9, var10, var11));
         }

         var1.add(var5);
      }

      return var1;
   }

   public void a(List<DataWatcher.WatchableObject> var1) {
      this.f.writeLock().lock();

      for (DataWatcher.WatchableObject var3 : var1) {
         DataWatcher.WatchableObject var4 = this.d.get(var3.a());
         if (var4 != null) {
            var4.a(var3.b());
            this.a.i(var3.a());
         }
      }

      this.f.writeLock().unlock();
      this.e = true;
   }

   public boolean d() {
      return this.b;
   }

   public void e() {
      this.e = false;
   }

   static {
      c.put(Byte.class, 0);
      c.put(Short.class, 1);
      c.put(Integer.class, 2);
      c.put(Float.class, 3);
      c.put(String.class, 4);
      c.put(ItemStack.class, 5);
      c.put(BlockPos.class, 6);
      c.put(Rotations.class, 7);
   }

   public static class WatchableObject {
      private final int a;
      private final int b;
      private Object c;
      private boolean d;

      public WatchableObject(int var1, int var2, Object var3) {
         this.b = var2;
         this.c = var3;
         this.a = var1;
         this.d = true;
      }

      public int a() {
         return this.b;
      }

      public void a(Object var1) {
         this.c = var1;
      }

      public Object b() {
         return this.c;
      }

      public int c() {
         return this.a;
      }

      public boolean d() {
         return this.d;
      }

      public void a(boolean var1) {
         this.d = var1;
      }
   }
}
