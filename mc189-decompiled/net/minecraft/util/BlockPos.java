package net.minecraft.util;

import com.google.common.collect.AbstractIterator;
import java.util.Iterator;
import net.minecraft.entity.Entity;

public class BlockPos extends Vec3i {
   public static final BlockPos a = new BlockPos(0, 0, 0);
   private static final int c = 1 + MathHelper.c(MathHelper.b(30000000));
   private static final int d = c;
   private static final int e = 64 - c - d;
   private static final int f = 0 + d;
   private static final int g = f + e;
   private static final long h = (1L << c) - 1L;
   private static final long i = (1L << e) - 1L;
   private static final long j = (1L << d) - 1L;

   public BlockPos(int var1, int var2, int var3) {
      super(var1, var2, var3);
   }

   public BlockPos(double var1, double var3, double var5) {
      super(var1, var3, var5);
   }

   public BlockPos(Entity var1) {
      this(var1.s, var1.t, var1.u);
   }

   public BlockPos(Vec3 var1) {
      this(var1.a, var1.b, var1.c);
   }

   public BlockPos(Vec3i var1) {
      this(var1.n(), var1.o(), var1.p());
   }

   public BlockPos a(double var1, double var3, double var5) {
      return var1 == 0.0 && var3 == 0.0 && var5 == 0.0 ? this : new BlockPos(this.n() + var1, this.o() + var3, this.p() + var5);
   }

   public BlockPos a(int var1, int var2, int var3) {
      return var1 == 0 && var2 == 0 && var3 == 0 ? this : new BlockPos(this.n() + var1, this.o() + var2, this.p() + var3);
   }

   public BlockPos a(Vec3i var1) {
      return var1.n() == 0 && var1.o() == 0 && var1.p() == 0 ? this : new BlockPos(this.n() + var1.n(), this.o() + var1.o(), this.p() + var1.p());
   }

   public BlockPos b(Vec3i var1) {
      return var1.n() == 0 && var1.o() == 0 && var1.p() == 0 ? this : new BlockPos(this.n() - var1.n(), this.o() - var1.o(), this.p() - var1.p());
   }

   public BlockPos a() {
      return this.b(1);
   }

   public BlockPos b(int var1) {
      return this.a(EnumFacing.b, var1);
   }

   public BlockPos b() {
      return this.c(1);
   }

   public BlockPos c(int var1) {
      return this.a(EnumFacing.a, var1);
   }

   public BlockPos c() {
      return this.d(1);
   }

   public BlockPos d(int var1) {
      return this.a(EnumFacing.c, var1);
   }

   public BlockPos d() {
      return this.e(1);
   }

   public BlockPos e(int var1) {
      return this.a(EnumFacing.d, var1);
   }

   public BlockPos e() {
      return this.f(1);
   }

   public BlockPos f(int var1) {
      return this.a(EnumFacing.e, var1);
   }

   public BlockPos f() {
      return this.g(1);
   }

   public BlockPos g(int var1) {
      return this.a(EnumFacing.f, var1);
   }

   public BlockPos a(EnumFacing var1) {
      return this.a(var1, 1);
   }

   public BlockPos a(EnumFacing var1, int var2) {
      return var2 == 0 ? this : new BlockPos(this.n() + var1.g() * var2, this.o() + var1.h() * var2, this.p() + var1.i() * var2);
   }

   public BlockPos c(Vec3i var1) {
      return new BlockPos(this.o() * var1.p() - this.p() * var1.o(), this.p() * var1.n() - this.n() * var1.p(), this.n() * var1.o() - this.o() * var1.n());
   }

   public long g() {
      return (this.n() & h) << g | (this.o() & i) << f | (this.p() & j) << 0;
   }

   public static BlockPos a(long var0) {
      int var2 = (int)(var0 << 64 - g - c >> 64 - c);
      int var3 = (int)(var0 << 64 - f - e >> 64 - e);
      int var4 = (int)(var0 << 64 - d >> 64 - d);
      return new BlockPos(var2, var3, var4);
   }

   public static Iterable<BlockPos> a(BlockPos var0, BlockPos var1) {
      final BlockPos var2 = new BlockPos(Math.min(var0.n(), var1.n()), Math.min(var0.o(), var1.o()), Math.min(var0.p(), var1.p()));
      final BlockPos var3 = new BlockPos(Math.max(var0.n(), var1.n()), Math.max(var0.o(), var1.o()), Math.max(var0.p(), var1.p()));
      return new Iterable<BlockPos>() {
         @Override
         public Iterator<BlockPos> iterator() {
            return new AbstractIterator<BlockPos>() {
               private BlockPos b = null;

               protected BlockPos a() {
                  if (this.b == null) {
                     this.b = var2;
                     return this.b;
                  } else if (this.b.equals(var3)) {
                     return (BlockPos)this.endOfData();
                  } else {
                     int var1x = this.b.n();
                     int var2x = this.b.o();
                     int var3x = this.b.p();
                     if (var1x < var3.n()) {
                        var1x++;
                     } else if (var2x < var3.o()) {
                        var1x = var2.n();
                        var2x++;
                     } else if (var3x < var3.p()) {
                        var1x = var2.n();
                        var2x = var2.o();
                        var3x++;
                     }

                     this.b = new BlockPos(var1x, var2x, var3x);
                     return this.b;
                  }
               }
            };
         }
      };
   }

   public static Iterable<BlockPos.MutableBlockPos> b(BlockPos var0, BlockPos var1) {
      final BlockPos var2 = new BlockPos(Math.min(var0.n(), var1.n()), Math.min(var0.o(), var1.o()), Math.min(var0.p(), var1.p()));
      final BlockPos var3 = new BlockPos(Math.max(var0.n(), var1.n()), Math.max(var0.o(), var1.o()), Math.max(var0.p(), var1.p()));
      return new Iterable<BlockPos.MutableBlockPos>() {
         @Override
         public Iterator<BlockPos.MutableBlockPos> iterator() {
            return new AbstractIterator<BlockPos.MutableBlockPos>() {
               private BlockPos.MutableBlockPos b = null;

               protected BlockPos.MutableBlockPos a() {
                  if (this.b == null) {
                     this.b = new BlockPos.MutableBlockPos(var2.n(), var2.o(), var2.p());
                     return this.b;
                  } else if (this.b.equals(var3)) {
                     return (BlockPos.MutableBlockPos)this.endOfData();
                  } else {
                     int var1x = this.b.n();
                     int var2x = this.b.o();
                     int var3x = this.b.p();
                     if (var1x < var3.n()) {
                        var1x++;
                     } else if (var2x < var3.o()) {
                        var1x = var2.n();
                        var2x++;
                     } else if (var3x < var3.p()) {
                        var1x = var2.n();
                        var2x = var2.o();
                        var3x++;
                     }

                     this.b.c = var1x;
                     this.b.d = var2x;
                     this.b.e = var3x;
                     return this.b;
                  }
               }
            };
         }
      };
   }

   public static final class MutableBlockPos extends BlockPos {
      private int c;
      private int d;
      private int e;

      public MutableBlockPos() {
         this(0, 0, 0);
      }

      public MutableBlockPos(int var1, int var2, int var3) {
         super(0, 0, 0);
         this.c = var1;
         this.d = var2;
         this.e = var3;
      }

      @Override
      public int n() {
         return this.c;
      }

      @Override
      public int o() {
         return this.d;
      }

      @Override
      public int p() {
         return this.e;
      }

      public BlockPos.MutableBlockPos c(int var1, int var2, int var3) {
         this.c = var1;
         this.d = var2;
         this.e = var3;
         return this;
      }
   }
}
