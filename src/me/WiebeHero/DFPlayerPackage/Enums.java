package me.WiebeHero.DFPlayerPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomEvents.DFShootBowEvent;
import me.WiebeHero.DFPlayerPackage.EnumSkills.Stats;
import me.WiebeHero.Factions.DFFactionManager;
import net.minecraft.server.v1_13_R2.EntityPlayer;

public class Enums implements Listener{	
	
	private static DFPlayerManager dfManager;
	private static DFFactionManager facManager;
	
	public Enums(DFPlayerManager dfManager, DFFactionManager facManager) {
		Enums.dfManager = dfManager;
		Enums.facManager = facManager;
	}
	
	public enum Classes implements Listener{
		WRATH("&6Hatred of the Wrath"){
			ArrayList<UUID> disableBow = new ArrayList<UUID>();
			@Override
			public void ability(Player player){
				DFPlayer dfPlayer = dfManager.getEntity(player);
				HashMap<Stats, Double> statList = dfPlayer.getStatList();
				double damage1 = statList.get(Stats.DAMAGE);
				double range = statList.get(Stats.RANGE);
				long cooldown = (long)(statList.get(Stats.COOLDOWN) * 20);
				ArrayList<Entity> ents = new ArrayList<Entity>(player.getNearbyEntities(range, range, range));
				ents.remove(player);
				if(!ents.isEmpty()) {
					for(Entity ent : ents) {
						if(ent instanceof LivingEntity) {
							if(facManager.isFriendly(player, ent)) {
								ents.remove(ent);
							}
						}
						else {
							ents.remove(ent);
						}
					}
				}
				if(!ents.isEmpty()) {
					dfPlayer.setUseable(false);
					for(Entity e : ents) {
						if(e != null) {
							if(e instanceof LivingEntity) {
								LivingEntity victim = (LivingEntity) e;
								victim.getWorld().strikeLightningEffect(victim.getLocation());
								victim.damage(damage1);
							}
						}
					}
					if(dfPlayer.getAtkMod() > 0) {
						int cLevel = dfPlayer.getAtkMod();
						double inc = cLevel * 1.5;
						inc = inc * ents.size();
						long dur = (long) (cLevel * 2.5 * 20);
						dfPlayer.addAtkCal(inc, dur);
					}
					if(dfPlayer.getCrtMod() > 0) {
						int cLevel = dfPlayer.getCrtMod();
						double inc = cLevel * 0.75;
						inc = inc * ents.size();
						long dur = cLevel * 3;
						dfPlayer.addCrtCal(inc, dur);
					}
					if(dfPlayer.getRndMod() > 0) {
						int cLevel = dfPlayer.getRndMod();
						long dur = cLevel * 60;
						for(Entity e : ents) {
							if(e instanceof Player) {
								this.disableBow.add(e.getUniqueId());
								new BukkitRunnable() {
									public void run() {
										disableBow.remove(e.getUniqueId());
									}
								}.runTaskLater(CustomEnchantments.getInstance(), dur);
							}
						}
					}
					new BukkitRunnable() {
						public void run() {
							dfPlayer.setUseable(true);
						}
					}.runTaskLater(CustomEnchantments.getInstance(), (long)(cooldown * 20));
				}
			}
			@EventHandler
			public void dfShoot(DFShootBowEvent event){
				Player player = event.getShooter();
				if(disableBow.contains(player.getUniqueId())) {
					event.getProjectile().remove();
					event.setCancelled(true);
				}
			}
			@EventHandler
			public void shoot(EntityShootBowEvent event){
				LivingEntity ent = event.getEntity();
				if(disableBow.contains(ent.getUniqueId())) {
					event.setCancelled(true);
				}
			}
			@EventHandler
			public void chainStrikes(EntityDamageByEntityEvent event) {
				if(!event.isCancelled()) {
					if(event.getDamager() instanceof Player) {
						if(event.getEntity() instanceof LivingEntity) {
							Player player = (Player) event.getDamager();
							if(dfManager.contains(player)) {
								DFPlayer dfPlayer = dfManager.getEntity(player);
								if(dfPlayer.getPlayerClass() == Classes.WRATH) {
									if(dfPlayer.getSpdMod() > 0) {
										int cLevel = dfPlayer.getSpdMod();
										double inc = cLevel * 0.75;
										long dur = cLevel * 40;
										dfPlayer.addAtkCal(inc, dur);
										dfPlayer.addCrtCal(inc, dur);
									}
								}
							}
						}
					}
				}
			}
			{
				baseList.put(Stats.DAMAGE, 5.00);
				baseList.put(Stats.RANGE, 3.50);
				baseList.put(Stats.COOLDOWN, 70.00);
				incList.put(Stats.DAMAGE, 0.05);
				incList.put(Stats.RANGE, 0.04);
				incList.put(Stats.COOLDOWN, 0.15);
			}
		},
		LUST("&6Drain Blood"){
			@Override
			public void ability(Player player){
				DFPlayer dfPlayer = dfManager.getEntity(player);
				HashMap<Stats, Double> statList = dfPlayer.getStatList();
				double heal = statList.get(Stats.HEAL);
				double damage = statList.get(Stats.DAMAGE);
				double range = statList.get(Stats.RANGE);
				long cooldown = (long)(statList.get(Stats.COOLDOWN) * 20);
				double healMe = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * (heal / 100);
				double healAllies = 0.00;
				ArrayList<Entity> ents = new ArrayList<Entity>(player.getNearbyEntities(range, range, range));
				ents.remove(player);
				if(!ents.isEmpty()) {
					for(int i = 0; i < ents.size(); i++) {
						Entity e = ents.get(i);
						if(e != null) {
							if(!(e instanceof LivingEntity)) {
								ents.remove(e);
							}
						}
					}
				}
				if(!ents.isEmpty()) {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have used &6Drain Blood!"));
					for(int i = 0; i < ents.size(); i++) {
						Entity e = ents.get(i);
						if(e != null) {
							if(e instanceof LivingEntity) {
								LivingEntity victim = (LivingEntity) e;
								if(!facManager.isFriendly(player, victim)) {
									double dealtDamage = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 100 * damage;
									victim.damage(dealtDamage);
									Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.8D, victim.getLocation().getZ() + 0D);
									victim.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, locCF, 60, 0.15, 0.15, 0.15, 0.1); 
									victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 1.5);
									healAllies += dealtDamage;
								}
							}
						}
					}
					ArrayList<LivingEntity> others = new ArrayList<LivingEntity>();
					for(Entity e : player.getNearbyEntities(range, range, range)) {
						if(e != null) {
							if(e instanceof LivingEntity) {
								LivingEntity p = (LivingEntity) e;
								if(facManager.isFriendly(player, p)) {
									others.add(p);
								}
							}
						}
					}
					healAllies = healAllies / others.size();
					if(!others.isEmpty()) {
						for(LivingEntity p : others) {
							if(healAllies + p.getHealth() <= p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
								dfPlayer.getPlayer().setHealth(p.getHealth() + healAllies);
							}
							else {
								dfPlayer.getPlayer().setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
							}
						}
					}
					if(healMe + healAllies + dfPlayer.getHealth() <= dfPlayer.getMaxHealth()) {
						dfPlayer.getPlayer().setHealth(dfPlayer.getHealth() + healMe + healAllies);
					}
					else {
						dfPlayer.getPlayer().setHealth(dfPlayer.getMaxHealth());
						if(dfPlayer.getHpMod() > 0) {
							int cLevel = dfPlayer.getHpMod();
							Bukkit.broadcastMessage(healAllies + "");
							double remaining = dfPlayer.getMaxHealth() - (healAllies + healMe + dfPlayer.getHealth());
							Bukkit.broadcastMessage(remaining + "");
							float health = (float)remaining / 100.00F * (cLevel * 10.00F);
							Bukkit.broadcastMessage(cLevel + "");
							if(health >= 254.00F) {
								health = 254.00F;
							}
							long dur = cLevel * 100;
							EntityPlayer p = ((CraftPlayer)dfPlayer.getPlayer()).getHandle();
							p.setAbsorptionHearts(health);
							new BukkitRunnable() {
								public void run() {
									p.setAbsorptionHearts(0.0F);
								}
							}.runTaskLater(CustomEnchantments.getInstance(), dur);
						}
					}
					dfPlayer.setUseable(false);
					new BukkitRunnable() {
						public void run() {
							dfPlayer.setUseable(true);
						}
					}.runTaskLater(CustomEnchantments.getInstance(), cooldown);
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThere are no enemies nearby!"));
				}
			}
			@EventHandler
			public void criticalDown(EntityDamageByEntityEvent event) {
				if(!event.isCancelled()) {
					if(event.getEntity() instanceof LivingEntity) {
						if(event.getDamager() instanceof Arrow || event.getDamager() instanceof Player) {
							Player player = null;
							LivingEntity victim = (LivingEntity) event.getEntity();
							if(event.getDamager() instanceof Player) {
								player = (Player)event.getDamager();
							}
							else if(event.getDamager() instanceof Arrow) {
								Arrow arrow = (Arrow) event.getDamager();
								if(arrow.getShooter() instanceof Player) {
									player = (Player) arrow.getShooter();
								}
							}
							if(player != null) {
								if(dfManager.contains(player) && dfManager.contains(victim)) {
									DFPlayer dfPlayer = dfManager.getEntity(player), dfVictim = dfManager.getEntity(victim);
									if(dfPlayer.getPlayerClass() == Classes.LUST) {
										if(dfPlayer.getCrtMod() > 0) {
											int cLevel = dfPlayer.getCrtMod();
											double inc = cLevel * 0.5;
											double dur = cLevel * 2.5;
											dfVictim.removeCrtCal(inc, (long)dur * 20);
										}
										if(dfPlayer.getRndMod() > 0) {
											int cLevel = dfPlayer.getRndMod();
											double inc = cLevel * 10;
											double test = 1 - dfPlayer.getHp() / dfPlayer.getMaxHealth();
											dfPlayer.addRndCal(inc * test, 1);
										}
										if(dfPlayer.getDfMod() > 0) {
											int cLevel = dfPlayer.getDfMod();
											double inc = cLevel * 10;
											double test = 1 - dfPlayer.getHp() / dfPlayer.getMaxHealth();
											dfPlayer.addDfCal(test * inc, 1);
										}
									}
								}
							}
						}
					}
				}
			}
			{
				baseList.put(Stats.HEAL, 10.00);
				baseList.put(Stats.DAMAGE, 5.00);
				baseList.put(Stats.RANGE, 7.00);
				baseList.put(Stats.COOLDOWN, 90.00);
				incList.put(Stats.HEAL, 0.15);
				incList.put(Stats.DAMAGE, 0.05);
				incList.put(Stats.RANGE, 0.07);
				incList.put(Stats.COOLDOWN, 0.25);
			}
		},
		ENVY("&6Special Attack"){
			@Override
			public void ability(Player player){
				DFPlayer dfPlayer = dfManager.getEntity(player);
				HashMap<Stats, Double> statList = dfPlayer.getStatList();
				dfPlayer.setActivation(true);
				dfPlayer.setUseable(false);
				long duration = (long)(statList.get(Stats.DURATION) * 20);
				long cooldown = (long)(statList.get(Stats.COOLDOWN) * 20);
				double damage = statList.get(Stats.ATK_INC);
				dfPlayer.addAtkCal(damage, 0);
				Location loc = player.getLocation();
				loc.setY(loc.getY() + 2.5);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 2.0F, 1.0F);
				player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 80, 0.15, 0.15, 0.15, 0); 
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have used &6Special Attack!"));
				new BukkitRunnable() {
					public void run() {
						if(dfPlayer.getActivation() == true) {
							dfPlayer.setActivation(false);
							dfPlayer.removeAtkCal(damage, 0);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have failed to use &6Special Attack!"));
							new BukkitRunnable() {
								public void run() {
									dfPlayer.setUseable(true);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou can use &6Special Attack &aagain!"));
								}
							}.runTaskLater(CustomEnchantments.getInstance(), cooldown);
						}
					}
				}.runTaskLater(CustomEnchantments.getInstance(), duration);
			}
			HashMap<UUID, Double> healthReduction = new HashMap<UUID, Double>();
			@EventHandler
			public void extraDamage(EntityDamageByEntityEvent event) {
				if(!event.isCancelled()) {
					if(event.getDamager() instanceof Player || event.getDamager() instanceof Arrow) {
						if(event.getEntity() instanceof LivingEntity) {
							Player player = null;
							if(event.getDamager() instanceof Player) {
								player = (Player)event.getDamager();
							}
							else if(event.getDamager() instanceof Arrow) {
								Arrow arrow = (Arrow) event.getDamager();
								if(arrow.getShooter() instanceof Player) {
									player = (Player) arrow.getShooter();
								}
							}
							if(player != null) {
								if(dfManager.contains(player)) {
									DFPlayer dfPlayer = dfManager.getEntity(player);
									if(dfPlayer.getPlayerClass() == Classes.ENVY) {
										if(dfPlayer.getActivation() == true) {
											dfPlayer.setActivation(false);
											int level = dfPlayer.getLevel();
											double cooldown = 75 - level * 0.25;
											double damage = 50 + level * 0.50;
											final Player pp = player;
											new BukkitRunnable() {
												public void run() {
													dfPlayer.removeAtkCal(damage, 0);
													new BukkitRunnable() {
														public void run() {
															dfPlayer.setUseable(true);
															pp.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou can use &6Special Attack &aagain!"));
														}
													}.runTaskLater(CustomEnchantments.getInstance(), (long)(cooldown * 20));
												}
											}.runTaskLater(CustomEnchantments.getInstance(), 1L);
											LivingEntity p = (LivingEntity) event.getEntity();
											DFPlayer dfPlay = dfManager.getEntity(p);
											if(dfPlayer.getAtkMod() > 0) {
												int cLevel = dfPlayer.getAtkMod();
												double weak = cLevel * 5;
												double dur = cLevel * 3;
												dfPlay.removeAtkCal(weak, (long)dur * 20);
											}
											if(dfPlayer.getRndMod() > 0) {
												int cLevel = dfPlayer.getRndMod();
												double weak = cLevel * 2.5;
												double dur = cLevel * 3;
												dfPlay.removeCrtCal(weak, (long)dur * 20);
												dfPlay.removeRndCal(weak, (long)dur * 20);
											}
											if(dfPlayer.getDfMod() > 0) {
												int cLevel = dfPlayer.getDfMod();
												double break1 = cLevel * 10;
												double dur = cLevel * 3;
												dfPlay.removeDfCal(break1, (long)dur * 20);
											}
											if(dfPlayer.getHpMod() > 0) {
												int cLevel = dfPlayer.getHpMod();
												double dis = cLevel * 7;
												double dur = cLevel * 3;
												healthReduction.put(p.getUniqueId(), dur);
												new BukkitRunnable() {
													public void run() {
														healthReduction.remove(p.getUniqueId(), dis);
													}
												}.runTaskLater(CustomEnchantments.getInstance(), (long)(dur * 20));
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
			public void recover(EntityRegainHealthEvent event) {
				if(event.getEntity() instanceof LivingEntity) {
					LivingEntity ent = (LivingEntity) event.getEntity();
					if(healthReduction.containsKey(ent.getUniqueId())) {
						event.setAmount(event.getAmount() / 100 * (100 - healthReduction.get(ent.getUniqueId())));
					}
				}
			}
			{
				baseList.put(Stats.ATK_INC, 50.00);
				baseList.put(Stats.COOLDOWN, 75.00);
				baseList.put(Stats.DURATION, 5.00);
				incList.put(Stats.ATK_INC, 0.5);
				incList.put(Stats.COOLDOWN, 0.25);
				incList.put(Stats.DURATION, 0.05);
			}
		},
		SLOTH("&6Hedgehog"){
			ArrayList<UUID> arrowList = new ArrayList<UUID>();
			@Override
			public void ability(Player player){
				DFPlayer dfPlayer = dfManager.getEntity(player);
				HashMap<Stats, Double> statList = dfPlayer.getStatList();
				dfPlayer.setUseable(false);
				dfPlayer.setActivation(true);
				Location loc = player.getEyeLocation();
				Vector vec = loc.getDirection();
				vec.setY(0.00);
				loc.add(vec.getX() - (vec.getX()* 2.0),0,vec.getZ() - (vec.getZ() * 2.0));
				double arrowA = statList.get(Stats.ARROW_COUNT);
				long duration = (long)(statList.get(Stats.DURATION) * 20L);
				long cooldown = (long)(statList.get(Stats.COOLDOWN) * 20L);
				double damage = statList.get(Stats.COOLDOWN);
				double defense = statList.get(Stats.DF_INC);
				dfPlayer.addDfCal(defense, duration);
				if(dfPlayer.getAtkMod() > 0) {
					int cLevel = dfPlayer.getLevel();
					double inc = cLevel * 0.4;
					dfPlayer.addAtkCal(inc, duration);
				}
				for(int i = 0; i <= (int)arrowA; i++) {
					Arrow arrow = player.getWorld().spawnArrow(loc, rotateVectorAroundY(vec, 180), (float)(1.50 * 0.20), 26);
					arrow.setShooter(player);
					arrow.setCritical(true);
					arrow.setMetadata("AttackStrength", new FixedMetadataValue(CustomEnchantments.getInstance(), 1.0F));
					arrow.setDamage(damage);
				}
				new BukkitRunnable() {
					public void run() {
						dfPlayer.setActivation(false);
						new BukkitRunnable() {
							public void run() {
								dfPlayer.setUseable(true);
							}
						}.runTaskLater(CustomEnchantments.getInstance(), cooldown);
					}
				}.runTaskLater(CustomEnchantments.getInstance(), duration);
			}
			@EventHandler
			public void arrowHit(EntityDamageByEntityEvent event) {
				if(!event.isCancelled()) {
					if(event.getDamager() instanceof Arrow) {
						Arrow arrow = (Arrow) event.getDamager();
						if(arrow.getShooter() instanceof Player) {
							Player player = (Player) arrow.getShooter();
							if(dfManager.contains(player)) {
								if(arrowList.contains(arrow.getUniqueId())) {
									DFPlayer dfPlayer = dfManager.getEntity(player);
									if(dfPlayer.getHp() > 0) {
										int cLevel = dfPlayer.getHp();
										double inc = cLevel * 0.5;
										if(dfPlayer.getHealth() + inc <= dfPlayer.getMaxHealth()) {
											dfPlayer.getPlayer().setHealth(dfPlayer.getHealth() + inc);
										}
										else {
											dfPlayer.getPlayer().setHealth(dfPlayer.getMaxHealth());
										}
									}
								}
							}
						}
					}
				}
			}
			@EventHandler
			public void turnTable(EntityDamageByEntityEvent event) {
				if(!event.isCancelled()) {
					if(event.getDamager() instanceof Player) {
						Player player = (Player) event.getDamager();
						if(dfManager.contains(player)) {
							DFPlayer dfPlayer = dfManager.getEntity(player);
							if(dfPlayer.getPlayerClass() == Classes.SLOTH) {
								if(dfPlayer.getCrtMod() > 0) {
									if(dfPlayer.getHealth() >= dfPlayer.getMaxHealth() * 0.50) {
										dfPlayer.addCrtCal(dfPlayer.getCrtCal() * 5, 1);
									}
								}
							}
						}
					}
				}
			}
			@EventHandler
			public void fullIt(EntityDamageByEntityEvent event) {
				if(!event.isCancelled()) {
					if(event.getEntity() instanceof Player) {
						Player player = (Player) event.getEntity();
						if(dfManager.contains(player)) {
							DFPlayer dfPlayer = dfManager.getEntity(player);
							if(dfPlayer.getPlayerClass() == Classes.SLOTH) {
								if(dfPlayer.getCrtMod() > 0) {
									if(dfPlayer.getHealth() <= dfPlayer.getMaxHealth() * 0.50) {
										dfPlayer.addDfCal(25, 1);
									}
								}
							}
						}
					}
				}
			}
			public Vector rotateVectorAroundY(Vector vector, double degrees) {
			    double rad = Math.toRadians(degrees);
			   
			    double currentX = vector.getX();
			    double currentZ = vector.getZ();
			   
			    double cosine = Math.cos(rad);
			    double sine = Math.sin(rad);
			   
			    return new Vector((cosine * currentX - sine * currentZ), vector.getY(), (sine * currentX + cosine * currentZ));
			}
			{
				baseList.put(Stats.ARROW_COUNT, 10.00);
				baseList.put(Stats.DAMAGE, 3.00);
				baseList.put(Stats.DF_INC, 10.00);
				baseList.put(Stats.DURATION, 7.50);
				baseList.put(Stats.COOLDOWN, 75.00);
				incList.put(Stats.ARROW_COUNT, 0.25);
				incList.put(Stats.DAMAGE, 0.05);
				incList.put(Stats.DF_INC, 0.15);
				incList.put(Stats.DURATION, 0.05);
				incList.put(Stats.COOLDOWN, 0.2);
			}
		},
		PRIDE("&6Quick Attack"){
			@Override
			public void ability(Player player){
				DFPlayer dfPlayer = dfManager.getEntity(player);
				HashMap<Stats, Double> statList = dfPlayer.getStatList();
				dfPlayer.setUseable(false);
				dfPlayer.setActivation(true);
				long duration = (long)(statList.get(Stats.DURATION) * 20);
				long cooldown = (long)(statList.get(Stats.DURATION) * 20);
				double attackS = statList.get(Stats.DURATION);
				double defense = statList.get(Stats.DURATION);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SPIDER_DEATH, 2.0F, 2.0F);
				Location loc = player.getLocation();
				loc.setY(loc.getY() - 1.5);
				BlockData bd = Material.COBWEB.createBlockData();
				player.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 80, 0.15, 0.15, 0.15, 0, bd); 
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have used &6Quick Attack!"));
				if(dfPlayer.getSpdMod() > 0) {
					int cLevel = dfPlayer.getSpdMod();
					double inc = cLevel * 10;
					dfPlayer.addSpdCal(attackS + inc, duration);
				}
				else {
					dfPlayer.addSpdCal(attackS, duration);
				}
				if(dfPlayer.getDfMod() > 0) {
					int cLevel = dfPlayer.getDfMod();
					double inc = cLevel * 10;
					dfPlayer.addDfCal(defense + inc, duration);
				}
				else {
					dfPlayer.addDfCal(defense, duration);
				}
				new BukkitRunnable() {
					public void run() {
						dfPlayer.setActivation(false);
						new BukkitRunnable() {
							public void run() {
								dfPlayer.setUseable(true);
							}
						}.runTaskLater(CustomEnchantments.getInstance(), cooldown);
					}
				}.runTaskLater(CustomEnchantments.getInstance(), duration);
			}
			@EventHandler
			public void terminator(EntityDamageByEntityEvent event) {
				if(!event.isCancelled()) {
					if(event.getEntity() instanceof LivingEntity) {
						if(event.getDamager() instanceof Arrow || event.getDamager() instanceof Player) {
							Player player = null;
							if(event.getDamager() instanceof Player) {
								player = (Player)event.getDamager();
							}
							else if(event.getDamager() instanceof Arrow) {
								Arrow arrow = (Arrow) event.getDamager();
								if(arrow.getShooter() instanceof Player) {
									player = (Player) arrow.getShooter();
								}
							}
							if(player != null) {
								if(dfManager.contains(player)) {
									DFPlayer dfPlayer = dfManager.getEntity(player);
									if(dfPlayer != null) {
										if(dfPlayer.getPlayerClass() == Classes.PRIDE) {
											int level = dfPlayer.getLevel();
											long duration = 200 + level * 1;
											if(dfPlayer.getAtkMod() > 0) {
												int cLevel = dfPlayer.getAtkMod();
												double inc = cLevel * 1.5;
												dfPlayer.addAtkCal(inc, duration);
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
			public void deflection(EntityDamageByEntityEvent event) {
				if(!event.isCancelled()) {
					if(event.getEntity() instanceof LivingEntity) {
						if(event.getDamager() instanceof Arrow) {
							LivingEntity victim = (LivingEntity) event.getEntity();
							Arrow arrow = (Arrow) event.getDamager();
							if(arrow.getShooter() instanceof Player) {
								Player player = (Player) arrow.getShooter();
								if(dfManager.contains(victim) && dfManager.contains(player)) {
									DFPlayer dfPlayer = dfManager.getEntity(player), dfVictim = dfManager.getEntity(victim);
									if(dfVictim.getPlayerClass() != null) {
										if(dfVictim.getPlayerClass() == Classes.PRIDE) {
											if(dfVictim.getRndMod() > 0) {
												int cLevel = dfVictim.getRndMod();
												double inc = cLevel * 10;
												dfPlayer.removeRndCal(inc, 1);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			{
				baseList.put(Stats.SPD_INC, 10.00);
				baseList.put(Stats.DF_INC, 15.00);
				baseList.put(Stats.DURATION, 7.50);
				baseList.put(Stats.COOLDOWN, 90.00);
				incList.put(Stats.SPD_INC, 0.10);
				incList.put(Stats.DF_INC, 0.20);
				incList.put(Stats.DURATION, 0.05);
				incList.put(Stats.COOLDOWN, 0.05);
			}
		},
		GLUTTONY("&6Stand"){
			private HashMap<UUID, Double> recover = new HashMap<UUID, Double>();
			@Override
			public void ability(Player player){
				DFPlayer dfPlayer = dfManager.getEntity(player);
				HashMap<Stats, Double> statList = dfPlayer.getStatList();
				dfPlayer.setUseable(false);
				dfPlayer.setActivation(true);
				double heal = statList.get(Stats.HEAL);
				double defense = statList.get(Stats.DF_INC);
				long duration = (long)(statList.get(Stats.DURATION) * 20);
				long cooldown = (long)(statList.get(Stats.COOLDOWN) * 20);
				dfPlayer.addDfCal(defense, duration);
				if(dfPlayer.getSpdMod() > 0) {
					int cLevel = dfPlayer.getSpdMod();
					double hp = cLevel * 4;
					double de = cLevel * 1;
					recover.put(dfPlayer.getUUID(), hp);
					cooldown = cooldown - (long)(de * 20);
				}
				Location locCF = new Location(player.getWorld(), player.getLocation().getX() + 0D, player.getLocation().getY() + 1.5D, player.getLocation().getZ() + 0D);
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have used &6Stand!"));
				player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, locCF, 60, 0.15, 0.15, 0.15, 0.1); 
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 2, (float) 0.5);
				double totalHeal = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * (heal / 100);
				double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
				dfPlayer.addDfCal(defense, duration);
				if(player.getHealth() + totalHeal <= maxHealth) {
					player.setHealth(player.getHealth() + totalHeal);
				}
				else {
					player.setHealth(maxHealth);
				}
				final long finalCool = cooldown;
				new BukkitRunnable() {
					public void run() {
						dfPlayer.setActivation(false);
						new BukkitRunnable() {
							public void run() {
								dfPlayer.setUseable(true);
								recover.remove(dfPlayer.getUUID());
							}
						}.runTaskLater(CustomEnchantments.getInstance(), finalCool);
					}
				}.runTaskLater(CustomEnchantments.getInstance(), duration);
			}
			@EventHandler
			public void hitBySmth(EntityDamageByEntityEvent event) {
				if(event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
					Player attacker = (Player) event.getDamager();
					LivingEntity player = (LivingEntity) event.getEntity();
					if(dfManager.contains(attacker) && dfManager.contains(player)) {
						DFPlayer dfPlayer = dfManager.getEntity(player), aDFPlayer = dfManager.getEntity(attacker);
						if(dfPlayer.getPlayerClass() == Classes.GLUTTONY) {
							if(dfPlayer.getActivation() == true) {
								if(dfPlayer.getCrtMod() > 0) {
									int cLevel = dfPlayer.getCrtMod();
									double dec = cLevel * 1.5;
									double dur = cLevel * 3;
									aDFPlayer.removeCrtCal(dec, (long)dur * 20);
								}
							}
						}
					}
				}
			}
			public HashMap<UUID, Boolean> ironWall = new HashMap<UUID, Boolean>();
			@EventHandler
			public void healUp(EntityRegainHealthEvent event) {
				if(event.getEntity() instanceof Player) {
					Player player = (Player) event.getEntity();
					if(ironWall.containsKey(player.getUniqueId())) {
						double maxHP = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
						double health = player.getHealth();
						if(health > maxHP * 0.40) {
							ironWall.put(player.getUniqueId(), false);
						}
					}
					if(recover.containsKey(player.getUniqueId())) {
						event.setAmount(event.getAmount() / 100 * (100 + recover.get(player.getUniqueId())));
					}
				}
			}
			@EventHandler
			public void ironWall(EntityDamageByEntityEvent event) {
				if(event.getEntity() instanceof Player) {
					Player player = (Player) event.getEntity();
					if(dfManager.contains(player)) {
						DFPlayer dfPlayer = dfManager.getEntity(player);
						if(!ironWall.containsKey(dfPlayer.getUUID())) {
							ironWall.put(dfPlayer.getUUID(), false);
						}
						if(dfPlayer.getPlayerClass() == Classes.GLUTTONY) {
							if(dfPlayer.getDfMod() > 0) {
								int cLevel = dfPlayer.getDfMod();
								double inc = cLevel * 20;
								double dur = cLevel * 3;
								if(dfPlayer.getHealth() < dfPlayer.getMaxHealth() * 0.40) {
									ironWall.put(dfPlayer.getUUID(), true);
									dfPlayer.addDfCal(inc, (long)dur * 20);
								}
							}
						}
					}
				}
			}
			{
				baseList.put(Stats.HEAL, 10.00);
				baseList.put(Stats.DF_INC, 10.00);
				baseList.put(Stats.DURATION, 10.00);
				baseList.put(Stats.COOLDOWN, 115.00);
				incList.put(Stats.HEAL, 0.25);
				incList.put(Stats.DF_INC, 0.15);
				incList.put(Stats.DURATION, 0.1);
				incList.put(Stats.COOLDOWN, 0.1);
			}
		},
		GREED("&6Hunting Arrow"){
			@Override
			public void ability(Player player){
				DFPlayer dfPlayer = dfManager.getEntity(player);
				HashMap<Stats, Double> statList = dfPlayer.getStatList(); 
				dfPlayer.setUseable(false);
				dfPlayer.setActivation(true);
				double attackS = statList.get(Stats.SPD_INC);
				long cooldown = (long)(statList.get(Stats.COOLDOWN) * 20);
				long duration = (long)(statList.get(Stats.DURATION) * 20);
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have prepared a &6Hunting Arrow!"));
				Location loc = player.getLocation();
				loc.setY(loc.getY() + 2.5);
				BlockData bd = Material.COAL_BLOCK.createBlockData();
				player.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 80, 0.15, 0.15, 0.15, 0, bd); 
				dfPlayer.addSpdCal(attackS, duration);
				new BukkitRunnable() {
					public void run() {
						dfPlayer.setActivation(false);
						new BukkitRunnable() {
							public void run() {
								dfPlayer.setUseable(true);
							}
						}.runTaskLater(CustomEnchantments.getInstance(), cooldown);
					}
				}.runTaskLater(CustomEnchantments.getInstance(), duration);
			}
			@EventHandler
			public void shootGreedArrow(EntityDamageByEntityEvent event) {
				if(!event.isCancelled()) {
					if(event.getEntity() instanceof LivingEntity) {
						if(event.getDamager() instanceof Arrow) {
							LivingEntity p = (LivingEntity) event.getEntity();
							Arrow arrow = (Arrow) event.getDamager();
							if(arrow.getShooter() instanceof Player) {
								Player shooter = (Player) arrow.getShooter();
								if(dfManager.contains(shooter)) {
									DFPlayer dfShooter = dfManager.getEntity(shooter);
									if(dfShooter.getPlayerClass() == Classes.GREED) {
										if(dfShooter.getActivation() == true) {
											double decrease = 50 + dfShooter.getLevel() * 0.50;
											long duration = 130 + dfShooter.getLevel();
											if(dfManager.contains(p)) {
												DFPlayer victim = dfManager.getEntity(p);
												victim.removeDfCal(decrease, duration);
												if(dfShooter.getAtkMod() > 0) {
													int cLevel = dfShooter.getAtkMod();
													double dec = cLevel * 15;
													victim.removeAtkCal(dec, duration);
												}
												if(dfShooter.getSpdMod() > 0) {
													int cLevel = dfShooter.getSpdMod();
													float inc = cLevel * 0.08F;
													dfShooter.addMove(inc, duration);
												}
												if(dfShooter.getCrtMod() > 0) {
													int cLevel = dfShooter.getCrtMod();
													float inc = cLevel * 0.08F;
													victim.removeMove(inc, duration);
													victim.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (int) duration, 1));
												}
												dfShooter.setActivation(false);
											}
										} 
										if(dfShooter.getRndMod() > 0) {
											long duration = 130 + dfShooter.getLevel();
											int cLevel = dfShooter.getLevel();
											double inc = cLevel * 1;
											float speed = cLevel * 0.001F;
											dfShooter.addRndCal(inc, duration);
											dfShooter.addMove(speed, duration);
										}
									}
								}
							}
						}
					}
				}
			}
			{
				baseList.put(Stats.DF_DEC, 10.00);
				baseList.put(Stats.SPD_INC, 10.00);
				baseList.put(Stats.COOLDOWN, 85.00);
				baseList.put(Stats.DURATION, 10.00);
				incList.put(Stats.DF_DEC, 0.1);
				incList.put(Stats.SPD_INC, 0.1);
				incList.put(Stats.COOLDOWN, 0.25);
				incList.put(Stats.DURATION, 0.1);
			}
		},
		NONE("&6Nothing"){
			@Override
			public void ability(Player player){
				
			}
		};
		
		protected HashMap<Stats, Double> baseList;
		protected HashMap<Stats, Double> incList;
		private String name;
		
		public HashMap<Stats, Double> getBaseList(){
			return this.baseList;
		}
		
		public HashMap<Stats, Double> getIncList(){
			return this.incList;
		}
		
		protected String getAbilityName() {
			return this.name;
		}
		
		public void ability(Player player) {};
		
		private Classes(String name) {
			this.name = name;
			this.baseList = new HashMap<Stats, Double>();
			this.incList = new HashMap<Stats, Double>();
		}
	}
}
