package me.WiebeHero.RankedPlayerPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import me.WiebeHero.RankedPlayerPackage.RankEnum.Kit;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Rank;

public class RankedPlayer {
	public ArrayList<Rank> ranks;
	public int ahCount;
	public int homeCount;
	public HashMap<Kit, Long> kitCooldown;
	public HashMap<Kit, Boolean> kitUnlock;
	public RankedPlayer(Rank rank, String rankStyle, int ahCount, int homeCount) {
		this.ranks = new ArrayList<Rank>();
		this.ranks.add(rank);
		this.ahCount = ahCount;
		this.homeCount = homeCount;
		this.kitCooldown = new HashMap<Kit, Long>();
		this.kitUnlock = new HashMap<Kit, Boolean>();
	}
	public RankedPlayer(ArrayList<Rank> rank, String rankStyle, int ahCount, int homeCount) {
		this.ranks = rank;
		this.ahCount = ahCount;
		this.homeCount = homeCount;
		this.kitCooldown = new HashMap<Kit, Long>();
		this.kitUnlock = new HashMap<Kit, Boolean>();
	}
	public RankedPlayer() {
		this.ranks = new ArrayList<Rank>();
		this.kitCooldown = new HashMap<Kit, Long>();
		this.kitUnlock = new HashMap<Kit, Boolean>();
	}
	public Long getKitCooldown(Kit kit) {
		return this.kitCooldown.get(kit);
	}
	public void addKitCooldown(Kit kit, Long cooldown) {
		this.kitCooldown.put(kit, cooldown);
	}
	public void removeKitCooldown(Kit kit, Long cooldown) {
		this.kitCooldown.remove(kit);
	}
	public HashMap<Kit, Long> getKitCooldownList(){
		return this.kitCooldown;
	}
	public boolean hasRank(Rank rank) {
		return this.ranks.contains(rank);
	}
	public ArrayList<Rank> getRanks(){
		return this.ranks;
	}
	public HashMap<Kit, Boolean> getKitUnlockList(){
		return this.kitUnlock;
	}
	public Rank getRank(int rank) {
		Rank ranks1[] = Rank.values();
		for(int i = 0; i < ranks1.length; i++) {
			if(rank == ranks1[i].rank) {
				return ranks1[i];
			}
		}
		return Rank.USER;
	}
	public Rank getHighestRank() {
		ArrayList<Integer> ints = new ArrayList<Integer>();
		for(int i = 0; i < this.ranks.size(); i++) {
			ints.add(this.ranks.get(i).rank);
		}
		Collections.sort(ints);
		return this.getRank(ints.get(ints.size() - 1));
	}
	public boolean isStaff() {
		return (this.getHighestRank().rank >= 7);
	}
	public void setKitUnlock(Kit rank, boolean bool) {
		this.kitUnlock.put(rank, bool);
	}
	public boolean containsKitUnlock(Kit rank) {
		return this.kitUnlock.containsKey(rank);
	}
	public boolean getKitUnlock(Kit rank) {
		return this.kitUnlock.get(rank);
	}
	public void removeKitUnlock(Kit rank) {
		this.kitUnlock.remove(rank);
	}
	public void addRank(Rank rank) {
		this.ranks.add(rank);
	}
	public void setAHCount(int count) {
		this.ahCount = count;
	}
	public int getAHCount() {
		return this.ahCount;
	}
	public void setHomeCount(int count) {
		this.homeCount = count;
	}
	public int getHomeCount() {
		return this.homeCount;
	}
}
