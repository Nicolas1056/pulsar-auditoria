package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonExtension extends Block {
   public static final PropertyDirection a = PropertyDirection.a("facing");
   public static final PropertyEnum<BlockPistonExtension.EnumPistonType> b = PropertyEnum.a("type", BlockPistonExtension.EnumPistonType.class);
   public static final PropertyBool N = PropertyBool.a("short");

   public BlockPistonExtension() {
      super(Material.H);
      this.j(this.M.b().a(a, EnumFacing.c).a(b, BlockPistonExtension.EnumPistonType.a).a(N, false));
      this.a(i);
      this.c(0.5F);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if (var4.bA.d) {
         EnumFacing var5 = var3.b(a);
         if (var5 != null) {
            BlockPos var6 = var2.a(var5.d());
            Block var7 = var1.p(var6).c();
            if (var7 == Blocks.J || var7 == Blocks.F) {
               var1.g(var6);
            }
         }
      }

      super.a(var1, var2, var3, var4);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      super.b(var1, var2, var3);
      EnumFacing var4 = var3.b(a).d();
      var2 = var2.a(var4);
      IBlockState var5 = var1.p(var2);
      if ((var5.c() == Blocks.J || var5.c() == Blocks.F) && var5.b(BlockPistonBase.b)) {
         var5.c().b(var1, var2, var5, 0);
         var1.g(var2);
      }
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return false;
   }

   @Override
   public boolean b(World var1, BlockPos var2, EnumFacing var3) {
      return false;
   }

   @Override
   public int a(Random var1) {
      return 0;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List<AxisAlignedBB> var5, Entity var6) {
      this.d(var3);
      super.a(var1, var2, var3, var4, var5, var6);
      this.e(var3);
      super.a(var1, var2, var3, var4, var5, var6);
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   private void e(IBlockState var1) {
      float var2 = 0.25F;
      float var3 = 0.375F;
      float var4 = 0.625F;
      float var5 = 0.25F;
      float var6 = 0.75F;
      switch ((EnumFacing)var1.b(a)) {
         case a:
            this.a(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
            break;
         case b:
            this.a(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
            break;
         case c:
            this.a(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
            break;
         case d:
            this.a(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
            break;
         case e:
            this.a(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
            break;
         case f:
            this.a(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
      }
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      this.d(var1.p(var2));
   }

   public void d(IBlockState var1) {
      float var2 = 0.25F;
      EnumFacing var3 = var1.b(a);
      if (var3 != null) {
         switch (var3) {
            case a:
               this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
               break;
            case b:
               this.a(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
               break;
            case c:
               this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
               break;
            case d:
               this.a(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
               break;
            case e:
               this.a(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
               break;
            case f:
               this.a(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
         }
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      EnumFacing var5 = var3.b(a);
      BlockPos var6 = var2.a(var5.d());
      IBlockState var7 = var1.p(var6);
      if (var7.c() != Blocks.J && var7.c() != Blocks.F) {
         var1.g(var2);
      } else {
         var7.c().a(var1, var6, var7, var4);
      }
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return true;
   }

   public static EnumFacing b(int var0) {
      int var1 = var0 & 7;
      return var1 > 5 ? null : EnumFacing.a(var1);
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return var1.p(var2).b(b) == BlockPistonExtension.EnumPistonType.b ? Item.a(Blocks.F) : Item.a(Blocks.J);
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, b(var1)).a(b, (var1 & 8) > 0 ? BlockPistonExtension.EnumPistonType.b : BlockPistonExtension.EnumPistonType.a);
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(a).a();
      if (var1.b(b) == BlockPistonExtension.EnumPistonType.b) {
         var2 |= 8;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b, N);
   }

   public static enum EnumPistonType implements IStringSerializable {
      a("normal"),
      b("sticky");

      private final String c;

      private EnumPistonType(String var3) {
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
