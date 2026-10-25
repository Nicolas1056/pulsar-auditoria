package com.metrofico.pulsar.bootstrap.transform;

public final class ClassTransformException extends RuntimeException {
   private final String transformer;
   private final String className;

   public ClassTransformException(String transformer, String className, Throwable cause) {
      super("Transformer '" + transformer + "' failed on class " + className + ": " + cause, cause);
      this.transformer = transformer;
      this.className = className;
   }

   public String transformer() {
      return this.transformer;
   }

   public String className() {
      return this.className;
   }
}
