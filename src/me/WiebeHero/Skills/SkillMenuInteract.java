package me.WiebeHero.Skills;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Scoreboard.DFScoreboard;

public class SkillMenuInteract implements Listener{
	private DFPlayerManager dfManager;
	private SkillMenu menu;
	private ClassMenu cMenu;
	private DFScoreboard score;
	public SkillMenuInteract(DFPlayerManager manager, SkillMenu menu, ClassMenu cMenu, DFScoreboard board) {
		this.menu = menu;
		this.dfManager = manager;
		this.cMenu = cMenu;
		this.score = board;
	}
	@EventHandler
	public void skillMenuClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		Player player = (Player) event.getWhoClicked();
		DFPlayer dfPlayer = dfManager.getEntity(player);
		InventoryView current = player.getOpenInventory();
		if(current.getTitle().contains("Reset Profile:")) {
			event.setCancelled(true);
			if(item == null || !item.hasItemMeta()) {
				return;
			}
			else {
				NBTItem i = new NBTItem(item);
				if(i.hasKey("Confirm")) {
					if(dfPlayer.getLevel() >= 10 && dfPlayer.getMoney() >= 10000) {
						dfManager.resetEntity(player);
						player.teleport(Bukkit.getWorld("DFWarzone-1").getSpawnLocation());
						player.closeInventory();
						cMenu.ClassSelect(player);
						score.updateScoreboard(player);
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have reset your player profile!"));
					}
					else if(dfPlayer.getLevel() < 10){
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal level requirement to reset your profile!"));
					}
					else if(dfPlayer.getMoney() < 10000){
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal money requirement to reset your profile!"));
					}
				}
				else if(i.hasKey("Cancel")) {
					player.closeInventory();
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have decided to not reset your player profile."));
				}
			}
		}
		else if(current.getTitle().contains("Skills of:")) {
			event.setCancelled(true);
			if(item == null || !item.hasItemMeta()) {
				return;
			}
			else {
				NBTItem i = new NBTItem(item);
				String skillName = item.getItemMeta().getDisplayName();
				if(i.hasKey("Reset")) {
					menu.ResetSure(player);
				}
				else if(skillName.contains("Attack Damage")) {
					if(dfPlayer.getSkillPoints() > 0) {
						if(dfPlayer.getAtk() < 100) {
							dfPlayer.addAtk(1);
							dfPlayer.removeSkillPoints(1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							dfPlayer.resetIncreases();
							menu.SkillMenuInv(player);
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis skill is already maxed!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have enough &6Skill Points!"));
					}
				}
				else if(skillName.contains("Attack Speed")) {
					if(dfPlayer.getSkillPoints() > 0) {
						if(dfPlayer.getSpd() < 100) {
							dfPlayer.addSpd(1);
							dfPlayer.removeSkillPoints(1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							dfPlayer.resetIncreases();
							dfPlayer.attackSpeed();
							menu.SkillMenuInv(player);
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis skill is already maxed!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have enough &6Skill Points!"));
					}
				}
				else if(skillName.contains("Critical Chance")) {
					if(dfPlayer.getSkillPoints() > 0) {
						if(dfPlayer.getCrt() < 100) {
							dfPlayer.addCrt(1);
							dfPlayer.removeSkillPoints(1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							dfPlayer.resetIncreases();
							menu.SkillMenuInv(player);
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis skill is already maxed!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have enough &6Skill Points!"));
					}
				}
				else if(skillName.contains("Ranged Damage")) {
					if(dfPlayer.getSkillPoints() > 0) {
						if(dfPlayer.getRnd() < 100) {
							dfPlayer.addRnd(1);
							dfPlayer.removeSkillPoints(1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							dfPlayer.resetIncreases();
							menu.SkillMenuInv(player);
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis skill is already maxed!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have enough &6Skill Points!"));
					}
				}
				else if(skillName.contains("Health")) {
					if(dfPlayer.getSkillPoints() > 0) {
						if(dfPlayer.getHp() < 100) {
							dfPlayer.addHp(1);
							dfPlayer.removeSkillPoints(1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							dfPlayer.resetIncreases();
							menu.SkillMenuInv(player);
							dfPlayer.changeHealth();
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis skill is already maxed!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have enough &6Skill Points!"));
					}
				}
				else if(skillName.contains("Defense")) {
					if(dfPlayer.getSkillPoints() > 0) {
						if(dfPlayer.getDf() < 100) {
							dfPlayer.addDf(1);
							dfPlayer.removeSkillPoints(1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							dfPlayer.resetIncreases();
							menu.SkillMenuInv(player);
							dfPlayer.runDefense();
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis skill is already maxed!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have enough &6Skill Points!"));
					}
				}
				else if(skillName.contains(new CCT().colorize("&4"))) {
					if(dfPlayer.getAtkMod() < 5) {
						if(dfPlayer.getAtk() >= 20 + (20 * dfPlayer.getAtkMod())) {
							if(dfPlayer.getLevel() >= 10 + (10 * dfPlayer.getAtkMod())) {
								if(dfPlayer.getMoney() > 20000.00 + (20000.00 * dfPlayer.getAtkMod())) {
									double moneyNow = dfPlayer.getMoney() - (20000.00 + (20000.00 * dfPlayer.getAtkMod()));
									dfPlayer.setMoney(moneyNow);
									dfPlayer.addAtkMod(1);
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
									menu.SkillMenuInv(player);
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal money requirement!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Player Level &crequirement!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Attack Damage Level &crequirement!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis Ability Modifier is already maxed!"));
					}
				}
				else if(skillName.contains(new CCT().colorize("&9"))) {
					if(dfPlayer.getSpdMod() < 5) {
						if(dfPlayer.getSpd() >= 20 + (20 * dfPlayer.getSpdMod())) {
							if(dfPlayer.getLevel() >= 10 + (10 * dfPlayer.getSpdMod())) {
								if(dfPlayer.getMoney() > 20000.00 + (20000.00 * dfPlayer.getSpdMod())) {
									double moneyNow = dfPlayer.getMoney() - (20000.00 + (20000.00 * dfPlayer.getSpdMod()));
									dfPlayer.setMoney(moneyNow);
									dfPlayer.addSpdMod(1);
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
									menu.SkillMenuInv(player);
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal money requirement!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Player Level &crequirement!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Attack Speed Level &crequirement!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis Ability Modifier is already maxed!"));
					}
				}
				else if(skillName.contains(new CCT().colorize("&5"))) {
					if(dfPlayer.getCrtMod() < 5) {
						if(dfPlayer.getCrt() >= 20 + (20 * dfPlayer.getCrtMod())) {
							if(dfPlayer.getLevel() >= 10 + (10 * dfPlayer.getCrtMod())) {
								if(dfPlayer.getMoney() > 20000.00 + (20000.00 * dfPlayer.getCrtMod())) {
									double moneyNow = dfPlayer.getMoney() - (20000.00 + (20000.00 * dfPlayer.getCrtMod()));
									dfPlayer.setMoney(moneyNow);
									dfPlayer.addCrtMod(1);
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
									menu.SkillMenuInv(player);
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal money requirement!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Player Level &crequirement!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Critical Chance Level &crequirement!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis Ability Modifier is already maxed!"));
					}
				}
				else if(skillName.contains(new CCT().colorize("&d"))) {
					if(dfPlayer.getRndMod() < 5) {
						if(dfPlayer.getRnd() >= 20 + (20 * dfPlayer.getRndMod())) {
							if(dfPlayer.getLevel() >= 10 + (10 * dfPlayer.getRndMod())) {
								if(dfPlayer.getMoney() > 20000.00 + (20000.00 * dfPlayer.getRndMod())) {
									double moneyNow = dfPlayer.getMoney() - (20000.00 + (20000.00 * dfPlayer.getRndMod()));
									dfPlayer.setMoney(moneyNow);
									dfPlayer.addRndMod(1);
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
									menu.SkillMenuInv(player);
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal money requirement!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Player Level &crequirement!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Ranged Damage Level &crequirement!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis Ability Modifier is already maxed!"));
					}
				}
				else if(skillName.contains(new CCT().colorize("&c"))) {
					if(dfPlayer.getHpMod() < 5) {
						if(dfPlayer.getHp() >= 20 + (20 * dfPlayer.getHpMod())) {
							if(dfPlayer.getLevel() >= 10 + (10 * dfPlayer.getHpMod())) {
								if(dfPlayer.getMoney() > 20000.00 + (20000.00 * dfPlayer.getHpMod())) {
									double moneyNow = dfPlayer.getMoney() - (20000.00 + (20000.00 * dfPlayer.getHpMod()));
									dfPlayer.setMoney(moneyNow);
									dfPlayer.addHpMod(1);
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
									menu.SkillMenuInv(player);
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal money requirement!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Player Level &crequirement!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Health Level &crequirement!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis Ability Modifier is already maxed!"));
					}
				}
				else if(skillName.contains(new CCT().colorize("&8"))) {
					if(dfPlayer.getDfMod() < 5) {
						if(dfPlayer.getDf() >= 20 + (20 * dfPlayer.getDfMod())) {
							if(dfPlayer.getLevel() >= 10 + (10 * dfPlayer.getDfMod())) {
								if(dfPlayer.getMoney() > 20000.00 + (20000.00 * dfPlayer.getDfMod())) {
									double moneyNow = dfPlayer.getMoney() - (20000.00 + (20000.00 * dfPlayer.getDfMod()));
									dfPlayer.setMoney(moneyNow);
									dfPlayer.addDfMod(1);
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
									menu.SkillMenuInv(player);
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal money requirement!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Player Level &crequirement!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't meet the minimal &6Health Level &crequirement!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis Ability Modifier is already maxed!"));
					}
				}
			}
		}
	}
}