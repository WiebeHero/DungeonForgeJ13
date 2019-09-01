package me.WiebeHero.Skills;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.DFShops.MoneyCreate;
import net.md_5.bungee.api.ChatColor;

public class SkillMenuInteract implements Listener{
	SkillMenu menu = new SkillMenu();
	MoneyCreate money = new MoneyCreate();
	EffectSkills sk = new EffectSkills();
	@EventHandler
	public void skillMenuClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		Player player = (Player) event.getWhoClicked();
		DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
		InventoryView current = player.getOpenInventory();
		if(current.getTitle().contains("Skills of:")) {
			event.setCancelled(true);
			if(item == null || !item.hasItemMeta()) {
				return;
			}
			else {
				String skillName = ChatColor.stripColor(item.getItemMeta().getDisplayName());
				if(skillName.contains("Attack Damage")) {
					if(dfPlayer.getSkillPoints() > 0) {
						if(dfPlayer.getAtk() < 100) {
							dfPlayer.addAtk(1);
							dfPlayer.removeSkillPoints(1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							dfPlayer.resetCalculations();
							menu.SkillMenuInv(player);
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis skill is already maxed!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have enough &6Skill Points!"));
					}
				}
				else if(skillName.contains("Attack Speed")) {
					if(dfPlayer.getSkillPoints() > 0) {
						if(dfPlayer.getSpd() < 100) {
							dfPlayer.addSpd(1);
							dfPlayer.removeSkillPoints(1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							dfPlayer.resetCalculations();
							sk.attackSpeed(player);
							menu.SkillMenuInv(player);
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis skill is already maxed!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have enough &6Skill Points!"));
					}
				}
				else if(skillName.contains("Critical Chance")) {
					if(dfPlayer.getSkillPoints() > 0) {
						if(dfPlayer.getCrt() < 100) {
							dfPlayer.addCrt(1);
							dfPlayer.removeSkillPoints(1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							dfPlayer.resetCalculations();
							menu.SkillMenuInv(player);
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis skill is already maxed!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have enough &6Skill Points!"));
					}
				}
				else if(skillName.contains("Ranged Damage")) {
					if(dfPlayer.getSkillPoints() > 0) {
						if(dfPlayer.getRnd() < 100) {
							dfPlayer.addRnd(1);
							dfPlayer.removeSkillPoints(1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							dfPlayer.resetCalculations();
							menu.SkillMenuInv(player);
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis skill is already maxed!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have enough &6Skill Points!"));
					}
				}
				else if(skillName.contains("Health")) {
					if(dfPlayer.getSkillPoints() > 0) {
						if(dfPlayer.getHp() < 100) {
							dfPlayer.addHp(1);
							dfPlayer.removeSkillPoints(1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							dfPlayer.resetCalculations();
							menu.SkillMenuInv(player);
							sk.changeHealth(player);
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis skill is already maxed!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have enough &6Skill Points!"));
					}
				}
				else if(skillName.contains("Defense")) {
					if(dfPlayer.getSkillPoints() > 0) {
						if(dfPlayer.getDf() < 100) {
							dfPlayer.addDf(1);
							dfPlayer.removeSkillPoints(1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							menu.SkillMenuInv(player);
							sk.runDefense(player);
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis skill is already maxed!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have enough &6Skill Points!"));
					}
				}
				else if(skillName.contains("Attack Modifier")) {
					if(dfPlayer.getAtkMod() < 5) {
						if(dfPlayer.getAtk() >= 20 + (20 * dfPlayer.getAtkMod())) {
							if(dfPlayer.getLevel() >= 10 + (10 * dfPlayer.getAtkMod())) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * dfPlayer.getAtkMod())) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 * dfPlayer.getAtkMod()));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									dfPlayer.addAtkMod(1);
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
									menu.SkillMenuInv(player);
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal money requirement!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Player Level &crequirement!"));
							}
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Attack Damage Level &crequirement!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis Ability Modifier is already maxed!"));
					}
				}
				else if(skillName.contains("Speed Modifier")) {
					if(dfPlayer.getSpdMod() < 5) {
						if(dfPlayer.getSpd() >= 20 + (20 * dfPlayer.getSpdMod())) {
							if(dfPlayer.getLevel() >= 10 + (10 * dfPlayer.getSpdMod())) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * dfPlayer.getSpdMod())) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 * dfPlayer.getSpdMod()));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									dfPlayer.addSpdMod(1);
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
									menu.SkillMenuInv(player);
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal money requirement!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Player Level &crequirement!"));
							}
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Attack Speed Level &crequirement!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis Ability Modifier is already maxed!"));
					}
				}
				else if(skillName.contains("Critical Modifier")) {
					if(dfPlayer.getCrtMod() < 5) {
						if(dfPlayer.getCrt() >= 20 + (20 * dfPlayer.getCrtMod())) {
							if(dfPlayer.getLevel() >= 10 + (10 * dfPlayer.getCrtMod())) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * dfPlayer.getCrtMod())) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 * dfPlayer.getCrtMod()));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									dfPlayer.addCrtMod(1);
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
									menu.SkillMenuInv(player);
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal money requirement!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Player Level &crequirement!"));
							}
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Critical Chance Level &crequirement!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis Ability Modifier is already maxed!"));
					}
				}
				else if(skillName.contains("Ranged Modifier")) {
					if(dfPlayer.getRndMod() < 5) {
						if(dfPlayer.getRnd() >= 20 + (20 * dfPlayer.getRndMod())) {
							if(dfPlayer.getLevel() >= 10 + (10 * dfPlayer.getRndMod())) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * dfPlayer.getRndMod())) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 * dfPlayer.getRndMod()));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									dfPlayer.addRndMod(1);
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
									menu.SkillMenuInv(player);
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal money requirement!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Player Level &crequirement!"));
							}
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Ranged Damage Level &crequirement!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis Ability Modifier is already maxed!"));
					}
				}
				else if(skillName.contains("Health Modifier")) {
					if(dfPlayer.getHpMod() < 5) {
						if(dfPlayer.getHp() >= 20 + (20 * dfPlayer.getHpMod())) {
							if(dfPlayer.getLevel() >= 10 + (10 * dfPlayer.getHpMod())) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * dfPlayer.getHpMod())) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 * dfPlayer.getHpMod()));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									dfPlayer.addHpMod(1);
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
									menu.SkillMenuInv(player);
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal money requirement!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Player Level &crequirement!"));
							}
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Health Level &crequirement!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis Ability Modifier is already maxed!"));
					}
				}
				else if(skillName.contains("Defense Modifier")) {
					if(dfPlayer.getDfMod() < 5) {
						if(dfPlayer.getDf() >= 20 + (20 * dfPlayer.getDfMod())) {
							if(dfPlayer.getLevel() >= 10 + (10 * dfPlayer.getDfMod())) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * dfPlayer.getDfMod())) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 * dfPlayer.getDfMod()));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									dfPlayer.addDfMod(1);
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
									menu.SkillMenuInv(player);
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal money requirement!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Player Level &crequirement!"));
							}
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Health Level &crequirement!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis Ability Modifier is already maxed!"));
					}
				}
			}
		}
	}
}