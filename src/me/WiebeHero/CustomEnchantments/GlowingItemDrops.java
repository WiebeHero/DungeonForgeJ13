package me.WiebeHero.CustomEnchantments;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

public class GlowingItemDrops implements Listener{
	@EventHandler
	public void GlowItemDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		ItemStack item1 = event.getItemDrop().getItemStack();
		if(item1.hasItemMeta()) {
			if(item1.getItemMeta().hasLore()) {
				Scoreboard scoreboard = player.getScoreboard();
				String loreLine = "";
				for(String s : item1.getItemMeta().getLore()) {
					if(s.contains("Rarity:")) {
						loreLine = s;
					}
				}
				if(loreLine.contains("Common")) {
					Entity entity = (Entity) event.getItemDrop();
					scoreboard.getTeam("GRAY").addEntry(entity.getUniqueId().toString());
					entity.setGlowing(true);
				}
				else if(loreLine.contains("Rare")) {
					Entity entity = (Entity) event.getItemDrop();
					scoreboard.getTeam("GREEN").addEntry(entity.getUniqueId().toString());
					entity.setGlowing(true);
				}
				else if(loreLine.contains("Epic")) {
					Entity entity = (Entity) event.getItemDrop();
					scoreboard.getTeam("AQUA").addEntry(entity.getUniqueId().toString());
					entity.setGlowing(true);
				}
				else if(loreLine.contains("Legendary")) {
					Entity entity = (Entity) event.getItemDrop();
					scoreboard.getTeam("RED").addEntry(entity.getUniqueId().toString());
					entity.setGlowing(true);
				}
				else if(loreLine.contains("Mythic")) {
					Entity entity = (Entity) event.getItemDrop();
					scoreboard.getTeam("PURPLE").addEntry(entity.getUniqueId().toString());
					entity.setGlowing(true);
				}
				else if(loreLine.contains("Heroic")) {
					Entity entity = (Entity) event.getItemDrop();
					scoreboard.getTeam("YELLOW").addEntry(entity.getUniqueId().toString());
					entity.setGlowing(true);
				}
				else if(loreLine.contains("Regular")){
					Entity entity = (Entity) event.getItemDrop();
					entity.setGlowing(true);
				}
			}
		}
	}
}
