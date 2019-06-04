package NeededStuff;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MoneyCreation implements Listener{
	@EventHandler
	public void moneyGen(PlayerJoinEvent event) {
		File f =  new File("plugins/CustomEnchantments/moneyConfig.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		Player player = event.getPlayer();
		String id = player.getUniqueId().toString();
		try{
			yml.load(f);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if(!player.hasPlayedBefore()) {
			yml.createSection("List." + id);
			yml.set("List." + id + ".Money", 1000);
			player.sendMessage("works1");
		}
		else if(yml.getConfigurationSection("List") == null) {
			yml.createSection("List." + id);
			yml.set("List." + id + ".Money", 1000);
			player.sendMessage("works1");
		}
		else if(!yml.getConfigurationSection("List").getKeys(false).contains(id)){
			yml.createSection("List." + id);
			yml.set("List." + id + ".Money", 1000);
			player.sendMessage("works1");
		}
		try{
			yml.save(f);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
}
