package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S18PacketEntityTeleport implements Packet<INetHandlerPlayClient> {
   private int a;
   private int b;
   private int c;
   private int d;
   private byte e;
   private byte f;
   private boolean g;

   public S18PacketEntityTeleport() {
   }

   public S18PacketEntityTeleport(Entity var1) {
      this.a = var1.F();
      this.b = MathHelper.c(var1.s * 32.0);
      this.c = MathHelper.c(var1.t * 32.0);
      this.d = MathHelper.c(var1.u * 32.0);
      this.e = (byte)(var1.y * 256.0F / 360.0F);
      this.f = (byte)(var1.z * 256.0F / 360.0F);
      this.g = var1.C;
   }

   public S18PacketEntityTeleport(int var1, int var2, int var3, int var4, byte var5, byte var6, boolean var7) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
      this.f = var6;
      this.g = var7;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.b = var1.readInt();
      this.c = var1.readInt();
      this.d = var1.readInt();
      this.e = var1.readByte();
      this.f = var1.readByte();
      this.g = var1.readBoolean();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.writeInt(this.b);
      var1.writeInt(this.c);
      var1.writeInt(this.d);
      var1.writeByte(this.e);
      var1.writeByte(this.f);
      var1.writeBoolean(this.g);
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

   public byte e() {
      return this.e;
   }

   public byte f() {
      return this.f;
   }

   public boolean g() {
      return this.g;
   }
}
