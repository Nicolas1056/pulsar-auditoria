package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.CombatTracker;

public class S42PacketCombatEvent implements Packet<INetHandlerPlayClient> {
   public S42PacketCombatEvent.Event a;
   public int b;
   public int c;
   public int d;
   public String e;

   public S42PacketCombatEvent() {
   }

   public S42PacketCombatEvent(CombatTracker var1, S42PacketCombatEvent.Event var2) {
      this.a = var2;
      EntityLivingBase var3 = var1.c();
      switch (var2) {
         case b:
            this.d = var1.f();
            this.c = var3 == null ? -1 : var3.F();
            break;
         case c:
            this.b = var1.h().F();
            this.c = var3 == null ? -1 : var3.F();
            this.e = var1.b().c();
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.a(S42PacketCombatEvent.Event.class);
      if (this.a == S42PacketCombatEvent.Event.b) {
         this.d = var1.e();
         this.c = var1.readInt();
      } else if (this.a == S42PacketCombatEvent.Event.c) {
         this.b = var1.e();
         this.c = var1.readInt();
         this.e = var1.c(32767);
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      if (this.a == S42PacketCombatEvent.Event.b) {
         var1.b(this.d);
         var1.writeInt(this.c);
      } else if (this.a == S42PacketCombatEvent.Event.c) {
         var1.b(this.b);
         var1.writeInt(this.c);
         var1.a(this.e);
      }
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public static enum Event {
      a,
      b,
      c;
   }
}
