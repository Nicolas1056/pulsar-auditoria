package net.minecraft.client.renderer.entity.layers;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StringUtils;

public class LayerCustomHead implements LayerRenderer<EntityLivingBase> {
   private final ModelRenderer a;

   public LayerCustomHead(ModelRenderer var1) {
      this.a = var1;
   }

   @Override
   public void a(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      ItemStack var9 = var1.q(3);
      if (var9 != null && var9.b() != null) {
         Item var10 = var9.b();
         Minecraft var11 = Minecraft.A();
         GlStateManager.E();
         if (var1.av()) {
            GlStateManager.b(0.0F, 0.2F, 0.0F);
         }

         boolean var12 = var1 instanceof EntityVillager || var1 instanceof EntityZombie && ((EntityZombie)var1).co();
         if (!var12 && var1.j_()) {
            float var13 = 2.0F;
            float var14 = 1.4F;
            GlStateManager.a(var14 / var13, var14 / var13, var14 / var13);
            GlStateManager.b(0.0F, 16.0F * var8, 0.0F);
         }

         this.a.c(0.0625F);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         if (var10 instanceof ItemBlock) {
            float var17 = 0.625F;
            GlStateManager.b(0.0F, -0.25F, 0.0F);
            GlStateManager.b(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.a(var17, -var17, -var17);
            if (var12) {
               GlStateManager.b(0.0F, 0.1875F, 0.0F);
            }

            var11.ah().a(var1, var9, ItemCameraTransforms.TransformType.d);
         } else if (var10 == Items.bX) {
            float var18 = 1.1875F;
            GlStateManager.a(var18, -var18, -var18);
            if (var12) {
               GlStateManager.b(0.0F, 0.0625F, 0.0F);
            }

            GameProfile var19 = null;
            if (var9.n()) {
               NBTTagCompound var15 = var9.o();
               if (var15.b("SkullOwner", 10)) {
                  var19 = NBTUtil.a(var15.m("SkullOwner"));
               } else if (var15.b("SkullOwner", 8)) {
                  String var16 = var15.j("SkullOwner");
                  if (!StringUtils.b(var16)) {
                     var19 = TileEntitySkull.b(new GameProfile(null, var16));
                     var15.a("SkullOwner", NBTUtil.a(new NBTTagCompound(), var19));
                  }
               }
            }

            TileEntitySkullRenderer.c.a(-0.5F, 0.0F, -0.5F, EnumFacing.b, 180.0F, var9.i(), var19, -1);
         }

         GlStateManager.F();
      }
   }

   @Override
   public boolean b() {
      return true;
   }
}
