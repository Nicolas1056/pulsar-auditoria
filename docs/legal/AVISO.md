# Aviso legal

## Naturaleza del material

Este repositorio es el resultado de una **auditoría de ingeniería inversa estática** realizada con fines de investigación de seguridad, interoperabilidad, auditoría técnica y preservación de software. Todo el análisis se hizo sobre software descargado públicamente, sin modificar, parchear ni redistribuir los productos originales, y sin interactuar con ningún servicio de terceros más allá de las descargas y consultas públicas que un usuario normal haría.

## Contenido de terceros

Este repositorio **contiene material que no es propio** y sobre el que no se reclama ningún derecho:

| Directorio | Contenido | Titularidad |
|---|---|---|
| `mc189/client-1.8.9.jar`, `mc189/client-1.8.9-*.jar` | Cliente de Minecraft 1.8.9 y sus remapeos | Mojang AB / Microsoft |
| `mc189-decompiled/` | Código de Minecraft 1.8.9 decompilado | Mojang AB / Microsoft |
| `mc189/mcp/`, `mc189/mcp-srg/` | Mappings de MCP (de OceanLabs / MCP) | sus autores |
| `win-extracted/` | Instalador de Pulsar extraído, incluido `install_script.iss` | metrofico / autores de Pulsar |
| `dist/` | Binarios oficiales de Pulsar Launcher | metrofico / autores de Pulsar |
| `jadx-out/` | Código de Pulsar decompilado | metrofico / autores de Pulsar |
| `tools/innounp/` | Desempaquetador Inno Setup | sus autores (ver licencia incluida) |
| `tools/vineflower.jar` | Decompilador Vineflower | sus autores (Apache 2.0) |
| `rea/` | Clon del repositorio `morluto/rea` | morluto (MIT) |

**El Minecraft EULA de Mojang establece expresamente:**

> "you must not distribute anything we've made unless we specifically agree to it. By 'distribute anything we've made' what we mean is: give copies of our game software or content to anyone else… And so that we are crystal clear, 'the game' or 'what we have made' includes… parts of those things… or modified versions of a game." — [Minecraft EULA](https://www.minecraft.net/en-us/eula)

La publicación de código decompilado y del JAR del cliente **puede constituir una infracción de copyright** y está sujeta a retirada por notificación DMCA. Este repositorio **no es un canal de distribución** de Minecraft ni de Pulsar: si eres titular de derechos y consideras que este material excede el uso legítimo, abre una incidencia en el repositorio y se retirará.

## Ausencia de licencia

**No se concede licencia alguna** sobre el contenido de este repositorio.

- Los informes, reportes y scripts propios se publican únicamente con fines de lectura, cita y verificación de las conclusiones. No se autoriza su redistribución comercial.
- El contenido de terceros sigue bajo la licencia de sus respectivos titulares. Consulta el archivo de licencia de cada herramienta en `tools/`.

La ausencia de licencia implica **todos los derechos reservados** por defecto sobre el trabajo propio.

## Sobre las conclusiones

Este es un análisis **estático**. No se ejecutó el launcher de forma interactiva contra servicios de terceros, no se instrumentó en tiempo de ejecución y no se verificó el comportamiento del servidor remoto. En consecuencia:

- Las conclusiones sobre el **código** son verificables y reproducibles con los scripts incluidos.
- Las conclusiones sobre el **servidor** (`pulsarlauncher.com`, `rt.pulsarlauncher.com`) son inferencias a partir del código cliente, no auditorías del backend.
- Las conclusiones sobre **cumplimiento normativo** son análisis técnico-documental, **no asesoría legal**. Las Usage Guidelines de Mojang declaran expresamente que no emiten opinión sobre casos concretos y que debe consultarse a un abogado.

Ninguna afirmación de este repositorio debe interpretarse como acusación de actividad delictiva. Los hallazgos de seguridad describen **superficie de ataque y decisiones de diseño**, no intenciones.

## Marcas

"Minecraft" es una marca registrada de Mojang AB. "Microsoft" y "Windows" son marcas de Microsoft Corporation. "Pulsar Launcher" pertenece a sus autores. Los nombres se usan aquí únicamente con fines descriptivos e identificativos, sin sugerir afiliación, patrocinio ni aprobación.

Este repositorio **no está afiliado a Mojang, Microsoft ni a los autores de Pulsar Launcher**, y no ha sido aprobado ni revisado por ellos.
