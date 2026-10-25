package net.minecraft.client.renderer;

import net.minecraft.util.EnumWorldBlockLayer;

public class RegionRenderCacheBuilder {
   private final WorldRenderer[] a = new WorldRenderer[EnumWorldBlockLayer.values().length];

   public RegionRenderCacheBuilder() {
      this.a[EnumWorldBlockLayer.a.ordinal()] = new WorldRenderer(2097152);
      this.a[EnumWorldBlockLayer.c.ordinal()] = new WorldRenderer(131072);
      this.a[EnumWorldBlockLayer.b.ordinal()] = new WorldRenderer(131072);
      this.a[EnumWorldBlockLayer.d.ordinal()] = new WorldRenderer(262144);
   }

   public WorldRenderer a(EnumWorldBlockLayer var1) {
      return this.a[var1.ordinal()];
   }

   public WorldRenderer a(int var1) {
      return this.a[var1];
   }
}
