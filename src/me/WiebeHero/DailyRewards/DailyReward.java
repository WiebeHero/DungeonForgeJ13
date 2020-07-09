package me.WiebeHero.DailyRewards;

import java.util.ArrayList;
import java.util.UUID;

public class DailyReward {

	private UUID uuid;
//	private int claimed;
	private String lastDaily;
	private Long lastWeekly;
	private Long lastMonthly;
	private ArrayList<Integer> claimedSlots;
	
	public DailyReward(UUID uuid, String lastDaily, Long lastMonthly, long lastWeekly, ArrayList<Integer> claimedSlots) {
		this.uuid = uuid;
//		this.claimed = 0;
		this.lastDaily = lastDaily;
		this.lastWeekly = lastWeekly;
		this.lastMonthly = lastMonthly;
		this.claimedSlots = claimedSlots;
	}
	
	public DailyReward(UUID uuid) {
		this.uuid = uuid;
//		this.claimed = 0;
		this.lastDaily = "";
		this.lastWeekly = 0L;
		this.lastMonthly = 0L;
		this.claimedSlots = new ArrayList<Integer>();
	}
	
//	public DailyReward(UUID uuid, int claimed) {
//		this.uuid = uuid;
//		this.claimed = claimed;
//		this.lastDaily = "";
//		this.lastWeekly = "";
//		this.lastMontly = "";
//		this.claimedSlots = new ArrayList<Integer>();
//	}
	
	public void setLastDaily(String date) {
		this.lastDaily = date;
	}
	
	public String getLastDaily() {
		return this.lastDaily;
	}
	
	public void setLastWeekly(long l) {
		this.lastWeekly = l;
	}
	
	public Long getLastWeekly() {
		return this.lastWeekly;
	}
	
	public void setLastMonthly(Long date) {
		this.lastMonthly = date;
	}
	
	public Long getLastMonthly() {
		return this.lastMonthly;
	}
	
	public int getDayOfLastDaily() {
		if(!this.lastDaily.equals("")) {
			String strip[] = this.lastDaily.split("-");
			return Integer.parseInt(strip[0]);
		}
		return 0;
	}
	
	public int getMonthOfLastDaily() {
		if(!this.lastDaily.equals("")) {
			String strip[] = this.lastDaily.split("-");
			return Integer.parseInt(strip[1]);
		}
		return 0;
	}
	
	public int getYearOfLastDaily() {
		if(!this.lastDaily.equals("")) {
			String strip[] = this.lastDaily.split("-");
			return Integer.parseInt(strip[2]);
		}
		return 0;
	}
	
//	public void addClaimedAmount(int amount) {
//		this.claimed += amount;
//	}
//	
//	public void setClaimedAmount(int amount) {
//		this.claimed = amount;
//	}
//	
//	public void removeClaimedAmount(int amount) {
//		this.claimed -= amount;
//	}
//	
//	public int getClaimedAmount() {
//		return this.claimed;
//	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	public void addClaimedSlot(int i) {
		this.claimedSlots.add(i);
	}
	
	public boolean containsClaimedSlot(int i) {
		return this.claimedSlots.contains(i);
	}
	
	public void removeClaimedSlot(int i) {
		if(this.containsClaimedSlot(i)) {
			this.claimedSlots.remove(i);
		}
	}
	
	public void clearClaimedSlots() {
		this.claimedSlots.clear();
	}
	
	public ArrayList<Integer> getClaimedSlots() {
		return this.claimedSlots;
	}
}
