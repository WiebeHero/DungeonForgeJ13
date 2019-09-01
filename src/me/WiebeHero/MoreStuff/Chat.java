package me.WiebeHero.MoreStuff;

import java.io.File;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.WiebeHero.Factions.DFFactions;

public class Chat implements Listener{
	private DFFactions f = new DFFactions();
	@EventHandler
	public void chatFeature(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		File f1 =  new File("plugins/CustomEnchantments/playerskillsDF.yml");
		YamlConfiguration yml1 = YamlConfiguration.loadConfiguration(f1);
		if(player.getWorld().getName().equalsIgnoreCase("DFWarzone-1") || player.getWorld().getName().equalsIgnoreCase("factionworld-1")) {
			String fName = "";
			for(Entry<String, ArrayList<UUID>> entry : f.getFactionMemberList().entrySet()) {
				if(entry.getValue().contains(player.getUniqueId())) {
					fName = entry.getKey();
				}
			}
			int level = yml1.getInt("Skills.Players." + player.getUniqueId() + ".Level");
			if(!fName.equals("")) {
				event.setFormat("§6" + fName + "§a | §b§l"  + level + "§a | §7" + player.getName() + ": " + event.getMessage());
			}
			else {
				event.setFormat("§b§l"  + level + "&a | §7" + player.getName() + ": " + event.getMessage());
			}
		}
		else {
			event.setFormat("§7" + player.getName() + ": " + event.getMessage());
		}
	}
}
