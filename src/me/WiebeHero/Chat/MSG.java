package me.WiebeHero.Chat;

import java.util.ArrayList;
import java.util.UUID;

public class MSG {
	private ArrayList<UUID> ignoreList;
	private boolean staffChat;
	private boolean donatorChat;
	public MSG() {
		this.staffChat = false;
		this.donatorChat = false;
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
	public void setDonatorChat(boolean don) {
		this.donatorChat = don;
	}
	public boolean getStaffChat() {
		return this.staffChat;
	}
	public boolean getDonatorChat() {
		return this.donatorChat;
	}
}
