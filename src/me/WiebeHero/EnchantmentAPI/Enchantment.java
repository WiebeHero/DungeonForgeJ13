package me.WiebeHero.EnchantmentAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Animals;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Arrow.PickupStatus;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;

import javafx.util.Pair;
import me.WiebeHero.APIs.ParticleAPI;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomEvents.DFPlayerXpGainEvent;
import me.WiebeHero.CustomEvents.DFShootBowEvent;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.CustomMethods.PotionM;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.DFPlayerPackage.Enums.Classes;
import me.WiebeHero.EnchantmentAPI.EnchantmentCondition.Condition;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Factions.DFFactionManager;
import me.WiebeHero.Factions.DFFactionPlayer;
import me.WiebeHero.Factions.DFFactionPlayerManager;
import me.WiebeHero.Scoreboard.WGMethods;
import net.minecraft.server.v1_13_R2.EntityArmorStand;
import net.minecraft.server.v1_13_R2.EntityLiving;
import net.minecraft.server.v1_13_R2.PacketPlayOutWorldParticles;

public class Enchantment extends CommandFile implements Listener{
	//Empty Constructor
	private DFPlayerManager dfManager;
	private DFFactionManager facManager;
	private DFFactionPlayerManager facPlayerManager;
	private PotionM p;
	private ParticleAPI pApi;
	private ItemStackBuilder builder;
	private WGMethods wg;
	public Enchantment(DFPlayerManager dfManager, DFFactionManager facManager, PotionM p, ParticleAPI pApi, DFFactionPlayerManager facPlayerManager, ItemStackBuilder builder, WGMethods wg) {
		this.dfManager = dfManager;
		this.facManager = facManager;
		this.builder = builder;
		this.p = p;
		this.pApi = pApi;
		this.facPlayerManager = facPlayerManager;
		this.wg = wg;
		this.loadMeleeEnchantments();
		this.loadBowEnchantments();
		this.loadArmorEnchantments();
		this.loadShieldEnchantments();
	}
	//Enchantment Functionality List
	public HashMap<String, Pair<Condition, CommandFile>> listMelee;
	public HashMap<String, Pair<Condition, CommandFile>> listArmor;
	public HashMap<String, Pair<Condition, CommandFile>> listShield;
	public HashMap<String, Pair<Condition, CommandFile>> listBow;
	
	public void loadMeleeEnchantments() {
		this.listMelee = new HashMap<String, Pair<Condition, CommandFile>>();
		this.listMelee.put("All Out", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 1 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 2, (float) 1.2);
					if(dfManager.contains(damager)) {
						DFPlayer dfPlayer = dfManager.getEntity(damager);
						dfPlayer.addAtkCal(50.0 + 25.0 * level, 1);
						new BukkitRunnable() {
							public void run() {
								damager.damage(event.getDamage() / 100 * (55 - 5 * level));
								if(damager instanceof Player) {
									damager.setKiller((Player)damager);
								}
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 1L);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6All Out",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, you have a chance",
						"&7to go all out, causing your attack damage to",
						"&7highly rise during the attack. However, you go",
						"&7beyond your limit and damage yourself in the",
						"&7process. Taking a portion of the damage that",
						"&7you dealt to the enemy."
					))
				);
			}
		}));
		this.listMelee.put("Beserk", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 5 + level * 2) {
					Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.5D, damager.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.CRIT_MAGIC, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					damager.getWorld().playSound(damager.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 2, (float) 1);
					int amp = 0 + level;
					int durationAdd = 60 + (30 * level);
					ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.INCREASE_DAMAGE, PotionEffectType.SLOW_DIGGING));
					p.applyEffect(damager, types, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Beserk",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, you have a chance",
						"&7to go Beserk. When going beserk, you gain the",
						"&7strength and mining fatigue effect for a short",
						"&7amount of time."
					))
				);
			}
		}));
		this.listMelee.put("Blast", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				//if(i <= 4 + 1.5 * level) {
				if(i <= 4 + 1.5 * level) {
					final double range = 5.00 + level; 
					final double damage = 7 + 1.5 * level;
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, locCF, 150, range, range, range, 0.1);
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, (float) 1);
					for(Entity e : victim.getNearbyEntities(range, range, range)) {
						if(e != damager) {
							if(e instanceof LivingEntity) {
								LivingEntity ent = (LivingEntity) e;
								if(!facManager.isFriendly(damager, ent)) {
									if(damager instanceof Player) {
										ent.setKiller((Player)damager);
									}
									ent.damage(damage - e.getLocation().distance(victim.getLocation()));
								}	
							}
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Blast",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that an explosion will generate at the enemy's",
						"&7location. Causing the enemy that you attack to take",
						"&7extra damage and damage surrounding enemies."
					))
				);
			}
		}));
		this.listMelee.put("Bleed", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 2 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.SWEEP_ATTACK, locCF, 10, 0, 0.15, 0, 0); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_SPIDER_HURT, 2, (float) 1.2);
					new BukkitRunnable() {
						int count = 0;
						public void run() {
							if(count <= (14 + level * 2)) {
								if(damager instanceof Player) {
									victim.setKiller((Player)damager);
								}
								victim.damage(1 + level * 0.5);
								count++;
							}
							else {
								cancel();
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0, 10L);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Bleed",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you cut a wound in their flesh so deep that they",
						"&7start bleeding. Causing the enemy to take",
						"&7continuous damage for a short amount of time."
					))
				);
			}
		}));
		this.listMelee.put("Blind", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 8 + level * 2) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.PORTAL, locCF, 50, 0, 0, 0, 4); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2, (float) 1);
					int amp = 0;
					int durationAdd = 120 + 30 * level;
					PotionEffectType type = PotionEffectType.BLINDNESS;
					p.applyEffect(victim, type, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Blind",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you hit the enemy in the weak spot of their head.",
						"&7Causing them to go blind for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Blizzard", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 4 + level * 0.5) {
					double range = 2.5 + level * 0.5;
					for(Entity entity : victim.getNearbyEntities(range, range, range)){
						if(entity instanceof LivingEntity) {
							if(entity != damager) {
								LivingEntity entity1 = (LivingEntity) entity;
								Location locCF = new Location(entity1.getWorld(), entity1.getLocation().getX() + 0D, entity1.getLocation().getY() + 1.7D, entity1.getLocation().getZ() + 0D);
								entity1.getWorld().spawnParticle(Particle.PORTAL, locCF, 60, 0.05, 0.05, 0.05, 0.1); 
								entity1.getWorld().playSound(entity1.getLocation(), Sound.BLOCK_GLASS_BREAK, 2, (float) 1);
								int amp = (int)Math.floor(0 + (level) / 2);
								int durationAdd = 100 + 20 * level;
								ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.SLOW, PotionEffectType.BLINDNESS));
								p.applyEffect(entity1, types, amp, durationAdd);
							}                                           
						}
					}
				}
			} 
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Blizzard",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that a sudden Blizzard will rise from nowhere. Causing",
						"&7the enemy that you attack and sorounding enemies",
						"&7to move slower and go blind for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Brand", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			HashMap<UUID, Integer> brandList = new HashMap<UUID, Integer>();
			@EventHandler
			public void event(EntityDamageByEntityEvent event) {
				if(event.getEntity() instanceof LivingEntity) {
					LivingEntity ent = (LivingEntity) event.getEntity();
					if(event.getDamager() instanceof LivingEntity) {
						if(brandList.containsKey(ent.getUniqueId())) {
							LivingEntity damager = (LivingEntity) event.getDamager();
							if(dfManager.contains(damager)) {
								DFPlayer dfPlayer = dfManager.getEntity(damager);
								dfPlayer.addAtkCal(12.5 * brandList.get(ent.getUniqueId()), 1);
							}
						}
					}
					else if(event.getDamager() instanceof Arrow) {
						if(brandList.containsKey(ent.getUniqueId())) {
							Arrow arrow = (Arrow) event.getDamager();
							if(arrow.getShooter() instanceof LivingEntity) {
								LivingEntity shooter = (LivingEntity) arrow.getShooter();
								if(dfManager.contains(shooter)) {
									LivingEntity damager = (LivingEntity) shooter;
									DFPlayer dfPlayer = dfManager.getEntity(damager);
									dfPlayer.addRndCal(12.5 * brandList.get(ent.getUniqueId()), 1);
								}
							}
						}
					}
				}
			}
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 4 + 0.8 * level) {
					Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.FLAME, locCF1, 80, 0.15, 0.15, 0.15, 0); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, (float) 1.5);
					brandList.put(victim.getUniqueId(), level);
					new BukkitRunnable() {
						public void run() {
							brandList.remove(victim.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 140L + (30L * level));
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Brand",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you Brand your enemy. You mark them, this causes",
						"&7the enemy that you attacked to recieve more damage",
						"&7from melee and ranged attacks for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Break", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + 0.5 * level) {
					Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, locCF1, 80, 0.15, 0.15, 0.15, 0, Material.ANVIL.createBlockData()); 
					victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_ANVIL_PLACE, 2, (float) 1.0);
					DFPlayer dfPlayer = dfManager.getEntity(victim);
					dfPlayer.removeDfCal(3.0 * level, 140 + (level * 20));
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Break",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you hit them in a weak spot, causing their",
						"&7defense skill to decrease for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Charge", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(damager instanceof Player) {
					Player p = (Player) damager;
					p.setCooldown(p.getInventory().getItemInMainHand().getType(), 800 - 120 * level);
				}
				int amp = (int)Math.floor(0 + (level) / 2);
				int durationAdd = 100 + 33 * level;
				ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.SPEED, PotionEffectType.INCREASE_DAMAGE));
				p.applyEffect(damager, types, amp, durationAdd);
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Charge",
					new ArrayList<String>(Arrays.asList(
						"&7When you right click, you go into a charge",
						"&7causing you to gain the strength and speed effect",
						"&7for a few seconds.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listMelee.put("Chronical Disturbance", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 3.5 + 1.5 * level) {
					damager.getWorld().spawnParticle(Particle.PORTAL, damager.getLocation().add(0, 1.7, 0), 80, 0.2, 0.2, 0.2, 0.1); 
					damager.getWorld().playSound(damager.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 2, (float) 1);
					new BukkitRunnable() {
						int count = 0;
						public void run() {
							if(count <= 60 + 20 * level) {
								if(damager instanceof Player) {
									victim.setKiller((Player)damager);
								}
								victim.damage(0.1 + 0.05 * level);
								count++;
							}
							else {
								cancel();
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Chronical Disturbance",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that time warps around them. Causing them to take",
						"&7a huge amount of damage in a short amount of",
						"&7time and making their screen shake."
					))
				);
			}
		}));
		
		this.listMelee.put("Confusion", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 10 + level * 2) {	
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2D, victim.getLocation().getZ() + 0D);
					Particle.DustOptions dustOptions = new Particle.DustOptions(Color.YELLOW, 1);
					victim.getWorld().spawnParticle(Particle.REDSTONE, locCF, 60, 0.15, 0.15, 0.15, 0.1, dustOptions); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_GHAST_HURT, 2, (float) 0.5);
					int amp = 0 + level;
					int durationAdd = 180 + 50 * level;
					PotionEffectType type = PotionEffectType.CONFUSION;
					p.applyEffect(victim, type, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Confusion",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you hit them in the head very hard",
						"&7causing them to be confused and gain",
						"&7the nausea effect for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Cyclone", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 0.5 + 0.5 * level) {
					Location loc = victim.getLocation();
				    int radius = (int) (5 + 0.5 * level);
				    for(double y = 0; y <= 20; y+=0.0075) {
				        double x = radius * Math.cos(y);
				        double z = radius * Math.sin(y);
				        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(null, true, (float) (loc.getX() + x), (float) (loc.getY() + y), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
				        for(Player online : Bukkit.getOnlinePlayers()) {
				            ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
				        }
				    }
					double range = 5 + 0.5 * level;
					new BukkitRunnable() {
						public void run() {
							for(Entity e : victim.getNearbyEntities(range, range, range)) {
								if(e instanceof LivingEntity) {
									if(e != damager) {
										LivingEntity entity = (LivingEntity) e;
										if(!wg.isInZone(e, "spawn")) {
											int random1 = new Random().nextInt(4) - 4;
											int random2 = new Random().nextInt(4) - 4;
											float x = ThreadLocalRandom.current().nextFloat() * 4 + (float)random1;
											float z = ThreadLocalRandom.current().nextFloat() * 4 + (float)random2;
											entity.setVelocity(new Vector(x, 1.8 + 0.2 * level, z));
										}
									}
								}
							}
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 1L);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Cyclone",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that a cyclone will generate out of nowhere.",
						"&7Causing the enemy that you hit and enemies sorounding him",
						"&7to be launched high in the sky."
					))
				);
			}
		}));
		this.listMelee.put("Defensive Position", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 8 + level * 2) {
					Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.5D, damager.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.DRIP_WATER, locCF, 30, 0.1, 0.1, 0.1, 0.1);
					damager.getWorld().playSound(damager.getLocation(), Sound.BLOCK_ANVIL_FALL, 2, (float) 1);
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 60 + 10 * level;
					PotionEffectType type = PotionEffectType.DAMAGE_RESISTANCE;
					p.applyEffect(damager, type, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Defensive Position",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you prepare for an incoming",
						"&7counter attack, causing you to take a",
						"&7defensive stand and gaining the resistance",
						"&7effect for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Determined Slash", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			HashMap<UUID, Integer> activated = new HashMap<UUID, Integer>();
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!cooldown.contains(damager.getUniqueId())) {
					damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou prepare &6Determined Slash!"));
					activated.put(damager.getUniqueId(), level);
				}
//				Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.7D, damager.getLocation().getZ() + 0D);
//				damager.getWorld().spawnParticle(Particle.FLAME, locCF, 60, 0.05, 0.1, 0.05, 0.1); 
//				damager.getWorld().playSound(damager.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, (float) 1.25);
			}
			@EventHandler
			public void activateSlash(PlayerInteractEvent event) {
				Player player = event.getPlayer();
				UUID uuid = player.getUniqueId();
				Action action = event.getAction();
				if(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
					if(activated.containsKey(uuid)) {
						event.setCancelled(true);
						cooldown.add(uuid);
						new BukkitRunnable() {
							public void run() {
								cooldown.remove(uuid);
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 1300L - 50L * activated.get(player.getUniqueId()));
						ArmorStand slash1 = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, 1.5, 0).add(player.getLocation().getDirection()), EntityType.ARMOR_STAND);
						Vector front = slash1.getLocation().getDirection();
						Location locLeft = pApi.getLocationRelative(player.getLocation().add(0, 1.5, 0), -2.0, 0);
						Location locRight = pApi.getLocationRelative(player.getLocation().add(0, 1.5, 0), 2.0, 0);
						ArmorStand slash2 = (ArmorStand) player.getWorld().spawnEntity(locLeft, EntityType.ARMOR_STAND);
						ArmorStand slash3 = (ArmorStand) player.getWorld().spawnEntity(locRight, EntityType.ARMOR_STAND);
						Location to = slash1.getLocation().clone();
						Vector slash2ToMain = to.clone().subtract(slash2.getLocation()).toVector().normalize();
						Vector slash3ToMain = to.clone().subtract(slash3.getLocation()).toVector().normalize();
						ArrayList<ArmorStand> stands = new ArrayList<ArmorStand>(Arrays.asList(slash1, slash2, slash3));
						for(int i = 0; i < stands.size(); i++) {
							ArmorStand slash = stands.get(i);
							slash.setInvulnerable(true);
							slash.setRemoveWhenFarAway(false);
							EntityArmorStand standE = ((CraftArmorStand)slash).getHandle();
							standE.noclip = true;
							standE.setInvisible(true);
						}
						ArrayList<UUID> hit = new ArrayList<UUID>();
						new BukkitRunnable() {
							int c = 0;
							boolean cancelled = false;
							double damage = 8.00 + 2.00 * activated.get(uuid);
							public void run() {
								if(c <= 30) {
									Location sl2 = slash2.getLocation().clone();
									Location sl3 = slash3.getLocation().clone();
									sl2.setDirection(slash2ToMain);
									sl3.setDirection(slash3ToMain);
									sl2.subtract(slash2ToMain.clone().multiply(1.5));
									sl3.subtract(slash3ToMain.clone().multiply(1.5));
									for(int x = 0; x < 16; x++) {
										sl2.getWorld().spawnParticle(Particle.REDSTONE, sl2, 1, new DustOptions(Color.RED, 2.5F));
										sl3.getWorld().spawnParticle(Particle.REDSTONE, sl3, 1, new DustOptions(Color.RED, 2.5F));
										sl2.add(slash2ToMain.clone().multiply(0.25));
										sl3.add(slash3ToMain.clone().multiply(0.25));
									}
									for(int i = 0; i < stands.size(); i++) {
										ArmorStand slash = stands.get(i);
										for(Entity e : slash.getNearbyEntities(1.0, 0.2, 1.0)) {
											if(e instanceof LivingEntity && e != player && !stands.contains(e)) {
												if(!hit.contains(e.getUniqueId())) {
													LivingEntity ent = (LivingEntity) e;
													ent.setKiller(player);
													hit.add(ent.getUniqueId());
													if(!facManager.isFriendly(player, ent)) {
														ent.damage(damage);
													}
													damage = damage / 2;
												}
											}
										}
										if(slash.getLocation().getBlock().getType() != Material.AIR) {
											slash.remove();
											cancelled = true;
										}
										slash.setVelocity(front.clone().multiply(0.75F));
									}
								}
								if(cancelled == true) {
									for(ArmorStand stand : stands) {
										stand.remove();
									}
									cancel();
								}
								c++;
							}
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 2L);
						activated.remove(uuid);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Determined Slash",
					new ArrayList<String>(Arrays.asList(
						"&7When you right click, you prepare to perform",
						"&6Determined Slash. &7When you have prepared &6Determined Slash",
						"&7and then left click, a red slash will appear",
						"&7infront of you traveling a certain distance",
						"&7damaging enemies who come into contact with the",
						"&7slash. This slash is piercing, meaning it will continue",
						"&7after damaging an enemy. However, the damage will be decreased",
						"&7by half per enemy that is hit.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listMelee.put("Disarmor", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 1.25 + level * 0.25) {
					ItemStack helm = victim.getEquipment().getHelmet();
					ItemStack chest = victim.getEquipment().getChestplate();
					ItemStack legs = victim.getEquipment().getLeggings();
					ItemStack boots = victim.getEquipment().getBoots();
					int rand = new Random().nextInt (4) + 1;
					if(rand == 1) {
						if(helm != null) {
							victim.getWorld().dropItemNaturally(victim.getLocation().add(0, 1, 0), helm);
							victim.getEquipment().setHelmet(null);
						}
					}
					else if(rand == 2) {
						if(chest != null) {
							victim.getWorld().dropItemNaturally(victim.getLocation().add(0, 1, 0), chest);
							victim.getEquipment().setChestplate(null);
						}
					}
					else if(rand == 3) {
						if(legs != null) {
							victim.getWorld().dropItemNaturally(victim.getLocation().add(0, 1, 0), legs);
							victim.getEquipment().setLeggings(null);
						}
					}
					else if(rand == 4) {
						if(boots != null) {
							victim.getWorld().dropItemNaturally(victim.getLocation().add(0, 1, 0), boots);
							victim.getEquipment().setBoots(null);
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Disarmor",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you unequip 1 of the enemies armor pieces.",
						"&7This armor piece will drop on the ground when",
						"&7unequiped."
					))
				);
			}
		}));
		this.listMelee.put("Dragons Fireball", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> playerStuff = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!playerStuff.contains(damager.getUniqueId())) {
					playerStuff.add(damager.getUniqueId()); // set to "true"
					DragonFireball fireball = damager.launchProjectile(DragonFireball.class);
					fireball.setShooter(damager);
					Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.7D, damager.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.DRAGON_BREATH, locCF, 60, 0.05, 0.1, 0.05, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(damager.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, (float) 1.25);
					}
					new BukkitRunnable() {
						public void run() {
							playerStuff.remove(damager.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 800L - 50L * level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Dragon's Fireball",
					new ArrayList<String>(Arrays.asList(
						"&7When you right click, you fire the",
						"&7same fireball the ender dragon fires",
						"&7in the direction that you are looking.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listMelee.put("Drain", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 10 + level) {
					if(damager instanceof Player && victim instanceof Player) {
						Player d = (Player) damager;
						Player v = (Player) victim;
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.SPIT, locCF, 50, 0.1, 0.1, 0.1, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_EAT, 2, (float) 0.5);
						}
						if(v.getFoodLevel() - (1 + level) >= 0) {
							v.setFoodLevel(v.getFoodLevel() - (1 + level));
						}
						if(d.getFoodLevel()  + (1 + level) <= 20) {
							d.setFoodLevel(d.getFoodLevel() + (1 + level));
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Drain",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you will drain their stamina, causing them",
						"&7to lose hunger. You will gain the amount of hunger",
						"&7that you stole from the enemy"
					))
				);
			}
		}));
		this.listMelee.put("Energy Ball", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				Location loc = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.8D, damager.getLocation().getZ() + 0D, damager.getLocation().getYaw(), damager.getLocation().getPitch());
				damager.getWorld().spawnParticle(Particle.SPELL_WITCH, loc, 80, 0.15, 0.15, 0.15, 0); 
				damager.getWorld().playSound(damager.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 2, (float) 1.0);
				Location ballLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
				ballLoc.add(loc.getDirection());
				double speed = ballLoc.getDirection().length();
				new BukkitRunnable() {
					int duration = 100 + 20 * level;
					int count = 0;
					public void run() {
						if(count <= duration) {
							count++;
							if(ballLoc.getBlock().getType() == Material.AIR) {
								pApi.sphere(Particle.CRIT_MAGIC, ballLoc, 0.5, 10);
								boolean found = false;
								for(Entity e : ballLoc.getNearbyEntities(10, 10, 10)) {
									if(e instanceof LivingEntity) {
										if(e != damager) {
											found = true;
											LivingEntity ent = (LivingEntity) e;
											ballLoc.add(ballLoc.getDirection().add(ent.getLocation().add(0, 1, 0).subtract(ballLoc).toVector().normalize().multiply(speed / 100 * 175)).normalize().multiply(speed / 100 * 87.5));
											break;
										}
									}
								}
								if(found == false) {
									ballLoc.add(ballLoc.getDirection().multiply(speed));
								}
								for(Entity e : ballLoc.getNearbyEntities(0.5, 0.5, 0.5)) {
									if(e instanceof LivingEntity) {
										if(e != damager) {
											LivingEntity ent = (LivingEntity) e;
											ent.damage(8 + 1.5 * level);
											ent.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, ent.getLocation().add(0, 1, 0), 50, 0.15, 0.15, 0.15, 0.1); 
											ent.getWorld().playSound(ent.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2, (float) 1.0);
											cancel();
											break;
										}
									}
								}
							}
							else {
								cancel();
							}
						}
						else {
							cancel();
						}
					}
				}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 2L);
			}
		}));
		this.listMelee.put("Fatigue", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + 0.5 * level) {
					Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.8D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, locCF1, 80, 0.15, 0.15, 0.15, 0); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 2, (float) 1.0);
					DFPlayer dfPlayer = dfManager.getEntity(victim);
					dfPlayer.removeSpdCal(1.5 * level, 140 + (level * 20));
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Fatigue",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you make them tired, tired from fatigue.",
						"&7Causing their attack speed to drop significantly",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Featherweight", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 9 + level) {
					Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.5D, damager.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(damager.getLocation(), Sound.BLOCK_NOTE_BLOCK_FLUTE, 2, (float) 1);
					}
					int amp = 0 + level;
					int durationAdd = 100 + 40 * level;
					ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.SPEED, PotionEffectType.FAST_DIGGING));
					p.applyEffect(damager, types, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Featherweight",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you will feel as light as a feather, causing you",
						"&7to gain the haste and speed effect for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Final Blow", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 30 + 5 * level) {
					double maxHealth = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
					if(maxHealth < maxHealth * (0.15 + 0.03 * level)) {
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.CRIT, locCF, 50, 0.2, 0.2, 0.2, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_WITHER_SKELETON_HURT, 2, (float) 0.5);
						}
						double damage = event.getDamage();
						event.setDamage(damage * (1.50 + 0.3 * level));
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Final Blow",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you feel like you need to end the fight. Causing the enemy",
						"&7to take extra damage when they are below a certain",
						"&7Amount of HP."
					))
				);
			}
		}));
		this.listMelee.put("Fire Aspect", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 7 + level * 2) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.FLAME, locCF, 60, 0.05, 0.1, 0.05, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, (float) 1.25);
					}
					int duration = victim.getFireTicks();
					victim.setFireTicks(duration + (160 + 80 * level));
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Fire Aspect",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you ignite them in flames, causing them to be",
						"&7on fire for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Cleric", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!cooldown.contains(damager.getUniqueId())) {
					DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(damager);
					DFFaction fac = facManager.getFaction(facPlayer.getFactionId());
					if(fac != null) {
						double range = 5.00 + level * 1.25;
						for(Entity e : damager.getNearbyEntities(range, range, range)) {
							if(e instanceof LivingEntity) {
								LivingEntity ent = (LivingEntity) e;
								if(fac.isMember(ent.getUniqueId())) {
									Location locCF = new Location(ent.getWorld(), ent.getLocation().getX() + 0D, ent.getLocation().getY() + 2.25D, ent.getLocation().getZ() + 0D);
									ent.getWorld().spawnParticle(Particle.HEART, locCF, 60, 0.05, 0.1, 0.05, 0.1); 
									for(Player victim1 : Bukkit.getOnlinePlayers()) {
										((Player) victim1).playSound(ent.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, (float) 1);
									}
									double max = ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
									double heal = 4 + level;
									if(ent.getHealth() + heal <= max) {
										ent.setHealth(ent.getHealth() + heal);
									}
									else {
										ent.setHealth(max);
									}
								}
							}
						}
					}
					else {
						double max = damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
						double heal = 4 + level;
						if(damager.getHealth() + heal <= max) {
							damager.setHealth(damager.getHealth() + heal);
						}
						else {
							damager.setHealth(max);
						}
						Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 2.25D, damager.getLocation().getZ() + 0D);
						damager.getWorld().spawnParticle(Particle.HEART, locCF, 60, 0.05, 0.1, 0.05, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(damager.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, (float) 1);
						}
					}
					cooldown.add(damager.getUniqueId());
					new BukkitRunnable() {
						public void run() {
							cooldown.remove(damager.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 600L - 50 * level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Cleric",
					new ArrayList<String>(Arrays.asList(
						"&7When you right click, you release a healing aura.",
						"&7This causes you to heal nearby faction members",
						"&7and yourself by some HP.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listMelee.put("Freeze", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 4 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, locCF, 50, 0, 0, 0, 4, Material.ICE.createBlockData());
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_GLASS_BREAK, 2, (float) 1);
					}
					int amp1 = 20;
					int amp2 = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 80 + 20 * level;
					PotionEffectType type = PotionEffectType.SLOW;
					PotionEffectType type1 = PotionEffectType.SLOW_DIGGING;
					p.applyEffect(victim, type, amp1, durationAdd);
					p.applyEffect(victim, type1, amp2, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Freeze",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you freeze them in place, causing the enemy",
						"&7to gain a the slowness effect and mining fatigue",
						"&7effect for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Gaster Blasters", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> playerStuff = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!playerStuff.contains(damager.getUniqueId())) {
					playerStuff.add(damager.getUniqueId()); // set to "true"
					ArmorStand stand1 = (ArmorStand) damager.getWorld().spawnEntity(pApi.getLocationRelative(damager.getLocation().add(0, 1.5, 0), 1.5, 0), EntityType.ARMOR_STAND);
					stand1.setVisible(false);
					ArmorStand stand2 = (ArmorStand) damager.getWorld().spawnEntity(pApi.getLocationRelative(damager.getLocation().add(0, 1.5, 0), -1.5, 0), EntityType.ARMOR_STAND);
					stand2.setGravity(false);
					stand1.setGravity(false);
					stand2.setVisible(false);
					Entity e = damager.getTargetEntity(20);
					Location to = damager.getEyeLocation().add(e != null ? damager.getLocation().getDirection().multiply(e.getLocation().distance(damager.getEyeLocation())) : damager.getLocation().getDirection().multiply(20)).subtract(0, 1.5, 0);
					Vector v1 = to.clone().subtract(stand1.getLocation()).toVector().normalize();
					stand1.setHeadPose(stand1.getHeadPose().add(Math.toRadians(damager.getLocation().getPitch()), 0, 0));
					Vector v2 = to.clone().subtract(stand2.getLocation()).toVector().normalize();
					stand2.setHeadPose(stand2.getHeadPose().add(Math.toRadians(damager.getLocation().getPitch()), 0, 0));
					stand1.getEquipment().setHelmet(new ItemStack(Material.WITHER_SKELETON_SKULL, 1));
					stand2.getEquipment().setHelmet(new ItemStack(Material.WITHER_SKELETON_SKULL, 1));
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(damager.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 2, (float) 1.25);
					}
					Location loc1 = stand1.getEyeLocation().clone();
					loc1.setDirection(v1);
					Location loc2 = stand2.getEyeLocation().clone();
					loc2.setDirection(v2);
					ArrayList<Entity> hit = new ArrayList<Entity>();
					new BukkitRunnable() {
						public void run() {
							for(int i = 0; i <= 20; i++) {
								if(loc1.getBlock().getType() == Material.AIR && loc2.getBlock().getType() == Material.AIR && i != 20) {
									loc1.add(loc1.getDirection());
									loc2.add(loc2.getDirection());
									stand1.getWorld().spawnParticle(Particle.REDSTONE, loc1, 15, 0.4, 0.4, 0.4, 0.0, new DustOptions(Color.WHITE, 4.0F));
									stand2.getWorld().spawnParticle(Particle.REDSTONE, loc2, 15, 0.4, 0.4, 0.4, 0.0, new DustOptions(Color.WHITE, 4.0F));
									ArrayList<Entity> entList = new ArrayList<Entity>();
									entList.addAll(loc1.getNearbyEntities(0.5, 0.5, 0.5));
									entList.addAll(loc2.getNearbyEntities(0.5, 0.5, 0.5));
									for(Entity en : entList) {
										if(en != damager && (en != stand1 && en != stand2)) {
											if(en instanceof LivingEntity && !(en instanceof ArmorStand)) {
												LivingEntity ent = (LivingEntity) en;
												if(!facManager.isFriendly(damager, ent)) {
													if(!hit.contains(ent)) {
														hit.add(ent);
														if(damager instanceof Player) {
															ent.setKiller((Player)damager);
														}
														ent.damage(5.0 + level);
														p.applyEffect(ent, PotionEffectType.WITHER, 1, 160 + 40 * level);
													}
												}
											}
										}
									}
								}
								else {
									new BukkitRunnable() {
										public void run() {
											stand1.remove();
											stand2.remove();
										}
									}.runTaskLater(CustomEnchantments.getInstance(), 35L);
									break;
								}
							}
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 20L);
					new BukkitRunnable() {
						public void run() {
							playerStuff.remove(damager.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 1400L - 65L * level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Gaster Blasters",
					new ArrayList<String>(Arrays.asList(
						"&7When you right click, 2 wither skulls",
						"&7spawn at your left and right. After a short",
						"&7time they will fire a beam in the direction",
						"&7that you are looking, any entities caught",
						"&7in this beam will recieve damage and wither.",
						"&7This enchantment has a cooldown"
					))
				);
			}
		}));
		this.listMelee.put("Happiness", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 5 + level * 2) {
					DFPlayer player = dfManager.getEntity(victim);
					if(player.getPlayerClass() == Classes.WRATH) {
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, locCF, 50, 0, 0, 0, 4);
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GHAST_SCREAM, 2, (float) 1.5);
						}
						double extra = level * 5;
						player.addAtkCal(extra, 1);
						new BukkitRunnable() {
							public void run() {
								player.removeAtkCal(extra, 1);
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 1L);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Happiness",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you will deal extra damage against people",
						"&7who have chosen the Wrath class."
					))
				);
			}
		}));
		this.listMelee.put("Head Bash", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 4 + 0.5 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.DAMAGE_INDICATOR, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 2, (float) 0.8);
					}
					int amp = 0 + level;
					int durationAdd = 100 + 20 * level;
					ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.CONFUSION, PotionEffectType.BLINDNESS));
					p.applyEffect(victim, types, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Head Bash",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you strike the enemy very hard on the",
						"&7head, causing them to get the nausea effect",
						"&7and go blind for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Head Hunter", new Pair<>(Condition.PLAYER_DEATH_MELEE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, PlayerDeathEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 50 + 10 * level) {
					Location loc1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.25D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc1, 60, 0, 0.3, 0, 0);
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ZOMBIE_DEATH, 2, (float) 1);
					}
					ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
					SkullMeta sm = (SkullMeta) item.getItemMeta();
					sm.setOwningPlayer((OfflinePlayer)victim);
					item.setItemMeta(sm);
					event.getDrops().add(item);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Head Hunter",
					new ArrayList<String>(Arrays.asList(
						"&7When you kill the enemy, there is a chance",
						"&7that you get the head of the enemy that you",
						"&7killed. This only works on players."
					))
				);
			}
		}));
		this.listMelee.put("Heavy Hand", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 0.5 + 0.5 * level) {
					Location loc1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0.25D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + 0.25D);
					Location loc2 = new Location(victim.getWorld(), victim.getLocation().getX() + 0.5D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + 0.0D);
					Location loc3 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + 0.5D);
				 	Location loc4 = new Location(victim.getWorld(), victim.getLocation().getX() + -0.25D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + 0.25D);
				 	Location loc5 = new Location(victim.getWorld(), victim.getLocation().getX() + -0.5D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + 0D);
				 	Location loc6 = new Location(victim.getWorld(), victim.getLocation().getX() + -0.25D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + -0.25D);
				 	Location loc7 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + -0.5D);
				 	Location loc8 = new Location(victim.getWorld(), victim.getLocation().getX() + 0.25D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + -0.25D);
				 	damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc1, 15, 0, 0.3, 0, 0); 
				 	damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc2, 15, 0, 0.3, 0, 0); 
				 	damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc3, 15, 0, 0.3, 0, 0); 
				 	damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc4, 15, 0, 0.3, 0, 0); 
				 	damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc5, 15, 0, 0.3, 0, 0); 
				 	damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc6, 15, 0, 0.3, 0, 0); 
				 	damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc7, 15, 0, 0.3, 0, 0); 
				 	damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc8, 15, 0, 0.3, 0, 0);
				 	for(Player victim1 : Bukkit.getOnlinePlayers()) {
				 		((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_ANVIL_PLACE, 2, (float) 1);
				 	}
			 		DFPlayer dfPlayer = dfManager.getEntity(damager);
			 		dfPlayer.addAtkCal(25 + level * 25, 1);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Heavy Hand",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you will deal a high amount of damage",
						"&7to the enemy."
					))
				);
			}
		}));
		this.listMelee.put("Hit and Run", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			ArrayList<UUID> list = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				if(!list.contains(damager.getUniqueId())) {
					Location loc1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.25D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc1, 60, 0, 0.3, 0, 0);
					damager.getWorld().playSound(damager.getLocation(), Sound.BLOCK_NOTE_BLOCK_FLUTE, 2.0F, 1.0F);
					DFPlayer dfPlayer = dfManager.getEntity(damager);
					dfPlayer.addMove(dfPlayer.getMove() / 100F * 10F + 2 * level, 80L + 20 * level);
					list.add(damager.getUniqueId());
					new BukkitRunnable() {
						public void run() {
							list.remove(damager.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 600L - 33L * level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Hit and Run",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, you will gain",
						"&7a small movement boost for a few seconds.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listMelee.put("Inner Focus", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			ArrayList<UUID> jump = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!cooldown.contains(damager.getUniqueId())) {
					if(damager.isOnGround()) {
						int duration = 250 + 50 * level;
						int inc = 5 + level;
						DFPlayer dfPlayer = dfManager.getEntity(damager);
						p.applyEffect(damager, PotionEffectType.SLOW, 20, 100);
						cooldown.add(damager.getUniqueId());
						jump.add(damager.getUniqueId());
						new BukkitRunnable() {
							int timer = 100;
							int count = 0;
							public void run() {
								if(count > timer) {
									jump.remove(damager.getUniqueId());
									dfPlayer.addAtk(inc);
									dfPlayer.addSpd(inc);
									dfPlayer.addCrt(inc);
									dfPlayer.addRnd(inc);
									dfPlayer.addHp(inc);
									dfPlayer.addDf(inc);
									new BukkitRunnable() {
										public void run() {
											dfPlayer.removeAtk(inc);
											dfPlayer.removeSpd(inc);
											dfPlayer.removeCrt(inc);
											dfPlayer.removeRnd(inc);
											dfPlayer.removeHp(inc);
											dfPlayer.removeDf(inc);
										}
									}.runTaskLater(CustomEnchantments.getInstance(), duration);
									cancel();
								}
								else {
									count++;
								}
							}
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
						new BukkitRunnable() {
							public void run() {
								cooldown.remove(damager.getUniqueId());
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 100L);
//						.runTaskLater(CustomEnchantments.getInstance(), 1000 - 70 * level);
						
					}
				}
			}
			@EventHandler
			public void noMove(PlayerJumpEvent event) {
				Player player = event.getPlayer();
				if(jump.contains(player.getUniqueId())) {
					event.setCancelled(true);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Inner Focus",
					new ArrayList<String>(Arrays.asList(
						"&7When you right click, you freeze for 5 seconds.",
						"&7You charge all of the energy around you that",
						"&7causes your skills to rise by a certain amount",
						"&7for a few seconds.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listMelee.put("Large Fireball", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> playerStuff = new ArrayList<UUID>();
			HashMap<UUID, Integer> fireball = new HashMap<UUID, Integer>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level) {
				playerStuff.add(damager.getUniqueId()); // set to "true"
				Fireball ball = damager.launchProjectile(Fireball.class);
				fireball.put(ball.getUniqueId(), level);
				Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.7D, damager.getLocation().getZ() + 0D);
				damager.getWorld().spawnParticle(Particle.FLAME, locCF, 60, 0.05, 0.1, 0.05, 0.1); 
				for(Player victim1 : Bukkit.getOnlinePlayers()) {
					((Player) victim1).playSound(damager.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, (float) 1.25);
				}
				new BukkitRunnable() {
					public void run() {
						playerStuff.remove(damager.getUniqueId());
					}
				}.runTaskLater(CustomEnchantments.getInstance(), 800L - 50L * level);
			}
			@EventHandler
			public void LargeFireballBall(ProjectileHitEvent event){
		        if(event.getEntity() instanceof Fireball){
		            Fireball f = (Fireball) event.getEntity();
		            if(fireball.containsKey(f.getUniqueId())) {
		            	LivingEntity shooter = (LivingEntity) f.getShooter();
                		Location locCF = new Location(f.getWorld(), f.getLocation().getX() + 0D, f.getLocation().getY() + 1.7D, f.getLocation().getZ() + 0D);
						f.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, locCF, 60, 2, 2, 2, 1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(f.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, (float) 1.25);
						}
						int level = fireball.get(f.getUniqueId());
						double range = 4 + level;
						for(Entity en : f.getWorld().getNearbyEntities(locCF, range, range, range)){
							if(en instanceof LivingEntity) {
								LivingEntity nearby = (LivingEntity) en;
								if(shooter instanceof Player) {
									nearby.setKiller((Player)shooter);
								}
								nearby.damage(10 + 1.5 * level - (f.getLocation().distance(nearby.getLocation())));
							}
						}
		            }
		        }
		    }
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Large Fireball",
					new ArrayList<String>(Arrays.asList(
						"&7When you right click, you fire a",
						"&7large fireball in the direction that you",
						"&7are looking. When the fireball lands, it",
						"&7will deal damage to enemies who are in the",
						"&7blast radius of the fireball. Enemies directly",
						"&7hit by the fireball will recieve more damage.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listMelee.put("Levitate", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + 1.5 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.2D, victim.getLocation().getZ() + 0D);
					double red = 255 / 255D;
					double green = 255 / 255D;
					double blue = 255 / 255D;
					victim.getWorld().spawnParticle(Particle.SPELL, locCF, 50, red, green, blue, 1);
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 2, (float) 1);
					}
					int amp = 0 + level;
					int durationAdd = 100 + 20 * level;
					p.applyEffect(victim, PotionEffectType.LEVITATION, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Levitate",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you apply the levitation effect to",
						"&7the enemy. Causing them to go flying in the",
						"&7sky and falling down shortly after."
					))
				);
			}
		}));
		this.listMelee.put("Lifesteal", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 70 + 10 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, locCF, 40, 0.2, 0.2, 0.2, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 2, (float) 1);
					}
					double heal = damager.getHealth() + 1.00 + 1.00 * level;
					double attribute = damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
					if(heal <= attribute) {
						damager.setHealth(heal);
					}
					else {
						damager.setHealth(attribute);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Lifesteal",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you will gain some health."
					))
				);
			}
		}));
		this.listMelee.put("Lightning Thrust", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			HashMap<UUID, Pair<Integer, ItemStack>> activated = new HashMap<UUID, Pair<Integer, ItemStack>>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!cooldown.contains(damager.getUniqueId())) {
					damager.getWorld().strikeLightningEffect(damager.getLocation());
					damager.setVelocity(new Vector(0, 0.5, 0));
					new BukkitRunnable() {
						public void run() {
							damager.getWorld().playSound(damager.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 2, (float) 1.25);
							damager.setVelocity(damager.getLocation().getDirection().multiply(1.25F + 0.25F * level));
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 5L);
					cooldown.add(damager.getUniqueId());
					activated.put(damager.getUniqueId(), new Pair<Integer, ItemStack>(level, damager.getEquipment().getItemInMainHand()));
					new BukkitRunnable() {
						public void run() {
							if(activated.containsKey(damager.getUniqueId())) {
								activated.remove(damager.getUniqueId());
							}
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 55L);
					new BukkitRunnable() {
						public void run() {
							cooldown.remove(damager.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 1150L - 50 * level);
				}
			}
			@EventHandler
			public void lightningThrust(EntityDamageByEntityEvent event) {
				if(!event.isCancelled()) {
					if(event.getDamager() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
						LivingEntity damager = (LivingEntity) event.getDamager();
						LivingEntity victim = (LivingEntity) event.getEntity();
						if(activated.containsKey(damager.getUniqueId())) {
							Pair<Integer, ItemStack> pair = activated.get(damager.getUniqueId());
							if(pair.getValue().equals(damager.getEquipment().getItemInMainHand())) {
								victim.getWorld().strikeLightningEffect(victim.getLocation());
								p.applyEffect(victim, new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.SLOW_DIGGING, PotionEffectType.SLOW)), 1, 200 + pair.getKey());
								activated.remove(damager.getUniqueId());
								new BukkitRunnable() {
									int count = 0;
									int timer = 60 + 20 * pair.getKey();
									public void run() {
										if(count <= timer) {
											victim.damage(0.03 + 0.03 * pair.getKey());
											victim.setNoDamageTicks(0);
											count++;
										}
										else {
											cancel();
										}
									}
								}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
							}
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Lightning Thrust",
					new ArrayList<String>(Arrays.asList(
						"&7When you right click, you charge your weapon",
						"&7with electric energy, you then launch up and in",
						"&7the direction that you are looking. When you hit",
						"&7the enemy with your weapon, you give the enemy slowness,",
						"&7mining fatigue and a screen shake that also damages them."
					))
				);
			}
		}));
		this.listMelee.put("Looting", new Pair<>(Condition.ENTITY_DEATH_MELEE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDeathEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 30 + 10 * level) {
					if(!(victim instanceof Player) && (victim instanceof Monster || victim instanceof Animals)) {
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, locCF, 40, 0.2, 0.2, 0.2, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, (float) 1);
						}
						List<ItemStack> stacks = event.getDrops();
						for(int i1 = 0; i1 < stacks.size(); i1++) {
							if(stacks.get(i1).getMaxStackSize() != 1) {
								stacks.get(i1).setAmount((int)(stacks.get(i1).getAmount() * (1.25 + 0.25 * level)));
							}
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Looting",
					new ArrayList<String>(Arrays.asList(
						"&7When you kill the enemy, there is a chance",
						"&7that they will drop extra loot. This only works",
						"&7on mobs."
					))
				);
			}
		}));
		this.listMelee.put("Negative Hollow", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 3 + 1 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.4D, victim.getLocation().getZ() + 0D);
					DustOptions op = new DustOptions(Color.fromRGB(0, 0, 0), 1.5F);
					victim.getWorld().spawnParticle(Particle.REDSTONE, locCF, 60, op);
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 2, (float) 1);
					}
					victim.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					victim.removePotionEffect(PotionEffectType.SPEED);
					victim.removePotionEffect(PotionEffectType.SATURATION);
					victim.removePotionEffect(PotionEffectType.JUMP);
					victim.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
					victim.removePotionEffect(PotionEffectType.REGENERATION);
					victim.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					victim.removePotionEffect(PotionEffectType.FAST_DIGGING);
					victim.removePotionEffect(PotionEffectType.ABSORPTION);
					victim.removePotionEffect(PotionEffectType.HEALTH_BOOST);
					victim.removePotionEffect(PotionEffectType.NIGHT_VISION);
					victim.removePotionEffect(PotionEffectType.WATER_BREATHING);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Negative Hollow",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you will remove all of their positive",
						"&7potion effects."
					))
				);
			}
		}));
		this.listMelee.put("Numbness", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + 0.5 * level) {
					Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.8D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, locCF1, 80, 0.15, 0.15, 0.15, 0, Material.PINK_WOOL.createBlockData()); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_ARROW_SHOOT, 2, (float) 0.5);
					DFPlayer dfPlayer = dfManager.getEntity(victim);
					dfPlayer.removeRndCal(10.0 * level, 140 + (level * 40));
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Numbness",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you strike the enemy in the arms",
						"&7the numbness in their arms causes their",
						"&7Ranged damage to decrease."
					))
				);
			}
		}));
		this.listMelee.put("Paralyze", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 3 + level) {
					Location location = victim.getLocation();
			        location.getWorld().strikeLightningEffect(location);
					victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100 + 20 * level, 20));
					victim.getWorld().strikeLightningEffect(victim.getLocation());
					new BukkitRunnable() {
						int count = 0;
						public void run() {
							if(count < 100 + 20 * level) {
								count++;
								if(damager instanceof Player) {
									victim.setKiller((Player)damager);
								}
								victim.damage(0.05 + 0.05 * level);
							}
							else {
								cancel();
								count = 0;
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Paralyze",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that a lightning strike will strike down",
						"&7on the enemy causing them to freeze in their",
						"&7position and have their screen shake."
					))
				);
			}
		}));
		this.listMelee.put("Perfect Slash", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 30 + 10 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.CRIT_MAGIC, locCF, 50, 0.2, 0.2, 0.2, 0.1);
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 2, (float) 1.5);
					}
					event.setCancelled(false);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Perfect Slash",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that if they block or dodge the attack, that",
						"&7it will be negated."
					))
				);
			}
		}));
		this.listMelee.put("Phantom", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> list = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level) {
				if(!list.contains(damager.getUniqueId())) {
					Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.5D, damager.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, locCF, 50, 0.2, 0.2, 0.2, 0.2);
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(damager.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 2, (float) 0.5);
					}
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(p != null) {
							if(damager instanceof Player) {
								p.hidePlayer(CustomEnchantments.getInstance(), (Player) damager);
							}
							else {
								break;
							}
						}
					}
					list.add(damager.getUniqueId());
					new BukkitRunnable() {
						public void run() {
							for(Player p : Bukkit.getOnlinePlayers()) {
								if(p != null) {
									if(damager instanceof Player) {
										p.showPlayer(CustomEnchantments.getInstance(), (Player) damager);
									}
									else {
										break;
									}
								}
							}
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 100L + 30L * level);
					new BukkitRunnable() {
						public void run() {
							list.remove(damager.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 1200L - 50L * level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Phantom",
					new ArrayList<String>(Arrays.asList(
						"&7When you right click, you will go",
						"&7completely invisible for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Pickpocket", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 0.5 + 0.5 * level) {
					if(victim instanceof Player && damager instanceof Player) {
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
						damager.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, locCF, 30, 0.1, 0.1, 0.1);
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, (float) 1.25);
						}
						DFPlayer dfPlayer = dfManager.getEntity(damager);
						DFPlayer dfVictim = dfManager.getEntity(victim);
						double tempMoney = (double) (dfVictim.getMoney() * (0.01 + 0.01 * level));
						dfPlayer.addMoney(tempMoney);
						dfVictim.removeMoney(tempMoney);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Pickpocket",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you will steal some of their balance."
					))
				);
			}
		}));
		this.listMelee.put("Pinch", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 3 + 0.5 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.CRIT_MAGIC, locCF, 20, 0.05, 0.05, 0.05, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_SPIDER_AMBIENT, 2, (float) 1.25);
					}
					new BukkitRunnable() {
						int count = 10 + 2 * level;
						public void run() {
							count--;
							if(count == 0) {
								cancel();
							}
							else {
								if(damager instanceof Player) {
									victim.setKiller((Player)damager);
								}
								victim.damage(0.5 + 0.5 * level);
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 40 - 5 * level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Pinch",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that a pinch will be constantly done to them.",
						"&7Causing them to recieve continuous damage",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Poison", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.SLIME, locCF, 20, 0.05, 0.3, 0.05, 0.1);
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_SPIDER_AMBIENT, 2, (float) 1.25);
					}
					int amp = 0 + level;
					int durationAdd = 160 + 40 * level;
					p.applyEffect(victim, PotionEffectType.POISON, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Poison",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7you infect a deadly poison into them causing",
						"&7them to recieve the poison effect for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Rampage", new Pair<>(Condition.ENTITY_DEATH_MELEE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDeathEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(victim instanceof Player) {
					i = 0;
				}
				if(i <= 25 + 15 * level) {
					Location loc1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.25D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.REDSTONE, loc1, 60, 0.1, 0.1, 0.1, 0.1, new DustOptions(Color.fromRGB(255, 0, 0), 1.0F));
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 2, (float) 1.25);
					}
					DFPlayer dfPlayer = dfManager.getEntity(damager.getUniqueId());
					dfPlayer.addAtkCal(2.5 + 2.5 * level, 100 + 20 * level);
					dfPlayer.addSpdCal(0.5 + 0.5 * level, 100 + 20 * level);
					dfPlayer.addMove(0.005F + 0.005F * level, 100 + 20 * level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Rampage",
					new ArrayList<String>(Arrays.asList(
						"&7When you kill the enemy, there is a chance",
						"&7that your attack damage, attack speed and",
						"&7movement speed are increased for a few seconds.",
						"&7if the enemy is a player, the chance of this",
						"&7enchantment activating is increased to 100%."
					))
				);
			}
		}));
		this.listMelee.put("Sharpness", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				event.setDamage(event.getDamage() + 0.40 + 0.40 * level);
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Sharpness",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, you deal extra damage."
					))
				);
			}
		}));
		this.listMelee.put("Sky High", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 2.5 + 1 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, locCF, 80, 0, 0, 0, 0.2);
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2, (float) 1);
					}
					new BukkitRunnable() {
						  public void run() {
							  victim.setVelocity(new Vector(0, 1.5 + 0.3 * level, 0));
						  }
					}.runTaskLater(CustomEnchantments.getInstance(), 1L);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Sky High",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you will launch them high in the sky."
					))
				);
			}
		}));
		this.listMelee.put("Slow", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 8 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.SMOKE_LARGE, locCF, 50, 0.2, 0.2, 0.2, 0.1);
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 2, (float) 0.9);
					}
					int amp = 0 + level;
					int durationAdd = 120 + 40 * level;
					p.applyEffect(victim, PotionEffectType.SLOW, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Slow",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that they will feel slower movement in their",
						"&7legs. Causing them to recieve the slowness",
						"&7effect for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Snowball", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			HashMap<UUID, Integer> snowball = new HashMap<UUID, Integer>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!cooldown.contains(damager.getUniqueId())) {
					Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.7D, damager.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.SNOW_SHOVEL, locCF, 60, 0.05, 0.05, 0.05, 0.1); 
					damager.getWorld().playSound(damager.getLocation(), Sound.ENTITY_EGG_THROW, 2, (float) 1);
					Snowball ball = damager.launchProjectile(Snowball.class);
					snowball.put(ball.getUniqueId(), level);
					cooldown.add(damager.getUniqueId());
					new BukkitRunnable() {
						public void run() {
							cooldown.remove(damager.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 200L - 16L * level);
				}
			} 
			@EventHandler
			public void snowballHit(ProjectileHitEvent event) {
				if(event.getHitEntity() != null && event.getEntity() != null && event.getEntity().getShooter() instanceof LivingEntity) {
					if(event.getHitEntity() instanceof LivingEntity && event.getEntity().getShooter() instanceof LivingEntity) {
						Projectile pro = event.getEntity();
						if(pro.getShooter() instanceof LivingEntity) {
							LivingEntity shooter = (LivingEntity) pro.getShooter();
							if(snowball.containsKey(pro.getUniqueId())) {
								int level = snowball.get(pro.getUniqueId());
								snowball.remove(pro.getUniqueId());
								LivingEntity ent = (LivingEntity) event.getHitEntity();
								int amp = (int)Math.floor(0 + (level) / 2);
								int durationAdd = 100 + 20 * level;
								ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.SLOW, PotionEffectType.BLINDNESS));
								p.applyEffect(ent, types, amp, durationAdd);
								if(shooter instanceof Player) {
									ent.setKiller((Player)shooter);
								}
								ent.damage(1.5 + 0.5 * level);
							}
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Snowball",
					new ArrayList<String>(Arrays.asList(
						"&7When you right click, you fire a snowball.",
						"&7The snowball travels fast through the air, due to",
						"&7it's velocity and coldness, any enemy that gets",
						"&7hit by this snowball will recieve a small amount",
						"&7damage and will gain the slowness effect for a few seconds",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listMelee.put("Soul Burst", new Pair<>(Condition.ENTITY_DEATH_MELEE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 20 + 5.0 * level) {
					Location loc1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.50D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, loc1, 60, 0.1, 0.1, 0.1, 0.1);
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_ZOMBIE_DEATH, 2, (float) 0.5);
					double range = 6 + 1.5 * level;
					for(Entity e : victim.getNearbyEntities(range, range, range)) {
						if(e instanceof LivingEntity) {
							if(e != damager) {
								LivingEntity entity = (LivingEntity) e;
								if(damager instanceof Player) {
									entity.setKiller((Player)damager);
								}
								entity.damage(6 + 2.5 * level);
							}
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Soul Burst",
					new ArrayList<String>(Arrays.asList(
						"&7When you kill the enemy, there is a chance",
						"&7that their soul will burst. This burst will",
						"&7deal damage to nearby enemies."
					))
				);
			}
		}));
		this.listMelee.put("Reaper", new Pair<>(Condition.ENTITY_DEATH_MELEE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 40 + 10 * level) {
					Location loc1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.25D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc1, 60, 0, 0.3, 0, 0, Material.NETHER_BRICK.createBlockData()); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_WITHER_HURT, 2, (float) 0.5);
					}
					int amp = 0 + level;
					int durationAdd = 150 + 50 * level;
					ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.REGENERATION, PotionEffectType.INCREASE_DAMAGE));
					p.applyEffect(damager, types, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Reaper",
					new ArrayList<String>(Arrays.asList(
						"&7When you kill the enemy, there is a chance",
						"&7you feel their power flowing within you.",
						"&7Causing you to recieve the strength and regeneration",
						"&7effect for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Spectral", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 12 + 2 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.END_ROD, locCF, 50, 0.1, 0.1, 0.1, 0.1);
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 2, (float) 1);
					}
					int amp = 0;
					int durationAdd = 250 + 75 * level;
					p.applyEffect(victim, PotionEffectType.GLOWING, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Spectral",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you leave a trail on them for you to follow.",
						"&7Causing them to recieve the glowing effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Star Shine", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			HashMap<UUID, Integer> star = new HashMap<UUID, Integer>();
			ArrayList<UUID> list = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!list.contains(damager.getUniqueId())) {
					list.add(damager.getUniqueId());
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(damager.getLocation(), Sound.ENTITY_BLAZE_DEATH, 2, (float) 1.5);
					}
					new BukkitRunnable() {
						  public void run() {
							  list.remove(damager.getUniqueId()); // set to "false"
						  }
					}.runTaskLater(CustomEnchantments.getInstance(), 800 - 50 * level);
					p.applyEffect(damager, PotionEffectType.SPEED, (int)(level * 0.5), 60 + 2 * level);
					new BukkitRunnable() {
						int counter = 60 + 2 * level;
						@Override
						public void run() {
							Location locCF1 = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1D, damager.getLocation().getZ() + 0D);
							damager.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, locCF1, 60, 0.2, 0.2, 0.2, 0); 
							counter--;
							if(counter == 0) {
								cancel();
								star.remove(damager.getUniqueId());
							}
							else if(!star.containsKey(damager.getUniqueId())) {
								cancel();
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
				}
			}
			@EventHandler
			public void event(EntityDamageByEntityEvent event) {
				if(star.containsKey(event.getDamager().getUniqueId())) {
					event.setDamage(2.5 + star.get(event.getDamager().getUniqueId()) * 2.5);
					star.remove(event.getDamager().getUniqueId());
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Star Shine",
					new ArrayList<String>(Arrays.asList(
						"&7When you right click, star dust will shine off you.",
						"&7You recieve the speed effect for a few seconds.",
						"&7While the speed effect is active and you attack",
						"&7the enemy, you deal extra damage. However if you",
						"&7decide to attack the enemy, the speed effect",
						"&7will be removed after the attack.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listMelee.put("Disturb Health", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + 0.5 * level) {
					Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.8D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.DAMAGE_INDICATOR, locCF1, 80, 0.15, 0.15, 0.15, 0); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_COW_HURT, 2, (float) 0.5);
					DFPlayer dfPlayer = dfManager.getEntity(victim);
					dfPlayer.removeHpCal(12.5 + 12.5 * level, 140 + (level * 20));
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Disturb Health",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that their wellbeing will be disturbed. Causing",
						"&7their health skill to reduce significantly for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Unlucky", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + 0.5 * level) {
					Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.8D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.CRIT_MAGIC, locCF1, 80, 0.15, 0.15, 0.15, 0); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 2, (float) 0.5);
					DFPlayer dfPlayer = dfManager.getEntity(victim);
					dfPlayer.removeCrtCal(3.0 + 3.0 * level, 100 + (level * 20));
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Unlucky",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that the enemy's luck will drop. Causing their",
						"&7critical chance skill to reduce significantly for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Vampirism", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 16 + 2 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 2, (float) 1);
					}
					double damageTotal = event.getDamage();
					if((damager.getHealth() + (damageTotal * (0.10 + 0.05 * level)) <= damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue())) {
						double health = victim.getHealth();
						damager.setHealth(health + (damageTotal * (0.10 + 0.05 * level))); 
					}
					else {
						damager.setHealth(damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Vampirism",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you will drain some health from the damage",
						"&7you deal to the enemy."
					))
				);
			}
		}));
		this.listMelee.put("Venom", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 3 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.DRIP_WATER, locCF, 50, 0.2, 0.2, 0.2, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_SPIDER_HURT, 2, (float) 0.5);
					}
					int amp = 0;
					int durationAdd = 600 + 300 * level;
					p.applyEffect(victim, PotionEffectType.POISON, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Venom",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you inject them with a deadly venom. Causing the",
						"&7enemy to recieve a long lasting poison effect."
					))
				);
			}
		}));
		this.listMelee.put("Weaken", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + 0.5 * level) {
					Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.8D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, locCF1, 80, 0.15, 0.15, 0.15, 0, Material.BLACK_WOOL.createBlockData()); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 2, (float) 1.0);
					DFPlayer dfPlayer = dfManager.getEntity(victim);
					dfPlayer.removeAtkCal(4.5 + 4.5 * level, 140 + (level * 20));
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Weaken",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that their overall power will decrease. Causing their",
						"&7attack damage skill to reduce significantly for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Weakness", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + level) {
					Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2.5D, victim.getLocation().getZ() + 0D);
					BlockData block = Material.COAL_BLOCK.createBlockData();
					victim.getWorld().spawnParticle(Particle.FALLING_DUST, locCF1, 80, 0.15, 0.15, 0.15, 0, block); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_SKELETON_HURT, 2, (float) 1.1);
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 120 + 40 * level;
					p.applyEffect(victim, PotionEffectType.WEAKNESS, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Weakness",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that you will weaken the enemy. Causing the enemy",
						"&7to recieve the weakness effect for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Wither", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 4 + level * 1.5) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.SMOKE_NORMAL, locCF, 60, 0, 0, 0, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_WITHER_SHOOT, 2, (float) 1);
					}
					int amp = 0 + level;
					int durationAdd = 140 + 50;
					p.applyEffect(victim, PotionEffectType.WITHER, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Wither",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that they will start to wither away. Causing them",
						"&7to recieve the wither effect for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Wizard", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 3 + level * 0.5) {	
					Location location = victim.getLocation();
			        location.getWorld().strikeLightningEffect(location);
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 100 + 20 * level;
					p.applyEffect(victim, PotionEffectType.WEAKNESS, amp, durationAdd);
					if(damager instanceof Player) {
						victim.setKiller((Player)damager);
					}
					victim.damage(4 + level);
					victim.setFireTicks(victim.getFireTicks() + (100 + 20 * level));
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Wizard",
					new ArrayList<String>(Arrays.asList(
						"&7When you attack the enemy, there is a chance",
						"&7that a lightning strike will strike upon them.",
						"&7Causing to recieve extra damage, set them on fire and",
						"&7recieve the weakness effect for a few seconds."
					))
				);
			}
		}));
		this.listMelee.put("Wolf Pack", new Pair<>(Condition.PLAYER_DEATH_MELEE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 20 + 5 * level) {
					Location loc1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.25D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc1, 60, 0, 0.3, 0, 0); 
					DFPlayer dfPlayer = dfManager.getEntity(damager);
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_WOLF_HOWL, 2, (float) 1);
					}
					Wolf wolf = (Wolf) damager.getWorld().spawnEntity(victim.getLocation(), EntityType.WOLF);
					DFPlayer dfWolf = new DFPlayer(wolf);
					dfWolf.setLevel(dfPlayer.getLevel());
					wolf.setCustomName(new CCT().colorize("&a&lWolf &6[&aLv " + dfWolf.getLevel() + "&6]"));
					wolf.setCustomNameVisible(true);
					wolf.setOwner((AnimalTamer)damager);
					wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damager.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
					dfWolf.setAtk(dfPlayer.getAtk());
					dfWolf.setCrt(dfPlayer.getCrt());
					dfWolf.setDf(dfPlayer.getDf());
					dfWolf.setHp(dfPlayer.getHp());
					dfWolf.changeHealth();
					wolf.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(damager.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue());
					wolf.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(damager.getAttribute(Attribute.GENERIC_ARMOR).getValue());
					wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(damager.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue());
					new BukkitRunnable() {
						public void run() {
							wolf.remove();
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 600L + 50 * level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Wolf Pack",
					new ArrayList<String>(Arrays.asList(
						"&7When you kill the enemy, there is a chance",
						"&7that you will summon a wolf at the enemy's location.",
						"&7This wolf will recieve the same skills that you currently have.",
						"&7This enchantment will only activate when a player is killed.",
						"&7This wolf will despawn after a certain amount of time."
					))
				);
			}
		}));
		this.listMelee.put("XP Boost", new Pair<>(Condition.ENTITY_DEATH_MELEE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, int level, DFPlayerXpGainEvent event) {
				if(!(damager instanceof Player) && (damager instanceof Monster || damager instanceof Animals)) {
					event.setXPMultiplier(event.getXPMultiplier() + 7.5 + 7.5 * level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6XP Boost",
					new ArrayList<String>(Arrays.asList(
						"&7Earn Player XP at a faster rate when",
						"&7killing mobs."
					))
				);
			}
		}));
	}
	public void loadArmorEnchantments() {
		this.listArmor = new HashMap<String, Pair<Condition, CommandFile>>();
		this.listArmor.put("Absorbing", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 15 + 3 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.CRIT_MAGIC, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 2, (float) 1.4);
					}
					int amp = 0;
					int durationAdd = 80 + 10 * level;
					ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.ABSORPTION));
					p.applyEffect(victim, types, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Absorbing",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you will start to learn on how to absorb the damage.",
						"&7Causing you to recieve the absorption effect."
					))
				);
			}
		}));
		this.listArmor.put("Absorbing Combo", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			HashMap<UUID, Integer> list = new HashMap<UUID, Integer>();
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				if(!list.containsKey(victim.getUniqueId())) {
					list.put(victim.getUniqueId(), 1);
				}
				list.put(victim.getUniqueId(), list.get(victim.getUniqueId()) + 1);
				if(list.get(victim.getUniqueId()) == 8 - level) {
					EntityLiving l = ((CraftLivingEntity)victim).getHandle();
					if(l.getAbsorptionHearts() + + 3.00F + 1.50F * level > 40.00) {
						l.setAbsorptionHearts(40.00F);
					}
					else {
						l.setAbsorptionHearts(l.getAbsorptionHearts() + 3.00F + 1.50F * level);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Absorbing Combo",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you multiple times,",
						"&7You gain a certain amount of absorption hearts."
					))
				);
			}
		}));
		this.listArmor.put("Absorbing Comeback", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			HashMap<UUID, Double> extraDamage = new HashMap<UUID, Double>();
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 100/*7 + level*/) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.CRIT_MAGIC, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH, 2, (float) 1.3);
					}
					extraDamage.put(victim.getUniqueId(), event.getDamage() * (0.20 + 0.075 * level));
				}
			}
			@EventHandler
			public void attack(EntityDamageByEntityEvent event) {
				if(extraDamage.containsKey(event.getDamager().getUniqueId())) {
					event.setDamage(event.getDamage() + extraDamage.get(event.getDamager().getUniqueId()));
					extraDamage.remove(event.getDamager().getUniqueId());
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Absorbing Comeback",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7for you to absorb a part of the incoming damage",
						"&7and return that damage in the next time that you attack."
					))
				);
			}
		}));
		this.listArmor.put("Adrenaline Rush", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				DFPlayer dfPlayer = dfManager.getEntity(victim);
				if(dfPlayer.getHealth() <= dfPlayer.getMaxHealth() / 100 * 25) {
					if(i <= 15 + 5 * level) {
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.CLOUD, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_RABBIT_HURT, 2, (float) 1);
						}
						int amp = 2;
						int durationAdd = 130 + 20 * level;
						ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.JUMP, PotionEffectType.SPEED));
						p.applyEffect(victim, types, amp, durationAdd);
						dfPlayer.addSpdCal(4.0 + 4.0 * level, 130 + 20 * level);
						cooldown.add(victim.getUniqueId());
						new BukkitRunnable() {
							public void run() {
								cooldown.remove(victim.getUniqueId());
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 1200L - 50L * level);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Adrenaline Rush",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you and you are below",
						"&7a certain percentage of your current health, there",
						"&7is a chance that you will get an Adrenaline Rush.",
						"&7Causing you to recieve the jump and speed boost effect",
						"&7for a few seconds.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listArmor.put("Arcanist Explosion", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 13 + 2 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, locCF, 10, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, (float) 1);
					}
					double range = 3 + 0.5 * level;
					for(Entity entity : victim.getNearbyEntities(range, range, range)){
						if(entity instanceof LivingEntity) {
							if(entity != victim) {
								LivingEntity entity1 = (LivingEntity) entity;
								if(victim instanceof Player) {
									entity1.setKiller((Player)victim);
								}
								entity1.damage(2.00 + 0.50 * level);
							}
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Arcanist Explosion",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that a small explosion will generate from your location.",
						"&7Causing nearby enemies to recieve damage."
					))
				);
			}
		}));
		this.listArmor.put("Archery", new Pair<>(Condition.ARMOR_CHANGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, int level, boolean equiped, PlayerArmorChangeEvent event) {
				if(dfManager.contains(damager)) {
					DFPlayer dfPlayer = dfManager.getEntity(damager);
					if(equiped == true) {
						dfPlayer.addRndCal(3.0 + 3.0 * level, 0);
					}
					else if(equiped == false) {
						dfPlayer.removeRndCal(3.0 + 3.0 * level, 0);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Archery",
					new ArrayList<String>(Arrays.asList(
						"&7When you equip armor, you get more knowledge",
						"&7about ranged weapons. Causing your Ranged Damage",
						"&7skill to rise."
					))
				);
			}
		}));
		this.listArmor.put("Bane", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 7 + level) {
					Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.5D, damager.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.DRIP_WATER, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 2, (float) 0.6);
					}
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 150 + 25 * level;
					ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.SLOW_DIGGING));
					p.applyEffect(victim, types, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Bane",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that a fatigue curse will be put on them.",
						"&7Causing them to recieve the minin fatigue effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Blast Off", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 9 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.CRIT_MAGIC, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 2, (float) 1.4);
					}
					int amp = 20;
					int durationAdd = 150 + 15 * level;
					ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.DAMAGE_RESISTANCE));
					p.applyEffect(victim, types, amp, durationAdd);
					new BukkitRunnable() {
						@Override
						public void run() {
							victim.setVelocity(new Vector(0, 1.2 + 0.2 * level, 0));
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 1L);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Blast Off",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you create an explosion. Launching you high",
						"&7in the sky. You will be unable to recieve any",
						"&7damage for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Cactus", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 11 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.CRIT, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_HURT, 2, (float) 1.1);
					}
					if(victim instanceof Player) {
						damager.setKiller((Player)victim);
					}
					damager.damage(1 + level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Cactus",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that a small needle will puncture the enemy.",
						"&7Causing them to recieve damage."
					))
				);
			}
		}));
		this.listArmor.put("Curse", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			HashMap<UUID, UUID> cursed = new HashMap<UUID, UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 0.1 + 0.1 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 2, (float) 0.5);
					cursed.put(victim.getUniqueId(), damager.getUniqueId());
					new BukkitRunnable() {
						public void run() {
							if(cursed.containsKey(victim.getUniqueId())) {
								if(victim instanceof Player) {
									damager.setKiller((Player)victim);
								}
								damager.damage(Double.MAX_VALUE);
								cursed.remove(damager.getUniqueId());
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 600 - 40 * level);
				}
			}
			@EventHandler
			public void cancelCurse(EntityDamageByEntityEvent event) {
				if(!event.isCancelled()) {
					if(event.getDamager() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
						LivingEntity victim = (LivingEntity) event.getEntity();
						LivingEntity damager = (LivingEntity) event.getDamager();
						if(cursed.containsKey(victim.getUniqueId()) && cursed.get(victim.getUniqueId()).equals(damager.getUniqueId())) {
							cursed.remove(victim.getUniqueId());
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Curse",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you curse them to death. Causing them to be",
						"&7put under a curse that kills the enemy after a while.",
						"&7To break the curse, the attacker needs to damage",
						"&7you at least one time to break the curse."
					))
				);
			}
		}));
		this.listArmor.put("Dodge", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 1.5 + 1.5 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.CLOUD, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2, (float) 1.5);
					event.setCancelled(true);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Dodge",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you evade the attack. Causing you to recieve",
						"&7no damage at all."
					))
				);
			}
		}));
		this.listArmor.put("Full Counter", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 0.25 + level * 0.25) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_HURT, 2, (float) 1.5);
					}
					double damage = event.getDamage();
					if(victim instanceof Player) {
						damager.setKiller((Player)victim);
					}
					damager.damage(damage);
					event.setCancelled(true);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Full Counter",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you return all of the damage back to",
						"&7the attacker."
					))
				);
			}
		}));
		this.listArmor.put("Falling Absorption", new Pair<>(Condition.ENTITY_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity victim, int level, EntityDamageEvent event) {
				Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
				victim.getWorld().spawnParticle(Particle.END_ROD, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
				for(Player victim1 : Bukkit.getOnlinePlayers()) {
					((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_HURT, 2, (float) 1.5);
				}
				EntityLiving l = ((CraftLivingEntity)victim).getHandle();
				float newHearts = l.getAbsorptionHearts() + (float) event.getDamage() / 100F * (10F + 5F * level);
				if(newHearts <= 40) {
					l.setAbsorptionHearts(newHearts);
				}
				else {
					l.setAbsorptionHearts(40F);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Falling Absorption",
					new ArrayList<String>(Arrays.asList(
						"&7When you recieve fall damage, you convert",
						"&7a portion of the damage that you recieved to",
						"&7absorption hearts."
					))
				);
			}
		}));
		this.listArmor.put("Earthquake", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 1.5 + level * 0.25) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.2D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, locCF, 60, 0.1, 0.1, 0.1, 0.1, Material.GRASS_BLOCK.createBlockData()); 
					victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_GRASS_BREAK, 2, (float) 1);
					double range = 8.50 + level * 1.50;
					for(Entity e : victim.getNearbyEntities(range, range, range)) {
						if(e instanceof LivingEntity) {
							LivingEntity ent = (LivingEntity) e;
							if(victim instanceof Player) {
								ent.setKiller((Player)victim);
							}
							ent.damage(3 + level * 3);
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Earthquake",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that your rage of being hit will turn into",
						"&7an Earthquake. Causing nearby enemies to",
						"&7recieve damage."
					))
				);
			}
		}));
		this.listArmor.put("Empower", new Pair<>(Condition.ARMOR_CHANGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, int level, boolean equiped, PlayerArmorChangeEvent event) {
				if(dfManager.contains(damager)) {
					DFPlayer dfPlayer = dfManager.getEntity(damager);
					if(equiped == true) {
						dfPlayer.addAtkCal(2.25 + 2.25 * level, 0);
					}
					else if(equiped == false) {
						dfPlayer.removeAtkCal(2.25 + 2.25 * level, 0);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Empower",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you evade the attack. Causing you to recieve",
						"&7Causing nearby enemies to recieve damage."
					))
				);
			}
		}));
		this.listArmor.put("Enlightened", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 7 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.HEART, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, (float) 1.1);
					}
					double heal = victim.getHealth() + (1.5 + 1.5 * level);
					double attribute = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
					if(heal < attribute) {
						victim.setHealth(heal);
					}
					else {
						victim.setHealth(attribute);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Enlightened",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you get blessed by an unkown force. Causing",
						"&7to regenerate health instantly."
					))
				);
			}
		}));
		this.listArmor.put("Escape", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 13 + level) {
					if(victim.getHealth() <= 12 + 2 * level) {
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.2D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.SPELL, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_CHICKEN_HURT, 2, (float) 1.1);
						}
						int amp = 0 + level;
						int durationAdd = 200 + 50 * level;
						ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.SPEED, PotionEffectType.ABSORPTION));
						p.applyEffect(victim, types, amp, durationAdd);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Escape",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you and you are below a",
						"&7certain amount of health, there is a chance",
						"&7an opportunity comes to escape the battle.",
						"&7Causing you to recieve the speed and absorption effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Fortitude", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 7.5 + 2.5 * level) {
					double maxHealth = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
					double heal = 0.25 + 0.35 * level;
					new BukkitRunnable() {
						int count = 0;
						int check = 2 + level;
						public void run() {
							if(count <= check) {
								count++;
								Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
								victim.getWorld().spawnParticle(Particle.HEART, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
								victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_ANVIL_FALL, 2, (float) 1.3);
								if(victim.getHealth() + heal <= maxHealth) {
									victim.setHealth(victim.getHealth() + heal);
								}
								else {
									victim.setHealth(maxHealth);
								}
							}
							else {
								cancel();
								count = 0;
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 30L - 4L * level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Fortitude",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you quickly regenerate stamina. Causing you",
						"&7to regenerate health in a short amount of time."
					))
				);
			}
		}));
		this.listArmor.put("Fossil Blaze", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 5 + 2.0 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.FLAME, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_ANVIL_FALL, 2, (float) 1.3);
					double range = 3.00 + 1.25 * level;
					for(Entity entity : victim.getNearbyEntities(range, range, range)) {
						if(entity != null) {
							if(entity instanceof LivingEntity && entity != victim) {
								LivingEntity attacked = (LivingEntity) entity;
								if(!facManager.isFriendly(victim, attacked)) {
									if(victim instanceof Player) {
										attacked.setKiller((Player)victim);
									}
									attacked.setFireTicks(attacked.getFireTicks() + (140 + 20 * level));
								}
							}
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Fossil Blaze",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that your rage ignites something within you, forming",
						"&7an explosion of fire that set's enemies nearby aflame.",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Ghostly", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 15 + 3 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.SMOKE_LARGE, locCF, 100, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_HURT, 2, (float) 1.5);
					}
					int amp = 0;
					int durationAdd = 200 + 80 * level;
					PotionEffectType type = PotionEffectType.INVISIBILITY;
					p.applyEffect(victim, type, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Ghostly",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that a myserious aura forms around you. Causing",
						"&7you to recieve the invisibility effect for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Harden", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 7 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.WATER_BUBBLE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_ANVIL_FALL, 2, (float) 1.3);
					}
					int amp = 0 + (int)(level * 0.5);
					int durationAdd = 60 + 10 * level;
					p.applyEffect(victim, PotionEffectType.DAMAGE_RESISTANCE, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Harden",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you prepare for a flurry of attack's coming in.",
						"&7Causing you to recieve the resistance effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Hastefull", new Pair<>(Condition.ARMOR_CHANGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, int level, boolean equiped, PlayerArmorChangeEvent event) {
				if(dfManager.contains(damager)) {
					DFPlayer dfPlayer = dfManager.getEntity(damager);
					if(equiped == true) {
						dfPlayer.addSpdCal(1.5 + 1.5 * level, 0);
					}
					else if(equiped == false) {
						dfPlayer.removeSpdCal(1.5 + 1.5 * level, 0);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Hastefull",
					new ArrayList<String>(Arrays.asList(
						"&7When you equip armor, you gain knowledge on",
						"&7how to execute more attacks more quickly.",
						"&7Causing your attack speed skill to increase",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Hastened", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 7 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2, (float) 1.1);
					}
					int amp = 2 + level;
					int durationAdd = 60 + 7 * level;
					p.applyEffect(victim, PotionEffectType.FAST_DIGGING, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Hastened",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you feel like that you have to attack faster.",
						"&7Causing you to recieve the haste effect for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Ignite", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 10 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.FLAME, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_BLAZE_HURT, 2, (float) 1.1);
					}
					if(victim instanceof Player) {
						damager.setKiller((Player)victim);
					}
					damager.setFireTicks(damager.getFireTicks() + (150 + 70 * level));
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Ignite",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that your armor gets very hot.",
						"&7Causing the enemy to be engulfed by flames",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Invincibility", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			ArrayList<UUID> list = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 3 + 0.5 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_ANVIL_LAND, 2, (float) 1.3);
					}
					if(!list.contains(victim.getUniqueId())) {
						list.add(victim.getUniqueId());
						new BukkitRunnable() {
							public void run() {
								if(list.contains(victim.getUniqueId())) {
									list.remove(victim.getUniqueId());
								}
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 80 + 10 * level);
					}
				}
			}
			@EventHandler
			public void CustomEnchantmentsMInvincibilityI(EntityDamageByEntityEvent event) {
				if(event.getDamager() instanceof LivingEntity){
					if(event.getEntity() instanceof Player) {
						Player victim = (Player) event.getEntity();
						if(list.contains(victim.getUniqueId())) {
							event.setCancelled(true);
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Invincibility",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you will be blessed by godly powers.",
						"&7Causing you to be immune to melee and ranged attacks",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Jellyfish", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 2.5 + 1.25 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.2D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.WATER_BUBBLE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_GUARDIAN_DEATH, 2, (float) 1.5);
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 140 + 40 * level;
					ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.SLOW, PotionEffectType.POISON, PotionEffectType.SLOW_DIGGING));
					p.applyEffect(damager, types, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Jelly Fish",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that a jelly's sting will summon from the armor and",
						"&7piercing the enemy's skin to inflict a deadly poison.",
						"&7Causing the enemy to recieve the slowness, poison and",
						"&7the mining fatigue effect for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Kadabra", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GHAST_AMBIENT, 2, (float) 1.4);
					}
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 140 + 40 * level;
					p.applyEffect(damager, PotionEffectType.WEAKNESS, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Kadabra",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that a curse of weakness will be put upon them.",
						"&7Causing them to recieve the weakness effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Last Stand", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i < 15 + level * 2) {
					if(victim.getHealth() <= 12 + level) {
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.END_ROD, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_RABBIT_HURT, 2, (float) 1.1);
						}
						int amp = 0 + level;
						int durationAdd = 150 + 50 * level;
						ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.SPEED, PotionEffectType.ABSORPTION, PotionEffectType.REGENERATION));
						p.applyEffect(victim, types, amp, durationAdd);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Last Stand",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, and your health is below",
						"&7a certain point, there is a chance that",
						"&7you will give it your all, causing you to recieve",
						"&7the speed, absorption and regeneration effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Lightweight", new Pair<>(Condition.ARMOR_CHANGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, int level, boolean equiped, PlayerArmorChangeEvent event) {
				if(dfManager.contains(damager)) {
					DFPlayer dfPlayer = dfManager.getEntity(damager);
					if(damager instanceof Player) {
						if(equiped == true) {
							dfPlayer.addMove(0.003F + 0.003F * level, 0);
						}
						else if(equiped == false) {
							dfPlayer.removeMove(0.003F + 0.003F * level, 0);
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Lightweight",
					new ArrayList<String>(Arrays.asList(
						"&7When you equip armor, your legs start",
						"&7to feel stronger, causing your movement speed",
						"&7to increase."
					))
				);
			}
		}));
		this.listArmor.put("Lucky", new Pair<>(Condition.ARMOR_CHANGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, int level, boolean equiped, PlayerArmorChangeEvent event) {
				if(dfManager.contains(damager)) {
					DFPlayer dfPlayer = dfManager.getEntity(damager);
					if(equiped == true) {
						dfPlayer.addCrtCal(0.75 + 0.75 * level, 0);
					}
					else if(equiped == false) {
						dfPlayer.removeCrtCal(0.75 + 0.75 * level, 0);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Lucky",
					new ArrayList<String>(Arrays.asList(
						"&7When you equip armor, your luck is",
						"&7magically increased. Causing your critical",
						"&7chance skill to increase."
					))
				);
			}
		}));
		this.listArmor.put("Nurtrition", new Pair<>(Condition.FOOD_CHANGE_UP, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity victim, int level, FoodLevelChangeEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i < 25 + 25 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.DRIP_WATER, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 2, (float) 1.1);
					}
					if(victim instanceof Player) {
						Player p = (Player) victim;
						int foodBefore = p.getFoodLevel();
						int foodAfter = event.getFoodLevel();
						double foodGained = foodAfter - foodBefore;
						int food = (int)(foodGained * (1.5 + 0.3 * level));
						if(p.getFoodLevel() + food <= 20) {
							p.setFoodLevel(p.getFoodLevel() + food);
						}
						else {
							p.setFoodLevel(20);
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Nurtrition",
					new ArrayList<String>(Arrays.asList(
						"&7When eating food, there is a chance",
						"&7that you will gain additional food points."
					))
				);
			}
		}));
		this.listArmor.put("Overheal", new Pair<>(Condition.ARMOR_CHANGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, int level, boolean equiped, PlayerArmorChangeEvent event) {
				if(dfManager.contains(damager)) {
					DFPlayer dfPlayer = dfManager.getEntity(damager);
					if(equiped == true) {
						dfPlayer.addHpCal(7.5 + 7.5 * level, 0);
					}
					else if(equiped == false) {
						dfPlayer.removeHpCal(7.5 + 7.5 * level, 0);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Overheal",
					new ArrayList<String>(Arrays.asList(
						"&7When you equip armor, your well being is",
						"&7increased. Causing your health skill to increase."
					))
				);
			}
		}));
		this.listArmor.put("Protection", new Pair<>(Condition.ENTITY_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity victim, int level, EntityDamageEvent event) {
				event.setDamage(event.getDamage() / 100 * (100 - level * 2));
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Protection",
					new ArrayList<String>(Arrays.asList(
						"&7When you recieve damage, it is decreased."
					))
				);
			}
		}));
		this.listArmor.put("Rage", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 4 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, (float) 0.8);
					}
					int amp = 0 + level;
					int durationAdd = 50 + 8 * level;
					p.applyEffect(victim, PotionEffectType.INCREASE_DAMAGE, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Rage",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you will become enraged. Causing you to",
						"&7recieve the strength effect for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Regenerator", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i < 9 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.HEART, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_DRINK, 2, (float) 1.1);
					}
					int amp = 0 + level;
					int durationAdd = 300 - 67 * level;
					p.applyEffect(victim, PotionEffectType.REGENERATION, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Regenerator",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you call out to an unknown power. Causing",
						"&7you to recieve the regeneration effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Saturation", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i < 10 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.TOTEM, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_COW_MILK, 2, (float) 1);
					}
					int amp = 0 + level;
					int durationAdd = 100 + 30 * level;
					p.applyEffect(victim, PotionEffectType.SATURATION, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Saturation",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that your hunger will be satisfied, somehow.",
						"&7Causing you to recieve the saturation effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Savior", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(victim.getHealth() < victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * (0.17 + (0.01 * level))) {
					if(i < 16 + level * 2) {
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, locCF, 50, 0.1, 0.1, 0.1, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GHAST_AMBIENT, 2, (float) 1.5);
						}
						int amp = (int)Math.floor(0 + (level) / 2);
						int durationAdd = 300 + 50 * level;
						p.applyEffect(victim, PotionEffectType.REGENERATION, amp, durationAdd);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Savior",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you and your health is",
						"&7a certain point, there is a chance that",
						"&7someone will reach out and give you a hand.",
						"&7Causing you to recieve the regeneration effect.",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Self Destruct", new Pair<>(Condition.KILLED_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDeathEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i < 40 + 10 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.SMOKE_LARGE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_TNT_PRIMED, 2, (float) 1);
					}
					new BukkitRunnable() {
						int count = 0;
						public void run() {
							if(count <= 8 + 2 * level) {
								Entity tnt = victim.getWorld().spawn(victim.getLocation().add(0, 1.5, 0), TNTPrimed.class); 
								((TNTPrimed)tnt).setFuseTicks(90 - 10 * level);
							}
							else {
								cancel();
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 5L, 1L);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Self Destruct",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that your hunger will be satisfied, somehow.",
						"&7Causing you to recieve the saturation effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Slowness", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 12 + level) {
					Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.5D, damager.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.SPIT, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 2, (float) 1.1);
					}
					int amp = 0;
					int durationAdd = 200 + 50 * level;
					p.applyEffect(damager, PotionEffectType.SLOW, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Slowness",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you slow them down for a long while.",
						"&7Causing them to recieve the slowness effect",
						"&7for a while."
					))
				);
			}
		}));
		this.listArmor.put("Slow", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 9 + level) {
					Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.5D, damager.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.SPIT, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 2, (float) 1.1);
					}
					int amp = 0 + level;
					int durationAdd = 60 + 15 * level;
					p.applyEffect(damager, PotionEffectType.SLOW, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Slow",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you slow them down. For a short while.",
						"&7Causing them to recieve the slowness effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Smoke Screen", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 25) {
					if(victim.getHealth() < 13 + level) {
						double range = 3 + level;
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, locCF, 100, 4.5, 4.5, 4.5, 4.5); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 2, (float) 0.7);
						}
						for (Entity entity : victim.getNearbyEntities(range, range, range)){
							if(entity instanceof LivingEntity) {
								if(entity != victim) {
									LivingEntity entity1 = (LivingEntity) entity;
									int amp = (int)Math.floor(0 + (level) / 2);
									int durationAdd = 150 + 50 * level;
									ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.BLINDNESS, PotionEffectType.SLOW));
									p.applyEffect(entity1, types, amp, durationAdd);
								}
							}
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Smoke Screen",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you and you are below a",
						"&7certain amount of health, there is a chance",
						"&7that you create a smoke screen to escape.",
						"&7Causing nearby enemies to recieve the",
						"&7slowness and blindness effect for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Snare", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i < 3.5 + 0.5 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.SUSPENDED, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ITEM_BREAK, 2, (float) 1);
					}
					int amp = 20;
					int amp1 = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 140 + 20 * level;
					p.applyEffect(damager, PotionEffectType.SLOW, amp, durationAdd);
					p.applyEffect(damager, PotionEffectType.SLOW_DIGGING, amp1, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Snare",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you literally shut them up. Causing them to",
						"&7recieve the slowness and mining fatigue effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Tank", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 11 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.2D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.SPELL, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_CHICKEN_HURT, 2, (float) 1.1);
					}
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 200 + 50 * level;
					p.applyEffect(victim, new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.INCREASE_DAMAGE, PotionEffectType.DAMAGE_RESISTANCE, PotionEffectType.SLOW)), amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Tank",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you prepare for more incoming attacks.",
						"&7Causing you to recieve the strength, slowness and",
						"&7resistance effect for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Tough Skin", new Pair<>(Condition.ARMOR_CHANGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, int level, boolean equiped, PlayerArmorChangeEvent event) {
				if(dfManager.contains(damager)) {
					DFPlayer dfPlayer = dfManager.getEntity(damager);
					if(equiped == true) {
						dfPlayer.addDfCal(1.875 + 1.875 * level, 0);
					}
					else if(equiped == false) {
						dfPlayer.removeDfCal(1.875 + 1.875 * level, 0);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Tough Skin",
					new ArrayList<String>(Arrays.asList(
						"&7When you equip armor, you gain more knowledge",
						"&7on how to avoid recieving more damage. Causing",
						"&7your defense skill to increase."
					))
				);
			}
		}));
		this.listArmor.put("Use the system", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 13 + 2 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.0D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, locCF, 20, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_PISTON_EXTEND, 2, (float) 1.5);
					}
					new BukkitRunnable() {
						public void run() {
							victim.setNoDamageTicks(victim.getNoDamageTicks() / 100 * (120 + 8 * level));
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 0L);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Use the system",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you hack into the system and extend your",
						"&7immunity timer by a few ticks."
					))
				);
			}
		}));
		this.listArmor.put("Valor", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 7 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.WATER_BUBBLE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_ANVIL_FALL, 2, (float) 1.3);
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 100 + 30 * level;
					p.applyEffect(victim, PotionEffectType.DAMAGE_RESISTANCE, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Valor",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you feel protection coming over you.",
						"&7Causing you to recieve the resistance effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listArmor.put("Withering", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 8 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.SMOKE_NORMAL, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_WITHER_SHOOT, 2, (float) 1.1);
					}
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 150 + 50 * level;
					p.applyEffect(damager, PotionEffectType.WITHER, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Withering",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you wish that your enemy would just wither away.",
						"&7Causing them to recieve the wither effect",
						"&7for a few seconds."
					))
				);
			}
		}));
	}
	
	public void loadBowEnchantments() {
		this.listBow = new HashMap<String, Pair<Condition, CommandFile>>();
		this.listBow.put("Arrow Rain", new Pair<>(Condition.LEFT_CLICK, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			ArrayList<UUID> arrows = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!cooldown.contains(damager.getUniqueId())) {
					if(damager instanceof Player && dfManager.contains(damager)) {
						Player player = (Player) damager;
						DFPlayer dfPlayer = dfManager.getEntity(player);
						DFShootBowEvent shoot = new DFShootBowEvent(player, 1.0F, dfPlayer);
						if(shoot.getProjectile() instanceof Arrow) {
							cooldown.add(damager.getUniqueId());
							Arrow arrow = (Arrow) shoot.getProjectile();
							double range = 2.75 + 0.25 * level;
							int maxArrows = 50 + 10 * level;
							Location loc = player.getLocation().clone();
							loc.add(0, 15, 0);
							new BukkitRunnable() {
								int count = 0;
								public void run() {
									if(count <= maxArrows) {
										Location temp = loc.clone();
										float x = (float) (ThreadLocalRandom.current().nextFloat() * (range * 2.0F) - range);
										float y = (float) (ThreadLocalRandom.current().nextFloat() * (range * 2.0F) - range);
										temp.setPitch(90.0F);
										temp.add(x, 0, y);
										Arrow arr = player.getWorld().spawnArrow(temp, temp.getDirection(), 2.55F, 0.0F);
										arr.setShooter(player);
										arr.setCritical(true);
										arr.setDamage(arrow.getDamage());
										arr.setPickupStatus(PickupStatus.DISALLOWED);
										arrows.add(arr.getUniqueId());
										count++;
									}
									else {
										cancel();
									}
								}
							}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
							shoot.setCancelled(true);
							shoot.shootArrow();
							new BukkitRunnable() {
								public void run() {
									cooldown.remove(player.getUniqueId());
								}
							}.runTaskLater(CustomEnchantments.getInstance(), 1100 - 65L * level);
						}
					}
				}
			}
			@EventHandler
			public void arrowRainHit(ProjectileHitEvent event) {
				if(event.getEntity() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getEntity();
					if(arrows.contains(arrow.getUniqueId())) {
						if(arrow.getShooter() instanceof Player) {
							Player shooter = (Player) arrow.getShooter();
							if(event.getHitEntity() instanceof LivingEntity) {
								if(facManager.isFriendly(shooter, event.getHitEntity())) {
									arrow.remove();
								}
							}
							else {
								arrow.remove();
							}
						}
					}
				}
			}
			@EventHandler(priority = EventPriority.HIGHEST)
			public void arrowRainDamage(EntityDamageByEntityEvent event) {
				if(event.getDamager() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getDamager();
					if(arrow.getShooter() instanceof Player) {
						if(arrows.contains(arrow.getUniqueId())) {
							Player shooter = (Player) arrow.getShooter();
							if(event.getEntity() instanceof LivingEntity) {
								LivingEntity ent = (LivingEntity) event.getEntity();
								if(facManager.isFriendly(shooter, ent)) {
									event.setCancelled(true);
									arrows.remove(arrow.getUniqueId());
									arrow.remove();
								}
								else {
									new BukkitRunnable() {
										public void run() {
											ent.setNoDamageTicks(0);
											arrows.remove(arrow.getUniqueId());
										}
									}.runTaskLater(CustomEnchantments.getInstance(), 0L);
								}
							}
						}
					}
				}
			}
			@EventHandler
			public void projectileHit(ProjectileHitEvent event) {
				if(event.getEntity() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getEntity();
					if(arrow.getShooter() instanceof Player) {
						if(arrows.contains(arrow.getUniqueId())) {
							arrows.remove(arrow.getUniqueId());
							arrow.remove();
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Arrow Rain",
					new ArrayList<String>(Arrays.asList(
						"&7When you left click, you prepare a &6Grab and Pull",
						"&7arrow. When you hit a enemy, you pull them towards",
						"&7your current location.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listBow.put("Black Heart", new Pair<>(Condition.LEFT_CLICK, new CommandFile() {
			ArrayList<UUID> activated = new ArrayList<UUID>();
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			HashMap<UUID, Integer> levels = new HashMap<UUID, Integer>();
			HashMap<UUID, Integer> arrows = new HashMap<UUID, Integer>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!cooldown.contains(damager.getUniqueId()) && !activated.contains(damager.getUniqueId())) {
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(damager.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 2, (float) 1.5);
					}
					cooldown.add(damager.getUniqueId());
					levels.put(damager.getUniqueId(), level);
					activated.add(damager.getUniqueId());
					damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have prepared a &6Black Heart &aarrow!"));
				}
			}
			@EventHandler
			public void shootArrow(DFShootBowEvent event) {
				Player player = event.getShooter();
				if(!event.isCancelled()) {
					if(event.getProjectile() instanceof Arrow) {
						Arrow arrow = (Arrow) event.getProjectile();
						UUID uuid = player.getUniqueId();
						if(activated.contains(uuid)) {
							int level = levels.get(uuid);
							activated.remove(uuid);
							new BukkitRunnable() {
								public void run() {
									if(!arrow.isDead()) {
										Location locCF = new Location(arrow.getWorld(), arrow.getLocation().getX() + 0D, arrow.getLocation().getY() + 0D, arrow.getLocation().getZ() + 0D);
										arrow.getWorld().spawnParticle(Particle.REDSTONE, locCF, 1, new Particle.DustOptions(Color.fromRGB(0), 1)); 
									}
									else {
										cancel();
									}
								}
							}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 2L);
							new BukkitRunnable() {
								public void run() {
									cooldown.remove(uuid);
								}
							}.runTaskLater(CustomEnchantments.getInstance(), 1200L - 75L * level);
							arrows.put(arrow.getUniqueId(), levels.get(player.getUniqueId()));
							levels.remove(uuid);
						}
					}
				}
			}
			@EventHandler
			public void arrowHit(ProjectileHitEvent event) {
				if(event.getEntity() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getEntity();
					if(arrow.getShooter() instanceof Player) {
						Player shooter = (Player) arrow.getShooter();
						if(arrows.containsKey(arrow.getUniqueId())) {
							arrow.setPickupStatus(PickupStatus.DISALLOWED);
							if(event.getHitEntity() == null) {
								this.loop(shooter, arrow, arrow);
							}
							else {
								this.loop(shooter, arrow, event.getHitEntity());
							}
							
						}
					}
				}
			}
			public void loop(Player shooter, Arrow arrow, Entity ent) {
				int level = arrows.get(arrow.getUniqueId());
				final double range = 4.00 + level; 
				final double damage = 2 + 0.65 * level;
				final int count = 4 + level;
				new BukkitRunnable() {
					int c = 0;
					public void run() {
						if(c <= count) {
							for(Entity e : ent.getNearbyEntities(range, range, range)) {
								if(e != shooter) {
									if(e instanceof LivingEntity) {
										LivingEntity ent = (LivingEntity) e;
										BlockData data = Bukkit.createBlockData(Material.BLACK_CONCRETE);
										if(!facManager.isFriendly(shooter, e)) {
											if(shooter instanceof Player) {
												ent.setKiller((Player)shooter);
											}
											ent.damage(damage, shooter);
											Location locCF = new Location(ent.getWorld(), ent.getLocation().getX() + 0D, ent.getLocation().getY() + 1.5D, ent.getLocation().getZ() + 0D);
											ent.getWorld().spawnParticle(Particle.BLOCK_CRACK, locCF, 10, data); 
										}
									}
								}
							}
							c++;
						}
						else {
							arrow.remove();
							cancel();
						}
					}
				}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 60 - 5 * level);
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Black Heart",
					new ArrayList<String>(Arrays.asList(
						"&7When you left click, you prepare a &6Black Heart",
						"&7arrow. When you shoot this arrow, a black particle",
						"&7will follow the arrow. When this arrow lands, it damages",
						"&7any enemies nearby this arrow over a span of time. If this arrow hits an",
						"&7entity, it will follow the damage effect around the entity.",
						"&7This enchantments has a cooldown"
					))
				);
			}
		}));
		this.listBow.put("Black Hole", new Pair<>(Condition.LEFT_CLICK, new CommandFile() {
			ArrayList<UUID> activated = new ArrayList<UUID>();
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			HashMap<UUID, Integer> levels = new HashMap<UUID, Integer>();
			HashMap<UUID, Integer> arrows = new HashMap<UUID, Integer>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!cooldown.contains(damager.getUniqueId()) && !activated.contains(damager.getUniqueId())) {
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(damager.getLocation(), Sound.ENTITY_ENDERMAN_AMBIENT, 2, (float) 1.5);
					}
					cooldown.add(damager.getUniqueId());
					levels.put(damager.getUniqueId(), level);
					activated.add(damager.getUniqueId());
					damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have prepared a &6Black Hole &aarrow!"));
				}
			}
			@EventHandler
			public void shootArrow(DFShootBowEvent event) {
				Player player = event.getShooter();
				if(!event.isCancelled()) {
					if(event.getProjectile() instanceof Arrow) {
						Arrow arrow = (Arrow) event.getProjectile();
						UUID uuid = player.getUniqueId();
						if(activated.contains(uuid)) {
							int level = levels.get(uuid);
							activated.remove(uuid);
							new BukkitRunnable() {
								public void run() {
									if(!arrow.isDead()) {
										Location locCF = new Location(arrow.getWorld(), arrow.getLocation().getX() + 0D, arrow.getLocation().getY() + 0D, arrow.getLocation().getZ() + 0D);
										arrow.getWorld().spawnParticle(Particle.REDSTONE, locCF, 1, new Particle.DustOptions(Color.fromRGB(0), 1)); 
									}
									else {
										cancel();
									}
								}
							}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 2L);
							new BukkitRunnable() {
								public void run() {
									cooldown.remove(uuid);
								}
							}.runTaskLater(CustomEnchantments.getInstance(), 1500L - 100L * level);
							arrows.put(arrow.getUniqueId(), levels.get(player.getUniqueId()));
							levels.remove(uuid);
						}
					}
				}
			}
			@EventHandler
			public void arrowHit(ProjectileHitEvent event) {
				if(event.getEntity() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getEntity();
					if(arrow.getShooter() instanceof Player) {
						Player shooter = (Player) arrow.getShooter();
						if(arrows.containsKey(arrow.getUniqueId())) {
							arrow.setPickupStatus(PickupStatus.DISALLOWED);
							if(event.getHitEntity() == null) {
								this.loop(shooter, arrow, arrow);
							}
							else {
								this.loop(shooter, arrow, event.getHitEntity());
							}
							
						}
					}
				}
			}
			public void loop(Player shooter, Arrow arrow, Entity entity) {
				int level = arrows.get(arrow.getUniqueId());
				final double range = 8.00 + 1.5 * level; 
				final double damage = 2 + 0.65 * level;
				final int count = 75 + 15 * level;
				new BukkitRunnable() {
					int c = 0;
					public void run() {
						if(c <= count) {
							for(Entity e : entity.getNearbyEntities(range, range, range)) {
								if(e != shooter && e != entity) {
									if(e instanceof LivingEntity) {
										LivingEntity ent = (LivingEntity) e;
										if(!facManager.isFriendly(shooter, e)) {
											if(!wg.isInZone(ent, "spawn")) {
												Vector direction = entity.getLocation().add(0, -1.8, 0).toVector().subtract(ent.getLocation().toVector()).normalize();
												double distance = e.getLocation().distance(entity.getLocation());
												ent.setVelocity(direction.multiply(0.175F - (distance / 100))); 
											}
										}
									}
								}
							}
							c++;
						}
						else {
							int amp = (int)Math.floor(0 + (level) / 2);
							int durationAdd = 160 + level * 40;
							double implodeRadius = range / 2;
							Location loc = new Location(entity.getWorld(), entity.getLocation().getX() + 0D, entity.getLocation().getY() + 0.0D, entity.getLocation().getZ() + 0D);
							entity.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc, 60, 0.1, 0.1, 0.1, 0.1); 
							for(Entity e : entity.getNearbyEntities(implodeRadius, implodeRadius, implodeRadius)) {
								if(e != shooter) {
									if(e instanceof LivingEntity) {
										LivingEntity ent = (LivingEntity) e;
										if(!facManager.isFriendly(shooter, e)) {
											if(shooter instanceof Player) {
												ent.setKiller((Player)shooter);
											}
											p.applyEffect(ent, new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.BLINDNESS, PotionEffectType.SLOW)), amp, durationAdd);
											ent.damage(damage);
											Location locCF = new Location(entity.getWorld(), entity.getLocation().getX() + 0D, entity.getLocation().getY() + 0.0D, entity.getLocation().getZ() + 0D);
											entity.getWorld().spawnParticle(Particle.SMOKE_NORMAL, locCF, 15, 0.1, 0.1, 0.1, 0.1); 
										}
									}
								}
							}
							arrow.remove();
							cancel();
						}
					}
				}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 2L);
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Black Hole",
					new ArrayList<String>(Arrays.asList(
						"&7When you left click, you prepare a &6Black Hole",
						"&7arrow. When you shoot this arrow, a black particle",
						"&7will follow the arrow. When this arrow lands, it will pull",
						"&7nearby enemies towards the arrow, after a short while, the",
						"&7black hole will implode. Causing nearby enemies to take",
						"&7damage and recieve the blindness and slowness effect",
						"&7for a few seconds.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listBow.put("Blast", new Pair<>(Condition.LEFT_CLICK, new CommandFile() {
			ArrayList<UUID> activated = new ArrayList<UUID>();
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			HashMap<UUID, Integer> levels = new HashMap<UUID, Integer>();
			HashMap<UUID, Integer> arrows = new HashMap<UUID, Integer>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!cooldown.contains(damager.getUniqueId()) && !activated.contains(damager.getUniqueId())) {
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(damager.getLocation(), Sound.ENTITY_CREEPER_HURT, 2, (float) 1.5);
					}
					cooldown.add(damager.getUniqueId());
					levels.put(damager.getUniqueId(), level);
					activated.add(damager.getUniqueId());
					damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have prepared a &6Blast &aarrow!"));
				}
			}
			@EventHandler
			public void shootArrow(DFShootBowEvent event) {
				Player player = event.getShooter();
				if(!event.isCancelled()) {
					if(event.getProjectile() instanceof Arrow) {
						Arrow arrow = (Arrow) event.getProjectile();
						UUID uuid = player.getUniqueId();
						if(activated.contains(uuid)) {
							int level = levels.get(uuid);
							activated.remove(uuid);
							new BukkitRunnable() {
								public void run() {
									if(!arrow.isDead()) {
										Location locCF = new Location(arrow.getWorld(), arrow.getLocation().getX() + 0D, arrow.getLocation().getY() + 0D, arrow.getLocation().getZ() + 0D);
										arrow.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, locCF, 1); 
									}
									else {
										cancel();
									}
								}
							}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 2L);
							new BukkitRunnable() {
								public void run() {
									cooldown.remove(uuid);
								}
							}.runTaskLater(CustomEnchantments.getInstance(), 1000L - 75L * level);
							arrows.put(arrow.getUniqueId(), levels.get(player.getUniqueId()));
							levels.remove(uuid);
						}
					}
				}
			}
			@EventHandler
			public void arrowHit(ProjectileHitEvent event) {
				if(event.getEntity() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getEntity();
					if(arrow.getShooter() instanceof Player) {
						Player shooter = (Player) arrow.getShooter();
						if(arrows.containsKey(arrow.getUniqueId())) {
							arrow.setPickupStatus(PickupStatus.DISALLOWED);
							if(event.getHitEntity() == null) {
								this.loop(shooter, arrow, arrow);
							}
							else {
								this.loop(shooter, arrow, event.getHitEntity());
							}
						}
					}
				}
			}
			public void loop(Player shooter, Arrow arrow, Entity en) {
				int level = arrows.get(arrow.getUniqueId());
				final double range = 5.00 + level; 
				final double damage = 7 + 1.5 * level;
				Location locCF = new Location(en.getWorld(), en.getLocation().getX() + 0D, en.getLocation().getY() + 1.7D, en.getLocation().getZ() + 0D);
				en.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, locCF, 80, range, range, range, 0.1);
				en.getWorld().playSound(en.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, (float) 1);
				for(Entity e : en.getNearbyEntities(range, range, range)) {
					if(e != shooter) {
						if(e instanceof LivingEntity) {
							if(!facManager.isFriendly(shooter, e)) {
								LivingEntity ent = (LivingEntity) e;
								double finalDamage = damage - (e.getLocation().distance(en.getLocation())) / 2.5;
								if(shooter instanceof Player) {
									ent.setKiller((Player)shooter);
								}
								ent.damage(finalDamage, shooter);
							}
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Blast",
					new ArrayList<String>(Arrays.asList(
						"&7When you left click, you prepare a &6Blast",
						"&7arrow. When you shoot this arrow, an explosion",
						"&7particle will follow its path. When this arrow lands,",
						"&7it will create an explosion, damaging any enemies nearby",
						"&7it. This effect works the same if you hit an enemy.",
						"&7this enchantment has a cooldown."
					))
				);
			}
		}));
		this.listBow.put("Confusion", new Pair<>(Condition.PROJECTILE_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 12 + 2 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.REDSTONE, locCF, 60, 0.15, 0.15, 0.15, new DustOptions(Color.RED, 5)); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GHAST_HURT, 2, (float) 0.5);
					}
					int amp = 0 + level;
					int durationAdd = 200 + 50 * level;
					p.applyEffect(victim, PotionEffectType.CONFUSION, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Confusion",
					new ArrayList<String>(Arrays.asList(
						"&7When you shoot the enemy, there is a chance",
						"&7that you shoot the enemy in the head, they get confused",
						"&7and recieve the nausea effect for a few seconds."
					))
				);
			}
		}));
		this.listBow.put("Double Tap", new Pair<>(Condition.PROJECTILE_SHOOT, new CommandFile() {
			ArrayList<UUID> tap = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, DFShootBowEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 20 + 2.5 * level) {
					if(damager instanceof Player) {
						if(dfManager.contains(damager)) {
							DFPlayer dfPlayer = dfManager.getEntity(damager);
							Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 2.5D, damager.getLocation().getZ() + 0D);
							damager.getWorld().spawnParticle(Particle.REDSTONE, locCF, 20, 0.15, 0.15, 0.15, new DustOptions(Color.RED, 5)); 
							for(Player victim1 : Bukkit.getOnlinePlayers()) {
								((Player) victim1).playSound(damager.getLocation(), Sound.ENTITY_ARROW_SHOOT, 2, (float) 1.5);
							}
							tap.add(event.getProjectile().getUniqueId());
							new BukkitRunnable() {
								public void run() {
									DFShootBowEvent ev = new DFShootBowEvent((Player)damager, 1.0F, dfPlayer);
									Bukkit.getServer().getPluginManager().callEvent(ev);
									ev.shootArrow();
								}
							}.runTaskLater(CustomEnchantments.getInstance(), 6L);
						}
					}
				}
			}
			@EventHandler(priority = EventPriority.HIGHEST)
			public void tapHit(EntityDamageByEntityEvent event) {
				if(event.getEntity() != null && event.getDamager() != null) {
					if(event.getEntity() instanceof LivingEntity && event.getDamager() instanceof Arrow) {
						LivingEntity ent = (LivingEntity) event.getEntity();
						Arrow arrow = (Arrow) event.getDamager();
						if(tap.contains(arrow.getUniqueId())) {
							new BukkitRunnable() {
								public void run() {
									ent.setNoDamageTicks(0);
								}
							}.runTaskLater(CustomEnchantments.getInstance(), 0L);
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Double Tap",
					new ArrayList<String>(Arrays.asList(
						"&7When you shoot your bow, there is a chance",
						"&7that you shoot a second arrow imediatly after.",
						"&7When the first arrow that you shot hit's the enemy,",
						"&7it will set their imunity timer to 0 seconds."
					))
				);
			}
		}));
		this.listBow.put("Disarmor", new Pair<>(Condition.PROJECTILE_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 3 + 0.5 * level) {
					if(victim instanceof Player) {
						Player p = (Player) victim;
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.3D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.ITEM_CRACK, locCF, 50, 0.2, 0.2, 0.2, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ITEM_BREAK, 2, (float) 0.9);
						}
						ItemStack helm = p.getInventory().getHelmet();
						ItemStack chest = p.getInventory().getChestplate();
						ItemStack legs = p.getInventory().getLeggings();
						ItemStack boots = p.getInventory().getBoots();
						int rand = new Random().nextInt (3) + 1;
						if(rand == 1) {
							if(helm != null) {
								p.getInventory().addItem(helm);
								p.getInventory().setHelmet(null);
							}
						}
						else if(rand == 2) {
							if(chest != null) {
								p.getInventory().addItem(chest);
								p.getInventory().setHelmet(null);
							}
						}
						else if(rand == 3) {
							if(legs != null) {
								p.getInventory().addItem(legs);
								p.getInventory().setHelmet(null);
							}
						}
						else if(rand == 4) {
							if(boots != null) {
								p.getInventory().addItem(boots);
								p.getInventory().setHelmet(null);
							}
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Disarmor",
					new ArrayList<String>(Arrays.asList(
						"&7When you shoot the enemy, there is a chance",
						"&7that the arrow that you shot lunges under 1 of the enemy's",
						"&7armor pieces, causing 1 of the armor pieces to",
						"&7fall of the enemy."
					))
				);
			}
		}));
		this.listBow.put("Grab and Pull", new Pair<>(Condition.LEFT_CLICK, new CommandFile() {
			ArrayList<UUID> prepared = new ArrayList<UUID>();
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			HashMap<UUID, Integer> levelBow = new HashMap<UUID, Integer>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!cooldown.contains(damager.getUniqueId()) && !prepared.contains(damager.getUniqueId())) {
					prepared.add(damager.getUniqueId());
					cooldown.add(damager.getUniqueId());
					levelBow.put(damager.getUniqueId(), level);
					damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have prepared a &6Grab and Pull &aarrow!"));
				}
			}
			@EventHandler
			public void land(ProjectileHitEvent event) {
				if(event.getEntity() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getEntity();
					if(event.getEntity().getShooter() instanceof Player) {
						Player player = (Player) event.getEntity().getShooter();
						if(prepared.contains(player.getUniqueId())) {
							if(event.getHitEntity() != null && event.getHitEntity() instanceof LivingEntity) {
								LivingEntity ent = (LivingEntity) event.getHitEntity();
								prepared.remove(player.getUniqueId());
								int level = levelBow.get(player.getUniqueId());
								new BukkitRunnable() {
									public void run() {
										ent.setVelocity(new Vector(0, 0.5, 0));
										Vector direction = player.getLocation().add(0, 4.0, 0).toVector().subtract(ent.getLocation().toVector()).normalize().multiply(player.getLocation().distance(arrow.getLocation()) / (15.0 - level * 1.5));
										ent.setVelocity(direction);
									}
								}.runTaskLater(CustomEnchantments.getInstance(), 3L);
								new BukkitRunnable() {
									public void run() {
										cooldown.remove(player.getUniqueId());
									}
								}.runTaskLater(CustomEnchantments.getInstance(), 800 - 75 * level);
							}
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Grab and Pull",
					new ArrayList<String>(Arrays.asList(
						"&7When you left click, you prepare a &6Grab and Pull",
						"&7arrow. When you hit a enemy, you pull them towards",
						"&7your current location.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listBow.put("Grappling Hook", new Pair<>(Condition.LEFT_CLICK, new CommandFile() {
			HashMap<UUID, Boolean> prepared = new HashMap<UUID, Boolean>();
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			HashMap<UUID, Integer> levelBow = new HashMap<UUID, Integer>();
			HashMap<UUID, Vector> vector = new HashMap<UUID, Vector>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!cooldown.contains(damager.getUniqueId()) && !levelBow.containsKey(damager.getUniqueId())) {
					prepared.put(damager.getUniqueId(), true);
					cooldown.add(damager.getUniqueId());
					levelBow.put(damager.getUniqueId(), level);
					damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have prepared a &6Grappling Hook &7arrow!"));
				}
			}
			@EventHandler
			public void bowShoot(DFShootBowEvent event) {
				Player player = event.getShooter();
				if(!event.isCancelled()) {
					if(prepared.containsKey(player.getUniqueId())) {
						if(prepared.get(player.getUniqueId()) == true) {
							vector.put(player.getUniqueId(), player.getLocation().getDirection());
						}
					}
				}
			}
			@EventHandler
			public void land(ProjectileHitEvent event) {
				if(event.getEntity() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getEntity();
					if(event.getEntity().getShooter() instanceof Player) {
						Player player = (Player) event.getEntity().getShooter();
						if(prepared.containsKey(player.getUniqueId())) {
							if(prepared.get(player.getUniqueId()) == true) {
								prepared.put(player.getUniqueId(), false);
								player.setVelocity(new Vector(0, 0.5, 0));
								int level = levelBow.get(player.getUniqueId());
								new BukkitRunnable() {
									public void run() {
										player.setVelocity(vector.get(player.getUniqueId()).normalize().multiply(player.getLocation().distance(arrow.getLocation()) / (15.0 - level * 1.5)));
										vector.remove(player.getUniqueId());
									}
								}.runTaskLater(CustomEnchantments.getInstance(), 3L);
								new BukkitRunnable() {
									public void run() {
										cooldown.remove(player.getUniqueId());
									}
								}.runTaskLater(CustomEnchantments.getInstance(), 1200 - 100 * levelBow.get(player.getUniqueId()));
								levelBow.remove(player.getUniqueId());
							}
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Grappling Hook",
					new ArrayList<String>(Arrays.asList(
						"&7When you left click, you prepare a &6Grappling Hook",
						"&7arrow. When you hit a block/enemy, you leap towards",
						"&7where the arrow landed.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listBow.put("Homing", new Pair<>(Condition.PROJECTILE_SHOOT, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, int level, DFShootBowEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + level * 2) {
					Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 2D, damager.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.CLOUD, locCF, 30, 0.05, 0.05, 0.05); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(damager.getLocation(), Sound.ENTITY_PIG_SADDLE, 2, (float) 1.25);
					}
					new BukkitRunnable() {
						LivingEntity target = null;
						public void run() {
							//ballLoc.getDirection().add(ent.getLocation().add(0, 1, 0).subtract(ballLoc).toVector().normalize().multiply(speed / 100 * 175)).normalize().multiply(speed / 100 * 87.5)
							Arrow arrow = (Arrow) event.getProjectile();
							if(target == null) {
								for(Entity e : arrow.getNearbyEntities(20, 20, 20)) {
									if(e != null && e != damager && e instanceof LivingEntity) {
										LivingEntity tar = (LivingEntity) e;
										if(!facManager.isFriendly(damager, tar)) {
											target = tar;
											break;
										}
									}
								}
							}
							else {
								double speed = arrow.getVelocity().length();
								Vector finalLoc = arrow.getVelocity().add(target.getLocation().add(0, 1, 0).subtract(arrow.getLocation()).toVector().normalize().multiply(speed * 0.15)).normalize().multiply(speed);
								arrow.setVelocity(finalLoc);
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
					
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Homing",
					new ArrayList<String>(Arrays.asList(
						"&7When you shoot your bow, there is a chance",
						"&7that your arrow will home into the nearest target",
						"&7present in it's radius."
					))
				);
			}
		}));
		this.listBow.put("Shooting Star", new Pair<>(Condition.LEFT_CLICK, new CommandFile() {
			ArrayList<UUID> activated = new ArrayList<UUID>();
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			HashMap<UUID, Integer> levels = new HashMap<UUID, Integer>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!cooldown.contains(damager.getUniqueId()) && !activated.contains(damager.getUniqueId())) {
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(damager.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 1.5);
					}
					cooldown.add(damager.getUniqueId());
					levels.put(damager.getUniqueId(), level);
					activated.add(damager.getUniqueId());
					damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou prepare to &6shoot a Star!"));
				}
			}
			@EventHandler
			public void shootArrow(DFShootBowEvent event) {
				Player player = event.getShooter();
				UUID uuid = player.getUniqueId();
				if(activated.contains(uuid)) {
					event.getProjectile().remove();
					event.setCancelled(true);
					activated.remove(uuid);
					int level = levels.get(uuid);
					int count = 200 + 25 * level;
					double range = 3.0 + 0.5 * level;
					double damage = 10.0 + 3.0 * level;
					int duration = 200 + 50 * level;
					ArmorStand star = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, 1.8, 0).add(player.getLocation().getDirection()), EntityType.ARMOR_STAND);
					star.setInvulnerable(true);
					star.setRemoveWhenFarAway(false);
					EntityArmorStand stand = ((CraftArmorStand)star).getHandle();
					stand.noclip = true;
					stand.setInvisible(true);
					new BukkitRunnable() {
						int c = 0;
						public void run() {
							if(c <= count) {
								star.setVelocity(star.getLocation().getDirection().multiply(0.5));
								pApi.sphere(Particle.FIREWORKS_SPARK, star.getLocation(), 0.75, 5);
								for(Entity en : star.getNearbyEntities(0.75, 0.75, 0.75)) {
									if(en != null && en instanceof LivingEntity && en != player) {
										star.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, star.getLocation(), 60, 0.25, 0.25, 0.25, 0.2);
										star.getWorld().playSound(star.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 2, (float) 1);
										star.remove();
										for(Entity e : star.getNearbyEntities(range, range, range)) {
											if(!facManager.isFriendly(player, e)) {
												if(e instanceof LivingEntity) {
													Vector direction = star.getLocation().add(0, -1, 0).toVector().subtract(e.getLocation().toVector()).normalize();
													LivingEntity ent = (LivingEntity) e;
													double finalDamage = damage - ent.getLocation().distance(star.getLocation()) * 1.8;
													int finalDuration = (int)(duration - ent.getLocation().distance(star.getLocation()) * 35);
													if(player instanceof Player) {
														ent.setKiller((Player)player);
													}
													ent.damage(finalDamage, player);
													ent.setVelocity(direction.multiply(-1.8F + (range / 10)));
													ent.setFireTicks(finalDuration);
												}
											}
										}
										cancel();
										break;
									}
								}
								if(star.getLocation().getBlock().getType() != Material.AIR) {
									star.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, star.getLocation(), 60, 0.25, 0.25, 0.25, 0.1);
									star.getWorld().playSound(star.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 2, (float) 1);
									star.remove();
									for(Entity e : star.getNearbyEntities(range, range, range)) {
										if(e != null && e instanceof LivingEntity && e != player) {
											if(!facManager.isFriendly(player, e)) {
												LivingEntity ent = (LivingEntity) e;
												Vector direction = star.getLocation().add(0, -1, 0).toVector().subtract(ent.getLocation().toVector()).normalize();
												double finalDamage = damage - ent.getLocation().distance(star.getLocation()) * 1.8;
												int finalDuration = (int)(duration - ent.getLocation().distance(star.getLocation()) * 35);
												if(player instanceof Player) {
													ent.setKiller((Player)player);
												}
												ent.damage(finalDamage, player);
												ent.setVelocity(direction.multiply(-1.8F + (range / 10)));
												ent.setFireTicks(finalDuration);
											}
										}
									}
									cancel();
								}
								c++;
							}
							else {
								star.remove();
								cancel();
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
					new BukkitRunnable() {
						public void run() {
							cooldown.remove(uuid);
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 1400L - 75L * level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Shooting Star",
					new ArrayList<String>(Arrays.asList(
						"&7When you left click, you prepare to shoot a star.",
						"&7When you shoot, it will shoot a star towards the direction",
						"&7that you are looking. When this star hits a block or an",
						"&7entity, the star will explode. Causing nearby enemies to",
						"&7take damage, be set on fire and they will be knocked away",
						"&7from the star.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listBow.put("Lifesteal", new Pair<>(Condition.PROJECTILE_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 70 + 10 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, locCF, 40, 0.2, 0.2, 0.2, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 2, (float) 1);
					}
					double heal = damager.getHealth() + 1.50 + 1.50 * level;
					double attribute = damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
					if(heal < attribute) {
						damager.setHealth(heal);
					}
					else {
						damager.setHealth(attribute);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Lifesteal",
					new ArrayList<String>(Arrays.asList(
						"&7When you shoot the enemy, there is a chance",
						"&7that you steal some life force from the enemy.",
						"&7Causing you to gain health."
					))
				);
			}
		}));
		this.listBow.put("Lightning", new Pair<>(Condition.PROJECTILE_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 3.5 + 0.5 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0D, victim.getLocation().getZ() + 0D);
					victim.getWorld().strikeLightningEffect(locCF);
					victim.damage(event.getFinalDamage() + (5.00 + 1.00 * level));
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Lightning",
					new ArrayList<String>(Arrays.asList(
						"&7When you shoot the enemy, there is a chance",
						"&7that the arrow is electrically charged and will",
						"&7send a lightning strike down from the heavens, striking",
						"&7the enemy that you shot and dealing extra damage."
					))
				);
			}
		}));
		this.listBow.put("Paralyze", new Pair<>(Condition.PROJECTILE_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 4  + level) {
					Location location = victim.getLocation();
			        location.getWorld().strikeLightningEffect(location);
			        p.applyEffect(victim, PotionEffectType.SLOW, 20, 100 + 20 * level);
			        new BukkitRunnable() {
			        	int count = 0;
			        	public void run() {
			        		if(count <= 100 + level * 20) {
			        			victim.playEffect(EntityEffect.HURT); 
				        		victim.damage(0.5 + 0.1 * level);
			        		}
			        		else {
			        			count = 0;
			        			cancel();
			        		}
			        	}
			        }.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Paralyze",
					new ArrayList<String>(Arrays.asList(
						"&7When you shoot the enemy, there is a chance",
						"&7that the arrow will paralyze the enemy. Causing",
						"&7the enemy to recieve the slowness effect and",
						"&7let their shake."
					))
				);
			}
		}));
		this.listBow.put("Pickpocket", new Pair<>(Condition.PROJECTILE_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 0.5 + 0.5 * level) {
					if(damager instanceof Player && victim instanceof Player) {
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
						damager.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, locCF, 30, 0.1, 0.1, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, (float) 1.25);
						}
						DFPlayer dfPlayer = dfManager.getEntity(damager);
						DFPlayer dfVictim = dfManager.getEntity(victim);
						double tempMoney = (double) (dfVictim.getMoney() * (0.01 + 0.01 * level));
						dfPlayer.addMoney(tempMoney);
						dfVictim.removeMoney(tempMoney);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Pickpocket",
					new ArrayList<String>(Arrays.asList(
						"&7When you shoot the enemy, there is a chance",
						"&7that you magically steal some balance from the enemy."
					))
				);
			}
		}));
		this.listBow.put("Pierce", new Pair<>(Condition.PROJECTILE_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 2 + 0.5 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, locCF, 100, 5.5, 5.5, 5.5, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, (float) 1);
					}
					event.setCancelled(true);
					victim.damage(event.getDamage());
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Pierce",
					new ArrayList<String>(Arrays.asList(
						"&7When you shoot the enemy, there is a chance",
						"&7that the arrow will be imbedded with ancient magic.",
						"&7Causing your arrow's damage to ignore the enemy's armor."
					))
				);
			}
		}));
		this.listBow.put("Poison", new Pair<>(Condition.PROJECTILE_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 7 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.SLIME, locCF, 20, 0.05, 0.3, 0.05, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_SPIDER_AMBIENT, 2, (float) 1.25);
					}
					int amp = 0 + level;
					int durationAdd = 160 + 40 * level;
					p.applyEffect(victim, PotionEffectType.POISON, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Poison",
					new ArrayList<String>(Arrays.asList(
						"&7When you shoot the enemy, there is a chance",
						"&7that the wound that the arrow creates will poison",
						"&7the enemy for a few seconds."
					))
				);
			}
		}));
		this.listBow.put("Sandstorm", new Pair<>(Condition.LEFT_CLICK, new CommandFile() {
			ArrayList<UUID> activated = new ArrayList<UUID>();
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			HashMap<UUID, Integer> levels = new HashMap<UUID, Integer>();
			HashMap<UUID, Integer> arrows = new HashMap<UUID, Integer>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!cooldown.contains(damager.getUniqueId()) && !activated.contains(damager.getUniqueId())) {
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(damager.getLocation(), Sound.ENTITY_HUSK_AMBIENT, 2, (float) 1.5);
					}
					cooldown.add(damager.getUniqueId());
					levels.put(damager.getUniqueId(), level);
					activated.add(damager.getUniqueId());
					damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have prepared a &6Sandstorm &aarrow!"));
				}
			}
			@EventHandler
			public void shootArrow(DFShootBowEvent event) {
				Player player = event.getShooter();
				if(!event.isCancelled()) {
					if(event.getProjectile() instanceof Arrow) {
						Arrow arrow = (Arrow) event.getProjectile();
						UUID uuid = player.getUniqueId();
						if(activated.contains(uuid)) {
							int level = levels.get(uuid);
							activated.remove(uuid);
							BlockData data = Bukkit.createBlockData(Material.SAND);
							new BukkitRunnable() {
								public void run() {
									if(!arrow.isDead()) {
										Location locCF = new Location(arrow.getWorld(), arrow.getLocation().getX() + 0D, arrow.getLocation().getY() + 0D, arrow.getLocation().getZ() + 0D);
										arrow.getWorld().spawnParticle(Particle.BLOCK_CRACK, locCF, 3, data); 
									}
									else {
										cancel();
									}
								}
							}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 2L);
							new BukkitRunnable() {
								public void run() {
									cooldown.remove(uuid);
								}
							}.runTaskLater(CustomEnchantments.getInstance(), 900L - 65L * level);
							arrows.put(arrow.getUniqueId(), levels.get(player.getUniqueId()));
							levels.remove(uuid);
						}
					}
				}
			}
			@EventHandler
			public void arrowHit(ProjectileHitEvent event) {
				if(event.getEntity() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getEntity();
					if(arrow.getShooter() instanceof Player) {
						Player shooter = (Player) arrow.getShooter();
						if(arrows.containsKey(arrow.getUniqueId())) {
							arrow.setPickupStatus(PickupStatus.DISALLOWED);
							if(event.getHitEntity() != null) {
								this.loop(shooter, arrow, arrow);
							}
							else {
								this.loop(shooter, arrow, event.getHitEntity());
							}
							
						}
					}
				}
			}
			public void loop(Player shooter, Arrow arrow, Entity en) {
				int level = arrows.get(en.getUniqueId());
				final double range = 6.00 + level * 0.75; 
				final double damage = 0.25 + 0.25 * level;
				final int count = 6 + level * 2;
				new BukkitRunnable() {
					int c = 0;
					public void run() {
						if(c <= count) {
							for(Entity e : en.getNearbyEntities(range, range, range)) {
								if(e != shooter) {
									if(e instanceof LivingEntity) {
										if(facManager.isFriendly(shooter, e)) {
											LivingEntity ent = (LivingEntity) e;
											BlockData data = Bukkit.createBlockData(Material.SAND);
											double finalDamage = damage - (e.getLocation().distance(en.getLocation())) / 1.5;
											if(shooter instanceof Player) {
												ent.setKiller((Player)shooter);
											}
											ent.damage(finalDamage);
											Location locCF = new Location(ent.getWorld(), ent.getLocation().getX() + 0D, ent.getLocation().getY() + 1.5D, ent.getLocation().getZ() + 0D);
											ent.getWorld().spawnParticle(Particle.BLOCK_CRACK, locCF, 7, data);
										}
									}
								}
							}
							c++;
						}
					}
				}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 45 - 5 * level);
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Sandstorm",
					new ArrayList<String>(Arrays.asList(
						"&7When you left click, you prepare a &6Sandstorm",
						"&7arrow. When you hit a block/enemy, enemies nearby",
						"&7this arrow, recieve the blindness effect and",
						"&7recieve damage slowly over time.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listBow.put("Sharpshooter", new Pair<>(Condition.PROJECTILE_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				if(damager.getLocation().distance(victim.getLocation()) < 5.50 + 1.50 * level) {
					Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.CRIT_MAGIC, locCF1, 40, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 2, (float) 1.5);
					}
					event.setDamage(event.getDamage() + 5.50 + 1.50 * level - damager.getLocation().distance(victim.getLocation()));
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Sharpshooter",
					new ArrayList<String>(Arrays.asList(
						"&7When you shoot the enemy, close ranged",
						"&7attacks deal more damage."
					))
				);
			}
		}));
		this.listBow.put("Lazer Shot", new Pair<>(Condition.LEFT_CLICK, new CommandFile() {
			ArrayList<UUID> activated = new ArrayList<UUID>();
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			HashMap<UUID, Integer> levels = new HashMap<UUID, Integer>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!cooldown.contains(damager.getUniqueId()) && !activated.contains(damager.getUniqueId())) {
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(damager.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 1.5);
					}
					cooldown.add(damager.getUniqueId());
					levels.put(damager.getUniqueId(), level);
					activated.add(damager.getUniqueId());
					damager.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou prepare to &6shoot a Star!"));
				}
			}
			@EventHandler
			public void shootArrow(DFShootBowEvent event) {
				Player player = event.getShooter();
				UUID uuid = player.getUniqueId();
				if(activated.contains(uuid)) {
					event.getProjectile().remove();
					event.setCancelled(true);
					activated.remove(uuid);
					int level = levels.get(uuid);
					double range = 20 + 2.5 * level;
					double damage = 12.0 + 3.0 * level;
					int duration = 200 + 50 * level;
					int pierce = 2 + level;
					int pierced = 0;
					ArrayList<UUID> hit = new ArrayList<UUID>();
					Location loc = player.getLocation().clone().add(0, 1.65, 0);
					loc.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_ROCKET_SHOOT, 2, (float) 1);
					for(double i = 0.0; i <= range; i += 0.2) {
						loc.add(loc.getDirection().multiply(0.2F));
						loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 1, 0.0, 0.0, 0.0, 0.0);
						for(Entity en : loc.getNearbyEntities(0.2, 0.2, 0.2)) {
							if(en != null && en instanceof LivingEntity && en != player) {
								LivingEntity ent = (LivingEntity) en;
								if(!facManager.isFriendly(player, en)) {
									if(!hit.contains(ent.getUniqueId())) {
										hit.add(ent.getUniqueId());
										if(player instanceof Player) {
											ent.setKiller((Player)player);
										}
										ent.damage(damage);
										ent.setFireTicks(duration);
										damage = damage / 2;
										duration = duration / 2;
										loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 20, 0.1, 0.1, 0.1, 0.1);
										loc.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2, (float) 1);
										pierced++;
										if(pierced == pierce) {
											break;
										}
									}
								}
							}
						}
						if(loc.getBlock().getType() != Material.AIR) {
							break;
						}
					}
					new BukkitRunnable() {
						public void run() {
							cooldown.remove(uuid);
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 1300L - 80L * level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Lazer Shot",
					new ArrayList<String>(Arrays.asList(
						"&7When you left click, you prepare to shoot a lazer.",
						"&7When you shoot, it will shoot a lazer towards the direction",
						"&7that you are looking. When this lazer hits a block or an",
						"&7entity, the lazer will pierce the enemy. Causing that enemy",
						"&7to be set on fire and take damage. This lazer pierces other",
						"&7enemies, the lazer will get it's damage halved per enemy hit.",
						"&7The same rules are applied for the duration of the fire.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listBow.put("Sniper Shot", new Pair<>(Condition.PROJECTILE_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				if(damager.getLocation().distance(victim.getLocation()) > 20.00 - 1.60 * level) {
					Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.FALLING_DUST, locCF1, 80, 0.15, 0.15, 0.15, 0); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_SKELETON_HURT, 2, (float) 1.1);
					}
					event.setDamage(event.getDamage() + damager.getLocation().distance(victim.getLocation()) - 20.00 - 1.60 * level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Sniper Shot",
					new ArrayList<String>(Arrays.asList(
						"&7When you shoot the enemy, long ranged",
						"&7attacks deal more damage."
					))
				);
			}
		}));
		this.listBow.put("Newtowns Manipulation", new Pair<>(Condition.PROJECTILE_SHOOT, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, int level, DFShootBowEvent event) {
				Vector v = event.getProjectile().getVelocity().multiply(1.035D + 0.035D * level);
				event.getProjectile().setVelocity(v);
				event.getProjectile().setGravity(false);
				new BukkitRunnable() {
					public void run() {
						if(event.getProjectile() != null) {
							event.getProjectile().remove();
						}
					}	
				}.runTaskLater(CustomEnchantments.getInstance(), 200L);
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Newtons Manipulation",
					new ArrayList<String>(Arrays.asList(
						"&7When you shoot an arrow, it will defy",
						"&7gravity and shoot faster."
					))
				);
			}
		}));
		this.listBow.put("Weakness", new Pair<>(Condition.PROJECTILE_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + level) {
					Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2.5D, victim.getLocation().getZ() + 0D);
					BlockData block = Material.COAL_BLOCK.createBlockData();
					victim.getWorld().spawnParticle(Particle.FALLING_DUST, locCF1, 80, 0.15, 0.15, 0.15, 0, block); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_SKELETON_HURT, 2, (float) 1.1);
					}
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 200 + 50 * level;
					p.applyEffect(victim, PotionEffectType.WEAKNESS, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Weakness",
					new ArrayList<String>(Arrays.asList(
						"&7When you shoot the enemy, there is a chance",
						"&7that they will be weakened and recieve the",
						"&7weakness effect for a few seconds."
					))
				);
			}
		}));
		this.listBow.put("Wither", new Pair<>(Condition.PROJECTILE_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 7 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.SMOKE_NORMAL, locCF, 60, 0, 0, 0, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_WITHER_SHOOT, 2, (float) 1);
					}
					int amp = 0 + level;
					int durationAdd = 200 + 40 * level;
					p.applyEffect(victim, PotionEffectType.WITHER, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Pickpocket",
					new ArrayList<String>(Arrays.asList(
						"&7When you shoot the enemy, there is a chance",
						"&7that you magically steal some balance from the enemy."
					))
				);
			}
		}));
		
	}
	
	public void loadShieldEnchantments() {
		this.listShield = new HashMap<String, Pair<Condition, CommandFile>>();
		this.listShield.put("Parry", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			HashMap<UUID, Integer> activate = new HashMap<UUID, Integer>();
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				if(!cooldown.contains(victim.getUniqueId())) {
					activate.put(victim.getUniqueId(), level);
					cooldown.add(victim.getUniqueId());
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_ANVIL_PLACE, 2, (float) 2.0);
					}
					new BukkitRunnable() {
						public void run() {
							if(activate.containsKey(victim.getUniqueId())) {
								activate.remove(victim.getUniqueId());
								for(Player victim1 : Bukkit.getOnlinePlayers()) {
									((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, (float) 0.75);
								}
							}
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 40L);
				}
			}
			@EventHandler
			public void check(EntityDamageByEntityEvent event) {
				if(event.getDamager() instanceof LivingEntity) {
					LivingEntity damager = (LivingEntity) event.getDamager();
					if(activate.containsKey(damager.getUniqueId())) {
						event.setDamage(event.getDamage() + 1.5 + 1.5 * activate.get(damager.getUniqueId()));
						activate.remove(damager.getUniqueId());
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(damager.getLocation(), Sound.BLOCK_ANVIL_PLACE, 2, (float) 2.5);
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Parry",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemies attacks you and when your blocking,",
						"&7you will prepare for a counter attack. After &6Parry",
						"&7activates, your next attack will gain additional damage."
					))
				);
			}
		}));
		this.listShield.put("Absorbing", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 18 + 4 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.CRIT_MAGIC, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 2, (float) 1.4);
					}
					int amp = 0;
					int durationAdd = 80 + 10 * level;
					ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.ABSORPTION));
					p.applyEffect(victim, types, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Absorbing",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you will start to learn on how to absorb the damage.",
						"&7Causing you to recieve the absorption effect."
					))
				);
			}
		}));
		this.listShield.put("Absorbing Combo", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			HashMap<UUID, Integer> list = new HashMap<UUID, Integer>();
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				if(!list.containsKey(victim.getUniqueId())) {
					list.put(victim.getUniqueId(), 1);
				}
				list.put(victim.getUniqueId(), list.get(victim.getUniqueId()) + 1);
				if(list.get(victim.getUniqueId()) == 8 - level) {
					EntityLiving l = ((CraftLivingEntity)victim).getHandle();
					l.setAbsorptionHearts(l.getAbsorptionHearts() + 3.00F + 1.50F * level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Absorbing Combo",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you multiple times,",
						"&7You gain a certain amount of absorption hearts."
					))
				);
			}
		}));
		this.listShield.put("Absorbing Comeback", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			HashMap<UUID, Double> extraDamage = new HashMap<UUID, Double>();
			@Override
			public void activateEnchantment(LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 10 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH, 2, (float) 1.3);
					}
					extraDamage.put(victim.getUniqueId(), event.getDamage() * (0.10 + 0.05 * level));
				}
			}
			@EventHandler
			public void attack(EntityDamageByEntityEvent event) {
				if(extraDamage.containsKey(event.getDamager().getUniqueId())) {
					event.setDamage(event.getDamage() + extraDamage.get(event.getDamager().getUniqueId()));
					extraDamage.remove(event.getDamager().getUniqueId());
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Absorbing Comeback",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7for you to absorb a part of the incoming damage",
						"&7and return that damage in the next time that you attack."
					))
				);
			}
		}));
		this.listShield.put("Adrenaline Rush", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				DFPlayer dfPlayer = dfManager.getEntity(victim);
				if(dfPlayer.getHealth() <= dfPlayer.getMaxHealth() / 100 * 25) {
					if(i <= 18 + 6 * level) {
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.CLOUD, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_RABBIT_HURT, 2, (float) 1);
						}
						int amp = 2;
						int durationAdd = 130 + 20 * level;
						ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.JUMP, PotionEffectType.SPEED));
						p.applyEffect(victim, types, amp, durationAdd);
						dfPlayer.addSpdCal(4.0 + 4.0 * level, 130 + 20 * level);
						cooldown.add(victim.getUniqueId());
						new BukkitRunnable() {
							public void run() {
								cooldown.remove(victim.getUniqueId());
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 1200L - 50L * level);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Adrenaline Rush",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you and you are below",
						"&7a certain percentage of your current health, there",
						"&7is a chance that you will get an Adrenaline Rush.",
						"&7Causing you to recieve the jump and speed boost effect",
						"&7for a few seconds.",
						"&7This enchantment has a cooldown."
					))
				);
			}
		}));
		this.listShield.put("Arcanist Explosion", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 16 + 3 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, locCF, 10, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, (float) 1);
					}
					double range = 3 + 0.5 * level;
					for(Entity entity : victim.getNearbyEntities(range, range, range)){
						if(entity instanceof LivingEntity) {
							if(entity != victim) {
								if(facManager.isFriendly(victim, entity)) {
									LivingEntity entity1 = (LivingEntity) entity;
									if(victim instanceof Player) {
										entity1.setKiller((Player)victim);
									}
									entity1.damage(2.00 + 0.50 * level);
								}
							}
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Arcanist Explosion",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that a small explosion will generate from your location.",
						"&7Causing nearby enemies to recieve damage."
					))
				);
			}
		}));
		this.listShield.put("Bane", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 10 + level) {
					Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.5D, damager.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.DRIP_WATER, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 2, (float) 0.6);
					}
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 150 + 25 * level;
					ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.SLOW_DIGGING));
					p.applyEffect(victim, types, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Bane",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that a fatigue curse will be put on them.",
						"&7Causing them to recieve the minin fatigue effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Blast Off", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 12 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.CRIT_MAGIC, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 2, (float) 1.4);
					}
					int amp = 20;
					int durationAdd = 150 + 15 * level;
					ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.DAMAGE_RESISTANCE));
					p.applyEffect(victim, types, amp, durationAdd);
					new BukkitRunnable() {
						@Override
						public void run() {
							victim.setVelocity(new Vector(0, 1.2 + 0.2 * level, 0));
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 1L);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Blast Off",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you create an explosion. Launching you high",
						"&7in the sky. You will be unable to recieve any",
						"&7damage for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Cactus", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 15 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.CRIT, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_HURT, 2, (float) 1.1);
					}
					if(victim instanceof Player) {
						damager.setKiller((Player)victim);
					}
					damager.damage(1 + level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Cactus",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that a small needle will puncture the enemy.",
						"&7Causing them to recieve damage."
					))
				);
			}
		}));
		this.listShield.put("Curse", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				ArrayList<UUID> cursed = new ArrayList<UUID>();
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 0.2 + 0.2 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 2, (float) 0.5);
					cursed.add(damager.getUniqueId());
					new BukkitRunnable() {
						public void run() {
							if(cursed.contains(damager.getUniqueId())) {
								if(victim instanceof Player) {
									damager.setKiller((Player)victim);
								}
								damager.damage(Double.MAX_VALUE);
								cursed.remove(damager.getUniqueId());
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 600 - 40 * level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Curse",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you curse them to death. Causing them to be",
						"&7put under a curse that kills the enemy after a while.",
						"&7To break the curse, the attacker needs to hit you at least 3",
						"&7times to remove the curse."
					))
				);
			}
		}));
		this.listShield.put("Full Counter", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 0.65 + level * 0.65) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_HURT, 2, (float) 1.5);
					}
					double damage = event.getDamage();
					if(victim instanceof Player) {
						damager.setKiller((Player)victim);
					}
					damager.damage(damage);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Full Counter",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you absorb all the incoming damage.",
						"&7The next time you attack, you will return all",
						"&7of the absorbed damage back to the enemy that you attack0."
					))
				);
			}
		}));
		this.listShield.put("Earthquake", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 2.5 + level * 0.5) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.2D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, locCF, 60, 0.1, 0.1, 0.1, 0.1, Material.GRASS_BLOCK.createBlockData()); 
					victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_GRASS_BREAK, 2, (float) 1);
					double range = 8.50 + level * 1.50;
					for(Entity e : victim.getNearbyEntities(range, range, range)) {
						if(e instanceof LivingEntity) {
							LivingEntity ent = (LivingEntity) e;
							ent.damage(3 + level * 3);
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Earthquake",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that your rage of being hit will turn into",
						"&7an Earthquake. Causing nearby enemies to",
						"&7recieve damage."
					))
				);
			}
		}));
		this.listShield.put("Enlightened", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 10 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.HEART, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, (float) 1.1);
					}
					double heal = victim.getHealth() + (1.5 + 1.5 * level);
					double attribute = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
					if(heal < attribute) {
						victim.setHealth(heal);
					}
					else {
						victim.setHealth(attribute);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Enlightened",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you get blessed by an unkown force. Causing",
						"&7to regenerate health instantly."
					))
				);
			}
		}));
		this.listShield.put("Escape", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 17 + level) {
					if(victim.getHealth() <= 12 + 2 * level) {
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.2D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.SPELL, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_CHICKEN_HURT, 2, (float) 1.1);
						}
						int amp = 0 + level;
						int durationAdd = 200 + 50 * level;
						ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.SPEED, PotionEffectType.ABSORPTION));
						p.applyEffect(victim, types, amp, durationAdd);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Last Stand",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you and you are below a",
						"&7certain amount of health, there is a chance",
						"&7an opportunity comes to escape the battle.",
						"&7Causing you to recieve the speed and absorption effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Fortitude", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 9 + 3.0 * level) {
					double maxHealth = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
					double heal = 0.25 + 0.35 * level;
					new BukkitRunnable() {
						int count = 0;
						int check = 2 + level;
						public void run() {
							if(count <= check) {
								count++;
								Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
								victim.getWorld().spawnParticle(Particle.HEART, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
								victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_ANVIL_FALL, 2, (float) 1.3);
								if(victim.getHealth() + heal <= maxHealth) {
									victim.setHealth(victim.getHealth() + heal);
								}
								else {
									victim.setHealth(maxHealth);
								}
							}
							else {
								cancel();
								count = 0;
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 30L - 4L * level);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Fortitude",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you quickly regenerate stamina. Causing you",
						"&7to regenerate health in a short amount of time."
					))
				);
			}
		}));
		this.listShield.put("Fossil Blaze", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 8 + 2.5 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.FLAME, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_ANVIL_FALL, 2, (float) 1.3);
					double range = 3.00 + 1.25 * level;
					for(Entity entity : victim.getNearbyEntities(range, range, range)) {
						if(entity != null) {
							if(entity instanceof LivingEntity && entity != victim) {
								LivingEntity attacked = (LivingEntity) entity;
								if(facManager.isFriendly(victim, attacked)) {
									attacked.setFireTicks(attacked.getFireTicks() + (140 + 20 * level));
								}
							}
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Fossil Blaze",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that your rage ignites something within you, forming",
						"&7an explosion of fire that set's enemies nearby aflame.",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Ghostly", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 18 + 4 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.SMOKE_LARGE, locCF, 100, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_HURT, 2, (float) 1.5);
					}
					int amp = 0;
					int durationAdd = 200 + 80 * level;
					PotionEffectType type = PotionEffectType.INVISIBILITY;
					p.applyEffect(victim, type, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Ghostly",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that a myserious aura forms around you. Causing",
						"&7you to recieve the invisibility effect for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Harden", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 10 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.WATER_BUBBLE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_ANVIL_FALL, 2, (float) 1.3);
					}
					int amp = 0 + (int)(level * 0.5);
					int durationAdd = 60 + 10 * level;
					p.applyEffect(victim, PotionEffectType.DAMAGE_RESISTANCE, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Harden",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you prepare for a flurry of attack's coming in.",
						"&7Causing you to recieve the resistance effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Hastened", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 10 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2, (float) 1.1);
					}
					int amp = 2 + level;
					int durationAdd = 60 + 7 * level;
					p.applyEffect(victim, PotionEffectType.FAST_DIGGING, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Hastened",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you feel like that you have to attack faster.",
						"&7Causing you to recieve the haste effect for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Ignite", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 13 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.FLAME, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_BLAZE_HURT, 2, (float) 1.1);
					}
					damager.setFireTicks(damager.getFireTicks() + (150 + 70 * level));
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Ignite",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that your armor gets very hot.",
						"&7Causing the enemy to be engulfed by flames",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Invincibility", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			ArrayList<UUID> list = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 4.5 + 0.75 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_ANVIL_LAND, 2, (float) 1.3);
					}
					if(!list.contains(victim.getUniqueId())) {
						list.add(victim.getUniqueId());
						new BukkitRunnable() {
							public void run() {
								if(list.contains(victim.getUniqueId())) {
									list.remove(victim.getUniqueId());
								}
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 80 + 10 * level);
					}
				}
			}
			@EventHandler
			public void CustomEnchantmentsMInvincibilityI(EntityDamageByEntityEvent event) {
				if(event.getDamager() instanceof LivingEntity){
					if(event.getEntity() instanceof Player) {
						Player victim = (Player) event.getEntity();
						if(list.contains(victim.getUniqueId())) {
							event.setCancelled(true);
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Invincibility",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you will be blessed by godly powers.",
						"&7Causing you to be immune to melee and ranged attacks",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Jellyfish", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 4 + 1.5 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.2D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.WATER_BUBBLE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_GUARDIAN_DEATH, 2, (float) 1.5);
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 140 + 20 * level;
					ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.SLOW, PotionEffectType.POISON, PotionEffectType.SLOW_DIGGING));
					p.applyEffect(damager, types, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Jellyfish",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that a jelly's sting will summon from the armor and",
						"&7piercing the enemy's skin to inflict a deadly poison.",
						"&7Causing the enemy to recieve the slowness, poison and",
						"&7the mining fatigue effect for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Kadabra", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 8 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GHAST_AMBIENT, 2, (float) 1.4);
					}
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 140 + 40 * level;
					p.applyEffect(damager, PotionEffectType.WEAKNESS, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Kadabra",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that a curse of weakness will be put upon them.",
						"&7Causing them to recieve the weakness effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Last Stand", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i < 20 + level * 3) {
					if(victim.getHealth() <= 12 + level) {
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.END_ROD, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_RABBIT_HURT, 2, (float) 1.1);
						}
						int amp = 0 + level;
						int durationAdd = 150 + 50 * level;
						ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.SPEED, PotionEffectType.ABSORPTION, PotionEffectType.REGENERATION));
						p.applyEffect(victim, types, amp, durationAdd);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Last Stand",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, and your health is below",
						"&7a certain point, there is a chance that",
						"&7you will give it your all, causing you to recieve",
						"&7the speed, absorption and regeneration effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Rage", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, (float) 0.8);
					}
					int amp = 0 + level;
					int durationAdd = 60 + 8 * level;
					p.applyEffect(victim, PotionEffectType.INCREASE_DAMAGE, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Rage",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you will become enraged. Causing you to",
						"&7recieve the strength effect for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Regenerator", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i < 13 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.HEART, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_DRINK, 2, (float) 1.1);
					}
					int amp = 0 + level;
					int durationAdd = 300 - 65 * level;
					p.applyEffect(victim, PotionEffectType.REGENERATION, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Regenerator",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you call out to an unknown power. Causing",
						"&7you to recieve the regeneration effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Saturation", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i < 13 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.TOTEM, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_COW_MILK, 2, (float) 1);
					}
					int amp = 0 + level;
					int durationAdd = 100 + 30 * level;
					p.applyEffect(victim, PotionEffectType.SATURATION, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Saturation",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that your hunger will be satisfied, somehow.",
						"&7Causing you to recieve the saturation effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Savior", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(victim.getHealth() < victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * (0.17 + (0.01 * level))) {
					if(i < 20 + level * 3) {
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, locCF, 50, 0.1, 0.1, 0.1, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GHAST_AMBIENT, 2, (float) 1.5);
						}
						int amp = (int)Math.floor(0 + (level) / 2);
						int durationAdd = 300 + 50 * level;
						p.applyEffect(victim, PotionEffectType.REGENERATION, amp, durationAdd);
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Savior",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you and your health is",
						"&7a certain point, there is a chance that",
						"&7someone will reach out and give you a hand.",
						"&7Causing you to recieve the regeneration effect.",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Slowness", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 16 + level) {
					Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.5D, damager.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.SPIT, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 2, (float) 1.1);
					}
					int amp = 0;
					int durationAdd = 200 + 50 * level;
					p.applyEffect(damager, PotionEffectType.SLOW, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Slowness",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you slow them down for a long while.",
						"&7Causing them to recieve the slowness effect",
						"&7for a while."
					))
				);
			}
		}));
		this.listShield.put("Slow", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 12 + level) {
					Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.5D, damager.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.SPIT, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 2, (float) 1.1);
					}
					int amp = 0 + level;
					int durationAdd = 60 + 15 * level;
					p.applyEffect(damager, PotionEffectType.SLOW, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Slow",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you slow them down. For a short while.",
						"&7Causing them to recieve the slowness effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Smoke Screen", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 35) {
					if(victim.getHealth() < 13 + level) {
						double range = 3 + level;
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, locCF, 100, 4.5, 4.5, 4.5, 4.5); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 2, (float) 0.7);
						}
						for (Entity entity : victim.getNearbyEntities(range, range, range)){
							if(entity instanceof LivingEntity) {
								if(entity != victim) {
									LivingEntity entity1 = (LivingEntity) entity;
									int amp = (int)Math.floor(0 + (level) / 2);
									int durationAdd = 150 + 50 * level;
									ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.BLINDNESS, PotionEffectType.SLOW));
									p.applyEffect(entity1, types, amp, durationAdd);
								}
							}
						}
					}
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Smoke Screen",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you and you are below a",
						"&7certain amount of health, there is a chance",
						"&7that you create a smoke screen to escape.",
						"&7Causing nearby enemies to recieve the",
						"&7slowness and blindness effect for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Snare", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i < 4.5 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.SUSPENDED, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_ITEM_BREAK, 2, (float) 1);
					}
					int amp = 20;
					int amp1 = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 140 + 20 * level;
					p.applyEffect(victim, PotionEffectType.SLOW, amp, durationAdd);
					p.applyEffect(victim, PotionEffectType.SLOW_DIGGING, amp1, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Snare",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you literally shut them up. Causing them to",
						"&7recieve the slowness and mining fatigue effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Tank", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 15 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.2D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.SPELL, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_CHICKEN_HURT, 2, (float) 1.1);
					}
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 200 + 50 * level;
					p.applyEffect(victim, new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.INCREASE_DAMAGE, PotionEffectType.DAMAGE_RESISTANCE, PotionEffectType.SLOW)), amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Tank",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you prepare for more incoming attacks.",
						"&7Causing you to recieve the strength, slowness and",
						"&7resistance effect for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Valor", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 10 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.WATER_BUBBLE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_ANVIL_FALL, 2, (float) 1.3);
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 100 + 30 * level;
					p.applyEffect(victim, PotionEffectType.DAMAGE_RESISTANCE, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Valor",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you feel protection coming over you.",
						"&7Causing you to recieve the resistance effect",
						"&7for a few seconds."
					))
				);
			}
		}));
		this.listShield.put("Withering", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 12 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.SMOKE_NORMAL, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_WITHER_SHOOT, 2, (float) 1.1);
					}
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 150 + 50 * level;
					p.applyEffect(damager, PotionEffectType.WITHER, amp, durationAdd);
				}
			}
			@Override
			public ItemStack getStack() {
				return builder.constructItem(
					Material.ENCHANTED_BOOK,
					1,
					"&6Withering",
					new ArrayList<String>(Arrays.asList(
						"&7When the enemy attacks you, there is a chance",
						"&7that you wish that your enemy would just wither away.",
						"&7Causing them to recieve the wither effect",
						"&7for a few seconds."
					))
				);
			}
		}));
	}
	
	public HashMap<String, Pair<Condition, CommandFile>> getMeleeEnchantments(){
		return this.listMelee;
	}
	
	public HashMap<String, Pair<Condition, CommandFile>> getArmorEnchantments(){
		return this.listArmor;
	}
	
	public HashMap<String, Pair<Condition, CommandFile>> getBowEnchantments(){
		return this.listBow;
	}
	
	public HashMap<String, Pair<Condition, CommandFile>> getShieldEnchantments(){
		return this.listShield;
	}
}
