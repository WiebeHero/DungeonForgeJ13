package me.WiebeHero.XpTrader;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEvents.DFPlayerLevelUpEvent;
import me.WiebeHero.Scoreboard.DFScoreboard;
import me.WiebeHero.Skills.DFPlayer;

public class XPAddPlayers implements Listener {
	DFScoreboard board = new DFScoreboard();
	@EventHandler
	public void xpAddPlayer(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(player.getInventory().getItemInMainHand() != null) {
				if(player.getInventory().getItemInMainHand().getType() == Material.EXPERIENCE_BOTTLE) {
					if(player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
						if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.stripColor("XP Bottle (Player)"))) {
							DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
							ItemStack item = player.getInventory().getItemInMainHand();
							String xpAmount = "";
				    		for(String s : item.getItemMeta().getLore()) {
				    			if(s.contains("XP Amount:")) {
				    				xpAmount = ChatColor.stripColor(s);
				    			}
				    		}
				    		xpAmount = xpAmount.replaceAll("[^\\d.]", "");
							int xpAdd = Integer.parseInt(xpAmount);
							int xp = dfPlayer.getExperience();
							int maxxp = dfPlayer.getMaxExperience();
							int level = dfPlayer.getLevel();
							int finalXP = xp + xpAdd;
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 2, (float) 1.0);
							if(level < 100) {
								if(finalXP >= maxxp) {
									level++;
									xp = finalXP - maxxp;
									int maxxpFinal = (int)(double)(maxxp / 100.00 * 107.00);
									dfPlayer.addLevel(1);
									dfPlayer.setMaxExperience(maxxpFinal);
									dfPlayer.setExperience(xp);
									dfPlayer.addSkillPoints(3);
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, (float) 0.5);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have leveled up to level &6" + level + "&a!"));
									board.generateScoreboard(player);
									DFPlayerLevelUpEvent ev = new DFPlayerLevelUpEvent(player);
									Bukkit.getServer().getPluginManager().callEvent(ev);
								}
								else if(finalXP > 0){
									dfPlayer.setExperience(finalXP);
								}
							}
							float barprogress = (float) finalXP / maxxp;
							if(finalXP > 0){
								if(!(barprogress > 1)) {
									player.setLevel(level);
									player.setExp((float)barprogress);
								}
								else {
									player.setLevel(level);
									player.setExp((float)barprogress - 1.0F);
								}
							}
							player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
						}
					}
				}
			}
		}
	}
}
