package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;

public abstract class ChunkRenderContainer {
   private double c;
   private double d;
   private double e;
   protected List<RenderChunk> a = Lists.newArrayListWithCapacity(17424);
   protected boolean b;

   public void a(double var1, double var3, double var5) {
      this.b = true;
      this.a.clear();
      this.c = var1;
      this.d = var3;
      this.e = var5;
   }

   public void a(RenderChunk var1) {
      BlockPos var2 = var1.j();
      GlStateManager.b((float)(var2.n() - this.c), (float)(var2.o() - this.d), (float)(var2.p() - this.e));
   }

   public void a(RenderChunk var1, EnumWorldBlockLayer var2) {
      this.a.add(var1);
   }

   public abstract void a(EnumWorldBlockLayer var1);
}
