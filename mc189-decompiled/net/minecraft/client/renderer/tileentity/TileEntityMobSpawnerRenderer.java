package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;

public class TileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer<TileEntityMobSpawner> {
   public void a(TileEntityMobSpawner var1, double var2, double var4, double var6, float var8, int var9) {
      GlStateManager.E();
      GlStateManager.b((float)var2 + 0.5F, (float)var4, (float)var6 + 0.5F);
      a(var1.b(), var2, var4, var6, var8);
      GlStateManager.F();
   }

   public static void a(MobSpawnerBaseLogic var0, double var1, double var3, double var5, float var7) {
      Entity var8 = var0.a(var0.a());
      if (var8 != null) {
         float var9 = 0.4375F;
         GlStateManager.b(0.0F, 0.4F, 0.0F);
         GlStateManager.b((float)(var0.e() + (var0.d() - var0.e()) * var7) * 10.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.b(-30.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.b(0.0F, -0.4F, 0.0F);
         GlStateManager.a(var9, var9, var9);
         var8.b(var1, var3, var5, 0.0F, 0.0F);
         Minecraft.A().af().a(var8, 0.0, 0.0, 0.0, 0.0F, var7);
      }
   }
}
