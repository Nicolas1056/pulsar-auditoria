package net.minecraft.server.integrated;

import com.mojang.authlib.GameProfile;
import java.net.SocketAddress;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.ServerConfigurationManager;

public class IntegratedPlayerList extends ServerConfigurationManager {
   private NBTTagCompound f;

   public IntegratedPlayerList(IntegratedServer var1) {
      super(var1);
      this.a(10);
   }

   @Override
   protected void b(EntityPlayerMP var1) {
      if (var1.e_().equals(this.b().S())) {
         this.f = new NBTTagCompound();
         var1.e(this.f);
      }

      super.b(var1);
   }

   @Override
   public String a(SocketAddress var1, GameProfile var2) {
      return var2.getName().equalsIgnoreCase(this.b().S()) && this.a(var2.getName()) != null ? "That name is already taken." : super.a(var1, var2);
   }

   public IntegratedServer b() {
      return (IntegratedServer)super.c();
   }

   @Override
   public NBTTagCompound t() {
      return this.f;
   }
}
