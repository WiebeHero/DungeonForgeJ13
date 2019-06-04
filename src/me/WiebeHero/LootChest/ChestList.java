package me.WiebeHero.LootChest;

import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class ChestList extends LootRewards implements Listener {
	public String getLast(Set<String> set) {
        return set.stream().skip(set.stream().count() - 1).findFirst().get();
    }	
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void lootChest() {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(int counter = 1; counter < SetChest.getChestTierList().size(); counter++) {
					int tier = SetChest.getChestTierList().get(counter);
  					Location loc = SetChest.getChestLocationList().get(counter);
  					loc.setWorld(Bukkit.getWorld(SetChest.getChestWorldsList().get(counter)));
		  			if(loc.getBlock() != null && loc.getBlock().getType() != Material.AIR) {
			  			if(loc.getBlock().getType() == Material.CHEST) {
			  				Block block = loc.getBlock();
				  			Chest chest = (Chest) block.getState();
			  				Inventory inv = chest.getBlockInventory();
			  				inv.clear();
			  				for(int i = 0; i < tieredSlots(tier + 1); i++) {
			  					int slot = randomSlot();
			  					if(inv.getItem(slot) != null && inv.getItem(slot).getType() == Material.AIR) {
			  						i--;
			  					}
			  					inv.setItem(randomSlot(), rewards1.get(rewards(tier)));
			  				}
			  			}
		  			}
				}
			}
		}.runTaskTimer(plugin, 0L, 100L);
	}
	public int randomSlot() {
		int i = new Random().nextInt(26);
		return i;
	}
	public int rewards(int tier) {
		int rewardsR1 = 0;
		if(tier == 1) {
			new Random().nextInt(LootRewards.rewards1.size());
		}
		if(tier == 2) {
			new Random().nextInt(LootRewards.rewards2.size());
		}
		if(tier == 3) {
			new Random().nextInt(LootRewards.rewards3.size());
		}
		if(tier == 4) {
			new Random().nextInt(LootRewards.rewards4.size());
		}
		return rewardsR1;
	}
	public int tieredSlots(int slotAmount) {
		int yeet = new Random().nextInt(slotAmount);
		return yeet;
	}
}
