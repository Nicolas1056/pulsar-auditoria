package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.chunk.Chunk;

public class S26PacketMapChunkBulk implements Packet<INetHandlerPlayClient> {
   private int[] a;
   private int[] b;
   private S21PacketChunkData.Extracted[] c;
   private boolean d;

   public S26PacketMapChunkBulk() {
   }

   public S26PacketMapChunkBulk(List<Chunk> var1) {
      int var2 = var1.size();
      this.a = new int[var2];
      this.b = new int[var2];
      this.c = new S21PacketChunkData.Extracted[var2];
      this.d = !((Chunk)var1.get(0)).p().t.o();

      for (int var3 = 0; var3 < var2; var3++) {
         Chunk var4 = (Chunk)var1.get(var3);
         S21PacketChunkData.Extracted var5 = S21PacketChunkData.a(var4, true, this.d, 65535);
         this.a[var3] = var4.a;
         this.b[var3] = var4.b;
         this.c[var3] = var5;
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.d = var1.readBoolean();
      int var2 = var1.e();
      this.a = new int[var2];
      this.b = new int[var2];
      this.c = new S21PacketChunkData.Extracted[var2];

      for (int var3 = 0; var3 < var2; var3++) {
         this.a[var3] = var1.readInt();
         this.b[var3] = var1.readInt();
         this.c[var3] = new S21PacketChunkData.Extracted();
         this.c[var3].b = var1.readShort() & '\uffff';
         this.c[var3].a = new byte[S21PacketChunkData.a(Integer.bitCount(this.c[var3].b), this.d, true)];
      }

      for (int var4 = 0; var4 < var2; var4++) {
         var1.readBytes(this.c[var4].a);
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeBoolean(this.d);
      var1.b(this.c.length);

      for (int var2 = 0; var2 < this.a.length; var2++) {
         var1.writeInt(this.a[var2]);
         var1.writeInt(this.b[var2]);
         var1.writeShort((short)(this.c[var2].b & 65535));
      }

      for (int var3 = 0; var3 < this.a.length; var3++) {
         var1.writeBytes(this.c[var3].a);
      }
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public int a(int var1) {
      return this.a[var1];
   }

   public int b(int var1) {
      return this.b[var1];
   }

   public int a() {
      return this.a.length;
   }

   public byte[] c(int var1) {
      return this.c[var1].a;
   }

   public int d(int var1) {
      return this.c[var1].b;
   }
}
