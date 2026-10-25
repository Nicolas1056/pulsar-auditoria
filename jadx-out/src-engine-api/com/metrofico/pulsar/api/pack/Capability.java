package com.metrofico.pulsar.api.pack;

import java.util.EnumSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public enum Capability {
   UI_PANELS("ui.panels", "Paneles", "Mostrar paneles con información en la pantalla"),
   UI_SCOREBOARD("ui.scoreboard", "Marcador", "Sustituir el marcador por uno propio"),
   UI_TABLIST("ui.tablist", "Lista de jugadores", "Dar formato a la lista de jugadores"),
   UI_BOSSBAR("ui.bossbar", "Barras", "Mostrar barras de progreso propias"),
   UI_ACTIONBAR("ui.actionbar", "Avisos", "Mostrar avisos sobre la barra de objetos"),
   UI_TITLES("ui.titles", "Títulos", "Mostrar títulos en pantalla"),
   UI_COMPASS("ui.compass", "Brújula", "Mostrar una brújula con puntos de interés del servidor"),
   UI_MINIMAP("ui.minimap", "Minimapa", "Mostrar un minimapa enviado por el servidor"),
   UI_HOTBAR("ui.hotbar", "Barra de objetos", "Decorar la barra de objetos (etiquetas y tiempos)"),
   UI_HUD_TOGGLE("ui.hud.toggle", "Interfaz", "Ocultar partes de la interfaz normal mientras juegas aquí"),
   UI_MENU("ui.menu", "Menús", "Abrir menús propios del servidor"),
   UI_MAP("ui.map", "Mapa", "Abrir un mapa del servidor con una tecla"),
   UI_NOTIFICATIONS("ui.notifications", "Notificaciones", "Mostrar avisos emergentes"),
   UI_CHAT("ui.chat", "Chat", "Dar formato al chat (pestañas, insignias)"),
   UI_INVENTORY_SKIN("ui.inventory.skin", "Inventarios", "Cambiar el aspecto de los inventarios"),
   UI_CROSSHAIR("ui.crosshair", "Retícula", "Cambiar el aspecto de la retícula"),
   CAMERA_CINEMATIC("camera.cinematic", "Cinemáticas", "Mover la cámara en escenas (tú no te mueves mientras tanto)"),
   CAMERA_MODE("camera.mode", "Modo de cámara", "Fijar la vista (tercera persona, lateral, cenital)"),
   CAMERA_SHAKE("camera.shake", "Temblor", "Agitar la cámara en momentos concretos"),
   CAMERA_FOV("camera.fov", "Campo de visión", "Cambiar suavemente el campo de visión"),
   CAMERA_SPECTATE("camera.spectate", "Espectador", "Guiar la cámara cuando eres espectador"),
   WORLD_MODELS("world.models", "Modelos", "Mostrar objetos y figuras decorativas nuevas"),
   WORLD_BLOCKS_VISUAL("world.blocks.visual", "Bloques", "Mostrar bloques con aspecto propio"),
   WORLD_ITEMS_VISUAL("world.items.visual", "Objetos", "Mostrar objetos con aspecto propio"),
   WORLD_ANIMATIONS("world.animations", "Animaciones", "Reproducir animaciones y emotes"),
   WORLD_NPC("world.npc", "Personajes", "Mostrar personajes del servidor"),
   WORLD_HOLOGRAMS("world.holograms", "Textos flotantes", "Mostrar textos e imágenes flotantes"),
   WORLD_MARKERS("world.markers", "Marcadores", "Mostrar puntos de control y objetivos en el mundo"),
   WORLD_ZONES("world.zones", "Zonas", "Resaltar zonas del mapa"),
   WORLD_SKYBOX("world.skybox", "Cielo", "Cambiar el cielo y la niebla"),
   WORLD_WEATHER_VISUAL("world.weather.visual", "Clima", "Mostrar efectos de clima propios"),
   FX_PARTICLES("fx.particles", "Partículas", "Mostrar partículas propias"),
   FX_TRAILS("fx.trails", "Estelas", "Mostrar estelas"),
   FX_SCREEN("fx.screen", "Efectos de pantalla", "Mostrar efectos de pantalla (tintes, destellos)"),
   FX_LIGHTING("fx.lighting", "Luces", "Mostrar luces de colores"),
   FX_SHADERS_LIMITED("fx.shaders.limited", "Materiales", "Aplicar efectos de material predefinidos"),
   AUDIO_SOUNDS("audio.sounds", "Sonidos", "Reproducir sonidos propios"),
   AUDIO_MUSIC("audio.music", "Música", "Reproducir música de fondo"),
   AUDIO_AMBIENCE("audio.ambience", "Ambiente", "Reproducir sonido ambiente"),
   XP_KEYBINDS("xp.keybinds", "Teclas", "Registrar teclas (puedes cambiarlas)"),
   XP_COMBAT("xp.combat", "Estilo de combate", "Adaptar la presentación del combate al estilo del servidor (clásico o moderno)"),
   XP_LANGUAGE("xp.language", "Idioma", "Mostrar textos en tu idioma"),
   XP_TUTORIAL("xp.tutorial", "Ayuda", "Mostrar indicaciones de ayuda"),
   XP_LOADING("xp.loading", "Pantalla de carga", "Mostrar pantallas de carga propias"),
   XP_EMOTES("xp.emotes", "Emotes", "Mostrar tus emotes a los demás");

   public static final Set<Capability> INFORMATIVE = EnumSet.of(
      UI_PANELS, UI_SCOREBOARD, UI_TABLIST, UI_BOSSBAR, UI_ACTIONBAR, UI_TITLES, UI_NOTIFICATIONS, UI_CHAT, AUDIO_SOUNDS, XP_LANGUAGE, XP_TUTORIAL, XP_COMBAT
   );
   private final String id;
   private final String title;
   private final String description;

   private Capability(String id, String title, String description) {
      this.id = id;
      this.title = title;
      this.description = description;
   }

   public String id() {
      return this.id;
   }

   public String title() {
      return this.title;
   }

   public String description() {
      return this.description;
   }

   public String domain() {
      return this.id.substring(0, this.id.indexOf(46));
   }

   public static Optional<Capability> byId(String id) {
      String n = id == null ? "" : id.toLowerCase(Locale.ROOT);

      for (Capability c : values()) {
         if (c.id.equals(n)) {
            return Optional.of(c);
         }
      }

      return Optional.empty();
   }
}
