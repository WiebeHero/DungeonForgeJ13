package Skills;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import Skills.SkillEnum.Skills;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.DFShops.MoneyCreate;
import net.md_5.bungee.api.ChatColor;

public class SkillMenuInteract implements Listener{
	SkillJoin join = new SkillJoin();
	PlayerClass pc = new PlayerClass();
	SkillMenu menu = new SkillMenu();
	MoneyCreate money = new MoneyCreate();
	EffectSkills sk = new EffectSkills();
	@EventHandler
	public void skillMenuClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		Player player = (Player) event.getWhoClicked();
		InventoryView current = player.getOpenInventory();
		if(current.getTitle().contains("Skills of:")) {
			event.setCancelled(true);
			if(item == null || !item.hasItemMeta()) {
				return;
			}
			else {
				String skillName = ChatColor.stripColor(item.getItemMeta().getDisplayName());
				if(skillName.contains("Attack Damage")) {
					if(pc.getSkill(player.getUniqueId(), Skills.SKILL_POINTS) > 0) {
						if(pc.getSkill(player.getUniqueId(), Skills.ATTACK_DAMAGE) < 100) {
							pc.setSkill(player.getUniqueId(), Skills.ATTACK_DAMAGE, pc.getSkill(player.getUniqueId(), Skills.ATTACK_DAMAGE) + 1);
							pc.setSkill(player.getUniqueId(), Skills.SKILL_POINTS, pc.getSkill(player.getUniqueId(), Skills.SKILL_POINTS) - 1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							pc.resetCalculations(player.getUniqueId());
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
					if(pc.getSkill(player.getUniqueId(), Skills.SKILL_POINTS) > 0) {
						if(pc.getSkill(player.getUniqueId(), Skills.ATTACK_SPEED) < 100) {
							pc.setSkill(player.getUniqueId(), Skills.ATTACK_SPEED, pc.getSkill(player.getUniqueId(), Skills.ATTACK_SPEED) + 1);
							pc.setSkill(player.getUniqueId(), Skills.SKILL_POINTS, pc.getSkill(player.getUniqueId(), Skills.SKILL_POINTS) - 1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							pc.resetCalculations(player.getUniqueId());
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
					if(pc.getSkill(player.getUniqueId(), Skills.SKILL_POINTS) > 0) {
						if(pc.getSkill(player.getUniqueId(), Skills.CRITICAL_CHANCE) < 100) {
							pc.setSkill(player.getUniqueId(), Skills.CRITICAL_CHANCE, pc.getSkill(player.getUniqueId(), Skills.CRITICAL_CHANCE) + 1);
							pc.setSkill(player.getUniqueId(), Skills.SKILL_POINTS, pc.getSkill(player.getUniqueId(), Skills.SKILL_POINTS) - 1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							pc.resetCalculations(player.getUniqueId());
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
					if(pc.getSkill(player.getUniqueId(), Skills.SKILL_POINTS) > 0) {
						if(pc.getSkill(player.getUniqueId(), Skills.RANGED_DAMAGE) < 100) {
							pc.setSkill(player.getUniqueId(), Skills.RANGED_DAMAGE, pc.getSkill(player.getUniqueId(), Skills.RANGED_DAMAGE) + 1);
							pc.setSkill(player.getUniqueId(), Skills.SKILL_POINTS, pc.getSkill(player.getUniqueId(), Skills.SKILL_POINTS) - 1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							pc.resetCalculations(player.getUniqueId());
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
					if(pc.getSkill(player.getUniqueId(), Skills.SKILL_POINTS) > 0) {
						if(pc.getSkill(player.getUniqueId(), Skills.MAX_HEALTH) < 100) {
							pc.setSkill(player.getUniqueId(), Skills.MAX_HEALTH, pc.getSkill(player.getUniqueId(), Skills.MAX_HEALTH) + 1);
							pc.setSkill(player.getUniqueId(), Skills.SKILL_POINTS, pc.getSkill(player.getUniqueId(), Skills.SKILL_POINTS) - 1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							pc.resetCalculations(player.getUniqueId());
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
					if(pc.getSkill(player.getUniqueId(), Skills.SKILL_POINTS) > 0) {
						if(pc.getSkill(player.getUniqueId(), Skills.ARMOR_DEFENSE) < 100) {
							pc.setSkill(player.getUniqueId(), Skills.ARMOR_DEFENSE, pc.getSkill(player.getUniqueId(), Skills.ARMOR_DEFENSE) + 1);
							pc.setSkill(player.getUniqueId(), Skills.SKILL_POINTS, pc.getSkill(player.getUniqueId(), Skills.SKILL_POINTS) - 1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							pc.resetCalculations(player.getUniqueId());
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
					if(pc.getSkill(player.getUniqueId(), Skills.ATTACK_DAMAGE_MODIFIER) < 5) {
						if(pc.getSkill(player.getUniqueId(), Skills.ATTACK_DAMAGE) >= 20 + (20 * pc.getSkill(player.getUniqueId(), Skills.ATTACK_DAMAGE_MODIFIER))) {
							if(pc.getSkill(player.getUniqueId(), Skills.LEVEL) >= 10 + (10 * pc.getSkill(player.getUniqueId(), Skills.ATTACK_DAMAGE_MODIFIER))) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * pc.getSkill(player.getUniqueId(), Skills.ATTACK_DAMAGE_MODIFIER))) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 * pc.getSkill(player.getUniqueId(), Skills.ATTACK_DAMAGE_MODIFIER)));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									pc.setSkill(player.getUniqueId(), Skills.ATTACK_DAMAGE_MODIFIER, pc.getSkill(player.getUniqueId(), Skills.ATTACK_DAMAGE_MODIFIER) + 1);
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
					if(pc.getSkill(player.getUniqueId(), Skills.ATTACK_SPEED_MODIFIER) < 5) {
						if(pc.getSkill(player.getUniqueId(), Skills.ATTACK_SPEED) >= 20 + (20 * pc.getSkill(player.getUniqueId(), Skills.ATTACK_SPEED_MODIFIER))) {
							if(pc.getSkill(player.getUniqueId(), Skills.LEVEL) >= 10 + (10 * pc.getSkill(player.getUniqueId(), Skills.ATTACK_SPEED_MODIFIER))) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * pc.getSkill(player.getUniqueId(), Skills.ATTACK_SPEED_MODIFIER))) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 * pc.getSkill(player.getUniqueId(), Skills.ATTACK_SPEED_MODIFIER)));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									pc.setSkill(player.getUniqueId(), Skills.ATTACK_SPEED_MODIFIER, pc.getSkill(player.getUniqueId(), Skills.ATTACK_SPEED_MODIFIER) + 1);
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
					if(pc.getSkill(player.getUniqueId(), Skills.CRITICAL_CHANCE_MODIFIER) < 5) {
						if(pc.getSkill(player.getUniqueId(), Skills.CRITICAL_CHANCE) >= 20 + (20 * pc.getSkill(player.getUniqueId(), Skills.CRITICAL_CHANCE_MODIFIER))) {
							if(pc.getSkill(player.getUniqueId(), Skills.LEVEL) >= 10 + (10 * pc.getSkill(player.getUniqueId(), Skills.CRITICAL_CHANCE_MODIFIER))) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * pc.getSkill(player.getUniqueId(), Skills.CRITICAL_CHANCE_MODIFIER))) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 *pc.getSkill(player.getUniqueId(), Skills.CRITICAL_CHANCE_MODIFIER)));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									pc.setSkill(player.getUniqueId(), Skills.CRITICAL_CHANCE_MODIFIER, pc.getSkill(player.getUniqueId(), Skills.CRITICAL_CHANCE_MODIFIER) + 1);
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
					if(pc.getSkill(player.getUniqueId(), Skills.RANGED_DAMAGE_MODIFIER) < 5) {
						if(pc.getSkill(player.getUniqueId(), Skills.RANGED_DAMAGE) >= 20 + (20 * pc.getSkill(player.getUniqueId(), Skills.RANGED_DAMAGE_MODIFIER))) {
							if(pc.getSkill(player.getUniqueId(), Skills.LEVEL) >= 10 + (10 * pc.getSkill(player.getUniqueId(), Skills.RANGED_DAMAGE_MODIFIER))) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * pc.getSkill(player.getUniqueId(), Skills.RANGED_DAMAGE_MODIFIER))) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 * pc.getSkill(player.getUniqueId(), Skills.RANGED_DAMAGE_MODIFIER)));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									pc.setSkill(player.getUniqueId(), Skills.RANGED_DAMAGE_MODIFIER, pc.getSkill(player.getUniqueId(), Skills.RANGED_DAMAGE_MODIFIER) + 1);
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
					if(pc.getSkill(player.getUniqueId(), Skills.MAX_HEALTH_MODIFIER) < 5) {
						if(pc.getSkill(player.getUniqueId(), Skills.MAX_HEALTH) >= 20 + (20 * pc.getSkill(player.getUniqueId(), Skills.MAX_HEALTH_MODIFIER))) {
							if(pc.getSkill(player.getUniqueId(), Skills.LEVEL) >= 10 + (10 * pc.getSkill(player.getUniqueId(), Skills.MAX_HEALTH_MODIFIER))) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * pc.getSkill(player.getUniqueId(), Skills.MAX_HEALTH_MODIFIER))) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 * pc.getSkill(player.getUniqueId(), Skills.MAX_HEALTH_MODIFIER)));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									pc.setSkill(player.getUniqueId(), Skills.MAX_HEALTH_MODIFIER, pc.getSkill(player.getUniqueId(), Skills.MAX_HEALTH_MODIFIER) + 1);
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
					if(pc.getSkill(player.getUniqueId(), Skills.ARMOR_DEFENSE_MODIFIER) < 5) {
						if(pc.getSkill(player.getUniqueId(), Skills.ARMOR_DEFENSE) >= 20 + (20 * pc.getSkill(player.getUniqueId(), Skills.ARMOR_DEFENSE_MODIFIER))) {
							if(pc.getSkill(player.getUniqueId(), Skills.LEVEL) >= 10 + (10 * pc.getSkill(player.getUniqueId(), Skills.ARMOR_DEFENSE_MODIFIER))) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * pc.getSkill(player.getUniqueId(), Skills.ARMOR_DEFENSE_MODIFIER))) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 * pc.getSkill(player.getUniqueId(), Skills.ARMOR_DEFENSE_MODIFIER)));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									pc.setSkill(player.getUniqueId(), Skills.ARMOR_DEFENSE_MODIFIER, pc.getSkill(player.getUniqueId(), Skills.ARMOR_DEFENSE_MODIFIER) + 1);
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