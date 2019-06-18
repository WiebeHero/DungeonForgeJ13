package me.WiebeHero.CustomMethods;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerJoinEvent;

public class MethodHealth {
	public void setHealth(Entity e, double heal) {
		if(e instanceof LivingEntity) {
			LivingEntity ent = (LivingEntity) e;
			double maxHealth = this.getMaxHealth(ent);
			if(heal + maxHealth <= maxHealth) {
				ent.setHealth(this.getHealth(ent) + heal);
			}
			else {
				ent.setHealth(maxHealth);
			}
		}
	}
	public double getHealth(Entity e) {
		if(e instanceof LivingEntity) {
			LivingEntity ent = (LivingEntity) e;
			return ent.getHealth();
		}
		return 0.00;
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
