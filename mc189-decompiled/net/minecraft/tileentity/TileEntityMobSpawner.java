package net.minecraft.tileentity;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntityMobSpawner extends TileEntity implements ITickable {
   private final MobSpawnerBaseLogic a = new MobSpawnerBaseLogic() {
      @Override
      public void a(int var1) {
         TileEntityMobSpawner.this.b.c(TileEntityMobSpawner.this.c, Blocks.ac, var1, 0);
      }

      @Override
      public World a() {
         return TileEntityMobSpawner.this.b;
      }

      @Override
      public BlockPos b() {
         return TileEntityMobSpawner.this.c;
      }

      @Override
      public void a(MobSpawnerBaseLogic.WeightedRandomMinecart var1) {
         super.a(var1);
         if (this.a() != null) {
            this.a().h(TileEntityMobSpawner.this.c);
         }
      }
   };

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.a.a(var1);
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      this.a.b(var1);
   }

   @Override
   public void c() {
      this.a.c();
   }

   @Override
   public Packet y_() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.b(var1);
      var1.o("SpawnPotentials");
      return new S35PacketUpdateTileEntity(this.c, 1, var1);
   }

   @Override
   public boolean c(int var1, int var2) {
      return this.a.b(var1) ? true : super.c(var1, var2);
   }

   @Override
   public boolean F() {
      return true;
   }

   public MobSpawnerBaseLogic b() {
      return this.a;
   }
}
