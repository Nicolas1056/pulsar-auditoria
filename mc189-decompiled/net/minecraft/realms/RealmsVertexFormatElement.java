package net.minecraft.realms;

import net.minecraft.client.renderer.vertex.VertexFormatElement;

public class RealmsVertexFormatElement {
   private VertexFormatElement v;

   public RealmsVertexFormatElement(VertexFormatElement var1) {
      this.v = var1;
   }

   public VertexFormatElement getVertexFormatElement() {
      return this.v;
   }

   public boolean isPosition() {
      return this.v.f();
   }

   public int getIndex() {
      return this.v.d();
   }

   public int getByteSize() {
      return this.v.e();
   }

   public int getCount() {
      return this.v.c();
   }

   @Override
   public int hashCode() {
      return this.v.hashCode();
   }

   @Override
   public boolean equals(Object var1) {
      return this.v.equals(var1);
   }

   @Override
   public String toString() {
      return this.v.toString();
   }
}
