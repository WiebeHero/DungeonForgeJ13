package me.WiebeHero.LootChest;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.scheduler.BukkitRunnable;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtinjector.NBTInjector;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class ChestEvents implements Listener{
	@EventHandler
    public void onChestCloses(InventoryCloseEvent event){
        if(event.getInventory().getType() == InventoryType.CHEST) {
        	if(event.getInventory().getLocation() != null) {
	            Block block = event.getInventory().getLocation().getBlock();
	            if(block != null) {
	            	if(block.getWorld().getName().equalsIgnoreCase("DFWarzone-1")) {
	            		BlockState bs = block.getState();
			            if(bs instanceof Chest) {
			            	Chest chest = (Chest) bs;
			            	NBTCompound comp = NBTInjector.getNbtData(block.getState());
							if(comp.hasKey("LootChest")) {
								new BukkitRunnable() {
		                			public void run() {
		                				chest.getBlockInventory().clear();
		                        		chest.getBlock().setType(Material.AIR);
		                        		chest.getLocation().getWorld().spawnParticle(Particle.CLOUD, chest.getLocation().getX() + 0.5, chest.getLocation().getY() + 0.5, chest.getLocation().getZ() + 0.5, 45, 0.0000001, 0.0000001, 0.0000001, 0.15);
		                        		chest.getWorld().playSound(chest.getLocation(), Sound.ENTITY_CHICKEN_EGG, 2.0F, 1.0F);
		                			}
		                		}.runTaskLater(CustomEnchantments.getInstance(), 15L);
							}
			            }
	            	}
	            }
        	}
        }
    }
}
