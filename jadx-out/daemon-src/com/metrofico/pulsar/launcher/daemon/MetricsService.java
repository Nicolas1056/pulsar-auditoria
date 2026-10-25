package com.metrofico.pulsar.launcher.daemon;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.management.OperatingSystemMXBean;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public final class MetricsService {
   private static final SecureRandom RANDOM = new SecureRandom();
   private static final Path ID_FILE_NAME = Path.of("metrics-id");
   private final String serviceUrl;
   private final Supplier<String> token;
   private final Path dataDir;
   private final HttpClient http;

   public MetricsService(String serviceUrl, Supplier<String> token, Path dataDir, HttpClient http) {
      this.serviceUrl = serviceUrl.replaceAll("/+$", "");
      this.token = token;
      this.dataDir = dataDir;
      this.http = http != null ? http : HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10L)).build();
   }

   private Path idFile() {
      return this.dataDir.resolve(ID_FILE_NAME);
   }

   public synchronized String installId() {
      try {
         Path f = this.idFile();
         if (Files.isRegularFile(f)) {
            String saved = Files.readString(f, StandardCharsets.UTF_8).trim();
            if (saved.length() >= 22 && saved.length() <= 24 && saved.matches("[A-Za-z0-9_-]+")) {
               return saved;
            }
         }

         byte[] b = new byte[16];
         RANDOM.nextBytes(b);
         String id = Base64.getUrlEncoder().withoutPadding().encodeToString(b);
         Files.createDirectories(f.toAbsolutePath().getParent());
         Path tmp = f.resolveSibling(f.getFileName() + ".part");
         Files.writeString(tmp, id, StandardCharsets.UTF_8);
         Files.move(tmp, f, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
         return id;
      } catch (IOException var5) {
         throw new UncheckedIOException(var5);
      }
   }

   public synchronized String resetInstallId() {
      try {
         Files.deleteIfExists(this.idFile());
      } catch (IOException var2) {
         throw new UncheckedIOException(var2);
      }

      return this.installId();
   }

   public Map<String, Object> buildHw(String launcherVersion, int screenWidth, int screenHeight, Double screenHz, String locale) {
      Map<String, Object> os = new LinkedHashMap<>();
      os.put("name", osName());
      os.put("version", System.getProperty("os.version", ""));
      if (!osBuild().isEmpty()) {
         os.put("build", osBuild());
      }

      Map<String, Object> cpu = new LinkedHashMap<>();
      cpu.put("brand", cpuBrand());
      cpu.put("cores", Runtime.getRuntime().availableProcessors());
      cpu.put("threads", Runtime.getRuntime().availableProcessors());
      Map<String, Object> ram = new LinkedHashMap<>();
      ram.put("totalGb", (int)Math.round(totalRamGb()));
      Map<String, Object> gpu = new LinkedHashMap<>();
      gpu.put("brand", gpuBrand());
      Map<String, Object> display = new LinkedHashMap<>();
      display.put("width", screenWidth);
      display.put("height", screenHeight);
      if (screenHz != null) {
         display.put("hz", (int)Math.round(screenHz));
      }

      Map<String, Object> d = new LinkedHashMap<>();
      d.put("kind", "hw");
      d.put("installId", this.installId());
      d.put("sentAt", Instant.now().toString());
      d.put("os", os);
      d.put("cpu", cpu);
      d.put("ram", ram);
      d.put("gpu", gpu);
      d.put("display", display);
      d.put("launcher", launcherVersion);
      d.put("java", Runtime.version().feature());
      d.put("locale", locale == null ? Locale.getDefault().toLanguageTag() : locale);
      return d;
   }

   public Map<String, Object> buildSession(String launcherVersion, Map<String, Object> game) {
      Map<String, Object> d = new LinkedHashMap<>(game);
      d.put("kind", "session");
      d.put("installId", this.installId());
      d.put("sentAt", Instant.now().toString());
      d.put("launcher", launcherVersion);
      return d;
   }

   public void send(Map<String, Object> body) {
      try {
         this.sendChecked(body);
      } catch (IOException var3) {
         throw new UncheckedIOException(var3);
      }
   }

   private void sendChecked(Map<String, Object> body) throws IOException {
      String t = this.token.get();
      if (t == null) {
         throw new DaemonException("no_session", "Sin sesión de Pulsar", "Para mandar métricas, inicia sesión primero");
      } else {
         String json = new Gson().toJson(body);

         try {
            HttpResponse<String> r = this.http
               .send(
                  HttpRequest.newBuilder(URI.create(this.serviceUrl + "/api/v1/metrics"))
                     .timeout(Duration.ofSeconds(10L))
                     .header("Authorization", "Bearer " + t)
                     .header("Content-Type", "application/json")
                     .POST(BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                     .build(),
                  BodyHandlers.ofString(StandardCharsets.UTF_8)
               );
            if (r.statusCode() != 200) {
               throw new DaemonException("http_" + r.statusCode(), "Pulsar no respondió", "metrics: " + r.statusCode() + " " + r.body());
            }
         } catch (InterruptedException var5) {
            Thread.currentThread().interrupt();
            throw new DaemonException("interrupted", "Cancelado", "metrics");
         }
      }
   }

   private Path hwSentFile() {
      return this.dataDir.resolve("metrics-hw-sent");
   }

   public boolean hwDue(String launcherVersion, long nowMillis) {
      try {
         String[] parts = Files.readString(this.hwSentFile(), StandardCharsets.UTF_8).trim().split("\\|", 2);
         return parts.length != 2 || !parts[0].equals(launcherVersion) || nowMillis - Long.parseLong(parts[1]) >= 86400000L;
      } catch (RuntimeException | IOException var5) {
         return true;
      }
   }

   public void markHwSent(String launcherVersion, long nowMillis) {
      try {
         Files.createDirectories(this.dataDir);
         Files.writeString(this.hwSentFile(), launcherVersion + "|" + nowMillis, StandardCharsets.UTF_8);
      } catch (IOException var5) {
         LauncherLog.info("metrics · no se pudo guardar el último envío: " + var5.getMessage());
      }
   }

   public int forget() {
      try {
         return this.forgetChecked();
      } catch (IOException var2) {
         throw new UncheckedIOException(var2);
      }
   }

   private int forgetChecked() throws IOException {
      String t = this.token.get();
      if (t == null) {
         throw new DaemonException("no_session", "Sin sesión de Pulsar", "Para borrar métricas, inicia sesión primero");
      } else {
         String id = this.installId();

         try {
            HttpResponse<String> r = this.http
               .send(
                  HttpRequest.newBuilder(URI.create(this.serviceUrl + "/api/v1/metrics/me?installId=" + id))
                     .timeout(Duration.ofSeconds(10L))
                     .header("Authorization", "Bearer " + t)
                     .DELETE()
                     .build(),
                  BodyHandlers.ofString(StandardCharsets.UTF_8)
               );
            if (r.statusCode() != 200) {
               throw new DaemonException("http_" + r.statusCode(), "Pulsar no respondió", "metrics/me: " + r.statusCode());
            } else {
               return JsonParser.parseString(r.body()).getAsJsonObject().get("removed").getAsInt();
            }
         } catch (InterruptedException var4) {
            Thread.currentThread().interrupt();
            throw new DaemonException("interrupted", "Cancelado", "metrics/me");
         }
      }
   }

   static String osName() {
      String raw = System.getProperty("os.name", "");
      String n = raw.toLowerCase(Locale.ROOT);
      if (n.contains("win")) {
         return "Windows";
      } else if (n.contains("mac")) {
         return "macOS";
      } else {
         return n.contains("linux") ? "Linux" : raw;
      }
   }

   static String osBuild() {
      return !osName().equals("Windows") ? "" : System.getProperty("os.version", "");
   }

   static String cpuBrand() {
      try {
         if (osName().equals("Linux")) {
            for (String line : Files.readAllLines(Path.of("/proc/cpuinfo"))) {
               if (line.startsWith("model name")) {
                  int colon = line.indexOf(58);
                  if (colon > 0) {
                     return line.substring(colon + 1).trim();
                  }
               }
            }
         } else if (osName().equals("macOS")) {
            String out = run(List.of("sysctl", "-n", "machdep.cpu.brand_string"));
            if (!out.isEmpty()) {
               return out;
            }
         } else if (osName().equals("Windows")) {
            String out = run(List.of("powershell", "-NoProfile", "-Command", "(Get-CimInstance Win32_Processor).Name"));
            if (!out.isEmpty()) {
               return out;
            }
         }
      } catch (RuntimeException | IOException var3) {
         LauncherLog.info("metrics: cpu desconocida (" + var3.getMessage() + ")");
      }

      return System.getProperty("os.arch", "unknown");
   }

   static double totalRamGb() {
      try {
         if (ManagementFactory.getOperatingSystemMXBean() instanceof OperatingSystemMXBean os) {
            return os.getTotalMemorySize() / 1.0737418E9F;
         }
      } catch (RuntimeException var2) {
      }

      return 0.0;
   }

   static String gpuBrand() {
      try {
         if (osName().equals("Linux")) {
            String out = run(List.of("sh", "-c", "lspci 2>/dev/null | grep -iE 'vga|3d|display' | head -1"));
            int colon = out.lastIndexOf(58);
            if (colon >= 0 && colon + 1 < out.length()) {
               return out.substring(colon + 1).trim();
            }
         } else if (osName().equals("macOS")) {
            String out = run(List.of("sh", "-c", "system_profiler SPDisplaysDataType 2>/dev/null | awk -F': ' '/Chipset Model:/ {print $2; exit}'"));
            if (!out.isEmpty()) {
               return out;
            }
         } else if (osName().equals("Windows")) {
            String out = run(List.of("powershell", "-NoProfile", "-Command", "(Get-CimInstance Win32_VideoController)[0].Name"));
            if (!out.isEmpty()) {
               return out;
            }
         }
      } catch (RuntimeException | IOException var2) {
         LauncherLog.info("metrics: gpu desconocida (" + var2.getMessage() + ")");
      }

      return "unknown";
   }

   private static String run(List<String> cmd) throws IOException {
      try {
         Process p = new ProcessBuilder(cmd).redirectErrorStream(false).start();
         if (!p.waitFor(3L, TimeUnit.SECONDS)) {
            p.destroyForcibly();
            return "";
         } else {
            String out = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
            int nl = out.indexOf(10);
            return (nl >= 0 ? out.substring(0, nl) : out).trim();
         }
      } catch (InterruptedException var4) {
         Thread.currentThread().interrupt();
         return "";
      }
   }

   public static Map<String, Object> fromJson(JsonObject o) {
      return (Map<String, Object>)new Gson().fromJson(o, Map.class);
   }

   public static List<String> stringList(JsonArray a) {
      return a == null ? List.of() : a.asList().stream().map(e -> e.isJsonPrimitive() ? e.getAsString() : "").toList();
   }
}
