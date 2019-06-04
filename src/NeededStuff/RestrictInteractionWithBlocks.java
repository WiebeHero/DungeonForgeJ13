package NeededStuff;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RestrictInteractionWithBlocks implements Listener{
	@EventHandler
	public void disableInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getClickedBlock().getType() == Material.ENCHANTING_TABLE || event.getClickedBlock().getType() == Material.ANVIL) {
				event.setCancelled(true);
			}
		}
	}
}
