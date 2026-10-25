package net.minecraft.crash;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

public class CrashReportCategory {
   private final CrashReport a;
   private final String b;
   private final List<CrashReportCategory.Entry> c = Lists.newArrayList();
   private StackTraceElement[] d = new StackTraceElement[0];

   public CrashReportCategory(CrashReport var1, String var2) {
      this.a = var1;
      this.b = var2;
   }

   public static String a(double var0, double var2, double var4) {
      return String.format("%.2f,%.2f,%.2f - %s", var0, var2, var4, a(new BlockPos(var0, var2, var4)));
   }

   public static String a(BlockPos var0) {
      int var1 = var0.n();
      int var2 = var0.o();
      int var3 = var0.p();
      StringBuilder var4 = new StringBuilder();

      try {
         var4.append(String.format("World: (%d,%d,%d)", var1, var2, var3));
      } catch (Throwable var17) {
         var4.append("(Error finding world loc)");
      }

      var4.append(", ");

      try {
         int var5 = var1 >> 4;
         int var6 = var3 >> 4;
         int var7 = var1 & 15;
         int var8 = var2 >> 4;
         int var9 = var3 & 15;
         int var10 = var5 << 4;
         int var11 = var6 << 4;
         int var12 = (var5 + 1 << 4) - 1;
         int var13 = (var6 + 1 << 4) - 1;
         var4.append(
            String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", var7, var8, var9, var5, var6, var10, var11, var12, var13)
         );
      } catch (Throwable var16) {
         var4.append("(Error finding chunk loc)");
      }

      var4.append(", ");

      try {
         int var18 = var1 >> 9;
         int var19 = var3 >> 9;
         int var20 = var18 << 5;
         int var21 = var19 << 5;
         int var22 = (var18 + 1 << 5) - 1;
         int var23 = (var19 + 1 << 5) - 1;
         int var24 = var18 << 9;
         int var25 = var19 << 9;
         int var26 = (var18 + 1 << 9) - 1;
         int var14 = (var19 + 1 << 9) - 1;
         var4.append(
            String.format(
               "Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)",
               var18,
               var19,
               var20,
               var21,
               var22,
               var23,
               var24,
               var25,
               var26,
               var14
            )
         );
      } catch (Throwable var15) {
         var4.append("(Error finding world loc)");
      }

      return var4.toString();
   }

   public void a(String var1, Callable<String> var2) {
      try {
         this.a(var1, var2.call());
      } catch (Throwable var4) {
         this.a(var1, var4);
      }
   }

   public void a(String var1, Object var2) {
      this.c.add(new CrashReportCategory.Entry(var1, var2));
   }

   public void a(String var1, Throwable var2) {
      this.a(var1, (Object)var2);
   }

   public int a(int var1) {
      StackTraceElement[] var2 = Thread.currentThread().getStackTrace();
      if (var2.length <= 0) {
         return 0;
      } else {
         this.d = new StackTraceElement[var2.length - 3 - var1];
         System.arraycopy(var2, 3 + var1, this.d, 0, this.d.length);
         return this.d.length;
      }
   }

   public boolean a(StackTraceElement var1, StackTraceElement var2) {
      if (this.d.length != 0 && var1 != null) {
         StackTraceElement var3 = this.d[0];
         if (var3.isNativeMethod() == var1.isNativeMethod()
            && var3.getClassName().equals(var1.getClassName())
            && var3.getFileName().equals(var1.getFileName())
            && var3.getMethodName().equals(var1.getMethodName())) {
            if (var2 != null != this.d.length > 1) {
               return false;
            } else if (var2 != null && !this.d[1].equals(var2)) {
               return false;
            } else {
               this.d[0] = var1;
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void b(int var1) {
      StackTraceElement[] var2 = new StackTraceElement[this.d.length - var1];
      System.arraycopy(this.d, 0, var2, 0, var2.length);
      this.d = var2;
   }

   public void a(StringBuilder var1) {
      var1.append("-- ").append(this.b).append(" --\n");
      var1.append("Details:");

      for (CrashReportCategory.Entry var3 : this.c) {
         var1.append("\n\t");
         var1.append(var3.a());
         var1.append(": ");
         var1.append(var3.b());
      }

      if (this.d != null && this.d.length > 0) {
         var1.append("\nStacktrace:");

         for (StackTraceElement var5 : this.d) {
            var1.append("\n\tat ");
            var1.append(var5.toString());
         }
      }
   }

   public StackTraceElement[] a() {
      return this.d;
   }

   public static void a(CrashReportCategory var0, final BlockPos var1, final Block var2, final int var3) {
      final int var4 = Block.a(var2);
      var0.a("Block type", new Callable<String>() {
         public String a() throws Exception {
            try {
               return String.format("ID #%d (%s // %s)", var4, var2.a(), var2.getClass().getCanonicalName());
            } catch (Throwable var2x) {
               return "ID #" + var4;
            }
         }
      });
      var0.a("Block data value", new Callable<String>() {
         public String a() throws Exception {
            if (var3 < 0) {
               return "Unknown? (Got " + var3 + ")";
            } else {
               String var1 = String.format("%4s", Integer.toBinaryString(var3)).replace(" ", "0");
               return String.format("%1$d / 0x%1$X / 0b%2$s", var3, var1);
            }
         }
      });
      var0.a("Block location", new Callable<String>() {
         public String a() throws Exception {
            return CrashReportCategory.a(var1);
         }
      });
   }

   public static void a(CrashReportCategory var0, final BlockPos var1, final IBlockState var2) {
      var0.a("Block", new Callable<String>() {
         public String a() throws Exception {
            return var2.toString();
         }
      });
      var0.a("Block location", new Callable<String>() {
         public String a() throws Exception {
            return CrashReportCategory.a(var1);
         }
      });
   }

   static class Entry {
      private final String a;
      private final String b;

      public Entry(String var1, Object var2) {
         this.a = var1;
         if (var2 == null) {
            this.b = "~~NULL~~";
         } else if (var2 instanceof Throwable) {
            Throwable var3 = (Throwable)var2;
            this.b = "~~ERROR~~ " + var3.getClass().getSimpleName() + ": " + var3.getMessage();
         } else {
            this.b = var2.toString();
         }
      }

      public String a() {
         return this.a;
      }

      public String b() {
         return this.b;
      }
   }
}
