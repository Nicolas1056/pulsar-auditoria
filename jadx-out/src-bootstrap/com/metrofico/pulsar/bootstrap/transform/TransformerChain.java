package com.metrofico.pulsar.bootstrap.transform;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class TransformerChain {
   private final List<ClassTransformer> transformers = new CopyOnWriteArrayList<>();

   public void addFirst(ClassTransformer transformer) {
      this.transformers.add(0, transformer);
   }

   public void addLast(ClassTransformer transformer) {
      this.transformers.add(transformer);
   }

   public List<ClassTransformer> transformers() {
      return List.copyOf(this.transformers);
   }

   public byte[] transform(String className, byte[] bytes) {
      byte[] current = bytes;

      for (ClassTransformer transformer : this.transformers) {
         try {
            byte[] result = transformer.transform(className, current);
            if (result == null) {
               throw new IllegalStateException("returned null bytecode");
            }

            current = result;
         } catch (ClassTransformException var7) {
            throw var7;
         } catch (Error | RuntimeException var8) {
            throw new ClassTransformException(transformer.name(), className, var8);
         }
      }

      return current;
   }
}
