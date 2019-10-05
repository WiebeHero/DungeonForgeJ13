package me.WiebeHero.MoreStuff;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class MOTDSetting implements Listener{
	@EventHandler
	public void onPingChange(ServerListPingEvent event) {
		event.setMotd(new ColorCodeTranslator().colorize("&6&l          >-----&2&lDungeonForge&6&l-----<                         &c&lMAINTENANCE &2&lBETA &6" + CustomEnchantments.getInstance().getDescription().getVersion() + " &cJ"));
	}
}
