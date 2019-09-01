package me.WiebeHero.MoreStuff;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CancelJoinLeaveAdvancementMessages implements Listener{
	@EventHandler
	public void onJoinMessage(PlayerJoinEvent event) {
		event.setJoinMessage("");
	}
	@EventHandler
	public void onLeaveMessage(PlayerQuitEvent event) {
		event.setQuitMessage("");
	}
}
