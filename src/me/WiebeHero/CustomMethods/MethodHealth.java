package me.WiebeHero.CustomMethods;

import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import Skills.ClassC;
import Skills.Enums.Classes;
import Skills.SkillJoin;

public class MethodHealth {
	SkillJoin join = new SkillJoin();
	MethodItemStack mi = new MethodItemStack();
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
	ClassC c = new ClassC();
	public void updateHealth(UUID id) {
		join.getHHCalList().put(id, 100.00);
		double amount = join.getHHCalList().get(id);
		if(c.getClass(id) == Classes.LUST || c.getClass(id) == Classes.GLUTTONY) {
			amount = amount + 7.5 * join.getHHList().get(id);
		}
		else if(c.getClass(id) == Classes.WRATH || c.getClass(id) == Classes.GREED || c.getClass(id) == Classes.PRIDE) {
			amount = amount + 2.5 * join.getHHList().get(id);
		}
		else {
			amount = amount + 5.0 * join.getHHList().get(id);
		}
		join.getHHCalList().put(id, amount);
	}
}
