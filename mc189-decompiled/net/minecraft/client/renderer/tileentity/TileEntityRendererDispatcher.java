package net.minecraft.client.renderer.tileentity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

public class TileEntityRendererDispatcher {
   private Map<Class<? extends TileEntity>, TileEntitySpecialRenderer<? extends TileEntity>> m = Maps.newHashMap();
   public static TileEntityRendererDispatcher a = new TileEntityRendererDispatcher();
   private FontRenderer n;
   public static double b;
   public static double c;
   public static double d;
   public TextureManager e;
   public World f;
   public Entity g;
   public float h;
   public float i;
   public double j;
   public double k;
   public double l;

   private TileEntityRendererDispatcher() {
      this.m.put(TileEntitySign.class, new TileEntitySignRenderer());
      this.m.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
      this.m.put(TileEntityPiston.class, new TileEntityPistonRenderer());
      this.m.put(TileEntityChest.class, new TileEntityChestRenderer());
      this.m.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
      this.m.put(TileEntityEnchantmentTable.class, new TileEntityEnchantmentTableRenderer());
      this.m.put(TileEntityEndPortal.class, new TileEntityEndPortalRenderer());
      this.m.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
      this.m.put(TileEntitySkull.class, new TileEntitySkullRenderer());
      this.m.put(TileEntityBanner.class, new TileEntityBannerRenderer());

      for (TileEntitySpecialRenderer var2 : this.m.values()) {
         var2.a(this);
      }
   }

   public <T extends TileEntity> TileEntitySpecialRenderer<T> a(Class<? extends TileEntity> var1) {
      TileEntitySpecialRenderer var2 = this.m.get(var1);
      if (var2 == null && var1 != TileEntity.class) {
         var2 = this.a(var1.getSuperclass());
         this.m.put(var1, var2);
      }

      return var2;
   }

   public <T extends TileEntity> TileEntitySpecialRenderer<T> b(TileEntity var1) {
      return var1 == null ? null : this.a((Class<? extends TileEntity>)var1.getClass());
   }

   public void a(World var1, TextureManager var2, FontRenderer var3, Entity var4, float var5) {
      if (this.f != var1) {
         this.a(var1);
      }

      this.e = var2;
      this.g = var4;
      this.n = var3;
      this.h = var4.A + (var4.y - var4.A) * var5;
      this.i = var4.B + (var4.z - var4.B) * var5;
      this.j = var4.P + (var4.s - var4.P) * var5;
      this.k = var4.Q + (var4.t - var4.Q) * var5;
      this.l = var4.R + (var4.u - var4.R) * var5;
   }

   public void a(TileEntity var1, float var2, int var3) {
      if (var1.a(this.j, this.k, this.l) < var1.s()) {
         int var4 = this.f.b(var1.v(), 0);
         int var5 = var4 % 65536;
         int var6 = var4 / 65536;
         OpenGlHelper.a(OpenGlHelper.r, var5 / 1.0F, var6 / 1.0F);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         BlockPos var7 = var1.v();
         this.a(var1, var7.n() - b, var7.o() - c, var7.p() - d, var2, var3);
      }
   }

   public void a(TileEntity var1, double var2, double var4, double var6, float var8) {
      this.a(var1, var2, var4, var6, var8, -1);
   }

   public void a(TileEntity var1, double var2, double var4, double var6, float var8, int var9) {
      TileEntitySpecialRenderer var10 = this.b(var1);
      if (var10 != null) {
         try {
            var10.a(var1, var2, var4, var6, var8, var9);
         } catch (Throwable var14) {
            CrashReport var12 = CrashReport.a(var14, "Rendering Block Entity");
            CrashReportCategory var13 = var12.a("Block Entity Details");
            var1.a(var13);
            throw new ReportedException(var12);
         }
      }
   }

   public void a(World var1) {
      this.f = var1;
   }

   public FontRenderer a() {
      return this.n;
   }
}
