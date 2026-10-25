package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemModelMesher {
   private final Map<Integer, ModelResourceLocation> a = Maps.newHashMap();
   private final Map<Integer, IBakedModel> b = Maps.newHashMap();
   private final Map<Item, ItemMeshDefinition> c = Maps.newHashMap();
   private final ModelManager d;

   public ItemModelMesher(ModelManager var1) {
      this.d = var1;
   }

   public TextureAtlasSprite a(Item var1) {
      return this.a(var1, 0);
   }

   public TextureAtlasSprite a(Item var1, int var2) {
      return this.a(new ItemStack(var1, 1, var2)).e();
   }

   public IBakedModel a(ItemStack var1) {
      Item var2 = var1.b();
      IBakedModel var3 = this.b(var2, this.b(var1));
      if (var3 == null) {
         ItemMeshDefinition var4 = this.c.get(var2);
         if (var4 != null) {
            var3 = this.d.a(var4.a(var1));
         }
      }

      if (var3 == null) {
         var3 = this.d.a();
      }

      return var3;
   }

   protected int b(ItemStack var1) {
      return var1.e() ? 0 : var1.i();
   }

   protected IBakedModel b(Item var1, int var2) {
      return this.b.get(this.c(var1, var2));
   }

   private int c(Item var1, int var2) {
      return Item.b(var1) << 16 | var2;
   }

   public void a(Item var1, int var2, ModelResourceLocation var3) {
      this.a.put(this.c(var1, var2), var3);
      this.b.put(this.c(var1, var2), this.d.a(var3));
   }

   public void a(Item var1, ItemMeshDefinition var2) {
      this.c.put(var1, var2);
   }

   public ModelManager a() {
      return this.d;
   }

   public void b() {
      this.b.clear();

      for (Entry var2 : this.a.entrySet()) {
         this.b.put((Integer)var2.getKey(), this.d.a((ModelResourceLocation)var2.getValue()));
      }
   }
}
