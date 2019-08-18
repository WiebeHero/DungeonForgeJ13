package Skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Arrow.PickupStatus;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;

import NeededStuff.SwordSwingProgress;
import Skills.SkillEnum.Skills;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class EffectSkills implements Listener{
	PlayerClass pc = new PlayerClass();
	HashMap<UUID, Float> arrowList = new HashMap<UUID, Float>();
	HashMap<UUID, Double> arrowDamage = new HashMap<UUID, Double>();
	public SwordSwingProgress s = new SwordSwingProgress();
	@EventHandler
	public void attackDamage(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			if(damager.getInventory().getItemInMainHand() != null) {
				if(damager.getInventory().getItemInMainHand().getType() != Material.BOW) {
					if(event.getEntity() instanceof LivingEntity) {
						event.setDamage(event.getFinalDamage() / 100.00 * (pc.getCalculation(damager.getUniqueId(), Skills.ATTACK_DAMAGE_CALC) + 100.00 + pc.getCalculation(damager.getUniqueId(), Skills.ATTACK_DAMAGE_EXTRA)));
					}
				}
			}
			else {
				event.setDamage(event.getFinalDamage() / 100.00 * (pc.getCalculation(damager.getUniqueId(), Skills.ATTACK_DAMAGE_CALC) + 100.00 + pc.getCalculation(damager.getUniqueId(), Skills.ATTACK_DAMAGE_EXTRA)));
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
								if(item.getType() != Material.BOW) {
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
									p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed / 100.00 * (pc.getCalculation(p.getUniqueId(), Skills.ATTACK_SPEED_CALC) + 100.00 + pc.getCalculation(p.getUniqueId(), Skills.ATTACK_SPEED_EXTRA)));
								}
								else {
									String check1 = "";
									for(String s : item.getItemMeta().getLore()) {
										if(s.contains("Attack Speed")) {
											check1 = ChatColor.stripColor(s);
										}
									}
									check1 = check1.replaceAll("[^\\d.]", "");
									double attackSpeed = Double.parseDouble(check1);
									p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
									p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed / 100.00 * (pc.getCalculation(p.getUniqueId(), Skills.ATTACK_SPEED_CALC) + 100.00 + pc.getCalculation(p.getUniqueId(), Skills.ATTACK_SPEED_EXTRA)));
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
	public void shieldDisable(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(player.getInventory().getItemInOffHand() != null) {
				if(player.getInventory().getItemInOffHand().getType() == Material.SHIELD) {
					if(player.getInventory().getItemInOffHand().hasItemMeta()) {
						if(player.getInventory().getItemInOffHand().getItemMeta().hasLore()) {
							if(player.isBlocking()) {
								String cd = "";
								for(String s : player.getInventory().getItemInOffHand().getItemMeta().getLore()) {
									if(s.contains("Cooldown:")) {
										cd = ChatColor.stripColor(s);
									}
								}
								cd = cd.replaceAll("[^\\d.]", "");
								double cooldownInSec = Double.parseDouble(cd);
								int cooldownTime = (int)cooldownInSec * 20;
								ItemStack item = player.getInventory().getItemInOffHand();
								player.getInventory().setItemInOffHand(new ItemStack(Material.AIR, 1));
								player.updateInventory();
								new BukkitRunnable() {
									public void run() {
										player.getInventory().setItemInOffHand(item);
										player.updateInventory();
									}
								}.runTaskLater(CustomEnchantments.getInstance(), 0L);
								player.setCooldown(Material.SHIELD, cooldownTime);
							}
						}
					}
				}
			}
		}
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
	public void bowShootCustom(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(player.getInventory().getItemInMainHand() != null) {
			if(player.getInventory().getItemInMainHand().getType() == Material.BOW) {
				if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					event.setCancelled(true);
					if(player.getInventory().contains(Material.ARROW)) {
						if(s.getAttackStrength(player) > 0.35) {
							for(int i = 0; i <= 35; i++) {
								if(player.getInventory().getItem(i) != null) {
									if(player.getInventory().getItem(i).getType() == Material.ARROW) {
										player.getInventory().getItem(i).setAmount(player.getInventory().getItem(i).getAmount() - 1);
										break;
									}
								}
							}
							int i = player.getInventory().getHeldItemSlot();
							ItemStack item = player.getInventory().getItemInMainHand();
							for(int i1 = 0; i1 < 8; i1++) {
								if(i1 != i) {
									if(player.getInventory().getItem(i1) != null) {
										if(player.getInventory().getItem(i1).getType() == Material.BOW) {
											continue;
										}
										else {
											player.getInventory().setHeldItemSlot(i1);
											player.updateInventory();
											break;
										}
									}
									else {
										player.getInventory().setHeldItemSlot(i1);
										player.updateInventory();
										break;
									}
								}
							}
							new BukkitRunnable() {
								public void run() {
									player.getInventory().setHeldItemSlot(i);
									player.updateInventory();
								}
							}.runTaskLater(CustomEnchantments.getInstance(), 1L);
							Location loc = player.getLocation();
							loc.add(0, 1.75, 0);
							Vector direction = loc.getDirection();
							direction.add(direction);
							Arrow arrow = player.getWorld().spawnArrow(loc, direction, s.getAttackStrength(player) * 2.10F, 0.0F);
							arrow.setPickupStatus(PickupStatus.ALLOWED);
							if(s.getAttackStrength(player) == 1.00) {
								arrow.setCritical(true);
							}
							arrow.setShooter(player);
							arrow.setCustomName(s.getAttackStrength(player) + "");
							arrowList.put(arrow.getUniqueId(), s.getAttackStrength(player));
							if(item.hasItemMeta()) {
								ItemMeta meta = item.getItemMeta();
								if(meta.hasLore()) {
									ArrayList<String> lore = (ArrayList<String>) meta.getLore();
									if(lore.toString().contains("Attack Damage:")) {
										String check1 = "";
										for(String s : item.getItemMeta().getLore()) {
											if(s.contains("Attack Damage:")) {
												check1 = ChatColor.stripColor(s);
											}
										}
										check1 = check1.replaceAll("[^\\d.]", "");
										double attackDamage = Double.parseDouble(check1);
										arrowDamage.put(arrow.getUniqueId(), attackDamage);
									}
								}
							}
						}
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
				if(arrow.getShooter() != null && arrow.getShooter() instanceof Player && event.getEntity() instanceof LivingEntity) {
					Player damager = (Player) arrow.getShooter();
					if(arrowList.containsKey(arrow.getUniqueId())) {
						double damage = arrowDamage.get(arrow.getUniqueId());
						event.setDamage(damage / 100.00 * (pc.getCalculation(damager.getUniqueId(), Skills.RANGED_DAMAGE_CALC) + 100.00 + pc.getCalculation(damager.getUniqueId(), Skills.RANGED_DAMAGE_EXTRA)) * arrowList.get(arrow.getUniqueId()));
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
		double newHealth = 20.00 / 100.00 * (pc.getCalculation(p.getUniqueId(), Skills.MAX_HEALTH_CALC) + 100.00 + pc.getCalculation(p.getUniqueId(), Skills.MAX_HEALTH_EXTRA));
		double roundOff1 = (double) Math.round(newHealth * 100.00) / 100.00;
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
									armorD = armorD + armorDT / 100.00 * (pc.getCalculation(p.getUniqueId(), Skills.ARMOR_DEFENSE_CALC) + 100.00 + pc.getCalculation(p.getUniqueId(), Skills.ARMOR_DEFENSE_EXTRA));
									armorT = armorT + armorTT / 100.00 * (pc.getCalculation(p.getUniqueId(), Skills.ARMOR_DEFENSE_CALC) + 100.00 + pc.getCalculation(p.getUniqueId(), Skills.ARMOR_DEFENSE_EXTRA));
									
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
