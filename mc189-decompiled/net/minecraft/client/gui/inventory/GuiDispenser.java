package net.minecraft.client.gui.inventory;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiDispenser extends GuiContainer {
   private static final ResourceLocation v = new ResourceLocation("textures/gui/container/dispenser.png");
   private final InventoryPlayer w;
   public IInventory u;

   public GuiDispenser(InventoryPlayer var1, IInventory var2) {
      super(new ContainerDispenser(var1, var2));
      this.w = var1;
      this.u = var2;
   }

   @Override
   protected void b(int var1, int var2) {
      String var3 = this.u.f_().c();
      this.q.a(var3, this.f / 2 - this.q.a(var3) / 2, 6, 4210752);
      this.q.a(this.w.f_().c(), 8, this.g - 96 + 2, 4210752);
   }

   @Override
   protected void a(float var1, int var2, int var3) {
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.j.P().a(v);
      int var4 = (this.l - this.f) / 2;
      int var5 = (this.m - this.g) / 2;
      this.b(var4, var5, 0, 0, this.f, this.g);
   }
}
