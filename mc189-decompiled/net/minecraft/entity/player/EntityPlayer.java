package net.minecraft.entity.player;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

public abstract class EntityPlayer extends EntityLivingBase {
   public InventoryPlayer bi = new InventoryPlayer(this);
   private InventoryEnderChest a = new InventoryEnderChest();
   public Container bj;
   public Container bk;
   protected FoodStats bl = new FoodStats();
   protected int bm;
   public float bn;
   public float bo;
   public int bp;
   public double bq;
   public double br;
   public double bs;
   public double bt;
   public double bu;
   public double bv;
   protected boolean bw;
   public BlockPos bx;
   private int b;
   public float by;
   public float bZ;
   public float bz;
   private BlockPos c;
   private boolean d;
   private BlockPos e;
   public PlayerCapabilities bA = new PlayerCapabilities();
   public int bB;
   public int bC;
   public float bD;
   private int f;
   private ItemStack g;
   private int h;
   protected float bE = 0.1F;
   protected float bF = 0.02F;
   private int i;
   private final GameProfile bH;
   private boolean bI = false;
   public EntityFishHook bG;

   public EntityPlayer(World var1, GameProfile var2) {
      super(var1);
      this.aq = a(var2);
      this.bH = var2;
      this.bj = new ContainerPlayer(this.bi, !var1.D, this);
      this.bk = this.bj;
      BlockPos var3 = var1.M();
      this.b(var3.n() + 0.5, var3.o() + 1, var3.p() + 0.5, 0.0F, 0.0F);
      this.aV = 180.0F;
      this.X = 20;
   }

   @Override
   protected void aX() {
      super.aX();
      this.by().b(SharedMonsterAttributes.e).a(1.0);
      this.a(SharedMonsterAttributes.d).a(0.1F);
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(16, Byte.valueOf((byte)0));
      this.ac.a(17, 0.0F);
      this.ac.a(18, Integer.valueOf(0));
      this.ac.a(10, Byte.valueOf((byte)0));
   }

   public ItemStack bQ() {
      return this.g;
   }

   public int bR() {
      return this.h;
   }

   public boolean bS() {
      return this.g != null;
   }

   public int bT() {
      return this.bS() ? this.g.l() - this.h : 0;
   }

   public void bU() {
      if (this.g != null) {
         this.g.b(this.o, this, this.h);
      }

      this.bV();
   }

   public void bV() {
      this.g = null;
      this.h = 0;
      if (!this.o.D) {
         this.f(false);
      }
   }

   public boolean bW() {
      return this.bS() && this.g.b().e(this.g) == EnumAction.d;
   }

   @Override
   public void t_() {
      this.T = this.v();
      if (this.v()) {
         this.C = false;
      }

      if (this.g != null) {
         ItemStack var1 = this.bi.h();
         if (var1 == this.g) {
            if (this.h <= 25 && this.h % 4 == 0) {
               this.b(var1, 5);
            }

            if (--this.h == 0 && !this.o.D) {
               this.s();
            }
         } else {
            this.bV();
         }
      }

      if (this.bp > 0) {
         this.bp--;
      }

      if (this.bJ()) {
         this.b++;
         if (this.b > 100) {
            this.b = 100;
         }

         if (!this.o.D) {
            if (!this.p()) {
               this.a(true, true, false);
            } else if (this.o.w()) {
               this.a(false, true, true);
            }
         }
      } else if (this.b > 0) {
         this.b++;
         if (this.b >= 110) {
            this.b = 0;
         }
      }

      super.t_();
      if (!this.o.D && this.bk != null && !this.bk.a(this)) {
         this.n();
         this.bk = this.bj;
      }

      if (this.at() && this.bA.a) {
         this.N();
      }

      this.bq = this.bt;
      this.br = this.bu;
      this.bs = this.bv;
      double var14 = this.s - this.bt;
      double var3 = this.t - this.bu;
      double var5 = this.u - this.bv;
      double var7 = 10.0;
      if (var14 > var7) {
         this.bq = this.bt = this.s;
      }

      if (var5 > var7) {
         this.bs = this.bv = this.u;
      }

      if (var3 > var7) {
         this.br = this.bu = this.t;
      }

      if (var14 < -var7) {
         this.bq = this.bt = this.s;
      }

      if (var5 < -var7) {
         this.bs = this.bv = this.u;
      }

      if (var3 < -var7) {
         this.br = this.bu = this.t;
      }

      this.bt += var14 * 0.25;
      this.bv += var5 * 0.25;
      this.bu += var3 * 0.25;
      if (this.m == null) {
         this.e = null;
      }

      if (!this.o.D) {
         this.bl.a(this);
         this.b(StatList.g);
         if (this.ai()) {
            this.b(StatList.h);
         }
      }

      int var9 = 29999999;
      double var10 = MathHelper.a(this.s, -2.9999999E7, 2.9999999E7);
      double var12 = MathHelper.a(this.u, -2.9999999E7, 2.9999999E7);
      if (var10 != this.s || var12 != this.u) {
         this.b(var10, this.t, var12);
      }
   }

   @Override
   public int L() {
      return this.bA.a ? 0 : 80;
   }

   @Override
   protected String P() {
      return "game.player.swim";
   }

   @Override
   protected String aa() {
      return "game.player.swim.splash";
   }

   @Override
   public int aq() {
      return 10;
   }

   @Override
   public void a(String var1, float var2, float var3) {
      this.o.a(this, var1, var2, var3);
   }

   protected void b(ItemStack var1, int var2) {
      if (var1.m() == EnumAction.c) {
         this.a("random.drink", 0.5F, this.o.s.nextFloat() * 0.1F + 0.9F);
      }

      if (var1.m() == EnumAction.b) {
         for (int var3 = 0; var3 < var2; var3++) {
            Vec3 var4 = new Vec3((this.V.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
            var4 = var4.a(-this.z * (float) Math.PI / 180.0F);
            var4 = var4.b(-this.y * (float) Math.PI / 180.0F);
            double var5 = -this.V.nextFloat() * 0.6 - 0.3;
            Vec3 var7 = new Vec3((this.V.nextFloat() - 0.5) * 0.3, var5, 0.6);
            var7 = var7.a(-this.z * (float) Math.PI / 180.0F);
            var7 = var7.b(-this.y * (float) Math.PI / 180.0F);
            var7 = var7.b(this.s, this.t + this.aS(), this.u);
            if (var1.f()) {
               this.o.a(EnumParticleTypes.K, var7.a, var7.b, var7.c, var4.a, var4.b + 0.05, var4.c, Item.b(var1.b()), var1.i());
            } else {
               this.o.a(EnumParticleTypes.K, var7.a, var7.b, var7.c, var4.a, var4.b + 0.05, var4.c, Item.b(var1.b()));
            }
         }

         this.a("random.eat", 0.5F + 0.5F * this.V.nextInt(2), (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F);
      }
   }

   protected void s() {
      if (this.g != null) {
         this.b(this.g, 16);
         int var1 = this.g.b;
         ItemStack var2 = this.g.b(this.o, this);
         if (var2 != this.g || var2 != null && var2.b != var1) {
            this.bi.a[this.bi.c] = var2;
            if (var2.b == 0) {
               this.bi.a[this.bi.c] = null;
            }
         }

         this.bV();
      }
   }

   @Override
   public void a(byte var1) {
      if (var1 == 9) {
         this.s();
      } else if (var1 == 23) {
         this.bI = false;
      } else if (var1 == 22) {
         this.bI = true;
      } else {
         super.a(var1);
      }
   }

   @Override
   protected boolean bD() {
      return this.bn() <= 0.0F || this.bJ();
   }

   protected void n() {
      this.bk = this.bj;
   }

   @Override
   public void ak() {
      if (!this.o.D && this.av()) {
         this.a(null);
         this.c(false);
      } else {
         double var1 = this.s;
         double var3 = this.t;
         double var5 = this.u;
         float var7 = this.y;
         float var8 = this.z;
         super.ak();
         this.bn = this.bo;
         this.bo = 0.0F;
         this.l(this.s - var1, this.t - var3, this.u - var5);
         if (this.m instanceof EntityPig) {
            this.z = var8;
            this.y = var7;
            this.aI = ((EntityPig)this.m).aI;
         }
      }
   }

   @Override
   public void I() {
      this.a(0.6F, 1.8F);
      super.I();
      this.i(this.bu());
      this.ax = 0;
   }

   @Override
   protected void bK() {
      super.bK();
      this.bx();
      this.aK = this.y;
   }

   @Override
   public void m() {
      if (this.bm > 0) {
         this.bm--;
      }

      if (this.o.aa() == EnumDifficulty.a && this.o.Q().b("naturalRegeneration")) {
         if (this.bn() < this.bu() && this.W % 20 == 0) {
            this.h(1.0F);
         }

         if (this.bl.c() && this.W % 10 == 0) {
            this.bl.a(this.bl.a() + 1);
         }
      }

      this.bi.k();
      this.bn = this.bo;
      super.m();
      IAttributeInstance var1 = this.a(SharedMonsterAttributes.d);
      if (!this.o.D) {
         var1.a((double)this.bA.b());
      }

      this.aM = this.bF;
      if (this.aw()) {
         this.aM = (float)(this.aM + this.bF * 0.3);
      }

      this.k((float)var1.e());
      float var2 = MathHelper.a(this.v * this.v + this.x * this.x);
      float var3 = (float)(Math.atan(-this.w * 0.2F) * 15.0);
      if (var2 > 0.1F) {
         var2 = 0.1F;
      }

      if (!this.C || this.bn() <= 0.0F) {
         var2 = 0.0F;
      }

      if (this.C || this.bn() <= 0.0F) {
         var3 = 0.0F;
      }

      this.bo = this.bo + (var2 - this.bo) * 0.4F;
      this.aF = this.aF + (var3 - this.aF) * 0.8F;
      if (this.bn() > 0.0F && !this.v()) {
         Object var4 = null;
         if (this.m != null && !this.m.I) {
            var4 = this.aR().a(this.m.aR()).b(1.0, 0.0, 1.0);
         } else {
            var4 = this.aR().b(1.0, 0.5, 1.0);
         }

         List var5 = this.o.b(this, (AxisAlignedBB)var4);

         for (int var6 = 0; var6 < var5.size(); var6++) {
            Entity var7 = (Entity)var5.get(var6);
            if (!var7.I) {
               this.d(var7);
            }
         }
      }
   }

   private void d(Entity var1) {
      var1.d(this);
   }

   public int bX() {
      return this.ac.c(18);
   }

   public void r(int var1) {
      this.ac.b(18, var1);
   }

   public void s(int var1) {
      int var2 = this.bX();
      this.ac.b(18, var2 + var1);
   }

   @Override
   public void a(DamageSource var1) {
      super.a(var1);
      this.a(0.2F, 0.2F);
      this.b(this.s, this.t, this.u);
      this.w = 0.1F;
      if (this.e_().equals("Notch")) {
         this.a(new ItemStack(Items.e, 1), true, false);
      }

      if (!this.o.Q().b("keepInventory")) {
         this.bi.n();
      }

      if (var1 != null) {
         this.v = -MathHelper.b((this.aw + this.y) * (float) Math.PI / 180.0F) * 0.1F;
         this.x = -MathHelper.a((this.aw + this.y) * (float) Math.PI / 180.0F) * 0.1F;
      } else {
         this.v = this.x = 0.0;
      }

      this.b(StatList.y);
      this.a(StatList.h);
   }

   @Override
   protected String bo() {
      return "game.player.hurt";
   }

   @Override
   protected String bp() {
      return "game.player.die";
   }

   @Override
   public void b(Entity var1, int var2) {
      this.s(var2);
      Collection var3 = this.cp().a(IScoreObjectiveCriteria.f);
      if (var1 instanceof EntityPlayer) {
         this.b(StatList.B);
         var3.addAll(this.cp().a(IScoreObjectiveCriteria.e));
         var3.addAll(this.e(var1));
      } else {
         this.b(StatList.z);
      }

      for (ScoreObjective var5 : var3) {
         Score var6 = this.cp().c(this.e_(), var5);
         var6.a();
      }
   }

   private Collection<ScoreObjective> e(Entity var1) {
      ScorePlayerTeam var2 = this.cp().h(this.e_());
      if (var2 != null) {
         int var3 = var2.l().b();
         if (var3 >= 0 && var3 < IScoreObjectiveCriteria.i.length) {
            for (ScoreObjective var5 : this.cp().a(IScoreObjectiveCriteria.i[var3])) {
               Score var6 = this.cp().c(var1.e_(), var5);
               var6.a();
            }
         }
      }

      ScorePlayerTeam var7 = this.cp().h(var1.e_());
      if (var7 != null) {
         int var8 = var7.l().b();
         if (var8 >= 0 && var8 < IScoreObjectiveCriteria.h.length) {
            return this.cp().a(IScoreObjectiveCriteria.h[var8]);
         }
      }

      return Lists.newArrayList();
   }

   public EntityItem a(boolean var1) {
      return this.a(this.bi.a(this.bi.c, var1 && this.bi.h() != null ? this.bi.h().b : 1), false, true);
   }

   public EntityItem a(ItemStack var1, boolean var2) {
      return this.a(var1, false, false);
   }

   public EntityItem a(ItemStack var1, boolean var2, boolean var3) {
      if (var1 == null) {
         return null;
      } else if (var1.b == 0) {
         return null;
      } else {
         double var4 = this.t - 0.3F + this.aS();
         EntityItem var6 = new EntityItem(this.o, this.s, var4, this.u, var1);
         var6.a(40);
         if (var3) {
            var6.c(this.e_());
         }

         if (var2) {
            float var7 = this.V.nextFloat() * 0.5F;
            float var8 = this.V.nextFloat() * (float) Math.PI * 2.0F;
            var6.v = -MathHelper.a(var8) * var7;
            var6.x = MathHelper.b(var8) * var7;
            var6.w = 0.2F;
         } else {
            float var9 = 0.3F;
            var6.v = -MathHelper.a(this.y / 180.0F * (float) Math.PI) * MathHelper.b(this.z / 180.0F * (float) Math.PI) * var9;
            var6.x = MathHelper.b(this.y / 180.0F * (float) Math.PI) * MathHelper.b(this.z / 180.0F * (float) Math.PI) * var9;
            var6.w = -MathHelper.a(this.z / 180.0F * (float) Math.PI) * var9 + 0.1F;
            float var11 = this.V.nextFloat() * (float) Math.PI * 2.0F;
            var9 = 0.02F * this.V.nextFloat();
            var6.v = var6.v + Math.cos(var11) * var9;
            var6.w = var6.w + (this.V.nextFloat() - this.V.nextFloat()) * 0.1F;
            var6.x = var6.x + Math.sin(var11) * var9;
         }

         this.a(var6);
         if (var3) {
            this.b(StatList.v);
         }

         return var6;
      }
   }

   protected void a(EntityItem var1) {
      this.o.d(var1);
   }

   public float a(Block var1) {
      float var2 = this.bi.a(var1);
      if (var2 > 1.0F) {
         int var3 = EnchantmentHelper.c(this);
         ItemStack var4 = this.bi.h();
         if (var3 > 0 && var4 != null) {
            var2 += var3 * var3 + 1;
         }
      }

      if (this.a(Potion.e)) {
         var2 *= 1.0F + (this.b(Potion.e).c() + 1) * 0.2F;
      }

      if (this.a(Potion.f)) {
         float var5 = 1.0F;
         switch (this.b(Potion.f).c()) {
            case 0:
               var5 = 0.3F;
               break;
            case 1:
               var5 = 0.09F;
               break;
            case 2:
               var5 = 0.0027F;
               break;
            case 3:
            default:
               var5 = 8.1E-4F;
         }

         var2 *= var5;
      }

      if (this.a(Material.h) && !EnchantmentHelper.j(this)) {
         var2 /= 5.0F;
      }

      if (!this.C) {
         var2 /= 5.0F;
      }

      return var2;
   }

   public boolean b(Block var1) {
      return this.bi.b(var1);
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.aq = a(this.bH);
      NBTTagList var2 = var1.c("Inventory", 10);
      this.bi.b(var2);
      this.bi.c = var1.f("SelectedItemSlot");
      this.bw = var1.n("Sleeping");
      this.b = var1.e("SleepTimer");
      this.bD = var1.h("XpP");
      this.bB = var1.f("XpLevel");
      this.bC = var1.f("XpTotal");
      this.f = var1.f("XpSeed");
      if (this.f == 0) {
         this.f = this.V.nextInt();
      }

      this.r(var1.f("Score"));
      if (this.bw) {
         this.bx = new BlockPos(this);
         this.a(true, true, false);
      }

      if (var1.b("SpawnX", 99) && var1.b("SpawnY", 99) && var1.b("SpawnZ", 99)) {
         this.c = new BlockPos(var1.f("SpawnX"), var1.f("SpawnY"), var1.f("SpawnZ"));
         this.d = var1.n("SpawnForced");
      }

      this.bl.a(var1);
      this.bA.b(var1);
      if (var1.b("EnderItems", 9)) {
         NBTTagList var3 = var1.c("EnderItems", 10);
         this.a.a(var3);
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("Inventory", this.bi.a(new NBTTagList()));
      var1.a("SelectedItemSlot", this.bi.c);
      var1.a("Sleeping", this.bw);
      var1.a("SleepTimer", (short)this.b);
      var1.a("XpP", this.bD);
      var1.a("XpLevel", this.bB);
      var1.a("XpTotal", this.bC);
      var1.a("XpSeed", this.f);
      var1.a("Score", this.bX());
      if (this.c != null) {
         var1.a("SpawnX", this.c.n());
         var1.a("SpawnY", this.c.o());
         var1.a("SpawnZ", this.c.p());
         var1.a("SpawnForced", this.d);
      }

      this.bl.b(var1);
      this.bA.a(var1);
      var1.a("EnderItems", this.a.h());
      ItemStack var2 = this.bi.h();
      if (var2 != null && var2.b() != null) {
         var1.a("SelectedItem", var2.b(new NBTTagCompound()));
      }
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else if (this.bA.a && !var1.g()) {
         return false;
      } else {
         this.aQ = 0;
         if (this.bn() <= 0.0F) {
            return false;
         } else {
            if (this.bJ() && !this.o.D) {
               this.a(true, true, false);
            }

            if (var1.r()) {
               if (this.o.aa() == EnumDifficulty.a) {
                  var2 = 0.0F;
               }

               if (this.o.aa() == EnumDifficulty.b) {
                  var2 = var2 / 2.0F + 1.0F;
               }

               if (this.o.aa() == EnumDifficulty.d) {
                  var2 = var2 * 3.0F / 2.0F;
               }
            }

            if (var2 == 0.0F) {
               return false;
            } else {
               Entity var3 = var1.j();
               if (var3 instanceof EntityArrow && ((EntityArrow)var3).c != null) {
                  var3 = ((EntityArrow)var3).c;
               }

               return super.a(var1, var2);
            }
         }
      }
   }

   public boolean a(EntityPlayer var1) {
      Team var2 = this.bO();
      Team var3 = var1.bO();
      if (var2 == null) {
         return true;
      } else {
         return !var2.a(var3) ? true : var2.g();
      }
   }

   @Override
   protected void j(float var1) {
      this.bi.a(var1);
   }

   @Override
   public int br() {
      return this.bi.m();
   }

   public float bY() {
      int var1 = 0;

      for (ItemStack var5 : this.bi.b) {
         if (var5 != null) {
            var1++;
         }
      }

      return (float)var1 / this.bi.b.length;
   }

   @Override
   protected void d(DamageSource var1, float var2) {
      if (!this.b(var1)) {
         if (!var1.e() && this.bW() && var2 > 0.0F) {
            var2 = (1.0F + var2) * 0.5F;
         }

         var2 = this.b(var1, var2);
         var2 = this.c(var1, var2);
         float var7 = Math.max(var2 - this.bN(), 0.0F);
         this.m(this.bN() - (var2 - var7));
         if (var7 != 0.0F) {
            this.a(var1.f());
            float var4 = this.bn();
            this.i(this.bn() - var7);
            this.bs().a(var1, var4, var7);
            if (var7 < 3.4028235E37F) {
               this.a(StatList.x, Math.round(var7 * 10.0F));
            }
         }
      }
   }

   public void a(TileEntitySign var1) {
   }

   public void a(CommandBlockLogic var1) {
   }

   public void a(IMerchant var1) {
   }

   public void a(IInventory var1) {
   }

   public void a(EntityHorse var1, IInventory var2) {
   }

   public void a(IInteractionObject var1) {
   }

   public void a(ItemStack var1) {
   }

   public boolean u(Entity var1) {
      if (this.v()) {
         if (var1 instanceof IInventory) {
            this.a((IInventory)var1);
         }

         return false;
      } else {
         ItemStack var2 = this.bZ();
         ItemStack var3 = var2 != null ? var2.k() : null;
         if (!var1.e(this)) {
            if (var2 != null && var1 instanceof EntityLivingBase) {
               if (this.bA.d) {
                  var2 = var3;
               }

               if (var2.a(this, (EntityLivingBase)var1)) {
                  if (var2.b <= 0 && !this.bA.d) {
                     this.ca();
                  }

                  return true;
               }
            }

            return false;
         } else {
            if (var2 != null && var2 == this.bZ()) {
               if (var2.b <= 0 && !this.bA.d) {
                  this.ca();
               } else if (var2.b < var3.b && this.bA.d) {
                  var2.b = var3.b;
               }
            }

            return true;
         }
      }
   }

   public ItemStack bZ() {
      return this.bi.h();
   }

   public void ca() {
      this.bi.a(this.bi.c, null);
   }

   @Override
   public double am() {
      return -0.35;
   }

   public void f(Entity var1) {
      if (var1.aD()) {
         if (!var1.l(this)) {
            float var2 = (float)this.a(SharedMonsterAttributes.e).e();
            int var3 = 0;
            float var4 = 0.0F;
            if (var1 instanceof EntityLivingBase) {
               var4 = EnchantmentHelper.a(this.bA(), ((EntityLivingBase)var1).bz());
            } else {
               var4 = EnchantmentHelper.a(this.bA(), EnumCreatureAttribute.a);
            }

            var3 += EnchantmentHelper.a((EntityLivingBase)this);
            if (this.aw()) {
               var3++;
            }

            if (var2 > 0.0F || var4 > 0.0F) {
               boolean var5 = this.O > 0.0F && !this.C && !this.k_() && !this.V() && !this.a(Potion.q) && this.m == null && var1 instanceof EntityLivingBase;
               if (var5 && var2 > 0.0F) {
                  var2 *= 1.5F;
               }

               var2 += var4;
               boolean var6 = false;
               int var7 = EnchantmentHelper.b((EntityLivingBase)this);
               if (var1 instanceof EntityLivingBase && var7 > 0 && !var1.at()) {
                  var6 = true;
                  var1.e(1);
               }

               double var8 = var1.v;
               double var10 = var1.w;
               double var12 = var1.x;
               boolean var14 = var1.a(DamageSource.a(this), var2);
               if (var14) {
                  if (var3 > 0) {
                     var1.g(-MathHelper.a(this.y * (float) Math.PI / 180.0F) * var3 * 0.5F, 0.1, MathHelper.b(this.y * (float) Math.PI / 180.0F) * var3 * 0.5F);
                     this.v *= 0.6;
                     this.x *= 0.6;
                     this.d(false);
                  }

                  if (var1 instanceof EntityPlayerMP && var1.G) {
                     ((EntityPlayerMP)var1).a.a(new S12PacketEntityVelocity(var1));
                     var1.G = false;
                     var1.v = var8;
                     var1.w = var10;
                     var1.x = var12;
                  }

                  if (var5) {
                     this.b(var1);
                  }

                  if (var4 > 0.0F) {
                     this.c(var1);
                  }

                  if (var2 >= 18.0F) {
                     this.b(AchievementList.F);
                  }

                  this.p(var1);
                  if (var1 instanceof EntityLivingBase) {
                     EnchantmentHelper.a((EntityLivingBase)var1, this);
                  }

                  EnchantmentHelper.b(this, var1);
                  ItemStack var15 = this.bZ();
                  Object var16 = var1;
                  if (var1 instanceof EntityDragonPart) {
                     IEntityMultiPart var17 = ((EntityDragonPart)var1).a;
                     if (var17 instanceof EntityLivingBase) {
                        var16 = (EntityLivingBase)var17;
                     }
                  }

                  if (var15 != null && var16 instanceof EntityLivingBase) {
                     var15.a((EntityLivingBase)var16, this);
                     if (var15.b <= 0) {
                        this.ca();
                     }
                  }

                  if (var1 instanceof EntityLivingBase) {
                     this.a(StatList.w, Math.round(var2 * 10.0F));
                     if (var7 > 0) {
                        var1.e(var7 * 4);
                     }
                  }

                  this.a(0.3F);
               } else if (var6) {
                  var1.N();
               }
            }
         }
      }
   }

   public void b(Entity var1) {
   }

   public void c(Entity var1) {
   }

   public void cb() {
   }

   @Override
   public void J() {
      super.J();
      this.bj.b(this);
      if (this.bk != null) {
         this.bk.b(this);
      }
   }

   @Override
   public boolean aj() {
      return !this.bw && super.aj();
   }

   public boolean cc() {
      return false;
   }

   public GameProfile cd() {
      return this.bH;
   }

   public EntityPlayer.EnumStatus a(BlockPos var1) {
      if (!this.o.D) {
         if (this.bJ() || !this.ai()) {
            return EntityPlayer.EnumStatus.e;
         }

         if (!this.o.t.d()) {
            return EntityPlayer.EnumStatus.b;
         }

         if (this.o.w()) {
            return EntityPlayer.EnumStatus.c;
         }

         if (Math.abs(this.s - var1.n()) > 3.0 || Math.abs(this.t - var1.o()) > 2.0 || Math.abs(this.u - var1.p()) > 3.0) {
            return EntityPlayer.EnumStatus.d;
         }

         double var2 = 8.0;
         double var4 = 5.0;
         List var6 = this.o
            .a(EntityMob.class, new AxisAlignedBB(var1.n() - var2, var1.o() - var4, var1.p() - var2, var1.n() + var2, var1.o() + var4, var1.p() + var2));
         if (!var6.isEmpty()) {
            return EntityPlayer.EnumStatus.f;
         }
      }

      if (this.au()) {
         this.a(null);
      }

      this.a(0.2F, 0.2F);
      if (this.o.e(var1)) {
         EnumFacing var7 = this.o.p(var1).b(BlockDirectional.O);
         float var3 = 0.5F;
         float var8 = 0.5F;
         switch (var7) {
            case d:
               var8 = 0.9F;
               break;
            case c:
               var8 = 0.1F;
               break;
            case e:
               var3 = 0.1F;
               break;
            case f:
               var3 = 0.9F;
         }

         this.a(var7);
         this.b(var1.n() + var3, var1.o() + 0.6875F, var1.p() + var8);
      } else {
         this.b(var1.n() + 0.5F, var1.o() + 0.6875F, var1.p() + 0.5F);
      }

      this.bw = true;
      this.b = 0;
      this.bx = var1;
      this.v = this.x = this.w = 0.0;
      if (!this.o.D) {
         this.o.d();
      }

      return EntityPlayer.EnumStatus.a;
   }

   private void a(EnumFacing var1) {
      this.by = 0.0F;
      this.bz = 0.0F;
      switch (var1) {
         case d:
            this.bz = -1.8F;
            break;
         case c:
            this.bz = 1.8F;
            break;
         case e:
            this.by = 1.8F;
            break;
         case f:
            this.by = -1.8F;
      }
   }

   public void a(boolean var1, boolean var2, boolean var3) {
      this.a(0.6F, 1.8F);
      IBlockState var4 = this.o.p(this.bx);
      if (this.bx != null && var4.c() == Blocks.C) {
         this.o.a(this.bx, var4.a(BlockBed.b, false), 4);
         BlockPos var5 = BlockBed.a(this.o, this.bx, 0);
         if (var5 == null) {
            var5 = this.bx.a();
         }

         this.b(var5.n() + 0.5F, var5.o() + 0.1F, var5.p() + 0.5F);
      }

      this.bw = false;
      if (!this.o.D && var2) {
         this.o.d();
      }

      this.b = var1 ? 0 : 100;
      if (var3) {
         this.a(this.bx, false);
      }
   }

   private boolean p() {
      return this.o.p(this.bx).c() == Blocks.C;
   }

   public static BlockPos a(World var0, BlockPos var1, boolean var2) {
      Block var3 = var0.p(var1).c();
      if (var3 != Blocks.C) {
         if (!var2) {
            return null;
         } else {
            boolean var4 = var3.g();
            boolean var5 = var0.p(var1.a()).c().g();
            return var4 && var5 ? var1 : null;
         }
      } else {
         return BlockBed.a(var0, var1, 0);
      }
   }

   public float ce() {
      if (this.bx != null) {
         EnumFacing var1 = this.o.p(this.bx).b(BlockDirectional.O);
         switch (var1) {
            case d:
               return 90.0F;
            case c:
               return 270.0F;
            case e:
               return 0.0F;
            case f:
               return 180.0F;
         }
      }

      return 0.0F;
   }

   @Override
   public boolean bJ() {
      return this.bw;
   }

   public boolean cf() {
      return this.bw && this.b >= 100;
   }

   public int cg() {
      return this.b;
   }

   public void b(IChatComponent var1) {
   }

   public BlockPos ch() {
      return this.c;
   }

   public boolean ci() {
      return this.d;
   }

   public void a(BlockPos var1, boolean var2) {
      if (var1 != null) {
         this.c = var1;
         this.d = var2;
      } else {
         this.c = null;
         this.d = false;
      }
   }

   public void b(StatBase var1) {
      this.a(var1, 1);
   }

   public void a(StatBase var1, int var2) {
   }

   public void a(StatBase var1) {
   }

   @Override
   public void bF() {
      super.bF();
      this.b(StatList.u);
      if (this.aw()) {
         this.a(0.8F);
      } else {
         this.a(0.2F);
      }
   }

   @Override
   public void g(float var1, float var2) {
      double var3 = this.s;
      double var5 = this.t;
      double var7 = this.u;
      if (this.bA.b && this.m == null) {
         double var9 = this.w;
         float var11 = this.aM;
         this.aM = this.bA.a() * (this.aw() ? 2 : 1);
         super.g(var1, var2);
         this.w = var9 * 0.6;
         this.aM = var11;
      } else {
         super.g(var1, var2);
      }

      this.k(this.s - var3, this.t - var5, this.u - var7);
   }

   @Override
   public float bI() {
      return (float)this.a(SharedMonsterAttributes.d).e();
   }

   public void k(double var1, double var3, double var5) {
      if (this.m == null) {
         if (this.a(Material.h)) {
            int var7 = Math.round(MathHelper.a(var1 * var1 + var3 * var3 + var5 * var5) * 100.0F);
            if (var7 > 0) {
               this.a(StatList.p, var7);
               this.a(0.015F * var7 * 0.01F);
            }
         } else if (this.V()) {
            int var8 = Math.round(MathHelper.a(var1 * var1 + var5 * var5) * 100.0F);
            if (var8 > 0) {
               this.a(StatList.l, var8);
               this.a(0.015F * var8 * 0.01F);
            }
         } else if (this.k_()) {
            if (var3 > 0.0) {
               this.a(StatList.n, (int)Math.round(var3 * 100.0));
            }
         } else if (this.C) {
            int var9 = Math.round(MathHelper.a(var1 * var1 + var5 * var5) * 100.0F);
            if (var9 > 0) {
               this.a(StatList.i, var9);
               if (this.aw()) {
                  this.a(StatList.k, var9);
                  this.a(0.099999994F * var9 * 0.01F);
               } else {
                  if (this.av()) {
                     this.a(StatList.j, var9);
                  }

                  this.a(0.01F * var9 * 0.01F);
               }
            }
         } else {
            int var10 = Math.round(MathHelper.a(var1 * var1 + var5 * var5) * 100.0F);
            if (var10 > 25) {
               this.a(StatList.o, var10);
            }
         }
      }
   }

   private void l(double var1, double var3, double var5) {
      if (this.m != null) {
         int var7 = Math.round(MathHelper.a(var1 * var1 + var3 * var3 + var5 * var5) * 100.0F);
         if (var7 > 0) {
            if (this.m instanceof EntityMinecart) {
               this.a(StatList.q, var7);
               if (this.e == null) {
                  this.e = new BlockPos(this);
               } else if (this.e.c(MathHelper.c(this.s), MathHelper.c(this.t), MathHelper.c(this.u)) >= 1000000.0) {
                  this.b(AchievementList.q);
               }
            } else if (this.m instanceof EntityBoat) {
               this.a(StatList.r, var7);
            } else if (this.m instanceof EntityPig) {
               this.a(StatList.s, var7);
            } else if (this.m instanceof EntityHorse) {
               this.a(StatList.t, var7);
            }
         }
      }
   }

   @Override
   public void e(float var1, float var2) {
      if (!this.bA.c) {
         if (var1 >= 2.0F) {
            this.a(StatList.m, (int)Math.round(var1 * 100.0));
         }

         super.e(var1, var2);
      }
   }

   @Override
   protected void X() {
      if (!this.v()) {
         super.X();
      }
   }

   @Override
   protected String n(int var1) {
      return var1 > 4 ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
   }

   @Override
   public void a(EntityLivingBase var1) {
      if (var1 instanceof IMob) {
         this.b(AchievementList.s);
      }

      EntityList.EntityEggInfo var2 = EntityList.a.get(EntityList.a(var1));
      if (var2 != null) {
         this.b(var2.d);
      }
   }

   @Override
   public void aA() {
      if (!this.bA.b) {
         super.aA();
      }
   }

   @Override
   public ItemStack q(int var1) {
      return this.bi.e(var1);
   }

   public void u(int var1) {
      this.s(var1);
      int var2 = Integer.MAX_VALUE - this.bC;
      if (var1 > var2) {
         var1 = var2;
      }

      this.bD = this.bD + (float)var1 / this.ck();

      for (this.bC += var1; this.bD >= 1.0F; this.bD = this.bD / this.ck()) {
         this.bD = (this.bD - 1.0F) * this.ck();
         this.a(1);
      }
   }

   public int cj() {
      return this.f;
   }

   public void b(int var1) {
      this.bB -= var1;
      if (this.bB < 0) {
         this.bB = 0;
         this.bD = 0.0F;
         this.bC = 0;
      }

      this.f = this.V.nextInt();
   }

   public void a(int var1) {
      this.bB += var1;
      if (this.bB < 0) {
         this.bB = 0;
         this.bD = 0.0F;
         this.bC = 0;
      }

      if (var1 > 0 && this.bB % 5 == 0 && this.i < this.W - 100.0F) {
         float var2 = this.bB > 30 ? 1.0F : this.bB / 30.0F;
         this.o.a((Entity)this, "random.levelup", var2 * 0.75F, 1.0F);
         this.i = this.W;
      }
   }

   public int ck() {
      if (this.bB >= 30) {
         return 112 + (this.bB - 30) * 9;
      } else {
         return this.bB >= 15 ? 37 + (this.bB - 15) * 5 : 7 + this.bB * 2;
      }
   }

   public void a(float var1) {
      if (!this.bA.a) {
         if (!this.o.D) {
            this.bl.a(var1);
         }
      }
   }

   public FoodStats cl() {
      return this.bl;
   }

   public boolean j(boolean var1) {
      return (var1 || this.bl.c()) && !this.bA.a;
   }

   public boolean cm() {
      return this.bn() > 0.0F && this.bn() < this.bu();
   }

   public void a(ItemStack var1, int var2) {
      if (var1 != this.g) {
         this.g = var1;
         this.h = var2;
         if (!this.o.D) {
            this.f(true);
         }
      }
   }

   public boolean cn() {
      return this.bA.e;
   }

   public boolean a(BlockPos var1, EnumFacing var2, ItemStack var3) {
      if (this.bA.e) {
         return true;
      } else if (var3 == null) {
         return false;
      } else {
         BlockPos var4 = var1.a(var2.d());
         Block var5 = this.o.p(var4).c();
         return var3.d(var5) || var3.x();
      }
   }

   @Override
   protected int b(EntityPlayer var1) {
      if (this.o.Q().b("keepInventory")) {
         return 0;
      } else {
         int var2 = this.bB * 7;
         return var2 > 100 ? 100 : var2;
      }
   }

   @Override
   protected boolean bb() {
      return true;
   }

   @Override
   public boolean aO() {
      return true;
   }

   public void a(EntityPlayer var1, boolean var2) {
      if (var2) {
         this.bi.b(var1.bi);
         this.i(var1.bn());
         this.bl = var1.bl;
         this.bB = var1.bB;
         this.bC = var1.bC;
         this.bD = var1.bD;
         this.r(var1.bX());
         this.an = var1.an;
         this.ao = var1.ao;
         this.ap = var1.ap;
      } else if (this.o.Q().b("keepInventory")) {
         this.bi.b(var1.bi);
         this.bB = var1.bB;
         this.bC = var1.bC;
         this.bD = var1.bD;
         this.r(var1.bX());
      }

      this.f = var1.f;
      this.a = var1.a;
      this.H().b(10, var1.H().a(10));
   }

   @Override
   protected boolean s_() {
      return !this.bA.b;
   }

   public void t() {
   }

   public void a(WorldSettings.GameType var1) {
   }

   @Override
   public String e_() {
      return this.bH.getName();
   }

   public InventoryEnderChest co() {
      return this.a;
   }

   @Override
   public ItemStack p(int var1) {
      return var1 == 0 ? this.bi.h() : this.bi.b[var1 - 1];
   }

   @Override
   public ItemStack bA() {
      return this.bi.h();
   }

   @Override
   public void c(int var1, ItemStack var2) {
      this.bi.b[var1] = var2;
   }

   @Override
   public boolean f(EntityPlayer var1) {
      if (!this.ax()) {
         return false;
      } else if (var1.v()) {
         return false;
      } else {
         Team var2 = this.bO();
         return var2 == null || var1 == null || var1.bO() != var2 || !var2.h();
      }
   }

   public abstract boolean v();

   @Override
   public ItemStack[] as() {
      return this.bi.b;
   }

   @Override
   public boolean aL() {
      return !this.bA.b;
   }

   public Scoreboard cp() {
      return this.o.Z();
   }

   @Override
   public Team bO() {
      return this.cp().h(this.e_());
   }

   @Override
   public IChatComponent f_() {
      ChatComponentText var1 = new ChatComponentText(ScorePlayerTeam.a(this.bO(), this.e_()));
      var1.b().a(new ClickEvent(ClickEvent.Action.e, "/msg " + this.e_() + " "));
      var1.b().a(this.aQ());
      var1.b().a(this.e_());
      return var1;
   }

   @Override
   public float aS() {
      float var1 = 1.62F;
      if (this.bJ()) {
         var1 = 0.2F;
      }

      if (this.av()) {
         var1 -= 0.08F;
      }

      return var1;
   }

   @Override
   public void m(float var1) {
      if (var1 < 0.0F) {
         var1 = 0.0F;
      }

      this.H().b(17, var1);
   }

   @Override
   public float bN() {
      return this.H().d(17);
   }

   public static UUID a(GameProfile var0) {
      UUID var1 = var0.getId();
      if (var1 == null) {
         var1 = b(var0.getName());
      }

      return var1;
   }

   public static UUID b(String var0) {
      return UUID.nameUUIDFromBytes(("OfflinePlayer:" + var0).getBytes(Charsets.UTF_8));
   }

   public boolean a(LockCode var1) {
      if (var1.a()) {
         return true;
      } else {
         ItemStack var2 = this.bZ();
         return var2 != null && var2.s() ? var2.q().equals(var1.b()) : false;
      }
   }

   public boolean a(EnumPlayerModelParts var1) {
      return (this.H().a(10) & var1.a()) == var1.a();
   }

   @Override
   public boolean u_() {
      return MinecraftServer.N().d[0].Q().b("sendCommandFeedback");
   }

   @Override
   public boolean d(int var1, ItemStack var2) {
      if (var1 >= 0 && var1 < this.bi.a.length) {
         this.bi.a(var1, var2);
         return true;
      } else {
         int var3 = var1 - 100;
         if (var3 >= 0 && var3 < this.bi.b.length) {
            int var5 = var3 + 1;
            if (var2 != null && var2.b() != null) {
               if (var2.b() instanceof ItemArmor) {
                  if (EntityLiving.c(var2) != var5) {
                     return false;
                  }
               } else if (var5 != 4 || var2.b() != Items.bX && !(var2.b() instanceof ItemBlock)) {
                  return false;
               }
            }

            this.bi.a(var3 + this.bi.a.length, var2);
            return true;
         } else {
            int var4 = var1 - 200;
            if (var4 >= 0 && var4 < this.a.o_()) {
               this.a.a(var4, var2);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   public boolean cq() {
      return this.bI;
   }

   public void k(boolean var1) {
      this.bI = var1;
   }

   public static enum EnumChatVisibility {
      a(0, "options.chat.visibility.full"),
      b(1, "options.chat.visibility.system"),
      c(2, "options.chat.visibility.hidden");

      private static final EntityPlayer.EnumChatVisibility[] d = new EntityPlayer.EnumChatVisibility[values().length];
      private final int e;
      private final String f;

      private EnumChatVisibility(int var3, String var4) {
         this.e = var3;
         this.f = var4;
      }

      public int a() {
         return this.e;
      }

      public static EntityPlayer.EnumChatVisibility a(int var0) {
         return d[var0 % d.length];
      }

      public String b() {
         return this.f;
      }

      static {
         for (EntityPlayer.EnumChatVisibility var3 : values()) {
            d[var3.e] = var3;
         }
      }
   }

   public static enum EnumStatus {
      a,
      b,
      c,
      d,
      e,
      f;
   }
}
