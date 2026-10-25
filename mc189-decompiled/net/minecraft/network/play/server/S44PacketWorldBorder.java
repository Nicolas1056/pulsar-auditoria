package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.border.WorldBorder;

public class S44PacketWorldBorder implements Packet<INetHandlerPlayClient> {
   private S44PacketWorldBorder.Action a;
   private int b;
   private double c;
   private double d;
   private double e;
   private double f;
   private long g;
   private int h;
   private int i;

   public S44PacketWorldBorder() {
   }

   public S44PacketWorldBorder(WorldBorder var1, S44PacketWorldBorder.Action var2) {
      this.a = var2;
      this.c = var1.f();
      this.d = var1.g();
      this.f = var1.h();
      this.e = var1.j();
      this.g = var1.i();
      this.b = var1.l();
      this.i = var1.q();
      this.h = var1.p();
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.a(S44PacketWorldBorder.Action.class);
      switch (this.a) {
         case a:
            this.e = var1.readDouble();
            break;
         case b:
            this.f = var1.readDouble();
            this.e = var1.readDouble();
            this.g = var1.f();
            break;
         case c:
            this.c = var1.readDouble();
            this.d = var1.readDouble();
            break;
         case f:
            this.i = var1.e();
            break;
         case e:
            this.h = var1.e();
            break;
         case d:
            this.c = var1.readDouble();
            this.d = var1.readDouble();
            this.f = var1.readDouble();
            this.e = var1.readDouble();
            this.g = var1.f();
            this.b = var1.e();
            this.i = var1.e();
            this.h = var1.e();
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      switch (this.a) {
         case a:
            var1.writeDouble(this.e);
            break;
         case b:
            var1.writeDouble(this.f);
            var1.writeDouble(this.e);
            var1.b(this.g);
            break;
         case c:
            var1.writeDouble(this.c);
            var1.writeDouble(this.d);
            break;
         case f:
            var1.b(this.i);
            break;
         case e:
            var1.b(this.h);
            break;
         case d:
            var1.writeDouble(this.c);
            var1.writeDouble(this.d);
            var1.writeDouble(this.f);
            var1.writeDouble(this.e);
            var1.b(this.g);
            var1.b(this.b);
            var1.b(this.i);
            var1.b(this.h);
      }
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public void a(WorldBorder var1) {
      switch (this.a) {
         case a:
            var1.a(this.e);
            break;
         case b:
            var1.a(this.f, this.e, this.g);
            break;
         case c:
            var1.c(this.c, this.d);
            break;
         case f:
            var1.c(this.i);
            break;
         case e:
            var1.b(this.h);
            break;
         case d:
            var1.c(this.c, this.d);
            if (this.g > 0L) {
               var1.a(this.f, this.e, this.g);
            } else {
               var1.a(this.e);
            }

            var1.a(this.b);
            var1.c(this.i);
            var1.b(this.h);
      }
   }

   public static enum Action {
      a,
      b,
      c,
      d,
      e,
      f;
   }
}
