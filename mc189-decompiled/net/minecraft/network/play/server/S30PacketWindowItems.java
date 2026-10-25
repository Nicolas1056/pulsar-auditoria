package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S30PacketWindowItems implements Packet<INetHandlerPlayClient> {
   private int a;
   private ItemStack[] b;

   public S30PacketWindowItems() {
   }

   public S30PacketWindowItems(int var1, List<ItemStack> var2) {
      this.a = var1;
      this.b = new ItemStack[var2.size()];

      for (int var3 = 0; var3 < this.b.length; var3++) {
         ItemStack var4 = (ItemStack)var2.get(var3);
         this.b[var3] = var4 == null ? null : var4.k();
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.readUnsignedByte();
      short var2 = var1.readShort();
      this.b = new ItemStack[var2];

      for (int var3 = 0; var3 < var2; var3++) {
         this.b[var3] = var1.i();
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeByte(this.a);
      var1.writeShort(this.b.length);

      for (ItemStack var5 : this.b) {
         var1.a(var5);
      }
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public int a() {
      return this.a;
   }

   public ItemStack[] b() {
      return this.b;
   }
}
