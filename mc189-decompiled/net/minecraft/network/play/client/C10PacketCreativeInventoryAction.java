package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C10PacketCreativeInventoryAction implements Packet<INetHandlerPlayServer> {
   private int a;
   private ItemStack b;

   public C10PacketCreativeInventoryAction() {
   }

   public C10PacketCreativeInventoryAction(int var1, ItemStack var2) {
      this.a = var1;
      this.b = var2 != null ? var2.k() : null;
   }

   public void a(INetHandlerPlayServer var1) {
      var1.a(this);
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.readShort();
      this.b = var1.i();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeShort(this.a);
      var1.a(this.b);
   }

   public int a() {
      return this.a;
   }

   public ItemStack b() {
      return this.b;
   }
}
