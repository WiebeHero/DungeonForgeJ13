package Skills;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;

import Skills.SkillEnum.Skills;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class EffectSkills implements Listener{
	PlayerClass pc = new PlayerClass();
	HashMap<UUID, Float> arrowList = new HashMap<UUID, Float>();
	@EventHandler
	public void attackDamage(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			if(event.getEntity() instanceof LivingEntity) {
				event.setDamage(event.getFinalDamage() / 100 * (pc.getCalculation(damager.getUniqueId(), Skills.ATTACK_DAMAGE_CALC) + 100.00 + pc.getCalculation(damager.getUniqueId(), Skills.ATTACK_DAMAGE_EXTRA)));
			}
		}
	}
	public void attackSpeed(Player p) {
		new BukkitRunnable() {
			public void run() {
				ItemStack item = p.getInventory().getItemInMainHand();
				if(item != null) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasLore()) {
							if(item.getItemMeta().getLore().toString().contains("Attack Speed")) {
								String check1 = "";
								String check2 = "";
								for(String s : item.getItemMeta().getLore()) {
									if(s.contains("Attack Speed")) {
										check1 = ChatColor.stripColor(s);
									}
									else if(s.contains("Attack Damage")) {
										check2 = ChatColor.stripColor(s);
									}
								}
								check1 = check1.replaceAll("[^\\d.]", "");
								double attackSpeed = Double.parseDouble(check1);
								check2 = check2.replaceAll("[^\\d.]", "");
								double attackDamage = Double.parseDouble(check2);
								p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(attackDamage);
								p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed / 100 * (pc.getCalculation(p.getUniqueId(), Skills.ATTACK_SPEED_CALC) + 100.00 + pc.getCalculation(p.getUniqueId(), Skills.ATTACK_SPEED_EXTRA)));
							}
							else {
								p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
								p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
							}
						}
						else {
							p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
							p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
						}
					}
					else {
						p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
						p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
					}
				}
				else {
					p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
					p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
				}
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 1L);
	}
	@EventHandler
	public void attackSpeedItemChange(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		attackSpeed(player);
	}
	@EventHandler
	public void attackSpeedDeath(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		new BukkitRunnable() {
			public void run() {
				attackSpeed(player);
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 1L);
	}
	@EventHandler
	public void attackSpeedItemDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		attackSpeed(player);
	}
	@EventHandler
	public void attackSpeedItemPickup(PlayerAttemptPickupItemEvent event) {
		Player player = event.getPlayer();
		attackSpeed(player);
	}
	@EventHandler
	public void attackSpeedInvClick(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			attackSpeed(player);
		}
	}
	@EventHandler
	public void attackSpeedSwitchHand(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		attackSpeed(player);
	}
	@EventHandler
	public void criticalChance(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Player) {
				Player damager = (Player) event.getDamager();
				if(event.getEntity() instanceof LivingEntity) {
					float i = ThreadLocalRandom.current().nextFloat() * 100;
					if(i <= pc.getCalculation(damager.getUniqueId(), Skills.CRITICAL_CHANCE_CALC) + pc.getCalculation(damager.getUniqueId(), Skills.CRITICAL_CHANCE_EXTRA)) {
						event.setDamage(event.getFinalDamage() * 2);
					}
				}
			}
		}
	}
	@EventHandler
	public void rangedDamage(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow) event.getDamager();
				if(arrow.getShooter() != null && arrow.getShooter() instanceof Player) {
					Player damager = (Player) arrow.getShooter();
					if(arrowList.containsKey(arrow.getUniqueId())) {
						event.setDamage(event.getFinalDamage() / 100 * (pc.getCalculation(damager.getUniqueId(), Skills.RANGED_DAMAGE_CALC) + 100.00 + pc.getCalculation(damager.getUniqueId(), Skills.RANGED_DAMAGE_EXTRA)) * arrowList.get(arrow.getUniqueId()));
						arrowList.remove(arrow.getUniqueId());
					}
				}
			}
		}
	}
	@EventHandler
	public void arrowShoot(EntityShootBowEvent event) {
		if(!event.isCancelled()) {
			if(event.getEntity() instanceof Player) {
				arrowList.put(event.getProjectile().getUniqueId(), event.getForce());
			}
		}
	}
	public void changeHealth(Player p) {
		double newHealth = 20 / 100 * (pc.getCalculation(p.getUniqueId(), Skills.MAX_HEALTH_CALC) + 100.00 + pc.getCalculation(p.getUniqueId(), Skills.MAX_HEALTH_EXTRA));
		double roundOff1 = (double) Math.round(newHealth * 100) / 100;
		p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(roundOff1);
	}
	public void runDefense(Player p) {
		new BukkitRunnable() {
			public void run() {
				double armorD = 0.0;
				double armorT = 0.0;
				for(ItemStack item : p.getInventory().getArmorContents()) {
					if(item != null) {
						if(item.hasItemMeta()) {
							if(item.getItemMeta().hasLore()) {
								if(item.getItemMeta().getLore().toString().contains("Armor Defense") && item.getItemMeta().getLore().toString().contains("Armor Toughness")) {
									String check1 = "";
									String check2 = "";
									for(String s : item.getItemMeta().getLore()) {
										if(s.contains("Armor Defense")) {
											check1 = ChatColor.stripColor(s);
										}
										else if(s.contains("Armor Toughness")){
											check2 = ChatColor.stripColor(s);
										}
									}
									check1 = check1.replaceAll("[^\\d.]", "");
									check2 = check2.replaceAll("[^\\d.]", "");
									double armorDT = Double.parseDouble(check1);
									double armorTT = Double.parseDouble(check2);
									armorD = armorD + armorDT / 100 * (pc.getCalculation(p.getUniqueId(), Skills.ARMOR_DEFENSE_CALC) + 100.00 + pc.getCalculation(p.getUniqueId(), Skills.ARMOR_DEFENSE_EXTRA));
									armorT = armorT + armorTT / 100 * (pc.getCalculation(p.getUniqueId(), Skills.ARMOR_DEFENSE_CALC) + 100.00 + pc.getCalculation(p.getUniqueId(), Skills.ARMOR_DEFENSE_EXTRA));
									
								}
							}
						}
					}
				}
				ItemStack item = p.getInventory().getItemInOffHand();
				if(item != null) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasLore()) {
							if(item.getItemMeta().getLore().toString().contains("Armor Toughness")) {
								String check1 = "";
								for(String s : item.getItemMeta().getLore()) {
									if(s.contains("Armor Toughness")) {
										check1 = ChatColor.stripColor(s);
									}
								}
								check1 = check1.replaceAll("[^\\d.]", "");
								double armorTT = Double.parseDouble(check1);
								armorT = armorT + armorTT / 100.00 * (pc.getCalculation(p.getUniqueId(), Skills.ARMOR_DEFENSE_CALC) + 100.00 + pc.getCalculation(p.getUniqueId(), Skills.ARMOR_DEFENSE_EXTRA));
							}
						}
					}
				}
				p.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(armorD);
				p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(armorT);
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 1L);
	}
	@EventHandler
	public void armorSwitch(PlayerArmorChangeEvent event) {
		Player player = event.getPlayer();
		runDefense(player);
	}
}
