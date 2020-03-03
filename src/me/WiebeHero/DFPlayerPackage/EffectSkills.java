package me.WiebeHero.DFPlayerPackage;

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
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomEvents.DFShootBowEvent;
import me.WiebeHero.MoreStuff.SwordSwingProgress;
import net.md_5.bungee.api.ChatColor;

public class EffectSkills implements Listener{
	public static HashMap<UUID, Float> arrowList = new HashMap<UUID, Float>();
	public static HashMap<UUID, Double> arrowDamage = new HashMap<UUID, Double>();
	public static ArrayList<UUID> disableBow = new ArrayList<UUID>();
	private ArrayList<UUID> disableShieldM = new ArrayList<UUID>();
	private SwordSwingProgress s;
	private DFPlayerManager dfManager;
	public EffectSkills(DFPlayerManager dfManager, SwordSwingProgress s) {
		this.dfManager = dfManager;
		this.s = s;
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void nerfStrength(EntityDamageByEntityEvent event) {
		if(event.getCause() == DamageCause.ENTITY_ATTACK) {
			if(event.getDamager() instanceof LivingEntity) {
				LivingEntity damager = (LivingEntity) event.getDamager();
				double damage = event.getDamage();
				if(damager.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
					int amp = damager.getPotionEffect(PotionEffectType.INCREASE_DAMAGE).getAmplifier();
					damage = damage - ((amp + 1) * 2.25);
				}
				event.setDamage(damage);
			}
		}
		else if(event.getCause() == DamageCause.PROJECTILE) {
			if(event.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow) event.getDamager();
				if(arrow.getShooter() instanceof LivingEntity) {
					LivingEntity damager = (LivingEntity) arrow.getShooter();
					double damage = event.getDamage();
					if(damager.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
						int amp = damager.getPotionEffect(PotionEffectType.INCREASE_DAMAGE).getAmplifier();
						damage = damage + 0.75 * (amp + 1);
					}
					event.setDamage(damage);
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void attackDamage(EntityDamageByEntityEvent event) {
		if(event.getCause() == DamageCause.ENTITY_ATTACK) {
			if(event.getDamager() instanceof LivingEntity) {
				LivingEntity damager = (LivingEntity) event.getDamager();
				double damage = event.getDamage();
				if(dfManager.contains(damager)) {
					DFPlayer dfPlayer = dfManager.getEntity(damager);
					if(damager.getEquipment().getItemInMainHand() != null) {
						if(damager.getEquipment().getItemInMainHand().getType() != Material.BOW) {
							if(event.getEntity() instanceof LivingEntity) {
								event.setDamage((damage) / 100.00 * (dfPlayer.getAtkCal() + 100.00));
							}
						}
					}
					else {
						event.setDamage((damage) / 100.00 * (dfPlayer.getAtkCal() + 100.00));
					}
				}
			}
		}
	}
	public void attackSpeed(LivingEntity p) {
		new BukkitRunnable() {
			public void run() {
				//-------------------------------------------------------
				//Player Attack Speed Handler
				//-------------------------------------------------------
				if(dfManager.contains(p)) {
					DFPlayer dfPlayer = dfManager.getEntity(p);
					ItemStack item = p.getEquipment().getItemInMainHand();
					if(item != null) {
						if(item.hasItemMeta()) {
							if(item.getItemMeta().hasLore()) {
								if(ChatColor.stripColor(item.getItemMeta().getLore().toString()).contains("Attack Speed:")) {
									if(item.getType() != Material.BOW) {
										String check1 = "";
										String check2 = "";
										for(String s : item.getItemMeta().getLore()) {
											if(ChatColor.stripColor(s).contains("Attack Speed:")) {
												check1 = ChatColor.stripColor(s);
											}
											else if(ChatColor.stripColor(s).contains("Attack Damage:")) {
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
		}.runTaskLater(CustomEnchantments.getInstance(), 4L);
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
	public void attackSpeedItemPickup(CraftItemEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			attackSpeed(player);
		}
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
			if(dfManager.contains(player)) {
				if(player.getInventory().getItemInOffHand() != null) {
					if(player.getInventory().getItemInOffHand().getType() == Material.SHIELD) {
						if(player.getInventory().getItemInOffHand().hasItemMeta()) {
							if(player.getInventory().getItemInOffHand().getItemMeta().hasLore()) {
								if(player.isBlocking()) {
									DFPlayer dfPlayer = dfManager.getEntity(player);
									String cd = "";
									for(String s : player.getInventory().getItemInOffHand().getItemMeta().getLore()) {
										if(s.contains("Cooldown:")) {
											cd = ChatColor.stripColor(s);
										}
									}
									cd = cd.replaceAll("[^\\d.]", "");
									double cooldownInSec = Double.parseDouble(cd);
									int cooldownTime = (int)cooldownInSec * 20;
									cooldownTime = (int) (cooldownTime - (cooldownTime / 100.00 * dfPlayer.getSpdCal()));
									ItemStack item = player.getEquipment().getItemInOffHand();
									player.setCooldown(Material.SHIELD, cooldownTime);
									player.getEquipment().setItemInOffHand(item);
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
									DFPlayer dfPlayer = dfManager.getEntity(player);
									for(String s : player.getInventory().getItemInMainHand().getItemMeta().getLore()) {
										if(s.contains("Cooldown:")) {
											cd = ChatColor.stripColor(s);
										}
									}
									cd = cd.replaceAll("[^\\d.]", "");
									double cooldownInSec = Double.parseDouble(cd);
									int cooldownTime = (int)cooldownInSec * 20;
									cooldownTime = (int) (cooldownTime - (cooldownTime / 100.00 * dfPlayer.getSpdCal()));
									ItemStack item = player.getEquipment().getItemInMainHand();
									player.setCooldown(Material.SHIELD, cooldownTime);
									player.getEquipment().setItemInMainHand(item);
								}
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
		if(event.getEntity() instanceof LivingEntity && !(event.getEntity() instanceof Player)) {
			LivingEntity ent = (LivingEntity) event.getEntity();
			if(dfManager.contains(ent)) {
				if(ent.getEquipment().getItemInOffHand() != null) {
					if(ent.getEquipment().getItemInOffHand().getType() == Material.SHIELD) {
						if(ent.getEquipment().getItemInOffHand().hasItemMeta()) {
							if(ent.getEquipment().getItemInOffHand().getItemMeta().hasLore()) {
								if(!disableShieldM.contains(ent.getUniqueId())) {
									DFPlayer dfPlayer = dfManager.getEntity(ent);
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
									cooldownTime = (int) (cooldownTime - (cooldownTime / 100.00 * dfPlayer.getSpdCal()));
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
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void criticalChance(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof LivingEntity) {
				LivingEntity damager = (LivingEntity) event.getDamager();
				if(dfManager.contains(damager)) {
					DFPlayer dfPlayer = dfManager.getEntity(damager);
					if(event.getEntity() instanceof LivingEntity) {
						float i = ThreadLocalRandom.current().nextFloat() * 100;
						double chance = Math.abs(dfPlayer.getCrtCal());
						if(i <= chance) {
							double critDmg = 2.0;
							if(dfPlayer.getCrtCal() > 100.00) {
								double temp = dfPlayer.getCrtCal() - 100.00;
								critDmg = critDmg + (temp / 100.00);
							}
							event.setDamage(event.getDamage() * critDmg);
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void bowShootCustom(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(dfManager.contains(player)) {
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
									ItemStack item = player.getInventory().getItemInMainHand();
//									for(int i1 = 0; i1 < 8; i1++) {
//										if(i1 != i) {
//											if(player.getInventory().getItem(i1) != null) {
//												if(player.getInventory().getItem(i1).getType() == Material.BOW) {
//													continue;
//												}
//												else {
//													player.getInventory().setHeldItemSlot(i1);
//													player.updateInventory();
//													break;
//												}
//											}
//											else {
//												player.getInventory().setHeldItemSlot(i1);
//												player.updateInventory();
//												break;
//											}
//										}
//									}
//									new BukkitRunnable() {
//										public void run() {
//											player.getInventory().setHeldItemSlot(i);
//											player.updateInventory();
//										}
//									}.runTaskLater(CustomEnchantments.getInstance(), 2L);
									Location loc = player.getLocation();
									loc.add(0, 1.40, 0);
									Vector direction = loc.getDirection();
									direction.add(direction);
									direction.setY(direction.getY());
									direction.normalize();
									Arrow arrow = player.getWorld().spawnArrow(loc, direction, s.getAttackStrength(player) * 2.55F, 0.0F);
									arrow.setPickupStatus(PickupStatus.ALLOWED);
									if(s.getAttackStrength(player) == 1.00) {
										arrow.setCritical(true);
									}
									arrow.setShooter(player);
									arrow.setMetadata("AttackStrength", new FixedMetadataValue(CustomEnchantments.getInstance(), s.getAttackStrength(player)));
									arrowList.put(arrow.getUniqueId(), s.getAttackStrength(player));
									player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
									this.attackSpeed(player);
									EffectSkills sk = this;
									new BukkitRunnable() {
										public void run() {
											player.getInventory().setItemInMainHand(item);
											sk.attackSpeed(player);
										}
									}.runTaskLater(CustomEnchantments.getInstance(), 0L);
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
												DFShootBowEvent ev = new DFShootBowEvent(player, item, arrow);
												Bukkit.getServer().getPluginManager().callEvent(ev);
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
			if(event.getCause() == DamageCause.PROJECTILE) {
				if(event.getDamager() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getDamager();
					if(arrow.getShooter() != null && arrow.getShooter() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
						LivingEntity damager = (LivingEntity) arrow.getShooter();
						if(dfManager.contains(damager)) {
							DFPlayer dfPlayer = dfManager.getEntity(damager);
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
	}
	public void changeHealth(LivingEntity p) {
		if(dfManager.contains(p)) {
			DFPlayer dfPlayer = dfManager.getEntity(p);
			double baseHealth = 20.00;
			if(p instanceof Player) {
				baseHealth = 20.00;
			}
			else {
				baseHealth = 25.00;
			}
			double newHealth = baseHealth;
			newHealth = baseHealth / 100.00 * (dfPlayer.getHpCal() + 100.00);
			double roundOff1 = (double) Math.round(newHealth * 100.00) / 100.00;
			p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(roundOff1);
		}
	}
	public void runDefense(LivingEntity p) {
		new BukkitRunnable() {
			public void run() {
				if(dfManager.contains(p)) {
					DFPlayer dfPlayer = dfManager.getEntity(p);
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
				if(dfManager.contains(player)) {
					DFPlayer dfPlayer = dfManager.getEntity(player);
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
						total = total / 1.35;
						event.setDamage(event.getDamage() / 100.00 * (100.00 - total));
					}
				}
			}
		}
	}
}