package net.minecraft.item;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class ItemAxe extends ItemTool {
   private static final Set<Block> c = Sets.newHashSet(
      new Block[]{Blocks.f, Blocks.X, Blocks.r, Blocks.s, Blocks.ae, Blocks.aU, Blocks.aZ, Blocks.bk, Blocks.au}
   );

   protected ItemAxe(Item.ToolMaterial var1) {
      super(3.0F, var1, c);
   }

   @Override
   public float a(ItemStack var1, Block var2) {
      return var2.t() != Material.d && var2.t() != Material.k && var2.t() != Material.l ? super.a(var1, var2) : this.a;
   }
}
