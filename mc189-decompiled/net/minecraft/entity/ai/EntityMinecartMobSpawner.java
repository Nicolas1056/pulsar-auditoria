package net.minecraft.entity.ai;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityMinecartMobSpawner extends EntityMinecart {
   private final MobSpawnerBaseLogic a = new MobSpawnerBaseLogic() {
      @Override
      public void a(int var1) {
         EntityMinecartMobSpawner.this.o.a(EntityMinecartMobSpawner.this, (byte)var1);
      }

      @Override
      public World a() {
         return EntityMinecartMobSpawner.this.o;
      }

      @Override
      public BlockPos b() {
         return new BlockPos(EntityMinecartMobSpawner.this);
      }
   };

   public EntityMinecartMobSpawner(World var1) {
      super(var1);
   }

   public EntityMinecartMobSpawner(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   @Override
   public EntityMinecart.EnumMinecartType s() {
      return EntityMinecart.EnumMinecartType.e;
   }

   @Override
   public IBlockState u() {
      return Blocks.ac.Q();
   }

   @Override
   protected void a(NBTTagCompound var1) {
      super.a(var1);
      this.a.a(var1);
   }

   @Override
   protected void b(NBTTagCompound var1) {
      super.b(var1);
      this.a.b(var1);
   }

   @Override
   public void a(byte var1) {
      this.a.b(var1);
   }

   @Override
   public void t_() {
      super.t_();
      this.a.c();
   }

   public MobSpawnerBaseLogic j() {
      return this.a;
   }
}
