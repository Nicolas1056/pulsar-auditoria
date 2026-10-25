package net.minecraft.client.renderer.entity;

import java.util.List;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockWall;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3i;

public class RenderItem implements IResourceManagerReloadListener {
   private static final ResourceLocation b = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   private boolean c = true;
   public float a;
   private final ItemModelMesher d;
   private final TextureManager e;

   public RenderItem(TextureManager var1, ModelManager var2) {
      this.e = var1;
      this.d = new ItemModelMesher(var2);
      this.b();
   }

   public void a(boolean var1) {
      this.c = var1;
   }

   public ItemModelMesher a() {
      return this.d;
   }

   protected void a(Item var1, int var2, String var3) {
      this.d.a(var1, var2, new ModelResourceLocation(var3, "inventory"));
   }

   protected void a(Block var1, int var2, String var3) {
      this.a(Item.a(var1), var2, var3);
   }

   private void a(Block var1, String var2) {
      this.a(var1, 0, var2);
   }

   private void a(Item var1, String var2) {
      this.a(var1, 0, var2);
   }

   private void a(IBakedModel var1, ItemStack var2) {
      this.a(var1, -1, var2);
   }

   private void a(IBakedModel var1, int var2) {
      this.a(var1, var2, null);
   }

   private void a(IBakedModel var1, int var2, ItemStack var3) {
      Tessellator var4 = Tessellator.a();
      WorldRenderer var5 = var4.c();
      var5.a(7, DefaultVertexFormats.b);

      for (EnumFacing var9 : EnumFacing.values()) {
         this.a(var5, var1.a(var9), var2, var3);
      }

      this.a(var5, var1.a(), var2, var3);
      var4.b();
   }

   public void a(ItemStack var1, IBakedModel var2) {
      if (var1 != null) {
         GlStateManager.E();
         GlStateManager.a(0.5F, 0.5F, 0.5F);
         if (var2.d()) {
            GlStateManager.b(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.b(-0.5F, -0.5F, -0.5F);
            GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.B();
            TileEntityItemStackRenderer.a.a(var1);
         } else {
            GlStateManager.b(-0.5F, -0.5F, -0.5F);
            this.a(var2, var1);
            if (var1.t()) {
               this.a(var2);
            }
         }

         GlStateManager.F();
      }
   }

   private void a(IBakedModel var1) {
      GlStateManager.a(false);
      GlStateManager.c(514);
      GlStateManager.f();
      GlStateManager.b(768, 1);
      this.e.a(b);
      GlStateManager.n(5890);
      GlStateManager.E();
      GlStateManager.a(8.0F, 8.0F, 8.0F);
      float var2 = (float)(Minecraft.J() % 3000L) / 3000.0F / 8.0F;
      GlStateManager.b(var2, 0.0F, 0.0F);
      GlStateManager.b(-50.0F, 0.0F, 0.0F, 1.0F);
      this.a(var1, -8372020);
      GlStateManager.F();
      GlStateManager.E();
      GlStateManager.a(8.0F, 8.0F, 8.0F);
      float var3 = (float)(Minecraft.J() % 4873L) / 4873.0F / 8.0F;
      GlStateManager.b(-var3, 0.0F, 0.0F);
      GlStateManager.b(10.0F, 0.0F, 0.0F, 1.0F);
      this.a(var1, -8372020);
      GlStateManager.F();
      GlStateManager.n(5888);
      GlStateManager.b(770, 771);
      GlStateManager.e();
      GlStateManager.c(515);
      GlStateManager.a(true);
      this.e.a(TextureMap.g);
   }

   private void a(WorldRenderer var1, BakedQuad var2) {
      Vec3i var3 = var2.d().m();
      var1.b((float)var3.n(), (float)var3.o(), (float)var3.p());
   }

   private void a(WorldRenderer var1, BakedQuad var2, int var3) {
      var1.a(var2.a());
      var1.a(var3);
      this.a(var1, var2);
   }

   private void a(WorldRenderer var1, List<BakedQuad> var2, int var3, ItemStack var4) {
      boolean var5 = var3 == -1 && var4 != null;
      int var6 = 0;

      for (int var7 = var2.size(); var6 < var7; var6++) {
         BakedQuad var8 = (BakedQuad)var2.get(var6);
         int var9 = var3;
         if (var5 && var8.b()) {
            var9 = var4.b().a(var4, var8.c());
            if (EntityRenderer.a) {
               var9 = TextureUtil.c(var9);
            }

            var9 |= -16777216;
         }

         this.a(var1, var8, var9);
      }
   }

   public boolean a(ItemStack var1) {
      IBakedModel var2 = this.d.a(var1);
      return var2 == null ? false : var2.c();
   }

   private void b(ItemStack var1) {
      IBakedModel var2 = this.d.a(var1);
      Item var3 = var1.b();
      if (var3 != null) {
         boolean var4 = var2.c();
         if (!var4) {
            GlStateManager.a(2.0F, 2.0F, 2.0F);
         }

         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   public void a(ItemStack var1, ItemCameraTransforms.TransformType var2) {
      if (var1 != null) {
         IBakedModel var3 = this.d.a(var1);
         this.a(var1, var3, var2);
      }
   }

   public void a(ItemStack var1, EntityLivingBase var2, ItemCameraTransforms.TransformType var3) {
      if (var1 != null && var2 != null) {
         IBakedModel var4 = this.d.a(var1);
         if (var2 instanceof EntityPlayer) {
            EntityPlayer var5 = (EntityPlayer)var2;
            Item var6 = var1.b();
            ModelResourceLocation var7 = null;
            if (var6 == Items.aR && var5.bG != null) {
               var7 = new ModelResourceLocation("fishing_rod_cast", "inventory");
            } else if (var6 == Items.f && var5.bQ() != null) {
               int var8 = var1.l() - var5.bR();
               if (var8 >= 18) {
                  var7 = new ModelResourceLocation("bow_pulling_2", "inventory");
               } else if (var8 > 13) {
                  var7 = new ModelResourceLocation("bow_pulling_1", "inventory");
               } else if (var8 > 0) {
                  var7 = new ModelResourceLocation("bow_pulling_0", "inventory");
               }
            }

            if (var7 != null) {
               var4 = this.d.a().a(var7);
            }
         }

         this.a(var1, var4, var3);
      }
   }

   protected void a(ItemStack var1, IBakedModel var2, ItemCameraTransforms.TransformType var3) {
      this.e.a(TextureMap.g);
      this.e.b(TextureMap.g).b(false, false);
      this.b(var1);
      GlStateManager.B();
      GlStateManager.a(516, 0.1F);
      GlStateManager.l();
      GlStateManager.a(770, 771, 1, 0);
      GlStateManager.E();
      ItemCameraTransforms var4 = var2.f();
      var4.a(var3);
      if (this.a(var4.b(var3))) {
         GlStateManager.e(1028);
      }

      this.a(var1, var2);
      GlStateManager.e(1029);
      GlStateManager.F();
      GlStateManager.C();
      GlStateManager.k();
      this.e.a(TextureMap.g);
      this.e.b(TextureMap.g).a();
   }

   private boolean a(ItemTransformVec3f var1) {
      return var1.d.x < 0.0F ^ var1.d.y < 0.0F ^ var1.d.z < 0.0F;
   }

   public void a(ItemStack var1, int var2, int var3) {
      IBakedModel var4 = this.d.a(var1);
      GlStateManager.E();
      this.e.a(TextureMap.g);
      this.e.b(TextureMap.g).b(false, false);
      GlStateManager.B();
      GlStateManager.d();
      GlStateManager.a(516, 0.1F);
      GlStateManager.l();
      GlStateManager.b(770, 771);
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.a(var2, var3, var4.c());
      var4.f().a(ItemCameraTransforms.TransformType.e);
      this.a(var1, var4);
      GlStateManager.c();
      GlStateManager.C();
      GlStateManager.f();
      GlStateManager.F();
      this.e.a(TextureMap.g);
      this.e.b(TextureMap.g).a();
   }

   private void a(int var1, int var2, boolean var3) {
      GlStateManager.b((float)var1, (float)var2, 100.0F + this.a);
      GlStateManager.b(8.0F, 8.0F, 0.0F);
      GlStateManager.a(1.0F, 1.0F, -1.0F);
      GlStateManager.a(0.5F, 0.5F, 0.5F);
      if (var3) {
         GlStateManager.a(40.0F, 40.0F, 40.0F);
         GlStateManager.b(210.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.b(-135.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.e();
      } else {
         GlStateManager.a(64.0F, 64.0F, 64.0F);
         GlStateManager.b(180.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.f();
      }
   }

   public void b(final ItemStack var1, int var2, int var3) {
      if (var1 != null && var1.b() != null) {
         this.a += 50.0F;

         try {
            this.a(var1, var2, var3);
         } catch (Throwable var7) {
            CrashReport var5 = CrashReport.a(var7, "Rendering item");
            CrashReportCategory var6 = var5.a("Item being rendered");
            var6.a("Item Type", new Callable<String>() {
               public String a() throws Exception {
                  return String.valueOf(var1.b());
               }
            });
            var6.a("Item Aux", new Callable<String>() {
               public String a() throws Exception {
                  return String.valueOf(var1.i());
               }
            });
            var6.a("Item NBT", new Callable<String>() {
               public String a() throws Exception {
                  return String.valueOf(var1.o());
               }
            });
            var6.a("Item Foil", new Callable<String>() {
               public String a() throws Exception {
                  return String.valueOf(var1.t());
               }
            });
            throw new ReportedException(var5);
         }

         this.a -= 50.0F;
      }
   }

   public void a(FontRenderer var1, ItemStack var2, int var3, int var4) {
      this.a(var1, var2, var3, var4, null);
   }

   public void a(FontRenderer var1, ItemStack var2, int var3, int var4, String var5) {
      if (var2 != null) {
         if (var2.b != 1 || var5 != null) {
            String var6 = var5 == null ? String.valueOf(var2.b) : var5;
            if (var5 == null && var2.b < 1) {
               var6 = EnumChatFormatting.m + String.valueOf(var2.b);
            }

            GlStateManager.f();
            GlStateManager.i();
            GlStateManager.k();
            var1.a(var6, (float)(var3 + 19 - 2 - var1.a(var6)), (float)(var4 + 6 + 3), 16777215);
            GlStateManager.e();
            GlStateManager.j();
         }

         if (var2.g()) {
            int var10 = (int)Math.round(13.0 - var2.h() * 13.0 / var2.j());
            int var7 = (int)Math.round(255.0 - var2.h() * 255.0 / var2.j());
            GlStateManager.f();
            GlStateManager.i();
            GlStateManager.x();
            GlStateManager.c();
            GlStateManager.k();
            Tessellator var8 = Tessellator.a();
            WorldRenderer var9 = var8.c();
            this.a(var9, var3 + 2, var4 + 13, 13, 2, 0, 0, 0, 255);
            this.a(var9, var3 + 2, var4 + 13, 12, 1, (255 - var7) / 4, 64, 0, 255);
            this.a(var9, var3 + 2, var4 + 13, var10, 1, 255 - var7, var7, 0, 255);
            GlStateManager.l();
            GlStateManager.d();
            GlStateManager.w();
            GlStateManager.e();
            GlStateManager.j();
         }
      }
   }

   private void a(WorldRenderer var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      var1.a(7, DefaultVertexFormats.f);
      var1.b((double)(var2 + 0), (double)(var3 + 0), 0.0).b(var6, var7, var8, var9).d();
      var1.b((double)(var2 + 0), (double)(var3 + var5), 0.0).b(var6, var7, var8, var9).d();
      var1.b((double)(var2 + var4), (double)(var3 + var5), 0.0).b(var6, var7, var8, var9).d();
      var1.b((double)(var2 + var4), (double)(var3 + 0), 0.0).b(var6, var7, var8, var9).d();
      Tessellator.a().b();
   }

   private void b() {
      this.a(Blocks.cf, "anvil_intact");
      this.a(Blocks.cf, 1, "anvil_slightly_damaged");
      this.a(Blocks.cf, 2, "anvil_very_damaged");
      this.a(Blocks.cy, EnumDyeColor.p.a(), "black_carpet");
      this.a(Blocks.cy, EnumDyeColor.l.a(), "blue_carpet");
      this.a(Blocks.cy, EnumDyeColor.m.a(), "brown_carpet");
      this.a(Blocks.cy, EnumDyeColor.j.a(), "cyan_carpet");
      this.a(Blocks.cy, EnumDyeColor.h.a(), "gray_carpet");
      this.a(Blocks.cy, EnumDyeColor.n.a(), "green_carpet");
      this.a(Blocks.cy, EnumDyeColor.d.a(), "light_blue_carpet");
      this.a(Blocks.cy, EnumDyeColor.f.a(), "lime_carpet");
      this.a(Blocks.cy, EnumDyeColor.c.a(), "magenta_carpet");
      this.a(Blocks.cy, EnumDyeColor.b.a(), "orange_carpet");
      this.a(Blocks.cy, EnumDyeColor.g.a(), "pink_carpet");
      this.a(Blocks.cy, EnumDyeColor.k.a(), "purple_carpet");
      this.a(Blocks.cy, EnumDyeColor.o.a(), "red_carpet");
      this.a(Blocks.cy, EnumDyeColor.i.a(), "silver_carpet");
      this.a(Blocks.cy, EnumDyeColor.a.a(), "white_carpet");
      this.a(Blocks.cy, EnumDyeColor.e.a(), "yellow_carpet");
      this.a(Blocks.bZ, BlockWall.EnumType.b.a(), "mossy_cobblestone_wall");
      this.a(Blocks.bZ, BlockWall.EnumType.a.a(), "cobblestone_wall");
      this.a(Blocks.d, BlockDirt.DirtType.b.a(), "coarse_dirt");
      this.a(Blocks.d, BlockDirt.DirtType.a.a(), "dirt");
      this.a(Blocks.d, BlockDirt.DirtType.c.a(), "podzol");
      this.a(Blocks.cF, BlockDoublePlant.EnumPlantType.d.a(), "double_fern");
      this.a(Blocks.cF, BlockDoublePlant.EnumPlantType.c.a(), "double_grass");
      this.a(Blocks.cF, BlockDoublePlant.EnumPlantType.f.a(), "paeonia");
      this.a(Blocks.cF, BlockDoublePlant.EnumPlantType.e.a(), "double_rose");
      this.a(Blocks.cF, BlockDoublePlant.EnumPlantType.a.a(), "sunflower");
      this.a(Blocks.cF, BlockDoublePlant.EnumPlantType.b.a(), "syringa");
      this.a(Blocks.t, BlockPlanks.EnumType.c.a(), "birch_leaves");
      this.a(Blocks.t, BlockPlanks.EnumType.d.a(), "jungle_leaves");
      this.a(Blocks.t, BlockPlanks.EnumType.a.a(), "oak_leaves");
      this.a(Blocks.t, BlockPlanks.EnumType.b.a(), "spruce_leaves");
      this.a(Blocks.u, BlockPlanks.EnumType.e.a() - 4, "acacia_leaves");
      this.a(Blocks.u, BlockPlanks.EnumType.f.a() - 4, "dark_oak_leaves");
      this.a(Blocks.r, BlockPlanks.EnumType.c.a(), "birch_log");
      this.a(Blocks.r, BlockPlanks.EnumType.d.a(), "jungle_log");
      this.a(Blocks.r, BlockPlanks.EnumType.a.a(), "oak_log");
      this.a(Blocks.r, BlockPlanks.EnumType.b.a(), "spruce_log");
      this.a(Blocks.s, BlockPlanks.EnumType.e.a() - 4, "acacia_log");
      this.a(Blocks.s, BlockPlanks.EnumType.f.a() - 4, "dark_oak_log");
      this.a(Blocks.be, BlockSilverfish.EnumType.f.a(), "chiseled_brick_monster_egg");
      this.a(Blocks.be, BlockSilverfish.EnumType.b.a(), "cobblestone_monster_egg");
      this.a(Blocks.be, BlockSilverfish.EnumType.e.a(), "cracked_brick_monster_egg");
      this.a(Blocks.be, BlockSilverfish.EnumType.d.a(), "mossy_brick_monster_egg");
      this.a(Blocks.be, BlockSilverfish.EnumType.a.a(), "stone_monster_egg");
      this.a(Blocks.be, BlockSilverfish.EnumType.c.a(), "stone_brick_monster_egg");
      this.a(Blocks.f, BlockPlanks.EnumType.e.a(), "acacia_planks");
      this.a(Blocks.f, BlockPlanks.EnumType.c.a(), "birch_planks");
      this.a(Blocks.f, BlockPlanks.EnumType.f.a(), "dark_oak_planks");
      this.a(Blocks.f, BlockPlanks.EnumType.d.a(), "jungle_planks");
      this.a(Blocks.f, BlockPlanks.EnumType.a.a(), "oak_planks");
      this.a(Blocks.f, BlockPlanks.EnumType.b.a(), "spruce_planks");
      this.a(Blocks.cI, BlockPrismarine.EnumType.b.a(), "prismarine_bricks");
      this.a(Blocks.cI, BlockPrismarine.EnumType.c.a(), "dark_prismarine");
      this.a(Blocks.cI, BlockPrismarine.EnumType.a.a(), "prismarine");
      this.a(Blocks.cq, BlockQuartz.EnumType.b.a(), "chiseled_quartz_block");
      this.a(Blocks.cq, BlockQuartz.EnumType.a.a(), "quartz_block");
      this.a(Blocks.cq, BlockQuartz.EnumType.c.a(), "quartz_column");
      this.a(Blocks.O, BlockFlower.EnumFlowerType.d.b(), "allium");
      this.a(Blocks.O, BlockFlower.EnumFlowerType.c.b(), "blue_orchid");
      this.a(Blocks.O, BlockFlower.EnumFlowerType.e.b(), "houstonia");
      this.a(Blocks.O, BlockFlower.EnumFlowerType.g.b(), "orange_tulip");
      this.a(Blocks.O, BlockFlower.EnumFlowerType.j.b(), "oxeye_daisy");
      this.a(Blocks.O, BlockFlower.EnumFlowerType.i.b(), "pink_tulip");
      this.a(Blocks.O, BlockFlower.EnumFlowerType.b.b(), "poppy");
      this.a(Blocks.O, BlockFlower.EnumFlowerType.f.b(), "red_tulip");
      this.a(Blocks.O, BlockFlower.EnumFlowerType.h.b(), "white_tulip");
      this.a(Blocks.m, BlockSand.EnumType.b.a(), "red_sand");
      this.a(Blocks.m, BlockSand.EnumType.a.a(), "sand");
      this.a(Blocks.A, BlockSandStone.EnumType.b.a(), "chiseled_sandstone");
      this.a(Blocks.A, BlockSandStone.EnumType.a.a(), "sandstone");
      this.a(Blocks.A, BlockSandStone.EnumType.c.a(), "smooth_sandstone");
      this.a(Blocks.cM, BlockRedSandstone.EnumType.b.a(), "chiseled_red_sandstone");
      this.a(Blocks.cM, BlockRedSandstone.EnumType.a.a(), "red_sandstone");
      this.a(Blocks.cM, BlockRedSandstone.EnumType.c.a(), "smooth_red_sandstone");
      this.a(Blocks.g, BlockPlanks.EnumType.e.a(), "acacia_sapling");
      this.a(Blocks.g, BlockPlanks.EnumType.c.a(), "birch_sapling");
      this.a(Blocks.g, BlockPlanks.EnumType.f.a(), "dark_oak_sapling");
      this.a(Blocks.g, BlockPlanks.EnumType.d.a(), "jungle_sapling");
      this.a(Blocks.g, BlockPlanks.EnumType.a.a(), "oak_sapling");
      this.a(Blocks.g, BlockPlanks.EnumType.b.a(), "spruce_sapling");
      this.a(Blocks.v, 0, "sponge");
      this.a(Blocks.v, 1, "sponge_wet");
      this.a(Blocks.cG, EnumDyeColor.p.a(), "black_stained_glass");
      this.a(Blocks.cG, EnumDyeColor.l.a(), "blue_stained_glass");
      this.a(Blocks.cG, EnumDyeColor.m.a(), "brown_stained_glass");
      this.a(Blocks.cG, EnumDyeColor.j.a(), "cyan_stained_glass");
      this.a(Blocks.cG, EnumDyeColor.h.a(), "gray_stained_glass");
      this.a(Blocks.cG, EnumDyeColor.n.a(), "green_stained_glass");
      this.a(Blocks.cG, EnumDyeColor.d.a(), "light_blue_stained_glass");
      this.a(Blocks.cG, EnumDyeColor.f.a(), "lime_stained_glass");
      this.a(Blocks.cG, EnumDyeColor.c.a(), "magenta_stained_glass");
      this.a(Blocks.cG, EnumDyeColor.b.a(), "orange_stained_glass");
      this.a(Blocks.cG, EnumDyeColor.g.a(), "pink_stained_glass");
      this.a(Blocks.cG, EnumDyeColor.k.a(), "purple_stained_glass");
      this.a(Blocks.cG, EnumDyeColor.o.a(), "red_stained_glass");
      this.a(Blocks.cG, EnumDyeColor.i.a(), "silver_stained_glass");
      this.a(Blocks.cG, EnumDyeColor.a.a(), "white_stained_glass");
      this.a(Blocks.cG, EnumDyeColor.e.a(), "yellow_stained_glass");
      this.a(Blocks.cH, EnumDyeColor.p.a(), "black_stained_glass_pane");
      this.a(Blocks.cH, EnumDyeColor.l.a(), "blue_stained_glass_pane");
      this.a(Blocks.cH, EnumDyeColor.m.a(), "brown_stained_glass_pane");
      this.a(Blocks.cH, EnumDyeColor.j.a(), "cyan_stained_glass_pane");
      this.a(Blocks.cH, EnumDyeColor.h.a(), "gray_stained_glass_pane");
      this.a(Blocks.cH, EnumDyeColor.n.a(), "green_stained_glass_pane");
      this.a(Blocks.cH, EnumDyeColor.d.a(), "light_blue_stained_glass_pane");
      this.a(Blocks.cH, EnumDyeColor.f.a(), "lime_stained_glass_pane");
      this.a(Blocks.cH, EnumDyeColor.c.a(), "magenta_stained_glass_pane");
      this.a(Blocks.cH, EnumDyeColor.b.a(), "orange_stained_glass_pane");
      this.a(Blocks.cH, EnumDyeColor.g.a(), "pink_stained_glass_pane");
      this.a(Blocks.cH, EnumDyeColor.k.a(), "purple_stained_glass_pane");
      this.a(Blocks.cH, EnumDyeColor.o.a(), "red_stained_glass_pane");
      this.a(Blocks.cH, EnumDyeColor.i.a(), "silver_stained_glass_pane");
      this.a(Blocks.cH, EnumDyeColor.a.a(), "white_stained_glass_pane");
      this.a(Blocks.cH, EnumDyeColor.e.a(), "yellow_stained_glass_pane");
      this.a(Blocks.cu, EnumDyeColor.p.a(), "black_stained_hardened_clay");
      this.a(Blocks.cu, EnumDyeColor.l.a(), "blue_stained_hardened_clay");
      this.a(Blocks.cu, EnumDyeColor.m.a(), "brown_stained_hardened_clay");
      this.a(Blocks.cu, EnumDyeColor.j.a(), "cyan_stained_hardened_clay");
      this.a(Blocks.cu, EnumDyeColor.h.a(), "gray_stained_hardened_clay");
      this.a(Blocks.cu, EnumDyeColor.n.a(), "green_stained_hardened_clay");
      this.a(Blocks.cu, EnumDyeColor.d.a(), "light_blue_stained_hardened_clay");
      this.a(Blocks.cu, EnumDyeColor.f.a(), "lime_stained_hardened_clay");
      this.a(Blocks.cu, EnumDyeColor.c.a(), "magenta_stained_hardened_clay");
      this.a(Blocks.cu, EnumDyeColor.b.a(), "orange_stained_hardened_clay");
      this.a(Blocks.cu, EnumDyeColor.g.a(), "pink_stained_hardened_clay");
      this.a(Blocks.cu, EnumDyeColor.k.a(), "purple_stained_hardened_clay");
      this.a(Blocks.cu, EnumDyeColor.o.a(), "red_stained_hardened_clay");
      this.a(Blocks.cu, EnumDyeColor.i.a(), "silver_stained_hardened_clay");
      this.a(Blocks.cu, EnumDyeColor.a.a(), "white_stained_hardened_clay");
      this.a(Blocks.cu, EnumDyeColor.e.a(), "yellow_stained_hardened_clay");
      this.a(Blocks.b, BlockStone.EnumType.f.a(), "andesite");
      this.a(Blocks.b, BlockStone.EnumType.g.a(), "andesite_smooth");
      this.a(Blocks.b, BlockStone.EnumType.d.a(), "diorite");
      this.a(Blocks.b, BlockStone.EnumType.e.a(), "diorite_smooth");
      this.a(Blocks.b, BlockStone.EnumType.b.a(), "granite");
      this.a(Blocks.b, BlockStone.EnumType.c.a(), "granite_smooth");
      this.a(Blocks.b, BlockStone.EnumType.a.a(), "stone");
      this.a(Blocks.bf, BlockStoneBrick.EnumType.c.a(), "cracked_stonebrick");
      this.a(Blocks.bf, BlockStoneBrick.EnumType.a.a(), "stonebrick");
      this.a(Blocks.bf, BlockStoneBrick.EnumType.d.a(), "chiseled_stonebrick");
      this.a(Blocks.bf, BlockStoneBrick.EnumType.b.a(), "mossy_stonebrick");
      this.a(Blocks.U, BlockStoneSlab.EnumType.e.a(), "brick_slab");
      this.a(Blocks.U, BlockStoneSlab.EnumType.d.a(), "cobblestone_slab");
      this.a(Blocks.U, BlockStoneSlab.EnumType.c.a(), "old_wood_slab");
      this.a(Blocks.U, BlockStoneSlab.EnumType.g.a(), "nether_brick_slab");
      this.a(Blocks.U, BlockStoneSlab.EnumType.h.a(), "quartz_slab");
      this.a(Blocks.U, BlockStoneSlab.EnumType.b.a(), "sandstone_slab");
      this.a(Blocks.U, BlockStoneSlab.EnumType.f.a(), "stone_brick_slab");
      this.a(Blocks.U, BlockStoneSlab.EnumType.a.a(), "stone_slab");
      this.a(Blocks.cP, BlockStoneSlabNew.EnumType.a.a(), "red_sandstone_slab");
      this.a(Blocks.H, BlockTallGrass.EnumType.a.a(), "dead_bush");
      this.a(Blocks.H, BlockTallGrass.EnumType.c.a(), "fern");
      this.a(Blocks.H, BlockTallGrass.EnumType.b.a(), "tall_grass");
      this.a(Blocks.bM, BlockPlanks.EnumType.e.a(), "acacia_slab");
      this.a(Blocks.bM, BlockPlanks.EnumType.c.a(), "birch_slab");
      this.a(Blocks.bM, BlockPlanks.EnumType.f.a(), "dark_oak_slab");
      this.a(Blocks.bM, BlockPlanks.EnumType.d.a(), "jungle_slab");
      this.a(Blocks.bM, BlockPlanks.EnumType.a.a(), "oak_slab");
      this.a(Blocks.bM, BlockPlanks.EnumType.b.a(), "spruce_slab");
      this.a(Blocks.L, EnumDyeColor.p.a(), "black_wool");
      this.a(Blocks.L, EnumDyeColor.l.a(), "blue_wool");
      this.a(Blocks.L, EnumDyeColor.m.a(), "brown_wool");
      this.a(Blocks.L, EnumDyeColor.j.a(), "cyan_wool");
      this.a(Blocks.L, EnumDyeColor.h.a(), "gray_wool");
      this.a(Blocks.L, EnumDyeColor.n.a(), "green_wool");
      this.a(Blocks.L, EnumDyeColor.d.a(), "light_blue_wool");
      this.a(Blocks.L, EnumDyeColor.f.a(), "lime_wool");
      this.a(Blocks.L, EnumDyeColor.c.a(), "magenta_wool");
      this.a(Blocks.L, EnumDyeColor.b.a(), "orange_wool");
      this.a(Blocks.L, EnumDyeColor.g.a(), "pink_wool");
      this.a(Blocks.L, EnumDyeColor.k.a(), "purple_wool");
      this.a(Blocks.L, EnumDyeColor.o.a(), "red_wool");
      this.a(Blocks.L, EnumDyeColor.i.a(), "silver_wool");
      this.a(Blocks.L, EnumDyeColor.a.a(), "white_wool");
      this.a(Blocks.L, EnumDyeColor.e.a(), "yellow_wool");
      this.a(Blocks.cC, "acacia_stairs");
      this.a(Blocks.cs, "activator_rail");
      this.a(Blocks.bY, "beacon");
      this.a(Blocks.h, "bedrock");
      this.a(Blocks.bV, "birch_stairs");
      this.a(Blocks.X, "bookshelf");
      this.a(Blocks.V, "brick_block");
      this.a(Blocks.V, "brick_block");
      this.a(Blocks.bu, "brick_stairs");
      this.a(Blocks.P, "brown_mushroom");
      this.a(Blocks.aK, "cactus");
      this.a(Blocks.aL, "clay");
      this.a(Blocks.cA, "coal_block");
      this.a(Blocks.q, "coal_ore");
      this.a(Blocks.e, "cobblestone");
      this.a(Blocks.ai, "crafting_table");
      this.a(Blocks.cD, "dark_oak_stairs");
      this.a(Blocks.cl, "daylight_detector");
      this.a(Blocks.I, "dead_bush");
      this.a(Blocks.E, "detector_rail");
      this.a(Blocks.ah, "diamond_block");
      this.a(Blocks.ag, "diamond_ore");
      this.a(Blocks.z, "dispenser");
      this.a(Blocks.ct, "dropper");
      this.a(Blocks.bT, "emerald_block");
      this.a(Blocks.bP, "emerald_ore");
      this.a(Blocks.bC, "enchanting_table");
      this.a(Blocks.bG, "end_portal_frame");
      this.a(Blocks.bH, "end_stone");
      this.a(Blocks.aO, "oak_fence");
      this.a(Blocks.aP, "spruce_fence");
      this.a(Blocks.aQ, "birch_fence");
      this.a(Blocks.aR, "jungle_fence");
      this.a(Blocks.aS, "dark_oak_fence");
      this.a(Blocks.aT, "acacia_fence");
      this.a(Blocks.bo, "oak_fence_gate");
      this.a(Blocks.bp, "spruce_fence_gate");
      this.a(Blocks.bq, "birch_fence_gate");
      this.a(Blocks.br, "jungle_fence_gate");
      this.a(Blocks.bs, "dark_oak_fence_gate");
      this.a(Blocks.bt, "acacia_fence_gate");
      this.a(Blocks.al, "furnace");
      this.a(Blocks.w, "glass");
      this.a(Blocks.bj, "glass_pane");
      this.a(Blocks.aX, "glowstone");
      this.a(Blocks.D, "golden_rail");
      this.a(Blocks.R, "gold_block");
      this.a(Blocks.o, "gold_ore");
      this.a(Blocks.c, "grass");
      this.a(Blocks.n, "gravel");
      this.a(Blocks.cz, "hardened_clay");
      this.a(Blocks.cx, "hay_block");
      this.a(Blocks.ci, "heavy_weighted_pressure_plate");
      this.a(Blocks.cp, "hopper");
      this.a(Blocks.aI, "ice");
      this.a(Blocks.bi, "iron_bars");
      this.a(Blocks.S, "iron_block");
      this.a(Blocks.p, "iron_ore");
      this.a(Blocks.cw, "iron_trapdoor");
      this.a(Blocks.aN, "jukebox");
      this.a(Blocks.bW, "jungle_stairs");
      this.a(Blocks.au, "ladder");
      this.a(Blocks.y, "lapis_block");
      this.a(Blocks.x, "lapis_ore");
      this.a(Blocks.ay, "lever");
      this.a(Blocks.ch, "light_weighted_pressure_plate");
      this.a(Blocks.aZ, "lit_pumpkin");
      this.a(Blocks.bk, "melon_block");
      this.a(Blocks.Y, "mossy_cobblestone");
      this.a(Blocks.bw, "mycelium");
      this.a(Blocks.aV, "netherrack");
      this.a(Blocks.by, "nether_brick");
      this.a(Blocks.bz, "nether_brick_fence");
      this.a(Blocks.bA, "nether_brick_stairs");
      this.a(Blocks.B, "noteblock");
      this.a(Blocks.ad, "oak_stairs");
      this.a(Blocks.Z, "obsidian");
      this.a(Blocks.cB, "packed_ice");
      this.a(Blocks.J, "piston");
      this.a(Blocks.aU, "pumpkin");
      this.a(Blocks.co, "quartz_ore");
      this.a(Blocks.cr, "quartz_stairs");
      this.a(Blocks.av, "rail");
      this.a(Blocks.cn, "redstone_block");
      this.a(Blocks.bJ, "redstone_lamp");
      this.a(Blocks.aC, "redstone_ore");
      this.a(Blocks.aF, "redstone_torch");
      this.a(Blocks.Q, "red_mushroom");
      this.a(Blocks.bO, "sandstone_stairs");
      this.a(Blocks.cN, "red_sandstone_stairs");
      this.a(Blocks.cJ, "sea_lantern");
      this.a(Blocks.cE, "slime");
      this.a(Blocks.aJ, "snow");
      this.a(Blocks.aH, "snow_layer");
      this.a(Blocks.aW, "soul_sand");
      this.a(Blocks.bU, "spruce_stairs");
      this.a(Blocks.F, "sticky_piston");
      this.a(Blocks.bv, "stone_brick_stairs");
      this.a(Blocks.aG, "stone_button");
      this.a(Blocks.az, "stone_pressure_plate");
      this.a(Blocks.aw, "stone_stairs");
      this.a(Blocks.W, "tnt");
      this.a(Blocks.aa, "torch");
      this.a(Blocks.bd, "trapdoor");
      this.a(Blocks.bR, "tripwire_hook");
      this.a(Blocks.bn, "vine");
      this.a(Blocks.bx, "waterlily");
      this.a(Blocks.G, "web");
      this.a(Blocks.cd, "wooden_button");
      this.a(Blocks.aB, "wooden_pressure_plate");
      this.a(Blocks.N, BlockFlower.EnumFlowerType.a.b(), "dandelion");
      this.a(Blocks.ae, "chest");
      this.a(Blocks.cg, "trapped_chest");
      this.a(Blocks.bQ, "ender_chest");
      this.a(Items.a, "iron_shovel");
      this.a(Items.b, "iron_pickaxe");
      this.a(Items.c, "iron_axe");
      this.a(Items.d, "flint_and_steel");
      this.a(Items.e, "apple");
      this.a(Items.f, 0, "bow");
      this.a(Items.f, 1, "bow_pulling_0");
      this.a(Items.f, 2, "bow_pulling_1");
      this.a(Items.f, 3, "bow_pulling_2");
      this.a(Items.g, "arrow");
      this.a(Items.h, 0, "coal");
      this.a(Items.h, 1, "charcoal");
      this.a(Items.i, "diamond");
      this.a(Items.j, "iron_ingot");
      this.a(Items.k, "gold_ingot");
      this.a(Items.l, "iron_sword");
      this.a(Items.m, "wooden_sword");
      this.a(Items.n, "wooden_shovel");
      this.a(Items.o, "wooden_pickaxe");
      this.a(Items.p, "wooden_axe");
      this.a(Items.q, "stone_sword");
      this.a(Items.r, "stone_shovel");
      this.a(Items.s, "stone_pickaxe");
      this.a(Items.t, "stone_axe");
      this.a(Items.u, "diamond_sword");
      this.a(Items.v, "diamond_shovel");
      this.a(Items.w, "diamond_pickaxe");
      this.a(Items.x, "diamond_axe");
      this.a(Items.y, "stick");
      this.a(Items.z, "bowl");
      this.a(Items.A, "mushroom_stew");
      this.a(Items.B, "golden_sword");
      this.a(Items.C, "golden_shovel");
      this.a(Items.D, "golden_pickaxe");
      this.a(Items.E, "golden_axe");
      this.a(Items.F, "string");
      this.a(Items.G, "feather");
      this.a(Items.H, "gunpowder");
      this.a(Items.I, "wooden_hoe");
      this.a(Items.J, "stone_hoe");
      this.a(Items.K, "iron_hoe");
      this.a(Items.L, "diamond_hoe");
      this.a(Items.M, "golden_hoe");
      this.a(Items.N, "wheat_seeds");
      this.a(Items.O, "wheat");
      this.a(Items.P, "bread");
      this.a(Items.Q, "leather_helmet");
      this.a(Items.R, "leather_chestplate");
      this.a(Items.S, "leather_leggings");
      this.a(Items.T, "leather_boots");
      this.a(Items.U, "chainmail_helmet");
      this.a(Items.V, "chainmail_chestplate");
      this.a(Items.W, "chainmail_leggings");
      this.a(Items.X, "chainmail_boots");
      this.a(Items.Y, "iron_helmet");
      this.a(Items.Z, "iron_chestplate");
      this.a(Items.aa, "iron_leggings");
      this.a(Items.ab, "iron_boots");
      this.a(Items.ac, "diamond_helmet");
      this.a(Items.ad, "diamond_chestplate");
      this.a(Items.ae, "diamond_leggings");
      this.a(Items.af, "diamond_boots");
      this.a(Items.ag, "golden_helmet");
      this.a(Items.ah, "golden_chestplate");
      this.a(Items.ai, "golden_leggings");
      this.a(Items.aj, "golden_boots");
      this.a(Items.ak, "flint");
      this.a(Items.al, "porkchop");
      this.a(Items.am, "cooked_porkchop");
      this.a(Items.an, "painting");
      this.a(Items.ao, "golden_apple");
      this.a(Items.ao, 1, "golden_apple");
      this.a(Items.ap, "sign");
      this.a(Items.aq, "oak_door");
      this.a(Items.ar, "spruce_door");
      this.a(Items.as, "birch_door");
      this.a(Items.at, "jungle_door");
      this.a(Items.au, "acacia_door");
      this.a(Items.av, "dark_oak_door");
      this.a(Items.aw, "bucket");
      this.a(Items.ax, "water_bucket");
      this.a(Items.ay, "lava_bucket");
      this.a(Items.az, "minecart");
      this.a(Items.aA, "saddle");
      this.a(Items.aB, "iron_door");
      this.a(Items.aC, "redstone");
      this.a(Items.aD, "snowball");
      this.a(Items.aE, "boat");
      this.a(Items.aF, "leather");
      this.a(Items.aG, "milk_bucket");
      this.a(Items.aH, "brick");
      this.a(Items.aI, "clay_ball");
      this.a(Items.aJ, "reeds");
      this.a(Items.aK, "paper");
      this.a(Items.aL, "book");
      this.a(Items.aM, "slime_ball");
      this.a(Items.aN, "chest_minecart");
      this.a(Items.aO, "furnace_minecart");
      this.a(Items.aP, "egg");
      this.a(Items.aQ, "compass");
      this.a(Items.aR, "fishing_rod");
      this.a(Items.aR, 1, "fishing_rod_cast");
      this.a(Items.aS, "clock");
      this.a(Items.aT, "glowstone_dust");
      this.a(Items.aU, ItemFishFood.FishType.a.a(), "cod");
      this.a(Items.aU, ItemFishFood.FishType.b.a(), "salmon");
      this.a(Items.aU, ItemFishFood.FishType.c.a(), "clownfish");
      this.a(Items.aU, ItemFishFood.FishType.d.a(), "pufferfish");
      this.a(Items.aV, ItemFishFood.FishType.a.a(), "cooked_cod");
      this.a(Items.aV, ItemFishFood.FishType.b.a(), "cooked_salmon");
      this.a(Items.aW, EnumDyeColor.p.b(), "dye_black");
      this.a(Items.aW, EnumDyeColor.o.b(), "dye_red");
      this.a(Items.aW, EnumDyeColor.n.b(), "dye_green");
      this.a(Items.aW, EnumDyeColor.m.b(), "dye_brown");
      this.a(Items.aW, EnumDyeColor.l.b(), "dye_blue");
      this.a(Items.aW, EnumDyeColor.k.b(), "dye_purple");
      this.a(Items.aW, EnumDyeColor.j.b(), "dye_cyan");
      this.a(Items.aW, EnumDyeColor.i.b(), "dye_silver");
      this.a(Items.aW, EnumDyeColor.h.b(), "dye_gray");
      this.a(Items.aW, EnumDyeColor.g.b(), "dye_pink");
      this.a(Items.aW, EnumDyeColor.f.b(), "dye_lime");
      this.a(Items.aW, EnumDyeColor.e.b(), "dye_yellow");
      this.a(Items.aW, EnumDyeColor.d.b(), "dye_light_blue");
      this.a(Items.aW, EnumDyeColor.c.b(), "dye_magenta");
      this.a(Items.aW, EnumDyeColor.b.b(), "dye_orange");
      this.a(Items.aW, EnumDyeColor.a.b(), "dye_white");
      this.a(Items.aX, "bone");
      this.a(Items.aY, "sugar");
      this.a(Items.aZ, "cake");
      this.a(Items.ba, "bed");
      this.a(Items.bb, "repeater");
      this.a(Items.bc, "cookie");
      this.a(Items.be, "shears");
      this.a(Items.bf, "melon");
      this.a(Items.bg, "pumpkin_seeds");
      this.a(Items.bh, "melon_seeds");
      this.a(Items.bi, "beef");
      this.a(Items.bj, "cooked_beef");
      this.a(Items.bk, "chicken");
      this.a(Items.bl, "cooked_chicken");
      this.a(Items.bo, "rabbit");
      this.a(Items.bp, "cooked_rabbit");
      this.a(Items.bm, "mutton");
      this.a(Items.bn, "cooked_mutton");
      this.a(Items.br, "rabbit_foot");
      this.a(Items.bs, "rabbit_hide");
      this.a(Items.bq, "rabbit_stew");
      this.a(Items.bt, "rotten_flesh");
      this.a(Items.bu, "ender_pearl");
      this.a(Items.bv, "blaze_rod");
      this.a(Items.bw, "ghast_tear");
      this.a(Items.bx, "gold_nugget");
      this.a(Items.by, "nether_wart");
      this.d
         .a(
            Items.bz,
            new ItemMeshDefinition() {
               @Override
               public ModelResourceLocation a(ItemStack var1) {
                  return ItemPotion.f(var1.i())
                     ? new ModelResourceLocation("bottle_splash", "inventory")
                     : new ModelResourceLocation("bottle_drinkable", "inventory");
               }
            }
         );
      this.a(Items.bA, "glass_bottle");
      this.a(Items.bB, "spider_eye");
      this.a(Items.bC, "fermented_spider_eye");
      this.a(Items.bD, "blaze_powder");
      this.a(Items.bE, "magma_cream");
      this.a(Items.bF, "brewing_stand");
      this.a(Items.bG, "cauldron");
      this.a(Items.bH, "ender_eye");
      this.a(Items.bI, "speckled_melon");
      this.d.a(Items.bJ, new ItemMeshDefinition() {
         @Override
         public ModelResourceLocation a(ItemStack var1) {
            return new ModelResourceLocation("spawn_egg", "inventory");
         }
      });
      this.a(Items.bK, "experience_bottle");
      this.a(Items.bL, "fire_charge");
      this.a(Items.bM, "writable_book");
      this.a(Items.bO, "emerald");
      this.a(Items.bP, "item_frame");
      this.a(Items.bQ, "flower_pot");
      this.a(Items.bR, "carrot");
      this.a(Items.bS, "potato");
      this.a(Items.bT, "baked_potato");
      this.a(Items.bU, "poisonous_potato");
      this.a(Items.bV, "map");
      this.a(Items.bW, "golden_carrot");
      this.a(Items.bX, 0, "skull_skeleton");
      this.a(Items.bX, 1, "skull_wither");
      this.a(Items.bX, 2, "skull_zombie");
      this.a(Items.bX, 3, "skull_char");
      this.a(Items.bX, 4, "skull_creeper");
      this.a(Items.bY, "carrot_on_a_stick");
      this.a(Items.bZ, "nether_star");
      this.a(Items.ca, "pumpkin_pie");
      this.a(Items.cc, "firework_charge");
      this.a(Items.ce, "comparator");
      this.a(Items.cf, "netherbrick");
      this.a(Items.cg, "quartz");
      this.a(Items.ch, "tnt_minecart");
      this.a(Items.ci, "hopper_minecart");
      this.a(Items.cj, "armor_stand");
      this.a(Items.ck, "iron_horse_armor");
      this.a(Items.cl, "golden_horse_armor");
      this.a(Items.cm, "diamond_horse_armor");
      this.a(Items.cn, "lead");
      this.a(Items.co, "name_tag");
      this.d.a(Items.cE, new ItemMeshDefinition() {
         @Override
         public ModelResourceLocation a(ItemStack var1) {
            return new ModelResourceLocation("banner", "inventory");
         }
      });
      this.a(Items.cq, "record_13");
      this.a(Items.cr, "record_cat");
      this.a(Items.cs, "record_blocks");
      this.a(Items.ct, "record_chirp");
      this.a(Items.cu, "record_far");
      this.a(Items.cv, "record_mall");
      this.a(Items.cw, "record_mellohi");
      this.a(Items.cx, "record_stal");
      this.a(Items.cy, "record_strad");
      this.a(Items.cz, "record_ward");
      this.a(Items.cA, "record_11");
      this.a(Items.cB, "record_wait");
      this.a(Items.cC, "prismarine_shard");
      this.a(Items.cD, "prismarine_crystals");
      this.d.a(Items.cd, new ItemMeshDefinition() {
         @Override
         public ModelResourceLocation a(ItemStack var1) {
            return new ModelResourceLocation("enchanted_book", "inventory");
         }
      });
      this.d.a(Items.bd, new ItemMeshDefinition() {
         @Override
         public ModelResourceLocation a(ItemStack var1) {
            return new ModelResourceLocation("filled_map", "inventory");
         }
      });
      this.a(Blocks.bX, "command_block");
      this.a(Items.cb, "fireworks");
      this.a(Items.cp, "command_block_minecart");
      this.a(Blocks.cv, "barrier");
      this.a(Blocks.ac, "mob_spawner");
      this.a(Items.bN, "written_book");
      this.a(Blocks.bg, BlockHugeMushroom.EnumType.k.a(), "brown_mushroom_block");
      this.a(Blocks.bh, BlockHugeMushroom.EnumType.k.a(), "red_mushroom_block");
      this.a(Blocks.bI, "dragon_egg");
   }

   @Override
   public void a(IResourceManager var1) {
      this.d.b();
   }
}
