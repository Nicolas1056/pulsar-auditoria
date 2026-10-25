package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BlockBed extends BlockDirectional {
   public static final PropertyEnum<BlockBed.EnumPartType> a = PropertyEnum.a("part", BlockBed.EnumPartType.class);
   public static final PropertyBool b = PropertyBool.a("occupied");

   public BlockBed() {
      super(Material.n);
      this.j(this.M.b().a(a, BlockBed.EnumPartType.b).a(b, false));
      this.l();
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.D) {
         return true;
      } else {
         if (var3.b(a) != BlockBed.EnumPartType.a) {
            var2 = var2.a(var3.b(O));
            var3 = var1.p(var2);
            if (var3.c() != this) {
               return true;
            }
         }

         if (var1.t.e() && var1.b(var2) != BiomeGenBase.x) {
            if (var3.b(b)) {
               EntityPlayer var11 = this.f(var1, var2);
               if (var11 != null) {
                  var4.b(new ChatComponentTranslation("tile.bed.occupied"));
                  return true;
               }

               var3 = var3.a(b, false);
               var1.a(var2, var3, 4);
            }

            EntityPlayer.EnumStatus var12 = var4.a(var2);
            if (var12 == EntityPlayer.EnumStatus.a) {
               var3 = var3.a(b, true);
               var1.a(var2, var3, 4);
               return true;
            } else {
               if (var12 == EntityPlayer.EnumStatus.c) {
                  var4.b(new ChatComponentTranslation("tile.bed.noSleep"));
               } else if (var12 == EntityPlayer.EnumStatus.f) {
                  var4.b(new ChatComponentTranslation("tile.bed.notSafe"));
               }

               return true;
            }
         } else {
            var1.g(var2);
            BlockPos var9 = var2.a(var3.b(O).d());
            if (var1.p(var9).c() == this) {
               var1.g(var9);
            }

            var1.a(null, var2.n() + 0.5, var2.o() + 0.5, var2.p() + 0.5, 5.0F, true, true);
            return true;
         }
      }
   }

   private EntityPlayer f(World var1, BlockPos var2) {
      for (EntityPlayer var4 : var1.j) {
         if (var4.bJ() && var4.bx.equals(var2)) {
            return var4;
         }
      }

      return null;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      this.l();
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      EnumFacing var5 = var3.b(O);
      if (var3.b(a) == BlockBed.EnumPartType.a) {
         if (var1.p(var2.a(var5.d())).c() != this) {
            var1.g(var2);
         }
      } else if (var1.p(var2.a(var5)).c() != this) {
         var1.g(var2);
         if (!var1.D) {
            this.b(var1, var2, var3, 0);
         }
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return var1.b(a) == BlockBed.EnumPartType.a ? null : Items.ba;
   }

   private void l() {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
   }

   public static BlockPos a(World var0, BlockPos var1, int var2) {
      EnumFacing var3 = var0.p(var1).b(O);
      int var4 = var1.n();
      int var5 = var1.o();
      int var6 = var1.p();

      for (int var7 = 0; var7 <= 1; var7++) {
         int var8 = var4 - var3.g() * var7 - 1;
         int var9 = var6 - var3.i() * var7 - 1;
         int var10 = var8 + 2;
         int var11 = var9 + 2;

         for (int var12 = var8; var12 <= var10; var12++) {
            for (int var13 = var9; var13 <= var11; var13++) {
               BlockPos var14 = new BlockPos(var12, var5, var13);
               if (e(var0, var14)) {
                  if (var2 <= 0) {
                     return var14;
                  }

                  var2--;
               }
            }
         }
      }

      return null;
   }

   protected static boolean e(World var0, BlockPos var1) {
      return World.a(var0, var1.b()) && !var0.p(var1).c().t().a() && !var0.p(var1.a()).c().t().a();
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if (var3.b(a) == BlockBed.EnumPartType.b) {
         super.a(var1, var2, var3, var4, 0);
      }
   }

   @Override
   public int k() {
      return 1;
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Items.ba;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if (var4.bA.d && var3.b(a) == BlockBed.EnumPartType.a) {
         BlockPos var5 = var2.a(var3.b(O).d());
         if (var1.p(var5).c() == this) {
            var1.g(var5);
         }
      }
   }

   @Override
   public IBlockState a(int var1) {
      EnumFacing var2 = EnumFacing.b(var1);
      return (var1 & 8) > 0 ? this.Q().a(a, BlockBed.EnumPartType.a).a(O, var2).a(b, (var1 & 4) > 0) : this.Q().a(a, BlockBed.EnumPartType.b).a(O, var2);
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      if (var1.b(a) == BlockBed.EnumPartType.b) {
         IBlockState var4 = var2.p(var3.a(var1.b(O)));
         if (var4.c() == this) {
            var1 = var1.a(b, var4.b(b));
         }
      }

      return var1;
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(O).b();
      if (var1.b(a) == BlockBed.EnumPartType.a) {
         var2 |= 8;
         if (var1.b(b)) {
            var2 |= 4;
         }
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, O, a, b);
   }

   public static enum EnumPartType implements IStringSerializable {
      a("head"),
      b("foot");

      private final String c;

      private EnumPartType(String var3) {
         this.c = var3;
      }

      @Override
      public String toString() {
         return this.c;
      }

      @Override
      public String l() {
         return this.c;
      }
   }
}
