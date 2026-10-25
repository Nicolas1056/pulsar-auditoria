package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S49PacketUpdateEntityNBT implements Packet<INetHandlerPlayClient> {
   private int a;
   private NBTTagCompound b;

   public S49PacketUpdateEntityNBT() {
   }

   public S49PacketUpdateEntityNBT(int var1, NBTTagCompound var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.b = var1.h();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.a(this.b);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public NBTTagCompound a() {
      return this.b;
   }

   public Entity a(World var1) {
      return var1.a(this.a);
   }
}
