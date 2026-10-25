package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ViewFrustum {
   protected final RenderGlobal a;
   protected final World b;
   protected int c;
   protected int d;
   protected int e;
   public RenderChunk[] f;

   public ViewFrustum(World var1, int var2, RenderGlobal var3, IRenderChunkFactory var4) {
      this.a = var3;
      this.b = var1;
      this.a(var2);
      this.a(var4);
   }

   protected void a(IRenderChunkFactory var1) {
      int var2 = this.d * this.c * this.e;
      this.f = new RenderChunk[var2];
      int var3 = 0;

      for (int var4 = 0; var4 < this.d; var4++) {
         for (int var5 = 0; var5 < this.c; var5++) {
            for (int var6 = 0; var6 < this.e; var6++) {
               int var7 = (var6 * this.c + var5) * this.d + var4;
               BlockPos var8 = new BlockPos(var4 * 16, var5 * 16, var6 * 16);
               this.f[var7] = var1.a(this.b, this.a, var8, var3++);
            }
         }
      }
   }

   public void a() {
      for (RenderChunk var4 : this.f) {
         var4.a();
      }
   }

   protected void a(int var1) {
      int var2 = var1 * 2 + 1;
      this.d = var2;
      this.c = 16;
      this.e = var2;
   }

   public void a(double var1, double var3) {
      int var5 = MathHelper.c(var1) - 8;
      int var6 = MathHelper.c(var3) - 8;
      int var7 = this.d * 16;

      for (int var8 = 0; var8 < this.d; var8++) {
         int var9 = this.a(var5, var7, var8);

         for (int var10 = 0; var10 < this.e; var10++) {
            int var11 = this.a(var6, var7, var10);

            for (int var12 = 0; var12 < this.c; var12++) {
               int var13 = var12 * 16;
               RenderChunk var14 = this.f[(var10 * this.c + var12) * this.d + var8];
               BlockPos var15 = new BlockPos(var9, var13, var11);
               if (!var15.equals(var14.j())) {
                  var14.a(var15);
               }
            }
         }
      }
   }

   private int a(int var1, int var2, int var3) {
      int var4 = var3 * 16;
      int var5 = var4 - var1 + var2 / 2;
      if (var5 < 0) {
         var5 -= var2 - 1;
      }

      return var4 - var5 / var2 * var2;
   }

   public void a(int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = MathHelper.a(var1, 16);
      int var8 = MathHelper.a(var2, 16);
      int var9 = MathHelper.a(var3, 16);
      int var10 = MathHelper.a(var4, 16);
      int var11 = MathHelper.a(var5, 16);
      int var12 = MathHelper.a(var6, 16);

      for (int var13 = var7; var13 <= var10; var13++) {
         int var14 = var13 % this.d;
         if (var14 < 0) {
            var14 += this.d;
         }

         for (int var15 = var8; var15 <= var11; var15++) {
            int var16 = var15 % this.c;
            if (var16 < 0) {
               var16 += this.c;
            }

            for (int var17 = var9; var17 <= var12; var17++) {
               int var18 = var17 % this.e;
               if (var18 < 0) {
                  var18 += this.e;
               }

               int var19 = (var18 * this.c + var16) * this.d + var14;
               RenderChunk var20 = this.f[var19];
               var20.a(true);
            }
         }
      }
   }

   protected RenderChunk a(BlockPos var1) {
      int var2 = MathHelper.a(var1.n(), 16);
      int var3 = MathHelper.a(var1.o(), 16);
      int var4 = MathHelper.a(var1.p(), 16);
      if (var3 >= 0 && var3 < this.c) {
         var2 %= this.d;
         if (var2 < 0) {
            var2 += this.d;
         }

         var4 %= this.e;
         if (var4 < 0) {
            var4 += this.e;
         }

         int var5 = (var4 * this.c + var3) * this.d + var2;
         return this.f[var5];
      } else {
         return null;
      }
   }
}
