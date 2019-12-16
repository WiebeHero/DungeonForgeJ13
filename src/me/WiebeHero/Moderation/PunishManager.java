package me.WiebeHero.Moderation;

import java.util.HashMap;
import java.util.UUID;

public class PunishManager {
	public HashMap<UUID, Punish> punishList;
	public PunishManager() {
		this.punishList = new HashMap<UUID, Punish>();
	}
	public void loadPunishList() {
		
	}
	public void savePunishList() {
		
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
