package me.WiebeHero.MoreStuff;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;

public class MOTDSetting implements Listener{
	@EventHandler
	public void onPingChange(ServerListPingEvent event) {
		event.setMotd(new ColorCodeTranslator().colorize("&6&l          >-----Dungeon&e&lForge-----<                        &c&lMAINTENANCE &8&lALPHA &60.6 &cJ"));
	}
}
