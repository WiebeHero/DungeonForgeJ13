package me.WiebeHero.DungeonInstances;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class DungeonEvents implements Listener{
	
	private DungeonManager dManager;
	
	public DungeonEvents(DungeonManager dManager) {
		this.dManager = dManager;
	}
	
	public void startDungeon(PlayerMoveEvent event) {
		Player player = event.getPlayer();
	}
	
}
