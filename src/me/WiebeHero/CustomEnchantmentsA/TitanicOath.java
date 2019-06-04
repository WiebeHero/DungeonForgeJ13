package me.WiebeHero.CustomEnchantmentsA;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class TitanicOath implements Listener{
	@EventHandler
	public void titanicOath(PlayerMoveEvent event) {
		if(!event.isCancelled()) {
			Player victim = (Player) event.getPlayer();
			if(victim.getInventory().getArmorContents() != null) {
				ItemStack[] items = victim.getInventory().getArmorContents();
				for(ItemStack item : items) {
					if(item != null) {
						if(item.getItemMeta().getLore() != null) {
							String check = "";
							for(String s1 : item.getItemMeta().getLore()){
								if(s1.contains(ChatColor.stripColor("Titanic Oath"))) {
									check = ChatColor.stripColor(s1);
								}
							}
							if(check.contains("Titanic Oath")){
								if(victim.getLocation().getBlock().getType() == Material.WATER) {
									check = check.replaceAll("[^\\d.]", "");
									int level = Integer.parseInt(check);
									ItemMeta meta = item.getItemMeta();
									meta.addEnchant(Enchantment.DEPTH_STRIDER, level, true);
									item.setItemMeta(meta);
									victim.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, level));
								}
								else {
									ItemMeta meta = item.getItemMeta();
									meta.removeEnchant(Enchantment.DEPTH_STRIDER);
									item.setItemMeta(meta);
									victim.removePotionEffect(PotionEffectType.SPEED);
								}
							}
						}
					}
				}
			}
		}
	}
}
