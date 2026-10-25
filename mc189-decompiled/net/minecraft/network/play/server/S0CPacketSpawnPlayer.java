package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S0CPacketSpawnPlayer implements Packet<INetHandlerPlayClient> {
   private int a;
   private UUID b;
   private int c;
   private int d;
   private int e;
   private byte f;
   private byte g;
   private int h;
   private DataWatcher i;
   private List<DataWatcher.WatchableObject> j;

   public S0CPacketSpawnPlayer() {
   }

   public S0CPacketSpawnPlayer(EntityPlayer var1) {
      this.a = var1.F();
      this.b = var1.cd().getId();
      this.c = MathHelper.c(var1.s * 32.0);
      this.d = MathHelper.c(var1.t * 32.0);
      this.e = MathHelper.c(var1.u * 32.0);
      this.f = (byte)(var1.y * 256.0F / 360.0F);
      this.g = (byte)(var1.z * 256.0F / 360.0F);
      ItemStack var2 = var1.bi.h();
      this.h = var2 == null ? 0 : Item.b(var2.b());
      this.i = var1.H();
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.b = var1.g();
      this.c = var1.readInt();
      this.d = var1.readInt();
      this.e = var1.readInt();
      this.f = var1.readByte();
      this.g = var1.readByte();
      this.h = var1.readShort();
      this.j = DataWatcher.b(var1);
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.a(this.b);
      var1.writeInt(this.c);
      var1.writeInt(this.d);
      var1.writeInt(this.e);
      var1.writeByte(this.f);
      var1.writeByte(this.g);
      var1.writeShort(this.h);
      this.i.a(var1);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public List<DataWatcher.WatchableObject> a() {
      if (this.j == null) {
         this.j = this.i.c();
      }

      return this.j;
   }

   public int b() {
      return this.a;
   }

   public UUID c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public int e() {
      return this.d;
   }

   public int f() {
      return this.e;
   }

   public byte g() {
      return this.f;
   }

   public byte h() {
      return this.g;
   }

   public int i() {
      return this.h;
   }
}
