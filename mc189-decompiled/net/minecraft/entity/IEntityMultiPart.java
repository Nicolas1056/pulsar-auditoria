package net.minecraft.entity;

import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public interface IEntityMultiPart {
   World a();

   boolean a(EntityDragonPart var1, DamageSource var2, float var3);
}
