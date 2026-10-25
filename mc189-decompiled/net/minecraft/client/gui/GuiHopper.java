package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiHopper extends GuiContainer {
   private static final ResourceLocation u = new ResourceLocation("textures/gui/container/hopper.png");
   private IInventory v;
   private IInventory w;

   public GuiHopper(InventoryPlayer var1, IInventory var2) {
      super(new ContainerHopper(var1, var2, Minecraft.A().h));
      this.v = var1;
      this.w = var2;
      this.p = false;
      this.g = 133;
   }

   @Override
   protected void b(int var1, int var2) {
      this.q.a(this.w.f_().c(), 8, 6, 4210752);
      this.q.a(this.v.f_().c(), 8, this.g - 96 + 2, 4210752);
   }

   @Override
   protected void a(float var1, int var2, int var3) {
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.j.P().a(u);
      int var4 = (this.l - this.f) / 2;
      int var5 = (this.m - this.g) / 2;
      this.b(var4, var5, 0, 0, this.f, this.g);
   }
}
