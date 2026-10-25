package com.metrofico.pulsar.api.hud.scoreboard;

@FunctionalInterface
public interface SidebarSource {
   Sidebar read();
}
