package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class C02PacketUseEntity implements Packet<INetHandlerPlayServer> {
   private int a;
   private C02PacketUseEntity.Action b;
   private Vec3 c;

   public C02PacketUseEntity() {
   }

   public C02PacketUseEntity(Entity var1, C02PacketUseEntity.Action var2) {
      this.a = var1.F();
      this.b = var2;
   }

   public C02PacketUseEntity(Entity var1, Vec3 var2) {
      this(var1, C02PacketUseEntity.Action.c);
      this.c = var2;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.b = var1.a(C02PacketUseEntity.Action.class);
      if (this.b == C02PacketUseEntity.Action.c) {
         this.c = new Vec3(var1.readFloat(), var1.readFloat(), var1.readFloat());
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.a(this.b);
      if (this.b == C02PacketUseEntity.Action.c) {
         var1.writeFloat((float)this.c.a);
         var1.writeFloat((float)this.c.b);
         var1.writeFloat((float)this.c.c);
      }
   }

   public void a(INetHandlerPlayServer var1) {
      var1.a(this);
   }

   public Entity a(World var1) {
      return var1.a(this.a);
   }

   public C02PacketUseEntity.Action a() {
      return this.b;
   }

   public Vec3 b() {
      return this.c;
   }

   public static enum Action {
      a,
      b,
      c;
   }
}
