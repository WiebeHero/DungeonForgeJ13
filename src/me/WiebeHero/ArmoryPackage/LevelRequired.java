package me.WiebeHero.ArmoryPackage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.WiebeHero.CustomEvents.DFItemLevelUpEvent;
import me.WiebeHero.CustomEvents.DFItemXpGainEvent;
import me.WiebeHero.Skills.DFPlayer;

public class LevelRequired implements Listener{
	@EventHandler
	public void cancelLevelUp(DFItemLevelUpEvent event) {
		Player p = event.getPlayer();
		DFPlayer dfPlayer = new DFPlayer().getPlayer(p);
		if(dfPlayer.getLevel() < event.getLevelRequired()) {
			event.setCancelled(true);
		}
		event.activate();
	}
	@EventHandler
	public void cancelLevelUp(DFItemXpGainEvent event) {
		Player p = event.getPlayer();
		DFPlayer dfPlayer = new DFPlayer().getPlayer(p);
		if(dfPlayer.getLevel() < event.getLevelRequired()) {
			event.setCancelled(true);
		}
		event.activate();
	}
}	
