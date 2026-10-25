package net.minecraft.client.audio;

public class SoundEventAccessor implements ISoundEventAccessor<SoundPoolEntry> {
   private final SoundPoolEntry a;
   private final int b;

   SoundEventAccessor(SoundPoolEntry var1, int var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public int a() {
      return this.b;
   }

   public SoundPoolEntry b() {
      return new SoundPoolEntry(this.a);
   }
}
