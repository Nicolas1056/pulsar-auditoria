package net.minecraft.block.state;

import com.google.common.base.Predicate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockWorldState {
   private final World a;
   private final BlockPos b;
   private final boolean c;
   private IBlockState d;
   private TileEntity e;
   private boolean f;

   public BlockWorldState(World var1, BlockPos var2, boolean var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public IBlockState a() {
      if (this.d == null && (this.c || this.a.e(this.b))) {
         this.d = this.a.p(this.b);
      }

      return this.d;
   }

   public TileEntity b() {
      if (this.e == null && !this.f) {
         this.e = this.a.s(this.b);
         this.f = true;
      }

      return this.e;
   }

   public BlockPos d() {
      return this.b;
   }

   public static Predicate<BlockWorldState> a(final Predicate<IBlockState> var0) {
      return new Predicate<BlockWorldState>() {
         public boolean a(BlockWorldState var1) {
            return var1 != null && var0.apply(var1.a());
         }
      };
   }
}
