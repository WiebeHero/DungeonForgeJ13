package NeededStuff;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class CancelWeaponPlaced implements Listener{
	@EventHandler
	public void placeWeaponEvent(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		ItemStack block = player.getInventory().getItemInMainHand();
		if(block != null) {
			if(block.hasItemMeta()) {
				if(block.getItemMeta().hasDisplayName()) {
					if(block.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Bandits Dagger ")) || block.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Shulkers Bane ")) || block.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Tesla Coil "))) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
}
