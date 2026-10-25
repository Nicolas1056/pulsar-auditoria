package net.minecraft.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemSword extends Item {
   private float a;
   private final Item.ToolMaterial b;

   public ItemSword(Item.ToolMaterial var1) {
      this.b = var1;
      this.h = 1;
      this.d(var1.a());
      this.a(CreativeTabs.j);
      this.a = 4.0F + var1.c();
   }

   public float g() {
      return this.b.c();
   }

   @Override
   public float a(ItemStack var1, Block var2) {
      if (var2 == Blocks.G) {
         return 15.0F;
      } else {
         Material var3 = var2.t();
         return var3 != Material.k && var3 != Material.l && var3 != Material.v && var3 != Material.j && var3 != Material.C ? 1.0F : 1.5F;
      }
   }

   @Override
   public boolean a(ItemStack var1, EntityLivingBase var2, EntityLivingBase var3) {
      var1.a(1, var3);
      return true;
   }

   @Override
   public boolean a(ItemStack var1, World var2, Block var3, BlockPos var4, EntityLivingBase var5) {
      if (var3.g(var2, var4) != 0.0) {
         var1.a(2, var5);
      }

      return true;
   }

   @Override
   public boolean w_() {
      return true;
   }

   @Override
   public EnumAction e(ItemStack var1) {
      return EnumAction.d;
   }

   @Override
   public int d(ItemStack var1) {
      return 72000;
   }

   @Override
   public ItemStack a(ItemStack var1, World var2, EntityPlayer var3) {
      var3.a(var1, this.d(var1));
      return var1;
   }

   @Override
   public boolean b(Block var1) {
      return var1 == Blocks.G;
   }

   @Override
   public int b() {
      return this.b.e();
   }

   public String h() {
      return this.b.toString();
   }

   @Override
   public boolean a(ItemStack var1, ItemStack var2) {
      return this.b.f() == var2.b() ? true : super.a(var1, var2);
   }

   @Override
   public Multimap<String, AttributeModifier> i() {
      Multimap var1 = super.i();
      var1.put(SharedMonsterAttributes.e.a(), new AttributeModifier(f, "Weapon modifier", this.a, 0));
      return var1;
   }
}
