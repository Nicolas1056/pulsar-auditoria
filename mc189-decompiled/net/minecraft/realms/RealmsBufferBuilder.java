package net.minecraft.realms;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class RealmsBufferBuilder {
   private WorldRenderer b;

   public RealmsBufferBuilder(WorldRenderer var1) {
      this.b = var1;
   }

   public RealmsBufferBuilder from(WorldRenderer var1) {
      this.b = var1;
      return this;
   }

   public void sortQuads(float var1, float var2, float var3) {
      this.b.a(var1, var2, var3);
   }

   public void fixupQuadColor(int var1) {
      this.b.a(var1);
   }

   public ByteBuffer getBuffer() {
      return this.b.f();
   }

   public void postNormal(float var1, float var2, float var3) {
      this.b.b(var1, var2, var3);
   }

   public int getDrawMode() {
      return this.b.i();
   }

   public void offset(double var1, double var3, double var5) {
      this.b.c(var1, var3, var5);
   }

   public void restoreState(WorldRenderer.State var1) {
      this.b.a(var1);
   }

   public void endVertex() {
      this.b.d();
   }

   public RealmsBufferBuilder normal(float var1, float var2, float var3) {
      return this.from(this.b.c(var1, var2, var3));
   }

   public void end() {
      this.b.e();
   }

   public void begin(int var1, VertexFormat var2) {
      this.b.a(var1, var2);
   }

   public RealmsBufferBuilder color(int var1, int var2, int var3, int var4) {
      return this.from(this.b.b(var1, var2, var3, var4));
   }

   public void faceTex2(int var1, int var2, int var3, int var4) {
      this.b.a(var1, var2, var3, var4);
   }

   public void postProcessFacePosition(double var1, double var3, double var5) {
      this.b.a(var1, var3, var5);
   }

   public void fixupVertexColor(float var1, float var2, float var3, int var4) {
      this.b.b(var1, var2, var3, var4);
   }

   public RealmsBufferBuilder color(float var1, float var2, float var3, float var4) {
      return this.from(this.b.a(var1, var2, var3, var4));
   }

   public RealmsVertexFormat getVertexFormat() {
      return new RealmsVertexFormat(this.b.g());
   }

   public void faceTint(float var1, float var2, float var3, int var4) {
      this.b.a(var1, var2, var3, var4);
   }

   public RealmsBufferBuilder tex2(int var1, int var2) {
      return this.from(this.b.a(var1, var2));
   }

   public void putBulkData(int[] var1) {
      this.b.a(var1);
   }

   public RealmsBufferBuilder tex(double var1, double var3) {
      return this.from(this.b.a(var1, var3));
   }

   public int getVertexCount() {
      return this.b.h();
   }

   public void clear() {
      this.b.b();
   }

   public RealmsBufferBuilder vertex(double var1, double var3, double var5) {
      return this.from(this.b.b(var1, var3, var5));
   }

   public void fixupQuadColor(float var1, float var2, float var3) {
      this.b.d(var1, var2, var3);
   }

   public void noColor() {
      this.b.c();
   }
}
