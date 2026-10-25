package net.minecraft.util;

public class TupleIntJsonSerializable {
   private int a;
   private IJsonSerializable b;

   public int a() {
      return this.a;
   }

   public void a(int var1) {
      this.a = var1;
   }

   public <T extends IJsonSerializable> T b() {
      return (T)this.b;
   }

   public void a(IJsonSerializable var1) {
      this.b = var1;
   }
}
