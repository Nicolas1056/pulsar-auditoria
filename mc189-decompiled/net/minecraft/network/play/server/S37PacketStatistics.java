package net.minecraft.network.play.server;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

public class S37PacketStatistics implements Packet<INetHandlerPlayClient> {
   private Map<StatBase, Integer> a;

   public S37PacketStatistics() {
   }

   public S37PacketStatistics(Map<StatBase, Integer> var1) {
      this.a = var1;
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      int var2 = var1.e();
      this.a = Maps.newHashMap();

      for (int var3 = 0; var3 < var2; var3++) {
         StatBase var4 = StatList.a(var1.c(32767));
         int var5 = var1.e();
         if (var4 != null) {
            this.a.put(var4, var5);
         }
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a.size());

      for (Entry var3 : this.a.entrySet()) {
         var1.a(((StatBase)var3.getKey()).e);
         var1.b((Integer)var3.getValue());
      }
   }

   public Map<StatBase, Integer> a() {
      return this.a;
   }
}
