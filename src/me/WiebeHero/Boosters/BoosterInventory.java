package me.WiebeHero.Boosters;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Factions.DFFactionManager;
import me.WiebeHero.Factions.DFFactionPlayer;
import me.WiebeHero.Factions.DFFactionPlayerManager;

public class BoosterInventory {
	
	private ItemStackBuilder builder;
	private BoosterManager bManager;
	private DFFactionManager facManager;
	private DFFactionPlayerManager facPlayerManager;
	public BoosterInventory(ItemStackBuilder builder, BoosterManager bManager, DFFactionManager facManager, DFFactionPlayerManager facPlayerManager) {
		this.builder = builder;
		this.bManager = bManager;
		this.facManager = facManager;
		this.facPlayerManager = facPlayerManager;
	}
	
	public void ActiveBoosters(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Active Boosters")));
		ItemStack nothing = this.builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				1,
				" "
		);
		for(int x = 0; x < i.getSize(); x++) {
			i.setItem(x, nothing);
		}
		Booster personal = this.bManager.getPersonalBooster(player.getUniqueId());
		boolean pNull = personal == null;
		DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player);
		DFFaction fac = null;
		if(facPlayer.getFactionId() != null) {
			fac = this.facManager.getFaction(facPlayer.getFactionId());
		}
		Booster faction = this.bManager.getFactionBooster(fac);
		boolean fNull = faction == null;
		Booster all = this.bManager.getAllBooster();
		boolean aNull = all == null;
		long pTime = pNull ? 0L : personal.getEndTime() - System.currentTimeMillis();
		long diffSeconds = pTime / 1000 % 60;
        long diffMinutes = pTime / (60 * 1000) % 60;
        long diffHours = pTime / (60 * 60 * 1000) % 24;
	    String time = diffHours + ":" + diffMinutes + ":" + diffSeconds;
		i.setItem(12, this.builder.constructItem(
				pNull ? Material.RED_STAINED_GLASS_PANE : Material.LIME_STAINED_GLASS_PANE,
				1,
				pNull ? "&7Personal Booster: &cNot active" : "&7Personal Booster: &aBooster active",
				pNull ? new String[] {} :
				new String[] {
						"&7Booster Owner: &6" + Bukkit.getOfflinePlayer(personal.getOwner()).getName(),
						"&7Booster Multiplier: &a" + personal.getMultiplier() + "%",
						"&7Booster Duration: &b" + time
				}
		));
		long fTime = fNull ? 0L : faction.getEndTime() - System.currentTimeMillis();
		diffSeconds = fTime / 1000 % 60;
        diffMinutes = fTime / (60 * 1000) % 60;
        diffHours = fTime / (60 * 60 * 1000) % 24;
	    time = diffHours + ":" + diffMinutes + ":" + diffSeconds;
		i.setItem(13, this.builder.constructItem(
				fNull ? Material.RED_STAINED_GLASS_PANE : Material.LIME_STAINED_GLASS_PANE,
				1,
				fNull ? "&7Faction Booster: &cNot active" : "&7Faction Booster: &aBooster active",
				fNull ? new String[] {} :
				new String[] {
						"&7Booster Owner: &6" + Bukkit.getOfflinePlayer(faction.getOwner()).getName(),
						"&7Booster Multiplier: &a" + faction.getMultiplier() + "%",
						"&7Booster Duration: &b" + time
				}
		));
		long aTime = aNull ? 0L : all.getEndTime() - System.currentTimeMillis();
		diffSeconds = aTime / 1000 % 60;
        diffMinutes = aTime / (60 * 1000) % 60;
        diffHours = aTime / (60 * 60 * 1000) % 24;
	    time = diffHours + ":" + diffMinutes + ":" + diffSeconds;
		i.setItem(14, this.builder.constructItem(
				aNull ? Material.RED_STAINED_GLASS_PANE : Material.LIME_STAINED_GLASS_PANE,
				1,
				aNull ? "&7Global Booster: &cNot active" : "&7Global Booster: &aBooster active",
				aNull ? new String[] {} :
				new String[] {
						"&7Booster Owner: &6" + Bukkit.getOfflinePlayer(all.getOwner()).getName(),
						"&7Booster Multiplier: &a" + all.getMultiplier() + "%",
						"&7Booster Duration: &b" + time
				}
		));
		player.openInventory(i);
	}
}
