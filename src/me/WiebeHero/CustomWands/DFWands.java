package me.WiebeHero.CustomWands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import NeededStuff.SwordSwingProgress;
import Skills.AttackSpeed;
import Skills.SkillJoin;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Spawners.SpawnerList;
import net.minecraft.server.v1_13_R2.DamageSource;
import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.EntityArrow;

public class DFWands extends SwordSwingProgress implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	AttackSpeed speed = new AttackSpeed();
	SkillJoin join = new SkillJoin();
	@EventHandler
	public void beam (PlayerAnimationEvent event){
		if(event.getAnimationType() == PlayerAnimationType.ARM_SWING) {
			Player player = event.getPlayer();
			if (player.getInventory().getItemInMainHand() != null){ 
				if(player.getInventory().getItemInMainHand().hasItemMeta()) {
					if(player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
						if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Attack Range")) {
							if(swordSwingProgress.get(player.getName()) == 1.00) {
								Location loc = player.getLocation();
								Vector direction = loc.getDirection();
								player.getLocation().getWorld().playSound(loc, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 2);
								double range = 0.00;
								double damage = 0.00;
								String rangeS = "";
								String damageS = "";
								for(String l : player.getInventory().getItemInMainHand().getItemMeta().getLore()) {
									if(l.contains("Attack Range")) {
										rangeS = ChatColor.stripColor(l);
									}
								}
								for(String l : player.getInventory().getItemInMainHand().getItemMeta().getLore()) {
									if(l.contains("Attack Damage")) {
										damageS = ChatColor.stripColor(l);
									}
								}
								Matcher matcher1 = Pattern.compile("Attack Range: (\\d+)\\.(\\d+)").matcher(ChatColor.stripColor(rangeS));
				  				while(matcher1.find()) {
				  					range = Double.parseDouble(matcher1.group(1) + "." + matcher1.group(2));	
				  				}
				  				Matcher matcher2 = Pattern.compile("Attack Damage: (\\d+)\\.(\\d+)").matcher(ChatColor.stripColor(damageS));
				  				while(matcher2.find()) {
				  					damage = Double.parseDouble(matcher2.group(1) + "." + matcher2.group(2));	
				  				}
								outerloop: for(double t = 0; t < range; t+=0.25){
									loc.add(direction.getX() / 100, direction.getY() / 100, direction.getZ() / 100);
									loc.add(0, 1.5, 0);
									BlockData t1 = Material.ANVIL.createBlockData();
									BlockData t2 = Material.EMERALD_BLOCK.createBlockData();
									BlockData t3 = Material.DIAMOND_BLOCK.createBlockData();
									BlockData t4 = Material.REDSTONE_BLOCK.createBlockData();
									BlockData t5 = Material.MAGENTA_WOOL.createBlockData();
									BlockData t6 = Material.GOLD_BLOCK.createBlockData();
									if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Common")) {
										player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t1);
									}
									else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Rare")) {
										player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t2);
									}
									else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Epic")) {
										player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t3);
									}
									else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Legendary")) {
										player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t4);
									}
									else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Mythic")) {
										player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t5);
									}
									else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Heroic")) {
										player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t6);
									}
									if(loc.getBlock().getType().isSolid()){
										if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Common")) {
											player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t1);
										}
										else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Rare")) {
											player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t2);
										}
										else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Epic")) {
											player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t3);
										}
										else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Legendary")) {
											player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t4);
										}
										else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Mythic")) {
											player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t5);
										}
										else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Heroic")) {
											player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t6);
										}
										break outerloop;
									}
									for (org.bukkit.entity.Entity e : loc.getChunk().getEntities()){
										if (e.getLocation().distance(loc) < 2.0){
											if(e != (player)){
												if(e.getType().isAlive()) {
													Damageable d = (Damageable) e;
													if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Common")) {
														player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t1);
													}
													else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Rare")) {
														player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t2);
													}
													else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Epic")) {
														player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t3);
													}
													else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Legendary")) {
														player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t4);
													}
													else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Mythic")) {
														player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t5);
													}
													else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Heroic")) {
														player.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0.05, 0.05, 0.05, 0.05, t6);
													}
													org.bukkit.World world = player.getWorld();
													EntityArrow testArrow = new EntityArrow(null, ((CraftWorld)world).getHandle()){
														@Override
														protected net.minecraft.server.v1_13_R2.ItemStack getItemStack() {
															// TODO Auto-generated method stub
															return null;
														}
													};
													Arrow arrow = (Arrow) testArrow.getBukkitEntity();
													arrow.setShooter(player);
													arrow.setCustomName("1.00");
													Entity player1 = ((CraftEntity) event.getPlayer()).getHandle();
													((CraftEntity) d).getHandle().damageEntity(DamageSource.arrow(testArrow, player1), (float) damage);
													EntityDamageEvent ev = new EntityDamageEvent(d, DamageCause.PROJECTILE, d.getHealth());
													break outerloop;
												}
											}
										}
									}
									loc.subtract(0, 1.5, 0);
								}
							}
						}
					}
				}
			}
		}
	}
	int levelVictim;
	String realName = "";
	@EventHandler
	public void weapons(EntityDeathEvent event) {
		LivingEntity victim = (LivingEntity) event.getEntity();
		EntityDamageEvent ede = victim.getLastDamageCause();
		DamageCause dc = ede.getCause();
		if(event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player && dc == DamageCause.PROJECTILE){
			Player damager = (Player) event.getEntity().getKiller();
			if(damager.getInventory().getItemInMainHand() != null) {
				if(damager.getInventory().getItemInMainHand().getItemMeta() != null) {
					if(damager.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
						if(damager.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Attack Range:")) {
							String check = "";
							for(String s1 : damager.getInventory().getItemInMainHand().getItemMeta().getLore()){
								if(ChatColor.stripColor(s1).contains("Upgrade Progress:")) {
									check = s1;
								}
							}
							if(check.contains(ChatColor.stripColor("Upgrade Progress:"))) {
								ItemStack item1 = damager.getInventory().getItemInMainHand();
								ItemMeta im = item1.getItemMeta();
								List<String> lore = damager.getInventory().getItemInMainHand().getItemMeta().getLore();
								String llore = check;
								String stripped = ChatColor.stripColor(llore);
								int firstInt = 0;
								int secondInt = 0;
								Matcher matcher = Pattern.compile("(\\d+) / (\\d+)").matcher(ChatColor.stripColor(stripped));
								while(matcher.find()) {
								    firstInt = Integer.parseInt(matcher.group(1));
								    secondInt = Integer.parseInt(matcher.group(2));
								}
								int getLine = 0;
							    for(int lijn = 0; lijn < lore.size(); lijn++) {
							    	if(lore.get(lijn).contains(ChatColor.stripColor("Upgrade Progress:"))) {
							    		getLine = lijn;
							    	}
							    }
						    	String nameW = damager.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
						    	String stripped1 = ChatColor.stripColor(nameW);
						    	String[] partName = stripped1.split(Pattern.quote(" ["));
						    	String part1 = partName[0];
						    	Set<String> configSection1 = plugin.getConfig().getConfigurationSection(("Items.Wands")).getKeys(false);
						    	List<String> configSection2 = new ArrayList<String>(configSection1);
						    	for(int i6 = 0; i6 < configSection2.size(); i6++) {
						    		if(part1.contains(configSection2.get(i6))) {
						    			realName = part1;
						    		}
						    	}
						    	if(damager.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(realName)){
							    	if(lore.get(getLine).contains(ChatColor.stripColor("Upgrade Progress: "))) {
							    		int totalxpearned = 0;
							    		if(SpawnerList.getMobList().containsKey(event.getEntity().getUniqueId())) {
											int tier = SpawnerList.getMobList().get(event.getEntity().getUniqueId());
											int i1 = new Random().nextInt(100 + 100 * tier) + (50 + 50 * tier);
											totalxpearned = i1 + firstInt;
										}
										else {
											if(victim.getType().equals(EntityType.ZOMBIE)) {
												totalxpearned = 2 + firstInt;
											}
											else if(victim.getType().equals(EntityType.SKELETON)) {
												totalxpearned = 3 + firstInt;
											}
											else if(victim.getType().equals(EntityType.SPIDER)) {
												totalxpearned = 2 + firstInt;
											}
											else if(victim.getType().equals(EntityType.CREEPER)) {
												totalxpearned = 3 + firstInt;
											}
											else if(victim.getType().equals(EntityType.ENDERMAN)) {
												totalxpearned = 6 + firstInt;
											}
											else if(victim.getType().equals(EntityType.BLAZE)) {
												totalxpearned = 4 + firstInt;
											}
											else if(victim.getType().equals(EntityType.WITCH)) {
												totalxpearned = 4 + firstInt;
											}
											else if(victim.getType().equals(EntityType.VILLAGER)) {
												totalxpearned = 2 + firstInt;
											}
											else if(victim.getType().equals(EntityType.IRON_GOLEM)) {
												totalxpearned = 8 + firstInt;
											}
											else if(victim.getType().equals(EntityType.CHICKEN)) {
												totalxpearned = 1 + firstInt;
											}
											else if(victim.getType().equals(EntityType.SHEEP)) {
												totalxpearned = 4 + firstInt;
											}
											else if(victim.getType().equals(EntityType.COW)) {
												totalxpearned = 4 + firstInt;
											}
											else if(victim.getType().equals(EntityType.PIG_ZOMBIE)) {
												totalxpearned = 3 + firstInt;
											}
											else if(victim.getType().equals(EntityType.HORSE)) {
												totalxpearned = 3 + firstInt;
											}
											else if(victim.getType() == EntityType.PLAYER) {
												int levelVictim = join.getLevelList().get(victim.getUniqueId());
												if(levelVictim >= 0) {
													int i6 = new Random().nextInt(50) + 50;
													totalxpearned = i6 + firstInt;
												}
												else if(levelVictim > 10) {
													int i6 = new Random().nextInt(100) + 60;
													totalxpearned = i6 + firstInt;
												}
												else if(levelVictim > 20) {
													int i6 = new Random().nextInt(150) + 70;
													totalxpearned = i6 + firstInt;
												}
												else if(levelVictim > 30) {
													int i6 = new Random().nextInt(200) + 80;
													totalxpearned = i6 + firstInt;
												}
												else if(levelVictim > 40) {
													int i6 = new Random().nextInt(250) + 90;
													totalxpearned = i6 + firstInt;
												}
												else if(levelVictim > 50) {
													int i6 = new Random().nextInt(300) + 100;
													totalxpearned = i6 + firstInt;
												}
												else if(levelVictim > 60) {
													int i6 = new Random().nextInt(350) + 110;
													totalxpearned = i6 + firstInt;
												}
												else if(levelVictim > 70) {
													int i6 = new Random().nextInt(400) + 120;
													totalxpearned = i6 + firstInt;
												}
												else if(levelVictim > 80) {
													int i6 = new Random().nextInt(450) + 130;
													totalxpearned = i6 + firstInt;
												}
												else if(levelVictim > 90) {
													int i6 = new Random().nextInt(500) + 140;
													totalxpearned = i6 + firstInt;
												}
												else if(levelVictim > 99) {
													int i6 = new Random().nextInt(550) + 150;
													totalxpearned = i6 + firstInt;
												}
											}
										}
										if(totalxpearned > 0){
							    			if(totalxpearned >= secondInt) {
												ItemStack item = item1;
												damager.getWorld().playSound(damager.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 1);
							    	            //Config Data
							    				String levelString[] = stripped1.split("\\[Lv |\\]");
							    				String levelStringFinal = levelString[1];
							    				int levelWeapon = Integer.parseInt(levelStringFinal);
							    				levelWeapon++;
							    				String enchantmentsString = plugin.getConfig().getString("Items.Wands." + realName + ".Enchantments." + levelWeapon);
							    				double damageWeapon = plugin.getConfig().getDouble("Items.Wands." + realName + ".Damage." + levelWeapon);
							    				double speedWeapon = plugin.getConfig().getDouble("Items.Wands." + realName + ".Speed." + levelWeapon);
							    				double wandRange = plugin.getConfig().getDouble("Items.Wands." + realName + ".Range." + levelWeapon);
							    				String rarity = plugin.getConfig().getString("Items.Wands." + realName + ".Rarity" );
							    				//Config Data
							    				//Weapon Data
							    				ItemMeta meta = item.getItemMeta();
							    				String translator = "";
							    				if(rarity.contains("Common")) {
							    					translator = "&7";
							    				}
							    				else if(rarity.contains("Rare")) {
							    					translator = "&a";
							    				}
							    				else if(rarity.contains("Epic")) {
							    					translator = "&b";
							    				}
							    				else if(rarity.contains("Legendary")) {
							    					translator = "&c";
							    				}
							    				else if(rarity.contains("Mythic")) {
							    					translator = "&5";
							    				}
							    				else if(rarity.contains("Heroic")) {
							    					translator = "&e";
							    				}
							    				meta.setDisplayName(new ColorCodeTranslator().colorize(translator + realName + " &a[&6Lv " + levelWeapon + "&a]"));
							    				ArrayList<String> newLore = new ArrayList<String>();
							    				String enchantmentSetting[] = enchantmentsString.split("//");
							    				for(int i = 0; i < enchantmentSetting.length; i++) {
							    					newLore.add(new ColorCodeTranslator().colorize("&9" + enchantmentSetting[i]));
							    				}
							    				newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
							    				newLore.add(new ColorCodeTranslator().colorize("&7Attack Damage: &6" + damageWeapon));
							    				newLore.add(new ColorCodeTranslator().colorize("&7Attack Speed: &6" + speedWeapon));
							    				newLore.add(new ColorCodeTranslator().colorize("&7Attack Range: &6" + wandRange));
							    				newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
							    				if(levelWeapon < 15) {
							    					int xp = plugin.getConfig().getInt("XPValue." + levelWeapon);
							    					newLore.add(new ColorCodeTranslator().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l" + xp + "&a]"));
							    				}
							    				else if(levelWeapon == 15) {
							    					newLore.add(new ColorCodeTranslator().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
							    				}
							    				newLore.add(new ColorCodeTranslator().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
							    				newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
							    				if(levelWeapon >= 6) {
							    					int loreRequired = levelWeapon - 4;
							    					int levelRequired = loreRequired * 5;
							    					newLore.add(new ColorCodeTranslator().colorize("&7Level Required: &6" + levelRequired));
							    				}
							    				newLore.add(new ColorCodeTranslator().colorize("&7Rarity: " + rarity));
							    				meta.setLore(newLore);
							    				item.setItemMeta(meta);
							    				speed.attackSpeedRun(damager, 0L, 1.0);
							    			}
								    		else {
									    		lore.set(getLine, new ColorCodeTranslator().colorize("&7Upgrade Progress: " + "&a[&b&l" + (totalxpearned) + " &6/ " + "&b&l" + secondInt + "&a]"));
									    		for(int lijn1 = 0; lijn1 < lore.size(); lijn1++) {
									    			if(lore.get(lijn1).contains(ChatColor.stripColor("::::"))) {
										    			double barprogress = (double) totalxpearned / (double) secondInt * 100.0;
										    			if(barprogress >= 0 && barprogress <= 2) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:&7:::::::::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 2 && barprogress <= 4) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::&7::::::::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 4 && barprogress <= 6) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::&7:::::::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 6 && barprogress <= 8) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::&7::::::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 8 && barprogress <= 10) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::&7:::::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 10 && barprogress <= 12) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::&7::::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 12 && barprogress <= 14) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::&7:::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 14 && barprogress <= 16) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::&7::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 16 && barprogress <= 18) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::&7:::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 18 && barprogress <= 20) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::&7::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 20 && barprogress <= 22) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::&7:::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 22 && barprogress <= 24) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::&7::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 24 && barprogress <= 26) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::&7:::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 26 && barprogress <= 28) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::&7::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 28 && barprogress <= 30) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::&7:::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 30 && barprogress <= 32) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::&7::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 32 && barprogress <= 34) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::&7:::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 34 && barprogress <= 36) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::&7::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 36 && barprogress <= 38) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::&7:::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 38 && barprogress <= 40) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::&7::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 40 && barprogress <= 42) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::&7:::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 42 && barprogress <= 44) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::&7::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 44 && barprogress <= 46) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::&7:::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 46 && barprogress <= 48) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::&7::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 48 && barprogress <= 50) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::&7:::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 50 && barprogress <= 52) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::&7::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 52 && barprogress <= 54) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::&7:::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 54 && barprogress <= 56) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::&7::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 56 && barprogress <= 58) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::&7:::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 58 && barprogress <= 60) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::&7::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 60 && barprogress <= 62) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::&7:::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 62 && barprogress <= 64) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::&7::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 64 && barprogress <= 66) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::&7:::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 66 && barprogress <= 68) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::&7::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 68 && barprogress <= 70) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::::&7:::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 70 && barprogress <= 72) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::::&7::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 72 && barprogress <= 74) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::::::&7:::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 74 && barprogress <= 76) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::::::&7::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 76 && barprogress <= 78) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::&7:::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 78 && barprogress <= 80) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::&7::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 80 && barprogress <= 82) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::::&7:::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 82 && barprogress <= 84) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::::&7::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 84 && barprogress <= 86) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::::::&7:::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 86 && barprogress <= 88) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::::::&7::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 88 && barprogress <= 90) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::::::::&7:::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 90 && barprogress <= 92) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::::::::&7::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 92 && barprogress <= 94) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::::::::::&7:::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 94 && barprogress <= 96) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::::::::::&7::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 96 && barprogress <= 98) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::::::::::::&7:&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
										    			else if(barprogress >= 98 && barprogress <= 100) {
										    				lore.set(lijn1, new ColorCodeTranslator().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a" + String.format("%.2f", barprogress) + "%"));
										    			}
									    			}
									    		}
									    		im.setLore(lore);
									    		item1.setItemMeta(im);
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
