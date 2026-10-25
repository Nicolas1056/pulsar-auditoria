package net.minecraft.client.renderer.entity;

import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RenderPotion extends RenderSnowball<EntityPotion> {
   public RenderPotion(RenderManager var1, RenderItem var2) {
      super(var1, Items.bz, var2);
   }

   public ItemStack a(EntityPotion var1) {
      return new ItemStack(this.a, 1, var1.o());
   }
}
