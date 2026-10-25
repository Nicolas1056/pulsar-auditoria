package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.render.FaceSort;
import com.metrofico.pulsar.api.render.FogCull;
import com.metrofico.pulsar.api.render.TerrainShaderPatch;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.metrofico.pulsar.engine.v26x.mixin.LevelRendererAccessor;
import com.metrofico.pulsar.engine.v26x.mixin.RenderPipelinesAccessor;
import com.mojang.blaze3d.GpuFormat;
import com.mojang.blaze3d.IndexType;
import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.BindGroupLayout;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.RenderPipeline.Builder;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.RenderPass.Draw;
import com.mojang.blaze3d.systems.RenderSystem.AutoStorageIndexBuffer;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTextureView;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.DynamicUniforms.ChunkSectionInfo;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.chunk.ChunkSectionLayerGroup;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import net.minecraft.client.renderer.chunk.SectionMesh;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.client.renderer.chunk.SectionMesh.SectionDraw;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSectionBufferSlice;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4fc;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

public final class TerrainMultidraw {
   private static final int COMMAND_BYTES = 20;
   private static final int INFO_BYTES = 16;
   private static final int RING = 3;
   private static volatile TerrainMultidraw.Frame frame;
   private static final TerrainMultidraw.Frame REUSED = new TerrainMultidraw.Frame();
   private static Boolean supported;
   private static final EnumMap<ChunkSectionLayer, RenderPipeline> PIPELINES = new EnumMap<>(ChunkSectionLayer.class);
   private static final EnumMap<ChunkSectionLayer, RenderPipeline> PIPELINES_COMPACT = new EnumMap<>(ChunkSectionLayer.class);
   private static GpuBuffer[][] COMMAND_BUFFERS = new GpuBuffer[3][8];
   private static GpuBuffer[][] INFO_BUFFERS = new GpuBuffer[3][8];
   private static int ring;
   private static ByteBuffer staging = ByteBuffer.allocateDirect(65536).order(ByteOrder.nativeOrder());
   private static ByteBuffer stagingInfo = ByteBuffer.allocateDirect(65536).order(ByteOrder.nativeOrder());
   public static final Identifier SHADER = Identifier.fromNamespaceAndPath("pulsar", "core/terrain_mdi");
   public static final Identifier SHADER_COMPACT = Identifier.fromNamespaceAndPath("pulsar", "core/terrain_mdi_compact");

   private TerrainMultidraw() {
   }

   public static boolean active() {
      ClientOptions o = ClientOptions.get();
      return o.enabled("perf") && o.bool("perf.terrainMultidraw", true) && supported();
   }

   private static boolean supported() {
      if (supported == null) {
         try {
            String backend = PerfStats.backendClass();
            if (!backend.startsWith("com.mojang.blaze3d.opengl.")) {
               if (backend.startsWith("com.mojang.blaze3d.vulkan.")) {
                  supported = true;
               } else {
                  supported = false;
               }
            } else {
               GLCapabilities caps = GL.getCapabilities();
               supported = caps != null && caps.GL_ARB_shader_draw_parameters && caps.GL_ARB_multi_draw_indirect && caps.OpenGL33;
            }

            BootstrapLog.info(
               "perf: terrain multidraw " + (supported ? "enabled on " + backend.substring(backend.lastIndexOf(46) + 1) : "not supported on " + backend)
            );
            CompactVertices.setSupported(supported);
         } catch (Throwable var2) {
            supported = false;
            BootstrapLog.info("perf: terrain multidraw check failed: " + var2);
         }
      }

      return supported;
   }

   private static void fail(String what, Throwable t) {
      supported = false;
      frame = null;
      BootstrapLog.info("perf: terrain multidraw disabled after failure in " + what + ": " + t);
      CompactVertices.abortIfNeeded("failure in " + what);
   }

   private static RenderPipeline pipeline(ChunkSectionLayer layer, boolean compact) {
      return (compact ? PIPELINES_COMPACT : PIPELINES)
         .computeIfAbsent(
            layer,
            l -> {
               BindGroupLayout extra = BindGroupLayout.builder().withUniform("PulsarSections", UniformType.TEXEL_BUFFER, GpuFormat.RGBA32_SINT).build();
               Identifier shader = compact ? SHADER_COMPACT : SHADER;
               Builder b = RenderPipeline.builder(new Snippet[]{RenderPipelinesAccessor.pulsar$terrainSnippet()})
                  .withLocation(Identifier.fromNamespaceAndPath("pulsar", "pipeline/" + l.label() + (compact ? "_terrain_mdi_compact" : "_terrain_mdi")))
                  .withVertexShader(shader)
                  .withFragmentShader(shader)
                  .withBindGroupLayout(extra);
               if (compact) {
                  b.withVertexBinding(0, CompactVertices.FORMAT);
               }

               switch (l) {
                  case CUTOUT:
                     b.withShaderDefine("ALPHA_CUTOUT", 0.5F);
                     break;
                  case TRANSLUCENT:
                     b.withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT)).withShaderDefine("ALPHA_CUTOUT", 0.1F);
               }

               return b.build();
            }
         );
   }

   public static ChunkSectionsToRender prepare(LevelRenderer lr, Matrix4fc modelView) {
      TerrainMultidraw.Frame f = REUSED;
      f.reset();
      frame = null;

      try {
         Minecraft mc = Minecraft.getInstance();
         boolean faceCulling = ClientOptions.get().bool("perf.faceCulling", true);
         Vec3 cam = mc.gameRenderer.mainCamera().position();
         FogData fog = null;
         if (ClientOptions.get().bool("perf.fogCulling", true)
            && lr instanceof LevelRendererAccessor a
            && a.pulsar$levelRenderState() != null
            && a.pulsar$levelRenderState().cameraRenderState != null) {
            fog = a.pulsar$levelRenderState().cameraRenderState.fogData;
         }

         f.atlas = mc.getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).getTextureView();
         SectionRenderDispatcher dispatcher = lr.sectionRenderDispatcher();
         long now = Util.getMillis();
         int atlasW = f.atlas.getWidth(0);
         int atlasH = f.atlas.getHeight(0);
         dispatcher.lock();

         try {
            ObjectListIterator var12 = lr.visibleSections().iterator();

            while (var12.hasNext()) {
               RenderSection section = (RenderSection)var12.next();
               SectionMesh mesh = section.getSectionMesh();
               if (mesh != null) {
                  BlockPos origin = section.getRenderOrigin();
                  if (fog != null
                     && fog.color != null
                     && FogCull.fullyFogged(
                        cam.x, cam.y, cam.z, origin.getX(), origin.getY(), origin.getZ(), 16, fog.renderDistanceEnd, fog.environmentalEnd, fog.color.w, 2.0F
                     )) {
                     f.fogCulled++;
                  } else {
                     float visibility = section.getVisibility(now);
                     int visibleMask = faceCulling ? FaceSort.visibleMask(cam.x, cam.y, cam.z, origin.getX(), origin.getY(), origin.getZ()) : -1;

                     for (ChunkSectionLayer layer : ChunkSectionLayer.values()) {
                        SectionDraw draw = mesh.getSectionDraw(layer);
                        if (draw != null && draw.indexCount() > 0) {
                           RenderSectionBufferSlice slice = dispatcher.getRenderSectionSlice(mesh, layer);
                           if (slice != null && slice.vertexBuffer() != null) {
                              GpuBuffer index = draw.hasCustomIndexBuffer() ? slice.indexBuffer() : null;
                              IndexType type = draw.hasCustomIndexBuffer() ? draw.indexType() : null;
                              int firstIndex = index == null ? 0 : (int)(slice.indexBufferOffset() / type.bytes);
                              boolean compact = FaceGroups.compact(mesh);
                              int stride = compact ? 16 : layer.pipeline().getVertexFormatBinding(0).getVertexSize();
                              if (slice.vertexBufferOffset() % stride != 0L) {
                                 throw new IllegalStateException("section vertex offset " + slice.vertexBufferOffset() + " not aligned to stride " + stride);
                              }

                              int baseVertex = (int)(slice.vertexBufferOffset() / stride);
                              if (compact) {
                                 f.compactSections++;
                              }

                              if (index == null) {
                                 f.maxIndices = Math.max(f.maxIndices, draw.indexCount());
                              }

                              TerrainMultidraw.Batch b = batch(f.batches.get(layer), slice.vertexBuffer(), index, type, compact);
                              int[] faces = index == null && visibleMask != -1 ? FaceGroups.counts(mesh, layer) : null;
                              if (faces == null) {
                                 b.add(draw.indexCount(), firstIndex, baseVertex, origin.getX(), origin.getY(), origin.getZ(), visibility);
                              } else {
                                 addFaceGroups(f, b, faces, visibleMask, firstIndex, baseVertex, origin, visibility);
                              }

                              f.sections++;
                           }
                        }
                     }
                  }
               }
            }
         } finally {
            dispatcher.unlock();
         }

         for (TerrainMultidraw.Batch b : f.batches.get(ChunkSectionLayer.TRANSLUCENT)) {
            reverse(b);
         }

         f.trim();
         f.sectionUbo = RenderSystem.getDynamicUniforms()
            .writeChunkSections(new ChunkSectionInfo[]{new ChunkSectionInfo(modelView, 0, 0, 0, 1.0F, atlasW, atlasH)})[0];
         upload(f);
         frame = f;
      } catch (Throwable var36) {
         fail("prepare", var36);
      }

      return empty(f.atlas, f.maxIndices);
   }

   public static ChunkSectionsToRender empty() {
      return empty(null, 0);
   }

   private static ChunkSectionsToRender empty(GpuTextureView atlas, int maxIndices) {
      EnumMap<ChunkSectionLayer, Int2ObjectOpenHashMap<List<Draw<GpuBufferSlice[]>>>> empty = new EnumMap<>(ChunkSectionLayer.class);

      for (ChunkSectionLayer layer : ChunkSectionLayer.values()) {
         empty.put(layer, new Int2ObjectOpenHashMap());
      }

      if (atlas == null) {
         atlas = Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).getTextureView();
      }

      return new ChunkSectionsToRender(atlas, empty, maxIndices, new GpuBufferSlice[0]);
   }

   private static void addFaceGroups(
      TerrainMultidraw.Frame f, TerrainMultidraw.Batch b, int[] faces, int visibleMask, int firstIndex, int baseVertex, BlockPos origin, float visibility
   ) {
      int quad = 0;
      int runStart = -1;

      for (int g = 0; g < 7; g++) {
         int n = faces[g];
         boolean visible = (visibleMask & 1 << g) != 0;
         f.quads += n;
         if (!visible) {
            f.culledQuads += n;
         }

         if (visible && n > 0) {
            if (runStart < 0) {
               runStart = quad;
            }
         } else if (runStart >= 0) {
            b.add((quad - runStart) * 6, firstIndex + runStart * 6, baseVertex, origin.getX(), origin.getY(), origin.getZ(), visibility);
            runStart = -1;
         }

         quad += n;
      }

      if (runStart >= 0) {
         b.add((quad - runStart) * 6, firstIndex + runStart * 6, baseVertex, origin.getX(), origin.getY(), origin.getZ(), visibility);
      }
   }

   private static TerrainMultidraw.Batch batch(List<TerrainMultidraw.Batch> list, GpuBuffer vb, GpuBuffer ib, IndexType type, boolean compact) {
      for (TerrainMultidraw.Batch b : list) {
         if (b.vertexBuffer == vb && b.indexBuffer == ib && b.indexType == type && b.compact == compact) {
            return b;
         }
      }

      TerrainMultidraw.Batch bx = new TerrainMultidraw.Batch(vb, ib, type, compact);
      list.add(bx);
      return bx;
   }

   private static void reverse(TerrainMultidraw.Batch b) {
      int i = 0;

      for (int j = b.count - 1; i < j; j--) {
         for (int k = 0; k < 5; k++) {
            int t = b.commands[i * 5 + k];
            b.commands[i * 5 + k] = b.commands[j * 5 + k];
            b.commands[j * 5 + k] = t;
         }

         for (int k = 0; k < 4; k++) {
            int t = b.infos[i * 4 + k];
            b.infos[i * 4 + k] = b.infos[j * 4 + k];
            b.infos[j * 4 + k] = t;
         }

         i++;
      }
   }

   private static void upload(TerrainMultidraw.Frame f) {
      int batches = 0;
      int maxCount = 0;

      for (List<TerrainMultidraw.Batch> list : f.batches.values()) {
         for (TerrainMultidraw.Batch b : list) {
            if (b.count > 0) {
               b.index = batches++;
               maxCount = Math.max(maxCount, b.count);
            }
         }
      }

      if (batches != 0) {
         int commandBytes = maxCount * 20;
         int infoBytes = maxCount * 16;
         if (staging.capacity() < commandBytes) {
            staging = ByteBuffer.allocateDirect(Integer.highestOneBit(commandBytes) * 2).order(ByteOrder.nativeOrder());
         }

         if (stagingInfo.capacity() < infoBytes) {
            stagingInfo = ByteBuffer.allocateDirect(Integer.highestOneBit(infoBytes) * 2).order(ByteOrder.nativeOrder());
         }

         ring = (ring + 1) % 3;
         if (COMMAND_BUFFERS[ring].length < batches) {
            COMMAND_BUFFERS[ring] = Arrays.copyOf(COMMAND_BUFFERS[ring], batches * 2);
            INFO_BUFFERS[ring] = Arrays.copyOf(INFO_BUFFERS[ring], batches * 2);
         }

         GpuDevice device = RenderSystem.getDevice();
         CommandEncoder enc = device.createCommandEncoder();

         for (List<TerrainMultidraw.Batch> list : f.batches.values()) {
            for (TerrainMultidraw.Batch bx : list) {
               if (bx.count != 0) {
                  int cb = bx.count * 20;
                  int ib = bx.count * 16;
                  staging.clear();
                  staging.asIntBuffer().put(bx.commands, 0, bx.count * 5);
                  staging.position(0).limit(cb);
                  stagingInfo.clear();
                  stagingInfo.asIntBuffer().put(bx.infos, 0, bx.count * 4);
                  stagingInfo.position(0).limit(ib);
                  GpuBuffer[] cmds = COMMAND_BUFFERS[ring];
                  GpuBuffer[] infos = INFO_BUFFERS[ring];
                  cmds[bx.index] = ensure(device, cmds[bx.index], cb, 520, "pulsar terrain commands " + bx.index);
                  infos[bx.index] = ensure(device, infos[bx.index], ib, 264, "pulsar terrain sections " + bx.index);
                  enc.writeToBuffer(cmds[bx.index].slice(0L, cb), staging);
                  enc.writeToBuffer(infos[bx.index].slice(0L, ib), stagingInfo);
               }
            }
         }
      }
   }

   private static GpuBuffer ensure(GpuDevice device, GpuBuffer current, int bytes, int usage, String name) {
      if (current != null && current.size() >= bytes) {
         return current;
      } else {
         if (current != null) {
            current.close();
         }

         long size = Math.max(65536L, Integer.highestOneBit(bytes) * 2L);
         return device.createBuffer(() -> name, usage, size);
      }
   }

   public static boolean renderGroup(ChunkSectionLayerGroup group, GpuSampler sampler) {
      TerrainMultidraw.Frame f = frame;
      if (f == null) {
         return false;
      } else {
         try {
            Minecraft mc = Minecraft.getInstance();
            AutoStorageIndexBuffer sequential = RenderSystem.getSequentialBuffer(PrimitiveTopology.QUADS);
            GpuBuffer sharedIndex = sequential.getBuffer(Math.max(6, f.maxIndices));
            IndexType sharedType = sequential.type();
            RenderTarget target = group.outputTarget();
            RenderPass pass = RenderSystem.getDevice()
               .createCommandEncoder()
               .createRenderPass(
                  () -> "pulsar terrain " + group.label(), target.getColorTextureView(), Optional.empty(), target.getDepthTextureView(), OptionalDouble.empty()
               );

            try {
               RenderSystem.bindDefaultUniforms(pass);
               pass.bindTexture("Sampler0", f.atlas, sampler);
               pass.bindTexture("Sampler2", mc.gameRenderer.lightmap(), RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR));

               for (ChunkSectionLayer layer : group.layers()) {
                  List<TerrainMultidraw.Batch> batches = f.batches.get(layer);
                  if (batches != null && !batches.isEmpty()) {
                     Boolean bound = null;

                     for (TerrainMultidraw.Batch b : batches) {
                        if (b.count != 0) {
                           if (bound == null || bound != b.compact) {
                              pass.setPipeline(pipeline(layer, b.compact));
                              pass.setUniform("ChunkSection", f.sectionUbo);
                              bound = b.compact;
                           }

                           pass.setVertexBuffer(0, b.vertexBuffer.slice());
                           pass.setIndexBuffer(b.indexBuffer != null ? b.indexBuffer : sharedIndex, b.indexBuffer != null ? b.indexType : sharedType);
                           pass.setUniform("PulsarSections", INFO_BUFFERS[ring][b.index].slice(0L, b.count * 16L));
                           pass.drawIndexedIndirect(COMMAND_BUFFERS[ring][b.index].slice(0L, b.count * 20L), b.count);
                           f.draws++;
                        }
                     }
                  }
               }
            } catch (Throwable var18) {
               if (pass != null) {
                  try {
                     pass.close();
                  } catch (Throwable var17) {
                     var18.addSuppressed(var17);
                  }
               }

               throw var18;
            }

            if (pass != null) {
               pass.close();
            }

            PerfStats.terrain(f.sections, f.draws, f.quads, f.culledQuads, f.compactSections, f.fogCulled);
            return true;
         } catch (Throwable var19) {
            fail("render", var19);
            return false;
         }
      }
   }

   public static String patchShader(String vanilla, boolean vertex, boolean compact) {
      int minVersion = "VK".equals(PerfStats.backend()) ? 450 : 0;
      return TerrainShaderPatch.patch(vanilla, vertex, compact, minVersion);
   }

   private static final class Batch {
      final GpuBuffer vertexBuffer;
      final GpuBuffer indexBuffer;
      final IndexType indexType;
      final boolean compact;
      int[] commands = new int[320];
      int[] infos = new int[256];
      int count;
      int index;

      Batch(GpuBuffer vertexBuffer, GpuBuffer indexBuffer, IndexType indexType, boolean compact) {
         this.vertexBuffer = vertexBuffer;
         this.indexBuffer = indexBuffer;
         this.indexType = indexType;
         this.compact = compact;
      }

      void add(int indexCount, int firstIndex, int baseVertex, int x, int y, int z, float visibility) {
         if (this.count * 5 + 5 > this.commands.length) {
            this.commands = Arrays.copyOf(this.commands, this.commands.length * 2);
            this.infos = Arrays.copyOf(this.infos, this.infos.length * 2);
         }

         int c = this.count * 5;
         this.commands[c] = indexCount;
         this.commands[c + 1] = 1;
         this.commands[c + 2] = firstIndex;
         this.commands[c + 3] = baseVertex;
         this.commands[c + 4] = 0;
         int i = this.count * 4;
         this.infos[i] = x;
         this.infos[i + 1] = y;
         this.infos[i + 2] = z;
         this.infos[i + 3] = Float.floatToRawIntBits(visibility);
         this.count++;
      }
   }

   private static final class Frame {
      final EnumMap<ChunkSectionLayer, List<TerrainMultidraw.Batch>> batches = new EnumMap<>(ChunkSectionLayer.class);
      GpuBufferSlice sectionUbo;
      GpuTextureView atlas;
      int maxIndices;
      int sections;
      int draws;
      long quads;
      long culledQuads;
      int compactSections;
      int fogCulled;

      Frame() {
         for (ChunkSectionLayer layer : ChunkSectionLayer.values()) {
            this.batches.put(layer, new ArrayList<>());
         }
      }

      void reset() {
         this.sectionUbo = null;
         this.atlas = null;
         this.maxIndices = 0;
         this.sections = 0;
         this.draws = 0;
         this.quads = 0L;
         this.culledQuads = 0L;
         this.compactSections = 0;
         this.fogCulled = 0;

         for (List<TerrainMultidraw.Batch> list : this.batches.values()) {
            for (TerrainMultidraw.Batch b : list) {
               b.count = 0;
            }
         }
      }

      void trim() {
         for (List<TerrainMultidraw.Batch> list : this.batches.values()) {
            for (int i = list.size() - 1; i >= 0; i--) {
               if (list.get(i).count == 0) {
                  list.remove(i);
               }
            }
         }
      }
   }
}
