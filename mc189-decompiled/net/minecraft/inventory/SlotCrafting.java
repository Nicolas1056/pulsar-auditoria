package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.stats.AchievementList;

public class SlotCrafting extends Slot {
   private final InventoryCrafting a;
   private final EntityPlayer b;
   private int c;

   public SlotCrafting(EntityPlayer var1, InventoryCrafting var2, IInventory var3, int var4, int var5, int var6) {
      super(var3, var4, var5, var6);
      this.b = var1;
      this.a = var2;
   }

   @Override
   public boolean a(ItemStack var1) {
      return false;
   }

   @Override
   public ItemStack a(int var1) {
      if (this.e()) {
         this.c = this.c + Math.min(var1, this.d().b);
      }

      return super.a(var1);
   }

   @Override
   protected void a(ItemStack var1, int var2) {
      this.c += var2;
      this.c(var1);
   }

   @Override
   protected void c(ItemStack var1) {
      if (this.c > 0) {
         var1.a(this.b.o, this.b, this.c);
      }

      this.c = 0;
      if (var1.b() == Item.a(Blocks.ai)) {
         this.b.b(AchievementList.h);
      }

      if (var1.b() instanceof ItemPickaxe) {
         this.b.b(AchievementList.i);
      }

      if (var1.b() == Item.a(Blocks.al)) {
         this.b.b(AchievementList.j);
      }

      if (var1.b() instanceof ItemHoe) {
         this.b.b(AchievementList.l);
      }

      if (var1.b() == Items.P) {
         this.b.b(AchievementList.m);
      }

      if (var1.b() == Items.aZ) {
         this.b.b(AchievementList.n);
      }

      if (var1.b() instanceof ItemPickaxe && ((ItemPickaxe)var1.b()).g() != Item.ToolMaterial.a) {
         this.b.b(AchievementList.o);
      }

      if (var1.b() instanceof ItemSword) {
         this.b.b(AchievementList.r);
      }

      if (var1.b() == Item.a(Blocks.bC)) {
         this.b.b(AchievementList.E);
      }

      if (var1.b() == Item.a(Blocks.X)) {
         this.b.b(AchievementList.G);
      }

      if (var1.b() == Items.ao && var1.i() == 1) {
         this.b.b(AchievementList.M);
      }
   }

   @Override
   public void a(EntityPlayer var1, ItemStack var2) {
      this.c(var2);
      ItemStack[] var3 = CraftingManager.a().b(this.a, var1.o);

      for (int var4 = 0; var4 < var3.length; var4++) {
         ItemStack var5 = this.a.a(var4);
         ItemStack var6 = var3[var4];
         if (var5 != null) {
            this.a.a(var4, 1);
         }

         if (var6 != null) {
            if (this.a.a(var4) == null) {
               this.a.a(var4, var6);
            } else if (!this.b.bi.a(var6)) {
               this.b.a(var6, false);
            }
         }
      }
   }
}
