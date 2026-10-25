package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerList {
   private static final Logger a = LogManager.getLogger();
   private final Minecraft b;
   private final List<ServerData> c = Lists.newArrayList();

   public ServerList(Minecraft var1) {
      this.b = var1;
      this.a();
   }

   public void a() {
      try {
         this.c.clear();
         NBTTagCompound var1 = CompressedStreamTools.a(new File(this.b.v, "servers.dat"));
         if (var1 == null) {
            return;
         }

         NBTTagList var2 = var1.c("servers", 10);

         for (int var3 = 0; var3 < var2.c(); var3++) {
            this.c.add(ServerData.a(var2.b(var3)));
         }
      } catch (Exception var4) {
         a.error("Couldn't load server list", var4);
      }
   }

   public void b() {
      try {
         NBTTagList var1 = new NBTTagList();

         for (ServerData var3 : this.c) {
            var1.a(var3.a());
         }

         NBTTagCompound var5 = new NBTTagCompound();
         var5.a("servers", var1);
         CompressedStreamTools.a(var5, new File(this.b.v, "servers.dat"));
      } catch (Exception var4) {
         a.error("Couldn't save server list", var4);
      }
   }

   public ServerData a(int var1) {
      return this.c.get(var1);
   }

   public void b(int var1) {
      this.c.remove(var1);
   }

   public void a(ServerData var1) {
      this.c.add(var1);
   }

   public int c() {
      return this.c.size();
   }

   public void a(int var1, int var2) {
      ServerData var3 = this.a(var1);
      this.c.set(var1, this.a(var2));
      this.c.set(var2, var3);
      this.b();
   }

   public void a(int var1, ServerData var2) {
      this.c.set(var1, var2);
   }

   public static void b(ServerData var0) {
      ServerList var1 = new ServerList(Minecraft.A());
      var1.a();

      for (int var2 = 0; var2 < var1.c(); var2++) {
         ServerData var3 = var1.a(var2);
         if (var3.a.equals(var0.a) && var3.b.equals(var0.b)) {
            var1.a(var2, var0);
            break;
         }
      }

      var1.b();
   }
}
