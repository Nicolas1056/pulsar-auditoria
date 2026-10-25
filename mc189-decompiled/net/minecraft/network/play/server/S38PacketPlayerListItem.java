package net.minecraft.network.play.server;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;

public class S38PacketPlayerListItem implements Packet<INetHandlerPlayClient> {
   private S38PacketPlayerListItem.Action a;
   private final List<S38PacketPlayerListItem.AddPlayerData> b = Lists.newArrayList();

   public S38PacketPlayerListItem() {
   }

   public S38PacketPlayerListItem(S38PacketPlayerListItem.Action var1, EntityPlayerMP... var2) {
      this.a = var1;

      for (EntityPlayerMP var6 : var2) {
         this.b.add(new S38PacketPlayerListItem.AddPlayerData(var6.cd(), var6.h, var6.c.b(), var6.E()));
      }
   }

   public S38PacketPlayerListItem(S38PacketPlayerListItem.Action var1, Iterable<EntityPlayerMP> var2) {
      this.a = var1;

      for (EntityPlayerMP var4 : var2) {
         this.b.add(new S38PacketPlayerListItem.AddPlayerData(var4.cd(), var4.h, var4.c.b(), var4.E()));
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.a(S38PacketPlayerListItem.Action.class);
      int var2 = var1.e();

      for (int var3 = 0; var3 < var2; var3++) {
         GameProfile var4 = null;
         int var5 = 0;
         WorldSettings.GameType var6 = null;
         IChatComponent var7 = null;
         switch (this.a) {
            case a:
               var4 = new GameProfile(var1.g(), var1.c(16));
               int var8 = var1.e();
               int var9 = 0;

               for (; var9 < var8; var9++) {
                  String var10 = var1.c(32767);
                  String var11 = var1.c(32767);
                  if (var1.readBoolean()) {
                     var4.getProperties().put(var10, new Property(var10, var11, var1.c(32767)));
                  } else {
                     var4.getProperties().put(var10, new Property(var10, var11));
                  }
               }

               var6 = WorldSettings.GameType.a(var1.e());
               var5 = var1.e();
               if (var1.readBoolean()) {
                  var7 = var1.d();
               }
               break;
            case b:
               var4 = new GameProfile(var1.g(), null);
               var6 = WorldSettings.GameType.a(var1.e());
               break;
            case c:
               var4 = new GameProfile(var1.g(), null);
               var5 = var1.e();
               break;
            case d:
               var4 = new GameProfile(var1.g(), null);
               if (var1.readBoolean()) {
                  var7 = var1.d();
               }
               break;
            case e:
               var4 = new GameProfile(var1.g(), null);
         }

         this.b.add(new S38PacketPlayerListItem.AddPlayerData(var4, var5, var6, var7));
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      var1.b(this.b.size());

      for (S38PacketPlayerListItem.AddPlayerData var3 : this.b) {
         switch (this.a) {
            case a:
               var1.a(var3.a().getId());
               var1.a(var3.a().getName());
               var1.b(var3.a().getProperties().size());

               for (Property var5 : var3.a().getProperties().values()) {
                  var1.a(var5.getName());
                  var1.a(var5.getValue());
                  if (var5.hasSignature()) {
                     var1.writeBoolean(true);
                     var1.a(var5.getSignature());
                  } else {
                     var1.writeBoolean(false);
                  }
               }

               var1.b(var3.c().a());
               var1.b(var3.b());
               if (var3.d() == null) {
                  var1.writeBoolean(false);
               } else {
                  var1.writeBoolean(true);
                  var1.a(var3.d());
               }
               break;
            case b:
               var1.a(var3.a().getId());
               var1.b(var3.c().a());
               break;
            case c:
               var1.a(var3.a().getId());
               var1.b(var3.b());
               break;
            case d:
               var1.a(var3.a().getId());
               if (var3.d() == null) {
                  var1.writeBoolean(false);
               } else {
                  var1.writeBoolean(true);
                  var1.a(var3.d());
               }
               break;
            case e:
               var1.a(var3.a().getId());
         }
      }
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public List<S38PacketPlayerListItem.AddPlayerData> a() {
      return this.b;
   }

   public S38PacketPlayerListItem.Action b() {
      return this.a;
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this).add("action", this.a).add("entries", this.b).toString();
   }

   public static enum Action {
      a,
      b,
      c,
      d,
      e;
   }

   public class AddPlayerData {
      private final int b;
      private final WorldSettings.GameType c;
      private final GameProfile d;
      private final IChatComponent e;

      public AddPlayerData(GameProfile var2, int var3, WorldSettings.GameType var4, IChatComponent var5) {
         this.d = var2;
         this.b = var3;
         this.c = var4;
         this.e = var5;
      }

      public GameProfile a() {
         return this.d;
      }

      public int b() {
         return this.b;
      }

      public WorldSettings.GameType c() {
         return this.c;
      }

      public IChatComponent d() {
         return this.e;
      }

      @Override
      public String toString() {
         return Objects.toStringHelper(this)
            .add("latency", this.b)
            .add("gameMode", this.c)
            .add("profile", this.d)
            .add("displayName", this.e == null ? null : IChatComponent.Serializer.a(this.e))
            .toString();
      }
   }
}
