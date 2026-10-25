package net.minecraft.entity.ai.attributes;

import java.util.Collection;
import java.util.UUID;

public interface IAttributeInstance {
   IAttribute a();

   double b();

   void a(double var1);

   Collection<AttributeModifier> a(int var1);

   Collection<AttributeModifier> c();

   boolean a(AttributeModifier var1);

   AttributeModifier a(UUID var1);

   void b(AttributeModifier var1);

   void c(AttributeModifier var1);

   void d();

   double e();
}
