package net.minecraft.item.crafting;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;

public class FurnaceRecipes {
   private static final FurnaceRecipes a = new FurnaceRecipes();
   private Map<ItemStack, ItemStack> b = Maps.newHashMap();
   private Map<ItemStack, Float> c = Maps.newHashMap();

   public static FurnaceRecipes a() {
      return a;
   }

   private FurnaceRecipes() {
      this.a(Blocks.p, new ItemStack(Items.j), 0.7F);
      this.a(Blocks.o, new ItemStack(Items.k), 1.0F);
      this.a(Blocks.ag, new ItemStack(Items.i), 1.0F);
      this.a(Blocks.m, new ItemStack(Blocks.w), 0.1F);
      this.a(Items.al, new ItemStack(Items.am), 0.35F);
      this.a(Items.bi, new ItemStack(Items.bj), 0.35F);
      this.a(Items.bk, new ItemStack(Items.bl), 0.35F);
      this.a(Items.bo, new ItemStack(Items.bp), 0.35F);
      this.a(Items.bm, new ItemStack(Items.bn), 0.35F);
      this.a(Blocks.e, new ItemStack(Blocks.b), 0.1F);
      this.a(new ItemStack(Blocks.bf, 1, BlockStoneBrick.b), new ItemStack(Blocks.bf, 1, BlockStoneBrick.O), 0.1F);
      this.a(Items.aI, new ItemStack(Items.aH), 0.3F);
      this.a(Blocks.aL, new ItemStack(Blocks.cz), 0.35F);
      this.a(Blocks.aK, new ItemStack(Items.aW, 1, EnumDyeColor.n.b()), 0.2F);
      this.a(Blocks.r, new ItemStack(Items.h, 1, 1), 0.15F);
      this.a(Blocks.s, new ItemStack(Items.h, 1, 1), 0.15F);
      this.a(Blocks.bP, new ItemStack(Items.bO), 1.0F);
      this.a(Items.bS, new ItemStack(Items.bT), 0.35F);
      this.a(Blocks.aV, new ItemStack(Items.cf), 0.1F);
      this.a(new ItemStack(Blocks.v, 1, 1), new ItemStack(Blocks.v, 1, 0), 0.15F);

      for (ItemFishFood.FishType var4 : ItemFishFood.FishType.values()) {
         if (var4.g()) {
            this.a(new ItemStack(Items.aU, 1, var4.a()), new ItemStack(Items.aV, 1, var4.a()), 0.35F);
         }
      }

      this.a(Blocks.q, new ItemStack(Items.h), 0.1F);
      this.a(Blocks.aC, new ItemStack(Items.aC), 0.7F);
      this.a(Blocks.x, new ItemStack(Items.aW, 1, EnumDyeColor.l.b()), 0.2F);
      this.a(Blocks.co, new ItemStack(Items.cg), 0.2F);
   }

   public void a(Block var1, ItemStack var2, float var3) {
      this.a(Item.a(var1), var2, var3);
   }

   public void a(Item var1, ItemStack var2, float var3) {
      this.a(new ItemStack(var1, 1, 32767), var2, var3);
   }

   public void a(ItemStack var1, ItemStack var2, float var3) {
      this.b.put(var1, var2);
      this.c.put(var2, var3);
   }

   public ItemStack a(ItemStack var1) {
      for (Entry var3 : this.b.entrySet()) {
         if (this.a(var1, (ItemStack)var3.getKey())) {
            return (ItemStack)var3.getValue();
         }
      }

      return null;
   }

   private boolean a(ItemStack var1, ItemStack var2) {
      return var2.b() == var1.b() && (var2.i() == 32767 || var2.i() == var1.i());
   }

   public Map<ItemStack, ItemStack> b() {
      return this.b;
   }

   public float b(ItemStack var1) {
      for (Entry var3 : this.c.entrySet()) {
         if (this.a(var1, (ItemStack)var3.getKey())) {
            return (Float)var3.getValue();
         }
      }

      return 0.0F;
   }
}
