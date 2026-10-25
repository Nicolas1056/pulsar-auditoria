package net.minecraft.tileentity;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityFlowerPot extends TileEntity {
   private Item a;
   private int f;

   public TileEntityFlowerPot() {
   }

   public TileEntityFlowerPot(Item var1, int var2) {
      this.a = var1;
      this.f = var2;
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      ResourceLocation var2 = Item.e.c(this.a);
      var1.a("Item", var2 == null ? "" : var2.toString());
      var1.a("Data", this.f);
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      if (var1.b("Item", 8)) {
         this.a = Item.d(var1.j("Item"));
      } else {
         this.a = Item.b(var1.f("Item"));
      }

      this.f = var1.f("Data");
   }

   @Override
   public Packet y_() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.b(var1);
      var1.o("Item");
      var1.a("Item", Item.b(this.a));
      return new S35PacketUpdateTileEntity(this.c, 5, var1);
   }

   public void a(Item var1, int var2) {
      this.a = var1;
      this.f = var2;
   }

   public Item b() {
      return this.a;
   }

   public int c() {
      return this.f;
   }
}
