package Skills;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomMethods.MethodAttack;
import me.WiebeHero.CustomMethods.MethodAttackSpeed;
import me.WiebeHero.CustomMethods.MethodCritical;
import me.WiebeHero.CustomMethods.MethodDefense;
import me.WiebeHero.CustomMethods.MethodHealth;
import me.WiebeHero.CustomMethods.MethodRanged;
import me.WiebeHero.DFShops.MoneyCreate;
import net.md_5.bungee.api.ChatColor;

public class SkillMenuInteract implements Listener{
	SkillJoin join = new SkillJoin();
	SkillMenu menu = new SkillMenu();
	AttackSpeed speed = new AttackSpeed();
	HealthH health = new HealthH();
	Defense defense = new Defense();
	MoneyCreate money = new MoneyCreate();
	MethodAttack at = new MethodAttack();
	MethodAttackSpeed as = new MethodAttackSpeed();
	MethodCritical cc = new MethodCritical();
	MethodDefense df = new MethodDefense();
	MethodHealth hh = new MethodHealth();
	MethodRanged ra = new MethodRanged();
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
					if(join.getSkillPoints().get(player.getUniqueId()) > 0) {
						if(join.getADList().get(player.getUniqueId()) < 100) {
							join.getADList().put(player.getUniqueId(), join.getADList().get(player.getUniqueId()) + 1);
							join.getSkillPoints().put(player.getUniqueId(), join.getSkillPoints().get(player.getUniqueId()) - 1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							menu.SkillMenuInv(player);
							at.updateAttack(player.getUniqueId());
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
					if(join.getSkillPoints().get(player.getUniqueId()) > 0) {
						if(join.getASList().get(player.getUniqueId()) < 100) {
							join.getASList().put(player.getUniqueId(), join.getASList().get(player.getUniqueId()) + 1);
							join.getSkillPoints().put(player.getUniqueId(), join.getSkillPoints().get(player.getUniqueId()) - 1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							as.updateAttackSpeed(player.getUniqueId());
							speed.attackSpeedRun(player);
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
					if(join.getSkillPoints().get(player.getUniqueId()) > 0) {
						if(join.getCCList().get(player.getUniqueId()) < 100) {
							join.getCCList().put(player.getUniqueId(), join.getCCList().get(player.getUniqueId()) + 1);
							join.getSkillPoints().put(player.getUniqueId(), join.getSkillPoints().get(player.getUniqueId()) - 1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							cc.updateCriticalChance(player.getUniqueId());
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
					if(join.getSkillPoints().get(player.getUniqueId()) > 0) {
						if(join.getRDList().get(player.getUniqueId()) < 100) {
							join.getRDList().put(player.getUniqueId(), join.getRDList().get(player.getUniqueId()) + 1);
							join.getSkillPoints().put(player.getUniqueId(), join.getSkillPoints().get(player.getUniqueId()) - 1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							ra.updateRanged(player.getUniqueId());
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
					if(join.getSkillPoints().get(player.getUniqueId()) > 0) {
						if(join.getHHList().get(player.getUniqueId()) < 100) {
							join.getHHList().put(player.getUniqueId(), join.getHHList().get(player.getUniqueId()) + 1);
							join.getSkillPoints().put(player.getUniqueId(), join.getSkillPoints().get(player.getUniqueId()) - 1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							menu.SkillMenuInv(player);
							hh.updateHealth(player.getUniqueId());
							health.updateHealth(player);
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
					if(join.getSkillPoints().get(player.getUniqueId()) > 0) {
						if(join.getDFList().get(player.getUniqueId()) < 100) {
							join.getDFList().put(player.getUniqueId(), join.getDFList().get(player.getUniqueId()) + 1);
							join.getSkillPoints().put(player.getUniqueId(), join.getSkillPoints().get(player.getUniqueId()) - 1);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
							menu.SkillMenuInv(player);
							df.updateDefense(player.getUniqueId());
							defense.runDefense(player);
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
					if(join.getADMODList().get(player.getUniqueId()) < 5) {
						if(join.getADList().get(player.getUniqueId()) >= 20 + (20 * join.getADMODList().get(player.getUniqueId()))) {
							if(join.getLevelList().get(player.getUniqueId()) >= 10 + (10 * join.getADMODList().get(player.getUniqueId()))) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * join.getADMODList().get(player.getUniqueId()))) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 * join.getADMODList().get(player.getUniqueId())));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									join.getADMODList().put(player.getUniqueId(), join.getADMODList().get(player.getUniqueId()) + 1);
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
					if(join.getASMODList().get(player.getUniqueId()) < 5) {
						if(join.getASList().get(player.getUniqueId()) >= 20 + (20 * join.getASMODList().get(player.getUniqueId()))) {
							if(join.getLevelList().get(player.getUniqueId()) >= 10 + (10 * join.getASMODList().get(player.getUniqueId()))) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * join.getASMODList().get(player.getUniqueId()))) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 * join.getASMODList().get(player.getUniqueId())));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									join.getASMODList().put(player.getUniqueId(), join.getASMODList().get(player.getUniqueId()) + 1);
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
					if(join.getCCMODList().get(player.getUniqueId()) < 5) {
						if(join.getCCList().get(player.getUniqueId()) >= 20 + (20 * join.getCCMODList().get(player.getUniqueId()))) {
							if(join.getLevelList().get(player.getUniqueId()) >= 10 + (10 * join.getCCMODList().get(player.getUniqueId()))) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * join.getCCMODList().get(player.getUniqueId()))) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 *join.getCCMODList().get(player.getUniqueId())));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									join.getCCMODList().put(player.getUniqueId(), join.getCCMODList().get(player.getUniqueId()) + 1);
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
					if(join.getRDMODList().get(player.getUniqueId()) < 5) {
						if(join.getRDList().get(player.getUniqueId()) >= 20 + (20 * join.getRDMODList().get(player.getUniqueId()))) {
							if(join.getLevelList().get(player.getUniqueId()) >= 10 + (10 * join.getRDMODList().get(player.getUniqueId()))) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * join.getRDMODList().get(player.getUniqueId()))) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 * join.getRDMODList().get(player.getUniqueId())));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									join.getRDMODList().put(player.getUniqueId(), join.getRDMODList().get(player.getUniqueId()) + 1);
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
					if(join.getHHMODList().get(player.getUniqueId()) < 5) {
						if(join.getHHList().get(player.getUniqueId()) >= 20 + (20 * join.getHHMODList().get(player.getUniqueId()))) {
							if(join.getLevelList().get(player.getUniqueId()) >= 10 + (10 * join.getHHMODList().get(player.getUniqueId()))) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * join.getHHMODList().get(player.getUniqueId()))) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 * join.getHHMODList().get(player.getUniqueId())));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									join.getHHMODList().put(player.getUniqueId(), join.getHHMODList().get(player.getUniqueId()) + 1);
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
					if(join.getDFMODList().get(player.getUniqueId()) < 5) {
						if(join.getDFList().get(player.getUniqueId()) >= 20 + (20 * join.getDFMODList().get(player.getUniqueId()))) {
							if(join.getLevelList().get(player.getUniqueId()) >= 10 + (10 * join.getDFMODList().get(player.getUniqueId()))) {
								if(money.getMoneyList().get(player.getUniqueId()) > 10000.00 + (10000.00 * join.getDFMODList().get(player.getUniqueId()))) {
									double moneyNow = money.getMoneyList().get(player.getUniqueId()) - (10000.00 + (10000.00 * join.getDFMODList().get(player.getUniqueId())));
									money.getMoneyList().put(player.getUniqueId(), moneyNow);
									join.getDFMODList().put(player.getUniqueId(), join.getDFMODList().get(player.getUniqueId()) + 1);
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