package NeededStuff;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class ServerTeleporterClick implements Listener{
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory inv = event.getClickedInventory();
		ItemStack item = event.getCurrentItem();
		if(inv == null) {
			return;
		}
		if(inv.getName().contains(("Server "))) {
			event.setCancelled(true);
			File f =  new File("plugins/CustomEnchantments/worldInv.yml");
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
			if(item.getItemMeta().getDisplayName().contains(ChatColor.stripColor("DF-1"))) {
				Location spawn = Bukkit.getWorld("world").getSpawnLocation();
				player.teleport(spawn);
			}
			try{
				yml.save(f);
	        }
	        catch(IOException e){
	            e.printStackTrace();
	        }
		}
	}
}