package net.minecraft.client.resources;

import net.minecraft.client.gui.GuiScreenResourcePacks;

public class ResourcePackListEntryFound extends ResourcePackListEntry {
   private final ResourcePackRepository.Entry c;

   public ResourcePackListEntryFound(GuiScreenResourcePacks var1, ResourcePackRepository.Entry var2) {
      super(var1);
      this.c = var2;
   }

   @Override
   protected void d() {
      this.c.a(this.a.P());
   }

   @Override
   protected int a() {
      return this.c.f();
   }

   @Override
   protected String b() {
      return this.c.e();
   }

   @Override
   protected String c() {
      return this.c.d();
   }

   public ResourcePackRepository.Entry j() {
      return this.c;
   }
}
