package net.minecraft.entity;

import com.google.common.collect.Maps;
import java.util.HashMap;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;

public class EntitySpawnPlacementRegistry {
   private static final HashMap<Class, EntityLiving.SpawnPlacementType> a = Maps.newHashMap();

   public static EntityLiving.SpawnPlacementType a(Class var0) {
      return a.get(var0);
   }

   static {
      a.put(EntityBat.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityChicken.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityCow.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityHorse.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityMooshroom.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityOcelot.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityPig.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityRabbit.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntitySheep.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntitySnowman.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntitySquid.class, EntityLiving.SpawnPlacementType.c);
      a.put(EntityIronGolem.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityWolf.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityVillager.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityDragon.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityWither.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityBlaze.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityCaveSpider.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityCreeper.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityEnderman.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityEndermite.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityGhast.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityGiantZombie.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityGuardian.class, EntityLiving.SpawnPlacementType.c);
      a.put(EntityMagmaCube.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityPigZombie.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntitySilverfish.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntitySkeleton.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntitySlime.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntitySpider.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityWitch.class, EntityLiving.SpawnPlacementType.a);
      a.put(EntityZombie.class, EntityLiving.SpawnPlacementType.a);
   }
}
