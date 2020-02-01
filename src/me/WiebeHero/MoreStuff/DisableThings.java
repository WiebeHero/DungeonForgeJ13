package me.WiebeHero.MoreStuff;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class DisableThings implements Listener{
	@EventHandler
	public void onBoatPlace(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (player.getInventory().getItemInMainHand().getType().toString().contains("BOAT")) {
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
    public void onEntityDamage(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (event.getCause() == TeleportCause.ENDER_PEARL) {
            event.setCancelled(true);
            player.teleport(event.getTo());
        }
    }
}
