package me.WiebeHero.Skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Arrow.PickupStatus;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
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

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.MoreStuff.SwordSwingProgress;
import net.md_5.bungee.api.ChatColor;

public class EffectSkills implements Listener{
	public static HashMap<UUID, Float> arrowList = new HashMap<UUID, Float>();
	public static HashMap<UUID, Double> arrowDamage = new HashMap<UUID, Double>();
	public static ArrayList<UUID> disableBow = new ArrayList<UUID>();
	public ArrayList<UUID> disableShieldM = new ArrayList<UUID>();
	public SwordSwingProgress s = new SwordSwingProgress();
	@EventHandler
	public void attackDamage(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity) {
			LivingEntity damager = (LivingEntity) event.getDamager();
			DFPlayer dfPlayer = new DFPlayer().getPlayer(damager);
			if(damager.getEquipment().getItemInMainHand() != null) {
				if(damager.getEquipment().getItemInMainHand().getType() != Material.BOW) {
					if(event.getEntity() instanceof LivingEntity) {
						event.setDamage(event.getFinalDamage() / 100.00 * (dfPlayer.getAtkCal() + 100.00));
					}
				}
			}
			else {
				event.setDamage(event.getFinalDamage() / 100.00 * (dfPlayer.getAtkCal() + 100.00));
			}
		}
	}
	public void attackSpeed(LivingEntity p) {
		new BukkitRunnable() {
			public void run() {
				//-------------------------------------------------------
				//Player Attack Speed Handler
				//-------------------------------------------------------]
				DFPlayer dfPlayer = new DFPlayer().getPlayer(p);
				ItemStack item = p.getEquipment().getItemInMainHand();
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
									if(p instanceof Player) {
										p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed / 100.00 * (dfPlayer.getSpdCal() + 100.00));
									}
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
									if(p instanceof Player) {
										p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed / 100.00 * (dfPlayer.getSpdCal() + 100.00));
									}
								}
							}
							else {
								p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
								if(p instanceof Player) {
									p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
								}
							}
						}
						else {
							p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
							if(p instanceof Player) {
								p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
							}
						}
					}
					else {
						p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
						if(p instanceof Player) {
							p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
						}
					}
				}
				else {
					p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
					if(p instanceof Player) {
						p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
					}
				}
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 1L);
	}
	//-------------------------------------------------------
	//Player Attack Speed Event Handler
	//-------------------------------------------------------
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
	//-------------------------------------------------------
	//Player Shield Handler
	//-------------------------------------------------------
	@EventHandler
	public void shieldDisablePlayer(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(player.getInventory().getItemInOffHand() != null) {
				if(player.getInventory().getItemInOffHand().getType() == Material.SHIELD) {
					if(player.getInventory().getItemInOffHand().hasItemMeta()) {
						if(player.getInventory().getItemInOffHand().getItemMeta().hasLore()) {
							if(player.isBlocking()) {
								DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
								String cd = "";
								for(String s : player.getInventory().getItemInOffHand().getItemMeta().getLore()) {
									if(s.contains("Cooldown:")) {
										cd = ChatColor.stripColor(s);
									}
								}
								cd = cd.replaceAll("[^\\d.]", "");
								double cooldownInSec = Double.parseDouble(cd);
								int cooldownTime = (int)cooldownInSec * 20;
								cooldownTime = (int) (cooldownTime - (cooldownTime / 100 * dfPlayer.getSpdCal()));
								player.setCooldown(Material.SHIELD, cooldownTime);
							}
						}
					}
				}
			}
			else if(player.getInventory().getItemInMainHand() != null) {
				if(player.getInventory().getItemInMainHand().getType() == Material.SHIELD) {
					if(player.getInventory().getItemInMainHand().hasItemMeta()) {
						if(player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
							if(player.isBlocking()) {
								String cd = "";
								DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
								for(String s : player.getInventory().getItemInMainHand().getItemMeta().getLore()) {
									if(s.contains("Cooldown:")) {
										cd = ChatColor.stripColor(s);
									}
								}
								cd = cd.replaceAll("[^\\d.]", "");
								double cooldownInSec = Double.parseDouble(cd);
								int cooldownTime = (int)cooldownInSec * 20;
								cooldownTime = (int) (cooldownTime - (cooldownTime / 100 * dfPlayer.getSpdCal()));
								player.setCooldown(Material.SHIELD, cooldownTime);
							}
						}
					}
				}
			}
		}
	}
	//-------------------------------------------------------
	//Entity Shield Handler
	//-------------------------------------------------------
	@EventHandler
	public void shieldDisableEntity(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			LivingEntity ent = (LivingEntity) event.getEntity();
			if(ent.getEquipment().getItemInOffHand() != null) {
				if(ent.getEquipment().getItemInOffHand().getType() == Material.SHIELD) {
					if(ent.getEquipment().getItemInOffHand().hasItemMeta()) {
						if(ent.getEquipment().getItemInOffHand().getItemMeta().hasLore()) {
							if(!disableShieldM.contains(ent.getUniqueId())) {
								DFPlayer dfPlayer = new DFPlayer().getPlayer(ent);
								event.setDamage(0.00);
								String cd = "";
								for(String s : ent.getEquipment().getItemInOffHand().getItemMeta().getLore()) {
									if(s.contains("Cooldown:")) {
										cd = ChatColor.stripColor(s);
									}
								}
								cd = cd.replaceAll("[^\\d.]", "");
								double cooldownInSec = Double.parseDouble(cd);
								int cooldownTime = (int)cooldownInSec * 20;
								cooldownTime = (int) (cooldownTime - (cooldownTime / 100 * dfPlayer.getSpdCal()));
								disableShieldM.add(ent.getUniqueId());
								new BukkitRunnable() {
									public void run() {
										disableShieldM.remove(ent.getUniqueId());
									}
								}.runTaskLater(CustomEnchantments.getInstance(), (long)cooldownTime);
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
			if(event.getDamager() instanceof LivingEntity) {
				LivingEntity damager = (LivingEntity) event.getDamager();
				DFPlayer dfPlayer = new DFPlayer().getPlayer(damager);
				if(event.getEntity() instanceof LivingEntity) {
					float i = ThreadLocalRandom.current().nextFloat() * 100;
					if(i <= dfPlayer.getCrtCal()) {
						double critDmg = 2.0;
						if(dfPlayer.getCrtCal() > 100) {
							double temp = dfPlayer.getCrtCal() - 100;
							critDmg = critDmg + (temp / 100);
						}
						else if(dfPlayer.getCrtCal() < 0) {
							critDmg = critDmg - (dfPlayer.getCrt() / 100);
						}
						event.setDamage(event.getFinalDamage() * critDmg);
					}
				}
			}
		}
	}
	@EventHandler
	public void bowShootCustom(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(!disableBow.contains(player.getUniqueId())) {
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
								}.runTaskLater(CustomEnchantments.getInstance(), 2L);
								Location loc = player.getLocation();
								loc.add(0, 1.57, 0);
								Vector direction = loc.getDirection();
								direction.add(direction);
								direction.setY(direction.getY() + 0.15);
								direction.normalize();
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
	}
	@EventHandler
	public void shootBowMob(EntityShootBowEvent event) {
		if(event.getEntity() instanceof Monster) {
			Monster ent = (Monster) event.getEntity();
			arrowList.put(ent.getUniqueId(), event.getForce());
			ItemStack item = ent.getEquipment().getItemInMainHand();
			if(item != null) {
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
							arrowDamage.put(event.getProjectile().getUniqueId(), attackDamage);
						}
					}
					else {
						arrowDamage.put(event.getProjectile().getUniqueId(), 4.0);
					}
				}
				else {
					arrowDamage.put(event.getProjectile().getUniqueId(), 4.0);
				}
			}
			else {
				arrowDamage.put(event.getProjectile().getUniqueId(), 4.0);
			}
		}
	}
	@EventHandler
	public void rangedDamage(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow) event.getDamager();
				if(arrow.getShooter() != null && arrow.getShooter() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
					LivingEntity damager = (LivingEntity) arrow.getShooter();
					DFPlayer df = new DFPlayer();
					if(df.containsPlayer(damager)) {
						DFPlayer dfPlayer = new DFPlayer().getPlayer(damager);
						if(arrowList.containsKey(arrow.getUniqueId()) && arrowDamage.containsKey(arrow.getUniqueId())) {
							double damage = arrowDamage.get(arrow.getUniqueId());
							event.setDamage(damage / 100.00 * (dfPlayer.getRndCal() + 100.00) * arrowList.get(arrow.getUniqueId()));
							arrowList.remove(arrow.getUniqueId());
						}
					}
				}
			}
		}
	}
	public void changeHealth(LivingEntity p) {
		DFPlayer dfPlayer = new DFPlayer().getPlayer(p);
		double baseHealth = 20.00;
		if(p instanceof Player) {
			baseHealth = 20.00;
		}
		else {
			baseHealth = 25.00;
		}
		double newHealth = baseHealth / 100.00 * (dfPlayer.getHpCal() + 100.00);
		double roundOff1 = (double) Math.round(newHealth * 100.00) / 100.00;
		p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(roundOff1);
	}
	public void runDefense(LivingEntity p) {
		new BukkitRunnable() {
			public void run() {
				DFPlayer dfPlayer = new DFPlayer().getPlayer(p);
				double armorD = 0.0;
				double armorT = 0.0;
				for(ItemStack item : p.getEquipment().getArmorContents()) {
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
									armorD = armorD + armorDT / 100.00 * (dfPlayer.getDfCal() + 100.00);
									armorT = armorT + armorTT / 100.00 * (dfPlayer.getDfCal() + 100.00);
								}
							}
						}
					}
				}
				ItemStack item = p.getEquipment().getItemInOffHand();
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
								armorT = armorT + armorTT / 100.00 * (dfPlayer.getDfCal() + 100.00);
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
	@EventHandler
	public void extraDefense(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getEntity() instanceof LivingEntity) {
				LivingEntity player = (LivingEntity) event.getEntity();
				DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
				if(dfPlayer.getDfCal() > 0) {
					double armor = player.getAttribute(Attribute.GENERIC_ARMOR).getValue();
					double toughness = player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue();
					double total = 0;
					if(armor > 30) {
						total = total + (armor - 30);
					}
					if(toughness > 20) {
						total = total + (toughness - 20);
					}
					total = total * 1.5;
					event.setDamage(event.getDamage() / 100.00 * (100.00 - total));
				}
				else if(dfPlayer.getDfCal() < 0) {
					event.setDamage(event.getDamage() / 100.00 * (100.00 + Math.abs(dfPlayer.getDfCal())));
				}
			}
		}
	}
}
