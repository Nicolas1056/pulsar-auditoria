package net.minecraft.client.network;

import com.google.common.base.Objects;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings;

public class NetworkPlayerInfo {
   private final GameProfile a;
   private WorldSettings.GameType b;
   private int c;
   private boolean d = false;
   private ResourceLocation e;
   private ResourceLocation f;
   private String g;
   private IChatComponent h;
   private int i = 0;
   private int j = 0;
   private long k = 0L;
   private long l = 0L;
   private long m = 0L;

   public NetworkPlayerInfo(GameProfile var1) {
      this.a = var1;
   }

   public NetworkPlayerInfo(S38PacketPlayerListItem.AddPlayerData var1) {
      this.a = var1.a();
      this.b = var1.c();
      this.c = var1.b();
      this.h = var1.d();
   }

   public GameProfile a() {
      return this.a;
   }

   public WorldSettings.GameType b() {
      return this.b;
   }

   public int c() {
      return this.c;
   }

   protected void a(WorldSettings.GameType var1) {
      this.b = var1;
   }

   protected void a(int var1) {
      this.c = var1;
   }

   public boolean e() {
      return this.e != null;
   }

   public String f() {
      return this.g == null ? DefaultPlayerSkin.b(this.a.getId()) : this.g;
   }

   public ResourceLocation g() {
      if (this.e == null) {
         this.j();
      }

      return (ResourceLocation)Objects.firstNonNull(this.e, DefaultPlayerSkin.a(this.a.getId()));
   }

   public ResourceLocation h() {
      if (this.f == null) {
         this.j();
      }

      return this.f;
   }

   public ScorePlayerTeam i() {
      return Minecraft.A().f.Z().h(this.a().getName());
   }

   protected void j() {
      synchronized (this) {
         if (!this.d) {
            this.d = true;
            Minecraft.A().ab().a(this.a, new SkinManager.SkinAvailableCallback() {
               @Override
               public void a(Type var1, ResourceLocation var2, MinecraftProfileTexture var3) {
                  switch (var1) {
                     case SKIN:
                        NetworkPlayerInfo.this.e = var2;
                        NetworkPlayerInfo.this.g = var3.getMetadata("model");
                        if (NetworkPlayerInfo.this.g == null) {
                           NetworkPlayerInfo.this.g = "default";
                        }
                        break;
                     case CAPE:
                        NetworkPlayerInfo.this.f = var2;
                  }
               }
            }, true);
         }
      }
   }

   public void a(IChatComponent var1) {
      this.h = var1;
   }

   public IChatComponent k() {
      return this.h;
   }

   public int l() {
      return this.i;
   }

   public void b(int var1) {
      this.i = var1;
   }

   public int m() {
      return this.j;
   }

   public void c(int var1) {
      this.j = var1;
   }

   public long n() {
      return this.k;
   }

   public void a(long var1) {
      this.k = var1;
   }

   public long o() {
      return this.l;
   }

   public void b(long var1) {
      this.l = var1;
   }

   public long p() {
      return this.m;
   }

   public void c(long var1) {
      this.m = var1;
   }
}
