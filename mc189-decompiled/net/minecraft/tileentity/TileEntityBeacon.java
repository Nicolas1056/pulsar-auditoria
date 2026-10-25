package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;

public class TileEntityBeacon extends TileEntityLockable implements ITickable, IInventory {
   public static final Potion[][] a = new Potion[][]{{Potion.c, Potion.e}, {Potion.m, Potion.j}, {Potion.g}, {Potion.l}};
   private final List<TileEntityBeacon.BeamSegment> f = Lists.newArrayList();
   private long g;
   private float h;
   private boolean i;
   private int j = -1;
   private int k;
   private int l;
   private ItemStack m;
   private String n;

   @Override
   public void c() {
      if (this.b.K() % 80L == 0L) {
         this.m();
      }
   }

   public void m() {
      this.B();
      this.A();
   }

   private void A() {
      if (this.i && this.j > 0 && !this.b.D && this.k > 0) {
         double var1 = this.j * 10 + 10;
         byte var3 = 0;
         if (this.j >= 4 && this.k == this.l) {
            var3 = 1;
         }

         int var4 = this.c.n();
         int var5 = this.c.o();
         int var6 = this.c.p();
         AxisAlignedBB var7 = new AxisAlignedBB(var4, var5, var6, var4 + 1, var5 + 1, var6 + 1).b(var1, var1, var1).a(0.0, this.b.U(), 0.0);
         List var8 = this.b.a(EntityPlayer.class, var7);

         for (EntityPlayer var10 : var8) {
            var10.c(new PotionEffect(this.k, 180, var3, true, true));
         }

         if (this.j >= 4 && this.k != this.l && this.l > 0) {
            for (EntityPlayer var12 : var8) {
               var12.c(new PotionEffect(this.l, 180, 0, true, true));
            }
         }
      }
   }

   private void B() {
      int var1 = this.j;
      int var2 = this.c.n();
      int var3 = this.c.o();
      int var4 = this.c.p();
      this.j = 0;
      this.f.clear();
      this.i = true;
      TileEntityBeacon.BeamSegment var5 = new TileEntityBeacon.BeamSegment(EntitySheep.a(EnumDyeColor.a));
      this.f.add(var5);
      boolean var6 = true;
      BlockPos.MutableBlockPos var7 = new BlockPos.MutableBlockPos();

      for (int var8 = var3 + 1; var8 < 256; var8++) {
         IBlockState var9 = this.b.p(var7.c(var2, var8, var4));
         float[] var10;
         if (var9.c() == Blocks.cG) {
            var10 = EntitySheep.a(var9.b(BlockStainedGlass.a));
         } else {
            if (var9.c() != Blocks.cH) {
               if (var9.c().p() >= 15 && var9.c() != Blocks.h) {
                  this.i = false;
                  this.f.clear();
                  break;
               }

               var5.a();
               continue;
            }

            var10 = EntitySheep.a(var9.b(BlockStainedGlassPane.a));
         }

         if (!var6) {
            var10 = new float[]{(var5.b()[0] + var10[0]) / 2.0F, (var5.b()[1] + var10[1]) / 2.0F, (var5.b()[2] + var10[2]) / 2.0F};
         }

         if (Arrays.equals(var10, var5.b())) {
            var5.a();
         } else {
            var5 = new TileEntityBeacon.BeamSegment(var10);
            this.f.add(var5);
         }

         var6 = false;
      }

      if (this.i) {
         for (int var14 = 1; var14 <= 4; this.j = var14++) {
            int var16 = var3 - var14;
            if (var16 < 0) {
               break;
            }

            boolean var18 = true;

            for (int var11 = var2 - var14; var11 <= var2 + var14 && var18; var11++) {
               for (int var12 = var4 - var14; var12 <= var4 + var14; var12++) {
                  Block var13 = this.b.p(new BlockPos(var11, var16, var12)).c();
                  if (var13 != Blocks.bT && var13 != Blocks.R && var13 != Blocks.ah && var13 != Blocks.S) {
                     var18 = false;
                     break;
                  }
               }
            }

            if (!var18) {
               break;
            }
         }

         if (this.j == 0) {
            this.i = false;
         }
      }

      if (!this.b.D && this.j == 4 && var1 < this.j) {
         for (EntityPlayer var17 : this.b.a(EntityPlayer.class, new AxisAlignedBB(var2, var3, var4, var2, var3 - 4, var4).b(10.0, 5.0, 10.0))) {
            var17.b(AchievementList.K);
         }
      }
   }

   public List<TileEntityBeacon.BeamSegment> n() {
      return this.f;
   }

   public float o() {
      if (!this.i) {
         return 0.0F;
      } else {
         int var1 = (int)(this.b.K() - this.g);
         this.g = this.b.K();
         if (var1 > 1) {
            this.h -= var1 / 40.0F;
            if (this.h < 0.0F) {
               this.h = 0.0F;
            }
         }

         this.h += 0.025F;
         if (this.h > 1.0F) {
            this.h = 1.0F;
         }

         return this.h;
      }
   }

   @Override
   public Packet y_() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.b(var1);
      return new S35PacketUpdateTileEntity(this.c, 3, var1);
   }

   @Override
   public double s() {
      return 65536.0;
   }

   private int h(int var1) {
      if (var1 >= 0 && var1 < Potion.a.length && Potion.a[var1] != null) {
         Potion var2 = Potion.a[var1];
         return var2 != Potion.c && var2 != Potion.e && var2 != Potion.m && var2 != Potion.j && var2 != Potion.g && var2 != Potion.l ? 0 : var1;
      } else {
         return 0;
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.k = this.h(var1.f("Primary"));
      this.l = this.h(var1.f("Secondary"));
      this.j = var1.f("Levels");
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("Primary", this.k);
      var1.a("Secondary", this.l);
      var1.a("Levels", this.j);
   }

   @Override
   public int o_() {
      return 1;
   }

   @Override
   public ItemStack a(int var1) {
      return var1 == 0 ? this.m : null;
   }

   @Override
   public ItemStack a(int var1, int var2) {
      if (var1 != 0 || this.m == null) {
         return null;
      } else if (var2 >= this.m.b) {
         ItemStack var3 = this.m;
         this.m = null;
         return var3;
      } else {
         this.m.b -= var2;
         return new ItemStack(this.m.b(), var2, this.m.i());
      }
   }

   @Override
   public ItemStack b(int var1) {
      if (var1 == 0 && this.m != null) {
         ItemStack var2 = this.m;
         this.m = null;
         return var2;
      } else {
         return null;
      }
   }

   @Override
   public void a(int var1, ItemStack var2) {
      if (var1 == 0) {
         this.m = var2;
      }
   }

   @Override
   public String e_() {
      return this.l_() ? this.n : "container.beacon";
   }

   @Override
   public boolean l_() {
      return this.n != null && this.n.length() > 0;
   }

   public void a(String var1) {
      this.n = var1;
   }

   @Override
   public int q_() {
      return 1;
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return this.b.s(this.c) != this ? false : !(var1.e(this.c.n() + 0.5, this.c.o() + 0.5, this.c.p() + 0.5) > 64.0);
   }

   @Override
   public void b(EntityPlayer var1) {
   }

   @Override
   public void c(EntityPlayer var1) {
   }

   @Override
   public boolean b(int var1, ItemStack var2) {
      return var2.b() == Items.bO || var2.b() == Items.i || var2.b() == Items.k || var2.b() == Items.j;
   }

   @Override
   public String k() {
      return "minecraft:beacon";
   }

   @Override
   public Container a(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerBeacon(var1, this);
   }

   @Override
   public int a_(int var1) {
      switch (var1) {
         case 0:
            return this.j;
         case 1:
            return this.k;
         case 2:
            return this.l;
         default:
            return 0;
      }
   }

   @Override
   public void b(int var1, int var2) {
      switch (var1) {
         case 0:
            this.j = var2;
            break;
         case 1:
            this.k = this.h(var2);
            break;
         case 2:
            this.l = this.h(var2);
      }
   }

   @Override
   public int g() {
      return 3;
   }

   @Override
   public void l() {
      this.m = null;
   }

   @Override
   public boolean c(int var1, int var2) {
      if (var1 == 1) {
         this.m();
         return true;
      } else {
         return super.c(var1, var2);
      }
   }

   public static class BeamSegment {
      private final float[] a;
      private int b;

      public BeamSegment(float[] var1) {
         this.a = var1;
         this.b = 1;
      }

      protected void a() {
         this.b++;
      }

      public float[] b() {
         return this.a;
      }

      public int c() {
         return this.b;
      }
   }
}
