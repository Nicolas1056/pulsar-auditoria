package net.minecraft.entity;

import java.util.Collection;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SharedMonsterAttributes {
   private static final Logger f = LogManager.getLogger();
   public static final IAttribute a = new RangedAttribute(null, "generic.maxHealth", 20.0, 0.0, 1024.0).a("Max Health").a(true);
   public static final IAttribute b = new RangedAttribute(null, "generic.followRange", 32.0, 0.0, 2048.0).a("Follow Range");
   public static final IAttribute c = new RangedAttribute(null, "generic.knockbackResistance", 0.0, 0.0, 1.0).a("Knockback Resistance");
   public static final IAttribute d = new RangedAttribute(null, "generic.movementSpeed", 0.7F, 0.0, 1024.0).a("Movement Speed").a(true);
   public static final IAttribute e = new RangedAttribute(null, "generic.attackDamage", 2.0, 0.0, 2048.0);

   public static NBTTagList a(BaseAttributeMap var0) {
      NBTTagList var1 = new NBTTagList();

      for (IAttributeInstance var3 : var0.a()) {
         var1.a(a(var3));
      }

      return var1;
   }

   private static NBTTagCompound a(IAttributeInstance var0) {
      NBTTagCompound var1 = new NBTTagCompound();
      IAttribute var2 = var0.a();
      var1.a("Name", var2.a());
      var1.a("Base", var0.b());
      Collection var3 = var0.c();
      if (var3 != null && !var3.isEmpty()) {
         NBTTagList var4 = new NBTTagList();

         for (AttributeModifier var6 : var3) {
            if (var6.e()) {
               var4.a(a(var6));
            }
         }

         var1.a("Modifiers", var4);
      }

      return var1;
   }

   private static NBTTagCompound a(AttributeModifier var0) {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.a("Name", var0.b());
      var1.a("Amount", var0.d());
      var1.a("Operation", var0.c());
      var1.a("UUIDMost", var0.a().getMostSignificantBits());
      var1.a("UUIDLeast", var0.a().getLeastSignificantBits());
      return var1;
   }

   public static void a(BaseAttributeMap var0, NBTTagList var1) {
      for (int var2 = 0; var2 < var1.c(); var2++) {
         NBTTagCompound var3 = var1.b(var2);
         IAttributeInstance var4 = var0.a(var3.j("Name"));
         if (var4 != null) {
            a(var4, var3);
         } else {
            f.warn("Ignoring unknown attribute '" + var3.j("Name") + "'");
         }
      }
   }

   private static void a(IAttributeInstance var0, NBTTagCompound var1) {
      var0.a(var1.i("Base"));
      if (var1.b("Modifiers", 9)) {
         NBTTagList var2 = var1.c("Modifiers", 10);

         for (int var3 = 0; var3 < var2.c(); var3++) {
            AttributeModifier var4 = a(var2.b(var3));
            if (var4 != null) {
               AttributeModifier var5 = var0.a(var4.a());
               if (var5 != null) {
                  var0.c(var5);
               }

               var0.b(var4);
            }
         }
      }
   }

   public static AttributeModifier a(NBTTagCompound var0) {
      UUID var1 = new UUID(var0.g("UUIDMost"), var0.g("UUIDLeast"));

      try {
         return new AttributeModifier(var1, var0.j("Name"), var0.i("Amount"), var0.f("Operation"));
      } catch (Exception var3) {
         f.warn("Unable to create attribute: " + var3.getMessage());
         return null;
      }
   }
}
