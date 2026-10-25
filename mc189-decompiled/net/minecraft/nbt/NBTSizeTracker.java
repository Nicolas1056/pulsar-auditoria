package net.minecraft.nbt;

public class NBTSizeTracker {
   public static final NBTSizeTracker a = new NBTSizeTracker(0L) {
      @Override
      public void a(long var1) {
      }
   };
   private final long b;
   private long c;

   public NBTSizeTracker(long var1) {
      this.b = var1;
   }

   public void a(long var1) {
      this.c += var1 / 8L;
      if (this.c > this.b) {
         throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.c + "bytes where max allowed: " + this.b);
      }
   }
}
