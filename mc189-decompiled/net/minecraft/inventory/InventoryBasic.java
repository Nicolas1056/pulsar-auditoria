package net.minecraft.inventory;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class InventoryBasic implements IInventory {
   private String a;
   private int b;
   private ItemStack[] c;
   private List<IInvBasic> d;
   private boolean e;

   public InventoryBasic(String var1, boolean var2, int var3) {
      this.a = var1;
      this.e = var2;
      this.b = var3;
      this.c = new ItemStack[var3];
   }

   public InventoryBasic(IChatComponent var1, int var2) {
      this(var1.c(), true, var2);
   }

   public void a(IInvBasic var1) {
      if (this.d == null) {
         this.d = Lists.newArrayList();
      }

      this.d.add(var1);
   }

   public void b(IInvBasic var1) {
      this.d.remove(var1);
   }

   @Override
   public ItemStack a(int var1) {
      return var1 >= 0 && var1 < this.c.length ? this.c[var1] : null;
   }

   @Override
   public ItemStack a(int var1, int var2) {
      if (this.c[var1] != null) {
         if (this.c[var1].b <= var2) {
            ItemStack var4 = this.c[var1];
            this.c[var1] = null;
            this.p_();
            return var4;
         } else {
            ItemStack var3 = this.c[var1].a(var2);
            if (this.c[var1].b == 0) {
               this.c[var1] = null;
            }

            this.p_();
            return var3;
         }
      } else {
         return null;
      }
   }

   public ItemStack a(ItemStack var1) {
      ItemStack var2 = var1.k();

      for (int var3 = 0; var3 < this.b; var3++) {
         ItemStack var4 = this.a(var3);
         if (var4 == null) {
            this.a(var3, var2);
            this.p_();
            return null;
         }

         if (ItemStack.c(var4, var2)) {
            int var5 = Math.min(this.q_(), var4.c());
            int var6 = Math.min(var2.b, var5 - var4.b);
            if (var6 > 0) {
               var4.b += var6;
               var2.b -= var6;
               if (var2.b <= 0) {
                  this.p_();
                  return null;
               }
            }
         }
      }

      if (var2.b != var1.b) {
         this.p_();
      }

      return var2;
   }

   @Override
   public ItemStack b(int var1) {
      if (this.c[var1] != null) {
         ItemStack var2 = this.c[var1];
         this.c[var1] = null;
         return var2;
      } else {
         return null;
      }
   }

   @Override
   public void a(int var1, ItemStack var2) {
      this.c[var1] = var2;
      if (var2 != null && var2.b > this.q_()) {
         var2.b = this.q_();
      }

      this.p_();
   }

   @Override
   public int o_() {
      return this.b;
   }

   @Override
   public String e_() {
      return this.a;
   }

   @Override
   public boolean l_() {
      return this.e;
   }

   public void a(String var1) {
      this.e = true;
      this.a = var1;
   }

   @Override
   public IChatComponent f_() {
      return (IChatComponent)(this.l_() ? new ChatComponentText(this.e_()) : new ChatComponentTranslation(this.e_()));
   }

   @Override
   public int q_() {
      return 64;
   }

   @Override
   public void p_() {
      if (this.d != null) {
         for (int var1 = 0; var1 < this.d.size(); var1++) {
            this.d.get(var1).a(this);
         }
      }
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return true;
   }

   @Override
   public void b(EntityPlayer var1) {
   }

   @Override
   public void c(EntityPlayer var1) {
   }

   @Override
   public boolean b(int var1, ItemStack var2) {
      return true;
   }

   @Override
   public int a_(int var1) {
      return 0;
   }

   @Override
   public void b(int var1, int var2) {
   }

   @Override
   public int g() {
      return 0;
   }

   @Override
   public void l() {
      for (int var1 = 0; var1 < this.c.length; var1++) {
         this.c[var1] = null;
      }
   }
}
