package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S14PacketEntity implements Packet<INetHandlerPlayClient> {
   protected int a;
   protected byte b;
   protected byte c;
   protected byte d;
   protected byte e;
   protected byte f;
   protected boolean g;
   protected boolean h;

   public S14PacketEntity() {
   }

   public S14PacketEntity(int var1) {
      this.a = var1;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   @Override
   public String toString() {
      return "Entity_" + super.toString();
   }

   public Entity a(World var1) {
      return var1.a(this.a);
   }

   public byte a() {
      return this.b;
   }

   public byte b() {
      return this.c;
   }

   public byte c() {
      return this.d;
   }

   public byte d() {
      return this.e;
   }

   public byte e() {
      return this.f;
   }

   public boolean f() {
      return this.h;
   }

   public boolean g() {
      return this.g;
   }

   public static class S15PacketEntityRelMove extends S14PacketEntity {
      public S15PacketEntityRelMove() {
      }

      public S15PacketEntityRelMove(int var1, byte var2, byte var3, byte var4, boolean var5) {
         super(var1);
         this.b = var2;
         this.c = var3;
         this.d = var4;
         this.g = var5;
      }

      @Override
      public void a(PacketBuffer var1) throws IOException {
         super.a(var1);
         this.b = var1.readByte();
         this.c = var1.readByte();
         this.d = var1.readByte();
         this.g = var1.readBoolean();
      }

      @Override
      public void b(PacketBuffer var1) throws IOException {
         super.b(var1);
         var1.writeByte(this.b);
         var1.writeByte(this.c);
         var1.writeByte(this.d);
         var1.writeBoolean(this.g);
      }
   }

   public static class S16PacketEntityLook extends S14PacketEntity {
      public S16PacketEntityLook() {
         this.h = true;
      }

      public S16PacketEntityLook(int var1, byte var2, byte var3, boolean var4) {
         super(var1);
         this.e = var2;
         this.f = var3;
         this.h = true;
         this.g = var4;
      }

      @Override
      public void a(PacketBuffer var1) throws IOException {
         super.a(var1);
         this.e = var1.readByte();
         this.f = var1.readByte();
         this.g = var1.readBoolean();
      }

      @Override
      public void b(PacketBuffer var1) throws IOException {
         super.b(var1);
         var1.writeByte(this.e);
         var1.writeByte(this.f);
         var1.writeBoolean(this.g);
      }
   }

   public static class S17PacketEntityLookMove extends S14PacketEntity {
      public S17PacketEntityLookMove() {
         this.h = true;
      }

      public S17PacketEntityLookMove(int var1, byte var2, byte var3, byte var4, byte var5, byte var6, boolean var7) {
         super(var1);
         this.b = var2;
         this.c = var3;
         this.d = var4;
         this.e = var5;
         this.f = var6;
         this.g = var7;
         this.h = true;
      }

      @Override
      public void a(PacketBuffer var1) throws IOException {
         super.a(var1);
         this.b = var1.readByte();
         this.c = var1.readByte();
         this.d = var1.readByte();
         this.e = var1.readByte();
         this.f = var1.readByte();
         this.g = var1.readBoolean();
      }

      @Override
      public void b(PacketBuffer var1) throws IOException {
         super.b(var1);
         var1.writeByte(this.b);
         var1.writeByte(this.c);
         var1.writeByte(this.d);
         var1.writeByte(this.e);
         var1.writeByte(this.f);
         var1.writeBoolean(this.g);
      }
   }
}
