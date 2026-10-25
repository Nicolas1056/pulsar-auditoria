package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemRecord extends Item {
   private static final Map<String, ItemRecord> b = Maps.newHashMap();
   public final String a;

   protected ItemRecord(String var1) {
      this.a = var1;
      this.h = 1;
      this.a(CreativeTabs.f);
      b.put("records." + var1, this);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      IBlockState var9 = var3.p(var4);
      if (var9.c() != Blocks.aN || var9.b(BlockJukebox.a)) {
         return false;
      } else if (var3.D) {
         return true;
      } else {
         ((BlockJukebox)Blocks.aN).a(var3, var4, var9, var1);
         var3.a(null, 1005, var4, Item.b(this));
         var1.b--;
         var2.b(StatList.X);
         return true;
      }
   }

   @Override
   public void a(ItemStack var1, EntityPlayer var2, List<String> var3, boolean var4) {
      var3.add(this.g());
   }

   public String g() {
      return StatCollector.a("item.record." + this.a + ".desc");
   }

   @Override
   public EnumRarity g(ItemStack var1) {
      return EnumRarity.c;
   }

   public static ItemRecord b(String var0) {
      return b.get(var0);
   }
}
