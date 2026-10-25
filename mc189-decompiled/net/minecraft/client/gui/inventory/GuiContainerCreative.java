package net.minecraft.client.gui.inventory;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiContainerCreative extends InventoryEffectRenderer {
   private static final ResourceLocation u = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
   private static InventoryBasic v = new InventoryBasic("tmp", true, 45);
   private static int w = CreativeTabs.b.a();
   private float x;
   private boolean y;
   private boolean z;
   private GuiTextField A;
   private List<Slot> B;
   private Slot C;
   private boolean D;
   private CreativeCrafting E;

   public GuiContainerCreative(EntityPlayer var1) {
      super(new GuiContainerCreative.ContainerCreative(var1));
      var1.bk = this.h;
      this.p = true;
      this.g = 136;
      this.f = 195;
   }

   @Override
   public void e() {
      if (!this.j.c.h()) {
         this.j.a(new GuiInventory(this.j.h));
      }

      this.a();
   }

   @Override
   protected void a(Slot var1, int var2, int var3, int var4) {
      this.D = true;
      boolean var5 = var4 == 1;
      var4 = var2 == -999 && var4 == 0 ? 4 : var4;
      if (var1 == null && w != CreativeTabs.m.a() && var4 != 5) {
         InventoryPlayer var15 = this.j.h.bi;
         if (var15.p() != null) {
            if (var3 == 0) {
               this.j.h.a(var15.p(), true);
               this.j.c.a(var15.p());
               var15.b(null);
            }

            if (var3 == 1) {
               ItemStack var17 = var15.p().a(1);
               this.j.h.a(var17, true);
               this.j.c.a(var17);
               if (var15.p().b == 0) {
                  var15.b(null);
               }
            }
         }
      } else if (var1 == this.C && var5) {
         for (int var14 = 0; var14 < this.j.h.bj.a().size(); var14++) {
            this.j.c.a(null, var14);
         }
      } else if (w == CreativeTabs.m.a()) {
         if (var1 == this.C) {
            this.j.h.bi.b(null);
         } else if (var4 == 4 && var1 != null && var1.e()) {
            ItemStack var6 = var1.a(var3 == 0 ? 1 : var1.d().c());
            this.j.h.a(var6, true);
            this.j.c.a(var6);
         } else if (var4 == 4 && this.j.h.bi.p() != null) {
            this.j.h.a(this.j.h.bi.p(), true);
            this.j.c.a(this.j.h.bi.p());
            this.j.h.bi.b(null);
         } else {
            this.j.h.bj.a(var1 == null ? var2 : ((GuiContainerCreative.CreativeSlot)var1).b.e, var3, var4, this.j.h);
            this.j.h.bj.b();
         }
      } else if (var4 != 5 && var1.d == v) {
         InventoryPlayer var13 = this.j.h.bi;
         ItemStack var7 = var13.p();
         ItemStack var8 = var1.d();
         if (var4 == 2) {
            if (var8 != null && var3 >= 0 && var3 < 9) {
               ItemStack var19 = var8.k();
               var19.b = var19.c();
               this.j.h.bi.a(var3, var19);
               this.j.h.bj.b();
            }

            return;
         }

         if (var4 == 3) {
            if (var13.p() == null && var1.e()) {
               ItemStack var18 = var1.d().k();
               var18.b = var18.c();
               var13.b(var18);
            }

            return;
         }

         if (var4 == 4) {
            if (var8 != null) {
               ItemStack var9 = var8.k();
               var9.b = var3 == 0 ? 1 : var9.c();
               this.j.h.a(var9, true);
               this.j.c.a(var9);
            }

            return;
         }

         if (var7 != null && var8 != null && var7.a(var8)) {
            if (var3 == 0) {
               if (var5) {
                  var7.b = var7.c();
               } else if (var7.b < var7.c()) {
                  var7.b++;
               }
            } else if (var7.b <= 1) {
               var13.b(null);
            } else {
               var7.b--;
            }
         } else if (var8 != null && var7 == null) {
            var13.b(ItemStack.b(var8));
            var7 = var13.p();
            if (var5) {
               var7.b = var7.c();
            }
         } else {
            var13.b(null);
         }
      } else {
         this.h.a(var1 == null ? var2 : var1.e, var3, var4, this.j.h);
         if (Container.c(var3) == 2) {
            for (int var11 = 0; var11 < 9; var11++) {
               this.j.c.a(this.h.a(45 + var11).d(), 36 + var11);
            }
         } else if (var1 != null) {
            ItemStack var12 = this.h.a(var1.e).d();
            this.j.c.a(var12, var1.e - this.h.c.size() + 9 + 36);
         }
      }
   }

   @Override
   protected void a() {
      int var1 = this.i;
      super.a();
      if (this.A != null && this.i != var1) {
         this.A.a = this.i + 82;
      }
   }

   @Override
   public void b() {
      if (this.j.c.h()) {
         super.b();
         this.n.clear();
         Keyboard.enableRepeatEvents(true);
         this.A = new GuiTextField(0, this.q, this.i + 82, this.r + 6, 89, this.q.a);
         this.A.f(15);
         this.A.a(false);
         this.A.e(false);
         this.A.g(16777215);
         int var1 = w;
         w = -1;
         this.b(CreativeTabs.a[var1]);
         this.E = new CreativeCrafting(this.j);
         this.j.h.bj.a(this.E);
      } else {
         this.j.a(new GuiInventory(this.j.h));
      }
   }

   @Override
   public void m() {
      super.m();
      if (this.j.h != null && this.j.h.bi != null) {
         this.j.h.bj.b(this.E);
      }

      Keyboard.enableRepeatEvents(false);
   }

   @Override
   protected void a(char var1, int var2) {
      if (w != CreativeTabs.g.a()) {
         if (GameSettings.a(this.j.t.ak)) {
            this.b(CreativeTabs.g);
         } else {
            super.a(var1, var2);
         }
      } else {
         if (this.D) {
            this.D = false;
            this.A.a("");
         }

         if (!this.b(var2)) {
            if (this.A.a(var1, var2)) {
               this.h();
            } else {
               super.a(var1, var2);
            }
         }
      }
   }

   private void h() {
      GuiContainerCreative.ContainerCreative var1 = (GuiContainerCreative.ContainerCreative)this.h;
      var1.a.clear();

      for (Item var3 : Item.e) {
         if (var3 != null && var3.c() != null) {
            var3.a(var3, null, var1.a);
         }
      }

      for (Enchantment var5 : Enchantment.b) {
         if (var5 != null && var5.C != null) {
            Items.cd.a(var5, var1.a);
         }
      }

      Iterator var9 = var1.a.iterator();
      String var11 = this.A.b().toLowerCase();

      while (var9.hasNext()) {
         ItemStack var12 = (ItemStack)var9.next();
         boolean var13 = false;

         for (String var7 : var12.a(this.j.h, this.j.t.y)) {
            if (EnumChatFormatting.a(var7).toLowerCase().contains(var11)) {
               var13 = true;
               break;
            }
         }

         if (!var13) {
            var9.remove();
         }
      }

      this.x = 0.0F;
      var1.a(0.0F);
   }

   @Override
   protected void b(int var1, int var2) {
      CreativeTabs var3 = CreativeTabs.a[w];
      if (var3.h()) {
         GlStateManager.k();
         this.q.a(I18n.a(var3.c()), 8, 6, 4210752);
      }
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      if (var3 == 0) {
         int var4 = var1 - this.i;
         int var5 = var2 - this.r;

         for (CreativeTabs var9 : CreativeTabs.a) {
            if (this.a(var9, var4, var5)) {
               return;
            }
         }
      }

      super.a(var1, var2, var3);
   }

   @Override
   protected void b(int var1, int var2, int var3) {
      if (var3 == 0) {
         int var4 = var1 - this.i;
         int var5 = var2 - this.r;

         for (CreativeTabs var9 : CreativeTabs.a) {
            if (this.a(var9, var4, var5)) {
               this.b(var9);
               return;
            }
         }
      }

      super.b(var1, var2, var3);
   }

   private boolean i() {
      return w != CreativeTabs.m.a() && CreativeTabs.a[w].j() && ((GuiContainerCreative.ContainerCreative)this.h).e();
   }

   private void b(CreativeTabs var1) {
      int var2 = w;
      w = var1.a();
      GuiContainerCreative.ContainerCreative var3 = (GuiContainerCreative.ContainerCreative)this.h;
      this.s.clear();
      var3.a.clear();
      var1.a(var3.a);
      if (var1 == CreativeTabs.m) {
         Container var4 = this.j.h.bj;
         if (this.B == null) {
            this.B = var3.c;
         }

         var3.c = Lists.newArrayList();

         for (int var5 = 0; var5 < var4.c.size(); var5++) {
            GuiContainerCreative.CreativeSlot var6 = new GuiContainerCreative.CreativeSlot(var4.c.get(var5), var5);
            var3.c.add(var6);
            if (var5 >= 5 && var5 < 9) {
               int var10 = var5 - 5;
               int var11 = var10 / 2;
               int var12 = var10 % 2;
               var6.f = 9 + var11 * 54;
               var6.g = 6 + var12 * 27;
            } else if (var5 >= 0 && var5 < 5) {
               var6.g = -2000;
               var6.f = -2000;
            } else if (var5 < var4.c.size()) {
               int var7 = var5 - 9;
               int var8 = var7 % 9;
               int var9 = var7 / 9;
               var6.f = 9 + var8 * 18;
               if (var5 >= 36) {
                  var6.g = 112;
               } else {
                  var6.g = 54 + var9 * 18;
               }
            }
         }

         this.C = new Slot(v, 0, 173, 112);
         var3.c.add(this.C);
      } else if (var2 == CreativeTabs.m.a()) {
         var3.c = this.B;
         this.B = null;
      }

      if (this.A != null) {
         if (var1 == CreativeTabs.g) {
            this.A.e(true);
            this.A.d(false);
            this.A.b(true);
            this.A.a("");
            this.h();
         } else {
            this.A.e(false);
            this.A.d(true);
            this.A.b(false);
         }
      }

      this.x = 0.0F;
      var3.a(0.0F);
   }

   @Override
   public void k() {
      super.k();
      int var1 = Mouse.getEventDWheel();
      if (var1 != 0 && this.i()) {
         int var2 = ((GuiContainerCreative.ContainerCreative)this.h).a.size() / 9 - 5;
         if (var1 > 0) {
            var1 = 1;
         }

         if (var1 < 0) {
            var1 = -1;
         }

         this.x = (float)(this.x - (double)var1 / var2);
         this.x = MathHelper.a(this.x, 0.0F, 1.0F);
         ((GuiContainerCreative.ContainerCreative)this.h).a(this.x);
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      boolean var4 = Mouse.isButtonDown(0);
      int var5 = this.i;
      int var6 = this.r;
      int var7 = var5 + 175;
      int var8 = var6 + 18;
      int var9 = var7 + 14;
      int var10 = var8 + 112;
      if (!this.z && var4 && var1 >= var7 && var2 >= var8 && var1 < var9 && var2 < var10) {
         this.y = this.i();
      }

      if (!var4) {
         this.y = false;
      }

      this.z = var4;
      if (this.y) {
         this.x = (var2 - var8 - 7.5F) / (var10 - var8 - 15.0F);
         this.x = MathHelper.a(this.x, 0.0F, 1.0F);
         ((GuiContainerCreative.ContainerCreative)this.h).a(this.x);
      }

      super.a(var1, var2, var3);

      for (CreativeTabs var14 : CreativeTabs.a) {
         if (this.b(var14, var1, var2)) {
            break;
         }
      }

      if (this.C != null && w == CreativeTabs.m.a() && this.c(this.C.f, this.C.g, 16, 16, var1, var2)) {
         this.a(I18n.a("inventory.binSlot"), var1, var2);
      }

      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.f();
   }

   @Override
   protected void a(ItemStack var1, int var2, int var3) {
      if (w == CreativeTabs.g.a()) {
         List var4 = var1.a(this.j.h, this.j.t.y);
         CreativeTabs var5 = var1.b().c();
         if (var5 == null && var1.b() == Items.cd) {
            Map var6 = EnchantmentHelper.a(var1);
            if (var6.size() == 1) {
               Enchantment var7 = Enchantment.c((Integer)var6.keySet().iterator().next());

               for (CreativeTabs var11 : CreativeTabs.a) {
                  if (var11.a(var7.C)) {
                     var5 = var11;
                     break;
                  }
               }
            }
         }

         if (var5 != null) {
            var4.add(1, "" + EnumChatFormatting.r + EnumChatFormatting.j + I18n.a(var5.c()));
         }

         for (int var12 = 0; var12 < var4.size(); var12++) {
            if (var12 == 0) {
               var4.set(var12, var1.u().e + (String)var4.get(var12));
            } else {
               var4.set(var12, EnumChatFormatting.h + (String)var4.get(var12));
            }
         }

         this.a(var4, var2, var3);
      } else {
         super.a(var1, var2, var3);
      }
   }

   @Override
   protected void a(float var1, int var2, int var3) {
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      RenderHelper.c();
      CreativeTabs var4 = CreativeTabs.a[w];

      for (CreativeTabs var8 : CreativeTabs.a) {
         this.j.P().a(u);
         if (var8.a() != w) {
            this.a(var8);
         }
      }

      this.j.P().a(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + var4.g()));
      this.b(this.i, this.r, 0, 0, this.f, this.g);
      this.A.g();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      int var9 = this.i + 175;
      int var10 = this.r + 18;
      int var11 = var10 + 112;
      this.j.P().a(u);
      if (var4.j()) {
         this.b(var9, var10 + (int)((var11 - var10 - 17) * this.x), 232 + (this.i() ? 0 : 12), 0, 12, 15);
      }

      this.a(var4);
      if (var4 == CreativeTabs.m) {
         GuiInventory.a(this.i + 43, this.r + 45, 20, this.i + 43 - var2, this.r + 45 - 30 - var3, this.j.h);
      }
   }

   protected boolean a(CreativeTabs var1, int var2, int var3) {
      int var4 = var1.l();
      int var5 = 28 * var4;
      int var6 = 0;
      if (var4 == 5) {
         var5 = this.f - 28 + 2;
      } else if (var4 > 0) {
         var5 += var4;
      }

      if (var1.m()) {
         var6 -= 32;
      } else {
         var6 += this.g;
      }

      return var2 >= var5 && var2 <= var5 + 28 && var3 >= var6 && var3 <= var6 + 32;
   }

   protected boolean b(CreativeTabs var1, int var2, int var3) {
      int var4 = var1.l();
      int var5 = 28 * var4;
      int var6 = 0;
      if (var4 == 5) {
         var5 = this.f - 28 + 2;
      } else if (var4 > 0) {
         var5 += var4;
      }

      if (var1.m()) {
         var6 -= 32;
      } else {
         var6 += this.g;
      }

      if (this.c(var5 + 3, var6 + 3, 23, 27, var2, var3)) {
         this.a(I18n.a(var1.c()), var2, var3);
         return true;
      } else {
         return false;
      }
   }

   protected void a(CreativeTabs var1) {
      boolean var2 = var1.a() == w;
      boolean var3 = var1.m();
      int var4 = var1.l();
      int var5 = var4 * 28;
      byte var6 = 0;
      int var7 = this.i + 28 * var4;
      int var8 = this.r;
      byte var9 = 32;
      if (var2) {
         var6 += 32;
      }

      if (var4 == 5) {
         var7 = this.i + this.f - 28;
      } else if (var4 > 0) {
         var7 += var4;
      }

      if (var3) {
         var8 -= 28;
      } else {
         var6 += 64;
         var8 += this.g - 4;
      }

      GlStateManager.f();
      this.b(var7, var8, var5, var6, 28, var9);
      this.e = 100.0F;
      this.k.a = 100.0F;
      var7 += 6;
      var8 += 8 + (var3 ? 1 : -1);
      GlStateManager.e();
      GlStateManager.B();
      ItemStack var10 = var1.d();
      this.k.b(var10, var7, var8);
      this.k.a(this.q, var10, var7, var8);
      GlStateManager.f();
      this.k.a = 0.0F;
      this.e = 0.0F;
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.k == 0) {
         this.j.a(new GuiAchievements(this, this.j.h.x()));
      }

      if (var1.k == 1) {
         this.j.a(new GuiStats(this, this.j.h.x()));
      }
   }

   public int f() {
      return w;
   }

   static class ContainerCreative extends Container {
      public List<ItemStack> a = Lists.newArrayList();

      public ContainerCreative(EntityPlayer var1) {
         InventoryPlayer var2 = var1.bi;

         for (int var3 = 0; var3 < 5; var3++) {
            for (int var4 = 0; var4 < 9; var4++) {
               this.a(new Slot(GuiContainerCreative.v, var3 * 9 + var4, 9 + var4 * 18, 18 + var3 * 18));
            }
         }

         for (int var5 = 0; var5 < 9; var5++) {
            this.a(new Slot(var2, var5, 9 + var5 * 18, 112));
         }

         this.a(0.0F);
      }

      @Override
      public boolean a(EntityPlayer var1) {
         return true;
      }

      public void a(float var1) {
         int var2 = (this.a.size() + 9 - 1) / 9 - 5;
         int var3 = (int)(var1 * var2 + 0.5);
         if (var3 < 0) {
            var3 = 0;
         }

         for (int var4 = 0; var4 < 5; var4++) {
            for (int var5 = 0; var5 < 9; var5++) {
               int var6 = var5 + (var4 + var3) * 9;
               if (var6 >= 0 && var6 < this.a.size()) {
                  GuiContainerCreative.v.a(var5 + var4 * 9, this.a.get(var6));
               } else {
                  GuiContainerCreative.v.a(var5 + var4 * 9, null);
               }
            }
         }
      }

      public boolean e() {
         return this.a.size() > 45;
      }

      @Override
      protected void a(int var1, int var2, boolean var3, EntityPlayer var4) {
      }

      @Override
      public ItemStack b(EntityPlayer var1, int var2) {
         if (var2 >= this.c.size() - 9 && var2 < this.c.size()) {
            Slot var3 = this.c.get(var2);
            if (var3 != null && var3.e()) {
               var3.d(null);
            }
         }

         return null;
      }

      @Override
      public boolean a(ItemStack var1, Slot var2) {
         return var2.g > 90;
      }

      @Override
      public boolean b(Slot var1) {
         return var1.d instanceof InventoryPlayer || var1.g > 90 && var1.f <= 162;
      }
   }

   class CreativeSlot extends Slot {
      private final Slot b;

      public CreativeSlot(Slot var2, int var3) {
         super(var2.d, var3, 0, 0);
         this.b = var2;
      }

      @Override
      public void a(EntityPlayer var1, ItemStack var2) {
         this.b.a(var1, var2);
      }

      @Override
      public boolean a(ItemStack var1) {
         return this.b.a(var1);
      }

      @Override
      public ItemStack d() {
         return this.b.d();
      }

      @Override
      public boolean e() {
         return this.b.e();
      }

      @Override
      public void d(ItemStack var1) {
         this.b.d(var1);
      }

      @Override
      public void f() {
         this.b.f();
      }

      @Override
      public int a() {
         return this.b.a();
      }

      @Override
      public int b(ItemStack var1) {
         return this.b.b(var1);
      }

      @Override
      public String c() {
         return this.b.c();
      }

      @Override
      public ItemStack a(int var1) {
         return this.b.a(var1);
      }

      @Override
      public boolean a(IInventory var1, int var2) {
         return this.b.a(var1, var2);
      }
   }
}
