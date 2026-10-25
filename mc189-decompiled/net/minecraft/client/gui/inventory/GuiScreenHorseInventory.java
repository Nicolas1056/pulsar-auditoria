package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiScreenHorseInventory extends GuiContainer {
   private static final ResourceLocation u = new ResourceLocation("textures/gui/container/horse.png");
   private IInventory v;
   private IInventory w;
   private EntityHorse x;
   private float y;
   private float z;

   public GuiScreenHorseInventory(IInventory var1, IInventory var2, EntityHorse var3) {
      super(new ContainerHorseInventory(var1, var2, var3, Minecraft.A().h));
      this.v = var1;
      this.w = var2;
      this.x = var3;
      this.p = false;
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
      if (this.x.cw()) {
         this.b(var4 + 79, var5 + 17, 0, this.g, 90, 54);
      }

      if (this.x.cO()) {
         this.b(var4 + 7, var5 + 35, 0, this.g + 54, 18, 18);
      }

      GuiInventory.a(var4 + 51, var5 + 60, 17, var4 + 51 - this.y, var5 + 75 - 50 - this.z, this.x);
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.y = var1;
      this.z = var2;
      super.a(var1, var2, var3);
   }
}
