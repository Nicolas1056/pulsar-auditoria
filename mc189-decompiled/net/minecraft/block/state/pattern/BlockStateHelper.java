package net.minecraft.block.state.pattern;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;

public class BlockStateHelper implements Predicate<IBlockState> {
   private final BlockState a;
   private final Map<IProperty, Predicate> b = Maps.newHashMap();

   private BlockStateHelper(BlockState var1) {
      this.a = var1;
   }

   public static BlockStateHelper a(Block var0) {
      return new BlockStateHelper(var0.P());
   }

   public boolean a(IBlockState var1) {
      if (var1 != null && var1.c().equals(this.a.c())) {
         for (Entry var3 : this.b.entrySet()) {
            Comparable var4 = var1.b((IProperty<Comparable>)var3.getKey());
            if (!((Predicate)var3.getValue()).apply(var4)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public <V extends Comparable<V>> BlockStateHelper a(IProperty<V> var1, Predicate<? extends V> var2) {
      if (!this.a.d().contains(var1)) {
         throw new IllegalArgumentException(this.a + " cannot support property " + var1);
      } else {
         this.b.put(var1, var2);
         return this;
      }
   }
}
