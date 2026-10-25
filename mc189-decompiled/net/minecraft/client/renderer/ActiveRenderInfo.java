package net.minecraft.client.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class ActiveRenderInfo {
   private static final IntBuffer a = GLAllocation.f(16);
   private static final FloatBuffer b = GLAllocation.h(16);
   private static final FloatBuffer c = GLAllocation.h(16);
   private static final FloatBuffer d = GLAllocation.h(3);
   private static Vec3 e = new Vec3(0.0, 0.0, 0.0);
   private static float f;
   private static float g;
   private static float h;
   private static float i;
   private static float j;

   public static void a(EntityPlayer var0, boolean var1) {
      GlStateManager.a(2982, b);
      GlStateManager.a(2983, c);
      GL11.glGetInteger(2978, a);
      float var2 = (a.get(0) + a.get(2)) / 2;
      float var3 = (a.get(1) + a.get(3)) / 2;
      GLU.gluUnProject(var2, var3, 0.0F, b, c, a, d);
      e = new Vec3(d.get(0), d.get(1), d.get(2));
      int var4 = var1 ? 1 : 0;
      float var5 = var0.z;
      float var6 = var0.y;
      f = MathHelper.b(var6 * (float) Math.PI / 180.0F) * (1 - var4 * 2);
      h = MathHelper.a(var6 * (float) Math.PI / 180.0F) * (1 - var4 * 2);
      i = -h * MathHelper.a(var5 * (float) Math.PI / 180.0F) * (1 - var4 * 2);
      j = f * MathHelper.a(var5 * (float) Math.PI / 180.0F) * (1 - var4 * 2);
      g = MathHelper.b(var5 * (float) Math.PI / 180.0F);
   }

   public static Vec3 a(Entity var0, double var1) {
      double var3 = var0.p + (var0.s - var0.p) * var1;
      double var5 = var0.q + (var0.t - var0.q) * var1;
      double var7 = var0.r + (var0.u - var0.r) * var1;
      double var9 = var3 + e.a;
      double var11 = var5 + e.b;
      double var13 = var7 + e.c;
      return new Vec3(var9, var11, var13);
   }

   public static Block a(World var0, Entity var1, float var2) {
      Vec3 var3 = a(var1, var2);
      BlockPos var4 = new BlockPos(var3);
      IBlockState var5 = var0.p(var4);
      Block var6 = var5.c();
      if (var6.t().d()) {
         float var7 = 0.0F;
         if (var5.c() instanceof BlockLiquid) {
            var7 = BlockLiquid.b(var5.b(BlockLiquid.b)) - 0.11111111F;
         }

         float var8 = var4.o() + 1 - var7;
         if (var3.b >= var8) {
            var6 = var0.p(var4.a()).c();
         }
      }

      return var6;
   }

   public static Vec3 a() {
      return e;
   }

   public static float b() {
      return f;
   }

   public static float c() {
      return g;
   }

   public static float d() {
      return h;
   }

   public static float e() {
      return i;
   }

   public static float f() {
      return j;
   }
}
