package me.WiebeHero.DFPlayerPackage;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;

import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomEvents.DFShootBowEvent;
import me.WiebeHero.MoreStuff.SwordSwingProgress;
import net.md_5.bungee.api.ChatColor;

public class EffectSkills implements Listener{
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
			if(event.getDamager() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
				LivingEntity damager = (LivingEntity) event.getDamager();
				LivingEntity victim = (LivingEntity) event.getEntity();
				double damage = event.getDamage();
				if(damager.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
					int amp = damager.getPotionEffect(PotionEffectType.INCREASE_DAMAGE).getAmplifier();
					damage = damage - ((amp + 1) * 2.25);
				}
				if(damager.hasPotionEffect(PotionEffectType.WEAKNESS)) {
					int amp = damager.getPotionEffect(PotionEffectType.WEAKNESS).getAmplifier();
					damage = damage + ((amp + 1) * 3.25);
				}
				if(dfManager.contains(damager)) {
					DFPlayer dfPlayer = dfManager.getEntity(damager);
					new BukkitRunnable() {
						public void run() {
							victim.setNoDamageTicks((int)(20.00 - (dfPlayer.getSpdCal() / 12.50)));
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 1L);
				}
				event.setDamage(damage);
			}
		}
		else if(event.getCause() == DamageCause.PROJECTILE) {
			if(event.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow) event.getDamager();
				if(arrow.getShooter() instanceof LivingEntity  && event.getEntity() instanceof LivingEntity) {
					LivingEntity damager = (LivingEntity) arrow.getShooter();
					double damage = event.getDamage();
					if(damager.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
						int amp = damager.getPotionEffect(PotionEffectType.INCREASE_DAMAGE).getAmplifier();
						damage = damage - ((amp + 1) * 2.25);
					}
					if(damager.hasPotionEffect(PotionEffectType.WEAKNESS)) {
						int amp = damager.getPotionEffect(PotionEffectType.WEAKNESS).getAmplifier();
						damage = damage + ((amp + 1) * 3.25);
					}
					if(dfManager.contains(damager)) {
						DFPlayer dfPlayer = dfManager.getEntity(damager);
						LivingEntity victim = (LivingEntity) event.getEntity();
						new BukkitRunnable() {
							public void run() {
								victim.setNoDamageTicks((int)(20.00 - (dfPlayer.getSpdCal() / 12.50)));
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 1L);
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
	//-------------------------------------------------------
	//Player Attack Speed Event Handler
	//-------------------------------------------------------
	@EventHandler
	public void attackSpeedItemChange(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		if(dfManager.contains(player)) {
			DFPlayer dfPlayer = dfManager.getEntity(player);
			dfPlayer.attackSpeed();
		}
	}
	@EventHandler
	public void attackSpeedDeath(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		new BukkitRunnable() {
			public void run() {
				if(dfManager.contains(player)) {
					DFPlayer dfPlayer = dfManager.getEntity(player);
					dfPlayer.attackSpeed();
				}
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 4L);
	}
	@EventHandler
	public void attackSpeedItemDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if(dfManager.contains(player)) {
			DFPlayer dfPlayer = dfManager.getEntity(player);
			dfPlayer.attackSpeed();
		}
	}
	@EventHandler
	public void attackSpeedItemPickup(PlayerAttemptPickupItemEvent event) {
		Player player = event.getPlayer();
		if(dfManager.contains(player)) {
			DFPlayer dfPlayer = dfManager.getEntity(player);
			dfPlayer.attackSpeed();
		}
	}
	@EventHandler
	public void attackSpeedItemPickup(CraftItemEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			if(dfManager.contains(player)) {
				DFPlayer dfPlayer = dfManager.getEntity(player);
				dfPlayer.attackSpeed();
			}
		}
	}
	@EventHandler
	public void attackSpeedInvClick(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			if(dfManager.contains(player)) {
				DFPlayer dfPlayer = dfManager.getEntity(player);
				dfPlayer.attackSpeed();
			}
		}
	}
	@EventHandler
	public void attackSpeedSwitchHand(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(dfManager.contains(player)) {
			DFPlayer dfPlayer = dfManager.getEntity(player);
			dfPlayer.attackSpeed();
		}
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
			if(player.getInventory().getItemInMainHand() != null) {
				if(player.getInventory().getItemInMainHand().getType() == Material.BOW) {
					if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						event.setCancelled(true);
						if(player.getInventory().contains(Material.ARROW)) {
							if(s.getAttackStrength(player) > 0.35) {
								DFPlayer dfPlayer = dfManager.getEntity(player);
								for(int i = 0; i <= 35; i++) {
									if(player.getInventory().getItem(i) != null) {
										if(player.getInventory().getItem(i).getType() == Material.ARROW) {
											player.getInventory().getItem(i).setAmount(player.getInventory().getItem(i).getAmount() - 1);
											break;
										}
									}
								}
								DFShootBowEvent ev = new DFShootBowEvent(player, s.getAttackStrength(player), dfPlayer);
								Bukkit.getServer().getPluginManager().callEvent(ev);
								ev.shootArrow();
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
			if(event.getCause() == DamageCause.PROJECTILE) {
				if(event.getDamager() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getDamager();
					if(arrow.getShooter() != null && arrow.getShooter() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
						LivingEntity damager = (LivingEntity) arrow.getShooter();
						if(dfManager.contains(damager)) {
							DFPlayer dfPlayer = dfManager.getEntity(damager);
							double damage = arrow.getDamage();
							event.setDamage(damage / 100.00 * (dfPlayer.getRndCal() + 100.00));
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void armorSwitch(PlayerArmorChangeEvent event) {
		Player player = event.getPlayer();
		if(dfManager.contains(player)) {
			DFPlayer dfPlayer = dfManager.getEntity(player);
			dfPlayer.runDefense();
		}
	}
	@EventHandler
	public void extraDefense(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getEntity() instanceof LivingEntity) {
				LivingEntity player = (LivingEntity) event.getEntity();
				if(dfManager.contains(player)) {
					DFPlayer dfPlayer = dfManager.getEntity(player);
					if(dfPlayer.getDfCal() > 0) {
						Pair<Double, Double> pair = dfPlayer.getAbsoluteArmorPoints();
						double armor = pair.getKey();
						double toughness = pair.getValue();
						double total = 0;
						if(armor > 30) {
							total = total + (armor - 30);
						}
						if(toughness > 20) {
							total = total + (toughness - 20);
						}
						total = total / 1.35;
						double newTotal = 100.00 - total < 0 ? 0 : 100.00 - total;
						event.setDamage(event.getDamage() / 100.00 * (newTotal));
					}
				}
			}
		}
	}
}
