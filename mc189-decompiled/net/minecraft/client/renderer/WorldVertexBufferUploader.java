package net.minecraft.client.renderer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.List;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import org.lwjgl.opengl.GL11;

public class WorldVertexBufferUploader {
   public void a(WorldRenderer var1) {
      if (var1.h() > 0) {
         VertexFormat var2 = var1.g();
         int var3 = var2.g();
         ByteBuffer var4 = var1.f();
         List var5 = var2.h();

         for (int var6 = 0; var6 < var5.size(); var6++) {
            VertexFormatElement var7 = (VertexFormatElement)var5.get(var6);
            VertexFormatElement.EnumUsage var8 = var7.b();
            int var9 = var7.a().c();
            int var10 = var7.d();
            ((Buffer)var4).position(var2.d(var6));
            switch (var8) {
               case a:
                  GL11.glVertexPointer(var7.c(), var9, var3, var4);
                  GL11.glEnableClientState(32884);
                  break;
               case d:
                  OpenGlHelper.l(OpenGlHelper.q + var10);
                  GL11.glTexCoordPointer(var7.c(), var9, var3, var4);
                  GL11.glEnableClientState(32888);
                  OpenGlHelper.l(OpenGlHelper.q);
                  break;
               case c:
                  GL11.glColorPointer(var7.c(), var9, var3, var4);
                  GL11.glEnableClientState(32886);
                  break;
               case b:
                  GL11.glNormalPointer(var9, var3, var4);
                  GL11.glEnableClientState(32885);
            }
         }

         GL11.glDrawArrays(var1.i(), 0, var1.h());
         int var11 = 0;

         for (int var12 = var5.size(); var11 < var12; var11++) {
            VertexFormatElement var13 = (VertexFormatElement)var5.get(var11);
            VertexFormatElement.EnumUsage var14 = var13.b();
            int var15 = var13.d();
            switch (var14) {
               case a:
                  GL11.glDisableClientState(32884);
                  break;
               case d:
                  OpenGlHelper.l(OpenGlHelper.q + var15);
                  GL11.glDisableClientState(32888);
                  OpenGlHelper.l(OpenGlHelper.q);
                  break;
               case c:
                  GL11.glDisableClientState(32886);
                  GlStateManager.G();
                  break;
               case b:
                  GL11.glDisableClientState(32885);
            }
         }
      }

      var1.b();
   }
}
