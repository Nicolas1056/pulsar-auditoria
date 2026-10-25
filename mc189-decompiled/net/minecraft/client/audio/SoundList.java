package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import java.util.List;

public class SoundList {
   private final List<SoundList.SoundEntry> a = Lists.newArrayList();
   private boolean b;
   private SoundCategory c;

   public List<SoundList.SoundEntry> a() {
      return this.a;
   }

   public boolean b() {
      return this.b;
   }

   public void a(boolean var1) {
      this.b = var1;
   }

   public SoundCategory c() {
      return this.c;
   }

   public void a(SoundCategory var1) {
      this.c = var1;
   }

   public static class SoundEntry {
      private String a;
      private float b = 1.0F;
      private float c = 1.0F;
      private int d = 1;
      private SoundList.SoundEntry.Type e = SoundList.SoundEntry.Type.a;
      private boolean f = false;

      public String a() {
         return this.a;
      }

      public void a(String var1) {
         this.a = var1;
      }

      public float b() {
         return this.b;
      }

      public void a(float var1) {
         this.b = var1;
      }

      public float c() {
         return this.c;
      }

      public void b(float var1) {
         this.c = var1;
      }

      public int d() {
         return this.d;
      }

      public void a(int var1) {
         this.d = var1;
      }

      public SoundList.SoundEntry.Type e() {
         return this.e;
      }

      public void a(SoundList.SoundEntry.Type var1) {
         this.e = var1;
      }

      public boolean f() {
         return this.f;
      }

      public void a(boolean var1) {
         this.f = var1;
      }

      public static enum Type {
         a("file"),
         b("event");

         private final String c;

         private Type(String var3) {
            this.c = var3;
         }

         public static SoundList.SoundEntry.Type a(String var0) {
            for (SoundList.SoundEntry.Type var4 : values()) {
               if (var4.c.equals(var0)) {
                  return var4;
               }
            }

            return null;
         }
      }
   }
}
