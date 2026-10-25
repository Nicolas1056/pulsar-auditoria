package com.metrofico.pulsar.api.emote;

import com.metrofico.pulsar.api.camera.CameraDirector;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.modules.Freecam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public final class EmotePlayer {
   private static final int LOOP_TTL_SECONDS = 300;
   private static final long PENDING_NANOS = 10000000000L;
   private static final double REMOTE_MOVE_SQ = 9.0E-4;
   private static EmotePlayer.Active[] active = new EmotePlayer.Active[4];
   private static int count;
   private static final List<EmotePlayer.Pending> pending = new ArrayList<>();
   private static String cameraBefore;
   private static String cameraSet;

   private EmotePlayer() {
   }

   public static EmotePlayer.Active active(int entityId) {
      EmotePlayer.Active[] a = active;

      for (int i = 0; i < count; i++) {
         if (a[i].entityId == entityId) {
            return a[i];
         }
      }

      return null;
   }

   public static boolean anyActive() {
      return count > 0;
   }

   public static EmotePlayer.Active play(int entityId, Emote emote, long elapsedMs, boolean own) {
      remove(entityId);
      long start = EmoteClock.nanos() - Math.max(0L, Math.min(elapsedMs, (long)(emote.duration() * 1000.0))) * 1000000L;
      float yaw = Emotes.host().bodyYaw(entityId);
      EmotePlayer.Active a = new EmotePlayer.Active(entityId, emote, start, own, yaw);
      if (count == active.length) {
         active = Arrays.copyOf(active, count * 2);
      }

      active[count++] = a;
      if (own) {
         enterCamera(emote);
      }

      return a;
   }

   public static EmotePlayer.Active playOwn(EmoteLibrary.Entry entry) {
      EmotePlayer.Active a = playOwn(entry.emote());
      if (a != null) {
         EmoteSync.announcePlay(entry.emote(), entry.sha256(), entry.inline());
      }

      return a;
   }

   public static EmotePlayer.Active playOwn(Emote emote) {
      int own = Emotes.host().ownEntityId();
      if (own < 0) {
         return null;
      } else {
         Emotes.log("emote: play " + emote.id() + (emote.loop() ? " (loop)" : ""));
         return play(own, emote, 0L, true);
      }
   }

   public static void play(UUID player, Emote emote, long elapsedMs) {
      EmoteHost host = Emotes.host();
      if (host.inWorld()) {
         int id = host.entityIdOf(player);
         boolean own = id >= 0 && id == host.ownEntityId();
         if (own || ClientOptions.get().bool("emotes.others", true)) {
            if (id >= 0) {
               play(id, emote, elapsedMs, own);
            } else {
               pending.removeIf(q -> q.player().equals(player));
               pending.add(new EmotePlayer.Pending(player, emote, EmoteClock.nanos(), elapsedMs));
            }
         }
      }
   }

   public static void stop(UUID player) {
      pending.removeIf(q -> q.player().equals(player));
      int id = Emotes.host().inWorld() ? Emotes.host().entityIdOf(player) : -1;
      if (id >= 0) {
         stop(id);
      }
   }

   public static void stop(int entityId) {
      EmotePlayer.Active a = active(entityId);
      if (a != null) {
         double now = a.elapsed();
         if (now < a.endAt) {
            a.endAt = now;
         }
      }
   }

   public static boolean stopOwn() {
      int own = Emotes.host().ownEntityId();
      EmotePlayer.Active a = own < 0 ? null : active(own);
      if (a != null && !(a.elapsed() >= a.endAt)) {
         stop(a.entityId);
         EmoteSync.announceStop();
         return true;
      } else {
         return false;
      }
   }

   public static EmotePlayer.Active own() {
      int own = Emotes.host().ownEntityId();
      return own < 0 ? null : active(own);
   }

   public static void tick() {
      EmoteHost host = Emotes.host();
      if (!host.inWorld()) {
         clear();
      } else {
         if (!pending.isEmpty()) {
            long now = EmoteClock.nanos();

            for (int i = pending.size() - 1; i >= 0; i--) {
               EmotePlayer.Pending q = pending.get(i);
               int id = host.entityIdOf(q.player());
               if (id >= 0) {
                  pending.remove(i);
                  long waited = (now - q.receivedNanos()) / 1000000L;
                  play(id, q.emote(), q.elapsedMs() + waited, id == host.ownEntityId());
               } else if (now - q.receivedNanos() > 10000000000L) {
                  pending.remove(i);
               }
            }
         }

         if (count != 0) {
            for (int ix = count - 1; ix >= 0; ix--) {
               EmotePlayer.Active a = active[ix];
               double elapsed = a.elapsed();
               if (host.entityExists(a.entityId) && !EmoteEvaluator.finished(elapsed, a.endAt) && (!a.emote.loop() || !(elapsed > 300.0))) {
                  if (elapsed < a.endAt && shouldCancel(host, a)) {
                     a.endAt = elapsed;
                     if (a.own) {
                        EmoteSync.announceStop();
                     }
                  }

                  EmoteSounds.tick(a);
               } else {
                  removeAt(ix);
               }
            }
         }
      }
   }

   private static boolean shouldCancel(EmoteHost host, EmotePlayer.Active a) {
      EmoteHost.CancelState s = host.cancelState(a.entityId, a.own);
      if (s == null) {
         return true;
      } else if (s.hurt() || s.airborneOrSwimming() || s.passenger() || s.sleeping()) {
         return true;
      } else if (a.own) {
         if (s.swinging() || s.usingItem()) {
            return true;
         } else {
            return s.moving() && a.emote.cancelOnMove() ? true : s.screenBlocks();
         }
      } else {
         return a.emote.cancelOnMove() ? s.movedSq() > 9.0E-4 : false;
      }
   }

   public static void clear() {
      pending.clear();

      while (count > 0) {
         removeAt(count - 1);
      }
   }

   private static void remove(int entityId) {
      for (int i = 0; i < count; i++) {
         if (active[i].entityId == entityId) {
            removeAt(i);
            return;
         }
      }
   }

   private static void removeAt(int i) {
      EmotePlayer.Active a = active[i];
      EmoteSounds.stop(a);
      active[i] = active[count - 1];
      active[--count] = null;
      if (a.own) {
         leaveCamera();
      }
   }

   private static void enterCamera(Emote emote) {
      if (emote.camera() != Emote.Camera.NONE && !CameraDirector.get().active()) {
         String pref = ClientOptions.get().string("emotes.camera", "emote");
         if (!"none".equals(pref)) {
            boolean back = "back".equals(pref) || "emote".equals(pref) && emote.camera() == Emote.Camera.BACK;
            String target = back ? "back" : "front";
            if (cameraSet == null) {
               cameraBefore = Emotes.host().cameraType();
            }

            cameraSet = target;
            Emotes.host().cameraType(target);
            float[] look = Emotes.host().ownLook();
            if (look != null) {
               Freecam.beginEmoteOrbit(look[0], look[1]);
            }
         }
      }
   }

   private static void leaveCamera() {
      if (cameraSet != null) {
         Freecam.endEmoteOrbit();
         if (cameraSet.equals(Emotes.host().cameraType())) {
            Emotes.host().cameraType(cameraBefore);
         }

         cameraSet = null;
         cameraBefore = null;
      }
   }

   public static final class Active {
      public final int entityId;
      public final Emote emote;
      public final Emote.Bone[] bones = new Emote.Bone[Emote.BONE_ORDER.size()];
      public final boolean own;
      public final float lockYaw;
      final long startNanos;
      double endAt;

      Active(int entityId, Emote emote, long startNanos, boolean own, float lockYaw) {
         this.entityId = entityId;
         this.emote = emote;
         this.startNanos = startNanos;
         this.own = own;
         this.lockYaw = lockYaw;
         this.endAt = emote.loop() ? Double.POSITIVE_INFINITY : emote.duration();

         for (int i = 0; i < this.bones.length; i++) {
            this.bones[i] = emote.bone(Emote.BONE_ORDER.get(i));
         }
      }

      public double elapsed() {
         return EmoteClock.elapsed(this.startNanos);
      }

      public double endAt() {
         return this.endAt;
      }

      public boolean running() {
         return this.elapsed() < this.endAt;
      }

      public boolean animatesHead() {
         return this.bones[0] != null;
      }

      public boolean stopping() {
         return this.endAt != Double.POSITIVE_INFINITY && !this.emote.loop() ? false : this.endAt != Double.POSITIVE_INFINITY;
      }
   }

   private record Pending(UUID player, Emote emote, long receivedNanos, long elapsedMs) {
   }
}
