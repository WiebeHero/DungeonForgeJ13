package me.WiebeHero.MoreStuff;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.WiebeHero.Factions.DFFaction;

public class Chat implements Listener{
	private DFFaction method = new DFFaction();
	@EventHandler
	public void chatFeature(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		File f1 =  new File("plugins/CustomEnchantments/playerskillsDF.yml");
		YamlConfiguration yml1 = YamlConfiguration.loadConfiguration(f1);
		if(player.getWorld().getName().equalsIgnoreCase("DFWarzone-1") || player.getWorld().getName().equalsIgnoreCase("factionworld-1")) {
			int level = yml1.getInt("Skills.Players." + player.getUniqueId() + ".Level");
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
	}
}
