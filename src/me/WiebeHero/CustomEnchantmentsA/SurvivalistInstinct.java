package me.WiebeHero.CustomEnchantmentsA;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class SurvivalistInstinct  implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMRage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			if(!event.isCancelled()) {
				Player victim = (Player) event.getEntity();
				DamageCause damageCause = event.getCause();
				if(damageCause == DamageCause.SUFFOCATION || damageCause == DamageCause.DROWNING || damageCause == DamageCause.STARVATION) {
					if(victim.getInventory().getArmorContents() != null) {
						ArrayList<ItemStack> items = new ArrayList<ItemStack>(Arrays.asList(victim.getInventory().getArmorContents()));
						items.add(victim.getInventory().getItemInOffHand());
						for(ItemStack item : items) {
							if(item != null) {
								if(item.getItemMeta().getLore() != null) {
									String check = "";
									for(String s1 : item.getItemMeta().getLore()){
										if(s1.contains(ChatColor.stripColor("Survivalist Instinct"))) {
											check = ChatColor.stripColor(s1);
										}
									}
									if(check.contains("Survivalist Instinct")){
										check = check.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(check);
										event.setDamage(event.getFinalDamage() / 100 * (100 - level * 3.5));
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
