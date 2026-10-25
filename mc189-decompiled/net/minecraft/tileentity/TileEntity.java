package net.minecraft.tileentity;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TileEntity {
   private static final Logger a = LogManager.getLogger();
   private static Map<String, Class<? extends TileEntity>> f = Maps.newHashMap();
   private static Map<Class<? extends TileEntity>, String> g = Maps.newHashMap();
   protected World b;
   protected BlockPos c = BlockPos.a;
   protected boolean d;
   private int h = -1;
   protected Block e;

   private static void a(Class<? extends TileEntity> var0, String var1) {
      if (f.containsKey(var1)) {
         throw new IllegalArgumentException("Duplicate id: " + var1);
      } else {
         f.put(var1, var0);
         g.put(var0, var1);
      }
   }

   public World z() {
      return this.b;
   }

   public void a(World var1) {
      this.b = var1;
   }

   public boolean t() {
      return this.b != null;
   }

   public void a(NBTTagCompound var1) {
      this.c = new BlockPos(var1.f("x"), var1.f("y"), var1.f("z"));
   }

   public void b(NBTTagCompound var1) {
      String var2 = g.get(this.getClass());
      if (var2 == null) {
         throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
      } else {
         var1.a("id", var2);
         var1.a("x", this.c.n());
         var1.a("y", this.c.o());
         var1.a("z", this.c.p());
      }
   }

   public static TileEntity c(NBTTagCompound var0) {
      TileEntity var1 = null;

      try {
         Class var2 = f.get(var0.j("id"));
         if (var2 != null) {
            var1 = (TileEntity)var2.newInstance();
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      if (var1 != null) {
         var1.a(var0);
      } else {
         a.warn("Skipping BlockEntity with id " + var0.j("id"));
      }

      return var1;
   }

   public int u() {
      if (this.h == -1) {
         IBlockState var1 = this.b.p(this.c);
         this.h = var1.c().c(var1);
      }

      return this.h;
   }

   public void p_() {
      if (this.b != null) {
         IBlockState var1 = this.b.p(this.c);
         this.h = var1.c().c(var1);
         this.b.b(this.c, this);
         if (this.w() != Blocks.a) {
            this.b.e(this.c, this.w());
         }
      }
   }

   public double a(double var1, double var3, double var5) {
      double var7 = this.c.n() + 0.5 - var1;
      double var9 = this.c.o() + 0.5 - var3;
      double var11 = this.c.p() + 0.5 - var5;
      return var7 * var7 + var9 * var9 + var11 * var11;
   }

   public double s() {
      return 4096.0;
   }

   public BlockPos v() {
      return this.c;
   }

   public Block w() {
      if (this.e == null) {
         this.e = this.b.p(this.c).c();
      }

      return this.e;
   }

   public Packet y_() {
      return null;
   }

   public boolean x() {
      return this.d;
   }

   public void y() {
      this.d = true;
   }

   public void D() {
      this.d = false;
   }

   public boolean c(int var1, int var2) {
      return false;
   }

   public void E() {
      this.e = null;
      this.h = -1;
   }

   public void a(CrashReportCategory var1) {
      var1.a("Name", new Callable<String>() {
         public String a() throws Exception {
            return TileEntity.g.get(TileEntity.this.getClass()) + " // " + TileEntity.this.getClass().getCanonicalName();
         }
      });
      if (this.b != null) {
         CrashReportCategory.a(var1, this.c, this.w(), this.u());
         var1.a("Actual block type", new Callable<String>() {
            public String a() throws Exception {
               int var1 = Block.a(TileEntity.this.b.p(TileEntity.this.c).c());

               try {
                  return String.format("ID #%d (%s // %s)", var1, Block.c(var1).a(), Block.c(var1).getClass().getCanonicalName());
               } catch (Throwable var3) {
                  return "ID #" + var1;
               }
            }
         });
         var1.a("Actual block data value", new Callable<String>() {
            public String a() throws Exception {
               IBlockState var1 = TileEntity.this.b.p(TileEntity.this.c);
               int var2 = var1.c().c(var1);
               if (var2 < 0) {
                  return "Unknown? (Got " + var2 + ")";
               } else {
                  String var3 = String.format("%4s", Integer.toBinaryString(var2)).replace(" ", "0");
                  return String.format("%1$d / 0x%1$X / 0b%2$s", var2, var3);
               }
            }
         });
      }
   }

   public void a(BlockPos var1) {
      this.c = var1;
   }

   public boolean F() {
      return false;
   }

   static {
      a(TileEntityFurnace.class, "Furnace");
      a(TileEntityChest.class, "Chest");
      a(TileEntityEnderChest.class, "EnderChest");
      a(BlockJukebox.TileEntityJukebox.class, "RecordPlayer");
      a(TileEntityDispenser.class, "Trap");
      a(TileEntityDropper.class, "Dropper");
      a(TileEntitySign.class, "Sign");
      a(TileEntityMobSpawner.class, "MobSpawner");
      a(TileEntityNote.class, "Music");
      a(TileEntityPiston.class, "Piston");
      a(TileEntityBrewingStand.class, "Cauldron");
      a(TileEntityEnchantmentTable.class, "EnchantTable");
      a(TileEntityEndPortal.class, "Airportal");
      a(TileEntityCommandBlock.class, "Control");
      a(TileEntityBeacon.class, "Beacon");
      a(TileEntitySkull.class, "Skull");
      a(TileEntityDaylightDetector.class, "DLDetector");
      a(TileEntityHopper.class, "Hopper");
      a(TileEntityComparator.class, "Comparator");
      a(TileEntityFlowerPot.class, "FlowerPot");
      a(TileEntityBanner.class, "Banner");
   }
}
