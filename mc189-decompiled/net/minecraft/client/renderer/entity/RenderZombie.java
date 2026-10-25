package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerVillagerArmor;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

public class RenderZombie extends RenderBiped<EntityZombie> {
   private static final ResourceLocation j = new ResourceLocation("textures/entity/zombie/zombie.png");
   private static final ResourceLocation k = new ResourceLocation("textures/entity/zombie/zombie_villager.png");
   private final ModelBiped l;
   private final ModelZombieVillager m;
   private final List<LayerRenderer<EntityZombie>> n;
   private final List<LayerRenderer<EntityZombie>> o;

   public RenderZombie(RenderManager var1) {
      super(var1, new ModelZombie(), 0.5F, 1.0F);
      LayerRenderer var2 = this.h.get(0);
      this.l = this.a;
      this.m = new ModelZombieVillager();
      this.a(new LayerHeldItem(this));
      LayerBipedArmor var3 = new LayerBipedArmor(this) {
         @Override
         protected void a() {
            this.c = new ModelZombie(0.5F, true);
            this.d = new ModelZombie(1.0F, true);
         }
      };
      this.a(var3);
      this.o = Lists.newArrayList(this.h);
      if (var2 instanceof LayerCustomHead) {
         this.b(var2);
         this.a(new LayerCustomHead(this.m.e));
      }

      this.b(var3);
      this.a(new LayerVillagerArmor(this));
      this.n = Lists.newArrayList(this.h);
   }

   public void a(EntityZombie var1, double var2, double var4, double var6, float var8, float var9) {
      this.b(var1);
      super.a(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation a(EntityZombie var1) {
      return var1.co() ? k : j;
   }

   private void b(EntityZombie var1) {
      if (var1.co()) {
         this.f = this.m;
         this.h = this.n;
      } else {
         this.f = this.l;
         this.h = this.o;
      }

      this.a = (ModelBiped)this.f;
   }

   protected void a(EntityZombie var1, float var2, float var3, float var4) {
      if (var1.cp()) {
         var3 += (float)(Math.cos(var1.W * 3.25) * Math.PI * 0.25);
      }

      super.a(var1, var2, var3, var4);
   }
}
