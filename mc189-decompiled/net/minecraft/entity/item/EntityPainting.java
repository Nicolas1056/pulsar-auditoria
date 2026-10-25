package net.minecraft.entity.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityPainting extends EntityHanging {
   public EntityPainting.EnumArt c;

   public EntityPainting(World var1) {
      super(var1);
   }

   public EntityPainting(World var1, BlockPos var2, EnumFacing var3) {
      super(var1, var2);
      ArrayList var4 = Lists.newArrayList();

      for (EntityPainting.EnumArt var8 : EntityPainting.EnumArt.values()) {
         this.c = var8;
         this.a(var3);
         if (this.j()) {
            var4.add(var8);
         }
      }

      if (!var4.isEmpty()) {
         this.c = (EntityPainting.EnumArt)var4.get(this.V.nextInt(var4.size()));
      }

      this.a(var3);
   }

   public EntityPainting(World var1, BlockPos var2, EnumFacing var3, String var4) {
      this(var1, var2, var3);

      for (EntityPainting.EnumArt var8 : EntityPainting.EnumArt.values()) {
         if (var8.B.equals(var4)) {
            this.c = var8;
            break;
         }
      }

      this.a(var3);
   }

   @Override
   public void b(NBTTagCompound var1) {
      var1.a("Motive", this.c.B);
      super.b(var1);
   }

   @Override
   public void a(NBTTagCompound var1) {
      String var2 = var1.j("Motive");

      for (EntityPainting.EnumArt var6 : EntityPainting.EnumArt.values()) {
         if (var6.B.equals(var2)) {
            this.c = var6;
         }
      }

      if (this.c == null) {
         this.c = EntityPainting.EnumArt.a;
      }

      super.a(var1);
   }

   @Override
   public int l() {
      return this.c.C;
   }

   @Override
   public int m() {
      return this.c.D;
   }

   @Override
   public void b(Entity var1) {
      if (this.o.Q().b("doEntityDrops")) {
         if (var1 instanceof EntityPlayer) {
            EntityPlayer var2 = (EntityPlayer)var1;
            if (var2.bA.d) {
               return;
            }
         }

         this.a(new ItemStack(Items.an), 0.0F);
      }
   }

   @Override
   public void b(double var1, double var3, double var5, float var7, float var8) {
      BlockPos var9 = this.a.a(var1 - this.s, var3 - this.t, var5 - this.u);
      this.b(var9.n(), var9.o(), var9.p());
   }

   @Override
   public void a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      BlockPos var11 = this.a.a(var1 - this.s, var3 - this.t, var5 - this.u);
      this.b(var11.n(), var11.o(), var11.p());
   }

   public static enum EnumArt {
      a("Kebab", 16, 16, 0, 0),
      b("Aztec", 16, 16, 16, 0),
      c("Alban", 16, 16, 32, 0),
      d("Aztec2", 16, 16, 48, 0),
      e("Bomb", 16, 16, 64, 0),
      f("Plant", 16, 16, 80, 0),
      g("Wasteland", 16, 16, 96, 0),
      h("Pool", 32, 16, 0, 32),
      i("Courbet", 32, 16, 32, 32),
      j("Sea", 32, 16, 64, 32),
      k("Sunset", 32, 16, 96, 32),
      l("Creebet", 32, 16, 128, 32),
      m("Wanderer", 16, 32, 0, 64),
      n("Graham", 16, 32, 16, 64),
      o("Match", 32, 32, 0, 128),
      p("Bust", 32, 32, 32, 128),
      q("Stage", 32, 32, 64, 128),
      r("Void", 32, 32, 96, 128),
      s("SkullAndRoses", 32, 32, 128, 128),
      t("Wither", 32, 32, 160, 128),
      u("Fighters", 64, 32, 0, 96),
      v("Pointer", 64, 64, 0, 192),
      w("Pigscene", 64, 64, 64, 192),
      x("BurningSkull", 64, 64, 128, 192),
      y("Skeleton", 64, 48, 192, 64),
      z("DonkeyKong", 64, 48, 192, 112);

      public static final int A = "SkullAndRoses".length();
      public final String B;
      public final int C;
      public final int D;
      public final int E;
      public final int F;

      private EnumArt(String var3, int var4, int var5, int var6, int var7) {
         this.B = var3;
         this.C = var4;
         this.D = var5;
         this.E = var6;
         this.F = var7;
      }
   }
}
