package net.minecraft.client.renderer;

import net.minecraft.util.BlockPos;

public class DestroyBlockProgress {
   private final int a;
   private final BlockPos b;
   private int c;
   private int d;

   public DestroyBlockProgress(int var1, BlockPos var2) {
      this.a = var1;
      this.b = var2;
   }

   public BlockPos b() {
      return this.b;
   }

   public void a(int var1) {
      if (var1 > 10) {
         var1 = 10;
      }

      this.c = var1;
   }

   public int c() {
      return this.c;
   }

   public void b(int var1) {
      this.d = var1;
   }

   public int d() {
      return this.d;
   }
}
