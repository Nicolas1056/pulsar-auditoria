package net.minecraft.tileentity;

import io.netty.buffer.ByteBuf;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityCommandBlock extends TileEntity {
   private final CommandBlockLogic a = new CommandBlockLogic() {
      @Override
      public BlockPos c() {
         return TileEntityCommandBlock.this.c;
      }

      @Override
      public Vec3 d() {
         return new Vec3(TileEntityCommandBlock.this.c.n() + 0.5, TileEntityCommandBlock.this.c.o() + 0.5, TileEntityCommandBlock.this.c.p() + 0.5);
      }

      @Override
      public World e() {
         return TileEntityCommandBlock.this.z();
      }

      @Override
      public void a(String var1) {
         super.a(var1);
         TileEntityCommandBlock.this.p_();
      }

      @Override
      public void h() {
         TileEntityCommandBlock.this.z().h(TileEntityCommandBlock.this.c);
      }

      @Override
      public int i() {
         return 0;
      }

      @Override
      public void a(ByteBuf var1) {
         var1.writeInt(TileEntityCommandBlock.this.c.n());
         var1.writeInt(TileEntityCommandBlock.this.c.o());
         var1.writeInt(TileEntityCommandBlock.this.c.p());
      }

      @Override
      public Entity f() {
         return null;
      }
   };

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      this.a.a(var1);
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.a.b(var1);
   }

   @Override
   public Packet y_() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.b(var1);
      return new S35PacketUpdateTileEntity(this.c, 2, var1);
   }

   @Override
   public boolean F() {
      return true;
   }

   public CommandBlockLogic b() {
      return this.a;
   }

   public CommandResultStats c() {
      return this.a.n();
   }
}
