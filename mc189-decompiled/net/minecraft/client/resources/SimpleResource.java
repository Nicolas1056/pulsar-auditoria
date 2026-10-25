package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class SimpleResource implements IResource {
   private final Map<String, IMetadataSection> a = Maps.newHashMap();
   private final String b;
   private final ResourceLocation c;
   private final InputStream d;
   private final InputStream e;
   private final IMetadataSerializer f;
   private boolean g;
   private JsonObject h;

   public SimpleResource(String var1, ResourceLocation var2, InputStream var3, InputStream var4, IMetadataSerializer var5) {
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
   }

   @Override
   public ResourceLocation a() {
      return this.c;
   }

   @Override
   public InputStream b() {
      return this.d;
   }

   @Override
   public boolean c() {
      return this.e != null;
   }

   @Override
   public <T extends IMetadataSection> T a(String var1) {
      if (!this.c()) {
         return null;
      } else {
         if (this.h == null && !this.g) {
            this.g = true;
            BufferedReader var2 = null;

            try {
               var2 = new BufferedReader(new InputStreamReader(this.e));
               this.h = new JsonParser().parse(var2).getAsJsonObject();
            } finally {
               IOUtils.closeQuietly(var2);
            }
         }

         IMetadataSection var6 = this.a.get(var1);
         if (var6 == null) {
            var6 = this.f.a(var1, this.h);
         }

         return (T)var6;
      }
   }

   @Override
   public String d() {
      return this.b;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof SimpleResource)) {
         return false;
      } else {
         SimpleResource var2 = (SimpleResource)var1;
         if (this.c != null ? this.c.equals(var2.c) : var2.c == null) {
            return this.b != null ? this.b.equals(var2.b) : var2.b == null;
         } else {
            return false;
         }
      }
   }

   @Override
   public int hashCode() {
      int var1 = this.b != null ? this.b.hashCode() : 0;
      return 31 * var1 + (this.c != null ? this.c.hashCode() : 0);
   }
}
