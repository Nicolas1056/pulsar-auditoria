package net.minecraft.stats;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S37PacketStatistics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.TupleIntJsonSerializable;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StatisticsFile extends StatFileWriter {
   private static final Logger b = LogManager.getLogger();
   private final MinecraftServer c;
   private final File d;
   private final Set<StatBase> e = Sets.newHashSet();
   private int f = -300;
   private boolean g = false;

   public StatisticsFile(MinecraftServer var1, File var2) {
      this.c = var1;
      this.d = var2;
   }

   public void a() {
      if (this.d.isFile()) {
         try {
            this.a.clear();
            this.a.putAll(this.a(FileUtils.readFileToString(this.d)));
         } catch (IOException var2) {
            b.error("Couldn't read statistics file " + this.d, var2);
         } catch (JsonParseException var3) {
            b.error("Couldn't parse statistics file " + this.d, var3);
         }
      }
   }

   public void b() {
      try {
         FileUtils.writeStringToFile(this.d, a(this.a));
      } catch (IOException var2) {
         b.error("Couldn't save stats", var2);
      }
   }

   @Override
   public void a(EntityPlayer var1, StatBase var2, int var3) {
      int var4 = var2.d() ? this.a(var2) : 0;
      super.a(var1, var2, var3);
      this.e.add(var2);
      if (var2.d() && var4 == 0 && var3 > 0) {
         this.g = true;
         if (this.c.aB()) {
            this.c.ap().a(new ChatComponentTranslation("chat.type.achievement", var1.f_(), var2.j()));
         }
      }

      if (var2.d() && var4 > 0 && var3 == 0) {
         this.g = true;
         if (this.c.aB()) {
            this.c.ap().a(new ChatComponentTranslation("chat.type.achievement.taken", var1.f_(), var2.j()));
         }
      }
   }

   public Set<StatBase> c() {
      HashSet var1 = Sets.newHashSet(this.e);
      this.e.clear();
      this.g = false;
      return var1;
   }

   public Map<StatBase, TupleIntJsonSerializable> a(String var1) {
      JsonElement var2 = new JsonParser().parse(var1);
      if (!var2.isJsonObject()) {
         return Maps.newHashMap();
      } else {
         JsonObject var3 = var2.getAsJsonObject();
         HashMap var4 = Maps.newHashMap();

         for (Entry var6 : var3.entrySet()) {
            StatBase var7 = StatList.a((String)var6.getKey());
            if (var7 != null) {
               TupleIntJsonSerializable var8 = new TupleIntJsonSerializable();
               if (((JsonElement)var6.getValue()).isJsonPrimitive() && ((JsonElement)var6.getValue()).getAsJsonPrimitive().isNumber()) {
                  var8.a(((JsonElement)var6.getValue()).getAsInt());
               } else if (((JsonElement)var6.getValue()).isJsonObject()) {
                  JsonObject var9 = ((JsonElement)var6.getValue()).getAsJsonObject();
                  if (var9.has("value") && var9.get("value").isJsonPrimitive() && var9.get("value").getAsJsonPrimitive().isNumber()) {
                     var8.a(var9.getAsJsonPrimitive("value").getAsInt());
                  }

                  if (var9.has("progress") && var7.l() != null) {
                     try {
                        Constructor var10 = var7.l().getConstructor();
                        IJsonSerializable var11 = (IJsonSerializable)var10.newInstance();
                        var11.a(var9.get("progress"));
                        var8.a(var11);
                     } catch (Throwable var12) {
                        b.warn("Invalid statistic progress in " + this.d, var12);
                     }
                  }
               }

               var4.put(var7, var8);
            } else {
               b.warn("Invalid statistic in " + this.d + ": Don't know what " + (String)var6.getKey() + " is");
            }
         }

         return var4;
      }
   }

   public static String a(Map<StatBase, TupleIntJsonSerializable> var0) {
      JsonObject var1 = new JsonObject();

      for (Entry var3 : var0.entrySet()) {
         if (((TupleIntJsonSerializable)var3.getValue()).b() != null) {
            JsonObject var4 = new JsonObject();
            var4.addProperty("value", ((TupleIntJsonSerializable)var3.getValue()).a());

            try {
               var4.add("progress", ((TupleIntJsonSerializable)var3.getValue()).<IJsonSerializable>b().a());
            } catch (Throwable var6) {
               b.warn("Couldn't save statistic " + ((StatBase)var3.getKey()).e() + ": error serializing progress", var6);
            }

            var1.add(((StatBase)var3.getKey()).e, var4);
         } else {
            var1.addProperty(((StatBase)var3.getKey()).e, ((TupleIntJsonSerializable)var3.getValue()).a());
         }
      }

      return var1.toString();
   }

   public void d() {
      for (StatBase var2 : this.a.keySet()) {
         this.e.add(var2);
      }
   }

   public void a(EntityPlayerMP var1) {
      int var2 = this.c.at();
      HashMap var3 = Maps.newHashMap();
      if (this.g || var2 - this.f > 300) {
         this.f = var2;

         for (StatBase var5 : this.c()) {
            var3.put(var5, this.a(var5));
         }
      }

      var1.a.a(new S37PacketStatistics(var3));
   }

   public void b(EntityPlayerMP var1) {
      HashMap var2 = Maps.newHashMap();

      for (Achievement var4 : AchievementList.e) {
         if (this.a(var4)) {
            var2.put(var4, this.a(var4));
            this.e.remove(var4);
         }
      }

      var1.a.a(new S37PacketStatistics(var2));
   }

   public boolean e() {
      return this.g;
   }
}
