package me.WiebeHero.CustomItemsFOOD;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class ButterscotchPie implements Listener{
	@EventHandler
	public void customFoodButtersotchPie(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		ItemStack eatenItem = event.getItem();
		if(eatenItem != null) {
			if(eatenItem.hasItemMeta()) {
				if(eatenItem.getItemMeta().hasDisplayName()) {
					if(eatenItem.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Butterscotch Pie"))) {
						double attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
						double heal = player.getHealth() + attribute * 0.25;
						if(heal < attribute) {
							player.setHealth(heal);
						}
						else {
							player.setHealth(attribute);
						}
						
					}
				}
			}
		}
	}
}
