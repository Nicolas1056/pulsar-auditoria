package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class BenchShot189 {
   private static int counter;

   private BenchShot189() {
   }

   public static void frameEnd() {
      if ((++counter & 31) == 0) {
         ave mc = ave.A();
         File set = new File(mc.v, "pulsar-set");
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
            } catch (Exception var12) {
               ClientOptions.LOG.accept("bench: pulsar-set fallido: " + var12);
            }

            set.delete();
         }

         File trigger = new File(mc.v, "pulsar-shot");
         if (trigger.exists()) {
            try {
               String name = new String(Files.readAllBytes(trigger.toPath())).trim();
               trigger.delete();
               if (name.isEmpty()) {
                  name = Long.toString(System.currentTimeMillis());
               }

               int w = mc.d;
               int h = mc.e;
               ByteBuffer px = BufferUtils.createByteBuffer(w * h * 3);
               GL11.glPixelStorei(3333, 1);
               GL11.glReadPixels(0, 0, w, h, 6407, 5121, px);
               BufferedImage img = new BufferedImage(w, h, 1);

               for (int y = 0; y < h; y++) {
                  for (int x = 0; x < w; x++) {
                     int i = (y * w + x) * 3;
                     img.setRGB(x, h - 1 - y, (px.get(i) & 255) << 16 | (px.get(i + 1) & 255) << 8 | px.get(i + 2) & 255);
                  }
               }

               File dir = new File(mc.v, "pulsar-shots");
               dir.mkdirs();
               ImageIO.write(img, "png", new File(dir, name.replaceAll("[^a-zA-Z0-9_.-]", "_") + ".png"));
            } catch (Exception var11) {
               ClientOptions.LOG.accept("bench: captura fallida: " + var11);
            }
         }
      }
   }
}
