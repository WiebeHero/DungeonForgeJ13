package NeededStuff;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class DisableCraftItems implements Listener{
	@EventHandler
	public void disableCraft(CraftItemEvent event) {
		ItemStack item = event.getCurrentItem();
		if(item != null) {
			if(item.getType() == Material.BEACON || item.getType() == Material.HOPPER) {
				event.setCancelled(true);
			}
		}
	}
}
