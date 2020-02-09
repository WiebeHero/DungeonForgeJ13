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
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;

import javafx.util.Pair;
import me.WiebeHero.APIs.ParticleAPI;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomEvents.DFShootBowEvent;
import me.WiebeHero.CustomMethods.PotionM;
import me.WiebeHero.EnchantmentAPI.EnchantmentCondition.Condition;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Skills.DFPlayer;
import me.WiebeHero.Skills.Enums.Classes;
import net.minecraft.server.v1_13_R2.EntityLiving;
import net.minecraft.server.v1_13_R2.PacketPlayOutWorldParticles;

public class Enchantment extends CommandFile implements Listener{
	//Empty Constructor
	DFPlayer df = new DFPlayer();
	DFFaction fac = new DFFaction();
	PotionM p = new PotionM();
	ParticleAPI pApi = new ParticleAPI();
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
					if(df.containsPlayer(damager)) {
						DFPlayer dfPlayer = df.getPlayer(damager);
						dfPlayer.addAtkCal(200.0 + 50.0 * level, 1);
						new BukkitRunnable() {
							public void run() {
								damager.damage(event.getFinalDamage() * 0.60 - (0.05 * level));
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 1L);
					}
				}
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
		}));
		this.listMelee.put("Blast", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 4 + 1.5 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, locCF, 100, 5, 5, 5, 0.1);
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, (float) 1);
					EntityDamageEvent e = new EntityDamageEvent(damager, EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, victim.getHealth());
					victim.setLastDamageCause(e);
					CustomEnchantments.getInstance().getServer().getPluginManager().callEvent(e);
					victim.damage(5 + level);
				}
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
							if(df.containsPlayer(damager)) {
								DFPlayer dfPlayer = new DFPlayer(damager);
								dfPlayer.addAtkCal(12.5 * brandList.get(ent.getUniqueId()), 1);
							}
						}
					}
					else if(event.getDamager() instanceof Arrow) {
						if(brandList.containsKey(ent.getUniqueId())) {
							Arrow arrow = (Arrow) event.getDamager();
							if(arrow.getShooter() instanceof LivingEntity) {
								LivingEntity shooter = (LivingEntity) arrow.getShooter();
								if(df.containsPlayer(shooter)) {
									LivingEntity damager = (LivingEntity) shooter;
									DFPlayer dfPlayer = new DFPlayer(damager);
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
			
		}));
		this.listMelee.put("Break", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + 0.5 * level) {
					Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, locCF1, 80, 0.15, 0.15, 0.15, 0, Material.ANVIL.createBlockData()); 
					victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_ANVIL_PLACE, 2, (float) 1.0);
					DFPlayer dfPlayer = new DFPlayer().getPlayer(victim);
					dfPlayer.removeDfCal(3.0 * level, 140 + (level * 20));
				}
			}
		}));
		this.listMelee.put("Charge", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> playerStuff = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, PlayerInteractEvent event) {
				if(!playerStuff.contains(damager.getUniqueId())) {
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 100 + 33 * level;
					ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.SPEED, PotionEffectType.INCREASE_DAMAGE));
					p.applyEffect(damager, types, amp, durationAdd);
					new BukkitRunnable() {
						public void run() {
							playerStuff.remove(damager.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 800 - 120 * level);
				}
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
										int random1 = new Random().nextInt(4) - 4;
										int random2 = new Random().nextInt(4) - 4;
										float x = ThreadLocalRandom.current().nextFloat() * 4 + (float)random1;
										float z = ThreadLocalRandom.current().nextFloat() * 4 + (float)random2;
										LivingEntity entity = (LivingEntity) e;
										entity.setVelocity(new Vector(x, 1.8 + 0.2 * level, z));
									}
								}
							}
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 1L);
				}
			}
		}));
		this.listMelee.put("Defensive Position", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 3 + level) {
					Location locCF = new Location(damager.getWorld(), damager.getLocation().getX() + 0D, damager.getLocation().getY() + 1.5D, damager.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.DRIP_WATER, locCF, 30, 0.1, 0.1, 0.1, 0.1);
					damager.getWorld().playSound(damager.getLocation(), Sound.BLOCK_ANVIL_FALL, 2, (float) 1);
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 100 + 20 * level;
					PotionEffectType type = PotionEffectType.DAMAGE_RESISTANCE;
					p.applyEffect(damager, type, amp, durationAdd);
				}
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
		}));
		this.listMelee.put("Dragons Fireball", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> playerStuff = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, PlayerInteractEvent event) {
				if(!playerStuff.contains(damager.getUniqueId())) {
					playerStuff.add(damager.getUniqueId()); // set to "true"
					damager.launchProjectile(DragonFireball.class);
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
					DFPlayer dfPlayer = new DFPlayer().getPlayer(victim);
					dfPlayer.removeSpdCal(1.5 * level, 140 + (level * 20));
				}
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
		}));
		this.listMelee.put("Cleric", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!cooldown.contains(damager.getUniqueId())) {
					DFFaction fac = new DFFaction().getFaction(damager.getUniqueId());
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
		}));
		this.listMelee.put("Happiness", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 4 + level) {
					DFPlayer player = new DFPlayer().getPlayer(victim);
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
			 		DFPlayer dfPlayer = new DFPlayer().getPlayer(damager);
			 		dfPlayer.addAtkCal(150 + level * 50, 1);
				}
			}
		}));
		this.listMelee.put("Large Fireball", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> playerStuff = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level) {
				playerStuff.add(damager.getUniqueId()); // set to "true"
				Fireball ball = damager.launchProjectile(Fireball.class);
				ball.setMetadata("Level", new FixedMetadataValue(CustomEnchantments.getInstance(), level));
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
		            LivingEntity victim = (LivingEntity) event.getHitEntity();
		            Block hitBlock = (Block) event.getHitBlock();
		            if(f.getShooter() instanceof Player){
		                if(f.hasMetadata("Level")) {
	                		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
							victim.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, locCF, 60, 2, 2, 2, 1); 
							for(Player victim1 : Bukkit.getOnlinePlayers()) {
								((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, (float) 1.25);
							}
							int level = f.getMetadata("Level").get(0).asInt();
							double range = 4 + 1 * level;
							if(victim instanceof LivingEntity) {
								if(victim != null) {
									for(Entity en : victim.getNearbyEntities(range, range, range)){
										LivingEntity victimsNearby = (LivingEntity) en;
										if(en == victim) {
											victim.damage(10 + 3 * level);
										}
										else {
											victimsNearby.damage(5 + 1.5 * level);	
										}
									}
								}
							}
							else if(hitBlock instanceof Block) {
								if(hitBlock != null) {
									for(Entity en : hitBlock.getWorld().getNearbyEntities(locCF, range, range, range)){
										if(en instanceof LivingEntity) {
											LivingEntity victimsNearby = (LivingEntity) en;
											victimsNearby.damage(5 + 1.5 * level);
										}
									}
								}
							}
		                }
		            }
		        }
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
		}));
		this.listMelee.put("Looting", new Pair<>(Condition.ENTITY_DEATH_MELEE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDeathEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 30 + 10 * level) {
					if(!(victim instanceof Player)) {
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.6D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, locCF, 40, 0.2, 0.2, 0.2, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, (float) 1);
						}
						List<ItemStack> stacks = event.getDrops();
						for(int i1 = 0; i1 < stacks.size(); i1++) {
							if(stacks.get(i1).getMaxStackSize() != 1) {
								stacks.get(i1).setAmount((int)(stacks.get(i1).getAmount() * (1.50 + 0.30 * level)));
							}
						}
					}
				}
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
		}));
		this.listMelee.put("Numbness", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + 0.5 * level) {
					Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.8D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, locCF1, 80, 0.15, 0.15, 0.15, 0, Material.PINK_WOOL.createBlockData()); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_ARROW_SHOOT, 2, (float) 0.5);
					DFPlayer dfPlayer = new DFPlayer().getPlayer(victim);
					dfPlayer.removeRndCal(10.0 * level, 140 + (level * 40));
				}
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
		}));
		this.listMelee.put("Phantom", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> list = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				if(!list.contains(damager.getUniqueId())) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, locCF, 50, 0.2, 0.2, 0.2, 0.2);
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 2, (float) 0.5);
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
					}.runTaskLater(CustomEnchantments.getInstance(), 140L + 30L * level);
					new BukkitRunnable() {
						public void run() {
							
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 140L);
				}
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
						DFPlayer dfPlayer = new DFPlayer().getPlayer(damager);
						DFPlayer dfVictim = new DFPlayer().getPlayer(victim);
						double tempMoney = (double) (dfVictim.getMoney() * (0.01 + 0.01 * level));
						dfPlayer.addMoney(tempMoney);
						dfVictim.removeMoney(tempMoney);
					}
				}
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
								victim.damage(0.5 + 0.5 * level);
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 40 - 5 * level);
				}
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
		}));
		this.listMelee.put("Sharpness", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				event.setDamage(event.getDamage() + 0.40 + 0.40 * level);
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
							  victim.setVelocity(new Vector(0, 1.2 + 0.1 * level, 0));
						  }
					}.runTaskLater(CustomEnchantments.getInstance(), 1L);
				}
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
						if(snowball.containsKey(pro.getUniqueId())) {
							int level = snowball.get(pro.getUniqueId());
							snowball.remove(pro.getUniqueId());
							LivingEntity ent = (LivingEntity) event.getHitEntity();
							int amp = level;
							int durationAdd = 100 + 20 * level;
							ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.SLOW, PotionEffectType.BLINDNESS));
							p.applyEffect(ent, types, amp, durationAdd);
							ent.damage(1.5 + 0.5 * level);
						}
					}
				}
			}
		}));
		this.listMelee.put("Soul Burst", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 10 + 5.0 * level) {
					Location loc1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.50D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, loc1, 60, 0.1, 0.1, 0.1, 0.1);
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_ZOMBIE_DEATH, 2, (float) 0.5);
					double range = 5 + 1.5 * level;
					for(Entity e : victim.getNearbyEntities(range, range, range)) {
						if(e instanceof LivingEntity) {
							if(e != damager) {
								LivingEntity entity = (LivingEntity) e;
								entity.damage(6 + 2.5 * level);
							}
						}
					}
				}
			}
		}));
		this.listMelee.put("Reaper", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
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
					int durationAdd = 200 + 40 * level;
					p.applyEffect(victim, PotionEffectType.GLOWING, amp, durationAdd);
				}
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
		}));
		this.listMelee.put("Disturb Health", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + 0.5 * level) {
					Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.8D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.DAMAGE_INDICATOR, locCF1, 80, 0.15, 0.15, 0.15, 0); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_COW_HURT, 2, (float) 0.5);
					DFPlayer dfPlayer = new DFPlayer().getPlayer(victim);
					dfPlayer.removeHpCal(12.5 * level, 140 + (level * 20));
				}
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
					DFPlayer dfPlayer = new DFPlayer().getPlayer(victim);
					dfPlayer.removeCrtCal(3.0 * level, 100 + (level * 20));
				}
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
		}));
		this.listMelee.put("Weaken", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 6 + 0.5 * level) {
					Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.8D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, locCF1, 80, 0.15, 0.15, 0.15, 0, Material.BLACK_WOOL.createBlockData()); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 2, (float) 1.0);
					DFPlayer dfPlayer = new DFPlayer().getPlayer(victim);
					dfPlayer.removeAtkCal(4.5 + 4.5 * level, 140 + (level * 20));
				}
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
		}));
		this.listMelee.put("Wither", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 4 + level) {
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
		}));
		this.listMelee.put("Wizard", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 3 + level) {	
					Location location = victim.getLocation();
			        location.getWorld().strikeLightningEffect(location);
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 60 + 20 * level;
					p.applyEffect(victim, PotionEffectType.WEAKNESS, amp, durationAdd);
					victim.damage(4 + level);
					victim.setFireTicks(victim.getFireTicks() + (40 + 20 * level));
				}
			}
		}));
		this.listMelee.put("Wolf Pack", new Pair<>(Condition.PLAYER_DEATH_MELEE, new CommandFile() {
			HashMap<UUID, Integer> list = new HashMap<UUID, Integer>();
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 20 + 5 * level) {
					Location loc1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.25D, victim.getLocation().getZ() + 0D);
					damager.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc1, 60, 0, 0.3, 0, 0); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_WOLF_HOWL, 2, (float) 1);
					}
					Wolf wolf = (Wolf) damager.getWorld().spawnEntity(victim.getLocation(), EntityType.WOLF);
					wolf.setCustomName(new CCT().colorize("&a&lWolf &6[&aLv " + (level + 1) + "&6]"));
					wolf.setCustomNameVisible(true);
					wolf.setOwner((AnimalTamer)damager);
					wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(50.00 + level * 10.00);
					wolf.setHealth(50.00 + level * 10.00);
					wolf.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0 + level));
					wolf.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(5.0 + 5.0 * level);
					wolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(2.0 + 2.0 * level);
					wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(3.0 + 3.0 * level);
					list.put(wolf.getUniqueId(), level);
					new BukkitRunnable() {
						public void run() {
							wolf.remove();
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 600L + 50 * level);
				}
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
		}));
		this.listArmor.put("Absorbing Combo", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			HashMap<UUID, Integer> list = new HashMap<UUID, Integer>();
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				if(!list.containsKey(victim.getUniqueId())) {
					list.put(victim.getUniqueId(), 0);
				}
				list.put(victim.getUniqueId(), list.get(victim.getUniqueId()) + 1);
				if(list.get(victim.getUniqueId()) == 8 - level) {
					EntityLiving l = ((CraftLivingEntity)victim).getHandle();
					l.setAbsorptionHearts(3.00F + 1.50F * level);
				}
			}
		}));
		this.listArmor.put("Absorbing Comeback", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			HashMap<UUID, Double> extraDamage = new HashMap<UUID, Double>();
			@Override
			public void activateEnchantment(LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 7 + level) {
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
		}));
		this.listArmor.put("Adrenaline Rush", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				DFPlayer dfPlayer = df.getPlayer(victim);
				if(dfPlayer.getHealth() <= dfPlayer.getMaxHealth() / 100 * 25) {
					if(i <= 20 + 5 * level) {
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
								entity1.damage(2.00 + 0.50 * level);
							}
						}
					}
				}
			}
		}));
		this.listArmor.put("Archery", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			HashMap<UUID, Double> list = new HashMap<UUID, Double>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, boolean equiped, PlayerArmorChangeEvent event) {
				if(df.containsPlayer(damager)) {
					DFPlayer dfPlayer = new DFPlayer().getPlayer(damager);
					if(equiped == true && !list.containsKey(damager.getUniqueId())) {
						dfPlayer.addRndCal(6.0 + 6.0 * level, 0);
						list.put(dfPlayer.getUUID(), 6.0 + 6.0 * level);
					}
					else if(equiped == false && list.containsKey(damager.getUniqueId())) {
						dfPlayer.removeRndCal(list.get(dfPlayer.getUUID()), 0);
						list.remove(dfPlayer.getUUID());
					}
				}
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
					int durationAdd = 60 + 10 * level;
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
					damager.damage(1 + level);
				}
			}
		}));
		this.listArmor.put("Curse", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {
				ArrayList<UUID> cursed = new ArrayList<UUID>();
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 0.1 + 0.1 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 2, (float) 0.5);
					cursed.add(damager.getUniqueId());
					new BukkitRunnable() {
						public void run() {
							if(cursed.contains(damager.getUniqueId())) {
								damager.damage(Double.MAX_VALUE);
								cursed.remove(damager.getUniqueId());
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 600 - 40 * level);
				}
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
					damager.damage(damage);
				}
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
							ent.damage(3 + level * 3);
						}
					}
				}
			}
		}));
		this.listArmor.put("Empower", new Pair<>(Condition.ARMOR_CHANGE, new CommandFile() {
			HashMap<UUID, Double> list = new HashMap<UUID, Double>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, boolean equiped, PlayerArmorChangeEvent event) {
				if(df.containsPlayer(damager)) {
					DFPlayer dfPlayer = new DFPlayer().getPlayer(damager);
					if(equiped == true && !list.containsKey(damager.getUniqueId())) {
						dfPlayer.addAtkCal(4.50 + 4.50 * level, 0);
						list.put(dfPlayer.getUUID(), 4.50 + 4.50 * level);
					}
					else if(equiped == false && list.containsKey(damager.getUniqueId())) {
						dfPlayer.removeAtkCal(list.get(dfPlayer.getUUID()), 0);
						list.remove(dfPlayer.getUUID());
					}
				}
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
		}));
		this.listArmor.put("Escape", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 13 + level) {
					if(victim.getHealth() <= 12 + level) {
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
		}));
		this.listArmor.put("Fortitude", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 10 + 2.5 * level) {
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
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
				}
			}
		}));
		this.listArmor.put("Fossil Blaze", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 3 + 1.3 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.FLAME, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_ANVIL_FALL, 2, (float) 1.3);
					double range = 3.00 + 1.25 * level;
					for(Entity entity : victim.getNearbyEntities(range, range, range)) {
						if(entity != null) {
							if(entity instanceof LivingEntity) {
								LivingEntity attacked = (LivingEntity) entity;
								if(attacked != victim) {
									attacked.setFireTicks(attacked.getFireTicks() + (140 + 20 * level));
								}
							}
						}
					}
				}
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
		}));
		this.listArmor.put("Hastefull", new Pair<>(Condition.ARMOR_CHANGE, new CommandFile() {
			HashMap<UUID, Double> list = new HashMap<UUID, Double>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, boolean equiped, PlayerArmorChangeEvent event) {
				if(df.containsPlayer(damager)) {
					DFPlayer dfPlayer = new DFPlayer().getPlayer(damager);
					if(equiped == true && !list.containsKey(damager.getUniqueId())) {
						dfPlayer.addSpdCal(1.5 + 1.5 * level, 0);
						list.put(dfPlayer.getUUID(), 1.5 + 1.5 * level);
					}
					else if(equiped == false && list.containsKey(damager.getUniqueId())) {
						dfPlayer.removeSpdCal(list.get(dfPlayer.getUUID()), 0);
						list.remove(dfPlayer.getUUID());
					}
				}
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
					damager.setFireTicks(damager.getFireTicks() + (150 + 70 * level));
				}
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
						}.runTaskLater(CustomEnchantments.getInstance(), 80 - 10 * level);
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
		}));
		this.listArmor.put("Jelly Fish", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 1.5 + 0.75 * level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.2D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.WATER_BUBBLE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_GUARDIAN_DEATH, 2, (float) 1.5);
					int amp = (int)Math.floor(0 + (level) / 2);
					int durationAdd = 180 + 40 * level;
					ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(PotionEffectType.SLOW, PotionEffectType.POISON, PotionEffectType.SLOW_DIGGING));
					p.applyEffect(damager, types, amp, durationAdd);
				}
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
		}));
		this.listArmor.put("Lightweight", new Pair<>(Condition.ARMOR_CHANGE, new CommandFile() {
			HashMap<UUID, Double> list = new HashMap<UUID, Double>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, boolean equiped, PlayerArmorChangeEvent event) {
				if(df.containsPlayer(damager)) {
					DFPlayer dfPlayer = new DFPlayer().getPlayer(damager);
					if(equiped == true && !list.containsKey(damager.getUniqueId())) {
						dfPlayer.addMove(0.02 + 0.02 * level, 0);
						damager.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(dfPlayer.getMove());
						list.put(dfPlayer.getUUID(), 0.02 + 0.02 * level);
					}
					else if(equiped == false && list.containsKey(damager.getUniqueId())) {
						dfPlayer.removeMove(list.get(dfPlayer.getUUID()), 0);
						damager.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(dfPlayer.getMove());
						list.remove(dfPlayer.getUUID());
					}
				}
			}
		}));
		this.listArmor.put("Lucky", new Pair<>(Condition.ARMOR_CHANGE, new CommandFile() {
			HashMap<UUID, Double> list = new HashMap<UUID, Double>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, boolean equiped, PlayerArmorChangeEvent event) {
				if(df.containsPlayer(damager)) {
					DFPlayer dfPlayer = new DFPlayer().getPlayer(damager);
					if(equiped == true && !list.containsKey(damager.getUniqueId())) {
						dfPlayer.addCrtCal(0.75 + 0.75 * level, 0);
						list.put(dfPlayer.getUUID(), 0.75 + 0.75 * level);
					}
					else if(equiped == false && list.containsKey(damager.getUniqueId())) {
						dfPlayer.removeCrtCal(list.get(dfPlayer.getUUID()), 0);
						list.remove(dfPlayer.getUUID());
					}
				}
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
		}));
		this.listArmor.put("Overheal", new Pair<>(Condition.ARMOR_CHANGE, new CommandFile() {
			HashMap<UUID, Double> list = new HashMap<UUID, Double>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, boolean equiped, PlayerArmorChangeEvent event) {
				if(df.containsPlayer(damager)) {
					DFPlayer dfPlayer = new DFPlayer().getPlayer(damager);
					if(equiped == true && !list.containsKey(damager.getUniqueId())) {
						dfPlayer.addHpCal(12.5 + 12.5 * level, 0);
						list.put(dfPlayer.getUUID(), 12.5 + 12.5 * level);
					}
					else if(equiped == false && list.containsKey(damager.getUniqueId())) {
						dfPlayer.removeHpCal(list.get(dfPlayer.getUUID()), 0);
						list.remove(dfPlayer.getUUID());
					}
				}
			}
		}));
		this.listArmor.put("Protection", new Pair<>(Condition.ENTITY_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity victim, int level, EntityDamageEvent event) {
				event.setDamage(event.getDamage() / 100 * (100 - level * 2));
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
					int durationAdd = 30 + 8 * level;
					p.applyEffect(victim, PotionEffectType.INCREASE_DAMAGE, amp, durationAdd);
				}
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
					int durationAdd = 300 - 70 * level;
					p.applyEffect(victim, PotionEffectType.REGENERATION, amp, durationAdd);
				}
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
		}));
		this.listArmor.put("Savior", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(victim.getHealth() < victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * (0.17 + (0.01 * level))) {
					if(i < 17 + level) {
						Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
						victim.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, locCF, 50, 0.1, 0.1, 0.1, 0.1); 
						for(Player victim1 : Bukkit.getOnlinePlayers()) {
							((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GHAST_AMBIENT, 2, (float) 1.5);
						}
						int amp = (int)Math.floor(0 + (level) / 2);
						int durationAdd = 100 + 30 * level;
						p.applyEffect(victim, PotionEffectType.REGENERATION, amp, durationAdd);
					}
				}
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
		}));
		this.listArmor.put("Slowness", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 9 + level) {
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
		}));
		this.listArmor.put("Smoke Screen", new Pair<>(Condition.ENTITY_DAMAGE_BY_ENTITY, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i < 25) {
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
					p.applyEffect(victim, PotionEffectType.SLOW, amp, durationAdd);
					p.applyEffect(victim, PotionEffectType.SLOW, amp1, durationAdd);
				}
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
		}));
		this.listArmor.put("Tough Skin", new Pair<>(Condition.ARMOR_CHANGE, new CommandFile() {
			HashMap<UUID, Double> list = new HashMap<UUID, Double>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, boolean equiped, PlayerArmorChangeEvent event) {
				if(df.containsPlayer(damager)) {
					DFPlayer dfPlayer = new DFPlayer().getPlayer(damager);
					if(equiped == true && !list.containsKey(damager.getUniqueId())) {
						dfPlayer.addDfCal(1.5 + 1.5 * level, 0);
						list.put(dfPlayer.getUUID(), 1.5 + 1.5 * level);
					}
					else if(equiped == false && list.containsKey(damager.getUniqueId())) {
						dfPlayer.removeDfCal(list.get(dfPlayer.getUUID()), 0);
						list.remove(dfPlayer.getUUID());
					}
				}
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
		}));
	}
	
	public void loadBowEnchantments() {
		this.listBow = new HashMap<String, Pair<Condition, CommandFile>>();
		this.listBow.put("Black Heart", new Pair<>(Condition.PROJECTILE_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 5 + level) {
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.DRAGON_BREATH, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 2, (float) 1.4);
					}
					final double range = 5.00; 
					final double damage = 2.50 + 0.75 * level;
					final int count = 3 + level;
					DFFaction faction = fac.getFaction(damager.getUniqueId());
					new BukkitRunnable() {
						int c = 0;
						public void run() {
							if(c <= count) {
								for(Entity e : victim.getNearbyEntities(range, range, range)) {
									if(e instanceof LivingEntity) {
										LivingEntity ent = (LivingEntity) e;
										DFFaction other = fac.getFaction(ent.getUniqueId());
										if(faction != null && other != null) {
											if(!faction.isAlly(other.getName()) || !faction.isMember(ent.getUniqueId())) {
												ent.damage(damage);
											}
										}
										else {
											ent.damage(damage);
										}
									}
								}
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 60 - 5 * level);
				}
			}
		}));
		this.listBow.put("Blast", new Pair<>(Condition.PROJECTILE_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 4 + 1.5 * level) {
					double range = 5 + 0.75 * level;
					Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, locCF, 100, 5.5, 5.5, 5.5, 0.1); 
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, (float) 1);
					}
					for (Entity entity : victim.getNearbyEntities(range, range, range)){
						if(entity instanceof LivingEntity) {
							if(entity != damager) {
								if(entity == victim) {
									victim.damage(10);
								}
								else {
									((LivingEntity) entity).damage(8);
								}
							}
						}
					}
				}
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
		}));
		this.listBow.put("Grappling Hook", new Pair<>(Condition.LEFT_CLICK, new CommandFile() {
			HashMap<UUID, Boolean> prepared = new HashMap<UUID, Boolean>();
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			HashMap<UUID, Integer> levelBow = new HashMap<UUID, Integer>();
			HashMap<UUID, Vector> vector = new HashMap<UUID, Vector>();
			@Override
			public void activateEnchantment(LivingEntity damager, int level, PlayerInteractEvent event) {
				if(!cooldown.contains(damager.getUniqueId())) {
					prepared.put(damager.getUniqueId(), true);
					cooldown.add(damager.getUniqueId());
					levelBow.put(damager.getUniqueId(), level);
				}
			}
			@EventHandler
			public void bowShoot(DFShootBowEvent event) {
				Player player = event.getShooter();
				if(prepared.containsKey(player.getUniqueId())) {
					if(prepared.get(player.getUniqueId()) == true) {
						prepared.put(player.getUniqueId(), false);
						vector.put(player.getUniqueId(), player.getLocation().getDirection());
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
								player.setVelocity(new Vector(0, 0.5, 0));
								new BukkitRunnable() {
									public void run() {
										player.setVelocity(vector.get(player.getUniqueId()).normalize().multiply(player.getLocation().distance(arrow.getLocation()) / 15));
										vector.remove(player.getUniqueId());
									}
								}.runTaskLater(CustomEnchantments.getInstance(), 3L);
								new BukkitRunnable() {
									public void run() {
										cooldown.remove(player.getUniqueId());
									}
								}.runTaskLater(CustomEnchantments.getInstance(), 1200 - 100 * levelBow.get(player.getUniqueId()));
							}
						}
					}
				}
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
						DFPlayer dfPlayer = new DFPlayer().getPlayer(damager);
						DFPlayer dfVictim = new DFPlayer().getPlayer(victim);
						double tempMoney = (double) (dfVictim.getMoney() * (0.01 + 0.01 * level));
						dfPlayer.addMoney(tempMoney);
						dfVictim.removeMoney(tempMoney);
					}
				}
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
		}));
		this.listBow.put("Sandstorm", new Pair<>(Condition.PROJECTILE_DAMAGE, new CommandFile() {
			@Override
			public void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				if(i <= 7 + level) {
					Location locCF1 = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 2.5D, victim.getLocation().getZ() + 0D);
					victim.getWorld().spawnParticle(Particle.FALLING_DUST, locCF1, 80, 1, 1, 1, 0, Material.SAND.createBlockData()); 
					victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_SKELETON_HURT, 2, (float) 1.1);
					double range = 5.00 + 1.5 * level;
					for(Entity e : damager.getNearbyEntities(range, range, range)) {
						if(e != null) {
							if(e instanceof LivingEntity) {
								LivingEntity entity = (LivingEntity) e;
								if(entity != damager) {
									int amp = 0;
									int durationAdd = 200 + 40 * level;
									p.applyEffect(entity, PotionEffectType.BLINDNESS, amp, durationAdd);
								}
							}
						}
					}
				}
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
					event.setDamage(event.getFinalDamage() + 5.50 + 1.50 * level - damager.getLocation().distance(victim.getLocation()));
				}
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
					event.setDamage(event.getFinalDamage() + damager.getLocation().distance(victim.getLocation()) - 20.00 - 1.60 * level);
				}
			}
		}));
		this.listBow.put("Newtowns Power", new Pair<>(Condition.PROJECTILE_SHOOT, new CommandFile() {
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
				}.runTaskLater(CustomEnchantments.getInstance(), 100L);
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
