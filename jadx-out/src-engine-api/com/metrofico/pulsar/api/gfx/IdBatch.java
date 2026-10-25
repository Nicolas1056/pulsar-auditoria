package com.metrofico.pulsar.api.gfx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.function.Consumer;

public final class IdBatch {
   private IntBuffer ids;

   public IdBatch(int initialCapacity) {
      this.ids = direct(Math.max(16, initialCapacity));
   }

   private static IntBuffer direct(int capacity) {
      return ByteBuffer.allocateDirect(capacity * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
   }

   public void add(int id) {
      if (!this.ids.hasRemaining()) {
         IntBuffer bigger = direct(this.ids.capacity() * 2);
         this.ids.flip();
         bigger.put(this.ids);
         this.ids = bigger;
      }

      this.ids.put(id);
   }

   public int size() {
      return this.ids.position();
   }

   public void drainTo(Consumer<IntBuffer> delete) {
      this.drainTo(Integer.MAX_VALUE, delete);
   }

   public void drainTo(int max, Consumer<IntBuffer> delete) {
      int total = this.ids.position();
      if (total != 0 && max > 0) {
         int n = Math.min(max, total);
         this.ids.position(0).limit(n);

         try {
            delete.accept(this.ids.slice());
         } finally {
            this.ids.limit(total).position(n);
            this.ids.compact();
         }
      }
   }

   int capacity() {
      return this.ids.capacity();
   }
}
