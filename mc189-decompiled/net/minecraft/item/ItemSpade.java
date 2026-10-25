package net.minecraft.item;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ItemSpade extends ItemTool {
   private static final Set<Block> c = Sets.newHashSet(
      new Block[]{Blocks.aL, Blocks.d, Blocks.ak, Blocks.c, Blocks.n, Blocks.bw, Blocks.m, Blocks.aJ, Blocks.aH, Blocks.aW}
   );

   public ItemSpade(Item.ToolMaterial var1) {
      super(1.0F, var1, c);
   }

   @Override
   public boolean b(Block var1) {
      return var1 == Blocks.aH ? true : var1 == Blocks.aJ;
   }
}
