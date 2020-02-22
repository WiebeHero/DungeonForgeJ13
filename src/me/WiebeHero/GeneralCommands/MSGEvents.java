package me.WiebeHero.GeneralCommands;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MSGEvents implements Listener{
	private MSGManager msgManager;
	public MSGEvents(MSGManager msgManager) {
		this.msgManager = msgManager;
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(!msgManager.contains(player.getUniqueId())) {
			msgManager.add(player.getUniqueId(), new MSG());
		}
	}
}
