# Build Linux — diferencias respecto a Windows

**Versión:** 0.2.26-beta · **Artefacto:** `PulsarLauncher-0.2.26-beta-linux-x86_64.tar.gz` (251.906.485 B, SHA-256 `4f20b18ee2efe9ae5ee5f6100a16e4b342b8ec9d88091931747c7ef6ee07fe83`)

Documento complementario. El análisis principal está en [`docs/win/02-privacidad-y-seguridad.md`](../win/02-privacidad-y-seguridad.md), que es el enfoque prioritario.

---

## 1. ¿Es el mismo código?

Verificado antes de comparar nada:

| Comprobación | Resultado |
|---|---|
| `.class` de los 13 JARs | **idénticos byte a byte** |
| `SHA-256` del JAR | **difieren**, pero por CRLF en `.json`, `.pub` y `META-INF/services` |
| `pulsar-v1_8_9.mixins.json` | idéntico en contenido (102 mixins) |
| `web/assets/index-*.js` | idéntico (`6dff3246…ffd3f`) |

Conclusión: **misma lógica, distinto empaquetado.** Todo lo que aplica al código Java (telemetría, módulo `legit`, autenticación, actualizaciones firmadas) es válido en ambas plataformas. Las diferencias reales están en las rutas específicas de plataforma.

Detalle curioso: el `mixins.json` de Windows incluye `EpollMixin`, que es una optimización de red de Linux. Es un artefacto del build, no un cambio funcional.

---

## 2. Lo que cambia: privilegios

**Esta es la diferencia que invierte el veredicto.**

### Windows
```
PrivilegesRequired=lowest  →  %LOCALAPPDATA%\Programs\Pulsar Launcher
Sin UAC. Ninguna escritura fuera de HKCU y del perfil del usuario.
```

### Linux — escalada de privilegios real

**a) Regla udev como root** (`InputDetector.grantPermission()`):

```java
String script = "printf '%s' \"$1\" > /etc/udev/rules.d/70-pulsar-input.rules "
  + "&& chmod 644 /etc/udev/rules.d/70-pulsar-input.rules "
  + "&& udevadm control --reload && udevadm trigger --subsystem-match=input --action=change "
  + "&& udevadm settle --timeout=5";
new ProcessBuilder("pkexec", "/bin/sh", "-c", script, "sh", rule).start();
```

Escribe en `/etc` con `pkexec` (pide contraseña de administrador).

**b) Instalación de `.deb` como root** (`UpdateService.installDeb()`):

```java
run(List.of(pkexec, "/usr/bin/apt-get", "install", "-y", "--allow-downgrades", file.toString()));
```

Instala como root, sin confirmación interactiva (`-y`) y **permitiendo bajar de versión** (`--allow-downgrades`), el `.deb` que se acaba de descargar.

**Mitigación real:** el paquete viene de un manifiesto firmado con Ed25519 y se verifica `sha256` antes de instalar. Pero el patrón "proceso que habla por red → pide root → instala un paquete del servidor" es la vía exacta de un ataque si se compromete `pulsarlauncher.com` o la clave de firma. En Windows ese vector **no existe**.

### La regla udev, en detalle

Es más prudente de lo que el nombre sugiere:

```
SUBSYSTEM=="input", KERNEL=="event*", ENV{ID_INPUT_KEYBOARD}!="1", ENV{ID_INPUT_MOUSE}=="1", TAG+="uaccess"
SUBSYSTEM=="input", KERNEL=="event*", ENV{ID_INPUT_KEYBOARD}!="1", ENV{ID_INPUT_TOUCHPAD}=="1", TAG+="uaccess"
```

- `TAG+="uaccess"` → solo el usuario con sesión gráfica activa (mismo mecanismo que cámaras o mandos). No da acceso a todo el sistema.
- `ENV{ID_INPUT_KEYBOARD}!="1"` → **excluye explícitamente cualquier nodo que también sea teclado**. Un teclado con touchpad integrado queda fuera.
- Solo ratón y touchpad.

Verificado además en el código del cliente: `LinuxRawMouse189.scan()` solo abre nodos `/dev/input/event*` cuya descripción sea ratón (`relativeXY(node)`), y `MouseLock` recibe el listado de dispositivos marcados como `keyboard` para **excluirlos**.

**Conclusión:** la regla es correcta en su alcance. El problema no es lo que concede, es que concederlo requiere root y se hace desde un proceso que habla por red.

---

## 3. Almacén de secretos

| | Windows | Linux |
|---|---|---|
| Backend primario | **DPAPI** vía `powershell.exe` | **libsecret** (`org.freedesktop.secrets`) vía D-Bus |
| Fallback 1 | `MachineBoundFile` (AES-GCM con `/etc/machine-id`) | KWallet nativo |
| Fallback 2 | — | `secret-tool` (CLI) |
| Fallback 3 | — | `MachineBoundFile` (AES-GCM con `/etc/machine-id`) |
| Último recurso | `InMemory` (la sesión dura lo que el proceso) | `InMemory` |

**En Linux no existe la fuga del token.** El backend D-Bus y `secret-tool` pasan el secreto por **entrada estándar** (`SecretStores.run(..., stdin)`), no por línea de comandos. Es el diseño correcto — y precisamente el que Windows no usa (ver [win/02 §1](../win/02-privacidad-y-seguridad.md#1--crítico--el-refresh-token-viaja-en-la-línea-de-comandos)).

Es decir: **el fallo del token es específico de Windows.**

| | Windows | Linux |
|---|---|---|
| Refresh token en reposo | DPAPI `CurrentUser` ✅ | keyring del escritorio ✅ |
| Refresh token en tránsito al keyring | 🔴 **línea de comandos de PowerShell** | 🟢 stdin |

---

## 4. Telemetría: diferencias

| Campo | Windows | Linux |
|---|---|---|
| `os.build` | ✅ se envía (`os.version`) | ❌ vacío (`osBuild()` devuelve `""`) |
| `cpu.brand` | `powershell Get-CimInstance Win32_Processor` | `/proc/cpuinfo` |
| `gpu.brand` | `powershell Get-CimInstance Win32_VideoController` | `lspci \| grep -iE 'vga\|3d\|display'` |
| Inventario de módulos | `WindowsGuard`: nombre + SHA-256 + **`signed`** | `LinuxGuard`: nombre + SHA-256 |
| Detección de depurador | `IsDebuggerPresent` + `CheckRemoteDebuggerPresent` | `TracerPid` en `/proc/self/status` |
| Hilos ajenos / `remoteOpens` | ✅ | ❌ |
| Filtro de módulos | catálogo WinTrust + directorios de sistema | `/proc/self/maps` + filtro "solo root" por `uid_map` |

**`WindowsGuard` es más invasivo**: verifica la firma de cada DLL contra el catálogo de Microsoft, lo que equivale a un inventario de software validado. `LinuxGuard` se limita a leer `.so` mapeados y descarta los que pertenecen a root.

En ambos casos se envía **solo** `name`, `sha256` y `signed` — nunca la ruta completa.

---

## 5. Empaquetado

| | Windows | Linux |
|---|---|---|
| Formato | Inno Setup 6.7.0 (`lzma2`) | `tar.gz` + `.deb` + `.AppImage` |
| Instalación | `%LOCALAPPDATA%`, sin admin | `tar.gz` portable o `.deb` (root) |
| Actualización | relanza `setup.exe /SILENT /NORESTART` | reemplaza árbol de directorios, o `pkexec apt-get install` para `.deb` |
| Autostart | ninguno | ninguno |
| `chrome-sandbox` | n/a | presente (`setuid` de Chromium) |
| Entorno | `PULSAR_*` | `PULSAR_*`, `APPIMAGE`, `XDG_DATA_HOME` |
| Watcher del updater | `windowsWatchdog` | `systemd-run --user` / `setsid` |
| Prioridad de CPU | — | `systemd-run --user --scope --property=CPUWeight=25` |

En Linux el updater usa `systemd-run --user --scope --property=CPUWeight=25` (o `CPUWeight=100` en otro caso) para que la actualización no compita con el juego. Detalle de calidad, no de riesgo.

---

## 6. Veredicto comparado

| Criterio | Windows | Linux |
|---|---|---|
| Pide privilegios de administrador | ❌ No | ✅ **Sí** (`pkexec` × 2) |
| Escribe fuera del perfil del usuario | ❌ No (solo HKCU) | ✅ **Sí** (`/etc/udev/rules.d/`) |
| Expone el token en línea de comandos | 🔴 **Sí** | 🟢 No |
| Inventario de módulos con verificación de firma | 🟠 Sí (`WindowsGuard`) | 🟡 Más limitado |
| Firma de código en el ejecutable | ❌ No | n/a (`tar.gz`) |
| **Riesgo global** | **Medio** | **Medio** |

Empate en la puntuación, por motivos opuestos: en Windows el riesgo es de **credenciales**; en Linux es de **privilegios**. En ninguno de los dos hay indicios de código malicioso.
