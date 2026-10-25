package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;

public class C08PacketPlayerBlockPlacement implements Packet<INetHandlerPlayServer> {
   private static final BlockPos a = new BlockPos(-1, -1, -1);
   private BlockPos b;
   private int c;
   private ItemStack d;
   private float e;
   private float f;
   private float g;

   public C08PacketPlayerBlockPlacement() {
   }

   public C08PacketPlayerBlockPlacement(ItemStack var1) {
      this(a, 255, var1, 0.0F, 0.0F, 0.0F);
   }

   public C08PacketPlayerBlockPlacement(BlockPos var1, int var2, ItemStack var3, float var4, float var5, float var6) {
      this.b = var1;
      this.c = var2;
      this.d = var3 != null ? var3.k() : null;
      this.e = var4;
      this.f = var5;
      this.g = var6;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.b = var1.c();
      this.c = var1.readUnsignedByte();
      this.d = var1.i();
      this.e = var1.readUnsignedByte() / 16.0F;
      this.f = var1.readUnsignedByte() / 16.0F;
      this.g = var1.readUnsignedByte() / 16.0F;
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.b);
      var1.writeByte(this.c);
      var1.a(this.d);
      var1.writeByte((int)(this.e * 16.0F));
      var1.writeByte((int)(this.f * 16.0F));
      var1.writeByte((int)(this.g * 16.0F));
   }

   public void a(INetHandlerPlayServer var1) {
      var1.a(this);
   }

   public BlockPos a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }

   public ItemStack c() {
      return this.d;
   }

   public float d() {
      return this.e;
   }

   public float e() {
      return this.f;
   }

   public float f() {
      return this.g;
   }
}
