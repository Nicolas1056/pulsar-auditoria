package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityAIEatGrass extends EntityAIBase {
   private static final Predicate<IBlockState> b = BlockStateHelper.a(Blocks.H).a(BlockTallGrass.a, Predicates.equalTo(BlockTallGrass.EnumType.b));
   private EntityLiving c;
   private World d;
   int a;

   public EntityAIEatGrass(EntityLiving var1) {
      this.c = var1;
      this.d = var1.o;
      this.a(7);
   }

   @Override
   public boolean a() {
      if (this.c.bc().nextInt(this.c.j_() ? 50 : 1000) != 0) {
         return false;
      } else {
         BlockPos var1 = new BlockPos(this.c.s, this.c.t, this.c.u);
         return b.apply(this.d.p(var1)) ? true : this.d.p(var1.b()).c() == Blocks.c;
      }
   }

   @Override
   public void c() {
      this.a = 40;
      this.d.a(this.c, (byte)10);
      this.c.s().n();
   }

   @Override
   public void d() {
      this.a = 0;
   }

   @Override
   public boolean b() {
      return this.a > 0;
   }

   public int f() {
      return this.a;
   }

   @Override
   public void e() {
      this.a = Math.max(0, this.a - 1);
      if (this.a == 4) {
         BlockPos var1 = new BlockPos(this.c.s, this.c.t, this.c.u);
         if (b.apply(this.d.p(var1))) {
            if (this.d.Q().b("mobGriefing")) {
               this.d.b(var1, false);
            }

            this.c.v();
         } else {
            BlockPos var2 = var1.b();
            if (this.d.p(var2).c() == Blocks.c) {
               if (this.d.Q().b("mobGriefing")) {
                  this.d.b(2001, var2, Block.a(Blocks.c));
                  this.d.a(var2, Blocks.d.Q(), 2);
               }

               this.c.v();
            }
         }
      }
   }
}
