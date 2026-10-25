package net.minecraft.entity.passive;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityHorse extends EntityAnimal implements IInvBasic {
   private static final Predicate<Entity> bs = new Predicate<Entity>() {
      public boolean a(Entity var1) {
         return var1 instanceof EntityHorse && ((EntityHorse)var1).cA();
      }
   };
   private static final IAttribute bt = new RangedAttribute(null, "horse.jumpStrength", 0.7, 0.0, 2.0).a("Jump Strength").a(true);
   private static final String[] bu = new String[]{
      null,
      "textures/entity/horse/armor/horse_armor_iron.png",
      "textures/entity/horse/armor/horse_armor_gold.png",
      "textures/entity/horse/armor/horse_armor_diamond.png"
   };
   private static final String[] bv = new String[]{"", "meo", "goo", "dio"};
   private static final int[] bw = new int[]{0, 5, 7, 11};
   private static final String[] bx = new String[]{
      "textures/entity/horse/horse_white.png",
      "textures/entity/horse/horse_creamy.png",
      "textures/entity/horse/horse_chestnut.png",
      "textures/entity/horse/horse_brown.png",
      "textures/entity/horse/horse_black.png",
      "textures/entity/horse/horse_gray.png",
      "textures/entity/horse/horse_darkbrown.png"
   };
   private static final String[] by = new String[]{"hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb"};
   private static final String[] bz = new String[]{
      null,
      "textures/entity/horse/horse_markings_white.png",
      "textures/entity/horse/horse_markings_whitefield.png",
      "textures/entity/horse/horse_markings_whitedots.png",
      "textures/entity/horse/horse_markings_blackdots.png"
   };
   private static final String[] bA = new String[]{"", "wo_", "wmo", "wdo", "bdo"};
   private int bB;
   private int bC;
   private int bD;
   public int bm;
   public int bo;
   protected boolean bp;
   private AnimalChest bE;
   private boolean bF;
   protected int bq;
   protected float br;
   private boolean bG;
   private float bH;
   private float bI;
   private float bJ;
   private float bK;
   private float bL;
   private float bM;
   private int bN;
   private String bO;
   private String[] bP = new String[3];
   private boolean bQ = false;

   public EntityHorse(World var1) {
      super(var1);
      this.a(1.4F, 1.6F);
      this.ab = false;
      this.o(false);
      ((PathNavigateGround)this.s()).a(true);
      this.i.a(0, new EntityAISwimming(this));
      this.i.a(1, new EntityAIPanic(this, 1.2));
      this.i.a(1, new EntityAIRunAroundLikeCrazy(this, 1.2));
      this.i.a(2, new EntityAIMate(this, 1.0));
      this.i.a(4, new EntityAIFollowParent(this, 1.0));
      this.i.a(6, new EntityAIWander(this, 0.7));
      this.i.a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.i.a(8, new EntityAILookIdle(this));
      this.da();
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(16, Integer.valueOf(0));
      this.ac.a(19, Byte.valueOf((byte)0));
      this.ac.a(20, Integer.valueOf(0));
      this.ac.a(21, String.valueOf(""));
      this.ac.a(22, Integer.valueOf(0));
   }

   public void r(int var1) {
      this.ac.b(19, (byte)var1);
      this.dc();
   }

   public int cl() {
      return this.ac.a(19);
   }

   public void s(int var1) {
      this.ac.b(20, var1);
      this.dc();
   }

   public int cm() {
      return this.ac.c(20);
   }

   @Override
   public String e_() {
      if (this.l_()) {
         return this.aM();
      } else {
         int var1 = this.cl();
         switch (var1) {
            case 0:
            default:
               return StatCollector.a("entity.horse.name");
            case 1:
               return StatCollector.a("entity.donkey.name");
            case 2:
               return StatCollector.a("entity.mule.name");
            case 3:
               return StatCollector.a("entity.zombiehorse.name");
            case 4:
               return StatCollector.a("entity.skeletonhorse.name");
         }
      }
   }

   private boolean w(int var1) {
      return (this.ac.c(16) & var1) != 0;
   }

   private void c(int var1, boolean var2) {
      int var3 = this.ac.c(16);
      if (var2) {
         this.ac.b(16, var3 | var1);
      } else {
         this.ac.b(16, var3 & ~var1);
      }
   }

   public boolean cn() {
      return !this.j_();
   }

   public boolean co() {
      return this.w(2);
   }

   public boolean cp() {
      return this.cn();
   }

   public String ct() {
      return this.ac.e(21);
   }

   public void b(String var1) {
      this.ac.b(21, var1);
   }

   public float cu() {
      return 0.5F;
   }

   @Override
   public void a(boolean var1) {
      if (var1) {
         this.a(this.cu());
      } else {
         this.a(1.0F);
      }
   }

   public boolean cv() {
      return this.bp;
   }

   public void l(boolean var1) {
      this.c(2, var1);
   }

   public void m(boolean var1) {
      this.bp = var1;
   }

   @Override
   public boolean cb() {
      return !this.cR() && super.cb();
   }

   @Override
   protected void o(float var1) {
      if (var1 > 6.0F && this.cy()) {
         this.r(false);
      }
   }

   public boolean cw() {
      return this.w(8);
   }

   public int cx() {
      return this.ac.c(22);
   }

   private int f(ItemStack var1) {
      if (var1 == null) {
         return 0;
      } else {
         Item var2 = var1.b();
         if (var2 == Items.ck) {
            return 1;
         } else if (var2 == Items.cl) {
            return 2;
         } else {
            return var2 == Items.cm ? 3 : 0;
         }
      }
   }

   public boolean cy() {
      return this.w(32);
   }

   public boolean cz() {
      return this.w(64);
   }

   public boolean cA() {
      return this.w(16);
   }

   public boolean cB() {
      return this.bF;
   }

   public void e(ItemStack var1) {
      this.ac.b(22, this.f(var1));
      this.dc();
   }

   public void n(boolean var1) {
      this.c(16, var1);
   }

   public void o(boolean var1) {
      this.c(8, var1);
   }

   public void p(boolean var1) {
      this.bF = var1;
   }

   public void q(boolean var1) {
      this.c(4, var1);
   }

   public int cC() {
      return this.bq;
   }

   public void t(int var1) {
      this.bq = var1;
   }

   public int u(int var1) {
      int var2 = MathHelper.a(this.cC() + var1, 0, this.cI());
      this.t(var2);
      return var2;
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      Entity var3 = var1.j();
      return this.l != null && this.l.equals(var3) ? false : super.a(var1, var2);
   }

   @Override
   public int br() {
      return bw[this.cx()];
   }

   @Override
   public boolean ae() {
      return this.l == null;
   }

   public boolean cD() {
      int var1 = MathHelper.c(this.s);
      int var2 = MathHelper.c(this.u);
      this.o.b(new BlockPos(var1, 0, var2));
      return true;
   }

   public void cE() {
      if (!this.o.D && this.cw()) {
         this.a(Item.a(Blocks.ae), 1);
         this.o(false);
      }
   }

   private void cY() {
      this.df();
      if (!this.R()) {
         this.o.a(this, "eating", 1.0F, 1.0F + (this.V.nextFloat() - this.V.nextFloat()) * 0.2F);
      }
   }

   @Override
   public void e(float var1, float var2) {
      if (var1 > 1.0F) {
         this.a("mob.horse.land", 0.4F, 1.0F);
      }

      int var3 = MathHelper.f((var1 * 0.5F - 3.0F) * var2);
      if (var3 > 0) {
         this.a(DamageSource.i, (float)var3);
         if (this.l != null) {
            this.l.a(DamageSource.i, (float)var3);
         }

         Block var4 = this.o.p(new BlockPos(this.s, this.t - 0.2 - this.A, this.u)).c();
         if (var4.t() != Material.a && !this.R()) {
            Block.SoundType var5 = var4.H;
            this.o.a(this, var5.c(), var5.d() * 0.5F, var5.e() * 0.75F);
         }
      }
   }

   private int cZ() {
      int var1 = this.cl();
      return !this.cw() || var1 != 1 && var1 != 2 ? 2 : 17;
   }

   private void da() {
      AnimalChest var1 = this.bE;
      this.bE = new AnimalChest("HorseChest", this.cZ());
      this.bE.a(this.e_());
      if (var1 != null) {
         var1.b(this);
         int var2 = Math.min(var1.o_(), this.bE.o_());

         for (int var3 = 0; var3 < var2; var3++) {
            ItemStack var4 = var1.a(var3);
            if (var4 != null) {
               this.bE.a(var3, var4.k());
            }
         }
      }

      this.bE.a(this);
      this.db();
   }

   private void db() {
      if (!this.o.D) {
         this.q(this.bE.a(0) != null);
         if (this.cO()) {
            this.e(this.bE.a(1));
         }
      }
   }

   @Override
   public void a(InventoryBasic var1) {
      int var2 = this.cx();
      boolean var3 = this.cG();
      this.db();
      if (this.W > 20) {
         if (var2 == 0 && var2 != this.cx()) {
            this.a("mob.horse.armor", 0.5F, 1.0F);
         } else if (var2 != this.cx()) {
            this.a("mob.horse.armor", 0.5F, 1.0F);
         }

         if (!var3 && this.cG()) {
            this.a("mob.horse.leather", 0.5F, 1.0F);
         }
      }
   }

   @Override
   public boolean bR() {
      this.cD();
      return super.bR();
   }

   protected EntityHorse a(Entity var1, double var2) {
      double var4 = Double.MAX_VALUE;
      Entity var6 = null;

      for (Entity var9 : this.o.a(var1, var1.aR().a(var2, var2, var2), bs)) {
         double var10 = var9.e(var1.s, var1.t, var1.u);
         if (var10 < var4) {
            var6 = var9;
            var4 = var10;
         }
      }

      return (EntityHorse)var6;
   }

   public double cF() {
      return this.a(bt).e();
   }

   @Override
   protected String bp() {
      this.df();
      int var1 = this.cl();
      if (var1 == 3) {
         return "mob.horse.zombie.death";
      } else if (var1 == 4) {
         return "mob.horse.skeleton.death";
      } else {
         return var1 != 1 && var1 != 2 ? "mob.horse.death" : "mob.horse.donkey.death";
      }
   }

   @Override
   protected Item A() {
      boolean var1 = this.V.nextInt(4) == 0;
      int var2 = this.cl();
      if (var2 == 4) {
         return Items.aX;
      } else if (var2 == 3) {
         return var1 ? null : Items.bt;
      } else {
         return Items.aF;
      }
   }

   @Override
   protected String bo() {
      this.df();
      if (this.V.nextInt(3) == 0) {
         this.dh();
      }

      int var1 = this.cl();
      if (var1 == 3) {
         return "mob.horse.zombie.hit";
      } else if (var1 == 4) {
         return "mob.horse.skeleton.hit";
      } else {
         return var1 != 1 && var1 != 2 ? "mob.horse.hit" : "mob.horse.donkey.hit";
      }
   }

   public boolean cG() {
      return this.w(4);
   }

   @Override
   protected String z() {
      this.df();
      if (this.V.nextInt(10) == 0 && !this.bD()) {
         this.dh();
      }

      int var1 = this.cl();
      if (var1 == 3) {
         return "mob.horse.zombie.idle";
      } else if (var1 == 4) {
         return "mob.horse.skeleton.idle";
      } else {
         return var1 != 1 && var1 != 2 ? "mob.horse.idle" : "mob.horse.donkey.idle";
      }
   }

   protected String cH() {
      this.df();
      this.dh();
      int var1 = this.cl();
      if (var1 == 3 || var1 == 4) {
         return null;
      } else {
         return var1 != 1 && var1 != 2 ? "mob.horse.angry" : "mob.horse.donkey.angry";
      }
   }

   @Override
   protected void a(BlockPos var1, Block var2) {
      Block.SoundType var3 = var2.H;
      if (this.o.p(var1.a()).c() == Blocks.aH) {
         var3 = Blocks.aH.H;
      }

      if (!var2.t().d()) {
         int var4 = this.cl();
         if (this.l != null && var4 != 1 && var4 != 2) {
            this.bN++;
            if (this.bN > 5 && this.bN % 3 == 0) {
               this.a("mob.horse.gallop", var3.d() * 0.15F, var3.e());
               if (var4 == 0 && this.V.nextInt(10) == 0) {
                  this.a("mob.horse.breathe", var3.d() * 0.6F, var3.e());
               }
            } else if (this.bN <= 5) {
               this.a("mob.horse.wood", var3.d() * 0.15F, var3.e());
            }
         } else if (var3 == Block.f) {
            this.a("mob.horse.wood", var3.d() * 0.15F, var3.e());
         } else {
            this.a("mob.horse.soft", var3.d() * 0.15F, var3.e());
         }
      }
   }

   @Override
   protected void aX() {
      super.aX();
      this.by().b(bt);
      this.a(SharedMonsterAttributes.a).a(53.0);
      this.a(SharedMonsterAttributes.d).a(0.225F);
   }

   @Override
   public int bV() {
      return 6;
   }

   public int cI() {
      return 100;
   }

   @Override
   protected float bB() {
      return 0.8F;
   }

   @Override
   public int w() {
      return 400;
   }

   public boolean cJ() {
      return this.cl() == 0 || this.cx() > 0;
   }

   private void dc() {
      this.bO = null;
   }

   public boolean cK() {
      return this.bQ;
   }

   private void dd() {
      this.bO = "horse/";
      this.bP[0] = null;
      this.bP[1] = null;
      this.bP[2] = null;
      int var1 = this.cl();
      int var2 = this.cm();
      if (var1 == 0) {
         int var3 = var2 & 0xFF;
         int var4 = (var2 & 0xFF00) >> 8;
         if (var3 >= bx.length) {
            this.bQ = false;
            return;
         }

         this.bP[0] = bx[var3];
         this.bO = this.bO + by[var3];
         if (var4 >= bz.length) {
            this.bQ = false;
            return;
         }

         this.bP[1] = bz[var4];
         this.bO = this.bO + bA[var4];
      } else {
         this.bP[0] = "";
         this.bO = this.bO + "_" + var1 + "_";
      }

      int var5 = this.cx();
      if (var5 >= bu.length) {
         this.bQ = false;
      } else {
         this.bP[2] = bu[var5];
         this.bO = this.bO + bv[var5];
         this.bQ = true;
      }
   }

   public String cL() {
      if (this.bO == null) {
         this.dd();
      }

      return this.bO;
   }

   public String[] cM() {
      if (this.bO == null) {
         this.dd();
      }

      return this.bP;
   }

   public void g(EntityPlayer var1) {
      if (!this.o.D && (this.l == null || this.l == var1) && this.co()) {
         this.bE.a(this.e_());
         var1.a(this, this.bE);
      }
   }

   @Override
   public boolean a(EntityPlayer var1) {
      ItemStack var2 = var1.bi.h();
      if (var2 != null && var2.b() == Items.bJ) {
         return super.a(var1);
      } else if (!this.co() && this.cR()) {
         return false;
      } else if (this.co() && this.cn() && var1.av()) {
         this.g(var1);
         return true;
      } else if (this.cp() && this.l != null) {
         return super.a(var1);
      } else {
         if (var2 != null) {
            boolean var3 = false;
            if (this.cO()) {
               byte var4 = -1;
               if (var2.b() == Items.ck) {
                  var4 = 1;
               } else if (var2.b() == Items.cl) {
                  var4 = 2;
               } else if (var2.b() == Items.cm) {
                  var4 = 3;
               }

               if (var4 >= 0) {
                  if (!this.co()) {
                     this.cW();
                     return true;
                  }

                  this.g(var1);
                  return true;
               }
            }

            if (!var3 && !this.cR()) {
               float var7 = 0.0F;
               short var5 = 0;
               byte var6 = 0;
               if (var2.b() == Items.O) {
                  var7 = 2.0F;
                  var5 = 20;
                  var6 = 3;
               } else if (var2.b() == Items.aY) {
                  var7 = 1.0F;
                  var5 = 30;
                  var6 = 3;
               } else if (Block.a(var2.b()) == Blocks.cx) {
                  var7 = 20.0F;
                  var5 = 180;
               } else if (var2.b() == Items.e) {
                  var7 = 3.0F;
                  var5 = 60;
                  var6 = 3;
               } else if (var2.b() == Items.bW) {
                  var7 = 4.0F;
                  var5 = 60;
                  var6 = 5;
                  if (this.co() && this.l() == 0) {
                     var3 = true;
                     this.c(var1);
                  }
               } else if (var2.b() == Items.ao) {
                  var7 = 10.0F;
                  var5 = 240;
                  var6 = 10;
                  if (this.co() && this.l() == 0) {
                     var3 = true;
                     this.c(var1);
                  }
               }

               if (this.bn() < this.bu() && var7 > 0.0F) {
                  this.h(var7);
                  var3 = true;
               }

               if (!this.cn() && var5 > 0) {
                  this.a(var5);
                  var3 = true;
               }

               if (var6 > 0 && (var3 || !this.co()) && var6 < this.cI()) {
                  var3 = true;
                  this.u(var6);
               }

               if (var3) {
                  this.cY();
               }
            }

            if (!this.co() && !var3) {
               if (var2 != null && var2.a(var1, this)) {
                  return true;
               }

               this.cW();
               return true;
            }

            if (!var3 && this.cP() && !this.cw() && var2.b() == Item.a(Blocks.ae)) {
               this.o(true);
               this.a("mob.chickenplop", 1.0F, (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F);
               var3 = true;
               this.da();
            }

            if (!var3 && this.cp() && !this.cG() && var2.b() == Items.aA) {
               this.g(var1);
               return true;
            }

            if (var3) {
               if (!var1.bA.d && --var2.b == 0) {
                  var1.bi.a(var1.bi.c, null);
               }

               return true;
            }
         }

         if (!this.cp() || this.l != null) {
            return super.a(var1);
         } else if (var2 != null && var2.a(var1, this)) {
            return true;
         } else {
            this.i(var1);
            return true;
         }
      }
   }

   private void i(EntityPlayer var1) {
      var1.y = this.y;
      var1.z = this.z;
      this.r(false);
      this.s(false);
      if (!this.o.D) {
         var1.a(this);
      }
   }

   public boolean cO() {
      return this.cl() == 0;
   }

   public boolean cP() {
      int var1 = this.cl();
      return var1 == 2 || var1 == 1;
   }

   @Override
   protected boolean bD() {
      return this.l != null && this.cG() ? true : this.cy() || this.cz();
   }

   public boolean cR() {
      int var1 = this.cl();
      return var1 == 3 || var1 == 4;
   }

   public boolean cS() {
      return this.cR() || this.cl() == 2;
   }

   @Override
   public boolean d(ItemStack var1) {
      return false;
   }

   private void de() {
      this.bm = 1;
   }

   @Override
   public void a(DamageSource var1) {
      super.a(var1);
      if (!this.o.D) {
         this.cX();
      }
   }

   @Override
   public void m() {
      if (this.V.nextInt(200) == 0) {
         this.de();
      }

      super.m();
      if (!this.o.D) {
         if (this.V.nextInt(900) == 0 && this.ax == 0) {
            this.h(1.0F);
         }

         if (!this.cy()
            && this.l == null
            && this.V.nextInt(300) == 0
            && this.o.p(new BlockPos(MathHelper.c(this.s), MathHelper.c(this.t) - 1, MathHelper.c(this.u))).c() == Blocks.c) {
            this.r(true);
         }

         if (this.cy() && ++this.bB > 50) {
            this.bB = 0;
            this.r(false);
         }

         if (this.cA() && !this.cn() && !this.cy()) {
            EntityHorse var1 = this.a(this, 16.0);
            if (var1 != null && this.h(var1) > 4.0) {
               this.h.a(var1);
            }
         }
      }
   }

   @Override
   public void t_() {
      super.t_();
      if (this.o.D && this.ac.a()) {
         this.ac.e();
         this.dc();
      }

      if (this.bC > 0 && ++this.bC > 30) {
         this.bC = 0;
         this.c(128, false);
      }

      if (!this.o.D && this.bD > 0 && ++this.bD > 20) {
         this.bD = 0;
         this.s(false);
      }

      if (this.bm > 0 && ++this.bm > 8) {
         this.bm = 0;
      }

      if (this.bo > 0) {
         this.bo++;
         if (this.bo > 300) {
            this.bo = 0;
         }
      }

      this.bI = this.bH;
      if (this.cy()) {
         this.bH = this.bH + ((1.0F - this.bH) * 0.4F + 0.05F);
         if (this.bH > 1.0F) {
            this.bH = 1.0F;
         }
      } else {
         this.bH = this.bH + ((0.0F - this.bH) * 0.4F - 0.05F);
         if (this.bH < 0.0F) {
            this.bH = 0.0F;
         }
      }

      this.bK = this.bJ;
      if (this.cz()) {
         this.bI = this.bH = 0.0F;
         this.bJ = this.bJ + ((1.0F - this.bJ) * 0.4F + 0.05F);
         if (this.bJ > 1.0F) {
            this.bJ = 1.0F;
         }
      } else {
         this.bG = false;
         this.bJ = this.bJ + ((0.8F * this.bJ * this.bJ * this.bJ - this.bJ) * 0.6F - 0.05F);
         if (this.bJ < 0.0F) {
            this.bJ = 0.0F;
         }
      }

      this.bM = this.bL;
      if (this.w(128)) {
         this.bL = this.bL + ((1.0F - this.bL) * 0.7F + 0.05F);
         if (this.bL > 1.0F) {
            this.bL = 1.0F;
         }
      } else {
         this.bL = this.bL + ((0.0F - this.bL) * 0.7F - 0.05F);
         if (this.bL < 0.0F) {
            this.bL = 0.0F;
         }
      }
   }

   private void df() {
      if (!this.o.D) {
         this.bC = 1;
         this.c(128, true);
      }
   }

   private boolean dg() {
      return this.l == null && this.m == null && this.co() && this.cn() && !this.cS() && this.bn() >= this.bu() && this.cr();
   }

   @Override
   public void f(boolean var1) {
      this.c(32, var1);
   }

   public void r(boolean var1) {
      this.f(var1);
   }

   public void s(boolean var1) {
      if (var1) {
         this.r(false);
      }

      this.c(64, var1);
   }

   private void dh() {
      if (!this.o.D) {
         this.bD = 1;
         this.s(true);
      }
   }

   public void cW() {
      this.dh();
      String var1 = this.cH();
      if (var1 != null) {
         this.a(var1, this.bB(), this.bC());
      }
   }

   public void cX() {
      this.a(this, this.bE);
      this.cE();
   }

   private void a(Entity var1, AnimalChest var2) {
      if (var2 != null && !this.o.D) {
         for (int var3 = 0; var3 < var2.o_(); var3++) {
            ItemStack var4 = var2.a(var3);
            if (var4 != null) {
               this.a(var4, 0.0F);
            }
         }
      }
   }

   public boolean h(EntityPlayer var1) {
      this.b(var1.aK().toString());
      this.l(true);
      return true;
   }

   @Override
   public void g(float var1, float var2) {
      if (this.l != null && this.l instanceof EntityLivingBase && this.cG()) {
         this.A = this.y = this.l.y;
         this.z = this.l.z * 0.5F;
         this.b(this.y, this.z);
         this.aK = this.aI = this.y;
         var1 = ((EntityLivingBase)this.l).aZ * 0.5F;
         var2 = ((EntityLivingBase)this.l).ba;
         if (var2 <= 0.0F) {
            var2 *= 0.25F;
            this.bN = 0;
         }

         if (this.C && this.br == 0.0F && this.cz() && !this.bG) {
            var1 = 0.0F;
            var2 = 0.0F;
         }

         if (this.br > 0.0F && !this.cv() && this.C) {
            this.w = this.cF() * this.br;
            if (this.a(Potion.j)) {
               this.w = this.w + (this.b(Potion.j).c() + 1) * 0.1F;
            }

            this.m(true);
            this.ai = true;
            if (var2 > 0.0F) {
               float var3 = MathHelper.a(this.y * (float) Math.PI / 180.0F);
               float var4 = MathHelper.b(this.y * (float) Math.PI / 180.0F);
               this.v = this.v + -0.4F * var3 * this.br;
               this.x = this.x + 0.4F * var4 * this.br;
               this.a("mob.horse.jump", 0.4F, 1.0F);
            }

            this.br = 0.0F;
         }

         this.S = 1.0F;
         this.aM = this.bI() * 0.1F;
         if (!this.o.D) {
            this.k((float)this.a(SharedMonsterAttributes.d).e());
            super.g(var1, var2);
         }

         if (this.C) {
            this.br = 0.0F;
            this.m(false);
         }

         this.aA = this.aB;
         double var10 = this.s - this.p;
         double var5 = this.u - this.r;
         float var7 = MathHelper.a(var10 * var10 + var5 * var5) * 4.0F;
         if (var7 > 1.0F) {
            var7 = 1.0F;
         }

         this.aB = this.aB + (var7 - this.aB) * 0.4F;
         this.aC = this.aC + this.aB;
      } else {
         this.S = 0.5F;
         this.aM = 0.02F;
         super.g(var1, var2);
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("EatingHaystack", this.cy());
      var1.a("ChestedHorse", this.cw());
      var1.a("HasReproduced", this.cB());
      var1.a("Bred", this.cA());
      var1.a("Type", this.cl());
      var1.a("Variant", this.cm());
      var1.a("Temper", this.cC());
      var1.a("Tame", this.co());
      var1.a("OwnerUUID", this.ct());
      if (this.cw()) {
         NBTTagList var2 = new NBTTagList();

         for (int var3 = 2; var3 < this.bE.o_(); var3++) {
            ItemStack var4 = this.bE.a(var3);
            if (var4 != null) {
               NBTTagCompound var5 = new NBTTagCompound();
               var5.a("Slot", (byte)var3);
               var4.b(var5);
               var2.a(var5);
            }
         }

         var1.a("Items", var2);
      }

      if (this.bE.a(1) != null) {
         var1.a("ArmorItem", this.bE.a(1).b(new NBTTagCompound()));
      }

      if (this.bE.a(0) != null) {
         var1.a("SaddleItem", this.bE.a(0).b(new NBTTagCompound()));
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.r(var1.n("EatingHaystack"));
      this.n(var1.n("Bred"));
      this.o(var1.n("ChestedHorse"));
      this.p(var1.n("HasReproduced"));
      this.r(var1.f("Type"));
      this.s(var1.f("Variant"));
      this.t(var1.f("Temper"));
      this.l(var1.n("Tame"));
      String var2 = "";
      if (var1.b("OwnerUUID", 8)) {
         var2 = var1.j("OwnerUUID");
      } else {
         String var3 = var1.j("Owner");
         var2 = PreYggdrasilConverter.a(var3);
      }

      if (var2.length() > 0) {
         this.b(var2);
      }

      IAttributeInstance var9 = this.by().a("Speed");
      if (var9 != null) {
         this.a(SharedMonsterAttributes.d).a(var9.b() * 0.25);
      }

      if (this.cw()) {
         NBTTagList var4 = var1.c("Items", 10);
         this.da();

         for (int var5 = 0; var5 < var4.c(); var5++) {
            NBTTagCompound var6 = var4.b(var5);
            int var7 = var6.d("Slot") & 255;
            if (var7 >= 2 && var7 < this.bE.o_()) {
               this.bE.a(var7, ItemStack.a(var6));
            }
         }
      }

      if (var1.b("ArmorItem", 10)) {
         ItemStack var10 = ItemStack.a(var1.m("ArmorItem"));
         if (var10 != null && a(var10.b())) {
            this.bE.a(1, var10);
         }
      }

      if (var1.b("SaddleItem", 10)) {
         ItemStack var11 = ItemStack.a(var1.m("SaddleItem"));
         if (var11 != null && var11.b() == Items.aA) {
            this.bE.a(0, var11);
         }
      } else if (var1.n("Saddle")) {
         this.bE.a(0, new ItemStack(Items.aA));
      }

      this.db();
   }

   @Override
   public boolean a(EntityAnimal var1) {
      if (var1 == this) {
         return false;
      } else if (var1.getClass() != this.getClass()) {
         return false;
      } else {
         EntityHorse var2 = (EntityHorse)var1;
         if (this.dg() && var2.dg()) {
            int var3 = this.cl();
            int var4 = var2.cl();
            return var3 == var4 || var3 == 0 && var4 == 1 || var3 == 1 && var4 == 0;
         } else {
            return false;
         }
      }
   }

   @Override
   public EntityAgeable a(EntityAgeable var1) {
      EntityHorse var2 = (EntityHorse)var1;
      EntityHorse var3 = new EntityHorse(this.o);
      int var4 = this.cl();
      int var5 = var2.cl();
      int var6 = 0;
      if (var4 == var5) {
         var6 = var4;
      } else if (var4 == 0 && var5 == 1 || var4 == 1 && var5 == 0) {
         var6 = 2;
      }

      if (var6 == 0) {
         int var8 = this.V.nextInt(9);
         int var7;
         if (var8 < 4) {
            var7 = this.cm() & 0xFF;
         } else if (var8 < 8) {
            var7 = var2.cm() & 0xFF;
         } else {
            var7 = this.V.nextInt(7);
         }

         int var9 = this.V.nextInt(5);
         if (var9 < 2) {
            var7 |= this.cm() & 0xFF00;
         } else if (var9 < 4) {
            var7 |= var2.cm() & 0xFF00;
         } else {
            var7 |= this.V.nextInt(5) << 8 & 0xFF00;
         }

         var3.s(var7);
      }

      var3.r(var6);
      double var14 = this.a(SharedMonsterAttributes.a).b() + var1.a(SharedMonsterAttributes.a).b() + this.di();
      var3.a(SharedMonsterAttributes.a).a(var14 / 3.0);
      double var15 = this.a(bt).b() + var1.a(bt).b() + this.dj();
      var3.a(bt).a(var15 / 3.0);
      double var11 = this.a(SharedMonsterAttributes.d).b() + var1.a(SharedMonsterAttributes.d).b() + this.dk();
      var3.a(SharedMonsterAttributes.d).a(var11 / 3.0);
      return var3;
   }

   @Override
   public IEntityLivingData a(DifficultyInstance var1, IEntityLivingData var2) {
      var2 = super.a(var1, var2);
      int var3 = 0;
      int var4 = 0;
      if (var2 instanceof EntityHorse.GroupData) {
         var3 = ((EntityHorse.GroupData)var2).a;
         var4 = ((EntityHorse.GroupData)var2).b & 0xFF | this.V.nextInt(5) << 8;
      } else {
         if (this.V.nextInt(10) == 0) {
            var3 = 1;
         } else {
            int var5 = this.V.nextInt(7);
            int var6 = this.V.nextInt(5);
            var3 = 0;
            var4 = var5 | var6 << 8;
         }

         var2 = new EntityHorse.GroupData(var3, var4);
      }

      this.r(var3);
      this.s(var4);
      if (this.V.nextInt(5) == 0) {
         this.b(-24000);
      }

      if (var3 != 4 && var3 != 3) {
         this.a(SharedMonsterAttributes.a).a((double)this.di());
         if (var3 == 0) {
            this.a(SharedMonsterAttributes.d).a(this.dk());
         } else {
            this.a(SharedMonsterAttributes.d).a(0.175F);
         }
      } else {
         this.a(SharedMonsterAttributes.a).a(15.0);
         this.a(SharedMonsterAttributes.d).a(0.2F);
      }

      if (var3 != 2 && var3 != 1) {
         this.a(bt).a(this.dj());
      } else {
         this.a(bt).a(0.5);
      }

      this.i(this.bu());
      return var2;
   }

   public float p(float var1) {
      return this.bI + (this.bH - this.bI) * var1;
   }

   public float q(float var1) {
      return this.bK + (this.bJ - this.bK) * var1;
   }

   public float r(float var1) {
      return this.bM + (this.bL - this.bM) * var1;
   }

   public void v(int var1) {
      if (this.cG()) {
         if (var1 < 0) {
            var1 = 0;
         } else {
            this.bG = true;
            this.dh();
         }

         if (var1 >= 90) {
            this.br = 1.0F;
         } else {
            this.br = 0.4F + 0.4F * var1 / 90.0F;
         }
      }
   }

   protected void t(boolean var1) {
      EnumParticleTypes var2 = var1 ? EnumParticleTypes.I : EnumParticleTypes.l;

      for (int var3 = 0; var3 < 7; var3++) {
         double var4 = this.V.nextGaussian() * 0.02;
         double var6 = this.V.nextGaussian() * 0.02;
         double var8 = this.V.nextGaussian() * 0.02;
         this.o
            .a(
               var2,
               this.s + this.V.nextFloat() * this.J * 2.0F - this.J,
               this.t + 0.5 + this.V.nextFloat() * this.K,
               this.u + this.V.nextFloat() * this.J * 2.0F - this.J,
               var4,
               var6,
               var8
            );
      }
   }

   @Override
   public void a(byte var1) {
      if (var1 == 7) {
         this.t(true);
      } else if (var1 == 6) {
         this.t(false);
      } else {
         super.a(var1);
      }
   }

   @Override
   public void al() {
      super.al();
      if (this.bK > 0.0F) {
         float var1 = MathHelper.a(this.aI * (float) Math.PI / 180.0F);
         float var2 = MathHelper.b(this.aI * (float) Math.PI / 180.0F);
         float var3 = 0.7F * this.bK;
         float var4 = 0.15F * this.bK;
         this.l.b(this.s + var3 * var1, this.t + this.an() + this.l.am() + var4, this.u - var3 * var2);
         if (this.l instanceof EntityLivingBase) {
            ((EntityLivingBase)this.l).aI = this.aI;
         }
      }
   }

   private float di() {
      return 15.0F + this.V.nextInt(8) + this.V.nextInt(9);
   }

   private double dj() {
      return 0.4F + this.V.nextDouble() * 0.2 + this.V.nextDouble() * 0.2 + this.V.nextDouble() * 0.2;
   }

   private double dk() {
      return (0.45F + this.V.nextDouble() * 0.3 + this.V.nextDouble() * 0.3 + this.V.nextDouble() * 0.3) * 0.25;
   }

   public static boolean a(Item var0) {
      return var0 == Items.ck || var0 == Items.cl || var0 == Items.cm;
   }

   @Override
   public boolean k_() {
      return false;
   }

   @Override
   public float aS() {
      return this.K;
   }

   @Override
   public boolean d(int var1, ItemStack var2) {
      if (var1 == 499 && this.cP()) {
         if (var2 == null && this.cw()) {
            this.o(false);
            this.da();
            return true;
         }

         if (var2 != null && var2.b() == Item.a(Blocks.ae) && !this.cw()) {
            this.o(true);
            this.da();
            return true;
         }
      }

      int var3 = var1 - 400;
      if (var3 >= 0 && var3 < 2 && var3 < this.bE.o_()) {
         if (var3 == 0 && var2 != null && var2.b() != Items.aA) {
            return false;
         } else if (var3 != 1 || (var2 == null || a(var2.b())) && this.cO()) {
            this.bE.a(var3, var2);
            this.db();
            return true;
         } else {
            return false;
         }
      } else {
         int var4 = var1 - 500 + 2;
         if (var4 >= 2 && var4 < this.bE.o_()) {
            this.bE.a(var4, var2);
            return true;
         } else {
            return false;
         }
      }
   }

   public static class GroupData implements IEntityLivingData {
      public int a;
      public int b;

      public GroupData(int var1, int var2) {
         this.a = var1;
         this.b = var2;
      }
   }
}
