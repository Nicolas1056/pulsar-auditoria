# Pulsar Launcher — auditoría de ingeniería inversa

Análisis estático independiente de **Pulsar Launcher 0.2.26-beta** (launcher y cliente de terceros para Minecraft: Java Edition), con enfoque en **Windows**, complementado con el build Linux y con el `client.jar` oficial de Minecraft 1.8.9 que el cliente ejecuta.

| | |
|---|---|
| Artefacto principal | `PulsarLauncher-0.2.26-beta-windows-x86_64-setup.exe` |
| SHA-256 | `a6c5435ea7ae89d7de7d765620b4e489b305ce9d89d424c7b45ba858b751098b` |
| Tamaño | 187.028.886 B |
| Instalador | Inno Setup 6.7.0 (Unicode), `lzma2`, `PrivilegesRequired=lowest` |
| Firma Authenticode | **ausente** en todos los ejecutables, incluido `libcef.dll` |
| Fecha del análisis | 2026-10-25 |

---

## Documentación

### Windows (enfoque principal)

| Documento | Contenido |
|---|---|
| [`docs/win/01-informe-launcher.md`](docs/win/01-informe-launcher.md) | Arquitectura completa: CEF 151 + shell C++ + daemon Java 25 + cliente Minecraft con Mixin. 53 comandos del daemon, endpoints, autenticación, actualizaciones, módulo `legit` |
| [`docs/win/02-privacidad-y-seguridad.md`](docs/win/02-privacidad-y-seguridad.md) | Privacidad y seguridad real en Windows: fuga del refresh token por línea de comandos, `RIDEV_INPUTSINK`, escritura en registro, mitigaciones ausentes, ranking de telemetría, veredicto |
| [`docs/win/03-modulo-legit.md`](docs/win/03-modulo-legit.md) | Anatomía completa del "anticheat": arquitectura de tres canales, challenge-response, los tres sensores, `ClickCap`, `ProbeEngine`, `SelfGuard`, `StaffXrayGate` y análisis de consentimiento |

### Linux (comparativa)

| Documento | Contenido |
|---|---|
| [`docs/linux/01-diferencias-vs-windows.md`](docs/linux/01-diferencias-vs-windows.md) | Qué cambia en el build Linux: `pkexec`/`apt-get`, regla udev, GDK/AppImage, y por qué el veredicto de privilegios se invierte |

### Cumplimiento

| Documento | Contenido |
|---|---|
| [`docs/compliance/01-eula-y-usage-guidelines.md`](docs/compliance/01-eula-y-usage-guidelines.md) | Contraste con el EULA de Minecraft y las Usage Guidelines: 9 puntos evaluados, 2 incumplimientos, 1 riesgo de plataforma |

### Minecraft 1.8.9

| Documento | Contenido |
|---|---|
| [`docs/minecraft-1.8.9/01-informe-remap-y-mixins.md`](docs/minecraft-1.8.9/01-informe-remap-y-mixins.md) | Descarga verificada del `client.jar`, remapeo Searge→MCP, y verificación de los **289 hooks / 81 targets** contra el bytecode real |

### Legal

| Documento | Contenido |
|---|---|
| [`docs/legal/AVISO.md`](docs/legal/AVISO.md) | Naturaleza del material publicado, copyright de terceros y ausencia de licencia |

---

## El módulo `legit`: el "anticheat"

Es el componente central del launcher y el que más consecuencias tiene para el jugador: **1.706 líneas en 15 clases** más 6 en `bootstrap/legit`. Análisis completo en **[`docs/win/03-modulo-legit.md`](docs/win/03-modulo-legit.md)**.

### Cómo funciona

No es un anticheat del servidor: es un **notario criptográfico de cliente**. El servidor de Minecraft pregunta, Pulsar firma la respuesta, y el servidor decide. Pulsar no juzga.

El canal por el que llega el challenge es **plugin messaging de Minecraft** (`pulsar:v1`), el mismo mecanismo que usan los plugins de Spigot: **no hace falta infraestructura de Pulsar para preguntar**, solo para firmar.

```
Servidor MC ──legit.challenge {nonce, sensors, cpsCap}──▶ Cliente Pulsar
                                                              │
                                            WSS rt.pulsarlauncher.com (firma)
                                                              ▼
Servidor MC ◀──legit.attestation {token | error}──────────────┘
```

### Qué recoge

| Sensor | Qué mide | Cómo detecta trampas |
|---|---|---|
| `ClickSensor` | intervalos entre clics (80 buckets de 5 ms), duración de pulsación, `cpsMax` | **6 intervalos consecutivos repetidos con ±2 ms**: un humano no lo produce, un autoclicker sí |
| `AimSensor` | media y varianza de Δyaw/Δpitch por tick (Welford) | ***snap***: giro >30° seguido de movimiento <0,001° (corrección instantánea a la cabeza) |
| `ReachSensor` | distancia ojo→AABB en 160 buckets de 0,05 | **`p50` y `p95`**: un reach hack desplaza la distribución entera |
| `ProbeEngine` | `SHA-256(salt ‖ bytes)` de clases y rangos de JAR | challenge-response con **salt aleatorio del servidor** (no precalculable) |
| `SelfGuard` | clases desconocidas/redefinidas, agentes JVM, `TracerPid`/`IsDebuggerPresent`, módulos con SHA-256 + firma | detecta mods inyectados y depuradores; cada **5 s** |
| `BootSeal` | `bootHash` de todos los JARs, `mojangSha1`, argumentos JVM inyectores | raíz de confianza en el arranque |

### Qué **hace** al jugador

`ClickCap` es el único componente que **muta el juego en tiempo real**: si el servidor manda `cpsCap: 8` y haces 12 CPS, **el cliente se come 4 clics**. Se aplica en el mixin antes de `Minecraft.clickMouse()`, así que el juego nunca los ve — y se aplica en `EntryPass.respond()` **antes de cualquier validación**, de modo que el cap queda puesto incluso si la atestación acaba fallando.

### Incumplimientos y riesgos

| # | Punto | Severidad | Detalle |
|---|---|---|---|
| L1 | Verifica acceso *out-of-game* y altera funciones *in-game* (capado de CPS, denegación de entrada) | ❌ **Incumple** Usage Guidelines §"Extended functionality" | [compliance/01](docs/compliance/01-eula-y-usage-guidelines.md#6--el-módulo-legit--incumple) |
| L2 | **El jugador no puede negarse**: `SignalsChoice.mandatory()` está hardcodeado en `PackClient` | 🔴 Crítico | [win/03 §4](docs/win/03-modulo-legit.md#4--el-jugador-no-puede-rechazar-los-sensores) |
| L3 | Silenciar los sensores **no se comunica al servidor**: el token se envía igual, el servidor cree que tiene datos | 🟠 Alto | [win/03 §4](docs/win/03-modulo-legit.md#4--el-jugador-no-puede-rechazar-los-sensores) |
| L4 | Telemetría conductual obligatoria: patrón de clics (80 buckets) y de apuntado (varianza por tick) = firma biométrica | 🟠 Alto | [win/02 §7](docs/win/02-privacidad-y-seguridad.md#7-ranking-de-telemetría--específico-de-windows) |
| L5 | Inventario de DLLs con SHA-256 y verificación de firma WinTrust, cada 5 s | 🟠 Medio | [win/03 §7](docs/win/03-modulo-legit.md#7-selfguard-y-guard--lo-que-se-envía-cada-5-segundos) |
| L6 | `StaffXrayGate` permite al staff ocultar bloques; el diseño es sólido (doble firma) pero concentra riesgo en la clave privada de Pulsar | 🟡 Bajo | [win/03 §8](docs/win/03-modulo-legit.md#8-staffxraygate--el-control-de-moderación) |

### Detalle sobre L2: la interfaz de consentimiento existe y no se usa

```java
// PackClient.java:64 — constante, no configurable
private final SignalsChoice signalsChoice = SignalsChoice.mandatory();

// SignalsChoice.java — devuelve siempre lo que el servidor pidió
static SignalsChoice mandatory() {
   return (serverId, requested) -> CompletableFuture.completedFuture(requested);
}
```

Existen la interfaz `SignalsChoice`, la rama `declined_sensors` y hasta una factoría `EntryPass.sharing(Predicate)`. **Nadie las cablea**: es una abstracción preparada para un diálogo de consentimiento que nunca se implementó.

### Cómo desactivarlo

| Nivel | Método | Efecto real |
|---|---|---|
| Total | `-Dpulsar.legit=off` | sin sesión, sin sensores, sin probes |
| Sensores | `"legit.sensors": false` en `client-options.json` | los sensores no registran, **pero el token y el `ClickCap` siguen activos** |
| Sesión | jugar sin cuenta de Pulsar | el handshake anuncia `legit=false` |
| Servidor | que no lo pida | sin `legit.challenge` no hay nada |

---

## Hallazgos principales

| # | Hallazgo | Severidad | Documento |
|---|---|---|---|
| 1 | El **refresh token de Microsoft** se pasa como argumento `-Command` a `powershell.exe`, legible por cualquier proceso del usuario | 🔴 Crítico | [win/02](docs/win/02-privacidad-y-seguridad.md#1--crítico--el-refresh-token-viaja-en-la-línea-de-comandos) |
| 2 | Falta el disclaimer obligatorio **"NOT AN OFFICIAL MINECRAFT PRODUCT…"** | ❌ Incumple | [compliance/01](docs/compliance/01-eula-y-usage-guidelines.md#5--falta-el-disclaimer-obligatorio--incumple) |
| 3 | El módulo `legit` verifica acceso *out-of-game* y altera funciones *in-game* (capado de CPS, denegación de entrada) | ❌ Incumple | [ver §legit](#el-módulo-legit-el-anticheat) · [compliance/01](docs/compliance/01-eula-y-usage-guidelines.md#6--el-módulo-legit--incumple) |
| 3b | **`SignalsChoice.mandatory()` está hardcodeado**: no existe ruta de código para que el jugador rechace compartir los sensores | 🔴 Crítico | [ver §legit](#detalle-sobre-l2-la-interfaz-de-consentimiento-existe-y-no-se-usa) · [win/03](docs/win/03-modulo-legit.md#4--el-jugador-no-puede-rechazar-los-sensores) |
| 3c | Los sensores conductuales (clics, apuntado) son **firma biométrica** enviada a un tercero sin consentimiento posible | 🟠 Alto | [ver §legit](#incumplimientos-y-riesgos) · [win/02](docs/win/02-privacidad-y-seguridad.md#7-ranking-de-telemetría--específico-de-windows) |
| 4 | Ratón crudo con `RIDEV_INPUTSINK`: recibe eventos **sin foco de ventana** | 🟠 Medio | [win/02](docs/win/02-privacidad-y-seguridad.md#2--ridev_inputsink--el-ratón-se-lee-sin-foco) |
| 5 | Escribe `HKCU\…\UserGpuPreferences` por defecto vía `reg.exe add /f` | 🟠 Medio | [win/02](docs/win/02-privacidad-y-seguridad.md#3--modifica-el-registro-de-windows) |
| 6 | Sin firma Authenticode **ni Control Flow Guard**; `libcef.dll` tampoco firmada | 🟠 Medio | [win/02](docs/win/02-privacidad-y-seguridad.md#4--endurecimiento-ausente-en-los-ejecutables) |
| 7 | Telemetría de hardware + inventario de DLLs con SHA-256 + comportamiento de juego | 🟠 Medio | [win/02](docs/win/02-privacidad-y-seguridad.md#7-ranking-de-telemetría--específico-de-windows) |
| 8 | `pkexec apt-get install -y --allow-downgrades` en Linux | 🟠 Medio (**solo Linux**) | [linux/01](docs/linux/01-diferencias-vs-windows.md) |
| 9 | Client ID de Microsoft ajeno (`00000000402b5328`) por defecto | ⚠️ Riesgo | [compliance/01](docs/compliance/01-eula-y-usage-guidelines.md) |

### Lo que **no** es

Verificado en negativo sobre los binarios de Windows: sin cifrado de archivos, sin persistencia, sin keylogger (`SetWindowsHookEx`/`WH_KEYBOARD`/`GetAsyncKeyState`), sin captura de pantalla, sin inyección en otros procesos, sin robo de archivos (cero `BodyPublishers.ofFile`), sin backdoor. La actualización está firmada con **Ed25519** y la firma se verificó criptográficamente. **No es un virus.**

---

## Estructura del repositorio

```
.
├── docs/                     informes y reportes (fuente: Markdown)
├── evid/                     evidencia JSON generada por REA
├── tools/                    vineflower.jar, innoup (desempaquetador Inno Setup)
├── win-extracted/            instalador Windows extraído + install_script.iss
├── dist/                     binarios originales (setup.exe, tar.gz Linux)
├── mc189/                    artefactos de Minecraft 1.8.9 + scripts de remapeo
├── mc189-decompiled/         Minecraft 1.8.9 decompilado (nombres MCP)
├── jadx-out/                 Pulsar decompilado (639 clases Java)
└── rea/                      repo morluto/rea clonado (herramienta usada)
```

### Scripts propios

| Script | Función |
|---|---|
| `mc189/srg2classmapping.py` | Fusiona `joined.srg` + `methods.csv`/`fields.csv` en un mapping para ASM |
| `mc189/RemapMinecraft.java` | Remapea el `client.jar` Searge→MCP con `ClassRemapper` |
| `mc189/StripDebug.java` | Elimina `LocalVariableTable` ofuscada (`SKIP_DEBUG`) |
| `mc189/verify_hooks.py` | Resuelve los 289 hooks de mixins contra el bytecode real |
| `mc189/verify_targets.py` | Resuelve los 81 `@At target`, siguiendo la jerarquía de clases |

---

## Ficheros excluidos del repositorio

GitHub rechaza ficheros de más de **100 MB** y el push falla. Seis ficheros del análisis superan ese límite (1,2 GB en total) y quedan fuera del repo:

| Fichero | Tamaño | Cómo obtenerlo |
|---|---|---|
| `win-extracted/{app}/libcef.dll` | 272 MB | reextraer el setup con `innounp` |
| `dist/linux/…/libcef.so` | 256 MB | desempaquetar `dist/PulsarLauncher-linux-x86_64.tar.gz` |
| `dist/PulsarLauncher-linux-x86_64.tar.gz` | 240 MB | descargar del release de GitHub |
| `dist/PulsarLauncher-setup.exe`<br>(copiado en la raíz) | 178 MB | descargar de `pulsarlauncher.com` |
| `win-extracted/{app}/daemon/jre/lib/modules` | 138 MB | reextraer el setup |
| `dist/linux/…/daemon/jre/lib/modules` | 139 MB | desempaquetar el tar.gz |

También se excluye `.wine32/` (prefijo Wine de 636 MB, regenerable con `wineboot`) y `rea/.git/` (historial del repo ajeno).

El `.gitignore` reproduce estas exclusiones; el workflow [`.github/workflows/verificar-limites.yml`](.github/workflows/verificar-limites.yml) falla si alguien vuelve a meter un fichero >100 MB.

---

## Reproducir

```bash
# 1. Instalador Windows y extracción (requiere Wine + innounp 2.71+)
curl -sL -o PulsarLauncher-setup.exe \
  "https://pulsarlauncher.com/api/v1/releases/download?os=windows&arch=x86_64&format=setup.exe"
sha256sum PulsarLauncher-setup.exe   # a6c5435e…098b
WINEPREFIX=.wine32 WINEDEBUG=-all wine tools/innounp/innounp.exe -b -q -x -y \
  -d'Z:\ruta\al\repo\win-extracted' 'Z:\ruta\al\repo\PulsarLauncher-setup.exe'

# 2. Decompilar Pulsar
java -jar tools/vineflower.jar win-extracted/{app}/daemon/lib/launcher-daemon-0.2.26-beta.jar jadx-out/daemon-src

# 3. Minecraft 1.8.9: descarga verificada + remapeo
curl -sLO https://launcher.mojang.com/v1/objects/3870888a6c3d349d3771a3e9d16c9bf5e076b908/client.jar
sha1sum client.jar                   # 3870888a…b908
python3 mc189/srg2classmapping.py
java -cp "mc189/asm/*" mc189/RemapMinecraft.java client.jar mc189/client-1.8.9-mcp2.jar mc189/classmapping-classesonly.json

# 4. Verificación contra bytecode
python3 mc189/verify_hooks.py    > mc189/hooks-report.txt
python3 mc189/verify_targets.py  > mc189/targets-report.txt
```

---

## Aviso

Este repositorio contiene **material de terceros**: código propietario de Mojang decompilado (`mc189-decompiled/`, `mc189/client-1.8.9.jar`) y de Pulsar (`jadx-out/`, `win-extracted/`, `dist/`). Se publica con fines de investigación de seguridad, interoperabilidad y preservación. **No hay licencia**: no se concede ningún derecho sobre el contenido de terceros ni sobre los informes. Ver [`docs/legal/AVISO.md`](docs/legal/AVISO.md).
