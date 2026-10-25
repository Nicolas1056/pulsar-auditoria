package net.minecraft.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.passive.IAnimals;

public enum EnumCreatureType {
   a(IMob.class, 70, Material.a, false, false),
   b(EntityAnimal.class, 10, Material.a, true, true),
   c(EntityAmbientCreature.class, 15, Material.a, true, false),
   d(EntityWaterMob.class, 5, Material.h, true, false);

   private final Class<? extends IAnimals> e;
   private final int f;
   private final Material g;
   private final boolean h;
   private final boolean i;

   private EnumCreatureType(Class<? extends IAnimals> var3, int var4, Material var5, boolean var6, boolean var7) {
      this.e = var3;
      this.f = var4;
      this.g = var5;
      this.h = var6;
      this.i = var7;
   }

   public Class<? extends IAnimals> a() {
      return this.e;
   }

   public int b() {
      return this.f;
   }

   public boolean d() {
      return this.h;
   }

   public boolean e() {
      return this.i;
   }
}
