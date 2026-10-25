package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.BlockFlower;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntityBanner extends TileEntity {
   private int a;
   private NBTTagList f;
   private boolean g;
   private List<TileEntityBanner.EnumBannerPattern> h;
   private List<EnumDyeColor> i;
   private String j;

   public void a(ItemStack var1) {
      this.f = null;
      if (var1.n() && var1.o().b("BlockEntityTag", 10)) {
         NBTTagCompound var2 = var1.o().m("BlockEntityTag");
         if (var2.c("Patterns")) {
            this.f = (NBTTagList)var2.c("Patterns", 10).b();
         }

         if (var2.b("Base", 99)) {
            this.a = var2.f("Base");
         } else {
            this.a = var1.i() & 15;
         }
      } else {
         this.a = var1.i() & 15;
      }

      this.h = null;
      this.i = null;
      this.j = "";
      this.g = true;
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      a(var1, this.a, this.f);
   }

   public static void a(NBTTagCompound var0, int var1, NBTTagList var2) {
      var0.a("Base", var1);
      if (var2 != null) {
         var0.a("Patterns", var2);
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.a = var1.f("Base");
      this.f = var1.c("Patterns", 10);
      this.h = null;
      this.i = null;
      this.j = null;
      this.g = true;
   }

   @Override
   public Packet y_() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.b(var1);
      return new S35PacketUpdateTileEntity(this.c, 6, var1);
   }

   public int b() {
      return this.a;
   }

   public static int b(ItemStack var0) {
      NBTTagCompound var1 = var0.a("BlockEntityTag", false);
      return var1 != null && var1.c("Base") ? var1.f("Base") : var0.i();
   }

   public static int c(ItemStack var0) {
      NBTTagCompound var1 = var0.a("BlockEntityTag", false);
      return var1 != null && var1.c("Patterns") ? var1.c("Patterns", 10).c() : 0;
   }

   public List<TileEntityBanner.EnumBannerPattern> c() {
      this.h();
      return this.h;
   }

   public NBTTagList d() {
      return this.f;
   }

   public List<EnumDyeColor> e() {
      this.h();
      return this.i;
   }

   public String g() {
      this.h();
      return this.j;
   }

   private void h() {
      if (this.h == null || this.i == null || this.j == null) {
         if (!this.g) {
            this.j = "";
         } else {
            this.h = Lists.newArrayList();
            this.i = Lists.newArrayList();
            this.h.add(TileEntityBanner.EnumBannerPattern.a);
            this.i.add(EnumDyeColor.a(this.a));
            this.j = "b" + this.a;
            if (this.f != null) {
               for (int var1 = 0; var1 < this.f.c(); var1++) {
                  NBTTagCompound var2 = this.f.b(var1);
                  TileEntityBanner.EnumBannerPattern var3 = TileEntityBanner.EnumBannerPattern.a(var2.j("Pattern"));
                  if (var3 != null) {
                     this.h.add(var3);
                     int var4 = var2.f("Color");
                     this.i.add(EnumDyeColor.a(var4));
                     this.j = this.j + var3.b() + var4;
                  }
               }
            }
         }
      }
   }

   public static void e(ItemStack var0) {
      NBTTagCompound var1 = var0.a("BlockEntityTag", false);
      if (var1 != null && var1.b("Patterns", 9)) {
         NBTTagList var2 = var1.c("Patterns", 10);
         if (var2.c() > 0) {
            var2.a(var2.c() - 1);
            if (var2.c_()) {
               var0.o().o("BlockEntityTag");
               if (var0.o().c_()) {
                  var0.d(null);
               }
            }
         }
      }
   }

   public static enum EnumBannerPattern {
      a("base", "b"),
      b("square_bottom_left", "bl", "   ", "   ", "#  "),
      c("square_bottom_right", "br", "   ", "   ", "  #"),
      d("square_top_left", "tl", "#  ", "   ", "   "),
      e("square_top_right", "tr", "  #", "   ", "   "),
      f("stripe_bottom", "bs", "   ", "   ", "###"),
      g("stripe_top", "ts", "###", "   ", "   "),
      h("stripe_left", "ls", "#  ", "#  ", "#  "),
      i("stripe_right", "rs", "  #", "  #", "  #"),
      j("stripe_center", "cs", " # ", " # ", " # "),
      k("stripe_middle", "ms", "   ", "###", "   "),
      l("stripe_downright", "drs", "#  ", " # ", "  #"),
      m("stripe_downleft", "dls", "  #", " # ", "#  "),
      n("small_stripes", "ss", "# #", "# #", "   "),
      o("cross", "cr", "# #", " # ", "# #"),
      p("straight_cross", "sc", " # ", "###", " # "),
      q("triangle_bottom", "bt", "   ", " # ", "# #"),
      r("triangle_top", "tt", "# #", " # ", "   "),
      s("triangles_bottom", "bts", "   ", "# #", " # "),
      t("triangles_top", "tts", " # ", "# #", "   "),
      u("diagonal_left", "ld", "## ", "#  ", "   "),
      v("diagonal_up_right", "rd", "   ", "  #", " ##"),
      w("diagonal_up_left", "lud", "   ", "#  ", "## "),
      x("diagonal_right", "rud", " ##", "  #", "   "),
      y("circle", "mc", "   ", " # ", "   "),
      z("rhombus", "mr", " # ", "# #", " # "),
      A("half_vertical", "vh", "## ", "## ", "## "),
      B("half_horizontal", "hh", "###", "###", "   "),
      C("half_vertical_right", "vhr", " ##", " ##", " ##"),
      D("half_horizontal_bottom", "hhb", "   ", "###", "###"),
      E("border", "bo", "###", "# #", "###"),
      F("curly_border", "cbo", new ItemStack(Blocks.bn)),
      G("creeper", "cre", new ItemStack(Items.bX, 1, 4)),
      H("gradient", "gra", "# #", " # ", " # "),
      I("gradient_up", "gru", " # ", " # ", "# #"),
      J("bricks", "bri", new ItemStack(Blocks.V)),
      K("skull", "sku", new ItemStack(Items.bX, 1, 1)),
      L("flower", "flo", new ItemStack(Blocks.O, 1, BlockFlower.EnumFlowerType.j.b())),
      M("mojang", "moj", new ItemStack(Items.ao, 1, 1));

      private String N;
      private String O;
      private String[] P = new String[3];
      private ItemStack Q;

      private EnumBannerPattern(String var3, String var4) {
         this.N = var3;
         this.O = var4;
      }

      private EnumBannerPattern(String var3, String var4, ItemStack var5) {
         this(var3, var4);
         this.Q = var5;
      }

      private EnumBannerPattern(String var3, String var4, String var5, String var6, String var7) {
         this(var3, var4);
         this.P[0] = var5;
         this.P[1] = var6;
         this.P[2] = var7;
      }

      public String a() {
         return this.N;
      }

      public String b() {
         return this.O;
      }

      public String[] c() {
         return this.P;
      }

      public boolean d() {
         return this.Q != null || this.P[0] != null;
      }

      public boolean e() {
         return this.Q != null;
      }

      public ItemStack f() {
         return this.Q;
      }

      public static TileEntityBanner.EnumBannerPattern a(String var0) {
         for (TileEntityBanner.EnumBannerPattern var4 : values()) {
            if (var4.O.equals(var0)) {
               return var4;
            }
         }

         return null;
      }
   }
}
