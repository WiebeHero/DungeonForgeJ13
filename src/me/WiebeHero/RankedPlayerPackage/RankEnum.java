package me.WiebeHero.RankedPlayerPackage;

public class RankEnum {
	public enum PayRank{
		USER(0),
		BRONZE(1),
		SILVER(2),
		GOLD(3),
		PLATINUM(4),
		DIAMOND(5),
		EMERALD(6),
		YOUTUBER(6);
		public final int rank;
		
		private PayRank(int rank) {
			this.rank = rank;
		}
	}
	public enum ModRank{
		USER(0),
		QA(1),
		QA_ADMIN(3),
		HELPER(4),
		HELPER_PLUS(5),
		MOD(6),
		HEAD_MOD(7),
		ADMIN(8),
		HEAD_ADMIN(9),
		MANAGER(10),
		OWNER(11);
		
		public final int rank;
		
		private ModRank(int rank) {
			this.rank = rank;
		}
	}
}
