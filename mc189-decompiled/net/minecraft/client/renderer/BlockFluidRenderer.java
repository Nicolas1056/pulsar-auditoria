package net.minecraft.client.renderer;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class BlockFluidRenderer {
   private TextureAtlasSprite[] a = new TextureAtlasSprite[2];
   private TextureAtlasSprite[] b = new TextureAtlasSprite[2];

   public BlockFluidRenderer() {
      this.a();
   }

   protected void a() {
      TextureMap var1 = Minecraft.A().T();
      this.a[0] = var1.a("minecraft:blocks/lava_still");
      this.a[1] = var1.a("minecraft:blocks/lava_flow");
      this.b[0] = var1.a("minecraft:blocks/water_still");
      this.b[1] = var1.a("minecraft:blocks/water_flow");
   }

   public boolean a(IBlockAccess var1, IBlockState var2, BlockPos var3, WorldRenderer var4) {
      BlockLiquid var5 = (BlockLiquid)var2.c();
      var5.a(var1, var3);
      TextureAtlasSprite[] var6 = var5.t() == Material.i ? this.a : this.b;
      int var7 = var5.d(var1, var3);
      float var8 = (var7 >> 16 & 0xFF) / 255.0F;
      float var9 = (var7 >> 8 & 0xFF) / 255.0F;
      float var10 = (var7 & 0xFF) / 255.0F;
      boolean var11 = var5.a(var1, var3.a(), EnumFacing.b);
      boolean var12 = var5.a(var1, var3.b(), EnumFacing.a);
      boolean[] var13 = new boolean[]{
         var5.a(var1, var3.c(), EnumFacing.c), var5.a(var1, var3.d(), EnumFacing.d), var5.a(var1, var3.e(), EnumFacing.e), var5.a(var1, var3.f(), EnumFacing.f)
      };
      if (!var11 && !var12 && !var13[0] && !var13[1] && !var13[2] && !var13[3]) {
         return false;
      } else {
         boolean var14 = false;
         float var15 = 0.5F;
         float var16 = 1.0F;
         float var17 = 0.8F;
         float var18 = 0.6F;
         Material var19 = var5.t();
         float var20 = this.a(var1, var3, var19);
         float var21 = this.a(var1, var3.d(), var19);
         float var22 = this.a(var1, var3.f().d(), var19);
         float var23 = this.a(var1, var3.f(), var19);
         double var24 = var3.n();
         double var26 = var3.o();
         double var28 = var3.p();
         float var30 = 0.001F;
         if (var11) {
            var14 = true;
            TextureAtlasSprite var31 = var6[0];
            float var32 = (float)BlockLiquid.a(var1, var3, var19);
            if (var32 > -999.0F) {
               var31 = var6[1];
            }

            var20 -= var30;
            var21 -= var30;
            var22 -= var30;
            var23 -= var30;
            float var33;
            float var34;
            float var35;
            float var36;
            float var37;
            float var38;
            float var39;
            float var40;
            if (var32 < -999.0F) {
               var33 = var31.a(0.0);
               var37 = var31.b(0.0);
               var34 = var33;
               var38 = var31.b(16.0);
               var35 = var31.a(16.0);
               var39 = var38;
               var36 = var35;
               var40 = var37;
            } else {
               float var41 = MathHelper.a(var32) * 0.25F;
               float var42 = MathHelper.b(var32) * 0.25F;
               float var43 = 8.0F;
               var33 = var31.a((double)(8.0F + (-var42 - var41) * 16.0F));
               var37 = var31.b((double)(8.0F + (-var42 + var41) * 16.0F));
               var34 = var31.a((double)(8.0F + (-var42 + var41) * 16.0F));
               var38 = var31.b((double)(8.0F + (var42 + var41) * 16.0F));
               var35 = var31.a((double)(8.0F + (var42 + var41) * 16.0F));
               var39 = var31.b((double)(8.0F + (var42 - var41) * 16.0F));
               var36 = var31.a((double)(8.0F + (var42 - var41) * 16.0F));
               var40 = var31.b((double)(8.0F + (-var42 - var41) * 16.0F));
            }

            int var74 = var5.c(var1, var3);
            int var75 = var74 >> 16 & 65535;
            int var77 = var74 & 65535;
            float var44 = var16 * var8;
            float var45 = var16 * var9;
            float var46 = var16 * var10;
            var4.b(var24 + 0.0, var26 + var20, var28 + 0.0).a(var44, var45, var46, 1.0F).a((double)var33, (double)var37).a(var75, var77).d();
            var4.b(var24 + 0.0, var26 + var21, var28 + 1.0).a(var44, var45, var46, 1.0F).a((double)var34, (double)var38).a(var75, var77).d();
            var4.b(var24 + 1.0, var26 + var22, var28 + 1.0).a(var44, var45, var46, 1.0F).a((double)var35, (double)var39).a(var75, var77).d();
            var4.b(var24 + 1.0, var26 + var23, var28 + 0.0).a(var44, var45, var46, 1.0F).a((double)var36, (double)var40).a(var75, var77).d();
            if (var5.g(var1, var3.a())) {
               var4.b(var24 + 0.0, var26 + var20, var28 + 0.0).a(var44, var45, var46, 1.0F).a((double)var33, (double)var37).a(var75, var77).d();
               var4.b(var24 + 1.0, var26 + var23, var28 + 0.0).a(var44, var45, var46, 1.0F).a((double)var36, (double)var40).a(var75, var77).d();
               var4.b(var24 + 1.0, var26 + var22, var28 + 1.0).a(var44, var45, var46, 1.0F).a((double)var35, (double)var39).a(var75, var77).d();
               var4.b(var24 + 0.0, var26 + var21, var28 + 1.0).a(var44, var45, var46, 1.0F).a((double)var34, (double)var38).a(var75, var77).d();
            }
         }

         if (var12) {
            float var59 = var6[0].e();
            float var61 = var6[0].f();
            float var63 = var6[0].g();
            float var65 = var6[0].h();
            int var67 = var5.c(var1, var3.b());
            int var69 = var67 >> 16 & 65535;
            int var71 = var67 & 65535;
            var4.b(var24, var26, var28 + 1.0).a(var15, var15, var15, 1.0F).a((double)var59, (double)var65).a(var69, var71).d();
            var4.b(var24, var26, var28).a(var15, var15, var15, 1.0F).a((double)var59, (double)var63).a(var69, var71).d();
            var4.b(var24 + 1.0, var26, var28).a(var15, var15, var15, 1.0F).a((double)var61, (double)var63).a(var69, var71).d();
            var4.b(var24 + 1.0, var26, var28 + 1.0).a(var15, var15, var15, 1.0F).a((double)var61, (double)var65).a(var69, var71).d();
            var14 = true;
         }

         for (int var60 = 0; var60 < 4; var60++) {
            int var62 = 0;
            int var64 = 0;
            if (var60 == 0) {
               var64--;
            }

            if (var60 == 1) {
               var64++;
            }

            if (var60 == 2) {
               var62--;
            }

            if (var60 == 3) {
               var62++;
            }

            BlockPos var66 = var3.a(var62, 0, var64);
            TextureAtlasSprite var58 = var6[1];
            if (var13[var60]) {
               float var68;
               float var70;
               double var72;
               double var73;
               double var76;
               double var78;
               if (var60 == 0) {
                  var68 = var20;
                  var70 = var23;
                  var72 = var24;
                  var76 = var24 + 1.0;
                  var73 = var28 + var30;
                  var78 = var28 + var30;
               } else if (var60 == 1) {
                  var68 = var22;
                  var70 = var21;
                  var72 = var24 + 1.0;
                  var76 = var24;
                  var73 = var28 + 1.0 - var30;
                  var78 = var28 + 1.0 - var30;
               } else if (var60 == 2) {
                  var68 = var21;
                  var70 = var20;
                  var72 = var24 + var30;
                  var76 = var24 + var30;
                  var73 = var28 + 1.0;
                  var78 = var28;
               } else {
                  var68 = var23;
                  var70 = var22;
                  var72 = var24 + 1.0 - var30;
                  var76 = var24 + 1.0 - var30;
                  var73 = var28;
                  var78 = var28 + 1.0;
               }

               var14 = true;
               float var79 = var58.a(0.0);
               float var47 = var58.a(8.0);
               float var48 = var58.b((double)((1.0F - var68) * 16.0F * 0.5F));
               float var49 = var58.b((double)((1.0F - var70) * 16.0F * 0.5F));
               float var50 = var58.b(8.0);
               int var51 = var5.c(var1, var66);
               int var52 = var51 >> 16 & 65535;
               int var53 = var51 & 65535;
               float var54 = var60 < 2 ? var17 : var18;
               float var55 = var16 * var54 * var8;
               float var56 = var16 * var54 * var9;
               float var57 = var16 * var54 * var10;
               var4.b(var72, var26 + var68, var73).a(var55, var56, var57, 1.0F).a((double)var79, (double)var48).a(var52, var53).d();
               var4.b(var76, var26 + var70, var78).a(var55, var56, var57, 1.0F).a((double)var47, (double)var49).a(var52, var53).d();
               var4.b(var76, var26 + 0.0, var78).a(var55, var56, var57, 1.0F).a((double)var47, (double)var50).a(var52, var53).d();
               var4.b(var72, var26 + 0.0, var73).a(var55, var56, var57, 1.0F).a((double)var79, (double)var50).a(var52, var53).d();
               var4.b(var72, var26 + 0.0, var73).a(var55, var56, var57, 1.0F).a((double)var79, (double)var50).a(var52, var53).d();
               var4.b(var76, var26 + 0.0, var78).a(var55, var56, var57, 1.0F).a((double)var47, (double)var50).a(var52, var53).d();
               var4.b(var76, var26 + var70, var78).a(var55, var56, var57, 1.0F).a((double)var47, (double)var49).a(var52, var53).d();
               var4.b(var72, var26 + var68, var73).a(var55, var56, var57, 1.0F).a((double)var79, (double)var48).a(var52, var53).d();
            }
         }

         return var14;
      }
   }

   private float a(IBlockAccess var1, BlockPos var2, Material var3) {
      int var4 = 0;
      float var5 = 0.0F;

      for (int var6 = 0; var6 < 4; var6++) {
         BlockPos var7 = var2.a(-(var6 & 1), 0, -(var6 >> 1 & 1));
         if (var1.p(var7.a()).c().t() == var3) {
            return 1.0F;
         }

         IBlockState var8 = var1.p(var7);
         Material var9 = var8.c().t();
         if (var9 == var3) {
            int var10 = var8.b(BlockLiquid.b);
            if (var10 >= 8 || var10 == 0) {
               var5 += BlockLiquid.b(var10) * 10.0F;
               var4 += 10;
            }

            var5 += BlockLiquid.b(var10);
            var4++;
         } else if (!var9.a()) {
            var5++;
            var4++;
         }
      }

      return 1.0F - var5 / var4;
   }
}
