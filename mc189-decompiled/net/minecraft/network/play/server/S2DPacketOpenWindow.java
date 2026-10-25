package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S2DPacketOpenWindow implements Packet<INetHandlerPlayClient> {
   private int a;
   private String b;
   private IChatComponent c;
   private int d;
   private int e;

   public S2DPacketOpenWindow() {
   }

   public S2DPacketOpenWindow(int var1, String var2, IChatComponent var3) {
      this(var1, var2, var3, 0);
   }

   public S2DPacketOpenWindow(int var1, String var2, IChatComponent var3, int var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public S2DPacketOpenWindow(int var1, String var2, IChatComponent var3, int var4, int var5) {
      this(var1, var2, var3, var4);
      this.e = var5;
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.readUnsignedByte();
      this.b = var1.c(32);
      this.c = var1.d();
      this.d = var1.readUnsignedByte();
      if (this.b.equals("EntityHorse")) {
         this.e = var1.readInt();
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeByte(this.a);
      var1.a(this.b);
      var1.a(this.c);
      var1.writeByte(this.d);
      if (this.b.equals("EntityHorse")) {
         var1.writeInt(this.e);
      }
   }

   public int a() {
      return this.a;
   }

   public String b() {
      return this.b;
   }

   public IChatComponent c() {
      return this.c;
   }

   public int d() {
      return this.d;
   }

   public int e() {
      return this.e;
   }

   public boolean f() {
      return this.d > 0;
   }
}
