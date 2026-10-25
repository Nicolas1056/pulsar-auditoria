package com.metrofico.pulsar.bootstrap.transform;

public interface ClassTransformer {
   String name();

   byte[] transform(String var1, byte[] var2);
}
