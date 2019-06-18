package me.WiebeHero.CustomMethods;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Skills.HealthH;
import Skills.SkillJoin;

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
	public void updateHealth(Player p) {
		join.getHHCalList().put(p.getUniqueId(), 20.00);
		if(p != null) {
			for(ItemStack item : p.getInventory().getArmorContents()) {
				if(mi.loreContains(item, "Reinforced")) {
					int level = mi.getLevelEnchant(item, "Reinforced");
					double calc = join.getHHCalList().get(p.getUniqueId()) + 0.625 * level;
					join.getHHCalList().put(p.getUniqueId(), calc);
				}
			}
		}
	}
}
