package net.minecraft.block.properties;

import java.util.Collection;

public interface IProperty<T extends Comparable<T>> {
   String a();

   Collection<T> c();

   Class<T> b();

   String a(T var1);
}
