package com.metrofico.pulsar.engine.v189.perf;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Arrays;
import org.lwjgl.opengl.GL15;

final class RawStream189 {
   int[] data;
   int size;
   private ByteBuffer nativeBytes;
   private IntBuffer nativeInts;
   private int vbo;

   int vbo() {
      return this.vbo;
   }

   RawStream189(int capacityInts) {
      this.data = new int[capacityInts];
   }

   int reserve(int more) {
      if (this.size + more > this.data.length) {
         this.data = Arrays.copyOf(this.data, Math.max(this.data.length * 2, this.size + more));
      }

      int at = this.size;
      this.size += more;
      return at;
   }

   int bytes() {
      return this.size * 4;
   }

   void bindAndUpload() {
      if (this.nativeInts == null || this.nativeInts.capacity() < this.size) {
         this.nativeBytes = ByteBuffer.allocateDirect(Math.max(this.size, this.data.length) * 4).order(ByteOrder.nativeOrder());
         this.nativeInts = this.nativeBytes.asIntBuffer();
      }

      this.nativeInts.clear();
      this.nativeInts.put(this.data, 0, this.size);
      this.nativeBytes.position(0).limit(this.size * 4);
      if (this.vbo == 0) {
         this.vbo = bqs.e();
      }

      bqs.g(bqs.R, this.vbo);
      GL15.glBufferData(bqs.R, this.nativeBytes, 35040);
      this.nativeBytes.clear();
   }

   ByteBuffer clientBytes() {
      if (this.nativeInts == null || this.nativeInts.capacity() < this.size) {
         this.nativeBytes = ByteBuffer.allocateDirect(Math.max(this.size, this.data.length) * 4).order(ByteOrder.nativeOrder());
         this.nativeInts = this.nativeBytes.asIntBuffer();
      }

      this.nativeInts.clear();
      this.nativeInts.put(this.data, 0, this.size);
      this.nativeBytes.position(0).limit(this.size * 4);
      bqs.g(bqs.R, 0);
      return this.nativeBytes;
   }

   void reset() {
      this.size = 0;
   }
}
