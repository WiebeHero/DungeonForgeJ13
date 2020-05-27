package me.WiebeHero.Novis;

import com.google.common.base.Enums;

import net.md_5.bungee.api.ChatColor;

public class RarityEnum {
	public static Rarity getIfPresent(Rarity rarity) {
		return Enums.getIfPresent(Rarity.class, rarity.toString().toUpperCase()).orNull();
	}
	public static Rarity getIfPresent(String rarity) {
		return Enums.getIfPresent(Rarity.class, rarity.toUpperCase()).orNull();
	}
	public enum Rarity {
		COMMON("§7", ChatColor.GRAY, "Common"),
		RARE("§a", ChatColor.GREEN, "Rare"),
		EPIC("§b", ChatColor.AQUA, "Epic"),
		LEGENDARY("§c", ChatColor.RED, "Legendary"),
		MYTHIC("&5", ChatColor.DARK_PURPLE, "Mythic"),
		HEROIC("&e", ChatColor.YELLOW, "Heroic");
		private String colorCode;
		private ChatColor color;
		private String display;
		Rarity(String colorCode, ChatColor color, String display) {
			this.colorCode = colorCode;
			this.color = color;
			this.display = display;
		}
		public String getColorCode() {
			return this.colorCode;
		}
		public ChatColor getChatColor() {
			return this.color;
		}
		public String getDisplay() {
			return this.display;
		}
	}
}
