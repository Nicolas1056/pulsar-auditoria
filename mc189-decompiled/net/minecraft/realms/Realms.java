package net.minecraft.realms;

import com.google.common.util.concurrent.ListenableFuture;
import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.net.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.Session;
import net.minecraft.world.WorldSettings;

public class Realms {
   public static boolean isTouchScreen() {
      return Minecraft.A().t.A;
   }

   public static Proxy getProxy() {
      return Minecraft.A().O();
   }

   public static String sessionId() {
      Session var0 = Minecraft.A().L();
      return var0 == null ? null : var0.a();
   }

   public static String userName() {
      Session var0 = Minecraft.A().L();
      return var0 == null ? null : var0.c();
   }

   public static long currentTimeMillis() {
      return Minecraft.J();
   }

   public static String getSessionId() {
      return Minecraft.A().L().a();
   }

   public static String getUUID() {
      return Minecraft.A().L().b();
   }

   public static String getName() {
      return Minecraft.A().L().c();
   }

   public static String uuidToName(String var0) {
      return Minecraft.A().aa().fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(var0), null), false).getName();
   }

   public static void setScreen(RealmsScreen var0) {
      Minecraft.A().a(var0.getProxy());
   }

   public static String getGameDirectoryPath() {
      return Minecraft.A().v.getAbsolutePath();
   }

   public static int survivalId() {
      return WorldSettings.GameType.b.a();
   }

   public static int creativeId() {
      return WorldSettings.GameType.c.a();
   }

   public static int adventureId() {
      return WorldSettings.GameType.d.a();
   }

   public static int spectatorId() {
      return WorldSettings.GameType.e.a();
   }

   public static void setConnectedToRealms(boolean var0) {
      Minecraft.A().a(var0);
   }

   public static ListenableFuture<Object> downloadResourcePack(String var0, String var1) {
      return Minecraft.A().R().a(var0, var1);
   }

   public static void clearResourcePack() {
      Minecraft.A().R().f();
   }

   public static boolean getRealmsNotificationsEnabled() {
      return Minecraft.A().t.b(GameSettings.Options.S);
   }

   public static boolean inTitleScreen() {
      return Minecraft.A().m != null && Minecraft.A().m instanceof GuiMainMenu;
   }
}
