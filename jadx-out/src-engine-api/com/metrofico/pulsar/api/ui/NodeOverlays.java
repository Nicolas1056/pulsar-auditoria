package com.metrofico.pulsar.api.ui;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hud.HudRenderer;
import com.metrofico.pulsar.api.hud.PanelLayout;
import com.metrofico.pulsar.api.pack.PackClient;
import java.util.ArrayList;
import java.util.List;

public final class NodeOverlays implements HudRenderer.Extras {
   private final PackClient pack;
   private final NodeView view;

   public NodeOverlays(PackClient pack) {
      this.pack = pack;
      this.view = new NodeView(pack);
   }

   @Override
   public List<PanelLayout.Widget> widgets(Canvas g) {
      List<PanelLayout.Widget> out = new ArrayList<>();
      Layout layout = NodeView.layout();

      for (ScreenDefinition o : this.pack.screens().overlays()) {
         int[] size = layout.measure(o.root());
         out.add(new PanelLayout.Widget("overlay:" + o.id(), o.zone(), Math.max(1, size[0]), Math.max(1, size[1])));
      }

      return out;
   }

   @Override
   public void draw(Canvas g, PanelLayout.Box box) {
      this.pack.screens().get(box.widgetId().substring(8)).ifPresent(o -> {
         int[] off = HudRenderer.slide(o.zone(), o.progress(System.currentTimeMillis()), box, g.width(), g.height());

         for (Layout.Box b : NodeView.layout().layout(o.root(), box.x() + off[0], box.y() + off[1], box.width(), box.height())) {
            this.view.draw(g, b, -1, -1, null);
         }
      });
   }
}
