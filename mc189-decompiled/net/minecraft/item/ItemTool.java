package net.minecraft.item;

import com.google.common.collect.Multimap;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemTool extends Item {
   private Set<Block> c;
   protected float a = 4.0F;
   private float d;
   protected Item.ToolMaterial b;

   protected ItemTool(float var1, Item.ToolMaterial var2, Set<Block> var3) {
      this.b = var2;
      this.c = var3;
      this.h = 1;
      this.d(var2.a());
      this.a = var2.b();
      this.d = var1 + var2.c();
      this.a(CreativeTabs.i);
   }

   @Override
   public float a(ItemStack var1, Block var2) {
      return this.c.contains(var2) ? this.a : 1.0F;
   }

   @Override
   public boolean a(ItemStack var1, EntityLivingBase var2, EntityLivingBase var3) {
      var1.a(2, var3);
      return true;
   }

   @Override
   public boolean a(ItemStack var1, World var2, Block var3, BlockPos var4, EntityLivingBase var5) {
      if (var3.g(var2, var4) != 0.0) {
         var1.a(1, var5);
      }

      return true;
   }

   @Override
   public boolean w_() {
      return true;
   }

   public Item.ToolMaterial g() {
      return this.b;
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
      var1.put(SharedMonsterAttributes.e.a(), new AttributeModifier(f, "Tool modifier", this.d, 0));
      return var1;
   }
}
