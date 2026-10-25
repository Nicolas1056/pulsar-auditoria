package net.minecraft.block.state;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.util.Cartesian;
import net.minecraft.util.MapPopulator;

public class BlockState {
   private static final Joiner a = Joiner.on(", ");
   private static final Function<IProperty, String> b = new Function<IProperty, String>() {
      public String a(IProperty var1) {
         return var1 == null ? "<NULL>" : var1.a();
      }
   };
   private final Block c;
   private final ImmutableList<IProperty> d;
   private final ImmutableList<IBlockState> e;

   public BlockState(Block var1, IProperty... var2) {
      this.c = var1;
      Arrays.sort(var2, new Comparator<IProperty>() {
         public int a(IProperty var1, IProperty var2x) {
            return var1.a().compareTo(var2x.a());
         }
      });
      this.d = ImmutableList.copyOf(var2);
      LinkedHashMap var3 = Maps.newLinkedHashMap();
      ArrayList var4 = Lists.newArrayList();

      for (List var7 : Cartesian.a(this.e())) {
         Map var8 = MapPopulator.b(this.d, var7);
         BlockState.StateImplementation var9 = new BlockState.StateImplementation(var1, ImmutableMap.copyOf(var8));
         var3.put(var8, var9);
         var4.add(var9);
      }

      for (BlockState.StateImplementation var11 : var4) {
         var11.a(var3);
      }

      this.e = ImmutableList.copyOf(var4);
   }

   public ImmutableList<IBlockState> a() {
      return this.e;
   }

   private List<Iterable<Comparable>> e() {
      ArrayList var1 = Lists.newArrayList();

      for (int var2 = 0; var2 < this.d.size(); var2++) {
         var1.add(((IProperty)this.d.get(var2)).c());
      }

      return var1;
   }

   public IBlockState b() {
      return (IBlockState)this.e.get(0);
   }

   public Block c() {
      return this.c;
   }

   public Collection<IProperty> d() {
      return this.d;
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this).add("block", Block.c.c(this.c)).add("properties", Iterables.transform(this.d, b)).toString();
   }

   static class StateImplementation extends BlockStateBase {
      private final Block a;
      private final ImmutableMap<IProperty, Comparable> b;
      private ImmutableTable<IProperty, Comparable, IBlockState> c;

      private StateImplementation(Block var1, ImmutableMap<IProperty, Comparable> var2) {
         this.a = var1;
         this.b = var2;
      }

      @Override
      public Collection<IProperty> a() {
         return Collections.unmodifiableCollection(this.b.keySet());
      }

      @Override
      public <T extends Comparable<T>> T b(IProperty<T> var1) {
         if (!this.b.containsKey(var1)) {
            throw new IllegalArgumentException("Cannot get property " + var1 + " as it does not exist in " + this.a.P());
         } else {
            return (T)var1.b().cast(this.b.get(var1));
         }
      }

      @Override
      public <T extends Comparable<T>, V extends T> IBlockState a(IProperty<T> var1, V var2) {
         if (!this.b.containsKey(var1)) {
            throw new IllegalArgumentException("Cannot set property " + var1 + " as it does not exist in " + this.a.P());
         } else if (!var1.c().contains(var2)) {
            throw new IllegalArgumentException(
               "Cannot set property " + var1 + " to " + var2 + " on block " + Block.c.c(this.a) + ", it is not an allowed value"
            );
         } else {
            return (IBlockState)(this.b.get(var1) == var2 ? this : (IBlockState)this.c.get(var1, var2));
         }
      }

      @Override
      public ImmutableMap<IProperty, Comparable> b() {
         return this.b;
      }

      @Override
      public Block c() {
         return this.a;
      }

      @Override
      public boolean equals(Object var1) {
         return this == var1;
      }

      @Override
      public int hashCode() {
         return this.b.hashCode();
      }

      public void a(Map<Map<IProperty, Comparable>, BlockState.StateImplementation> var1) {
         if (this.c != null) {
            throw new IllegalStateException();
         } else {
            HashBasedTable var2 = HashBasedTable.create();

            for (IProperty var4 : this.b.keySet()) {
               for (Comparable var6 : var4.c()) {
                  if (var6 != this.b.get(var4)) {
                     var2.put(var4, var6, var1.get(this.b(var4, var6)));
                  }
               }
            }

            this.c = ImmutableTable.copyOf(var2);
         }
      }

      private Map<IProperty, Comparable> b(IProperty var1, Comparable var2) {
         HashMap var3 = Maps.newHashMap(this.b);
         var3.put(var1, var2);
         return var3;
      }
   }
}
