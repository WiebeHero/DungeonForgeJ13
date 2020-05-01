package me.WiebeHero.Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Factions.DFFactionManager;

public class ActivateAbility implements Listener{
	
	private DFPlayerManager dfManager;
	private DFFactionManager facManager;
	
	public ActivateAbility(DFPlayerManager dfManager, DFFactionManager facManager) {
		this.dfManager = dfManager;
		this.facManager = facManager;
	}
	@EventHandler
	public void active(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(dfManager.contains(player)) {
			DFPlayer dfPlayer = dfManager.getEntity(player);
			if(dfPlayer.getUseable()) {
				event.setCancelled(true);
				dfPlayer.getPlayerClass().ability(dfPlayer.getPlayer());
			}
			else {
				event.setCancelled(true);
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this Ability yet!"));
			}
		}
	}
}
