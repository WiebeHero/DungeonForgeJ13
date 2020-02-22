package me.WiebeHero.ArmoryPackage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.WiebeHero.CustomEvents.DFItemLevelUpEvent;
import me.WiebeHero.CustomEvents.DFItemXpGainEvent;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;

public class LevelRequired implements Listener{
	private DFPlayerManager dfManager;
	public LevelRequired(DFPlayerManager dfManager) {
		this.dfManager = dfManager;
	}
	@EventHandler
	public void cancelLevelUp(DFItemLevelUpEvent event) {
		Player p = event.getPlayer();
		DFPlayer dfPlayer = dfManager.getEntity(p);
		if(event.getLevelRequired() > dfPlayer.getLevel()) {
			event.setCancelled(true);
		}
		event.activate();
	}
	@EventHandler
	public void cancelLevelUp(DFItemXpGainEvent event) {
		Player p = event.getPlayer();
		DFPlayer dfPlayer = dfManager.getEntity(p);
		if(event.getLevelRequired() > dfPlayer.getLevel()) {
			event.setCancelled(true);
		}
		event.activate();
	}
}	
