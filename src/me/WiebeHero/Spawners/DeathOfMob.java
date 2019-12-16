package me.WiebeHero.Spawners;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtinjector.NBTInjector;
import me.WiebeHero.CustomEnchantments.CCT;

public class DeathOfMob implements Listener {
	@EventHandler
	public void deathEvent(EntityDeathEvent event) {
		if(event.getEntity().getKiller() instanceof Player) {
			if(event.getEntity() instanceof LivingEntity) {
				LivingEntity victim = event.getEntity();
				//----------------------------------------------------------------------------------
				//Common Crystal
				//----------------------------------------------------------------------------------
				ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
				ItemMeta itemmeta1 = item1.getItemMeta();
				itemmeta1.setDisplayName(new CCT().colorize("&7Common Crystal"));
				ArrayList<String> lore1 = new ArrayList<String>();
				lore1.add(new CCT().colorize("&7Bring this to &6NOVIS &7to get"));
				lore1.add(new CCT().colorize("&7a reward!"));
				lore1.add(new CCT().colorize("&7Rarity: Common"));
				itemmeta1.setLore(lore1);
				item1.setItemMeta(itemmeta1);
				//----------------------------------------------------------------------------------
				//Rare Crystal
				//----------------------------------------------------------------------------------
				ItemStack item2 = new ItemStack(Material.NETHER_STAR, 1);
				ItemMeta itemmeta2 = item2.getItemMeta();
				itemmeta2.setDisplayName(new CCT().colorize("&aRare Crystal"));
				ArrayList<String> lore2 = new ArrayList<String>();
				lore2.add(new CCT().colorize("&7Bring this to &6NOVIS &7to get"));
				lore2.add(new CCT().colorize("&7a reward!"));
				lore2.add(new CCT().colorize("&7Rarity: &aRare"));
				itemmeta2.setLore(lore2);
				item2.setItemMeta(itemmeta2);
				//----------------------------------------------------------------------------------
				//Epic Crystal
				//----------------------------------------------------------------------------------
				ItemStack item3 = new ItemStack(Material.NETHER_STAR, 1);
				ItemMeta itemmeta3 = item3.getItemMeta();
				itemmeta3.setDisplayName(new CCT().colorize("&bEpic Crystal"));
				ArrayList<String> lore3 = new ArrayList<String>();
				lore3.add(new CCT().colorize("&7Bring this to &6NOVIS &7to get"));
				lore3.add(new CCT().colorize("&7a reward!"));
				lore3.add(new CCT().colorize("&7Rarity: &bEpic"));
				itemmeta3.setLore(lore3);
				item3.setItemMeta(itemmeta3);
				//----------------------------------------------------------------------------------
				//Legendary Crystal
				//----------------------------------------------------------------------------------
				ItemStack item4 = new ItemStack(Material.NETHER_STAR, 1);
				ItemMeta itemmeta4 = item4.getItemMeta();
				itemmeta4.setDisplayName(new CCT().colorize("&cLegendary Crystal"));
				ArrayList<String> lore4 = new ArrayList<String>();
				lore4.add(new CCT().colorize("&7Bring this to &6NOVIS &7to get"));
				lore4.add(new CCT().colorize("&7a reward!"));
				lore4.add(new CCT().colorize("&7Rarity: &cLegendary"));
				itemmeta4.setLore(lore4);
				item4.setItemMeta(itemmeta4);
				//----------------------------------------------------------------------------------
				//Mythic Crystal
				//----------------------------------------------------------------------------------
				ItemStack item5 = new ItemStack(Material.NETHER_STAR, 1);
				ItemMeta itemmeta5 = item5.getItemMeta();
				itemmeta5.setDisplayName(new CCT().colorize("&5Mythic Crystal"));
				ArrayList<String> lore5 = new ArrayList<String>();
				lore5.add(new CCT().colorize("&7Bring this to &6NOVIS &7to get"));
				lore5.add(new CCT().colorize("&7a reward!"));
				lore5.add(new CCT().colorize("&7Rarity: &5Mythic"));
				itemmeta5.setLore(lore5);
				item4.setItemMeta(itemmeta5);
				Entity e = event.getEntity();
	    		e = NBTInjector.patchEntity(e);
	    		NBTCompound comp = NBTInjector.getNbtData(e);
				if(comp.hasKey("SpawnerUUID")) {
					int tier = comp.getInteger("Tier");
					if(tier >= 1) {
						if(random() < 0.50 + tier * 0.50) {
							event.getDrops().add(item1);
						}
					}
					else if(tier >= 2) {
						if(random() < 0.40 + tier * 0.40) {
							event.getDrops().add(item2);
						}
					}
					else if(tier >= 3) {
						if(random() < 0.30 + tier * 0.30) {
							event.getDrops().add(item3);
						}
					}
					else if(tier >= 4) {
						if(random() < 0.20 + tier * 0.20) {
							event.getDrops().add(item4);
						}
					}
					else if(tier == 5) {
						if(random() < 0.10 + tier * 0.10) {
							event.getDrops().add(item5);
						}
					}
					if(victim.getType() == EntityType.ZOMBIE || victim.getType() == EntityType.HUSK) {
						if(random() < 30 + 5 * tier) {
							event.getDrops().add(new ItemStack(Material.ROTTEN_FLESH, number()));
						}
					}
					else if(victim.getType() == EntityType.SKELETON || victim.getType() == EntityType.STRAY) {
						if(random() < 30 + 5 * tier) {
							event.getDrops().add(new ItemStack(Material.BONE, number()));
						}
						if(random() < 20 + 5 * tier) {
							event.getDrops().add(new ItemStack(Material.ARROW, number()));
						}
					}
					else if(victim.getType() == EntityType.SPIDER || victim.getType() == EntityType.CAVE_SPIDER) {
						if(random() < 30 + 10 * tier) {
							event.getDrops().add(new ItemStack(Material.STRING, number()));
						}
						if(random() < 10 + 5 * tier) {
							event.getDrops().add(new ItemStack(Material.SPIDER_EYE, number()));
						}
					}
					else if(victim.getType() == EntityType.CREEPER) {
						if(random() < 30 + 7.5 * tier) {
							event.getDrops().add(new ItemStack(Material.GUNPOWDER, number()));
						}
					}
					else if(victim.getType() == EntityType.BLAZE) {
						if(random() < 2.5 + 2.5 * tier) {
							event.getDrops().add(new ItemStack(Material.BLAZE_ROD, number()));
						}
					}
					else if(victim.getType() == EntityType.VINDICATOR) {
						if(random() < 1 + tier * 0.8) {
							event.getDrops().add(new ItemStack(Material.EMERALD, number()));
						}
						if(random() < 0.002 + tier * 0.002) {
							event.getDrops().add(new ItemStack(Material.TOTEM_OF_UNDYING, number()));
						}
					}
					else if(victim.getType() == EntityType.ENDERMAN) {
						if(random() < 5 + 2.5 * tier) {
							event.getDrops().add(new ItemStack(Material.ENDER_PEARL, number()));
						}
					}
					else if(victim.getType() == EntityType.EVOKER) {
						if(random() < 5 + tier * 5) {
							event.getDrops().add(new ItemStack(Material.EMERALD, number()));
						}
						if(random() < 1 + 0.8 * tier) {
							event.getDrops().add(new ItemStack(Material.TOTEM_OF_UNDYING, number()));
						}
					}
					else if(victim.getType() == EntityType.MAGMA_CUBE) {
						if(random() < 5 + tier * 5) {
							event.getDrops().add(new ItemStack(Material.MAGMA_CREAM, number()));
						}
					}
					else if(victim.getType() == EntityType.ELDER_GUARDIAN) {
						if(random() < 50 + 10 * tier) {
							event.getDrops().add(new ItemStack(Material.PRISMARINE_CRYSTALS, number()));
						}
						if(random() < 50 + 10 * tier) {
							event.getDrops().add(new ItemStack(Material.PRISMARINE_SHARD, number()));
						}
					}
					else if(victim.getType() == EntityType.GUARDIAN) {
						if(random() < 10 + 5 * tier) {
							event.getDrops().add(new ItemStack(Material.PRISMARINE_CRYSTALS, number()));
						}
						if(random() < 10 + 5 * tier) {
							event.getDrops().add(new ItemStack(Material.PRISMARINE_SHARD, number()));
						}
					}
					else if(victim.getType() == EntityType.PIG_ZOMBIE) {
						if(random() < 10 + 5 * tier) {
							event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, number()));
						}
						if(random() < 0.5 + 0.5 * tier) {
							event.getDrops().add(new ItemStack(Material.GOLD_INGOT, number()));
						}
					}
					else if(victim.getType() == EntityType.SHULKER) {
						if(random() < 0.3 + 0.3 * tier) {
							event.getDrops().add(new ItemStack(Material.SHULKER_SHELL, number()));
						}
					}
					else if(victim.getType() == EntityType.WITCH) {
						if(random() < 2.5 + 2.5 * tier) {
							event.getDrops().add(new ItemStack(Material.REDSTONE, number()));
						}
						if(random() < 2.5 + 2.5 * tier) {
							event.getDrops().add(new ItemStack(Material.GLOWSTONE, number()));
						}
						if(random() < 2.5 + 2.5 * tier) {
							event.getDrops().add(new ItemStack(Material.STICK, number()));
						}
						if(random() < 2.5 + 2.5 * tier) {
							event.getDrops().add(new ItemStack(Material.GLASS_BOTTLE, number()));
						}
						if(random() < 2.5 + 2.5 * tier) {
							event.getDrops().add(new ItemStack(Material.BROWN_MUSHROOM, number()));
						}
						if(random() < 2.5 + 2.5 * tier) {
							event.getDrops().add(new ItemStack(Material.RED_MUSHROOM, number()));
						}
					}
					else if(victim.getType() == EntityType.WITHER_SKELETON) {
						if(random() < 10 + 5 * tier) {
							event.getDrops().add(new ItemStack(Material.BONE, number()));
						}
						if(random() < 5 + 5 * tier) {
							event.getDrops().add(new ItemStack(Material.COAL, number()));
						}
						if(random() < 0.1 + 0.1 * tier) {
							event.getDrops().add(new ItemStack(Material.WITHER_SKELETON_SKULL, number()));
						}
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
