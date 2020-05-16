package me.WiebeHero.EnchantmentAPI;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEvents.DFShootBowEvent;
import me.WiebeHero.EnchantmentAPI.EnchantmentCondition.Condition;
import me.WiebeHero.MoreStuff.SwordSwingProgress;
import me.WiebeHero.Scoreboard.WGMethods;

public class EnchantmentHandler extends SwordSwingProgress{
	private Enchantment enchantment;
	private EnchantmentGuideInventory enchInv;
	private WGMethods wg;
	public EnchantmentHandler(Enchantment enchantment, EnchantmentGuideInventory enchInv, WGMethods wg) {
		this.enchantment = enchantment;
		this.enchInv = enchInv;
		this.wg = wg;
	}
	@EventHandler
	public void meleeEnchantmentHandler(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity){
			if(event.getEntity() instanceof LivingEntity) {
				if(!event.isCancelled()) {
					LivingEntity damager = (LivingEntity) event.getDamager();
					LivingEntity victim = (LivingEntity) event.getEntity();
					double d = 0.0;
					if(swordSwingProgress.containsKey(damager.getName())) {
						d = swordSwingProgress.get(damager.getName());
					}
					else {
						d = 1.0;
					}
					DamageCause damageCause = event.getCause();
					if(damageCause != null && damageCause == DamageCause.ENTITY_ATTACK || damageCause == DamageCause.ENTITY_SWEEP_ATTACK) {
						if(d == 1.0) {
							if(damager.getEquipment().getItemInMainHand() != null) {
								if(damager.getEquipment().getItemInMainHand().hasItemMeta()) {
									if(damager.getEquipment().getItemInMainHand().getItemMeta().hasLore()) {
										for(String s1 : damager.getEquipment().getItemInMainHand().getItemMeta().getLore()){
											String enchant = ChatColor.stripColor(s1);
											String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
											if(enchantment.getMeleeEnchantments().containsKey(check)) {
												if(enchantment.getMeleeEnchantments().get(check).getKey() == Condition.ENTITY_DAMAGE_BY_ENTITY) {
													if(!wg.isInZone(damager, "spawn")) {
														enchant = enchant.replaceAll("[^\\d.]", "");
														int level = Integer.parseInt(enchant) - 1;
														enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, level);
														enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
														enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, victim, level);
														enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, victim, level, event);
													}
													else {
														damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
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
		}
	}
	@EventHandler
	public void meleeKillEnchantmentHandler(EntityDeathEvent event) {
		if(event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof LivingEntity){
			if(event.getEntity() instanceof LivingEntity) {
				if(!event.isCancelled()) {
					LivingEntity damager = (LivingEntity) event.getEntity().getKiller();
					LivingEntity victim = (LivingEntity) event.getEntity();
					double d = 0.0;
					if(swordSwingProgress.containsKey(damager.getName())) {
						d = swordSwingProgress.get(damager.getName());
					}
					else {
						d = 1.0;
					}
					if(victim.getLastDamageCause() != null && victim.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK || victim.getLastDamageCause().getCause() == DamageCause.ENTITY_SWEEP_ATTACK) {
						if(d == 1.0) {
							if(damager.getEquipment().getItemInMainHand() != null) {
								if(damager.getEquipment().getItemInMainHand().hasItemMeta()) {
									if(damager.getEquipment().getItemInMainHand().getItemMeta().hasLore()) {
										for(String s1 : damager.getEquipment().getItemInMainHand().getItemMeta().getLore()){
											String enchant = ChatColor.stripColor(s1);
											String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
											if(enchantment.getMeleeEnchantments().containsKey(check)) {
												if(enchantment.getMeleeEnchantments().get(check).getKey() == Condition.ENTITY_DEATH_MELEE) {
													if(!wg.isInZone(damager, "spawn")) {
														enchant = enchant.replaceAll("[^\\d.]", "");
														int level = Integer.parseInt(enchant) - 1;
														enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, level);
														enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
														enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, victim, level);
														enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, victim, level, event);
													}
													else {
														damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
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
		}
	}
	@EventHandler
	public void meleeKillPlayerEnchantmentHandler(PlayerDeathEvent event) {
		if(event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player){
			if(event.getEntity() instanceof Player) {
				if(!event.isCancelled()) {
					Player damager = (Player) event.getEntity().getKiller();
					Player victim = (Player) event.getEntity();
					double d = 0.0;
					if(swordSwingProgress.containsKey(damager.getName())) {
						d = swordSwingProgress.get(damager.getName());
					}
					else {
						d = 1.0;
					}
					if(victim.getLastDamageCause() != null && victim.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK || victim.getLastDamageCause().getCause() == DamageCause.ENTITY_SWEEP_ATTACK) {
						if(d == 1.0) {
							if(damager.getEquipment().getItemInMainHand() != null) {
								if(damager.getEquipment().getItemInMainHand().hasItemMeta()) {
									if(damager.getEquipment().getItemInMainHand().getItemMeta().hasLore()) {
										for(String s1 : damager.getEquipment().getItemInMainHand().getItemMeta().getLore()){
											String enchant = ChatColor.stripColor(s1);
											String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
											if(enchantment.getMeleeEnchantments().containsKey(check)) {
												if(enchantment.getMeleeEnchantments().get(check).getKey() == Condition.PLAYER_DEATH_MELEE) {
													if(!wg.isInZone(damager, "spawn")) {
														enchant = enchant.replaceAll("[^\\d.]", "");
														int level = Integer.parseInt(enchant) - 1;
														enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, level);
														enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
														enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, victim, level);
														enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, victim, level, event);
													}
													else {
														damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
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
		}
	}
	@EventHandler
	public void armorEnchantmentHandler(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity){
			if(event.getEntity() instanceof LivingEntity) {
				if(!event.isCancelled()) {
					LivingEntity damager = (LivingEntity) event.getDamager();
					LivingEntity victim = (LivingEntity) event.getEntity();
					DamageCause damageCause = event.getCause();
					if(damageCause != null && damageCause == DamageCause.ENTITY_ATTACK || damageCause == DamageCause.ENTITY_SWEEP_ATTACK || damageCause == DamageCause.PROJECTILE) {
						if(victim.getEquipment().getArmorContents() != null && victim.getEquipment().getItemInOffHand() != null) {
							ArrayList<ItemStack> items = new ArrayList<ItemStack>(Arrays.asList(victim.getEquipment().getArmorContents()));
							items.add(victim.getEquipment().getItemInOffHand());
							for(ItemStack item : items) {
								if(item != null) {
									if(item.hasItemMeta()) {
										if(item.getItemMeta().hasLore()) {
											for(String s1 : item.getItemMeta().getLore()){
												String enchant = ChatColor.stripColor(s1);
												String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
												if(enchantment.getArmorEnchantments().containsKey(check)) {
													if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.ENTITY_DAMAGE_BY_ENTITY) {
														if(!wg.isInZone(victim, "spawn")) {
															enchant = enchant.replaceAll("[^\\d.]", "");
															int level = Integer.parseInt(enchant) - 1;
															enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(damager, level);
															enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
															enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(damager, victim, level);
															enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(damager, victim, level, event);
														}
														else {
															victim.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
															break;
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
			}
		}
	}
	@EventHandler
	public void armorEnchantmentHandlerDeath(EntityDeathEvent event) {
		if(event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof LivingEntity){
			if(event.getEntity() instanceof LivingEntity) {
				if(!event.isCancelled()) {
					LivingEntity damager = (LivingEntity) event.getEntity().getKiller();
					LivingEntity victim = (LivingEntity) event.getEntity();
					if(victim.getEquipment().getArmorContents() != null && victim.getEquipment().getItemInOffHand() != null) {
						ArrayList<ItemStack> items = new ArrayList<ItemStack>(Arrays.asList(victim.getEquipment().getArmorContents()));
						items.add(victim.getEquipment().getItemInOffHand());
						for(ItemStack item : items) {
							if(item != null) {
								if(item.hasItemMeta()) {
									if(item.getItemMeta().hasLore()) {
										for(String s1 : item.getItemMeta().getLore()){
											String enchant = ChatColor.stripColor(s1);
											String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
											if(enchantment.getArmorEnchantments().containsKey(check)) {
												if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.KILLED_ENTITY) {
													if(!wg.isInZone(victim, "spawn")) {
														enchant = enchant.replaceAll("[^\\d.]", "");
														int level = Integer.parseInt(enchant) - 1;
														enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(damager, level);
														enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
														enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(damager, victim, level);
														enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(damager, victim, level, event);
													}
													else {
														victim.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
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
		}
	}
	@EventHandler
	public void shieldEnchantmentHandler(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity){
			if(event.getEntity() instanceof Player) {
				if(!event.isCancelled()) {
					LivingEntity damager = (LivingEntity) event.getDamager();
					Player victim = (Player) event.getEntity();
					DamageCause damageCause = event.getCause();
					if(damageCause != null && damageCause == DamageCause.ENTITY_ATTACK || damageCause == DamageCause.ENTITY_SWEEP_ATTACK || damageCause == DamageCause.PROJECTILE) {
						if(victim.isBlocking()) {
							if(victim.getEquipment().getItemInOffHand() != null) {
								ItemStack item = victim.getEquipment().getItemInOffHand();
								if(item != null) {
									if(item.hasItemMeta()) {
										if(item.getItemMeta().hasLore()) {
											for(String s1 : item.getItemMeta().getLore()){
												String enchant = ChatColor.stripColor(s1);
												String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
												if(enchantment.getShieldEnchantments().containsKey(check)) {
													if(enchantment.getShieldEnchantments().get(check).getKey() == Condition.BLOCKING) {
														if(!wg.isInZone(victim, "spawn")) {
															enchant = enchant.replaceAll("[^\\d.]", "");
															int level = Integer.parseInt(enchant) - 1;
															enchantment.getShieldEnchantments().get(check).getValue().activateEnchantment(damager, level);
															enchantment.getShieldEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
															enchantment.getShieldEnchantments().get(check).getValue().activateEnchantment(damager, victim, level);
															enchantment.getShieldEnchantments().get(check).getValue().activateEnchantment(damager, victim, level, event);
														}
														else {
															victim.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
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
			}
		}
	}
	@EventHandler
	public void bowDamageHandler(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Arrow){
			Arrow arrow = (Arrow) event.getDamager();
			if(arrow.getShooter() instanceof LivingEntity) {
				if(event.getEntity() instanceof LivingEntity) {
					if(!event.isCancelled()) {
						LivingEntity damager = (LivingEntity) arrow.getShooter();
						LivingEntity victim = (LivingEntity) event.getEntity();
						DamageCause damageCause = event.getCause();
						if(damageCause != null && damageCause == DamageCause.PROJECTILE) {
							if(arrow.hasMetadata("AttackStrength") && arrow.getMetadata("AttackStrength").get(0).asDouble() == 1.00) {
								if(damager.getEquipment().getItemInMainHand() != null) {
									if(damager.getEquipment().getItemInMainHand().hasItemMeta()) {
										if(damager.getEquipment().getItemInMainHand().getItemMeta().hasLore()) {
											for(String s1 : damager.getEquipment().getItemInMainHand().getItemMeta().getLore()){
												String enchant = ChatColor.stripColor(s1);
												String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
												if(enchantment.getBowEnchantments().containsKey(check)) {
													if(enchantment.getBowEnchantments().get(check).getKey() == Condition.PROJECTILE_DAMAGE) {
														if(!wg.isInZone(damager, "spawn") && !wg.isInZone(arrow, "spawn")) {
															enchant = enchant.replaceAll("[^\\d.]", "");
															int level = Integer.parseInt(enchant) - 1;
															enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, level);
															enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
															enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, victim, level);
															enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, victim, level, event);
														}
														else {
															damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
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
			}
		}
	}
	@EventHandler
	public void bowShootHandler(DFShootBowEvent event) {
		if(event.getShooter() instanceof LivingEntity){
			if(!event.isCancelled()) {
				LivingEntity damager = (LivingEntity) event.getShooter();
				ItemStack item = event.getBow();
				if(event.getAttackCharge() == 1.0F) {
					if(item != null) {
						if(item.hasItemMeta()) {
							if(item.getItemMeta().hasLore()) {
								for(String s1 : item.getItemMeta().getLore()){
									String enchant = ChatColor.stripColor(s1);
									String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
									if(enchantment.getBowEnchantments().containsKey(check)) {
										if(enchantment.getBowEnchantments().get(check).getKey() == Condition.PROJECTILE_SHOOT) {
											if(!wg.isInZone(damager, "spawner") && !wg.isInZone(event.getProjectile(), "spawn")) {
												enchant = enchant.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(enchant) - 1;
												enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, level);
												enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
											}
											else {
												damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
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
	public void bowLandHandler(ProjectileHitEvent event) {
		if(event.getEntity() instanceof Arrow){
			Arrow arrow = (Arrow) event.getEntity();
			if(arrow.getShooter() instanceof LivingEntity) {
				LivingEntity damager = (LivingEntity) arrow.getShooter();
				if(arrow.hasMetadata("AttackStrength") && arrow.getMetadata("AttackStrength").get(0).asDouble() == 1.00) {
					if(damager.getEquipment().getItemInMainHand() != null) {
						if(damager.getEquipment().getItemInMainHand().getItemMeta() != null) {
							if(damager.getEquipment().getItemInMainHand().getItemMeta().getLore() != null) {
								for(String s1 : damager.getEquipment().getItemInMainHand().getItemMeta().getLore()){
									String enchant = ChatColor.stripColor(s1);
									String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
									if(enchantment.getBowEnchantments().containsKey(check)) {
										if(enchantment.getBowEnchantments().get(check).getKey() == Condition.PROJECTILE_LAND) {
											if(!wg.isInZone(damager, "spawn") && !wg.isInZone(arrow, "spawn")) {
												enchant = enchant.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(enchant) - 1;
												enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, level);
												enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
											}
											else {
												damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
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
	public void rightLeftClickHandler(PlayerInteractEvent event) {
		if(event.getPlayer() instanceof LivingEntity) {
			Player damager = event.getPlayer();
			if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(damager.getEquipment().getItemInMainHand() != null) {
					if(damager.getEquipment().getItemInMainHand().getType() != Material.BOW) {
						if(damager.getEquipment().getItemInMainHand().getItemMeta() != null) {
							if(damager.getEquipment().getItemInMainHand().getItemMeta().getLore() != null) {
								for(String s1 : damager.getEquipment().getItemInMainHand().getItemMeta().getLore()){
									String enchant = ChatColor.stripColor(s1);
									String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
									if(enchantment.getMeleeEnchantments().containsKey(check)) {
										if(enchantment.getMeleeEnchantments().get(check).getKey() == Condition.RIGHT_CLICK) {
											if(!wg.isInZone(damager, "spawn")) {
												if(!damager.hasCooldown(damager.getEquipment().getItemInMainHand().getType())) {
													enchant = enchant.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(enchant) - 1;
													enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, level);
													enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
												}
											}
											else {
												damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
											}
										}
									}
								}
							}
						}
					}
				}
			}
			else if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
				if(damager.getEquipment().getItemInMainHand() != null) {
					if(damager.getEquipment().getItemInMainHand().getType() == Material.BOW) {
						if(damager.getEquipment().getItemInMainHand().getItemMeta() != null) {
							if(damager.getEquipment().getItemInMainHand().getItemMeta().getLore() != null) {
								for(String s1 : damager.getEquipment().getItemInMainHand().getItemMeta().getLore()){
									String enchant = ChatColor.stripColor(s1);
									String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
									if(enchantment.getBowEnchantments().containsKey(check)) {
										if(enchantment.getBowEnchantments().get(check).getKey() == Condition.LEFT_CLICK) {
											if(!wg.isInZone(damager, "spawn")) {
												if(!damager.hasCooldown(damager.getEquipment().getItemInMainHand().getType())) {
													enchant = enchant.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(enchant) - 1;
													enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, level);
													enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
												}
											}
											else {
												damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
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
	public void damageEnchantmentHandler(EntityDamageEvent event) {
		if(event.getEntity() instanceof LivingEntity) {
			LivingEntity victim = (LivingEntity) event.getEntity();
			if(victim.getEquipment().getArmorContents() != null && victim.getEquipment().getItemInOffHand() != null) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>(Arrays.asList(victim.getEquipment().getArmorContents()));
				items.add(victim.getEquipment().getItemInOffHand());
				for(ItemStack item : items) {
					if(item != null) {
						if(item.hasItemMeta()) {
							if(item.getItemMeta().hasLore()) {
								for(String s1 : item.getItemMeta().getLore()){
									String enchant = ChatColor.stripColor(s1);
									String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
									if(enchantment.getArmorEnchantments().containsKey(check)) {
										if(event.getCause() == DamageCause.FALL) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.FALL) {
												if(!wg.isInZone(victim, "spawn")) {
													enchant = enchant.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(enchant) - 1;
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
												}
												else {
													victim.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
												}
											}
										}
										else if(event.getCause() == DamageCause.WITHER) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.WITHER) {
												if(!wg.isInZone(victim, "spawn")) {
													enchant = enchant.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(enchant) - 1;
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
												}
												else {
													victim.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
												}
											}
										}
										else if(event.getCause() == DamageCause.POISON) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.POISON) {
												if(!wg.isInZone(victim, "spawn")) {
													enchant = enchant.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(enchant) - 1;
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
												}
												else {
													victim.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
												}
											}
										}
										else if(event.getCause() == DamageCause.FIRE) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.LAVA) {
												if(!wg.isInZone(victim, "spawn")) {
													enchant = enchant.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(enchant) - 1;
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
												}
												else {
													victim.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
												}
											}
										}
										else if(event.getCause() == DamageCause.FIRE_TICK) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.FIRE) {
												if(!wg.isInZone(victim, "spawn")) {
													enchant = enchant.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(enchant) - 1;
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
												}
												else {
													victim.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
												}
											}
										}
										else if(event.getCause() == DamageCause.LIGHTNING) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.LIGHTNING) {
												if(!wg.isInZone(victim, "spawn")) {
													enchant = enchant.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(enchant) - 1;
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
												}
												else {
													victim.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
												}
											}
										}
										else if(event.getCause() == DamageCause.DROWNING) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.DROWNING) {
												if(!wg.isInZone(victim, "spawn")) {
													enchant = enchant.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(enchant) - 1;
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
												}
												else {
													victim.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
												}
											}
										}
										else if(event.getCause() == DamageCause.HOT_FLOOR) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.HOT_FLOOR) {
												if(!wg.isInZone(victim, "spawn")) {
													enchant = enchant.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(enchant) - 1;
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
												}
												else {
													victim.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
												}
											}
										}
										else if(event.getCause() == DamageCause.MAGIC) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.MAGIC) {
												if(!wg.isInZone(victim, "spawn")) {
													enchant = enchant.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(enchant) - 1;
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
												}
												else {
													victim.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
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
	}
	@EventHandler
	public void normalDamageEnchantmentHandler(EntityDamageEvent event) {
		if(event.getEntity() instanceof LivingEntity) {
			LivingEntity victim = (LivingEntity) event.getEntity();
			if(victim.getEquipment().getArmorContents() != null && victim.getEquipment().getItemInOffHand() != null) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>(Arrays.asList(victim.getEquipment().getArmorContents()));
				items.add(victim.getEquipment().getItemInOffHand());
				for(ItemStack item : items) {
					if(item != null) {
						if(item.hasItemMeta()) {
							if(item.getItemMeta().hasLore()) {
								for(String s1 : item.getItemMeta().getLore()){
									String enchant = ChatColor.stripColor(s1);
									String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
									if(enchantment.getArmorEnchantments().containsKey(check)) {
										if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.ENTITY_DAMAGE) {
											if(!wg.isInZone(victim, "spawn")) {
												enchant = enchant.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(enchant) - 1;
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
											}
											else {
												victim.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use enchantments at spawn!"));
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
	public void armorChangeListener(PlayerArmorChangeEvent event) {
		Player p = event.getPlayer();
		ItemStack armorNew = event.getNewItem();
		ItemStack armorOld = event.getOldItem();
		if(armorNew != null) {
			if(armorNew.hasItemMeta()) {
				if(armorNew.getItemMeta().hasLore()) {
					for(String s1 : armorNew.getItemMeta().getLore()){
						String enchant = ChatColor.stripColor(s1);
						String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
						if(enchantment.getArmorEnchantments().containsKey(check)) {
							if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.ARMOR_CHANGE) {
								enchant = enchant.replaceAll("[^\\d.]", "");
								int level = Integer.parseInt(enchant) - 1;
								enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(p, level, true);
								enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(p, level, true, event);
								enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(p, level, event);
							}
						}
					}
				}
			}
		}
		if(armorOld != null) {
			if(armorOld.hasItemMeta()) {
				if(armorOld.getItemMeta().hasLore()) {
					for(String s1 : armorOld.getItemMeta().getLore()){
						String enchant = ChatColor.stripColor(s1);
						String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
						if(enchantment.getArmorEnchantments().containsKey(check)) {
							if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.ARMOR_CHANGE) {
								enchant = enchant.replaceAll("[^\\d.]", "");
								int level = Integer.parseInt(enchant) - 1;
								enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(p, level, false);
								enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(p, level, false, event);
								enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(p, level, event);
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void consumeHandler(PlayerItemConsumeEvent event) {
		Player victim = (Player) event.getPlayer();
		if(victim.getEquipment().getArmorContents() != null && victim.getEquipment().getItemInOffHand() != null) {
			ArrayList<ItemStack> items = new ArrayList<ItemStack>(Arrays.asList(victim.getEquipment().getArmorContents()));
			items.add(victim.getEquipment().getItemInOffHand());
			for(ItemStack item : items) {
				if(item != null) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasLore()) {
							for(String s1 : item.getItemMeta().getLore()){
								String enchant = ChatColor.stripColor(s1);
								String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
								if(enchantment.getArmorEnchantments().containsKey(check)) {
									if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.CONSUME) {
										enchant = enchant.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(enchant) - 1;
										enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
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
	public void foodLevelUpDownHandler(FoodLevelChangeEvent event) {
		Player p = (Player) event.getEntity();
		int foodBefore = p.getFoodLevel();
		int foodAfter = event.getFoodLevel();
		if(p.getInventory().getArmorContents() != null && p.getInventory().getItemInOffHand() != null) {
			ArrayList<ItemStack> items = new ArrayList<ItemStack>(Arrays.asList(p.getInventory().getArmorContents()));
			items.add(p.getInventory().getItemInOffHand());
			for(ItemStack item : items) {
				if(item != null) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasLore()) {
							for(String s1 : item.getItemMeta().getLore()){
								String enchant = ChatColor.stripColor(s1);
								String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
								if(foodBefore < foodAfter) {
									if(enchantment.getArmorEnchantments().containsKey(check)) {
										if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.FOOD_CHANGE_UP) {
											enchant = enchant.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(enchant) - 1;
											enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(p, level, event);
										}
									}
								}
								if(foodBefore > foodAfter) {
									if(enchantment.getArmorEnchantments().containsKey(check)) {
										if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.FOOD_CHANGE_DOWN) {
											enchant = enchant.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(enchant) - 1;
											enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(p, level, event);
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
	public void enchantmentGuideOpen(PlayerInteractEntityEvent event) {
		if(event.getPlayer() instanceof Player) {
			if(event.getRightClicked() instanceof HumanEntity) {
				Player player = event.getPlayer();
				HumanEntity guide = (HumanEntity) event.getRightClicked();
				if(guide.getCustomName() != null) {
					if(guide.getCustomName().contains(ChatColor.stripColor("Enchantments"))) {
						this.enchInv.openMainInventory(player);
					}
				}
			}
		}
	}
	@EventHandler
	public void enchantmentGuideMenu(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		if(ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("Enchantments")) {
			event.setCancelled(true);
			if(i != null) {
				NBTItem item = new NBTItem(i);
				if(item.hasKey("Melee")) {
					if(this.enchInv.getMeleeSize() != 0) {
						this.enchInv.openMeleeInventory(player, 1);
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cMelee enchantments are currently not available"));
					}
				}
				if(item.hasKey("Bow")) {
					if(this.enchInv.getBowSize() != 0) {
						this.enchInv.openBowInventory(player, 1);
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cBow enchantments are currently not available"));
					}
				}
				if(item.hasKey("Armor")) {
					if(this.enchInv.getArmorSize() != 0) {
						this.enchInv.openArmorInventory(player, 1);
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cArmor enchantments are currently not available"));
					}
				}
				if(item.hasKey("Shield")) {
					if(this.enchInv.getShieldSize() != 0) {
						this.enchInv.openShieldInventory(player, 1);
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cShield enchantments are currently not available"));
					}
				}
				else if(item.hasKey("Exit")) {
					player.closeInventory();
				}
			}
		}
		else if(player.getOpenInventory().getTitle().contains("Melee Enchantments")) {
			event.setCancelled(true);
			if(i != null) {
				NBTItem item = new NBTItem(i);
				if(item.hasKey("Next")) {
					int page = item.getInteger("Next");
					if(page - 1 < this.enchInv.getMeleeSize()) {
						this.enchInv.openMeleeInventory(player, page);
					}
					else {
						this.enchInv.openMeleeInventory(player, 1);
					}
				}
				else if(item.hasKey("Previous")) {
					int page = item.getInteger("Previous");
					if(page - 1 >= 0) {
						this.enchInv.openMeleeInventory(player, page);
					}
					else {
						this.enchInv.openMeleeInventory(player, this.enchInv.getMeleeSize());
					}
				}
				else if(item.hasKey("Back")) {
					this.enchInv.openMainInventory(player);
				}
			}
		}
		else if(player.getOpenInventory().getTitle().contains("Bow Enchantments")) {
			event.setCancelled(true);
			if(i != null) {
				NBTItem item = new NBTItem(i);
				if(item.hasKey("Next")) {
					int page = item.getInteger("Next");
					if(page - 1 < this.enchInv.getBowSize()) {
						this.enchInv.openBowInventory(player, page);
					}
					else {
						this.enchInv.openBowInventory(player, 1);
					}
				}
				else if(item.hasKey("Previous")) {
					int page = item.getInteger("Previous");
					if(page - 1 >= 0) {
						this.enchInv.openBowInventory(player, page);
					}
					else {
						this.enchInv.openBowInventory(player, this.enchInv.getBowSize());
					}
				}
				else if(item.hasKey("Back")) {
					this.enchInv.openMainInventory(player);
				}
			}
		}
		else if(player.getOpenInventory().getTitle().contains("Armor Enchantments")) {
			event.setCancelled(true);
			if(i != null) {
				NBTItem item = new NBTItem(i);
				if(item.hasKey("Next")) {
					int page = item.getInteger("Next");
					if(page - 1 < this.enchInv.getArmorSize()) {
						this.enchInv.openArmorInventory(player, page);
					}
					else {
						this.enchInv.openArmorInventory(player, 1);
					}
				}
				else if(item.hasKey("Previous")) {
					int page = item.getInteger("Previous");
					if(page - 1 >= 0) {
						this.enchInv.openArmorInventory(player, page);
					}
					else {
						this.enchInv.openArmorInventory(player, this.enchInv.getArmorSize());
					}
				}
				else if(item.hasKey("Back")) {
					this.enchInv.openMainInventory(player);
				}
			}
		}
		else if(player.getOpenInventory().getTitle().contains("Shield Enchantments")) {
			event.setCancelled(true);
			if(i != null) {
				NBTItem item = new NBTItem(i);
				if(item.hasKey("Next")) {
					int page = item.getInteger("Next");
					if(page - 1 < this.enchInv.getShieldSize()) {
						this.enchInv.openShieldInventory(player, page);
					}
					else {
						this.enchInv.openShieldInventory(player, 1);
					}
				}
				else if(item.hasKey("Previous")) {
					int page = item.getInteger("Previous");
					if(page - 1 >= 0) {
						this.enchInv.openShieldInventory(player, page);
					}
					else {
						this.enchInv.openShieldInventory(player, this.enchInv.getShieldSize());
					}
				}
				else if(item.hasKey("Back")) {
					this.enchInv.openMainInventory(player);
				}
			}
		}
	}
}
