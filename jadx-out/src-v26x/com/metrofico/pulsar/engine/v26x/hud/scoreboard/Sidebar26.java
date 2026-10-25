package com.metrofico.pulsar.engine.v26x.hud.scoreboard;

import com.metrofico.pulsar.api.hud.scoreboard.MeasuredTexts;
import com.metrofico.pulsar.api.hud.scoreboard.Sidebar;
import com.metrofico.pulsar.api.hud.scoreboard.SidebarSource;
import com.metrofico.pulsar.api.hud.scoreboard.Sidebar.Row;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.network.chat.numbers.StyledFormat;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerScoreEntry;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.TeamColor;

public final class Sidebar26 implements SidebarSource {
   private static final int MAX_ROWS = 15;
   private static final Comparator<PlayerScoreEntry> VANILLA_ORDER = Comparator.comparing(PlayerScoreEntry::value)
      .reversed()
      .thenComparing(PlayerScoreEntry::owner, String.CASE_INSENSITIVE_ORDER);
   private final MeasuredTexts<Component, ComponentSidebarText> texts = new MeasuredTexts(ComponentSidebarText::of);

   public Sidebar read() {
      Minecraft client = Minecraft.getInstance();
      if (client.level != null && client.player != null) {
         Scoreboard scoreboard = client.level.getScoreboard();
         Objective objective = displayedObjective(scoreboard, client.player.getScoreboardName());
         if (objective == null) {
            return null;
         } else {
            NumberFormat numberFormat = objective.numberFormatOrDefault(StyledFormat.SIDEBAR_DEFAULT);
            List<Row> rows = scoreboard.listPlayerScores(objective)
               .stream()
               .filter(entry -> !entry.isHidden())
               .sorted(VANILLA_ORDER)
               .limit(15L)
               .map(
                  entry -> new Row(
                     this.texts.get(PlayerTeam.formatNameForTeam(scoreboard.getPlayersTeam(entry.owner()), entry.ownerName())),
                     this.texts.get(entry.formatValue(numberFormat))
                  )
               )
               .toList();
            return new Sidebar(this.texts.get(objective.getDisplayName()), rows);
         }
      } else {
         return null;
      }
   }

   private static Objective displayedObjective(Scoreboard scoreboard, String playerName) {
      PlayerTeam team = scoreboard.getPlayersTeam(playerName);
      if (team != null) {
         DisplaySlot teamSlot = team.getColor().<DisplaySlot>map(TeamColor::displaySlot).orElse(null);
         if (teamSlot != null && scoreboard.getDisplayObjective(teamSlot) != null) {
            return scoreboard.getDisplayObjective(teamSlot);
         }
      }

      return scoreboard.getDisplayObjective(DisplaySlot.SIDEBAR);
   }
}
