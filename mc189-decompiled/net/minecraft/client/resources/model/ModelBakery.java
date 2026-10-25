package net.minecraft.client.resources.model;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
import net.minecraft.client.renderer.texture.IIconCreator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IRegistry;
import net.minecraft.util.RegistrySimple;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelBakery {
   private static final Set<ResourceLocation> b = Sets.newHashSet(
      new ResourceLocation[]{
         new ResourceLocation("blocks/water_flow"),
         new ResourceLocation("blocks/water_still"),
         new ResourceLocation("blocks/lava_flow"),
         new ResourceLocation("blocks/lava_still"),
         new ResourceLocation("blocks/destroy_stage_0"),
         new ResourceLocation("blocks/destroy_stage_1"),
         new ResourceLocation("blocks/destroy_stage_2"),
         new ResourceLocation("blocks/destroy_stage_3"),
         new ResourceLocation("blocks/destroy_stage_4"),
         new ResourceLocation("blocks/destroy_stage_5"),
         new ResourceLocation("blocks/destroy_stage_6"),
         new ResourceLocation("blocks/destroy_stage_7"),
         new ResourceLocation("blocks/destroy_stage_8"),
         new ResourceLocation("blocks/destroy_stage_9"),
         new ResourceLocation("items/empty_armor_slot_helmet"),
         new ResourceLocation("items/empty_armor_slot_chestplate"),
         new ResourceLocation("items/empty_armor_slot_leggings"),
         new ResourceLocation("items/empty_armor_slot_boots")
      }
   );
   private static final Logger c = LogManager.getLogger();
   protected static final ModelResourceLocation a = new ModelResourceLocation("builtin/missing", "missing");
   private static final Map<String, String> d = Maps.newHashMap();
   private static final Joiner e = Joiner.on(" -> ");
   private final IResourceManager f;
   private final Map<ResourceLocation, TextureAtlasSprite> g = Maps.newHashMap();
   private final Map<ResourceLocation, ModelBlock> h = Maps.newLinkedHashMap();
   private final Map<ModelResourceLocation, ModelBlockDefinition.Variants> i = Maps.newLinkedHashMap();
   private final TextureMap j;
   private final BlockModelShapes k;
   private final FaceBakery l = new FaceBakery();
   private final ItemModelGenerator m = new ItemModelGenerator();
   private RegistrySimple<ModelResourceLocation, IBakedModel> n = new RegistrySimple<>();
   private static final ModelBlock o = ModelBlock.a(
      "{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}"
   );
   private static final ModelBlock p = ModelBlock.a(
      "{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}"
   );
   private static final ModelBlock q = ModelBlock.a(
      "{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}"
   );
   private static final ModelBlock r = ModelBlock.a(
      "{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}"
   );
   private Map<String, ResourceLocation> s = Maps.newLinkedHashMap();
   private final Map<ResourceLocation, ModelBlockDefinition> t = Maps.newHashMap();
   private Map<Item, List<String>> u = Maps.newIdentityHashMap();

   public ModelBakery(IResourceManager var1, TextureMap var2, BlockModelShapes var3) {
      this.f = var1;
      this.j = var2;
      this.k = var3;
   }

   public IRegistry<ModelResourceLocation, IBakedModel> a() {
      this.b();
      this.h();
      this.j();
      this.l();
      this.f();
      return this.n;
   }

   private void b() {
      this.a(this.k.a().a().values());
      this.i
         .put(
            a,
            new ModelBlockDefinition.Variants(
               a.c(),
               Lists.newArrayList(new ModelBlockDefinition.Variant[]{new ModelBlockDefinition.Variant(new ResourceLocation(a.a()), ModelRotation.a, false, 1)})
            )
         );
      ResourceLocation var1 = new ResourceLocation("item_frame");
      ModelBlockDefinition var2 = this.a(var1);
      this.a(var2, new ModelResourceLocation(var1, "normal"));
      this.a(var2, new ModelResourceLocation(var1, "map"));
      this.c();
      this.d();
   }

   private void a(Collection<ModelResourceLocation> var1) {
      for (ModelResourceLocation var3 : var1) {
         try {
            ModelBlockDefinition var4 = this.a(var3);

            try {
               this.a(var4, var3);
            } catch (Exception var6) {
               c.warn("Unable to load variant: " + var3.c() + " from " + var3);
            }
         } catch (Exception var7) {
            c.warn("Unable to load definition " + var3, var7);
         }
      }
   }

   private void a(ModelBlockDefinition var1, ModelResourceLocation var2) {
      this.i.put(var2, var1.b(var2.c()));
   }

   private ModelBlockDefinition a(ResourceLocation var1) {
      ResourceLocation var2 = this.b(var1);
      ModelBlockDefinition var3 = this.t.get(var2);
      if (var3 == null) {
         ArrayList var4 = Lists.newArrayList();

         try {
            for (IResource var6 : this.f.b(var2)) {
               InputStream var7 = null;

               try {
                  var7 = var6.b();
                  ModelBlockDefinition var8 = ModelBlockDefinition.a(new InputStreamReader(var7, Charsets.UTF_8));
                  var4.add(var8);
               } catch (Exception var13) {
                  throw new RuntimeException(
                     "Encountered an exception when loading model definition of '" + var1 + "' from: '" + var6.a() + "' in resourcepack: '" + var6.d() + "'",
                     var13
                  );
               } finally {
                  IOUtils.closeQuietly(var7);
               }
            }
         } catch (IOException var15) {
            throw new RuntimeException("Encountered an exception when loading model definition of model " + var2.toString(), var15);
         }

         var3 = new ModelBlockDefinition(var4);
         this.t.put(var2, var3);
      }

      return var3;
   }

   private ResourceLocation b(ResourceLocation var1) {
      return new ResourceLocation(var1.b(), "blockstates/" + var1.a() + ".json");
   }

   private void c() {
      for (ModelResourceLocation var2 : this.i.keySet()) {
         for (ModelBlockDefinition.Variant var4 : this.i.get(var2).b()) {
            ResourceLocation var5 = var4.a();
            if (this.h.get(var5) == null) {
               try {
                  ModelBlock var6 = this.c(var5);
                  this.h.put(var5, var6);
               } catch (Exception var7) {
                  c.warn("Unable to load block model: '" + var5 + "' for variant: '" + var2 + "'", var7);
               }
            }
         }
      }
   }

   private ModelBlock c(ResourceLocation var1) throws IOException {
      String var3 = var1.a();
      if ("builtin/generated".equals(var3)) {
         return o;
      } else if ("builtin/compass".equals(var3)) {
         return p;
      } else if ("builtin/clock".equals(var3)) {
         return q;
      } else if ("builtin/entity".equals(var3)) {
         return r;
      } else {
         Object var2;
         if (var3.startsWith("builtin/")) {
            String var4 = var3.substring("builtin/".length());
            String var5 = d.get(var4);
            if (var5 == null) {
               throw new FileNotFoundException(var1.toString());
            }

            var2 = new StringReader(var5);
         } else {
            IResource var9 = this.f.a(this.d(var1));
            var2 = new InputStreamReader(var9.b(), Charsets.UTF_8);
         }

         ModelBlock var11;
         try {
            ModelBlock var10 = ModelBlock.a((Reader)var2);
            var10.b = var1.toString();
            var11 = var10;
         } finally {
            var2.close();
         }

         return var11;
      }
   }

   private ResourceLocation d(ResourceLocation var1) {
      return new ResourceLocation(var1.b(), "models/" + var1.a() + ".json");
   }

   private void d() {
      this.e();

      for (Item var2 : Item.e) {
         for (String var5 : this.a(var2)) {
            ResourceLocation var6 = this.a(var5);
            this.s.put(var5, var6);
            if (this.h.get(var6) == null) {
               try {
                  ModelBlock var7 = this.c(var6);
                  this.h.put(var6, var7);
               } catch (Exception var8) {
                  c.warn("Unable to load item model: '" + var6 + "' for item: '" + Item.e.c(var2) + "'", var8);
               }
            }
         }
      }
   }

   private void e() {
      this.u
         .put(
            Item.a(Blocks.b),
            Lists.newArrayList(new String[]{"stone", "granite", "granite_smooth", "diorite", "diorite_smooth", "andesite", "andesite_smooth"})
         );
      this.u.put(Item.a(Blocks.d), Lists.newArrayList(new String[]{"dirt", "coarse_dirt", "podzol"}));
      this.u
         .put(
            Item.a(Blocks.f),
            Lists.newArrayList(new String[]{"oak_planks", "spruce_planks", "birch_planks", "jungle_planks", "acacia_planks", "dark_oak_planks"})
         );
      this.u
         .put(
            Item.a(Blocks.g),
            Lists.newArrayList(new String[]{"oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling"})
         );
      this.u.put(Item.a(Blocks.m), Lists.newArrayList(new String[]{"sand", "red_sand"}));
      this.u.put(Item.a(Blocks.r), Lists.newArrayList(new String[]{"oak_log", "spruce_log", "birch_log", "jungle_log"}));
      this.u.put(Item.a(Blocks.t), Lists.newArrayList(new String[]{"oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves"}));
      this.u.put(Item.a(Blocks.v), Lists.newArrayList(new String[]{"sponge", "sponge_wet"}));
      this.u.put(Item.a(Blocks.A), Lists.newArrayList(new String[]{"sandstone", "chiseled_sandstone", "smooth_sandstone"}));
      this.u.put(Item.a(Blocks.cM), Lists.newArrayList(new String[]{"red_sandstone", "chiseled_red_sandstone", "smooth_red_sandstone"}));
      this.u.put(Item.a(Blocks.H), Lists.newArrayList(new String[]{"dead_bush", "tall_grass", "fern"}));
      this.u.put(Item.a(Blocks.I), Lists.newArrayList(new String[]{"dead_bush"}));
      this.u
         .put(
            Item.a(Blocks.L),
            Lists.newArrayList(
               new String[]{
                  "black_wool",
                  "red_wool",
                  "green_wool",
                  "brown_wool",
                  "blue_wool",
                  "purple_wool",
                  "cyan_wool",
                  "silver_wool",
                  "gray_wool",
                  "pink_wool",
                  "lime_wool",
                  "yellow_wool",
                  "light_blue_wool",
                  "magenta_wool",
                  "orange_wool",
                  "white_wool"
               }
            )
         );
      this.u.put(Item.a(Blocks.N), Lists.newArrayList(new String[]{"dandelion"}));
      this.u
         .put(
            Item.a(Blocks.O),
            Lists.newArrayList(
               new String[]{"poppy", "blue_orchid", "allium", "houstonia", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy"}
            )
         );
      this.u
         .put(
            Item.a(Blocks.U),
            Lists.newArrayList(
               new String[]{"stone_slab", "sandstone_slab", "cobblestone_slab", "brick_slab", "stone_brick_slab", "nether_brick_slab", "quartz_slab"}
            )
         );
      this.u.put(Item.a(Blocks.cP), Lists.newArrayList(new String[]{"red_sandstone_slab"}));
      this.u
         .put(
            Item.a(Blocks.cG),
            Lists.newArrayList(
               new String[]{
                  "black_stained_glass",
                  "red_stained_glass",
                  "green_stained_glass",
                  "brown_stained_glass",
                  "blue_stained_glass",
                  "purple_stained_glass",
                  "cyan_stained_glass",
                  "silver_stained_glass",
                  "gray_stained_glass",
                  "pink_stained_glass",
                  "lime_stained_glass",
                  "yellow_stained_glass",
                  "light_blue_stained_glass",
                  "magenta_stained_glass",
                  "orange_stained_glass",
                  "white_stained_glass"
               }
            )
         );
      this.u
         .put(
            Item.a(Blocks.be),
            Lists.newArrayList(
               new String[]{
                  "stone_monster_egg",
                  "cobblestone_monster_egg",
                  "stone_brick_monster_egg",
                  "mossy_brick_monster_egg",
                  "cracked_brick_monster_egg",
                  "chiseled_brick_monster_egg"
               }
            )
         );
      this.u.put(Item.a(Blocks.bf), Lists.newArrayList(new String[]{"stonebrick", "mossy_stonebrick", "cracked_stonebrick", "chiseled_stonebrick"}));
      this.u.put(Item.a(Blocks.bM), Lists.newArrayList(new String[]{"oak_slab", "spruce_slab", "birch_slab", "jungle_slab", "acacia_slab", "dark_oak_slab"}));
      this.u.put(Item.a(Blocks.bZ), Lists.newArrayList(new String[]{"cobblestone_wall", "mossy_cobblestone_wall"}));
      this.u.put(Item.a(Blocks.cf), Lists.newArrayList(new String[]{"anvil_intact", "anvil_slightly_damaged", "anvil_very_damaged"}));
      this.u.put(Item.a(Blocks.cq), Lists.newArrayList(new String[]{"quartz_block", "chiseled_quartz_block", "quartz_column"}));
      this.u
         .put(
            Item.a(Blocks.cu),
            Lists.newArrayList(
               new String[]{
                  "black_stained_hardened_clay",
                  "red_stained_hardened_clay",
                  "green_stained_hardened_clay",
                  "brown_stained_hardened_clay",
                  "blue_stained_hardened_clay",
                  "purple_stained_hardened_clay",
                  "cyan_stained_hardened_clay",
                  "silver_stained_hardened_clay",
                  "gray_stained_hardened_clay",
                  "pink_stained_hardened_clay",
                  "lime_stained_hardened_clay",
                  "yellow_stained_hardened_clay",
                  "light_blue_stained_hardened_clay",
                  "magenta_stained_hardened_clay",
                  "orange_stained_hardened_clay",
                  "white_stained_hardened_clay"
               }
            )
         );
      this.u
         .put(
            Item.a(Blocks.cH),
            Lists.newArrayList(
               new String[]{
                  "black_stained_glass_pane",
                  "red_stained_glass_pane",
                  "green_stained_glass_pane",
                  "brown_stained_glass_pane",
                  "blue_stained_glass_pane",
                  "purple_stained_glass_pane",
                  "cyan_stained_glass_pane",
                  "silver_stained_glass_pane",
                  "gray_stained_glass_pane",
                  "pink_stained_glass_pane",
                  "lime_stained_glass_pane",
                  "yellow_stained_glass_pane",
                  "light_blue_stained_glass_pane",
                  "magenta_stained_glass_pane",
                  "orange_stained_glass_pane",
                  "white_stained_glass_pane"
               }
            )
         );
      this.u.put(Item.a(Blocks.u), Lists.newArrayList(new String[]{"acacia_leaves", "dark_oak_leaves"}));
      this.u.put(Item.a(Blocks.s), Lists.newArrayList(new String[]{"acacia_log", "dark_oak_log"}));
      this.u.put(Item.a(Blocks.cI), Lists.newArrayList(new String[]{"prismarine", "prismarine_bricks", "dark_prismarine"}));
      this.u
         .put(
            Item.a(Blocks.cy),
            Lists.newArrayList(
               new String[]{
                  "black_carpet",
                  "red_carpet",
                  "green_carpet",
                  "brown_carpet",
                  "blue_carpet",
                  "purple_carpet",
                  "cyan_carpet",
                  "silver_carpet",
                  "gray_carpet",
                  "pink_carpet",
                  "lime_carpet",
                  "yellow_carpet",
                  "light_blue_carpet",
                  "magenta_carpet",
                  "orange_carpet",
                  "white_carpet"
               }
            )
         );
      this.u.put(Item.a(Blocks.cF), Lists.newArrayList(new String[]{"sunflower", "syringa", "double_grass", "double_fern", "double_rose", "paeonia"}));
      this.u.put(Items.f, Lists.newArrayList(new String[]{"bow", "bow_pulling_0", "bow_pulling_1", "bow_pulling_2"}));
      this.u.put(Items.h, Lists.newArrayList(new String[]{"coal", "charcoal"}));
      this.u.put(Items.aR, Lists.newArrayList(new String[]{"fishing_rod", "fishing_rod_cast"}));
      this.u.put(Items.aU, Lists.newArrayList(new String[]{"cod", "salmon", "clownfish", "pufferfish"}));
      this.u.put(Items.aV, Lists.newArrayList(new String[]{"cooked_cod", "cooked_salmon"}));
      this.u
         .put(
            Items.aW,
            Lists.newArrayList(
               new String[]{
                  "dye_black",
                  "dye_red",
                  "dye_green",
                  "dye_brown",
                  "dye_blue",
                  "dye_purple",
                  "dye_cyan",
                  "dye_silver",
                  "dye_gray",
                  "dye_pink",
                  "dye_lime",
                  "dye_yellow",
                  "dye_light_blue",
                  "dye_magenta",
                  "dye_orange",
                  "dye_white"
               }
            )
         );
      this.u.put(Items.bz, Lists.newArrayList(new String[]{"bottle_drinkable", "bottle_splash"}));
      this.u.put(Items.bX, Lists.newArrayList(new String[]{"skull_skeleton", "skull_wither", "skull_zombie", "skull_char", "skull_creeper"}));
      this.u.put(Item.a(Blocks.bo), Lists.newArrayList(new String[]{"oak_fence_gate"}));
      this.u.put(Item.a(Blocks.aO), Lists.newArrayList(new String[]{"oak_fence"}));
      this.u.put(Items.aq, Lists.newArrayList(new String[]{"oak_door"}));
   }

   private List<String> a(Item var1) {
      List var2 = this.u.get(var1);
      if (var2 == null) {
         var2 = Collections.singletonList(Item.e.c(var1).toString());
      }

      return var2;
   }

   private ResourceLocation a(String var1) {
      ResourceLocation var2 = new ResourceLocation(var1);
      return new ResourceLocation(var2.b(), "item/" + var2.a());
   }

   private void f() {
      for (ModelResourceLocation var2 : this.i.keySet()) {
         WeightedBakedModel.Builder var3 = new WeightedBakedModel.Builder();
         int var4 = 0;

         for (ModelBlockDefinition.Variant var6 : this.i.get(var2).b()) {
            ModelBlock var7 = this.h.get(var6.a());
            if (var7 != null && var7.d()) {
               var4++;
               var3.a(this.a(var7, var6.b(), var6.c()), var6.d());
            } else {
               c.warn("Missing model for: " + var2);
            }
         }

         if (var4 == 0) {
            c.warn("No weighted models for: " + var2);
         } else if (var4 == 1) {
            this.n.a(var2, var3.b());
         } else {
            this.n.a(var2, var3.a());
         }
      }

      for (Entry var9 : this.s.entrySet()) {
         ResourceLocation var10 = (ResourceLocation)var9.getValue();
         ModelResourceLocation var11 = new ModelResourceLocation((String)var9.getKey(), "inventory");
         ModelBlock var12 = this.h.get(var10);
         if (var12 == null || !var12.d()) {
            c.warn("Missing model for: " + var10);
         } else if (this.c(var12)) {
            this.n.a(var11, new BuiltInModel(var12.g()));
         } else {
            this.n.a(var11, this.a(var12, ModelRotation.a, false));
         }
      }
   }

   private Set<ResourceLocation> g() {
      HashSet var1 = Sets.newHashSet();
      ArrayList var2 = Lists.newArrayList(this.i.keySet());
      Collections.sort(var2, new Comparator<ModelResourceLocation>() {
         public int a(ModelResourceLocation var1, ModelResourceLocation var2x) {
            return var1.toString().compareTo(var2x.toString());
         }
      });

      for (ModelResourceLocation var4 : var2) {
         ModelBlockDefinition.Variants var5 = this.i.get(var4);

         for (ModelBlockDefinition.Variant var7 : var5.b()) {
            ModelBlock var8 = this.h.get(var7.a());
            if (var8 == null) {
               c.warn("Missing model for: " + var4);
            } else {
               var1.addAll(this.a(var8));
            }
         }
      }

      var1.addAll(b);
      return var1;
   }

   private IBakedModel a(ModelBlock var1, ModelRotation var2, boolean var3) {
      TextureAtlasSprite var4 = this.g.get(new ResourceLocation(var1.c("particle")));
      SimpleBakedModel.Builder var5 = new SimpleBakedModel.Builder(var1).a(var4);

      for (BlockPart var7 : var1.a()) {
         for (EnumFacing var9 : var7.c.keySet()) {
            BlockPartFace var10 = var7.c.get(var9);
            TextureAtlasSprite var11 = this.g.get(new ResourceLocation(var1.c(var10.d)));
            if (var10.b == null) {
               var5.a(this.a(var7, var10, var11, var9, var2, var3));
            } else {
               var5.a(var2.a(var10.b), this.a(var7, var10, var11, var9, var2, var3));
            }
         }
      }

      return var5.b();
   }

   private BakedQuad a(BlockPart var1, BlockPartFace var2, TextureAtlasSprite var3, EnumFacing var4, ModelRotation var5, boolean var6) {
      return this.l.a(var1.a, var1.b, var2, var3, var4, var5, var1.d, var6, var1.e);
   }

   private void h() {
      this.i();

      for (ModelBlock var2 : this.h.values()) {
         var2.a(this.h);
      }

      ModelBlock.b(this.h);
   }

   private void i() {
      ArrayDeque var1 = Queues.newArrayDeque();
      HashSet var2 = Sets.newHashSet();

      for (ResourceLocation var4 : this.h.keySet()) {
         var2.add(var4);
         ResourceLocation var5 = this.h.get(var4).e();
         if (var5 != null) {
            var1.add(var5);
         }
      }

      while (!var1.isEmpty()) {
         ResourceLocation var7 = (ResourceLocation)var1.pop();

         try {
            if (this.h.get(var7) != null) {
               continue;
            }

            ModelBlock var8 = this.c(var7);
            this.h.put(var7, var8);
            ResourceLocation var9 = var8.e();
            if (var9 != null && !var2.contains(var9)) {
               var1.add(var9);
            }
         } catch (Exception var6) {
            c.warn("In parent chain: " + e.join(this.e(var7)) + "; unable to load model: '" + var7 + "'", var6);
         }

         var2.add(var7);
      }
   }

   private List<ResourceLocation> e(ResourceLocation var1) {
      ArrayList var2 = Lists.newArrayList(new ResourceLocation[]{var1});
      ResourceLocation var3 = var1;

      while ((var3 = this.f(var3)) != null) {
         var2.add(0, var3);
      }

      return var2;
   }

   private ResourceLocation f(ResourceLocation var1) {
      for (Entry var3 : this.h.entrySet()) {
         ModelBlock var4 = (ModelBlock)var3.getValue();
         if (var4 != null && var1.equals(var4.e())) {
            return (ResourceLocation)var3.getKey();
         }
      }

      return null;
   }

   private Set<ResourceLocation> a(ModelBlock var1) {
      HashSet var2 = Sets.newHashSet();

      for (BlockPart var4 : var1.a()) {
         for (BlockPartFace var6 : var4.c.values()) {
            ResourceLocation var7 = new ResourceLocation(var1.c(var6.d));
            var2.add(var7);
         }
      }

      var2.add(new ResourceLocation(var1.c("particle")));
      return var2;
   }

   private void j() {
      final Set var1 = this.g();
      var1.addAll(this.k());
      var1.remove(TextureMap.f);
      IIconCreator var2 = new IIconCreator() {
         @Override
         public void a(TextureMap var1x) {
            for (ResourceLocation var3 : var1) {
               TextureAtlasSprite var4 = var1x.a(var3);
               ModelBakery.this.g.put(var3, var4);
            }
         }
      };
      this.j.a(this.f, var2);
      this.g.put(new ResourceLocation("missingno"), this.j.f());
   }

   private Set<ResourceLocation> k() {
      HashSet var1 = Sets.newHashSet();

      for (ResourceLocation var3 : this.s.values()) {
         ModelBlock var4 = this.h.get(var3);
         if (var4 != null) {
            var1.add(new ResourceLocation(var4.c("particle")));
            if (this.b(var4)) {
               for (String var11 : ItemModelGenerator.a) {
                  ResourceLocation var12 = new ResourceLocation(var4.c(var11));
                  if (var4.f() == p && !TextureMap.f.equals(var12)) {
                     TextureAtlasSprite.b(var12.toString());
                  } else if (var4.f() == q && !TextureMap.f.equals(var12)) {
                     TextureAtlasSprite.a(var12.toString());
                  }

                  var1.add(var12);
               }
            } else if (!this.c(var4)) {
               for (BlockPart var6 : var4.a()) {
                  for (BlockPartFace var8 : var6.c.values()) {
                     ResourceLocation var9 = new ResourceLocation(var4.c(var8.d));
                     var1.add(var9);
                  }
               }
            }
         }
      }

      return var1;
   }

   private boolean b(ModelBlock var1) {
      if (var1 == null) {
         return false;
      } else {
         ModelBlock var2 = var1.f();
         return var2 == o || var2 == p || var2 == q;
      }
   }

   private boolean c(ModelBlock var1) {
      if (var1 == null) {
         return false;
      } else {
         ModelBlock var2 = var1.f();
         return var2 == r;
      }
   }

   private void l() {
      for (ResourceLocation var2 : this.s.values()) {
         ModelBlock var3 = this.h.get(var2);
         if (this.b(var3)) {
            ModelBlock var4 = this.d(var3);
            if (var4 != null) {
               var4.b = var2.toString();
            }

            this.h.put(var2, var4);
         } else if (this.c(var3)) {
            this.h.put(var2, var3);
         }
      }

      for (TextureAtlasSprite var6 : this.g.values()) {
         if (!var6.m()) {
            var6.l();
         }
      }
   }

   private ModelBlock d(ModelBlock var1) {
      return this.m.a(this.j, var1);
   }

   static {
      d.put(
         "missing",
         "{ \"textures\": {   \"particle\": \"missingno\",   \"missingno\": \"missingno\"}, \"elements\": [ {     \"from\": [ 0, 0, 0 ],     \"to\": [ 16, 16, 16 ],     \"faces\": {         \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"down\", \"texture\": \"#missingno\" },         \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"up\", \"texture\": \"#missingno\" },         \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"north\", \"texture\": \"#missingno\" },         \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"south\", \"texture\": \"#missingno\" },         \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"west\", \"texture\": \"#missingno\" },         \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"east\", \"texture\": \"#missingno\" }    }}]}"
      );
      o.b = "generation marker";
      p.b = "compass generation marker";
      q.b = "class generation marker";
      r.b = "block entity marker";
   }
}
