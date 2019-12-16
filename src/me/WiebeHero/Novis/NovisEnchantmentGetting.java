package me.WiebeHero.Novis;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import me.WiebeHero.CustomEnchantments.CCT;

public class NovisEnchantmentGetting {
	public ArrayList<String> setEnchantments(int level, String enchants, String rarity, ArrayList<String> lore) {
		ArrayList<String> newLore = lore;
		String test[] = enchants.split("//");
		int maxLevel = 15;
		int maxEnchantment = 0;
		for(int i = 0; i < test.length; i++) {
			maxEnchantment = maxEnchantment + Integer.parseInt(test[i].replaceAll("[^\\d.]", ""));
		}
		maxEnchantment = maxEnchantment - 1;
		for(int i = 0; i < test.length; i++) {
			test[i] = test[i].replace("1", "");
			test[i] = test[i].replace("2", "");
			test[i] = test[i].replace("3", "");
			test[i] = test[i].replace("4", "");
		}
		double current = (double)((double)maxEnchantment / (double)maxLevel * (double)level);
		Bukkit.broadcastMessage(maxEnchantment + "");
		Bukkit.broadcastMessage(maxLevel + "");
		Bukkit.broadcastMessage(level + "");
		current = current + 1;
		Bukkit.broadcastMessage(current + "");
		double prat = Math.floor((double)maxEnchantment / (double)maxLevel * (double)level);
		prat = prat + 1;
		for(int i = 0; i < current / 4.0; i++) {
			Bukkit.broadcastMessage(current / 4.0 + "");
			if(prat >= 4) {
				newLore.add(new CCT().colorize("&9" + test[i]) + "4");
				prat = prat - 4;
			}
			else {
				if(prat != 0) {
					newLore.add(new CCT().colorize("&9" + test[i]) + (int)prat);
					break;
				}
			}
		}
		return newLore;
	}
}
