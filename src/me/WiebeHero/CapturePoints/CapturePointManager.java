package me.WiebeHero.CapturePoints;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Banner;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Factions.DFFactionManager;
import me.WiebeHero.Factions.DFFactionPlayer;
import me.WiebeHero.Factions.DFFactionPlayerManager;

public class CapturePointManager {
	
	private DFFactionPlayerManager facPlayerManager;
	private DFFactionManager facManager;
	private HashMap<UUID, CapturePoint> capturePointList;
	
	public CapturePointManager(DFFactionManager facManager, DFFactionPlayerManager facPlayerManager) {
		this.capturePointList = new HashMap<UUID, CapturePoint>();
		this.facManager = facManager;
		this.facPlayerManager = facPlayerManager;
	}
	
	public void addCapturePoint(CapturePoint point) {
		this.capturePointList.put(point.getCaptureId(), point);
	}
	
	public void removeCapturePoint(CapturePoint point) {
		point.getBossBar().removeAll();
		this.capturePointList.remove(point.getCaptureId());
	}
	
	public boolean containsCapturePoint(UUID uuid) {
		return this.capturePointList.containsKey(uuid);
	}
	
	public CapturePoint getCapturePoint(UUID uuid) {
		if(this.capturePointList.containsKey(uuid)) {
			return this.capturePointList.get(uuid);
		}
		return null;
	}
	
	public HashMap<UUID, CapturePoint> getCapturePointList() {
		return this.capturePointList;
	}
	
	public void loadCapturePoints() {
		File f = new File("plugins/CustomEnchantments/CapturePoints.yml");
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
		if(yml.getConfigurationSection("Capture Points") != null) {
			if(!yml.get("Capture Points").equals("{}")) {
				ArrayList<String> uuidStrings = new ArrayList<String>(yml.getConfigurationSection("Capture Points").getKeys(false));
				for(int i = 0; i < uuidStrings.size(); i++) {
					UUID cUuid = UUID.fromString(uuidStrings.get(i));
					UUID fUuid = null;
					if(yml.get("Capture Points." + cUuid + ".Captured ID") != null) {
						fUuid = UUID.fromString(yml.getString("Capture Points." + cUuid + ".Captured ID"));
					} 
					Location loc = (Location)yml.get("Capture Points." + cUuid + ".Capture Point Location");
					int progress = yml.getInt("Capture Points." + cUuid + ".Capture Point Progress");
					double radius = yml.getDouble("Capture Points." + cUuid + ".Capture Point Radius");
					double multiplier = yml.getDouble("Capture Points." + cUuid + ".Capture Point Multiplier");
					CapturePoint cp = new CapturePoint(cUuid, fUuid, loc, progress, radius, multiplier);
					this.capturePointList.put(cp.getCaptureId(), cp);
 				}
			}
		}
	}
	public void saveCapturePoints() {
		File f1 =  new File("plugins/CustomEnchantments/CapturePoints.yml");
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
		yml.set("Capture Points", null);
		for(CapturePoint cp : this.capturePointList.values()) {
			if(cp != null) {
				yml.createSection("Capture Points." + cp.getCaptureId().toString());
				if(cp.getCapturedId() != null) {
					yml.set("Capture Points." + cp.getCaptureId().toString() + ".Captured ID", cp.getCapturedId().toString());
				}
				yml.set("Capture Points." + cp.getCaptureId().toString() + ".Capture Point Location", cp.getCaptureLocation());
				yml.set("Capture Points." + cp.getCaptureId().toString() + ".Capture Point Progress", cp.getCaptureProgress());
				yml.set("Capture Points." + cp.getCaptureId().toString() + ".Capture Point Radius", cp.getCaptureRadius());
				yml.set("Capture Points." + cp.getCaptureId().toString() + ".Capture Point Multiplier", cp.getXPMultiplier());
			}
		}
		try{
			yml.save(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
	
	public void activateCapturePoints() {
		new BukkitRunnable() {
			public void run() {
				if(!capturePointList.isEmpty()) {
					for(CapturePoint point : capturePointList.values()) {
						point.getBossBar().removeAll();
						double radius = point.getCaptureRadius() + 5;
						for(Entity e : point.getCaptureLocation().getNearbyEntities(radius, radius, radius)) {
							if(e instanceof Player) {
								Player p = (Player) e;
								DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(p.getUniqueId());
								if(e.getLocation().distance(point.getCaptureLocation()) <= point.getCaptureRadius()) {
									if(facPlayer.getFactionId() != null) {
										point.getBossBar().addPlayer(p);
									}
								}
							}
						}
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 2L);
		new BukkitRunnable() {
			public void run() {
				if(!capturePointList.isEmpty()) {
					for(CapturePoint point : capturePointList.values()) {
						ArrayList<UUID> facsNear = new ArrayList<UUID>();
						HashMap<UUID, Integer> size = new HashMap<UUID, Integer>();
						double radius = point.getCaptureRadius() + 5;
						for(Entity e : point.getCaptureLocation().getNearbyEntities(radius, radius, radius)) {
							if(e instanceof Player) {
								Player p = (Player) e;
								DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(p.getUniqueId());
								if(e.getLocation().distance(point.getCaptureLocation()) <= point.getCaptureRadius()) {
									if(facPlayer.getFactionId() != null) {
										if(!facsNear.contains(facPlayer.getFactionId())) {
											facsNear.add(facPlayer.getFactionId());
										}
										if(size.containsKey(facPlayer.getFactionId())) {
											size.put(facPlayer.getFactionId(), size.get(facPlayer.getFactionId()) + 1);
										}
										else {
											size.put(facPlayer.getFactionId(), 1);
										}
									}
								}
							}
						}
						if(facsNear.size() == 1) {
							if(point.isCaptured()) {
								if(!point.getCapturedId().equals(facsNear.get(0))) {
									int capture = point.getCaptureProgress();
									int decrease = size.get(facsNear.get(0));
									if(capture - decrease <= 0) {
										point.setCaptureProgress(Math.abs(point.getCaptureProgress() - decrease));
										point.getCaptureLocation().getWorld().playSound(point.getCaptureLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2.0F, 1.0F);
										point.setCapturedId(null);
										Location loc = point.getCaptureLocation();
										loc.getBlock().setType(Material.BLACK_BANNER);
										point.getBossBar().setTitle(new CCT().colorize("&aCapture Point"));
									}
									else {
										point.setCaptureProgress(point.getCaptureProgress() - decrease);
										point.getCaptureLocation().getWorld().playSound(point.getCaptureLocation(), Sound.UI_BUTTON_CLICK, 2.0F, 1.0F);
									}
								}
							}
							else {
								int capture = point.getCaptureProgress();
								int increase = size.get(facsNear.get(0));
								if(capture + increase >= 10) {
									point.setCaptureProgress(10);
									point.setCapturedId(facsNear.get(0));
									DFFaction fac = facManager.getFaction(facsNear.get(0));
									point.getBossBar().setTitle(new CCT().colorize("&aCapture Point: &6" + fac.getName()));
									Location loc = point.getCaptureLocation();
									ItemStack bannerItem = fac.getBanner();
									BannerMeta m = (BannerMeta)bannerItem.getItemMeta();
									loc.getBlock().setType(bannerItem.getType());
									Banner banner = (Banner)loc.getBlock().getState();
									banner.setPatterns(m.getPatterns());
									banner.update();
									point.getCaptureLocation().getWorld().playSound(point.getCaptureLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2.0F, 0.5F);
								}
								else {
									point.getCaptureLocation().getWorld().playSound(point.getCaptureLocation(), Sound.UI_BUTTON_CLICK, 2.0F, 1.0F);
									point.setCaptureProgress(point.getCaptureProgress() + increase);
								}
							}
						}
						else if(facsNear.size() == 0){
							if(point.isCaptured()) {
								int capture = point.getCaptureProgress();
								int increase = 1;
								if(capture + increase <= 10) {
									point.setCaptureProgress(point.getCaptureProgress() + increase);
								}
							}
							else {
								if(point.getCaptureProgress() != 0) {
									int capture = point.getCaptureProgress();
									int decrease = 1;
									if(capture - decrease >= 0) {
										point.setCaptureProgress(point.getCaptureProgress() - decrease);
									}
								}
							}
						}
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
	}
}
