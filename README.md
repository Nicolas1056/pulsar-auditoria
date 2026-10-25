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

## Hallazgos principales

| # | Hallazgo | Severidad | Documento |
|---|---|---|---|
| 1 | El **refresh token de Microsoft** se pasa como argumento `-Command` a `powershell.exe`, legible por cualquier proceso del usuario | 🔴 Crítico | [win/02](docs/win/02-privacidad-y-seguridad.md#1--crítico--el-refresh-token-viaja-en-la-línea-de-comandos) |
| 2 | Falta el disclaimer obligatorio **"NOT AN OFFICIAL MINECRAFT PRODUCT…"** | ❌ Incumple | [compliance/01](docs/compliance/01-eula-y-usage-guidelines.md#5--falta-el-disclaimer-obligatorio--incumple) |
| 3 | El módulo `legit` verifica acceso *out-of-game* y altera funciones *in-game* (capado de CPS, denegación de entrada) | ❌ Incumple | [compliance/01](docs/compliance/01-eula-y-usage-guidelines.md#6--el-módulo-legit--incumple) · [win/03](docs/win/03-modulo-legit.md) |
| 3b | **`SignalsChoice.mandatory()` está hardcodeado**: no existe ruta de código para que el jugador rechace compartir los sensores | 🔴 Crítico | [win/03](docs/win/03-modulo-legit.md#4--el-jugador-no-puede-rechazar-los-sensores) |
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
