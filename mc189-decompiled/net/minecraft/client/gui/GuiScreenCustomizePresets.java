package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.ChunkProviderSettings;
import org.lwjgl.input.Keyboard;

public class GuiScreenCustomizePresets extends GuiScreen {
   private static final List<GuiScreenCustomizePresets.Info> f = Lists.newArrayList();
   private GuiScreenCustomizePresets.ListPreset g;
   private GuiButton h;
   private GuiTextField i;
   private GuiCustomizeWorldScreen r;
   protected String a = "Customize World Presets";
   private String s;
   private String t;

   public GuiScreenCustomizePresets(GuiCustomizeWorldScreen var1) {
      this.r = var1;
   }

   @Override
   public void b() {
      this.n.clear();
      Keyboard.enableRepeatEvents(true);
      this.a = I18n.a("createWorld.customize.custom.presets.title");
      this.s = I18n.a("createWorld.customize.presets.share");
      this.t = I18n.a("createWorld.customize.presets.list");
      this.i = new GuiTextField(2, this.q, 50, 40, this.l - 100, 20);
      this.g = new GuiScreenCustomizePresets.ListPreset();
      this.i.f(2000);
      this.i.a(this.r.a());
      this.n.add(this.h = new GuiButton(0, this.l / 2 - 102, this.m - 27, 100, 20, I18n.a("createWorld.customize.presets.select")));
      this.n.add(new GuiButton(1, this.l / 2 + 3, this.m - 27, 100, 20, I18n.a("gui.cancel")));
      this.a();
   }

   @Override
   public void k() {
      super.k();
      this.g.p();
   }

   @Override
   public void m() {
      Keyboard.enableRepeatEvents(false);
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      this.i.a(var1, var2, var3);
      super.a(var1, var2, var3);
   }

   @Override
   protected void a(char var1, int var2) {
      if (!this.i.a(var1, var2)) {
         super.a(var1, var2);
      }
   }

   @Override
   protected void a(GuiButton var1) {
      switch (var1.k) {
         case 0:
            this.r.a(this.i.b());
            this.j.a(this.r);
            break;
         case 1:
            this.j.a(this.r);
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.g.a(var1, var2, var3);
      this.a(this.q, this.a, this.l / 2, 8, 16777215);
      this.c(this.q, this.s, 50, 30, 10526880);
      this.c(this.q, this.t, 50, 70, 10526880);
      this.i.g();
      super.a(var1, var2, var3);
   }

   @Override
   public void e() {
      this.i.a();
      super.e();
   }

   public void a() {
      this.h.l = this.g();
   }

   private boolean g() {
      return this.g.u > -1 && this.g.u < f.size() || this.i.b().length() > 1;
   }

   static {
      ChunkProviderSettings.Factory var0 = ChunkProviderSettings.Factory.a(
         "{ \"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":8.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":0.5, \"biomeScaleWeight\":2.0, \"biomeScaleOffset\":0.375, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":255 }"
      );
      ResourceLocation var1 = new ResourceLocation("textures/gui/presets/water.png");
      f.add(new GuiScreenCustomizePresets.Info(I18n.a("createWorld.customize.custom.preset.waterWorld"), var1, var0));
      var0 = ChunkProviderSettings.Factory.a(
         "{\"coordinateScale\":3000.0, \"heightScale\":6000.0, \"upperLimitScale\":250.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }"
      );
      var1 = new ResourceLocation("textures/gui/presets/isles.png");
      f.add(new GuiScreenCustomizePresets.Info(I18n.a("createWorld.customize.custom.preset.isleLand"), var1, var0));
      var0 = ChunkProviderSettings.Factory.a(
         "{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":5.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":1.0, \"biomeScaleWeight\":4.0, \"biomeScaleOffset\":1.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }"
      );
      var1 = new ResourceLocation("textures/gui/presets/delight.png");
      f.add(new GuiScreenCustomizePresets.Info(I18n.a("createWorld.customize.custom.preset.caveDelight"), var1, var0));
      var0 = ChunkProviderSettings.Factory.a(
         "{\"coordinateScale\":738.41864, \"heightScale\":157.69133, \"upperLimitScale\":801.4267, \"lowerLimitScale\":1254.1643, \"depthNoiseScaleX\":374.93652, \"depthNoiseScaleZ\":288.65228, \"depthNoiseScaleExponent\":1.2092624, \"mainNoiseScaleX\":1355.9908, \"mainNoiseScaleY\":745.5343, \"mainNoiseScaleZ\":1183.464, \"baseSize\":1.8758626, \"stretchY\":1.7137525, \"biomeDepthWeight\":1.7553768, \"biomeDepthOffset\":3.4701107, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":2.535211, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }"
      );
      var1 = new ResourceLocation("textures/gui/presets/madness.png");
      f.add(new GuiScreenCustomizePresets.Info(I18n.a("createWorld.customize.custom.preset.mountains"), var1, var0));
      var0 = ChunkProviderSettings.Factory.a(
         "{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":1000.0, \"mainNoiseScaleY\":3000.0, \"mainNoiseScaleZ\":1000.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":20 }"
      );
      var1 = new ResourceLocation("textures/gui/presets/drought.png");
      f.add(new GuiScreenCustomizePresets.Info(I18n.a("createWorld.customize.custom.preset.drought"), var1, var0));
      var0 = ChunkProviderSettings.Factory.a(
         "{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":2.0, \"lowerLimitScale\":64.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":6 }"
      );
      var1 = new ResourceLocation("textures/gui/presets/chaos.png");
      f.add(new GuiScreenCustomizePresets.Info(I18n.a("createWorld.customize.custom.preset.caveChaos"), var1, var0));
      var0 = ChunkProviderSettings.Factory.a(
         "{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":true, \"seaLevel\":40 }"
      );
      var1 = new ResourceLocation("textures/gui/presets/luck.png");
      f.add(new GuiScreenCustomizePresets.Info(I18n.a("createWorld.customize.custom.preset.goodLuck"), var1, var0));
   }

   static class Info {
      public String a;
      public ResourceLocation b;
      public ChunkProviderSettings.Factory c;

      public Info(String var1, ResourceLocation var2, ChunkProviderSettings.Factory var3) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
      }
   }

   class ListPreset extends GuiSlot {
      public int u = -1;

      public ListPreset() {
         super(
            GuiScreenCustomizePresets.this.j, GuiScreenCustomizePresets.this.l, GuiScreenCustomizePresets.this.m, 80, GuiScreenCustomizePresets.this.m - 32, 38
         );
      }

      @Override
      protected int b() {
         return GuiScreenCustomizePresets.f.size();
      }

      @Override
      protected void a(int var1, boolean var2, int var3, int var4) {
         this.u = var1;
         GuiScreenCustomizePresets.this.a();
         GuiScreenCustomizePresets.this.i.a(GuiScreenCustomizePresets.f.get(GuiScreenCustomizePresets.this.g.u).c.toString());
      }

      @Override
      protected boolean a(int var1) {
         return var1 == this.u;
      }

      @Override
      protected void a() {
      }

      private void a(int var1, int var2, ResourceLocation var3) {
         int var4 = var1 + 5;
         GuiScreenCustomizePresets.this.a(var4 - 1, var4 + 32, var2 - 1, -2039584);
         GuiScreenCustomizePresets.this.a(var4 - 1, var4 + 32, var2 + 32, -6250336);
         GuiScreenCustomizePresets.this.b(var4 - 1, var2 - 1, var2 + 32, -2039584);
         GuiScreenCustomizePresets.this.b(var4 + 32, var2 - 1, var2 + 32, -6250336);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         this.a.P().a(var3);
         byte var6 = 32;
         byte var7 = 32;
         Tessellator var8 = Tessellator.a();
         WorldRenderer var9 = var8.c();
         var9.a(7, DefaultVertexFormats.g);
         var9.b((double)(var4 + 0), (double)(var2 + 32), 0.0).a(0.0, 1.0).d();
         var9.b((double)(var4 + 32), (double)(var2 + 32), 0.0).a(1.0, 1.0).d();
         var9.b((double)(var4 + 32), (double)(var2 + 0), 0.0).a(1.0, 0.0).d();
         var9.b((double)(var4 + 0), (double)(var2 + 0), 0.0).a(0.0, 0.0).d();
         var8.b();
      }

      @Override
      protected void a(int var1, int var2, int var3, int var4, int var5, int var6) {
         GuiScreenCustomizePresets.Info var7 = GuiScreenCustomizePresets.f.get(var1);
         this.a(var2, var3, var7.b);
         GuiScreenCustomizePresets.this.q.a(var7.a, var2 + 32 + 10, var3 + 14, 16777215);
      }
   }
}
