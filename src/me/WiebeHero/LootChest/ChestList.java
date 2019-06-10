package me.WiebeHero.LootChest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class ChestList implements Listener {
	public void lootChest() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(CustomEnchantments.getInstance().getShutdownState() == false) {
					for(int counter = 1; counter < SetChest.getChestTierList().size() + 1; counter++) {
						int tier = SetChest.getChestTierList().get(counter);
	  					Location loc = SetChest.getChestLocationList().get(counter);
	  					loc.setWorld(Bukkit.getWorld(SetChest.getChestWorldsList().get(counter)));
		  				if(SetChest.getChestRadiusList().get(counter) == 1) {
				  			if(loc.getBlock().getType() == Material.CHEST) {
				  				Block block = loc.getBlock();
					  			Chest chest = (Chest) block.getState();
				  				Inventory inv = chest.getBlockInventory();
				  				inv.clear();
				  				for(int i = 0; i <= tieredSlots(tier) + tier + 1; i++) {
				  					int slot = randomSlot();
				  					if(inv.getItem(slot) != null && inv.getItem(slot).getType() == Material.AIR) {
				  						i--;
				  					}
				  					if(tier == 1) {
				  						inv.setItem(slot, LootRewards.getTier1List().get(rewards(tier)));
				  					}
				  					if(tier == 2) {
				  						inv.setItem(slot, LootRewards.getTier2List().get(rewards(tier)));
				  					}
				  					if(tier == 3) {
				  						inv.setItem(slot, LootRewards.getTier3List().get(rewards(tier)));
				  					}
				  					if(tier == 4) {
				  						inv.setItem(slot, LootRewards.getTier4List().get(rewards(tier)));
					  				}
				  				}
				  			}
				  			else {
				  				loc.getBlock().setType(Material.CHEST);
				  				Block block = loc.getBlock();
					  			Chest chest = (Chest) block.getState();
				  				Inventory inv = chest.getBlockInventory();
				  				inv.clear();
				  				for(int i = 0; i <= tieredSlots(tier) + tier + 1; i++) {
				  					int slot = randomSlot();
				  					if(inv.getItem(slot) != null && inv.getItem(slot).getType() == Material.AIR) {
				  						i--;
				  					}
				  					if(tier == 1) {
				  						inv.setItem(slot, LootRewards.getTier1List().get(rewards(tier)));
				  					}
				  					if(tier == 2) {
				  						inv.setItem(slot, LootRewards.getTier2List().get(rewards(tier)));
				  					}
				  					if(tier == 3) {
				  						inv.setItem(slot, LootRewards.getTier3List().get(rewards(tier)));
				  					}
				  					if(tier == 4) {
				  						inv.setItem(slot, LootRewards.getTier4List().get(rewards(tier)));
					  				}
				  					
				  				}
				  			}
			  			}
		  				else {
		  					if(ChestList.checkChest(SetChest.getChestLocationList().get(counter).getBlock(), SetChest.getChestRadiusList().get(counter)) == null) {
		  						Location tempLoc = new Location(loc.getWorld(), loc.getX(), loc.getY() + SetChest.getChestRadiusList().get(counter), loc.getZ());
		  						tempLoc.setX(tempLoc.getX() + randomCoords(SetChest.getChestRadiusList().get(counter)));
		  						tempLoc.setZ(tempLoc.getZ() + randomCoords(SetChest.getChestRadiusList().get(counter)));
		  						int count = 0;
		  						for(double y = tempLoc.getY(); y > loc.getY() - SetChest.getChestRadiusList().get(counter);) {
		  							y--;
		  							tempLoc.setY(y);
		  							if(tempLoc.getBlock().getType() != Material.AIR) {
		  								tempLoc.setY(y + 1.0);
		  								if(tempLoc.getBlock().getType() == Material.AIR) {
		  									tempLoc.getBlock().setType(Material.CHEST);
		  					  				Block block = tempLoc.getBlock();
		  						  			Chest chest = (Chest) block.getState();
		  					  				Inventory inv = chest.getBlockInventory();
		  					  				inv.clear();
		  					  				for(int i = 0; i <= tieredSlots(tier) + tier + 1; i++) {
		  					  					int slot = randomSlot();
		  					  					if(inv.getItem(slot) != null && inv.getItem(slot).getType() == Material.AIR) {
		  					  						i--;
		  					  					}
		  					  					if(tier == 1) {
		  					  						inv.setItem(slot, LootRewards.getTier1List().get(rewards(tier)));
		  					  					}
		  					  					if(tier == 2) {
		  					  						inv.setItem(slot, LootRewards.getTier2List().get(rewards(tier)));
		  					  					}
		  					  					if(tier == 3) {
		  					  						inv.setItem(slot, LootRewards.getTier3List().get(rewards(tier)));
		  					  					}
		  					  					if(tier == 4) {
		  					  						inv.setItem(slot, LootRewards.getTier4List().get(rewards(tier)));
		  						  				}
		  					  				}
		  					  				break;
		  								}
		  								else {
		  									tempLoc = new Location(loc.getWorld(), loc.getX(), loc.getY() + SetChest.getChestRadiusList().get(counter), loc.getZ());
		  									tempLoc.setX(tempLoc.getX() + randomCoords(SetChest.getChestRadiusList().get(counter)));
		  			  						tempLoc.setZ(tempLoc.getZ() + randomCoords(SetChest.getChestRadiusList().get(counter)));
		  			  						y = tempLoc.getY();
		  			  						count++;
		  			  						if(count == 5) {
		  			  							break;
		  			  						}
		  								}
		  							}
		  						}
		  					}
		  					else {
		  						Block temp = ChestList.checkChest(SetChest.getChestLocationList().get(counter).getBlock(), SetChest.getChestRadiusList().get(counter));
		  						Chest chest = (Chest) temp.getState();
				  				Inventory inv = chest.getBlockInventory();
				  				inv.clear();
				  				for(int i = 0; i <= tieredSlots(tier) + tier + 1; i++) {
				  					int slot = randomSlot();
				  					if(inv.getItem(slot) != null && inv.getItem(slot).getType() == Material.AIR) {
				  						i--;
				  					}
				  					if(tier == 1) {
				  						inv.setItem(slot, LootRewards.getTier1List().get(rewards(tier)));
				  					}
				  					if(tier == 2) {
				  						inv.setItem(slot, LootRewards.getTier2List().get(rewards(tier)));
				  					}
				  					if(tier == 3) {
				  						inv.setItem(slot, LootRewards.getTier3List().get(rewards(tier)));
				  					}
				  					if(tier == 4) {
				  						inv.setItem(slot, LootRewards.getTier4List().get(rewards(tier)));
					  				}
				  				}
		  					}
		  				}
	  				}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 6000L);
	}
	public int randomSlot() {
		int i = new Random().nextInt(26);
		return i;
	}
	public int rewards(int tier) {
		int rewardsR1 = 0;
		if(tier == 1) {
			rewardsR1 = new Random().nextInt(LootRewards.getTier1List().size());
		}
		if(tier == 2) {
			rewardsR1 = new Random().nextInt(LootRewards.getTier2List().size());
		}
		if(tier == 3) {
			rewardsR1 = new Random().nextInt(LootRewards.getTier3List().size());
		}
		if(tier == 4) {
			rewardsR1 = new Random().nextInt(LootRewards.getTier4List().size());
		}
		return rewardsR1;
	}
	public int tieredSlots(int slotAmount) {
		int yeet = new Random().nextInt(slotAmount);
		return yeet;
	}
	public int randomCoords(int rad) {
		int i = new Random().nextInt(rad + 1 + rad) - rad;
		return i;
	}
	public static ArrayList<Block> getBlocks(Block start, int radius) {
		int iterations = (radius * 2) + 1;
		List<Block> blocks = new ArrayList<Block>(iterations * iterations * iterations);
	    for (int x = -radius; x <= radius; x++) {
	        for (int y = -radius; y <= radius; y++) {
	            for (int z = -radius; z <= radius; z++) {
	                blocks.add(start.getRelative(x, y, z));
	            }
	        }
	    }
	    return (ArrayList<Block>) blocks;
	}
	public static Block checkChest(Block start, double radius) {
		Block chest = null;
		for(Block block : ChestList.getBlocks(start, (int)radius)) {
			if(block.getType() == Material.CHEST) {
				chest = block;
			}
		}
		return chest;
	}
}
