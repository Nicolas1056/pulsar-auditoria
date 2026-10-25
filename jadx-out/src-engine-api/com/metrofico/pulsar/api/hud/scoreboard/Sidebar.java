package com.metrofico.pulsar.api.hud.scoreboard;

import java.util.List;

public record Sidebar(SidebarText title, List<Sidebar.Row> rows) {
   public Sidebar(SidebarText title, List<Sidebar.Row> rows) {
      rows = List.copyOf(rows);
      this.title = title;
      this.rows = rows;
   }

   public record Row(SidebarText name, SidebarText score) {
   }
}
