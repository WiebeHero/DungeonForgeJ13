package me.WiebeHero.MoreStuff;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.GeneralCommands.MSG;
import me.WiebeHero.GeneralCommands.MSGManager;
import me.WiebeHero.Skills.DFPlayer;

public class Chat implements Listener{
	private MSGManager msgManager = CustomEnchantments.getInstance().msgManager;
	private DFFaction method = new DFFaction();
	@EventHandler
	public void chatFeature(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
		if(player.getWorld().getName().equalsIgnoreCase("DFWarzone-1") || player.getWorld().getName().equalsIgnoreCase("factionworld-1")) {
			int level = dfPlayer.getLevel();
			DFFaction faction = method.getFaction(player.getUniqueId());
			if(faction != null) {
				event.setFormat("§6" + faction.getName() + " §a| §b§l"  + level + "§a | §7" + player.getName() + ": " + event.getMessage());
			}
			else {
				event.setFormat("§b§l" + level + " §a| §7" + player.getName() + ": " + event.getMessage());
			}
		}
		else {
			event.setFormat("§7" + player.getName() + ": " + event.getMessage());
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
