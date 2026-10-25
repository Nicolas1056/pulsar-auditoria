package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

public class NextTickListEntry implements Comparable<NextTickListEntry> {
   private static long d;
   private final Block e;
   public final BlockPos a;
   public long b;
   public int c;
   private long f;

   public NextTickListEntry(BlockPos var1, Block var2) {
      this.f = d++;
      this.a = var1;
      this.e = var2;
   }

   @Override
   public boolean equals(Object var1) {
      if (!(var1 instanceof NextTickListEntry)) {
         return false;
      } else {
         NextTickListEntry var2 = (NextTickListEntry)var1;
         return this.a.equals(var2.a) && Block.a(this.e, var2.e);
      }
   }

   @Override
   public int hashCode() {
      return this.a.hashCode();
   }

   public NextTickListEntry a(long var1) {
      this.b = var1;
      return this;
   }

   public void a(int var1) {
      this.c = var1;
   }

   public int a(NextTickListEntry var1) {
      if (this.b < var1.b) {
         return -1;
      } else if (this.b > var1.b) {
         return 1;
      } else if (this.c != var1.c) {
         return this.c - var1.c;
      } else if (this.f < var1.f) {
         return -1;
      } else {
         return this.f > var1.f ? 1 : 0;
      }
   }

   @Override
   public String toString() {
      return Block.a(this.e) + ": " + this.a + ", " + this.b + ", " + this.c + ", " + this.f;
   }

   public Block a() {
      return this.e;
   }
}
