package me.WiebeHero.DailyRewards;

public class DailyRewardEnum {
	
	public enum LootTable{
		COMMON,
		RARE,
		VERY_RARE,
		SUPER_RARE,
		INSANELY_RARE,
		NONE;
		
		private float rate;
		
		public LootTable setRate(float rate) {
			this.rate = rate;
			return this;
		}
		
		public float getRate() {
			return this.rate;
		}
	}
	
}
