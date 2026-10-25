package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiChest extends GuiContainer {
   private static final ResourceLocation u = new ResourceLocation("textures/gui/container/generic_54.png");
   private IInventory v;
   private IInventory w;
   private int x;

   public GuiChest(IInventory var1, IInventory var2) {
      super(new ContainerChest(var1, var2, Minecraft.A().h));
      this.v = var1;
      this.w = var2;
      this.p = false;
      short var3 = 222;
      int var4 = var3 - 108;
      this.x = var2.o_() / 9;
      this.g = var4 + this.x * 18;
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
      this.b(var4, var5, 0, 0, this.f, this.x * 18 + 17);
      this.b(var4, var5 + this.x * 18 + 17, 0, 126, this.f, 96);
   }
}
