package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

public class S07PacketRespawn implements Packet<INetHandlerPlayClient> {
   private int a;
   private EnumDifficulty b;
   private WorldSettings.GameType c;
   private WorldType d;

   public S07PacketRespawn() {
   }

   public S07PacketRespawn(int var1, EnumDifficulty var2, WorldType var3, WorldSettings.GameType var4) {
      this.a = var1;
      this.b = var2;
      this.c = var4;
      this.d = var3;
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.readInt();
      this.b = EnumDifficulty.a(var1.readUnsignedByte());
      this.c = WorldSettings.GameType.a(var1.readUnsignedByte());
      this.d = WorldType.a(var1.c(16));
      if (this.d == null) {
         this.d = WorldType.b;
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeInt(this.a);
      var1.writeByte(this.b.a());
      var1.writeByte(this.c.a());
      var1.a(this.d.a());
   }

   public int a() {
      return this.a;
   }

   public EnumDifficulty b() {
      return this.b;
   }

   public WorldSettings.GameType c() {
      return this.c;
   }

   public WorldType d() {
      return this.d;
   }
}
