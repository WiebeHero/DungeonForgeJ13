package me.WiebeHero.EnchantmentAPI;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;

import me.WiebeHero.CustomEvents.DFShootBowEvent;
import me.WiebeHero.EnchantmentAPI.EnchantmentCondition.Condition;
import me.WiebeHero.MoreStuff.SwordSwingProgress;

public class EnchantmentHandler extends SwordSwingProgress{
	private Enchantment enchantment;
	public EnchantmentHandler(Enchantment enchantment) {
		this.enchantment = enchantment;
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
													enchant = enchant.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(enchant) - 1;
													enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, level);
													enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
													enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, victim, level);
													enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, victim, level, event);
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
					if(victim.getLastDamageCause() != null && victim.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK && victim.getLastDamageCause().getCause() == DamageCause.ENTITY_SWEEP_ATTACK) {
						if(d == 1.0) {
							if(damager.getEquipment().getItemInMainHand() != null) {
								if(damager.getEquipment().getItemInMainHand().hasItemMeta()) {
									if(damager.getEquipment().getItemInMainHand().getItemMeta().hasLore()) {
										for(String s1 : damager.getEquipment().getItemInMainHand().getItemMeta().getLore()){
											String enchant = ChatColor.stripColor(s1);
											String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
											if(enchantment.getMeleeEnchantments().containsKey(check)) {
												if(enchantment.getMeleeEnchantments().get(check).getKey() == Condition.ENTITY_DEATH_MELEE) {
													enchant = enchant.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(enchant) - 1;
													enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, level);
													enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
													enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, victim, level);
													enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, victim, level, event);
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
					if(victim.getLastDamageCause() != null && victim.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK && victim.getLastDamageCause().getCause() == DamageCause.ENTITY_SWEEP_ATTACK) {
						if(d == 1.0) {
							if(damager.getEquipment().getItemInMainHand() != null) {
								if(damager.getEquipment().getItemInMainHand().hasItemMeta()) {
									if(damager.getEquipment().getItemInMainHand().getItemMeta().hasLore()) {
										for(String s1 : damager.getEquipment().getItemInMainHand().getItemMeta().getLore()){
											String enchant = ChatColor.stripColor(s1);
											String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
											if(enchantment.getMeleeEnchantments().containsKey(check)) {
												if(enchantment.getMeleeEnchantments().get(check).getKey() == Condition.PLAYER_DEATH_MELEE) {
													enchant = enchant.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(enchant) - 1;
													enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, level);
													enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
													enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, victim, level);
													enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, victim, level, event);
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
														enchant = enchant.replaceAll("[^\\d.]", "");
														int level = Integer.parseInt(enchant) - 1;
														enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(damager, level);
														enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
														enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(damager, victim, level);
														enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(damager, victim, level, event);
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
													enchant = enchant.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(enchant) - 1;
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(damager, level);
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(damager, victim, level);
													enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(damager, victim, level, event);
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
														enchant = enchant.replaceAll("[^\\d.]", "");
														int level = Integer.parseInt(enchant) - 1;
														enchantment.getShieldEnchantments().get(check).getValue().activateEnchantment(damager, level);
														enchantment.getShieldEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
														enchantment.getShieldEnchantments().get(check).getValue().activateEnchantment(damager, victim, level);
														enchantment.getShieldEnchantments().get(check).getValue().activateEnchantment(damager, victim, level, event);
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
														enchant = enchant.replaceAll("[^\\d.]", "");
														int level = Integer.parseInt(enchant) - 1;
														enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, level);
														enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
														enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, victim, level);
														enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, victim, level, event);
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
				if(event.getProjectile().hasMetadata("AttackStrength") && event.getProjectile().getMetadata("AttackStrength").get(0).asDouble() == 1.00) {
					if(item != null) {
						if(item.hasItemMeta()) {
							if(item.getItemMeta().hasLore()) {
								for(String s1 : item.getItemMeta().getLore()){
									String enchant = ChatColor.stripColor(s1);
									String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
									if(enchantment.getBowEnchantments().containsKey(check)) {
										if(enchantment.getBowEnchantments().get(check).getKey() == Condition.PROJECTILE_SHOOT) {
											enchant = enchant.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(enchant) - 1;
											enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, level);
											enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
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
											enchant = enchant.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(enchant) - 1;
											enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, level);
											enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
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
					if(damager.getEquipment().getItemInMainHand().getItemMeta() != null) {
						if(damager.getEquipment().getItemInMainHand().getItemMeta().getLore() != null) {
							for(String s1 : damager.getEquipment().getItemInMainHand().getItemMeta().getLore()){
								String enchant = ChatColor.stripColor(s1);
								String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
								if(enchantment.getMeleeEnchantments().containsKey(check)) {
									if(enchantment.getMeleeEnchantments().get(check).getKey() == Condition.RIGHT_CLICK) {
										enchant = enchant.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(enchant) - 1;
										enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, level);
										enchantment.getMeleeEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
									}
								}
							}
						}
					}
				}
			}
			else if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
				if(damager.getEquipment().getItemInMainHand() != null) {
					if(damager.getEquipment().getItemInMainHand().getItemMeta() != null) {
						if(damager.getEquipment().getItemInMainHand().getItemMeta().getLore() != null) {
							for(String s1 : damager.getEquipment().getItemInMainHand().getItemMeta().getLore()){
								String enchant = ChatColor.stripColor(s1);
								String check = StringUtils.substring(enchant, 0, enchant.length() - 2);
								if(enchantment.getBowEnchantments().containsKey(check)) {
									if(enchantment.getBowEnchantments().get(check).getKey() == Condition.LEFT_CLICK) {
										enchant = enchant.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(enchant) - 1;
										enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, level);
										enchantment.getBowEnchantments().get(check).getValue().activateEnchantment(damager, level, event);
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
												enchant = enchant.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(enchant) - 1;
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
											}
										}
										else if(event.getCause() == DamageCause.WITHER) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.WITHER) {
												enchant = enchant.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(enchant) - 1;
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
											}
										}
										else if(event.getCause() == DamageCause.POISON) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.POISON) {
												enchant = enchant.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(enchant) - 1;
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
											}
										}
										else if(event.getCause() == DamageCause.FIRE) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.LAVA) {
												enchant = enchant.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(enchant) - 1;
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
											}
										}
										else if(event.getCause() == DamageCause.FIRE_TICK) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.FIRE) {
												enchant = enchant.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(enchant) - 1;
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
											}
										}
										else if(event.getCause() == DamageCause.LIGHTNING) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.LIGHTNING) {
												enchant = enchant.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(enchant) - 1;
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
											}
										}
										else if(event.getCause() == DamageCause.DROWNING) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.DROWNING) {
												enchant = enchant.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(enchant) - 1;
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
											}
										}
										else if(event.getCause() == DamageCause.HOT_FLOOR) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.HOT_FLOOR) {
												enchant = enchant.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(enchant) - 1;
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level, event);
											}
										}
										else if(event.getCause() == DamageCause.MAGIC) {
											if(enchantment.getArmorEnchantments().get(check).getKey() == Condition.MAGIC) {
												enchant = enchant.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(enchant) - 1;
												enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
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
											enchant = enchant.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(enchant) - 1;
											enchantment.getArmorEnchantments().get(check).getValue().activateEnchantment(victim, level);
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
			if(armorNew.hasItemMeta()) {
				if(armorNew.getItemMeta().hasLore()) {
					for(String s1 : armorNew.getItemMeta().getLore()){
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
	
}
