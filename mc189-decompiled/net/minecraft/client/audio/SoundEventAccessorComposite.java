package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.util.ResourceLocation;

public class SoundEventAccessorComposite implements ISoundEventAccessor<SoundPoolEntry> {
   private final List<ISoundEventAccessor<SoundPoolEntry>> a = Lists.newArrayList();
   private final Random b = new Random();
   private final ResourceLocation c;
   private final SoundCategory d;
   private double e;
   private double f;

   public SoundEventAccessorComposite(ResourceLocation var1, double var2, double var4, SoundCategory var6) {
      this.c = var1;
      this.f = var4;
      this.e = var2;
      this.d = var6;
   }

   @Override
   public int a() {
      int var1 = 0;

      for (ISoundEventAccessor var3 : this.a) {
         var1 += var3.a();
      }

      return var1;
   }

   public SoundPoolEntry b() {
      int var1 = this.a();
      if (!this.a.isEmpty() && var1 != 0) {
         int var2 = this.b.nextInt(var1);

         for (ISoundEventAccessor var4 : this.a) {
            var2 -= var4.a();
            if (var2 < 0) {
               SoundPoolEntry var5 = (SoundPoolEntry)var4.g();
               var5.a(var5.b() * this.e);
               var5.b(var5.c() * this.f);
               return var5;
            }
         }

         return SoundHandler.a;
      } else {
         return SoundHandler.a;
      }
   }

   public void a(ISoundEventAccessor<SoundPoolEntry> var1) {
      this.a.add(var1);
   }

   public ResourceLocation c() {
      return this.c;
   }

   public SoundCategory d() {
      return this.d;
   }
}
