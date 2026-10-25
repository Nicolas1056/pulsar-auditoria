package com.metrofico.pulsar.api.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

public final class Layout {
   public static final int BUTTON_PAD_X = 8;
   public static final int BUTTON_PAD_Y = 5;
   public static final int DEFAULT_IMAGE = 64;
   public static final int PROGRESS_HEIGHT = 8;
   private final ToIntFunction<String> textWidth;
   private final int lineHeight;

   public Layout(ToIntFunction<String> textWidth, int lineHeight) {
      this.textWidth = textWidth;
      this.lineHeight = lineHeight;
   }

   public List<Layout.Box> layout(Node root, int x, int y, int w, int h) {
      List<Layout.Box> out = new ArrayList<>();
      this.place(root, x, y, w, h, out);
      return out;
   }

   public int[] measure(Node n) {
      int[] content = switch (n.type()) {
         case TEXT -> new int[]{(int)Math.ceil(this.textWidth.applyAsInt(n.text()) * scale(n)), (int)Math.ceil(this.lineHeight * scale(n))};
         case BUTTON -> new int[]{(int)Math.ceil(this.textWidth.applyAsInt(n.text()) * scale(n)) + 16, (int)Math.ceil(this.lineHeight * scale(n)) + 10};
         case IMAGE -> new int[]{64, 64};
         case PROGRESS -> new int[]{100, 8};
         case SPACER -> new int[]{0, 0};
         case MAP -> new int[]{200, 150};
         case GRID -> this.measureGrid(n);
         case BOX, ROW, COLUMN -> this.measureContainer(n);
      };
      Node.Size sw = n.size("w");
      Node.Size sh = n.size("h");
      int w = sw != null && sw.kind() == Node.Size.Kind.PX ? (int)sw.value() : content[0];
      int h = sh != null && sh.kind() == Node.Size.Kind.PX ? (int)sh.value() : content[1];
      return new int[]{w, h};
   }

   private int[] measureContainer(Node n) {
      int pad = n.integer("pad", 0);
      int gap = n.integer("gap", 0);
      int w = 0;
      int h = 0;
      int visible = 0;

      for (Node c : n.children()) {
         if (c.visible()) {
            int[] m = this.measure(c);
            int mw = m[0];
            int mh = m[1];
            switch (n.type()) {
               case ROW:
                  w += mw;
                  h = Math.max(h, mh);
                  break;
               case COLUMN:
                  w = Math.max(w, mw);
                  h += mh;
                  break;
               default:
                  w = Math.max(w, mw);
                  h = Math.max(h, mh);
            }

            visible++;
         }
      }

      if (visible > 1 && n.type() != Node.Type.BOX) {
         if (n.type() == Node.Type.ROW) {
            w += gap * (visible - 1);
         } else {
            h += gap * (visible - 1);
         }
      }

      return new int[]{w + pad * 2, h + pad * 2};
   }

   private int[] measureGrid(Node n) {
      List<Node> kids = n.children().stream().filter(Node::visible).toList();
      int pad = n.integer("pad", 0);
      int gap = n.integer("gap", 4);
      int cols = Math.max(1, Math.min(12, n.integer("cols", 1)));
      if (kids.isEmpty()) {
         return new int[]{pad * 2, pad * 2};
      } else {
         int rows = (kids.size() + cols - 1) / cols;
         int cellH = 0;
         int[] colW = new int[cols];

         for (int i = 0; i < kids.size(); i++) {
            int[] m = this.measure(kids.get(i));
            int col = i / rows;
            colW[col] = Math.max(colW[col], m[0]);
            cellH = Math.max(cellH, m[1]);
         }

         int w = 0;
         int used = Math.min(cols, kids.size() == 0 ? 0 : (kids.size() + rows - 1) / rows);

         for (int c = 0; c < used; c++) {
            w += colW[c];
         }

         w += gap * Math.max(0, used - 1);
         int h = rows * cellH + gap * (rows - 1);
         return new int[]{w + pad * 2, h + pad * 2};
      }
   }

   private void placeGrid(Node n, int x, int y, int w, int h, List<Layout.Box> out) {
      List<Node> kids = n.children().stream().filter(Node::visible).toList();
      int pad = n.integer("pad", 0);
      int gap = n.integer("gap", 4);
      int cols = Math.max(1, Math.min(12, n.integer("cols", 1)));
      if (!kids.isEmpty()) {
         int rows = (kids.size() + cols - 1) / cols;
         int cellH = 0;
         int[] colW = new int[cols];

         for (int i = 0; i < kids.size(); i++) {
            int[] m = this.measure(kids.get(i));
            colW[i / rows] = Math.max(colW[i / rows], m[0]);
            cellH = Math.max(cellH, m[1]);
         }

         int used = Math.min(cols, (kids.size() + rows - 1) / rows);
         int natural = 0;

         for (int c = 0; c < used; c++) {
            natural += colW[c];
         }

         int extra = Math.max(0, w - pad * 2 - gap * (used - 1) - natural) / Math.max(1, used);
         int[] colX = new int[used];
         int cx = x + pad;

         for (int c = 0; c < used; c++) {
            colX[c] = cx;
            cx += colW[c] + extra + gap;
         }

         for (int i = 0; i < kids.size(); i++) {
            int col = i / rows;
            int row = i % rows;
            this.place(kids.get(i), colX[col], y + pad + row * (cellH + gap), colW[col] + extra, cellH, out);
         }
      }
   }

   private static double scale(Node n) {
      return Math.max(0.5, Math.min(3.0, n.number("scale", 1.0)));
   }

   private void place(Node n, int x, int y, int w, int h, List<Layout.Box> out) {
      out.add(new Layout.Box(n, x, y, w, h));
      if (n.type() == Node.Type.GRID) {
         this.placeGrid(n, x, y, w, h, out);
      } else if (n.type() == Node.Type.BOX || n.type() == Node.Type.ROW || n.type() == Node.Type.COLUMN) {
         int pad = n.integer("pad", 0);
         int gap = n.integer("gap", 0);
         int cx = x + pad;
         int cy = y + pad;
         int cw = Math.max(0, w - pad * 2);
         int ch = Math.max(0, h - pad * 2);
         List<Node> kids = n.children().stream().filter(Node::visible).toList();
         if (!kids.isEmpty()) {
            boolean row = n.type() == Node.Type.ROW;
            if (n.type() == Node.Type.BOX) {
               for (Node k : kids) {
                  int kw = resolve(k, "w", this.measure(k)[0], cw, cw);
                  int kh = resolve(k, "h", this.measure(k)[1], ch, ch);
                  int kx = cx + offset(k.string("align", n.string("align", "start")), cw, kw);
                  int ky = cy + offset(k.string("valign", n.string("valign", "start")), ch, kh);
                  this.place(k, kx, ky, kw, kh, out);
               }
            } else {
               int mainTotal = row ? cw : ch;
               int crossTotal = row ? ch : cw;
               int[] main = new int[kids.size()];
               int fixed = 0;
               int fills = 0;

               for (int i = 0; i < kids.size(); i++) {
                  Node k = kids.get(i);
                  Node.Size s = k.size(row ? "w" : "h");
                  if (s != null && s.kind() == Node.Size.Kind.FILL) {
                     fills++;
                     main[i] = -1;
                  } else {
                     main[i] = resolve(k, row ? "w" : "h", this.measure(k)[row ? 0 : 1], mainTotal, -1);
                     fixed += main[i];
                  }
               }

               int gaps = gap * (kids.size() - 1);
               int remaining = Math.max(0, mainTotal - fixed - gaps);

               for (int ix = 0; ix < kids.size(); ix++) {
                  if (main[ix] < 0) {
                     main[ix] = remaining / fills;
                  }
               }

               int used = fixed + gaps + (fills > 0 ? remaining : 0);
               String justify = n.string("justify", "start");
               int cursor = (row ? cx : cy) + (justify.equals("center") ? (mainTotal - used) / 2 : (justify.equals("end") ? mainTotal - used : 0));
               int between = justify.equals("between") && kids.size() > 1 ? (mainTotal - used) / (kids.size() - 1) : 0;
               String align = n.string("align", "start");

               for (int ixx = 0; ixx < kids.size(); ixx++) {
                  Node k = kids.get(ixx);
                  int cross = resolve(k, row ? "h" : "w", this.measure(k)[row ? 1 : 0], crossTotal, crossTotal);
                  int off = offset(k.string("align", align), crossTotal, cross);
                  if (row) {
                     this.place(k, cursor, cy + off, main[ixx], cross, out);
                  } else {
                     this.place(k, cx + off, cursor, cross, main[ixx], out);
                  }

                  cursor += main[ixx] + gap + between;
               }
            }
         }
      }
   }

   private static int resolve(Node k, String key, int content, int total, int fillValue) {
      Node.Size s = k.size(key);
      if (s == null) {
         return content;
      } else {
         return switch (s.kind()) {
            case PX -> (int)s.value();
            case PERCENT -> (int)Math.round(total * s.value() / 100.0);
            case FILL -> fillValue < 0 ? total : fillValue;
         };
      }
   }

   private static int offset(String align, int total, int size) {
      return switch (align) {
         case "center" -> Math.max(0, (total - size) / 2);
         case "end" -> Math.max(0, total - size);
         default -> 0;
      };
   }

   public record Box(Node node, int x, int y, int w, int h) {
      public boolean contains(double px, double py) {
         return px >= this.x && px < this.x + this.w && py >= this.y && py < this.y + this.h;
      }
   }
}
