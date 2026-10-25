package net.minecraft.block.state;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;

public interface IBlockState {
   Collection<IProperty> a();

   <T extends Comparable<T>> T b(IProperty<T> var1);

   <T extends Comparable<T>, V extends T> IBlockState a(IProperty<T> var1, V var2);

   <T extends Comparable<T>> IBlockState a(IProperty<T> var1);

   ImmutableMap<IProperty, Comparable> b();

   Block c();
}
