package me.WiebeHero.Spawners;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtinjector.NBTInjector;
import me.WiebeHero.LootChest.LootRewards;

public class DeathOfMob implements Listener {
	private LootRewards rewards = new LootRewards();
	@EventHandler
	public void deathEvent(EntityDeathEvent event) {
		if(event.getEntity().getKiller() instanceof Player) {
			if(event.getEntity() instanceof LivingEntity) {
				LivingEntity victim = event.getEntity();
				if(victim instanceof Monster) {
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
					if(victim.getType() == EntityType.ZOMBIE || victim.getType() == EntityType.HUSK) {
						if(random() < 30) {
							event.getDrops().add(new ItemStack(Material.ROTTEN_FLESH, number()));
						}
					}
					else if(victim.getType() == EntityType.SKELETON || victim.getType() == EntityType.STRAY) {
						if(random() < 30) {
							event.getDrops().add(new ItemStack(Material.BONE, number()));
						}
						if(random() < 20) {
							event.getDrops().add(new ItemStack(Material.ARROW, number()));
						}
					}
					else if(victim.getType() == EntityType.SPIDER || victim.getType() == EntityType.CAVE_SPIDER) {
						if(random() < 30) {
							event.getDrops().add(new ItemStack(Material.STRING, number()));
						}
						if(random() < 10) {
							event.getDrops().add(new ItemStack(Material.SPIDER_EYE, number()));
						}
					}
					else if(victim.getType() == EntityType.CREEPER) {
						if(random() < 30) {
							event.getDrops().add(new ItemStack(Material.GUNPOWDER, number()));
						}
					}
					else if(victim.getType() == EntityType.BLAZE) {
						if(random() < 2.5) {
							event.getDrops().add(new ItemStack(Material.BLAZE_ROD, number()));
						}
					}
					else if(victim.getType() == EntityType.VINDICATOR) {
						if(random() < 1) {
							event.getDrops().add(new ItemStack(Material.EMERALD, number()));
						}
						if(random() < 0.002) {
							event.getDrops().add(new ItemStack(Material.TOTEM_OF_UNDYING, number()));
						}
					}
					else if(victim.getType() == EntityType.ENDERMAN) {
						if(random() < 5) {
							event.getDrops().add(new ItemStack(Material.ENDER_PEARL, number()));
						}
					}
					else if(victim.getType() == EntityType.EVOKER) {
						if(random() < 5) {
							event.getDrops().add(new ItemStack(Material.EMERALD, number()));
						}
						if(random() < 1 ) {
							event.getDrops().add(new ItemStack(Material.TOTEM_OF_UNDYING, number()));
						}
					}
					else if(victim.getType() == EntityType.MAGMA_CUBE) {
						if(random() < 5) {
							event.getDrops().add(new ItemStack(Material.MAGMA_CREAM, number()));
						}
					}
					else if(victim.getType() == EntityType.ELDER_GUARDIAN) {
						if(random() < 50) {
							event.getDrops().add(new ItemStack(Material.PRISMARINE_CRYSTALS, number()));
						}
						if(random() < 50) {
							event.getDrops().add(new ItemStack(Material.PRISMARINE_SHARD, number()));
						}
					}
					else if(victim.getType() == EntityType.GUARDIAN) {
						if(random() < 10) {
							event.getDrops().add(new ItemStack(Material.PRISMARINE_CRYSTALS, number()));
						}
						if(random() < 10) {
							event.getDrops().add(new ItemStack(Material.PRISMARINE_SHARD, number()));
						}
					}
					else if(victim.getType() == EntityType.PIG_ZOMBIE) {
						if(random() < 10) {
							event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, number()));
						}
						if(random() < 0.5) {
							event.getDrops().add(new ItemStack(Material.GOLD_INGOT, number()));
						}
					}
					else if(victim.getType() == EntityType.SHULKER) {
						if(random() < 0.3) {
							event.getDrops().add(new ItemStack(Material.SHULKER_SHELL, number()));
						}
					}
					else if(victim.getType() == EntityType.WITCH) {
						if(random() < 2.5) {
							event.getDrops().add(new ItemStack(Material.REDSTONE, number()));
						}
						if(random() < 2.5) {
							event.getDrops().add(new ItemStack(Material.GLOWSTONE, number()));
						}
						if(random() < 2.5) {
							event.getDrops().add(new ItemStack(Material.STICK, number()));
						}
						if(random() < 2.5) {
							event.getDrops().add(new ItemStack(Material.GLASS_BOTTLE, number()));
						}
						if(random() < 2.5) {
							event.getDrops().add(new ItemStack(Material.BROWN_MUSHROOM, number()));
						}
						if(random() < 2.5) {
							event.getDrops().add(new ItemStack(Material.RED_MUSHROOM, number()));
						}
					}
					else if(victim.getType() == EntityType.WITHER_SKELETON) {
						if(random() < 10) {
							event.getDrops().add(new ItemStack(Material.BONE, number()));
						}
						if(random() < 5) {
							event.getDrops().add(new ItemStack(Material.COAL, number()));
						}
						if(random() < 0.1) {
							event.getDrops().add(new ItemStack(Material.WITHER_SKELETON_SKULL, number()));
						}
					}
				}
				else if(victim instanceof Villager) {
					if(random() <= 50) {
						event.getDrops().add(new ItemStack(Material.EMERALD, number()));
					}
				}
			}
		}
	}
	public float random() {
		float i = ThreadLocalRandom.current().nextFloat() * 100;
		return i;
	}
	public int number() {
		int i = new Random().nextInt(1) + 1;
		return i;
	}
}
