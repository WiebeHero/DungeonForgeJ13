package me.WiebeHero.DungeonInstances;

import java.util.ArrayList;
import java.util.UUID;

public class DungeonManager {
	
	private ArrayList<Dungeon> dungeonList;
	
	public DungeonManager() {
		this.dungeonList = new ArrayList<Dungeon>();
	}
	
	public void addDungeon(Dungeon dungeon) {
		if(!this.dungeonList.contains(dungeon)) {
			this.dungeonList.add(dungeon);
		}
	}
	
	public void removeDungeon(Dungeon dungeon) {
		if(this.dungeonList.contains(dungeon)) {
			this.dungeonList.remove(dungeon);
		}
	}
	
	public void removeDungeon(UUID uuid) {
		for(Dungeon dungeon : this.dungeonList) {
			if(dungeon != null) {
				if(dungeon.getUniqueId().equals(uuid)) {
					this.dungeonList.remove(dungeon);
				}
			}
		}
	}
	
	public Dungeon getDungeon(UUID uuid) {
		for(Dungeon dungeon : this.dungeonList) {
			if(dungeon.getUniqueId().equals(uuid)) {
				return dungeon;
			}
		}
		return null;
	}
	
	public ArrayList<Dungeon> getDungeonList(){
		return this.dungeonList;
	}
	
	public void setDungeonList(ArrayList<Dungeon> dungeonList){
		this.dungeonList = dungeonList;
	}
}
