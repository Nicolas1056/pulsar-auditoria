package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S45PacketTitle implements Packet<INetHandlerPlayClient> {
   private S45PacketTitle.Type a;
   private IChatComponent b;
   private int c;
   private int d;
   private int e;

   public S45PacketTitle() {
   }

   public S45PacketTitle(S45PacketTitle.Type var1, IChatComponent var2) {
      this(var1, var2, -1, -1, -1);
   }

   public S45PacketTitle(int var1, int var2, int var3) {
      this(S45PacketTitle.Type.c, null, var1, var2, var3);
   }

   public S45PacketTitle(S45PacketTitle.Type var1, IChatComponent var2, int var3, int var4, int var5) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.a(S45PacketTitle.Type.class);
      if (this.a == S45PacketTitle.Type.a || this.a == S45PacketTitle.Type.b) {
         this.b = var1.d();
      }

      if (this.a == S45PacketTitle.Type.c) {
         this.c = var1.readInt();
         this.d = var1.readInt();
         this.e = var1.readInt();
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      if (this.a == S45PacketTitle.Type.a || this.a == S45PacketTitle.Type.b) {
         var1.a(this.b);
      }

      if (this.a == S45PacketTitle.Type.c) {
         var1.writeInt(this.c);
         var1.writeInt(this.d);
         var1.writeInt(this.e);
      }
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public S45PacketTitle.Type a() {
      return this.a;
   }

   public IChatComponent b() {
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

   public static enum Type {
      a,
      b,
      c,
      d,
      e;

      public static S45PacketTitle.Type a(String var0) {
         for (S45PacketTitle.Type var4 : values()) {
            if (var4.name().equalsIgnoreCase(var0)) {
               return var4;
            }
         }

         return a;
      }

      public static String[] a() {
         String[] var0 = new String[values().length];
         int var1 = 0;

         for (S45PacketTitle.Type var5 : values()) {
            var0[var1++] = var5.name().toLowerCase();
         }

         return var0;
      }
   }
}
