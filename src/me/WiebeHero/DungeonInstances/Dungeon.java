package me.WiebeHero.DungeonInstances;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Dungeon {
	
	private UUID uuid;
	private String dungeonName;
	private ItemStack itemDisplay;
	private Location entranceLocation;
	private Location startLocation;
	private ArrayList<DungeonCheckpoint> checkPoints;
	private Long maxTime;
	
	public Dungeon() {
		this.uuid = UUID.randomUUID();
		this.checkPoints = new ArrayList<DungeonCheckpoint>();
		this.maxTime = 0L;
		this.dungeonName = "";
	}
	
	public Dungeon(String dungeonName, ItemStack display, Location entranceLocation, Location startLocation, ArrayList<DungeonCheckpoint> checkPoints, Long maxTime) {
		this.uuid = UUID.randomUUID();
		this.entranceLocation = entranceLocation;
		this.dungeonName = dungeonName;
		this.itemDisplay = display;
		this.startLocation = startLocation;
		this.checkPoints = checkPoints;
		this.maxTime = maxTime;
	}
	
	public String getName() {
		return this.dungeonName;
	}
	
	public void setName(String name) {
		this.dungeonName = name;
	}
	
	public ItemStack getItemDisplay() {
		return this.itemDisplay;
	}
	
	public void setItemDisplay(ItemStack display) {
		this.itemDisplay = display;
	}
	
	public Location getEntranceLocation() {
		return this.entranceLocation;
	}
	
	public void setEntranceLocation(Location entranceLocation) {
		this.entranceLocation = entranceLocation;
	}
	
	public Location getStartLocation() {
		return this.startLocation;
	}
	
	public void setStartLocation(Location startLocation) {
		this.startLocation = startLocation;
	}
	
	public ArrayList<DungeonCheckpoint> getAllCheckpoints(){
		return this.checkPoints;
	}
	
	public DungeonCheckpoint getCheckPoint(UUID uuid) {
		for(DungeonCheckpoint point : this.checkPoints) {
			if(point.getUuid().equals(uuid)) {
				return point;
			}
		}
		return null;
	}
	
	public void setAllCheckpoints(ArrayList<DungeonCheckpoint> checkPoints) {
		this.checkPoints = checkPoints;
	}
	
	public void addCheckpoint(DungeonCheckpoint point) {
		if(!this.checkPoints.contains(point)) {
			this.checkPoints.add(point);
		}
	}
	
	public void removeCheckpoint(DungeonCheckpoint point) {
		if(this.checkPoints.contains(point)) {
			this.checkPoints.remove(point);
		}
	}
	
	public void removeCheckpoint(UUID uuid) {
		for(DungeonCheckpoint point : this.checkPoints) {
			if(point != null) {
				if(point.getUuid().equals(uuid)) {
					this.checkPoints.remove(point);
				}
			}
		}
	}
	
	public Long getMaxTime() {
		return this.maxTime;
	}
	
	public void setMaxTime(Long maxTime) {
		this.maxTime = maxTime;
	}
	
	public UUID getUniqueId() {
		return this.uuid;
	}
	
}
