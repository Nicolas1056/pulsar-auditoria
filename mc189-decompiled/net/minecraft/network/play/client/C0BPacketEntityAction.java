package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0BPacketEntityAction implements Packet<INetHandlerPlayServer> {
   private int a;
   private C0BPacketEntityAction.Action b;
   private int c;

   public C0BPacketEntityAction() {
   }

   public C0BPacketEntityAction(Entity var1, C0BPacketEntityAction.Action var2) {
      this(var1, var2, 0);
   }

   public C0BPacketEntityAction(Entity var1, C0BPacketEntityAction.Action var2, int var3) {
      this.a = var1.F();
      this.b = var2;
      this.c = var3;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.b = var1.a(C0BPacketEntityAction.Action.class);
      this.c = var1.e();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.a(this.b);
      var1.b(this.c);
   }

   public void a(INetHandlerPlayServer var1) {
      var1.a(this);
   }

   public C0BPacketEntityAction.Action b() {
      return this.b;
   }

   public int c() {
      return this.c;
   }

   public static enum Action {
      a,
      b,
      c,
      d,
      e,
      f,
      g;
   }
}
