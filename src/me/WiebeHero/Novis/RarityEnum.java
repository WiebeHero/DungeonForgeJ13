package me.WiebeHero.Novis;

import net.md_5.bungee.api.ChatColor;

public class RarityEnum {
	public enum Rarity {
		COMMON("§7", ChatColor.GRAY),
		RARE("§a", ChatColor.GREEN),
		EPIC("§b", ChatColor.AQUA),
		LEGENDARY("§c", ChatColor.RED),
		MYTHIC("&5", ChatColor.DARK_PURPLE),
		HEROIC("&e", ChatColor.YELLOW);
		private String colorCode;
		private ChatColor color;
		Rarity(String colorCode, ChatColor color) {
			this.colorCode = colorCode;
			this.color = color;
		}
		public String getColorCode() {
			return this.colorCode;
		}
		public ChatColor getChatColor() {
			return this.color;
		}
	}
}
