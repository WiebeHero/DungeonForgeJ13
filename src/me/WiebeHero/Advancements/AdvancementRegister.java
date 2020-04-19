package me.WiebeHero.Advancements;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.NameKey;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomEvents.DFPlayerLevelUpEvent;

public class AdvancementRegister implements Listener{
	
	private Advancements advancements;
	
	public AdvancementRegister(Advancements advancements) {
		this.advancements = advancements;
	}
	
//	@EventHandler
//	public void playerJoin(PlayerJoinEvent event) {
//		Player player = event.getPlayer();
//		if(player != null) {
//			if(!this.advancements.getAdvancementManager().getPlayers().contains(player)) {
//				new BukkitRunnable() {
//					public void run() {
//						Advancement advancement = advancements.getAdvancementManager().getAdvancement(new NameKey("dungeonforge_basic", "join"));
//						Advancement advancementFac = advancements.getAdvancementManager().getAdvancement(new NameKey("dungeonforge_factions", "join"));
//						advancements.getAdvancementManager().addPlayer(player);
//						advancements.getAdvancementManager().grantAdvancement(player, advancement);
//						advancements.getAdvancementManager().grantAdvancement(player, advancementFac);
//					}
//				}.runTaskLater(CustomEnchantments.getInstance(), 2L);
//			}
//		}
//	}
//	@EventHandler
//	public void playerLevelUp(DFPlayerLevelUpEvent event) {
//		Player player = event.getPlayer();
//		HashMap<String, Advancement> advancement = this.advancements.getAdancementsEasy(); 
//		for(int level : event.getLevelsPassed()) {
//			if(advancement.containsKey("Level Up " + level)) {
//				Advancement child = advancement.get("Level Up " + level);
//				Advancement parent = child.getParent();
//				if(parent.isDone(player)) {
//					new BukkitRunnable() {
//						public void run() {
//							advancements.getAdvancementManager().grantAdvancement(player, child);
//						}
//					}.runTaskLater(CustomEnchantments.getInstance(), 2L);
//				}
//			}
//		}	
//	}
}
