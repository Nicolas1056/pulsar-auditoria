package net.minecraft.client.renderer.tileentity;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;

public class TileEntityItemStackRenderer {
   public static TileEntityItemStackRenderer a = new TileEntityItemStackRenderer();
   private TileEntityChest b = new TileEntityChest(0);
   private TileEntityChest c = new TileEntityChest(1);
   private TileEntityEnderChest d = new TileEntityEnderChest();
   private TileEntityBanner e = new TileEntityBanner();
   private TileEntitySkull f = new TileEntitySkull();

   public void a(ItemStack var1) {
      if (var1.b() == Items.cE) {
         this.e.a(var1);
         TileEntityRendererDispatcher.a.a(this.e, 0.0, 0.0, 0.0, 0.0F);
      } else if (var1.b() == Items.bX) {
         GameProfile var2 = null;
         if (var1.n()) {
            NBTTagCompound var3 = var1.o();
            if (var3.b("SkullOwner", 10)) {
               var2 = NBTUtil.a(var3.m("SkullOwner"));
            } else if (var3.b("SkullOwner", 8) && var3.j("SkullOwner").length() > 0) {
               GameProfile var4 = new GameProfile(null, var3.j("SkullOwner"));
               var2 = TileEntitySkull.b(var4);
               var3.o("SkullOwner");
               var3.a("SkullOwner", NBTUtil.a(new NBTTagCompound(), var2));
            }
         }

         if (TileEntitySkullRenderer.c != null) {
            GlStateManager.E();
            GlStateManager.b(-0.5F, 0.0F, -0.5F);
            GlStateManager.a(2.0F, 2.0F, 2.0F);
            GlStateManager.p();
            TileEntitySkullRenderer.c.a(0.0F, 0.0F, 0.0F, EnumFacing.b, 0.0F, var1.i(), var2, -1);
            GlStateManager.o();
            GlStateManager.F();
         }
      } else {
         Block var5 = Block.a(var1.b());
         if (var5 == Blocks.bQ) {
            TileEntityRendererDispatcher.a.a(this.d, 0.0, 0.0, 0.0, 0.0F);
         } else if (var5 == Blocks.cg) {
            TileEntityRendererDispatcher.a.a(this.c, 0.0, 0.0, 0.0, 0.0F);
         } else {
            TileEntityRendererDispatcher.a.a(this.b, 0.0, 0.0, 0.0, 0.0F);
         }
      }
   }
}
