package me.WiebeHero.LootChest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtinjector.NBTInjector;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class LootChestManager {
	public HashMap<UUID, LootChest> lootChest;
	public LootChestManager() {
		this.lootChest = new HashMap<UUID, LootChest>();
	}
	//---------------------------------------------------------
	//Saving and loading Spawners
	//---------------------------------------------------------
	public void loadLootChests() {
		File f =  new File("plugins/CustomEnchantments/lootConfig.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		try{
			yml.load(f);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if(yml.getConfigurationSection("Loot.Chests") != null) {
			Set<String> set = yml.getConfigurationSection("Loot.Chests").getKeys(false);
			ArrayList<String> list = new ArrayList<String>(set);
			if(list.size() != 0) {
				for(int i = 0; i < list.size(); i++) {
					UUID uuid = UUID.fromString(list.get(i));
					Location loc = (Location) yml.get("Loot.Chests." + uuid + ".Location");
					int tier = yml.getInt("Loot.Chests." + uuid + ".Tier");
					int radius = yml.getInt("Loot.Chests." + uuid + ".Radius");
					LootChest chest = new LootChest(uuid, loc, tier, radius);
					this.add(uuid, chest);
				}
			}
		}
	}
	
	public void saveLootChests() {
		File f =  new File("plugins/CustomEnchantments/lootConfig.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		yml.set("Loot.Chests", null);
		if(!this.getLootList().isEmpty()) {
			for(LootChest loot : this.getLootList().values()) {
				yml.set("Loot.Chests." + loot.getUUID() + ".Location", loot.getLocation());
				yml.set("Loot.Chests." + loot.getUUID() + ".Tier", loot.getTier());
				yml.set("Loot.Chests." + loot.getUUID() + ".Radius", loot.getRadius());
			}
		}
		try{
			yml.save(f);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
	public void startLootSpawning() {
		LootChestManager manager = this;
		new BukkitRunnable() {
			@Override
			public void run() {
				if(CustomEnchantments.getInstance().getShutdownState() == false) {
					for(LootChest loot : lootChest.values()) {
						if(loot != null) {
							int tier = loot.getTier();
		  					Location loc = loot.getLocation();
			  				if(loot.getRadius() == 1) {
					  			if(loc.getBlock().getType() == Material.CHEST) {
					  				Block block = loc.getBlock();
					  				NBTCompound comp = NBTInjector.getNbtData(block.getState());
									comp.setString("LootChest", "");
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
					  				NBTCompound comp = NBTInjector.getNbtData(block.getState());
									comp.setString("LootChest", "");
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
		  						Block temp = manager.checkChest(loot.getLocation().getBlock(), (double)loot.getRadius());
		  						if(temp != null) {
		  							NBTCompound comp = NBTInjector.getNbtData(temp.getState());
									comp.setString("LootChest", "");
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
		  						else {
		  							Location tempLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
		  							tempLoc.add(randomCoords(loot.getRadius()), 0, randomCoords(loot.getRadius()));
		  							for(double i = tempLoc.getY() + 5; i >= tempLoc.getY() - loot.getRadius() * 2; i--) {
		  								tempLoc.add(0, -1, 0);
		  								if(tempLoc.getBlock().getType() == Material.AIR) {
		  									continue;
		  								}
		  								else {
		  									tempLoc.setY(i);
		  									break;
		  								}
		  							}
	  								Block block = tempLoc.getBlock();
	  								NBTCompound comp = NBTInjector.getNbtData(block.getState());
									comp.setString("LootChest", "");
	  								block.setType(Material.CHEST);
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
						}
	  				}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 6000L);
	}
	public LootChest get(UUID uuid) {
		return this.lootChest.get(uuid);
	}
	public boolean contains(UUID uuid) {
		if(this.lootChest.containsKey(uuid)) {
			return true;
		}
		return false;
	}
	public void add(UUID uuid, LootChest p) {
		this.lootChest.put(uuid, p);
	}
	public void remove(UUID uuid) {
		if(this.lootChest.containsKey(uuid)) {
			this.lootChest.remove(uuid);
		}
	}
	public HashMap<UUID, LootChest> getLootList(){
		return this.lootChest;
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
	public ArrayList<Block> getBlocks(Block start, int radius) {
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
	public Block checkChest(Block start, double radius) {
		Block chest = null;
		for(Block block : this.getBlocks(start, (int)radius)) {
			if(block.getType() == Material.CHEST) {
				chest = block;
			}
		}
		return chest;
	}
}
