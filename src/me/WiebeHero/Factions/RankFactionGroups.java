package me.WiebeHero.Factions;

public class RankFactionGroups {
	public RankFactionGroup getRankFactionGroup(int rank) {
		switch(rank) {
			case 1:
				return RankFactionGroup.RECRUIT;
			case 2:
				return RankFactionGroup.MEMBER;
			case 3:
				return RankFactionGroup.OFFICER;
			case 4:
				return RankFactionGroup.LEADER;
			default:
				return RankFactionGroup.RECRUIT;	
		}
	}
	public enum RankFactionGroup {
		RECRUIT(1, "Recruit"),
		MEMBER(2, "Member"),
		OFFICER(3, "Officer"),
		LEADER(4, "Leader");
		private int rank;
		private String display;
		private RankFactionGroup(int rank, String display) {
			this.rank = rank;
			this.display = display;
		}
		public int getNumber() {
			return this.rank;
		}
		public String getDisplay() {
			return this.display;
		}
	}
}
