# Pulsar Launcher 0.2.26-beta — Ingeniería inversa

**Carpeta de trabajo:** `/home/user/pulsar-rea` (todo el trabajo está contenido aquí)
**Fecha del análisis:** 2026-10-25
**Estado:** análisis estático completo de las capas nativa, web y Java; verificación criptográfica de la cadena de actualización.

---

## 1. Material obtenido

| Artefacto | Origen | Tamaño | SHA-256 |
|---|---|---|---|
| `PulsarLauncher-setup.exe` | URL pedida (`pulsarlauncher.com/api/v1/releases/download?...&format=setup.exe`) → redirige a `github.com/metrofico/pulsar-releases` | 187.028.886 B | `a6c5435e…098b` |
| `dist/PulsarLauncher-linux-x86_64.tar.gz` | mismo release, build Linux | 251.906.485 B | `4f20b18e…fe83` |
| `win-extracted/` | instalador Windows extraído (721 ficheros) | ~356 MB | — |
| `dist/linux/PulsarLauncher-0.2.26-beta-linux-x86_64/` | build Linux desempaquetado | ~645 MB | — |
| `jadx-out/` | 639 ficheros `.java` decompilados | 4,6 MB | — |

Versión: **0.2.26-beta**. Nota: mientras se hacía el análisis se publicó la 0.2.27-beta (el `releases.json` ya la sirve).

### Cadena de redirecciones
`pulsarlauncher.com/api/v1/releases/download` → `302 /downloads/PulsarLauncher-0.2.26-beta-windows-x86_64-setup.exe` → `302` a `github.com/metrofico/pulsar-releases/releases/...`. Es decir, el binario se sirve desde GitHub Releases bajo la cuenta `metrofico`; el dominio propio solo actúa de índice.

---

## 2. Instalador Windows (lo que pediste descargar)

- Firma de fichero: `PE32 executable for MS Windows 6.01 (GUI), Intel i386, 11 sections`.
- **Inno Setup 6.7.0 (Unicode)**, compresión `lzma2`, `PrivilegesRequired=lowest`, `x64compatible`.
- Sin cifrado de setup (`EncryptionUse = euNone`).
- **No está firmado Authenticode**: el directorio `IMAGE_DIRECTORY_ENTRY_SECURITY` está ausente en el setup y en los tres PE internos (`pulsar-launcher.exe`, `pulsar-input.exe`, DLLs). No hay firma de código en toda la distribución Windows.

### Extracción
`innoextract 1.9` y su `master` **fallan**: solo reconocen datos hasta `Inno Setup Setup Data (6.4.0.1)` y aquí el loader usa `revision 2` del offset table (verificado leyendo `Shared.Struct.pas` del repo `jrsoftware/issrc`: `SetupLdrOffsetTableVersion = 2`, `TSetupLdrOffsetTable` con `Int64` y `SetupLdrOffsetTableResID = 11111`). El *issue* #201 del repo `dscharrer/innoextract` sigue abierto por lo mismo.

Solución aplicada: parseo propio del `TSetupLdrOffsetTable` en `0x126b34` (CRC de tabla validado: `0xfed90c71`), confirmando `SetupID = "Inno Setup Setup Data (6.7.0)"`, `Offset0 = 0xb1313ea`, `OffsetEXE = 0xb13c650`; extracción final con **`innounp 2.71.2`** bajo Wine (`-x -b -q -y`), que sí soporta hasta Inno 7.x y además recupera el **`install_script.iss` original**.

### Contenido del `.iss` (revelador)
```
AppName=Pulsar Launcher
AppPublisher=metrofico
AppPublisherURL=https://github.com/metrofico/PulsarLauncher
DefaultDirName={localappdata}\Programs\Pulsar Launcher
Compression=lzma2 ; ArchitecturesAllowed=x64compatible ; PrivilegesRequired=lowest
[Run] Filename: "{app}\pulsar-launcher.exe"; Flags: postinstall skipifsilent nowait
[InstallDelete] {app}\client, {app}\engine, {app}\daemon, {app}\web
```
No escribe claves de registro propias, no instala servicios, no añade tareas programadas ni asociaciones. Es un **desempaquetador**: el launcher se autoactualiza por su cuenta (ver §6).

---

## 3. Arquitectura real

No es Electron. Es **CEF (Chromium Embedded Framework) + shell nativo C++ + daemon Java 25 + cliente Minecraft moddeado**.

```
pulsar-launcher(.exe)   shell C++/CEF 151.3.23 (chromium 151.0.7922.170), stripped, ~1 MB
  └─ libcef.so / libcef.dll        268 MB / 285 MB
  └─ web/index.html + assets       UI React compilada (352 KB JS minificado)
  └─ lanza → daemon/ (JRE 25 Temurin embebido, com.metrofico.pulsar.launcher.daemon.Daemon)
       ├─ launcher-daemon-0.2.26-beta.jar   34 clases: auth, sesión, métricas, updates, emotes, legit…
       ├─ launcher-0.2.26-beta.jar          53 clases: meta Mojang, resolución de librerías, Java runtimes, launch
       └─ client/ + engine/
            ├─ bootstrap-0.2.26-beta.jar    24 clases: GameClassLoader, Mixin, legit boot, guard
            ├─ engine-api-0.2.26-beta.jar  186 clases: API del cliente (hud, emotes, pack, legit, guard…)
            ├─ v1_8_9-0.2.26-beta.jar      194 clases: implementación 1.8.9 (mixin 1.8.9 ofuscado)
            └─ v26x-0.2.26-beta.jar        148 clases: implementación Minecraft moderno
```

### Shell nativo (`pulsar-launcher`)
Rutas de build filtradas en los strings: `/home/teoremabit/actions-runner/_work/PulsarLauncher/PulsarLauncher/launcher-shell/...`. Variables de entorno: `PULSAR_ROOT`, `PULSAR_EXECUTABLE`, `PULSAR_INSTALL_FORMAT` (`APPIMAGE`), `PULSAR_CLIENT_DIR`, `PULSAR_ENGINE_DIR`, `PULSAR_JAVA_HOME`, `PULSAR_DAEMON`, `PULSAR_WEB_DIR`, `PULSAR_WEB_URL`, `PULSAR_CEF_LOG`.

### Puente UI ↔ nativo
Un solo objeto expuesto al JS: `window.pulsar` con `pulsar.send(json: string)`, evento `pulsar.message`, callback `onMessage`. Sin IPC de Electron (los "fuses" no aplican).

Comandos de ventana que maneja el **C++** (más rápido que el daemon):
`window.tray.state` (`hidden`), `window.signin.open`, `window.signin.close`, `window.minimize`, `window.maximize`, `window.restore`, `window.quit`, `window.close`, `window.notify`, `window.background`.
Respuesta: `{"id":…,"ok":true,"result":{}}`. Evento de login: `{"event":"signin","state":…,"url":…}` usando `oauth20_authorize.srf` / `oauth20_desktop.srf`.

### Daemon Java: 53 comandos
`auth.begin|cancel|current|logout|remove|select|window.begin|window.complete`, `data.open`, `emotes.claim|options|refresh|setOptions|state`, `game.status`, `hello`, `input.grant|status`, `java.list`, `launch`, `legit.appeal|mark|status`, `logs.open`, `metrics.forget|resetId|sendHw|sendSession|status`, `packs.clearCache|list|revoke`, `preferences.get|set`, `prepare`, `profiles.delete|list|save|select`, `push.dismiss|set|state`, `report.create`, `shutdown`, `update.apply|check|download|retry|state|verify`, `url.open`, `versions`, `cancel`.

Protocolo: JSON línea a línea `{id, cmd, args}` → `{id, ok, result}` o `{id, ok:false, error:{code,title,message,detail}}` (ver `Protocol.java`). Por defecto por stdio; existe modo desarrollo `ws://127.0.0.1:port` (`WebSocketTransport`).

---

## 4. Autenticación

`AuthEndpoints.java` trabaja con **dos client IDs de Microsoft**:

- `00000000402b5328` → *Live Connect* (el client ID histórico del launcher oficial de Minecraft), scope `service::user.auth.xboxlive.com::MBI_SSL`. Es el valor por defecto.
- `d35d8c14-9bcf-473d-ac3d-f7f99a82737a` → client ID propio, flujo **Azure AD device code** (`login.microsoftonline.com/consumers/oauth2/v2.0/devicecode`), scope `XboxLive.signin offline_access`.
- Se puede forzar otro con la variable `PULSAR_MSA_CLIENT_ID`; también hay `withBase(...)` para apuntar a un proxy propio (`/devicecode`, `/token`, `/xbl`, `/xsts`, `/mc/login`, `/mc/profile`, `/authorize`, `/desktop`).

Cadena: MSA → `user.auth.xboxlive.com/user/authenticate` → `xsts.auth.xboxlive.com/xsts/authorize` → `api.minecraftservices.com/authentication/login_with_xbox` → `/minecraft/profile`. Clásico; nada exótico.

**Almacenamiento de tokens:** `SecretStores` → `LinuxKeyrings` (D-Bus `org.freedesktop.secrets`) → `secret-tool`, o, si no hay keyring, `MachineBoundFile`: AES-GCM con IV de 12 bytes y clave derivada del `/etc/machine-id` / `/var/lib/dbus/machine-id` (servicio `com.metrofico.pulsar`). En macOS usa `security`, en Windows el almacén nativo. Tokens nunca en claro en `account.json`.

---

## 5. Backend y endpoints (todos sobre `https://pulsarlauncher.com`)

`pulsar-version.properties` del JAR:
```
version=0.2.26-beta
serviceUrl=https://pulsarlauncher.com
realtimeUrl=wss://rt.pulsarlauncher.com/v1/ws?client=game
```

| Endpoint | Uso |
|---|---|
| `POST /api/v1/metrics` | telemetría (`kind: hw` / `kind: session`), Bearer del launcher |
| `DELETE /api/v1/metrics/me?installId=` | borrado de métricas del propio usuario |
| `GET /api/v1/legit/mark`, `POST /api/v1/legit/appeal` | estado/apelación del sistema antitrampas |
| `GET /api/v1/launcher/session`, `/subscriptions`, `/stream?since=`, `/wardrobe`, `/emotes/{id}/claim` | sesión, push, armario y emotes |
| `GET /api/v1/releases.json?channel=` | manifiesto de actualización |
| `GET /api/v1/tree?channel=&os=&arch=&version=` | árbol de instalación para updates por objeto |
| `/downloads/{archivo}`, `/downloads/objects/{xx}/{sha}.gz` | binarios y objetos |
| `wss://rt.pulsarlauncher.com/v1/ws?client=game` | WebSocket en tiempo real (relay social + `legit.*`) |

La UI web, en modo mock, consume además `banners.json`, `marketplace.json`, `emotes.json` (carpeta `web/mock/`), y soporta `?content=` / `localStorage.pulsar.contentUrl` para apuntar a otro origen (`https://pulsarlauncher.com/api/v1/`).

---

## 6. Actualizaciones: cadena firmada correctamente

`ReleaseManifest` valida **Ed25519** sobre una serialización JSON canónica (claves ordenadas, sin espacios, `\u` para controles, números sin `.0`).

Verificado de forma independiente con Python sobre el manifiesto en vivo:

```
canonical len: 1577
SIGNATURE VALID (Ed25519, canonical JSON)
pubkey sha256: dd28e6cc59a8a8a1c16be145adc962badebad3d421ed353dda8c5e31e0eef27b
```
Clave pública embebida en el JAR (`pulsar-release.pub`, `MCowBQYDK2VwAyEA7p/bDQV1ob3OdeaxdW4mi2pDdC+WMLoEdxHRkaPZFNk=`).

Además cada paquete lleva `sha256` + `size` y los objetos del árbol se descargan con verificación (`VerifiedDownloader`, `ResumableDownloader`). **La actualización está bien hecha** (a diferencia de la ausencia de firma PE): sin la clave privada no se puede inyectar un release falso.

---

## 7. Telemetría

`MetricsService` genera un `installId` aleatorio (16 bytes base64url, ~22 chars) persistido en `metrics-id` y envía:

- `kind: hw` (una vez/24 h por versión de launcher): SO y build, marca de CPU, núcleos/hilos, RAM total en GB, marca de GPU, resolución y Hz, versión del launcher, versión de Java, locale.
- `kind: session`: datos de la partida + `installId` + `launcher`.

**Opt-in real:** `Preferences.metricsOptInOn()` exige `metricsOptIn == TRUE`; el `send` falla con `no_session` sin sesión. Con `metrics.forget` se borra en servidor por `installId`. La UI llama a esto desde Ajustes.

---

## 8. "Legit" — el sistema antitrampas del cliente (lo más interesante)

`engine-api/.../api/legit/*` es un **módulo de integridad y telemetría de inputs** con canal propio, independiente del canal de packs:

- **Canal**: `wss://rt.pulsarlauncher.com/v1/ws?client=game` con `Authorization: Bearer <token del launcher>`. Envelope `{t, id, d}`. Heartbeat 15 s, reportes cada 5 s, señales cada 10 s, timeout de attestation 4 s, backoff exponencial hasta 60 s.
- **Sala por servidor**: `relay.join`/`relay.leave` con el host como `room` (regex `[a-z0-9.-]{1,253}`) y `relay.roster`/`relay.member` para saber qué jugadores del servidor llevan Pulsar (`social/PulsarPlayers`).
- **Examen de arranque** (`BootSeal.exam`): versión del cliente, versión de Minecraft, `buildId` (atributo `Pulsar-Build` del manifiesto del JAR), SO, `bootHash` (hash del conjunto de JARs Pulsar), **SHA-1 del `minecraft.jar`** y `origins`, donde `origins` enumera argumentos JVM inyectores (`-javaagent`, `-agentlib`, `-agentpath`, `-Xbootclasspath`, `--patch-module`, `-Djava.system.class.loader`) más `game-sha1-mismatch`. Se envía como `legit.exam.begin`.
- **Attestation por servidor**: el servidor de juego manda `legit.challenge` con `nonce` y `sensors` (obligatorio o no); el cliente responde `legit.attestation` con `token` o `error` (`declined_sensors`, `unknown_host`, `no_channel`, `cloud_unavailable`, `not_integral`). El servidor de juego puede **exigir** señales o denegar la entrada.
- **Pruebas de código** (`ProbeEngine`): el servidor pide `legit.probe` con `salt` y `classes` o `ranges` (jar/offset/length); el cliente devuelve `SHA-256(salt ‖ bytes)` truncado a 16 bytes por elemento, con normalización de UUIDs en los class bytes. Es un **challenge-response sobre el bytecode realmente cargado**, para detectar JARs alterados.
- **Sensores** (`GameSignals`):
  - `ClickSensor`: intervalos entre clics en buckets de 5 ms (80), duración de pulsación (40 buckets), `cpsMax`, secuencias y repeticiones en una ventana de 128, tolerancia 2 ms, ráfagas 400 ms. Detecta *autoclicker*/macro por regularidad.
  - `AimSensor`: media y varianza (Welford) de Δyaw/Δpitch por tick y contador de *snaps* (giro >30° seguido de movimiento <0.001°): detección de *aimbot*.
  - `ReachSensor`: distancia del ojo al AABB del objetivo en 160 buckets de 0.05; alimentado por `AttackReachMixin` en ambas versiones (`MultiPlayerGameMode.attack` en moderno, `method a(Lwn;Lpk;)V` en 1.8.9).
  - `ClickCap`: **capado activo de CPS** por botón (hasta 255) que el servidor puede imponer vía `cpsCap`/`cpsCapRight` en el challenge; los clics por encima del límite se **descartan** (`ClickCap.drop`).
  - `LiveSignals`: resumen por segundo (CPS izq/der, capados) enviado solo cuando `EntryPass.activeHost() != null`.
- **Lado guard** (`api/guard/*`): `LinuxGuard` lee `/proc/self/status` (`TracerPid`) y `/proc/self/maps` para listar `.so` cargados no incluidos en `KnownModules` (whitelist de ~80 DLL/so: drivers, overlays, LWJGL, JVM…), con SHA-256 de cada módulo y filtro de "solo root" (uid_map del user namespace). `WindowsGuard` usa FFM (Panama) con `IsDebuggerPresent`, `CheckRemoteDebuggerPresent`, snapshots de hilos, `WTD_CHOICE_FILE/CATALOG` de WinTrust para saber si un módulo está **firmado** por catálogo del sistema, y detecta hilos "extranjeros". `MacGuard` equivalente.
- **SelfGuard**: `agents` inyectados, `DisableAttachMechanism`, presencia de hilo `Attach Listener`, `ClassWatch` (transformer que marca clases definidas fuera del classpath y clases redefinidas), `guardFlags` (`attach-enabled`, `attach-listener`, `class-watch-off`).
- **`StaffXrayGate`**: órdenes firmadas desde el servidor (ventana máx. 120 s, grant máx. 900 s, skew 300 s) que activan durante un tiempo limitado una lista de elementos a ocultar (`Order(network, hide, validMs, nonce)`), con deduplicación por `SEEN`. Es decir, soporte nativo para que el staff de un servidor pueda inspeccionar ("xray") a un jugador.

Valoración: es un antitrampas de cliente **cooperativo** (el servidor de Minecraft manda desafíos por el WebSocket de Pulsar). No verifica nada si el usuario ejecuta el cliente sin el módulo `legit` (`-Dpulsar.legit=off` lo desactiva explícitamente) o con una build no oficial; la única atestación "dura" contra manipulación es `bootHash`/`mojangSha1` + el challenge de bytecode, todo emitido por el propio cliente. Solo es sólido en servidores que exijan token.

No se ha encontrado en `engine-api`, `v1_8_9` ni `v26x` ningún *reach* extendido, *aimbot*, *velocity*, *nofall* ni *hitbox*: el único código de "reach" es el sensor que **mide** para denunciar. Funciones de cliente presentes y legítimas en su mayoría: `Freecam`, `Zoom`, `DynamicFov`, `ToggleSneakSprint`, `HUD` (coords, FPS, CPS, ping, scoreboard), `Nametags`, `Crosshair`, emotes, cámara/cinemáticas, packs de servidor (`PackClient`, `PackVerifier`), `Visuals17`, `OcclusionCuller`/`EntityCache` (optimizaciones de render).

---

## 9. Arranque del juego

`ArgumentBuilder` + `GameLauncher` son una implementación estándar de lanzamiento de Minecraft: resuelven `version_manifest_v2.json` de `piston-meta.mojang.com`, librerías de `resources.download.minecraft.net`, evalúan `rules` por plataforma/features, extraen nativos, eligen JRE de Adoptium (`api.adoptium.net`) o del sistema (`/usr/lib/jvm`, `/opt/java`, `/usr/java`), y arman la línea `java` sustituyendo `${natives_directory}`, `${classpath}`, etc. Para el cliente Pulsar añaden al classpath `bootstrap-*.jar` + `engine-api-*.jar` + `v1_8_9|v26x`, fijan `-Dpulsar.*` (`data.dir`, `client.version`, `game.sha1`, `realtime.url`, `input`, `input.enforce`, `legit`) y arrancan `com.metrofico.pulsar.bootstrap.Main`, que monta un `GameClassLoader` aislado, inicializa Mixin y lanza el `mainClass` real.

`LaunchSession.developmentOffline()` permite arranque offline con UUID `OfflinePlayer:<nombre>` (modo desarrollo).

---

## 10. Repo `morluto/rea` — qué se usó y qué no

`rea` (REA, "Reverse Engineer Anything", v6.8.0, MIT) se instaló con `npm i -g rea-agents` y se usó como bancada:

| Comando REA | Resultado |
|---|---|
| `rea analyze <dir>` / `analyze-javascript-application web/` | inventario y grafo de la app JS; **timeout** a 300 s y a 1500 s sobre el bundle minificado de 352 KB (comportamiento esperado: no está pensado para bundles ofuscados gigantes) |
| `rea inspect-desktop-application .` | identificación de runtime y candidatos por cabecera |
| `rea inspect-electron-fuses pulsar-launcher` | correcto: **no** es Electron, informa formato `elf` y fuses ausentes |
| `rea inspect-native-exports` | ELF amd64, 177 entradas dinámicas examinadas, **0 exports** (binario stripped con visibilidad por defecto) |
| `rea inspect-pe-certificates` | detecta la ausencia de `IMAGE_DIRECTORY_ENTRY_SECURITY` en los tres PE |
| `rea inspect-with-objdump` | wrapper de `objdump` con evidencia (salida guardada) |
| `rea doctor` / `capabilities` | Hopper/Ghidra/IDA/JADX no configurados en este host; el proveedor `rea-artifact-graph` sí disponible |

Conclusión honesta: REA sirvió muy bien para **clasificar** el artefacto (runtime, formato, firmas, exports) y para producir evidencia reproducible; **no** sustituye a un decompilador de Java (no tiene backend para `.jar`/bytecode: sus targets son nativo, .NET, JS/Electron, Android y web) y se atasca con bundles JS minificados. Para el bytecode la herramienta efectiva fue **Vineflower 1.11.1** (`tools/vineflower.jar` → `jadx-out/`).

### Herramientas del entorno (todo dentro de `pulsar-rea/`)
```
tools/vineflower.jar         decompilador CFR/Fernflower moderno
tools/innounp/innounp.exe    desempaquetador Inno Setup 2.71.2 (vía Wine)
dist/                        descargas originales
win-extracted/{app}/         build Windows extraído + install_script.iss
dist/linux/.../              build Linux extraído
jadx-out/                    fuentes Java decompiladas (639 ficheros)
evid/                        evidencia JSON de REA
.wine32/                     prefijo Wine usado (Windows 11 emulado)
```

---

## 11. Hallazgos y riesgos

1. **Sin firma Authenticode en Windows** (setup, launcher, input, DLLs). El sistema de actualización sí está firmado (Ed25519) y verifica SHA-256 por objeto, así que el riesgo real es de *primer contacto*: el `.exe` que se descarga de la web no tiene ninguna atestación criptográfica de su procedencia. Un ZIP o instalador interceptado en esa primera descarga no sería detectable por el usuario.
2. **Client ID de Live Connect ajeno** (`00000000402b5328`, el del launcher oficial de Mojang) usado por defecto. Microsoft lleva años restringiendo ese client ID; es el patrón habitual de launchers de terceros, pero conviene saberlo.
3. **Telemetría de hardware y sesión** con `installId` persistente. Opt-in correcto y borrado bajo demanda, pero `hw` se manda automáticamente una vez al día **por versión** cuando el opt-in está activo.
4. **`legit` es un antitrampas de cliente cooperativo**, no verificable fuera de los servidores que emiten desafíos; desactivable con `-Dpulsar.legit=off`. Los datos que recoge (CPS, distribución de giros, distancias de golpe, módulos cargados con hash, argumentos JVM, hilos de depuración) son sensibles y se envían a un tercero.
5. **`StaffXrayGate`** permite a un servidor ordenar al cliente que oculte elementos temporalmente: interesante como herramienta de moderación y también como superficie de abuso si la clave de firma se filtra.
6. **`PULSAR_MSA_CLIENT_ID`, `PULSAR_WEB_URL`, `withBase(...)`**: la build permite redirigir autenticación, UI y servicio; cómodo para desarrollo, arriesgado si un tercero controla esas variables de entorno.
7. Sin *exploits* evidentes ni puertas traseras en lo revisado; el código está limpio y bien estructurado, con comentarios en español. Los strings de build revelan la ruta del runner de CI (`/home/teoremabit/actions-runner/...`), lo que confirma compilación por GitHub Actions en un repo privado (`metrofico/PulsarLauncher`).

---

## 12. Reproducir

```bash
cd /home/user/pulsar-rea
# 1) descarga (redirige a GitHub Releases)
curl -sL -o PulsarLauncher-setup.exe "https://pulsarlauncher.com/api/v1/releases/download?os=windows&arch=x86_64&format=setup.exe"
# 2) extracción del instalador Inno 6.7 (requiere Wine + innounp 2.71+)
WINEPREFIX=.wine32 WINEDEBUG=-all wine tools/innounp/innounp.exe -b -q -x -y \
  -d'Z:\home\user\pulsar-rea\win-extracted' 'Z:\home\user\pulsar-rea\PulsarLauncher-setup.exe'
# 3) build Linux (misma release) y decompilación
tar xzf dist/PulsarLauncher-linux-x86_64.tar.gz -C dist/linux
java -jar tools/vineflower.jar dist/linux/*/daemon/lib/launcher-daemon-0.2.26-beta.jar jadx-out/daemon-src
# 4) evidencia con REA
export PATH=$HOME/.local/bin:$PATH
rea inspect-pe-certificates ./PulsarLauncher-setup.exe
rea inspect-electron-fuses ./dist/linux/*/pulsar-launcher
```

---

## 13. Aviso

Análisis estático con fines de interoperabilidad, auditoría de seguridad y preservación, sobre software descargado públicamente. No se ha modificado, parcheado ni redistribuido el launcher. El código y los assets siguen siendo propiedad de sus autores; las conclusiones sobre `legit` son lectura del bytecode, no una auditoría de extremo a extremo del servidor.
