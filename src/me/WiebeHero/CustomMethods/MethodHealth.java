package me.WiebeHero.CustomMethods;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Skills.ClassC;
import Skills.HealthH;
import Skills.SkillJoin;
import Skills.Enums.Classes;

public class MethodHealth {
	SkillJoin join = new SkillJoin();
	HealthH he = new HealthH();
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
	public void updateHealth(Player p) {
		if(p != null) {
			join.getHHCalList().put(p.getUniqueId(), 100.00);
			double amount = join.getHHCalList().get(p.getUniqueId());
			if(c.getClass(p) == Classes.LUST || c.getClass(p) == Classes.GLUTTONY) {
				amount = amount + 7.5 * join.getHHList().get(p.getUniqueId());
			}
			else if(c.getClass(p) == Classes.WRATH || c.getClass(p) == Classes.GREED || c.getClass(p) == Classes.PRIDE) {
				amount = amount + 2.5 * join.getHHList().get(p.getUniqueId());
			}
			else {
				amount = amount + 5.0 * join.getHHList().get(p.getUniqueId());
			}
			join.getHHCalList().put(p.getUniqueId(), amount);
		}
	}
}
