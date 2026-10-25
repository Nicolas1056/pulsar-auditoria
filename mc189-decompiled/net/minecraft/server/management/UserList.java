package net.minecraft.server.management;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserList<K, V extends UserListEntry<K>> {
   protected static final Logger a = LogManager.getLogger();
   protected final Gson b;
   private final File c;
   private final Map<String, V> d = Maps.newHashMap();
   private boolean e = true;
   private static final ParameterizedType f = new ParameterizedType() {
      @Override
      public Type[] getActualTypeArguments() {
         return new Type[]{UserListEntry.class};
      }

      @Override
      public Type getRawType() {
         return List.class;
      }

      @Override
      public Type getOwnerType() {
         return null;
      }
   };

   public UserList(File var1) {
      this.c = var1;
      GsonBuilder var2 = new GsonBuilder().setPrettyPrinting();
      var2.registerTypeHierarchyAdapter(UserListEntry.class, new UserList.Serializer());
      this.b = var2.create();
   }

   public boolean b() {
      return this.e;
   }

   public void a(boolean var1) {
      this.e = var1;
   }

   public void a(V var1) {
      this.d.put(this.a((K)var1.f()), (V)var1);

      try {
         this.f();
      } catch (IOException var3) {
         a.warn("Could not save the list after adding a user.", var3);
      }
   }

   public V b(K var1) {
      this.h();
      return this.d.get(this.a((K)var1));
   }

   public void c(K var1) {
      this.d.remove(this.a((K)var1));

      try {
         this.f();
      } catch (IOException var3) {
         a.warn("Could not save the list after removing a user.", var3);
      }
   }

   public String[] a() {
      return this.d.keySet().toArray(new String[this.d.size()]);
   }

   protected String a(K var1) {
      return var1.toString();
   }

   protected boolean d(K var1) {
      return this.d.containsKey(this.a((K)var1));
   }

   private void h() {
      ArrayList var1 = Lists.newArrayList();

      for (UserListEntry var3 : this.d.values()) {
         if (var3.e()) {
            var1.add(var3.f());
         }
      }

      for (Object var5 : var1) {
         this.d.remove(var5);
      }
   }

   protected UserListEntry<K> a(JsonObject var1) {
      return new UserListEntry<>(null, var1);
   }

   protected Map<String, V> e() {
      return this.d;
   }

   public void f() throws IOException {
      Collection var1 = this.d.values();
      String var2 = this.b.toJson(var1);
      BufferedWriter var3 = null;

      try {
         var3 = Files.newWriter(this.c, Charsets.UTF_8);
         var3.write(var2);
      } finally {
         IOUtils.closeQuietly(var3);
      }
   }

   class Serializer implements JsonDeserializer<UserListEntry<K>>, JsonSerializer<UserListEntry<K>> {
      private Serializer() {
      }

      public JsonElement a(UserListEntry<K> var1, Type var2, JsonSerializationContext var3) {
         JsonObject var4 = new JsonObject();
         var1.a(var4);
         return var4;
      }

      public UserListEntry<K> a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (var1.isJsonObject()) {
            JsonObject var4 = var1.getAsJsonObject();
            return UserList.this.a(var4);
         } else {
            return null;
         }
      }
   }
}
