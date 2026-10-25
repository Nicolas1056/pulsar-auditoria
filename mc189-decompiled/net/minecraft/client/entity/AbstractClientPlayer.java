package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

public abstract class AbstractClientPlayer extends EntityPlayer {
   private NetworkPlayerInfo a;

   public AbstractClientPlayer(World var1, GameProfile var2) {
      super(var1, var2);
   }

   @Override
   public boolean v() {
      NetworkPlayerInfo var1 = Minecraft.A().u().a(this.cd().getId());
      return var1 != null && var1.b() == WorldSettings.GameType.e;
   }

   public boolean a() {
      return this.b() != null;
   }

   protected NetworkPlayerInfo b() {
      if (this.a == null) {
         this.a = Minecraft.A().u().a(this.aK());
      }

      return this.a;
   }

   public boolean g() {
      NetworkPlayerInfo var1 = this.b();
      return var1 != null && var1.e();
   }

   public ResourceLocation i() {
      NetworkPlayerInfo var1 = this.b();
      return var1 == null ? DefaultPlayerSkin.a(this.aK()) : var1.g();
   }

   public ResourceLocation k() {
      NetworkPlayerInfo var1 = this.b();
      return var1 == null ? null : var1.h();
   }

   public static ThreadDownloadImageData a(ResourceLocation var0, String var1) {
      TextureManager var2 = Minecraft.A().P();
      Object var3 = var2.b(var0);
      if (var3 == null) {
         var3 = new ThreadDownloadImageData(
            null,
            String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.a(var1)),
            DefaultPlayerSkin.a(b(var1)),
            new ImageBufferDownload()
         );
         var2.a(var0, (ITextureObject)var3);
      }

      return (ThreadDownloadImageData)var3;
   }

   public static ResourceLocation c(String var0) {
      return new ResourceLocation("skins/" + StringUtils.a(var0));
   }

   public String l() {
      NetworkPlayerInfo var1 = this.b();
      return var1 == null ? DefaultPlayerSkin.b(this.aK()) : var1.f();
   }

   public float o() {
      float var1 = 1.0F;
      if (this.bA.b) {
         var1 *= 1.1F;
      }

      IAttributeInstance var2 = this.a(SharedMonsterAttributes.d);
      var1 = (float)(var1 * ((var2.e() / this.bA.b() + 1.0) / 2.0));
      if (this.bA.b() == 0.0F || Float.isNaN(var1) || Float.isInfinite(var1)) {
         var1 = 1.0F;
      }

      if (this.bS() && this.bQ().b() == Items.f) {
         int var3 = this.bT();
         float var4 = var3 / 20.0F;
         if (var4 > 1.0F) {
            var4 = 1.0F;
         } else {
            var4 *= var4;
         }

         var1 *= 1.0F - var4 * 0.15F;
      }

      return var1;
   }
}
