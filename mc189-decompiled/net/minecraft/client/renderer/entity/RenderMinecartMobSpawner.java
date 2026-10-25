package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.init.Blocks;

public class RenderMinecartMobSpawner extends RenderMinecart<EntityMinecartMobSpawner> {
   public RenderMinecartMobSpawner(RenderManager var1) {
      super(var1);
   }

   protected void a(EntityMinecartMobSpawner var1, float var2, IBlockState var3) {
      super.a(var1, var2, var3);
      if (var3.c() == Blocks.ac) {
         TileEntityMobSpawnerRenderer.a(var1.j(), var1.s, var1.t, var1.u, var2);
      }
   }
}
