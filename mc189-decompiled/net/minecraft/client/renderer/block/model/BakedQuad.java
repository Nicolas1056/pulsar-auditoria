package net.minecraft.client.renderer.block.model;

import net.minecraft.util.EnumFacing;

public class BakedQuad {
   protected final int[] a;
   protected final int b;
   protected final EnumFacing c;

   public BakedQuad(int[] var1, int var2, EnumFacing var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public int[] a() {
      return this.a;
   }

   public boolean b() {
      return this.b != -1;
   }

   public int c() {
      return this.b;
   }

   public EnumFacing d() {
      return this.c;
   }
}
