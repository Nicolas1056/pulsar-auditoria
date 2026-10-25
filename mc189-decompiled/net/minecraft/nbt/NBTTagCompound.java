package net.minecraft.nbt;

import com.google.common.collect.Maps;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;

public class NBTTagCompound extends NBTBase {
   private Map<String, NBTBase> b = Maps.newHashMap();

   @Override
   void a(DataOutput var1) throws IOException {
      for (String var3 : this.b.keySet()) {
         NBTBase var4 = this.b.get(var3);
         a(var3, var4, var1);
      }

      var1.writeByte(0);
   }

   @Override
   void a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.a(384L);
      if (var2 > 512) {
         throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
      } else {
         this.b.clear();

         byte var4;
         while ((var4 = a(var1, var3)) != 0) {
            String var5 = b(var1, var3);
            var3.a(224 + 16 * var5.length());
            NBTBase var6 = a(var4, var5, var1, var2 + 1, var3);
            if (this.b.put(var5, var6) != null) {
               var3.a(288L);
            }
         }
      }
   }

   public Set<String> c() {
      return this.b.keySet();
   }

   @Override
   public byte a() {
      return 10;
   }

   public void a(String var1, NBTBase var2) {
      this.b.put(var1, var2);
   }

   public void a(String var1, byte var2) {
      this.b.put(var1, new NBTTagByte(var2));
   }

   public void a(String var1, short var2) {
      this.b.put(var1, new NBTTagShort(var2));
   }

   public void a(String var1, int var2) {
      this.b.put(var1, new NBTTagInt(var2));
   }

   public void a(String var1, long var2) {
      this.b.put(var1, new NBTTagLong(var2));
   }

   public void a(String var1, float var2) {
      this.b.put(var1, new NBTTagFloat(var2));
   }

   public void a(String var1, double var2) {
      this.b.put(var1, new NBTTagDouble(var2));
   }

   public void a(String var1, String var2) {
      this.b.put(var1, new NBTTagString(var2));
   }

   public void a(String var1, byte[] var2) {
      this.b.put(var1, new NBTTagByteArray(var2));
   }

   public void a(String var1, int[] var2) {
      this.b.put(var1, new NBTTagIntArray(var2));
   }

   public void a(String var1, boolean var2) {
      this.a(var1, (byte)(var2 ? 1 : 0));
   }

   public NBTBase a(String var1) {
      return this.b.get(var1);
   }

   public byte b(String var1) {
      NBTBase var2 = this.b.get(var1);
      return var2 != null ? var2.a() : 0;
   }

   public boolean c(String var1) {
      return this.b.containsKey(var1);
   }

   public boolean b(String var1, int var2) {
      byte var3 = this.b(var1);
      if (var3 == var2) {
         return true;
      } else if (var2 != 99) {
         if (var3 > 0) {
         }

         return false;
      } else {
         return var3 == 1 || var3 == 2 || var3 == 3 || var3 == 4 || var3 == 5 || var3 == 6;
      }
   }

   public byte d(String var1) {
      try {
         return !this.b(var1, 99) ? 0 : ((NBTBase.NBTPrimitive)this.b.get(var1)).f();
      } catch (ClassCastException var3) {
         return 0;
      }
   }

   public short e(String var1) {
      try {
         return !this.b(var1, 99) ? 0 : ((NBTBase.NBTPrimitive)this.b.get(var1)).e();
      } catch (ClassCastException var3) {
         return 0;
      }
   }

   public int f(String var1) {
      try {
         return !this.b(var1, 99) ? 0 : ((NBTBase.NBTPrimitive)this.b.get(var1)).d();
      } catch (ClassCastException var3) {
         return 0;
      }
   }

   public long g(String var1) {
      try {
         return !this.b(var1, 99) ? 0L : ((NBTBase.NBTPrimitive)this.b.get(var1)).c();
      } catch (ClassCastException var3) {
         return 0L;
      }
   }

   public float h(String var1) {
      try {
         return !this.b(var1, 99) ? 0.0F : ((NBTBase.NBTPrimitive)this.b.get(var1)).h();
      } catch (ClassCastException var3) {
         return 0.0F;
      }
   }

   public double i(String var1) {
      try {
         return !this.b(var1, 99) ? 0.0 : ((NBTBase.NBTPrimitive)this.b.get(var1)).g();
      } catch (ClassCastException var3) {
         return 0.0;
      }
   }

   public String j(String var1) {
      try {
         return !this.b(var1, 8) ? "" : this.b.get(var1).a_();
      } catch (ClassCastException var3) {
         return "";
      }
   }

   public byte[] k(String var1) {
      try {
         return !this.b(var1, 7) ? new byte[0] : ((NBTTagByteArray)this.b.get(var1)).c();
      } catch (ClassCastException var3) {
         throw new ReportedException(this.a(var1, 7, var3));
      }
   }

   public int[] l(String var1) {
      try {
         return !this.b(var1, 11) ? new int[0] : ((NBTTagIntArray)this.b.get(var1)).c();
      } catch (ClassCastException var3) {
         throw new ReportedException(this.a(var1, 11, var3));
      }
   }

   public NBTTagCompound m(String var1) {
      try {
         return !this.b(var1, 10) ? new NBTTagCompound() : (NBTTagCompound)this.b.get(var1);
      } catch (ClassCastException var3) {
         throw new ReportedException(this.a(var1, 10, var3));
      }
   }

   public NBTTagList c(String var1, int var2) {
      try {
         if (this.b(var1) != 9) {
            return new NBTTagList();
         } else {
            NBTTagList var3 = (NBTTagList)this.b.get(var1);
            return var3.c() > 0 && var3.f() != var2 ? new NBTTagList() : var3;
         }
      } catch (ClassCastException var4) {
         throw new ReportedException(this.a(var1, 9, var4));
      }
   }

   public boolean n(String var1) {
      return this.d(var1) != 0;
   }

   public void o(String var1) {
      this.b.remove(var1);
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("{");

      for (Entry var3 : this.b.entrySet()) {
         if (var1.length() != 1) {
            var1.append(',');
         }

         var1.append((String)var3.getKey()).append(':').append(var3.getValue());
      }

      return var1.append('}').toString();
   }

   @Override
   public boolean c_() {
      return this.b.isEmpty();
   }

   private CrashReport a(final String var1, final int var2, ClassCastException var3) {
      CrashReport var4 = CrashReport.a(var3, "Reading NBT data");
      CrashReportCategory var5 = var4.a("Corrupt NBT tag", 1);
      var5.a("Tag type found", new Callable<String>() {
         public String a() throws Exception {
            return NBTBase.a[NBTTagCompound.this.b.get(var1).a()];
         }
      });
      var5.a("Tag type expected", new Callable<String>() {
         public String a() throws Exception {
            return NBTBase.a[var2];
         }
      });
      var5.a("Tag name", var1);
      return var4;
   }

   @Override
   public NBTBase b() {
      NBTTagCompound var1 = new NBTTagCompound();

      for (String var3 : this.b.keySet()) {
         var1.a(var3, this.b.get(var3).b());
      }

      return var1;
   }

   @Override
   public boolean equals(Object var1) {
      if (super.equals(var1)) {
         NBTTagCompound var2 = (NBTTagCompound)var1;
         return this.b.entrySet().equals(var2.b.entrySet());
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ this.b.hashCode();
   }

   private static void a(String var0, NBTBase var1, DataOutput var2) throws IOException {
      var2.writeByte(var1.a());
      if (var1.a() != 0) {
         var2.writeUTF(var0);
         var1.a(var2);
      }
   }

   private static byte a(DataInput var0, NBTSizeTracker var1) throws IOException {
      return var0.readByte();
   }

   private static String b(DataInput var0, NBTSizeTracker var1) throws IOException {
      return var0.readUTF();
   }

   static NBTBase a(byte var0, String var1, DataInput var2, int var3, NBTSizeTracker var4) throws IOException {
      NBTBase var5 = NBTBase.a(var0);

      try {
         var5.a(var2, var3, var4);
         return var5;
      } catch (IOException var9) {
         CrashReport var7 = CrashReport.a(var9, "Loading NBT data");
         CrashReportCategory var8 = var7.a("NBT Tag");
         var8.a("Tag name", var1);
         var8.a("Tag type", var0);
         throw new ReportedException(var7);
      }
   }

   public void a(NBTTagCompound var1) {
      for (String var3 : var1.b.keySet()) {
         NBTBase var4 = var1.b.get(var3);
         if (var4.a() == 10) {
            if (this.b(var3, 10)) {
               NBTTagCompound var5 = this.m(var3);
               var5.a((NBTTagCompound)var4);
            } else {
               this.a(var3, var4.b());
            }
         } else {
            this.a(var3, var4.b());
         }
      }
   }
}
