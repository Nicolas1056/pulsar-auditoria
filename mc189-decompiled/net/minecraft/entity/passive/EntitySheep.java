package net.minecraft.entity.passive;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntitySheep extends EntityAnimal {
   private final InventoryCrafting bm = new InventoryCrafting(new Container() {
      @Override
      public boolean a(EntityPlayer var1) {
         return false;
      }
   }, 2, 1);
   private static final Map<EnumDyeColor, float[]> bo = Maps.newEnumMap(EnumDyeColor.class);
   private int bp;
   private EntityAIEatGrass bq = new EntityAIEatGrass(this);

   public static float[] a(EnumDyeColor var0) {
      return bo.get(var0);
   }

   public EntitySheep(World var1) {
      super(var1);
      this.a(0.9F, 1.3F);
      ((PathNavigateGround)this.s()).a(true);
      this.i.a(0, new EntityAISwimming(this));
      this.i.a(1, new EntityAIPanic(this, 1.25));
      this.i.a(2, new EntityAIMate(this, 1.0));
      this.i.a(3, new EntityAITempt(this, 1.1, Items.O, false));
      this.i.a(4, new EntityAIFollowParent(this, 1.1));
      this.i.a(5, this.bq);
      this.i.a(6, new EntityAIWander(this, 1.0));
      this.i.a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.i.a(8, new EntityAILookIdle(this));
      this.bm.a(0, new ItemStack(Items.aW, 1, 0));
      this.bm.a(1, new ItemStack(Items.aW, 1, 0));
   }

   @Override
   protected void E() {
      this.bp = this.bq.f();
      super.E();
   }

   @Override
   public void m() {
      if (this.o.D) {
         this.bp = Math.max(0, this.bp - 1);
      }

      super.m();
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(8.0);
      this.a(SharedMonsterAttributes.d).a(0.23F);
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(16, new Byte((byte)0));
   }

   @Override
   protected void b(boolean var1, int var2) {
      if (!this.cm()) {
         this.a(new ItemStack(Item.a(Blocks.L), 1, this.cl().a()), 0.0F);
      }

      int var3 = this.V.nextInt(2) + 1 + this.V.nextInt(1 + var2);

      for (int var4 = 0; var4 < var3; var4++) {
         if (this.at()) {
            this.a(Items.bn, 1);
         } else {
            this.a(Items.bm, 1);
         }
      }
   }

   @Override
   protected Item A() {
      return Item.a(Blocks.L);
   }

   @Override
   public void a(byte var1) {
      if (var1 == 10) {
         this.bp = 40;
      } else {
         super.a(var1);
      }
   }

   public float p(float var1) {
      if (this.bp <= 0) {
         return 0.0F;
      } else if (this.bp >= 4 && this.bp <= 36) {
         return 1.0F;
      } else {
         return this.bp < 4 ? (this.bp - var1) / 4.0F : -(this.bp - 40 - var1) / 4.0F;
      }
   }

   public float q(float var1) {
      if (this.bp > 4 && this.bp <= 36) {
         float var2 = (this.bp - 4 - var1) / 32.0F;
         return (float) (Math.PI / 5) + 0.21991149F * MathHelper.a(var2 * 28.7F);
      } else {
         return this.bp > 0 ? (float) (Math.PI / 5) : this.z / (180.0F / (float)Math.PI);
      }
   }

   @Override
   public boolean a(EntityPlayer var1) {
      ItemStack var2 = var1.bi.h();
      if (var2 != null && var2.b() == Items.be && !this.cm() && !this.j_()) {
         if (!this.o.D) {
            this.l(true);
            int var3 = 1 + this.V.nextInt(3);

            for (int var4 = 0; var4 < var3; var4++) {
               EntityItem var5 = this.a(new ItemStack(Item.a(Blocks.L), 1, this.cl().a()), 1.0F);
               var5.w = var5.w + this.V.nextFloat() * 0.05F;
               var5.v = var5.v + (this.V.nextFloat() - this.V.nextFloat()) * 0.1F;
               var5.x = var5.x + (this.V.nextFloat() - this.V.nextFloat()) * 0.1F;
            }
         }

         var2.a(1, var1);
         this.a("mob.sheep.shear", 1.0F, 1.0F);
      }

      return super.a(var1);
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("Sheared", this.cm());
      var1.a("Color", (byte)this.cl().a());
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.l(var1.n("Sheared"));
      this.b(EnumDyeColor.b(var1.d("Color")));
   }

   @Override
   protected String z() {
      return "mob.sheep.say";
   }

   @Override
   protected String bo() {
      return "mob.sheep.say";
   }

   @Override
   protected String bp() {
      return "mob.sheep.say";
   }

   @Override
   protected void a(BlockPos var1, Block var2) {
      this.a("mob.sheep.step", 0.15F, 1.0F);
   }

   public EnumDyeColor cl() {
      return EnumDyeColor.b(this.ac.a(16) & 15);
   }

   public void b(EnumDyeColor var1) {
      byte var2 = this.ac.a(16);
      this.ac.b(16, (byte)(var2 & 240 | var1.a() & 15));
   }

   public boolean cm() {
      return (this.ac.a(16) & 16) != 0;
   }

   public void l(boolean var1) {
      byte var2 = this.ac.a(16);
      if (var1) {
         this.ac.b(16, (byte)(var2 | 16));
      } else {
         this.ac.b(16, (byte)(var2 & -17));
      }
   }

   public static EnumDyeColor a(Random var0) {
      int var1 = var0.nextInt(100);
      if (var1 < 5) {
         return EnumDyeColor.p;
      } else if (var1 < 10) {
         return EnumDyeColor.h;
      } else if (var1 < 15) {
         return EnumDyeColor.i;
      } else if (var1 < 18) {
         return EnumDyeColor.m;
      } else {
         return var0.nextInt(500) == 0 ? EnumDyeColor.g : EnumDyeColor.a;
      }
   }

   public EntitySheep b(EntityAgeable var1) {
      EntitySheep var2 = (EntitySheep)var1;
      EntitySheep var3 = new EntitySheep(this.o);
      var3.b(this.a(this, var2));
      return var3;
   }

   @Override
   public void v() {
      this.l(false);
      if (this.j_()) {
         this.a(60);
      }
   }

   @Override
   public IEntityLivingData a(DifficultyInstance var1, IEntityLivingData var2) {
      var2 = super.a(var1, var2);
      this.b(a(this.o.s));
      return var2;
   }

   private EnumDyeColor a(EntityAnimal var1, EntityAnimal var2) {
      int var3 = ((EntitySheep)var1).cl().b();
      int var4 = ((EntitySheep)var2).cl().b();
      this.bm.a(0).b(var3);
      this.bm.a(1).b(var4);
      ItemStack var5 = CraftingManager.a().a(this.bm, ((EntitySheep)var1).o);
      int var6;
      if (var5 != null && var5.b() == Items.aW) {
         var6 = var5.i();
      } else {
         var6 = this.o.s.nextBoolean() ? var3 : var4;
      }

      return EnumDyeColor.a(var6);
   }

   @Override
   public float aS() {
      return 0.95F * this.K;
   }

   static {
      bo.put(EnumDyeColor.a, new float[]{1.0F, 1.0F, 1.0F});
      bo.put(EnumDyeColor.b, new float[]{0.85F, 0.5F, 0.2F});
      bo.put(EnumDyeColor.c, new float[]{0.7F, 0.3F, 0.85F});
      bo.put(EnumDyeColor.d, new float[]{0.4F, 0.6F, 0.85F});
      bo.put(EnumDyeColor.e, new float[]{0.9F, 0.9F, 0.2F});
      bo.put(EnumDyeColor.f, new float[]{0.5F, 0.8F, 0.1F});
      bo.put(EnumDyeColor.g, new float[]{0.95F, 0.5F, 0.65F});
      bo.put(EnumDyeColor.h, new float[]{0.3F, 0.3F, 0.3F});
      bo.put(EnumDyeColor.i, new float[]{0.6F, 0.6F, 0.6F});
      bo.put(EnumDyeColor.j, new float[]{0.3F, 0.5F, 0.6F});
      bo.put(EnumDyeColor.k, new float[]{0.5F, 0.25F, 0.7F});
      bo.put(EnumDyeColor.l, new float[]{0.2F, 0.3F, 0.7F});
      bo.put(EnumDyeColor.m, new float[]{0.4F, 0.3F, 0.2F});
      bo.put(EnumDyeColor.n, new float[]{0.4F, 0.5F, 0.2F});
      bo.put(EnumDyeColor.o, new float[]{0.6F, 0.2F, 0.2F});
      bo.put(EnumDyeColor.p, new float[]{0.1F, 0.1F, 0.1F});
   }
}
