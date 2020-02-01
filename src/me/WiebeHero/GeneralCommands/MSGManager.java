package me.WiebeHero.GeneralCommands;

import java.util.HashMap;
import java.util.UUID;

public class MSGManager {
	public HashMap<UUID, MSG> msgManager;
	public MSGManager() {
		this.msgManager = new HashMap<UUID, MSG>();
	}
	public void add(UUID uuid, MSG msg) {
		this.msgManager.put(uuid, msg);
	}
	public MSG get(UUID uuid) {
		if(this.contains(uuid)) {
			return this.msgManager.get(uuid);
		}
		return null;
	}
	public void remove(UUID uuid) {
		if(this.contains(uuid)) {
			this.msgManager.remove(uuid);
		}
	}
	public boolean contains(UUID uuid) {
		if(this.msgManager.containsKey(uuid)) {
			return true;
		}
		return false;
	}
	public HashMap<UUID, MSG> getMSGList() {
		return this.msgManager;
	}
}
