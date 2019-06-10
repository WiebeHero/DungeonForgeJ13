package me.WiebeHero.XpTrader;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import Skills.SkillJoin;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.Spawners.SpawnerList;

public class XPAddPlayers extends SpawnerList implements Listener {
	SkillJoin join = new SkillJoin();
	@EventHandler
	public void xpAddPlayer(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(player.getInventory().getItemInMainHand() != null) {
				if(player.getInventory().getItemInMainHand().getType() == Material.EXPERIENCE_BOTTLE) {
					if(player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
						if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.stripColor("XP Bottle (Player)"))) {
							ItemStack item = player.getInventory().getItemInMainHand();
							String xpAmount = "";
				    		for(String s : item.getItemMeta().getLore()) {
				    			if(s.contains("XP Amount:")) {
				    				xpAmount = ChatColor.stripColor(s);
				    			}
				    		}
				    		xpAmount = xpAmount.replaceAll("[^\\d.]", "");
							int xpAdd = Integer.parseInt(xpAmount);
							int xp = join.getXPList().get(player.getUniqueId());
							int maxxp = join.getMXPList().get(player.getUniqueId());
							int level = join.getLevelList().get(player.getUniqueId());
							int finalXP = xp + xpAdd;
							if(level < 100) {
								if(finalXP >= maxxp) {
									level++;
									xp = finalXP - maxxp;
									int maxxpFinal = (int)(double)(maxxp / 100.00 * 107.00);
									join.getLevelList().put(player.getUniqueId(), level);
									join.getMXPList().put(player.getUniqueId(), maxxpFinal);
									join.getXPList().put(player.getUniqueId(), xp);
									join.getSkillPoints().put(player.getUniqueId(), join.getSkillPoints().get(player.getUniqueId()) + 2);
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, (float) 0.5);
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have leveled up to level &6" + level + "&a!"));
									
								}
								else if(finalXP > 0){
									join.getXPList().put(player.getUniqueId(), finalXP);
								}
							}
							float barprogress = (float) finalXP / maxxp;
							if(finalXP > 0){
								if(!(barprogress > 1)) {
									player.setLevel(level);
									player.setExp((float)barprogress);
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
