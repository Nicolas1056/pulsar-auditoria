package com.metrofico.pulsar.engine.v189.pack;

import com.metrofico.pulsar.api.pack.ConsentView;
import com.metrofico.pulsar.api.pack.PackManifest;
import com.metrofico.pulsar.api.pack.ServerRecord;
import com.metrofico.pulsar.api.pack.PackSession.Consent;
import com.metrofico.pulsar.engine.v189.gfx.Canvas189;
import java.util.function.Consumer;

public final class ConsentScreen189 extends axu {
   private final ConsentView view;

   public ConsentScreen189(ServerRecord server, PackManifest manifest, Consumer<Consent> onDecision) {
      this.view = new ConsentView(server, manifest, onDecision, () -> ave.A().a(null));
   }

   public void a(int mouseX, int mouseY, float tickDelta) {
      Canvas189 g = new Canvas189();
      this.view.render(g, mouseX, mouseY);
      g.flush();
   }

   protected void a(int x, int y, int button) {
      this.view.mouseClicked(x, y, button);
   }

   protected void a(char character, int code) {
      if (code == 1) {
         this.view.choose(Consent.NONE);
      }
   }

   public boolean d() {
      return false;
   }
}
