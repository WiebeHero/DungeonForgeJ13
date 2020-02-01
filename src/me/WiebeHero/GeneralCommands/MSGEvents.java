package me.WiebeHero.GeneralCommands;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class MSGEvents implements Listener{
	private MSGManager msgManager = CustomEnchantments.getInstance().msgManager;
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(!msgManager.contains(player.getUniqueId())) {
			msgManager.add(player.getUniqueId(), new MSG());
		}
	}
}
