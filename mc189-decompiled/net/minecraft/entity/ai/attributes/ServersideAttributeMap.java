package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.server.management.LowerStringMap;

public class ServersideAttributeMap extends BaseAttributeMap {
   private final Set<IAttributeInstance> e = Sets.newHashSet();
   protected final Map<String, IAttributeInstance> d = new LowerStringMap<>();

   public ModifiableAttributeInstance e(IAttribute var1) {
      return (ModifiableAttributeInstance)super.a(var1);
   }

   public ModifiableAttributeInstance b(String var1) {
      IAttributeInstance var2 = super.a(var1);
      if (var2 == null) {
         var2 = this.d.get(var1);
      }

      return (ModifiableAttributeInstance)var2;
   }

   @Override
   public IAttributeInstance b(IAttribute var1) {
      IAttributeInstance var2 = super.b(var1);
      if (var1 instanceof RangedAttribute && ((RangedAttribute)var1).g() != null) {
         this.d.put(((RangedAttribute)var1).g(), var2);
      }

      return var2;
   }

   @Override
   protected IAttributeInstance c(IAttribute var1) {
      return new ModifiableAttributeInstance(this, var1);
   }

   @Override
   public void a(IAttributeInstance var1) {
      if (var1.a().c()) {
         this.e.add(var1);
      }

      for (IAttribute var3 : this.c.get(var1.a())) {
         ModifiableAttributeInstance var4 = this.e(var3);
         if (var4 != null) {
            var4.f();
         }
      }
   }

   public Set<IAttributeInstance> b() {
      return this.e;
   }

   public Collection<IAttributeInstance> c() {
      HashSet var1 = Sets.newHashSet();

      for (IAttributeInstance var3 : this.a()) {
         if (var3.a().c()) {
            var1.add(var3);
         }
      }

      return var1;
   }
}
