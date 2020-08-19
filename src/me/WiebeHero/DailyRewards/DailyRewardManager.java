package me.WiebeHero.DailyRewards;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class DailyRewardManager {
	
	private HashMap<UUID, DailyReward> dailyRewardList;
	
	public DailyRewardManager() {
		this.dailyRewardList = new HashMap<UUID, DailyReward>();
	}
	
	public void addDailyReward(UUID uuid) {
		this.dailyRewardList.put(uuid, new DailyReward(uuid));
	}
	
	public void addDailyReward(DailyReward reward) {
		this.dailyRewardList.put(reward.getUUID(), reward);
	}
	
	public void removeDailyReward(DailyReward reward) {
		this.dailyRewardList.remove(reward.getUUID());
	}
	
	public void removeDailyReward(UUID uuid) {
		this.dailyRewardList.remove(uuid);
	}
	
	public boolean containsDailyReward(DailyReward reward) {
		return this.dailyRewardList.containsKey(reward.getUUID());
	}
	
	public boolean containsDailyReward(UUID uuid) {
		return this.dailyRewardList.containsKey(uuid);
	}
	
	public DailyReward getDailyReward(UUID uuid) {
		if(this.dailyRewardList.containsKey(uuid)) {
			return this.dailyRewardList.get(uuid);
		}
		return null;
	}
	
	public String getCurrentDailyDate() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
	}
	
	public String getCurrentMonthlyDate() {
		Calendar cal = Calendar.getInstance();
		return cal.get(cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
	}
	
	public void loadDailys() {
		File f1 =  new File("plugins/CustomEnchantments/DailyRewards.yml");
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
		if(yml.getConfigurationSection("DailyRewards") != null) {
			Set<String> l = yml.getConfigurationSection("DailyRewards").getKeys(false);
			ArrayList<String> list = new ArrayList<String>(l);
			for(int i = 0; i < list.size(); i++) {
				UUID uuid = UUID.fromString(list.get(i));
				String lastDaily = yml.getString("DailyRewards." + uuid + ".Last Daily") == null ? "" : yml.getString("DailyRewards." + uuid + ".Last Daily");
				long lastWeekly = yml.getLong("DailyRewards." + uuid + ".Last Weekly");
				long lastMonthly = yml.getLong("DailyRewards." + uuid + ".Last Monthly");
				ArrayList<Integer> claimedSlots = yml.getIntegerList("DailyRewards." + uuid + ".Claimed Slots") == null ? new ArrayList<Integer>() : new ArrayList<Integer>(yml.getIntegerList("DailyRewards." + uuid + ".Claimed Slots"));
				DailyReward reward = new DailyReward(uuid, lastDaily, lastMonthly, lastWeekly, claimedSlots);
				this.dailyRewardList.put(uuid, reward);
			}
		}
	}
	public void saveDailys() {
		File f1 =  new File("plugins/CustomEnchantments/DailyRewards.yml");
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
		yml.set("DailyRewards", null);
		for(Entry<UUID, DailyReward> entry : this.dailyRewardList.entrySet()) {
			yml.createSection("DailyRewards." + entry.getKey());
			UUID uuid = entry.getKey();
			DailyReward daily = entry.getValue();
			yml.set("DailyRewards." + uuid + ".Last Daily", daily.getLastDaily());
			yml.set("DailyRewards." + uuid + ".Last Weekly", daily.getLastWeekly());
			yml.set("DailyRewards." + uuid + ".Last Monthly", daily.getLastMonthly());
			yml.set("DailyRewards." + uuid + ".Claimed Slots", daily.getClaimedSlots());
		}
		try{
			yml.save(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
	public void saveDailysBackup(String folder) {
		File f1 =  new File("plugins/CustomEnchantments/Data-Backups/" + folder + "/DailyRewards.yml");
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
		yml.set("DailyRewards", null);
		for(Entry<UUID, DailyReward> entry : this.dailyRewardList.entrySet()) {
			yml.createSection("DailyRewards." + entry.getKey());
			UUID uuid = entry.getKey();
			DailyReward daily = entry.getValue();
			yml.set("DailyRewards." + uuid + ".Last Daily", daily.getLastDaily());
			yml.set("DailyRewards." + uuid + ".Last Weekly", daily.getLastWeekly());
			yml.set("DailyRewards." + uuid + ".Last Monthly", daily.getLastMonthly());
			yml.set("DailyRewards." + uuid + ".Claimed Slots", daily.getClaimedSlots());
		}
		try{
			yml.save(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
}
