package net.minecraft.client.renderer.block.statemap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class StateMap extends StateMapperBase {
   private final IProperty<?> a;
   private final String c;
   private final List<IProperty<?>> d;

   private StateMap(IProperty<?> var1, String var2, List<IProperty<?>> var3) {
      this.a = var1;
      this.c = var2;
      this.d = var3;
   }

   @Override
   protected ModelResourceLocation a(IBlockState var1) {
      LinkedHashMap var2 = Maps.newLinkedHashMap(var1.b());
      String var3;
      if (this.a == null) {
         var3 = Block.c.c(var1.c()).toString();
      } else {
         var3 = ((IProperty<Object>)this.a).a((Comparable)var2.remove(this.a));
      }

      if (this.c != null) {
         var3 = var3 + this.c;
      }

      for (IProperty var5 : this.d) {
         var2.remove(var5);
      }

      return new ModelResourceLocation(var3, this.a(var2));
   }

   public static class Builder {
      private IProperty<?> a;
      private String b;
      private final List<IProperty<?>> c = Lists.newArrayList();

      public StateMap.Builder a(IProperty<?> var1) {
         this.a = var1;
         return this;
      }

      public StateMap.Builder a(String var1) {
         this.b = var1;
         return this;
      }

      public StateMap.Builder a(IProperty<?>... var1) {
         Collections.addAll(this.c, var1);
         return this;
      }

      public StateMap a() {
         return new StateMap(this.a, this.b, this.c);
      }
   }
}
