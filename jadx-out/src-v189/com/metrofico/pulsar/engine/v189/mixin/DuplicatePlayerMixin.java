package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.engine.v189.perf.ServerMoveStamp189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bdb.class})
public abstract class DuplicatePlayerMixin {
   private static final long STALE_NANOS = 1000000000L;

   @Inject(
      method = {"a(ILpk;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$dropStaleCopy(int id, pk entity, CallbackInfo ci) {
      if (entity instanceof bex && entity.aK() != null) {
         bdb self = (bdb)this;
         wn old = self.b(entity.aK());
         if (old instanceof bex && old != entity && old.F() != id) {
            long quiet = System.nanoTime() - ((ServerMoveStamp189)old).pulsar$lastServerMove();
            boolean stale = quiet >= 1000000000L;
            boolean enabled = ClientOptions.get().bool("fix.duplicatePlayers", true);
            ClientOptions.LOG
               .accept(
                  "players: duplicate "
                     + old.e_()
                     + " (old id "
                     + old.F()
                     + ", new id "
                     + id
                     + ", old quiet "
                     + quiet / 1000000L
                     + " ms) "
                     + (stale && enabled ? "removed stale copy" : (stale ? "kept (fix.duplicatePlayers off)" : "kept: old one still moves"))
               );
            if (stale && enabled) {
               self.e(old.F());
            }
         }
      }
   }
}
