package net.minecraft.client.resources;

import com.google.gson.JsonParseException;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcePackListEntryDefault extends ResourcePackListEntry {
   private static final Logger c = LogManager.getLogger();
   private final IResourcePack d = this.a.R().a;
   private final ResourceLocation e;

   public ResourcePackListEntryDefault(GuiScreenResourcePacks var1) {
      super(var1);

      DynamicTexture var2;
      try {
         var2 = new DynamicTexture(this.d.a());
      } catch (IOException var4) {
         var2 = TextureUtil.a;
      }

      this.e = this.a.P().a("texturepackicon", var2);
   }

   @Override
   protected int a() {
      return 1;
   }

   @Override
   protected String b() {
      try {
         PackMetadataSection var1 = this.d.a(this.a.R().b, "pack");
         if (var1 != null) {
            return var1.a().d();
         }
      } catch (JsonParseException var2) {
         c.error("Couldn't load metadata info", var2);
      } catch (IOException var3) {
         c.error("Couldn't load metadata info", var3);
      }

      return EnumChatFormatting.m + "Missing " + "pack.mcmeta" + " :(";
   }

   @Override
   protected boolean f() {
      return false;
   }

   @Override
   protected boolean g() {
      return false;
   }

   @Override
   protected boolean h() {
      return false;
   }

   @Override
   protected boolean i() {
      return false;
   }

   @Override
   protected String c() {
      return "Default";
   }

   @Override
   protected void d() {
      this.a.P().a(this.e);
   }

   @Override
   protected boolean e() {
      return false;
   }
}
