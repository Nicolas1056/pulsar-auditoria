package net.minecraft.client.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public abstract class ModelBase {
   public float p;
   public boolean q;
   public boolean r = true;
   public List<ModelRenderer> s = Lists.newArrayList();
   private Map<String, TextureOffset> a = Maps.newHashMap();
   public int t = 64;
   public int u = 32;

   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
   }

   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
   }

   public void a(EntityLivingBase var1, float var2, float var3, float var4) {
   }

   public ModelRenderer a(Random var1) {
      return this.s.get(var1.nextInt(this.s.size()));
   }

   protected void a(String var1, int var2, int var3) {
      this.a.put(var1, new TextureOffset(var2, var3));
   }

   public TextureOffset a(String var1) {
      return this.a.get(var1);
   }

   public static void a(ModelRenderer var0, ModelRenderer var1) {
      var1.f = var0.f;
      var1.g = var0.g;
      var1.h = var0.h;
      var1.c = var0.c;
      var1.d = var0.d;
      var1.e = var0.e;
   }

   public void a(ModelBase var1) {
      this.p = var1.p;
      this.q = var1.q;
      this.r = var1.r;
   }
}
