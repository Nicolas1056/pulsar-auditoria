package com.metrofico.pulsar.api.hud.scoreboard;

public record SidebarLayout(int width, int height, int lineHeight, int contentWidth, boolean scores) {
   public static final int PADDING = 2;
   public static final int HEADER_GAP = 1;

   public static SidebarLayout of(Sidebar sidebar, boolean scores, int lineHeight, int scoreGap) {
      int contentWidth = sidebar.title().width();

      for (Sidebar.Row row : sidebar.rows()) {
         int rowWidth = row.name().width();
         if (scores && row.score().width() > 0) {
            rowWidth += scoreGap + row.score().width();
         }

         contentWidth = Math.max(contentWidth, rowWidth);
      }

      int width = contentWidth + 4;
      int height = lineHeight + 1 + sidebar.rows().size() * lineHeight;
      return new SidebarLayout(width, height, lineHeight, contentWidth, scores);
   }

   public int bodyTop() {
      return this.lineHeight;
   }

   public int rowTop(int index) {
      return this.lineHeight + 1 + index * this.lineHeight;
   }

   public int titleX(SidebarText title) {
      return 2 + (this.contentWidth - title.width()) / 2;
   }

   public int scoreX(SidebarText score) {
      return this.width - 2 - score.width();
   }
}
