package com.metrofico.pulsar.api.hud.modules;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.HudModule;
import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.hud.Placement;
import com.metrofico.pulsar.api.hud.Zone;
import com.metrofico.pulsar.api.hud.scoreboard.PlainSidebarText;
import com.metrofico.pulsar.api.hud.scoreboard.Sidebar;
import com.metrofico.pulsar.api.hud.scoreboard.SidebarLayout;
import com.metrofico.pulsar.api.hud.scoreboard.SidebarSource;
import com.metrofico.pulsar.api.hud.scoreboard.SidebarStyle;
import com.metrofico.pulsar.api.hud.scoreboard.SidebarText;
import java.util.List;

public final class ScoreboardModule implements HudModule {
   public static final String ID = "scoreboard";
   private static final int TEXT_COLOR = -1;
   private static final String SCORE_SEPARATOR = ": ";
   private static final int VANILLA_RIGHT_GAP = 1;
   private final SidebarSource source;
   private Sidebar sidebar;
   private int readTick = Integer.MIN_VALUE;
   private boolean readWhileEditing;
   private Sidebar layoutSidebar;
   private SidebarStyle layoutStyle;
   private SidebarLayout layout;
   private int measuredWidth;
   private int measuredHeight;

   public ScoreboardModule(SidebarSource source) {
      this.source = source;
   }

   @Override
   public String id() {
      return "scoreboard";
   }

   @Override
   public Zone zone() {
      return ClientOptions.get().zone("scoreboard.zone", Zone.RIGHT);
   }

   @Override
   public Placement home(Canvas canvas) {
      Sidebar shown = this.displayed(canvas);
      SidebarLayout geometry = this.layout(canvas);
      if (shown != null && geometry != null) {
         int height = this.height(canvas);
         int bottom = canvas.height() / 2 + (int)(shown.rows().size() * geometry.lineHeight() * this.crispScale(canvas) / 3.0);
         int y = bottom - height;
         return new Placement(Zone.RIGHT, -1, y - (canvas.height() - height) / 2);
      } else {
         return null;
      }
   }

   @Override
   public boolean present() {
      return this.current() != null;
   }

   @Override
   public int width(Canvas canvas) {
      SidebarLayout geometry = this.layout(canvas);
      this.measuredWidth = geometry == null ? 0 : this.scaled(geometry.width(), canvas);
      return this.measuredWidth;
   }

   @Override
   public int height(Canvas canvas) {
      SidebarLayout geometry = this.layout(canvas);
      this.measuredHeight = geometry == null ? 0 : this.scaled(geometry.height(), canvas);
      return this.measuredHeight;
   }

   private int scaled(int size, Canvas canvas) {
      return (int)Math.ceil(size * this.crispScale(canvas));
   }

   private double crispScale(Canvas canvas) {
      int guiScale = Math.max(1, canvas.guiScale());
      long screenPixels = Math.max(1L, Math.round(this.scale() * guiScale));
      return (double)screenPixels / guiScale;
   }

   @Override
   public void draw(Canvas canvas, int x, int y) {
      this.refresh();
      Sidebar shown = this.displayed(canvas);
      SidebarLayout geometry = this.layout(canvas);
      if (shown != null && geometry != null) {
         SidebarStyle style = this.layoutStyle;
         float factor = (float)this.crispScale(canvas);
         Zone anchor = this.anchor();
         int width = this.scaled(geometry.width(), canvas);
         int height = this.scaled(geometry.height(), canvas);
         int alignedX = x + alignment(anchor.column(), this.measuredWidth, width);
         int alignedY = y + alignment(anchor.row(), this.measuredHeight, height);
         Runnable paint = () -> paint(canvas, shown, geometry, style, factor, alignedX, alignedY);
         if (animated(shown)) {
            paint.run();
         } else {
            canvas.cached(
               "scoreboard",
               new ScoreboardModule.CacheKey(shown, style, factor, alignedX, alignedY, canvas.width(), canvas.height()),
               alignedX,
               alignedY,
               alignedX + width,
               alignedY + height,
               paint
            );
         }
      }
   }

   private static boolean animated(Sidebar sidebar) {
      if (sidebar.title().animated()) {
         return true;
      } else {
         for (Sidebar.Row row : sidebar.rows()) {
            if (row.name().animated() || row.score().animated()) {
               return true;
            }
         }

         return false;
      }
   }

   private static void paint(Canvas canvas, Sidebar shown, SidebarLayout geometry, SidebarStyle style, float factor, int alignedX, int alignedY) {
      canvas.push();
      canvas.translate(alignedX, alignedY);
      canvas.scale(factor, factor);
      if (style.background()) {
         canvas.fill(0, 0, geometry.width(), geometry.bodyTop(), style.headerColor());
         canvas.fill(0, geometry.bodyTop(), geometry.width(), geometry.height(), style.bodyColor());
      }

      shown.title().draw(canvas, geometry.titleX(shown.title()), 1, -1, style.shadow());
      List<Sidebar.Row> rows = shown.rows();

      for (int index = 0; index < rows.size(); index++) {
         Sidebar.Row row = rows.get(index);
         int top = geometry.rowTop(index);
         row.name().draw(canvas, 2, top, -1, style.shadow());
         if (geometry.scores() && row.score().width() > 0) {
            row.score().draw(canvas, geometry.scoreX(row.score()), top, -1, style.shadow());
         }
      }

      canvas.pop();
   }

   private static int alignment(int anchorIndex, int boxSize, int actualSize) {
      if (boxSize <= 0) {
         return 0;
      } else {
         return switch (anchorIndex) {
            case 0 -> 0;
            case 1 -> (boxSize - actualSize) / 2;
            default -> boxSize - actualSize;
         };
      }
   }

   private Zone anchor() {
      ClientOptions options = ClientOptions.get();
      return Placement.parse(options.string("scoreboard.pos", null))
         .filter(Placement::free)
         .map(Placement::anchor)
         .orElseGet(() -> options.string("scoreboard.zone", null) == null ? Zone.RIGHT : this.zone());
   }

   private void refresh() {
      if (!HudModules.editing) {
         this.readTick = HudModules.TICK;
         this.readWhileEditing = false;
         this.sidebar = this.source.read();
      }
   }

   private Sidebar current() {
      if (this.readTick != HudModules.TICK || this.readWhileEditing != HudModules.editing) {
         this.readTick = HudModules.TICK;
         this.readWhileEditing = HudModules.editing;
         this.sidebar = this.source.read();
      }

      return this.sidebar;
   }

   private Sidebar displayed(Canvas canvas) {
      Sidebar real = this.current();
      return real == null && HudModules.editing ? ScoreboardModule.SampleSidebar.get(canvas) : real;
   }

   private SidebarLayout layout(Canvas canvas) {
      Sidebar shown = this.displayed(canvas);
      if (shown == null) {
         return null;
      } else {
         SidebarStyle style = SidebarStyle.fromOptions();
         if (!shown.equals(this.layoutSidebar) || !style.equals(this.layoutStyle)) {
            this.layoutSidebar = shown;
            this.layoutStyle = style;
            this.layout = SidebarLayout.of(shown, style.scores(), canvas.vlineHeight(), canvas.vwidth(": "));
         }

         return this.layout;
      }
   }

   private record CacheKey(Sidebar sidebar, SidebarStyle style, float factor, int x, int y, int guiWidth, int guiHeight) {
   }

   private static final class SampleSidebar {
      private static final int TITLE_COLOR = -171;
      private static final int SCORE_COLOR = -43691;
      private static Sidebar sample;

      static Sidebar get(Canvas canvas) {
         if (sample == null) {
            sample = new Sidebar(
               text(canvas, "PULSAR", -171),
               List.of(row(canvas, "Jugadores", "24"), row(canvas, "Monedas", "1520"), row(canvas, "Kills", "12"), row(canvas, "pulsarlauncher.com", "0"))
            );
         }

         return sample;
      }

      private static Sidebar.Row row(Canvas canvas, String name, String score) {
         return new Sidebar.Row(text(canvas, name, 0), text(canvas, score, -43691));
      }

      private static SidebarText text(Canvas canvas, String value, int color) {
         return new PlainSidebarText(value, color).measure(canvas);
      }
   }
}
