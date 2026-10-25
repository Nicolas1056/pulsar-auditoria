package com.metrofico.pulsar.engine.v189.emote;

import java.io.InputStream;

public final class EmoteResource189 implements bnh {
   private final jy id;
   private final InputStream in;

   public EmoteResource189(jy id, InputStream in) {
      this.id = id;
      this.in = in;
   }

   public jy a() {
      return this.id;
   }

   public InputStream b() {
      return this.in;
   }

   public boolean c() {
      return false;
   }

   public <T extends bnw> T a(String key) {
      return null;
   }

   public String d() {
      return "pulsar-emote";
   }
}
