package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.hub.ClientOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ItemInHandRenderer.class})
abstract class ItemInHandRendererMixin {
   @Shadow
   private float mainHandHeight;
   @Shadow
   private float oMainHandHeight;
   @Shadow
   private ItemStack mainHandItem;

   @Inject(
      method = {"tick"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$classicHand(CallbackInfo ci) {
      if (ClientOptions.get().classicPvp()) {
         LocalPlayer player = Minecraft.getInstance().player;
         if (player != null && ItemStack.matches(this.mainHandItem, player.getMainHandItem())) {
            this.mainHandHeight = 1.0F;
            this.oMainHandHeight = 1.0F;
         }
      }
   }
}
