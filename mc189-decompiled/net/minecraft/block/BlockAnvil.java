package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockAnvil extends BlockFalling {
   public static final PropertyDirection a = PropertyDirection.a("facing", EnumFacing.Plane.a);
   public static final PropertyInteger b = PropertyInteger.a("damage", 0, 2);

   protected BlockAnvil() {
      super(Material.g);
      this.j(this.M.b().a(a, EnumFacing.c).a(b, 0));
      this.e(0);
      this.a(CreativeTabs.c);
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
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      EnumFacing var9 = var8.aP().e();
      return super.a(var1, var2, var3, var4, var5, var6, var7, var8).a(a, var9).a(b, var7 >> 2);
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (!var1.D) {
         var4.a(new BlockAnvil.Anvil(var1, var2));
      }

      return true;
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(b);
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      EnumFacing var3 = var1.p(var2).b(a);
      if (var3.k() == EnumFacing.Axis.a) {
         this.a(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
      } else {
         this.a(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
      }
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      var3.add(new ItemStack(var1, 1, 0));
      var3.add(new ItemStack(var1, 1, 1));
      var3.add(new ItemStack(var1, 1, 2));
   }

   @Override
   protected void a(EntityFallingBlock var1) {
      var1.a(true);
   }

   @Override
   public void a_(World var1, BlockPos var2) {
      var1.b(1022, var2, 0);
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return true;
   }

   @Override
   public IBlockState b(IBlockState var1) {
      return this.Q().a(a, EnumFacing.d);
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, EnumFacing.b(var1 & 3)).a(b, (var1 & 15) >> 2);
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(a).b();
      return var2 | var1.b(b) << 2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b);
   }

   public static class Anvil implements IInteractionObject {
      private final World a;
      private final BlockPos b;

      public Anvil(World var1, BlockPos var2) {
         this.a = var1;
         this.b = var2;
      }

      @Override
      public String e_() {
         return "anvil";
      }

      @Override
      public boolean l_() {
         return false;
      }

      @Override
      public IChatComponent f_() {
         return new ChatComponentTranslation(Blocks.cf.a() + ".name");
      }

      @Override
      public Container a(InventoryPlayer var1, EntityPlayer var2) {
         return new ContainerRepair(var1, this.a, this.b, var2);
      }

      @Override
      public String k() {
         return "minecraft:anvil";
      }
   }
}
