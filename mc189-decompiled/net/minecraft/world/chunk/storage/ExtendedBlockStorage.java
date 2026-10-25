package net.minecraft.world.chunk.storage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.NibbleArray;

public class ExtendedBlockStorage {
   private int a;
   private int b;
   private int c;
   private char[] d;
   private NibbleArray e;
   private NibbleArray f;

   public ExtendedBlockStorage(int var1, boolean var2) {
      this.a = var1;
      this.d = new char[4096];
      this.e = new NibbleArray();
      if (var2) {
         this.f = new NibbleArray();
      }
   }

   public IBlockState a(int var1, int var2, int var3) {
      IBlockState var4 = Block.d.a(this.d[var2 << 8 | var3 << 4 | var1]);
      return var4 != null ? var4 : Blocks.a.Q();
   }

   public void a(int var1, int var2, int var3, IBlockState var4) {
      IBlockState var5 = this.a(var1, var2, var3);
      Block var6 = var5.c();
      Block var7 = var4.c();
      if (var6 != Blocks.a) {
         this.b--;
         if (var6.y()) {
            this.c--;
         }
      }

      if (var7 != Blocks.a) {
         this.b++;
         if (var7.y()) {
            this.c++;
         }
      }

      this.d[var2 << 8 | var3 << 4 | var1] = (char)Block.d.b(var4);
   }

   public Block b(int var1, int var2, int var3) {
      return this.a(var1, var2, var3).c();
   }

   public int c(int var1, int var2, int var3) {
      IBlockState var4 = this.a(var1, var2, var3);
      return var4.c().c(var4);
   }

   public boolean a() {
      return this.b == 0;
   }

   public boolean b() {
      return this.c > 0;
   }

   public int d() {
      return this.a;
   }

   public void a(int var1, int var2, int var3, int var4) {
      this.f.a(var1, var2, var3, var4);
   }

   public int d(int var1, int var2, int var3) {
      return this.f.a(var1, var2, var3);
   }

   public void b(int var1, int var2, int var3, int var4) {
      this.e.a(var1, var2, var3, var4);
   }

   public int e(int var1, int var2, int var3) {
      return this.e.a(var1, var2, var3);
   }

   public void e() {
      this.b = 0;
      this.c = 0;

      for (int var1 = 0; var1 < 16; var1++) {
         for (int var2 = 0; var2 < 16; var2++) {
            for (int var3 = 0; var3 < 16; var3++) {
               Block var4 = this.b(var1, var2, var3);
               if (var4 != Blocks.a) {
                  this.b++;
                  if (var4.y()) {
                     this.c++;
                  }
               }
            }
         }
      }
   }

   public char[] g() {
      return this.d;
   }

   public void a(char[] var1) {
      this.d = var1;
   }

   public NibbleArray h() {
      return this.e;
   }

   public NibbleArray i() {
      return this.f;
   }

   public void a(NibbleArray var1) {
      this.e = var1;
   }

   public void b(NibbleArray var1) {
      this.f = var1;
   }
}
