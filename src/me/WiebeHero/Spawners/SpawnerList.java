package me.WiebeHero.Spawners;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class SpawnerList implements Listener{
	private static HashMap<UUID, Integer> mobList = new HashMap<UUID, Integer>();
	private HashMap<UUID, Integer> teleportBack = new HashMap<UUID, Integer>();
	public void spawnerJoin() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(CustomEnchantments.getInstance().getShutdownState() == false) {
					if(!SetSpawner.getSpawnerLocList().isEmpty()) {
						for(int counter = 1; counter <= SetSpawner.getSpawnerLocList().size(); counter++) {
							Location spawnerLoc = new Location(Bukkit.getWorld(SetSpawner.getWorldList().get(counter)), SetSpawner.getSpawnerLocList().get(counter).getX(), SetSpawner.getSpawnerLocList().get(counter).getY(), SetSpawner.getSpawnerLocList().get(counter).getZ());
		  					spawnerLoc.setPitch(0.0F);
		  					spawnerLoc.setYaw(0.0F);
				  			spawnerLoc.setX(spawnerLoc.getX() + randomLocOffSet());
				  			spawnerLoc.setZ(spawnerLoc.getZ() + randomLocOffSet());
				  			Location rightLoc = SetSpawner.getSpawnerLocList().get(counter);
				  			rightLoc.setWorld(Bukkit.getWorld(SetSpawner.getWorldList().get(counter)));
				  			for(Entity e : rightLoc.getWorld().getNearbyEntities(rightLoc, 80, 80, 80)) {
				  				if(e instanceof Monster) {
				  					if(teleportBack.containsKey(e.getUniqueId()) && teleportBack.get(e.getUniqueId()) == counter) {
				  						Location check2 = e.getLocation();
				  						if(rightLoc.distance(check2) > 20) {
				  							for(double y = rightLoc.getY() + 15.00; y > 0.00;) {
				  								y--;
				  								rightLoc.setY(y);
				  								if(rightLoc.getBlock().getType() != Material.AIR) {
				  									break;
				  								}
				  							}
				  							e.teleport(spawnerLoc);
				  						}
				  					}
				  				}
				  			}
						}
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 50L);
		new BukkitRunnable() {
			@Override
			public void run() {
				if(CustomEnchantments.getInstance().getShutdownState() == false) {
					if(!SetSpawner.getSpawnerLocList().isEmpty()) {
						for(int counter = 1; counter <= SetSpawner.getSpawnerLocList().size(); counter++) {
							boolean check = false;
		  					Location fixedLoc = SetSpawner.getSpawnerLocList().get(counter);
		  					int countMobs = 0;
		  					fixedLoc.setWorld(Bukkit.getWorld(SetSpawner.getWorldList().get(counter)));
		  					for(Entity e1 : fixedLoc.getWorld().getNearbyEntities(fixedLoc, 45, 45, 45)) {
		  						if(e1 instanceof Player) {
		  							check = true;
		  							break;
		  						}
		  					}
				  			for(Entity e : fixedLoc.getWorld().getNearbyEntities(fixedLoc, 20, 20, 20)) {
				  				if(e != null) {
				  					if(teleportBack.containsKey(e.getUniqueId())) {
					  					if(teleportBack.get(e.getUniqueId()) == counter) {
					  						countMobs++;
					  					}
				  					}
				  				}
				  			}
				  			if(check == true) {
					  			int tier = SetSpawner.getTieredList().get(counter);
					  			int mobMax = 3 + 1 * tier - countMobs;
					  			for(int i = 0; i <= mobMax; i++) {
					  				Location spawnLoc = new Location(Bukkit.getWorld(SetSpawner.getWorldList().get(counter)), SetSpawner.getSpawnerLocList().get(counter).getX(), SetSpawner.getSpawnerLocList().get(counter).getY(), SetSpawner.getSpawnerLocList().get(counter).getZ());
					  				double originalY = spawnLoc.getY();
					  				double x = spawnLoc.getX();
					  				double z = spawnLoc.getZ();
				  					spawnLoc.setX(spawnLoc.getX() + randomLocOffSet());
				  					spawnLoc.setZ(spawnLoc.getZ() + randomLocOffSet());
					  				for(double y1 = spawnLoc.getY() + 10.00; y1 > 0;) {
					  					y1--;
					  					spawnLoc.setY(y1);
					  					if(spawnLoc.getBlock().getType() != Material.AIR) {
					  						spawnLoc.setY(y1 + 2.00);
					  						if(spawnLoc.getBlock().getType() == Material.AIR) {
					  							break;
					  						}
					  						else {
					  							y1 = originalY + 10.00;
					  							spawnLoc.setX(spawnLoc.getX() + randomLocOffSet());
							  					spawnLoc.setZ(spawnLoc.getZ() + randomLocOffSet());
					  						}
					  					}
					  				}
					  				LivingEntity mob = (LivingEntity) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.valueOf(SetSpawner.getTypeList().get(counter)));
					  				SetSpawner.getSpawnerLocList().get(counter).setX(x);
					  				SetSpawner.getSpawnerLocList().get(counter).setZ(z);
					  				String name = SetSpawner.getTypeList().get(counter).toString();
					  				name = name.toLowerCase();
					  				String mobName = name.substring(0, 1).toUpperCase() + name.substring(1);
					  				if(tier == 1) {
					  					mob.setCustomName(new ColorCodeTranslator().colorize("&f" + mobName + " &6[&b" + new Random().nextInt(20) + "&6]"));
					  				}
					  				else if(tier == 2) {
					  					mob.setCustomName(new ColorCodeTranslator().colorize("&a" + mobName + " &6[&b" + (new Random().nextInt(20) + 20) + "&6]"));
					  				}
					  				else if(tier == 3) {
					  					mob.setCustomName(new ColorCodeTranslator().colorize("&e" + mobName + " &6[&b" + (new Random().nextInt(20) + 40) + "&6]"));
					  				}
					  				else if(tier == 4) {
					  					mob.setCustomName(new ColorCodeTranslator().colorize("&e" + mobName + " &6[&b" + (new Random().nextInt(20) + 60) + "&6]"));
					  				}
					  				else if(tier == 5) {
					  					mob.setCustomName(new ColorCodeTranslator().colorize("&e" + mobName + " &6[&b" + (new Random().nextInt(20) + 80) + "&6]"));
					  				}
					  				mob.setCustomNameVisible(true);
					  				if(mob instanceof Zombie) {
					  					Zombie zombie = (Zombie) mob;
					  					zombie.setBaby(false);
					  				}
					  				else if(mob instanceof PigZombie) {
					  					Zombie zombie = (Zombie) mob;
					  					zombie.setBaby(false);
					  				}
					  				mob.getEquipment().setHelmet(new ItemStack(Material.STONE_BUTTON, 1));
				  					if(randomDouble() < 40 + 10 * tier) {
				  						if(tier == 1) {
				  							mob.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
				  						}
				  						else if(tier == 2) {
				  							mob.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE, 1));
				  						}
				  						else if(tier == 3){
				  							mob.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
				  						}
				  						else if(tier == 4) {
				  							mob.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
				  						}
				  						else if(tier == 5) {
				  							mob.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
				  						}
				  					}
				  					if(randomDouble() < 40 + 10 * tier) {
				  						if(tier == 1) {
				  							mob.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
				  						}
				  						else if(tier == 2) {
				  							mob.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS, 1));
				  						}
				  						else if(tier == 3){
				  							mob.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
				  						}
				  						else if(tier == 4) {
				  							mob.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
				  						}
				  						else if(tier == 5) {
				  							mob.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
				  						}
				  					}
				  					if(randomDouble() < 40 + 10 * tier) {
				  						if(tier == 1) {
				  							mob.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
				  						}
				  						else if(tier == 2) {
				  							mob.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS, 1));
				  						}
				  						else if(tier == 3){
				  							mob.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
				  						}
				  						else if(tier == 4) {
				  							mob.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS, 1));
				  						}
				  						else if(tier == 5) {
				  							mob.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
				  						}
				  					}
				  					if(randomDouble() < 75) {
				  						if(tier == 1) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_SWORD, 1));
				  						}
				  						if(tier == 2) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD, 1));
				  						}
				  						if(tier == 3) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SWORD, 1));
				  						}
				  						if(tier == 4) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD, 1));
				  						}
				  						if(tier == 5) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD, 1));
				  						}
				  					}
				  					else if(randomDouble() < 60) {
				  						if(tier == 1) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_AXE, 1));
				  						}
				  						if(tier == 2) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_AXE, 1));
				  						}
				  						if(tier == 3) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_AXE, 1));
				  						}
				  						if(tier == 4) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE, 1));
				  						}
				  						if(tier == 5) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE, 1));
				  						}
				  					}
				  					else if(randomDouble() < 45) {
				  						if(tier == 1) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_PICKAXE, 1));
				  						}
				  						if(tier == 2) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_PICKAXE, 1));
				  						}
				  						if(tier == 3) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_PICKAXE, 1));
				  						}
				  						if(tier == 4) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_PICKAXE, 1));
				  						}
				  						if(tier == 5) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_PICKAXE, 1));
				  						}
				  					}
				  					else if(randomDouble() < 30) {
				  						if(tier == 1) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_HOE, 1));
				  						}
				  						if(tier == 2) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_HOE, 1));
				  						}
				  						if(tier == 3) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_HOE, 1));
				  						}
				  						if(tier == 4) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_HOE, 1));
				  						}
				  						if(tier == 5) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_HOE, 1));
				  						}
				  					}
				  					else if(randomDouble() < 15) {
				  						if(tier == 1) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_SHOVEL, 1));
				  						}
				  						if(tier == 2) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_SHOVEL, 1));
				  						}
				  						if(tier == 3) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SHOVEL, 1));
				  						}
				  						if(tier == 4) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SHOVEL, 1));
				  						}
				  						if(tier == 5) {
				  							mob.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SHOVEL, 1));
				  						}
				  					}
				  					if(enchantmentChance() < 40 + 10 * tier) {
					  					if(mob.getEquipment().getHelmet() != null) {
					  						if(mob.getEquipment().getHelmet().hasItemMeta()) {
						  						ItemMeta meta = mob.getEquipment().getHelmet().getItemMeta();
						  						meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, enchantmentLevel(tier), true);
						  						mob.getEquipment().getHelmet().setItemMeta(meta);
					  						}
					  					}
					  				}
				  					if(enchantmentChance() < 40 + 10 * tier) {
					  					if(mob.getEquipment().getChestplate() != null) {
					  						if(mob.getEquipment().getChestplate().hasItemMeta()) {
						  						ItemMeta meta = mob.getEquipment().getChestplate().getItemMeta();
						  						meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, enchantmentLevel(tier), true);
						  						mob.getEquipment().getChestplate().setItemMeta(meta);
					  						}
					  					}
					  				}
				  					if(enchantmentChance() < 40 + 10 * tier) {
					  					if(mob.getEquipment().getLeggings() != null) {
					  						if(mob.getEquipment().getLeggings().hasItemMeta()) {
						  						ItemMeta meta = mob.getEquipment().getLeggings().getItemMeta();
						  						meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, enchantmentLevel(tier), true);
						  						mob.getEquipment().getLeggings().setItemMeta(meta);
					  						}
					  					}
					  				}
				  					if(enchantmentChance() < 40 + 10 * tier) {
					  					if(mob.getEquipment().getBoots() != null) {
					  						if(mob.getEquipment().getBoots().hasItemMeta()) {
						  						ItemMeta meta = mob.getEquipment().getBoots().getItemMeta();
						  						meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, enchantmentLevel(tier), true);
						  						mob.getEquipment().getBoots().setItemMeta(meta);
					  						}
					  					}
					  				}
				  					if(enchantmentChance() < 40 + 10 * tier) {
					  					if(mob.getEquipment().getItemInMainHand() != null) {
					  						if(mob.getEquipment().getItemInMainHand().hasItemMeta()) {
						  						ItemMeta meta = mob.getEquipment().getItemInMainHand().getItemMeta();
						  						meta.addEnchant(Enchantment.DAMAGE_ALL, enchantmentLevel(tier), true);
						  						mob.getEquipment().getItemInMainHand().setItemMeta(meta);
					  						}
					  					}
					  				}
				  					AttributeInstance attribute1 = mob.getAttribute(Attribute.GENERIC_MAX_HEALTH);
				  					AttributeInstance attribute2 = mob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
				  					double dub = randomDouble1(tier);
									attribute1.setBaseValue(dub);
									mob.setHealth(dub);
									attribute2.setBaseValue(0.23 + 0.02 * tier);
							  		mobList.put(mob.getUniqueId(), 1);
							  		teleportBack.put(mob.getUniqueId(), counter);
					  			}
				  			}
				  			countMobs = 0;
						}
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 100L);
	}
	public double randomDouble() {
		double i = ThreadLocalRandom.current().nextDouble() * 100;
		return i;
	}
	public double extraHealth() {
		double i = ThreadLocalRandom.current().nextDouble() * 15.00;
		return i;
	}
	public double randomDouble1(int tier) {
		double i = 30.00 * tier + extraHealth();
		return i;
	}
	public double enchantmentChance() {
		double i = ThreadLocalRandom.current().nextDouble() * 100;
		return i;
	}
	public int enchantmentLevel(int tier) {
		int i = new Random().nextInt(tier) + 1;
		return i;
	}
	public double randomLocOffSet() {
		double i = ThreadLocalRandom.current().nextDouble() * 30.00 - 15.00;
		return i;
	}
	public double randomLocOffSetY() {
		double i = ThreadLocalRandom.current().nextDouble() * 2.00 + 1.00;
		return i;
	}
	public static HashMap<UUID, Integer> getMobList() {
		return SpawnerList.mobList;
	}
}
