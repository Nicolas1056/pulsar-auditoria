package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;

public class ItemFishFood extends ItemFood {
   private final boolean b;

   public ItemFishFood(boolean var1) {
      super(0, 0.0F, false);
      this.b = var1;
   }

   @Override
   public int h(ItemStack var1) {
      ItemFishFood.FishType var2 = ItemFishFood.FishType.a(var1);
      return this.b && var2.g() ? var2.e() : var2.c();
   }

   @Override
   public float i(ItemStack var1) {
      ItemFishFood.FishType var2 = ItemFishFood.FishType.a(var1);
      return this.b && var2.g() ? var2.f() : var2.d();
   }

   @Override
   public String j(ItemStack var1) {
      return ItemFishFood.FishType.a(var1) == ItemFishFood.FishType.d ? PotionHelper.m : null;
   }

   @Override
   protected void c(ItemStack var1, World var2, EntityPlayer var3) {
      ItemFishFood.FishType var4 = ItemFishFood.FishType.a(var1);
      if (var4 == ItemFishFood.FishType.d) {
         var3.c(new PotionEffect(Potion.u.H, 1200, 3));
         var3.c(new PotionEffect(Potion.s.H, 300, 2));
         var3.c(new PotionEffect(Potion.k.H, 300, 1));
      }

      super.c(var1, var2, var3);
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (ItemFishFood.FishType var7 : ItemFishFood.FishType.values()) {
         if (!this.b || var7.g()) {
            var3.add(new ItemStack(this, 1, var7.a()));
         }
      }
   }

   @Override
   public String e_(ItemStack var1) {
      ItemFishFood.FishType var2 = ItemFishFood.FishType.a(var1);
      return this.a() + "." + var2.b() + "." + (this.b && var2.g() ? "cooked" : "raw");
   }

   public static enum FishType {
      a(0, "cod", 2, 0.1F, 5, 0.6F),
      b(1, "salmon", 2, 0.1F, 6, 0.8F),
      c(2, "clownfish", 1, 0.1F),
      d(3, "pufferfish", 1, 0.1F);

      private static final Map<Integer, ItemFishFood.FishType> e = Maps.newHashMap();
      private final int f;
      private final String g;
      private final int h;
      private final float i;
      private final int j;
      private final float k;
      private boolean l = false;

      private FishType(int var3, String var4, int var5, float var6, int var7, float var8) {
         this.f = var3;
         this.g = var4;
         this.h = var5;
         this.i = var6;
         this.j = var7;
         this.k = var8;
         this.l = true;
      }

      private FishType(int var3, String var4, int var5, float var6) {
         this.f = var3;
         this.g = var4;
         this.h = var5;
         this.i = var6;
         this.j = 0;
         this.k = 0.0F;
         this.l = false;
      }

      public int a() {
         return this.f;
      }

      public String b() {
         return this.g;
      }

      public int c() {
         return this.h;
      }

      public float d() {
         return this.i;
      }

      public int e() {
         return this.j;
      }

      public float f() {
         return this.k;
      }

      public boolean g() {
         return this.l;
      }

      public static ItemFishFood.FishType a(int var0) {
         ItemFishFood.FishType var1 = e.get(var0);
         return var1 == null ? a : var1;
      }

      public static ItemFishFood.FishType a(ItemStack var0) {
         return var0.b() instanceof ItemFishFood ? a(var0.i()) : a;
      }

      static {
         for (ItemFishFood.FishType var3 : values()) {
            e.put(var3.a(), var3);
         }
      }
   }
}
