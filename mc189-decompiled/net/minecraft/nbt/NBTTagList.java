package net.minecraft.nbt;

import com.google.common.collect.Lists;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NBTTagList extends NBTBase {
   private static final Logger b = LogManager.getLogger();
   private List<NBTBase> c = Lists.newArrayList();
   private byte d = 0;

   @Override
   void a(DataOutput var1) throws IOException {
      if (!this.c.isEmpty()) {
         this.d = this.c.get(0).a();
      } else {
         this.d = 0;
      }

      var1.writeByte(this.d);
      var1.writeInt(this.c.size());

      for (int var2 = 0; var2 < this.c.size(); var2++) {
         this.c.get(var2).a(var1);
      }
   }

   @Override
   void a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.a(296L);
      if (var2 > 512) {
         throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
      } else {
         this.d = var1.readByte();
         int var4 = var1.readInt();
         if (this.d == 0 && var4 > 0) {
            throw new RuntimeException("Missing type on ListTag");
         } else {
            var3.a(32L * var4);
            this.c = Lists.newArrayListWithCapacity(var4);

            for (int var5 = 0; var5 < var4; var5++) {
               NBTBase var6 = NBTBase.a(this.d);
               var6.a(var1, var2 + 1, var3);
               this.c.add(var6);
            }
         }
      }
   }

   @Override
   public byte a() {
      return 9;
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("[");

      for (int var2 = 0; var2 < this.c.size(); var2++) {
         if (var2 != 0) {
            var1.append(',');
         }

         var1.append(var2).append(':').append(this.c.get(var2));
      }

      return var1.append(']').toString();
   }

   public void a(NBTBase var1) {
      if (var1.a() == 0) {
         b.warn("Invalid TagEnd added to ListTag");
      } else {
         if (this.d == 0) {
            this.d = var1.a();
         } else if (this.d != var1.a()) {
            b.warn("Adding mismatching tag types to tag list");
            return;
         }

         this.c.add(var1);
      }
   }

   public void a(int var1, NBTBase var2) {
      if (var2.a() == 0) {
         b.warn("Invalid TagEnd added to ListTag");
      } else if (var1 >= 0 && var1 < this.c.size()) {
         if (this.d == 0) {
            this.d = var2.a();
         } else if (this.d != var2.a()) {
            b.warn("Adding mismatching tag types to tag list");
            return;
         }

         this.c.set(var1, var2);
      } else {
         b.warn("index out of bounds to set tag in tag list");
      }
   }

   public NBTBase a(int var1) {
      return this.c.remove(var1);
   }

   @Override
   public boolean c_() {
      return this.c.isEmpty();
   }

   public NBTTagCompound b(int var1) {
      if (var1 >= 0 && var1 < this.c.size()) {
         NBTBase var2 = this.c.get(var1);
         return var2.a() == 10 ? (NBTTagCompound)var2 : new NBTTagCompound();
      } else {
         return new NBTTagCompound();
      }
   }

   public int[] c(int var1) {
      if (var1 >= 0 && var1 < this.c.size()) {
         NBTBase var2 = this.c.get(var1);
         return var2.a() == 11 ? ((NBTTagIntArray)var2).c() : new int[0];
      } else {
         return new int[0];
      }
   }

   public double d(int var1) {
      if (var1 >= 0 && var1 < this.c.size()) {
         NBTBase var2 = this.c.get(var1);
         return var2.a() == 6 ? ((NBTTagDouble)var2).g() : 0.0;
      } else {
         return 0.0;
      }
   }

   public float e(int var1) {
      if (var1 >= 0 && var1 < this.c.size()) {
         NBTBase var2 = this.c.get(var1);
         return var2.a() == 5 ? ((NBTTagFloat)var2).h() : 0.0F;
      } else {
         return 0.0F;
      }
   }

   public String f(int var1) {
      if (var1 >= 0 && var1 < this.c.size()) {
         NBTBase var2 = this.c.get(var1);
         return var2.a() == 8 ? var2.a_() : var2.toString();
      } else {
         return "";
      }
   }

   public NBTBase g(int var1) {
      return (NBTBase)(var1 >= 0 && var1 < this.c.size() ? this.c.get(var1) : new NBTTagEnd());
   }

   public int c() {
      return this.c.size();
   }

   @Override
   public NBTBase b() {
      NBTTagList var1 = new NBTTagList();
      var1.d = this.d;

      for (NBTBase var3 : this.c) {
         NBTBase var4 = var3.b();
         var1.c.add(var4);
      }

      return var1;
   }

   @Override
   public boolean equals(Object var1) {
      if (super.equals(var1)) {
         NBTTagList var2 = (NBTTagList)var1;
         if (this.d == var2.d) {
            return this.c.equals(var2.c);
         }
      }

      return false;
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ this.c.hashCode();
   }

   public int f() {
      return this.d;
   }
}
