package NeededStuff;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;

public class ServerTeleporterInteract implements Listener{
	@EventHandler
	public void teleporterInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(player.getInventory().getItemInMainHand() != null) {
			if(player.getInventory().getItemInMainHand().hasItemMeta()) {
				if(player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
					
					if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.stripColor("Server Selector"))) {
						player.sendMessage("GG");
						if(event.getAction() == Action.RIGHT_CLICK_AIR) {
							player.sendMessage(new ColorCodeTranslator().colorize("&aOpening the server teleporter..."));
							ServerTeleporterInv t = new ServerTeleporterInv();
							t.ServerSelector(player);
						}
					}
				}
			}
		}
	}
}
