package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.client.resources.model.WeightedBakedModel;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;

public class BlockRendererDispatcher implements IResourceManagerReloadListener {
   private BlockModelShapes a;
   private final GameSettings b;
   private final BlockModelRenderer c = new BlockModelRenderer();
   private final ChestRenderer d = new ChestRenderer();
   private final BlockFluidRenderer e = new BlockFluidRenderer();

   public BlockRendererDispatcher(BlockModelShapes var1, GameSettings var2) {
      this.a = var1;
      this.b = var2;
   }

   public BlockModelShapes a() {
      return this.a;
   }

   public void a(IBlockState var1, BlockPos var2, TextureAtlasSprite var3, IBlockAccess var4) {
      Block var5 = var1.c();
      int var6 = var5.b();
      if (var6 == 3) {
         var1 = var5.a(var1, var4, var2);
         IBakedModel var7 = this.a.b(var1);
         IBakedModel var8 = new SimpleBakedModel.Builder(var7, var3).b();
         this.c.a(var4, var8, var1, var2, Tessellator.a().c());
      }
   }

   public boolean a(IBlockState var1, BlockPos var2, IBlockAccess var3, WorldRenderer var4) {
      try {
         int var5 = var1.c().b();
         if (var5 == -1) {
            return false;
         } else {
            switch (var5) {
               case 1:
                  return this.e.a(var3, var1, var2, var4);
               case 2:
                  return false;
               case 3:
                  IBakedModel var9 = this.a(var1, var3, var2);
                  return this.c.a(var3, var9, var1, var2, var4);
               default:
                  return false;
            }
         }
      } catch (Throwable var8) {
         CrashReport var6 = CrashReport.a(var8, "Tesselating block in world");
         CrashReportCategory var7 = var6.a("Block being tesselated");
         CrashReportCategory.a(var7, var2, var1.c(), var1.c().c(var1));
         throw new ReportedException(var6);
      }
   }

   public BlockModelRenderer b() {
      return this.c;
   }

   private IBakedModel a(IBlockState var1, BlockPos var2) {
      IBakedModel var3 = this.a.b(var1);
      if (var2 != null && this.b.v && var3 instanceof WeightedBakedModel) {
         var3 = ((WeightedBakedModel)var3).a(MathHelper.a(var2));
      }

      return var3;
   }

   public IBakedModel a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      Block var4 = var1.c();
      if (var2.G() != WorldType.g) {
         try {
            var1 = var4.a(var1, var2, var3);
         } catch (Exception var6) {
         }
      }

      IBakedModel var5 = this.a.b(var1);
      if (var3 != null && this.b.v && var5 instanceof WeightedBakedModel) {
         var5 = ((WeightedBakedModel)var5).a(MathHelper.a(var3));
      }

      return var5;
   }

   public void a(IBlockState var1, float var2) {
      int var3 = var1.c().b();
      if (var3 != -1) {
         switch (var3) {
            case 1:
            default:
               break;
            case 2:
               this.d.a(var1.c(), var2);
               break;
            case 3:
               IBakedModel var4 = this.a(var1, null);
               this.c.a(var4, var1, var2, true);
         }
      }
   }

   public boolean a(Block var1, int var2) {
      if (var1 == null) {
         return false;
      } else {
         int var3 = var1.b();
         return var3 == 3 ? false : var3 == 2;
      }
   }

   @Override
   public void a(IResourceManager var1) {
      this.e.a();
   }
}
