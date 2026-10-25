package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class ListedRenderChunk extends RenderChunk {
   private final int d = GLAllocation.a(EnumWorldBlockLayer.values().length);

   public ListedRenderChunk(World var1, RenderGlobal var2, BlockPos var3, int var4) {
      super(var1, var2, var3, var4);
   }

   public int a(EnumWorldBlockLayer var1, CompiledChunk var2) {
      return !var2.b(var1) ? this.d + var1.ordinal() : -1;
   }

   @Override
   public void a() {
      super.a();
      GLAllocation.a(this.d, EnumWorldBlockLayer.values().length);
   }
}
