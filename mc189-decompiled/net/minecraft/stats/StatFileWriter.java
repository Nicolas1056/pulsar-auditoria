package net.minecraft.stats;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.TupleIntJsonSerializable;

public class StatFileWriter {
   protected final Map<StatBase, TupleIntJsonSerializable> a = Maps.newConcurrentMap();

   public boolean a(Achievement var1) {
      return this.a((StatBase)var1) > 0;
   }

   public boolean b(Achievement var1) {
      return var1.c == null || this.a(var1.c);
   }

   public int c(Achievement var1) {
      if (this.a(var1)) {
         return 0;
      } else {
         int var2 = 0;

         for (Achievement var3 = var1.c; var3 != null && !this.a(var3); var2++) {
            var3 = var3.c;
         }

         return var2;
      }
   }

   public void b(EntityPlayer var1, StatBase var2, int var3) {
      if (!var2.d() || this.b((Achievement)var2)) {
         this.a(var1, var2, this.a(var2) + var3);
      }
   }

   public void a(EntityPlayer var1, StatBase var2, int var3) {
      TupleIntJsonSerializable var4 = this.a.get(var2);
      if (var4 == null) {
         var4 = new TupleIntJsonSerializable();
         this.a.put(var2, var4);
      }

      var4.a(var3);
   }

   public int a(StatBase var1) {
      TupleIntJsonSerializable var2 = this.a.get(var1);
      return var2 == null ? 0 : var2.a();
   }

   public <T extends IJsonSerializable> T b(StatBase var1) {
      TupleIntJsonSerializable var2 = this.a.get(var1);
      return var2 != null ? var2.b() : null;
   }

   public <T extends IJsonSerializable> T a(StatBase var1, T var2) {
      TupleIntJsonSerializable var3 = this.a.get(var1);
      if (var3 == null) {
         var3 = new TupleIntJsonSerializable();
         this.a.put(var1, var3);
      }

      var3.a(var2);
      return (T)var2;
   }
}
