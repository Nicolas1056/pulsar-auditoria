package net.minecraft.init;

import com.mojang.authlib.GameProfile;
import java.io.PrintStream;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.LoggingPrintStream;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bootstrap {
   private static final PrintStream a = System.out;
   private static boolean b = false;
   private static final Logger c = LogManager.getLogger();

   public static boolean a() {
      return b;
   }

   static void b() {
      BlockDispenser.N.a(Items.g, new BehaviorProjectileDispense() {
         @Override
         protected IProjectile a(World var1, IPosition var2) {
            EntityArrow var3 = new EntityArrow(var1, var2.a(), var2.b(), var2.c());
            var3.a = 1;
            return var3;
         }
      });
      BlockDispenser.N.a(Items.aP, new BehaviorProjectileDispense() {
         @Override
         protected IProjectile a(World var1, IPosition var2) {
            return new EntityEgg(var1, var2.a(), var2.b(), var2.c());
         }
      });
      BlockDispenser.N.a(Items.aD, new BehaviorProjectileDispense() {
         @Override
         protected IProjectile a(World var1, IPosition var2) {
            return new EntitySnowball(var1, var2.a(), var2.b(), var2.c());
         }
      });
      BlockDispenser.N.a(Items.bK, new BehaviorProjectileDispense() {
         @Override
         protected IProjectile a(World var1, IPosition var2) {
            return new EntityExpBottle(var1, var2.a(), var2.b(), var2.c());
         }

         @Override
         protected float a() {
            return super.a() * 0.5F;
         }

         @Override
         protected float b() {
            return super.b() * 1.25F;
         }
      });
      BlockDispenser.N.a(Items.bz, new IBehaviorDispenseItem() {
         private final BehaviorDefaultDispenseItem b = new BehaviorDefaultDispenseItem();

         @Override
         public ItemStack a(IBlockSource var1, final ItemStack var2) {
            return ItemPotion.f(var2.i()) ? (new BehaviorProjectileDispense() {
               @Override
               protected IProjectile a(World var1, IPosition var2x) {
                  return new EntityPotion(var1, var2x.a(), var2x.b(), var2x.c(), var2.k());
               }

               @Override
               protected float a() {
                  return super.a() * 0.5F;
               }

               @Override
               protected float b() {
                  return super.b() * 1.25F;
               }
            }).a(var1, var2) : this.b.a(var1, var2);
         }
      });
      BlockDispenser.N.a(Items.bJ, new BehaviorDefaultDispenseItem() {
         @Override
         public ItemStack b(IBlockSource var1, ItemStack var2) {
            EnumFacing var3 = BlockDispenser.b(var1.f());
            double var4 = var1.a() + var3.g();
            double var6 = var1.d().o() + 0.2F;
            double var8 = var1.c() + var3.i();
            Entity var10 = ItemMonsterPlacer.a(var1.i(), var2.i(), var4, var6, var8);
            if (var10 instanceof EntityLivingBase && var2.s()) {
               ((EntityLiving)var10).a(var2.q());
            }

            var2.a(1);
            return var2;
         }
      });
      BlockDispenser.N.a(Items.cb, new BehaviorDefaultDispenseItem() {
         @Override
         public ItemStack b(IBlockSource var1, ItemStack var2) {
            EnumFacing var3 = BlockDispenser.b(var1.f());
            double var4 = var1.a() + var3.g();
            double var6 = var1.d().o() + 0.2F;
            double var8 = var1.c() + var3.i();
            EntityFireworkRocket var10 = new EntityFireworkRocket(var1.i(), var4, var6, var8, var2);
            var1.i().d(var10);
            var2.a(1);
            return var2;
         }

         @Override
         protected void a(IBlockSource var1) {
            var1.i().b(1002, var1.d(), 0);
         }
      });
      BlockDispenser.N.a(Items.bL, new BehaviorDefaultDispenseItem() {
         @Override
         public ItemStack b(IBlockSource var1, ItemStack var2) {
            EnumFacing var3 = BlockDispenser.b(var1.f());
            IPosition var4 = BlockDispenser.a(var1);
            double var5 = var4.a() + var3.g() * 0.3F;
            double var7 = var4.b() + var3.h() * 0.3F;
            double var9 = var4.c() + var3.i() * 0.3F;
            World var11 = var1.i();
            Random var12 = var11.s;
            double var13 = var12.nextGaussian() * 0.05 + var3.g();
            double var15 = var12.nextGaussian() * 0.05 + var3.h();
            double var17 = var12.nextGaussian() * 0.05 + var3.i();
            var11.d(new EntitySmallFireball(var11, var5, var7, var9, var13, var15, var17));
            var2.a(1);
            return var2;
         }

         @Override
         protected void a(IBlockSource var1) {
            var1.i().b(1009, var1.d(), 0);
         }
      });
      BlockDispenser.N.a(Items.aE, new BehaviorDefaultDispenseItem() {
         private final BehaviorDefaultDispenseItem b = new BehaviorDefaultDispenseItem();

         @Override
         public ItemStack b(IBlockSource var1, ItemStack var2) {
            EnumFacing var3 = BlockDispenser.b(var1.f());
            World var4 = var1.i();
            double var5 = var1.a() + var3.g() * 1.125F;
            double var7 = var1.b() + var3.h() * 1.125F;
            double var9 = var1.c() + var3.i() * 1.125F;
            BlockPos var11 = var1.d().a(var3);
            Material var12 = var4.p(var11).c().t();
            double var13;
            if (Material.h.equals(var12)) {
               var13 = 1.0;
            } else {
               if (!Material.a.equals(var12) || !Material.h.equals(var4.p(var11.b()).c().t())) {
                  return this.b.a(var1, var2);
               }

               var13 = 0.0;
            }

            EntityBoat var15 = new EntityBoat(var4, var5, var7 + var13, var9);
            var4.d(var15);
            var2.a(1);
            return var2;
         }

         @Override
         protected void a(IBlockSource var1) {
            var1.i().b(1000, var1.d(), 0);
         }
      });
      BehaviorDefaultDispenseItem var0 = new BehaviorDefaultDispenseItem() {
         private final BehaviorDefaultDispenseItem b = new BehaviorDefaultDispenseItem();

         @Override
         public ItemStack b(IBlockSource var1, ItemStack var2) {
            ItemBucket var3 = (ItemBucket)var2.b();
            BlockPos var4 = var1.d().a(BlockDispenser.b(var1.f()));
            if (var3.a(var1.i(), var4)) {
               var2.a(Items.aw);
               var2.b = 1;
               return var2;
            } else {
               return this.b.a(var1, var2);
            }
         }
      };
      BlockDispenser.N.a(Items.ay, var0);
      BlockDispenser.N.a(Items.ax, var0);
      BlockDispenser.N.a(Items.aw, new BehaviorDefaultDispenseItem() {
         private final BehaviorDefaultDispenseItem b = new BehaviorDefaultDispenseItem();

         @Override
         public ItemStack b(IBlockSource var1, ItemStack var2) {
            World var3 = var1.i();
            BlockPos var4 = var1.d().a(BlockDispenser.b(var1.f()));
            IBlockState var5 = var3.p(var4);
            Block var6 = var5.c();
            Material var7 = var6.t();
            Item var8;
            if (Material.h.equals(var7) && var6 instanceof BlockLiquid && var5.b(BlockLiquid.b) == 0) {
               var8 = Items.ax;
            } else {
               if (!Material.i.equals(var7) || !(var6 instanceof BlockLiquid) || var5.b(BlockLiquid.b) != 0) {
                  return super.b(var1, var2);
               }

               var8 = Items.ay;
            }

            var3.g(var4);
            if (--var2.b == 0) {
               var2.a(var8);
               var2.b = 1;
            } else if (var1.<TileEntityDispenser>h().a(new ItemStack(var8)) < 0) {
               this.b.a(var1, new ItemStack(var8));
            }

            return var2;
         }
      });
      BlockDispenser.N.a(Items.d, new BehaviorDefaultDispenseItem() {
         private boolean b = true;

         @Override
         protected ItemStack b(IBlockSource var1, ItemStack var2) {
            World var3 = var1.i();
            BlockPos var4 = var1.d().a(BlockDispenser.b(var1.f()));
            if (var3.d(var4)) {
               var3.a(var4, Blocks.ab.Q());
               if (var2.a(1, var3.s)) {
                  var2.b = 0;
               }
            } else if (var3.p(var4).c() == Blocks.W) {
               Blocks.W.d(var3, var4, Blocks.W.Q().a(BlockTNT.a, true));
               var3.g(var4);
            } else {
               this.b = false;
            }

            return var2;
         }

         @Override
         protected void a(IBlockSource var1) {
            if (this.b) {
               var1.i().b(1000, var1.d(), 0);
            } else {
               var1.i().b(1001, var1.d(), 0);
            }
         }
      });
      BlockDispenser.N.a(Items.aW, new BehaviorDefaultDispenseItem() {
         private boolean b = true;

         @Override
         protected ItemStack b(IBlockSource var1, ItemStack var2) {
            if (EnumDyeColor.a == EnumDyeColor.a(var2.i())) {
               World var3 = var1.i();
               BlockPos var4 = var1.d().a(BlockDispenser.b(var1.f()));
               if (ItemDye.a(var2, var3, var4)) {
                  if (!var3.D) {
                     var3.b(2005, var4, 0);
                  }
               } else {
                  this.b = false;
               }

               return var2;
            } else {
               return super.b(var1, var2);
            }
         }

         @Override
         protected void a(IBlockSource var1) {
            if (this.b) {
               var1.i().b(1000, var1.d(), 0);
            } else {
               var1.i().b(1001, var1.d(), 0);
            }
         }
      });
      BlockDispenser.N.a(Item.a(Blocks.W), new BehaviorDefaultDispenseItem() {
         @Override
         protected ItemStack b(IBlockSource var1, ItemStack var2) {
            World var3 = var1.i();
            BlockPos var4 = var1.d().a(BlockDispenser.b(var1.f()));
            EntityTNTPrimed var5 = new EntityTNTPrimed(var3, var4.n() + 0.5, var4.o(), var4.p() + 0.5, null);
            var3.d(var5);
            var3.a(var5, "game.tnt.primed", 1.0F, 1.0F);
            var2.b--;
            return var2;
         }
      });
      BlockDispenser.N.a(Items.bX, new BehaviorDefaultDispenseItem() {
         private boolean b = true;

         @Override
         protected ItemStack b(IBlockSource var1, ItemStack var2) {
            World var3 = var1.i();
            EnumFacing var4 = BlockDispenser.b(var1.f());
            BlockPos var5 = var1.d().a(var4);
            BlockSkull var6 = Blocks.ce;
            if (!var3.d(var5) || !var6.b(var3, var5, var2)) {
               this.b = false;
            } else if (!var3.D) {
               var3.a(var5, var6.Q().a(BlockSkull.a, EnumFacing.b), 3);
               TileEntity var7 = var3.s(var5);
               if (var7 instanceof TileEntitySkull) {
                  if (var2.i() == 3) {
                     GameProfile var8 = null;
                     if (var2.n()) {
                        NBTTagCompound var9 = var2.o();
                        if (var9.b("SkullOwner", 10)) {
                           var8 = NBTUtil.a(var9.m("SkullOwner"));
                        } else if (var9.b("SkullOwner", 8)) {
                           String var10 = var9.j("SkullOwner");
                           if (!StringUtils.b(var10)) {
                              var8 = new GameProfile(null, var10);
                           }
                        }
                     }

                     ((TileEntitySkull)var7).a(var8);
                  } else {
                     ((TileEntitySkull)var7).a(var2.i());
                  }

                  ((TileEntitySkull)var7).b(var4.d().b() * 4);
                  Blocks.ce.a(var3, var5, (TileEntitySkull)var7);
               }

               var2.b--;
            }

            return var2;
         }

         @Override
         protected void a(IBlockSource var1) {
            if (this.b) {
               var1.i().b(1000, var1.d(), 0);
            } else {
               var1.i().b(1001, var1.d(), 0);
            }
         }
      });
      BlockDispenser.N.a(Item.a(Blocks.aU), new BehaviorDefaultDispenseItem() {
         private boolean b = true;

         @Override
         protected ItemStack b(IBlockSource var1, ItemStack var2) {
            World var3 = var1.i();
            BlockPos var4 = var1.d().a(BlockDispenser.b(var1.f()));
            BlockPumpkin var5 = (BlockPumpkin)Blocks.aU;
            if (var3.d(var4) && var5.e(var3, var4)) {
               if (!var3.D) {
                  var3.a(var4, var5.Q(), 3);
               }

               var2.b--;
            } else {
               this.b = false;
            }

            return var2;
         }

         @Override
         protected void a(IBlockSource var1) {
            if (this.b) {
               var1.i().b(1000, var1.d(), 0);
            } else {
               var1.i().b(1001, var1.d(), 0);
            }
         }
      });
   }

   public static void c() {
      if (!b) {
         b = true;
         if (c.isDebugEnabled()) {
            d();
         }

         Block.S();
         BlockFire.l();
         Item.t();
         StatList.a();
         b();
      }
   }

   private static void d() {
      System.setErr(new LoggingPrintStream("STDERR", System.err));
      System.setOut(new LoggingPrintStream("STDOUT", a));
   }

   public static void a(String var0) {
      a.println(var0);
   }
}
