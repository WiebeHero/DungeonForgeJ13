package me.WiebeHero.Moderation;

import java.util.ArrayList;
import java.util.UUID;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class Punish {
	private PunishManager manager = CustomEnchantments.getInstance().punishManager;
	public UUID uuid;
	public ArrayList<String> banReason;
	public ArrayList<String> muteReason;
	public ArrayList<String> warnReason;
	public ArrayList<String> bannedBy;
	public ArrayList<String> mutedBy;
	public Long banTime;
	public Long muteTime;
	public Punish(UUID uuid) {
		this.uuid = uuid;
		this.banReason = new ArrayList<String>();
		this.muteReason = new ArrayList<String>();
		this.warnReason = new ArrayList<String>();
		this.bannedBy = new ArrayList<String>();
		this.mutedBy = new ArrayList<String>();
		this.banTime = 0L;
		this.muteTime = 0L;
		manager.add(uuid, this);
	}
	public Punish() {
		
	}
	/*
	 * UUID Methods
	 */
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	public UUID getUUID() {
		return this.uuid;
	}
	/*
	 * Ban Reason methods
	 */
	public ArrayList<String> getBanReasonsList() {
		return this.banReason;
	}
	public String getBanReason(int index) {
		if(banReason.get(index) != null) {
			return banReason.get(index);
		}
		return null;
	}
	public String getBanReason(String s) {
		if(banReason.contains(s)) {
			return s;
		}
		return null;
	}
	public void editBanReason(String replace, int index) {
		if(banReason.get(index) != null) {
			banReason.set(index, replace);
		}
	}
	public void removeBanReason(int index) {
		if(banReason.get(index) != null) {
			banReason.remove(index);
		}
	}
	public void addBanReason(String reason) {
		this.banReason.add(reason);
	}
	public void clearBanReasons() {
		this.banReason.clear();
	}
	/*
	 * Mute Reason Methods
	 */
	public ArrayList<String> getMuteReasonsList() {
		return this.muteReason;
	}
	public String getMuteReason(int index) {
		if(muteReason.get(index) != null) {
			return muteReason.get(index);
		}
		return null;
	}
	public String getMuteReason(String s) {
		if(muteReason.contains(s)) {
			return s;
		}
		return null;
	}
	public void editMuteReason(String replace, int index) {
		if(muteReason.get(index) != null) {
			muteReason.set(index, replace);
		}
	}
	public void removeMuteReason(int index) {
		if(muteReason.get(index) != null) {
			muteReason.remove(index);
		}
	}
	public void addMuteReason(String reason) {
		this.muteReason.add(reason);
	}
	public void clearMuteReasons() {
		this.muteReason.clear();
	}
	/*
	 * Warning Reason Methods
	 */
	public ArrayList<String> getWarnReasonsList() {
		return this.warnReason;
	}
	public String getWarnReason(int index) {
		if(warnReason.get(index) != null) {
			return warnReason.get(index);
		}
		return null;
	}
	public String getWarnReason(String s) {
		if(warnReason.contains(s)) {
			return s;
		}
		return null;
	}
	public void editWarnReason(String replace, int index) {
		if(warnReason.get(index) != null) {
			warnReason.set(index, replace);
		}
	}
	public void removeWarnReason(int index) {
		if(warnReason.get(index) != null) {
			warnReason.remove(index);
		}
	}
	public void addWarnReason(String reason) {
		this.warnReason.add(reason);
	}
	public void clearWarnReasons() {
		this.warnReason.clear();
	}
	/*
	 * Banned By Methods
	 */
	public ArrayList<String> getBannedByList() {
		return this.warnReason;
	}
	public String getBannedBy(int index) {
		if(warnReason.get(index) != null) {
			return warnReason.get(index);
		}
		return null;
	}
	public String getBannedBy(String s) {
		if(warnReason.contains(s)) {
			return s;
		}
		return null;
	}
	public void removeBannedBy(int index) {
		if(warnReason.get(index) != null) {
			warnReason.remove(index);
		}
	}
	public void addBannedBy(String reason) {
		this.warnReason.add(reason);
	}
	public void clearBanned() {
		this.warnReason.clear();
	}
	/*
	 * Muted By Methods
	 */
	public ArrayList<String> getMutedByList() {
		return this.warnReason;
	}
	public String getMutedBy(int index) {
		if(warnReason.get(index) != null) {
			return warnReason.get(index);
		}
		return null;
	}
	public String getMutedBy(String s) {
		if(warnReason.contains(s)) {
			return s;
		}
		return null;
	}
	public void editMutedBy(String replace, int index) {
		if(warnReason.get(index) != null) {
			warnReason.set(index, replace);
		}
	}
	public void removeMutedBy(int index) {
		if(warnReason.get(index) != null) {
			warnReason.remove(index);
		}
	}
	public void addMutedBy(String reason) {
		this.warnReason.add(reason);
	}
	public void clearMutedBy() {
		this.warnReason.clear();
	}
	/*
	 * Ban Time Methods
	 */
	public void setBanTime(Long time) {
		this.banTime = time;
	}
	public Long getBanTime() {
		return this.banTime;
	}
	public boolean isBanned() {
		if(this.banTime > System.currentTimeMillis()) {
			return true;
		}
		return false;
	}
	/*
	 * Mute Time Methods
	 */
	public void setMuteTime(Long time) {
		this.muteTime = time;
	}
	public Long getMuteTime() {
		return this.muteTime;
	}
	public boolean isMuted() {
		if(this.muteTime > System.currentTimeMillis()) {
			return true;
		}
		return false;
	}
	/*
	 * Ban Offenses Methods
	 */
	public int getBanOffense() {
		return this.banReason.size();
	}
	/*
	 * Mute Offenses Methods
	 */
	public int getMuteOffense() {
		return this.muteReason.size();
	}
}
