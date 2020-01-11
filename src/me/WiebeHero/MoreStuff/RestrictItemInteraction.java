package me.WiebeHero.MoreStuff;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import me.WiebeHero.CustomEnchantments.CCT;

public class RestrictItemInteraction implements Listener{
	@EventHandler
	public void disableBookAndQuil(CraftItemEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			if(event.getCurrentItem().getType() == Material.WRITABLE_BOOK) {
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't craft books, they are disabled for now."));
				event.setCancelled(true);
			}
		}
	}
}
