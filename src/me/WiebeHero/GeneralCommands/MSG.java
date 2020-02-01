package me.WiebeHero.GeneralCommands;

import java.util.ArrayList;
import java.util.UUID;

public class MSG {
	public ArrayList<UUID> ignoreList;
	public MSG() {
		this.ignoreList = new ArrayList<UUID>();
	}
	public void addIgnore(UUID uuid) {
		this.ignoreList.add(uuid);
	}
	public void removeIgnore(UUID uuid) {
		if(this.isInIgnore(uuid)) {
			this.ignoreList.remove(uuid);
		}
	}
	public boolean isInIgnore(UUID uuid) {
		return this.ignoreList.contains(uuid);
	}
	public ArrayList<UUID> getIgnoreList(){
		return this.ignoreList;
	}
	public void setIgnoreList(ArrayList<UUID> list) {
		this.ignoreList = list;
	}
}
