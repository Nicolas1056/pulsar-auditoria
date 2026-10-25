package net.minecraft.block;

import net.minecraft.util.BlockPos;

public class BlockEventData {
   private BlockPos a;
   private Block b;
   private int c;
   private int d;

   public BlockEventData(BlockPos var1, Block var2, int var3, int var4) {
      this.a = var1;
      this.c = var3;
      this.d = var4;
      this.b = var2;
   }

   public BlockPos a() {
      return this.a;
   }

   public int b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   public Block d() {
      return this.b;
   }

   @Override
   public boolean equals(Object var1) {
      if (!(var1 instanceof BlockEventData)) {
         return false;
      } else {
         BlockEventData var2 = (BlockEventData)var1;
         return this.a.equals(var2.a) && this.c == var2.c && this.d == var2.d && this.b == var2.b;
      }
   }

   @Override
   public String toString() {
      return "TE(" + this.a + ")," + this.c + "," + this.d + "," + this.b;
   }
}
