package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C15PacketClientSettings implements Packet<INetHandlerPlayServer> {
   private String a;
   private int b;
   private EntityPlayer.EnumChatVisibility c;
   private boolean d;
   private int e;

   public C15PacketClientSettings() {
   }

   public C15PacketClientSettings(String var1, int var2, EntityPlayer.EnumChatVisibility var3, boolean var4, int var5) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.c(7);
      this.b = var1.readByte();
      this.c = EntityPlayer.EnumChatVisibility.a(var1.readByte());
      this.d = var1.readBoolean();
      this.e = var1.readUnsignedByte();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      var1.writeByte(this.b);
      var1.writeByte(this.c.a());
      var1.writeBoolean(this.d);
      var1.writeByte(this.e);
   }

   public void a(INetHandlerPlayServer var1) {
      var1.a(this);
   }

   public String a() {
      return this.a;
   }

   public EntityPlayer.EnumChatVisibility c() {
      return this.c;
   }

   public boolean d() {
      return this.d;
   }

   public int e() {
      return this.e;
   }
}
