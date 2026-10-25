package net.minecraft.entity.item;

import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.world.World;

public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper {
   private boolean a = true;
   private int b = -1;
   private BlockPos c = BlockPos.a;

   public EntityMinecartHopper(World var1) {
      super(var1);
   }

   public EntityMinecartHopper(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   @Override
   public EntityMinecart.EnumMinecartType s() {
      return EntityMinecart.EnumMinecartType.f;
   }

   @Override
   public IBlockState u() {
      return Blocks.cp.Q();
   }

   @Override
   public int w() {
      return 1;
   }

   @Override
   public int o_() {
      return 5;
   }

   @Override
   public boolean e(EntityPlayer var1) {
      if (!this.o.D) {
         var1.a((IInventory)this);
      }

      return true;
   }

   @Override
   public void a(int var1, int var2, int var3, boolean var4) {
      boolean var5 = !var4;
      if (var5 != this.y()) {
         this.i(var5);
      }
   }

   public boolean y() {
      return this.a;
   }

   public void i(boolean var1) {
      this.a = var1;
   }

   @Override
   public World z() {
      return this.o;
   }

   @Override
   public double A() {
      return this.s;
   }

   @Override
   public double B() {
      return this.t + 0.5;
   }

   @Override
   public double C() {
      return this.u;
   }

   @Override
   public void t_() {
      super.t_();
      if (!this.o.D && this.ai() && this.y()) {
         BlockPos var1 = new BlockPos(this);
         if (var1.equals(this.c)) {
            this.b--;
         } else {
            this.m(0);
         }

         if (!this.E()) {
            this.m(0);
            if (this.D()) {
               this.m(4);
               this.p_();
            }
         }
      }
   }

   public boolean D() {
      if (TileEntityHopper.a(this)) {
         return true;
      } else {
         List var1 = this.o.a(EntityItem.class, this.aR().b(0.25, 0.0, 0.25), EntitySelectors.a);
         if (var1.size() > 0) {
            TileEntityHopper.a(this, (EntityItem)var1.get(0));
         }

         return false;
      }
   }

   @Override
   public void a(DamageSource var1) {
      super.a(var1);
      if (this.o.Q().b("doEntityDrops")) {
         this.a(Item.a(Blocks.cp), 1, 0.0F);
      }
   }

   @Override
   protected void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("TransferCooldown", this.b);
   }

   @Override
   protected void a(NBTTagCompound var1) {
      super.a(var1);
      this.b = var1.f("TransferCooldown");
   }

   public void m(int var1) {
      this.b = var1;
   }

   public boolean E() {
      return this.b > 0;
   }

   @Override
   public String k() {
      return "minecraft:hopper";
   }

   @Override
   public Container a(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerHopper(var1, this, var2);
   }
}
