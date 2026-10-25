package com.metrofico.pulsar.engine.v26x.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({EntityRenderer.class})
public interface EntityRendererInvoker {
   @Invoker("submitNameDisplay")
   void pulsar$submitNameDisplay(EntityRenderState var1, PoseStack var2, SubmitNodeCollector var3, CameraRenderState var4);
}
