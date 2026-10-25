package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityPiston extends TileEntity implements ITickable {
   private IBlockState a;
   private EnumFacing f;
   private boolean g;
   private boolean h;
   private float i;
   private float j;
   private List<Entity> k = Lists.newArrayList();

   public TileEntityPiston() {
   }

   public TileEntityPiston(IBlockState var1, EnumFacing var2, boolean var3, boolean var4) {
      this.a = var1;
      this.f = var2;
      this.g = var3;
      this.h = var4;
   }

   public IBlockState b() {
      return this.a;
   }

   @Override
   public int u() {
      return 0;
   }

   public boolean d() {
      return this.g;
   }

   public EnumFacing e() {
      return this.f;
   }

   public boolean g() {
      return this.h;
   }

   public float a(float var1) {
      if (var1 > 1.0F) {
         var1 = 1.0F;
      }

      return this.j + (this.i - this.j) * var1;
   }

   public float b(float var1) {
      return this.g ? (this.a(var1) - 1.0F) * this.f.g() : (1.0F - this.a(var1)) * this.f.g();
   }

   public float c(float var1) {
      return this.g ? (this.a(var1) - 1.0F) * this.f.h() : (1.0F - this.a(var1)) * this.f.h();
   }

   public float d(float var1) {
      return this.g ? (this.a(var1) - 1.0F) * this.f.i() : (1.0F - this.a(var1)) * this.f.i();
   }

   private void a(float var1, float var2) {
      if (this.g) {
         var1 = 1.0F - var1;
      } else {
         var1--;
      }

      AxisAlignedBB var3 = Blocks.M.a(this.b, this.c, this.a, var1, this.f);
      if (var3 != null) {
         List var4 = this.b.b(null, var3);
         if (!var4.isEmpty()) {
            this.k.addAll(var4);

            for (Entity var6 : this.k) {
               if (this.a.c() == Blocks.cE && this.g) {
                  switch (this.f.k()) {
                     case a:
                        var6.v = this.f.g();
                        break;
                     case b:
                        var6.w = this.f.h();
                        break;
                     case c:
                        var6.x = this.f.i();
                  }
               } else {
                  var6.d(var2 * this.f.g(), var2 * this.f.h(), var2 * this.f.i());
               }
            }

            this.k.clear();
         }
      }
   }

   public void h() {
      if (this.j < 1.0F && this.b != null) {
         this.j = this.i = 1.0F;
         this.b.t(this.c);
         this.y();
         if (this.b.p(this.c).c() == Blocks.M) {
            this.b.a(this.c, this.a, 3);
            this.b.d(this.c, this.a.c());
         }
      }
   }

   @Override
   public void c() {
      this.j = this.i;
      if (this.j >= 1.0F) {
         this.a(1.0F, 0.25F);
         this.b.t(this.c);
         this.y();
         if (this.b.p(this.c).c() == Blocks.M) {
            this.b.a(this.c, this.a, 3);
            this.b.d(this.c, this.a.c());
         }
      } else {
         this.i += 0.5F;
         if (this.i >= 1.0F) {
            this.i = 1.0F;
         }

         if (this.g) {
            this.a(this.i, this.i - this.j + 0.0625F);
         }
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.a = Block.c(var1.f("blockId")).a(var1.f("blockData"));
      this.f = EnumFacing.a(var1.f("facing"));
      this.j = this.i = var1.h("progress");
      this.g = var1.n("extending");
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("blockId", Block.a(this.a.c()));
      var1.a("blockData", this.a.c().c(this.a));
      var1.a("facing", this.f.a());
      var1.a("progress", this.j);
      var1.a("extending", this.g);
   }
}
