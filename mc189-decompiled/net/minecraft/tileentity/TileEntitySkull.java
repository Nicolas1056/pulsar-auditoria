package net.minecraft.tileentity;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;

public class TileEntitySkull extends TileEntity {
   private int a;
   private int f;
   private GameProfile g = null;

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("SkullType", (byte)(this.a & 0xFF));
      var1.a("Rot", (byte)(this.f & 0xFF));
      if (this.g != null) {
         NBTTagCompound var2 = new NBTTagCompound();
         NBTUtil.a(var2, this.g);
         var1.a("Owner", var2);
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.a = var1.d("SkullType");
      this.f = var1.d("Rot");
      if (this.a == 3) {
         if (var1.b("Owner", 10)) {
            this.g = NBTUtil.a(var1.m("Owner"));
         } else if (var1.b("ExtraType", 8)) {
            String var2 = var1.j("ExtraType");
            if (!StringUtils.b(var2)) {
               this.g = new GameProfile(null, var2);
               this.e();
            }
         }
      }
   }

   public GameProfile b() {
      return this.g;
   }

   @Override
   public Packet y_() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.b(var1);
      return new S35PacketUpdateTileEntity(this.c, 4, var1);
   }

   public void a(int var1) {
      this.a = var1;
      this.g = null;
   }

   public void a(GameProfile var1) {
      this.a = 3;
      this.g = var1;
      this.e();
   }

   private void e() {
      this.g = b(this.g);
      this.p_();
   }

   public static GameProfile b(GameProfile var0) {
      if (var0 != null && !StringUtils.b(var0.getName())) {
         if (var0.isComplete() && var0.getProperties().containsKey("textures")) {
            return var0;
         } else if (MinecraftServer.N() == null) {
            return var0;
         } else {
            GameProfile var1 = MinecraftServer.N().aF().a(var0.getName());
            if (var1 == null) {
               return var0;
            } else {
               Property var2 = (Property)Iterables.getFirst(var1.getProperties().get("textures"), null);
               if (var2 == null) {
                  var1 = MinecraftServer.N().aD().fillProfileProperties(var1, true);
               }

               return var1;
            }
         }
      } else {
         return var0;
      }
   }

   public int c() {
      return this.a;
   }

   public int d() {
      return this.f;
   }

   public void b(int var1) {
      this.f = var1;
   }
}
