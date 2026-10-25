package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.social.PulsarPlayers;
import com.metrofico.pulsar.engine.v189.gfx.PulsarAtlases;
import com.metrofico.pulsar.engine.v189.pack.Pack189;
import com.metrofico.pulsar.engine.v189.perf.GlState189;
import com.metrofico.pulsar.engine.v189.perf.TabCache189;
import com.mojang.authlib.GameProfile;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({awh.class})
public abstract class PlayerListHudMixin {
   private static final MethodHandle PULSAR$PROFILE_ID = pulsar$resolveProfileId();
   private static final MethodHandle PULSAR$PROFILE_NAME = pulsar$resolveProfileName();
   private static String[] pulsar$names = new String[0];
   private static int pulsar$namesTick = Integer.MIN_VALUE;
   private static final int MARK_GAP = 9;
   private static final int[] PULSAR$STATE = new int[24];

   @Shadow
   public abstract String a(bdc var1);

   @Inject(
      method = {"a(ILauo;Lauk;)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$tabList(int width, auo scoreboard, auk objective, CallbackInfo ci) {
      if (!Pack189.get().tabList().nativeVisible() || TabCache189.draw((awh)this, width, scoreboard, objective)) {
         ci.cancel();
      }
   }

   private static MethodHandle pulsar$resolveProfileId() {
      for (String name : new String[]{"getId", "id"}) {
         try {
            return MethodHandles.publicLookup().findVirtual(GameProfile.class, name, MethodType.methodType(UUID.class));
         } catch (ReflectiveOperationException var5) {
         }
      }

      return null;
   }

   private static MethodHandle pulsar$resolveProfileName() {
      for (String name : new String[]{"getName", "name"}) {
         try {
            return MethodHandles.publicLookup().findVirtual(GameProfile.class, name, MethodType.methodType(String.class));
         } catch (ReflectiveOperationException var5) {
         }
      }

      return null;
   }

   private boolean pulsar$namedInRow(bdc entry) {
      String[] names = pulsar$markedNames();
      if (names.length == 0) {
         return false;
      } else {
         String text = pulsar$plain(this.a(entry));

         for (String name : names) {
            if (pulsar$containsWord(text, name)) {
               return true;
            }
         }

         return false;
      }
   }

   private static String[] pulsar$markedNames() {
      int tick = HudModules.TICK;
      if (tick == pulsar$namesTick) {
         return pulsar$names;
      } else {
         pulsar$namesTick = tick;
         Set<UUID> marked = PulsarPlayers.inTab();
         ave mc = ave.A();
         List<String> out = new ArrayList<>(marked.size());

         for (UUID id : marked) {
            String name = null;
            bdc real = mc.u() == null ? null : mc.u().a(id);
            if (real != null) {
               name = pulsar$profileName(real.a());
            }

            if (name == null && mc.f != null) {
               wn player = mc.f.b(id);
               name = player == null ? null : player.e_();
            }

            if (name != null && !name.isEmpty()) {
               out.add(name);
            }
         }

         pulsar$names = out.toArray(new String[0]);
         return pulsar$names;
      }
   }

   private static String pulsar$profileName(GameProfile profile) {
      if (profile != null && PULSAR$PROFILE_NAME != null) {
         try {
            return (String)PULSAR$PROFILE_NAME.invoke((GameProfile)profile);
         } catch (Throwable var2) {
            return null;
         }
      } else {
         return null;
      }
   }

   private static String pulsar$plain(String text) {
      if (text != null && text.indexOf(167) >= 0) {
         StringBuilder b = new StringBuilder(text.length());

         for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == 167) {
               i++;
            } else {
               b.append(c);
            }
         }

         return b.toString();
      } else {
         return text == null ? "" : text;
      }
   }

   private static boolean pulsar$containsWord(String text, String name) {
      for (int at = text.indexOf(name); at >= 0; at = text.indexOf(name, at + 1)) {
         int end = at + name.length();
         if ((at == 0 || !pulsar$nameChar(text.charAt(at - 1))) && (end == text.length() || !pulsar$nameChar(text.charAt(end)))) {
            return true;
         }
      }

      return false;
   }

   private static boolean pulsar$nameChar(char c) {
      return Character.isLetterOrDigit(c) || c == '_';
   }

   private static UUID pulsar$profileId(GameProfile profile) {
      if (profile != null && PULSAR$PROFILE_ID != null) {
         try {
            return (UUID)PULSAR$PROFILE_ID.invoke((GameProfile)profile);
         } catch (Throwable var2) {
            return null;
         }
      } else {
         return null;
      }
   }

   @ModifyConstant(
      method = {"a(ILauo;Lauk;)V"},
      constant = {@Constant(
         intValue = 13
      )},
      require = 1
   )
   private int pulsar$markGap(int pingWidth) {
      return PulsarPlayers.anyInTab() ? pingWidth + 9 : pingWidth;
   }

   @Inject(
      method = {"a(IIILbdc;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$mark(int width, int x, int y, bdc entry, CallbackInfo ci) {
      int texture;
      if (entry != null && (PulsarPlayers.tab(pulsar$profileId(entry.a())) || this.pulsar$namedInRow(entry)) && (texture = PulsarAtlases.mark()) != 0) {
         float size = 7.0F;
         float left = x + width - 11 - 9;
         float top = y + (8.0F - size) / 2.0F;
         GlState189.snapshot(PULSAR$STATE);
         bfl.w();
         bfl.i(texture);
         bfl.l();
         bfl.a(770, 771, 1, 0);
         bfl.c();
         bfl.c(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glBegin(7);
         pulsar$vertex(0.0F, 0.0F, left, top);
         pulsar$vertex(0.0F, 1.0F, left, top + size);
         pulsar$vertex(1.0F, 1.0F, left + size, top + size);
         pulsar$vertex(1.0F, 0.0F, left + size, top);
         GL11.glEnd();
         GlState189.apply(PULSAR$STATE);
      }
   }

   private static void pulsar$vertex(float u, float v, float x, float y) {
      GL11.glTexCoord2f(u, v);
      GL11.glVertex2f(x, y);
   }
}
