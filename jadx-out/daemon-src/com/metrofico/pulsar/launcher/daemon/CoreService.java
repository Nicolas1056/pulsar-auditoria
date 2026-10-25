package com.metrofico.pulsar.launcher.daemon;

import com.metrofico.pulsar.launcher.auth.MicrosoftAuth;
import com.metrofico.pulsar.launcher.auth.SecretStore;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

public interface CoreService {
   List<CoreService.Release> versions();

   void prepare(String var1, String var2, Consumer<PrepareProgress> var3);

   List<CoreService.JavaInfo> runtimes();

   CoreService.JavaInfo javaFor(String var1, String var2);

   CoreService.Launched launch(Profile var1, Preferences var2, Identity var3, Consumer<Integer> var4);

   default void grantInputPermission() {
   }

   default boolean inputSetupNeeded() {
      return false;
   }

   default CoreService.Launched launch(Profile profile, Preferences preferences, Identity identity, String quickPlay, Consumer<Integer> onExit) {
      return this.launch(profile, preferences, identity, onExit);
   }

   MicrosoftAuth microsoftAuth();

   MicrosoftAuth microsoftAuth(String var1);

   SecretStore secretStore();

   default boolean offlinePlayAllowed() {
      return "1".equals(System.getenv("PULSAR_NO_AUTH"));
   }

   UpdateService updateService(Path var1, Consumer<String> var2);

   void cancel();

   Path logsDir();

   Path crashReportsDir();

   default void open(Path target) {
      this.openTarget(target.toString(), target.getFileName().toString());
   }

   default void openUrl(String url) {
      this.openTarget(url, url);
   }

   private void openTarget(String target, String shown) {
      String os = System.getProperty("os.name", "").toLowerCase();
      List<String> command = os.contains("win")
         ? List.of("rundll32", "url.dll,FileProtocolHandler", target)
         : (os.contains("mac") ? List.of("open", target) : List.of("xdg-open", target));

      try {
         new ProcessBuilder(command).redirectErrorStream(true).redirectOutput(Redirect.DISCARD).start();
      } catch (IOException var6) {
         throw new DaemonException("open_failed", "No se pudo abrir " + shown, "Ábrelo a mano: " + target, null, var6);
      }
   }

   public record JavaInfo(Integer major, String home, String source, String implementor) {
      public static CoreService.JavaInfo download(int major) {
         return new CoreService.JavaInfo(major, null, "download", null);
      }

      public static CoreService.JavaInfo unknown() {
         return new CoreService.JavaInfo(null, null, "unknown", null);
      }
   }

   public record Launched(long pid, String logFile) {
   }

   public record Release(String id, String type) {
   }
}
