# Cómo publicar este repositorio

El repo está **inicializado y commiteado en local** (`commit 4c54fef`, 12.588 ficheros, 554 MB). Solo falta apuntarlo a GitHub y hacer push. **No hay credenciales en este entorno**, así que ese paso lo haces tú.

---

## 1. Crear el repositorio vacío en GitHub

En <https://github.com/new>:

- **Nombre:** el que quieras, p. ej. `pulsar-launcher-audit`
- **Visibilidad:** privado mientras revisas el contenido (recomendado)
- **NO** marques "Add a README", "Add .gitignore" ni "Choose a license" — el repo ya los tiene

---

## 2. Apuntar el remoto y subir

Sustituye `TU_USUARIO` y `REPO`:

```bash
cd /home/user/pulsar-rea

# HTTPS (te pedirá usuario + Personal Access Token como contraseña)
git remote add origin https://github.com/TU_USUARIO/REPO.git

# o por SSH (requiere clave configurada en GitHub)
# git remote add origin git@github.com:TU_USUARIO/REPO.git

git branch -M main
git push -u origin main
```

Si el push falla por tamaño, mira el apartado 4.

---

## 3. Verificar que subió bien

```bash
git remote -v
git --no-pager log --oneline -1
git ls-files | wc -l          # debe dar 12588
```

Y en GitHub comprueba que aparece la pestaña **Actions** con el workflow `Verificar límites de GitHub` en verde.

---

## 4. Si el push falla

### `remote: error: File ... is 138.00 MB; this exceeds GitHub's file size limit of 100.00 MB`

Algún fichero >100 MB se coló. Localízalo:

```bash
python3 - <<'EOF'
import subprocess, os
files = [f for f in subprocess.run(['git','ls-files','-z'],capture_output=True).stdout.split(b'\0') if f]
for f in files:
    p = f.decode('utf-8','replace')
    try: s = os.path.getsize(p)
    except OSError: continue
    if s > 100*1024*1024: print(f"{s/1048576:.0f} MB  {p}")
EOF
```

Añádelo al `.gitignore` y sácalo del índice:

```bash
echo "ruta/al/fichero" >> .gitignore
git rm --cached "ruta/al/fichero"
git commit -m "Excluir fichero >100 MB"
```

### `fatal: refusing to merge unrelated histories` o el repo ya tenía commits

```bash
git pull --rebase origin main --allow-unrelated-histories
git push -u origin main
```

### El push se corta a mitad (554 MB por conexión lenta)

```bash
git config http.postBuffer 524288000
git push -u origin main
```

Si sigue fallando, sube por partes:

```bash
git push -u origin main --progress
# o limita la profundidad del historial
git push -u origin main --depth=1
```

---

## 5. Advertencia sobre el contenido

Antes de hacerlo público, ten presente lo que dice [`docs/legal/AVISO.md`](docs/legal/AVISO.md):

- El repo contiene **el `client.jar` de Minecraft y su código decompilado** (Mojang), y **binarios y código decompilado de Pulsar** (metrofico). El EULA de Mojang prohíbe explícitamente "give copies of our game software or content to anyone else".
- Un repo público con esto **puede recibir una notificación DMCA** y ser retirado, y puede exponerte a una reclamación.
- Se ha elegido **sin licencia**, lo que significa todos los derechos reservados sobre tu trabajo, pero **no protege** frente a los derechos de Mojang ni de Pulsar sobre su material.

Si quieres reducir el riesgo sin perder el trabajo, la variante más segura es dejar el repo **privado**, o publicar solo `docs/` + `evid/` + scripts (unos 200 KB) y mantener los artefactos grandes en local o en un archivo aparte.

---

## 6. Estructura del commit

```
README.md                                            índice y hallazgos
.gitignore                                           exclusiones >100 MB
.github/workflows/verificar-limites.yml              CI que falla si vuelve a entrar un fichero grande
docs/win/01-informe-launcher.md                      arquitectura
docs/win/02-privacidad-y-seguridad.md                privacidad y seguridad (enfoque Windows)
docs/linux/01-diferencias-vs-windows.md              comparativa Linux
docs/compliance/01-eula-y-usage-guidelines.md        cumplimiento Mojang
docs/minecraft-1.8.9/01-informe-remap-y-mixins.md    remapeo y verificación de mixins
docs/legal/AVISO.md                                  aviso legal
evid/                                                evidencia JSON de REA
mc189/                                               scripts de remapeo + reportes
jadx-out/                                            Pulsar decompilado (639 clases)
mc189-decompiled/                                    Minecraft 1.8.9 decompilado (1612)
win-extracted/                                       instalador Windows extraído + .iss
dist/                                                binarios originales (sin los >100 MB)
```

### Ficheros excluidos (1,2 GB)

| Fichero | Tamaño | Cómo obtenerlo |
|---|---|---|
| `libcef.dll` (Windows) | 272 MB | `innounp -x` sobre el setup |
| `libcef.so` (Linux) | 256 MB | descomprimir el `tar.gz` |
| `PulsarLauncher-linux-x86_64.tar.gz` | 240 MB | descargar del release |
| `PulsarLauncher-setup.exe` | 178 MB | descargar de `pulsarlauncher.com` |
| `daemon/jre/lib/modules` × 2 | 277 MB | reextraer / descomprimir |

Más `.wine32/` (636 MB, regenerable con `wineboot`) y `rea/` (118 MB, `git clone https://github.com/morluto/rea.git`).
