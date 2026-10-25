package net.minecraft.util;

import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;

public class Rotations {
   protected final float a;
   protected final float b;
   protected final float c;

   public Rotations(float var1, float var2, float var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public Rotations(NBTTagList var1) {
      this.a = var1.e(0);
      this.b = var1.e(1);
      this.c = var1.e(2);
   }

   public NBTTagList a() {
      NBTTagList var1 = new NBTTagList();
      var1.a(new NBTTagFloat(this.a));
      var1.a(new NBTTagFloat(this.b));
      var1.a(new NBTTagFloat(this.c));
      return var1;
   }

   @Override
   public boolean equals(Object var1) {
      if (!(var1 instanceof Rotations)) {
         return false;
      } else {
         Rotations var2 = (Rotations)var1;
         return this.a == var2.a && this.b == var2.b && this.c == var2.c;
      }
   }

   public float b() {
      return this.a;
   }

   public float c() {
      return this.b;
   }

   public float d() {
      return this.c;
   }
}
