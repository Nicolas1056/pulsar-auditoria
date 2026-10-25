package com.metrofico.pulsar.api.ui;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.hud.RichText;
import com.metrofico.pulsar.api.pack.PackClient;
import java.util.ArrayList;
import java.util.List;

public final class TabListView {
   private static final int TOP = 8;
   private static final int BOTTOM_MARGIN = 40;
   private final PackClient pack;
   private final NodeView renderer;
   private double scroll;
   private int contentHeight;
   private int viewportHeight;
   private boolean wasDown;
   private List<TabListView.Op> plan = List.of();
   private int cachedVersion = -1;
   private int cachedGuiW = -1;
   private int panelX;
   private int panelW;

   public TabListView(PackClient pack) {
      this.pack = pack;
      this.renderer = new NodeView(pack);
   }

   public boolean scrollable() {
      return this.wasDown && this.contentHeight > this.viewportHeight;
   }

   public void scrollBy(double amount) {
      this.scroll = Math.max(0.0, Math.min((double)(this.contentHeight - this.viewportHeight), this.scroll + amount));
   }

   public void render(Canvas g) {
      Node root = this.pack.tabList().root();
      boolean down = root != null && this.pack.active() && HudModules.data().tabKeyDown();
      if (!down) {
         if (this.wasDown) {
            this.scroll = 0.0;
         }

         this.wasDown = false;
      } else {
         this.wasDown = true;
         int version = this.pack.tabList().version();
         this.viewportHeight = g.height() - 8 - 40;
         if (version != this.cachedVersion || g.width() != this.cachedGuiW) {
            this.compile(root, g);
            this.cachedVersion = version;
            this.cachedGuiW = g.width();
         }

         int h = Math.min(this.contentHeight, this.viewportHeight);
         this.scroll = Math.max(0.0, Math.min((double)Math.max(0, this.contentHeight - this.viewportHeight), this.scroll));
         g.clip(this.panelX, 8, this.panelX + this.panelW + 4, 8 + h);
         g.push();
         g.translate(0.0F, (float)(-this.scroll));

         for (TabListView.Op op : this.plan) {
            this.draw(g, op);
         }

         g.pop();
         g.unclip();
         if (this.contentHeight > this.viewportHeight) {
            int barH = Math.max(12, (int)((long)h * h / this.contentHeight));
            int barY = 8 + (int)((h - barH) * (this.scroll / (this.contentHeight - this.viewportHeight)));
            g.fill(this.panelX + this.panelW + 1, 8, this.panelX + this.panelW + 3, 8 + h, 1090519039);
            g.fill(this.panelX + this.panelW + 1, barY, this.panelX + this.panelW + 3, barY + barH, -1056964609);
         }
      }
   }

   private void draw(Canvas g, TabListView.Op op) {
      if (op instanceof TabListView.Op.Fill f) {
         g.fill(f.x0(), f.y0(), f.x1(), f.y1(), f.argb());
      } else if (op instanceof TabListView.Op.Text t) {
         if (t.scale() == 1.0F) {
            g.vtext(t.text(), t.x(), t.y(), t.color(), t.shadow());
         } else {
            g.push();
            g.scale(t.scale(), t.scale());
            g.vtext(t.text(), Math.round(t.x() / t.scale()), Math.round(t.y() / t.scale()), t.color(), t.shadow());
            g.pop();
         }
      } else if (op instanceof TabListView.Op.Image i) {
         g.image(i.ref(), i.x(), i.y(), i.w(), i.h());
      } else if (op instanceof TabListView.Op.Generic ge) {
         this.renderer.draw(g, ge.box(), -1, -1, null);
      }
   }

   private void compile(Node root, Canvas g) {
      int guiWidth = g.width();
      Layout layout = NodeView.layout();
      int[] size = layout.measure(root);
      this.panelW = Math.min(size[0], guiWidth - 40);
      this.contentHeight = size[1];
      this.panelX = (guiWidth - this.panelW) / 2;
      List<Layout.Box> boxes = layout.layout(root, this.panelX, 8, this.panelW, this.contentHeight);
      List<TabListView.Op> out = new ArrayList<>(boxes.size() * 2);

      for (Layout.Box box : boxes) {
         Node n = box.node();
         int bg = n.color("bg", 0);
         if (n.type() != Node.Type.BUTTON && bg >>> 24 != 0) {
            out.add(new TabListView.Op.Fill(box.x(), box.y(), box.x() + box.w(), box.y() + box.h(), bg));
         }

         int border = n.color("border", 0);
         if (border >>> 24 != 0) {
            out.add(new TabListView.Op.Fill(box.x(), box.y(), box.x() + box.w(), box.y() + 1, border));
            out.add(new TabListView.Op.Fill(box.x(), box.y() + box.h() - 1, box.x() + box.w(), box.y() + box.h(), border));
            out.add(new TabListView.Op.Fill(box.x(), box.y(), box.x() + 1, box.y() + box.h(), border));
            out.add(new TabListView.Op.Fill(box.x() + box.w() - 1, box.y(), box.x() + box.w(), box.y() + box.h(), border));
         }

         switch (n.type()) {
            case TEXT:
               List<RichText.Span> spans = RichText.parse(n.text(), 0);
               int base = n.color("color", -855310);
               int width = 0;

               for (RichText.Span span : spans) {
                  width += g.vwidth(span.text());
               }

               double sc = NodeView.scale(n);
               int tw = (int)Math.ceil(width * sc);
               String align = n.string("align", "left");
               int tx = align.equals("center") ? box.x() + (box.w() - tw) / 2 : (align.equals("right") ? box.x() + box.w() - tw : box.x());
               boolean shadow = n.bool("shadow", true);
               int cx = 0;

               for (RichText.Span span : spans) {
                  if (!span.text().isEmpty()) {
                     out.add(new TabListView.Op.Text(span.text(), RichText.colorOf(span, base), tx + (int)Math.round(cx * sc), box.y(), shadow, (float)sc));
                     cx += g.vwidth(span.text());
                  }
               }
               break;
            case IMAGE:
               out.add(new TabListView.Op.Image(n.string("image", ""), box.x(), box.y(), box.w(), box.h()));
            case BOX:
            case ROW:
            case COLUMN:
            case GRID:
            case SPACER:
               break;
            default:
               out.add(new TabListView.Op.Generic(box));
         }
      }

      this.plan = out;
   }

   private sealed interface Op permits TabListView.Op.Fill, TabListView.Op.Text, TabListView.Op.Image, TabListView.Op.Generic {
      public record Fill(int x0, int y0, int x1, int y1, int argb) implements TabListView.Op {
      }

      public record Generic(Layout.Box box) implements TabListView.Op {
      }

      public record Image(String ref, int x, int y, int w, int h) implements TabListView.Op {
      }

      public record Text(String text, int color, int x, int y, boolean shadow, float scale) implements TabListView.Op {
      }
   }
}
