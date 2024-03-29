package me.WiebeHero.RankedPlayerPackage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.MethodLuck;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Kit;
import net.luckperms.api.model.user.User;

public class RankedManager {
	private MethodLuck lp;
	private HashMap<UUID, RankedPlayer> ranked;
	public RankedManager(MethodLuck lp) {
		this.lp = lp;
		this.ranked = new HashMap<UUID, RankedPlayer>();
	}
	@SuppressWarnings("unchecked")
	public void loadKitCooldowns() {
		File f1 =  new File("plugins/CustomEnchantments/KitCooldowns.yml");
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
		if(yml.getConfigurationSection("Kits.Cooldowns") != null) {
			Set<String> l = yml.getConfigurationSection("Kits.Cooldowns").getKeys(false);
			ArrayList<String> list = new ArrayList<String>(l);
			for(int i = 0; i < list.size(); i++) {
				UUID uuid = UUID.fromString(list.get(i));
				RankedPlayer rPlayer = new RankedPlayer();
				rPlayer.addKitCooldown(Kit.BRONZE, yml.getLong("Kits.Cooldowns." + uuid + ".Bronze"));
				rPlayer.addKitCooldown(Kit.SILVER, yml.getLong("Kits.Cooldowns." + uuid + ".Silver"));
				rPlayer.addKitCooldown(Kit.GOLD, yml.getLong("Kits.Cooldowns." + uuid + ".Gold"));
				rPlayer.addKitCooldown(Kit.PLATINUM, yml.getLong("Kits.Cooldowns." + uuid + ".Platinum"));
				rPlayer.addKitCooldown(Kit.DIAMOND, yml.getLong("Kits.Cooldowns." + uuid + ".Diamond"));
				rPlayer.addKitCooldown(Kit.EMERALD, yml.getLong("Kits.Cooldowns." + uuid + ".Emerald"));
				rPlayer.addKitCooldown(Kit.HELPER, yml.getLong("Kits.Cooldowns." + uuid + ".Helper"));
				rPlayer.addKitCooldown(Kit.HELPER_PLUS, yml.getLong("Kits.Cooldowns." + uuid + ".Helper_Plus"));
				rPlayer.addKitCooldown(Kit.MOD, yml.getLong("Kits.Cooldowns." + uuid + ".Mod"));
				rPlayer.addKitCooldown(Kit.HEAD_MOD, yml.getLong("Kits.Cooldowns." + uuid + ".Head_Mod"));
				rPlayer.addKitCooldown(Kit.ADMIN, yml.getLong("Kits.Cooldowns." + uuid + ".Admin"));
				rPlayer.addKitCooldown(Kit.HEAD_ADMIN, yml.getLong("Kits.Cooldowns." + uuid + ".Head_Admin"));
				rPlayer.addKitCooldown(Kit.MANAGER, yml.getLong("Kits.Cooldowns." + uuid + ".Manager"));
				rPlayer.addKitCooldown(Kit.OWNER, yml.getLong("Kits.Cooldowns." + uuid + ".Owner"));
				rPlayer.addKitCooldown(Kit.USER, yml.getLong("Kits.Cooldowns." + uuid + ".User"));
				rPlayer.addKitCooldown(Kit.RAID, yml.getLong("Kits.Cooldowns." + uuid + ".Raid.Cooldown"));
				rPlayer.setKitUnlock(Kit.RAID, yml.getBoolean("Kits.Cooldowns." + uuid + ".Raid.Unlock"));
				rPlayer.addKitCooldown(Kit.POTION, yml.getLong("Kits.Cooldowns." + uuid + ".Potion.Cooldown"));
				rPlayer.setKitUnlock(Kit.POTION, yml.getBoolean("Kits.Cooldowns." + uuid + ".Potion.Unlock"));
				rPlayer.addKitCooldown(Kit.CRYSTAL, yml.getLong("Kits.Cooldowns." + uuid + ".Crystal.Cooldown"));
				rPlayer.setKitUnlock(Kit.CRYSTAL, yml.getBoolean("Kits.Cooldowns." + uuid + ".Crystal.Unlock"));
				rPlayer.addKitCooldown(Kit.BUILD, yml.getLong("Kits.Cooldowns." + uuid + ".Build.Cooldown"));
				rPlayer.setKitUnlock(Kit.BUILD, yml.getBoolean("Kits.Cooldowns." + uuid + ".Build.Unlock"));
				rPlayer.addKitCooldown(Kit.FLIGHT, yml.getLong("Kits.Cooldowns." + uuid + ".Flight.Cooldown"));
				rPlayer.setKitUnlock(Kit.FLIGHT, yml.getBoolean("Kits.Cooldowns." + uuid + ".Flight.Unlock"));
				rPlayer.addKitCooldown(Kit.SUPPLIER, yml.getLong("Kits.Cooldowns." + uuid + ".Supplier.Cooldown"));
				rPlayer.setKitUnlock(Kit.SUPPLIER, yml.getBoolean("Kits.Cooldowns." + uuid + ".Supplier.Unlock"));
				rPlayer.setGiftCooldown(yml.getLong("Kits.Cooldowns." + uuid + ".Gift Cooldown"));
				rPlayer.setFreeRankCooldown(yml.getLong("Kits.Cooldowns." + uuid + ".Free Rank Command"));
				long realTime = yml.getLong("Kits.Cooldowns." + uuid + ".Free Bronze Time");
				long bronzeTime = realTime - System.currentTimeMillis();
				if(bronzeTime >= 0L) {
					rPlayer.setBronzeTime(realTime);
					BukkitTask task = new BukkitRunnable() {
						public void run() {
							User user = lp.loadUser(uuid);
							if(user != null) {
								if(lp.containsParrent(user, "bronze")) {
									lp.removeParent(user, "bronze");
									rPlayer.setBronzeTime(0L);
									Player p = Bukkit.getPlayer(uuid);
									if(p != null) {
										p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour free &6Bronze &crank rank out! If you want to have this rank permanently. Visit: dungeonforge.eu/shop/"));
									}
								}
							}
						}
					}.runTaskLater(CustomEnchantments.getInstance(), bronzeTime / 50L);
					rPlayer.setTask(task);
				}
				ItemStack[] content = new ItemStack[0];
				if(yml.get("Kits.Cooldowns." + uuid + ".PlayerVaults") != null) {
					content = ((List<ItemStack>) yml.get("Kits.Cooldowns." + uuid + ".PlayerVaults")).toArray(new ItemStack[0]);
				}
				rPlayer.setStackList(content);
				this.ranked.put(uuid, rPlayer);
			}
		}
	}
	public void saveKitCooldowns() {
		File f1 =  new File("plugins/CustomEnchantments/KitCooldowns.yml");
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
		yml.set("Kits.Cooldowns", null);
		yml.set("PlayerVaults", null);
		for(Entry<UUID, RankedPlayer> entry : this.ranked.entrySet()) {
			yml.createSection("Kits.Cooldowns." + entry.getKey());
			UUID uuid = entry.getKey();
			RankedPlayer rPlayer = entry.getValue();
			yml.set("Kits.Cooldowns." + uuid, null);
			if(rPlayer.hasKitCooldown(Kit.BRONZE)) {
				if(rPlayer.getKitCooldown(Kit.BRONZE) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Bronze", rPlayer.getKitCooldown(Kit.BRONZE));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.SILVER)) {
				if(rPlayer.getKitCooldown(Kit.SILVER) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Silver", rPlayer.getKitCooldown(Kit.SILVER));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.GOLD)) {
				if(rPlayer.getKitCooldown(Kit.GOLD) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Gold", rPlayer.getKitCooldown(Kit.GOLD));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.PLATINUM)) {
				if(rPlayer.getKitCooldown(Kit.PLATINUM) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Platinum", rPlayer.getKitCooldown(Kit.PLATINUM));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.DIAMOND)) {
				if(rPlayer.getKitCooldown(Kit.DIAMOND) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Diamond", rPlayer.getKitCooldown(Kit.DIAMOND));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.EMERALD)) {
				if(rPlayer.getKitCooldown(Kit.EMERALD) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Emerald", rPlayer.getKitCooldown(Kit.EMERALD));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.HELPER)) {
				if(rPlayer.getKitCooldown(Kit.HELPER) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Helper", rPlayer.getKitCooldown(Kit.HELPER));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.HELPER_PLUS)) {
				if(rPlayer.getKitCooldown(Kit.HELPER_PLUS) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Helper_Plus", rPlayer.getKitCooldown(Kit.HELPER_PLUS));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.MOD)) {
				if(rPlayer.getKitCooldown(Kit.MOD) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Mod", rPlayer.getKitCooldown(Kit.MOD));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.HEAD_MOD)) {
				if(rPlayer.getKitCooldown(Kit.HEAD_MOD) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Head_Mod", rPlayer.getKitCooldown(Kit.HEAD_MOD));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.ADMIN)) {
				if(rPlayer.getKitCooldown(Kit.ADMIN) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Admin", rPlayer.getKitCooldown(Kit.ADMIN));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.HEAD_ADMIN)) {
				if(rPlayer.getKitCooldown(Kit.HEAD_ADMIN) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Head_Admin", rPlayer.getKitCooldown(Kit.HEAD_ADMIN));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.MANAGER)) {
				if(rPlayer.getKitCooldown(Kit.MANAGER) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Manager", rPlayer.getKitCooldown(Kit.MANAGER));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.OWNER)) {
				if(rPlayer.getKitCooldown(Kit.OWNER) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Owner", rPlayer.getKitCooldown(Kit.OWNER));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.USER)) {
				if(rPlayer.getKitCooldown(Kit.USER) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".User", rPlayer.getKitCooldown(Kit.USER));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.RAID)) {
				if(rPlayer.getKitCooldown(Kit.RAID) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Raid.Cooldown", rPlayer.getKitCooldown(Kit.RAID));
				}
			}
			if(rPlayer.hasKitUnlock(Kit.RAID)) {
				if(rPlayer.getKitUnlock(Kit.RAID) == true) {
					yml.set("Kits.Cooldowns." + uuid + ".Raid.Unlock", rPlayer.getKitUnlock(Kit.RAID));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.POTION)) {
				if(rPlayer.getKitCooldown(Kit.POTION) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Potion.Cooldown", rPlayer.getKitCooldown(Kit.POTION));
				}
			}
			if(rPlayer.hasKitUnlock(Kit.POTION)) {
				if(rPlayer.getKitUnlock(Kit.POTION) == true) {
					yml.set("Kits.Cooldowns." + uuid + ".Potion.Unlock", rPlayer.getKitUnlock(Kit.POTION));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.BUILD)) {
				if(rPlayer.getKitCooldown(Kit.BUILD) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Build.Cooldown", rPlayer.getKitCooldown(Kit.BUILD));
				}
			}
			if(rPlayer.hasKitUnlock(Kit.BUILD)) {
				if(rPlayer.getKitUnlock(Kit.BUILD) == true) {
					yml.set("Kits.Cooldowns." + uuid + ".Build.Unlock", rPlayer.getKitUnlock(Kit.BUILD));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.CRYSTAL)) {
				if(rPlayer.getKitCooldown(Kit.CRYSTAL) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Crystal.Cooldown", rPlayer.getKitCooldown(Kit.CRYSTAL));
				}
			}
			if(rPlayer.hasKitUnlock(Kit.CRYSTAL)) {
				if(rPlayer.getKitUnlock(Kit.CRYSTAL) == true) {
					yml.set("Kits.Cooldowns." + uuid + ".Crystal.Unlock", rPlayer.getKitUnlock(Kit.CRYSTAL));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.FLIGHT)) {
				if(rPlayer.getKitCooldown(Kit.FLIGHT) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Flight.Cooldown", rPlayer.getKitCooldown(Kit.CRYSTAL));
				}
			}
			if(rPlayer.hasKitUnlock(Kit.FLIGHT)) {
				if(rPlayer.getKitUnlock(Kit.FLIGHT) == true) {
					yml.set("Kits.Cooldowns." + uuid + ".Flight.Unlock", rPlayer.getKitUnlock(Kit.CRYSTAL));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.SUPPLIER)) {
				if(rPlayer.getKitCooldown(Kit.SUPPLIER) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Supplier.Cooldown", rPlayer.getKitCooldown(Kit.SUPPLIER));
				}
			}
			if(rPlayer.hasKitUnlock(Kit.SUPPLIER)) {
				if(rPlayer.getKitUnlock(Kit.SUPPLIER) == true) {
					yml.set("Kits.Cooldowns." + uuid + ".Supplier.Unlock", rPlayer.getKitUnlock(Kit.SUPPLIER));
				}
			}
			yml.set("Kits.Cooldowns." + uuid + ".Gift Cooldown", rPlayer.getGiftCooldown());
			yml.set("Kits.Cooldowns." + uuid + ".Free Rank Command", rPlayer.getFreeRankCooldown());
			yml.set("Kits.Cooldowns." + uuid + ".Free Bronze Time", rPlayer.getBronzeTime());
			yml.set("Kits.Cooldowns." + uuid + ".PlayerVaults", rPlayer.getStackList());
		}
		try{
			yml.save(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
	public void saveKitCooldownsBackup(String folder) {
		File f1 =  new File("plugins/CustomEnchantments/Data-Backups/" + folder + "/KitCooldowns.yml");
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
		yml.set("Kits.Cooldowns", null);
		yml.set("PlayerVaults", null);
		for(Entry<UUID, RankedPlayer> entry : this.ranked.entrySet()) {
			yml.createSection("Kits.Cooldowns." + entry.getKey());
			UUID uuid = entry.getKey();
			RankedPlayer rPlayer = entry.getValue();
			yml.set("Kits.Cooldowns." + uuid, null);
			if(rPlayer.hasKitCooldown(Kit.BRONZE)) {
				if(rPlayer.getKitCooldown(Kit.BRONZE) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Bronze", rPlayer.getKitCooldown(Kit.BRONZE));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.SILVER)) {
				if(rPlayer.getKitCooldown(Kit.SILVER) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Silver", rPlayer.getKitCooldown(Kit.SILVER));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.GOLD)) {
				if(rPlayer.getKitCooldown(Kit.GOLD) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Gold", rPlayer.getKitCooldown(Kit.GOLD));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.PLATINUM)) {
				if(rPlayer.getKitCooldown(Kit.PLATINUM) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Platinum", rPlayer.getKitCooldown(Kit.PLATINUM));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.DIAMOND)) {
				if(rPlayer.getKitCooldown(Kit.DIAMOND) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Diamond", rPlayer.getKitCooldown(Kit.DIAMOND));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.EMERALD)) {
				if(rPlayer.getKitCooldown(Kit.EMERALD) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Emerald", rPlayer.getKitCooldown(Kit.EMERALD));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.HELPER)) {
				if(rPlayer.getKitCooldown(Kit.HELPER) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Helper", rPlayer.getKitCooldown(Kit.HELPER));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.HELPER_PLUS)) {
				if(rPlayer.getKitCooldown(Kit.HELPER_PLUS) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Helper_Plus", rPlayer.getKitCooldown(Kit.HELPER_PLUS));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.MOD)) {
				if(rPlayer.getKitCooldown(Kit.MOD) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Mod", rPlayer.getKitCooldown(Kit.MOD));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.HEAD_MOD)) {
				if(rPlayer.getKitCooldown(Kit.HEAD_MOD) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Head_Mod", rPlayer.getKitCooldown(Kit.HEAD_MOD));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.ADMIN)) {
				if(rPlayer.getKitCooldown(Kit.ADMIN) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Admin", rPlayer.getKitCooldown(Kit.ADMIN));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.HEAD_ADMIN)) {
				if(rPlayer.getKitCooldown(Kit.HEAD_ADMIN) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Head_Admin", rPlayer.getKitCooldown(Kit.HEAD_ADMIN));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.MANAGER)) {
				if(rPlayer.getKitCooldown(Kit.MANAGER) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Manager", rPlayer.getKitCooldown(Kit.MANAGER));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.OWNER)) {
				if(rPlayer.getKitCooldown(Kit.OWNER) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Owner", rPlayer.getKitCooldown(Kit.OWNER));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.USER)) {
				if(rPlayer.getKitCooldown(Kit.USER) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".User", rPlayer.getKitCooldown(Kit.USER));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.RAID)) {
				if(rPlayer.getKitCooldown(Kit.RAID) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Raid.Cooldown", rPlayer.getKitCooldown(Kit.RAID));
				}
			}
			if(rPlayer.hasKitUnlock(Kit.RAID)) {
				if(rPlayer.getKitUnlock(Kit.RAID) == true) {
					yml.set("Kits.Cooldowns." + uuid + ".Raid.Unlock", rPlayer.getKitUnlock(Kit.RAID));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.POTION)) {
				if(rPlayer.getKitCooldown(Kit.POTION) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Potion.Cooldown", rPlayer.getKitCooldown(Kit.POTION));
				}
			}
			if(rPlayer.hasKitUnlock(Kit.POTION)) {
				if(rPlayer.getKitUnlock(Kit.POTION) == true) {
					yml.set("Kits.Cooldowns." + uuid + ".Potion.Unlock", rPlayer.getKitUnlock(Kit.POTION));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.BUILD)) {
				if(rPlayer.getKitCooldown(Kit.BUILD) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Build.Cooldown", rPlayer.getKitCooldown(Kit.BUILD));
				}
			}
			if(rPlayer.hasKitUnlock(Kit.BUILD)) {
				if(rPlayer.getKitUnlock(Kit.BUILD) == true) {
					yml.set("Kits.Cooldowns." + uuid + ".Build.Unlock", rPlayer.getKitUnlock(Kit.BUILD));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.CRYSTAL)) {
				if(rPlayer.getKitCooldown(Kit.CRYSTAL) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Crystal.Cooldown", rPlayer.getKitCooldown(Kit.CRYSTAL));
				}
			}
			if(rPlayer.hasKitUnlock(Kit.CRYSTAL)) {
				if(rPlayer.getKitUnlock(Kit.CRYSTAL) == true) {
					yml.set("Kits.Cooldowns." + uuid + ".Crystal.Unlock", rPlayer.getKitUnlock(Kit.CRYSTAL));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.FLIGHT)) {
				if(rPlayer.getKitCooldown(Kit.FLIGHT) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Flight.Cooldown", rPlayer.getKitCooldown(Kit.CRYSTAL));
				}
			}
			if(rPlayer.hasKitUnlock(Kit.FLIGHT)) {
				if(rPlayer.getKitUnlock(Kit.FLIGHT) == true) {
					yml.set("Kits.Cooldowns." + uuid + ".Flight.Unlock", rPlayer.getKitUnlock(Kit.CRYSTAL));
				}
			}
			if(rPlayer.hasKitCooldown(Kit.SUPPLIER)) {
				if(rPlayer.getKitCooldown(Kit.SUPPLIER) != 0L) {
					yml.set("Kits.Cooldowns." + uuid + ".Supplier.Cooldown", rPlayer.getKitCooldown(Kit.SUPPLIER));
				}
			}
			if(rPlayer.hasKitUnlock(Kit.SUPPLIER)) {
				if(rPlayer.getKitUnlock(Kit.SUPPLIER) == true) {
					yml.set("Kits.Cooldowns." + uuid + ".Supplier.Unlock", rPlayer.getKitUnlock(Kit.SUPPLIER));
				}
			}
			yml.set("Kits.Cooldowns." + uuid + ".Gift Cooldown", rPlayer.getGiftCooldown());
			yml.set("Kits.Cooldowns." + uuid + ".Free Rank Command", rPlayer.getFreeRankCooldown());
			yml.set("Kits.Cooldowns." + uuid + ".Free Bronze Time", rPlayer.getBronzeTime());
			yml.set("Kits.Cooldowns." + uuid + ".PlayerVaults", rPlayer.getStackList());
		}
		try{
			yml.save(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
	public void add(UUID uuid, RankedPlayer p) {
		this.ranked.put(uuid, p);
	}
	public void remove(UUID uuid) {
		if(this.contains(uuid)) {
			this.ranked.remove(uuid);
		}
	}
	public boolean contains(UUID uuid) {
		if(this.ranked.containsKey(uuid)) {
			return true;
		}
		return false;
	}
	public RankedPlayer getRankedPlayer(UUID uuid) {
		if(this.contains(uuid)) {
			return this.ranked.get(uuid);
		}
		return null;
	}
	public HashMap<UUID, RankedPlayer> getRankedPlayerList(){
		return this.ranked;
	}
}
