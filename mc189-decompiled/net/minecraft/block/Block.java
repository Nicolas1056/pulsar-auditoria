package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Block {
   private static final ResourceLocation a = new ResourceLocation("air");
   public static final RegistryNamespacedDefaultedByKey<ResourceLocation, Block> c = new RegistryNamespacedDefaultedByKey<>(a);
   public static final ObjectIntIdentityMap<IBlockState> d = new ObjectIntIdentityMap<>();
   private CreativeTabs b;
   public static final Block.SoundType e = new Block.SoundType("stone", 1.0F, 1.0F);
   public static final Block.SoundType f = new Block.SoundType("wood", 1.0F, 1.0F);
   public static final Block.SoundType g = new Block.SoundType("gravel", 1.0F, 1.0F);
   public static final Block.SoundType h = new Block.SoundType("grass", 1.0F, 1.0F);
   public static final Block.SoundType i = new Block.SoundType("stone", 1.0F, 1.0F);
   public static final Block.SoundType j = new Block.SoundType("stone", 1.0F, 1.5F);
   public static final Block.SoundType k = new Block.SoundType("stone", 1.0F, 1.0F) {
      @Override
      public String a() {
         return "dig.glass";
      }

      @Override
      public String b() {
         return "step.stone";
      }
   };
   public static final Block.SoundType l = new Block.SoundType("cloth", 1.0F, 1.0F);
   public static final Block.SoundType m = new Block.SoundType("sand", 1.0F, 1.0F);
   public static final Block.SoundType n = new Block.SoundType("snow", 1.0F, 1.0F);
   public static final Block.SoundType o = new Block.SoundType("ladder", 1.0F, 1.0F) {
      @Override
      public String a() {
         return "dig.wood";
      }
   };
   public static final Block.SoundType p = new Block.SoundType("anvil", 0.3F, 1.0F) {
      @Override
      public String a() {
         return "dig.stone";
      }

      @Override
      public String b() {
         return "random.anvil_land";
      }
   };
   public static final Block.SoundType q = new Block.SoundType("slime", 1.0F, 1.0F) {
      @Override
      public String a() {
         return "mob.slime.big";
      }

      @Override
      public String b() {
         return "mob.slime.big";
      }

      @Override
      public String c() {
         return "mob.slime.small";
      }
   };
   protected boolean r;
   protected int s;
   protected boolean t;
   protected int u;
   protected boolean v;
   protected float w;
   protected float x;
   protected boolean y = true;
   protected boolean z;
   protected boolean A;
   protected double B;
   protected double C;
   protected double D;
   protected double E;
   protected double F;
   protected double G;
   public Block.SoundType H = e;
   public float I = 1.0F;
   protected final Material J;
   protected final MapColor K;
   public float L = 0.6F;
   protected final BlockState M;
   private IBlockState N;
   private String O;

   public static int a(Block var0) {
      return c.b(var0);
   }

   public static int f(IBlockState var0) {
      Block var1 = var0.c();
      return a(var1) + (var1.c(var0) << 12);
   }

   public static Block c(int var0) {
      return c.a(var0);
   }

   public static IBlockState d(int var0) {
      int var1 = var0 & 4095;
      int var2 = var0 >> 12 & 15;
      return c(var1).a(var2);
   }

   public static Block a(Item var0) {
      return var0 instanceof ItemBlock ? ((ItemBlock)var0).d() : null;
   }

   public static Block b(String var0) {
      ResourceLocation var1 = new ResourceLocation(var0);
      if (c.d(var1)) {
         return c.a(var1);
      } else {
         try {
            return c.a(Integer.parseInt(var0));
         } catch (NumberFormatException var3) {
            return null;
         }
      }
   }

   public boolean o() {
      return this.r;
   }

   public int p() {
      return this.s;
   }

   public boolean q() {
      return this.t;
   }

   public int r() {
      return this.u;
   }

   public boolean s() {
      return this.v;
   }

   public Material t() {
      return this.J;
   }

   public MapColor g(IBlockState var1) {
      return this.K;
   }

   public IBlockState a(int var1) {
      return this.Q();
   }

   public int c(IBlockState var1) {
      if (var1 != null && !var1.a().isEmpty()) {
         throw new IllegalArgumentException("Don't know how to convert " + var1 + " back into data...");
      } else {
         return 0;
      }
   }

   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1;
   }

   public Block(Material var1, MapColor var2) {
      this.J = var1;
      this.K = var2;
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.r = this.c();
      this.s = this.c() ? 255 : 0;
      this.t = !var1.b();
      this.M = this.e();
      this.j(this.M.b());
   }

   protected Block(Material var1) {
      this(var1, var1.r());
   }

   protected Block a(Block.SoundType var1) {
      this.H = var1;
      return this;
   }

   protected Block e(int var1) {
      this.s = var1;
      return this;
   }

   protected Block a(float var1) {
      this.u = (int)(15.0F * var1);
      return this;
   }

   protected Block b(float var1) {
      this.x = var1 * 3.0F;
      return this;
   }

   public boolean u() {
      return this.J.c() && this.d();
   }

   public boolean v() {
      return this.J.k() && this.d() && !this.i();
   }

   public boolean w() {
      return this.J.c() && this.d();
   }

   public boolean d() {
      return true;
   }

   public boolean b(IBlockAccess var1, BlockPos var2) {
      return !this.J.c();
   }

   public int b() {
      return 3;
   }

   public boolean a(World var1, BlockPos var2) {
      return false;
   }

   protected Block c(float var1) {
      this.w = var1;
      if (this.x < var1 * 5.0F) {
         this.x = var1 * 5.0F;
      }

      return this;
   }

   protected Block x() {
      this.c(-1.0F);
      return this;
   }

   public float g(World var1, BlockPos var2) {
      return this.w;
   }

   protected Block a(boolean var1) {
      this.z = var1;
      return this;
   }

   public boolean y() {
      return this.z;
   }

   public boolean z() {
      return this.A;
   }

   protected final void a(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.B = var1;
      this.C = var2;
      this.D = var3;
      this.E = var4;
      this.F = var5;
      this.G = var6;
   }

   public int c(IBlockAccess var1, BlockPos var2) {
      Block var3 = var1.p(var2).c();
      int var4 = var1.b(var2, var3.r());
      if (var4 == 0 && var3 instanceof BlockSlab) {
         var2 = var2.b();
         var3 = var1.p(var2).c();
         return var1.b(var2, var3.r());
      } else {
         return var4;
      }
   }

   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      if (var3 == EnumFacing.a && this.C > 0.0) {
         return true;
      } else if (var3 == EnumFacing.b && this.F < 1.0) {
         return true;
      } else if (var3 == EnumFacing.c && this.D > 0.0) {
         return true;
      } else if (var3 == EnumFacing.d && this.G < 1.0) {
         return true;
      } else if (var3 == EnumFacing.e && this.B > 0.0) {
         return true;
      } else {
         return var3 == EnumFacing.f && this.E < 1.0 ? true : !var1.p(var2).c().c();
      }
   }

   public boolean b(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return var1.p(var2).c().t().a();
   }

   public AxisAlignedBB b(World var1, BlockPos var2) {
      return new AxisAlignedBB(var2.n() + this.B, var2.o() + this.C, var2.p() + this.D, var2.n() + this.E, var2.o() + this.F, var2.p() + this.G);
   }

   public void a(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List<AxisAlignedBB> var5, Entity var6) {
      AxisAlignedBB var7 = this.a(var1, var2, var3);
      if (var7 != null && var4.b(var7)) {
         var5.add(var7);
      }
   }

   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return new AxisAlignedBB(var2.n() + this.B, var2.o() + this.C, var2.p() + this.D, var2.n() + this.E, var2.o() + this.F, var2.p() + this.G);
   }

   public boolean c() {
      return true;
   }

   public boolean a(IBlockState var1, boolean var2) {
      return this.A();
   }

   public boolean A() {
      return true;
   }

   public void a(World var1, BlockPos var2, IBlockState var3, Random var4) {
      this.b(var1, var2, var3, var4);
   }

   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   public void d(World var1, BlockPos var2, IBlockState var3) {
   }

   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
   }

   public int a(World var1) {
      return 10;
   }

   public void c(World var1, BlockPos var2, IBlockState var3) {
   }

   public void b(World var1, BlockPos var2, IBlockState var3) {
   }

   public int a(Random var1) {
      return 1;
   }

   public Item a(IBlockState var1, Random var2, int var3) {
      return Item.a(this);
   }

   public float a(EntityPlayer var1, World var2, BlockPos var3) {
      float var4 = this.g(var2, var3);
      if (var4 < 0.0F) {
         return 0.0F;
      } else {
         return !var1.b(this) ? var1.a(this) / var4 / 100.0F : var1.a(this) / var4 / 30.0F;
      }
   }

   public final void b(World var1, BlockPos var2, IBlockState var3, int var4) {
      this.a(var1, var2, var3, 1.0F, var4);
   }

   public void a(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if (!var1.D) {
         int var6 = this.a(var5, var1.s);

         for (int var7 = 0; var7 < var6; var7++) {
            if (!(var1.s.nextFloat() > var4)) {
               Item var8 = this.a(var3, var1.s, var5);
               if (var8 != null) {
                  a(var1, var2, new ItemStack(var8, 1, this.a(var3)));
               }
            }
         }
      }
   }

   public static void a(World var0, BlockPos var1, ItemStack var2) {
      if (!var0.D && var0.Q().b("doTileDrops")) {
         float var3 = 0.5F;
         double var4 = var0.s.nextFloat() * var3 + (1.0F - var3) * 0.5;
         double var6 = var0.s.nextFloat() * var3 + (1.0F - var3) * 0.5;
         double var8 = var0.s.nextFloat() * var3 + (1.0F - var3) * 0.5;
         EntityItem var10 = new EntityItem(var0, var1.n() + var4, var1.o() + var6, var1.p() + var8, var2);
         var10.p();
         var0.d(var10);
      }
   }

   protected void b(World var1, BlockPos var2, int var3) {
      if (!var1.D) {
         while (var3 > 0) {
            int var4 = EntityXPOrb.a(var3);
            var3 -= var4;
            var1.d(new EntityXPOrb(var1, var2.n() + 0.5, var2.o() + 0.5, var2.p() + 0.5, var4));
         }
      }
   }

   public int a(IBlockState var1) {
      return 0;
   }

   public float a(Entity var1) {
      return this.x / 5.0F;
   }

   public MovingObjectPosition a(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      this.a((IBlockAccess)var1, var2);
      var3 = var3.b(-var2.n(), -var2.o(), -var2.p());
      var4 = var4.b(-var2.n(), -var2.o(), -var2.p());
      Vec3 var5 = var3.a(var4, this.B);
      Vec3 var6 = var3.a(var4, this.E);
      Vec3 var7 = var3.b(var4, this.C);
      Vec3 var8 = var3.b(var4, this.F);
      Vec3 var9 = var3.c(var4, this.D);
      Vec3 var10 = var3.c(var4, this.G);
      if (!this.a(var5)) {
         var5 = null;
      }

      if (!this.a(var6)) {
         var6 = null;
      }

      if (!this.b(var7)) {
         var7 = null;
      }

      if (!this.b(var8)) {
         var8 = null;
      }

      if (!this.c(var9)) {
         var9 = null;
      }

      if (!this.c(var10)) {
         var10 = null;
      }

      Vec3 var11 = null;
      if (var5 != null && (var11 == null || var3.g(var5) < var3.g(var11))) {
         var11 = var5;
      }

      if (var6 != null && (var11 == null || var3.g(var6) < var3.g(var11))) {
         var11 = var6;
      }

      if (var7 != null && (var11 == null || var3.g(var7) < var3.g(var11))) {
         var11 = var7;
      }

      if (var8 != null && (var11 == null || var3.g(var8) < var3.g(var11))) {
         var11 = var8;
      }

      if (var9 != null && (var11 == null || var3.g(var9) < var3.g(var11))) {
         var11 = var9;
      }

      if (var10 != null && (var11 == null || var3.g(var10) < var3.g(var11))) {
         var11 = var10;
      }

      if (var11 == null) {
         return null;
      } else {
         EnumFacing var12 = null;
         if (var11 == var5) {
            var12 = EnumFacing.e;
         }

         if (var11 == var6) {
            var12 = EnumFacing.f;
         }

         if (var11 == var7) {
            var12 = EnumFacing.a;
         }

         if (var11 == var8) {
            var12 = EnumFacing.b;
         }

         if (var11 == var9) {
            var12 = EnumFacing.c;
         }

         if (var11 == var10) {
            var12 = EnumFacing.d;
         }

         return new MovingObjectPosition(var11.b(var2.n(), var2.o(), var2.p()), var12, var2);
      }
   }

   private boolean a(Vec3 var1) {
      return var1 == null ? false : var1.b >= this.C && var1.b <= this.F && var1.c >= this.D && var1.c <= this.G;
   }

   private boolean b(Vec3 var1) {
      return var1 == null ? false : var1.a >= this.B && var1.a <= this.E && var1.c >= this.D && var1.c <= this.G;
   }

   private boolean c(Vec3 var1) {
      return var1 == null ? false : var1.a >= this.B && var1.a <= this.E && var1.b >= this.C && var1.b <= this.F;
   }

   public void a(World var1, BlockPos var2, Explosion var3) {
   }

   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.a;
   }

   public boolean a(World var1, BlockPos var2, EnumFacing var3, ItemStack var4) {
      return this.b(var1, var2, var3);
   }

   public boolean b(World var1, BlockPos var2, EnumFacing var3) {
      return this.d(var1, var2);
   }

   public boolean d(World var1, BlockPos var2) {
      return var1.p(var2).c().J.j();
   }

   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      return false;
   }

   public void a(World var1, BlockPos var2, Entity var3) {
   }

   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.a(var7);
   }

   public void a(World var1, BlockPos var2, EntityPlayer var3) {
   }

   public Vec3 a(World var1, BlockPos var2, Entity var3, Vec3 var4) {
      return var4;
   }

   public void a(IBlockAccess var1, BlockPos var2) {
   }

   public final double B() {
      return this.B;
   }

   public final double C() {
      return this.E;
   }

   public final double D() {
      return this.C;
   }

   public final double E() {
      return this.F;
   }

   public final double F() {
      return this.D;
   }

   public final double G() {
      return this.G;
   }

   public int H() {
      return 16777215;
   }

   public int h(IBlockState var1) {
      return 16777215;
   }

   public int a(IBlockAccess var1, BlockPos var2, int var3) {
      return 16777215;
   }

   public final int d(IBlockAccess var1, BlockPos var2) {
      return this.a(var1, var2, 0);
   }

   public int a(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return 0;
   }

   public boolean i() {
      return false;
   }

   public void a(World var1, BlockPos var2, IBlockState var3, Entity var4) {
   }

   public int b(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return 0;
   }

   public void j() {
   }

   public void a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      var2.b(StatList.ab[a(this)]);
      var2.a(0.025F);
      if (this.I() && EnchantmentHelper.e(var2)) {
         ItemStack var7 = this.i(var4);
         if (var7 != null) {
            a(var1, var3, var7);
         }
      } else {
         int var6 = EnchantmentHelper.f(var2);
         this.b(var1, var3, var4, var6);
      }
   }

   protected boolean I() {
      return this.d() && !this.A;
   }

   protected ItemStack i(IBlockState var1) {
      int var2 = 0;
      Item var3 = Item.a(this);
      if (var3 != null && var3.k()) {
         var2 = this.c(var1);
      }

      return new ItemStack(var3, 1, var2);
   }

   public int a(int var1, Random var2) {
      return this.a(var2);
   }

   public void a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
   }

   public boolean g() {
      return !this.J.a() && !this.J.d();
   }

   public Block c(String var1) {
      this.O = var1;
      return this;
   }

   public String f() {
      return StatCollector.a(this.a() + ".name");
   }

   public String a() {
      return "tile." + this.O;
   }

   public boolean a(World var1, BlockPos var2, IBlockState var3, int var4, int var5) {
      return false;
   }

   public boolean J() {
      return this.y;
   }

   protected Block K() {
      this.y = false;
      return this;
   }

   public int k() {
      return this.J.m();
   }

   public float h() {
      return this.u() ? 0.2F : 1.0F;
   }

   public void a(World var1, BlockPos var2, Entity var3, float var4) {
      var3.e(var4, 1.0F);
   }

   public void a(World var1, Entity var2) {
      var2.w = 0.0;
   }

   public Item c(World var1, BlockPos var2) {
      return Item.a(this);
   }

   public int j(World var1, BlockPos var2) {
      return this.a(var1.p(var2));
   }

   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      var3.add(new ItemStack(var1, 1, 0));
   }

   public CreativeTabs L() {
      return this.b;
   }

   public Block a(CreativeTabs var1) {
      this.b = var1;
      return this;
   }

   public void a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
   }

   public void k(World var1, BlockPos var2) {
   }

   public boolean M() {
      return false;
   }

   public boolean N() {
      return true;
   }

   public boolean a(Explosion var1) {
      return true;
   }

   public boolean b(Block var1) {
      return this == var1;
   }

   public static boolean a(Block var0, Block var1) {
      if (var0 == null || var1 == null) {
         return false;
      } else {
         return var0 == var1 ? true : var0.b(var1);
      }
   }

   public boolean O() {
      return false;
   }

   public int l(World var1, BlockPos var2) {
      return 0;
   }

   public IBlockState b(IBlockState var1) {
      return var1;
   }

   protected BlockState e() {
      return new BlockState(this);
   }

   public BlockState P() {
      return this.M;
   }

   protected final void j(IBlockState var1) {
      this.N = var1;
   }

   public final IBlockState Q() {
      return this.N;
   }

   public Block.EnumOffsetType R() {
      return Block.EnumOffsetType.a;
   }

   @Override
   public String toString() {
      return "Block{" + c.c(this) + "}";
   }

   public static void S() {
      a(0, a, new BlockAir().c("air"));
      a(1, "stone", new BlockStone().c(1.5F).b(10.0F).a(i).c("stone"));
      a(2, "grass", new BlockGrass().c(0.6F).a(h).c("grass"));
      a(3, "dirt", new BlockDirt().c(0.5F).a(g).c("dirt"));
      Block var0 = new Block(Material.e).c(2.0F).b(10.0F).a(i).c("stonebrick").a(CreativeTabs.b);
      a(4, "cobblestone", var0);
      Block var1 = new BlockPlanks().c(2.0F).b(5.0F).a(f).c("wood");
      a(5, "planks", var1);
      a(6, "sapling", new BlockSapling().c(0.0F).a(h).c("sapling"));
      a(7, "bedrock", new Block(Material.e).x().b(6000000.0F).a(i).c("bedrock").K().a(CreativeTabs.b));
      a(8, "flowing_water", new BlockDynamicLiquid(Material.h).c(100.0F).e(3).c("water").K());
      a(9, "water", new BlockStaticLiquid(Material.h).c(100.0F).e(3).c("water").K());
      a(10, "flowing_lava", new BlockDynamicLiquid(Material.i).c(100.0F).a(1.0F).c("lava").K());
      a(11, "lava", new BlockStaticLiquid(Material.i).c(100.0F).a(1.0F).c("lava").K());
      a(12, "sand", new BlockSand().c(0.5F).a(m).c("sand"));
      a(13, "gravel", new BlockGravel().c(0.6F).a(g).c("gravel"));
      a(14, "gold_ore", new BlockOre().c(3.0F).b(5.0F).a(i).c("oreGold"));
      a(15, "iron_ore", new BlockOre().c(3.0F).b(5.0F).a(i).c("oreIron"));
      a(16, "coal_ore", new BlockOre().c(3.0F).b(5.0F).a(i).c("oreCoal"));
      a(17, "log", new BlockOldLog().c("log"));
      a(18, "leaves", new BlockOldLeaf().c("leaves"));
      a(19, "sponge", new BlockSponge().c(0.6F).a(h).c("sponge"));
      a(20, "glass", new BlockGlass(Material.s, false).c(0.3F).a(k).c("glass"));
      a(21, "lapis_ore", new BlockOre().c(3.0F).b(5.0F).a(i).c("oreLapis"));
      a(22, "lapis_block", new Block(Material.f, MapColor.H).c(3.0F).b(5.0F).a(i).c("blockLapis").a(CreativeTabs.b));
      a(23, "dispenser", new BlockDispenser().c(3.5F).a(i).c("dispenser"));
      Block var2 = new BlockSandStone().a(i).c(0.8F).c("sandStone");
      a(24, "sandstone", var2);
      a(25, "noteblock", new BlockNote().c(0.8F).c("musicBlock"));
      a(26, "bed", new BlockBed().a(f).c(0.2F).c("bed").K());
      a(27, "golden_rail", new BlockRailPowered().c(0.7F).a(j).c("goldenRail"));
      a(28, "detector_rail", new BlockRailDetector().c(0.7F).a(j).c("detectorRail"));
      a(29, "sticky_piston", new BlockPistonBase(true).c("pistonStickyBase"));
      a(30, "web", new BlockWeb().e(1).c(4.0F).c("web"));
      a(31, "tallgrass", new BlockTallGrass().c(0.0F).a(h).c("tallgrass"));
      a(32, "deadbush", new BlockDeadBush().c(0.0F).a(h).c("deadbush"));
      a(33, "piston", new BlockPistonBase(false).c("pistonBase"));
      a(34, "piston_head", new BlockPistonExtension().c("pistonBase"));
      a(35, "wool", new BlockColored(Material.n).c(0.8F).a(l).c("cloth"));
      a(36, "piston_extension", new BlockPistonMoving());
      a(37, "yellow_flower", new BlockYellowFlower().c(0.0F).a(h).c("flower1"));
      a(38, "red_flower", new BlockRedFlower().c(0.0F).a(h).c("flower2"));
      Block var3 = new BlockMushroom().c(0.0F).a(h).a(0.125F).c("mushroom");
      a(39, "brown_mushroom", var3);
      Block var4 = new BlockMushroom().c(0.0F).a(h).c("mushroom");
      a(40, "red_mushroom", var4);
      a(41, "gold_block", new Block(Material.f, MapColor.F).c(3.0F).b(10.0F).a(j).c("blockGold").a(CreativeTabs.b));
      a(42, "iron_block", new Block(Material.f, MapColor.h).c(5.0F).b(10.0F).a(j).c("blockIron").a(CreativeTabs.b));
      a(43, "double_stone_slab", new BlockDoubleStoneSlab().c(2.0F).b(10.0F).a(i).c("stoneSlab"));
      a(44, "stone_slab", new BlockHalfStoneSlab().c(2.0F).b(10.0F).a(i).c("stoneSlab"));
      Block var5 = new Block(Material.e, MapColor.D).c(2.0F).b(10.0F).a(i).c("brick").a(CreativeTabs.b);
      a(45, "brick_block", var5);
      a(46, "tnt", new BlockTNT().c(0.0F).a(h).c("tnt"));
      a(47, "bookshelf", new BlockBookshelf().c(1.5F).a(f).c("bookshelf"));
      a(48, "mossy_cobblestone", new Block(Material.e).c(2.0F).b(10.0F).a(i).c("stoneMoss").a(CreativeTabs.b));
      a(49, "obsidian", new BlockObsidian().c(50.0F).b(2000.0F).a(i).c("obsidian"));
      a(50, "torch", new BlockTorch().c(0.0F).a(0.9375F).a(f).c("torch"));
      a(51, "fire", new BlockFire().c(0.0F).a(1.0F).a(l).c("fire").K());
      a(52, "mob_spawner", new BlockMobSpawner().c(5.0F).a(j).c("mobSpawner").K());
      a(53, "oak_stairs", new BlockStairs(var1.Q().a(BlockPlanks.a, BlockPlanks.EnumType.a)).c("stairsWood"));
      a(54, "chest", new BlockChest(0).c(2.5F).a(f).c("chest"));
      a(55, "redstone_wire", new BlockRedstoneWire().c(0.0F).a(e).c("redstoneDust").K());
      a(56, "diamond_ore", new BlockOre().c(3.0F).b(5.0F).a(i).c("oreDiamond"));
      a(57, "diamond_block", new Block(Material.f, MapColor.G).c(5.0F).b(10.0F).a(j).c("blockDiamond").a(CreativeTabs.b));
      a(58, "crafting_table", new BlockWorkbench().c(2.5F).a(f).c("workbench"));
      a(59, "wheat", new BlockCrops().c("crops"));
      Block var6 = new BlockFarmland().c(0.6F).a(g).c("farmland");
      a(60, "farmland", var6);
      a(61, "furnace", new BlockFurnace(false).c(3.5F).a(i).c("furnace").a(CreativeTabs.c));
      a(62, "lit_furnace", new BlockFurnace(true).c(3.5F).a(i).a(0.875F).c("furnace"));
      a(63, "standing_sign", new BlockStandingSign().c(1.0F).a(f).c("sign").K());
      a(64, "wooden_door", new BlockDoor(Material.d).c(3.0F).a(f).c("doorOak").K());
      a(65, "ladder", new BlockLadder().c(0.4F).a(o).c("ladder"));
      a(66, "rail", new BlockRail().c(0.7F).a(j).c("rail"));
      a(67, "stone_stairs", new BlockStairs(var0.Q()).c("stairsStone"));
      a(68, "wall_sign", new BlockWallSign().c(1.0F).a(f).c("sign").K());
      a(69, "lever", new BlockLever().c(0.5F).a(f).c("lever"));
      a(70, "stone_pressure_plate", new BlockPressurePlate(Material.e, BlockPressurePlate.Sensitivity.b).c(0.5F).a(i).c("pressurePlateStone"));
      a(71, "iron_door", new BlockDoor(Material.f).c(5.0F).a(j).c("doorIron").K());
      a(72, "wooden_pressure_plate", new BlockPressurePlate(Material.d, BlockPressurePlate.Sensitivity.a).c(0.5F).a(f).c("pressurePlateWood"));
      a(73, "redstone_ore", new BlockRedstoneOre(false).c(3.0F).b(5.0F).a(i).c("oreRedstone").a(CreativeTabs.b));
      a(74, "lit_redstone_ore", new BlockRedstoneOre(true).a(0.625F).c(3.0F).b(5.0F).a(i).c("oreRedstone"));
      a(75, "unlit_redstone_torch", new BlockRedstoneTorch(false).c(0.0F).a(f).c("notGate"));
      a(76, "redstone_torch", new BlockRedstoneTorch(true).c(0.0F).a(0.5F).a(f).c("notGate").a(CreativeTabs.d));
      a(77, "stone_button", new BlockButtonStone().c(0.5F).a(i).c("button"));
      a(78, "snow_layer", new BlockSnow().c(0.1F).a(n).c("snow").e(0));
      a(79, "ice", new BlockIce().c(0.5F).e(3).a(k).c("ice"));
      a(80, "snow", new BlockSnowBlock().c(0.2F).a(n).c("snow"));
      a(81, "cactus", new BlockCactus().c(0.4F).a(l).c("cactus"));
      a(82, "clay", new BlockClay().c(0.6F).a(g).c("clay"));
      a(83, "reeds", new BlockReed().c(0.0F).a(h).c("reeds").K());
      a(84, "jukebox", new BlockJukebox().c(2.0F).b(10.0F).a(i).c("jukebox"));
      a(85, "fence", new BlockFence(Material.d, BlockPlanks.EnumType.a.c()).c(2.0F).b(5.0F).a(f).c("fence"));
      Block var7 = new BlockPumpkin().c(1.0F).a(f).c("pumpkin");
      a(86, "pumpkin", var7);
      a(87, "netherrack", new BlockNetherrack().c(0.4F).a(i).c("hellrock"));
      a(88, "soul_sand", new BlockSoulSand().c(0.5F).a(m).c("hellsand"));
      a(89, "glowstone", new BlockGlowstone(Material.s).c(0.3F).a(k).a(1.0F).c("lightgem"));
      a(90, "portal", new BlockPortal().c(-1.0F).a(k).a(0.75F).c("portal"));
      a(91, "lit_pumpkin", new BlockPumpkin().c(1.0F).a(f).a(1.0F).c("litpumpkin"));
      a(92, "cake", new BlockCake().c(0.5F).a(l).c("cake").K());
      a(93, "unpowered_repeater", new BlockRedstoneRepeater(false).c(0.0F).a(f).c("diode").K());
      a(94, "powered_repeater", new BlockRedstoneRepeater(true).c(0.0F).a(f).c("diode").K());
      a(95, "stained_glass", new BlockStainedGlass(Material.s).c(0.3F).a(k).c("stainedGlass"));
      a(96, "trapdoor", new BlockTrapDoor(Material.d).c(3.0F).a(f).c("trapdoor").K());
      a(97, "monster_egg", new BlockSilverfish().c(0.75F).c("monsterStoneEgg"));
      Block var8 = new BlockStoneBrick().c(1.5F).b(10.0F).a(i).c("stonebricksmooth");
      a(98, "stonebrick", var8);
      a(99, "brown_mushroom_block", new BlockHugeMushroom(Material.d, MapColor.l, var3).c(0.2F).a(f).c("mushroom"));
      a(100, "red_mushroom_block", new BlockHugeMushroom(Material.d, MapColor.D, var4).c(0.2F).a(f).c("mushroom"));
      a(101, "iron_bars", new BlockPane(Material.f, true).c(5.0F).b(10.0F).a(j).c("fenceIron"));
      a(102, "glass_pane", new BlockPane(Material.s, false).c(0.3F).a(k).c("thinGlass"));
      Block var9 = new BlockMelon().c(1.0F).a(f).c("melon");
      a(103, "melon_block", var9);
      a(104, "pumpkin_stem", new BlockStem(var7).c(0.0F).a(f).c("pumpkinStem"));
      a(105, "melon_stem", new BlockStem(var9).c(0.0F).a(f).c("pumpkinStem"));
      a(106, "vine", new BlockVine().c(0.2F).a(h).c("vine"));
      a(107, "fence_gate", new BlockFenceGate(BlockPlanks.EnumType.a).c(2.0F).b(5.0F).a(f).c("fenceGate"));
      a(108, "brick_stairs", new BlockStairs(var5.Q()).c("stairsBrick"));
      a(109, "stone_brick_stairs", new BlockStairs(var8.Q().a(BlockStoneBrick.a, BlockStoneBrick.EnumType.a)).c("stairsStoneBrickSmooth"));
      a(110, "mycelium", new BlockMycelium().c(0.6F).a(h).c("mycel"));
      a(111, "waterlily", new BlockLilyPad().c(0.0F).a(h).c("waterlily"));
      Block var10 = new BlockNetherBrick().c(2.0F).b(10.0F).a(i).c("netherBrick").a(CreativeTabs.b);
      a(112, "nether_brick", var10);
      a(113, "nether_brick_fence", new BlockFence(Material.e, MapColor.K).c(2.0F).b(10.0F).a(i).c("netherFence"));
      a(114, "nether_brick_stairs", new BlockStairs(var10.Q()).c("stairsNetherBrick"));
      a(115, "nether_wart", new BlockNetherWart().c("netherStalk"));
      a(116, "enchanting_table", new BlockEnchantmentTable().c(5.0F).b(2000.0F).c("enchantmentTable"));
      a(117, "brewing_stand", new BlockBrewingStand().c(0.5F).a(0.125F).c("brewingStand"));
      a(118, "cauldron", new BlockCauldron().c(2.0F).c("cauldron"));
      a(119, "end_portal", new BlockEndPortal(Material.E).c(-1.0F).b(6000000.0F));
      a(120, "end_portal_frame", new BlockEndPortalFrame().a(k).a(0.125F).c(-1.0F).c("endPortalFrame").b(6000000.0F).a(CreativeTabs.c));
      a(121, "end_stone", new Block(Material.e, MapColor.d).c(3.0F).b(15.0F).a(i).c("whiteStone").a(CreativeTabs.b));
      a(122, "dragon_egg", new BlockDragonEgg().c(3.0F).b(15.0F).a(i).a(0.125F).c("dragonEgg"));
      a(123, "redstone_lamp", new BlockRedstoneLight(false).c(0.3F).a(k).c("redstoneLight").a(CreativeTabs.d));
      a(124, "lit_redstone_lamp", new BlockRedstoneLight(true).c(0.3F).a(k).c("redstoneLight"));
      a(125, "double_wooden_slab", new BlockDoubleWoodSlab().c(2.0F).b(5.0F).a(f).c("woodSlab"));
      a(126, "wooden_slab", new BlockHalfWoodSlab().c(2.0F).b(5.0F).a(f).c("woodSlab"));
      a(127, "cocoa", new BlockCocoa().c(0.2F).b(5.0F).a(f).c("cocoa"));
      a(128, "sandstone_stairs", new BlockStairs(var2.Q().a(BlockSandStone.a, BlockSandStone.EnumType.c)).c("stairsSandStone"));
      a(129, "emerald_ore", new BlockOre().c(3.0F).b(5.0F).a(i).c("oreEmerald"));
      a(130, "ender_chest", new BlockEnderChest().c(22.5F).b(1000.0F).a(i).c("enderChest").a(0.5F));
      a(131, "tripwire_hook", new BlockTripWireHook().c("tripWireSource"));
      a(132, "tripwire", new BlockTripWire().c("tripWire"));
      a(133, "emerald_block", new Block(Material.f, MapColor.I).c(5.0F).b(10.0F).a(j).c("blockEmerald").a(CreativeTabs.b));
      a(134, "spruce_stairs", new BlockStairs(var1.Q().a(BlockPlanks.a, BlockPlanks.EnumType.b)).c("stairsWoodSpruce"));
      a(135, "birch_stairs", new BlockStairs(var1.Q().a(BlockPlanks.a, BlockPlanks.EnumType.c)).c("stairsWoodBirch"));
      a(136, "jungle_stairs", new BlockStairs(var1.Q().a(BlockPlanks.a, BlockPlanks.EnumType.d)).c("stairsWoodJungle"));
      a(137, "command_block", new BlockCommandBlock().x().b(6000000.0F).c("commandBlock"));
      a(138, "beacon", new BlockBeacon().c("beacon").a(1.0F));
      a(139, "cobblestone_wall", new BlockWall(var0).c("cobbleWall"));
      a(140, "flower_pot", new BlockFlowerPot().c(0.0F).a(e).c("flowerPot"));
      a(141, "carrots", new BlockCarrot().c("carrots"));
      a(142, "potatoes", new BlockPotato().c("potatoes"));
      a(143, "wooden_button", new BlockButtonWood().c(0.5F).a(f).c("button"));
      a(144, "skull", new BlockSkull().c(1.0F).a(i).c("skull"));
      a(145, "anvil", new BlockAnvil().c(5.0F).a(p).b(2000.0F).c("anvil"));
      a(146, "trapped_chest", new BlockChest(1).c(2.5F).a(f).c("chestTrap"));
      a(147, "light_weighted_pressure_plate", new BlockPressurePlateWeighted(Material.f, 15, MapColor.F).c(0.5F).a(f).c("weightedPlate_light"));
      a(148, "heavy_weighted_pressure_plate", new BlockPressurePlateWeighted(Material.f, 150).c(0.5F).a(f).c("weightedPlate_heavy"));
      a(149, "unpowered_comparator", new BlockRedstoneComparator(false).c(0.0F).a(f).c("comparator").K());
      a(150, "powered_comparator", new BlockRedstoneComparator(true).c(0.0F).a(0.625F).a(f).c("comparator").K());
      a(151, "daylight_detector", new BlockDaylightDetector(false));
      a(152, "redstone_block", new BlockCompressedPowered(Material.f, MapColor.f).c(5.0F).b(10.0F).a(j).c("blockRedstone").a(CreativeTabs.d));
      a(153, "quartz_ore", new BlockOre(MapColor.K).c(3.0F).b(5.0F).a(i).c("netherquartz"));
      a(154, "hopper", new BlockHopper().c(3.0F).b(8.0F).a(j).c("hopper"));
      Block var11 = new BlockQuartz().a(i).c(0.8F).c("quartzBlock");
      a(155, "quartz_block", var11);
      a(156, "quartz_stairs", new BlockStairs(var11.Q().a(BlockQuartz.a, BlockQuartz.EnumType.a)).c("stairsQuartz"));
      a(157, "activator_rail", new BlockRailPowered().c(0.7F).a(j).c("activatorRail"));
      a(158, "dropper", new BlockDropper().c(3.5F).a(i).c("dropper"));
      a(159, "stained_hardened_clay", new BlockColored(Material.e).c(1.25F).b(7.0F).a(i).c("clayHardenedStained"));
      a(160, "stained_glass_pane", new BlockStainedGlassPane().c(0.3F).a(k).c("thinStainedGlass"));
      a(161, "leaves2", new BlockNewLeaf().c("leaves"));
      a(162, "log2", new BlockNewLog().c("log"));
      a(163, "acacia_stairs", new BlockStairs(var1.Q().a(BlockPlanks.a, BlockPlanks.EnumType.e)).c("stairsWoodAcacia"));
      a(164, "dark_oak_stairs", new BlockStairs(var1.Q().a(BlockPlanks.a, BlockPlanks.EnumType.f)).c("stairsWoodDarkOak"));
      a(165, "slime", new BlockSlime().c("slime").a(q));
      a(166, "barrier", new BlockBarrier().c("barrier"));
      a(167, "iron_trapdoor", new BlockTrapDoor(Material.f).c(5.0F).a(j).c("ironTrapdoor").K());
      a(168, "prismarine", new BlockPrismarine().c(1.5F).b(10.0F).a(i).c("prismarine"));
      a(169, "sea_lantern", new BlockSeaLantern(Material.s).c(0.3F).a(k).a(1.0F).c("seaLantern"));
      a(170, "hay_block", new BlockHay().c(0.5F).a(h).c("hayBlock").a(CreativeTabs.b));
      a(171, "carpet", new BlockCarpet().c(0.1F).a(l).c("woolCarpet").e(0));
      a(172, "hardened_clay", new BlockHardenedClay().c(1.25F).b(7.0F).a(i).c("clayHardened"));
      a(173, "coal_block", new Block(Material.e, MapColor.E).c(5.0F).b(10.0F).a(i).c("blockCoal").a(CreativeTabs.b));
      a(174, "packed_ice", new BlockPackedIce().c(0.5F).a(k).c("icePacked"));
      a(175, "double_plant", new BlockDoublePlant());
      a(176, "standing_banner", new BlockBanner.BlockBannerStanding().c(1.0F).a(f).c("banner").K());
      a(177, "wall_banner", new BlockBanner.BlockBannerHanging().c(1.0F).a(f).c("banner").K());
      a(178, "daylight_detector_inverted", new BlockDaylightDetector(true));
      Block var12 = new BlockRedSandstone().a(i).c(0.8F).c("redSandStone");
      a(179, "red_sandstone", var12);
      a(180, "red_sandstone_stairs", new BlockStairs(var12.Q().a(BlockRedSandstone.a, BlockRedSandstone.EnumType.c)).c("stairsRedSandStone"));
      a(181, "double_stone_slab2", new BlockDoubleStoneSlabNew().c(2.0F).b(10.0F).a(i).c("stoneSlab2"));
      a(182, "stone_slab2", new BlockHalfStoneSlabNew().c(2.0F).b(10.0F).a(i).c("stoneSlab2"));
      a(183, "spruce_fence_gate", new BlockFenceGate(BlockPlanks.EnumType.b).c(2.0F).b(5.0F).a(f).c("spruceFenceGate"));
      a(184, "birch_fence_gate", new BlockFenceGate(BlockPlanks.EnumType.c).c(2.0F).b(5.0F).a(f).c("birchFenceGate"));
      a(185, "jungle_fence_gate", new BlockFenceGate(BlockPlanks.EnumType.d).c(2.0F).b(5.0F).a(f).c("jungleFenceGate"));
      a(186, "dark_oak_fence_gate", new BlockFenceGate(BlockPlanks.EnumType.f).c(2.0F).b(5.0F).a(f).c("darkOakFenceGate"));
      a(187, "acacia_fence_gate", new BlockFenceGate(BlockPlanks.EnumType.e).c(2.0F).b(5.0F).a(f).c("acaciaFenceGate"));
      a(188, "spruce_fence", new BlockFence(Material.d, BlockPlanks.EnumType.b.c()).c(2.0F).b(5.0F).a(f).c("spruceFence"));
      a(189, "birch_fence", new BlockFence(Material.d, BlockPlanks.EnumType.c.c()).c(2.0F).b(5.0F).a(f).c("birchFence"));
      a(190, "jungle_fence", new BlockFence(Material.d, BlockPlanks.EnumType.d.c()).c(2.0F).b(5.0F).a(f).c("jungleFence"));
      a(191, "dark_oak_fence", new BlockFence(Material.d, BlockPlanks.EnumType.f.c()).c(2.0F).b(5.0F).a(f).c("darkOakFence"));
      a(192, "acacia_fence", new BlockFence(Material.d, BlockPlanks.EnumType.e.c()).c(2.0F).b(5.0F).a(f).c("acaciaFence"));
      a(193, "spruce_door", new BlockDoor(Material.d).c(3.0F).a(f).c("doorSpruce").K());
      a(194, "birch_door", new BlockDoor(Material.d).c(3.0F).a(f).c("doorBirch").K());
      a(195, "jungle_door", new BlockDoor(Material.d).c(3.0F).a(f).c("doorJungle").K());
      a(196, "acacia_door", new BlockDoor(Material.d).c(3.0F).a(f).c("doorAcacia").K());
      a(197, "dark_oak_door", new BlockDoor(Material.d).c(3.0F).a(f).c("doorDarkOak").K());
      c.a();

      for (Block var14 : c) {
         if (var14.J == Material.a) {
            var14.v = false;
         } else {
            boolean var15 = false;
            boolean var16 = var14 instanceof BlockStairs;
            boolean var17 = var14 instanceof BlockSlab;
            boolean var18 = var14 == var6;
            boolean var19 = var14.t;
            boolean var20 = var14.s == 0;
            if (var16 || var17 || var18 || var19 || var20) {
               var15 = true;
            }

            var14.v = var15;
         }
      }

      for (Block var22 : c) {
         for (IBlockState var24 : var22.P().a()) {
            int var25 = c.b(var22) << 4 | var22.c(var24);
            d.a(var24, var25);
         }
      }
   }

   private static void a(int var0, ResourceLocation var1, Block var2) {
      c.a(var0, var1, var2);
   }

   private static void a(int var0, String var1, Block var2) {
      a(var0, new ResourceLocation(var1), var2);
   }

   public static enum EnumOffsetType {
      a,
      b,
      c;
   }

   public static class SoundType {
      public final String a;
      public final float b;
      public final float c;

      public SoundType(String var1, float var2, float var3) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
      }

      public float d() {
         return this.b;
      }

      public float e() {
         return this.c;
      }

      public String a() {
         return "dig." + this.a;
      }

      public String c() {
         return "step." + this.a;
      }

      public String b() {
         return this.a();
      }
   }
}
