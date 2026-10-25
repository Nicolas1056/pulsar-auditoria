package net.minecraft.block.state.pattern;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class BlockPattern {
   private final Predicate<BlockWorldState>[][][] a;
   private final int b;
   private final int c;
   private final int d;

   public BlockPattern(Predicate<BlockWorldState>[][][] var1) {
      this.a = var1;
      this.b = var1.length;
      if (this.b > 0) {
         this.c = var1[0].length;
         if (this.c > 0) {
            this.d = var1[0][0].length;
         } else {
            this.d = 0;
         }
      } else {
         this.c = 0;
         this.d = 0;
      }
   }

   public int b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   private BlockPattern.PatternHelper a(BlockPos var1, EnumFacing var2, EnumFacing var3, LoadingCache<BlockPos, BlockWorldState> var4) {
      for (int var5 = 0; var5 < this.d; var5++) {
         for (int var6 = 0; var6 < this.c; var6++) {
            for (int var7 = 0; var7 < this.b; var7++) {
               if (!this.a[var7][var6][var5].apply(var4.getUnchecked(a(var1, var2, var3, var5, var6, var7)))) {
                  return null;
               }
            }
         }
      }

      return new BlockPattern.PatternHelper(var1, var2, var3, var4, this.d, this.c, this.b);
   }

   public BlockPattern.PatternHelper a(World var1, BlockPos var2) {
      LoadingCache var3 = a(var1, false);
      int var4 = Math.max(Math.max(this.d, this.c), this.b);

      for (BlockPos var6 : BlockPos.a(var2, var2.a(var4 - 1, var4 - 1, var4 - 1))) {
         for (EnumFacing var10 : EnumFacing.values()) {
            for (EnumFacing var14 : EnumFacing.values()) {
               if (var14 != var10 && var14 != var10.d()) {
                  BlockPattern.PatternHelper var15 = this.a(var6, var10, var14, var3);
                  if (var15 != null) {
                     return var15;
                  }
               }
            }
         }
      }

      return null;
   }

   public static LoadingCache<BlockPos, BlockWorldState> a(World var0, boolean var1) {
      return CacheBuilder.newBuilder().build(new BlockPattern.CacheLoader(var0, var1));
   }

   protected static BlockPos a(BlockPos var0, EnumFacing var1, EnumFacing var2, int var3, int var4, int var5) {
      if (var1 != var2 && var1 != var2.d()) {
         Vec3i var6 = new Vec3i(var1.g(), var1.h(), var1.i());
         Vec3i var7 = new Vec3i(var2.g(), var2.h(), var2.i());
         Vec3i var8 = var6.d(var7);
         return var0.a(
            var7.n() * -var4 + var8.n() * var3 + var6.n() * var5,
            var7.o() * -var4 + var8.o() * var3 + var6.o() * var5,
            var7.p() * -var4 + var8.p() * var3 + var6.p() * var5
         );
      } else {
         throw new IllegalArgumentException("Invalid forwards & up combination");
      }
   }

   static class CacheLoader extends com.google.common.cache.CacheLoader<BlockPos, BlockWorldState> {
      private final World a;
      private final boolean b;

      public CacheLoader(World var1, boolean var2) {
         this.a = var1;
         this.b = var2;
      }

      public BlockWorldState a(BlockPos var1) throws Exception {
         return new BlockWorldState(this.a, var1, this.b);
      }
   }

   public static class PatternHelper {
      private final BlockPos a;
      private final EnumFacing b;
      private final EnumFacing c;
      private final LoadingCache<BlockPos, BlockWorldState> d;
      private final int e;
      private final int f;
      private final int g;

      public PatternHelper(BlockPos var1, EnumFacing var2, EnumFacing var3, LoadingCache<BlockPos, BlockWorldState> var4, int var5, int var6, int var7) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
         this.d = var4;
         this.e = var5;
         this.f = var6;
         this.g = var7;
      }

      public BlockPos a() {
         return this.a;
      }

      public EnumFacing b() {
         return this.b;
      }

      public EnumFacing c() {
         return this.c;
      }

      public int d() {
         return this.e;
      }

      public int e() {
         return this.f;
      }

      public BlockWorldState a(int var1, int var2, int var3) {
         return (BlockWorldState)this.d.getUnchecked(BlockPattern.a(this.a, this.b(), this.c(), var1, var2, var3));
      }

      @Override
      public String toString() {
         return Objects.toStringHelper(this).add("up", this.c).add("forwards", this.b).add("frontTopLeft", this.a).toString();
      }
   }
}
