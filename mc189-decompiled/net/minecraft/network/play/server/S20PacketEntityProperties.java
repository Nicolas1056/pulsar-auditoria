package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S20PacketEntityProperties implements Packet<INetHandlerPlayClient> {
   private int a;
   private final List<S20PacketEntityProperties.Snapshot> b = Lists.newArrayList();

   public S20PacketEntityProperties() {
   }

   public S20PacketEntityProperties(int var1, Collection<IAttributeInstance> var2) {
      this.a = var1;

      for (IAttributeInstance var4 : var2) {
         this.b.add(new S20PacketEntityProperties.Snapshot(var4.a().a(), var4.b(), var4.c()));
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      int var2 = var1.readInt();

      for (int var3 = 0; var3 < var2; var3++) {
         String var4 = var1.c(64);
         double var5 = var1.readDouble();
         ArrayList var7 = Lists.newArrayList();
         int var8 = var1.e();

         for (int var9 = 0; var9 < var8; var9++) {
            UUID var10 = var1.g();
            var7.add(new AttributeModifier(var10, "Unknown synced attribute modifier", var1.readDouble(), var1.readByte()));
         }

         this.b.add(new S20PacketEntityProperties.Snapshot(var4, var5, var7));
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.writeInt(this.b.size());

      for (S20PacketEntityProperties.Snapshot var3 : this.b) {
         var1.a(var3.a());
         var1.writeDouble(var3.b());
         var1.b(var3.c().size());

         for (AttributeModifier var5 : var3.c()) {
            var1.a(var5.a());
            var1.writeDouble(var5.d());
            var1.writeByte(var5.c());
         }
      }
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public int a() {
      return this.a;
   }

   public List<S20PacketEntityProperties.Snapshot> b() {
      return this.b;
   }

   public class Snapshot {
      private final String b;
      private final double c;
      private final Collection<AttributeModifier> d;

      public Snapshot(String var2, double var3, Collection<AttributeModifier> var5) {
         this.b = var2;
         this.c = var3;
         this.d = var5;
      }

      public String a() {
         return this.b;
      }

      public double b() {
         return this.c;
      }

      public Collection<AttributeModifier> c() {
         return this.d;
      }
   }
}
