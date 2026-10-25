package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S2CPacketSpawnGlobalEntity implements Packet<INetHandlerPlayClient> {
   private int a;
   private int b;
   private int c;
   private int d;
   private int e;

   public S2CPacketSpawnGlobalEntity() {
   }

   public S2CPacketSpawnGlobalEntity(Entity var1) {
      this.a = var1.F();
      this.b = MathHelper.c(var1.s * 32.0);
      this.c = MathHelper.c(var1.t * 32.0);
      this.d = MathHelper.c(var1.u * 32.0);
      if (var1 instanceof EntityLightningBolt) {
         this.e = 1;
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.e = var1.readByte();
      this.b = var1.readInt();
      this.c = var1.readInt();
      this.d = var1.readInt();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.writeByte(this.e);
      var1.writeInt(this.b);
      var1.writeInt(this.c);
      var1.writeInt(this.d);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public int a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }

   public int c() {
      return this.c;
   }

   public int d() {
      return this.d;
   }

   public int e() {
      return this.e;
   }
}
