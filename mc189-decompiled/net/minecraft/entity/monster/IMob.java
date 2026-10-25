package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IAnimals;

public interface IMob extends IAnimals {
   Predicate<Entity> d = new Predicate<Entity>() {
      public boolean a(Entity var1) {
         return var1 instanceof IMob;
      }
   };
   Predicate<Entity> e = new Predicate<Entity>() {
      public boolean a(Entity var1) {
         return var1 instanceof IMob && !var1.ax();
      }
   };
}
