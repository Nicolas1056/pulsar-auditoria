package net.minecraft.stats;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;

public class StatList {
   protected static Map<String, StatBase> a = Maps.newHashMap();
   public static List<StatBase> b = Lists.newArrayList();
   public static List<StatBase> c = Lists.newArrayList();
   public static List<StatCrafting> d = Lists.newArrayList();
   public static List<StatCrafting> e = Lists.newArrayList();
   public static StatBase f = new StatBasic("stat.leaveGame", new ChatComponentTranslation("stat.leaveGame")).i().h();
   public static StatBase g = new StatBasic("stat.playOneMinute", new ChatComponentTranslation("stat.playOneMinute"), StatBase.h).i().h();
   public static StatBase h = new StatBasic("stat.timeSinceDeath", new ChatComponentTranslation("stat.timeSinceDeath"), StatBase.h).i().h();
   public static StatBase i = new StatBasic("stat.walkOneCm", new ChatComponentTranslation("stat.walkOneCm"), StatBase.i).i().h();
   public static StatBase j = new StatBasic("stat.crouchOneCm", new ChatComponentTranslation("stat.crouchOneCm"), StatBase.i).i().h();
   public static StatBase k = new StatBasic("stat.sprintOneCm", new ChatComponentTranslation("stat.sprintOneCm"), StatBase.i).i().h();
   public static StatBase l = new StatBasic("stat.swimOneCm", new ChatComponentTranslation("stat.swimOneCm"), StatBase.i).i().h();
   public static StatBase m = new StatBasic("stat.fallOneCm", new ChatComponentTranslation("stat.fallOneCm"), StatBase.i).i().h();
   public static StatBase n = new StatBasic("stat.climbOneCm", new ChatComponentTranslation("stat.climbOneCm"), StatBase.i).i().h();
   public static StatBase o = new StatBasic("stat.flyOneCm", new ChatComponentTranslation("stat.flyOneCm"), StatBase.i).i().h();
   public static StatBase p = new StatBasic("stat.diveOneCm", new ChatComponentTranslation("stat.diveOneCm"), StatBase.i).i().h();
   public static StatBase q = new StatBasic("stat.minecartOneCm", new ChatComponentTranslation("stat.minecartOneCm"), StatBase.i).i().h();
   public static StatBase r = new StatBasic("stat.boatOneCm", new ChatComponentTranslation("stat.boatOneCm"), StatBase.i).i().h();
   public static StatBase s = new StatBasic("stat.pigOneCm", new ChatComponentTranslation("stat.pigOneCm"), StatBase.i).i().h();
   public static StatBase t = new StatBasic("stat.horseOneCm", new ChatComponentTranslation("stat.horseOneCm"), StatBase.i).i().h();
   public static StatBase u = new StatBasic("stat.jump", new ChatComponentTranslation("stat.jump")).i().h();
   public static StatBase v = new StatBasic("stat.drop", new ChatComponentTranslation("stat.drop")).i().h();
   public static StatBase w = new StatBasic("stat.damageDealt", new ChatComponentTranslation("stat.damageDealt"), StatBase.j).h();
   public static StatBase x = new StatBasic("stat.damageTaken", new ChatComponentTranslation("stat.damageTaken"), StatBase.j).h();
   public static StatBase y = new StatBasic("stat.deaths", new ChatComponentTranslation("stat.deaths")).h();
   public static StatBase z = new StatBasic("stat.mobKills", new ChatComponentTranslation("stat.mobKills")).h();
   public static StatBase A = new StatBasic("stat.animalsBred", new ChatComponentTranslation("stat.animalsBred")).h();
   public static StatBase B = new StatBasic("stat.playerKills", new ChatComponentTranslation("stat.playerKills")).h();
   public static StatBase C = new StatBasic("stat.fishCaught", new ChatComponentTranslation("stat.fishCaught")).h();
   public static StatBase D = new StatBasic("stat.junkFished", new ChatComponentTranslation("stat.junkFished")).h();
   public static StatBase E = new StatBasic("stat.treasureFished", new ChatComponentTranslation("stat.treasureFished")).h();
   public static StatBase F = new StatBasic("stat.talkedToVillager", new ChatComponentTranslation("stat.talkedToVillager")).h();
   public static StatBase G = new StatBasic("stat.tradedWithVillager", new ChatComponentTranslation("stat.tradedWithVillager")).h();
   public static StatBase H = new StatBasic("stat.cakeSlicesEaten", new ChatComponentTranslation("stat.cakeSlicesEaten")).h();
   public static StatBase I = new StatBasic("stat.cauldronFilled", new ChatComponentTranslation("stat.cauldronFilled")).h();
   public static StatBase J = new StatBasic("stat.cauldronUsed", new ChatComponentTranslation("stat.cauldronUsed")).h();
   public static StatBase K = new StatBasic("stat.armorCleaned", new ChatComponentTranslation("stat.armorCleaned")).h();
   public static StatBase L = new StatBasic("stat.bannerCleaned", new ChatComponentTranslation("stat.bannerCleaned")).h();
   public static StatBase M = new StatBasic("stat.brewingstandInteraction", new ChatComponentTranslation("stat.brewingstandInteraction")).h();
   public static StatBase N = new StatBasic("stat.beaconInteraction", new ChatComponentTranslation("stat.beaconInteraction")).h();
   public static StatBase O = new StatBasic("stat.dropperInspected", new ChatComponentTranslation("stat.dropperInspected")).h();
   public static StatBase P = new StatBasic("stat.hopperInspected", new ChatComponentTranslation("stat.hopperInspected")).h();
   public static StatBase Q = new StatBasic("stat.dispenserInspected", new ChatComponentTranslation("stat.dispenserInspected")).h();
   public static StatBase R = new StatBasic("stat.noteblockPlayed", new ChatComponentTranslation("stat.noteblockPlayed")).h();
   public static StatBase S = new StatBasic("stat.noteblockTuned", new ChatComponentTranslation("stat.noteblockTuned")).h();
   public static StatBase T = new StatBasic("stat.flowerPotted", new ChatComponentTranslation("stat.flowerPotted")).h();
   public static StatBase U = new StatBasic("stat.trappedChestTriggered", new ChatComponentTranslation("stat.trappedChestTriggered")).h();
   public static StatBase V = new StatBasic("stat.enderchestOpened", new ChatComponentTranslation("stat.enderchestOpened")).h();
   public static StatBase W = new StatBasic("stat.itemEnchanted", new ChatComponentTranslation("stat.itemEnchanted")).h();
   public static StatBase X = new StatBasic("stat.recordPlayed", new ChatComponentTranslation("stat.recordPlayed")).h();
   public static StatBase Y = new StatBasic("stat.furnaceInteraction", new ChatComponentTranslation("stat.furnaceInteraction")).h();
   public static StatBase Z = new StatBasic("stat.craftingTableInteraction", new ChatComponentTranslation("stat.workbenchInteraction")).h();
   public static StatBase aa = new StatBasic("stat.chestOpened", new ChatComponentTranslation("stat.chestOpened")).h();
   public static final StatBase[] ab = new StatBase[4096];
   public static final StatBase[] ac = new StatBase[32000];
   public static final StatBase[] ad = new StatBase[32000];
   public static final StatBase[] ae = new StatBase[32000];

   public static void a() {
      c();
      d();
      e();
      b();
      AchievementList.a();
      EntityList.a();
   }

   private static void b() {
      HashSet var0 = Sets.newHashSet();

      for (IRecipe var2 : CraftingManager.a().b()) {
         if (var2.b() != null) {
            var0.add(var2.b().b());
         }
      }

      for (ItemStack var7 : FurnaceRecipes.a().b().values()) {
         var0.add(var7.b());
      }

      for (Item var8 : var0) {
         if (var8 != null) {
            int var3 = Item.b(var8);
            String var4 = a(var8);
            if (var4 != null) {
               ac[var3] = new StatCrafting("stat.craftItem.", var4, new ChatComponentTranslation("stat.craftItem", new ItemStack(var8).C()), var8).h();
            }
         }
      }

      a(ac);
   }

   private static void c() {
      for (Block var1 : Block.c) {
         Item var2 = Item.a(var1);
         if (var2 != null) {
            int var3 = Block.a(var1);
            String var4 = a(var2);
            if (var4 != null && var1.J()) {
               ab[var3] = new StatCrafting("stat.mineBlock.", var4, new ChatComponentTranslation("stat.mineBlock", new ItemStack(var1).C()), var2).h();
               e.add((StatCrafting)ab[var3]);
            }
         }
      }

      a(ab);
   }

   private static void d() {
      for (Item var1 : Item.e) {
         if (var1 != null) {
            int var2 = Item.b(var1);
            String var3 = a(var1);
            if (var3 != null) {
               ad[var2] = new StatCrafting("stat.useItem.", var3, new ChatComponentTranslation("stat.useItem", new ItemStack(var1).C()), var1).h();
               if (!(var1 instanceof ItemBlock)) {
                  d.add((StatCrafting)ad[var2]);
               }
            }
         }
      }

      a(ad);
   }

   private static void e() {
      for (Item var1 : Item.e) {
         if (var1 != null) {
            int var2 = Item.b(var1);
            String var3 = a(var1);
            if (var3 != null && var1.m()) {
               ae[var2] = new StatCrafting("stat.breakItem.", var3, new ChatComponentTranslation("stat.breakItem", new ItemStack(var1).C()), var1).h();
            }
         }
      }

      a(ae);
   }

   private static String a(Item var0) {
      ResourceLocation var1 = Item.e.c(var0);
      return var1 != null ? var1.toString().replace(':', '.') : null;
   }

   private static void a(StatBase[] var0) {
      a(var0, Blocks.j, Blocks.i);
      a(var0, Blocks.l, Blocks.k);
      a(var0, Blocks.aZ, Blocks.aU);
      a(var0, Blocks.am, Blocks.al);
      a(var0, Blocks.aD, Blocks.aC);
      a(var0, Blocks.bc, Blocks.bb);
      a(var0, Blocks.ck, Blocks.cj);
      a(var0, Blocks.aF, Blocks.aE);
      a(var0, Blocks.bK, Blocks.bJ);
      a(var0, Blocks.T, Blocks.U);
      a(var0, Blocks.bL, Blocks.bM);
      a(var0, Blocks.cO, Blocks.cP);
      a(var0, Blocks.c, Blocks.d);
      a(var0, Blocks.ak, Blocks.d);
   }

   private static void a(StatBase[] var0, Block var1, Block var2) {
      int var3 = Block.a(var1);
      int var4 = Block.a(var2);
      if (var0[var3] != null && var0[var4] == null) {
         var0[var4] = var0[var3];
      } else {
         b.remove(var0[var3]);
         e.remove(var0[var3]);
         c.remove(var0[var3]);
         var0[var3] = var0[var4];
      }
   }

   public static StatBase a(EntityList.EntityEggInfo var0) {
      String var1 = EntityList.b(var0.a);
      return var1 == null
         ? null
         : new StatBase("stat.killEntity." + var1, new ChatComponentTranslation("stat.entityKill", new ChatComponentTranslation("entity." + var1 + ".name")))
            .h();
   }

   public static StatBase b(EntityList.EntityEggInfo var0) {
      String var1 = EntityList.b(var0.a);
      return var1 == null
         ? null
         : new StatBase(
               "stat.entityKilledBy." + var1, new ChatComponentTranslation("stat.entityKilledBy", new ChatComponentTranslation("entity." + var1 + ".name"))
            )
            .h();
   }

   public static StatBase a(String var0) {
      return a.get(var0);
   }
}
