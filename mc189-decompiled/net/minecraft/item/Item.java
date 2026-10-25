package net.minecraft.item;

import com.google.common.base.Function;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockWall;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Item {
   public static final RegistryNamespaced<ResourceLocation, Item> e = new RegistryNamespaced<>();
   private static final Map<Block, Item> a = Maps.newHashMap();
   protected static final UUID f = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
   private CreativeTabs b;
   protected static Random g = new Random();
   protected int h = 64;
   private int c;
   protected boolean i;
   protected boolean j;
   private Item d;
   private String k;
   private String l;

   public static int b(Item var0) {
      return var0 == null ? 0 : e.b(var0);
   }

   public static Item b(int var0) {
      return e.a(var0);
   }

   public static Item a(Block var0) {
      return a.get(var0);
   }

   public static Item d(String var0) {
      Item var1 = e.a(new ResourceLocation(var0));
      if (var1 == null) {
         try {
            return b(Integer.parseInt(var0));
         } catch (NumberFormatException var3) {
         }
      }

      return var1;
   }

   public boolean a(NBTTagCompound var1) {
      return false;
   }

   public Item c(int var1) {
      this.h = var1;
      return this;
   }

   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      return false;
   }

   public float a(ItemStack var1, Block var2) {
      return 1.0F;
   }

   public ItemStack a(ItemStack var1, World var2, EntityPlayer var3) {
      return var1;
   }

   public ItemStack b(ItemStack var1, World var2, EntityPlayer var3) {
      return var1;
   }

   public int j() {
      return this.h;
   }

   public int a(int var1) {
      return 0;
   }

   public boolean k() {
      return this.j;
   }

   protected Item a(boolean var1) {
      this.j = var1;
      return this;
   }

   public int l() {
      return this.c;
   }

   protected Item d(int var1) {
      this.c = var1;
      return this;
   }

   public boolean m() {
      return this.c > 0 && !this.j;
   }

   public boolean a(ItemStack var1, EntityLivingBase var2, EntityLivingBase var3) {
      return false;
   }

   public boolean a(ItemStack var1, World var2, Block var3, BlockPos var4, EntityLivingBase var5) {
      return false;
   }

   public boolean b(Block var1) {
      return false;
   }

   public boolean a(ItemStack var1, EntityPlayer var2, EntityLivingBase var3) {
      return false;
   }

   public Item n() {
      this.i = true;
      return this;
   }

   public boolean w_() {
      return this.i;
   }

   public boolean e() {
      return false;
   }

   public Item c(String var1) {
      this.l = var1;
      return this;
   }

   public String k(ItemStack var1) {
      String var2 = this.e_(var1);
      return var2 == null ? "" : StatCollector.a(var2);
   }

   public String a() {
      return "item." + this.l;
   }

   public String e_(ItemStack var1) {
      return "item." + this.l;
   }

   public Item c(Item var1) {
      this.d = var1;
      return this;
   }

   public boolean p() {
      return true;
   }

   public Item q() {
      return this.d;
   }

   public boolean r() {
      return this.d != null;
   }

   public int a(ItemStack var1, int var2) {
      return 16777215;
   }

   public void a(ItemStack var1, World var2, Entity var3, int var4, boolean var5) {
   }

   public void d(ItemStack var1, World var2, EntityPlayer var3) {
   }

   public boolean f() {
      return false;
   }

   public EnumAction e(ItemStack var1) {
      return EnumAction.a;
   }

   public int d(ItemStack var1) {
      return 0;
   }

   public void a(ItemStack var1, World var2, EntityPlayer var3, int var4) {
   }

   protected Item e(String var1) {
      this.k = var1;
      return this;
   }

   public String j(ItemStack var1) {
      return this.k;
   }

   public boolean l(ItemStack var1) {
      return this.j(var1) != null;
   }

   public void a(ItemStack var1, EntityPlayer var2, List<String> var3, boolean var4) {
   }

   public String a(ItemStack var1) {
      return ("" + StatCollector.a(this.k(var1) + ".name")).trim();
   }

   public boolean f(ItemStack var1) {
      return var1.w();
   }

   public EnumRarity g(ItemStack var1) {
      return var1.w() ? EnumRarity.c : EnumRarity.a;
   }

   public boolean f_(ItemStack var1) {
      return this.j() == 1 && this.m();
   }

   protected MovingObjectPosition a(World var1, EntityPlayer var2, boolean var3) {
      float var4 = var2.z;
      float var5 = var2.y;
      double var6 = var2.s;
      double var8 = var2.t + var2.aS();
      double var10 = var2.u;
      Vec3 var12 = new Vec3(var6, var8, var10);
      float var13 = MathHelper.b(-var5 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var14 = MathHelper.a(-var5 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var15 = -MathHelper.b(-var4 * (float) (Math.PI / 180.0));
      float var16 = MathHelper.a(-var4 * (float) (Math.PI / 180.0));
      float var17 = var14 * var15;
      float var19 = var13 * var15;
      double var20 = 5.0;
      Vec3 var22 = var12.b(var17 * var20, var16 * var20, var19 * var20);
      return var1.a(var12, var22, var3, !var3, false);
   }

   public int b() {
      return 0;
   }

   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      var3.add(new ItemStack(var1, 1, 0));
   }

   public CreativeTabs c() {
      return this.b;
   }

   public Item a(CreativeTabs var1) {
      this.b = var1;
      return this;
   }

   public boolean s() {
      return false;
   }

   public boolean a(ItemStack var1, ItemStack var2) {
      return false;
   }

   public Multimap<String, AttributeModifier> i() {
      return HashMultimap.create();
   }

   public static void t() {
      a(Blocks.b, new ItemMultiTexture(Blocks.b, Blocks.b, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return BlockStone.EnumType.a(var1.i()).d();
         }
      }).b("stone"));
      a(Blocks.c, new ItemColored(Blocks.c, false));
      a(Blocks.d, new ItemMultiTexture(Blocks.d, Blocks.d, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return BlockDirt.DirtType.a(var1.i()).c();
         }
      }).b("dirt"));
      c(Blocks.e);
      a(Blocks.f, new ItemMultiTexture(Blocks.f, Blocks.f, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return BlockPlanks.EnumType.a(var1.i()).d();
         }
      }).b("wood"));
      a(Blocks.g, new ItemMultiTexture(Blocks.g, Blocks.g, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return BlockPlanks.EnumType.a(var1.i()).d();
         }
      }).b("sapling"));
      c(Blocks.h);
      a(Blocks.m, new ItemMultiTexture(Blocks.m, Blocks.m, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return BlockSand.EnumType.a(var1.i()).d();
         }
      }).b("sand"));
      c(Blocks.n);
      c(Blocks.o);
      c(Blocks.p);
      c(Blocks.q);
      a(Blocks.r, new ItemMultiTexture(Blocks.r, Blocks.r, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return BlockPlanks.EnumType.a(var1.i()).d();
         }
      }).b("log"));
      a(Blocks.s, new ItemMultiTexture(Blocks.s, Blocks.s, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return BlockPlanks.EnumType.a(var1.i() + 4).d();
         }
      }).b("log"));
      a(Blocks.t, new ItemLeaves(Blocks.t).b("leaves"));
      a(Blocks.u, new ItemLeaves(Blocks.u).b("leaves"));
      a(Blocks.v, new ItemMultiTexture(Blocks.v, Blocks.v, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return (var1.i() & 1) == 1 ? "wet" : "dry";
         }
      }).b("sponge"));
      c(Blocks.w);
      c(Blocks.x);
      c(Blocks.y);
      c(Blocks.z);
      a(Blocks.A, new ItemMultiTexture(Blocks.A, Blocks.A, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return BlockSandStone.EnumType.a(var1.i()).c();
         }
      }).b("sandStone"));
      c(Blocks.B);
      c(Blocks.D);
      c(Blocks.E);
      a(Blocks.F, new ItemPiston(Blocks.F));
      c(Blocks.G);
      a(Blocks.H, new ItemColored(Blocks.H, true).a(new String[]{"shrub", "grass", "fern"}));
      c(Blocks.I);
      a(Blocks.J, new ItemPiston(Blocks.J));
      a(Blocks.L, new ItemCloth(Blocks.L).b("cloth"));
      a(Blocks.N, new ItemMultiTexture(Blocks.N, Blocks.N, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return BlockFlower.EnumFlowerType.a(BlockFlower.EnumFlowerColor.a, var1.i()).d();
         }
      }).b("flower"));
      a(Blocks.O, new ItemMultiTexture(Blocks.O, Blocks.O, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return BlockFlower.EnumFlowerType.a(BlockFlower.EnumFlowerColor.b, var1.i()).d();
         }
      }).b("rose"));
      c(Blocks.P);
      c(Blocks.Q);
      c(Blocks.R);
      c(Blocks.S);
      a(Blocks.U, new ItemSlab(Blocks.U, Blocks.U, Blocks.T).b("stoneSlab"));
      c(Blocks.V);
      c(Blocks.W);
      c(Blocks.X);
      c(Blocks.Y);
      c(Blocks.Z);
      c(Blocks.aa);
      c(Blocks.ac);
      c(Blocks.ad);
      c(Blocks.ae);
      c(Blocks.ag);
      c(Blocks.ah);
      c(Blocks.ai);
      c(Blocks.ak);
      c(Blocks.al);
      c(Blocks.am);
      c(Blocks.au);
      c(Blocks.av);
      c(Blocks.aw);
      c(Blocks.ay);
      c(Blocks.az);
      c(Blocks.aB);
      c(Blocks.aC);
      c(Blocks.aF);
      c(Blocks.aG);
      a(Blocks.aH, new ItemSnow(Blocks.aH));
      c(Blocks.aI);
      c(Blocks.aJ);
      c(Blocks.aK);
      c(Blocks.aL);
      c(Blocks.aN);
      c(Blocks.aO);
      c(Blocks.aP);
      c(Blocks.aQ);
      c(Blocks.aR);
      c(Blocks.aS);
      c(Blocks.aT);
      c(Blocks.aU);
      c(Blocks.aV);
      c(Blocks.aW);
      c(Blocks.aX);
      c(Blocks.aZ);
      c(Blocks.bd);
      a(Blocks.be, new ItemMultiTexture(Blocks.be, Blocks.be, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return BlockSilverfish.EnumType.a(var1.i()).c();
         }
      }).b("monsterStoneEgg"));
      a(Blocks.bf, new ItemMultiTexture(Blocks.bf, Blocks.bf, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return BlockStoneBrick.EnumType.a(var1.i()).c();
         }
      }).b("stonebricksmooth"));
      c(Blocks.bg);
      c(Blocks.bh);
      c(Blocks.bi);
      c(Blocks.bj);
      c(Blocks.bk);
      a(Blocks.bn, new ItemColored(Blocks.bn, false));
      c(Blocks.bo);
      c(Blocks.bp);
      c(Blocks.bq);
      c(Blocks.br);
      c(Blocks.bs);
      c(Blocks.bt);
      c(Blocks.bu);
      c(Blocks.bv);
      c(Blocks.bw);
      a(Blocks.bx, new ItemLilyPad(Blocks.bx));
      c(Blocks.by);
      c(Blocks.bz);
      c(Blocks.bA);
      c(Blocks.bC);
      c(Blocks.bG);
      c(Blocks.bH);
      c(Blocks.bI);
      c(Blocks.bJ);
      a(Blocks.bM, new ItemSlab(Blocks.bM, Blocks.bM, Blocks.bL).b("woodSlab"));
      c(Blocks.bO);
      c(Blocks.bP);
      c(Blocks.bQ);
      c(Blocks.bR);
      c(Blocks.bT);
      c(Blocks.bU);
      c(Blocks.bV);
      c(Blocks.bW);
      c(Blocks.bX);
      c(Blocks.bY);
      a(Blocks.bZ, new ItemMultiTexture(Blocks.bZ, Blocks.bZ, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return BlockWall.EnumType.a(var1.i()).c();
         }
      }).b("cobbleWall"));
      c(Blocks.cd);
      a(Blocks.cf, new ItemAnvilBlock(Blocks.cf).b("anvil"));
      c(Blocks.cg);
      c(Blocks.ch);
      c(Blocks.ci);
      c(Blocks.cl);
      c(Blocks.cn);
      c(Blocks.co);
      c(Blocks.cp);
      a(Blocks.cq, new ItemMultiTexture(Blocks.cq, Blocks.cq, new String[]{"default", "chiseled", "lines"}).b("quartzBlock"));
      c(Blocks.cr);
      c(Blocks.cs);
      c(Blocks.ct);
      a(Blocks.cu, new ItemCloth(Blocks.cu).b("clayHardenedStained"));
      c(Blocks.cv);
      c(Blocks.cw);
      c(Blocks.cx);
      a(Blocks.cy, new ItemCloth(Blocks.cy).b("woolCarpet"));
      c(Blocks.cz);
      c(Blocks.cA);
      c(Blocks.cB);
      c(Blocks.cC);
      c(Blocks.cD);
      c(Blocks.cE);
      a(Blocks.cF, new ItemDoublePlant(Blocks.cF, Blocks.cF, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return BlockDoublePlant.EnumPlantType.a(var1.i()).c();
         }
      }).b("doublePlant"));
      a(Blocks.cG, new ItemCloth(Blocks.cG).b("stainedGlass"));
      a(Blocks.cH, new ItemCloth(Blocks.cH).b("stainedGlassPane"));
      a(Blocks.cI, new ItemMultiTexture(Blocks.cI, Blocks.cI, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return BlockPrismarine.EnumType.a(var1.i()).c();
         }
      }).b("prismarine"));
      c(Blocks.cJ);
      a(Blocks.cM, new ItemMultiTexture(Blocks.cM, Blocks.cM, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            return BlockRedSandstone.EnumType.a(var1.i()).c();
         }
      }).b("redSandStone"));
      c(Blocks.cN);
      a(Blocks.cP, new ItemSlab(Blocks.cP, Blocks.cP, Blocks.cO).b("stoneSlab2"));
      a(256, "iron_shovel", new ItemSpade(Item.ToolMaterial.c).c("shovelIron"));
      a(257, "iron_pickaxe", new ItemPickaxe(Item.ToolMaterial.c).c("pickaxeIron"));
      a(258, "iron_axe", new ItemAxe(Item.ToolMaterial.c).c("hatchetIron"));
      a(259, "flint_and_steel", new ItemFlintAndSteel().c("flintAndSteel"));
      a(260, "apple", new ItemFood(4, 0.3F, false).c("apple"));
      a(261, "bow", new ItemBow().c("bow"));
      a(262, "arrow", new Item().c("arrow").a(CreativeTabs.j));
      a(263, "coal", new ItemCoal().c("coal"));
      a(264, "diamond", new Item().c("diamond").a(CreativeTabs.l));
      a(265, "iron_ingot", new Item().c("ingotIron").a(CreativeTabs.l));
      a(266, "gold_ingot", new Item().c("ingotGold").a(CreativeTabs.l));
      a(267, "iron_sword", new ItemSword(Item.ToolMaterial.c).c("swordIron"));
      a(268, "wooden_sword", new ItemSword(Item.ToolMaterial.a).c("swordWood"));
      a(269, "wooden_shovel", new ItemSpade(Item.ToolMaterial.a).c("shovelWood"));
      a(270, "wooden_pickaxe", new ItemPickaxe(Item.ToolMaterial.a).c("pickaxeWood"));
      a(271, "wooden_axe", new ItemAxe(Item.ToolMaterial.a).c("hatchetWood"));
      a(272, "stone_sword", new ItemSword(Item.ToolMaterial.b).c("swordStone"));
      a(273, "stone_shovel", new ItemSpade(Item.ToolMaterial.b).c("shovelStone"));
      a(274, "stone_pickaxe", new ItemPickaxe(Item.ToolMaterial.b).c("pickaxeStone"));
      a(275, "stone_axe", new ItemAxe(Item.ToolMaterial.b).c("hatchetStone"));
      a(276, "diamond_sword", new ItemSword(Item.ToolMaterial.d).c("swordDiamond"));
      a(277, "diamond_shovel", new ItemSpade(Item.ToolMaterial.d).c("shovelDiamond"));
      a(278, "diamond_pickaxe", new ItemPickaxe(Item.ToolMaterial.d).c("pickaxeDiamond"));
      a(279, "diamond_axe", new ItemAxe(Item.ToolMaterial.d).c("hatchetDiamond"));
      a(280, "stick", new Item().n().c("stick").a(CreativeTabs.l));
      a(281, "bowl", new Item().c("bowl").a(CreativeTabs.l));
      a(282, "mushroom_stew", new ItemSoup(6).c("mushroomStew"));
      a(283, "golden_sword", new ItemSword(Item.ToolMaterial.e).c("swordGold"));
      a(284, "golden_shovel", new ItemSpade(Item.ToolMaterial.e).c("shovelGold"));
      a(285, "golden_pickaxe", new ItemPickaxe(Item.ToolMaterial.e).c("pickaxeGold"));
      a(286, "golden_axe", new ItemAxe(Item.ToolMaterial.e).c("hatchetGold"));
      a(287, "string", new ItemReed(Blocks.bS).c("string").a(CreativeTabs.l));
      a(288, "feather", new Item().c("feather").a(CreativeTabs.l));
      a(289, "gunpowder", new Item().c("sulphur").e(PotionHelper.k).a(CreativeTabs.l));
      a(290, "wooden_hoe", new ItemHoe(Item.ToolMaterial.a).c("hoeWood"));
      a(291, "stone_hoe", new ItemHoe(Item.ToolMaterial.b).c("hoeStone"));
      a(292, "iron_hoe", new ItemHoe(Item.ToolMaterial.c).c("hoeIron"));
      a(293, "diamond_hoe", new ItemHoe(Item.ToolMaterial.d).c("hoeDiamond"));
      a(294, "golden_hoe", new ItemHoe(Item.ToolMaterial.e).c("hoeGold"));
      a(295, "wheat_seeds", new ItemSeeds(Blocks.aj, Blocks.ak).c("seeds"));
      a(296, "wheat", new Item().c("wheat").a(CreativeTabs.l));
      a(297, "bread", new ItemFood(5, 0.6F, false).c("bread"));
      a(298, "leather_helmet", new ItemArmor(ItemArmor.ArmorMaterial.a, 0, 0).c("helmetCloth"));
      a(299, "leather_chestplate", new ItemArmor(ItemArmor.ArmorMaterial.a, 0, 1).c("chestplateCloth"));
      a(300, "leather_leggings", new ItemArmor(ItemArmor.ArmorMaterial.a, 0, 2).c("leggingsCloth"));
      a(301, "leather_boots", new ItemArmor(ItemArmor.ArmorMaterial.a, 0, 3).c("bootsCloth"));
      a(302, "chainmail_helmet", new ItemArmor(ItemArmor.ArmorMaterial.b, 1, 0).c("helmetChain"));
      a(303, "chainmail_chestplate", new ItemArmor(ItemArmor.ArmorMaterial.b, 1, 1).c("chestplateChain"));
      a(304, "chainmail_leggings", new ItemArmor(ItemArmor.ArmorMaterial.b, 1, 2).c("leggingsChain"));
      a(305, "chainmail_boots", new ItemArmor(ItemArmor.ArmorMaterial.b, 1, 3).c("bootsChain"));
      a(306, "iron_helmet", new ItemArmor(ItemArmor.ArmorMaterial.c, 2, 0).c("helmetIron"));
      a(307, "iron_chestplate", new ItemArmor(ItemArmor.ArmorMaterial.c, 2, 1).c("chestplateIron"));
      a(308, "iron_leggings", new ItemArmor(ItemArmor.ArmorMaterial.c, 2, 2).c("leggingsIron"));
      a(309, "iron_boots", new ItemArmor(ItemArmor.ArmorMaterial.c, 2, 3).c("bootsIron"));
      a(310, "diamond_helmet", new ItemArmor(ItemArmor.ArmorMaterial.e, 3, 0).c("helmetDiamond"));
      a(311, "diamond_chestplate", new ItemArmor(ItemArmor.ArmorMaterial.e, 3, 1).c("chestplateDiamond"));
      a(312, "diamond_leggings", new ItemArmor(ItemArmor.ArmorMaterial.e, 3, 2).c("leggingsDiamond"));
      a(313, "diamond_boots", new ItemArmor(ItemArmor.ArmorMaterial.e, 3, 3).c("bootsDiamond"));
      a(314, "golden_helmet", new ItemArmor(ItemArmor.ArmorMaterial.d, 4, 0).c("helmetGold"));
      a(315, "golden_chestplate", new ItemArmor(ItemArmor.ArmorMaterial.d, 4, 1).c("chestplateGold"));
      a(316, "golden_leggings", new ItemArmor(ItemArmor.ArmorMaterial.d, 4, 2).c("leggingsGold"));
      a(317, "golden_boots", new ItemArmor(ItemArmor.ArmorMaterial.d, 4, 3).c("bootsGold"));
      a(318, "flint", new Item().c("flint").a(CreativeTabs.l));
      a(319, "porkchop", new ItemFood(3, 0.3F, true).c("porkchopRaw"));
      a(320, "cooked_porkchop", new ItemFood(8, 0.8F, true).c("porkchopCooked"));
      a(321, "painting", new ItemHangingEntity(EntityPainting.class).c("painting"));
      a(322, "golden_apple", new ItemAppleGold(4, 1.2F, false).h().a(Potion.l.H, 5, 1, 1.0F).c("appleGold"));
      a(323, "sign", new ItemSign().c("sign"));
      a(324, "wooden_door", new ItemDoor(Blocks.ao).c("doorOak"));
      Item var0 = new ItemBucket(Blocks.a).c("bucket").c(16);
      a(325, "bucket", var0);
      a(326, "water_bucket", new ItemBucket(Blocks.i).c("bucketWater").c(var0));
      a(327, "lava_bucket", new ItemBucket(Blocks.k).c("bucketLava").c(var0));
      a(328, "minecart", new ItemMinecart(EntityMinecart.EnumMinecartType.a).c("minecart"));
      a(329, "saddle", new ItemSaddle().c("saddle"));
      a(330, "iron_door", new ItemDoor(Blocks.aA).c("doorIron"));
      a(331, "redstone", new ItemRedstone().c("redstone").e(PotionHelper.i));
      a(332, "snowball", new ItemSnowball().c("snowball"));
      a(333, "boat", new ItemBoat().c("boat"));
      a(334, "leather", new Item().c("leather").a(CreativeTabs.l));
      a(335, "milk_bucket", new ItemBucketMilk().c("milk").c(var0));
      a(336, "brick", new Item().c("brick").a(CreativeTabs.l));
      a(337, "clay_ball", new Item().c("clay").a(CreativeTabs.l));
      a(338, "reeds", new ItemReed(Blocks.aM).c("reeds").a(CreativeTabs.l));
      a(339, "paper", new Item().c("paper").a(CreativeTabs.f));
      a(340, "book", new ItemBook().c("book").a(CreativeTabs.f));
      a(341, "slime_ball", new Item().c("slimeball").a(CreativeTabs.f));
      a(342, "chest_minecart", new ItemMinecart(EntityMinecart.EnumMinecartType.b).c("minecartChest"));
      a(343, "furnace_minecart", new ItemMinecart(EntityMinecart.EnumMinecartType.c).c("minecartFurnace"));
      a(344, "egg", new ItemEgg().c("egg"));
      a(345, "compass", new Item().c("compass").a(CreativeTabs.i));
      a(346, "fishing_rod", new ItemFishingRod().c("fishingRod"));
      a(347, "clock", new Item().c("clock").a(CreativeTabs.i));
      a(348, "glowstone_dust", new Item().c("yellowDust").e(PotionHelper.j).a(CreativeTabs.l));
      a(349, "fish", new ItemFishFood(false).c("fish").a(true));
      a(350, "cooked_fish", new ItemFishFood(true).c("fish").a(true));
      a(351, "dye", new ItemDye().c("dyePowder"));
      a(352, "bone", new Item().c("bone").n().a(CreativeTabs.f));
      a(353, "sugar", new Item().c("sugar").e(PotionHelper.b).a(CreativeTabs.l));
      a(354, "cake", new ItemReed(Blocks.ba).c(1).c("cake").a(CreativeTabs.h));
      a(355, "bed", new ItemBed().c(1).c("bed"));
      a(356, "repeater", new ItemReed(Blocks.bb).c("diode").a(CreativeTabs.d));
      a(357, "cookie", new ItemFood(2, 0.1F, false).c("cookie"));
      a(358, "filled_map", new ItemMap().c("map"));
      a(359, "shears", new ItemShears().c("shears"));
      a(360, "melon", new ItemFood(2, 0.3F, false).c("melon"));
      a(361, "pumpkin_seeds", new ItemSeeds(Blocks.bl, Blocks.ak).c("seeds_pumpkin"));
      a(362, "melon_seeds", new ItemSeeds(Blocks.bm, Blocks.ak).c("seeds_melon"));
      a(363, "beef", new ItemFood(3, 0.3F, true).c("beefRaw"));
      a(364, "cooked_beef", new ItemFood(8, 0.8F, true).c("beefCooked"));
      a(365, "chicken", new ItemFood(2, 0.3F, true).a(Potion.s.H, 30, 0, 0.3F).c("chickenRaw"));
      a(366, "cooked_chicken", new ItemFood(6, 0.6F, true).c("chickenCooked"));
      a(367, "rotten_flesh", new ItemFood(4, 0.1F, true).a(Potion.s.H, 30, 0, 0.8F).c("rottenFlesh"));
      a(368, "ender_pearl", new ItemEnderPearl().c("enderPearl"));
      a(369, "blaze_rod", new Item().c("blazeRod").a(CreativeTabs.l).n());
      a(370, "ghast_tear", new Item().c("ghastTear").e(PotionHelper.c).a(CreativeTabs.k));
      a(371, "gold_nugget", new Item().c("goldNugget").a(CreativeTabs.l));
      a(372, "nether_wart", new ItemSeeds(Blocks.bB, Blocks.aW).c("netherStalkSeeds").e("+4"));
      a(373, "potion", new ItemPotion().c("potion"));
      a(374, "glass_bottle", new ItemGlassBottle().c("glassBottle"));
      a(375, "spider_eye", new ItemFood(2, 0.8F, false).a(Potion.u.H, 5, 0, 1.0F).c("spiderEye").e(PotionHelper.d));
      a(376, "fermented_spider_eye", new Item().c("fermentedSpiderEye").e(PotionHelper.e).a(CreativeTabs.k));
      a(377, "blaze_powder", new Item().c("blazePowder").e(PotionHelper.g).a(CreativeTabs.k));
      a(378, "magma_cream", new Item().c("magmaCream").e(PotionHelper.h).a(CreativeTabs.k));
      a(379, "brewing_stand", new ItemReed(Blocks.bD).c("brewingStand").a(CreativeTabs.k));
      a(380, "cauldron", new ItemReed(Blocks.bE).c("cauldron").a(CreativeTabs.k));
      a(381, "ender_eye", new ItemEnderEye().c("eyeOfEnder"));
      a(382, "speckled_melon", new Item().c("speckledMelon").e(PotionHelper.f).a(CreativeTabs.k));
      a(383, "spawn_egg", new ItemMonsterPlacer().c("monsterPlacer"));
      a(384, "experience_bottle", new ItemExpBottle().c("expBottle"));
      a(385, "fire_charge", new ItemFireball().c("fireball"));
      a(386, "writable_book", new ItemWritableBook().c("writingBook").a(CreativeTabs.f));
      a(387, "written_book", new ItemEditableBook().c("writtenBook").c(16));
      a(388, "emerald", new Item().c("emerald").a(CreativeTabs.l));
      a(389, "item_frame", new ItemHangingEntity(EntityItemFrame.class).c("frame"));
      a(390, "flower_pot", new ItemReed(Blocks.ca).c("flowerPot").a(CreativeTabs.c));
      a(391, "carrot", new ItemSeedFood(3, 0.6F, Blocks.cb, Blocks.ak).c("carrots"));
      a(392, "potato", new ItemSeedFood(1, 0.3F, Blocks.cc, Blocks.ak).c("potato"));
      a(393, "baked_potato", new ItemFood(5, 0.6F, false).c("potatoBaked"));
      a(394, "poisonous_potato", new ItemFood(2, 0.3F, false).a(Potion.u.H, 5, 0, 0.6F).c("potatoPoisonous"));
      a(395, "map", new ItemEmptyMap().c("emptyMap"));
      a(396, "golden_carrot", new ItemFood(6, 1.2F, false).c("carrotGolden").e(PotionHelper.l).a(CreativeTabs.k));
      a(397, "skull", new ItemSkull().c("skull"));
      a(398, "carrot_on_a_stick", new ItemCarrotOnAStick().c("carrotOnAStick"));
      a(399, "nether_star", new ItemSimpleFoiled().c("netherStar").a(CreativeTabs.l));
      a(400, "pumpkin_pie", new ItemFood(8, 0.3F, false).c("pumpkinPie").a(CreativeTabs.h));
      a(401, "fireworks", new ItemFirework().c("fireworks"));
      a(402, "firework_charge", new ItemFireworkCharge().c("fireworksCharge").a(CreativeTabs.f));
      a(403, "enchanted_book", new ItemEnchantedBook().c(1).c("enchantedBook"));
      a(404, "comparator", new ItemReed(Blocks.cj).c("comparator").a(CreativeTabs.d));
      a(405, "netherbrick", new Item().c("netherbrick").a(CreativeTabs.l));
      a(406, "quartz", new Item().c("netherquartz").a(CreativeTabs.l));
      a(407, "tnt_minecart", new ItemMinecart(EntityMinecart.EnumMinecartType.d).c("minecartTnt"));
      a(408, "hopper_minecart", new ItemMinecart(EntityMinecart.EnumMinecartType.f).c("minecartHopper"));
      a(409, "prismarine_shard", new Item().c("prismarineShard").a(CreativeTabs.l));
      a(410, "prismarine_crystals", new Item().c("prismarineCrystals").a(CreativeTabs.l));
      a(411, "rabbit", new ItemFood(3, 0.3F, true).c("rabbitRaw"));
      a(412, "cooked_rabbit", new ItemFood(5, 0.6F, true).c("rabbitCooked"));
      a(413, "rabbit_stew", new ItemSoup(10).c("rabbitStew"));
      a(414, "rabbit_foot", new Item().c("rabbitFoot").e(PotionHelper.n).a(CreativeTabs.k));
      a(415, "rabbit_hide", new Item().c("rabbitHide").a(CreativeTabs.l));
      a(416, "armor_stand", new ItemArmorStand().c("armorStand").c(16));
      a(417, "iron_horse_armor", new Item().c("horsearmormetal").c(1).a(CreativeTabs.f));
      a(418, "golden_horse_armor", new Item().c("horsearmorgold").c(1).a(CreativeTabs.f));
      a(419, "diamond_horse_armor", new Item().c("horsearmordiamond").c(1).a(CreativeTabs.f));
      a(420, "lead", new ItemLead().c("leash"));
      a(421, "name_tag", new ItemNameTag().c("nameTag"));
      a(422, "command_block_minecart", new ItemMinecart(EntityMinecart.EnumMinecartType.g).c("minecartCommandBlock").a(null));
      a(423, "mutton", new ItemFood(2, 0.3F, true).c("muttonRaw"));
      a(424, "cooked_mutton", new ItemFood(6, 0.8F, true).c("muttonCooked"));
      a(425, "banner", new ItemBanner().b("banner"));
      a(427, "spruce_door", new ItemDoor(Blocks.ap).c("doorSpruce"));
      a(428, "birch_door", new ItemDoor(Blocks.aq).c("doorBirch"));
      a(429, "jungle_door", new ItemDoor(Blocks.ar).c("doorJungle"));
      a(430, "acacia_door", new ItemDoor(Blocks.as).c("doorAcacia"));
      a(431, "dark_oak_door", new ItemDoor(Blocks.at).c("doorDarkOak"));
      a(2256, "record_13", new ItemRecord("13").c("record"));
      a(2257, "record_cat", new ItemRecord("cat").c("record"));
      a(2258, "record_blocks", new ItemRecord("blocks").c("record"));
      a(2259, "record_chirp", new ItemRecord("chirp").c("record"));
      a(2260, "record_far", new ItemRecord("far").c("record"));
      a(2261, "record_mall", new ItemRecord("mall").c("record"));
      a(2262, "record_mellohi", new ItemRecord("mellohi").c("record"));
      a(2263, "record_stal", new ItemRecord("stal").c("record"));
      a(2264, "record_strad", new ItemRecord("strad").c("record"));
      a(2265, "record_ward", new ItemRecord("ward").c("record"));
      a(2266, "record_11", new ItemRecord("11").c("record"));
      a(2267, "record_wait", new ItemRecord("wait").c("record"));
   }

   private static void c(Block var0) {
      a(var0, new ItemBlock(var0));
   }

   protected static void a(Block var0, Item var1) {
      a(Block.a(var0), Block.c.c(var0), var1);
      a.put(var0, var1);
   }

   private static void a(int var0, String var1, Item var2) {
      a(var0, new ResourceLocation(var1), var2);
   }

   private static void a(int var0, ResourceLocation var1, Item var2) {
      e.a(var0, var1, var2);
   }

   public static enum ToolMaterial {
      a(0, 59, 2.0F, 0.0F, 15),
      b(1, 131, 4.0F, 1.0F, 5),
      c(2, 250, 6.0F, 2.0F, 14),
      d(3, 1561, 8.0F, 3.0F, 10),
      e(0, 32, 12.0F, 0.0F, 22);

      private final int f;
      private final int g;
      private final float h;
      private final float i;
      private final int j;

      private ToolMaterial(int var3, int var4, float var5, float var6, int var7) {
         this.f = var3;
         this.g = var4;
         this.h = var5;
         this.i = var6;
         this.j = var7;
      }

      public int a() {
         return this.g;
      }

      public float b() {
         return this.h;
      }

      public float c() {
         return this.i;
      }

      public int d() {
         return this.f;
      }

      public int e() {
         return this.j;
      }

      public Item f() {
         if (this == a) {
            return Item.a(Blocks.f);
         } else if (this == b) {
            return Item.a(Blocks.e);
         } else if (this == e) {
            return Items.k;
         } else if (this == c) {
            return Items.j;
         } else {
            return this == d ? Items.i : null;
         }
      }
   }
}
