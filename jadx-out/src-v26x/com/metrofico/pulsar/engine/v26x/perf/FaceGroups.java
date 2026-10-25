package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.api.render.FaceSort;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.chunk.SectionMesh;
import net.minecraft.client.renderer.chunk.SectionCompiler.Results;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public final class FaceGroups {
   private static volatile long sortedSections;
   private static volatile long sortedQuads;

   private FaceGroups() {
   }

   public static void sort(Results results) {
      if (results instanceof FaceGroups.Holder holder) {
         EnumMap faces = null;

         for (Entry<ChunkSectionLayer, MeshData> e : results.renderedLayers.entrySet()) {
            ChunkSectionLayer layer = e.getKey();
            MeshData mesh = e.getValue();
            if (layer != ChunkSectionLayer.TRANSLUCENT && mesh != null) {
               int[] counts = sort(mesh);
               if (counts != null) {
                  if (faces == null) {
                     faces = new EnumMap(ChunkSectionLayer.class);
                  }

                  faces.put((Enum)layer, counts);
                  sortedQuads = sortedQuads + mesh.drawState().vertexCount() / 4;
               }
            }
         }

         if (faces != null) {
            sortedSections++;
            holder.pulsar$setFaces(faces);
         }
      }
   }

   static int[] sort(MeshData mesh) {
      VertexFormat format = mesh.drawState().format();
      if (format.getElements().isEmpty() || !"Position".equals(((VertexFormatElement)format.getElements().get(0)).name())) {
         return null;
      } else {
         return mesh.indexBuffer() != null ? null : FaceSort.sort(mesh.vertexBuffer(), mesh.drawState().vertexCount(), format.getVertexSize());
      }
   }

   public static int[] counts(SectionMesh mesh, ChunkSectionLayer layer) {
      if (mesh instanceof FaceGroups.Holder h) {
         Map<ChunkSectionLayer, int[]> faces = h.pulsar$faces();
         return faces == null ? null : faces.get(layer);
      } else {
         return null;
      }
   }

   public static boolean compact(SectionMesh mesh) {
      return mesh instanceof FaceGroups.Holder h && h.pulsar$compact();
   }

   public static long sortedSections() {
      return sortedSections;
   }

   public static long sortedQuads() {
      return sortedQuads;
   }

   public interface Holder {
      Map<ChunkSectionLayer, int[]> pulsar$faces();

      void pulsar$setFaces(Map<ChunkSectionLayer, int[]> var1);

      boolean pulsar$compact();

      void pulsar$setCompact(boolean var1);

      List<TextureAtlasSprite> pulsar$sprites();

      void pulsar$setSprites(List<TextureAtlasSprite> var1);
   }
}
