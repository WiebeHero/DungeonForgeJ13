package me.WiebeHero.MoreStuff;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RestrictInteractionWithBlocks implements Listener{
	@EventHandler
	public void disableInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getClickedBlock().getType() == Material.ENCHANTING_TABLE || event.getClickedBlock().getType() == Material.ANVIL) {
				event.setCancelled(true);
			}
		}
	}
}
