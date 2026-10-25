# Reporte 1 (Windows) — Pulsar Launcher frente al EULA y las Usage Guidelines de Minecraft

**Plataforma:** Windows 10/11 x86_64 · **Versión:** 0.2.26-beta
**Artefacto:** `PulsarLauncher-0.2.26-beta-windows-x86_64-setup.exe` — Inno Setup 6.7.0, 187.028.886 B, SHA-256 `a6c5435ea7ae89d7de7d765620b4e489b305ce9d89d424c7b45ba858b751098b`
**Base normativa:** EULA de Minecraft [1] y Minecraft Usage Guidelines [2]

> Esto es análisis técnico-documental, no asesoría legal. Las Usage Guidelines dicen que no emiten opinión sobre casos concretos y que consultes a un abogado si tienes dudas [2].

---

## 0. Resumen ejecutivo (Windows)

Pulsar **no es piratería**. En Windows instala **sin administrador** en `%LOCALAPPDATA%`, descarga el `client.jar` de los servidores de Mojang (SHA-1 verificado contra el manifiesto oficial) y **no redistribuye** ni una clase ni un asset de Minecraft. Tampoco cobra.

Pero incumple **dos requisitos formales** y tiene un **riesgo crítico de plataforma**:

| # | Punto | Estado |
|---|---|---|
| 1 | Distribución de código/assets de Mojang | ✅ **Cumple** |
| 2 | Distribución de "Modded Version" del cliente | ✅ **Cumple** |
| 3 | Mods como obra original | ✅ **Cumple** |
| 4 | Descargas desde fuente autorizada por Mojang | ✅ **Cumple** |
| 5 | Aviso "NOT AN OFFICIAL MINECRAFT PRODUCT…" | ❌ **Incumple** |
| 6 | Mod que verifica acceso *out-of-game* para funciones *in-game* | ❌ **Incumple** |
| 7 | Client ID de Microsoft ajeno | ⚠️ **Riesgo alto** (no es EULA; son ToS de identidad) |
| 8 | Ejecutables sin firma Authenticode, incluido `libcef.dll` | ⚠️ **Zona gris** frente a "authorized source" |
| 9 | Monetización | ✅ **Cumple** |

---

## 1. Lo que el instalador Windows hace y no hace

`install_script.iss` recuperado del propio instalador:

```
AppName=Pulsar Launcher
AppPublisher=metrofico
DefaultDirName={localappdata}\Programs\Pulsar Launcher
PrivilegesRequired=lowest
ArchitecturesAllowed=x64compatible
Compression=lzma2
[Run]           Filename: "{app}\pulsar-launcher.exe"; Flags: postinstall skipifsilent nowait
[InstallDelete] {app}\client, {app}\engine, {app}\daemon, {app}\web
[Icons]         {group}\Pulsar Launcher ; {autodesktop}\Pulsar Launcher (tarea opcional)
[Tasks]         desktopicon
```

Verificado: **no hay sección `[Registry]`**, no crea servicios, no crea tareas programadas, no añade autoarranque y **no pide UAC** (`PrivilegesRequired=lowest` → instalación por usuario en `%LOCALAPPDATA%`). Solo accesos directos "Pulsar Launcher", sin marca de Minecraft en ellos.

Esto es **correcto y favorable**: un launcher de terceros que se instale sin privilegios de administrador es exactamente lo que debería hacer.

---

## 2. Descargas desde fuente autorizada — CUMPLE (punto clave del EULA)

El EULA es explícito:

> "In order to ensure the integrity of our games, we need all game downloads and updates to come from a source that we authorize." [1]

Verificado: Pulsar **no sirve el juego**. Pide el manifiesto a `piston-meta.mojang.com`, el `client.jar` a `launcher.mojang.com` y las librerías a `resources.download.net`, y comprueba el hash. Lo comprobé descargando el JAR real:

```
SHA-1 esperado (manifiesto Mojang): 3870888a6c3d349d3771a3e9d16c9bf5e076b908
SHA-1 calculado (client.jar):       3870888a6c3d349d3771a3e9d16c9bf5e076b908   ✅
33 librerías: 33/33 verificadas
```

El origen **es** el autorizado. **Cumple.**

## 3. Redistribución de material de Mojang — CUMPLE

El EULA prohíbe "give copies of our game software or content to anyone else", incluyendo "modified versions of a game" [1].

Verificado sobre los 13 JARs del paquete Windows (`%LOCALAPPDATA%\Programs\Pulsar Launcher\client` y `\engine`):

```
bootstrap, engine-api, v1_8_9, v26x, asm*, gson, sponge-mixin, error_prone
→ 0 entradas net.minecraft.*  ·  0 entradas assets/minecraft/*
```

Cero código de Mojang dentro de los binarios de Pulsar. **Cumple.**

## 4. Mods como obra original — CUMPLE

El EULA permite mods que sean "something original… that doesn't contain a substantial part of our copyrightable code or content" [1]. Los 639 ficheros Java decompilados son código propio (HUD, emotes, cámara, packs, optimizaciones, 102 mixins). **Cumple.**

## 5. ❌ Falta el disclaimer obligatorio — INCUMPLE

> "Prominently include the disclaimer similar to the following: 'NOT AN OFFICIAL MINECRAFT [PRODUCT/SERVICE/EVENT/etc.]. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT' on your product, listing, description, website/webpage, and all other related materials." [2]

Verificado en el paquete Windows completo:

```
grep -rni "not an official|not approved by|not affiliated" \
     win-extracted/{app}/web  win-extracted/install_script.iss  jadx-out/src-launcher
→ sin resultados
```

**No aparece** en la UI, ni en el instalador, ni en los accesos directos, ni en el diálogo de desinstalación, ni en la web. **Incumple.**

Agrava que el producto se presenta como `Pulsar, launcher y cliente para Minecraft: Java Edition` y que el instalador registra `AppName=Pulsar Launcher` con `UninstallDisplayIcon={app}\pulsar-launcher.exe`. Las guidelines exigen evitar que alguien pueda interpretarlo como oficial [2], y "launcher y cliente para Minecraft" sin descargo va justo en la dirección contraria.

## 6. ❌ El módulo `legit` — INCUMPLE

Las guidelines permiten un mod **solo si**:

> "The mod cannot be used to directly or indirectly verify whether a player owns or has access to out-of-game content, products, or services that affect in-game features and functions" [2]

El módulo `legit` verifica tener Pulsar instalado con un `bootHash` concreto **y a cambio altera funciones in-game**:

- `ClickCap` **descarta clics** por encima del CPS que imponga el servidor (capado activo).
- El servidor puede **denegar la entrada** (`declined_sensors`, `not_integral`, `unknown_host`).
- `GameSignals` se activa o no según lo que pida el servidor.

El ejemplo de la propia guideline (el mod que comprueba un NFT para desbloquear funciones) tiene idéntica estructura. La defensa de Pulsar —"es un antitrampas, no una tienda"— es razonable y probablemente aceptable en la práctica, pero **no encaja en la letra del texto tal como está escrito**.

### En Windows este punto es más fuerte, no más débil

`WindowsGuard` es **más invasivo** que el de Linux y hace algo que Linux no puede: verifica cada DLL cargada contra el **catálogo WinTrust de Microsoft** (`WTD_CHOICE_CATALOG`, `WINTRUST_ACTION_GENERIC_VERIFY_V2`) y añade detección de hilos ajenos, `remoteOpens` y depurador remoto. Es decir, en Windows el módulo **verifica el estado de tu sistema operativo como condición para jugar**, y reporta `name` + `sha256` + `signed` de todos tus módulos cargados. Eso refuerza el argumento de que es una verificación out-of-game con efecto in-game.

## 7. ⚠️ Ejecutables sin firma de código (incluido `libcef.dll`)

| Binario | Authenticode | CFG |
|---|---|---|
| `PulsarLauncher-setup.exe` | ❌ | ❌ |
| `pulsar-launcher.exe` | ❌ | ❌ |
| `pulsar-input.exe` | ❌ | ❌ |
| **`libcef.dll`** | ❌ | ✅ |

Consecuencia para Windows: **SmartScreen** mostrará "Windows protegió tu PC" y Defender puede marcar el instalador como desconocido en el primer contacto. Además, que `libcef.dll` —un componente de Chromium Embedded Framework— esté **sin firmar** mientras que `libcef.dll` sí lleva CFG, sugiere que se tomó de una build no oficial o se reempaquetó. Esto es relevante frente al requisito del EULA de que las descargas vengan de "a source that we authorize" [1]: la parte del *juego* cumple, pero **el runtime de navegador que ejecuta la UI del launcher no tiene procedencia verificable**.

---

## 8. Sobre "me obligan a usarlo" (Windows)

- **Mojang no te obliga.** El launcher oficial está en `minecraft.net` y funciona en Windows 10/11 sin Pulsar. Tu licencia es tuya.
- **El servidor puede exigirlo.** Un servidor privado puede condicionar el acceso a que uses su cliente; si valida el token de `legit`, técnicamente estás obligado para entrar **a ese servidor**.
- **Eso es una condición contractual privada**, y es precisamente la que entra en el supuesto del punto 6.
- **Alternativa real:** launcher oficial en servidores que no exijan Pulsar, o pedir al staff una vía alternativa de verificación.

---

## 9. Qué puedes hacer

1. **Reportar** al formulario de violaciones de guidelines que indican las propias Usage Guidelines [2].
2. **Exigir el disclaimer** al operador del servidor/launcher (es un cambio de una línea en su web).
3. Mientras tanto, si decides usarlo: instala desde `github.com/metrofico/pulsar-releases` y **verifica el SHA-256** (`a6c5435e…098b`), porque SmartScreen no te va a proteger.

## 10. Conclusión

En Windows, Pulsar es un **mod + launcher legítimo en su construcción** —no redistribuye, no cobra, se instala sin privilegios y descarga el juego del origen autorizado— que **incumple dos requisitos formales de las Usage Guidelines** (el disclaimer obligatorio y la prohibición de mods que condicionen funciones in-game a verificación out-of-game), y que añade un **riesgo de plataforma** al no firmar ninguno de sus ejecutables, incluido el runtime de Chromium que muestra su interfaz.
