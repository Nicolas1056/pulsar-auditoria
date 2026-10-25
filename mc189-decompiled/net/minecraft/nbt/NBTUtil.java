package net.minecraft.nbt;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.UUID;
import net.minecraft.util.StringUtils;

public final class NBTUtil {
   public static GameProfile a(NBTTagCompound var0) {
      String var1 = null;
      String var2 = null;
      if (var0.b("Name", 8)) {
         var1 = var0.j("Name");
      }

      if (var0.b("Id", 8)) {
         var2 = var0.j("Id");
      }

      if (StringUtils.b(var1) && StringUtils.b(var2)) {
         return null;
      } else {
         UUID var3;
         try {
            var3 = UUID.fromString(var2);
         } catch (Throwable var12) {
            var3 = null;
         }

         GameProfile var4 = new GameProfile(var3, var1);
         if (var0.b("Properties", 10)) {
            NBTTagCompound var5 = var0.m("Properties");

            for (String var7 : var5.c()) {
               NBTTagList var8 = var5.c(var7, 10);

               for (int var9 = 0; var9 < var8.c(); var9++) {
                  NBTTagCompound var10 = var8.b(var9);
                  String var11 = var10.j("Value");
                  if (var10.b("Signature", 8)) {
                     var4.getProperties().put(var7, new Property(var7, var11, var10.j("Signature")));
                  } else {
                     var4.getProperties().put(var7, new Property(var7, var11));
                  }
               }
            }
         }

         return var4;
      }
   }

   public static NBTTagCompound a(NBTTagCompound var0, GameProfile var1) {
      if (!StringUtils.b(var1.getName())) {
         var0.a("Name", var1.getName());
      }

      if (var1.getId() != null) {
         var0.a("Id", var1.getId().toString());
      }

      if (!var1.getProperties().isEmpty()) {
         NBTTagCompound var2 = new NBTTagCompound();

         for (String var4 : var1.getProperties().keySet()) {
            NBTTagList var5 = new NBTTagList();

            for (Property var7 : var1.getProperties().get(var4)) {
               NBTTagCompound var8 = new NBTTagCompound();
               var8.a("Value", var7.getValue());
               if (var7.hasSignature()) {
                  var8.a("Signature", var7.getSignature());
               }

               var5.a(var8);
            }

            var2.a(var4, var5);
         }

         var0.a("Properties", var2);
      }

      return var0;
   }

   public static boolean a(NBTBase var0, NBTBase var1, boolean var2) {
      if (var0 == var1) {
         return true;
      } else if (var0 == null) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (!var0.getClass().equals(var1.getClass())) {
         return false;
      } else if (var0 instanceof NBTTagCompound) {
         NBTTagCompound var9 = (NBTTagCompound)var0;
         NBTTagCompound var10 = (NBTTagCompound)var1;

         for (String var12 : var9.c()) {
            NBTBase var13 = var9.a(var12);
            if (!a(var13, var10.a(var12), var2)) {
               return false;
            }
         }

         return true;
      } else if (var0 instanceof NBTTagList && var2) {
         NBTTagList var3 = (NBTTagList)var0;
         NBTTagList var4 = (NBTTagList)var1;
         if (var3.c() == 0) {
            return var4.c() == 0;
         } else {
            for (int var5 = 0; var5 < var3.c(); var5++) {
               NBTBase var6 = var3.g(var5);
               boolean var7 = false;

               for (int var8 = 0; var8 < var4.c(); var8++) {
                  if (a(var6, var4.g(var8), var2)) {
                     var7 = true;
                     break;
                  }
               }

               if (!var7) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return var0.equals(var1);
      }
   }
}
