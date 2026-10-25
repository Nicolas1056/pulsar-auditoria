package com.metrofico.pulsar.launcher.auth;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public final class SecretStores {
   public static final String SERVICE = "com.metrofico.pulsar";
   static final long TIMEOUT_SECONDS = 5L;

   public static SecretStore detect(Path dataDir) {
      return detect(dataDir, line -> {});
   }

   public static SecretStore detect(Path dataDir, Consumer<String> log) {
      String os = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);

      for (SecretStores.Native candidate : os.contains("win")
         ? List.of(new SecretStores.Dpapi(dataDir), new MachineBoundFile(dataDir))
         : (
            os.contains("mac")
               ? List.of(new SecretStores.Keychain(), new MachineBoundFile(dataDir))
               : List.of(new LinuxKeyrings.SecretService(), new LinuxKeyrings.KWalletNative(), new SecretStores.SecretTool(), new MachineBoundFile(dataDir))
         )) {
         long t0 = System.nanoTime();

         boolean ok;
         try {
            ok = candidate.available();
         } catch (RuntimeException var10) {
            ok = false;
            log.accept("secret store probe · " + candidate.describe() + " · error " + var10.getClass().getSimpleName());
         }

         log.accept("secret store probe · " + candidate.describe() + " · " + (ok ? "ok" : "unavailable") + " · " + (System.nanoTime() - t0) / 1000000L + " ms");
         if (ok) {
            return candidate;
         }
      }

      log.accept("secret store · none available: the session lasts until the launcher closes");
      return new SecretStore.InMemory();
   }

   static Optional<String> run(List<String> command, String stdin) {
      return exec(command, stdin, false, 5L).filter(r -> r.exit() == 0).map(r -> r.output().trim());
   }

   static Optional<SecretStores.Result> exec(List<String> command, String stdin, boolean mergeStderr, long timeoutSeconds) {
      Process p;
      try {
         ProcessBuilder pb = new ProcessBuilder(command).redirectErrorStream(mergeStderr);
         if (!mergeStderr) {
            pb.redirectError(Redirect.DISCARD);
         }

         p = pb.start();
      } catch (IOException var15) {
         return Optional.empty();
      }

      try {
         CompletableFuture<String> output = CompletableFuture.supplyAsync(() -> {
            try {
               return new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            } catch (IOException var2x) {
               return "";
            }
         }, Thread::startVirtualThread);

         try (OutputStream in = p.getOutputStream()) {
            if (stdin != null) {
               in.write(stdin.getBytes(StandardCharsets.UTF_8));
            }
         } catch (IOException var12) {
         }

         if (!p.waitFor(timeoutSeconds, TimeUnit.SECONDS)) {
            p.destroyForcibly();
            return Optional.empty();
         } else {
            return Optional.of(new SecretStores.Result(p.exitValue(), output.get(timeoutSeconds, TimeUnit.SECONDS)));
         }
      } catch (InterruptedException var13) {
         Thread.currentThread().interrupt();
         p.destroyForcibly();
         return Optional.empty();
      } catch (TimeoutException | ExecutionException var14) {
         p.destroyForcibly();
         return Optional.empty();
      }
   }

   private SecretStores() {
   }

   static final class Dpapi implements SecretStores.Native {
      private final Path dir;

      Dpapi(Path dataDir) {
         this.dir = dataDir.resolve("secrets");
      }

      @Override
      public String describe() {
         return "protección de datos de Windows (DPAPI)";
      }

      @Override
      public boolean persistent() {
         return true;
      }

      @Override
      public boolean available() {
         return SecretStores.run(List.of("powershell", "-NoProfile", "-NonInteractive", "-Command", "Add-Type -AssemblyName System.Security; 'ok'"), null)
            .map("ok"::equals)
            .orElse(false);
      }

      private Path file(String key) {
         return this.dir.resolve(key.replaceAll("[^A-Za-z0-9._-]", "_") + ".dpapi");
      }

      @Override
      public Optional<String> get(String key) {
         Path f = this.file(key);
         if (!Files.isRegularFile(f)) {
            return Optional.empty();
         } else {
            String script = "Add-Type -AssemblyName System.Security; $b=[IO.File]::ReadAllBytes('"
               + f.toString().replace("'", "''")
               + "');[Text.Encoding]::UTF8.GetString([Security.Cryptography.ProtectedData]::Unprotect($b,$null,'CurrentUser'))";
            return SecretStores.run(List.of("powershell", "-NoProfile", "-NonInteractive", "-Command", script), null).filter(s -> !s.isEmpty());
         }
      }

      @Override
      public void put(String key, String value) {
         try {
            Files.createDirectories(this.dir);
         } catch (IOException var5) {
            return;
         }

         String b64 = Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
         String script = "Add-Type -AssemblyName System.Security; $p=[Convert]::FromBase64String('"
            + b64
            + "');[IO.File]::WriteAllBytes('"
            + this.file(key).toString().replace("'", "''")
            + "',[Security.Cryptography.ProtectedData]::Protect($p,$null,'CurrentUser'))";
         SecretStores.run(List.of("powershell", "-NoProfile", "-NonInteractive", "-Command", script), null);
      }

      @Override
      public void delete(String key) {
         try {
            Files.deleteIfExists(this.file(key));
         } catch (IOException var3) {
         }
      }
   }

   static final class Keychain implements SecretStores.Native {
      @Override
      public String describe() {
         return "llavero de macOS";
      }

      @Override
      public boolean persistent() {
         return true;
      }

      @Override
      public boolean available() {
         return SecretStores.run(List.of("security", "list-keychains"), null).isPresent();
      }

      @Override
      public Optional<String> get(String key) {
         return SecretStores.run(List.of("security", "find-generic-password", "-s", "com.metrofico.pulsar", "-a", key, "-w"), null).filter(s -> !s.isEmpty());
      }

      @Override
      public void put(String key, String value) {
         SecretStores.run(
            List.of("security", "add-generic-password", "-U", "-s", "com.metrofico.pulsar", "-a", key, "-l", "Pulsar Launcher", "-w", value), null
         );
      }

      @Override
      public void delete(String key) {
         SecretStores.run(List.of("security", "delete-generic-password", "-s", "com.metrofico.pulsar", "-a", key), null);
      }
   }

   interface Native extends SecretStore {
      boolean available();
   }

   record Result(int exit, String output) {
   }

   static final class SecretTool implements SecretStores.Native {
      @Override
      public String describe() {
         return "almacén del sistema (libsecret)";
      }

      @Override
      public boolean persistent() {
         return true;
      }

      @Override
      public boolean available() {
         return SecretStores.exec(List.of("secret-tool", "lookup", "service", "com.metrofico.pulsar", "account", "__probe__"), null, true, 5L)
            .map(r -> !r.output().toLowerCase(Locale.ROOT).contains("error"))
            .orElse(false);
      }

      @Override
      public Optional<String> get(String key) {
         return SecretStores.run(List.of("secret-tool", "lookup", "service", "com.metrofico.pulsar", "account", key), null).filter(s -> !s.isEmpty());
      }

      @Override
      public void put(String key, String value) {
         SecretStores.run(List.of("secret-tool", "store", "--label", "Pulsar Launcher (" + key + ")", "service", "com.metrofico.pulsar", "account", key), value);
      }

      @Override
      public void delete(String key) {
         SecretStores.run(List.of("secret-tool", "clear", "service", "com.metrofico.pulsar", "account", key), null);
      }
   }
}
