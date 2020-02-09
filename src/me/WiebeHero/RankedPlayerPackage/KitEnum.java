package me.WiebeHero.RankedPlayerPackage;

public class KitEnum {
	public enum KitPaid{
		
		BRONZE(1),
		SILVER(2),
		GOLD(3),
		PLATINUM(4),
		DIAMOND(5),
		EMERALD(6);
		
		public final int rank;
		
		private KitPaid(int rank) {
			this.rank = rank;
		}
	}
	public enum KitStaff{
		
		BRONZE(1),
		SILVER(2),
		GOLD(3),
		PLATINUM(4),
		DIAMOND(5),
		EMERALD(6);
		
		public final int rank;
		
		private KitStaff(int rank) {
			this.rank = rank;
		}
	}
}
