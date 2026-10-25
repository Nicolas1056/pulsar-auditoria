package net.minecraft.block.state.pattern;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class BlockHelper implements Predicate<IBlockState> {
   private final Block a;

   private BlockHelper(Block var1) {
      this.a = var1;
   }

   public static BlockHelper a(Block var0) {
      return new BlockHelper(var0);
   }

   public boolean a(IBlockState var1) {
      return var1 != null && var1.c() == this.a;
   }
}
