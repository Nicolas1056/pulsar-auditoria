package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;

public class CompiledChunk {
   public static final CompiledChunk a = new CompiledChunk() {
      @Override
      protected void a(EnumWorldBlockLayer var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void c(EnumWorldBlockLayer var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean a(EnumFacing var1, EnumFacing var2) {
         return false;
      }
   };
   private final boolean[] b = new boolean[EnumWorldBlockLayer.values().length];
   private final boolean[] c = new boolean[EnumWorldBlockLayer.values().length];
   private boolean d = true;
   private final List<TileEntity> e = Lists.newArrayList();
   private SetVisibility f = new SetVisibility();
   private WorldRenderer.State g;

   public boolean a() {
      return this.d;
   }

   protected void a(EnumWorldBlockLayer var1) {
      this.d = false;
      this.b[var1.ordinal()] = true;
   }

   public boolean b(EnumWorldBlockLayer var1) {
      return !this.b[var1.ordinal()];
   }

   public void c(EnumWorldBlockLayer var1) {
      this.c[var1.ordinal()] = true;
   }

   public boolean d(EnumWorldBlockLayer var1) {
      return this.c[var1.ordinal()];
   }

   public List<TileEntity> b() {
      return this.e;
   }

   public void a(TileEntity var1) {
      this.e.add(var1);
   }

   public boolean a(EnumFacing var1, EnumFacing var2) {
      return this.f.a(var1, var2);
   }

   public void a(SetVisibility var1) {
      this.f = var1;
   }

   public WorldRenderer.State c() {
      return this.g;
   }

   public void a(WorldRenderer.State var1) {
      this.g = var1;
   }
}
