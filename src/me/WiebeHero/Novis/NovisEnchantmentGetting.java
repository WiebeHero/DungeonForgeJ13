package me.WiebeHero.Novis;

import java.util.ArrayList;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class NovisEnchantmentGetting {
	public ArrayList<String> setEnchantments(int level, String enchants, String rarity, ArrayList<String> lore) {
		ArrayList<String> newLore = lore;
		String test[] = enchants.split("//");
		if(rarity.equals("Common")) {
			if(level == 1) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 1");
			}
			if(level == 2) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 1");
			}
			if(level == 3) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 1");
			}
			if(level == 4) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 2");
			}
			if(level == 5) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 2");
			}
			if(level == 6) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 2");
			}
			if(level == 7) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 3");
			}
			if(level == 8) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 3");
			}
			if(level == 9) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 3");
			}
			if(level == 10) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
			}
			if(level == 11) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
			}
			if(level == 12) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
			}
			if(level == 13) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 1");
			}
			if(level == 14) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 1");
			}
			if(level == 15) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 1");
			}
		}
		if(rarity.equals("Rare")) {
			if(level == 1) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 1");
			}
			if(level == 2) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 1");
			}
			if(level == 3) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 1");
			}
			if(level == 4) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 2");
			}
			if(level == 5) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 2");
			}
			if(level == 6) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 3");
			}
			if(level == 7) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 3");
			}
			if(level == 8) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 3");
			}
			if(level == 9) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
			}
			if(level == 10) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
			}
			if(level == 11) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 1");
			}
			if(level == 12) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 1");
			}
			if(level == 13) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 1");
			}
			if(level == 14) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 2");
			}
			if(level == 15) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 2");
			}
		}
		if(rarity.equals("Epic")) {
			if(level == 1) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 1");
			}
			if(level == 2) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 1");
			}
			if(level == 3) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 1");
			}
			if(level == 4) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 2");
			}
			if(level == 5) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 2");
			}
			if(level == 6) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 3");
			}
			if(level == 7) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 3");
			}
			if(level == 8) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
			}
			if(level == 9) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
			}
			if(level == 10) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 1");
			}
			if(level == 11) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 1");
			}
			if(level == 12) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 2");
			}
			if(level == 13) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 2");
			}
			if(level == 14) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 2");
			}
			if(level == 15) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 3");
			}
		}
		if(rarity.equals("Legendary")) {
			if(level == 1) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 1");
			}
			if(level == 2) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 1");
			}
			if(level == 3) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 2");
			}
			if(level == 4) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 2");
			}
			if(level == 5) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 3");
			}
			if(level == 6) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 3");
			}
			if(level == 7) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
			}
			if(level == 8) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
			}
			if(level == 9) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 1");
			}
			if(level == 10) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 1");
			}
			if(level == 11) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 2");
			}
			if(level == 12) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 2");
			}
			if(level == 13) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 3");
			}
			if(level == 14) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 3");
			}
			if(level == 15) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 4");
			}
		}
		if(rarity.equals("Mythic")) {
			if(level == 1) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 1");
			}
			if(level == 2) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 1");
			}
			if(level == 3) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 2");
			}
			if(level == 4) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 2");
			}
			if(level == 5) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 3");
			}
			if(level == 6) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 3");
			}
			if(level == 7) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
			}
			if(level == 8) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 1");
			}
			if(level == 9) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 1");
			}
			if(level == 10) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 2");
			}
			if(level == 11) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 2");
			}
			if(level == 12) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 3");
			}
			if(level == 13) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 4");
			}
			if(level == 14) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 4");
			}
			if(level == 15) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[2]) + " 1");
			}
		}
		if(rarity.equals("Heroic")) {
			if(level == 1) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 1");
			}
			if(level == 2) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 1");
			}
			if(level == 3) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 2");
			}
			if(level == 4) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 3");
			}
			if(level == 5) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 3");
			}
			if(level == 6) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
			}
			if(level == 7) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 1");
			}
			if(level == 8) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 1");
			}
			if(level == 9) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 2");
			}
			if(level == 10) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 3");
			}
			if(level == 11) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 3");
			}
			if(level == 12) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 4");
			}
			if(level == 13) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[2]) + " 1");
			}
			if(level == 14) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[2]) + " 1");
			}
			if(level == 15) {
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[0]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[1]) + " 4");
				newLore.add(new ColorCodeTranslator().colorize("&9" + test[2]) + " 2");
			}
		}
		return newLore;
	}
}
