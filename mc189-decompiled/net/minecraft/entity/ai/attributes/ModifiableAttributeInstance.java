package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ModifiableAttributeInstance implements IAttributeInstance {
   private final BaseAttributeMap a;
   private final IAttribute b;
   private final Map<Integer, Set<AttributeModifier>> c = Maps.newHashMap();
   private final Map<String, Set<AttributeModifier>> d = Maps.newHashMap();
   private final Map<UUID, AttributeModifier> e = Maps.newHashMap();
   private double f;
   private boolean g = true;
   private double h;

   public ModifiableAttributeInstance(BaseAttributeMap var1, IAttribute var2) {
      this.a = var1;
      this.b = var2;
      this.f = var2.b();

      for (int var3 = 0; var3 < 3; var3++) {
         this.c.put(var3, Sets.newHashSet());
      }
   }

   @Override
   public IAttribute a() {
      return this.b;
   }

   @Override
   public double b() {
      return this.f;
   }

   @Override
   public void a(double var1) {
      if (var1 != this.b()) {
         this.f = var1;
         this.f();
      }
   }

   @Override
   public Collection<AttributeModifier> a(int var1) {
      return this.c.get(var1);
   }

   @Override
   public Collection<AttributeModifier> c() {
      HashSet var1 = Sets.newHashSet();

      for (int var2 = 0; var2 < 3; var2++) {
         var1.addAll(this.a(var2));
      }

      return var1;
   }

   @Override
   public AttributeModifier a(UUID var1) {
      return this.e.get(var1);
   }

   @Override
   public boolean a(AttributeModifier var1) {
      return this.e.get(var1.a()) != null;
   }

   @Override
   public void b(AttributeModifier var1) {
      if (this.a(var1.a()) != null) {
         throw new IllegalArgumentException("Modifier is already applied on this attribute!");
      } else {
         Object var2 = this.d.get(var1.b());
         if (var2 == null) {
            var2 = Sets.newHashSet();
            this.d.put(var1.b(), (Set<AttributeModifier>)var2);
         }

         this.c.get(var1.c()).add(var1);
         var2.add(var1);
         this.e.put(var1.a(), var1);
         this.f();
      }
   }

   protected void f() {
      this.g = true;
      this.a.a(this);
   }

   @Override
   public void c(AttributeModifier var1) {
      for (int var2 = 0; var2 < 3; var2++) {
         Set var3 = this.c.get(var2);
         var3.remove(var1);
      }

      Set var4 = this.d.get(var1.b());
      if (var4 != null) {
         var4.remove(var1);
         if (var4.isEmpty()) {
            this.d.remove(var1.b());
         }
      }

      this.e.remove(var1.a());
      this.f();
   }

   @Override
   public void d() {
      Collection var1 = this.c();
      if (var1 != null) {
         for (AttributeModifier var3 : Lists.newArrayList(var1)) {
            this.c(var3);
         }
      }
   }

   @Override
   public double e() {
      if (this.g) {
         this.h = this.g();
         this.g = false;
      }

      return this.h;
   }

   private double g() {
      double var1 = this.b();

      for (AttributeModifier var4 : this.b(0)) {
         var1 += var4.d();
      }

      double var7 = var1;

      for (AttributeModifier var6 : this.b(1)) {
         var7 += var1 * var6.d();
      }

      for (AttributeModifier var9 : this.b(2)) {
         var7 *= 1.0 + var9.d();
      }

      return this.b.a(var7);
   }

   private Collection<AttributeModifier> b(int var1) {
      HashSet var2 = Sets.newHashSet(this.a(var1));

      for (IAttribute var3 = this.b.d(); var3 != null; var3 = var3.d()) {
         IAttributeInstance var4 = this.a.a(var3);
         if (var4 != null) {
            var2.addAll(var4.a(var1));
         }
      }

      return var2;
   }
}
