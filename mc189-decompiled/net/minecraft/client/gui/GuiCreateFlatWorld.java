package net.minecraft.client.gui;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;

public class GuiCreateFlatWorld extends GuiScreen {
   private final GuiCreateWorld a;
   private FlatGeneratorInfo f = FlatGeneratorInfo.e();
   private String g;
   private String h;
   private String i;
   private GuiCreateFlatWorld.Details r;
   private GuiButton s;
   private GuiButton t;
   private GuiButton u;

   public GuiCreateFlatWorld(GuiCreateWorld var1, String var2) {
      this.a = var1;
      this.a(var2);
   }

   public String a() {
      return this.f.toString();
   }

   public void a(String var1) {
      this.f = FlatGeneratorInfo.a(var1);
   }

   @Override
   public void b() {
      this.n.clear();
      this.g = I18n.a("createWorld.customize.flat.title");
      this.h = I18n.a("createWorld.customize.flat.tile");
      this.i = I18n.a("createWorld.customize.flat.height");
      this.r = new GuiCreateFlatWorld.Details();
      this.n.add(this.s = new GuiButton(2, this.l / 2 - 154, this.m - 52, 100, 20, I18n.a("createWorld.customize.flat.addLayer") + " (NYI)"));
      this.n.add(this.t = new GuiButton(3, this.l / 2 - 50, this.m - 52, 100, 20, I18n.a("createWorld.customize.flat.editLayer") + " (NYI)"));
      this.n.add(this.u = new GuiButton(4, this.l / 2 - 155, this.m - 52, 150, 20, I18n.a("createWorld.customize.flat.removeLayer")));
      this.n.add(new GuiButton(0, this.l / 2 - 155, this.m - 28, 150, 20, I18n.a("gui.done")));
      this.n.add(new GuiButton(5, this.l / 2 + 5, this.m - 52, 150, 20, I18n.a("createWorld.customize.presets")));
      this.n.add(new GuiButton(1, this.l / 2 + 5, this.m - 28, 150, 20, I18n.a("gui.cancel")));
      this.s.m = this.t.m = false;
      this.f.d();
      this.f();
   }

   @Override
   public void k() {
      super.k();
      this.r.p();
   }

   @Override
   protected void a(GuiButton var1) {
      int var2 = this.f.c().size() - this.r.u - 1;
      if (var1.k == 1) {
         this.j.a(this.a);
      } else if (var1.k == 0) {
         this.a.a = this.a();
         this.j.a(this.a);
      } else if (var1.k == 5) {
         this.j.a(new GuiFlatPresets(this));
      } else if (var1.k == 4 && this.g()) {
         this.f.c().remove(var2);
         this.r.u = Math.min(this.r.u, this.f.c().size() - 1);
      }

      this.f.d();
      this.f();
   }

   public void f() {
      boolean var1 = this.g();
      this.u.l = var1;
      this.t.l = var1;
      this.t.l = false;
      this.s.l = false;
   }

   private boolean g() {
      return this.r.u > -1 && this.r.u < this.f.c().size();
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.r.a(var1, var2, var3);
      this.a(this.q, this.g, this.l / 2, 8, 16777215);
      int var4 = this.l / 2 - 92 - 16;
      this.c(this.q, this.h, var4, 32, 16777215);
      this.c(this.q, this.i, var4 + 2 + 213 - this.q.a(this.i), 32, 16777215);
      super.a(var1, var2, var3);
   }

   class Details extends GuiSlot {
      public int u = -1;

      public Details() {
         super(GuiCreateFlatWorld.this.j, GuiCreateFlatWorld.this.l, GuiCreateFlatWorld.this.m, 43, GuiCreateFlatWorld.this.m - 60, 24);
      }

      private void a(int var1, int var2, ItemStack var3) {
         this.e(var1 + 1, var2 + 1);
         GlStateManager.B();
         if (var3 != null && var3.b() != null) {
            RenderHelper.c();
            GuiCreateFlatWorld.this.k.a(var3, var1 + 2, var2 + 2);
            RenderHelper.a();
         }

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
         var10.b((double)(var1 + 0), (double)(var2 + 18), (double)GuiCreateFlatWorld.this.e)
            .a((double)((var3 + 0) * 0.0078125F), (double)((var4 + 18) * 0.0078125F))
            .d();
         var10.b((double)(var1 + 18), (double)(var2 + 18), (double)GuiCreateFlatWorld.this.e)
            .a((double)((var3 + 18) * 0.0078125F), (double)((var4 + 18) * 0.0078125F))
            .d();
         var10.b((double)(var1 + 18), (double)(var2 + 0), (double)GuiCreateFlatWorld.this.e)
            .a((double)((var3 + 18) * 0.0078125F), (double)((var4 + 0) * 0.0078125F))
            .d();
         var10.b((double)(var1 + 0), (double)(var2 + 0), (double)GuiCreateFlatWorld.this.e)
            .a((double)((var3 + 0) * 0.0078125F), (double)((var4 + 0) * 0.0078125F))
            .d();
         var9.b();
      }

      @Override
      protected int b() {
         return GuiCreateFlatWorld.this.f.c().size();
      }

      @Override
      protected void a(int var1, boolean var2, int var3, int var4) {
         this.u = var1;
         GuiCreateFlatWorld.this.f();
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
         FlatLayerInfo var7 = GuiCreateFlatWorld.this.f.c().get(GuiCreateFlatWorld.this.f.c().size() - var1 - 1);
         IBlockState var8 = var7.c();
         Block var9 = var8.c();
         Item var10 = Item.a(var9);
         ItemStack var11 = var9 != Blocks.a && var10 != null ? new ItemStack(var10, 1, var9.c(var8)) : null;
         String var12 = var11 == null ? "Air" : var10.a(var11);
         if (var10 == null) {
            if (var9 == Blocks.j || var9 == Blocks.i) {
               var10 = Items.ax;
            } else if (var9 == Blocks.l || var9 == Blocks.k) {
               var10 = Items.ay;
            }

            if (var10 != null) {
               var11 = new ItemStack(var10, 1, var9.c(var8));
               var12 = var9.f();
            }
         }

         this.a(var2, var3, var11);
         GuiCreateFlatWorld.this.q.a(var12, var2 + 18 + 5, var3 + 3, 16777215);
         String var13;
         if (var1 == 0) {
            var13 = I18n.a("createWorld.customize.flat.layer.top", var7.b());
         } else if (var1 == GuiCreateFlatWorld.this.f.c().size() - 1) {
            var13 = I18n.a("createWorld.customize.flat.layer.bottom", var7.b());
         } else {
            var13 = I18n.a("createWorld.customize.flat.layer", var7.b());
         }

         GuiCreateFlatWorld.this.q.a(var13, var2 + 2 + 213 - GuiCreateFlatWorld.this.q.a(var13), var3 + 3, 16777215);
      }

      @Override
      protected int d() {
         return this.b - 70;
      }
   }
}
