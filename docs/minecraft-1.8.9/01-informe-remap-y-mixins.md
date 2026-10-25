# Minecraft 1.8.9 + Pulsar Launcher — ingeniería inversa (fase 2)

**Carpeta:** `/home/user/pulsar-rea` (todo contenido aquí)
**Objetivo:** descargar el `client.jar` de 1.8.9 que ejecuta el cliente Pulsar v0.2.26-beta, remapearlo a nombres legibles y verificar contra el bytecode real los 100 mixins / 289 hooks / 47 accessors que Pulsar inyecta.

---

## 1. Qué se descargó y cómo se verificó

Fuente oficial (la misma que usa Pulsar en `meta/PistonMetaClient`):

```
GET https://piston-meta.mojang.com/mc/game/version_manifest_v2.json
  → 1.8.9 → sha1 d546f1707a3f2b7d034eece5ea2e311eda875787
GET https://piston-meta.mojang.com/v1/packages/d546.../1.8.9.json
  → downloads.client: sha1 3870888a6c3d349d3771a3e9d16c9bf5e076b908, size 8461484
GET https://launcher.mojang.com/v1/objects/3870888a.../client.jar
```

| Artefacto | SHA-1 calculado | Esperado | OK |
|---|---|---|---|
| `mc189/client-1.8.9.jar` (8.461.484 B) | `3870888a6c3d349d3771a3e9d16c9bf5e076b908` | idem | ✅ |
| 33 librerías de `mc189/libraries/` | verificadas una a una | manifiesto | ✅ 33/33 |
| SHA-256 del cliente | `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298` | — | — |

`mainClass = net.minecraft.client.main.Main`, `javaVersion.majorVersion = 8`, 37 librerías (4 solo nativos), `minecraftArguments` clásicos de 1.8.x. Coincide exactamente con lo que hace `launch/ArgumentBuilder.java` de Pulsar.

Detalle relevante para el sistema `legit`: `BootSeal.exam()` calcula `mojangSha1` con **SHA-1 de `<mcVersion>.jar`**. Ese valor tiene que ser `3870888a…b908`; cualquier otra cosa marca el cliente como modificado.

---

## 2. Cadena de herramientas montada

El `client.jar` está **ofuscado con nombres Searge** (`ave`, `wn`, `bda`, `a`, `b`…). Pasos:

### 2.1 Mappings
- `mcp-1.8.9-srg.zip` → **`joined.srg`** (2.541 clases, 16.426 métodos, 8.917 campos): el mapa `obf → net/minecraft/...` + SRG (`func_/field_`).
- `mcp_stable-22-1.8.9.zip` → `methods.csv` / `fields.csv`: SRG → nombre MCP legible.

### 2.2 `srg2classmapping.py`
Fusiona ambos en un `classmapping.json` compatible con `ClassRemapper` de ASM:

```
clases            : 2541
métodos mapeados  : 13547
campos mapeados   : 6530
entradas totales  : 22618
```

Incluye la remapeo de descriptores (`Lwn;` → `Lnet/minecraft/entity/player/EntityPlayer;`) con el truco de **clave exacta `owner/nombre+descriptor` primero y solo-nombre después**, porque en 1.8.9 los nombres ofuscados se reutilizan entre sobrecargas.

### 2.3 `RemapMinecraft.java` (ASM)
Remapea el JAR completo con `ClassRemapper`:

```
classes remapped: 2507 | resources: 3090 | failures: 0
member hits: 347.965 | member misses: 203.110
```

Los *misses* son casi todos métodos de librerías (Guava, LWJGL, Netty) y `<init>`, que no están en `joined.srg`.

Verificación: `unzip -l client-1.8.9-mcp.jar | grep -cE '^[a-z]{1,3}\.class$'` → **0** (no queda ninguna clase ofuscada).

### 2.4 `StripDebug.java` (ASM, `SKIP_DEBUG`)
El `client.jar` lleva `LocalVariableTable` con nombres ofuscados (`a`, `b`, `☃`). Vineflower los usa y produce código ilegible. Quitando la información de depuración:

```java
// antes                       // después
var1.f(var2);                  var1.f(var2);
☃.f(☃);                        var1.f(var2);
```

### 2.5 Descompilación
`vineflower 1.11.1` → `mc189-decompiled/` (1.612 ficheros `.java`, 27 MB, **0 errores** de descompilación).

> Nota honesta sobre el alcance: con miembros sin remapear los nombres de método siguen siendo Searge (`a(EntityPlayer, Entity)` en vez de `attackEntity`). El mapeo **nombre a nombre** sí está en `classmapping.json`, y los informes de hooks (§4) ya dan el nombre MCP de cada objetivo. Remapear también miembros produciría problemas porque el SRG de `joined.srg` es el de MCP *con* parches y no cubre todas las sobrecargas; se ha preferido exactitud sobre estética.

---

## 3. Qué usa Pulsar de 1.8.9 (números verificados)

| Métrica | Valor |
|---|---|
| Mixins declarados | **100** |
| Clases `@Mixin` encontradas en el jar | **100 / 100** |
| Hooks `@Inject/@Redirect/ModifyArg/ModifyVariable/@Overwrite` | **289** |
| Hooks cuyo método objetivo existe en el bytecode | **289 / 289** |
| Accessors/Invokers | **47** |
| `@At target` totales | 99 |
| — en clases del jar de MC | 81 → **81 resueltos** (4 por herencia) |
| — en LWJGL/JDK/Guava (fuera del jar) | 18 (no comprobables aquí) |
| Clases de Minecraft instrumentadas | **73** |

Scripts: `mc189/verify_hooks.py` → `hooks-report.txt`, `mc189/verify_targets.py` → `targets-report.txt`.

### Clases más instrumentadas
`Minecraft` (8 mixins), `GuiIngame` (4), `EntityRenderer` (3), `RenderGlobal` (3), `ChunkRenderContainer`, `LayerArmorBase`, `ModelBiped`, `WorldRenderer`, `ViewFrustum`, `ChunkRenderDispatcher`.

### Mapa completo obf → real (extracto verificado)
```
ave → net/minecraft/client/Minecraft                  bfr → renderer/RenderGlobal
bfk → renderer/EntityRenderer                         bfl → renderer/GlStateManager
bjl → renderer/entity/RendererLivingEntity            bda → multiplayer/PlayerControllerMP
bdb → multiplayer/WorldClient                         bcy → network/NetHandlerPlayClient
bdc → network/NetworkPlayerInfo                       avh → settings/GameSettings
wn  → entity/player/EntityPlayer                      wl  → entity/player/PlayerCapabilities
pr  → entity/EntityLivingBase                         pk  → entity/Entity
aug → util/AxisAlignedBB                              pz  → entity/DataWatcher
```

Cada alias que Pulsar escribe en crudo (`bda`, `wn`, `pk`, `bjl`, `pr`, `ave`, `bcy`, `bdc`, `aug`, `bdb`, `aul`, `wl`…) resuelve a su clase real.

---

## 4. Hooks resueltos (nombre Searge → nombre MCP)

Muestra representativa del informe `hooks-report.txt`:

| Mixin | clase obf | objetivo | método MCP 1.8.9 |
|---|---|---|---|
| `AttackReachMixin` | `bda` | `a(Lwn;Lpk;)V` | `attackEntity` |
| `FlyBoostMixin` | `wn` | `g` | `moveEntityWithHeading` |
| `GameRendererMixin` | `bfk` | `a(FJ)V` | `updateCameraAndRender` |
| | | `f(F)V` | `orientCamera` |
| | | `a(FZ)F` | `getFOVModifier` |
| | | `b(FI)V` | `renderHand` |
| | | `g` | `updateLightmap` |
| `WorldRendererMixin` | `bfr` | `a(Lpk;DLbia;IZ)V` | `setupTerrain` |
| | | `a(Lpk;Lbia;F)V` | `renderEntities` |
| | | `a(J)V` | `updateChunks` |
| | | `a(Ladf;DILpk;)I` | `renderBlockLayer` |
| | | `a(IIIIII)V` | `markBlockRangeForRenderUpdate` |
| `LivingEntityRendererMixin` | `bjl` | `b(Lpr;DDD)V` | `renderName` |
| | | `a(Lpr;DDDFF)V` | `doRender` |
| | | `a(Lpr;FZ)Z` | `setBrightness` |
| | | `a(Lpr;)Z` | `canRenderName` |
| `MinecraftMixin` | `ave` | `av` | `runGameLoop` |
| | | `s` | `runTick` |
| | | `Z` | `dispatchKeypresses` |
| | | `g` | `shutdownMinecraftApplet` |
| `MinecraftClientClicksMixin` | `ave` | `s` | `runTick` |
| `MouseInputMixin` | `avf` | `c` | `mouseXYChange` |
| | | `a` | `grabMouseCursor` |
| `ToggleSneakSprintMixin` | `bew` | `m` | `onLivingUpdate` |
| `ClientPlayNetworkHandlerMixin` | `bcy` | — | `handle…` |
| `ConnectionKeyMixin` | `bcx` | `a(Ljh;)V` | `handleEncryptionRequest` |
| `DataTrackerMixin` | `pz` | `a(ILjava/lang/Object;)V` | `addObject` |
| | | `j` | `getWatchedObject` |
| `PacketInflaterMixin` | `ei` | `decode` | `decode` (Netty) |
| `DuplicatePlayerMixin` | `bdb` | `a(ILpk;)V` | `addEntityToWorld` |
| `OtherClientPlayerMoveMixin` | `bex` | `a(DDDFFIZ)V` | `setPositionAndRotation2` |
| `SmartLeavesMixin` | `akg` | `a` | `BlockLeavesBase` |

### Caso verificado a mano: `FlyBoostMixin`
```java
@Mixin({wn.class})                                   // wn = EntityPlayer
@Redirect(method = {"g"},                            // g(FF)V = moveEntityWithHeading
          at = @At(value="INVOKE", target="Lwl;a()F")) // wl = PlayerCapabilities, a()F = getFlySpeed
private float pulsar$flyBoost(wl abilities) {
   float speed = abilities.a();
   return self == mc.thePlayer ? speed * ToggleSneakSprint.flySpeedFactor(...) : speed;
}
```
Comprobado con `javap -c` que `EntityPlayer.g(FF)` llama a `PlayerCapabilities.a()F` → el hook es válido y el efecto es **multiplicar la velocidad de vuelo hasta ×6,5 con rampa de 1,5 s** cuando vuelas y esprintas (`controls.flyBoostAmount`, `controls.flyBoostRamp`).

---

## 5. Cuatro hooks curiosos que conviene mirar

| Mixin | Qué hace | Lectura |
|---|---|---|
| `PacketInflaterMixin` + `DecoderHandlerMixin` | instrumentan `NettyCompressionDecoder.decode` y `MessageDeserializer.decode` | tocar la pila de red a ese nivel permite ver/alterar paquetes antes de que lleguen al juego. Aquí solo se usa para métricas (`NetMeter`) y detección, pero es la posición exacta donde se engancharía un *packet logger* |
| `DataTrackerMixin` | `addObject` / `getWatchedObject` | lee metadata sincronizada de entidades (invisibilidad, nametags, ítems) |
| `DuplicatePlayerMixin` (`WorldClient.addEntityToWorld`) | controla entidades duplicadas | usado por la caché de entidades; es también el punto clásico para *anti-dupe*/dupes visuales |
| `GlStateManagerMixin` (**42 hooks**) | envuelve casi todo el estado GL (`bindTexture`, `blendFunc`, `pushMatrix`…) | instrumentación masiva para el renderer propio (HUD, emotes, capas). Es el mixin más invasivo del paquete |

Ninguno de los 289 hooks introduce *reach*, *hitbox*, *velocity*, *nofall* ni *aimbot*: el único contacto con `attackEntity` es `AttackReachMixin`, que **solo mide** la distancia para el sensor `ReachSensor` (ver informe fase 1, §8).

---

## 6. Lo que REA hizo aquí (y lo que no)

| Comando | Resultado |
|---|---|
| `rea inspect-artifact mc189/client-1.8.9.jar` | formato `zip`, 5.548 nodos / 5.598 ocurrencias / 5.597 aristas, `graph_sha256 25c03dbe…045c`, `root_sha256 14f0d96d…d298` |
| `rea inspect-artifact mc189/client-1.8.9-named.jar` | inventario del JAR remapeado (5.548 nodos, `sha256 211c567d…`), útil para comparar el árbol antes/después |
| `rea inspect-artifact` sobre `v1_8_9-0.2.26-beta.jar` | 270 nodos: el mod de Pulsar es 5 % del tamaño del cliente |
| `rea inspect-artifact` sobre `launcher`, `bootstrap`, `engine-api` | inventarios archivados en `evid/` |
| `rea analyze-javascript-application` | sin sentido aquí (no hay JS) |
| `rea inspect-native-exports` / `inspect-pe-certificates` | fase 1 |

**Limitación real de REA:** no tiene proveedor de bytecode JVM (sus backends son nativo, .NET/CLI, JS/Electron, Android, WASM, firmware). Para `.class` solo ofrece el inventario de contenedor. Por eso el peso del trabajo lo llevaron:

- **ASM 9.10.1** (reutilizando los JARs que trae el propio Pulsar en `client/`) para remapear y limpiar.
- **Vineflower 1.11.1** para descompilar.
- **Scripts propios** (`srg2classmapping.py`, `verify_hooks.py`, `verify_targets.py`) para la verificación cruzada mixin ↔ bytecode.
- **REA** para la evidencia reproducible del contenedor y la cadena de custodia (`evid/*.json`).

---

## 7. Reproducir

```bash
cd /home/user/pulsar-rea/mc189
# 1. descarga y comprobación SHA-1 (contra el manifiesto de Mojang)
curl -sLO https://launcher.mojang.com/v1/objects/3870888a6c3d349d3771a3e9d16c9bf5e076b908/client.jar
sha1sum client.jar    # 3870888a6c3d349d3771a3e9d16c9bf5e076b908
# 2. mappings
curl -sLO https://maven.minecraftforge.net/de/oceanlabs/mcp/mcp/1.8.9/mcp-1.8.9-srg.zip
curl -sLO https://maven.minecraftforge.net/de/oceanlabs/mcp/mcp_stable/22-1.8.9/mcp_stable-22-1.8.9.zip
# 3. remapeo + limpieza (ASM, usa los jars que trae Pulsar)
java -cp "asm/*" RemapMinecraft.java client-1.8.9.jar client-1.8.9-mcp2.jar classmapping-classesonly.json
java -cp "asm/*" StripDebug.java client-1.8.9-mcp2.jar client-1.8.9-named.jar
# 4. descompilar
java -Xmx8g -jar ../tools/vineflower.jar client-1.8.9-named.jar ../mc189-decompiled
# 5. verificación mixin <-> bytecode
python3 verify_hooks.py   > hooks-report.txt
python3 verify_targets.py > targets-report.txt
```

---

## 8. Aviso

Análisis estático sobre software propietario descargado de los servidores oficiales de Mojang, con fines de interoperabilidad y auditoría. No se ha modificado el cliente de Minecraft ni se ha redistribuido; el JAR remapeado y las fuentes descompiladas son material de trabajo local. Los binarios de Pulsar siguen siendo de sus autores.
