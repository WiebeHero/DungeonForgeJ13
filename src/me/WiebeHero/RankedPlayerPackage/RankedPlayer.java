package me.WiebeHero.RankedPlayerPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import me.WiebeHero.RankedPlayerPackage.RankEnum.Kit;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Rank;

public class RankedPlayer {
	private ArrayList<Rank> ranks;
	private int ahCount;
	private int homeCount;
	private Long giftCooldown;
	private HashMap<Kit, Long> kitCooldown;
	private HashMap<Kit, Boolean> kitUnlock;
	private Long bronzeTime;
	private Long freeRankCooldown;
	private BukkitTask task;
	private Inventory pv;
	private boolean flight;
	private ItemStack stackList[];
	public RankedPlayer(Rank rank, String rankStyle, int ahCount, int homeCount) {
		this.ranks = new ArrayList<Rank>();
		this.addRank(Rank.USER);
		this.addRank(rank);
		this.ahCount = ahCount;
		this.homeCount = homeCount;
		this.kitCooldown = new HashMap<Kit, Long>();
		this.kitUnlock = new HashMap<Kit, Boolean>();
		this.stackList = new ItemStack[54];
		this.giftCooldown = 0L;
		this.bronzeTime = 0L;
		this.freeRankCooldown = 0L;
		this.task = null;
		this.flight = false;
	}
	public RankedPlayer(ArrayList<Rank> rank, String rankStyle, int ahCount, int homeCount) {
		this.ranks = rank;
		this.addRank(Rank.USER);
		this.ahCount = ahCount;
		this.homeCount = homeCount;
		this.kitCooldown = new HashMap<Kit, Long>();
		this.kitUnlock = new HashMap<Kit, Boolean>();
		this.stackList = new ItemStack[54];
		this.giftCooldown = 0L;
		this.bronzeTime = 0L;
		this.freeRankCooldown = 0L;
		this.task = null;
		this.flight = false;
	}
	public RankedPlayer() {
		this.ranks = new ArrayList<Rank>();
		this.addRank(Rank.USER);
		this.kitCooldown = new HashMap<Kit, Long>();
		this.kitUnlock = new HashMap<Kit, Boolean>();
		this.stackList = new ItemStack[54];
		this.giftCooldown = 0L;
		this.bronzeTime = 0L;
		this.freeRankCooldown = 0L;
		this.task = null;
		this.flight = false;
	}
	
	public Long getGiftCooldown() {
		return this.giftCooldown;
	}
	
	public void setGiftCooldown(Long giftCooldown) {
		this.giftCooldown = giftCooldown;
	}

	public Long getBronzeTime() {
		return this.bronzeTime;
	}
	
	public void setBronzeTime(Long freeBronze) {
		this.bronzeTime = freeBronze;
	}
	
	public Long getFreeRankCooldown() {
		return this.freeRankCooldown;
	}
	
	public BukkitTask getTask() {
		return this.task;
	}
	
	public void setTask(BukkitTask task) {
		this.task = task;
	}
	
	public boolean getFlight() {
		return this.flight;
	}
	
	public void setFlight(boolean flight) {
		this.flight = flight;
	}
	
	public void setFreeRankCooldown(Long freeRankCooldown) {
		this.freeRankCooldown = freeRankCooldown;
	}
	
	public Long getKitCooldown(Kit kit) {
		return this.kitCooldown.get(kit);
	}
	public boolean hasKitCooldown(Kit kit) {
		return this.kitCooldown.containsKey(kit);
	}
	public Kit getRandomNotUnlockedKit() {
		ArrayList<Kit> kitList = new ArrayList<Kit>();
		for(Entry<Kit, Boolean> entry : this.getKitUnlockList().entrySet()) {
			if(entry.getValue() == false) {
				kitList.add(entry.getKey());
			}
		}
		if(!kitList.isEmpty()) {
			int rand = new Random().nextInt(kitList.size());
			return kitList.get(rand);
		}
		return null;
	}
	public ArrayList<Kit> getKitsNotUnlocked() {
		ArrayList<Kit> kitList = new ArrayList<Kit>();
		for(Entry<Kit, Boolean> entry : this.getKitUnlockList().entrySet()) {
			if(entry.getValue() == false) {
				kitList.add(entry.getKey());
			}
		}
		return kitList;
	}
	public ArrayList<Kit> getKitsUnlocked() {
		ArrayList<Kit> kitList = new ArrayList<Kit>();
		for(Entry<Kit, Boolean> entry : this.getKitUnlockList().entrySet()) {
			if(entry.getValue() == true) {
				kitList.add(entry.getKey());
			}
		}
		return kitList;
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
	public Rank getPaidRank() {
		ArrayList<Rank> ranks = new ArrayList<Rank>(Arrays.asList(Rank.BRONZE, Rank.SILVER, Rank.GOLD, Rank.PLATINUM, Rank.DIAMOND, Rank.EMERALD));
		for(Rank rank : this.getRanks()) {
			if(ranks.contains(rank)) {
				return rank;
			}
		}
		return null;
	}
	public HashMap<Kit, Boolean> getKitUnlockList(){
		return this.kitUnlock;
	}
	public Rank getRank(int rank) {
		Rank ranks1[] = Rank.values();
		for(int i = 0; i < ranks1.length; i++) {
			if(rank == ranks1[i].getRank()) {
				return ranks1[i];
			}
		}
		return Rank.USER;
	}
	public Rank getHighestRank() {
		ArrayList<Integer> ints = new ArrayList<Integer>();
		for(int i = 0; i < this.ranks.size(); i++) {
			ints.add(this.ranks.get(i).getRank());
		}
		Collections.sort(ints);
		return this.getRank(ints.get(ints.size() - 1));
	}
	public boolean isStaff() {
		return (this.getHighestRank().getRank() >= 7);
	}
	public void setKitUnlock(Kit rank, boolean bool) {
		this.kitUnlock.put(rank, bool);
	}
	public boolean containsKitUnlock(Kit rank) {
		return this.kitUnlock.containsKey(rank);
	}
	public boolean hasKitUnlock(Kit rank) {
		return this.kitUnlock.containsKey(rank);
	}
	public boolean getKitUnlock(Kit rank) {
		return this.kitUnlock.get(rank);
	}
	public void removeKitUnlock(Kit rank) {
		this.kitUnlock.remove(rank);
	}
	public void addRank(Rank rank) {
		if(!this.hasRank(rank)) {
			this.ranks.add(rank);
		}
	}
	public void removeRank(Rank rank) {
		if(this.hasRank(rank)) {
			this.ranks.remove(rank);
		}
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
	public void loadKits() {
		for(Kit kit : Kit.values()) {
			if(!this.getKitCooldownList().containsKey(kit)) {
				this.addKitCooldown(kit, 0L);
				if(kit.getRank() >= 100) {
					this.setKitUnlock(kit, false);
				}
			}
		}
	}
	public void setPV(Inventory pv) {
		this.pv = pv;
	}
	public Inventory getPV() {
		return this.pv;
	}
	public void setStackList(ItemStack stackList[]) {
		this.stackList = stackList;
	}
	public ItemStack[] getStackList(){
		return this.stackList;
	}
}
