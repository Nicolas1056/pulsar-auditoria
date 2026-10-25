package com.metrofico.pulsar.engine.v189.hud.scoreboard;

import com.metrofico.pulsar.api.hud.scoreboard.MeasuredTexts;
import com.metrofico.pulsar.api.hud.scoreboard.Sidebar;
import com.metrofico.pulsar.api.hud.scoreboard.SidebarSource;
import com.metrofico.pulsar.api.hud.scoreboard.Sidebar.Row;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Sidebar189 implements SidebarSource {
   private static final int SIDEBAR_SLOT = 1;
   private static final int MAX_ROWS = 15;
   private static final String HIDDEN_ENTRY_PREFIX = "#";
   private static final String SCORE_FORMAT = "§c";
   private final MeasuredTexts<String, LegacySidebarText> texts = new MeasuredTexts(raw -> new LegacySidebarText(raw, ave.A().k.a(raw)));

   public Sidebar read() {
      ave client = ave.A();
      if (client.f != null && client.h != null) {
         auo scoreboard = client.f.Z();
         auk objective = scoreboard.a(1);
         if (objective == null) {
            return null;
         } else {
            List<Row> rows = new ArrayList<>();

            for (aum score : topScores(scoreboard, objective)) {
               String owner = score.e();
               String name = aul.a(scoreboard.h(owner), owner);
               String value = "§c" + score.c();
               rows.add(new Row(this.text(name), this.text(value)));
            }

            return new Sidebar(this.text(objective.d()), rows);
         }
      } else {
         return null;
      }
   }

   private static List<aum> topScores(auo scoreboard, auk objective) {
      List<aum> visible = new ArrayList<>();

      for (aum score : scoreboard.i(objective)) {
         String owner = score.e();
         if (owner != null && !owner.startsWith("#")) {
            visible.add(score);
         }
      }

      List<aum> top = new ArrayList<>(visible.subList(Math.max(0, visible.size() - 15), visible.size()));
      Collections.reverse(top);
      return top;
   }

   private LegacySidebarText text(String value) {
      return (LegacySidebarText)this.texts.get(value);
   }
}
