package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSkull extends BlockContainer {
   public static final PropertyDirection a = PropertyDirection.a("facing");
   public static final PropertyBool b = PropertyBool.a("nodrop");
   private static final Predicate<BlockWorldState> N = new Predicate<BlockWorldState>() {
      public boolean a(BlockWorldState var1) {
         return var1.a() != null && var1.a().c() == Blocks.ce && var1.b() instanceof TileEntitySkull && ((TileEntitySkull)var1.b()).c() == 1;
      }
   };
   private BlockPattern O;
   private BlockPattern P;

   protected BlockSkull() {
      super(Material.q);
      this.j(this.M.b().a(a, EnumFacing.c).a(b, false));
      this.a(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
   }

   @Override
   public String f() {
      return StatCollector.a("tile.skull.skeleton.name");
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
   public void a(IBlockAccess var1, BlockPos var2) {
      switch ((EnumFacing)var1.p(var2).b(a)) {
         case b:
         default:
            this.a(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
            break;
         case c:
            this.a(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
            break;
         case d:
            this.a(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
            break;
         case e:
            this.a(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
            break;
         case f:
            this.a(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
      }
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      this.a(var1, var2);
      return super.a(var1, var2, var3);
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.Q().a(a, var8.aP()).a(b, false);
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntitySkull();
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Items.bX;
   }

   @Override
   public int j(World var1, BlockPos var2) {
      TileEntity var3 = var1.s(var2);
      return var3 instanceof TileEntitySkull ? ((TileEntitySkull)var3).c() : super.j(var1, var2);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if (var4.bA.d) {
         var3 = var3.a(b, true);
         var1.a(var2, var3, 4);
      }

      super.a(var1, var2, var3, var4);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.D) {
         if (!var3.b(b)) {
            TileEntity var4 = var1.s(var2);
            if (var4 instanceof TileEntitySkull) {
               TileEntitySkull var5 = (TileEntitySkull)var4;
               ItemStack var6 = new ItemStack(Items.bX, 1, this.j(var1, var2));
               if (var5.c() == 3 && var5.b() != null) {
                  var6.d(new NBTTagCompound());
                  NBTTagCompound var7 = new NBTTagCompound();
                  NBTUtil.a(var7, var5.b());
                  var6.o().a("SkullOwner", var7);
               }

               a(var1, var2, var6);
            }
         }

         super.b(var1, var2, var3);
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Items.bX;
   }

   public boolean b(World var1, BlockPos var2, ItemStack var3) {
      return var3.i() == 1 && var2.o() >= 2 && var1.aa() != EnumDifficulty.a && !var1.D ? this.l().a(var1, var2) != null : false;
   }

   public void a(World var1, BlockPos var2, TileEntitySkull var3) {
      if (var3.c() == 1 && var2.o() >= 2 && var1.aa() != EnumDifficulty.a && !var1.D) {
         BlockPattern var4 = this.n();
         BlockPattern.PatternHelper var5 = var4.a(var1, var2);
         if (var5 != null) {
            for (int var6 = 0; var6 < 3; var6++) {
               BlockWorldState var7 = var5.a(var6, 0, 0);
               var1.a(var7.d(), var7.a().a(b, true), 2);
            }

            for (int var12 = 0; var12 < var4.c(); var12++) {
               for (int var14 = 0; var14 < var4.b(); var14++) {
                  BlockWorldState var8 = var5.a(var12, var14, 0);
                  var1.a(var8.d(), Blocks.a.Q(), 2);
               }
            }

            BlockPos var13 = var5.a(1, 0, 0).d();
            EntityWither var15 = new EntityWither(var1);
            BlockPos var16 = var5.a(1, 2, 0).d();
            var15.b(var16.n() + 0.5, var16.o() + 0.55, var16.p() + 0.5, var5.b().k() == EnumFacing.Axis.a ? 0.0F : 90.0F, 0.0F);
            var15.aI = var5.b().k() == EnumFacing.Axis.a ? 0.0F : 90.0F;
            var15.n();

            for (EntityPlayer var10 : var1.a(EntityPlayer.class, var15.aR().b(50.0, 50.0, 50.0))) {
               var10.b(AchievementList.I);
            }

            var1.d(var15);

            for (int var17 = 0; var17 < 120; var17++) {
               var1.a(
                  EnumParticleTypes.F,
                  var13.n() + var1.s.nextDouble(),
                  var13.o() - 2 + var1.s.nextDouble() * 3.9,
                  var13.p() + var1.s.nextDouble(),
                  0.0,
                  0.0,
                  0.0
               );
            }

            for (int var18 = 0; var18 < var4.c(); var18++) {
               for (int var19 = 0; var19 < var4.b(); var19++) {
                  BlockWorldState var11 = var5.a(var18, var19, 0);
                  var1.b(var11.d(), Blocks.a);
               }
            }
         }
      }
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, EnumFacing.a(var1 & 7)).a(b, (var1 & 8) > 0);
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(a).a();
      if (var1.b(b)) {
         var2 |= 8;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b);
   }

   protected BlockPattern l() {
      if (this.O == null) {
         this.O = FactoryBlockPattern.a()
            .a("   ", "###", "~#~")
            .a('#', BlockWorldState.a(BlockStateHelper.a(Blocks.aW)))
            .a('~', BlockWorldState.a(BlockStateHelper.a(Blocks.a)))
            .b();
      }

      return this.O;
   }

   protected BlockPattern n() {
      if (this.P == null) {
         this.P = FactoryBlockPattern.a()
            .a("^^^", "###", "~#~")
            .a('#', BlockWorldState.a(BlockStateHelper.a(Blocks.aW)))
            .a('^', N)
            .a('~', BlockWorldState.a(BlockStateHelper.a(Blocks.a)))
            .b();
      }

      return this.P;
   }
}
