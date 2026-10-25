package com.metrofico.pulsar.api.map;

import java.util.ArrayList;
import java.util.List;

public final class MapView {
   private MapDefinition map;
   private boolean auto;
   private final int viewX;
   private final int viewY;
   private final int viewW;
   private final int viewH;
   private double zoom;
   private double centerX;
   private double centerZ;

   public MapView(MapDefinition map, int viewX, int viewY, int viewW, int viewH) {
      this.map = map;
      this.viewX = viewX;
      this.viewY = viewY;
      this.viewW = viewW;
      this.viewH = viewH;
      this.centerX = (map.minX() + map.maxX()) / 2.0;
      this.centerZ = (map.minZ() + map.maxZ()) / 2.0;
      this.zoom = this.fitZoom();
   }

   public static MapView unbounded(MapDefinition map, int viewX, int viewY, int viewW, int viewH, double centerX, double centerZ, double zoom) {
      MapView v = new MapView(map, viewX, viewY, viewW, viewH);
      v.auto = true;
      v.centerX = centerX;
      v.centerZ = centerZ;
      v.zoom = zoom;
      return v;
   }

   public void map(MapDefinition map) {
      this.map = map;
   }

   public boolean auto() {
      return this.auto;
   }

   public double fitZoom() {
      return Math.min(this.viewW / this.map.width(), this.viewH / this.map.height());
   }

   public double zoom() {
      return this.zoom;
   }

   public void zoomBy(double factor, double aroundScreenX, double aroundScreenY) {
      double[] before = this.toWorld(aroundScreenX, aroundScreenY);
      this.zoom = this.auto
         ? Math.max(0.125, Math.min(16.0, this.zoom * factor))
         : Math.max(this.map.minZoom() * this.fitZoom(), Math.min(this.map.maxZoom() * this.fitZoom(), this.zoom * factor));
      double[] after = this.toWorld(aroundScreenX, aroundScreenY);
      this.centerX = this.centerX + (before[0] - after[0]);
      this.centerZ = this.centerZ + (before[1] - after[1]);
      this.clamp();
   }

   public void panPixels(double dx, double dy) {
      this.centerX = this.centerX - dx / this.zoom;
      this.centerZ = this.centerZ - dy / this.zoom;
      this.clamp();
   }

   public void centerOn(double worldX, double worldZ) {
      this.centerX = worldX;
      this.centerZ = worldZ;
      this.clamp();
   }

   private void clamp() {
      this.centerX = Math.max(this.map.minX(), Math.min(this.map.maxX(), this.centerX));
      this.centerZ = Math.max(this.map.minZ(), Math.min(this.map.maxZ(), this.centerZ));
   }

   public double[] toScreen(double worldX, double worldZ) {
      return new double[]{
         this.viewX + this.viewW / 2.0 + (worldX - this.centerX) * this.zoom, this.viewY + this.viewH / 2.0 + (worldZ - this.centerZ) * this.zoom
      };
   }

   public double[] toWorld(double screenX, double screenY) {
      return new double[]{
         this.centerX + (screenX - this.viewX - this.viewW / 2.0) / this.zoom, this.centerZ + (screenY - this.viewY - this.viewH / 2.0) / this.zoom
      };
   }

   public int[] imageRect() {
      double[] a = this.toScreen(this.map.minX(), this.map.minZ());
      double[] b = this.toScreen(this.map.maxX(), this.map.maxZ());
      return new int[]{(int)Math.floor(a[0]), (int)Math.floor(a[1]), (int)Math.ceil(b[0]), (int)Math.ceil(b[1])};
   }

   public boolean inView(double sx, double sy) {
      return sx >= this.viewX && sx < this.viewX + this.viewW && sy >= this.viewY && sy < this.viewY + this.viewH;
   }

   public static List<int[]> spans(List<double[]> screenPoints, int y) {
      List<Double> xs = new ArrayList<>();
      int n = screenPoints.size();
      double yc = y + 0.5;

      for (int i = 0; i < n; i++) {
         double[] p = screenPoints.get(i);
         double[] q = screenPoints.get((i + 1) % n);
         if (p[1] <= yc && q[1] > yc || q[1] <= yc && p[1] > yc) {
            xs.add(p[0] + (yc - p[1]) * (q[0] - p[0]) / (q[1] - p[1]));
         }
      }

      xs.sort(Double::compare);
      List<int[]> out = new ArrayList<>();

      for (int ix = 0; ix + 1 < xs.size(); ix += 2) {
         int x0 = (int)Math.round(xs.get(ix));
         int x1 = (int)Math.round(xs.get(ix + 1));
         if (x1 > x0) {
            out.add(new int[]{x0, x1});
         }
      }

      return out;
   }

   public List<double[]> project(List<double[]> worldPoints) {
      List<double[]> out = new ArrayList<>(worldPoints.size());

      for (double[] p : worldPoints) {
         out.add(this.toScreen(p[0], p[1]));
      }

      return out;
   }

   public int viewX() {
      return this.viewX;
   }

   public int viewY() {
      return this.viewY;
   }

   public int viewW() {
      return this.viewW;
   }

   public int viewH() {
      return this.viewH;
   }
}
