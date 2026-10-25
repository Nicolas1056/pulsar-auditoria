package net.minecraft.client.util;

import com.google.common.collect.Lists;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class JsonException extends IOException {
   private final List<JsonException.Entry> a = Lists.newArrayList();
   private final String b;

   public JsonException(String var1) {
      this.a.add(new JsonException.Entry());
      this.b = var1;
   }

   public JsonException(String var1, Throwable var2) {
      super(var2);
      this.a.add(new JsonException.Entry());
      this.b = var1;
   }

   public void a(String var1) {
      this.a.get(0).a(var1);
   }

   public void b(String var1) {
      this.a.get(0).a = var1;
      this.a.add(0, new JsonException.Entry());
   }

   @Override
   public String getMessage() {
      return "Invalid " + this.a.get(this.a.size() - 1).toString() + ": " + this.b;
   }

   public static JsonException a(Exception var0) {
      if (var0 instanceof JsonException) {
         return (JsonException)var0;
      } else {
         String var1 = var0.getMessage();
         if (var0 instanceof FileNotFoundException) {
            var1 = "File not found";
         }

         return new JsonException(var1, var0);
      }
   }

   public static class Entry {
      private String a = null;
      private final List<String> b = Lists.newArrayList();

      private Entry() {
      }

      private void a(String var1) {
         this.b.add(0, var1);
      }

      public String b() {
         return StringUtils.join(this.b, "->");
      }

      @Override
      public String toString() {
         if (this.a != null) {
            return !this.b.isEmpty() ? this.a + " " + this.b() : this.a;
         } else {
            return !this.b.isEmpty() ? "(Unknown file) " + this.b() : "(Unknown file)";
         }
      }
   }
}
