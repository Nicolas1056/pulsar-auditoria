package net.minecraft.item;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class ItemPickaxe extends ItemTool {
   private static final Set<Block> c = Sets.newHashSet(
      new Block[]{
         Blocks.cs,
         Blocks.q,
         Blocks.e,
         Blocks.E,
         Blocks.ah,
         Blocks.ag,
         Blocks.T,
         Blocks.D,
         Blocks.R,
         Blocks.o,
         Blocks.aI,
         Blocks.S,
         Blocks.p,
         Blocks.y,
         Blocks.x,
         Blocks.aD,
         Blocks.Y,
         Blocks.aV,
         Blocks.cB,
         Blocks.av,
         Blocks.aC,
         Blocks.A,
         Blocks.cM,
         Blocks.b,
         Blocks.U
      }
   );

   protected ItemPickaxe(Item.ToolMaterial var1) {
      super(2.0F, var1, c);
   }

   @Override
   public boolean b(Block var1) {
      if (var1 == Blocks.Z) {
         return this.b.d() == 3;
      } else if (var1 == Blocks.ah || var1 == Blocks.ag) {
         return this.b.d() >= 2;
      } else if (var1 == Blocks.bP || var1 == Blocks.bT) {
         return this.b.d() >= 2;
      } else if (var1 == Blocks.R || var1 == Blocks.o) {
         return this.b.d() >= 2;
      } else if (var1 == Blocks.S || var1 == Blocks.p) {
         return this.b.d() >= 1;
      } else if (var1 == Blocks.y || var1 == Blocks.x) {
         return this.b.d() >= 1;
      } else if (var1 != Blocks.aC && var1 != Blocks.aD) {
         if (var1.t() == Material.e) {
            return true;
         } else {
            return var1.t() == Material.f ? true : var1.t() == Material.g;
         }
      } else {
         return this.b.d() >= 2;
      }
   }

   @Override
   public float a(ItemStack var1, Block var2) {
      return var2.t() != Material.f && var2.t() != Material.g && var2.t() != Material.e ? super.a(var1, var2) : this.a;
   }
}
