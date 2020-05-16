package me.WiebeHero.Chat;

import java.util.ArrayList;
import java.util.UUID;

public class MSG {
	private ArrayList<UUID> ignoreList;
	private boolean staffChat;
	public MSG() {
		this.staffChat = false;
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
	public void setStaffChat(boolean staff) {
		this.staffChat = staff;
	}
	public boolean getStaffChat() {
		return this.staffChat;
	}
}
