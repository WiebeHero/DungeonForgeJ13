package me.WiebeHero.CustomMethods;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class MethodHealth {
	public void healEntity(Entity e, double heal) {
		if(e instanceof LivingEntity) {
			LivingEntity ent = (LivingEntity) e;
			double maxHealth = this.getMaxHealth(ent);
			if(heal + maxHealth <= maxHealth) {
				ent.setHealth(maxHealth + heal);
			}
			else {
				ent.setHealth(maxHealth);
			}
		}
	}
	public double getMaxHealth(Entity e) {
		if(e instanceof LivingEntity) {
			LivingEntity ent = (LivingEntity) e;
			double maxHealth = ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
			return maxHealth;
		}
		return 0.00;
	}
	public void setMaxHealth(Entity e, double max) {
		if(e instanceof LivingEntity) {
			LivingEntity ent = (LivingEntity) e;
			ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(max);
		}
	}
	
}
