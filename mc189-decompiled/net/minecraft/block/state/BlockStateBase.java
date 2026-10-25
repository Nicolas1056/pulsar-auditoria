package net.minecraft.block.state;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;

public abstract class BlockStateBase implements IBlockState {
   private static final Joiner a = Joiner.on(',');
   private static final Function<Entry<IProperty, Comparable>, String> b = new Function<Entry<IProperty, Comparable>, String>() {
      public String a(Entry<IProperty, Comparable> var1) {
         if (var1 == null) {
            return "<NULL>";
         } else {
            IProperty var2 = (IProperty)var1.getKey();
            return var2.a() + "=" + var2.a((Comparable)var1.getValue());
         }
      }
   };

   @Override
   public <T extends Comparable<T>> IBlockState a(IProperty<T> var1) {
      return this.a(var1, a(var1.c(), this.b(var1)));
   }

   protected static <T> T a(Collection<T> var0, T var1) {
      Iterator var2 = var0.iterator();

      while (var2.hasNext()) {
         if (var2.next().equals(var1)) {
            if (var2.hasNext()) {
               return (T)var2.next();
            }

            return (T)var0.iterator().next();
         }
      }

      return (T)var2.next();
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(Block.c.c(this.c()));
      if (!this.b().isEmpty()) {
         var1.append("[");
         a.appendTo(var1, Iterables.transform(this.b().entrySet(), b));
         var1.append("]");
      }

      return var1.toString();
   }
}
