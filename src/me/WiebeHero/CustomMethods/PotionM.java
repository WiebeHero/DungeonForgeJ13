package me.WiebeHero.CustomMethods;

import java.util.ArrayList;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionM {
	public void applyEffect(LivingEntity victim, ArrayList<PotionEffectType> types, int amp, int durationAdd) {
		for(PotionEffectType type : types) {
			if(victim.hasPotionEffect(type) && victim.getPotionEffect(type).getAmplifier() == amp) {
				int durationNow = victim.getPotionEffect(type).getDuration();
				victim.removePotionEffect(type);
				victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
			}
			else {
				victim.removePotionEffect(type);
				victim.addPotionEffect(new PotionEffect(type, durationAdd, amp));
			}
		}
	}
}
