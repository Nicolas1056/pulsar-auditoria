package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.util.StringTranslate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanguageManager implements IResourceManagerReloadListener {
   private static final Logger b = LogManager.getLogger();
   private final IMetadataSerializer c;
   private String d;
   protected static final Locale a = new Locale();
   private Map<String, Language> e = Maps.newHashMap();

   public LanguageManager(IMetadataSerializer var1, String var2) {
      this.c = var1;
      this.d = var2;
      I18n.a(a);
   }

   public void a(List<IResourcePack> var1) {
      this.e.clear();

      for (IResourcePack var3 : var1) {
         try {
            LanguageMetadataSection var4 = var3.a(this.c, "language");
            if (var4 != null) {
               for (Language var6 : var4.a()) {
                  if (!this.e.containsKey(var6.a())) {
                     this.e.put(var6.a(), var6);
                  }
               }
            }
         } catch (RuntimeException var7) {
            b.warn("Unable to parse metadata section of resourcepack: " + var3.b(), var7);
         } catch (IOException var8) {
            b.warn("Unable to parse metadata section of resourcepack: " + var3.b(), var8);
         }
      }
   }

   @Override
   public void a(IResourceManager var1) {
      ArrayList var2 = Lists.newArrayList(new String[]{"en_US"});
      if (!"en_US".equals(this.d)) {
         var2.add(this.d);
      }

      a.a(var1, var2);
      StringTranslate.a(a.a);
   }

   public boolean a() {
      return a.a();
   }

   public boolean b() {
      return this.c() != null && this.c().b();
   }

   public void a(Language var1) {
      this.d = var1.a();
   }

   public Language c() {
      return this.e.containsKey(this.d) ? this.e.get(this.d) : this.e.get("en_US");
   }

   public SortedSet<Language> d() {
      return Sets.newTreeSet(this.e.values());
   }
}
