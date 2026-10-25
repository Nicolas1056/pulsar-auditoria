package net.minecraft.stats;

import net.minecraft.item.Item;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.util.IChatComponent;

public class StatCrafting extends StatBase {
   private final Item a;

   public StatCrafting(String var1, String var2, IChatComponent var3, Item var4) {
      super(var1 + var2, var3);
      this.a = var4;
      int var5 = Item.b(var4);
      if (var5 != 0) {
         IScoreObjectiveCriteria.a.put(var1 + var5, this.k());
      }
   }

   public Item a() {
      return this.a;
   }
}
