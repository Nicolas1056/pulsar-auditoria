package com.metrofico.pulsar.api.hub;

import com.metrofico.pulsar.api.hud.HudDefaults;
import com.metrofico.pulsar.api.hud.Zone;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public final class HubOptions {
   public static final String[] CATEGORIES = new String[]{"Combate", "Interfaz", "Juego", "World", "Rendimiento", "Teclas", "Acerca"};
   private static final List<HubOption> ALL = new ArrayList<>(
      List.of(
         HubOption.segmented(
            "pvp.style",
            "Combate",
            "PvP",
            "Golpes como 1.8: sin indicador, recarga ni barrido.",
            "swords",
            new String[]{"Clásico", "Moderno"},
            new String[]{"classic", "modern"}
         ),
         HubOption.module(
            "fullbright",
            "Combate",
            "Full Brightness",
            "El mundo a plena luz, como con visión nocturna: cuevas, noche y agua sin oscuridad.",
            "sun",
            new Param.Range("fullbright.intensity", "Intensidad", 10.0, 100.0, 5.0, 100.0, " %", null)
         ),
         HubOption.module(
            "crosshair",
            "Combate",
            "Crosshair",
            "Cruz propia en vez de la vanilla: forma, tamaño y color.",
            "crosshair",
            new Param.Choice(
               "crosshair.style",
               "Estilo",
               new String[]{"cross", "dot", "cross-dot", "circle", "none"},
               new String[]{"Cruz", "Punto", "Cruz+punto", "Círculo", "Invisible"},
               "cross"
            ),
            new Param.Range("crosshair.size", "Tamaño", 1.0, 300.0, 1.0, 5.0, " px", null),
            new Param.Range("crosshair.thickness", "Grosor", 1.0, 40.0, 1.0, 1.0, " px", null),
            new Param.Range("crosshair.gap", "Separación", -40.0, 200.0, 1.0, 1.0, " px", null),
            new Param.Range("crosshair.dotSize", "Punto central", 0.0, 40.0, 1.0, 0.0, " px", null),
            new Param.Toggle("crosshair.tstyle", "Estilo T (sin línea superior)", false),
            new Param.Color("crosshair.color", "Color", -1),
            new Param.Range("crosshair.opacity", "Opacidad", 1.0, 100.0, 1.0, 100.0, " %", null),
            new Param.Toggle("crosshair.outline", "Contorno", true)
         ),
         HubOption.module(
            "cps",
            "Combate",
            "CPS",
            "Clics por segundo, izquierdo y derecho.",
            "mouse-pointer-click",
            new Param.ZonePick("cps.zone", "Zona", HudDefaults.zone("cps", null)),
            new Param.Toggle("cps.right", "Botón derecho", true),
            new Param.Choice("cps.format", "Formato", new String[]{"short", "labels"}, new String[]{"12 | 8 CPS", "12 IZQ | 8 DER"}, "short"),
            new Param.Toggle("cps.background", "Fondo", true),
            new Param.Range("cps.opacity", "Opacidad del fondo", 0.0, 100.0, 1.0, 40.0, " %", null),
            new Param.Range("cps.scale", "Escala", 0.5, 6.0, 0.25, HudDefaults.scale("cps"))
         ),
         HubOption.module(
            "effects",
            "Interfaz",
            "Efectos de poción",
            "Iconos con nombre, nivel y tiempo, donde tú quieras.",
            "flask-conical",
            new Param.ZonePick("effects.zone", "Zona", HudDefaults.zone("effects", null)),
            new Param.Choice("effects.layout", "Disposición", new String[]{"vertical", "horizontal"}, new String[]{"Vertical", "Horizontal"}, "vertical"),
            new Param.Range("effects.scale", "Escala", 0.5, 6.0, 0.25, HudDefaults.scale("effects")),
            new Param.Toggle("effects.name", "Nombre", true),
            new Param.Toggle("effects.time", "Tiempo", true),
            new Param.Toggle("effects.level", "Nivel", true),
            new Param.Toggle("effects.background", "Fondo", true),
            new Param.Range("effects.opacity", "Opacidad del fondo", 0.0, 100.0, 1.0, 40.0, " %", null),
            new Param.Toggle("effects.border", "Borde", false),
            new Param.Choice("effects.textWeight", "Grosor del texto", new String[]{"normal", "bold"}, new String[]{"Normal", "Grueso"}, "normal"),
            new Param.Toggle("effects.blink", "Parpadeo al acabar", true)
         ),
         HubOption.module(
            "armor",
            "Interfaz",
            "Estado de armadura",
            "Casco, peto, pantalón y botas con su durabilidad.",
            "shield",
            new Param.ZonePick("armor.zone", "Zona", HudDefaults.zone("armor", null)),
            new Param.Choice("armor.layout", "Disposición", new String[]{"vertical", "horizontal"}, new String[]{"Vertical", "Horizontal"}, "vertical"),
            new Param.Choice(
               "armor.durability", "Durabilidad", new String[]{"number", "percent", "bar"}, new String[]{"Número", "Porcentaje", "Barra"}, "number"
            ),
            new Param.Toggle("armor.colorize", "Colorear por desgaste", true),
            new Param.Toggle("armor.hands", "Mostrar manos", false),
            new Param.Toggle("armor.hideEmpty", "Ocultar huecos vacíos", true),
            new Param.Range("armor.textSize", "Tamaño del texto", 50.0, 100.0, 5.0, 75.0, " %", null),
            new Param.Toggle("armor.background", "Fondo", true),
            new Param.Range("armor.opacity", "Opacidad del fondo", 0.0, 100.0, 1.0, 40.0, " %", null),
            new Param.Range("armor.scale", "Escala", 0.5, 6.0, 0.25, HudDefaults.scale("armor"))
         ),
         HubOption.module(
            "scoreboard",
            "Interfaz",
            "Scoreboard",
            "El marcador lateral del servidor, movible y a tu gusto: fondo, cabecera, números.",
            "layout",
            new Param.ZonePick("scoreboard.zone", "Zona", Zone.RIGHT),
            new Param.Toggle("scoreboard.background", "Fondo", true),
            new Param.Range("scoreboard.opacity", "Opacidad del fondo", 0.0, 100.0, 1.0, 31.0, " %", null),
            new Param.Toggle("scoreboard.darkHeader", "Cabecera más oscura", false),
            new Param.Toggle("scoreboard.scores", "Números de la derecha", true),
            new Param.Toggle("scoreboard.shadow", "Sombra del texto", false),
            new Param.Range("scoreboard.scale", "Escala", 0.5, 6.0, 0.25, 1.0)
         ),
         HubOption.module(
            "nametag",
            "Interfaz",
            "Nametag",
            "Tu propio nombre sobre la cabeza en tercera persona (F5) y en el inventario, con el prefijo y el sufijo de tu equipo, como lo ven los demás.",
            "layout"
         ),
         HubOption.module(
            "pulsarMark",
            "Interfaz",
            "Marca de Pulsar",
            "Un latido junto al nombre de quien juega con Pulsar, en el tab y sobre la cabeza. Funciona en cualquier servidor.",
            "zap",
            new Param.Toggle("pulsarMark.tab", "En el tab", true),
            new Param.Toggle("pulsarMark.nametag", "Sobre la cabeza", true),
            new Param.Toggle("pulsarMark.visible", "Que los demás vean que uso Pulsar", true)
         ),
         HubOption.module(
            "legit",
            "Interfaz",
            "Legit Module",
            "«Verificado», «En observación» o «Sin verificar» en los servidores que piden el pase de Pulsar (spec 0047). Las señales de juego solo salen con tu permiso, servidor a servidor (launcher · Ajustes · Privacidad).",
            "layout",
            new Param.Toggle("legit.sensors", "Medir las señales en los servidores con los que las compartes", true)
         ),
         HubOption.module(
            "emotes",
            "Interfaz",
            "Emotes",
            "Rueda de emotes con su tecla (G de serie): tus emotes y los de la carpeta dev; los demás te ven si tienen Pulsar.",
            "zap",
            new Param.KeyBind("emotes.key", "Tecla", "emotes"),
            new Param.Choice(
               "emotes.camera",
               "Cámara al hacer un emote",
               new String[]{"emote", "front", "back", "none"},
               new String[]{"La del emote", "De frente", "De espaldas", "No cambiar"},
               "emote"
            ),
            new Param.Toggle("emotes.others", "Ver los emotes de otros jugadores", true),
            new Param.Toggle("emotes.sound", "Sonido de los emotes", true)
         ),
         HubOption.module(
            "zoom",
            "Juego",
            "Zoom",
            "Mantén su tecla (C de serie) para acercar, como OptiFine; ajústalo a tu gusto.",
            "search",
            new Param.KeyBind("zoom.key", "Tecla", "zoom"),
            new Param.Range("zoom.factor", "Aumento", 1.5, 10.0, 0.5, 3.0, "×", null),
            new Param.Range("zoom.sensitivity", "Sensibilidad durante el zoom", 10.0, 100.0, 5.0, 80.0, " %", null),
            new Param.Toggle("zoom.scroll", "Rueda ajusta el aumento", true),
            new Param.Toggle("zoom.cinematic", "Cámara suave durante el zoom", true),
            new Param.Toggle("zoom.smooth", "Transición suave", true),
            new Param.Range("zoom.duration", "Duración de la transición", 50.0, 500.0, 10.0, 150.0, " ms", null)
         ),
         HubOption.module(
            "visuals17",
            "Juego",
            "1.7 Visuals",
            "Animaciones y comportamiento de la 1.7 en 1.8.9. Todo cambia solo lo que ves en tu pantalla, salvo «Usar con el clic izquierdo pulsado».",
            "layout",
            new Param.Toggle("visuals17.armorHit", "Armor Hit Animation (la armadura se tiñe al recibir daño)", true),
            new Param.Color("visuals17.armorColor", "Hit Armor Color", 1308557312),
            new Param.Toggle("visuals17.blockHit", "Block Hitting (el golpe se ve con la espada bloqueando)", true),
            new Param.Toggle("visuals17.potionDrinking", "Potion Drinking (el golpe se ve mientras bebes)", true),
            new Param.Toggle("visuals17.sneaking", "Sneaking (la cámara baja al instante y sube suave)", true),
            new Param.Toggle("visuals17.swords", "Swords (la espada en la mano como en 1.7)", true),
            new Param.Toggle("visuals17.potions", "Potions (la poción en la mano como en 1.7)", true),
            new Param.Toggle("visuals17.bow", "Bow (el arco y su tensado como en 1.7)", true),
            new Param.Toggle("visuals17.fishingRod", "Fishing Rod (la caña y su línea como en 1.7)", true),
            new Param.Toggle("visuals17.healthFlashing", "Health Flashing (los corazones parpadean solo al recibir daño)", true),
            new Param.Toggle("visuals17.glint", "Glint Rendering (el brillo de encantamiento como en 1.7)", true),
            new Param.Toggle("visuals17.thirdPersonItems", "Third Person Held Items (los objetos en la mano de los demás como en 1.7)", true),
            new Param.Toggle("visuals17.carpets", "Carpets (la alfombra en la mano como en 1.7)", true),
            new Param.Toggle(
               "visuals17.useWhileDigging", "Usar con el clic izquierdo pulsado (pociones, comida, bloquear… aunque estés picando, como en 1.7)", false
            )
         ),
         HubOption.module(
            "rawInput",
            "Juego",
            "Raw Input",
            "El ratón gira la cámara 1:1, sin la aceleración ni la velocidad del puntero del sistema (como «Raw Input» de vanilla moderno). Si tu ratón no da datos en crudo, se usa el movimiento de siempre.",
            "move"
         ),
         HubOption.module(
            "freecam",
            "Juego",
            "Freecam",
            "Mantén la tecla para mirar a tu alrededor en tercera persona; sigues corriendo hacia donde ibas.",
            "orbit",
            new Param.KeyBind("freecam.key", "Tecla", "freecam")
         ),
         HubOption.module(
            "controls",
            "Juego",
            "Toggle Sneak/Sprint",
            "Agacharse y correr con una pulsación en vez de mantener la tecla.",
            "move",
            new Param.Toggle("toggles.enabled", "Toggle Sneak/Sprint HUD (indicador en pantalla)", true),
            new Param.ZonePick("toggles.zone", "HUD: zona", HudDefaults.zone("toggles", null)),
            new Param.Toggle("toggles.onlyToggle", "HUD: solo en modo fijo (no al mantener la tecla)", true),
            new Param.Toggle("toggles.background", "HUD: fondo", true),
            new Param.Range("toggles.opacity", "HUD: opacidad del fondo", 0.0, 100.0, 1.0, 40.0, " %", null),
            new Param.Range("toggles.scale", "HUD: escala", 0.5, 6.0, 0.25, HudDefaults.scale("toggles")),
            new Param.Toggle("controls.toggleSneak", "Toggle Sneak (volando, solo al mantener)", true),
            new Param.Toggle("controls.toggleSprint", "Toggle Sprint", true),
            new Param.Toggle("controls.doubleTap", "Double Tap (doble toque de W para correr)", true),
            new Param.Toggle("controls.sneakInWater", "En el agua, agacharse solo al mantener (sin fijo)", true),
            new Param.Toggle("controls.flyBoost", "Fly Boost (volando y corriendo, más rápido)", true),
            new Param.Range("controls.flyBoostAmount", "Fly Boost Amount", 1.0, 10.0, 0.5, 6.5, "×", null),
            new Param.Range("controls.flyBoostRamp", "Fly Boost: tiempo hasta el máximo", 0.0, 5.0, 0.25, 1.5, " s", null)
         ),
         HubOption.module(
            "ping",
            "Juego",
            "Ping",
            "Latencia real (ida y vuelta medida por el cliente, como F3+3) y aviso cuando la red se corta.",
            "zap",
            new Param.ZonePick("ping.zone", "Zona", HudDefaults.zone("ping", null)),
            new Param.Toggle("ping.detail", "Mostrar mínimo y máximo recientes", false),
            new Param.Toggle("ping.gaps", "Avisar cuando llevo >100 ms sin recibir datos", true),
            new Param.Toggle("ping.background", "Fondo", true),
            new Param.Range("ping.opacity", "Opacidad del fondo", 0.0, 100.0, 1.0, 40.0, " %", null),
            new Param.Range("ping.scale", "Escala", 0.5, 6.0, 0.25, HudDefaults.scale("ping"))
         ),
         HubOption.module(
            "coords",
            "World",
            "Coordenadas",
            "Tu posición X, Y y Z y hacia dónde miras, como F3 pero limpio.",
            "map-pin",
            new Param.ZonePick("coords.zone", "Zona", HudDefaults.zone("coords", null)),
            new Param.Choice("coords.layout", "Disposición", new String[]{"line", "stack"}, new String[]{"Una línea", "En columna"}, "line"),
            new Param.Toggle("coords.direction", "Rumbo (Norte, Sur…)", true),
            new Param.Toggle("coords.background", "Fondo", true),
            new Param.Range("coords.opacity", "Opacidad del fondo", 0.0, 100.0, 1.0, 40.0, " %", null),
            new Param.Range("coords.scale", "Escala", 0.5, 6.0, 0.25, HudDefaults.scale("coords"))
         ),
         HubOption.segmented(
            "ui.titleScreen",
            "Interfaz",
            "Menú principal",
            "El de Pulsar o el de Minecraft al abrir el juego y al salir de un servidor.",
            "layout",
            new String[]{"Minecraft", "Pulsar"},
            new String[]{"vanilla", "pulsar"}
         ),
         HubOption.segmented(
            "ui.tutorial",
            "Interfaz",
            "Tutorial de vanilla",
            "Los avisos de moverse y saltar no vuelven a aparecer.",
            "layout",
            new String[]{"Ocultar", "Mostrar"},
            new String[]{"hide", "show"}
         ),
         HubOption.module(
            "perf",
            "Rendimiento",
            "FPS booster",
            "Motor propio (multidraw, oclusión, LOD) sin tocar cómo se ve; los ajustes gráficos son opcionales.",
            "gauge",
            new Param.Choice(
               "perf.backend", "Backend gráfico (reinicio)", new String[]{"auto", "opengl", "vulkan"}, new String[]{"Auto", "OpenGL", "Vulkan"}, "auto"
            ),
            new Param.Toggle("perf.terrainMultidraw", "Terreno en multidraw (una llamada por capa)", true),
            new Param.Toggle("perf.terrainRegions", "Terreno por regiones (un VBO por región, menos llamadas GL)", true),
            new Param.Toggle("perf.fastMath", "Fast Math (seno y coseno con tabla pequeña; cambia decimales)", false),
            new Param.Toggle("perf.gpuOcclusion", "Oclusión de terreno por GPU (lo que hay detrás no se dibuja)", true),
            new Param.Toggle("perf.entityBatch", "Entidades por lotes (una llamada por entidad en vez de una por pieza)", true),
            new Param.Toggle("perf.uiBatch", "Interfaz por lotes (HUD y menús de Pulsar en una llamada por textura)", true),
            new Param.Toggle("perf.cloudCache", "Nubes con malla en caché (dos llamadas por frame en vez de 128)", true),
            new Param.Toggle("perf.visibility", "Visibilidad de secciones sin basura (misma búsqueda, sin objetos por frame)", true),
            new Param.Range(
               "perf.animDistance",
               "Distancia de texturas animadas: agua, lava y fuego más lejos no se animan (0 = sin límite; con zoom siempre)",
               0.0,
               256.0,
               16.0,
               64.0
            ),
            new Param.Toggle(
               "perf.sectionMatrix",
               "Secciones del terreno sin esperar a la GPU al moverse (su matriz es siempre la misma: se lee una vez, no en cada sección)",
               true
            ),
            new Param.Toggle(
               "perf.textureUpload", "Skins y otras imágenes sin reservar 16 MB al subirlas (mismas llamadas a la GPU, memoria del tamaño de la imagen)", true
            ),
            new Param.Toggle(
               "perf.batchDeletes", "Cambio de mundo sin tirones: la memoria de vídeo del terreno se libera de una vez, no sección a sección", true
            ),
            new Param.Toggle(
               "perf.lazyBuffers", "Entrar y cambiar de mundo más rápido: la memoria de vídeo de cada sección se pide al usarla, no al crearla", true
            ),
            new Param.Toggle("perf.signText", "Carteles: texto en caché y, de lejos (letra ilegible, también con zoom), sin texto", true),
            new Param.Toggle("perf.signBatch", "Carteles por sección (las tablas de cada sección en un solo dibujo)", true),
            new Param.Toggle(
               "perf.asyncNearChunks",
               "Carga de chunks sin tirones (las secciones junto al jugador se compilan en segundo plano; un bloque roto o puesto se sigue viendo al instante)",
               true
            ),
            new Param.Toggle(
               "perf.entityCache", "Caché de entidades (la pose se actualiza a 120–20 Hz según el tamaño; el resto de frames se reproduce desde la GPU)", true
            ),
            new Param.Toggle("perf.entityCacheCombat", "Caché de entidades en combate (armadura encantada y daño también se reproducen desde la GPU)", true),
            new Param.Toggle("perf.entityCacheMaps", "Caché de entidades con mapas (las imágenes hechas con marcos y mapas se reproducen desde la GPU)", true),
            new Param.Toggle("perf.entityCacheMerge", "Fusión de lotes de entidades (piezas con el mismo estado y brillos en un solo dibujo)", true),
            new Param.Toggle("perf.shadowBatch", "Sombras de las entidades por lotes (todas juntas al acabar las entidades)", true),
            new Param.Toggle("perf.clientArrays", "Letreros de nombre sin reservar memoria de vídeo en cada dibujo", true),
            new Param.Toggle("perf.drawFirst", "Entidades en caché sin reconfigurar los vértices en cada dibujo", true),
            new Param.Toggle("perf.entityLod", "LOD de entidades (menos triángulos con la distancia: solo las caras que miran a la cámara)", true),
            new Param.Toggle("perf.labelBatch", "Letreros de nombre por lotes (tres tandas por frame en vez de una por letrero)", true),
            new Param.Range("perf.chunkUpdates", "Chunk updates por frame (0 = vanilla)", 0.0, 5.0, 1.0, 0.0),
            new Param.Toggle("perf.dynamicUpdates", "Chunk updates sin límite al estar parado", true),
            new Param.Range("perf.chunkThreads", "Hilos de compilado de chunks (recarga)", 1.0, 5.0, 1.0, 2.0),
            new Param.Toggle("perf.fog", "Niebla", true),
            new Param.Toggle("perf.sky", "Cielo", true),
            new Param.Toggle("perf.sunMoon", "Sol y luna", true),
            new Param.Toggle("perf.stars", "Estrellas", true),
            new Param.Toggle("perf.vignette", "Viñeta", true),
            new Param.Toggle("perf.weather", "Lluvia y nieve", true),
            new Param.Toggle("perf.animWater", "Animar el agua", true),
            new Param.Toggle("perf.animLava", "Animar la lava", true),
            new Param.Toggle("perf.animFire", "Animar el fuego", true),
            new Param.Toggle("perf.animPortal", "Animar el portal", true),
            new Param.Toggle("perf.faceCulling", "Saltar las caras que miran en contra de la cámara", true),
            new Param.Toggle("perf.compactVertices", "Vértices de terreno compactos (16 B en vez de 28)", true),
            new Param.Toggle("perf.fogCulling", "No dibujar lo que la niebla ya tapa", true),
            new Param.Toggle("perf.spriteCulling", "Animar solo las texturas visibles", true),
            new Param.Toggle("perf.parallelFeatures", "Entidades y letreros en hilos aparte", true),
            new Param.Toggle("perf.tagBatching", "Letreros y hologramas en un solo lote", true),
            new Param.Toggle("perf.textCache", "Letreros y hologramas horneados", true),
            new Param.Toggle("perf.entityBake", "Modelos de entidades precomputados", true),
            new Param.Toggle(
               "perf.poseCache",
               "Caché de pose de jugadores (la pose se actualiza a 120–20 Hz según el tamaño; el resto de frames se copian sus vértices)",
               true
            ),
            new Param.Toggle("perf.noErrorContext", "OpenGL sin comprobación de errores (reinicio)", true),
            new Param.Toggle("perf.cullEntities", "Ocultar entidades tras paredes", true),
            new Param.Toggle("perf.cullPlayers", "…también jugadores", false),
            new Param.Toggle("perf.occludePlayers", "Jugadores tapados por bloques: solo su nombre", true),
            new Param.Toggle("perf.cullBlockEntities", "Ocultar cofres y similares tras paredes", true),
            new Param.Toggle("perf.itemLod", "Ítems caídos: agrupar de lejos (LOD)", true),
            new Param.Range("perf.itemLodDistance", "Ítems: agrupar a partir de", 8.0, 64.0, 4.0, 24.0, " bloques", null),
            new Param.Range("perf.itemLodCell", "Ítems: tamaño de grupo", 1.0, 4.0, 1.0, 2.0, " bloques", null),
            new Param.Range("perf.itemRange", "Ítems: alcance visible", 32.0, 128.0, 8.0, 64.0, " bloques", null),
            new Param.Range("perf.beDistance", "Distancia de cofres y similares", 16.0, 128.0, 8.0, 64.0, " bloques", null),
            new Param.Range("perf.particleBudget", "Tope de partículas", 128.0, 4096.0, 128.0, 1024.0),
            new Param.Range("perf.particleDistance", "Distancia de partículas", 8.0, 96.0, 8.0, 32.0, " bloques", null),
            new Param.Toggle("perf.pacer", "Pulsar Pacer: presupuesto por frame (menos tirones)", true),
            new Param.Toggle("perf.diagnostics", "Diagnóstico en el HUD", false),
            new Param.Choice("perf.particles", "Partículas", new String[]{"all", "decreased", "minimal"}, new String[]{"Todas", "Reducidas", "Mínimas"}, "all"),
            new Param.Toggle("perf.shadows", "Sombras de entidades", true),
            new Param.Choice("perf.clouds", "Nubes", new String[]{"off", "fast", "vanilla"}, new String[]{"Off", "Rápidas", "Vanilla"}, "vanilla"),
            new Param.Range("perf.biomeBlend", "Mezcla de bioma", 0.0, 7.0, 1.0, 2.0),
            new Param.Toggle("perf.limitDistance", "Limitar distancia de render", false),
            new Param.Range("perf.renderDistance", "Distancia de render", 2.0, 32.0, 1.0, 8.0, " chunks", null),
            new Param.Range("perf.entityDistance", "Entidades lejanas (vanilla: sin tocar)", 0.5, 1.5, 0.05, 1.0),
            new Param.Toggle("perf.smoothLighting", "Luz suave", true),
            new Param.Toggle("perf.fastLeaves", "Hojas rápidas", false),
            new Param.Toggle("perf.tabCache", "Tabulador en caché (se redibuja por tick, no por frame)", true),
            new Param.Toggle("perf.chatCache", "Chat en caché (se redibuja solo cuando cambia)", true),
            new Param.Toggle("perf.smartLeaves", "Hojas inteligentes (detalladas sin las caras interiores)", true),
            new Param.Toggle("perf.improvedTransparency", "Transparencia mejorada", false),
            new Param.Toggle("perf.vsync", "VSync", false),
            new Param.Range("perf.fpsLimit", "Límite de FPS", 30.0, 260.0, 10.0, 260.0, "", "∞")
         ),
         HubOption.module(
            "fps",
            "Rendimiento",
            "FPS",
            "Contador discreto en la zona que elijas.",
            "zap",
            new Param.ZonePick("fps.zone", "Zona", HudDefaults.zone("fps", null)),
            new Param.Toggle("fps.background", "Fondo", true),
            new Param.Range("fps.opacity", "Opacidad del fondo", 0.0, 100.0, 1.0, 40.0, " %", null),
            new Param.Range("fps.scale", "Escala", 0.5, 6.0, 0.25, HudDefaults.scale("fps"))
         )
      )
   );
   public static final Set<String> LEGACY_ONLY_PARAMS = Set.of(
      "perf.tabCache",
      "perf.sectionMatrix",
      "perf.textureUpload",
      "perf.batchDeletes",
      "perf.lazyBuffers",
      "perf.chatCache",
      "perf.smartLeaves",
      "perf.signText",
      "perf.signBatch",
      "perf.animDistance",
      "perf.asyncNearChunks",
      "perf.terrainRegions",
      "perf.fastMath",
      "perf.gpuOcclusion",
      "perf.entityBatch",
      "perf.uiBatch",
      "perf.cloudCache",
      "perf.visibility",
      "perf.labelBatch",
      "perf.entityLod",
      "perf.entityCache",
      "perf.entityCacheCombat",
      "perf.entityCacheMaps",
      "perf.entityCacheMerge",
      "perf.shadowBatch",
      "perf.clientArrays",
      "perf.drawFirst",
      "perf.chunkUpdates",
      "perf.dynamicUpdates",
      "perf.chunkThreads",
      "perf.fog",
      "perf.sky",
      "perf.sunMoon",
      "perf.stars",
      "perf.vignette",
      "perf.weather",
      "perf.animWater",
      "perf.animLava",
      "perf.animFire",
      "perf.animPortal"
   );
   public static final Set<String> LEGACY_ONLY_MODULES = Set.of("visuals17", "rawInput", "pulsarMark");
   private static volatile Predicate<HubOption> available = o -> true;
   private static volatile Predicate<String> paramAvailable = id -> true;

   public static void available(Predicate<HubOption> filter) {
      available = filter;
   }

   public static void paramAvailable(Predicate<String> filter) {
      paramAvailable = filter;
   }

   private HubOptions() {
   }

   public static List<HubOption> all() {
      List<HubOption> out = new ArrayList<>();

      for (HubOption o : ALL) {
         if (available.test(o)) {
            List<Param> params = new ArrayList<>();

            for (Param p : o.params()) {
               if (paramAvailable.test(p.id())) {
                  params.add(p);
               }
            }

            out.add(params.size() == o.params().size() ? o : o.withParams(Collections.unmodifiableList(params)));
         }
      }

      return Collections.unmodifiableList(out);
   }

   public static List<HubOption> definitions() {
      return Collections.unmodifiableList(ALL);
   }

   public static void register(HubOption option) {
      ALL.removeIf(o -> o.id().equals(option.id()));
      ALL.add(option);
   }

   public static boolean paramDisabled(HubOption option, Param param) {
      return option.id().equals("crosshair") && !param.id().equals("crosshair.style")
         ? "none".equals(ClientOptions.get().string("crosshair.style", "cross"))
         : false;
   }

   public static HubOption byId(String id) {
      for (HubOption o : ALL) {
         if (o.id().equals(id)) {
            return o;
         }
      }

      return null;
   }

   public static String categoryIcon(String c) {
      return switch (c) {
         case "Combate" -> "swords";
         case "Interfaz" -> "layout";
         case "Juego" -> "box";
         case "World" -> "globe";
         case "Rendimiento" -> "gauge";
         case "Teclas" -> "settings";
         default -> "info";
      };
   }
}
