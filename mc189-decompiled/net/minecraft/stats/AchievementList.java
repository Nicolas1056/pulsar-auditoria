package net.minecraft.stats;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonSerializableSet;

public class AchievementList {
   public static int a;
   public static int b;
   public static int c;
   public static int d;
   public static List<Achievement> e = Lists.newArrayList();
   public static Achievement f = new Achievement("achievement.openInventory", "openInventory", 0, 0, Items.aL, null).a().c();
   public static Achievement g = new Achievement("achievement.mineWood", "mineWood", 2, 1, Blocks.r, f).c();
   public static Achievement h = new Achievement("achievement.buildWorkBench", "buildWorkBench", 4, -1, Blocks.ai, g).c();
   public static Achievement i = new Achievement("achievement.buildPickaxe", "buildPickaxe", 4, 2, Items.o, h).c();
   public static Achievement j = new Achievement("achievement.buildFurnace", "buildFurnace", 3, 4, Blocks.al, i).c();
   public static Achievement k = new Achievement("achievement.acquireIron", "acquireIron", 1, 4, Items.j, j).c();
   public static Achievement l = new Achievement("achievement.buildHoe", "buildHoe", 2, -3, Items.I, h).c();
   public static Achievement m = new Achievement("achievement.makeBread", "makeBread", -1, -3, Items.P, l).c();
   public static Achievement n = new Achievement("achievement.bakeCake", "bakeCake", 0, -5, Items.aZ, l).c();
   public static Achievement o = new Achievement("achievement.buildBetterPickaxe", "buildBetterPickaxe", 6, 2, Items.s, i).c();
   public static Achievement p = new Achievement("achievement.cookFish", "cookFish", 2, 6, Items.aV, j).c();
   public static Achievement q = new Achievement("achievement.onARail", "onARail", 2, 3, Blocks.av, k).b().c();
   public static Achievement r = new Achievement("achievement.buildSword", "buildSword", 6, -1, Items.m, h).c();
   public static Achievement s = new Achievement("achievement.killEnemy", "killEnemy", 8, -1, Items.aX, r).c();
   public static Achievement t = new Achievement("achievement.killCow", "killCow", 7, -3, Items.aF, r).c();
   public static Achievement u = new Achievement("achievement.flyPig", "flyPig", 9, -3, Items.aA, t).b().c();
   public static Achievement v = new Achievement("achievement.snipeSkeleton", "snipeSkeleton", 7, 0, Items.f, s).b().c();
   public static Achievement w = new Achievement("achievement.diamonds", "diamonds", -1, 5, Blocks.ag, k).c();
   public static Achievement x = new Achievement("achievement.diamondsToYou", "diamondsToYou", -1, 2, Items.i, w).c();
   public static Achievement y = new Achievement("achievement.portal", "portal", -1, 7, Blocks.Z, w).c();
   public static Achievement z = new Achievement("achievement.ghast", "ghast", -4, 8, Items.bw, y).b().c();
   public static Achievement A = new Achievement("achievement.blazeRod", "blazeRod", 0, 9, Items.bv, y).c();
   public static Achievement B = new Achievement("achievement.potion", "potion", 2, 8, Items.bz, A).c();
   public static Achievement C = new Achievement("achievement.theEnd", "theEnd", 3, 10, Items.bH, A).b().c();
   public static Achievement D = new Achievement("achievement.theEnd2", "theEnd2", 4, 13, Blocks.bI, C).b().c();
   public static Achievement E = new Achievement("achievement.enchantments", "enchantments", -4, 4, Blocks.bC, w).c();
   public static Achievement F = new Achievement("achievement.overkill", "overkill", -4, 1, Items.u, E).b().c();
   public static Achievement G = new Achievement("achievement.bookcase", "bookcase", -3, 6, Blocks.X, E).c();
   public static Achievement H = new Achievement("achievement.breedCow", "breedCow", 7, -5, Items.O, t).c();
   public static Achievement I = new Achievement("achievement.spawnWither", "spawnWither", 7, 12, new ItemStack(Items.bX, 1, 1), D).c();
   public static Achievement J = new Achievement("achievement.killWither", "killWither", 7, 10, Items.bZ, I).c();
   public static Achievement K = new Achievement("achievement.fullBeacon", "fullBeacon", 7, 8, Blocks.bY, J).b().c();
   public static Achievement L = new Achievement("achievement.exploreAllBiomes", "exploreAllBiomes", 4, 8, Items.af, C).a(JsonSerializableSet.class).b().c();
   public static Achievement M = new Achievement("achievement.overpowered", "overpowered", 6, 4, new ItemStack(Items.ao, 1, 1), o).b().c();

   public static void a() {
   }
}
