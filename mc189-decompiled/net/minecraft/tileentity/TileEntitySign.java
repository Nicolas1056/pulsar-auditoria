package net.minecraft.tileentity;

import com.google.gson.JsonParseException;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S33PacketUpdateSign;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntitySign extends TileEntity {
   public final IChatComponent[] a = new IChatComponent[]{
      new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText("")
   };
   public int f = -1;
   private boolean g = true;
   private EntityPlayer h;
   private final CommandResultStats i = new CommandResultStats();

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);

      for (int var2 = 0; var2 < 4; var2++) {
         String var3 = IChatComponent.Serializer.a(this.a[var2]);
         var1.a("Text" + (var2 + 1), var3);
      }

      this.i.b(var1);
   }

   @Override
   public void a(NBTTagCompound var1) {
      this.g = false;
      super.a(var1);
      ICommandSender var2 = new ICommandSender() {
         @Override
         public String e_() {
            return "Sign";
         }

         @Override
         public IChatComponent f_() {
            return new ChatComponentText(this.e_());
         }

         @Override
         public void a(IChatComponent var1) {
         }

         @Override
         public boolean a(int var1, String var2x) {
            return true;
         }

         @Override
         public BlockPos c() {
            return TileEntitySign.this.c;
         }

         @Override
         public Vec3 d() {
            return new Vec3(TileEntitySign.this.c.n() + 0.5, TileEntitySign.this.c.o() + 0.5, TileEntitySign.this.c.p() + 0.5);
         }

         @Override
         public World e() {
            return TileEntitySign.this.b;
         }

         @Override
         public Entity f() {
            return null;
         }

         @Override
         public boolean u_() {
            return false;
         }

         @Override
         public void a(CommandResultStats.Type var1, int var2x) {
         }
      };

      for (int var3 = 0; var3 < 4; var3++) {
         String var4 = var1.j("Text" + (var3 + 1));

         try {
            IChatComponent var5 = IChatComponent.Serializer.a(var4);

            try {
               this.a[var3] = ChatComponentProcessor.a(var2, var5, null);
            } catch (CommandException var7) {
               this.a[var3] = var5;
            }
         } catch (JsonParseException var8) {
            this.a[var3] = new ChatComponentText(var4);
         }
      }

      this.i.a(var1);
   }

   @Override
   public Packet y_() {
      IChatComponent[] var1 = new IChatComponent[4];
      System.arraycopy(this.a, 0, var1, 0, 4);
      return new S33PacketUpdateSign(this.b, this.c, var1);
   }

   @Override
   public boolean F() {
      return true;
   }

   public boolean b() {
      return this.g;
   }

   public void a(boolean var1) {
      this.g = var1;
      if (!var1) {
         this.h = null;
      }
   }

   public void a(EntityPlayer var1) {
      this.h = var1;
   }

   public EntityPlayer c() {
      return this.h;
   }

   public boolean b(final EntityPlayer var1) {
      ICommandSender var2 = new ICommandSender() {
         @Override
         public String e_() {
            return var1.e_();
         }

         @Override
         public IChatComponent f_() {
            return var1.f_();
         }

         @Override
         public void a(IChatComponent var1x) {
         }

         @Override
         public boolean a(int var1x, String var2x) {
            return var1x <= 2;
         }

         @Override
         public BlockPos c() {
            return TileEntitySign.this.c;
         }

         @Override
         public Vec3 d() {
            return new Vec3(TileEntitySign.this.c.n() + 0.5, TileEntitySign.this.c.o() + 0.5, TileEntitySign.this.c.p() + 0.5);
         }

         @Override
         public World e() {
            return var1.e();
         }

         @Override
         public Entity f() {
            return var1;
         }

         @Override
         public boolean u_() {
            return false;
         }

         @Override
         public void a(CommandResultStats.Type var1x, int var2x) {
            TileEntitySign.this.i.a(this, var1x, var2x);
         }
      };

      for (int var3 = 0; var3 < this.a.length; var3++) {
         ChatStyle var4 = this.a[var3] == null ? null : this.a[var3].b();
         if (var4 != null && var4.h() != null) {
            ClickEvent var5 = var4.h();
            if (var5.a() == ClickEvent.Action.c) {
               MinecraftServer.N().P().a(var2, var5.b());
            }
         }
      }

      return true;
   }

   public CommandResultStats d() {
      return this.i;
   }
}
