package me.WiebeHero.CustomEnchantments;

import org.bukkit.ChatColor;
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
				for(String s1 : item1.getItemMeta().getLore()){
					if(ChatColor.stripColor(s1).contains("Common")) {
						Scoreboard scoreboard = player.getScoreboard();
						Entity entity = (Entity) event.getItemDrop();
						scoreboard.getTeam("GRAY").addEntry(entity.getUniqueId().toString());
						entity.setGlowing(true);
					}
					else if(ChatColor.stripColor(s1).contains("Rare")) {
						Scoreboard scoreboard = player.getScoreboard();
						Entity entity = (Entity) event.getItemDrop();
						scoreboard.getTeam("GREEN").addEntry(entity.getUniqueId().toString());
						entity.setGlowing(true);
					}
					else if(ChatColor.stripColor(s1).contains("Epic")) {
						Scoreboard scoreboard = player.getScoreboard();
						Entity entity = (Entity) event.getItemDrop();
						scoreboard.getTeam("AQUA").addEntry(entity.getUniqueId().toString());
						entity.setGlowing(true);
					}
					else if(ChatColor.stripColor(s1).contains("Legendary")) {
						Scoreboard scoreboard = player.getScoreboard();
						Entity entity = (Entity) event.getItemDrop();
						scoreboard.getTeam("RED").addEntry(entity.getUniqueId().toString());
						entity.setGlowing(true);
					}
					else if(ChatColor.stripColor(s1).contains("Mythic")) {
						Scoreboard scoreboard = player.getScoreboard();
						Entity entity = (Entity) event.getItemDrop();
						scoreboard.getTeam("PURPLE").addEntry(entity.getUniqueId().toString());
						entity.setGlowing(true);
					}
					else if(ChatColor.stripColor(s1).contains("Heroic")) {
						Scoreboard scoreboard = player.getScoreboard();
						Entity entity = (Entity) event.getItemDrop();
						scoreboard.getTeam("YELLOW").addEntry(entity.getUniqueId().toString());
						entity.setGlowing(true);
					}
					else {
						Entity entity = (Entity) event.getItemDrop();
						entity.setGlowing(false);
					}
				}
			}
		}
	}
}
