package com.metrofico.pulsar.engine.v189.emote;

import com.metrofico.pulsar.api.emote.EmoteHost;
import com.metrofico.pulsar.api.emote.EmoteHost.CancelState;
import com.metrofico.pulsar.api.emote.EmoteHost.SoundHandle;
import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.engine.v189.gfx.Canvas189;
import com.metrofico.pulsar.engine.v189.ui.EmoteLibraryScreen189;
import com.metrofico.pulsar.engine.v189.ui.EmoteWheelScreen189;
import java.io.InputStream;
import java.util.Locale;
import java.util.UUID;

public final class V189EmoteHost implements EmoteHost {
   private static final float PLAYER_HEIGHT_BLOCKS = 1.8F;

   public boolean paused() {
      ave mc = ave.A();
      return mc != null && mc.V();
   }

   public boolean inWorld() {
      return ave.A().f != null;
   }

   public int ownEntityId() {
      bew p = ave.A().h;
      return p == null ? -1 : p.F();
   }

   public int entityIdOf(UUID player) {
      ave mc = ave.A();
      wn p = mc.f == null ? null : mc.f.b(player);
      return p == null ? -1 : p.F();
   }

   public boolean entityExists(int entityId) {
      ave mc = ave.A();
      return mc.f != null && mc.f.a(entityId) != null;
   }

   public float bodyYaw(int entityId) {
      ave mc = ave.A();
      return (mc.f == null ? null : mc.f.a(entityId)) instanceof pr le ? le.aI : 0.0F;
   }

   public CancelState cancelState(int entityId, boolean own) {
      ave mc = ave.A();
      pk e = mc.f == null ? null : mc.f.a(entityId);
      if (!(e instanceof pr le)) {
         return null;
      } else {
         boolean swinging = false;
         boolean using = false;
         boolean moving = false;
         boolean screenBlocks = false;
         if (own && e instanceof bew p) {
            swinging = p.ar;
            using = p.bS();
            beu in = p.b;
            moving = in != null && (in.b != 0.0F || in.a != 0.0F || in.c || in.d);
            axu screen = mc.m;
            screenBlocks = screen != null && !(screen instanceof awv) && !(screen instanceof V189EmoteHost.EmoteScreen189);
         }

         double dx = le.s - le.p;
         double dy = le.t - le.q;
         double dz = le.u - le.r;
         return new CancelState(le.au > 0, false, le.au(), le.bJ(), swinging, using, moving, screenBlocks, dx * dx + dy * dy + dz * dz);
      }
   }

   public String accountUuid() {
      avm s = ave.A().L();
      String uuid = s == null ? null : s.b();
      return uuid != null && !uuid.isEmpty() ? uuid : null;
   }

   public String currentServer() {
      bde s = ave.A().D();
      return s != null && s.b != null && !s.b.isBlank() ? s.b.trim().toLowerCase(Locale.ROOT) : null;
   }

   public void runOnMain(Runnable task) {
      ave.A().a(() -> {
         task.run();
         return null;
      });
   }

   public String cameraType() {
      avh o = ave.A().t;
      String var10000;
      if (o == null) {
         var10000 = "first";
      } else {
         switch (o.aB) {
            case 1:
               var10000 = "back";
               break;
            case 2:
               var10000 = "front";
               break;
            default:
               var10000 = "first";
         }
      }

      return var10000;
   }

   public void cameraType(String type) {
      ave mc = ave.A();
      avh o = mc.t;
      if (o != null) {
         int next = switch (type) {
            case "back" -> 1;
            case "front" -> 2;
            default -> 0;
         };
         if (o.aB != next) {
            o.aB = next;
            if (next == 0) {
               mc.o.a(mc.ac());
            } else if (next == 1) {
               mc.o.a(null);
            }

            if (mc.g != null) {
               mc.g.m();
            }
         }
      }
   }

   public float[] ownLook() {
      bew p = ave.A().h;
      return p == null ? null : new float[]{p.y, p.z};
   }

   public SoundHandle playSound(String sha256, int entityId, float volume, float pitch) {
      return EmoteSounds189.play(sha256, entityId, volume, pitch);
   }

   public void openWheel(boolean hold) {
      ave.A().a(new EmoteWheelScreen189(hold));
   }

   public void openLibrary(int slot) {
      ave.A().a(new EmoteLibraryScreen189(slot));
   }

   public Canvas measure() {
      return new Canvas189();
   }

   public boolean drawOwnPlayer(Canvas g, int x0, int y0, int x1, int y1, int size, float lookX, float lookY) {
      bew p = ave.A().h;
      if (p == null) {
         return false;
      } else {
         int cx = (x0 + x1) / 2;
         int fit = Math.min(size, (int)((y1 - y0 - 8) / 1.8F));
         int feet = (y0 + y1) / 2 + Math.round(fit * 1.8F / 2.0F);
         ((Canvas189)g).direct(() -> azc.a(cx, feet, fit, cx - lookX, (y0 + y1) / 2.0F - lookY, p));
         return true;
      }
   }

   public InputStream resource(String absolutePath) {
      return V189EmoteHost.class.getResourceAsStream(absolutePath);
   }

   public interface EmoteScreen189 {
   }
}
