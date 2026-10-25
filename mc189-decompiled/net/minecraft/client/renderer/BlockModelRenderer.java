package net.minecraft.client.renderer;

import java.util.BitSet;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3i;
import net.minecraft.world.IBlockAccess;

public class BlockModelRenderer {
   public boolean a(IBlockAccess var1, IBakedModel var2, IBlockState var3, BlockPos var4, WorldRenderer var5) {
      Block var6 = var3.c();
      var6.a(var1, var4);
      return this.a(var1, var2, var3, var4, var5, true);
   }

   public boolean a(IBlockAccess var1, IBakedModel var2, IBlockState var3, BlockPos var4, WorldRenderer var5, boolean var6) {
      boolean var7 = Minecraft.x() && var3.c().r() == 0 && var2.b();

      try {
         Block var8 = var3.c();
         return var7 ? this.a(var1, var2, var8, var4, var5, var6) : this.b(var1, var2, var8, var4, var5, var6);
      } catch (Throwable var11) {
         CrashReport var9 = CrashReport.a(var11, "Tesselating block model");
         CrashReportCategory var10 = var9.a("Block model being tesselated");
         CrashReportCategory.a(var10, var4, var3);
         var10.a("Using AO", var7);
         throw new ReportedException(var9);
      }
   }

   public boolean a(IBlockAccess var1, IBakedModel var2, Block var3, BlockPos var4, WorldRenderer var5, boolean var6) {
      boolean var7 = false;
      float[] var8 = new float[EnumFacing.values().length * 2];
      BitSet var9 = new BitSet(3);
      BlockModelRenderer.AmbientOcclusionFace var10 = new BlockModelRenderer.AmbientOcclusionFace();

      for (EnumFacing var14 : EnumFacing.values()) {
         List var15 = var2.a(var14);
         if (!var15.isEmpty()) {
            BlockPos var16 = var4.a(var14);
            if (!var6 || var3.a(var1, var16, var14)) {
               this.a(var1, var3, var4, var5, var15, var8, var9, var10);
               var7 = true;
            }
         }
      }

      List var17 = var2.a();
      if (var17.size() > 0) {
         this.a(var1, var3, var4, var5, var17, var8, var9, var10);
         var7 = true;
      }

      return var7;
   }

   public boolean b(IBlockAccess var1, IBakedModel var2, Block var3, BlockPos var4, WorldRenderer var5, boolean var6) {
      boolean var7 = false;
      BitSet var8 = new BitSet(3);

      for (EnumFacing var12 : EnumFacing.values()) {
         List var13 = var2.a(var12);
         if (!var13.isEmpty()) {
            BlockPos var14 = var4.a(var12);
            if (!var6 || var3.a(var1, var14, var12)) {
               int var15 = var3.c(var1, var14);
               this.a(var1, var3, var4, var12, var15, false, var5, var13, var8);
               var7 = true;
            }
         }
      }

      List var16 = var2.a();
      if (var16.size() > 0) {
         this.a(var1, var3, var4, null, -1, true, var5, var16, var8);
         var7 = true;
      }

      return var7;
   }

   private void a(
      IBlockAccess var1,
      Block var2,
      BlockPos var3,
      WorldRenderer var4,
      List<BakedQuad> var5,
      float[] var6,
      BitSet var7,
      BlockModelRenderer.AmbientOcclusionFace var8
   ) {
      double var9 = var3.n();
      double var11 = var3.o();
      double var13 = var3.p();
      Block.EnumOffsetType var15 = var2.R();
      if (var15 != Block.EnumOffsetType.a) {
         long var16 = MathHelper.a(var3);
         var9 += ((float)(var16 >> 16 & 15L) / 15.0F - 0.5) * 0.5;
         var13 += ((float)(var16 >> 24 & 15L) / 15.0F - 0.5) * 0.5;
         if (var15 == Block.EnumOffsetType.c) {
            var11 += ((float)(var16 >> 20 & 15L) / 15.0F - 1.0) * 0.2;
         }
      }

      for (BakedQuad var17 : var5) {
         this.a(var2, var17.a(), var17.d(), var6, var7);
         var8.a(var1, var2, var3, var17.d(), var6, var7);
         var4.a(var17.a());
         var4.a(var8.c[0], var8.c[1], var8.c[2], var8.c[3]);
         if (var17.b()) {
            int var18 = var2.a(var1, var3, var17.c());
            if (EntityRenderer.a) {
               var18 = TextureUtil.c(var18);
            }

            float var19 = (var18 >> 16 & 0xFF) / 255.0F;
            float var20 = (var18 >> 8 & 0xFF) / 255.0F;
            float var21 = (var18 & 0xFF) / 255.0F;
            var4.a(var8.b[0] * var19, var8.b[0] * var20, var8.b[0] * var21, 4);
            var4.a(var8.b[1] * var19, var8.b[1] * var20, var8.b[1] * var21, 3);
            var4.a(var8.b[2] * var19, var8.b[2] * var20, var8.b[2] * var21, 2);
            var4.a(var8.b[3] * var19, var8.b[3] * var20, var8.b[3] * var21, 1);
         } else {
            var4.a(var8.b[0], var8.b[0], var8.b[0], 4);
            var4.a(var8.b[1], var8.b[1], var8.b[1], 3);
            var4.a(var8.b[2], var8.b[2], var8.b[2], 2);
            var4.a(var8.b[3], var8.b[3], var8.b[3], 1);
         }

         var4.a(var9, var11, var13);
      }
   }

   private void a(Block var1, int[] var2, EnumFacing var3, float[] var4, BitSet var5) {
      float var6 = 32.0F;
      float var7 = 32.0F;
      float var8 = 32.0F;
      float var9 = -32.0F;
      float var10 = -32.0F;
      float var11 = -32.0F;

      for (int var12 = 0; var12 < 4; var12++) {
         float var13 = Float.intBitsToFloat(var2[var12 * 7]);
         float var14 = Float.intBitsToFloat(var2[var12 * 7 + 1]);
         float var15 = Float.intBitsToFloat(var2[var12 * 7 + 2]);
         var6 = Math.min(var6, var13);
         var7 = Math.min(var7, var14);
         var8 = Math.min(var8, var15);
         var9 = Math.max(var9, var13);
         var10 = Math.max(var10, var14);
         var11 = Math.max(var11, var15);
      }

      if (var4 != null) {
         var4[EnumFacing.e.a()] = var6;
         var4[EnumFacing.f.a()] = var9;
         var4[EnumFacing.a.a()] = var7;
         var4[EnumFacing.b.a()] = var10;
         var4[EnumFacing.c.a()] = var8;
         var4[EnumFacing.d.a()] = var11;
         var4[EnumFacing.e.a() + EnumFacing.values().length] = 1.0F - var6;
         var4[EnumFacing.f.a() + EnumFacing.values().length] = 1.0F - var9;
         var4[EnumFacing.a.a() + EnumFacing.values().length] = 1.0F - var7;
         var4[EnumFacing.b.a() + EnumFacing.values().length] = 1.0F - var10;
         var4[EnumFacing.c.a() + EnumFacing.values().length] = 1.0F - var8;
         var4[EnumFacing.d.a() + EnumFacing.values().length] = 1.0F - var11;
      }

      float var16 = 1.0E-4F;
      float var17 = 0.9999F;
      switch (var3) {
         case a:
            var5.set(1, var6 >= 1.0E-4F || var8 >= 1.0E-4F || var9 <= 0.9999F || var11 <= 0.9999F);
            var5.set(0, (var7 < 1.0E-4F || var1.d()) && var7 == var10);
            break;
         case b:
            var5.set(1, var6 >= 1.0E-4F || var8 >= 1.0E-4F || var9 <= 0.9999F || var11 <= 0.9999F);
            var5.set(0, (var10 > 0.9999F || var1.d()) && var7 == var10);
            break;
         case c:
            var5.set(1, var6 >= 1.0E-4F || var7 >= 1.0E-4F || var9 <= 0.9999F || var10 <= 0.9999F);
            var5.set(0, (var8 < 1.0E-4F || var1.d()) && var8 == var11);
            break;
         case d:
            var5.set(1, var6 >= 1.0E-4F || var7 >= 1.0E-4F || var9 <= 0.9999F || var10 <= 0.9999F);
            var5.set(0, (var11 > 0.9999F || var1.d()) && var8 == var11);
            break;
         case e:
            var5.set(1, var7 >= 1.0E-4F || var8 >= 1.0E-4F || var10 <= 0.9999F || var11 <= 0.9999F);
            var5.set(0, (var6 < 1.0E-4F || var1.d()) && var6 == var9);
            break;
         case f:
            var5.set(1, var7 >= 1.0E-4F || var8 >= 1.0E-4F || var10 <= 0.9999F || var11 <= 0.9999F);
            var5.set(0, (var9 > 0.9999F || var1.d()) && var6 == var9);
      }
   }

   private void a(IBlockAccess var1, Block var2, BlockPos var3, EnumFacing var4, int var5, boolean var6, WorldRenderer var7, List<BakedQuad> var8, BitSet var9) {
      double var10 = var3.n();
      double var12 = var3.o();
      double var14 = var3.p();
      Block.EnumOffsetType var16 = var2.R();
      if (var16 != Block.EnumOffsetType.a) {
         int var17 = var3.n();
         int var18 = var3.p();
         long var19 = var17 * 3129871 ^ var18 * 116129781L;
         var19 = var19 * var19 * 42317861L + var19 * 11L;
         var10 += ((float)(var19 >> 16 & 15L) / 15.0F - 0.5) * 0.5;
         var14 += ((float)(var19 >> 24 & 15L) / 15.0F - 0.5) * 0.5;
         if (var16 == Block.EnumOffsetType.c) {
            var12 += ((float)(var19 >> 20 & 15L) / 15.0F - 1.0) * 0.2;
         }
      }

      for (BakedQuad var24 : var8) {
         if (var6) {
            this.a(var2, var24.a(), var24.d(), null, var9);
            var5 = var9.get(0) ? var2.c(var1, var3.a(var24.d())) : var2.c(var1, var3);
         }

         var7.a(var24.a());
         var7.a(var5, var5, var5, var5);
         if (var24.b()) {
            int var26 = var2.a(var1, var3, var24.c());
            if (EntityRenderer.a) {
               var26 = TextureUtil.c(var26);
            }

            float var20 = (var26 >> 16 & 0xFF) / 255.0F;
            float var21 = (var26 >> 8 & 0xFF) / 255.0F;
            float var22 = (var26 & 0xFF) / 255.0F;
            var7.a(var20, var21, var22, 4);
            var7.a(var20, var21, var22, 3);
            var7.a(var20, var21, var22, 2);
            var7.a(var20, var21, var22, 1);
         }

         var7.a(var10, var12, var14);
      }
   }

   public void a(IBakedModel var1, float var2, float var3, float var4, float var5) {
      for (EnumFacing var9 : EnumFacing.values()) {
         this.a(var2, var3, var4, var5, var1.a(var9));
      }

      this.a(var2, var3, var4, var5, var1.a());
   }

   public void a(IBakedModel var1, IBlockState var2, float var3, boolean var4) {
      Block var5 = var2.c();
      var5.j();
      GlStateManager.b(90.0F, 0.0F, 1.0F, 0.0F);
      int var6 = var5.h(var5.b(var2));
      if (EntityRenderer.a) {
         var6 = TextureUtil.c(var6);
      }

      float var7 = (var6 >> 16 & 0xFF) / 255.0F;
      float var8 = (var6 >> 8 & 0xFF) / 255.0F;
      float var9 = (var6 & 0xFF) / 255.0F;
      if (!var4) {
         GlStateManager.c(var3, var3, var3, 1.0F);
      }

      this.a(var1, var3, var7, var8, var9);
   }

   private void a(float var1, float var2, float var3, float var4, List<BakedQuad> var5) {
      Tessellator var6 = Tessellator.a();
      WorldRenderer var7 = var6.c();

      for (BakedQuad var9 : var5) {
         var7.a(7, DefaultVertexFormats.b);
         var7.a(var9.a());
         if (var9.b()) {
            var7.d(var2 * var1, var3 * var1, var4 * var1);
         } else {
            var7.d(var1, var1, var1);
         }

         Vec3i var10 = var9.d().m();
         var7.b((float)var10.n(), (float)var10.o(), (float)var10.p());
         var6.b();
      }
   }

   class AmbientOcclusionFace {
      private final float[] b = new float[4];
      private final int[] c = new int[4];

      public AmbientOcclusionFace() {
      }

      public void a(IBlockAccess var1, Block var2, BlockPos var3, EnumFacing var4, float[] var5, BitSet var6) {
         BlockPos var7 = var6.get(0) ? var3.a(var4) : var3;
         BlockModelRenderer.EnumNeighborInfo var8 = BlockModelRenderer.EnumNeighborInfo.a(var4);
         BlockPos var9 = var7.a(var8.g[0]);
         BlockPos var10 = var7.a(var8.g[1]);
         BlockPos var11 = var7.a(var8.g[2]);
         BlockPos var12 = var7.a(var8.g[3]);
         int var13 = var2.c(var1, var9);
         int var14 = var2.c(var1, var10);
         int var15 = var2.c(var1, var11);
         int var16 = var2.c(var1, var12);
         float var17 = var1.p(var9).c().h();
         float var18 = var1.p(var10).c().h();
         float var19 = var1.p(var11).c().h();
         float var20 = var1.p(var12).c().h();
         boolean var21 = var1.p(var9.a(var4)).c().q();
         boolean var22 = var1.p(var10.a(var4)).c().q();
         boolean var23 = var1.p(var11.a(var4)).c().q();
         boolean var24 = var1.p(var12.a(var4)).c().q();
         float var25;
         int var29;
         if (!var23 && !var21) {
            var25 = var17;
            var29 = var13;
         } else {
            BlockPos var33 = var9.a(var8.g[2]);
            var25 = var1.p(var33).c().h();
            var29 = var2.c(var1, var33);
         }

         float var26;
         int var30;
         if (!var24 && !var21) {
            var26 = var17;
            var30 = var13;
         } else {
            BlockPos var60 = var9.a(var8.g[3]);
            var26 = var1.p(var60).c().h();
            var30 = var2.c(var1, var60);
         }

         float var27;
         int var31;
         if (!var23 && !var22) {
            var27 = var18;
            var31 = var14;
         } else {
            BlockPos var61 = var10.a(var8.g[2]);
            var27 = var1.p(var61).c().h();
            var31 = var2.c(var1, var61);
         }

         float var28;
         int var32;
         if (!var24 && !var22) {
            var28 = var18;
            var32 = var14;
         } else {
            BlockPos var62 = var10.a(var8.g[3]);
            var28 = var1.p(var62).c().h();
            var32 = var2.c(var1, var62);
         }

         int var63 = var2.c(var1, var3);
         if (var6.get(0) || !var1.p(var3.a(var4)).c().c()) {
            var63 = var2.c(var1, var3.a(var4));
         }

         float var34 = var6.get(0) ? var1.p(var7).c().h() : var1.p(var3).c().h();
         BlockModelRenderer.VertexTranslations var35 = BlockModelRenderer.VertexTranslations.a(var4);
         if (var6.get(1) && var8.i) {
            float var64 = (var20 + var17 + var26 + var34) * 0.25F;
            float var65 = (var19 + var17 + var25 + var34) * 0.25F;
            float var66 = (var19 + var18 + var27 + var34) * 0.25F;
            float var67 = (var20 + var18 + var28 + var34) * 0.25F;
            float var40 = var5[var8.j[0].m] * var5[var8.j[1].m];
            float var41 = var5[var8.j[2].m] * var5[var8.j[3].m];
            float var42 = var5[var8.j[4].m] * var5[var8.j[5].m];
            float var43 = var5[var8.j[6].m] * var5[var8.j[7].m];
            float var44 = var5[var8.k[0].m] * var5[var8.k[1].m];
            float var45 = var5[var8.k[2].m] * var5[var8.k[3].m];
            float var46 = var5[var8.k[4].m] * var5[var8.k[5].m];
            float var47 = var5[var8.k[6].m] * var5[var8.k[7].m];
            float var48 = var5[var8.l[0].m] * var5[var8.l[1].m];
            float var49 = var5[var8.l[2].m] * var5[var8.l[3].m];
            float var50 = var5[var8.l[4].m] * var5[var8.l[5].m];
            float var51 = var5[var8.l[6].m] * var5[var8.l[7].m];
            float var52 = var5[var8.m[0].m] * var5[var8.m[1].m];
            float var53 = var5[var8.m[2].m] * var5[var8.m[3].m];
            float var54 = var5[var8.m[4].m] * var5[var8.m[5].m];
            float var55 = var5[var8.m[6].m] * var5[var8.m[7].m];
            this.b[var35.g] = var64 * var40 + var65 * var41 + var66 * var42 + var67 * var43;
            this.b[var35.h] = var64 * var44 + var65 * var45 + var66 * var46 + var67 * var47;
            this.b[var35.i] = var64 * var48 + var65 * var49 + var66 * var50 + var67 * var51;
            this.b[var35.j] = var64 * var52 + var65 * var53 + var66 * var54 + var67 * var55;
            int var56 = this.a(var16, var13, var30, var63);
            int var57 = this.a(var15, var13, var29, var63);
            int var58 = this.a(var15, var14, var31, var63);
            int var59 = this.a(var16, var14, var32, var63);
            this.c[var35.g] = this.a(var56, var57, var58, var59, var40, var41, var42, var43);
            this.c[var35.h] = this.a(var56, var57, var58, var59, var44, var45, var46, var47);
            this.c[var35.i] = this.a(var56, var57, var58, var59, var48, var49, var50, var51);
            this.c[var35.j] = this.a(var56, var57, var58, var59, var52, var53, var54, var55);
         } else {
            float var36 = (var20 + var17 + var26 + var34) * 0.25F;
            float var37 = (var19 + var17 + var25 + var34) * 0.25F;
            float var38 = (var19 + var18 + var27 + var34) * 0.25F;
            float var39 = (var20 + var18 + var28 + var34) * 0.25F;
            this.c[var35.g] = this.a(var16, var13, var30, var63);
            this.c[var35.h] = this.a(var15, var13, var29, var63);
            this.c[var35.i] = this.a(var15, var14, var31, var63);
            this.c[var35.j] = this.a(var16, var14, var32, var63);
            this.b[var35.g] = var36;
            this.b[var35.h] = var37;
            this.b[var35.i] = var38;
            this.b[var35.j] = var39;
         }
      }

      private int a(int var1, int var2, int var3, int var4) {
         if (var1 == 0) {
            var1 = var4;
         }

         if (var2 == 0) {
            var2 = var4;
         }

         if (var3 == 0) {
            var3 = var4;
         }

         return var1 + var2 + var3 + var4 >> 2 & 16711935;
      }

      private int a(int var1, int var2, int var3, int var4, float var5, float var6, float var7, float var8) {
         int var9 = (int)((var1 >> 16 & 0xFF) * var5 + (var2 >> 16 & 0xFF) * var6 + (var3 >> 16 & 0xFF) * var7 + (var4 >> 16 & 0xFF) * var8) & 0xFF;
         int var10 = (int)((var1 & 0xFF) * var5 + (var2 & 0xFF) * var6 + (var3 & 0xFF) * var7 + (var4 & 0xFF) * var8) & 0xFF;
         return var9 << 16 | var10;
      }
   }

   public static enum EnumNeighborInfo {
      a(
         new EnumFacing[]{EnumFacing.e, EnumFacing.f, EnumFacing.c, EnumFacing.d},
         0.5F,
         false,
         new BlockModelRenderer.Orientation[0],
         new BlockModelRenderer.Orientation[0],
         new BlockModelRenderer.Orientation[0],
         new BlockModelRenderer.Orientation[0]
      ),
      b(
         new EnumFacing[]{EnumFacing.f, EnumFacing.e, EnumFacing.c, EnumFacing.d},
         1.0F,
         false,
         new BlockModelRenderer.Orientation[0],
         new BlockModelRenderer.Orientation[0],
         new BlockModelRenderer.Orientation[0],
         new BlockModelRenderer.Orientation[0]
      ),
      c(
         new EnumFacing[]{EnumFacing.b, EnumFacing.a, EnumFacing.f, EnumFacing.e},
         0.8F,
         true,
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.b,
            BlockModelRenderer.Orientation.k,
            BlockModelRenderer.Orientation.b,
            BlockModelRenderer.Orientation.e,
            BlockModelRenderer.Orientation.h,
            BlockModelRenderer.Orientation.e,
            BlockModelRenderer.Orientation.h,
            BlockModelRenderer.Orientation.k
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.b,
            BlockModelRenderer.Orientation.l,
            BlockModelRenderer.Orientation.b,
            BlockModelRenderer.Orientation.f,
            BlockModelRenderer.Orientation.h,
            BlockModelRenderer.Orientation.f,
            BlockModelRenderer.Orientation.h,
            BlockModelRenderer.Orientation.l
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.a,
            BlockModelRenderer.Orientation.l,
            BlockModelRenderer.Orientation.a,
            BlockModelRenderer.Orientation.f,
            BlockModelRenderer.Orientation.g,
            BlockModelRenderer.Orientation.f,
            BlockModelRenderer.Orientation.g,
            BlockModelRenderer.Orientation.l
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.a,
            BlockModelRenderer.Orientation.k,
            BlockModelRenderer.Orientation.a,
            BlockModelRenderer.Orientation.e,
            BlockModelRenderer.Orientation.g,
            BlockModelRenderer.Orientation.e,
            BlockModelRenderer.Orientation.g,
            BlockModelRenderer.Orientation.k
         }
      ),
      d(
         new EnumFacing[]{EnumFacing.e, EnumFacing.f, EnumFacing.a, EnumFacing.b},
         0.8F,
         true,
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.b,
            BlockModelRenderer.Orientation.k,
            BlockModelRenderer.Orientation.h,
            BlockModelRenderer.Orientation.k,
            BlockModelRenderer.Orientation.h,
            BlockModelRenderer.Orientation.e,
            BlockModelRenderer.Orientation.b,
            BlockModelRenderer.Orientation.e
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.a,
            BlockModelRenderer.Orientation.k,
            BlockModelRenderer.Orientation.g,
            BlockModelRenderer.Orientation.k,
            BlockModelRenderer.Orientation.g,
            BlockModelRenderer.Orientation.e,
            BlockModelRenderer.Orientation.a,
            BlockModelRenderer.Orientation.e
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.a,
            BlockModelRenderer.Orientation.l,
            BlockModelRenderer.Orientation.g,
            BlockModelRenderer.Orientation.l,
            BlockModelRenderer.Orientation.g,
            BlockModelRenderer.Orientation.f,
            BlockModelRenderer.Orientation.a,
            BlockModelRenderer.Orientation.f
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.b,
            BlockModelRenderer.Orientation.l,
            BlockModelRenderer.Orientation.h,
            BlockModelRenderer.Orientation.l,
            BlockModelRenderer.Orientation.h,
            BlockModelRenderer.Orientation.f,
            BlockModelRenderer.Orientation.b,
            BlockModelRenderer.Orientation.f
         }
      ),
      e(
         new EnumFacing[]{EnumFacing.b, EnumFacing.a, EnumFacing.c, EnumFacing.d},
         0.6F,
         true,
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.b,
            BlockModelRenderer.Orientation.d,
            BlockModelRenderer.Orientation.b,
            BlockModelRenderer.Orientation.j,
            BlockModelRenderer.Orientation.h,
            BlockModelRenderer.Orientation.j,
            BlockModelRenderer.Orientation.h,
            BlockModelRenderer.Orientation.d
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.b,
            BlockModelRenderer.Orientation.c,
            BlockModelRenderer.Orientation.b,
            BlockModelRenderer.Orientation.i,
            BlockModelRenderer.Orientation.h,
            BlockModelRenderer.Orientation.i,
            BlockModelRenderer.Orientation.h,
            BlockModelRenderer.Orientation.c
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.a,
            BlockModelRenderer.Orientation.c,
            BlockModelRenderer.Orientation.a,
            BlockModelRenderer.Orientation.i,
            BlockModelRenderer.Orientation.g,
            BlockModelRenderer.Orientation.i,
            BlockModelRenderer.Orientation.g,
            BlockModelRenderer.Orientation.c
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.a,
            BlockModelRenderer.Orientation.d,
            BlockModelRenderer.Orientation.a,
            BlockModelRenderer.Orientation.j,
            BlockModelRenderer.Orientation.g,
            BlockModelRenderer.Orientation.j,
            BlockModelRenderer.Orientation.g,
            BlockModelRenderer.Orientation.d
         }
      ),
      f(
         new EnumFacing[]{EnumFacing.a, EnumFacing.b, EnumFacing.c, EnumFacing.d},
         0.6F,
         true,
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.g,
            BlockModelRenderer.Orientation.d,
            BlockModelRenderer.Orientation.g,
            BlockModelRenderer.Orientation.j,
            BlockModelRenderer.Orientation.a,
            BlockModelRenderer.Orientation.j,
            BlockModelRenderer.Orientation.a,
            BlockModelRenderer.Orientation.d
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.g,
            BlockModelRenderer.Orientation.c,
            BlockModelRenderer.Orientation.g,
            BlockModelRenderer.Orientation.i,
            BlockModelRenderer.Orientation.a,
            BlockModelRenderer.Orientation.i,
            BlockModelRenderer.Orientation.a,
            BlockModelRenderer.Orientation.c
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.h,
            BlockModelRenderer.Orientation.c,
            BlockModelRenderer.Orientation.h,
            BlockModelRenderer.Orientation.i,
            BlockModelRenderer.Orientation.b,
            BlockModelRenderer.Orientation.i,
            BlockModelRenderer.Orientation.b,
            BlockModelRenderer.Orientation.c
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.h,
            BlockModelRenderer.Orientation.d,
            BlockModelRenderer.Orientation.h,
            BlockModelRenderer.Orientation.j,
            BlockModelRenderer.Orientation.b,
            BlockModelRenderer.Orientation.j,
            BlockModelRenderer.Orientation.b,
            BlockModelRenderer.Orientation.d
         }
      );

      protected final EnumFacing[] g;
      protected final float h;
      protected final boolean i;
      protected final BlockModelRenderer.Orientation[] j;
      protected final BlockModelRenderer.Orientation[] k;
      protected final BlockModelRenderer.Orientation[] l;
      protected final BlockModelRenderer.Orientation[] m;
      private static final BlockModelRenderer.EnumNeighborInfo[] n = new BlockModelRenderer.EnumNeighborInfo[6];

      private EnumNeighborInfo(
         EnumFacing[] var3,
         float var4,
         boolean var5,
         BlockModelRenderer.Orientation[] var6,
         BlockModelRenderer.Orientation[] var7,
         BlockModelRenderer.Orientation[] var8,
         BlockModelRenderer.Orientation[] var9
      ) {
         this.g = var3;
         this.h = var4;
         this.i = var5;
         this.j = var6;
         this.k = var7;
         this.l = var8;
         this.m = var9;
      }

      public static BlockModelRenderer.EnumNeighborInfo a(EnumFacing var0) {
         return n[var0.a()];
      }

      static {
         n[EnumFacing.a.a()] = a;
         n[EnumFacing.b.a()] = b;
         n[EnumFacing.c.a()] = c;
         n[EnumFacing.d.a()] = d;
         n[EnumFacing.e.a()] = e;
         n[EnumFacing.f.a()] = f;
      }
   }

   public static enum Orientation {
      a(EnumFacing.a, false),
      b(EnumFacing.b, false),
      c(EnumFacing.c, false),
      d(EnumFacing.d, false),
      e(EnumFacing.e, false),
      f(EnumFacing.f, false),
      g(EnumFacing.a, true),
      h(EnumFacing.b, true),
      i(EnumFacing.c, true),
      j(EnumFacing.d, true),
      k(EnumFacing.e, true),
      l(EnumFacing.f, true);

      protected final int m;

      private Orientation(EnumFacing var3, boolean var4) {
         this.m = var3.a() + (var4 ? EnumFacing.values().length : 0);
      }
   }

   static enum VertexTranslations {
      a(0, 1, 2, 3),
      b(2, 3, 0, 1),
      c(3, 0, 1, 2),
      d(0, 1, 2, 3),
      e(3, 0, 1, 2),
      f(1, 2, 3, 0);

      private final int g;
      private final int h;
      private final int i;
      private final int j;
      private static final BlockModelRenderer.VertexTranslations[] k = new BlockModelRenderer.VertexTranslations[6];

      private VertexTranslations(int var3, int var4, int var5, int var6) {
         this.g = var3;
         this.h = var4;
         this.i = var5;
         this.j = var6;
      }

      public static BlockModelRenderer.VertexTranslations a(EnumFacing var0) {
         return k[var0.a()];
      }

      static {
         k[EnumFacing.a.a()] = a;
         k[EnumFacing.b.a()] = b;
         k[EnumFacing.c.a()] = c;
         k[EnumFacing.d.a()] = d;
         k[EnumFacing.e.a()] = e;
         k[EnumFacing.f.a()] = f;
      }
   }
}
