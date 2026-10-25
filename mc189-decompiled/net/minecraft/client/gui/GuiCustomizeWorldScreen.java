package net.minecraft.client.gui;

import com.google.common.base.Predicate;
import com.google.common.primitives.Floats;
import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettings;

public class GuiCustomizeWorldScreen extends GuiScreen implements GuiSlider.FormatHelper, GuiPageButtonList.GuiResponder {
   private GuiCreateWorld i;
   protected String a = "Customize World Settings";
   protected String f = "Page 1 of 3";
   protected String g = "Basic Settings";
   protected String[] h = new String[4];
   private GuiPageButtonList r;
   private GuiButton s;
   private GuiButton t;
   private GuiButton u;
   private GuiButton v;
   private GuiButton w;
   private GuiButton x;
   private GuiButton y;
   private GuiButton z;
   private boolean A = false;
   private int B = 0;
   private boolean C = false;
   private Predicate<String> D = new Predicate<String>() {
      public boolean a(String var1) {
         Float var2 = Floats.tryParse(var1);
         return var1.length() == 0 || var2 != null && Floats.isFinite(var2) && var2 >= 0.0F;
      }
   };
   private ChunkProviderSettings.Factory E = new ChunkProviderSettings.Factory();
   private ChunkProviderSettings.Factory F;
   private Random G = new Random();

   public GuiCustomizeWorldScreen(GuiScreen var1, String var2) {
      this.i = (GuiCreateWorld)var1;
      this.a(var2);
   }

   @Override
   public void b() {
      int var1 = 0;
      int var2 = 0;
      if (this.r != null) {
         var1 = this.r.e();
         var2 = this.r.n();
      }

      this.a = I18n.a("options.customizeTitle");
      this.n.clear();
      this.n.add(this.v = new GuiButton(302, 20, 5, 80, 20, I18n.a("createWorld.customize.custom.prev")));
      this.n.add(this.w = new GuiButton(303, this.l - 100, 5, 80, 20, I18n.a("createWorld.customize.custom.next")));
      this.n.add(this.u = new GuiButton(304, this.l / 2 - 187, this.m - 27, 90, 20, I18n.a("createWorld.customize.custom.defaults")));
      this.n.add(this.t = new GuiButton(301, this.l / 2 - 92, this.m - 27, 90, 20, I18n.a("createWorld.customize.custom.randomize")));
      this.n.add(this.z = new GuiButton(305, this.l / 2 + 3, this.m - 27, 90, 20, I18n.a("createWorld.customize.custom.presets")));
      this.n.add(this.s = new GuiButton(300, this.l / 2 + 98, this.m - 27, 90, 20, I18n.a("gui.done")));
      this.u.l = this.A;
      this.x = new GuiButton(306, this.l / 2 - 55, 160, 50, 20, I18n.a("gui.yes"));
      this.x.m = false;
      this.n.add(this.x);
      this.y = new GuiButton(307, this.l / 2 + 5, 160, 50, 20, I18n.a("gui.no"));
      this.y.m = false;
      this.n.add(this.y);
      if (this.B != 0) {
         this.x.m = true;
         this.y.m = true;
      }

      this.f();
      if (var1 != 0) {
         this.r.c(var1);
         this.r.h(var2);
         this.i();
      }
   }

   @Override
   public void k() {
      super.k();
      this.r.p();
   }

   private void f() {
      GuiPageButtonList.GuiListEntry[] var1 = new GuiPageButtonList.GuiListEntry[]{
         new GuiPageButtonList.GuiSlideEntry(160, I18n.a("createWorld.customize.custom.seaLevel"), true, this, 1.0F, 255.0F, this.F.r),
         new GuiPageButtonList.GuiButtonEntry(148, I18n.a("createWorld.customize.custom.useCaves"), true, this.F.s),
         new GuiPageButtonList.GuiButtonEntry(150, I18n.a("createWorld.customize.custom.useStrongholds"), true, this.F.v),
         new GuiPageButtonList.GuiButtonEntry(151, I18n.a("createWorld.customize.custom.useVillages"), true, this.F.w),
         new GuiPageButtonList.GuiButtonEntry(152, I18n.a("createWorld.customize.custom.useMineShafts"), true, this.F.x),
         new GuiPageButtonList.GuiButtonEntry(153, I18n.a("createWorld.customize.custom.useTemples"), true, this.F.y),
         new GuiPageButtonList.GuiButtonEntry(210, I18n.a("createWorld.customize.custom.useMonuments"), true, this.F.z),
         new GuiPageButtonList.GuiButtonEntry(154, I18n.a("createWorld.customize.custom.useRavines"), true, this.F.A),
         new GuiPageButtonList.GuiButtonEntry(149, I18n.a("createWorld.customize.custom.useDungeons"), true, this.F.t),
         new GuiPageButtonList.GuiSlideEntry(157, I18n.a("createWorld.customize.custom.dungeonChance"), true, this, 1.0F, 100.0F, this.F.u),
         new GuiPageButtonList.GuiButtonEntry(155, I18n.a("createWorld.customize.custom.useWaterLakes"), true, this.F.B),
         new GuiPageButtonList.GuiSlideEntry(158, I18n.a("createWorld.customize.custom.waterLakeChance"), true, this, 1.0F, 100.0F, this.F.C),
         new GuiPageButtonList.GuiButtonEntry(156, I18n.a("createWorld.customize.custom.useLavaLakes"), true, this.F.D),
         new GuiPageButtonList.GuiSlideEntry(159, I18n.a("createWorld.customize.custom.lavaLakeChance"), true, this, 10.0F, 100.0F, this.F.E),
         new GuiPageButtonList.GuiButtonEntry(161, I18n.a("createWorld.customize.custom.useLavaOceans"), true, this.F.F),
         new GuiPageButtonList.GuiSlideEntry(162, I18n.a("createWorld.customize.custom.fixedBiome"), true, this, -1.0F, 37.0F, this.F.G),
         new GuiPageButtonList.GuiSlideEntry(163, I18n.a("createWorld.customize.custom.biomeSize"), true, this, 1.0F, 8.0F, this.F.H),
         new GuiPageButtonList.GuiSlideEntry(164, I18n.a("createWorld.customize.custom.riverSize"), true, this, 1.0F, 5.0F, this.F.I)
      };
      GuiPageButtonList.GuiListEntry[] var2 = new GuiPageButtonList.GuiListEntry[]{
         new GuiPageButtonList.GuiLabelEntry(416, I18n.a("tile.dirt.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(165, I18n.a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.F.J),
         new GuiPageButtonList.GuiSlideEntry(166, I18n.a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.F.K),
         new GuiPageButtonList.GuiSlideEntry(167, I18n.a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.F.L),
         new GuiPageButtonList.GuiSlideEntry(168, I18n.a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.F.M),
         new GuiPageButtonList.GuiLabelEntry(417, I18n.a("tile.gravel.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(169, I18n.a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.F.N),
         new GuiPageButtonList.GuiSlideEntry(170, I18n.a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.F.O),
         new GuiPageButtonList.GuiSlideEntry(171, I18n.a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.F.P),
         new GuiPageButtonList.GuiSlideEntry(172, I18n.a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.F.Q),
         new GuiPageButtonList.GuiLabelEntry(418, I18n.a("tile.stone.granite.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(173, I18n.a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.F.R),
         new GuiPageButtonList.GuiSlideEntry(174, I18n.a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.F.S),
         new GuiPageButtonList.GuiSlideEntry(175, I18n.a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.F.T),
         new GuiPageButtonList.GuiSlideEntry(176, I18n.a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.F.U),
         new GuiPageButtonList.GuiLabelEntry(419, I18n.a("tile.stone.diorite.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(177, I18n.a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.F.V),
         new GuiPageButtonList.GuiSlideEntry(178, I18n.a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.F.W),
         new GuiPageButtonList.GuiSlideEntry(179, I18n.a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.F.X),
         new GuiPageButtonList.GuiSlideEntry(180, I18n.a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.F.Y),
         new GuiPageButtonList.GuiLabelEntry(420, I18n.a("tile.stone.andesite.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(181, I18n.a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.F.Z),
         new GuiPageButtonList.GuiSlideEntry(182, I18n.a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.F.aa),
         new GuiPageButtonList.GuiSlideEntry(183, I18n.a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.F.ab),
         new GuiPageButtonList.GuiSlideEntry(184, I18n.a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.F.ac),
         new GuiPageButtonList.GuiLabelEntry(421, I18n.a("tile.oreCoal.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(185, I18n.a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.F.ad),
         new GuiPageButtonList.GuiSlideEntry(186, I18n.a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.F.ae),
         new GuiPageButtonList.GuiSlideEntry(187, I18n.a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.F.af),
         new GuiPageButtonList.GuiSlideEntry(189, I18n.a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.F.ag),
         new GuiPageButtonList.GuiLabelEntry(422, I18n.a("tile.oreIron.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(190, I18n.a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.F.ah),
         new GuiPageButtonList.GuiSlideEntry(191, I18n.a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.F.ai),
         new GuiPageButtonList.GuiSlideEntry(192, I18n.a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.F.aj),
         new GuiPageButtonList.GuiSlideEntry(193, I18n.a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.F.ak),
         new GuiPageButtonList.GuiLabelEntry(423, I18n.a("tile.oreGold.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(194, I18n.a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.F.al),
         new GuiPageButtonList.GuiSlideEntry(195, I18n.a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.F.am),
         new GuiPageButtonList.GuiSlideEntry(196, I18n.a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.F.an),
         new GuiPageButtonList.GuiSlideEntry(197, I18n.a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.F.ao),
         new GuiPageButtonList.GuiLabelEntry(424, I18n.a("tile.oreRedstone.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(198, I18n.a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.F.ap),
         new GuiPageButtonList.GuiSlideEntry(199, I18n.a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.F.aq),
         new GuiPageButtonList.GuiSlideEntry(200, I18n.a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.F.ar),
         new GuiPageButtonList.GuiSlideEntry(201, I18n.a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.F.as),
         new GuiPageButtonList.GuiLabelEntry(425, I18n.a("tile.oreDiamond.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(202, I18n.a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.F.at),
         new GuiPageButtonList.GuiSlideEntry(203, I18n.a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.F.au),
         new GuiPageButtonList.GuiSlideEntry(204, I18n.a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.F.av),
         new GuiPageButtonList.GuiSlideEntry(205, I18n.a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.F.aw),
         new GuiPageButtonList.GuiLabelEntry(426, I18n.a("tile.oreLapis.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(206, I18n.a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.F.ax),
         new GuiPageButtonList.GuiSlideEntry(207, I18n.a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.F.ay),
         new GuiPageButtonList.GuiSlideEntry(208, I18n.a("createWorld.customize.custom.center"), false, this, 0.0F, 255.0F, this.F.az),
         new GuiPageButtonList.GuiSlideEntry(209, I18n.a("createWorld.customize.custom.spread"), false, this, 0.0F, 255.0F, this.F.aA)
      };
      GuiPageButtonList.GuiListEntry[] var3 = new GuiPageButtonList.GuiListEntry[]{
         new GuiPageButtonList.GuiSlideEntry(100, I18n.a("createWorld.customize.custom.mainNoiseScaleX"), false, this, 1.0F, 5000.0F, this.F.i),
         new GuiPageButtonList.GuiSlideEntry(101, I18n.a("createWorld.customize.custom.mainNoiseScaleY"), false, this, 1.0F, 5000.0F, this.F.j),
         new GuiPageButtonList.GuiSlideEntry(102, I18n.a("createWorld.customize.custom.mainNoiseScaleZ"), false, this, 1.0F, 5000.0F, this.F.k),
         new GuiPageButtonList.GuiSlideEntry(103, I18n.a("createWorld.customize.custom.depthNoiseScaleX"), false, this, 1.0F, 2000.0F, this.F.f),
         new GuiPageButtonList.GuiSlideEntry(104, I18n.a("createWorld.customize.custom.depthNoiseScaleZ"), false, this, 1.0F, 2000.0F, this.F.g),
         new GuiPageButtonList.GuiSlideEntry(105, I18n.a("createWorld.customize.custom.depthNoiseScaleExponent"), false, this, 0.01F, 20.0F, this.F.h),
         new GuiPageButtonList.GuiSlideEntry(106, I18n.a("createWorld.customize.custom.baseSize"), false, this, 1.0F, 25.0F, this.F.l),
         new GuiPageButtonList.GuiSlideEntry(107, I18n.a("createWorld.customize.custom.coordinateScale"), false, this, 1.0F, 6000.0F, this.F.b),
         new GuiPageButtonList.GuiSlideEntry(108, I18n.a("createWorld.customize.custom.heightScale"), false, this, 1.0F, 6000.0F, this.F.c),
         new GuiPageButtonList.GuiSlideEntry(109, I18n.a("createWorld.customize.custom.stretchY"), false, this, 0.01F, 50.0F, this.F.m),
         new GuiPageButtonList.GuiSlideEntry(110, I18n.a("createWorld.customize.custom.upperLimitScale"), false, this, 1.0F, 5000.0F, this.F.d),
         new GuiPageButtonList.GuiSlideEntry(111, I18n.a("createWorld.customize.custom.lowerLimitScale"), false, this, 1.0F, 5000.0F, this.F.e),
         new GuiPageButtonList.GuiSlideEntry(112, I18n.a("createWorld.customize.custom.biomeDepthWeight"), false, this, 1.0F, 20.0F, this.F.n),
         new GuiPageButtonList.GuiSlideEntry(113, I18n.a("createWorld.customize.custom.biomeDepthOffset"), false, this, 0.0F, 20.0F, this.F.o),
         new GuiPageButtonList.GuiSlideEntry(114, I18n.a("createWorld.customize.custom.biomeScaleWeight"), false, this, 1.0F, 20.0F, this.F.p),
         new GuiPageButtonList.GuiSlideEntry(115, I18n.a("createWorld.customize.custom.biomeScaleOffset"), false, this, 0.0F, 20.0F, this.F.q)
      };
      GuiPageButtonList.GuiListEntry[] var4 = new GuiPageButtonList.GuiListEntry[]{
         new GuiPageButtonList.GuiLabelEntry(400, I18n.a("createWorld.customize.custom.mainNoiseScaleX") + ":", false),
         new GuiPageButtonList.EditBoxEntry(132, String.format("%5.3f", this.F.i), false, this.D),
         new GuiPageButtonList.GuiLabelEntry(401, I18n.a("createWorld.customize.custom.mainNoiseScaleY") + ":", false),
         new GuiPageButtonList.EditBoxEntry(133, String.format("%5.3f", this.F.j), false, this.D),
         new GuiPageButtonList.GuiLabelEntry(402, I18n.a("createWorld.customize.custom.mainNoiseScaleZ") + ":", false),
         new GuiPageButtonList.EditBoxEntry(134, String.format("%5.3f", this.F.k), false, this.D),
         new GuiPageButtonList.GuiLabelEntry(403, I18n.a("createWorld.customize.custom.depthNoiseScaleX") + ":", false),
         new GuiPageButtonList.EditBoxEntry(135, String.format("%5.3f", this.F.f), false, this.D),
         new GuiPageButtonList.GuiLabelEntry(404, I18n.a("createWorld.customize.custom.depthNoiseScaleZ") + ":", false),
         new GuiPageButtonList.EditBoxEntry(136, String.format("%5.3f", this.F.g), false, this.D),
         new GuiPageButtonList.GuiLabelEntry(405, I18n.a("createWorld.customize.custom.depthNoiseScaleExponent") + ":", false),
         new GuiPageButtonList.EditBoxEntry(137, String.format("%2.3f", this.F.h), false, this.D),
         new GuiPageButtonList.GuiLabelEntry(406, I18n.a("createWorld.customize.custom.baseSize") + ":", false),
         new GuiPageButtonList.EditBoxEntry(138, String.format("%2.3f", this.F.l), false, this.D),
         new GuiPageButtonList.GuiLabelEntry(407, I18n.a("createWorld.customize.custom.coordinateScale") + ":", false),
         new GuiPageButtonList.EditBoxEntry(139, String.format("%5.3f", this.F.b), false, this.D),
         new GuiPageButtonList.GuiLabelEntry(408, I18n.a("createWorld.customize.custom.heightScale") + ":", false),
         new GuiPageButtonList.EditBoxEntry(140, String.format("%5.3f", this.F.c), false, this.D),
         new GuiPageButtonList.GuiLabelEntry(409, I18n.a("createWorld.customize.custom.stretchY") + ":", false),
         new GuiPageButtonList.EditBoxEntry(141, String.format("%2.3f", this.F.m), false, this.D),
         new GuiPageButtonList.GuiLabelEntry(410, I18n.a("createWorld.customize.custom.upperLimitScale") + ":", false),
         new GuiPageButtonList.EditBoxEntry(142, String.format("%5.3f", this.F.d), false, this.D),
         new GuiPageButtonList.GuiLabelEntry(411, I18n.a("createWorld.customize.custom.lowerLimitScale") + ":", false),
         new GuiPageButtonList.EditBoxEntry(143, String.format("%5.3f", this.F.e), false, this.D),
         new GuiPageButtonList.GuiLabelEntry(412, I18n.a("createWorld.customize.custom.biomeDepthWeight") + ":", false),
         new GuiPageButtonList.EditBoxEntry(144, String.format("%2.3f", this.F.n), false, this.D),
         new GuiPageButtonList.GuiLabelEntry(413, I18n.a("createWorld.customize.custom.biomeDepthOffset") + ":", false),
         new GuiPageButtonList.EditBoxEntry(145, String.format("%2.3f", this.F.o), false, this.D),
         new GuiPageButtonList.GuiLabelEntry(414, I18n.a("createWorld.customize.custom.biomeScaleWeight") + ":", false),
         new GuiPageButtonList.EditBoxEntry(146, String.format("%2.3f", this.F.p), false, this.D),
         new GuiPageButtonList.GuiLabelEntry(415, I18n.a("createWorld.customize.custom.biomeScaleOffset") + ":", false),
         new GuiPageButtonList.EditBoxEntry(147, String.format("%2.3f", this.F.q), false, this.D)
      };
      this.r = new GuiPageButtonList(this.j, this.l, this.m, 32, this.m - 32, 25, this, var1, var2, var3, var4);

      for (int var5 = 0; var5 < 4; var5++) {
         this.h[var5] = I18n.a("createWorld.customize.custom.page" + var5);
      }

      this.i();
   }

   public String a() {
      return this.F.toString().replace("\n", "");
   }

   public void a(String var1) {
      if (var1 != null && var1.length() != 0) {
         this.F = ChunkProviderSettings.Factory.a(var1);
      } else {
         this.F = new ChunkProviderSettings.Factory();
      }
   }

   @Override
   public void a(int var1, String var2) {
      float var3 = 0.0F;

      try {
         var3 = Float.parseFloat(var2);
      } catch (NumberFormatException var5) {
      }

      float var4 = 0.0F;
      switch (var1) {
         case 132:
            var4 = this.F.i = MathHelper.a(var3, 1.0F, 5000.0F);
            break;
         case 133:
            var4 = this.F.j = MathHelper.a(var3, 1.0F, 5000.0F);
            break;
         case 134:
            var4 = this.F.k = MathHelper.a(var3, 1.0F, 5000.0F);
            break;
         case 135:
            var4 = this.F.f = MathHelper.a(var3, 1.0F, 2000.0F);
            break;
         case 136:
            var4 = this.F.g = MathHelper.a(var3, 1.0F, 2000.0F);
            break;
         case 137:
            var4 = this.F.h = MathHelper.a(var3, 0.01F, 20.0F);
            break;
         case 138:
            var4 = this.F.l = MathHelper.a(var3, 1.0F, 25.0F);
            break;
         case 139:
            var4 = this.F.b = MathHelper.a(var3, 1.0F, 6000.0F);
            break;
         case 140:
            var4 = this.F.c = MathHelper.a(var3, 1.0F, 6000.0F);
            break;
         case 141:
            var4 = this.F.m = MathHelper.a(var3, 0.01F, 50.0F);
            break;
         case 142:
            var4 = this.F.d = MathHelper.a(var3, 1.0F, 5000.0F);
            break;
         case 143:
            var4 = this.F.e = MathHelper.a(var3, 1.0F, 5000.0F);
            break;
         case 144:
            var4 = this.F.n = MathHelper.a(var3, 1.0F, 20.0F);
            break;
         case 145:
            var4 = this.F.o = MathHelper.a(var3, 0.0F, 20.0F);
            break;
         case 146:
            var4 = this.F.p = MathHelper.a(var3, 1.0F, 20.0F);
            break;
         case 147:
            var4 = this.F.q = MathHelper.a(var3, 0.0F, 20.0F);
      }

      if (var4 != var3 && var3 != 0.0F) {
         ((GuiTextField)this.r.d(var1)).a(this.b(var1, var4));
      }

      ((GuiSlider)this.r.d(var1 - 132 + 100)).a(var4, false);
      if (!this.F.equals(this.E)) {
         this.a(true);
      }
   }

   private void a(boolean var1) {
      this.A = var1;
      this.u.l = var1;
   }

   @Override
   public String a(int var1, String var2, float var3) {
      return var2 + ": " + this.b(var1, var3);
   }

   private String b(int var1, float var2) {
      switch (var1) {
         case 100:
         case 101:
         case 102:
         case 103:
         case 104:
         case 107:
         case 108:
         case 110:
         case 111:
         case 132:
         case 133:
         case 134:
         case 135:
         case 136:
         case 139:
         case 140:
         case 142:
         case 143:
            return String.format("%5.3f", var2);
         case 105:
         case 106:
         case 109:
         case 112:
         case 113:
         case 114:
         case 115:
         case 137:
         case 138:
         case 141:
         case 144:
         case 145:
         case 146:
         case 147:
            return String.format("%2.3f", var2);
         case 116:
         case 117:
         case 118:
         case 119:
         case 120:
         case 121:
         case 122:
         case 123:
         case 124:
         case 125:
         case 126:
         case 127:
         case 128:
         case 129:
         case 130:
         case 131:
         case 148:
         case 149:
         case 150:
         case 151:
         case 152:
         case 153:
         case 154:
         case 155:
         case 156:
         case 157:
         case 158:
         case 159:
         case 160:
         case 161:
         default:
            return String.format("%d", (int)var2);
         case 162:
            if (var2 < 0.0F) {
               return I18n.a("gui.all");
            } else if ((int)var2 >= BiomeGenBase.x.az) {
               BiomeGenBase var4 = BiomeGenBase.n()[(int)var2 + 2];
               return var4 != null ? var4.ah : "?";
            } else {
               BiomeGenBase var3 = BiomeGenBase.n()[(int)var2];
               return var3 != null ? var3.ah : "?";
            }
      }
   }

   @Override
   public void a(int var1, boolean var2) {
      switch (var1) {
         case 148:
            this.F.s = var2;
            break;
         case 149:
            this.F.t = var2;
            break;
         case 150:
            this.F.v = var2;
            break;
         case 151:
            this.F.w = var2;
            break;
         case 152:
            this.F.x = var2;
            break;
         case 153:
            this.F.y = var2;
            break;
         case 154:
            this.F.A = var2;
            break;
         case 155:
            this.F.B = var2;
            break;
         case 156:
            this.F.D = var2;
            break;
         case 161:
            this.F.F = var2;
            break;
         case 210:
            this.F.z = var2;
      }

      if (!this.F.equals(this.E)) {
         this.a(true);
      }
   }

   @Override
   public void a(int var1, float var2) {
      switch (var1) {
         case 100:
            this.F.i = var2;
            break;
         case 101:
            this.F.j = var2;
            break;
         case 102:
            this.F.k = var2;
            break;
         case 103:
            this.F.f = var2;
            break;
         case 104:
            this.F.g = var2;
            break;
         case 105:
            this.F.h = var2;
            break;
         case 106:
            this.F.l = var2;
            break;
         case 107:
            this.F.b = var2;
            break;
         case 108:
            this.F.c = var2;
            break;
         case 109:
            this.F.m = var2;
            break;
         case 110:
            this.F.d = var2;
            break;
         case 111:
            this.F.e = var2;
            break;
         case 112:
            this.F.n = var2;
            break;
         case 113:
            this.F.o = var2;
            break;
         case 114:
            this.F.p = var2;
            break;
         case 115:
            this.F.q = var2;
         case 116:
         case 117:
         case 118:
         case 119:
         case 120:
         case 121:
         case 122:
         case 123:
         case 124:
         case 125:
         case 126:
         case 127:
         case 128:
         case 129:
         case 130:
         case 131:
         case 132:
         case 133:
         case 134:
         case 135:
         case 136:
         case 137:
         case 138:
         case 139:
         case 140:
         case 141:
         case 142:
         case 143:
         case 144:
         case 145:
         case 146:
         case 147:
         case 148:
         case 149:
         case 150:
         case 151:
         case 152:
         case 153:
         case 154:
         case 155:
         case 156:
         case 161:
         case 188:
         default:
            break;
         case 157:
            this.F.u = (int)var2;
            break;
         case 158:
            this.F.C = (int)var2;
            break;
         case 159:
            this.F.E = (int)var2;
            break;
         case 160:
            this.F.r = (int)var2;
            break;
         case 162:
            this.F.G = (int)var2;
            break;
         case 163:
            this.F.H = (int)var2;
            break;
         case 164:
            this.F.I = (int)var2;
            break;
         case 165:
            this.F.J = (int)var2;
            break;
         case 166:
            this.F.K = (int)var2;
            break;
         case 167:
            this.F.L = (int)var2;
            break;
         case 168:
            this.F.M = (int)var2;
            break;
         case 169:
            this.F.N = (int)var2;
            break;
         case 170:
            this.F.O = (int)var2;
            break;
         case 171:
            this.F.P = (int)var2;
            break;
         case 172:
            this.F.Q = (int)var2;
            break;
         case 173:
            this.F.R = (int)var2;
            break;
         case 174:
            this.F.S = (int)var2;
            break;
         case 175:
            this.F.T = (int)var2;
            break;
         case 176:
            this.F.U = (int)var2;
            break;
         case 177:
            this.F.V = (int)var2;
            break;
         case 178:
            this.F.W = (int)var2;
            break;
         case 179:
            this.F.X = (int)var2;
            break;
         case 180:
            this.F.Y = (int)var2;
            break;
         case 181:
            this.F.Z = (int)var2;
            break;
         case 182:
            this.F.aa = (int)var2;
            break;
         case 183:
            this.F.ab = (int)var2;
            break;
         case 184:
            this.F.ac = (int)var2;
            break;
         case 185:
            this.F.ad = (int)var2;
            break;
         case 186:
            this.F.ae = (int)var2;
            break;
         case 187:
            this.F.af = (int)var2;
            break;
         case 189:
            this.F.ag = (int)var2;
            break;
         case 190:
            this.F.ah = (int)var2;
            break;
         case 191:
            this.F.ai = (int)var2;
            break;
         case 192:
            this.F.aj = (int)var2;
            break;
         case 193:
            this.F.ak = (int)var2;
            break;
         case 194:
            this.F.al = (int)var2;
            break;
         case 195:
            this.F.am = (int)var2;
            break;
         case 196:
            this.F.an = (int)var2;
            break;
         case 197:
            this.F.ao = (int)var2;
            break;
         case 198:
            this.F.ap = (int)var2;
            break;
         case 199:
            this.F.aq = (int)var2;
            break;
         case 200:
            this.F.ar = (int)var2;
            break;
         case 201:
            this.F.as = (int)var2;
            break;
         case 202:
            this.F.at = (int)var2;
            break;
         case 203:
            this.F.au = (int)var2;
            break;
         case 204:
            this.F.av = (int)var2;
            break;
         case 205:
            this.F.aw = (int)var2;
            break;
         case 206:
            this.F.ax = (int)var2;
            break;
         case 207:
            this.F.ay = (int)var2;
            break;
         case 208:
            this.F.az = (int)var2;
            break;
         case 209:
            this.F.aA = (int)var2;
      }

      if (var1 >= 100 && var1 < 116) {
         Gui var3 = this.r.d(var1 - 100 + 132);
         if (var3 != null) {
            ((GuiTextField)var3).a(this.b(var1, var2));
         }
      }

      if (!this.F.equals(this.E)) {
         this.a(true);
      }
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         switch (var1.k) {
            case 300:
               this.i.a = this.F.toString();
               this.j.a(this.i);
               break;
            case 301:
               for (int var2 = 0; var2 < this.r.b(); var2++) {
                  GuiPageButtonList.GuiEntry var3 = this.r.e(var2);
                  Gui var4 = var3.a();
                  if (var4 instanceof GuiButton) {
                     GuiButton var5 = (GuiButton)var4;
                     if (var5 instanceof GuiSlider) {
                        float var6 = ((GuiSlider)var5).d() * (0.75F + this.G.nextFloat() * 0.5F) + (this.G.nextFloat() * 0.1F - 0.05F);
                        ((GuiSlider)var5).a(MathHelper.a(var6, 0.0F, 1.0F));
                     } else if (var5 instanceof GuiListButton) {
                        ((GuiListButton)var5).b(this.G.nextBoolean());
                     }
                  }

                  Gui var8 = var3.b();
                  if (var8 instanceof GuiButton) {
                     GuiButton var9 = (GuiButton)var8;
                     if (var9 instanceof GuiSlider) {
                        float var7 = ((GuiSlider)var9).d() * (0.75F + this.G.nextFloat() * 0.5F) + (this.G.nextFloat() * 0.1F - 0.05F);
                        ((GuiSlider)var9).a(MathHelper.a(var7, 0.0F, 1.0F));
                     } else if (var9 instanceof GuiListButton) {
                        ((GuiListButton)var9).b(this.G.nextBoolean());
                     }
                  }
               }
               break;
            case 302:
               this.r.h();
               this.i();
               break;
            case 303:
               this.r.i();
               this.i();
               break;
            case 304:
               if (this.A) {
                  this.b(304);
               }
               break;
            case 305:
               this.j.a(new GuiScreenCustomizePresets(this));
               break;
            case 306:
               this.h();
               break;
            case 307:
               this.B = 0;
               this.h();
         }
      }
   }

   private void g() {
      this.F.a();
      this.f();
      this.a(false);
   }

   private void b(int var1) {
      this.B = var1;
      this.b(true);
   }

   private void h() {
      switch (this.B) {
         case 300:
            this.a((GuiListButton)this.r.d(300));
            break;
         case 304:
            this.g();
      }

      this.B = 0;
      this.C = true;
      this.b(false);
   }

   private void b(boolean var1) {
      this.x.m = var1;
      this.y.m = var1;
      this.t.l = !var1;
      this.s.l = !var1;
      this.v.l = !var1;
      this.w.l = !var1;
      this.u.l = this.A && !var1;
      this.z.l = !var1;
      this.r.a(!var1);
   }

   private void i() {
      this.v.l = this.r.e() != 0;
      this.w.l = this.r.e() != this.r.f() - 1;
      this.f = I18n.a("book.pageIndicator", this.r.e() + 1, this.r.f());
      this.g = this.h[this.r.e()];
      this.t.l = this.r.e() != this.r.f() - 1;
   }

   @Override
   protected void a(char var1, int var2) {
      super.a(var1, var2);
      if (this.B == 0) {
         switch (var2) {
            case 200:
               this.a(1.0F);
               break;
            case 208:
               this.a(-1.0F);
               break;
            default:
               this.r.a(var1, var2);
         }
      }
   }

   private void a(float var1) {
      Gui var2 = this.r.g();
      if (var2 instanceof GuiTextField) {
         float var3 = var1;
         if (GuiScreen.r()) {
            var3 = var1 * 0.1F;
            if (GuiScreen.q()) {
               var3 *= 0.1F;
            }
         } else if (GuiScreen.q()) {
            var3 = var1 * 10.0F;
            if (GuiScreen.s()) {
               var3 *= 10.0F;
            }
         }

         GuiTextField var4 = (GuiTextField)var2;
         Float var5 = Floats.tryParse(var4.b());
         if (var5 != null) {
            var5 = var5 + var3;
            int var6 = var4.d();
            String var7 = this.b(var4.d(), var5);
            var4.a(var7);
            this.a(var6, var7);
         }
      }
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      super.a(var1, var2, var3);
      if (this.B == 0 && !this.C) {
         this.r.b(var1, var2, var3);
      }
   }

   @Override
   protected void b(int var1, int var2, int var3) {
      super.b(var1, var2, var3);
      if (this.C) {
         this.C = false;
      } else if (this.B == 0) {
         this.r.c(var1, var2, var3);
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.r.a(var1, var2, var3);
      this.a(this.q, this.a, this.l / 2, 2, 16777215);
      this.a(this.q, this.f, this.l / 2, 12, 16777215);
      this.a(this.q, this.g, this.l / 2, 22, 16777215);
      super.a(var1, var2, var3);
      if (this.B != 0) {
         a(0, 0, this.l, this.m, Integer.MIN_VALUE);
         this.a(this.l / 2 - 91, this.l / 2 + 90, 99, -2039584);
         this.a(this.l / 2 - 91, this.l / 2 + 90, 185, -6250336);
         this.b(this.l / 2 - 91, 99, 185, -2039584);
         this.b(this.l / 2 + 90, 99, 185, -6250336);
         float var4 = 85.0F;
         float var5 = 180.0F;
         GlStateManager.f();
         GlStateManager.n();
         Tessellator var6 = Tessellator.a();
         WorldRenderer var7 = var6.c();
         this.j.P().a(b);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         float var8 = 32.0F;
         var7.a(7, DefaultVertexFormats.i);
         var7.b((double)(this.l / 2 - 90), 185.0, 0.0).a(0.0, 2.65625).b(64, 64, 64, 64).d();
         var7.b((double)(this.l / 2 + 90), 185.0, 0.0).a(5.625, 2.65625).b(64, 64, 64, 64).d();
         var7.b((double)(this.l / 2 + 90), 100.0, 0.0).a(5.625, 0.0).b(64, 64, 64, 64).d();
         var7.b((double)(this.l / 2 - 90), 100.0, 0.0).a(0.0, 0.0).b(64, 64, 64, 64).d();
         var6.b();
         this.a(this.q, I18n.a("createWorld.customize.custom.confirmTitle"), this.l / 2, 105, 16777215);
         this.a(this.q, I18n.a("createWorld.customize.custom.confirm1"), this.l / 2, 125, 16777215);
         this.a(this.q, I18n.a("createWorld.customize.custom.confirm2"), this.l / 2, 135, 16777215);
         this.x.a(this.j, var1, var2);
         this.y.a(this.j, var1, var2);
      }
   }
}
