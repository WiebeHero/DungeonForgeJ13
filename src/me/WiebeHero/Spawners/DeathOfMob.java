package me.WiebeHero.Spawners;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Illager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtinjector.NBTInjector;
import me.WiebeHero.LootChest.LootRewards;

public class DeathOfMob implements Listener {
	private LootRewards rewards = new LootRewards();
	@EventHandler(priority = EventPriority.LOWEST)
	public void deathEvent(EntityDeathEvent event) {
		if(event.getEntity() instanceof LivingEntity) {
			LivingEntity victim = event.getEntity();
			if(victim instanceof Monster) {
	    		if(victim.getType() == EntityType.ZOMBIE || victim.getType() == EntityType.HUSK || victim.getType() == EntityType.DROWNED) {
					if(this.random() <= 55) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.ROTTEN_FLESH, this.number(3)));
					}
				}
				else if(victim.getType() == EntityType.SKELETON || victim.getType() == EntityType.STRAY) {
					if(this.random() <= 55) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.BONE, this.number(2)));
					}
					if(this.random() <= 45) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.ARROW, this.number(2)));
					}
				}
				else if(victim.getType() == EntityType.SPIDER || victim.getType() == EntityType.CAVE_SPIDER) {
					if(this.random() <= 50) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.STRING, this.number(3)));
					}
					if(this.random() <= 20) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.SPIDER_EYE, this.number(1)));
					}
				}
				else if(victim.getType() == EntityType.CREEPER) {
					if(this.random() <= 50) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.GUNPOWDER, this.number(2)));
					}
				}
				else if(victim.getType() == EntityType.BLAZE) {
					if(this.random() <= 50) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.BLAZE_ROD, this.number(3)));
					}
				}
				else if(victim.getType() == EntityType.ENDERMAN) {
					if(this.random() <= 50) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.ENDER_PEARL, this.number(2)));
					}
				}
				else if(victim.getType() == EntityType.ENDERMITE) {
					if(this.random() <= 25) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.CHORUS_FRUIT, this.number(3)));
					}
					if(this.random() <= 2.5) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.CHORUS_FLOWER, this.number(1)));
					}
				}
				else if(victim.getType() == EntityType.MAGMA_CUBE) {
					if(this.random() <= 40) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.MAGMA_CREAM, this.number(3)));
					}
				}
				else if(victim.getType() == EntityType.ELDER_GUARDIAN) {
					if(this.random() <= 50) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.PRISMARINE_CRYSTALS, this.number(8)));
					}
					if(this.random() <= 50) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.PRISMARINE_SHARD, this.number(8)));
					}
				}
				else if(victim.getType() == EntityType.GUARDIAN) {
					if(this.random() <= 35) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.PRISMARINE_CRYSTALS, this.number(4)));
					}
					if(this.random() <= 35) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.PRISMARINE_SHARD, this.number(4)));
					}
				}
				else if(victim.getType() == EntityType.PIG_ZOMBIE) {
					if(this.random() <= 25) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, this.number(4)));
					}
					if(this.random() <= 5) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.GOLD_INGOT, this.number(2)));
					}
				}
				else if(victim.getType() == EntityType.SHULKER) {
					if(this.random() <= 2.0) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.SHULKER_SHELL, this.number(1)));
					}
				}
				else if(victim.getType() == EntityType.SILVERFISH) {
					if(this.random() <= 2.0) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.STONE, this.number(1)));
					}
				}
				else if(victim.getType() == EntityType.WITCH) {
					if(this.random() < 15.0) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.REDSTONE, this.number(3)));
					}
					if(this.random() < 15.0) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.GLOWSTONE, this.number(3)));
					}
					if(this.random() < 15.0) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.STICK, this.number(2)));
					}
					if(this.random() < 15.0) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.GLASS_BOTTLE, this.number(1)));
					}
					if(this.random() < 15.0) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.BROWN_MUSHROOM, this.number(2)));
					}
					if(this.random() < 15.0) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.RED_MUSHROOM, this.number(2)));
					}
				}
				else if(victim.getType() == EntityType.WITHER_SKELETON) {
					if(this.random() < 65) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.BONE, this.number(3)));
					}
					if(this.random() < 15) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.COAL, this.number(4)));
					}
					if(this.random() < 1.0) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.WITHER_SKELETON_SKULL, this.number(1)));
					}
				}
	    		ItemStack item1 = this.rewards.commonCrystal();
				ItemStack item2 = this.rewards.rareCrystal();
				ItemStack item3 = this.rewards.epicCrystal();
				ItemStack item4 = this.rewards.legendaryCrystal();
				ItemStack item5 = this.rewards.mythicCrystal();
				Entity e = event.getEntity();
	    		e = NBTInjector.patchEntity(e);
	    		NBTCompound comp = NBTInjector.getNbtData(e);
				if(comp.hasKey("SpawnerUUID")) {
					if(this.random() <= 1.00) {
						event.getDrops().add(item1);
					}
					else if(this.random() <= 0.80) {
						event.getDrops().add(item2);
					}
					else if(this.random() <= 0.60) {
						event.getDrops().add(item3);
					}
					else if(this.random() <= 0.40) {
						event.getDrops().add(item4);
					}
					else if(this.random() <= 0.20) {
						event.getDrops().add(item5);
					}
				}
			}
			else if(victim instanceof Villager) {
				if(this.random() <= 65) {
					event.getDrops().clear();
					event.getDrops().add(new ItemStack(Material.EMERALD, this.number(2)));
				}
			}
			else if(victim instanceof Illager) {
				ItemStack item1 = rewards.commonCrystal();
				ItemStack item2 = rewards.rareCrystal();
				ItemStack item3 = rewards.epicCrystal();
				ItemStack item4 = rewards.legendaryCrystal();
				ItemStack item5 = rewards.mythicCrystal();
				Entity e = event.getEntity();
	    		e = NBTInjector.patchEntity(e);
	    		NBTCompound comp = NBTInjector.getNbtData(e);
				if(comp.hasKey("SpawnerUUID")) {
					if(this.random() <= 1.00) {
						event.getDrops().add(item1);
					}
					else if(this.random() <= 0.80) {
						event.getDrops().add(item2);
					}
					else if(this.random() <= 0.60) {
						event.getDrops().add(item3);
					}
					else if(this.random() <= 0.40) {
						event.getDrops().add(item4);
					}
					else if(this.random() <= 0.20) {
						event.getDrops().add(item5);
					}
				}
				if(victim.getType() == EntityType.VINDICATOR) {
					if(this.random() <= 40) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.EMERALD, this.number(2)));
					}
					if(this.random() <= 0.002) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.TOTEM_OF_UNDYING, this.number(1)));
					}
				}
				else if(victim.getType() == EntityType.EVOKER || victim.getType() == EntityType.ILLUSIONER) {
					if(this.random() <= 80) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.EMERALD, this.number(4)));
					}
					if(this.random() <= 1) {
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(Material.TOTEM_OF_UNDYING, this.number(1)));
					}
				}
			}
		}
	}
	public float random() {
		float i = ThreadLocalRandom.current().nextFloat() * 100;
		return i;
	}
	public int number(int max) {
		int i = new Random().nextInt(max - 1) + 1;
		return i;
	}
}
