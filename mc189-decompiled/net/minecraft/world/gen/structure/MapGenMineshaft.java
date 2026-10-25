package net.minecraft.world.gen.structure;

import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.MathHelper;

public class MapGenMineshaft extends MapGenStructure {
   private double d = 0.004;

   public MapGenMineshaft() {
   }

   @Override
   public String a() {
      return "Mineshaft";
   }

   public MapGenMineshaft(Map<String, String> var1) {
      for (Entry var3 : var1.entrySet()) {
         if (((String)var3.getKey()).equals("chance")) {
            this.d = MathHelper.a((String)var3.getValue(), this.d);
         }
      }
   }

   @Override
   protected boolean a(int var1, int var2) {
      return this.b.nextDouble() < this.d && this.b.nextInt(80) < Math.max(Math.abs(var1), Math.abs(var2));
   }

   @Override
   protected StructureStart b(int var1, int var2) {
      return new StructureMineshaftStart(this.c, this.b, var1, var2);
   }
}
