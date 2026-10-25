package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import java.io.File;
import java.nio.file.Files;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;

public final class BenchShot26 {
   private static int counter;

   private BenchShot26() {
   }

   public static void frameEnd() {
      if ((++counter & 31) == 0) {
         Minecraft mc = Minecraft.getInstance();
         File set = new File(mc.gameDirectory, "pulsar-set");
         if (set.exists()) {
            try {
               for (String line : Files.readAllLines(set.toPath())) {
                  int eq = line.indexOf(61);
                  if (eq > 0) {
                     String value = line.substring(eq + 1).trim();
                     Object parsed = !value.equals("true") && !value.equals("false") ? value : Boolean.valueOf(value);
                     ClientOptions.get().preview(line.substring(0, eq).trim(), parsed);
                  }
               }

               ClientOptions.LOG.accept("bench: opciones cambiadas en caliente: " + set.length() + " B");
            } catch (Exception var8) {
               ClientOptions.LOG.accept("bench: pulsar-set fallido: " + var8);
            }

            set.delete();
         }

         File trigger = new File(mc.gameDirectory, "pulsar-shot");
         if (trigger.exists()) {
            try {
               String name = new String(Files.readAllBytes(trigger.toPath())).trim();
               trigger.delete();
               if (name.isEmpty()) {
                  name = Long.toString(System.currentTimeMillis());
               }

               Screenshot.grab(mc.gameDirectory, name.replaceAll("[^a-zA-Z0-9_.-]", "_") + ".png", mc.gameRenderer.mainRenderTarget(), 1, msg -> {});
            } catch (Exception var7) {
               ClientOptions.LOG.accept("bench: captura fallida: " + var7);
            }
         }
      }
   }
}
