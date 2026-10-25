package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.perf.FrameFlags;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessage;
import net.minecraft.client.multiplayer.chat.GuiMessage.Line;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ChatComponent.class})
public abstract class ChatComponentMixin {
   @Shadow
   @Final
   private Minecraft minecraft;
   @Shadow
   @Final
   private List<GuiMessage> allMessages;
   @Shadow
   @Final
   private List<Line> trimmedMessages;
   @Shadow
   private Predicate<GuiMessage> visibleMessageFilter;
   @Unique
   private int pulsar$pending = -1;

   @Shadow
   private int getWidth() {
      throw new AssertionError();
   }

   @Shadow
   private double getScale() {
      throw new AssertionError();
   }

   @ModifyConstant(
      method = {"addMessageToDisplayQueue", "addMessageToQueue"},
      constant = {@Constant(
         intValue = 100
      )},
      require = 2
   )
   private int pulsar$history(int vanilla) {
      return 10000;
   }

   @Inject(
      method = {"addMessageToQueue"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$beforeQueue(GuiMessage message, CallbackInfo ci) {
      if (this.pulsar$pending >= 0) {
         this.pulsar$pending++;
      }
   }

   @Inject(
      method = {"clearMessages"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$cleared(boolean history, CallbackInfo ci) {
      this.pulsar$pending = -1;
   }

   @Inject(
      method = {"refreshTrimmedMessages"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$refresh(CallbackInfo ci) {
      if (!FrameFlags.chatRewrap) {
         this.pulsar$pending = -1;
      } else {
         this.trimmedMessages.clear();
         this.pulsar$pending = 0;
         this.pulsar$rewrap(100);
         ci.cancel();
      }
   }

   @Inject(
      method = {"extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/gui/Font;IIILnet/minecraft/client/gui/components/ChatComponent$DisplayMode;Z)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$frame(CallbackInfo ci) {
      if (this.pulsar$pending >= 0) {
         this.pulsar$rewrap(200);
      }
   }

   @Unique
   private void pulsar$rewrap(int budget) {
      int width = Mth.floor(this.getWidth() / this.getScale());

      while (budget-- > 0 && this.pulsar$pending >= 0) {
         if (this.pulsar$pending >= this.allMessages.size() || this.trimmedMessages.size() >= 10000) {
            this.pulsar$pending = -1;
            break;
         }

         GuiMessage message = this.allMessages.get(this.pulsar$pending++);
         if (this.visibleMessageFilter.test(message)) {
            List<FormattedCharSequence> lines = message.splitLines(this.minecraft.font, width);

            for (int j = lines.size() - 1; j >= 0; j--) {
               this.trimmedMessages.add(new Line(message, lines.get(j), j == lines.size() - 1));
            }
         }
      }
   }
}
