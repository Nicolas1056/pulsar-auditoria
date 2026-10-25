package com.metrofico.pulsar.bootstrap;

import com.metrofico.pulsar.api.input.InputPipe;
import com.metrofico.pulsar.api.input.MouseLock;
import com.metrofico.pulsar.bootstrap.engine.EngineLauncher;
import com.metrofico.pulsar.bootstrap.legit.ClassWatch;
import com.metrofico.pulsar.bootstrap.legit.LegitBoot;
import com.metrofico.pulsar.bootstrap.legit.LoadedCodeSource;
import com.metrofico.pulsar.bootstrap.loader.GameClassLoader;
import com.metrofico.pulsar.bootstrap.mixin.MixinSetup;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class Main {
   public static final String BRAND = "Pulsar Client";
   public static final String VERSION = "0.1.0-dev";

   public static void main(String[] args) {
      BootstrapLog.info("Pulsar Client 0.1.0-dev bootstrap starting on Java " + Runtime.version().feature());
      GameEntry game = GameEntry.fromSystemProperties();
      BootstrapLog.info("Game classpath: " + game.classpath().size() + " entries, main class " + game.mainClass());
      GameClassLoader gameLoader = new GameClassLoader(game.urls(), Main.class.getClassLoader());
      ClassWatch.attach(gameLoader);
      EngineLauncher.Selection engine = null;

      try {
         engine = EngineLauncher.select(
            gameLoader, EngineLauncher.versionFromSystemProperties(), osName(), archName(), Path.of(System.getProperty("pulsar.data.dir", "pulsar"))
         );
         List<String> configs = new ArrayList<>(engine.mixinConfigs());
         configs.addAll(mixinConfigs());
         MixinSetup.init(gameLoader, List.copyOf(configs));
      } catch (Error | IllegalStateException var8) {
         BootstrapLog.error("Pulsar Client cannot start: " + var8.getMessage(), var8.getCause());
         System.exit(12);
      }

      LoadedCodeSource.install(gameLoader, game.urls());
      if (InputPipe.startFromSystem(new MouseLock(InputPipe::enforcing, MouseLock.clockMs())) != null) {
         BootstrapLog.info("input: mouse lock on (pulsar-input pipe)");
      }

      LegitBoot.start(
         System.getProperty("pulsar.game.version"),
         game.classpath(),
         engine.backend().map(Object::getClass).orElse(null),
         Path.of(System.getProperty("pulsar.data.dir", "pulsar")),
         gameLoader
      );
      BootstrapLog.info(
         "GameClassLoader ready: " + gameLoader.transformers().transformers().size() + " transformer(s), parallel capable = " + gameLoader.parallelCapable()
      );
      Thread.currentThread().setContextClassLoader(gameLoader);

      try {
         Class<?> mainClass = Class.forName(game.mainClass(), true, gameLoader);
         if (mainClass.getClassLoader() != gameLoader) {
            throw new IllegalStateException(
               game.mainClass() + " was loaded by " + mainClass.getClassLoader() + " instead of the game loader: the game must not be on -cp"
            );
         }

         Method main = mainClass.getMethod("main", String[].class);
         BootstrapLog.info("Handing over to " + game.mainClass());
         main.invoke(null, args);
      } catch (NoSuchMethodException | IllegalAccessException | ClassNotFoundException var6) {
         BootstrapLog.error("Cannot start the game through the bootstrap", var6);
         System.exit(10);
      } catch (InvocationTargetException var7) {
         BootstrapLog.error("The game failed to start", var7.getCause());
         System.exit(11);
      }
   }

   static List<String> mixinConfigs() {
      String configs = System.getProperty("pulsar.mixin.configs", "");
      return configs.isBlank() ? List.of() : List.of(configs.split(","));
   }

   private static String osName() {
      String n = System.getProperty("os.name").toLowerCase();
      return n.contains("win") ? "windows" : (n.contains("mac") ? "osx" : "linux");
   }

   private static String archName() {
      String a = System.getProperty("os.arch").toLowerCase();
      return !a.equals("amd64") && !a.equals("x86_64") ? (a.equals("aarch64") ? "arm64" : a) : "x86_64";
   }

   private Main() {
   }
}
