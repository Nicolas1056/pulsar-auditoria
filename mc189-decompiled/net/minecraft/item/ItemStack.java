package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public final class ItemStack {
   public static final DecimalFormat a = new DecimalFormat("#.###");
   public int b;
   public int c;
   private Item d;
   private NBTTagCompound e;
   private int f;
   private EntityItemFrame g;
   private Block h = null;
   private boolean i = false;
   private Block j = null;
   private boolean k = false;

   public ItemStack(Block var1) {
      this(var1, 1);
   }

   public ItemStack(Block var1, int var2) {
      this(var1, var2, 0);
   }

   public ItemStack(Block var1, int var2, int var3) {
      this(Item.a(var1), var2, var3);
   }

   public ItemStack(Item var1) {
      this(var1, 1);
   }

   public ItemStack(Item var1, int var2) {
      this(var1, var2, 0);
   }

   public ItemStack(Item var1, int var2, int var3) {
      this.d = var1;
      this.b = var2;
      this.f = var3;
      if (this.f < 0) {
         this.f = 0;
      }
   }

   public static ItemStack a(NBTTagCompound var0) {
      ItemStack var1 = new ItemStack();
      var1.c(var0);
      return var1.b() != null ? var1 : null;
   }

   private ItemStack() {
   }

   public ItemStack a(int var1) {
      ItemStack var2 = new ItemStack(this.d, var1, this.f);
      if (this.e != null) {
         var2.e = (NBTTagCompound)this.e.b();
      }

      this.b -= var1;
      return var2;
   }

   public Item b() {
      return this.d;
   }

   public boolean a(EntityPlayer var1, World var2, BlockPos var3, EnumFacing var4, float var5, float var6, float var7) {
      boolean var8 = this.b().a(this, var1, var2, var3, var4, var5, var6, var7);
      if (var8) {
         var1.b(StatList.ad[Item.b(this.d)]);
      }

      return var8;
   }

   public float a(Block var1) {
      return this.b().a(this, var1);
   }

   public ItemStack a(World var1, EntityPlayer var2) {
      return this.b().a(this, var1, var2);
   }

   public ItemStack b(World var1, EntityPlayer var2) {
      return this.b().b(this, var1, var2);
   }

   public NBTTagCompound b(NBTTagCompound var1) {
      ResourceLocation var2 = Item.e.c(this.d);
      var1.a("id", var2 == null ? "minecraft:air" : var2.toString());
      var1.a("Count", (byte)this.b);
      var1.a("Damage", (short)this.f);
      if (this.e != null) {
         var1.a("tag", this.e);
      }

      return var1;
   }

   public void c(NBTTagCompound var1) {
      if (var1.b("id", 8)) {
         this.d = Item.d(var1.j("id"));
      } else {
         this.d = Item.b(var1.e("id"));
      }

      this.b = var1.d("Count");
      this.f = var1.e("Damage");
      if (this.f < 0) {
         this.f = 0;
      }

      if (var1.b("tag", 10)) {
         this.e = var1.m("tag");
         if (this.d != null) {
            this.d.a(this.e);
         }
      }
   }

   public int c() {
      return this.b().j();
   }

   public boolean d() {
      return this.c() > 1 && (!this.e() || !this.g());
   }

   public boolean e() {
      if (this.d == null) {
         return false;
      } else {
         return this.d.l() <= 0 ? false : !this.n() || !this.o().n("Unbreakable");
      }
   }

   public boolean f() {
      return this.d.k();
   }

   public boolean g() {
      return this.e() && this.f > 0;
   }

   public int h() {
      return this.f;
   }

   public int i() {
      return this.f;
   }

   public void b(int var1) {
      this.f = var1;
      if (this.f < 0) {
         this.f = 0;
      }
   }

   public int j() {
      return this.d.l();
   }

   public boolean a(int var1, Random var2) {
      if (!this.e()) {
         return false;
      } else {
         if (var1 > 0) {
            int var3 = EnchantmentHelper.a(Enchantment.t.B, this);
            int var4 = 0;

            for (int var5 = 0; var3 > 0 && var5 < var1; var5++) {
               if (EnchantmentDurability.a(this, var3, var2)) {
                  var4++;
               }
            }

            var1 -= var4;
            if (var1 <= 0) {
               return false;
            }
         }

         this.f += var1;
         return this.f > this.j();
      }
   }

   public void a(int var1, EntityLivingBase var2) {
      if (!(var2 instanceof EntityPlayer) || !((EntityPlayer)var2).bA.d) {
         if (this.e()) {
            if (this.a(var1, var2.bc())) {
               var2.b(this);
               this.b--;
               if (var2 instanceof EntityPlayer) {
                  EntityPlayer var3 = (EntityPlayer)var2;
                  var3.b(StatList.ae[Item.b(this.d)]);
                  if (this.b == 0 && this.b() instanceof ItemBow) {
                     var3.ca();
                  }
               }

               if (this.b < 0) {
                  this.b = 0;
               }

               this.f = 0;
            }
         }
      }
   }

   public void a(EntityLivingBase var1, EntityPlayer var2) {
      boolean var3 = this.d.a(this, var1, var2);
      if (var3) {
         var2.b(StatList.ad[Item.b(this.d)]);
      }
   }

   public void a(World var1, Block var2, BlockPos var3, EntityPlayer var4) {
      boolean var5 = this.d.a(this, var1, var2, var3, var4);
      if (var5) {
         var4.b(StatList.ad[Item.b(this.d)]);
      }
   }

   public boolean b(Block var1) {
      return this.d.b(var1);
   }

   public boolean a(EntityPlayer var1, EntityLivingBase var2) {
      return this.d.a(this, var1, var2);
   }

   public ItemStack k() {
      ItemStack var1 = new ItemStack(this.d, this.b, this.f);
      if (this.e != null) {
         var1.e = (NBTTagCompound)this.e.b();
      }

      return var1;
   }

   public static boolean a(ItemStack var0, ItemStack var1) {
      if (var0 == null && var1 == null) {
         return true;
      } else if (var0 == null || var1 == null) {
         return false;
      } else {
         return var0.e == null && var1.e != null ? false : var0.e == null || var0.e.equals(var1.e);
      }
   }

   public static boolean b(ItemStack var0, ItemStack var1) {
      if (var0 == null && var1 == null) {
         return true;
      } else {
         return var0 != null && var1 != null ? var0.d(var1) : false;
      }
   }

   private boolean d(ItemStack var1) {
      if (this.b != var1.b) {
         return false;
      } else if (this.d != var1.d) {
         return false;
      } else if (this.f != var1.f) {
         return false;
      } else {
         return this.e == null && var1.e != null ? false : this.e == null || this.e.equals(var1.e);
      }
   }

   public static boolean c(ItemStack var0, ItemStack var1) {
      if (var0 == null && var1 == null) {
         return true;
      } else {
         return var0 != null && var1 != null ? var0.a(var1) : false;
      }
   }

   public boolean a(ItemStack var1) {
      return var1 != null && this.d == var1.d && this.f == var1.f;
   }

   public String a() {
      return this.d.e_(this);
   }

   public static ItemStack b(ItemStack var0) {
      return var0 == null ? null : var0.k();
   }

   @Override
   public String toString() {
      return this.b + "x" + this.d.a() + "@" + this.f;
   }

   public void a(World var1, Entity var2, int var3, boolean var4) {
      if (this.c > 0) {
         this.c--;
      }

      this.d.a(this, var1, var2, var3, var4);
   }

   public void a(World var1, EntityPlayer var2, int var3) {
      var2.a(StatList.ac[Item.b(this.d)], var3);
      this.d.d(this, var1, var2);
   }

   public boolean c(ItemStack var1) {
      return this.d(var1);
   }

   public int l() {
      return this.b().d(this);
   }

   public EnumAction m() {
      return this.b().e(this);
   }

   public void b(World var1, EntityPlayer var2, int var3) {
      this.b().a(this, var1, var2, var3);
   }

   public boolean n() {
      return this.e != null;
   }

   public NBTTagCompound o() {
      return this.e;
   }

   public NBTTagCompound a(String var1, boolean var2) {
      if (this.e != null && this.e.b(var1, 10)) {
         return this.e.m(var1);
      } else if (var2) {
         NBTTagCompound var3 = new NBTTagCompound();
         this.a(var1, var3);
         return var3;
      } else {
         return null;
      }
   }

   public NBTTagList p() {
      return this.e == null ? null : this.e.c("ench", 10);
   }

   public void d(NBTTagCompound var1) {
      this.e = var1;
   }

   public String q() {
      String var1 = this.b().a(this);
      if (this.e != null && this.e.b("display", 10)) {
         NBTTagCompound var2 = this.e.m("display");
         if (var2.b("Name", 8)) {
            var1 = var2.j("Name");
         }
      }

      return var1;
   }

   public ItemStack c(String var1) {
      if (this.e == null) {
         this.e = new NBTTagCompound();
      }

      if (!this.e.b("display", 10)) {
         this.e.a("display", new NBTTagCompound());
      }

      this.e.m("display").a("Name", var1);
      return this;
   }

   public void r() {
      if (this.e != null) {
         if (this.e.b("display", 10)) {
            NBTTagCompound var1 = this.e.m("display");
            var1.o("Name");
            if (var1.c_()) {
               this.e.o("display");
               if (this.e.c_()) {
                  this.d(null);
               }
            }
         }
      }
   }

   public boolean s() {
      if (this.e == null) {
         return false;
      } else {
         return !this.e.b("display", 10) ? false : this.e.m("display").b("Name", 8);
      }
   }

   public List<String> a(EntityPlayer var1, boolean var2) {
      ArrayList var3 = Lists.newArrayList();
      Object var4 = this.q();
      if (this.s()) {
         var4 = EnumChatFormatting.u + var4;
      }

      var4 = var4 + EnumChatFormatting.v;
      if (var2) {
         String var5 = "";
         if (var4.length() > 0) {
            var4 = var4 + " (";
            var5 = ")";
         }

         int var6 = Item.b(this.d);
         if (this.f()) {
            var4 = var4 + String.format("#%04d/%d%s", var6, this.f, var5);
         } else {
            var4 = var4 + String.format("#%04d%s", var6, var5);
         }
      } else if (!this.s() && this.d == Items.bd) {
         var4 = var4 + " #" + this.f;
      }

      var3.add(var4);
      int var15 = 0;
      if (this.n() && this.e.b("HideFlags", 99)) {
         var15 = this.e.f("HideFlags");
      }

      if ((var15 & 32) == 0) {
         this.d.a(this, var1, var3, var2);
      }

      if (this.n()) {
         if ((var15 & 1) == 0) {
            NBTTagList var16 = this.p();
            if (var16 != null) {
               for (int var7 = 0; var7 < var16.c(); var7++) {
                  short var8 = var16.b(var7).e("id");
                  short var9 = var16.b(var7).e("lvl");
                  if (Enchantment.c(var8) != null) {
                     var3.add(Enchantment.c(var8).d(var9));
                  }
               }
            }
         }

         if (this.e.b("display", 10)) {
            NBTTagCompound var17 = this.e.m("display");
            if (var17.b("color", 3)) {
               if (var2) {
                  var3.add("Color: #" + Integer.toHexString(var17.f("color")).toUpperCase());
               } else {
                  var3.add(EnumChatFormatting.u + StatCollector.a("item.dyed"));
               }
            }

            if (var17.b("Lore") == 9) {
               NBTTagList var19 = var17.c("Lore", 8);
               if (var19.c() > 0) {
                  for (int var23 = 0; var23 < var19.c(); var23++) {
                     var3.add(EnumChatFormatting.f + "" + EnumChatFormatting.u + var19.f(var23));
                  }
               }
            }
         }
      }

      Multimap var18 = this.B();
      if (!var18.isEmpty() && (var15 & 2) == 0) {
         var3.add("");

         for (Entry var24 : var18.entries()) {
            AttributeModifier var27 = (AttributeModifier)var24.getValue();
            double var10 = var27.d();
            if (var27.a() == Item.f) {
               var10 += EnchantmentHelper.a(this, EnumCreatureAttribute.a);
            }

            double var12;
            if (var27.c() != 1 && var27.c() != 2) {
               var12 = var10;
            } else {
               var12 = var10 * 100.0;
            }

            if (var10 > 0.0) {
               var3.add(
                  EnumChatFormatting.j
                     + StatCollector.a("attribute.modifier.plus." + var27.c(), a.format(var12), StatCollector.a("attribute.name." + (String)var24.getKey()))
               );
            } else if (var10 < 0.0) {
               var12 *= -1.0;
               var3.add(
                  EnumChatFormatting.m
                     + StatCollector.a("attribute.modifier.take." + var27.c(), a.format(var12), StatCollector.a("attribute.name." + (String)var24.getKey()))
               );
            }
         }
      }

      if (this.n() && this.o().n("Unbreakable") && (var15 & 4) == 0) {
         var3.add(EnumChatFormatting.j + StatCollector.a("item.unbreakable"));
      }

      if (this.n() && this.e.b("CanDestroy", 9) && (var15 & 8) == 0) {
         NBTTagList var21 = this.e.c("CanDestroy", 8);
         if (var21.c() > 0) {
            var3.add("");
            var3.add(EnumChatFormatting.h + StatCollector.a("item.canBreak"));

            for (int var25 = 0; var25 < var21.c(); var25++) {
               Block var28 = Block.b(var21.f(var25));
               if (var28 != null) {
                  var3.add(EnumChatFormatting.i + var28.f());
               } else {
                  var3.add(EnumChatFormatting.i + "missingno");
               }
            }
         }
      }

      if (this.n() && this.e.b("CanPlaceOn", 9) && (var15 & 16) == 0) {
         NBTTagList var22 = this.e.c("CanPlaceOn", 8);
         if (var22.c() > 0) {
            var3.add("");
            var3.add(EnumChatFormatting.h + StatCollector.a("item.canPlace"));

            for (int var26 = 0; var26 < var22.c(); var26++) {
               Block var29 = Block.b(var22.f(var26));
               if (var29 != null) {
                  var3.add(EnumChatFormatting.i + var29.f());
               } else {
                  var3.add(EnumChatFormatting.i + "missingno");
               }
            }
         }
      }

      if (var2) {
         if (this.g()) {
            var3.add("Durability: " + (this.j() - this.h()) + " / " + this.j());
         }

         var3.add(EnumChatFormatting.i + Item.e.c(this.d).toString());
         if (this.n()) {
            var3.add(EnumChatFormatting.i + "NBT: " + this.o().c().size() + " tag(s)");
         }
      }

      return var3;
   }

   public boolean t() {
      return this.b().f(this);
   }

   public EnumRarity u() {
      return this.b().g(this);
   }

   public boolean v() {
      return !this.b().f_(this) ? false : !this.w();
   }

   public void a(Enchantment var1, int var2) {
      if (this.e == null) {
         this.d(new NBTTagCompound());
      }

      if (!this.e.b("ench", 9)) {
         this.e.a("ench", new NBTTagList());
      }

      NBTTagList var3 = this.e.c("ench", 10);
      NBTTagCompound var4 = new NBTTagCompound();
      var4.a("id", (short)var1.B);
      var4.a("lvl", (short)((byte)var2));
      var3.a(var4);
   }

   public boolean w() {
      return this.e != null && this.e.b("ench", 9);
   }

   public void a(String var1, NBTBase var2) {
      if (this.e == null) {
         this.d(new NBTTagCompound());
      }

      this.e.a(var1, var2);
   }

   public boolean x() {
      return this.b().s();
   }

   public boolean y() {
      return this.g != null;
   }

   public void a(EntityItemFrame var1) {
      this.g = var1;
   }

   public EntityItemFrame z() {
      return this.g;
   }

   public int A() {
      return this.n() && this.e.b("RepairCost", 3) ? this.e.f("RepairCost") : 0;
   }

   public void c(int var1) {
      if (!this.n()) {
         this.e = new NBTTagCompound();
      }

      this.e.a("RepairCost", var1);
   }

   public Multimap<String, AttributeModifier> B() {
      Object var1;
      if (this.n() && this.e.b("AttributeModifiers", 9)) {
         var1 = HashMultimap.create();
         NBTTagList var2 = this.e.c("AttributeModifiers", 10);

         for (int var3 = 0; var3 < var2.c(); var3++) {
            NBTTagCompound var4 = var2.b(var3);
            AttributeModifier var5 = SharedMonsterAttributes.a(var4);
            if (var5 != null && var5.a().getLeastSignificantBits() != 0L && var5.a().getMostSignificantBits() != 0L) {
               var1.put(var4.j("AttributeName"), var5);
            }
         }
      } else {
         var1 = this.b().i();
      }

      return (Multimap<String, AttributeModifier>)var1;
   }

   public void a(Item var1) {
      this.d = var1;
   }

   public IChatComponent C() {
      ChatComponentText var1 = new ChatComponentText(this.q());
      if (this.s()) {
         var1.b().b(true);
      }

      IChatComponent var2 = new ChatComponentText("[").a(var1).a("]");
      if (this.d != null) {
         NBTTagCompound var3 = new NBTTagCompound();
         this.b(var3);
         var2.b().a(new HoverEvent(HoverEvent.Action.c, new ChatComponentText(var3.toString())));
         var2.b().a(this.u().e);
      }

      return var2;
   }

   public boolean c(Block var1) {
      if (var1 == this.h) {
         return this.i;
      } else {
         this.h = var1;
         if (this.n() && this.e.b("CanDestroy", 9)) {
            NBTTagList var2 = this.e.c("CanDestroy", 8);

            for (int var3 = 0; var3 < var2.c(); var3++) {
               Block var4 = Block.b(var2.f(var3));
               if (var4 == var1) {
                  this.i = true;
                  return true;
               }
            }
         }

         this.i = false;
         return false;
      }
   }

   public boolean d(Block var1) {
      if (var1 == this.j) {
         return this.k;
      } else {
         this.j = var1;
         if (this.n() && this.e.b("CanPlaceOn", 9)) {
            NBTTagList var2 = this.e.c("CanPlaceOn", 8);

            for (int var3 = 0; var3 < var2.c(); var3++) {
               Block var4 = Block.b(var2.f(var3));
               if (var4 == var1) {
                  this.k = true;
                  return true;
               }
            }
         }

         this.k = false;
         return false;
      }
   }
}
