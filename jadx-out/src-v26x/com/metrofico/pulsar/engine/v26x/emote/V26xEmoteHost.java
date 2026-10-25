package com.metrofico.pulsar.engine.v26x.emote;

import com.metrofico.pulsar.api.emote.EmoteHost;
import com.metrofico.pulsar.api.emote.EmoteHost.CancelState;
import com.metrofico.pulsar.api.emote.EmoteHost.SoundHandle;
import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.engine.v26x.hub.Canvas26;
import java.io.InputStream;
import java.util.Locale;
import java.util.UUID;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.entity.player.Player;

public final class V26xEmoteHost implements EmoteHost {
   public boolean paused() {
      Minecraft mc = Minecraft.getInstance();
      return mc != null && mc.isPaused();
   }

   public boolean inWorld() {
      return Minecraft.getInstance().level != null;
   }

   public int ownEntityId() {
      LocalPlayer p = Minecraft.getInstance().player;
      return p == null ? -1 : p.getId();
   }

   public int entityIdOf(UUID player) {
      Minecraft mc = Minecraft.getInstance();
      Player p = mc.level == null ? null : mc.level.getPlayerByUUID(player);
      return p == null ? -1 : p.getId();
   }

   public boolean entityExists(int entityId) {
      Minecraft mc = Minecraft.getInstance();
      return mc.level != null && mc.level.getEntity(entityId) != null;
   }

   public float bodyYaw(int entityId) {
      Minecraft mc = Minecraft.getInstance();
      return (mc.level == null ? null : mc.level.getEntity(entityId)) instanceof LivingEntity le ? le.yBodyRot : 0.0F;
   }

   public CancelState cancelState(int entityId, boolean own) {
      Minecraft mc = Minecraft.getInstance();
      Entity entity = mc.level == null ? null : mc.level.getEntity(entityId);
      if (!(entity instanceof LivingEntity le)) {
         return null;
      } else {
         boolean swinging = false;
         boolean using = false;
         boolean moving = false;
         boolean screenBlocks = false;
         if (own && entity instanceof LocalPlayer p) {
            swinging = p.swinging;
            using = p.isUsingItem();
            Input k = p.input == null ? Input.EMPTY : p.input.keyPresses;
            moving = k.forward() || k.backward() || k.left() || k.right() || k.jump() || k.shift();
            Screen screen = mc.gui.screen();
            screenBlocks = screen != null && !(screen instanceof ChatScreen) && !(screen instanceof EmoteScreen);
         }

         double dx = le.getX() - le.xOld;
         double dy = le.getY() - le.yOld;
         double dz = le.getZ() - le.zOld;
         return new CancelState(
            le.hurtTime > 0,
            le.isFallFlying() || le.isVisuallySwimming() || le.isSwimming(),
            le.isPassenger(),
            le.isSleeping(),
            swinging,
            using,
            moving,
            screenBlocks,
            dx * dx + dy * dy + dz * dz
         );
      }
   }

   public String accountUuid() {
      Minecraft mc = Minecraft.getInstance();
      return mc.getUser() != null && mc.getUser().getProfileId() != null ? mc.getUser().getProfileId().toString() : null;
   }

   public String currentServer() {
      ServerData s = Minecraft.getInstance().getCurrentServer();
      return s != null && s.ip != null && !s.ip.isBlank() ? s.ip.trim().toLowerCase(Locale.ROOT) : null;
   }

   public void runOnMain(Runnable task) {
      Minecraft.getInstance().execute(task);
   }

   public String cameraType() {
      Options o = Minecraft.getInstance().options;
      if (o == null) {
         return "first";
      } else {
         return switch (o.getCameraType()) {
            case THIRD_PERSON_BACK -> "back";
            case THIRD_PERSON_FRONT -> "front";
            default -> "first";
         };
      }
   }

   public void cameraType(String type) {
      Options o = Minecraft.getInstance().options;
      if (o != null) {
         o.setCameraType(switch (type) {
            case "back" -> CameraType.THIRD_PERSON_BACK;
            case "front" -> CameraType.THIRD_PERSON_FRONT;
            default -> CameraType.FIRST_PERSON;
         });
      }
   }

   public float[] ownLook() {
      LocalPlayer p = Minecraft.getInstance().player;
      return p == null ? null : new float[]{p.getYRot(), p.getXRot()};
   }

   public SoundHandle playSound(String sha256, int entityId, float volume, float pitch) {
      EmoteSoundInstance instance = new EmoteSoundInstance(sha256, entityId, volume, pitch);
      Minecraft.getInstance().getSoundManager().play(instance);
      return instance;
   }

   public void openWheel(boolean hold) {
      Minecraft.getInstance().setScreenAndShow(new EmoteWheelScreen(hold));
   }

   public void openLibrary(int slot) {
      Minecraft.getInstance().setScreenAndShow(new EmoteLibraryScreen(slot));
   }

   public Canvas measure() {
      return Canvas26.measure();
   }

   public boolean drawOwnPlayer(Canvas g, int x0, int y0, int x1, int y1, int size, float lookX, float lookY) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player == null) {
         return false;
      } else {
         InventoryScreen.extractEntityInInventoryFollowsMouse(((Canvas26)g).gfx(), x0, y0, x1, y1, size, 0.0625F, lookX, lookY, mc.player);
         return true;
      }
   }

   public InputStream resource(String absolutePath) {
      return V26xEmoteHost.class.getResourceAsStream(absolutePath);
   }
}
