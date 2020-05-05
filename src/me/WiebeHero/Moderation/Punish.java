package me.WiebeHero.Moderation;

import java.util.ArrayList;
import java.util.UUID;

public class Punish {
	public UUID uuid;
	public ArrayList<String> banReason;
	public ArrayList<String> muteReason;
	public ArrayList<String> bannedBy;
	public ArrayList<String> mutedBy;
	public ArrayList<String> banDate;
	public ArrayList<String> muteDate;
	public Long banTime;
	public Long muteTime;
	public boolean banPerm;
	public boolean mutePerm;
	public Punish(UUID uuid) {
		this.uuid = uuid;
		this.banReason = new ArrayList<String>();
		this.muteReason = new ArrayList<String>();
		this.bannedBy = new ArrayList<String>();
		this.mutedBy = new ArrayList<String>();
		this.banDate = new ArrayList<String>();
		this.muteDate = new ArrayList<String>();
		this.banTime = 0L;
		this.muteTime = 0L;
		this.banPerm = false;
		this.mutePerm = false;
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
	public void setBanReason(ArrayList<String> reason) {
		this.banReason = reason;
	}
	public void clearBanReasons() {
		this.banReason.clear();
	}
	/*
	 * Ban Date methods
	 */
	public ArrayList<String> getBanDateList() {
		return this.banDate;
	}
	public String getBanDate(int index) {
		if(banDate.get(index) != null) {
			return banDate.get(index);
		}
		return null;
	}
	public void removeBanDate(int index) {
		if(banDate.get(index) != null) {
			banDate.remove(index);
		}
	}
	public void setBanDate(int index, String s) {
		if(banDate.size() < index && index > -1) {
			banDate.set(index, s);
		}
	}
	public void addBanDate(String date) {
		this.banDate.add(date);
	}
	public void setBanDate(ArrayList<String> dates) {
		this.banDate = dates;
	}
	public void clearBanDates() {
		this.banDate.clear();
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
	public void setMuteReason(ArrayList<String> reason) {
		this.muteReason = reason;
	}
	public void clearMuteReasons() {
		this.muteReason.clear();
	}
	/*
	 * Mute Date methods
	 */
	public ArrayList<String> getMuteDateList() {
		return this.muteDate;
	}
	public String getMuteDate(int index) {
		if(muteDate.get(index) != null) {
			return muteDate.get(index);
		}
		return null;
	}
	public void removeMuteDate(int index) {
		if(muteDate.get(index) != null) {
			muteDate.remove(index);
		}
	}
	public void setMuteDate(int index, String s) {
		if(muteDate.size() < index && index > -1) {
			muteDate.set(index, s);
		}
	}
	public void addMuteDate(String date) {
		this.muteDate.add(date);
	}
	public void setMuteDate(ArrayList<String> dates) {
		this.muteDate = dates;
	}
	public void clearMuteDates() {
		this.muteDate.clear();
	}
	/*
	 * Banned By Methods
	 */
	public ArrayList<String> getBannedByList() {
		return this.bannedBy;
	}
	public String getBannedBy(int index) {
		if(bannedBy.get(index) != null) {
			return bannedBy.get(index);
		}
		return null;
	}
	public String getBannedBy(String s) {
		if(bannedBy.contains(s)) {
			return s;
		}
		return null;
	}
	public void removeBannedBy(int index) {
		if(bannedBy.get(index) != null) {
			bannedBy.remove(index);
		}
	}
	public void addBannedBy(String reason) {
		this.bannedBy.add(reason);
	}
	public void setBannedBy(ArrayList<String> reason) {
		this.bannedBy = reason;
	}
	public void clearBanned() {
		this.bannedBy.clear();
	}
	/*
	 * Muted By Methods
	 */
	public ArrayList<String> getMutedByList() {
		return this.mutedBy;
	}
	public String getMutedBy(int index) {
		if(mutedBy.get(index) != null) {
			return mutedBy.get(index);
		}
		return null;
	}
	public String getMutedBy(String s) {
		if(mutedBy.contains(s)) {
			return s;
		}
		return null;
	}
	public void editMutedBy(String replace, int index) {
		if(mutedBy.get(index) != null) {
			mutedBy.set(index, replace);
		}
	}
	public void removeMutedBy(int index) {
		if(mutedBy.get(index) != null) {
			mutedBy.remove(index);
		}
	}
	public void addMutedBy(String reason) {
		this.mutedBy.add(reason);
	}
	public void setMutedBy(ArrayList<String> reason) {
		this.mutedBy = reason;
	}
	public void clearMutedBy() {
		this.mutedBy.clear();
	}
	/*
	 * Ban Time Methods
	 */
	public void setBanPerm(boolean banPerm) {
		this.banPerm = banPerm;
	}
	public boolean getBanPerm() {
		return this.banPerm;
	}
	public void setBanTime(Long time) {
		this.banTime = time;
	}
	public Long getBanTime() {
		return this.banTime;
	}
	public boolean isBanned() {
		if(this.banTime > System.currentTimeMillis() || this.banPerm == true) {
			return true;
		}
		return false;
	}
	/*
	 * Mute Time Methods
	 */
	public void setMutePerm(boolean mutePerm) {
		this.mutePerm = mutePerm;
	}
	public boolean getMutePerm() {
		return this.mutePerm;
	}
	public void setMuteTime(Long time) {
		this.muteTime = time;
	}
	public Long getMuteTime() {
		return this.muteTime;
	}
	public boolean isMuted() {
		if(this.muteTime > System.currentTimeMillis() || this.mutePerm == true) {
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
