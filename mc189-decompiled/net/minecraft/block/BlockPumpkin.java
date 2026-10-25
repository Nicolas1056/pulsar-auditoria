package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockPumpkin extends BlockDirectional {
   private BlockPattern a;
   private BlockPattern b;
   private BlockPattern N;
   private BlockPattern P;
   private static final Predicate<IBlockState> Q = new Predicate<IBlockState>() {
      public boolean a(IBlockState var1) {
         return var1 != null && (var1.c() == Blocks.aU || var1.c() == Blocks.aZ);
      }
   };

   protected BlockPumpkin() {
      super(Material.C, MapColor.q);
      this.j(this.M.b().a(O, EnumFacing.c));
      this.a(true);
      this.a(CreativeTabs.b);
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      super.c(var1, var2, var3);
      this.f(var1, var2);
   }

   public boolean e(World var1, BlockPos var2) {
      return this.l().a(var1, var2) != null || this.T().a(var1, var2) != null;
   }

   private void f(World var1, BlockPos var2) {
      BlockPattern.PatternHelper var3;
      if ((var3 = this.n().a(var1, var2)) != null) {
         for (int var4 = 0; var4 < this.n().b(); var4++) {
            BlockWorldState var5 = var3.a(0, var4, 0);
            var1.a(var5.d(), Blocks.a.Q(), 2);
         }

         EntitySnowman var10 = new EntitySnowman(var1);
         BlockPos var13 = var3.a(0, 2, 0).d();
         var10.b(var13.n() + 0.5, var13.o() + 0.05, var13.p() + 0.5, 0.0F, 0.0F);
         var1.d(var10);

         for (int var6 = 0; var6 < 120; var6++) {
            var1.a(EnumParticleTypes.G, var13.n() + var1.s.nextDouble(), var13.o() + var1.s.nextDouble() * 2.5, var13.p() + var1.s.nextDouble(), 0.0, 0.0, 0.0);
         }

         for (int var16 = 0; var16 < this.n().b(); var16++) {
            BlockWorldState var7 = var3.a(0, var16, 0);
            var1.b(var7.d(), Blocks.a);
         }
      } else if ((var3 = this.U().a(var1, var2)) != null) {
         for (int var11 = 0; var11 < this.U().c(); var11++) {
            for (int var14 = 0; var14 < this.U().b(); var14++) {
               var1.a(var3.a(var11, var14, 0).d(), Blocks.a.Q(), 2);
            }
         }

         BlockPos var12 = var3.a(1, 2, 0).d();
         EntityIronGolem var15 = new EntityIronGolem(var1);
         var15.l(true);
         var15.b(var12.n() + 0.5, var12.o() + 0.05, var12.p() + 0.5, 0.0F, 0.0F);
         var1.d(var15);

         for (int var17 = 0; var17 < 120; var17++) {
            var1.a(EnumParticleTypes.F, var12.n() + var1.s.nextDouble(), var12.o() + var1.s.nextDouble() * 3.9, var12.p() + var1.s.nextDouble(), 0.0, 0.0, 0.0);
         }

         for (int var18 = 0; var18 < this.U().c(); var18++) {
            for (int var19 = 0; var19 < this.U().b(); var19++) {
               BlockWorldState var8 = var3.a(var18, var19, 0);
               var1.b(var8.d(), Blocks.a);
            }
         }
      }
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return var1.p(var2).c().J.j() && World.a(var1, var2.b());
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.Q().a(O, var8.aP().d());
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(O, EnumFacing.b(var1));
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(O).b();
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, O);
   }

   protected BlockPattern l() {
      if (this.a == null) {
         this.a = FactoryBlockPattern.a().a(" ", "#", "#").a('#', BlockWorldState.a(BlockStateHelper.a(Blocks.aJ))).b();
      }

      return this.a;
   }

   protected BlockPattern n() {
      if (this.b == null) {
         this.b = FactoryBlockPattern.a().a("^", "#", "#").a('^', BlockWorldState.a(Q)).a('#', BlockWorldState.a(BlockStateHelper.a(Blocks.aJ))).b();
      }

      return this.b;
   }

   protected BlockPattern T() {
      if (this.N == null) {
         this.N = FactoryBlockPattern.a()
            .a("~ ~", "###", "~#~")
            .a('#', BlockWorldState.a(BlockStateHelper.a(Blocks.S)))
            .a('~', BlockWorldState.a(BlockStateHelper.a(Blocks.a)))
            .b();
      }

      return this.N;
   }

   protected BlockPattern U() {
      if (this.P == null) {
         this.P = FactoryBlockPattern.a()
            .a("~^~", "###", "~#~")
            .a('^', BlockWorldState.a(Q))
            .a('#', BlockWorldState.a(BlockStateHelper.a(Blocks.S)))
            .a('~', BlockWorldState.a(BlockStateHelper.a(Blocks.a)))
            .b();
      }

      return this.P;
   }
}
