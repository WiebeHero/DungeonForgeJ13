package me.WiebeHero.DFShops;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MoneyCreate implements Listener{
	public static HashMap<UUID, Double> moneyList = new HashMap<UUID, Double>();
	@EventHandler
	public void moneyCreateJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(!moneyList.containsKey(player.getUniqueId())) {
			moneyList.put(player.getUniqueId(), 1500.00);
		}
	}
	public void loadMoney(YamlConfiguration yml, File f) {
		try{
			yml.load(f);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		Set<String> set = yml.getConfigurationSection("List").getKeys(false);
		ArrayList<String> uuids = new ArrayList<String>(set);
		for(int i = 0; i < uuids.size(); i++) {
			UUID uuid = UUID.fromString(uuids.get(i));
			double money = yml.getDouble("List." + uuid);
			moneyList.put(uuid, money);
		}
	}
	public void saveMoney(YamlConfiguration yml, File f) {
		try{
			yml.load(f);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		for(Entry<UUID, Double> entry : moneyList.entrySet()) {
			yml.createSection("List." + entry.getKey());
			yml.set("List." + entry.getKey(), entry.getValue());
		}
		try{
			yml.save(f);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
	public HashMap<UUID, Double> getMoneyList(){
		return MoneyCreate.moneyList;
	}
}
