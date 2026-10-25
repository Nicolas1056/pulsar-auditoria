package net.minecraft.client.resources.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IRegistry;
import net.minecraft.util.RegistrySimple;

public class IMetadataSerializer {
   private final IRegistry<String, IMetadataSerializer.Registration<? extends IMetadataSection>> a = new RegistrySimple<>();
   private final GsonBuilder b = new GsonBuilder();
   private Gson c;

   public IMetadataSerializer() {
      this.b.registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer());
      this.b.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer());
      this.b.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
   }

   public <T extends IMetadataSection> void a(IMetadataSectionSerializer<T> var1, Class<T> var2) {
      this.a.a(var1.a(), new IMetadataSerializer.Registration<>(var1, var2));
      this.b.registerTypeAdapter(var2, var1);
      this.c = null;
   }

   public <T extends IMetadataSection> T a(String var1, JsonObject var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("Metadata section name cannot be null");
      } else if (!var2.has(var1)) {
         return null;
      } else if (!var2.get(var1).isJsonObject()) {
         throw new IllegalArgumentException("Invalid metadata for '" + var1 + "' - expected object, found " + var2.get(var1));
      } else {
         IMetadataSerializer.Registration var3 = this.a.a(var1);
         if (var3 == null) {
            throw new IllegalArgumentException("Don't know how to handle metadata section '" + var1 + "'");
         } else {
            return (T)this.a().fromJson(var2.getAsJsonObject(var1), var3.b);
         }
      }
   }

   private Gson a() {
      if (this.c == null) {
         this.c = this.b.create();
      }

      return this.c;
   }

   class Registration<T extends IMetadataSection> {
      final IMetadataSectionSerializer<T> a;
      final Class<T> b;

      private Registration(IMetadataSectionSerializer<T> var2, Class<T> var3) {
         this.a = var2;
         this.b = var3;
      }
   }
}
