package me.WiebeHero.CustomMethods;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class MethodMovementSpeed {
	public double getSpeed(Entity e) {
		if(e instanceof LivingEntity) {
			LivingEntity ent = (LivingEntity) e;
			double speed = ent.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue();
			return speed;
		}
		return 0.00;
	}
	public void setSpeed(Entity e, double speed) {
		if(e instanceof LivingEntity) {
			LivingEntity ent = (LivingEntity) e;
			ent.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
		}
	}
}
