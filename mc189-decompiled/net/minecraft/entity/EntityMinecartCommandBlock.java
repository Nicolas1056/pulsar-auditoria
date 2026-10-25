package net.minecraft.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMinecartCommandBlock extends EntityMinecart {
   private final CommandBlockLogic a = new CommandBlockLogic() {
      @Override
      public void h() {
         EntityMinecartCommandBlock.this.H().b(23, this.l());
         EntityMinecartCommandBlock.this.H().b(24, IChatComponent.Serializer.a(this.k()));
      }

      @Override
      public int i() {
         return 1;
      }

      @Override
      public void a(ByteBuf var1) {
         var1.writeInt(EntityMinecartCommandBlock.this.F());
      }

      @Override
      public BlockPos c() {
         return new BlockPos(EntityMinecartCommandBlock.this.s, EntityMinecartCommandBlock.this.t + 0.5, EntityMinecartCommandBlock.this.u);
      }

      @Override
      public Vec3 d() {
         return new Vec3(EntityMinecartCommandBlock.this.s, EntityMinecartCommandBlock.this.t, EntityMinecartCommandBlock.this.u);
      }

      @Override
      public World e() {
         return EntityMinecartCommandBlock.this.o;
      }

      @Override
      public Entity f() {
         return EntityMinecartCommandBlock.this;
      }
   };
   private int b = 0;

   public EntityMinecartCommandBlock(World var1) {
      super(var1);
   }

   public EntityMinecartCommandBlock(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   @Override
   protected void h() {
      super.h();
      this.H().a(23, "");
      this.H().a(24, "");
   }

   @Override
   protected void a(NBTTagCompound var1) {
      super.a(var1);
      this.a.b(var1);
      this.H().b(23, this.j().l());
      this.H().b(24, IChatComponent.Serializer.a(this.j().k()));
   }

   @Override
   protected void b(NBTTagCompound var1) {
      super.b(var1);
      this.a.a(var1);
   }

   @Override
   public EntityMinecart.EnumMinecartType s() {
      return EntityMinecart.EnumMinecartType.g;
   }

   @Override
   public IBlockState u() {
      return Blocks.bX.Q();
   }

   public CommandBlockLogic j() {
      return this.a;
   }

   @Override
   public void a(int var1, int var2, int var3, boolean var4) {
      if (var4 && this.W - this.b >= 4) {
         this.j().a(this.o);
         this.b = this.W;
      }
   }

   @Override
   public boolean e(EntityPlayer var1) {
      this.a.a(var1);
      return false;
   }

   @Override
   public void i(int var1) {
      super.i(var1);
      if (var1 == 24) {
         try {
            this.a.b(IChatComponent.Serializer.a(this.H().e(24)));
         } catch (Throwable var3) {
         }
      } else if (var1 == 23) {
         this.a.a(this.H().e(23));
      }
   }
}
