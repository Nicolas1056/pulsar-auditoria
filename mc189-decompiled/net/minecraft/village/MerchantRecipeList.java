package net.minecraft.village;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;

public class MerchantRecipeList extends ArrayList<MerchantRecipe> {
   public MerchantRecipeList() {
   }

   public MerchantRecipeList(NBTTagCompound var1) {
      this.a(var1);
   }

   public MerchantRecipe a(ItemStack var1, ItemStack var2, int var3) {
      if (var3 > 0 && var3 < this.size()) {
         MerchantRecipe var6 = this.get(var3);
         return !this.a(var1, var6.a())
               || (var2 != null || var6.c()) && (!var6.c() || !this.a(var2, var6.b()))
               || var1.b < var6.a().b
               || var6.c() && var2.b < var6.b().b
            ? null
            : var6;
      } else {
         for (int var4 = 0; var4 < this.size(); var4++) {
            MerchantRecipe var5 = this.get(var4);
            if (this.a(var1, var5.a()) && var1.b >= var5.a().b && (!var5.c() && var2 == null || var5.c() && this.a(var2, var5.b()) && var2.b >= var5.b().b)) {
               return var5;
            }
         }

         return null;
      }
   }

   private boolean a(ItemStack var1, ItemStack var2) {
      return ItemStack.c(var1, var2) && (!var2.n() || var1.n() && NBTUtil.a(var2.o(), var1.o(), false));
   }

   public void a(PacketBuffer var1) {
      var1.writeByte((byte)(this.size() & 0xFF));

      for (int var2 = 0; var2 < this.size(); var2++) {
         MerchantRecipe var3 = this.get(var2);
         var1.a(var3.a());
         var1.a(var3.d());
         ItemStack var4 = var3.b();
         var1.writeBoolean(var4 != null);
         if (var4 != null) {
            var1.a(var4);
         }

         var1.writeBoolean(var3.h());
         var1.writeInt(var3.e());
         var1.writeInt(var3.f());
      }
   }

   public static MerchantRecipeList b(PacketBuffer var0) throws IOException {
      MerchantRecipeList var1 = new MerchantRecipeList();
      int var2 = var0.readByte() & 255;

      for (int var3 = 0; var3 < var2; var3++) {
         ItemStack var4 = var0.i();
         ItemStack var5 = var0.i();
         ItemStack var6 = null;
         if (var0.readBoolean()) {
            var6 = var0.i();
         }

         boolean var7 = var0.readBoolean();
         int var8 = var0.readInt();
         int var9 = var0.readInt();
         MerchantRecipe var10 = new MerchantRecipe(var4, var6, var5, var8, var9);
         if (var7) {
            var10.i();
         }

         var1.add(var10);
      }

      return var1;
   }

   public void a(NBTTagCompound var1) {
      NBTTagList var2 = var1.c("Recipes", 10);

      for (int var3 = 0; var3 < var2.c(); var3++) {
         NBTTagCompound var4 = var2.b(var3);
         this.add(new MerchantRecipe(var4));
      }
   }

   public NBTTagCompound a() {
      NBTTagCompound var1 = new NBTTagCompound();
      NBTTagList var2 = new NBTTagList();

      for (int var3 = 0; var3 < this.size(); var3++) {
         MerchantRecipe var4 = this.get(var3);
         var2.a(var4.k());
      }

      var1.a("Recipes", var2);
      return var1;
   }
}
