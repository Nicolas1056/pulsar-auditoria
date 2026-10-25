package net.minecraft.creativetab;

import java.util.List;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class CreativeTabs {
   public static final CreativeTabs[] a = new CreativeTabs[12];
   public static final CreativeTabs b = new CreativeTabs(0, "buildingBlocks") {
      @Override
      public Item e() {
         return Item.a(Blocks.V);
      }
   };
   public static final CreativeTabs c = new CreativeTabs(1, "decorations") {
      @Override
      public Item e() {
         return Item.a(Blocks.cF);
      }

      @Override
      public int f() {
         return BlockDoublePlant.EnumPlantType.f.a();
      }
   };
   public static final CreativeTabs d = new CreativeTabs(2, "redstone") {
      @Override
      public Item e() {
         return Items.aC;
      }
   };
   public static final CreativeTabs e = new CreativeTabs(3, "transportation") {
      @Override
      public Item e() {
         return Item.a(Blocks.D);
      }
   };
   public static final CreativeTabs f = (new CreativeTabs(4, "misc") {
      @Override
      public Item e() {
         return Items.ay;
      }
   }).a(new EnumEnchantmentType[]{EnumEnchantmentType.a});
   public static final CreativeTabs g = (new CreativeTabs(5, "search") {
      @Override
      public Item e() {
         return Items.aQ;
      }
   }).a("item_search.png");
   public static final CreativeTabs h = new CreativeTabs(6, "food") {
      @Override
      public Item e() {
         return Items.e;
      }
   };
   public static final CreativeTabs i = (new CreativeTabs(7, "tools") {
      @Override
      public Item e() {
         return Items.c;
      }
   }).a(new EnumEnchantmentType[]{EnumEnchantmentType.h, EnumEnchantmentType.i, EnumEnchantmentType.j});
   public static final CreativeTabs j = (new CreativeTabs(8, "combat") {
         @Override
         public Item e() {
            return Items.B;
         }
      })
      .a(
         new EnumEnchantmentType[]{
            EnumEnchantmentType.b,
            EnumEnchantmentType.c,
            EnumEnchantmentType.f,
            EnumEnchantmentType.d,
            EnumEnchantmentType.e,
            EnumEnchantmentType.k,
            EnumEnchantmentType.g
         }
      );
   public static final CreativeTabs k = new CreativeTabs(9, "brewing") {
      @Override
      public Item e() {
         return Items.bz;
      }
   };
   public static final CreativeTabs l = new CreativeTabs(10, "materials") {
      @Override
      public Item e() {
         return Items.y;
      }
   };
   public static final CreativeTabs m = (new CreativeTabs(11, "inventory") {
      @Override
      public Item e() {
         return Item.a(Blocks.ae);
      }
   }).a("inventory.png").k().i();
   private final int n;
   private final String o;
   private String p = "items.png";
   private boolean q = true;
   private boolean r = true;
   private EnumEnchantmentType[] s;
   private ItemStack t;

   public CreativeTabs(int var1, String var2) {
      this.n = var1;
      this.o = var2;
      a[var1] = this;
   }

   public int a() {
      return this.n;
   }

   public String b() {
      return this.o;
   }

   public String c() {
      return "itemGroup." + this.b();
   }

   public ItemStack d() {
      if (this.t == null) {
         this.t = new ItemStack(this.e(), 1, this.f());
      }

      return this.t;
   }

   public abstract Item e();

   public int f() {
      return 0;
   }

   public String g() {
      return this.p;
   }

   public CreativeTabs a(String var1) {
      this.p = var1;
      return this;
   }

   public boolean h() {
      return this.r;
   }

   public CreativeTabs i() {
      this.r = false;
      return this;
   }

   public boolean j() {
      return this.q;
   }

   public CreativeTabs k() {
      this.q = false;
      return this;
   }

   public int l() {
      return this.n % 6;
   }

   public boolean m() {
      return this.n < 6;
   }

   public EnumEnchantmentType[] n() {
      return this.s;
   }

   public CreativeTabs a(EnumEnchantmentType... var1) {
      this.s = var1;
      return this;
   }

   public boolean a(EnumEnchantmentType var1) {
      if (this.s == null) {
         return false;
      } else {
         for (EnumEnchantmentType var5 : this.s) {
            if (var5 == var1) {
               return true;
            }
         }

         return false;
      }
   }

   public void a(List<ItemStack> var1) {
      for (Item var3 : Item.e) {
         if (var3 != null && var3.c() == this) {
            var3.a(var3, this, var1);
         }
      }

      if (this.n() != null) {
         this.a(var1, this.n());
      }
   }

   public void a(List<ItemStack> var1, EnumEnchantmentType... var2) {
      for (Enchantment var6 : Enchantment.b) {
         if (var6 != null && var6.C != null) {
            boolean var7 = false;

            for (int var8 = 0; var8 < var2.length && !var7; var8++) {
               if (var6.C == var2[var8]) {
                  var7 = true;
               }
            }

            if (var7) {
               var1.add(Items.cd.a(new EnchantmentData(var6, var6.b())));
            }
         }
      }
   }
}
