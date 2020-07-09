package me.WiebeHero.FlyTicket;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class FlyTicketManager {
	
	private HashMap<UUID, FlyTicket> flyTicketList;
	
	public FlyTicketManager() {
		this.flyTicketList = new HashMap<UUID, FlyTicket>();
	}
	
	public void addFlyTicket(UUID uuid) {
		this.flyTicketList.put(uuid, new FlyTicket(uuid));
	}
	
	public void addFlyTicket(FlyTicket ticket) {
		this.flyTicketList.put(ticket.getOwner(), ticket);
	}
	
	public boolean containsFlyTicket(UUID uuid) {
		return this.flyTicketList.containsKey(uuid);
	}
	
	public boolean containsFlyTicket(FlyTicket ticket) {
		return this.flyTicketList.containsKey(ticket.getOwner());
	}
	
	public void removeFlyTicket(UUID uuid) {
		if(this.containsFlyTicket(uuid)) {
			this.flyTicketList.remove(uuid);
		}
	}
	
	public void removeFlyTicket(FlyTicket ticket) {
		if(this.containsFlyTicket(ticket.getOwner())) {
			this.flyTicketList.remove(ticket.getOwner());
		}
	}
	
	public FlyTicket getFlyTicket(UUID uuid) {
		if(this.containsFlyTicket(uuid)) {
			return this.flyTicketList.get(uuid);
		}
		return null;
	}
	
	public void loadFlyTickets() {
		File f1 =  new File("plugins/CustomEnchantments/FlyTickets.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f1);
		try{
			yml.load(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if(yml.getConfigurationSection("FlyTickets") != null) {
			Set<String> l = yml.getConfigurationSection("FlyTickets").getKeys(false);
			ArrayList<String> list = new ArrayList<String>(l);
			for(int i = 0; i < list.size(); i++) {
				UUID uuid = UUID.fromString(list.get(i));
				long time = yml.getLong("FlyTickets." + uuid + ".End");
				if(time > System.currentTimeMillis()) {
					this.flyTicketList.put(uuid, new FlyTicket(uuid, time));
					long remaining = (time - System.currentTimeMillis()) / 50;
					new BukkitRunnable() {
						public void run() {
							if(flyTicketList.containsKey(uuid)) {
								flyTicketList.remove(uuid);
							}
						}
					}.runTaskLaterAsynchronously(CustomEnchantments.getInstance(), remaining);
				}
			}
		}
	}
	public void saveFlyTickets() {
		File f1 =  new File("plugins/CustomEnchantments/FlyTickets.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f1);
		try{
			yml.load(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		yml.set("FlyTickets", null);
		for(Entry<UUID, FlyTicket> entry : this.flyTicketList.entrySet()) {
			yml.createSection("FlyTickets." + entry.getKey());
			UUID uuid = entry.getKey();
			FlyTicket ticket = entry.getValue();
			yml.set("FlyTickets." + uuid + ".End", ticket.getFlyTime());
		}
		try{
			yml.save(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
}
