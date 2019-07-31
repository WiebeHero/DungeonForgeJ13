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

import Skills.PlayerClass;
import Skills.SkillEnum.Skills;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.Spawners.SpawnerList;

public class XPAddPlayers extends SpawnerList implements Listener {
	PlayerClass pc = new PlayerClass();
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
							int xp = pc.getSkill(player.getUniqueId(), Skills.XP);
							int maxxp = pc.getSkill(player.getUniqueId(), Skills.MAXXP);
							int level = pc.getSkill(player.getUniqueId(), Skills.LEVEL);
							int finalXP = xp + xpAdd;
							if(level < 100) {
								if(finalXP >= maxxp) {
									level++;
									xp = finalXP - maxxp;
									int maxxpFinal = (int)(double)(maxxp / 100.00 * 107.00);
									pc.setSkill(player.getUniqueId(), Skills.LEVEL, level);
									pc.setSkill(player.getUniqueId(), Skills.MAXXP, maxxpFinal);
									pc.setSkill(player.getUniqueId(), Skills.LEVEL, xp);
									pc.setSkill(player.getUniqueId(), Skills.SKILL_POINTS, pc.getSkill(player.getUniqueId(), Skills.SKILL_POINTS) + 3);
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, (float) 0.5);
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have leveled up to level &6" + level + "&a!"));
									
								}
								else if(finalXP > 0){
									pc.setSkill(player.getUniqueId(), Skills.XP, finalXP);
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
