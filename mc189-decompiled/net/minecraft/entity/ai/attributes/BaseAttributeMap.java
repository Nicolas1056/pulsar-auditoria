package net.minecraft.entity.ai.attributes;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.management.LowerStringMap;

public abstract class BaseAttributeMap {
   protected final Map<IAttribute, IAttributeInstance> a = Maps.newHashMap();
   protected final Map<String, IAttributeInstance> b = new LowerStringMap<>();
   protected final Multimap<IAttribute, IAttribute> c = HashMultimap.create();

   public IAttributeInstance a(IAttribute var1) {
      return this.a.get(var1);
   }

   public IAttributeInstance a(String var1) {
      return this.b.get(var1);
   }

   public IAttributeInstance b(IAttribute var1) {
      if (this.b.containsKey(var1.a())) {
         throw new IllegalArgumentException("Attribute is already registered!");
      } else {
         IAttributeInstance var2 = this.c(var1);
         this.b.put(var1.a(), var2);
         this.a.put(var1, var2);

         for (IAttribute var3 = var1.d(); var3 != null; var3 = var3.d()) {
            this.c.put(var3, var1);
         }

         return var2;
      }
   }

   protected abstract IAttributeInstance c(IAttribute var1);

   public Collection<IAttributeInstance> a() {
      return this.b.values();
   }

   public void a(IAttributeInstance var1) {
   }

   public void a(Multimap<String, AttributeModifier> var1) {
      for (Entry var3 : var1.entries()) {
         IAttributeInstance var4 = this.a((String)var3.getKey());
         if (var4 != null) {
            var4.c((AttributeModifier)var3.getValue());
         }
      }
   }

   public void b(Multimap<String, AttributeModifier> var1) {
      for (Entry var3 : var1.entries()) {
         IAttributeInstance var4 = this.a((String)var3.getKey());
         if (var4 != null) {
            var4.c((AttributeModifier)var3.getValue());
            var4.b((AttributeModifier)var3.getValue());
         }
      }
   }
}
