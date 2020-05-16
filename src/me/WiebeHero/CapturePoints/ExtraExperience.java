package me.WiebeHero.CapturePoints;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.WiebeHero.CustomEvents.DFPlayerXpGainEvent;
import me.WiebeHero.Factions.DFFactionPlayer;
import me.WiebeHero.Factions.DFFactionPlayerManager;

public class ExtraExperience implements Listener{
	
	private DFFactionPlayerManager facPlayerManager;
	private CapturePointManager cpManager;
	
	public ExtraExperience(DFFactionPlayerManager facPlayerManager, CapturePointManager cpManager) {
		this.facPlayerManager = facPlayerManager;
		this.cpManager = cpManager;
	}
	
	@EventHandler
	public void gainXP(DFPlayerXpGainEvent event) {
		Player player = event.getPlayer();
		DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(player);
		if(facPlayer.getFactionId() != null) {
			double xpMultiplier = event.getXPMultiplier();
			for(CapturePoint point : cpManager.getCapturePointList().values()) {
				if(point.getCapturedId().equals(facPlayer.getFactionId())) {
					xpMultiplier += point.getXPMultiplier();
				}
			}
			event.setXPMultiplier(xpMultiplier);
		}
	}
}
