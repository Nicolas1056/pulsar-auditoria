# Reporte 2 (Windows) — Privacidad, integridad de datos y seguridad real

**Plataforma:** Windows 10/11 x86_64 · **Versión:** 0.2.26-beta · **Artefacto:** `PulsarLauncher-0.2.26-beta-windows-x86_64-setup.exe` (SHA-256 `a6c5435ea7ae89d7de7d765620b4e489b305ce9d89d424c7b45ba858b751098b`)

> **Nota de método.** Este reporte sustituye al anterior hecho sobre el build Linux. Verifiqué primero si el código era el mismo: los `.class` de los 13 JARs son **idénticos byte a byte** entre Windows y Linux (las diferencias de SHA-256 son solo CRLF en `.json`, `.pub` y `META-INF/services`; el `mixins.json` es idéntico en contenido con 102 mixins). **Pero las rutas de código específicas de Windows son distintas y algunas cambian el veredicto.** Todo lo que sigue está verificado sobre los binarios de Windows.

---

## 0. Respuesta directa a "¿estoy seguro usando este launcher en Windows?"

**No es un virus.** Sin cifrado de archivos, sin persistencia, sin gusanos, sin keylogger, sin captura de pantalla, sin robo de archivos, y su auto-actualización está firmada con Ed25519 (verificada criptográficamente).

**Pero en Windows hay cuatro cosas que el análisis de Linux no mostraba y que cambian el cuadro:**

1. 🔴 **Tu refresh token de Microsoft se pasa en la línea de comandos de `powershell.exe`.** Cualquier proceso de tu sesión puede leerlo (Task Manager → columna de línea de comandos, `wmic process get commandline`, WMI, EDR). **Esto es el hallazgo más grave del análisis.**
2. 🟠 **El ratón crudo usa `RIDEV_INPUTSINK`**, lo que significa que `pulsar-input.exe` recibe clics y movimientos **aunque la ventana no tenga el foco**.
3. 🟠 **Modifica el Registro** (`HKCU\Software\Microsoft\DirectX\UserGpuPreferences`) por defecto, vía `reg.exe add /f`.
4. 🟠 **No tiene firma Authenticode ni Control Flow Guard**, y se instala en `%LOCALAPPDATA%` sin privilegios de administrador.

Veredicto para Windows: **riesgo medio** (sube desde "medio-bajo" del análisis Linux por el punto 1).

---

## 1. 🔴 CRÍTICO — El refresh token viaja en la línea de comandos

`SecretStores.Dpapi.put()` —el backend de secretos que Pulsar usa en Windows— construye esto:

```java
String b64 = Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
String script = "Add-Type -AssemblyName System.Security; $p=[Convert]::FromBase64String('"
   + b64
   + "');[IO.File]::WriteAllBytes('" + file + "',"
   + "[Security.Cryptography.ProtectedData]::Protect($p,$null,'CurrentUser'))";
SecretStores.run(List.of("powershell", "-NoProfile", "-NonInteractive", "-Command", script), null);
```

El `value` es el **refresh token de Microsoft** (`AuthService.REFRESH_KEY = "msa-refresh-token"`). Se codifica en Base64 y **se concatena dentro del script**, que va como argumento `-Command` a `powershell.exe`.

### Por qué es grave

El bloque `CommandLine` de un proceso es **legible por otros procesos del mismo usuario** en Windows. Base64 **no es cifrado**: `echo <cadena> | base64 -d` y tienes el token.

| Vector | Alcance |
|---|---|
| Administrador de tareas → columna "Línea de comandos" | visible mientras el proceso vive (decenas/cientos de ms) |
| `wmic process get commandline` / `Get-CimInstance Win32_Process` | idem |
| Cualquier malware corriendo **como tu usuario** (sin admin) en bucle de sondeo | lo captura con alta probabilidad |
| EDR / herramientas de auditoría / software de monitorización corporativo | registrado en logs durante años |
| Herramientas de volcado de memoria/paging | residuo del `CommandLine` en el heap |

Nota: el `.exe` de `powershell.exe` **no abre ventana de consola** (el launcher es `Subsystem: Windows GUI` y el proceso hijo hereda consola oculta), así que el usuario no ve nada. El riesgo es de exposición, no de visibilidad.

### Mitigación real

El token en reposo **sí** está bien protegido: DPAPI con ámbito `CurrentUser` y el archivo `.dpapi` en `AppData\Local`. El fallo es **transitorio en el momento de escribir/leer**. Un diseño correcto pasaría el secreto por `stdin` (que es lo que hace el backend Linux: `SecretStores.run(..., stdin)` con `secret-tool store`).

### Además: se lanza PowerShell en cada operación

`Dpapi.available()` ejecuta `powershell -Command "Add-Type -AssemblyName System.Security; 'ok'"` al arrancar, y `get`/`put` lanzan un `powershell.exe` nuevo cada vez. Coste real: arranque de PowerShell (~300-800 ms) cada vez que se toca un token, y **superficie de ataque**: `powershell.exe` es el binario más vigilado por EDR/AV, así que un launcher de Minecraft generando procesos de PowerShell de forma recurrente es exactamente el patrón que dispara alertas (y el que imita el malware fileless). Es molesto, no malicioso, pero explica falsos positivos.

---

## 2. 🟠 `RIDEV_INPUTSINK` — el ratón se lee sin foco

`WindowsRawMouse189$User32` (JNA) declara:

```java
CreateWindowExW, RegisterRawInputDevices, GetMessageW, DefWindowProcW, GetRawInputData
```

y el string del binario confirma el flag:

```
RIDEV_INPUTSINK
```

`RIDEV_INPUTSINK` significa que el dispositivo registrado entrega eventos **aunque la ventana esté en segundo plano**. Comparado con el build Linux (que solo abre `/dev/input/event*` de tipo ratón/touchpad con `uaccess`), aquí el comportamiento por defecto es más amplio.

**Alcance real (importante, para no exagerar):** solo ratón. `GetRawInputData` con `RID_INPUT` sobre un `RAWMOUSE` entrega movimiento y botones; **no teclas**. No hay `SetWindowsHookEx`, ni `WH_KEYBOARD`, ni `GetAsyncKeyState`, ni `GetKeyboardState`, ni `SendInput`, ni `BitBlt`/`GetDC`. Verificado sobre `pulsar-input.exe`: sus únicas DLLs son `HID.DLL`, `CFGMGR32.dll`, `USER32.dll`, `KERNEL32.dll`.

**Riesgo práctico:** con la ventana minimizada en la bandeja, el proceso sigue recibiendo tus clics de ratón (no contenido, solo posición/botones/tiempo). El código lo usa para el "ratón crudo" del juego y para el `MouseLock`, pero el flag no es el mínimo necesario para eso.

---

## 3. 🟠 Modifica el Registro de Windows

`GpuPreference.windows()`:

```java
registry.run(List.of("add",
    "HKCU\\Software\\Microsoft\\DirectX\\UserGpuPreferences",
    "/v", java, "/t", "REG_SZ", "/d", "GpuPreference=2;", "/f"));
```

- **Dónde:** `HKCU\Software\Microsoft\DirectX\UserGpuPreferences` (solo tu usuario, no sistema).
- **Qué:** fuerza "alto rendimiento" para el `java.exe` que lanza el juego → usa la GPU dedicada en portátiles.
- **Cómo:** `reg.exe add … /f` (sobrescribe sin preguntar).
- **Cuándo:** por defecto, al lanzar el juego.

Solo lee la preferencia existente y **no la sobrescribe si ya existe** (`query` primero). Es reversible (borra el valor) y HKCU no requiere admin. Pero es una modificación del sistema **no anunciada en ningún diálogo** y no aparece en el instalador (`install_script.iss` **no tiene sección `[Registry]`**, así que la escritura ocurre en tiempo de ejecución, no en la instalación).

Aparte: **no** crea servicios, **no** crea tareas programadas, **no** añade autoarranque, y no usa `java.util.prefs` (que ensuciaría el registro por otra vía).

---

## 4. 🟠 Endurecimiento ausente en los ejecutables

| Binario | Authenticode | CFG (`GUARD_CF`) | ASLR | DEP/NX |
|---|---|---|---|---|
| `PulsarLauncher-setup.exe` | ❌ | ❌ | ✅ | ✅ |
| `pulsar-launcher.exe` | ❌ | ❌ | ✅ (`HIGH_ENTROPY_VA`) | ✅ |
| `pulsar-input.exe` | ❌ | ❌ | ✅ | ✅ |
| `libcef.dll` (Chromium) | ❌ | ✅ | ✅ | ✅ |

**Firma de código:** ausente en los tres ejecutables propios **y también en `libcef.dll`**. Esto es relevante: CEF lo distribuye Spotify con firma de Spotify AB, y la build oficial de Chromium Embedded Framework también viene firmada por sus autores. Que esta `libcef.dll` esté **sin firmar** sugiere que fue recompilada o desempaquetada localmente, no que se tomó un binario oficial. No es prueba de nada malo, pero significa que **no puedes verificar que ese CEF sea el de upstream**.

**Control Flow Guard:** `libcef.dll` sí lo tiene; los ejecutables propios no. Sin CFG, un fallo de corrupción de memoria en `pulsar-input.exe` o en el shell es más explotable.

**Consecuencia para ti:** la firma Ed25519 protege las *actualizaciones*, no la *primera descarga*. Verifica el SHA-256 a mano.

---

## 5. Persistencia y huella en disco (Windows)

**Instalación:** `%LOCALAPPDATA%\Programs\Pulsar Launcher` con `PrivilegesRequired=lowest` → **sin UAC, sin admin**. Es una decisión correcta de seguridad.

**Datos en `%APPDATA%\.minecraft`** (la carpeta estándar de Minecraft, no una propia):
```
account.json        cuentas y uuid seleccionado
preferences.json    preferencias
client-options.json opciones del cliente
secrets/*.dpapi     tokens cifrados con DPAPI (CurrentUser)
metrics-id          identificador persistente  ← ver §6
metrics-hw-sent     marca temporal del último envío
legit-state.json    estado del módulo legit
emotes/session.json token del relay en claro (JSON)
```

**Un detalle:** `emotes/session.json` guarda el token del relay **como JSON sin cifrar**, mientras el refresh token de Microsoft sí va cifrado con DPAPI. Inconsistente.

**Desinstalación:** limpia (`[InstallDelete]` borra `client`, `engine`, `daemon`, `web`). Lo que queda es lo de `%APPDATA%\.minecraft`, que se comparte con el juego legítimo y por tanto **no se borra** — correcto, pero significa que `metrics-id` puede sobrevivir a la desinstalación.

---

## 6. Telemetría en Windows: qué cambia respecto a Linux

El esquema es el mismo, pero en Windows **se recogen dos campos que en Linux no existen**:

| Campo | Cómo se obtiene en Windows | Nota |
|---|---|---|
| `os.build` | `os.version` | **solo se envía si el SO es Windows** (`osBuild()` devuelve `""` en otros) → número de build exacto (p.ej. `10.0.22631`) |
| `cpu.brand` | **`powershell -Command "(Get-CimInstance Win32_Processor).Name"`** | lanza PowerShell |
| `gpu.brand` | **`powershell -Command "(Get-CimInstance Win32_VideoController)[0].Name"`** | lanza PowerShell |

Y el módulo `legit` en Windows añade `WindowsGuard`, que es **más invasivo que `LinuxGuard`**:

| `LinuxGuard` (Linux) | `WindowsGuard` (Windows) |
|---|---|
| `/proc/self/maps` → `.so` | `EnumProcessModules`/snapshot → **todos los módulos cargados** |
| nombre + SHA-256 | nombre + SHA-256 + **`signed`** (verificado contra **catálogo WinTrust**) |
| `TracerPid` | `IsDebuggerPresent` + `CheckRemoteDebuggerPresent` |
| — | **hilos "extranjeros"** (`foreignThreads`) |
| — | **`remoteOpens`** |

Solo se envía `name`, `sha256` y `signed` — **no la ruta completa** (verificado: `GuardReport.Module(String name, String sha256, boolean signed)`, y `measure()` recorta el path con `lastIndexOf`). Eso limita bastante la fuga, pero la lista de *nombres* de tus DLLs cargadas ya revela si usas OBS, Discord, overlays, anticheats de otros juegos, etc.

La verificación de firma por catálogo WinTrust es una novedad importante: **Pulsar sabe si cada DLL que tienes cargada está firmada por Microsoft o por un tercero**. Es exactamente lo que hace un anticheat serio, y también lo que hace un inventario de software.

---

## 7. Ranking de telemetría — específico de Windows

### 🔴 Peligroso

| # | Dato | Por qué (en Windows) |
|---|---|---|
| 1 | **Refresh token en `powershell.exe -Command`** | legible por cualquier proceso de tu usuario; no es telemetría, es una fuga de credencial |
| 2 | Nombre + SHA-256 + `signed` de **todas** tus DLL cargadas | inventario del software del equipo; combinado con `installId`, te identifica |
| 3 | `TracerPid`/`IsDebuggerPresent` + hilos extranjeros + `remoteOpens` | revela si tienes herramientas de análisis/instrumentación abiertas |
| 4 | Argumentos JVM (`agents`, `origins`) | pueden contener rutas y flags de entorno |
| 5 | Lista de servidores (`push.subscriptions`) | perfilado social |
| 6 | Patrón de clics (80 buckets) y apuntado (varianza por tick) | firma conductual |

### 🟠 Innecesario

`installId` persistente · `cpu.brand` (marca exacta vía PowerShell) · `gpu.brand` exacta · `display.hz` + resolución · `os.build` (build exacto) · `kind:"session"` atado a `installId` · `unknownClasses`/`redefinedClasses`

### 🟢 Legítimo

`os.name`, `java`, `launcher`, `locale`, `ram.totalGb`, `cores`, y el token de attestation.

---

## 8. ¿Es un virus? Comprobaciones negativas (Windows)

| Comprobación | Resultado |
|---|---|
| Cifrado de archivos / ransomware | ❌ Sin recorrido de usuario ni escritura masiva |
| Persistencia oculta | ❌ Sin servicios (`sc.exe`), sin `schtasks`, sin `Run`/`RunOnce` |
| Keylogger | ❌ Sin `SetWindowsHookEx`, `WH_KEYBOARD`, `GetAsyncKeyState`, `GetKeyboardState` |
| Captura de pantalla | ❌ Sin `BitBlt`, `GetDC`, `PrintWindow` |
| Inyección en otros procesos | ❌ Sin `OpenProcess`/`WriteProcessMemory`/`CreateRemoteThread` en los PE propios |
| Robo de archivos | ❌ **Cero** `BodyPublishers.ofFile` / multipart en los 13 JARs |
| Backdoor | ❌ El WebSocket solo acepta 53 comandos de lista cerrada |
| Escalada de privilegios | ✅ **No la intenta**: `PrivilegesRequired=lowest`, todo en HKCU y `%LOCALAPPDATA%` |
| Auto-update manipulable | ❌ Ed25519 verificado + SHA-256 por objeto |

**Nota frente al informe Linux:** en Linux **sí** hay escalada (`pkexec apt-get install -y --allow-downgrades` y `pkexec` para la regla udev en `/etc`). **En Windows ese vector no existe**: la actualización se hace con `RUN_INSTALLER` → relanza el `setup.exe /SILENT /NORESTART`, que es Inno Setup con `PrivilegesRequired=lowest`. Sin UAC.

---

## 9. Controles que tienes en Windows

| Control | Cómo |
|---|---|
| Telemetría | Apagada por defecto (`metricsOptIn` exige `TRUE`). No la actives |
| Borrar lo enviado | `metrics.forget` → `DELETE /api/v1/metrics/me?installId=…` |
| Cambiar `installId` | `metrics.resetId` |
| Desactivar `legit` | `-Dpulsar.legit=off` (solo si tu servidor no lo exige) |
| Deshacer el cambio de registro | `reg delete "HKCU\Software\Microsoft\DirectX\UserGpuPreferences" /v "<ruta java.exe>" /f` |
| Rotar el token expuesto | Cierra sesión en Pulsar (`auth.logout`) y vuelve a entrar: invalida el refresh token anterior |
| No verificar el token por línea de comandos | no configurable; es el diseño actual |

---

## 10. Veredicto final para Windows

| Pregunta | Respuesta |
|---|---|
| ¿Es un virus? | **No.** Verificado en negativo en 9 categorías |
| ¿Puede robar mi cuenta de Microsoft? | **No por sí mismo**, pero **expone el refresh token en la línea de comandos de PowerShell** mientras se escribe: cualquier programa que ya corra como tu usuario puede capturarlo |
| ¿Es más o menos seguro que en Linux? | **Más seguro en privilegios** (no pide admin, no toca `/etc`, sin UAC) y **menos seguro en credenciales** (fuga por `CommandLine`) |
| ¿Debo preocuparme? | Por malware, poco. Por **el token en PowerShell** y por el **inventario de DLLs**, sí |
| ¿Recomendación? | Instala desde GitHub con SHA-256 verificado, opt-in de métricas apagado, y asume que cualquier malware que tengas en esa sesión puede leer tu token. Si te preocupa la cuenta: usa una cuenta Microsoft secundaria para el launcher |

**Lo que cambia el veredicto respecto al informe Linux:** allí el problema era pedir root; aquí el problema es que **el secreto más valioso que maneja el launcher pasa por un lugar del que no es dueño** (la línea de comandos de un proceso hijo). Eso es un fallo de implementación concreto, no una sospecha.
