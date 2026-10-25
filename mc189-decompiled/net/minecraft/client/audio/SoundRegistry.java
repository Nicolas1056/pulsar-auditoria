package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.RegistrySimple;
import net.minecraft.util.ResourceLocation;

public class SoundRegistry extends RegistrySimple<ResourceLocation, SoundEventAccessorComposite> {
   private Map<ResourceLocation, SoundEventAccessorComposite> a;

   @Override
   protected Map<ResourceLocation, SoundEventAccessorComposite> b() {
      this.a = Maps.newHashMap();
      return this.a;
   }

   public void a(SoundEventAccessorComposite var1) {
      this.a(var1.c(), var1);
   }

   public void a() {
      this.a.clear();
   }
}
