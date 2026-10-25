package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
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
import net.minecraft.entity.monster.EntityMob;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityList {
   private static final Logger b = LogManager.getLogger();
   private static final Map<String, Class<? extends Entity>> c = Maps.newHashMap();
   private static final Map<Class<? extends Entity>, String> d = Maps.newHashMap();
   private static final Map<Integer, Class<? extends Entity>> e = Maps.newHashMap();
   private static final Map<Class<? extends Entity>, Integer> f = Maps.newHashMap();
   private static final Map<String, Integer> g = Maps.newHashMap();
   public static final Map<Integer, EntityList.EntityEggInfo> a = Maps.newLinkedHashMap();

   private static void a(Class<? extends Entity> var0, String var1, int var2) {
      if (c.containsKey(var1)) {
         throw new IllegalArgumentException("ID is already registered: " + var1);
      } else if (e.containsKey(var2)) {
         throw new IllegalArgumentException("ID is already registered: " + var2);
      } else if (var2 == 0) {
         throw new IllegalArgumentException("Cannot register to reserved id: " + var2);
      } else if (var0 == null) {
         throw new IllegalArgumentException("Cannot register null clazz for id: " + var2);
      } else {
         c.put(var1, var0);
         d.put(var0, var1);
         e.put(var2, var0);
         f.put(var0, var2);
         g.put(var1, var2);
      }
   }

   private static void a(Class<? extends Entity> var0, String var1, int var2, int var3, int var4) {
      a(var0, var1, var2);
      a.put(var2, new EntityList.EntityEggInfo(var2, var3, var4));
   }

   public static Entity a(String var0, World var1) {
      Entity var2 = null;

      try {
         Class var3 = c.get(var0);
         if (var3 != null) {
            var2 = (Entity)var3.getConstructor(World.class).newInstance(var1);
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return var2;
   }

   public static Entity a(NBTTagCompound var0, World var1) {
      Entity var2 = null;
      if ("Minecart".equals(var0.j("id"))) {
         var0.a("id", EntityMinecart.EnumMinecartType.a(var0.f("Type")).b());
         var0.o("Type");
      }

      try {
         Class var3 = c.get(var0.j("id"));
         if (var3 != null) {
            var2 = (Entity)var3.getConstructor(World.class).newInstance(var1);
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      if (var2 != null) {
         var2.f(var0);
      } else {
         b.warn("Skipping Entity with id " + var0.j("id"));
      }

      return var2;
   }

   public static Entity a(int var0, World var1) {
      Entity var2 = null;

      try {
         Class var3 = a(var0);
         if (var3 != null) {
            var2 = (Entity)var3.getConstructor(World.class).newInstance(var1);
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      if (var2 == null) {
         b.warn("Skipping Entity with id " + var0);
      }

      return var2;
   }

   public static int a(Entity var0) {
      Integer var1 = f.get(var0.getClass());
      return var1 == null ? 0 : var1;
   }

   public static Class<? extends Entity> a(int var0) {
      return e.get(var0);
   }

   public static String b(Entity var0) {
      return d.get(var0.getClass());
   }

   public static int a(String var0) {
      Integer var1 = g.get(var0);
      return var1 == null ? 90 : var1;
   }

   public static String b(int var0) {
      return d.get(a(var0));
   }

   public static void a() {
   }

   public static List<String> b() {
      Set var0 = c.keySet();
      ArrayList var1 = Lists.newArrayList();

      for (String var3 : var0) {
         Class var4 = c.get(var3);
         if ((var4.getModifiers() & 1024) != 1024) {
            var1.add(var3);
         }
      }

      var1.add("LightningBolt");
      return var1;
   }

   public static boolean a(Entity var0, String var1) {
      String var2 = b(var0);
      if (var2 == null && var0 instanceof EntityPlayer) {
         var2 = "Player";
      } else if (var2 == null && var0 instanceof EntityLightningBolt) {
         var2 = "LightningBolt";
      }

      return var1.equals(var2);
   }

   public static boolean b(String var0) {
      return "Player".equals(var0) || b().contains(var0);
   }

   static {
      a(EntityItem.class, "Item", 1);
      a(EntityXPOrb.class, "XPOrb", 2);
      a(EntityEgg.class, "ThrownEgg", 7);
      a(EntityLeashKnot.class, "LeashKnot", 8);
      a(EntityPainting.class, "Painting", 9);
      a(EntityArrow.class, "Arrow", 10);
      a(EntitySnowball.class, "Snowball", 11);
      a(EntityLargeFireball.class, "Fireball", 12);
      a(EntitySmallFireball.class, "SmallFireball", 13);
      a(EntityEnderPearl.class, "ThrownEnderpearl", 14);
      a(EntityEnderEye.class, "EyeOfEnderSignal", 15);
      a(EntityPotion.class, "ThrownPotion", 16);
      a(EntityExpBottle.class, "ThrownExpBottle", 17);
      a(EntityItemFrame.class, "ItemFrame", 18);
      a(EntityWitherSkull.class, "WitherSkull", 19);
      a(EntityTNTPrimed.class, "PrimedTnt", 20);
      a(EntityFallingBlock.class, "FallingSand", 21);
      a(EntityFireworkRocket.class, "FireworksRocketEntity", 22);
      a(EntityArmorStand.class, "ArmorStand", 30);
      a(EntityBoat.class, "Boat", 41);
      a(EntityMinecartEmpty.class, EntityMinecart.EnumMinecartType.a.b(), 42);
      a(EntityMinecartChest.class, EntityMinecart.EnumMinecartType.b.b(), 43);
      a(EntityMinecartFurnace.class, EntityMinecart.EnumMinecartType.c.b(), 44);
      a(EntityMinecartTNT.class, EntityMinecart.EnumMinecartType.d.b(), 45);
      a(EntityMinecartHopper.class, EntityMinecart.EnumMinecartType.f.b(), 46);
      a(EntityMinecartMobSpawner.class, EntityMinecart.EnumMinecartType.e.b(), 47);
      a(EntityMinecartCommandBlock.class, EntityMinecart.EnumMinecartType.g.b(), 40);
      a(EntityLiving.class, "Mob", 48);
      a(EntityMob.class, "Monster", 49);
      a(EntityCreeper.class, "Creeper", 50, 894731, 0);
      a(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
      a(EntitySpider.class, "Spider", 52, 3419431, 11013646);
      a(EntityGiantZombie.class, "Giant", 53);
      a(EntityZombie.class, "Zombie", 54, 44975, 7969893);
      a(EntitySlime.class, "Slime", 55, 5349438, 8306542);
      a(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
      a(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
      a(EntityEnderman.class, "Enderman", 58, 1447446, 0);
      a(EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
      a(EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
      a(EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
      a(EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
      a(EntityDragon.class, "EnderDragon", 63);
      a(EntityWither.class, "WitherBoss", 64);
      a(EntityBat.class, "Bat", 65, 4996656, 986895);
      a(EntityWitch.class, "Witch", 66, 3407872, 5349438);
      a(EntityEndermite.class, "Endermite", 67, 1447446, 7237230);
      a(EntityGuardian.class, "Guardian", 68, 5931634, 15826224);
      a(EntityPig.class, "Pig", 90, 15771042, 14377823);
      a(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
      a(EntityCow.class, "Cow", 92, 4470310, 10592673);
      a(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
      a(EntitySquid.class, "Squid", 94, 2243405, 7375001);
      a(EntityWolf.class, "Wolf", 95, 14144467, 13545366);
      a(EntityMooshroom.class, "MushroomCow", 96, 10489616, 12040119);
      a(EntitySnowman.class, "SnowMan", 97);
      a(EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
      a(EntityIronGolem.class, "VillagerGolem", 99);
      a(EntityHorse.class, "EntityHorse", 100, 12623485, 15656192);
      a(EntityRabbit.class, "Rabbit", 101, 10051392, 7555121);
      a(EntityVillager.class, "Villager", 120, 5651507, 12422002);
      a(EntityEnderCrystal.class, "EnderCrystal", 200);
   }

   public static class EntityEggInfo {
      public final int a;
      public final int b;
      public final int c;
      public final StatBase d;
      public final StatBase e;

      public EntityEggInfo(int var1, int var2, int var3) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
         this.d = StatList.a(this);
         this.e = StatList.b(this);
      }
   }
}
