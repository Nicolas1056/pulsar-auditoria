package net.minecraft.item;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.world.World;

public class ItemArmor extends Item {
   private static final int[] k = new int[]{11, 16, 15, 13};
   public static final String[] a = new String[]{
      "minecraft:items/empty_armor_slot_helmet",
      "minecraft:items/empty_armor_slot_chestplate",
      "minecraft:items/empty_armor_slot_leggings",
      "minecraft:items/empty_armor_slot_boots"
   };
   private static final IBehaviorDispenseItem l = new BehaviorDefaultDispenseItem() {
      @Override
      protected ItemStack b(IBlockSource var1, ItemStack var2) {
         BlockPos var3 = var1.d().a(BlockDispenser.b(var1.f()));
         int var4 = var3.n();
         int var5 = var3.o();
         int var6 = var3.p();
         AxisAlignedBB var7 = new AxisAlignedBB(var4, var5, var6, var4 + 1, var5 + 1, var6 + 1);
         List var8 = var1.i().a(EntityLivingBase.class, var7, Predicates.and(EntitySelectors.d, new EntitySelectors.ArmoredMob(var2)));
         if (var8.size() > 0) {
            EntityLivingBase var9 = (EntityLivingBase)var8.get(0);
            int var10 = var9 instanceof EntityPlayer ? 1 : 0;
            int var11 = EntityLiving.c(var2);
            ItemStack var12 = var2.k();
            var12.b = 1;
            var9.c(var11 - var10, var12);
            if (var9 instanceof EntityLiving) {
               ((EntityLiving)var9).a(var11, 2.0F);
            }

            var2.b--;
            return var2;
         } else {
            return super.b(var1, var2);
         }
      }
   };
   public final int b;
   public final int c;
   public final int d;
   private final ItemArmor.ArmorMaterial m;

   public ItemArmor(ItemArmor.ArmorMaterial var1, int var2, int var3) {
      this.m = var1;
      this.b = var3;
      this.d = var2;
      this.c = var1.b(var3);
      this.d(var1.a(var3));
      this.h = 1;
      this.a(CreativeTabs.j);
      BlockDispenser.N.a(this, l);
   }

   @Override
   public int a(ItemStack var1, int var2) {
      if (var2 > 0) {
         return 16777215;
      } else {
         int var3 = this.b(var1);
         if (var3 < 0) {
            var3 = 16777215;
         }

         return var3;
      }
   }

   @Override
   public int b() {
      return this.m.a();
   }

   public ItemArmor.ArmorMaterial x_() {
      return this.m;
   }

   public boolean d_(ItemStack var1) {
      if (this.m != ItemArmor.ArmorMaterial.a) {
         return false;
      } else if (!var1.n()) {
         return false;
      } else {
         return !var1.o().b("display", 10) ? false : var1.o().m("display").b("color", 3);
      }
   }

   public int b(ItemStack var1) {
      if (this.m != ItemArmor.ArmorMaterial.a) {
         return -1;
      } else {
         NBTTagCompound var2 = var1.o();
         if (var2 != null) {
            NBTTagCompound var3 = var2.m("display");
            if (var3 != null && var3.b("color", 3)) {
               return var3.f("color");
            }
         }

         return 10511680;
      }
   }

   public void c(ItemStack var1) {
      if (this.m == ItemArmor.ArmorMaterial.a) {
         NBTTagCompound var2 = var1.o();
         if (var2 != null) {
            NBTTagCompound var3 = var2.m("display");
            if (var3.c("color")) {
               var3.o("color");
            }
         }
      }
   }

   public void b(ItemStack var1, int var2) {
      if (this.m != ItemArmor.ArmorMaterial.a) {
         throw new UnsupportedOperationException("Can't dye non-leather!");
      } else {
         NBTTagCompound var3 = var1.o();
         if (var3 == null) {
            var3 = new NBTTagCompound();
            var1.d(var3);
         }

         NBTTagCompound var4 = var3.m("display");
         if (!var3.b("display", 10)) {
            var3.a("display", var4);
         }

         var4.a("color", var2);
      }
   }

   @Override
   public boolean a(ItemStack var1, ItemStack var2) {
      return this.m.b() == var2.b() ? true : super.a(var1, var2);
   }

   @Override
   public ItemStack a(ItemStack var1, World var2, EntityPlayer var3) {
      int var4 = EntityLiving.c(var1) - 1;
      ItemStack var5 = var3.q(var4);
      if (var5 == null) {
         var3.c(var4, var1.k());
         var1.b = 0;
      }

      return var1;
   }

   public static enum ArmorMaterial {
      a("leather", 5, new int[]{1, 3, 2, 1}, 15),
      b("chainmail", 15, new int[]{2, 5, 4, 1}, 12),
      c("iron", 15, new int[]{2, 6, 5, 2}, 9),
      d("gold", 7, new int[]{2, 5, 3, 1}, 25),
      e("diamond", 33, new int[]{3, 8, 6, 3}, 10);

      private final String f;
      private final int g;
      private final int[] h;
      private final int i;

      private ArmorMaterial(String var3, int var4, int[] var5, int var6) {
         this.f = var3;
         this.g = var4;
         this.h = var5;
         this.i = var6;
      }

      public int a(int var1) {
         return ItemArmor.k[var1] * this.g;
      }

      public int b(int var1) {
         return this.h[var1];
      }

      public int a() {
         return this.i;
      }

      public Item b() {
         if (this == a) {
            return Items.aF;
         } else if (this == b) {
            return Items.j;
         } else if (this == d) {
            return Items.k;
         } else if (this == c) {
            return Items.j;
         } else {
            return this == e ? Items.i : null;
         }
      }

      public String c() {
         return this.f;
      }
   }
}
