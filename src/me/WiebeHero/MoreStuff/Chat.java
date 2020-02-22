package me.WiebeHero.MoreStuff;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Factions.DFFactionPlayer;
import me.WiebeHero.Factions.DFFactionPlayerManager;
import me.WiebeHero.GeneralCommands.MSG;
import me.WiebeHero.GeneralCommands.MSGManager;

public class Chat implements Listener{
	private MSGManager msgManager;
	private DFPlayerManager dfManager;
	private DFFactionPlayerManager facPlayerManager;
	public Chat(DFPlayerManager dfManager, MSGManager msgManager, DFFactionPlayerManager facPlayerManager) {
		this.dfManager = dfManager;
		this.msgManager = msgManager;
		this.facPlayerManager = facPlayerManager;
	}
	@EventHandler
	public void chatFeature(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		DFPlayer dfPlayer = dfManager.getEntity(player);
		if(player.getWorld().getName().equalsIgnoreCase("DFWarzone-1") || player.getWorld().getName().equalsIgnoreCase("factionworld-1")) {
			int level = dfPlayer.getLevel();
			DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(player);
			DFFaction faction = facPlayer.getFaction();
			if(faction != null) {
				event.setFormat("�6" + faction.getName() + " �a| �b�l"  + level + "�a | �7" + player.getName() + ": " + event.getMessage());
			}
			else {
				event.setFormat("�b�l" + level + " �a| �7" + player.getName() + ": " + event.getMessage());
			}
		}
		else {
			event.setFormat("�7" + player.getName() + ": " + event.getMessage());
		}
		for(Player p : event.getRecipients()) {
			if(p != null) {
				MSG msg = msgManager.get(p.getUniqueId());
				if(msg != null) {
					if(msg.isInIgnore(player.getUniqueId())) {
						event.getRecipients().remove(p);
					}
				}
			}
		}
	}
}
