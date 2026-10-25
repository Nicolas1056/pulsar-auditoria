package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.Validate;

public class S29PacketSoundEffect implements Packet<INetHandlerPlayClient> {
   private String a;
   private int b;
   private int c = Integer.MAX_VALUE;
   private int d;
   private float e;
   private int f;

   public S29PacketSoundEffect() {
   }

   public S29PacketSoundEffect(String var1, double var2, double var4, double var6, float var8, float var9) {
      Validate.notNull(var1, "name", new Object[0]);
      this.a = var1;
      this.b = (int)(var2 * 8.0);
      this.c = (int)(var4 * 8.0);
      this.d = (int)(var6 * 8.0);
      this.e = var8;
      this.f = (int)(var9 * 63.0F);
      var9 = MathHelper.a(var9, 0.0F, 255.0F);
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.c(256);
      this.b = var1.readInt();
      this.c = var1.readInt();
      this.d = var1.readInt();
      this.e = var1.readFloat();
      this.f = var1.readUnsignedByte();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      var1.writeInt(this.b);
      var1.writeInt(this.c);
      var1.writeInt(this.d);
      var1.writeFloat(this.e);
      var1.writeByte(this.f);
   }

   public String a() {
      return this.a;
   }

   public double b() {
      return this.b / 8.0F;
   }

   public double c() {
      return this.c / 8.0F;
   }

   public double d() {
      return this.d / 8.0F;
   }

   public float e() {
      return this.e;
   }

   public float f() {
      return this.f / 63.0F;
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }
}
