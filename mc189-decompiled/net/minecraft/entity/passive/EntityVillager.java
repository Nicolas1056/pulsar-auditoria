package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowGolem;
import net.minecraft.entity.ai.EntityAIHarvestFarmland;
import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPlay;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITradePlayer;
import net.minecraft.entity.ai.EntityAIVillagerInteract;
import net.minecraft.entity.ai.EntityAIVillagerMate;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Tuple;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityVillager extends EntityAgeable implements IMerchant, INpc {
   private int bn;
   private boolean bo;
   private boolean bp;
   Village bm;
   private EntityPlayer bq;
   private MerchantRecipeList br;
   private int bs;
   private boolean bt;
   private boolean bu;
   private int bv;
   private String bw;
   private int bx;
   private int by;
   private boolean bz;
   private boolean bA;
   private InventoryBasic bB = new InventoryBasic("Items", false, 8);
   private static final EntityVillager.ITradeList[][][][] bC = new EntityVillager.ITradeList[][][][]{
      {
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.O, new EntityVillager.PriceInfo(18, 22)),
                        new EntityVillager.EmeraldForItems(Items.bS, new EntityVillager.PriceInfo(15, 19)),
                        new EntityVillager.EmeraldForItems(Items.bR, new EntityVillager.PriceInfo(15, 19)),
                        new EntityVillager.ListItemForEmeralds(Items.P, new EntityVillager.PriceInfo(-4, -2))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Item.a(Blocks.aU), new EntityVillager.PriceInfo(8, 13)),
                        new EntityVillager.ListItemForEmeralds(Items.ca, new EntityVillager.PriceInfo(-3, -2))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Item.a(Blocks.bk), new EntityVillager.PriceInfo(7, 12)),
                        new EntityVillager.ListItemForEmeralds(Items.e, new EntityVillager.PriceInfo(-5, -7))
                  },
                  {
                        new EntityVillager.ListItemForEmeralds(Items.bc, new EntityVillager.PriceInfo(-6, -10)),
                        new EntityVillager.ListItemForEmeralds(Items.aZ, new EntityVillager.PriceInfo(1, 1))
                  }
            },
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.F, new EntityVillager.PriceInfo(15, 20)),
                        new EntityVillager.EmeraldForItems(Items.h, new EntityVillager.PriceInfo(16, 24)),
                        new EntityVillager.ItemAndEmeraldToItem(Items.aU, new EntityVillager.PriceInfo(6, 6), Items.aV, new EntityVillager.PriceInfo(6, 6))
                  },
                  {new EntityVillager.ListEnchantedItemForEmeralds(Items.aR, new EntityVillager.PriceInfo(7, 8))}
            },
            {
                  {
                        new EntityVillager.EmeraldForItems(Item.a(Blocks.L), new EntityVillager.PriceInfo(16, 22)),
                        new EntityVillager.ListItemForEmeralds(Items.be, new EntityVillager.PriceInfo(3, 4))
                  },
                  {
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 0), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 1), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 2), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 3), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 4), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 5), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 6), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 7), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 8), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 9), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 10), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 11), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 12), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 13), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 14), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 15), new EntityVillager.PriceInfo(1, 2))
                  }
            },
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.F, new EntityVillager.PriceInfo(15, 20)),
                        new EntityVillager.ListItemForEmeralds(Items.g, new EntityVillager.PriceInfo(-12, -8))
                  },
                  {
                        new EntityVillager.ListItemForEmeralds(Items.f, new EntityVillager.PriceInfo(2, 3)),
                        new EntityVillager.ItemAndEmeraldToItem(
                           Item.a(Blocks.n), new EntityVillager.PriceInfo(10, 10), Items.ak, new EntityVillager.PriceInfo(6, 10)
                        )
                  }
            }
      },
      {
            {
                  {new EntityVillager.EmeraldForItems(Items.aK, new EntityVillager.PriceInfo(24, 36)), new EntityVillager.ListEnchantedBookForEmeralds()},
                  {
                        new EntityVillager.EmeraldForItems(Items.aL, new EntityVillager.PriceInfo(8, 10)),
                        new EntityVillager.ListItemForEmeralds(Items.aQ, new EntityVillager.PriceInfo(10, 12)),
                        new EntityVillager.ListItemForEmeralds(Item.a(Blocks.X), new EntityVillager.PriceInfo(3, 4))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.bN, new EntityVillager.PriceInfo(2, 2)),
                        new EntityVillager.ListItemForEmeralds(Items.aS, new EntityVillager.PriceInfo(10, 12)),
                        new EntityVillager.ListItemForEmeralds(Item.a(Blocks.w), new EntityVillager.PriceInfo(-5, -3))
                  },
                  {new EntityVillager.ListEnchantedBookForEmeralds()},
                  {new EntityVillager.ListEnchantedBookForEmeralds()},
                  {new EntityVillager.ListItemForEmeralds(Items.co, new EntityVillager.PriceInfo(20, 22))}
            }
      },
      {
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.bt, new EntityVillager.PriceInfo(36, 40)),
                        new EntityVillager.EmeraldForItems(Items.k, new EntityVillager.PriceInfo(8, 10))
                  },
                  {
                        new EntityVillager.ListItemForEmeralds(Items.aC, new EntityVillager.PriceInfo(-4, -1)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Items.aW, 1, EnumDyeColor.l.b()), new EntityVillager.PriceInfo(-2, -1))
                  },
                  {
                        new EntityVillager.ListItemForEmeralds(Items.bH, new EntityVillager.PriceInfo(7, 11)),
                        new EntityVillager.ListItemForEmeralds(Item.a(Blocks.aX), new EntityVillager.PriceInfo(-3, -1))
                  },
                  {new EntityVillager.ListItemForEmeralds(Items.bK, new EntityVillager.PriceInfo(3, 11))}
            }
      },
      {
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.h, new EntityVillager.PriceInfo(16, 24)),
                        new EntityVillager.ListItemForEmeralds(Items.Y, new EntityVillager.PriceInfo(4, 6))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.j, new EntityVillager.PriceInfo(7, 9)),
                        new EntityVillager.ListItemForEmeralds(Items.Z, new EntityVillager.PriceInfo(10, 14))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.i, new EntityVillager.PriceInfo(3, 4)),
                        new EntityVillager.ListEnchantedItemForEmeralds(Items.ad, new EntityVillager.PriceInfo(16, 19))
                  },
                  {
                        new EntityVillager.ListItemForEmeralds(Items.X, new EntityVillager.PriceInfo(5, 7)),
                        new EntityVillager.ListItemForEmeralds(Items.W, new EntityVillager.PriceInfo(9, 11)),
                        new EntityVillager.ListItemForEmeralds(Items.U, new EntityVillager.PriceInfo(5, 7)),
                        new EntityVillager.ListItemForEmeralds(Items.V, new EntityVillager.PriceInfo(11, 15))
                  }
            },
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.h, new EntityVillager.PriceInfo(16, 24)),
                        new EntityVillager.ListItemForEmeralds(Items.c, new EntityVillager.PriceInfo(6, 8))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.j, new EntityVillager.PriceInfo(7, 9)),
                        new EntityVillager.ListEnchantedItemForEmeralds(Items.l, new EntityVillager.PriceInfo(9, 10))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.i, new EntityVillager.PriceInfo(3, 4)),
                        new EntityVillager.ListEnchantedItemForEmeralds(Items.u, new EntityVillager.PriceInfo(12, 15)),
                        new EntityVillager.ListEnchantedItemForEmeralds(Items.x, new EntityVillager.PriceInfo(9, 12))
                  }
            },
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.h, new EntityVillager.PriceInfo(16, 24)),
                        new EntityVillager.ListEnchantedItemForEmeralds(Items.a, new EntityVillager.PriceInfo(5, 7))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.j, new EntityVillager.PriceInfo(7, 9)),
                        new EntityVillager.ListEnchantedItemForEmeralds(Items.b, new EntityVillager.PriceInfo(9, 11))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.i, new EntityVillager.PriceInfo(3, 4)),
                        new EntityVillager.ListEnchantedItemForEmeralds(Items.w, new EntityVillager.PriceInfo(12, 15))
                  }
            }
      },
      {
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.al, new EntityVillager.PriceInfo(14, 18)),
                        new EntityVillager.EmeraldForItems(Items.bk, new EntityVillager.PriceInfo(14, 18))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.h, new EntityVillager.PriceInfo(16, 24)),
                        new EntityVillager.ListItemForEmeralds(Items.am, new EntityVillager.PriceInfo(-7, -5)),
                        new EntityVillager.ListItemForEmeralds(Items.bl, new EntityVillager.PriceInfo(-8, -6))
                  }
            },
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.aF, new EntityVillager.PriceInfo(9, 12)),
                        new EntityVillager.ListItemForEmeralds(Items.S, new EntityVillager.PriceInfo(2, 4))
                  },
                  {new EntityVillager.ListEnchantedItemForEmeralds(Items.R, new EntityVillager.PriceInfo(7, 12))},
                  {new EntityVillager.ListItemForEmeralds(Items.aA, new EntityVillager.PriceInfo(8, 10))}
            }
      }
   };

   public EntityVillager(World var1) {
      this(var1, 0);
   }

   public EntityVillager(World var1, int var2) {
      super(var1);
      this.r(var2);
      this.a(0.6F, 1.8F);
      ((PathNavigateGround)this.s()).b(true);
      ((PathNavigateGround)this.s()).a(true);
      this.i.a(0, new EntityAISwimming(this));
      this.i.a(1, new EntityAIAvoidEntity<>(this, EntityZombie.class, 8.0F, 0.6, 0.6));
      this.i.a(1, new EntityAITradePlayer(this));
      this.i.a(1, new EntityAILookAtTradePlayer(this));
      this.i.a(2, new EntityAIMoveIndoors(this));
      this.i.a(3, new EntityAIRestrictOpenDoor(this));
      this.i.a(4, new EntityAIOpenDoor(this, true));
      this.i.a(5, new EntityAIMoveTowardsRestriction(this, 0.6));
      this.i.a(6, new EntityAIVillagerMate(this));
      this.i.a(7, new EntityAIFollowGolem(this));
      this.i.a(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
      this.i.a(9, new EntityAIVillagerInteract(this));
      this.i.a(9, new EntityAIWander(this, 0.6));
      this.i.a(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
      this.j(true);
   }

   private void cv() {
      if (!this.bA) {
         this.bA = true;
         if (this.j_()) {
            this.i.a(8, new EntityAIPlay(this, 0.32));
         } else if (this.cl() == 0) {
            this.i.a(6, new EntityAIHarvestFarmland(this, 0.6));
         }
      }
   }

   @Override
   protected void n() {
      if (this.cl() == 0) {
         this.i.a(8, new EntityAIHarvestFarmland(this, 0.6));
      }

      super.n();
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.d).a(0.5);
   }

   @Override
   protected void E() {
      if (--this.bn <= 0) {
         BlockPos var1 = new BlockPos(this);
         this.o.ae().a(var1);
         this.bn = 70 + this.V.nextInt(50);
         this.bm = this.o.ae().a(var1, 32);
         if (this.bm == null) {
            this.cj();
         } else {
            BlockPos var2 = this.bm.a();
            this.a(var2, (int)(this.bm.b() * 1.0F));
            if (this.bz) {
               this.bz = false;
               this.bm.b(5);
            }
         }
      }

      if (!this.co() && this.bs > 0) {
         this.bs--;
         if (this.bs <= 0) {
            if (this.bt) {
               for (MerchantRecipe var4 : this.br) {
                  if (var4.h()) {
                     var4.a(this.V.nextInt(6) + this.V.nextInt(6) + 2);
                  }
               }

               this.cw();
               this.bt = false;
               if (this.bm != null && this.bw != null) {
                  this.o.a(this, (byte)14);
                  this.bm.a(this.bw, 1);
               }
            }

            this.c(new PotionEffect(Potion.l.H, 200, 0));
         }
      }

      super.E();
   }

   @Override
   public boolean a(EntityPlayer var1) {
      ItemStack var2 = var1.bi.h();
      boolean var3 = var2 != null && var2.b() == Items.bJ;
      if (!var3 && this.ai() && !this.co() && !this.j_()) {
         if (!this.o.D && (this.br == null || this.br.size() > 0)) {
            this.a_(var1);
            var1.a((IMerchant)this);
         }

         var1.b(StatList.F);
         return true;
      } else {
         return super.a(var1);
      }
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(16, Integer.valueOf(0));
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("Profession", this.cl());
      var1.a("Riches", this.bv);
      var1.a("Career", this.bx);
      var1.a("CareerLevel", this.by);
      var1.a("Willing", this.bu);
      if (this.br != null) {
         var1.a("Offers", this.br.a());
      }

      NBTTagList var2 = new NBTTagList();

      for (int var3 = 0; var3 < this.bB.o_(); var3++) {
         ItemStack var4 = this.bB.a(var3);
         if (var4 != null) {
            var2.a(var4.b(new NBTTagCompound()));
         }
      }

      var1.a("Inventory", var2);
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.r(var1.f("Profession"));
      this.bv = var1.f("Riches");
      this.bx = var1.f("Career");
      this.by = var1.f("CareerLevel");
      this.bu = var1.n("Willing");
      if (var1.b("Offers", 10)) {
         NBTTagCompound var2 = var1.m("Offers");
         this.br = new MerchantRecipeList(var2);
      }

      NBTTagList var5 = var1.c("Inventory", 10);

      for (int var3 = 0; var3 < var5.c(); var3++) {
         ItemStack var4 = ItemStack.a(var5.b(var3));
         if (var4 != null) {
            this.bB.a(var4);
         }
      }

      this.j(true);
      this.cv();
   }

   @Override
   protected boolean C() {
      return false;
   }

   @Override
   protected String z() {
      return this.co() ? "mob.villager.haggle" : "mob.villager.idle";
   }

   @Override
   protected String bo() {
      return "mob.villager.hit";
   }

   @Override
   protected String bp() {
      return "mob.villager.death";
   }

   public void r(int var1) {
      this.ac.b(16, var1);
   }

   public int cl() {
      return Math.max(this.ac.c(16) % 5, 0);
   }

   public boolean cm() {
      return this.bo;
   }

   public void l(boolean var1) {
      this.bo = var1;
   }

   public void m(boolean var1) {
      this.bp = var1;
   }

   public boolean cn() {
      return this.bp;
   }

   @Override
   public void b(EntityLivingBase var1) {
      super.b(var1);
      if (this.bm != null && var1 != null) {
         this.bm.a(var1);
         if (var1 instanceof EntityPlayer) {
            byte var2 = -1;
            if (this.j_()) {
               var2 = -3;
            }

            this.bm.a(var1.e_(), var2);
            if (this.ai()) {
               this.o.a(this, (byte)13);
            }
         }
      }
   }

   @Override
   public void a(DamageSource var1) {
      if (this.bm != null) {
         Entity var2 = var1.j();
         if (var2 != null) {
            if (var2 instanceof EntityPlayer) {
               this.bm.a(var2.e_(), -2);
            } else if (var2 instanceof IMob) {
               this.bm.h();
            }
         } else {
            EntityPlayer var3 = this.o.a(this, 16.0);
            if (var3 != null) {
               this.bm.h();
            }
         }
      }

      super.a(var1);
   }

   @Override
   public void a_(EntityPlayer var1) {
      this.bq = var1;
   }

   @Override
   public EntityPlayer v_() {
      return this.bq;
   }

   public boolean co() {
      return this.bq != null;
   }

   public boolean n(boolean var1) {
      if (!this.bu && var1 && this.cr()) {
         boolean var2 = false;

         for (int var3 = 0; var3 < this.bB.o_(); var3++) {
            ItemStack var4 = this.bB.a(var3);
            if (var4 != null) {
               if (var4.b() == Items.P && var4.b >= 3) {
                  var2 = true;
                  this.bB.a(var3, 3);
               } else if ((var4.b() == Items.bS || var4.b() == Items.bR) && var4.b >= 12) {
                  var2 = true;
                  this.bB.a(var3, 12);
               }
            }

            if (var2) {
               this.o.a(this, (byte)18);
               this.bu = true;
               break;
            }
         }
      }

      return this.bu;
   }

   public void o(boolean var1) {
      this.bu = var1;
   }

   @Override
   public void a(MerchantRecipe var1) {
      var1.g();
      this.a_ = -this.w();
      this.a("mob.villager.yes", this.bB(), this.bC());
      int var2 = 3 + this.V.nextInt(4);
      if (var1.e() == 1 || this.V.nextInt(5) == 0) {
         this.bs = 40;
         this.bt = true;
         this.bu = true;
         if (this.bq != null) {
            this.bw = this.bq.e_();
         } else {
            this.bw = null;
         }

         var2 += 5;
      }

      if (var1.a().b() == Items.bO) {
         this.bv = this.bv + var1.a().b;
      }

      if (var1.j()) {
         this.o.d(new EntityXPOrb(this.o, this.s, this.t + 0.5, this.u, var2));
      }
   }

   @Override
   public void a_(ItemStack var1) {
      if (!this.o.D && this.a_ > -this.w() + 20) {
         this.a_ = -this.w();
         if (var1 != null) {
            this.a("mob.villager.yes", this.bB(), this.bC());
         } else {
            this.a("mob.villager.no", this.bB(), this.bC());
         }
      }
   }

   @Override
   public MerchantRecipeList b_(EntityPlayer var1) {
      if (this.br == null) {
         this.cw();
      }

      return this.br;
   }

   private void cw() {
      EntityVillager.ITradeList[][][] var1 = bC[this.cl()];
      if (this.bx != 0 && this.by != 0) {
         this.by++;
      } else {
         this.bx = this.V.nextInt(var1.length) + 1;
         this.by = 1;
      }

      if (this.br == null) {
         this.br = new MerchantRecipeList();
      }

      int var2 = this.bx - 1;
      int var3 = this.by - 1;
      EntityVillager.ITradeList[][] var4 = var1[var2];
      if (var3 >= 0 && var3 < var4.length) {
         EntityVillager.ITradeList[] var5 = var4[var3];

         for (EntityVillager.ITradeList var9 : var5) {
            var9.a(this.br, this.V);
         }
      }
   }

   @Override
   public void a(MerchantRecipeList var1) {
   }

   @Override
   public IChatComponent f_() {
      String var1 = this.aM();
      if (var1 != null && var1.length() > 0) {
         ChatComponentText var4 = new ChatComponentText(var1);
         var4.b().a(this.aQ());
         var4.b().a(this.aK().toString());
         return var4;
      } else {
         if (this.br == null) {
            this.cw();
         }

         String var2 = null;
         switch (this.cl()) {
            case 0:
               if (this.bx == 1) {
                  var2 = "farmer";
               } else if (this.bx == 2) {
                  var2 = "fisherman";
               } else if (this.bx == 3) {
                  var2 = "shepherd";
               } else if (this.bx == 4) {
                  var2 = "fletcher";
               }
               break;
            case 1:
               var2 = "librarian";
               break;
            case 2:
               var2 = "cleric";
               break;
            case 3:
               if (this.bx == 1) {
                  var2 = "armor";
               } else if (this.bx == 2) {
                  var2 = "weapon";
               } else if (this.bx == 3) {
                  var2 = "tool";
               }
               break;
            case 4:
               if (this.bx == 1) {
                  var2 = "butcher";
               } else if (this.bx == 2) {
                  var2 = "leather";
               }
         }

         if (var2 != null) {
            ChatComponentTranslation var3 = new ChatComponentTranslation("entity.Villager." + var2);
            var3.b().a(this.aQ());
            var3.b().a(this.aK().toString());
            return var3;
         } else {
            return super.f_();
         }
      }
   }

   @Override
   public float aS() {
      float var1 = 1.62F;
      if (this.j_()) {
         var1 = (float)(var1 - 0.81);
      }

      return var1;
   }

   @Override
   public void a(byte var1) {
      if (var1 == 12) {
         this.a(EnumParticleTypes.I);
      } else if (var1 == 13) {
         this.a(EnumParticleTypes.u);
      } else if (var1 == 14) {
         this.a(EnumParticleTypes.v);
      } else {
         super.a(var1);
      }
   }

   private void a(EnumParticleTypes var1) {
      for (int var2 = 0; var2 < 5; var2++) {
         double var3 = this.V.nextGaussian() * 0.02;
         double var5 = this.V.nextGaussian() * 0.02;
         double var7 = this.V.nextGaussian() * 0.02;
         this.o
            .a(
               var1,
               this.s + this.V.nextFloat() * this.J * 2.0F - this.J,
               this.t + 1.0 + this.V.nextFloat() * this.K,
               this.u + this.V.nextFloat() * this.J * 2.0F - this.J,
               var3,
               var5,
               var7
            );
      }
   }

   @Override
   public IEntityLivingData a(DifficultyInstance var1, IEntityLivingData var2) {
      var2 = super.a(var1, var2);
      this.r(this.o.s.nextInt(5));
      this.cv();
      return var2;
   }

   public void cp() {
      this.bz = true;
   }

   public EntityVillager b(EntityAgeable var1) {
      EntityVillager var2 = new EntityVillager(this.o);
      var2.a(this.o.E(new BlockPos(var2)), null);
      return var2;
   }

   @Override
   public boolean cb() {
      return false;
   }

   @Override
   public void a(EntityLightningBolt var1) {
      if (!this.o.D && !this.I) {
         EntityWitch var2 = new EntityWitch(this.o);
         var2.b(this.s, this.t, this.u, this.y, this.z);
         var2.a(this.o.E(new BlockPos(var2)), null);
         var2.k(this.ce());
         if (this.l_()) {
            var2.a(this.aM());
            var2.g(this.aN());
         }

         this.o.d(var2);
         this.J();
      }
   }

   public InventoryBasic cq() {
      return this.bB;
   }

   @Override
   protected void a(EntityItem var1) {
      ItemStack var2 = var1.l();
      Item var3 = var2.b();
      if (this.a(var3)) {
         ItemStack var4 = this.bB.a(var2);
         if (var4 == null) {
            var1.J();
         } else {
            var2.b = var4.b;
         }
      }
   }

   private boolean a(Item var1) {
      return var1 == Items.P || var1 == Items.bS || var1 == Items.bR || var1 == Items.O || var1 == Items.N;
   }

   public boolean cr() {
      return this.s(1);
   }

   public boolean cs() {
      return this.s(2);
   }

   public boolean ct() {
      boolean var1 = this.cl() == 0;
      return var1 ? !this.s(5) : !this.s(1);
   }

   private boolean s(int var1) {
      boolean var2 = this.cl() == 0;

      for (int var3 = 0; var3 < this.bB.o_(); var3++) {
         ItemStack var4 = this.bB.a(var3);
         if (var4 != null) {
            if (var4.b() == Items.P && var4.b >= 3 * var1 || var4.b() == Items.bS && var4.b >= 12 * var1 || var4.b() == Items.bR && var4.b >= 12 * var1) {
               return true;
            }

            if (var2 && var4.b() == Items.O && var4.b >= 9 * var1) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean cu() {
      for (int var1 = 0; var1 < this.bB.o_(); var1++) {
         ItemStack var2 = this.bB.a(var1);
         if (var2 != null && (var2.b() == Items.N || var2.b() == Items.bS || var2.b() == Items.bR)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean d(int var1, ItemStack var2) {
      if (super.d(var1, var2)) {
         return true;
      } else {
         int var3 = var1 - 300;
         if (var3 >= 0 && var3 < this.bB.o_()) {
            this.bB.a(var3, var2);
            return true;
         } else {
            return false;
         }
      }
   }

   static class EmeraldForItems implements EntityVillager.ITradeList {
      public Item a;
      public EntityVillager.PriceInfo b;

      public EmeraldForItems(Item var1, EntityVillager.PriceInfo var2) {
         this.a = var1;
         this.b = var2;
      }

      @Override
      public void a(MerchantRecipeList var1, Random var2) {
         int var3 = 1;
         if (this.b != null) {
            var3 = this.b.a(var2);
         }

         var1.add(new MerchantRecipe(new ItemStack(this.a, var3, 0), Items.bO));
      }
   }

   interface ITradeList {
      void a(MerchantRecipeList var1, Random var2);
   }

   static class ItemAndEmeraldToItem implements EntityVillager.ITradeList {
      public ItemStack a;
      public EntityVillager.PriceInfo b;
      public ItemStack c;
      public EntityVillager.PriceInfo d;

      public ItemAndEmeraldToItem(Item var1, EntityVillager.PriceInfo var2, Item var3, EntityVillager.PriceInfo var4) {
         this.a = new ItemStack(var1);
         this.b = var2;
         this.c = new ItemStack(var3);
         this.d = var4;
      }

      @Override
      public void a(MerchantRecipeList var1, Random var2) {
         int var3 = 1;
         if (this.b != null) {
            var3 = this.b.a(var2);
         }

         int var4 = 1;
         if (this.d != null) {
            var4 = this.d.a(var2);
         }

         var1.add(new MerchantRecipe(new ItemStack(this.a.b(), var3, this.a.i()), new ItemStack(Items.bO), new ItemStack(this.c.b(), var4, this.c.i())));
      }
   }

   static class ListEnchantedBookForEmeralds implements EntityVillager.ITradeList {
      public ListEnchantedBookForEmeralds() {
      }

      @Override
      public void a(MerchantRecipeList var1, Random var2) {
         Enchantment var3 = Enchantment.b[var2.nextInt(Enchantment.b.length)];
         int var4 = MathHelper.a(var2, var3.e(), var3.b());
         ItemStack var5 = Items.cd.a(new EnchantmentData(var3, var4));
         int var6 = 2 + var2.nextInt(5 + var4 * 10) + 3 * var4;
         if (var6 > 64) {
            var6 = 64;
         }

         var1.add(new MerchantRecipe(new ItemStack(Items.aL), new ItemStack(Items.bO, var6), var5));
      }
   }

   static class ListEnchantedItemForEmeralds implements EntityVillager.ITradeList {
      public ItemStack a;
      public EntityVillager.PriceInfo b;

      public ListEnchantedItemForEmeralds(Item var1, EntityVillager.PriceInfo var2) {
         this.a = new ItemStack(var1);
         this.b = var2;
      }

      @Override
      public void a(MerchantRecipeList var1, Random var2) {
         int var3 = 1;
         if (this.b != null) {
            var3 = this.b.a(var2);
         }

         ItemStack var4 = new ItemStack(Items.bO, var3, 0);
         ItemStack var5 = new ItemStack(this.a.b(), 1, this.a.i());
         var5 = EnchantmentHelper.a(var2, var5, 5 + var2.nextInt(15));
         var1.add(new MerchantRecipe(var4, var5));
      }
   }

   static class ListItemForEmeralds implements EntityVillager.ITradeList {
      public ItemStack a;
      public EntityVillager.PriceInfo b;

      public ListItemForEmeralds(Item var1, EntityVillager.PriceInfo var2) {
         this.a = new ItemStack(var1);
         this.b = var2;
      }

      public ListItemForEmeralds(ItemStack var1, EntityVillager.PriceInfo var2) {
         this.a = var1;
         this.b = var2;
      }

      @Override
      public void a(MerchantRecipeList var1, Random var2) {
         int var3 = 1;
         if (this.b != null) {
            var3 = this.b.a(var2);
         }

         ItemStack var4;
         ItemStack var5;
         if (var3 < 0) {
            var4 = new ItemStack(Items.bO, 1, 0);
            var5 = new ItemStack(this.a.b(), -var3, this.a.i());
         } else {
            var4 = new ItemStack(Items.bO, var3, 0);
            var5 = new ItemStack(this.a.b(), 1, this.a.i());
         }

         var1.add(new MerchantRecipe(var4, var5));
      }
   }

   static class PriceInfo extends Tuple<Integer, Integer> {
      public PriceInfo(int var1, int var2) {
         super(var1, var2);
      }

      public int a(Random var1) {
         return this.a() >= this.b() ? this.a() : this.a() + var1.nextInt(this.b() - this.a() + 1);
      }
   }
}
