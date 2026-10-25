package net.minecraft.client.resources.data;

import java.util.Collections;
import java.util.List;

public class TextureMetadataSection implements IMetadataSection {
   private final boolean a;
   private final boolean b;
   private final List<Integer> c;

   public TextureMetadataSection(boolean var1, boolean var2, List<Integer> var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public boolean a() {
      return this.a;
   }

   public boolean b() {
      return this.b;
   }

   public List<Integer> c() {
      return Collections.unmodifiableList(this.c);
   }
}
