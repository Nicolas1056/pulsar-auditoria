package net.minecraft.entity.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityFallingBlock extends Entity {
   private IBlockState d;
   public int a;
   public boolean b = true;
   private boolean e;
   private boolean f;
   private int g = 40;
   private float h = 2.0F;
   public NBTTagCompound c;

   public EntityFallingBlock(World var1) {
      super(var1);
   }

   public EntityFallingBlock(World var1, double var2, double var4, double var6, IBlockState var8) {
      super(var1);
      this.d = var8;
      this.k = true;
      this.a(0.98F, 0.98F);
      this.b(var2, var4, var6);
      this.v = 0.0;
      this.w = 0.0;
      this.x = 0.0;
      this.p = var2;
      this.q = var4;
      this.r = var6;
   }

   @Override
   protected boolean s_() {
      return false;
   }

   @Override
   protected void h() {
   }

   @Override
   public boolean ad() {
      return !this.I;
   }

   @Override
   public void t_() {
      Block var1 = this.d.c();
      if (var1.t() == Material.a) {
         this.J();
      } else {
         this.p = this.s;
         this.q = this.t;
         this.r = this.u;
         if (this.a++ == 0) {
            BlockPos var2 = new BlockPos(this);
            if (this.o.p(var2).c() == var1) {
               this.o.g(var2);
            } else if (!this.o.D) {
               this.J();
               return;
            }
         }

         this.w -= 0.04F;
         this.d(this.v, this.w, this.x);
         this.v *= 0.98F;
         this.w *= 0.98F;
         this.x *= 0.98F;
         if (!this.o.D) {
            BlockPos var8 = new BlockPos(this);
            if (this.C) {
               this.v *= 0.7F;
               this.x *= 0.7F;
               this.w *= -0.5;
               if (this.o.p(var8).c() != Blocks.M) {
                  this.J();
                  if (!this.e) {
                     if (this.o.a(var1, var8, true, EnumFacing.b, null, null) && !BlockFalling.e(this.o, var8.b()) && this.o.a(var8, this.d, 3)) {
                        if (var1 instanceof BlockFalling) {
                           ((BlockFalling)var1).a_(this.o, var8);
                        }

                        if (this.c != null && var1 instanceof ITileEntityProvider) {
                           TileEntity var3 = this.o.s(var8);
                           if (var3 != null) {
                              NBTTagCompound var4 = new NBTTagCompound();
                              var3.b(var4);

                              for (String var6 : this.c.c()) {
                                 NBTBase var7 = this.c.a(var6);
                                 if (!var6.equals("x") && !var6.equals("y") && !var6.equals("z")) {
                                    var4.a(var6, var7.b());
                                 }
                              }

                              var3.a(var4);
                              var3.p_();
                           }
                        }
                     } else if (this.b && this.o.Q().b("doEntityDrops")) {
                        this.a(new ItemStack(var1, 1, var1.a(this.d)), 0.0F);
                     }
                  }
               }
            } else if (this.a > 100 && !this.o.D && (var8.o() < 1 || var8.o() > 256) || this.a > 600) {
               if (this.b && this.o.Q().b("doEntityDrops")) {
                  this.a(new ItemStack(var1, 1, var1.a(this.d)), 0.0F);
               }

               this.J();
            }
         }
      }
   }

   @Override
   public void e(float var1, float var2) {
      Block var3 = this.d.c();
      if (this.f) {
         int var4 = MathHelper.f(var1 - 1.0F);
         if (var4 > 0) {
            ArrayList var5 = Lists.newArrayList(this.o.b(this, this.aR()));
            boolean var6 = var3 == Blocks.cf;
            DamageSource var7 = var6 ? DamageSource.n : DamageSource.o;

            for (Entity var9 : var5) {
               var9.a(var7, (float)Math.min(MathHelper.d(var4 * this.h), this.g));
            }

            if (var6 && this.V.nextFloat() < 0.05F + var4 * 0.05) {
               int var10 = this.d.b(BlockAnvil.b);
               if (++var10 > 2) {
                  this.e = true;
               } else {
                  this.d = this.d.a(BlockAnvil.b, var10);
               }
            }
         }
      }
   }

   @Override
   protected void b(NBTTagCompound var1) {
      Block var2 = this.d != null ? this.d.c() : Blocks.a;
      ResourceLocation var3 = Block.c.c(var2);
      var1.a("Block", var3 == null ? "" : var3.toString());
      var1.a("Data", (byte)var2.c(this.d));
      var1.a("Time", (byte)this.a);
      var1.a("DropItem", this.b);
      var1.a("HurtEntities", this.f);
      var1.a("FallHurtAmount", this.h);
      var1.a("FallHurtMax", this.g);
      if (this.c != null) {
         var1.a("TileEntityData", this.c);
      }
   }

   @Override
   protected void a(NBTTagCompound var1) {
      int var2 = var1.d("Data") & 255;
      if (var1.b("Block", 8)) {
         this.d = Block.b(var1.j("Block")).a(var2);
      } else if (var1.b("TileID", 99)) {
         this.d = Block.c(var1.f("TileID")).a(var2);
      } else {
         this.d = Block.c(var1.d("Tile") & 255).a(var2);
      }

      this.a = var1.d("Time") & 255;
      Block var3 = this.d.c();
      if (var1.b("HurtEntities", 99)) {
         this.f = var1.n("HurtEntities");
         this.h = var1.h("FallHurtAmount");
         this.g = var1.f("FallHurtMax");
      } else if (var3 == Blocks.cf) {
         this.f = true;
      }

      if (var1.b("DropItem", 99)) {
         this.b = var1.n("DropItem");
      }

      if (var1.b("TileEntityData", 10)) {
         this.c = var1.m("TileEntityData");
      }

      if (var3 == null || var3.t() == Material.a) {
         this.d = Blocks.m.Q();
      }
   }

   public World j() {
      return this.o;
   }

   public void a(boolean var1) {
      this.f = var1;
   }

   @Override
   public boolean aJ() {
      return false;
   }

   @Override
   public void a(CrashReportCategory var1) {
      super.a(var1);
      if (this.d != null) {
         Block var2 = this.d.c();
         var1.a("Immitating block ID", Block.a(var2));
         var1.a("Immitating block data", var2.c(this.d));
      }
   }

   public IBlockState l() {
      return this.d;
   }
}
