package net.minecraft.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class FlatLayerInfo {
   private final int a;
   private IBlockState b;
   private int c = 1;
   private int d;

   public FlatLayerInfo(int var1, Block var2) {
      this(3, var1, var2);
   }

   public FlatLayerInfo(int var1, int var2, Block var3) {
      this.a = var1;
      this.c = var2;
      this.b = var3.Q();
   }

   public FlatLayerInfo(int var1, int var2, Block var3, int var4) {
      this(var1, var2, var3);
      this.b = var3.a(var4);
   }

   public int b() {
      return this.c;
   }

   public IBlockState c() {
      return this.b;
   }

   private Block e() {
      return this.b.c();
   }

   private int f() {
      return this.b.c().c(this.b);
   }

   public int d() {
      return this.d;
   }

   public void b(int var1) {
      this.d = var1;
   }

   @Override
   public String toString() {
      String var1;
      if (this.a >= 3) {
         ResourceLocation var2 = Block.c.c(this.e());
         var1 = var2 == null ? "null" : var2.toString();
         if (this.c > 1) {
            var1 = this.c + "*" + var1;
         }
      } else {
         var1 = Integer.toString(Block.a(this.e()));
         if (this.c > 1) {
            var1 = this.c + "x" + var1;
         }
      }

      int var3 = this.f();
      if (var3 > 0) {
         var1 = var1 + ":" + var3;
      }

      return var1;
   }
}
