package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class GuiRepair extends GuiContainer implements ICrafting {
   private static final ResourceLocation u = new ResourceLocation("textures/gui/container/anvil.png");
   private ContainerRepair v;
   private GuiTextField w;
   private InventoryPlayer x;

   public GuiRepair(InventoryPlayer var1, World var2) {
      super(new ContainerRepair(var1, var2, Minecraft.A().h));
      this.x = var1;
      this.v = (ContainerRepair)this.h;
   }

   @Override
   public void b() {
      super.b();
      Keyboard.enableRepeatEvents(true);
      int var1 = (this.l - this.f) / 2;
      int var2 = (this.m - this.g) / 2;
      this.w = new GuiTextField(0, this.q, var1 + 62, var2 + 24, 103, 12);
      this.w.g(-1);
      this.w.h(-1);
      this.w.a(false);
      this.w.f(30);
      this.h.b(this);
      this.h.a(this);
   }

   @Override
   public void m() {
      super.m();
      Keyboard.enableRepeatEvents(false);
      this.h.b(this);
   }

   @Override
   protected void b(int var1, int var2) {
      GlStateManager.f();
      GlStateManager.k();
      this.q.a(I18n.a("container.repair"), 60, 6, 4210752);
      if (this.v.a > 0) {
         int var3 = 8453920;
         boolean var4 = true;
         String var5 = I18n.a("container.repair.cost", this.v.a);
         if (this.v.a >= 40 && !this.j.h.bA.d) {
            var5 = I18n.a("container.repair.expensive");
            var3 = 16736352;
         } else if (!this.v.a(2).e()) {
            var4 = false;
         } else if (!this.v.a(2).a(this.x.d)) {
            var3 = 16736352;
         }

         if (var4) {
            int var6 = 0xFF000000 | (var3 & 16579836) >> 2 | var3 & 0xFF000000;
            int var7 = this.f - 8 - this.q.a(var5);
            byte var8 = 67;
            if (this.q.a()) {
               a(var7 - 3, var8 - 2, this.f - 7, var8 + 10, -16777216);
               a(var7 - 2, var8 - 1, this.f - 8, var8 + 9, -12895429);
            } else {
               this.q.a(var5, var7, var8 + 1, var6);
               this.q.a(var5, var7 + 1, var8, var6);
               this.q.a(var5, var7 + 1, var8 + 1, var6);
            }

            this.q.a(var5, var7, var8, var3);
         }
      }

      GlStateManager.e();
   }

   @Override
   protected void a(char var1, int var2) {
      if (this.w.a(var1, var2)) {
         this.a();
      } else {
         super.a(var1, var2);
      }
   }

   private void a() {
      String var1 = this.w.b();
      Slot var2 = this.v.a(0);
      if (var2 != null && var2.e() && !var2.d().s() && var1.equals(var2.d().q())) {
         var1 = "";
      }

      this.v.a(var1);
      this.j.h.a.a(new C17PacketCustomPayload("MC|ItemName", new PacketBuffer(Unpooled.buffer()).a(var1)));
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      super.a(var1, var2, var3);
      this.w.a(var1, var2, var3);
   }

   @Override
   public void a(int var1, int var2, float var3) {
      super.a(var1, var2, var3);
      GlStateManager.f();
      GlStateManager.k();
      this.w.g();
   }

   @Override
   protected void a(float var1, int var2, int var3) {
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.j.P().a(u);
      int var4 = (this.l - this.f) / 2;
      int var5 = (this.m - this.g) / 2;
      this.b(var4, var5, 0, 0, this.f, this.g);
      this.b(var4 + 59, var5 + 20, 0, this.g + (this.v.a(0).e() ? 0 : 16), 110, 16);
      if ((this.v.a(0).e() || this.v.a(1).e()) && !this.v.a(2).e()) {
         this.b(var4 + 99, var5 + 45, this.f, 0, 28, 21);
      }
   }

   @Override
   public void a(Container var1, List<ItemStack> var2) {
      this.a(var1, 0, var1.a(0).d());
   }

   @Override
   public void a(Container var1, int var2, ItemStack var3) {
      if (var2 == 0) {
         this.w.a(var3 == null ? "" : var3.q());
         this.w.c(var3 != null);
         if (var3 != null) {
            this.a();
         }
      }
   }

   @Override
   public void a(Container var1, int var2, int var3) {
   }

   @Override
   public void a(Container var1, IInventory var2) {
   }
}
