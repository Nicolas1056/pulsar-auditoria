package net.minecraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityLivingBase extends Entity {
   private static final UUID a = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
   private static final AttributeModifier b = new AttributeModifier(a, "Sprinting speed boost", 0.3F, 2).a(false);
   private BaseAttributeMap c;
   private final CombatTracker f = new CombatTracker(this);
   private final Map<Integer, PotionEffect> g = Maps.newHashMap();
   private final ItemStack[] h = new ItemStack[5];
   public boolean ar;
   public int as;
   public int at;
   public int au;
   public int av;
   public float aw;
   public int ax;
   public float ay;
   public float az;
   public float aA;
   public float aB;
   public float aC;
   public int aD = 20;
   public float aE;
   public float aF;
   public float aG;
   public float aH;
   public float aI;
   public float aJ;
   public float aK;
   public float aL;
   public float aM = 0.02F;
   protected EntityPlayer aN;
   protected int aO;
   protected boolean aP;
   protected int aQ;
   protected float aR;
   protected float aS;
   protected float aT;
   protected float aU;
   protected float aV;
   protected int aW;
   protected float aX;
   protected boolean aY;
   public float aZ;
   public float ba;
   protected float bb;
   protected int bc;
   protected double bd;
   protected double be;
   protected double bf;
   protected double bg;
   protected double bh;
   private boolean i = true;
   private EntityLivingBase bi;
   private int bj;
   private EntityLivingBase bk;
   private int bl;
   private float bm;
   private int bn;
   private float bo;

   @Override
   public void G() {
      this.a(DamageSource.j, Float.MAX_VALUE);
   }

   public EntityLivingBase(World var1) {
      super(var1);
      this.aX();
      this.i(this.bu());
      this.k = true;
      this.aH = (float)((Math.random() + 1.0) * 0.01F);
      this.b(this.s, this.t, this.u);
      this.aG = (float)Math.random() * 12398.0F;
      this.y = (float)(Math.random() * (float) Math.PI * 2.0);
      this.aK = this.y;
      this.S = 0.6F;
   }

   @Override
   protected void h() {
      this.ac.a(7, Integer.valueOf(0));
      this.ac.a(8, Byte.valueOf((byte)0));
      this.ac.a(9, Byte.valueOf((byte)0));
      this.ac.a(6, 1.0F);
   }

   protected void aX() {
      this.by().b(SharedMonsterAttributes.a);
      this.by().b(SharedMonsterAttributes.c);
      this.by().b(SharedMonsterAttributes.d);
   }

   @Override
   protected void a(double var1, boolean var3, Block var4, BlockPos var5) {
      if (!this.V()) {
         this.W();
      }

      if (!this.o.D && this.O > 3.0F && var3) {
         IBlockState var6 = this.o.p(var5);
         Block var7 = var6.c();
         float var8 = MathHelper.f(this.O - 3.0F);
         if (var7.t() != Material.a) {
            double var9 = Math.min(0.2F + var8 / 15.0F, 10.0F);
            if (var9 > 2.5) {
               var9 = 2.5;
            }

            int var11 = (int)(150.0 * var9);
            ((WorldServer)this.o).a(EnumParticleTypes.M, this.s, this.t, this.u, var11, 0.0, 0.0, 0.0, 0.15F, Block.f(var6));
         }
      }

      super.a(var1, var3, var4, var5);
   }

   public boolean aY() {
      return false;
   }

   @Override
   public void K() {
      this.ay = this.az;
      super.K();
      this.o.B.a("livingEntityBaseTick");
      boolean var1 = this instanceof EntityPlayer;
      if (this.ai()) {
         if (this.aj()) {
            this.a(DamageSource.e, 1.0F);
         } else if (var1 && !this.o.af().a(this.aR())) {
            double var2 = this.o.af().a(this) + this.o.af().m();
            if (var2 < 0.0) {
               this.a(DamageSource.e, (float)Math.max(1, MathHelper.c(-var2 * this.o.af().n())));
            }
         }
      }

      if (this.T() || this.o.D) {
         this.N();
      }

      boolean var7 = var1 && ((EntityPlayer)this).bA.a;
      if (this.ai()) {
         if (this.a(Material.h)) {
            if (!this.aY() && !this.k(Potion.o.H) && !var7) {
               this.h(this.j(this.az()));
               if (this.az() == -20) {
                  this.h(0);

                  for (int var3 = 0; var3 < 8; var3++) {
                     float var4 = this.V.nextFloat() - this.V.nextFloat();
                     float var5 = this.V.nextFloat() - this.V.nextFloat();
                     float var6 = this.V.nextFloat() - this.V.nextFloat();
                     this.o.a(EnumParticleTypes.e, this.s + var4, this.t + var5, this.u + var6, this.v, this.w, this.x);
                  }

                  this.a(DamageSource.f, 2.0F);
               }
            }

            if (!this.o.D && this.au() && this.m instanceof EntityLivingBase) {
               this.a(null);
            }
         } else {
            this.h(300);
         }
      }

      if (this.ai() && this.U()) {
         this.N();
      }

      this.aE = this.aF;
      if (this.au > 0) {
         this.au--;
      }

      if (this.Z > 0 && !(this instanceof EntityPlayerMP)) {
         this.Z--;
      }

      if (this.bn() <= 0.0F) {
         this.aZ();
      }

      if (this.aO > 0) {
         this.aO--;
      } else {
         this.aN = null;
      }

      if (this.bk != null && !this.bk.ai()) {
         this.bk = null;
      }

      if (this.bi != null) {
         if (!this.bi.ai()) {
            this.b(null);
         } else if (this.W - this.bj > 100) {
            this.b(null);
         }
      }

      this.bi();
      this.aU = this.aT;
      this.aJ = this.aI;
      this.aL = this.aK;
      this.A = this.y;
      this.B = this.z;
      this.o.B.b();
   }

   public boolean j_() {
      return false;
   }

   protected void aZ() {
      this.ax++;
      if (this.ax == 20) {
         if (!this.o.D && (this.aO > 0 || this.bb()) && this.ba() && this.o.Q().b("doMobLoot")) {
            int var1 = this.b(this.aN);

            while (var1 > 0) {
               int var2 = EntityXPOrb.a(var1);
               var1 -= var2;
               this.o.d(new EntityXPOrb(this.o, this.s, this.t, this.u, var2));
            }
         }

         this.J();

         for (int var8 = 0; var8 < 20; var8++) {
            double var9 = this.V.nextGaussian() * 0.02;
            double var4 = this.V.nextGaussian() * 0.02;
            double var6 = this.V.nextGaussian() * 0.02;
            this.o
               .a(
                  EnumParticleTypes.a,
                  this.s + this.V.nextFloat() * this.J * 2.0F - this.J,
                  this.t + this.V.nextFloat() * this.K,
                  this.u + this.V.nextFloat() * this.J * 2.0F - this.J,
                  var9,
                  var4,
                  var6
               );
         }
      }
   }

   protected boolean ba() {
      return !this.j_();
   }

   protected int j(int var1) {
      int var2 = EnchantmentHelper.a((Entity)this);
      return var2 > 0 && this.V.nextInt(var2 + 1) > 0 ? var1 : var1 - 1;
   }

   protected int b(EntityPlayer var1) {
      return 0;
   }

   protected boolean bb() {
      return false;
   }

   public Random bc() {
      return this.V;
   }

   public EntityLivingBase bd() {
      return this.bi;
   }

   public int be() {
      return this.bj;
   }

   public void b(EntityLivingBase var1) {
      this.bi = var1;
      this.bj = this.W;
   }

   public EntityLivingBase bf() {
      return this.bk;
   }

   public int bg() {
      return this.bl;
   }

   public void p(Entity var1) {
      if (var1 instanceof EntityLivingBase) {
         this.bk = (EntityLivingBase)var1;
      } else {
         this.bk = null;
      }

      this.bl = this.W;
   }

   public int bh() {
      return this.aQ;
   }

   @Override
   public void b(NBTTagCompound var1) {
      var1.a("HealF", this.bn());
      var1.a("Health", (short)Math.ceil(this.bn()));
      var1.a("HurtTime", (short)this.au);
      var1.a("HurtByTimestamp", this.bj);
      var1.a("DeathTime", (short)this.ax);
      var1.a("AbsorptionAmount", this.bN());

      for (ItemStack var5 : this.as()) {
         if (var5 != null) {
            this.c.a(var5.B());
         }
      }

      var1.a("Attributes", SharedMonsterAttributes.a(this.by()));

      for (ItemStack var12 : this.as()) {
         if (var12 != null) {
            this.c.b(var12.B());
         }
      }

      if (!this.g.isEmpty()) {
         NBTTagList var7 = new NBTTagList();

         for (PotionEffect var11 : this.g.values()) {
            var7.a(var11.a(new NBTTagCompound()));
         }

         var1.a("ActiveEffects", var7);
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      this.m(var1.h("AbsorptionAmount"));
      if (var1.b("Attributes", 9) && this.o != null && !this.o.D) {
         SharedMonsterAttributes.a(this.by(), var1.c("Attributes", 10));
      }

      if (var1.b("ActiveEffects", 9)) {
         NBTTagList var2 = var1.c("ActiveEffects", 10);

         for (int var3 = 0; var3 < var2.c(); var3++) {
            NBTTagCompound var4 = var2.b(var3);
            PotionEffect var5 = PotionEffect.b(var4);
            if (var5 != null) {
               this.g.put(var5.a(), var5);
            }
         }
      }

      if (var1.b("HealF", 99)) {
         this.i(var1.h("HealF"));
      } else {
         NBTBase var6 = var1.a("Health");
         if (var6 == null) {
            this.i(this.bu());
         } else if (var6.a() == 5) {
            this.i(((NBTTagFloat)var6).h());
         } else if (var6.a() == 2) {
            this.i(((NBTTagShort)var6).e());
         }
      }

      this.au = var1.e("HurtTime");
      this.ax = var1.e("DeathTime");
      this.bj = var1.f("HurtByTimestamp");
   }

   protected void bi() {
      Iterator var1 = this.g.keySet().iterator();

      while (var1.hasNext()) {
         Integer var2 = (Integer)var1.next();
         PotionEffect var3 = this.g.get(var2);
         if (!var3.a(this)) {
            if (!this.o.D) {
               var1.remove();
               this.b(var3);
            }
         } else if (var3.b() % 600 == 0) {
            this.a(var3, false);
         }
      }

      if (this.i) {
         if (!this.o.D) {
            this.B();
         }

         this.i = false;
      }

      int var11 = this.ac.c(7);
      boolean var12 = this.ac.a(8) > 0;
      if (var11 > 0) {
         boolean var4 = false;
         if (!this.ax()) {
            var4 = this.V.nextBoolean();
         } else {
            var4 = this.V.nextInt(15) == 0;
         }

         if (var12) {
            var4 &= this.V.nextInt(5) == 0;
         }

         if (var4 && var11 > 0) {
            double var5 = (var11 >> 16 & 0xFF) / 255.0;
            double var7 = (var11 >> 8 & 0xFF) / 255.0;
            double var9 = (var11 >> 0 & 0xFF) / 255.0;
            this.o
               .a(
                  var12 ? EnumParticleTypes.q : EnumParticleTypes.p,
                  this.s + (this.V.nextDouble() - 0.5) * this.J,
                  this.t + this.V.nextDouble() * this.K,
                  this.u + (this.V.nextDouble() - 0.5) * this.J,
                  var5,
                  var7,
                  var9
               );
         }
      }
   }

   protected void B() {
      if (this.g.isEmpty()) {
         this.bj();
         this.e(false);
      } else {
         int var1 = PotionHelper.a(this.g.values());
         this.ac.b(8, Byte.valueOf((byte)(PotionHelper.b(this.g.values()) ? 1 : 0)));
         this.ac.b(7, var1);
         this.e(this.k(Potion.p.H));
      }
   }

   protected void bj() {
      this.ac.b(8, (byte)0);
      this.ac.b(7, 0);
   }

   public void bk() {
      Iterator var1 = this.g.keySet().iterator();

      while (var1.hasNext()) {
         Integer var2 = (Integer)var1.next();
         PotionEffect var3 = this.g.get(var2);
         if (!this.o.D) {
            var1.remove();
            this.b(var3);
         }
      }
   }

   public Collection<PotionEffect> bl() {
      return this.g.values();
   }

   public boolean k(int var1) {
      return this.g.containsKey(var1);
   }

   public boolean a(Potion var1) {
      return this.g.containsKey(var1.H);
   }

   public PotionEffect b(Potion var1) {
      return this.g.get(var1.H);
   }

   public void c(PotionEffect var1) {
      if (this.d(var1)) {
         if (this.g.containsKey(var1.a())) {
            this.g.get(var1.a()).a(var1);
            this.a(this.g.get(var1.a()), true);
         } else {
            this.g.put(var1.a(), var1);
            this.a(var1);
         }
      }
   }

   public boolean d(PotionEffect var1) {
      if (this.bz() == EnumCreatureAttribute.b) {
         int var2 = var1.a();
         if (var2 == Potion.l.H || var2 == Potion.u.H) {
            return false;
         }
      }

      return true;
   }

   public boolean bm() {
      return this.bz() == EnumCreatureAttribute.b;
   }

   public void l(int var1) {
      this.g.remove(var1);
   }

   public void m(int var1) {
      PotionEffect var2 = this.g.remove(var1);
      if (var2 != null) {
         this.b(var2);
      }
   }

   protected void a(PotionEffect var1) {
      this.i = true;
      if (!this.o.D) {
         Potion.a[var1.a()].b(this, this.by(), var1.c());
      }
   }

   protected void a(PotionEffect var1, boolean var2) {
      this.i = true;
      if (var2 && !this.o.D) {
         Potion.a[var1.a()].a(this, this.by(), var1.c());
         Potion.a[var1.a()].b(this, this.by(), var1.c());
      }
   }

   protected void b(PotionEffect var1) {
      this.i = true;
      if (!this.o.D) {
         Potion.a[var1.a()].a(this, this.by(), var1.c());
      }
   }

   public void h(float var1) {
      float var2 = this.bn();
      if (var2 > 0.0F) {
         this.i(var2 + var1);
      }
   }

   public final float bn() {
      return this.ac.d(6);
   }

   public void i(float var1) {
      this.ac.b(6, MathHelper.a(var1, 0.0F, this.bu()));
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else if (this.o.D) {
         return false;
      } else {
         this.aQ = 0;
         if (this.bn() <= 0.0F) {
            return false;
         } else if (var1.o() && this.a(Potion.n)) {
            return false;
         } else {
            if ((var1 == DamageSource.n || var1 == DamageSource.o) && this.p(4) != null) {
               this.p(4).a((int)(var2 * 4.0F + this.V.nextFloat() * var2 * 2.0F), this);
               var2 *= 0.75F;
            }

            this.aB = 1.5F;
            boolean var3 = true;
            if (this.Z > this.aD / 2.0F) {
               if (var2 <= this.aX) {
                  return false;
               }

               this.d(var1, var2 - this.aX);
               this.aX = var2;
               var3 = false;
            } else {
               this.aX = var2;
               this.Z = this.aD;
               this.d(var1, var2);
               this.au = this.av = 10;
            }

            this.aw = 0.0F;
            Entity var4 = var1.j();
            if (var4 != null) {
               if (var4 instanceof EntityLivingBase) {
                  this.b((EntityLivingBase)var4);
               }

               if (var4 instanceof EntityPlayer) {
                  this.aO = 100;
                  this.aN = (EntityPlayer)var4;
               } else if (var4 instanceof EntityWolf) {
                  EntityWolf var5 = (EntityWolf)var4;
                  if (var5.cl()) {
                     this.aO = 100;
                     this.aN = null;
                  }
               }
            }

            if (var3) {
               this.o.a(this, (byte)2);
               if (var1 != DamageSource.f) {
                  this.ac();
               }

               if (var4 != null) {
                  double var9 = var4.s - this.s;

                  double var7;
                  for (var7 = var4.u - this.u; var9 * var9 + var7 * var7 < 1.0E-4; var7 = (Math.random() - Math.random()) * 0.01) {
                     var9 = (Math.random() - Math.random()) * 0.01;
                  }

                  this.aw = (float)(MathHelper.b(var7, var9) * 180.0 / (float) Math.PI - this.y);
                  this.a(var4, var2, var9, var7);
               } else {
                  this.aw = (int)(Math.random() * 2.0) * 180;
               }
            }

            if (this.bn() <= 0.0F) {
               String var10 = this.bp();
               if (var3 && var10 != null) {
                  this.a(var10, this.bB(), this.bC());
               }

               this.a(var1);
            } else {
               String var11 = this.bo();
               if (var3 && var11 != null) {
                  this.a(var11, this.bB(), this.bC());
               }
            }

            return true;
         }
      }
   }

   public void b(ItemStack var1) {
      this.a("random.break", 0.8F, 0.8F + this.o.s.nextFloat() * 0.4F);

      for (int var2 = 0; var2 < 5; var2++) {
         Vec3 var3 = new Vec3((this.V.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
         var3 = var3.a(-this.z * (float) Math.PI / 180.0F);
         var3 = var3.b(-this.y * (float) Math.PI / 180.0F);
         double var4 = -this.V.nextFloat() * 0.6 - 0.3;
         Vec3 var6 = new Vec3((this.V.nextFloat() - 0.5) * 0.3, var4, 0.6);
         var6 = var6.a(-this.z * (float) Math.PI / 180.0F);
         var6 = var6.b(-this.y * (float) Math.PI / 180.0F);
         var6 = var6.b(this.s, this.t + this.aS(), this.u);
         this.o.a(EnumParticleTypes.K, var6.a, var6.b, var6.c, var3.a, var3.b + 0.05, var3.c, Item.b(var1.b()));
      }
   }

   public void a(DamageSource var1) {
      Entity var2 = var1.j();
      EntityLivingBase var3 = this.bt();
      if (this.aW >= 0 && var3 != null) {
         var3.b(this, this.aW);
      }

      if (var2 != null) {
         var2.a(this);
      }

      this.aP = true;
      this.bs().g();
      if (!this.o.D) {
         int var4 = 0;
         if (var2 instanceof EntityPlayer) {
            var4 = EnchantmentHelper.i((EntityLivingBase)var2);
         }

         if (this.ba() && this.o.Q().b("doMobLoot")) {
            this.b(this.aO > 0, var4);
            this.a(this.aO > 0, var4);
            if (this.aO > 0 && this.V.nextFloat() < 0.025F + var4 * 0.01F) {
               this.bq();
            }
         }
      }

      this.o.a(this, (byte)3);
   }

   protected void a(boolean var1, int var2) {
   }

   public void a(Entity var1, float var2, double var3, double var5) {
      if (!(this.V.nextDouble() < this.a(SharedMonsterAttributes.c).e())) {
         this.ai = true;
         float var7 = MathHelper.a(var3 * var3 + var5 * var5);
         float var8 = 0.4F;
         this.v /= 2.0;
         this.w /= 2.0;
         this.x /= 2.0;
         this.v -= var3 / var7 * var8;
         this.w += var8;
         this.x -= var5 / var7 * var8;
         if (this.w > 0.4F) {
            this.w = 0.4F;
         }
      }
   }

   protected String bo() {
      return "game.neutral.hurt";
   }

   protected String bp() {
      return "game.neutral.die";
   }

   protected void bq() {
   }

   protected void b(boolean var1, int var2) {
   }

   public boolean k_() {
      int var1 = MathHelper.c(this.s);
      int var2 = MathHelper.c(this.aR().b);
      int var3 = MathHelper.c(this.u);
      Block var4 = this.o.p(new BlockPos(var1, var2, var3)).c();
      return (var4 == Blocks.au || var4 == Blocks.bn) && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).v());
   }

   @Override
   public boolean ai() {
      return !this.I && this.bn() > 0.0F;
   }

   @Override
   public void e(float var1, float var2) {
      super.e(var1, var2);
      PotionEffect var3 = this.b(Potion.j);
      float var4 = var3 != null ? var3.c() + 1 : 0.0F;
      int var5 = MathHelper.f((var1 - 3.0F - var4) * var2);
      if (var5 > 0) {
         this.a(this.n(var5), 1.0F, 1.0F);
         this.a(DamageSource.i, (float)var5);
         int var6 = MathHelper.c(this.s);
         int var7 = MathHelper.c(this.t - 0.2F);
         int var8 = MathHelper.c(this.u);
         Block var9 = this.o.p(new BlockPos(var6, var7, var8)).c();
         if (var9.t() != Material.a) {
            Block.SoundType var10 = var9.H;
            this.a(var10.c(), var10.d() * 0.5F, var10.e() * 0.75F);
         }
      }
   }

   protected String n(int var1) {
      return var1 > 4 ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small";
   }

   @Override
   public void ar() {
      this.au = this.av = 10;
      this.aw = 0.0F;
   }

   public int br() {
      int var1 = 0;

      for (ItemStack var5 : this.as()) {
         if (var5 != null && var5.b() instanceof ItemArmor) {
            int var6 = ((ItemArmor)var5.b()).c;
            var1 += var6;
         }
      }

      return var1;
   }

   protected void j(float var1) {
   }

   protected float b(DamageSource var1, float var2) {
      if (!var1.e()) {
         int var3 = 25 - this.br();
         float var4 = var2 * var3;
         this.j(var2);
         var2 = var4 / 25.0F;
      }

      return var2;
   }

   protected float c(DamageSource var1, float var2) {
      if (var1.h()) {
         return var2;
      } else {
         if (this.a(Potion.m) && var1 != DamageSource.j) {
            int var3 = (this.b(Potion.m).c() + 1) * 5;
            int var4 = 25 - var3;
            float var5 = var2 * var4;
            var2 = var5 / 25.0F;
         }

         if (var2 <= 0.0F) {
            return 0.0F;
         } else {
            int var6 = EnchantmentHelper.a(this.as(), var1);
            if (var6 > 20) {
               var6 = 20;
            }

            if (var6 > 0 && var6 <= 20) {
               int var7 = 25 - var6;
               float var8 = var2 * var7;
               var2 = var8 / 25.0F;
            }

            return var2;
         }
      }
   }

   protected void d(DamageSource var1, float var2) {
      if (!this.b(var1)) {
         var2 = this.b(var1, var2);
         var2 = this.c(var1, var2);
         float var7 = Math.max(var2 - this.bN(), 0.0F);
         this.m(this.bN() - (var2 - var7));
         if (var7 != 0.0F) {
            float var4 = this.bn();
            this.i(var4 - var7);
            this.bs().a(var1, var4, var7);
            this.m(this.bN() - var7);
         }
      }
   }

   public CombatTracker bs() {
      return this.f;
   }

   public EntityLivingBase bt() {
      if (this.f.c() != null) {
         return this.f.c();
      } else if (this.aN != null) {
         return this.aN;
      } else {
         return this.bi != null ? this.bi : null;
      }
   }

   public final float bu() {
      return (float)this.a(SharedMonsterAttributes.a).e();
   }

   public final int bv() {
      return this.ac.a(9);
   }

   public final void o(int var1) {
      this.ac.b(9, (byte)var1);
   }

   private int n() {
      if (this.a(Potion.e)) {
         return 6 - (1 + this.b(Potion.e).c()) * 1;
      } else {
         return this.a(Potion.f) ? 6 + (1 + this.b(Potion.f).c()) * 2 : 6;
      }
   }

   public void bw() {
      if (!this.ar || this.as >= this.n() / 2 || this.as < 0) {
         this.as = -1;
         this.ar = true;
         if (this.o instanceof WorldServer) {
            ((WorldServer)this.o).s().a(this, new S0BPacketAnimation(this, 0));
         }
      }
   }

   @Override
   public void a(byte var1) {
      if (var1 == 2) {
         this.aB = 1.5F;
         this.Z = this.aD;
         this.au = this.av = 10;
         this.aw = 0.0F;
         String var2 = this.bo();
         if (var2 != null) {
            this.a(this.bo(), this.bB(), (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F);
         }

         this.a(DamageSource.k, 0.0F);
      } else if (var1 == 3) {
         String var3 = this.bp();
         if (var3 != null) {
            this.a(this.bp(), this.bB(), (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F);
         }

         this.i(0.0F);
         this.a(DamageSource.k);
      } else {
         super.a(var1);
      }
   }

   @Override
   protected void O() {
      this.a(DamageSource.j, 4.0F);
   }

   protected void bx() {
      int var1 = this.n();
      if (this.ar) {
         this.as++;
         if (this.as >= var1) {
            this.as = 0;
            this.ar = false;
         }
      } else {
         this.as = 0;
      }

      this.az = (float)this.as / var1;
   }

   public IAttributeInstance a(IAttribute var1) {
      return this.by().a(var1);
   }

   public BaseAttributeMap by() {
      if (this.c == null) {
         this.c = new ServersideAttributeMap();
      }

      return this.c;
   }

   public EnumCreatureAttribute bz() {
      return EnumCreatureAttribute.a;
   }

   public abstract ItemStack bA();

   public abstract ItemStack p(int var1);

   public abstract ItemStack q(int var1);

   @Override
   public abstract void c(int var1, ItemStack var2);

   @Override
   public void d(boolean var1) {
      super.d(var1);
      IAttributeInstance var2 = this.a(SharedMonsterAttributes.d);
      if (var2.a(a) != null) {
         var2.c(b);
      }

      if (var1) {
         var2.b(b);
      }
   }

   @Override
   public abstract ItemStack[] as();

   protected float bB() {
      return 1.0F;
   }

   protected float bC() {
      return this.j_() ? (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.5F : (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F;
   }

   protected boolean bD() {
      return this.bn() <= 0.0F;
   }

   public void q(Entity var1) {
      double var3 = var1.s;
      double var5 = var1.aR().b + var1.K;
      double var7 = var1.u;
      byte var9 = 1;

      for (int var10 = -var9; var10 <= var9; var10++) {
         for (int var11 = -var9; var11 < var9; var11++) {
            if (var10 != 0 || var11 != 0) {
               int var12 = (int)(this.s + var10);
               int var13 = (int)(this.u + var11);
               AxisAlignedBB var2 = this.aR().c(var10, 1.0, var11);
               if (this.o.a(var2).isEmpty()) {
                  if (World.a(this.o, new BlockPos(var12, (int)this.t, var13))) {
                     this.a(this.s + var10, this.t + 1.0, this.u + var11);
                     return;
                  }

                  if (World.a(this.o, new BlockPos(var12, (int)this.t - 1, var13))
                     || this.o.p(new BlockPos(var12, (int)this.t - 1, var13)).c().t() == Material.h) {
                     var3 = this.s + var10;
                     var5 = this.t + 1.0;
                     var7 = this.u + var11;
                  }
               }
            }
         }
      }

      this.a(var3, var5, var7);
   }

   @Override
   public boolean aO() {
      return false;
   }

   protected float bE() {
      return 0.42F;
   }

   protected void bF() {
      this.w = this.bE();
      if (this.a(Potion.j)) {
         this.w = this.w + (this.b(Potion.j).c() + 1) * 0.1F;
      }

      if (this.aw()) {
         float var1 = this.y * (float) (Math.PI / 180.0);
         this.v = this.v - MathHelper.a(var1) * 0.2F;
         this.x = this.x + MathHelper.b(var1) * 0.2F;
      }

      this.ai = true;
   }

   protected void bG() {
      this.w += 0.04F;
   }

   protected void bH() {
      this.w += 0.04F;
   }

   public void g(float var1, float var2) {
      if (this.bM()) {
         if (!this.V() || this instanceof EntityPlayer && ((EntityPlayer)this).bA.b) {
            if (!this.ab() || this instanceof EntityPlayer && ((EntityPlayer)this).bA.b) {
               float var9 = 0.91F;
               if (this.C) {
                  var9 = this.o.p(new BlockPos(MathHelper.c(this.s), MathHelper.c(this.aR().b) - 1, MathHelper.c(this.u))).c().L * 0.91F;
               }

               float var4 = 0.16277136F / (var9 * var9 * var9);
               float var12;
               if (this.C) {
                  var12 = this.bI() * var4;
               } else {
                  var12 = this.aM;
               }

               this.a(var1, var2, var12);
               var9 = 0.91F;
               if (this.C) {
                  var9 = this.o.p(new BlockPos(MathHelper.c(this.s), MathHelper.c(this.aR().b) - 1, MathHelper.c(this.u))).c().L * 0.91F;
               }

               if (this.k_()) {
                  float var14 = 0.15F;
                  this.v = MathHelper.a(this.v, (double)(-var14), (double)var14);
                  this.x = MathHelper.a(this.x, (double)(-var14), (double)var14);
                  this.O = 0.0F;
                  if (this.w < -0.15) {
                     this.w = -0.15;
                  }

                  boolean var15 = this.av() && this instanceof EntityPlayer;
                  if (var15 && this.w < 0.0) {
                     this.w = 0.0;
                  }
               }

               this.d(this.v, this.w, this.x);
               if (this.D && this.k_()) {
                  this.w = 0.2;
               }

               if (this.o.D && (!this.o.e(new BlockPos((int)this.s, 0, (int)this.u)) || !this.o.f(new BlockPos((int)this.s, 0, (int)this.u)).o())) {
                  if (this.t > 0.0) {
                     this.w = -0.1;
                  } else {
                     this.w = 0.0;
                  }
               } else {
                  this.w -= 0.08;
               }

               this.w *= 0.98F;
               this.v *= var9;
               this.x *= var9;
            } else {
               double var8 = this.t;
               this.a(var1, var2, 0.02F);
               this.d(this.v, this.w, this.x);
               this.v *= 0.5;
               this.w *= 0.5;
               this.x *= 0.5;
               this.w -= 0.02;
               if (this.D && this.c(this.v, this.w + 0.6F - this.t + var8, this.x)) {
                  this.w = 0.3F;
               }
            }
         } else {
            double var3 = this.t;
            float var5 = 0.8F;
            float var6 = 0.02F;
            float var7 = EnchantmentHelper.b((Entity)this);
            if (var7 > 3.0F) {
               var7 = 3.0F;
            }

            if (!this.C) {
               var7 *= 0.5F;
            }

            if (var7 > 0.0F) {
               var5 += (0.54600006F - var5) * var7 / 3.0F;
               var6 += (this.bI() * 1.0F - var6) * var7 / 3.0F;
            }

            this.a(var1, var2, var6);
            this.d(this.v, this.w, this.x);
            this.v *= var5;
            this.w *= 0.8F;
            this.x *= var5;
            this.w -= 0.02;
            if (this.D && this.c(this.v, this.w + 0.6F - this.t + var3, this.x)) {
               this.w = 0.3F;
            }
         }
      }

      this.aA = this.aB;
      double var11 = this.s - this.p;
      double var13 = this.u - this.r;
      float var16 = MathHelper.a(var11 * var11 + var13 * var13) * 4.0F;
      if (var16 > 1.0F) {
         var16 = 1.0F;
      }

      this.aB = this.aB + (var16 - this.aB) * 0.4F;
      this.aC = this.aC + this.aB;
   }

   public float bI() {
      return this.bm;
   }

   public void k(float var1) {
      this.bm = var1;
   }

   public boolean r(Entity var1) {
      this.p(var1);
      return false;
   }

   public boolean bJ() {
      return false;
   }

   @Override
   public void t_() {
      super.t_();
      if (!this.o.D) {
         int var1 = this.bv();
         if (var1 > 0) {
            if (this.at <= 0) {
               this.at = 20 * (30 - var1);
            }

            this.at--;
            if (this.at <= 0) {
               this.o(var1 - 1);
            }
         }

         for (int var2 = 0; var2 < 5; var2++) {
            ItemStack var3 = this.h[var2];
            ItemStack var4 = this.p(var2);
            if (!ItemStack.b(var4, var3)) {
               ((WorldServer)this.o).s().a(this, new S04PacketEntityEquipment(this.F(), var2, var4));
               if (var3 != null) {
                  this.c.a(var3.B());
               }

               if (var4 != null) {
                  this.c.b(var4.B());
               }

               this.h[var2] = var4 == null ? null : var4.k();
            }
         }

         if (this.W % 20 == 0) {
            this.bs().g();
         }
      }

      this.m();
      double var9 = this.s - this.p;
      double var10 = this.u - this.r;
      float var5 = (float)(var9 * var9 + var10 * var10);
      float var6 = this.aI;
      float var7 = 0.0F;
      this.aR = this.aS;
      float var8 = 0.0F;
      if (var5 > 0.0025000002F) {
         var8 = 1.0F;
         var7 = (float)Math.sqrt(var5) * 3.0F;
         var6 = (float)MathHelper.b(var10, var9) * 180.0F / (float) Math.PI - 90.0F;
      }

      if (this.az > 0.0F) {
         var6 = this.y;
      }

      if (!this.C) {
         var8 = 0.0F;
      }

      this.aS = this.aS + (var8 - this.aS) * 0.3F;
      this.o.B.a("headTurn");
      var7 = this.h(var6, var7);
      this.o.B.b();
      this.o.B.a("rangeChecks");

      while (this.y - this.A < -180.0F) {
         this.A -= 360.0F;
      }

      while (this.y - this.A >= 180.0F) {
         this.A += 360.0F;
      }

      while (this.aI - this.aJ < -180.0F) {
         this.aJ -= 360.0F;
      }

      while (this.aI - this.aJ >= 180.0F) {
         this.aJ += 360.0F;
      }

      while (this.z - this.B < -180.0F) {
         this.B -= 360.0F;
      }

      while (this.z - this.B >= 180.0F) {
         this.B += 360.0F;
      }

      while (this.aK - this.aL < -180.0F) {
         this.aL -= 360.0F;
      }

      while (this.aK - this.aL >= 180.0F) {
         this.aL += 360.0F;
      }

      this.o.B.b();
      this.aT += var7;
   }

   protected float h(float var1, float var2) {
      float var3 = MathHelper.g(var1 - this.aI);
      this.aI += var3 * 0.3F;
      float var4 = MathHelper.g(this.y - this.aI);
      boolean var5 = var4 < -90.0F || var4 >= 90.0F;
      if (var4 < -75.0F) {
         var4 = -75.0F;
      }

      if (var4 >= 75.0F) {
         var4 = 75.0F;
      }

      this.aI = this.y - var4;
      if (var4 * var4 > 2500.0F) {
         this.aI += var4 * 0.2F;
      }

      if (var5) {
         var2 *= -1.0F;
      }

      return var2;
   }

   @Override
   public void m() {
      if (this.bn > 0) {
         this.bn--;
      }

      if (this.bc > 0) {
         double var1 = this.s + (this.bd - this.s) / this.bc;
         double var3 = this.t + (this.be - this.t) / this.bc;
         double var5 = this.u + (this.bf - this.u) / this.bc;
         double var7 = MathHelper.g(this.bg - this.y);
         this.y = (float)(this.y + var7 / this.bc);
         this.z = (float)(this.z + (this.bh - this.z) / this.bc);
         this.bc--;
         this.b(var1, var3, var5);
         this.b(this.y, this.z);
      } else if (!this.bM()) {
         this.v *= 0.98;
         this.w *= 0.98;
         this.x *= 0.98;
      }

      if (Math.abs(this.v) < 0.005) {
         this.v = 0.0;
      }

      if (Math.abs(this.w) < 0.005) {
         this.w = 0.0;
      }

      if (Math.abs(this.x) < 0.005) {
         this.x = 0.0;
      }

      this.o.B.a("ai");
      if (this.bD()) {
         this.aY = false;
         this.aZ = 0.0F;
         this.ba = 0.0F;
         this.bb = 0.0F;
      } else if (this.bM()) {
         this.o.B.a("newAi");
         this.bK();
         this.o.B.b();
      }

      this.o.B.b();
      this.o.B.a("jump");
      if (this.aY) {
         if (this.V()) {
            this.bG();
         } else if (this.ab()) {
            this.bH();
         } else if (this.C && this.bn == 0) {
            this.bF();
            this.bn = 10;
         }
      } else {
         this.bn = 0;
      }

      this.o.B.b();
      this.o.B.a("travel");
      this.aZ *= 0.98F;
      this.ba *= 0.98F;
      this.bb *= 0.9F;
      this.g(this.aZ, this.ba);
      this.o.B.b();
      this.o.B.a("push");
      if (!this.o.D) {
         this.bL();
      }

      this.o.B.b();
   }

   protected void bK() {
   }

   protected void bL() {
      List var1 = this.o.a(this, this.aR().b(0.2F, 0.0, 0.2F), Predicates.and(EntitySelectors.d, new Predicate<Entity>() {
         public boolean a(Entity var1) {
            return var1.ae();
         }
      }));
      if (!var1.isEmpty()) {
         for (int var2 = 0; var2 < var1.size(); var2++) {
            Entity var3 = (Entity)var1.get(var2);
            this.s(var3);
         }
      }
   }

   protected void s(Entity var1) {
      var1.i(this);
   }

   @Override
   public void a(Entity var1) {
      if (this.m != null && var1 == null) {
         if (!this.o.D) {
            this.q(this.m);
         }

         if (this.m != null) {
            this.m.l = null;
         }

         this.m = null;
      } else {
         super.a(var1);
      }
   }

   @Override
   public void ak() {
      super.ak();
      this.aR = this.aS;
      this.aS = 0.0F;
      this.O = 0.0F;
   }

   @Override
   public void a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.bd = var1;
      this.be = var3;
      this.bf = var5;
      this.bg = var7;
      this.bh = var8;
      this.bc = var9;
   }

   public void i(boolean var1) {
      this.aY = var1;
   }

   public void a(Entity var1, int var2) {
      if (!var1.I && !this.o.D) {
         EntityTracker var3 = ((WorldServer)this.o).s();
         if (var1 instanceof EntityItem) {
            var3.a(var1, new S0DPacketCollectItem(var1.F(), this.F()));
         }

         if (var1 instanceof EntityArrow) {
            var3.a(var1, new S0DPacketCollectItem(var1.F(), this.F()));
         }

         if (var1 instanceof EntityXPOrb) {
            var3.a(var1, new S0DPacketCollectItem(var1.F(), this.F()));
         }
      }
   }

   public boolean t(Entity var1) {
      return this.o.a(new Vec3(this.s, this.t + this.aS(), this.u), new Vec3(var1.s, var1.t + var1.aS(), var1.u)) == null;
   }

   @Override
   public Vec3 ap() {
      return this.d(1.0F);
   }

   @Override
   public Vec3 d(float var1) {
      if (var1 == 1.0F) {
         return this.f(this.z, this.aK);
      } else {
         float var2 = this.B + (this.z - this.B) * var1;
         float var3 = this.aL + (this.aK - this.aL) * var1;
         return this.f(var2, var3);
      }
   }

   public float l(float var1) {
      float var2 = this.az - this.ay;
      if (var2 < 0.0F) {
         var2++;
      }

      return this.ay + var2 * var1;
   }

   public boolean bM() {
      return !this.o.D;
   }

   @Override
   public boolean ad() {
      return !this.I;
   }

   @Override
   public boolean ae() {
      return !this.I;
   }

   @Override
   protected void ac() {
      this.G = this.V.nextDouble() >= this.a(SharedMonsterAttributes.c).e();
   }

   @Override
   public float aC() {
      return this.aK;
   }

   @Override
   public void f(float var1) {
      this.aK = var1;
   }

   @Override
   public void g(float var1) {
      this.aI = var1;
   }

   public float bN() {
      return this.bo;
   }

   public void m(float var1) {
      if (var1 < 0.0F) {
         var1 = 0.0F;
      }

      this.bo = var1;
   }

   public Team bO() {
      return this.o.Z().h(this.aK().toString());
   }

   public boolean c(EntityLivingBase var1) {
      return this.a(var1.bO());
   }

   public boolean a(Team var1) {
      return this.bO() != null ? this.bO().a(var1) : false;
   }

   public void h_() {
   }

   public void j() {
   }

   protected void bP() {
      this.i = true;
   }
}
