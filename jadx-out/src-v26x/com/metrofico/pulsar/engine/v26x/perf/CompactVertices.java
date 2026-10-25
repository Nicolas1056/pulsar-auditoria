package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.render.CompactVertex;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.mojang.blaze3d.GpuFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.chunk.SectionCompiler.Results;

public final class CompactVertices {
   public static final VertexFormat FORMAT = VertexFormat.builder(0)
      .addAttribute("Position", GpuFormat.RGBA16_UINT)
      .addAttribute("Color", GpuFormat.RGBA8_UNORM)
      .addAttribute("UV0", GpuFormat.RG16_UNORM)
      .build();
   private static volatile boolean supported;
   private static volatile boolean anyCompact;
   private static volatile boolean resetScheduled;
   private static volatile long compacted;
   private static volatile long savedBytes;

   private CompactVertices() {
   }

   public static void setSupported(boolean value) {
      if (value && FORMAT.getVertexSize() != 16) {
         BootstrapLog.info("perf: compact vertices disabled, unexpected stride " + FORMAT.getVertexSize());
         value = false;
      }

      supported = value;
   }

   public static boolean enabled() {
      return supported && ClientOptions.get().bool("perf.compactVertices", true);
   }

   public static boolean anyCompact() {
      return anyCompact;
   }

   public static long compacted() {
      return compacted;
   }

   public static long savedBytes() {
      return savedBytes;
   }

   public static void compact(Results results) {
      if (enabled()) {
         if (results instanceof FaceGroups.Holder holder) {
            Map<ChunkSectionLayer, MeshData> layers = results.renderedLayers;

            for (MeshData mesh : layers.values()) {
               if (mesh == null || !isBlockFormat(mesh.drawState().format())) {
                  return;
               }
            }

            long saved = 0L;

            for (MeshData meshx : layers.values()) {
               VertexFormat f = meshx.drawState().format();
               int stride = f.getVertexSize();
               int count = meshx.drawState().vertexCount();
               int bytes = count == 0
                  ? 0
                  : CompactVertex.encode(
                     meshx.vertexBuffer(),
                     count,
                     stride,
                     f.getElement("Position").offset(),
                     f.getElement("Color").offset(),
                     f.getElement("UV0").offset(),
                     f.getElement("UV2").offset()
                  );
               ((CompactVertices.MeshExt)meshx).pulsar$setCompactBytes(bytes);
               saved += (long)count * stride - bytes;
            }

            holder.pulsar$setCompact(true);
            anyCompact = true;
            compacted++;
            savedBytes += saved;
         }
      }
   }

   private static boolean isBlockFormat(VertexFormat f) {
      return has(f, "Position", GpuFormat.RGB32_FLOAT)
         && has(f, "Color", GpuFormat.RGBA8_UNORM)
         && has(f, "UV0", GpuFormat.RG32_FLOAT)
         && has(f, "UV2", GpuFormat.RG16_SINT);
   }

   private static boolean has(VertexFormat f, String name, GpuFormat format) {
      VertexFormatElement e = f.contains(name) ? f.getElement(name) : null;
      return e != null && e.format() == format;
   }

   public static boolean abortIfNeeded(String why) {
      supported = false;
      if (!anyCompact) {
         return false;
      } else {
         if (!resetScheduled) {
            resetScheduled = true;
            BootstrapLog.info("perf: compact vertices off (" + why + "): rebuilding terrain in vanilla format");
            Minecraft mc = Minecraft.getInstance();
            mc.schedule(() -> {
               try {
                  if (mc.level != null && mc.level != null && mc.options != null && mc.gameRenderer != null) {
                     mc.levelRenderer.invalidateCompiledGeometry(mc.level, mc.options, mc.gameRenderer.mainCamera(), mc.getBlockColors());
                  }
               } finally {
                  anyCompact = false;
                  resetScheduled = false;
               }
            });
         }

         return true;
      }
   }

   public interface MeshExt {
      int pulsar$compactBytes();

      void pulsar$setCompactBytes(int var1);
   }
}
