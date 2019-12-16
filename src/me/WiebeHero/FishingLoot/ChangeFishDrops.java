package me.WiebeHero.FishingLoot;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.WiebeHero.CustomEnchantments.CCT;

public class ChangeFishDrops implements Listener{
	@EventHandler
	public void changeDrop(PlayerFishEvent event) {
		if(event.getCaught() instanceof Item) {
			Item caught = (Item) event.getCaught();
			ItemStack item = caught.getItemStack();
			item.setAmount(1);
			if(random() < 2.50) {
				item.setType(commonCrystal().getType());
				item.setItemMeta(commonCrystal().getItemMeta());
			}
			else if(random() < 2.0) {
				item.setType(rareCrystal().getType());
				item.setItemMeta(rareCrystal().getItemMeta());
			}
			else if(random() < 1.5) {
				item.setType(epicCrystal().getType());
				item.setItemMeta(epicCrystal().getItemMeta());
			}
			else if(random() < 1.0) {
				item.setType(legendaryCrystal().getType());
				item.setItemMeta(legendaryCrystal().getItemMeta());
			}
			else if(random() < 0.5) {
				item.setType(mythicalCrystal().getType());
				item.setItemMeta(mythicalCrystal().getItemMeta());
			}
			else if(random() < 5.0) {
				item.setType(Material.NAUTILUS_SHELL);
			}
			else if(random() < 2.5) {
				item.setType(Material.BLAZE_ROD);
			}
			else if(random() <= 6.0) {
				item.setType(Material.MAGMA_BLOCK);
			}
			else if(random() <= 2.0) {
				item.setType(Material.FISHING_ROD);
			}
			else if(random() <= 6.00) {
				item.setType(Material.ROTTEN_FLESH);
			}
			else if(random() <= 6.00) {
				item.setType(Material.BONE);
			}
			else if(random() <= 5.00) {
				item.setType(Material.STRING);
			}
			else if(random() <= 3.50) {
				item.setType(Material.LEATHER);
			}
			else if(random() <= 2.00) {
				item.setType(Material.WATER_BUCKET);
			}
			else {
				if(random() <= 25) {
					item.setType(Material.COD);
				}
				else if(random() <= 50) {
					item.setType(Material.SALMON);
				}
				else if(random() <= 75) {
					item.setType(Material.TROPICAL_FISH);
				}
				else if(random() <= 100) {
					item.setType(Material.PUFFERFISH);
				}
			}
		}
	}
	public ItemStack commonCrystal() {
		ItemStack i = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new CCT().colorize("&7Common Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new CCT().colorize("&7Really nice rewards!"));
		lore1.add(new CCT().colorize("&7Rarity: Common"));
		m.setLore(lore1);
		i.setItemMeta(m);
		return i;
	}
	public ItemStack rareCrystal() {
		ItemStack i = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new CCT().colorize("&aRare Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new CCT().colorize("&7Really nice rewards!"));
		lore1.add(new CCT().colorize("&7Rarity: Common"));
		m.setLore(lore1);
		i.setItemMeta(m);
		return i;
	}
	public ItemStack epicCrystal() {
		ItemStack i = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new CCT().colorize("&bEpic Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new CCT().colorize("&7Really nice rewards!"));
		lore1.add(new CCT().colorize("&7Rarity: Common"));
		m.setLore(lore1);
		i.setItemMeta(m);
		return i;
	}
	public ItemStack legendaryCrystal() {
		ItemStack i = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new CCT().colorize("&cLegendary Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new CCT().colorize("&7Really nice rewards!"));
		lore1.add(new CCT().colorize("&7Rarity: Common"));
		m.setLore(lore1);
		i.setItemMeta(m);
		return i;
	}
	public ItemStack mythicalCrystal() {
		ItemStack i = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new CCT().colorize("&5Mythical Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new CCT().colorize("&7Really nice rewards!"));
		lore1.add(new CCT().colorize("&7Rarity: Common"));
		m.setLore(lore1);
		i.setItemMeta(m);
		return i;
	}
	public float random() {
		float i = ThreadLocalRandom.current().nextFloat() * 100;
		return i;
	}
}
