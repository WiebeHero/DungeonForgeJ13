package me.WiebeHero.CustomMethods;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class MethodItemStack {
	public boolean loreContains(ItemStack item, String check) {
		if(item != null & item.getType() != Material.AIR) {
			if(item.hasItemMeta()) {
				if(item.getItemMeta().hasLore()) {
					if(item.getItemMeta().getLore().toString().contains(check)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public int levelEnchant(ItemStack item, String check) {
		if(item != null & item.getType() != Material.AIR) {
			if(item.hasItemMeta()) {
				if(item.getItemMeta().hasLore()) {
					if(item.getItemMeta().getLore().toString().contains(check)) {
						String checkIt = "";
						for(String s1 : item.getItemMeta().getLore()){
							if(s1.contains(ChatColor.stripColor(checkIt))) {
								checkIt = ChatColor.stripColor(s1);
							}
						}
						checkIt = checkIt.replaceAll("[^\\d.]", "");
						int level = Integer.parseInt(checkIt);
						return level;
					}
				}
			}
		}
		return 0;
	}
}
