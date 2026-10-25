package net.minecraft.client.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class GuiOverlayDebug extends Gui {
   private final Minecraft a;
   private final FontRenderer f;

   public GuiOverlayDebug(Minecraft var1) {
      this.a = var1;
      this.f = var1.k;
   }

   public void a(ScaledResolution var1) {
      this.a.A.a("debug");
      GlStateManager.E();
      this.a();
      this.b(var1);
      GlStateManager.F();
      if (this.a.t.aE) {
         this.e();
      }

      this.a.A.b();
   }

   private boolean d() {
      return this.a.h.cq() || this.a.t.w;
   }

   protected void a() {
      List var1 = this.b();

      for (int var2 = 0; var2 < var1.size(); var2++) {
         String var3 = (String)var1.get(var2);
         if (!Strings.isNullOrEmpty(var3)) {
            int var4 = this.f.a;
            int var5 = this.f.a(var3);
            byte var6 = 2;
            int var7 = 2 + var4 * var2;
            a(1, var7 - 1, 2 + var5 + 1, var7 + var4 - 1, -1873784752);
            this.f.a(var3, 2, var7, 14737632);
         }
      }
   }

   protected void b(ScaledResolution var1) {
      List var2 = this.c();

      for (int var3 = 0; var3 < var2.size(); var3++) {
         String var4 = (String)var2.get(var3);
         if (!Strings.isNullOrEmpty(var4)) {
            int var5 = this.f.a;
            int var6 = this.f.a(var4);
            int var7 = var1.a() - 2 - var6;
            int var8 = 2 + var5 * var3;
            a(var7 - 1, var8 - 1, var7 + var6 + 1, var8 + var5 - 1, -1873784752);
            this.f.a(var4, var7, var8, 14737632);
         }
      }
   }

   protected List<String> b() {
      BlockPos var1 = new BlockPos(this.a.ac().s, this.a.ac().aR().b, this.a.ac().u);
      if (this.d()) {
         return Lists.newArrayList(
            new String[]{
               "Minecraft 1.8.9 (" + this.a.c() + "/" + ClientBrandRetriever.getClientModName() + ")",
               this.a.C,
               this.a.g.f(),
               this.a.g.g(),
               "P: " + this.a.j.b() + ". T: " + this.a.f.z(),
               this.a.f.A(),
               "",
               String.format("Chunk-relative: %d %d %d", var1.n() & 15, var1.o() & 15, var1.p() & 15)
            }
         );
      } else {
         Entity var2 = this.a.ac();
         EnumFacing var3 = var2.aP();
         String var4 = "Invalid";
         switch (var3) {
            case c:
               var4 = "Towards negative Z";
               break;
            case d:
               var4 = "Towards positive Z";
               break;
            case e:
               var4 = "Towards negative X";
               break;
            case f:
               var4 = "Towards positive X";
         }

         ArrayList var5 = Lists.newArrayList(
            new String[]{
               "Minecraft 1.8.9 (" + this.a.c() + "/" + ClientBrandRetriever.getClientModName() + ")",
               this.a.C,
               this.a.g.f(),
               this.a.g.g(),
               "P: " + this.a.j.b() + ". T: " + this.a.f.z(),
               this.a.f.A(),
               "",
               String.format("XYZ: %.3f / %.5f / %.3f", this.a.ac().s, this.a.ac().aR().b, this.a.ac().u),
               String.format("Block: %d %d %d", var1.n(), var1.o(), var1.p()),
               String.format("Chunk: %d %d %d in %d %d %d", var1.n() & 15, var1.o() & 15, var1.p() & 15, var1.n() >> 4, var1.o() >> 4, var1.p() >> 4),
               String.format("Facing: %s (%s) (%.1f / %.1f)", var3, var4, MathHelper.g(var2.y), MathHelper.g(var2.z))
            }
         );
         if (this.a.f != null && this.a.f.e(var1)) {
            Chunk var6 = this.a.f.f(var1);
            var5.add("Biome: " + var6.a(var1, this.a.f.v()).ah);
            var5.add("Light: " + var6.a(var1, 0) + " (" + var6.a(EnumSkyBlock.a, var1) + " sky, " + var6.a(EnumSkyBlock.b, var1) + " block)");
            DifficultyInstance var7 = this.a.f.E(var1);
            if (this.a.E() && this.a.G() != null) {
               EntityPlayerMP var8 = this.a.G().ap().a(this.a.h.aK());
               if (var8 != null) {
                  var7 = var8.o.E(new BlockPos(var8));
               }
            }

            var5.add(String.format("Local Difficulty: %.2f (Day %d)", var7.b(), this.a.f.L() / 24000L));
         }

         if (this.a.o != null && this.a.o.a()) {
            var5.add("Shader: " + this.a.o.f().b());
         }

         if (this.a.s != null && this.a.s.a == MovingObjectPosition.MovingObjectType.b && this.a.s.a() != null) {
            BlockPos var9 = this.a.s.a();
            var5.add(String.format("Looking at: %d %d %d", var9.n(), var9.o(), var9.p()));
         }

         return var5;
      }
   }

   protected List<String> c() {
      long var1 = Runtime.getRuntime().maxMemory();
      long var3 = Runtime.getRuntime().totalMemory();
      long var5 = Runtime.getRuntime().freeMemory();
      long var7 = var3 - var5;
      ArrayList var9 = Lists.newArrayList(
         new String[]{
            String.format("Java: %s %dbit", System.getProperty("java.version"), this.a.U() ? 64 : 32),
            String.format("Mem: % 2d%% %03d/%03dMB", var7 * 100L / var1, a(var7), a(var1)),
            String.format("Allocated: % 2d%% %03dMB", var3 * 100L / var1, a(var3)),
            "",
            String.format("CPU: %s", OpenGlHelper.j()),
            "",
            String.format("Display: %dx%d (%s)", Display.getWidth(), Display.getHeight(), GL11.glGetString(7936)),
            GL11.glGetString(7937),
            GL11.glGetString(7938)
         }
      );
      if (this.d()) {
         return var9;
      } else {
         if (this.a.s != null && this.a.s.a == MovingObjectPosition.MovingObjectType.b && this.a.s.a() != null) {
            BlockPos var10 = this.a.s.a();
            IBlockState var11 = this.a.f.p(var10);
            if (this.a.f.G() != WorldType.g) {
               var11 = var11.c().a(var11, this.a.f, var10);
            }

            var9.add("");
            var9.add(String.valueOf(Block.c.c(var11.c())));

            for (Entry var13 : var11.b().entrySet()) {
               Object var14 = ((Comparable)var13.getValue()).toString();
               if (var13.getValue() == Boolean.TRUE) {
                  var14 = EnumChatFormatting.k + var14;
               } else if (var13.getValue() == Boolean.FALSE) {
                  var14 = EnumChatFormatting.m + var14;
               }

               var9.add(((IProperty)var13.getKey()).a() + ": " + var14);
            }
         }

         return var9;
      }
   }

   private void e() {
      GlStateManager.i();
      FrameTimer var1 = this.a.aj();
      int var2 = var1.a();
      int var3 = var1.b();
      long[] var4 = var1.c();
      ScaledResolution var5 = new ScaledResolution(this.a);
      int var6 = var2;
      int var7 = 0;
      a(0, var5.b() - 60, 240, var5.b(), -1873784752);

      while (var6 != var3) {
         int var8 = var1.a(var4[var6], 30);
         int var9 = this.c(MathHelper.a(var8, 0, 60), 0, 30, 60);
         this.b(var7, var5.b(), var5.b() - var8, var9);
         var7++;
         var6 = var1.b(var6 + 1);
      }

      a(1, var5.b() - 30 + 1, 14, var5.b() - 30 + 10, -1873784752);
      this.f.a("60", 2, var5.b() - 30 + 2, 14737632);
      this.a(0, 239, var5.b() - 30, -1);
      a(1, var5.b() - 60 + 1, 14, var5.b() - 60 + 10, -1873784752);
      this.f.a("30", 2, var5.b() - 60 + 2, 14737632);
      this.a(0, 239, var5.b() - 60, -1);
      this.a(0, 239, var5.b() - 1, -1);
      this.b(0, var5.b() - 60, var5.b(), -1);
      this.b(239, var5.b() - 60, var5.b(), -1);
      if (this.a.t.g <= 120) {
         this.a(0, 239, var5.b() - 60 + this.a.t.g / 2, -16711681);
      }

      GlStateManager.j();
   }

   private int c(int var1, int var2, int var3, int var4) {
      return var1 < var3 ? this.a(-16711936, -256, (float)var1 / var3) : this.a(-256, -65536, (float)(var1 - var3) / (var4 - var3));
   }

   private int a(int var1, int var2, float var3) {
      int var4 = var1 >> 24 & 0xFF;
      int var5 = var1 >> 16 & 0xFF;
      int var6 = var1 >> 8 & 0xFF;
      int var7 = var1 & 0xFF;
      int var8 = var2 >> 24 & 0xFF;
      int var9 = var2 >> 16 & 0xFF;
      int var10 = var2 >> 8 & 0xFF;
      int var11 = var2 & 0xFF;
      int var12 = MathHelper.a((int)(var4 + (var8 - var4) * var3), 0, 255);
      int var13 = MathHelper.a((int)(var5 + (var9 - var5) * var3), 0, 255);
      int var14 = MathHelper.a((int)(var6 + (var10 - var6) * var3), 0, 255);
      int var15 = MathHelper.a((int)(var7 + (var11 - var7) * var3), 0, 255);
      return var12 << 24 | var13 << 16 | var14 << 8 | var15;
   }

   private static long a(long var0) {
      return var0 / 1024L / 1024L;
   }
}
