package net.minecraft.world.gen.structure;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapGenStructureIO {
   private static final Logger a = LogManager.getLogger();
   private static Map<String, Class<? extends StructureStart>> b = Maps.newHashMap();
   private static Map<Class<? extends StructureStart>, String> c = Maps.newHashMap();
   private static Map<String, Class<? extends StructureComponent>> d = Maps.newHashMap();
   private static Map<Class<? extends StructureComponent>, String> e = Maps.newHashMap();

   private static void b(Class<? extends StructureStart> var0, String var1) {
      b.put(var1, var0);
      c.put(var0, var1);
   }

   static void a(Class<? extends StructureComponent> var0, String var1) {
      d.put(var1, var0);
      e.put(var0, var1);
   }

   public static String a(StructureStart var0) {
      return c.get(var0.getClass());
   }

   public static String a(StructureComponent var0) {
      return e.get(var0.getClass());
   }

   public static StructureStart a(NBTTagCompound var0, World var1) {
      StructureStart var2 = null;

      try {
         Class var3 = b.get(var0.j("id"));
         if (var3 != null) {
            var2 = (StructureStart)var3.newInstance();
         }
      } catch (Exception var4) {
         a.warn("Failed Start with id " + var0.j("id"));
         var4.printStackTrace();
      }

      if (var2 != null) {
         var2.a(var1, var0);
      } else {
         a.warn("Skipping Structure with id " + var0.j("id"));
      }

      return var2;
   }

   public static StructureComponent b(NBTTagCompound var0, World var1) {
      StructureComponent var2 = null;

      try {
         Class var3 = d.get(var0.j("id"));
         if (var3 != null) {
            var2 = (StructureComponent)var3.newInstance();
         }
      } catch (Exception var4) {
         a.warn("Failed Piece with id " + var0.j("id"));
         var4.printStackTrace();
      }

      if (var2 != null) {
         var2.a(var1, var0);
      } else {
         a.warn("Skipping Piece with id " + var0.j("id"));
      }

      return var2;
   }

   static {
      b(StructureMineshaftStart.class, "Mineshaft");
      b(MapGenVillage.Start.class, "Village");
      b(MapGenNetherBridge.Start.class, "Fortress");
      b(MapGenStronghold.Start.class, "Stronghold");
      b(MapGenScatteredFeature.Start.class, "Temple");
      b(StructureOceanMonument.StartMonument.class, "Monument");
      StructureMineshaftPieces.a();
      StructureVillagePieces.a();
      StructureNetherBridgePieces.a();
      StructureStrongholdPieces.a();
      ComponentScatteredFeaturePieces.a();
      StructureOceanMonumentPieces.a();
   }
}
