package net.minecraft.tileentity;

import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IInteractionObject;

public class TileEntityEnchantmentTable extends TileEntity implements ITickable, IInteractionObject {
   public int a;
   public float f;
   public float g;
   public float h;
   public float i;
   public float j;
   public float k;
   public float l;
   public float m;
   public float n;
   private static Random o = new Random();
   private String p;

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      if (this.l_()) {
         var1.a("CustomName", this.p);
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      if (var1.b("CustomName", 8)) {
         this.p = var1.j("CustomName");
      }
   }

   @Override
   public void c() {
      this.k = this.j;
      this.m = this.l;
      EntityPlayer var1 = this.b.a((double)(this.c.n() + 0.5F), (double)(this.c.o() + 0.5F), (double)(this.c.p() + 0.5F), 3.0);
      if (var1 != null) {
         double var2 = var1.s - (this.c.n() + 0.5F);
         double var4 = var1.u - (this.c.p() + 0.5F);
         this.n = (float)MathHelper.b(var4, var2);
         this.j += 0.1F;
         if (this.j < 0.5F || o.nextInt(40) == 0) {
            float var6 = this.h;

            do {
               this.h = this.h + (o.nextInt(4) - o.nextInt(4));
            } while (var6 == this.h);
         }
      } else {
         this.n += 0.02F;
         this.j -= 0.1F;
      }

      while (this.l >= (float) Math.PI) {
         this.l -= (float) (Math.PI * 2);
      }

      while (this.l < (float) -Math.PI) {
         this.l += (float) (Math.PI * 2);
      }

      while (this.n >= (float) Math.PI) {
         this.n -= (float) (Math.PI * 2);
      }

      while (this.n < (float) -Math.PI) {
         this.n += (float) (Math.PI * 2);
      }

      float var7 = this.n - this.l;

      while (var7 >= (float) Math.PI) {
         var7 -= (float) (Math.PI * 2);
      }

      while (var7 < (float) -Math.PI) {
         var7 += (float) (Math.PI * 2);
      }

      this.l += var7 * 0.4F;
      this.j = MathHelper.a(this.j, 0.0F, 1.0F);
      this.a++;
      this.g = this.f;
      float var3 = (this.h - this.f) * 0.4F;
      float var9 = 0.2F;
      var3 = MathHelper.a(var3, -var9, var9);
      this.i = this.i + (var3 - this.i) * 0.9F;
      this.f = this.f + this.i;
   }

   @Override
   public String e_() {
      return this.l_() ? this.p : "container.enchant";
   }

   @Override
   public boolean l_() {
      return this.p != null && this.p.length() > 0;
   }

   public void a(String var1) {
      this.p = var1;
   }

   @Override
   public IChatComponent f_() {
      return (IChatComponent)(this.l_() ? new ChatComponentText(this.e_()) : new ChatComponentTranslation(this.e_()));
   }

   @Override
   public Container a(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerEnchantment(var1, this.b, this.c);
   }

   @Override
   public String k() {
      return "minecraft:enchanting_table";
   }
}
