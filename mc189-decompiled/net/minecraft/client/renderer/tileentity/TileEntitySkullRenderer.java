package net.minecraft.client.renderer.tileentity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class TileEntitySkullRenderer extends TileEntitySpecialRenderer<TileEntitySkull> {
   private static final ResourceLocation d = new ResourceLocation("textures/entity/skeleton/skeleton.png");
   private static final ResourceLocation e = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
   private static final ResourceLocation f = new ResourceLocation("textures/entity/zombie/zombie.png");
   private static final ResourceLocation g = new ResourceLocation("textures/entity/creeper/creeper.png");
   public static TileEntitySkullRenderer c;
   private final ModelSkeletonHead h = new ModelSkeletonHead(0, 0, 64, 32);
   private final ModelSkeletonHead i = new ModelHumanoidHead();

   public void a(TileEntitySkull var1, double var2, double var4, double var6, float var8, int var9) {
      EnumFacing var10 = EnumFacing.a(var1.u() & 7);
      this.a((float)var2, (float)var4, (float)var6, var10, var1.d() * 360 / 16.0F, var1.c(), var1.b(), var9);
   }

   @Override
   public void a(TileEntityRendererDispatcher var1) {
      super.a(var1);
      c = this;
   }

   public void a(float var1, float var2, float var3, EnumFacing var4, float var5, int var6, GameProfile var7, int var8) {
      ModelSkeletonHead var9 = this.h;
      if (var8 >= 0) {
         this.a(a[var8]);
         GlStateManager.n(5890);
         GlStateManager.E();
         GlStateManager.a(4.0F, 2.0F, 1.0F);
         GlStateManager.b(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.n(5888);
      } else {
         switch (var6) {
            case 0:
            default:
               this.a(d);
               break;
            case 1:
               this.a(e);
               break;
            case 2:
               this.a(f);
               var9 = this.i;
               break;
            case 3:
               var9 = this.i;
               ResourceLocation var10 = DefaultPlayerSkin.a();
               if (var7 != null) {
                  Minecraft var11 = Minecraft.A();
                  Map var12 = var11.ab().a(var7);
                  if (var12.containsKey(Type.SKIN)) {
                     var10 = var11.ab().a((MinecraftProfileTexture)var12.get(Type.SKIN), Type.SKIN);
                  } else {
                     UUID var13 = EntityPlayer.a(var7);
                     var10 = DefaultPlayerSkin.a(var13);
                  }
               }

               this.a(var10);
               break;
            case 4:
               this.a(g);
         }
      }

      GlStateManager.E();
      GlStateManager.p();
      if (var4 != EnumFacing.b) {
         switch (var4) {
            case c:
               GlStateManager.b(var1 + 0.5F, var2 + 0.25F, var3 + 0.74F);
               break;
            case d:
               GlStateManager.b(var1 + 0.5F, var2 + 0.25F, var3 + 0.26F);
               var5 = 180.0F;
               break;
            case e:
               GlStateManager.b(var1 + 0.74F, var2 + 0.25F, var3 + 0.5F);
               var5 = 270.0F;
               break;
            case f:
            default:
               GlStateManager.b(var1 + 0.26F, var2 + 0.25F, var3 + 0.5F);
               var5 = 90.0F;
         }
      } else {
         GlStateManager.b(var1 + 0.5F, var2, var3 + 0.5F);
      }

      float var14 = 0.0625F;
      GlStateManager.B();
      GlStateManager.a(-1.0F, -1.0F, 1.0F);
      GlStateManager.d();
      var9.a(null, 0.0F, 0.0F, 0.0F, var5, 0.0F, var14);
      GlStateManager.F();
      if (var8 >= 0) {
         GlStateManager.n(5890);
         GlStateManager.F();
         GlStateManager.n(5888);
      }
   }
}
