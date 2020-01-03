package me.WiebeHero.Moderation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class PunishManager {
	public HashMap<UUID, Punish> punishList;
	public PunishManager() {
		this.punishList = new HashMap<UUID, Punish>();
	}
	public void loadPunishList() {
		File f = new File("plugins/CustomEnchantments/modConfig.yml");
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
		if(yml.getConfigurationSection("Mod.Punishments") != null) {
			Bukkit.getConsoleSender().sendMessage("Its not null");
			Set<String> set = yml.getConfigurationSection("Mod.Punishments").getKeys(false);
			ArrayList<String> uuids = new ArrayList<String>(set);
			for(int i = 0; i < uuids.size(); i++) {
				UUID uuid = UUID.fromString(uuids.get(i));
				
				ArrayList<String> banReason = (ArrayList<String>) yml.getStringList("Mod.Punishments." + uuid + ".Ban Data.Reason");
				ArrayList<String> muteReason = (ArrayList<String>) yml.getStringList("Mod.Punishments." + uuid + ".Mute Data.Reason");
				ArrayList<String> warnReason = (ArrayList<String>) yml.getStringList("Mod.Punishments." + uuid + ".Warn Data.Reason");
				
				ArrayList<String> bannedBy = (ArrayList<String>) yml.getStringList("Mod.Punishments." + uuid + ".Ban Data.By");
				ArrayList<String> mutedBy = (ArrayList<String>) yml.getStringList("Mod.Punishments." + uuid + ".Mute Data.By");
				ArrayList<String> warnedBy = (ArrayList<String>) yml.getStringList("Mod.Punishments." + uuid + ".Warn Data.By");
				
				Long banTime = yml.getLong("Mod.Punishments." + uuid + ".Ban Data.Temp");
				Long muteTime = yml.getLong("Mod.Punishments." + uuid + ".Mute Data.Temp");
				boolean banPerm = yml.getBoolean("Mod.Punishments." + uuid + ".Ban Data.Perm");
				boolean mutePerm = yml.getBoolean("Mod.Punishments." + uuid + ".Mute Data.Perm");
				
				Punish pun = new Punish(uuid);
				pun.setBanReason(banReason);
				pun.setMuteReason(muteReason);
				pun.setWarnReason(warnReason);
				pun.setBannedBy(bannedBy);
				pun.setMutedBy(mutedBy);
				pun.setWarnedBy(warnedBy);
				pun.setBanTime(banTime);
				pun.setMuteTime(muteTime);
				pun.setBanPerm(banPerm);
				pun.setMutePerm(mutePerm);
				this.punishList.put(uuid, pun);
			}
		}
	}
	public void savePunishList() {
		File f =  new File("plugins/CustomEnchantments/modConfig.yml");
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
		yml.set("Mod.Punishments", null);
		for(Entry<UUID, Punish> entry : this.punishList.entrySet()) {
			if(entry != null && entry.getKey() != null && entry.getValue() != null && this.contains(entry.getKey())) {
				UUID uuid = entry.getKey();
				Punish pun = this.punishList.get(uuid);
				yml.createSection("Mod.Punishments." + uuid);
				yml.set("Mod.Punishments." + uuid + ".Mute Data.Perm", pun.getMutePerm());
				yml.set("Mod.Punishments." + uuid + ".Mute Data.Temp", pun.getMuteTime());
				yml.set("Mod.Punishments." + uuid + ".Ban Data.Perm", pun.getBanPerm());
				yml.set("Mod.Punishments." + uuid + ".Ban Data.Temp", pun.getBanTime());
				yml.set("Mod.Punishments." + uuid + ".Mute Data.Reason", pun.getMuteReasonsList());
				yml.set("Mod.Punishments." + uuid + ".Ban Data.Reason", pun.getBanReasonsList());
				yml.set("Mod.Punishments." + uuid + ".Warn Data.Reason", pun.getWarnReasonsList());
				yml.set("Mod.Punishments." + uuid + ".Ban Data.By", pun.getBannedByList());
				yml.set("Mod.Punishments." + uuid + ".Mute Data.By", pun.getMutedByList());
				yml.set("Mod.Punishments." + uuid + ".Warn Data.By", pun.getWarnedByList());
			}
		}
		try{
			yml.save(f);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
	public Punish get(UUID uuid) {
		return this.punishList.get(uuid);
	}
	public boolean contains(UUID uuid) {
		if(this.punishList.containsKey(uuid)) {
			return true;
		}
		return false;
	}
	public void add(UUID uuid, Punish p) {
		this.punishList.put(uuid, p);
	}
	public void remove(UUID uuid) {
		if(this.punishList.containsKey(uuid)) {
			this.punishList.remove(uuid);
		}
	}
}
