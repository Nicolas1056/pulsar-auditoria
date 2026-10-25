package net.minecraft.client.resources.data;

import java.util.Collection;
import net.minecraft.client.resources.Language;

public class LanguageMetadataSection implements IMetadataSection {
   private final Collection<Language> a;

   public LanguageMetadataSection(Collection<Language> var1) {
      this.a = var1;
   }

   public Collection<Language> a() {
      return this.a;
   }
}
