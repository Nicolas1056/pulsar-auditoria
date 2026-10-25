package net.minecraft.client.renderer.block.statemap;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class BlockStateMapper {
   private Map<Block, IStateMapper> a = Maps.newIdentityHashMap();
   private Set<Block> b = Sets.newIdentityHashSet();

   public void a(Block var1, IStateMapper var2) {
      this.a.put(var1, var2);
   }

   public void a(Block... var1) {
      Collections.addAll(this.b, var1);
   }

   public Map<IBlockState, ModelResourceLocation> a() {
      IdentityHashMap var1 = Maps.newIdentityHashMap();

      for (Block var3 : Block.c) {
         if (!this.b.contains(var3)) {
            var1.putAll(((IStateMapper)Objects.firstNonNull(this.a.get(var3), new DefaultStateMapper())).a(var3));
         }
      }

      return var1;
   }
}
