package me.WiebeHero.RankedPlayerPackage;

import java.util.HashMap;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.RankedPlayerPackage.RankEnum.ModRank;
import me.WiebeHero.RankedPlayerPackage.RankEnum.PayRank;

public class RankedPlayer {
	public PayRank payRank;
	public ModRank modRank;
	public String rankStyle;
	public int ahCount;
	public int homeCount;
	public HashMap<PayRank, Long> kitCooldown;
	public HashMap<ModRank, Long> kitCooldownStaff;
	public RankedPlayer(ModRank rank, String rankStyle, int ahCount, int homeCount) {
		this.modRank = rank;
		this.rankStyle = new CCT().colorize(rankStyle);
		this.ahCount = ahCount;
		this.homeCount = homeCount;
		this.kitCooldown = new HashMap<PayRank, Long>();
		this.kitCooldownStaff = new HashMap<ModRank, Long>();
		this.setUpKitCooldowns();
	}
	public RankedPlayer(PayRank rank, String rankStyle, int ahCount, int homeCount) {
		this.payRank = rank;
		this.rankStyle = new CCT().colorize(rankStyle);
		this.ahCount = ahCount;
		this.homeCount = homeCount;
		this.kitCooldown = new HashMap<PayRank, Long>();
		this.kitCooldownStaff = new HashMap<ModRank, Long>();
		this.setUpKitCooldowns();
	}
	public RankedPlayer(ModRank rank1, PayRank rank2, String rankStyle, int ahCount, int homeCount) {
		this.modRank = rank1;
		this.payRank = rank2;
		this.rankStyle = new CCT().colorize(rankStyle);
		this.ahCount = ahCount;
		this.homeCount = homeCount;
		this.kitCooldown = new HashMap<PayRank, Long>();
		this.kitCooldownStaff = new HashMap<ModRank, Long>();
		this.setUpKitCooldowns();
	}
	public void setModRank(ModRank rank) {
		this.modRank = rank;
	}
	public ModRank getModRank() {
		return this.modRank;
	}
	public void setPayRank(PayRank rank) {
		this.payRank = rank;
	}
	public PayRank getPayRank() {
		return this.payRank;
	}
	public void setRankStyle(String s) {
		this.rankStyle = new CCT().colorize(s);
	}
	public String getRankStyle() {
		return this.rankStyle;
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
	public void setUpKitCooldowns() {
		this.kitCooldown.put(PayRank.BRONZE, 0L);
		this.kitCooldown.put(PayRank.SILVER, 0L);
		this.kitCooldown.put(PayRank.GOLD, 0L);
		this.kitCooldown.put(PayRank.PLATINUM, 0L);
		this.kitCooldown.put(PayRank.DIAMOND, 0L);
		this.kitCooldown.put(PayRank.EMERALD, 0L);
		this.kitCooldownStaff.put(ModRank.QA, 0L);
		this.kitCooldownStaff.put(ModRank.QA_ADMIN, 0L);
		this.kitCooldownStaff.put(ModRank.HELPER, 0L);
		this.kitCooldownStaff.put(ModRank.HELPER_PLUS, 0L);
		this.kitCooldownStaff.put(ModRank.MOD, 0L);
		this.kitCooldownStaff.put(ModRank.HEAD_MOD, 0L);
		this.kitCooldownStaff.put(ModRank.ADMIN, 0L);
		this.kitCooldownStaff.put(ModRank.HEAD_ADMIN, 0L);
		this.kitCooldownStaff.put(ModRank.MANAGER, 0L);
		this.kitCooldownStaff.put(ModRank.OWNER, 0L);
	}
}
