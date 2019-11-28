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
			else if(victim.hasPotionEffect(type) && victim.getPotionEffect(type).getAmplifier() > amp) {
				int durationNow = victim.getPotionEffect(type).getDuration();
				int ampDiff = victim.getPotionEffect(type).getAmplifier() - amp;
				for(int i = 1; i <= ampDiff; i++) {
					durationNow = durationNow / 50;
				}
				victim.removePotionEffect(type);
				victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, victim.getPotionEffect(type).getAmplifier()));
			}
			else if(victim.hasPotionEffect(type) && victim.getPotionEffect(type).getAmplifier() < amp) {
				int durationNow = victim.getPotionEffect(type).getDuration();
				int ampDiff = amp - victim.getPotionEffect(type).getAmplifier();
				for(int i = 1; i <= ampDiff; i++) {
					durationNow = durationNow / 25;
				}
				victim.removePotionEffect(type);
				victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, victim.getPotionEffect(type).getAmplifier()));
			}
			else {
				victim.removePotionEffect(type);
				victim.addPotionEffect(new PotionEffect(type, durationAdd, amp));
			}
		}
	}
	public void applyEffect(LivingEntity victim, PotionEffectType type, int amp, int durationAdd) {
		if(victim.hasPotionEffect(type) && victim.getPotionEffect(type).getAmplifier() == amp) {
			int durationNow = victim.getPotionEffect(type).getDuration();
			victim.removePotionEffect(type);
			victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
		}
		else if(victim.hasPotionEffect(type) && victim.getPotionEffect(type).getAmplifier() > amp) {
			int durationNow = victim.getPotionEffect(type).getDuration();
			int ampDiff = victim.getPotionEffect(type).getAmplifier() - amp;
			for(int i = 1; i <= ampDiff; i++) {
				durationNow = durationNow / 25;
			}
			victim.removePotionEffect(type);
			victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, victim.getPotionEffect(type).getAmplifier()));
		}
		else if(victim.hasPotionEffect(type) && victim.getPotionEffect(type).getAmplifier() < amp) {
			int durationNow = victim.getPotionEffect(type).getDuration();
			int ampDiff = amp - victim.getPotionEffect(type).getAmplifier();
			for(int i = 1; i <= ampDiff; i++) {
				durationNow = durationNow / 25;
			}
			victim.removePotionEffect(type);
			victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, victim.getPotionEffect(type).getAmplifier()));
		}
		else {
			victim.addPotionEffect(new PotionEffect(type, durationAdd, amp));
		}
	}
}
