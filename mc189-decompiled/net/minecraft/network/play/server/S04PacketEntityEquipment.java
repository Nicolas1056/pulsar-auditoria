package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S04PacketEntityEquipment implements Packet<INetHandlerPlayClient> {
   private int a;
   private int b;
   private ItemStack c;

   public S04PacketEntityEquipment() {
   }

   public S04PacketEntityEquipment(int var1, int var2, ItemStack var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3 == null ? null : var3.k();
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.b = var1.readShort();
      this.c = var1.i();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.writeShort(this.b);
      var1.a(this.c);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public ItemStack a() {
      return this.c;
   }

   public int b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }
}
