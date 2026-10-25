package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import org.lwjgl.input.Keyboard;

public class GuiFlatPresets extends GuiScreen {
   private static final List<GuiFlatPresets.LayerItem> a = Lists.newArrayList();
   private final GuiCreateFlatWorld f;
   private String g;
   private String h;
   private String i;
   private GuiFlatPresets.ListSlot r;
   private GuiButton s;
   private GuiTextField t;

   public GuiFlatPresets(GuiCreateFlatWorld var1) {
      this.f = var1;
   }

   @Override
   public void b() {
      this.n.clear();
      Keyboard.enableRepeatEvents(true);
      this.g = I18n.a("createWorld.customize.presets.title");
      this.h = I18n.a("createWorld.customize.presets.share");
      this.i = I18n.a("createWorld.customize.presets.list");
      this.t = new GuiTextField(2, this.q, 50, 40, this.l - 100, 20);
      this.r = new GuiFlatPresets.ListSlot();
      this.t.f(1230);
      this.t.a(this.f.a());
      this.n.add(this.s = new GuiButton(0, this.l / 2 - 155, this.m - 28, 150, 20, I18n.a("createWorld.customize.presets.select")));
      this.n.add(new GuiButton(1, this.l / 2 + 5, this.m - 28, 150, 20, I18n.a("gui.cancel")));
      this.a();
   }

   @Override
   public void k() {
      super.k();
      this.r.p();
   }

   @Override
   public void m() {
      Keyboard.enableRepeatEvents(false);
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      this.t.a(var1, var2, var3);
      super.a(var1, var2, var3);
   }

   @Override
   protected void a(char var1, int var2) {
      if (!this.t.a(var1, var2)) {
         super.a(var1, var2);
      }
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.k == 0 && this.g()) {
         this.f.a(this.t.b());
         this.j.a(this.f);
      } else if (var1.k == 1) {
         this.j.a(this.f);
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.r.a(var1, var2, var3);
      this.a(this.q, this.g, this.l / 2, 8, 16777215);
      this.c(this.q, this.h, 50, 30, 10526880);
      this.c(this.q, this.i, 50, 70, 10526880);
      this.t.g();
      super.a(var1, var2, var3);
   }

   @Override
   public void e() {
      this.t.a();
      super.e();
   }

   public void a() {
      boolean var1 = this.g();
      this.s.l = var1;
   }

   private boolean g() {
      return this.r.u > -1 && this.r.u < a.size() || this.t.b().length() > 1;
   }

   private static void a(String var0, Item var1, BiomeGenBase var2, FlatLayerInfo... var3) {
      a(var0, var1, 0, var2, null, var3);
   }

   private static void a(String var0, Item var1, BiomeGenBase var2, List<String> var3, FlatLayerInfo... var4) {
      a(var0, var1, 0, var2, var3, var4);
   }

   private static void a(String var0, Item var1, int var2, BiomeGenBase var3, List<String> var4, FlatLayerInfo... var5) {
      FlatGeneratorInfo var6 = new FlatGeneratorInfo();

      for (int var7 = var5.length - 1; var7 >= 0; var7--) {
         var6.c().add(var5[var7]);
      }

      var6.a(var3.az);
      var6.d();
      if (var4 != null) {
         for (String var8 : var4) {
            var6.b().put(var8, Maps.newHashMap());
         }
      }

      a.add(new GuiFlatPresets.LayerItem(var1, var2, var0, var6.toString()));
   }

   static {
      a(
         "Classic Flat",
         Item.a(Blocks.c),
         BiomeGenBase.q,
         Arrays.asList("village"),
         new FlatLayerInfo(1, Blocks.c),
         new FlatLayerInfo(2, Blocks.d),
         new FlatLayerInfo(1, Blocks.h)
      );
      a(
         "Tunnelers' Dream",
         Item.a(Blocks.b),
         BiomeGenBase.s,
         Arrays.asList("biome_1", "dungeon", "decoration", "stronghold", "mineshaft"),
         new FlatLayerInfo(1, Blocks.c),
         new FlatLayerInfo(5, Blocks.d),
         new FlatLayerInfo(230, Blocks.b),
         new FlatLayerInfo(1, Blocks.h)
      );
      a(
         "Water World",
         Items.ax,
         BiomeGenBase.N,
         Arrays.asList("biome_1", "oceanmonument"),
         new FlatLayerInfo(90, Blocks.j),
         new FlatLayerInfo(5, Blocks.m),
         new FlatLayerInfo(5, Blocks.d),
         new FlatLayerInfo(5, Blocks.b),
         new FlatLayerInfo(1, Blocks.h)
      );
      a(
         "Overworld",
         Item.a(Blocks.H),
         BlockTallGrass.EnumType.b.a(),
         BiomeGenBase.q,
         Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake"),
         new FlatLayerInfo(1, Blocks.c),
         new FlatLayerInfo(3, Blocks.d),
         new FlatLayerInfo(59, Blocks.b),
         new FlatLayerInfo(1, Blocks.h)
      );
      a(
         "Snowy Kingdom",
         Item.a(Blocks.aH),
         BiomeGenBase.B,
         Arrays.asList("village", "biome_1"),
         new FlatLayerInfo(1, Blocks.aH),
         new FlatLayerInfo(1, Blocks.c),
         new FlatLayerInfo(3, Blocks.d),
         new FlatLayerInfo(59, Blocks.b),
         new FlatLayerInfo(1, Blocks.h)
      );
      a(
         "Bottomless Pit",
         Items.G,
         BiomeGenBase.q,
         Arrays.asList("village", "biome_1"),
         new FlatLayerInfo(1, Blocks.c),
         new FlatLayerInfo(3, Blocks.d),
         new FlatLayerInfo(2, Blocks.e)
      );
      a(
         "Desert",
         Item.a(Blocks.m),
         BiomeGenBase.r,
         Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"),
         new FlatLayerInfo(8, Blocks.m),
         new FlatLayerInfo(52, Blocks.A),
         new FlatLayerInfo(3, Blocks.b),
         new FlatLayerInfo(1, Blocks.h)
      );
      a("Redstone Ready", Items.aC, BiomeGenBase.r, new FlatLayerInfo(52, Blocks.A), new FlatLayerInfo(3, Blocks.b), new FlatLayerInfo(1, Blocks.h));
   }

   static class LayerItem {
      public Item a;
      public int b;
      public String c;
      public String d;

      public LayerItem(Item var1, int var2, String var3, String var4) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
         this.d = var4;
      }
   }

   class ListSlot extends GuiSlot {
      public int u = -1;

      public ListSlot() {
         super(GuiFlatPresets.this.j, GuiFlatPresets.this.l, GuiFlatPresets.this.m, 80, GuiFlatPresets.this.m - 37, 24);
      }

      private void a(int var1, int var2, Item var3, int var4) {
         this.e(var1 + 1, var2 + 1);
         GlStateManager.B();
         RenderHelper.c();
         GuiFlatPresets.this.k.a(new ItemStack(var3, 1, var4), var1 + 2, var2 + 2);
         RenderHelper.a();
         GlStateManager.C();
      }

      private void e(int var1, int var2) {
         this.d(var1, var2, 0, 0);
      }

      private void d(int var1, int var2, int var3, int var4) {
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         this.a.P().a(Gui.c);
         float var5 = 0.0078125F;
         float var6 = 0.0078125F;
         byte var7 = 18;
         byte var8 = 18;
         Tessellator var9 = Tessellator.a();
         WorldRenderer var10 = var9.c();
         var10.a(7, DefaultVertexFormats.g);
         var10.b((double)(var1 + 0), (double)(var2 + 18), (double)GuiFlatPresets.this.e)
            .a((double)((var3 + 0) * 0.0078125F), (double)((var4 + 18) * 0.0078125F))
            .d();
         var10.b((double)(var1 + 18), (double)(var2 + 18), (double)GuiFlatPresets.this.e)
            .a((double)((var3 + 18) * 0.0078125F), (double)((var4 + 18) * 0.0078125F))
            .d();
         var10.b((double)(var1 + 18), (double)(var2 + 0), (double)GuiFlatPresets.this.e)
            .a((double)((var3 + 18) * 0.0078125F), (double)((var4 + 0) * 0.0078125F))
            .d();
         var10.b((double)(var1 + 0), (double)(var2 + 0), (double)GuiFlatPresets.this.e)
            .a((double)((var3 + 0) * 0.0078125F), (double)((var4 + 0) * 0.0078125F))
            .d();
         var9.b();
      }

      @Override
      protected int b() {
         return GuiFlatPresets.a.size();
      }

      @Override
      protected void a(int var1, boolean var2, int var3, int var4) {
         this.u = var1;
         GuiFlatPresets.this.a();
         GuiFlatPresets.this.t.a(GuiFlatPresets.a.get(GuiFlatPresets.this.r.u).d);
      }

      @Override
      protected boolean a(int var1) {
         return var1 == this.u;
      }

      @Override
      protected void a() {
      }

      @Override
      protected void a(int var1, int var2, int var3, int var4, int var5, int var6) {
         GuiFlatPresets.LayerItem var7 = GuiFlatPresets.a.get(var1);
         this.a(var2, var3, var7.a, var7.b);
         GuiFlatPresets.this.q.a(var7.c, var2 + 18 + 5, var3 + 6, 16777215);
      }
   }
}
