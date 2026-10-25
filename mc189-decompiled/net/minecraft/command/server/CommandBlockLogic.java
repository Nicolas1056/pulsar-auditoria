package net.minecraft.command.server;

import io.netty.buffer.ByteBuf;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

public abstract class CommandBlockLogic implements ICommandSender {
   private static final SimpleDateFormat a = new SimpleDateFormat("HH:mm:ss");
   private int b;
   private boolean c = true;
   private IChatComponent d = null;
   private String e = "";
   private String f = "@";
   private final CommandResultStats g = new CommandResultStats();

   public int j() {
      return this.b;
   }

   public IChatComponent k() {
      return this.d;
   }

   public void a(NBTTagCompound var1) {
      var1.a("Command", this.e);
      var1.a("SuccessCount", this.b);
      var1.a("CustomName", this.f);
      var1.a("TrackOutput", this.c);
      if (this.d != null && this.c) {
         var1.a("LastOutput", IChatComponent.Serializer.a(this.d));
      }

      this.g.b(var1);
   }

   public void b(NBTTagCompound var1) {
      this.e = var1.j("Command");
      this.b = var1.f("SuccessCount");
      if (var1.b("CustomName", 8)) {
         this.f = var1.j("CustomName");
      }

      if (var1.b("TrackOutput", 1)) {
         this.c = var1.n("TrackOutput");
      }

      if (var1.b("LastOutput", 8) && this.c) {
         this.d = IChatComponent.Serializer.a(var1.j("LastOutput"));
      }

      this.g.a(var1);
   }

   @Override
   public boolean a(int var1, String var2) {
      return var1 <= 2;
   }

   public void a(String var1) {
      this.e = var1;
      this.b = 0;
   }

   public String l() {
      return this.e;
   }

   public void a(World var1) {
      if (var1.D) {
         this.b = 0;
      }

      MinecraftServer var2 = MinecraftServer.N();
      if (var2 != null && var2.O() && var2.al()) {
         ICommandManager var3 = var2.P();

         try {
            this.d = null;
            this.b = var3.a(this, this.e);
         } catch (Throwable var7) {
            CrashReport var5 = CrashReport.a(var7, "Executing command block");
            CrashReportCategory var6 = var5.a("Command to be executed");
            var6.a("Command", new Callable<String>() {
               public String a() throws Exception {
                  return CommandBlockLogic.this.l();
               }
            });
            var6.a("Name", new Callable<String>() {
               public String a() throws Exception {
                  return CommandBlockLogic.this.e_();
               }
            });
            throw new ReportedException(var5);
         }
      } else {
         this.b = 0;
      }
   }

   @Override
   public String e_() {
      return this.f;
   }

   @Override
   public IChatComponent f_() {
      return new ChatComponentText(this.e_());
   }

   public void b(String var1) {
      this.f = var1;
   }

   @Override
   public void a(IChatComponent var1) {
      if (this.c && this.e() != null && !this.e().D) {
         this.d = new ChatComponentText("[" + a.format(new Date()) + "] ").a(var1);
         this.h();
      }
   }

   @Override
   public boolean u_() {
      MinecraftServer var1 = MinecraftServer.N();
      return var1 == null || !var1.O() || var1.d[0].Q().b("commandBlockOutput");
   }

   @Override
   public void a(CommandResultStats.Type var1, int var2) {
      this.g.a(this, var1, var2);
   }

   public abstract void h();

   public abstract int i();

   public abstract void a(ByteBuf var1);

   public void b(IChatComponent var1) {
      this.d = var1;
   }

   public void a(boolean var1) {
      this.c = var1;
   }

   public boolean m() {
      return this.c;
   }

   public boolean a(EntityPlayer var1) {
      if (!var1.bA.d) {
         return false;
      } else {
         if (var1.e().D) {
            var1.a(this);
         }

         return true;
      }
   }

   public CommandResultStats n() {
      return this.g;
   }
}
