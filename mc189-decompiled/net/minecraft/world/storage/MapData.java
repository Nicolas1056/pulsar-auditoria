package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec4b;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class MapData extends WorldSavedData {
   public int b;
   public int c;
   public byte d;
   public byte e;
   public byte[] f = new byte[16384];
   public List<MapData.MapInfo> g = Lists.newArrayList();
   private Map<EntityPlayer, MapData.MapInfo> i = Maps.newHashMap();
   public Map<String, Vec4b> h = Maps.newLinkedHashMap();

   public MapData(String var1) {
      super(var1);
   }

   public void a(double var1, double var3, int var5) {
      int var6 = 128 * (1 << var5);
      int var7 = MathHelper.c((var1 + 64.0) / var6);
      int var8 = MathHelper.c((var3 + 64.0) / var6);
      this.b = var7 * var6 + var6 / 2 - 64;
      this.c = var8 * var6 + var6 / 2 - 64;
   }

   @Override
   public void a(NBTTagCompound var1) {
      this.d = var1.d("dimension");
      this.b = var1.f("xCenter");
      this.c = var1.f("zCenter");
      this.e = var1.d("scale");
      this.e = (byte)MathHelper.a(this.e, 0, 4);
      short var2 = var1.e("width");
      short var3 = var1.e("height");
      if (var2 == 128 && var3 == 128) {
         this.f = var1.k("colors");
      } else {
         byte[] var4 = var1.k("colors");
         this.f = new byte[16384];
         int var5 = (128 - var2) / 2;
         int var6 = (128 - var3) / 2;

         for (int var7 = 0; var7 < var3; var7++) {
            int var8 = var7 + var6;
            if (var8 >= 0 || var8 < 128) {
               for (int var9 = 0; var9 < var2; var9++) {
                  int var10 = var9 + var5;
                  if (var10 >= 0 || var10 < 128) {
                     this.f[var10 + var8 * 128] = var4[var9 + var7 * var2];
                  }
               }
            }
         }
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      var1.a("dimension", this.d);
      var1.a("xCenter", this.b);
      var1.a("zCenter", this.c);
      var1.a("scale", this.e);
      var1.a("width", (short)128);
      var1.a("height", (short)128);
      var1.a("colors", this.f);
   }

   public void a(EntityPlayer var1, ItemStack var2) {
      if (!this.i.containsKey(var1)) {
         MapData.MapInfo var3 = new MapData.MapInfo(var1);
         this.i.put(var1, var3);
         this.g.add(var3);
      }

      if (!var1.bi.c(var2)) {
         this.h.remove(var1.e_());
      }

      for (int var6 = 0; var6 < this.g.size(); var6++) {
         MapData.MapInfo var4 = this.g.get(var6);
         if (!var4.a.I && (var4.a.bi.c(var2) || var2.y())) {
            if (!var2.y() && var4.a.am == this.d) {
               this.a(0, var4.a.o, var4.a.e_(), var4.a.s, var4.a.u, var4.a.y);
            }
         } else {
            this.i.remove(var4.a);
            this.g.remove(var4);
         }
      }

      if (var2.y()) {
         EntityItemFrame var7 = var2.z();
         BlockPos var9 = var7.n();
         this.a(1, var1.o, "frame-" + var7.F(), var9.n(), var9.p(), var7.b.b() * 90);
      }

      if (var2.n() && var2.o().b("Decorations", 9)) {
         NBTTagList var8 = var2.o().c("Decorations", 10);

         for (int var10 = 0; var10 < var8.c(); var10++) {
            NBTTagCompound var5 = var8.b(var10);
            if (!this.h.containsKey(var5.j("id"))) {
               this.a(var5.d("type"), var1.o, var5.j("id"), var5.i("x"), var5.i("z"), var5.i("rot"));
            }
         }
      }
   }

   private void a(int var1, World var2, String var3, double var4, double var6, double var8) {
      int var10 = 1 << this.e;
      float var11 = (float)(var4 - this.b) / var10;
      float var12 = (float)(var6 - this.c) / var10;
      byte var13 = (byte)(var11 * 2.0F + 0.5);
      byte var14 = (byte)(var12 * 2.0F + 0.5);
      byte var16 = 63;
      byte var15;
      if (var11 >= -var16 && var12 >= -var16 && var11 <= var16 && var12 <= var16) {
         var8 += var8 < 0.0 ? -8.0 : 8.0;
         var15 = (byte)(var8 * 16.0 / 360.0);
         if (this.d < 0) {
            int var17 = (int)(var2.P().g() / 10L);
            var15 = (byte)(var17 * var17 * 34187121 + var17 * 121 >> 15 & 15);
         }
      } else {
         if (!(Math.abs(var11) < 320.0F) || !(Math.abs(var12) < 320.0F)) {
            this.h.remove(var3);
            return;
         }

         var1 = 6;
         var15 = 0;
         if (var11 <= -var16) {
            var13 = (byte)(var16 * 2 + 2.5);
         }

         if (var12 <= -var16) {
            var14 = (byte)(var16 * 2 + 2.5);
         }

         if (var11 >= var16) {
            var13 = (byte)(var16 * 2 + 1);
         }

         if (var12 >= var16) {
            var14 = (byte)(var16 * 2 + 1);
         }
      }

      this.h.put(var3, new Vec4b((byte)var1, var13, var14, var15));
   }

   public Packet a(ItemStack var1, World var2, EntityPlayer var3) {
      MapData.MapInfo var4 = this.i.get(var3);
      return var4 == null ? null : var4.a(var1);
   }

   public void a(int var1, int var2) {
      super.c();

      for (MapData.MapInfo var4 : this.g) {
         var4.a(var1, var2);
      }
   }

   public MapData.MapInfo a(EntityPlayer var1) {
      MapData.MapInfo var2 = this.i.get(var1);
      if (var2 == null) {
         var2 = new MapData.MapInfo(var1);
         this.i.put(var1, var2);
         this.g.add(var2);
      }

      return var2;
   }

   public class MapInfo {
      public final EntityPlayer a;
      private boolean d = true;
      private int e = 0;
      private int f = 0;
      private int g = 127;
      private int h = 127;
      private int i;
      public int b;

      public MapInfo(EntityPlayer var2) {
         this.a = var2;
      }

      public Packet a(ItemStack var1) {
         if (this.d) {
            this.d = false;
            return new S34PacketMaps(
               var1.i(), MapData.this.e, MapData.this.h.values(), MapData.this.f, this.e, this.f, this.g + 1 - this.e, this.h + 1 - this.f
            );
         } else {
            return this.i++ % 5 == 0 ? new S34PacketMaps(var1.i(), MapData.this.e, MapData.this.h.values(), MapData.this.f, 0, 0, 0, 0) : null;
         }
      }

      public void a(int var1, int var2) {
         if (this.d) {
            this.e = Math.min(this.e, var1);
            this.f = Math.min(this.f, var2);
            this.g = Math.max(this.g, var1);
            this.h = Math.max(this.h, var2);
         } else {
            this.d = true;
            this.e = var1;
            this.f = var2;
            this.g = var1;
            this.h = var2;
         }
      }
   }
}
