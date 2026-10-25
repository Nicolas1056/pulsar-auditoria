package net.minecraft.realms;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;

public class RealmsVertexFormat {
   private VertexFormat v;

   public RealmsVertexFormat(VertexFormat var1) {
      this.v = var1;
   }

   public RealmsVertexFormat from(VertexFormat var1) {
      this.v = var1;
      return this;
   }

   public VertexFormat getVertexFormat() {
      return this.v;
   }

   public void clear() {
      this.v.a();
   }

   public int getUvOffset(int var1) {
      return this.v.b(var1);
   }

   public int getElementCount() {
      return this.v.i();
   }

   public boolean hasColor() {
      return this.v.d();
   }

   public boolean hasUv(int var1) {
      return this.v.a(var1);
   }

   public RealmsVertexFormatElement getElement(int var1) {
      return new RealmsVertexFormatElement(this.v.c(var1));
   }

   public RealmsVertexFormat addElement(RealmsVertexFormatElement var1) {
      return this.from(this.v.a(var1.getVertexFormatElement()));
   }

   public int getColorOffset() {
      return this.v.e();
   }

   public List<RealmsVertexFormatElement> getElements() {
      ArrayList var1 = new ArrayList();

      for (VertexFormatElement var3 : this.v.h()) {
         var1.add(new RealmsVertexFormatElement(var3));
      }

      return var1;
   }

   public boolean hasNormal() {
      return this.v.b();
   }

   public int getVertexSize() {
      return this.v.g();
   }

   public int getOffset(int var1) {
      return this.v.d(var1);
   }

   public int getNormalOffset() {
      return this.v.c();
   }

   public int getIntegerSize() {
      return this.v.f();
   }

   @Override
   public boolean equals(Object var1) {
      return this.v.equals(var1);
   }

   @Override
   public int hashCode() {
      return this.v.hashCode();
   }

   @Override
   public String toString() {
      return this.v.toString();
   }
}
