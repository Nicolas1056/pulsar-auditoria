package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureMap extends AbstractTexture implements ITickableTextureObject {
   private static final Logger h = LogManager.getLogger();
   public static final ResourceLocation f = new ResourceLocation("missingno");
   public static final ResourceLocation g = new ResourceLocation("textures/atlas/blocks.png");
   private final List<TextureAtlasSprite> i = Lists.newArrayList();
   private final Map<String, TextureAtlasSprite> j = Maps.newHashMap();
   private final Map<String, TextureAtlasSprite> k = Maps.newHashMap();
   private final String l;
   private final IIconCreator m;
   private int n;
   private final TextureAtlasSprite o = new TextureAtlasSprite("missingno");

   public TextureMap(String var1) {
      this(var1, null);
   }

   public TextureMap(String var1, IIconCreator var2) {
      this.l = var1;
      this.m = var2;
   }

   private void g() {
      int[] var1 = TextureUtil.b;
      this.o.b(16);
      this.o.c(16);
      int[][] var2 = new int[this.n + 1][];
      var2[0] = var1;
      this.o.a(Lists.newArrayList(new int[][][]{var2}));
   }

   @Override
   public void a(IResourceManager var1) throws IOException {
      if (this.m != null) {
         this.a(var1, this.m);
      }
   }

   public void a(IResourceManager var1, IIconCreator var2) {
      this.j.clear();
      var2.a(this);
      this.g();
      this.c();
      this.b(var1);
   }

   public void b(IResourceManager var1) {
      int var2 = Minecraft.C();
      Stitcher var3 = new Stitcher(var2, var2, true, 0, this.n);
      this.k.clear();
      this.i.clear();
      int var4 = Integer.MAX_VALUE;
      int var5 = 1 << this.n;

      for (Entry var7 : this.j.entrySet()) {
         TextureAtlasSprite var8 = (TextureAtlasSprite)var7.getValue();
         ResourceLocation var9 = new ResourceLocation(var8.i());
         ResourceLocation var10 = this.a(var9, 0);

         try {
            IResource var11 = var1.a(var10);
            BufferedImage[] var12 = new BufferedImage[1 + this.n];
            var12[0] = TextureUtil.a(var11.b());
            TextureMetadataSection var13 = var11.a("texture");
            if (var13 != null) {
               List var14 = var13.c();
               if (!var14.isEmpty()) {
                  int var15 = var12[0].getWidth();
                  int var16 = var12[0].getHeight();
                  if (MathHelper.b(var15) != var15 || MathHelper.b(var16) != var16) {
                     throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
                  }
               }

               for (int var42 : var14) {
                  if (var42 > 0 && var42 < var12.length - 1 && var12[var42] == null) {
                     ResourceLocation var17 = this.a(var9, var42);

                     try {
                        var12[var42] = TextureUtil.a(var1.a(var17).b());
                     } catch (IOException var22) {
                        h.error("Unable to load miplevel {} from: {}", new Object[]{var42, var17, var22});
                     }
                  }
               }
            }

            AnimationMetadataSection var39 = var11.a("animation");
            var8.a(var12, var39);
         } catch (RuntimeException var23) {
            h.error("Unable to parse metadata from " + var10, var23);
            continue;
         } catch (IOException var24) {
            h.error("Using missing texture, unable to load " + var10, var24);
            continue;
         }

         var4 = Math.min(var4, Math.min(var8.c(), var8.d()));
         int var34 = Math.min(Integer.lowestOneBit(var8.c()), Integer.lowestOneBit(var8.d()));
         if (var34 < var5) {
            h.warn(
               "Texture {} with size {}x{} limits mip level from {} to {}", new Object[]{var10, var8.c(), var8.d(), MathHelper.c(var5), MathHelper.c(var34)}
            );
            var5 = var34;
         }

         var3.a(var8);
      }

      int var25 = Math.min(var4, var5);
      int var26 = MathHelper.c(var25);
      if (var26 < this.n) {
         h.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[]{this.l, this.n, var26, var25});
         this.n = var26;
      }

      for (final TextureAtlasSprite var29 : this.j.values()) {
         try {
            var29.d(this.n);
         } catch (Throwable var21) {
            CrashReport var35 = CrashReport.a(var21, "Applying mipmap");
            CrashReportCategory var37 = var35.a("Sprite being mipmapped");
            var37.a("Sprite name", new Callable<String>() {
               public String a() throws Exception {
                  return var29.i();
               }
            });
            var37.a("Sprite size", new Callable<String>() {
               public String a() throws Exception {
                  return var29.c() + " x " + var29.d();
               }
            });
            var37.a("Sprite frames", new Callable<String>() {
               public String a() throws Exception {
                  return var29.k() + " frames";
               }
            });
            var37.a("Mipmap levels", this.n);
            throw new ReportedException(var35);
         }
      }

      this.o.d(this.n);
      var3.a(this.o);

      try {
         var3.c();
      } catch (StitcherException var20) {
         throw var20;
      }

      h.info("Created: {}x{} {}-atlas", new Object[]{var3.a(), var3.b(), this.l});
      TextureUtil.a(this.b(), this.n, var3.a(), var3.b());
      HashMap var28 = Maps.newHashMap(this.j);

      for (TextureAtlasSprite var32 : var3.d()) {
         String var36 = var32.i();
         var28.remove(var36);
         this.k.put(var36, var32);

         try {
            TextureUtil.a(var32.a(0), var32.c(), var32.d(), var32.a(), var32.b(), false, false);
         } catch (Throwable var19) {
            CrashReport var38 = CrashReport.a(var19, "Stitching texture atlas");
            CrashReportCategory var40 = var38.a("Texture being stitched together");
            var40.a("Atlas path", this.l);
            var40.a("Sprite", var32);
            throw new ReportedException(var38);
         }

         if (var32.m()) {
            this.i.add(var32);
         }
      }

      for (TextureAtlasSprite var33 : var28.values()) {
         var33.a(this.o);
      }
   }

   private ResourceLocation a(ResourceLocation var1, int var2) {
      return var2 == 0
         ? new ResourceLocation(var1.b(), String.format("%s/%s%s", this.l, var1.a(), ".png"))
         : new ResourceLocation(var1.b(), String.format("%s/mipmaps/%s.%d%s", this.l, var1.a(), var2, ".png"));
   }

   public TextureAtlasSprite a(String var1) {
      TextureAtlasSprite var2 = this.k.get(var1);
      if (var2 == null) {
         var2 = this.o;
      }

      return var2;
   }

   public void d() {
      TextureUtil.b(this.b());

      for (TextureAtlasSprite var2 : this.i) {
         var2.j();
      }
   }

   public TextureAtlasSprite a(ResourceLocation var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Location cannot be null!");
      } else {
         TextureAtlasSprite var2 = this.j.get(var1);
         if (var2 == null) {
            var2 = TextureAtlasSprite.a(var1);
            this.j.put(var1.toString(), var2);
         }

         return var2;
      }
   }

   @Override
   public void e() {
      this.d();
   }

   public void a(int var1) {
      this.n = var1;
   }

   public TextureAtlasSprite f() {
      return this.o;
   }
}
