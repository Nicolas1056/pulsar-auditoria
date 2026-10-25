package net.minecraft.entity;

import java.util.UUID;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityLiving extends EntityLivingBase {
   public int a_;
   protected int b_;
   private EntityLookHelper a;
   protected EntityMoveHelper f;
   protected EntityJumpHelper g;
   private EntityBodyHelper b;
   protected PathNavigate h;
   protected final EntityAITasks i;
   protected final EntityAITasks bi;
   private EntityLivingBase c;
   private EntitySenses bk;
   private ItemStack[] bl = new ItemStack[5];
   protected float[] bj = new float[5];
   private boolean bm;
   private boolean bn;
   private boolean bo;
   private Entity bp;
   private NBTTagCompound bq;

   public EntityLiving(World var1) {
      super(var1);
      this.i = new EntityAITasks(var1 != null && var1.B != null ? var1.B : null);
      this.bi = new EntityAITasks(var1 != null && var1.B != null ? var1.B : null);
      this.a = new EntityLookHelper(this);
      this.f = new EntityMoveHelper(this);
      this.g = new EntityJumpHelper(this);
      this.b = new EntityBodyHelper(this);
      this.h = this.b(var1);
      this.bk = new EntitySenses(this);

      for (int var2 = 0; var2 < this.bj.length; var2++) {
         this.bj[var2] = 0.085F;
      }
   }

   @Override
   protected void aX() {
      super.aX();
      this.by().b(SharedMonsterAttributes.b).a(16.0);
   }

   protected PathNavigate b(World var1) {
      return new PathNavigateGround(this, var1);
   }

   public EntityLookHelper p() {
      return this.a;
   }

   public EntityMoveHelper q() {
      return this.f;
   }

   public EntityJumpHelper r() {
      return this.g;
   }

   public PathNavigate s() {
      return this.h;
   }

   public EntitySenses t() {
      return this.bk;
   }

   public EntityLivingBase u() {
      return this.c;
   }

   public void d(EntityLivingBase var1) {
      this.c = var1;
   }

   public boolean a(Class<? extends EntityLivingBase> var1) {
      return var1 != EntityGhast.class;
   }

   public void v() {
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(15, Byte.valueOf((byte)0));
   }

   public int w() {
      return 80;
   }

   public void x() {
      String var1 = this.z();
      if (var1 != null) {
         this.a(var1, this.bB(), this.bC());
      }
   }

   @Override
   public void K() {
      super.K();
      this.o.B.a("mobBaseTick");
      if (this.ai() && this.V.nextInt(1000) < this.a_++) {
         this.a_ = -this.w();
         this.x();
      }

      this.o.B.b();
   }

   @Override
   protected int b(EntityPlayer var1) {
      if (this.b_ > 0) {
         int var2 = this.b_;
         ItemStack[] var3 = this.as();

         for (int var4 = 0; var4 < var3.length; var4++) {
            if (var3[var4] != null && this.bj[var4] <= 1.0F) {
               var2 += 1 + this.V.nextInt(3);
            }
         }

         return var2;
      } else {
         return this.b_;
      }
   }

   public void y() {
      if (this.o.D) {
         for (int var1 = 0; var1 < 20; var1++) {
            double var2 = this.V.nextGaussian() * 0.02;
            double var4 = this.V.nextGaussian() * 0.02;
            double var6 = this.V.nextGaussian() * 0.02;
            double var8 = 10.0;
            this.o
               .a(
                  EnumParticleTypes.a,
                  this.s + this.V.nextFloat() * this.J * 2.0F - this.J - var2 * var8,
                  this.t + this.V.nextFloat() * this.K - var4 * var8,
                  this.u + this.V.nextFloat() * this.J * 2.0F - this.J - var6 * var8,
                  var2,
                  var4,
                  var6
               );
         }
      } else {
         this.o.a(this, (byte)20);
      }
   }

   @Override
   public void a(byte var1) {
      if (var1 == 20) {
         this.y();
      } else {
         super.a(var1);
      }
   }

   @Override
   public void t_() {
      super.t_();
      if (!this.o.D) {
         this.ca();
      }
   }

   @Override
   protected float h(float var1, float var2) {
      this.b.a();
      return var2;
   }

   protected String z() {
      return null;
   }

   protected Item A() {
      return null;
   }

   @Override
   protected void b(boolean var1, int var2) {
      Item var3 = this.A();
      if (var3 != null) {
         int var4 = this.V.nextInt(3);
         if (var2 > 0) {
            var4 += this.V.nextInt(var2 + 1);
         }

         for (int var5 = 0; var5 < var4; var5++) {
            this.a(var3, 1);
         }
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("CanPickUpLoot", this.bY());
      var1.a("PersistenceRequired", this.bn);
      NBTTagList var2 = new NBTTagList();

      for (int var3 = 0; var3 < this.bl.length; var3++) {
         NBTTagCompound var4 = new NBTTagCompound();
         if (this.bl[var3] != null) {
            this.bl[var3].b(var4);
         }

         var2.a(var4);
      }

      var1.a("Equipment", var2);
      NBTTagList var6 = new NBTTagList();

      for (int var7 = 0; var7 < this.bj.length; var7++) {
         var6.a(new NBTTagFloat(this.bj[var7]));
      }

      var1.a("DropChances", var6);
      var1.a("Leashed", this.bo);
      if (this.bp != null) {
         NBTTagCompound var8 = new NBTTagCompound();
         if (this.bp instanceof EntityLivingBase) {
            var8.a("UUIDMost", this.bp.aK().getMostSignificantBits());
            var8.a("UUIDLeast", this.bp.aK().getLeastSignificantBits());
         } else if (this.bp instanceof EntityHanging) {
            BlockPos var5 = ((EntityHanging)this.bp).n();
            var8.a("X", var5.n());
            var8.a("Y", var5.o());
            var8.a("Z", var5.p());
         }

         var1.a("Leash", var8);
      }

      if (this.ce()) {
         var1.a("NoAI", this.ce());
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      if (var1.b("CanPickUpLoot", 1)) {
         this.j(var1.n("CanPickUpLoot"));
      }

      this.bn = var1.n("PersistenceRequired");
      if (var1.b("Equipment", 9)) {
         NBTTagList var2 = var1.c("Equipment", 10);

         for (int var3 = 0; var3 < this.bl.length; var3++) {
            this.bl[var3] = ItemStack.a(var2.b(var3));
         }
      }

      if (var1.b("DropChances", 9)) {
         NBTTagList var4 = var1.c("DropChances", 5);

         for (int var5 = 0; var5 < var4.c(); var5++) {
            this.bj[var5] = var4.e(var5);
         }
      }

      this.bo = var1.n("Leashed");
      if (this.bo && var1.b("Leash", 10)) {
         this.bq = var1.m("Leash");
      }

      this.k(var1.n("NoAI"));
   }

   public void n(float var1) {
      this.ba = var1;
   }

   @Override
   public void k(float var1) {
      super.k(var1);
      this.n(var1);
   }

   @Override
   public void m() {
      super.m();
      this.o.B.a("looting");
      if (!this.o.D && this.bY() && !this.aP && this.o.Q().b("mobGriefing")) {
         for (EntityItem var3 : this.o.a(EntityItem.class, this.aR().b(1.0, 0.0, 1.0))) {
            if (!var3.I && var3.l() != null && !var3.s()) {
               this.a(var3);
            }
         }
      }

      this.o.B.b();
   }

   protected void a(EntityItem var1) {
      ItemStack var2 = var1.l();
      int var3 = c(var2);
      if (var3 > -1) {
         boolean var4 = true;
         ItemStack var5 = this.p(var3);
         if (var5 != null) {
            if (var3 == 0) {
               if (var2.b() instanceof ItemSword && !(var5.b() instanceof ItemSword)) {
                  var4 = true;
               } else if (var2.b() instanceof ItemSword && var5.b() instanceof ItemSword) {
                  ItemSword var8 = (ItemSword)var2.b();
                  ItemSword var10 = (ItemSword)var5.b();
                  if (var8.g() != var10.g()) {
                     var4 = var8.g() > var10.g();
                  } else {
                     var4 = var2.i() > var5.i() || var2.n() && !var5.n();
                  }
               } else if (var2.b() instanceof ItemBow && var5.b() instanceof ItemBow) {
                  var4 = var2.n() && !var5.n();
               } else {
                  var4 = false;
               }
            } else if (var2.b() instanceof ItemArmor && !(var5.b() instanceof ItemArmor)) {
               var4 = true;
            } else if (var2.b() instanceof ItemArmor && var5.b() instanceof ItemArmor) {
               ItemArmor var6 = (ItemArmor)var2.b();
               ItemArmor var7 = (ItemArmor)var5.b();
               if (var6.c != var7.c) {
                  var4 = var6.c > var7.c;
               } else {
                  var4 = var2.i() > var5.i() || var2.n() && !var5.n();
               }
            } else {
               var4 = false;
            }
         }

         if (var4 && this.a(var2)) {
            if (var5 != null && this.V.nextFloat() - 0.1F < this.bj[var3]) {
               this.a(var5, 0.0F);
            }

            if (var2.b() == Items.i && var1.n() != null) {
               EntityPlayer var9 = this.o.a(var1.n());
               if (var9 != null) {
                  var9.b(AchievementList.x);
               }
            }

            this.c(var3, var2);
            this.bj[var3] = 2.0F;
            this.bn = true;
            this.a(var1, 1);
            var1.J();
         }
      }
   }

   protected boolean a(ItemStack var1) {
      return true;
   }

   protected boolean C() {
      return true;
   }

   protected void D() {
      if (this.bn) {
         this.aQ = 0;
      } else {
         EntityPlayer var1 = this.o.a(this, -1.0);
         if (var1 != null) {
            double var2 = var1.s - this.s;
            double var4 = var1.t - this.t;
            double var6 = var1.u - this.u;
            double var8 = var2 * var2 + var4 * var4 + var6 * var6;
            if (this.C() && var8 > 16384.0) {
               this.J();
            }

            if (this.aQ > 600 && this.V.nextInt(800) == 0 && var8 > 1024.0 && this.C()) {
               this.J();
            } else if (var8 < 1024.0) {
               this.aQ = 0;
            }
         }
      }
   }

   @Override
   protected final void bK() {
      this.aQ++;
      this.o.B.a("checkDespawn");
      this.D();
      this.o.B.b();
      this.o.B.a("sensing");
      this.bk.a();
      this.o.B.b();
      this.o.B.a("targetSelector");
      this.bi.a();
      this.o.B.b();
      this.o.B.a("goalSelector");
      this.i.a();
      this.o.B.b();
      this.o.B.a("navigation");
      this.h.k();
      this.o.B.b();
      this.o.B.a("mob tick");
      this.E();
      this.o.B.b();
      this.o.B.a("controls");
      this.o.B.a("move");
      this.f.c();
      this.o.B.c("look");
      this.a.a();
      this.o.B.c("jump");
      this.g.b();
      this.o.B.b();
      this.o.B.b();
   }

   protected void E() {
   }

   public int bQ() {
      return 40;
   }

   public void a(Entity var1, float var2, float var3) {
      double var4 = var1.s - this.s;
      double var8 = var1.u - this.u;
      double var6;
      if (var1 instanceof EntityLivingBase) {
         EntityLivingBase var10 = (EntityLivingBase)var1;
         var6 = var10.t + var10.aS() - (this.t + this.aS());
      } else {
         var6 = (var1.aR().b + var1.aR().e) / 2.0 - (this.t + this.aS());
      }

      double var14 = MathHelper.a(var4 * var4 + var8 * var8);
      float var12 = (float)(MathHelper.b(var8, var4) * 180.0 / (float) Math.PI) - 90.0F;
      float var13 = (float)(-(MathHelper.b(var6, var14) * 180.0 / (float) Math.PI));
      this.z = this.b(this.z, var13, var3);
      this.y = this.b(this.y, var12, var2);
   }

   private float b(float var1, float var2, float var3) {
      float var4 = MathHelper.g(var2 - var1);
      if (var4 > var3) {
         var4 = var3;
      }

      if (var4 < -var3) {
         var4 = -var3;
      }

      return var1 + var4;
   }

   public boolean bR() {
      return true;
   }

   public boolean bS() {
      return this.o.a(this.aR(), this) && this.o.a(this, this.aR()).isEmpty() && !this.o.d(this.aR());
   }

   public float bT() {
      return 1.0F;
   }

   public int bV() {
      return 4;
   }

   @Override
   public int aE() {
      if (this.u() == null) {
         return 3;
      } else {
         int var1 = (int)(this.bn() - this.bu() * 0.33F);
         var1 -= (3 - this.o.aa().a()) * 4;
         if (var1 < 0) {
            var1 = 0;
         }

         return var1 + 3;
      }
   }

   @Override
   public ItemStack bA() {
      return this.bl[0];
   }

   @Override
   public ItemStack p(int var1) {
      return this.bl[var1];
   }

   @Override
   public ItemStack q(int var1) {
      return this.bl[var1 + 1];
   }

   @Override
   public void c(int var1, ItemStack var2) {
      this.bl[var1] = var2;
   }

   @Override
   public ItemStack[] as() {
      return this.bl;
   }

   @Override
   protected void a(boolean var1, int var2) {
      for (int var3 = 0; var3 < this.as().length; var3++) {
         ItemStack var4 = this.p(var3);
         boolean var5 = this.bj[var3] > 1.0F;
         if (var4 != null && (var1 || var5) && this.V.nextFloat() - var2 * 0.01F < this.bj[var3]) {
            if (!var5 && var4.e()) {
               int var6 = Math.max(var4.j() - 25, 1);
               int var7 = var4.j() - this.V.nextInt(this.V.nextInt(var6) + 1);
               if (var7 > var6) {
                  var7 = var6;
               }

               if (var7 < 1) {
                  var7 = 1;
               }

               var4.b(var7);
            }

            this.a(var4, 0.0F);
         }
      }
   }

   protected void a(DifficultyInstance var1) {
      if (this.V.nextFloat() < 0.15F * var1.c()) {
         int var2 = this.V.nextInt(2);
         float var3 = this.o.aa() == EnumDifficulty.d ? 0.1F : 0.25F;
         if (this.V.nextFloat() < 0.095F) {
            var2++;
         }

         if (this.V.nextFloat() < 0.095F) {
            var2++;
         }

         if (this.V.nextFloat() < 0.095F) {
            var2++;
         }

         for (int var4 = 3; var4 >= 0; var4--) {
            ItemStack var5 = this.q(var4);
            if (var4 < 3 && this.V.nextFloat() < var3) {
               break;
            }

            if (var5 == null) {
               Item var6 = a(var4 + 1, var2);
               if (var6 != null) {
                  this.c(var4 + 1, new ItemStack(var6));
               }
            }
         }
      }
   }

   public static int c(ItemStack var0) {
      if (var0.b() == Item.a(Blocks.aU) || var0.b() == Items.bX) {
         return 4;
      } else {
         if (var0.b() instanceof ItemArmor) {
            switch (((ItemArmor)var0.b()).b) {
               case 0:
                  return 4;
               case 1:
                  return 3;
               case 2:
                  return 2;
               case 3:
                  return 1;
            }
         }

         return 0;
      }
   }

   public static Item a(int var0, int var1) {
      switch (var0) {
         case 4:
            if (var1 == 0) {
               return Items.Q;
            } else if (var1 == 1) {
               return Items.ag;
            } else if (var1 == 2) {
               return Items.U;
            } else if (var1 == 3) {
               return Items.Y;
            } else if (var1 == 4) {
               return Items.ac;
            }
         case 3:
            if (var1 == 0) {
               return Items.R;
            } else if (var1 == 1) {
               return Items.ah;
            } else if (var1 == 2) {
               return Items.V;
            } else if (var1 == 3) {
               return Items.Z;
            } else if (var1 == 4) {
               return Items.ad;
            }
         case 2:
            if (var1 == 0) {
               return Items.S;
            } else if (var1 == 1) {
               return Items.ai;
            } else if (var1 == 2) {
               return Items.W;
            } else if (var1 == 3) {
               return Items.aa;
            } else if (var1 == 4) {
               return Items.ae;
            }
         case 1:
            if (var1 == 0) {
               return Items.T;
            } else if (var1 == 1) {
               return Items.aj;
            } else if (var1 == 2) {
               return Items.X;
            } else if (var1 == 3) {
               return Items.ab;
            } else if (var1 == 4) {
               return Items.af;
            }
         default:
            return null;
      }
   }

   protected void b(DifficultyInstance var1) {
      float var2 = var1.c();
      if (this.bA() != null && this.V.nextFloat() < 0.25F * var2) {
         EnchantmentHelper.a(this.V, this.bA(), (int)(5.0F + var2 * this.V.nextInt(18)));
      }

      for (int var3 = 0; var3 < 4; var3++) {
         ItemStack var4 = this.q(var3);
         if (var4 != null && this.V.nextFloat() < 0.5F * var2) {
            EnchantmentHelper.a(this.V, var4, (int)(5.0F + var2 * this.V.nextInt(18)));
         }
      }
   }

   public IEntityLivingData a(DifficultyInstance var1, IEntityLivingData var2) {
      this.a(SharedMonsterAttributes.b).b(new AttributeModifier("Random spawn bonus", this.V.nextGaussian() * 0.05, 1));
      return var2;
   }

   public boolean bW() {
      return false;
   }

   public void bX() {
      this.bn = true;
   }

   public void a(int var1, float var2) {
      this.bj[var1] = var2;
   }

   public boolean bY() {
      return this.bm;
   }

   public void j(boolean var1) {
      this.bm = var1;
   }

   public boolean bZ() {
      return this.bn;
   }

   @Override
   public final boolean e(EntityPlayer var1) {
      if (this.cc() && this.cd() == var1) {
         this.a(true, !var1.bA.d);
         return true;
      } else {
         ItemStack var2 = var1.bi.h();
         if (var2 != null && var2.b() == Items.cn && this.cb()) {
            if (!(this instanceof EntityTameable) || !((EntityTameable)this).cl()) {
               this.a(var1, true);
               var2.b--;
               return true;
            }

            if (((EntityTameable)this).e(var1)) {
               this.a(var1, true);
               var2.b--;
               return true;
            }
         }

         return this.a(var1) ? true : super.e(var1);
      }
   }

   protected boolean a(EntityPlayer var1) {
      return false;
   }

   protected void ca() {
      if (this.bq != null) {
         this.n();
      }

      if (this.bo) {
         if (!this.ai()) {
            this.a(true, true);
         }

         if (this.bp == null || this.bp.I) {
            this.a(true, true);
         }
      }
   }

   public void a(boolean var1, boolean var2) {
      if (this.bo) {
         this.bo = false;
         this.bp = null;
         if (!this.o.D && var2) {
            this.a(Items.cn, 1);
         }

         if (!this.o.D && var1 && this.o instanceof WorldServer) {
            ((WorldServer)this.o).s().a(this, new S1BPacketEntityAttach(1, this, null));
         }
      }
   }

   public boolean cb() {
      return !this.cc() && !(this instanceof IMob);
   }

   public boolean cc() {
      return this.bo;
   }

   public Entity cd() {
      return this.bp;
   }

   public void a(Entity var1, boolean var2) {
      this.bo = true;
      this.bp = var1;
      if (!this.o.D && var2 && this.o instanceof WorldServer) {
         ((WorldServer)this.o).s().a(this, new S1BPacketEntityAttach(1, this, this.bp));
      }
   }

   private void n() {
      if (this.bo && this.bq != null) {
         if (this.bq.b("UUIDMost", 4) && this.bq.b("UUIDLeast", 4)) {
            UUID var5 = new UUID(this.bq.g("UUIDMost"), this.bq.g("UUIDLeast"));

            for (EntityLivingBase var4 : this.o.a(EntityLivingBase.class, this.aR().b(10.0, 10.0, 10.0))) {
               if (var4.aK().equals(var5)) {
                  this.bp = var4;
                  break;
               }
            }
         } else if (this.bq.b("X", 99) && this.bq.b("Y", 99) && this.bq.b("Z", 99)) {
            BlockPos var1 = new BlockPos(this.bq.f("X"), this.bq.f("Y"), this.bq.f("Z"));
            EntityLeashKnot var2 = EntityLeashKnot.b(this.o, var1);
            if (var2 == null) {
               var2 = EntityLeashKnot.a(this.o, var1);
            }

            this.bp = var2;
         } else {
            this.a(false, true);
         }
      }

      this.bq = null;
   }

   @Override
   public boolean d(int var1, ItemStack var2) {
      int var3;
      if (var1 == 99) {
         var3 = 0;
      } else {
         var3 = var1 - 100 + 1;
         if (var3 < 0 || var3 >= this.bl.length) {
            return false;
         }
      }

      if (var2 != null && c(var2) != var3 && (var3 != 4 || !(var2.b() instanceof ItemBlock))) {
         return false;
      } else {
         this.c(var3, var2);
         return true;
      }
   }

   @Override
   public boolean bM() {
      return super.bM() && !this.ce();
   }

   public void k(boolean var1) {
      this.ac.b(15, Byte.valueOf((byte)(var1 ? 1 : 0)));
   }

   public boolean ce() {
      return this.ac.a(15) != 0;
   }

   public static enum SpawnPlacementType {
      a,
      b,
      c;
   }
}
