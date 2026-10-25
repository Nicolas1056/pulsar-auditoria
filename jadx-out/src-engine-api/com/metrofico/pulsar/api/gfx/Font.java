package com.metrofico.pulsar.api.gfx;

public enum Font {
   REGULAR("font"),
   MEDIUM("font-medium"),
   BOLD("font-bold");

   private final String resource;

   private Font(String resource) {
      this.resource = resource;
   }

   public String resource() {
      return this.resource;
   }
}
