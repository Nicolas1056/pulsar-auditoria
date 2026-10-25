package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockNote extends BlockContainer {
   private static final List<String> a = Lists.newArrayList(new String[]{"harp", "bd", "snare", "hat", "bassattack"});

   public BlockNote() {
      super(Material.d);
      this.a(CreativeTabs.d);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      boolean var5 = var1.z(var2);
      TileEntity var6 = var1.s(var2);
      if (var6 instanceof TileEntityNote) {
         TileEntityNote var7 = (TileEntityNote)var6;
         if (var7.f != var5) {
            if (var5) {
               var7.a(var1, var2);
            }

            var7.f = var5;
         }
      }
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.D) {
         return true;
      } else {
         TileEntity var9 = var1.s(var2);
         if (var9 instanceof TileEntityNote) {
            TileEntityNote var10 = (TileEntityNote)var9;
            var10.b();
            var10.a(var1, var2);
            var4.b(StatList.S);
         }

         return true;
      }
   }

   @Override
   public void a(World var1, BlockPos var2, EntityPlayer var3) {
      if (!var1.D) {
         TileEntity var4 = var1.s(var2);
         if (var4 instanceof TileEntityNote) {
            ((TileEntityNote)var4).a(var1, var2);
            var3.b(StatList.R);
         }
      }
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntityNote();
   }

   private String b(int var1) {
      if (var1 < 0 || var1 >= a.size()) {
         var1 = 0;
      }

      return a.get(var1);
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, int var4, int var5) {
      float var6 = (float)Math.pow(2.0, (var5 - 12) / 12.0);
      var1.a(var2.n() + 0.5, var2.o() + 0.5, var2.p() + 0.5, "note." + this.b(var4), 3.0F, var6);
      var1.a(EnumParticleTypes.x, var2.n() + 0.5, var2.o() + 1.2, var2.p() + 0.5, var5 / 24.0, 0.0, 0.0);
      return true;
   }

   @Override
   public int b() {
      return 3;
   }
}
