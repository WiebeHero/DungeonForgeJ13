package me.WiebeHero.CustomEnchantments;

import java.util.Random;

public class RPGPlayer {
	double xp = 0;
	double maxxp = 50;
	double RPGLevel = 0;
	double RPGAttack = 0;
	double RPGAttackSpeed = 0;
	double RPGCriticalChance = 0;
	double RPGBowDamage = 0;
	double RPGHealth = 0;
	double RPGDefense = 0;
	
	public void addXp(int waarde) {

		 RPGPlayer rpger = new RPGPlayer();
		 int randomXP = new Random().nextInt (100) + 1;
		 rpger.addXp(randomXP);
		xp += waarde;
	}
}

