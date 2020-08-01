package me.WiebeHero.XpTrader;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEvents.DFFactonXpGainEvent;
import me.WiebeHero.CustomEvents.DFPlayerXpGainEvent;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Factions.DFFactionManager;
import me.WiebeHero.Factions.DFFactionPlayer;
import me.WiebeHero.Factions.DFFactionPlayerManager;
import me.WiebeHero.Scoreboard.DFScoreboard;

public class XPAddPlayers implements Listener {
	private DFScoreboard board;
	private DFPlayerManager dfManager;
	private DFFactionManager facManager;
	private DFFactionPlayerManager facPlayerManager;
	public XPAddPlayers(DFPlayerManager dfManager, DFFactionPlayerManager facPlayerManager, DFFactionManager facManager, DFScoreboard board) {
		this.board = board;
		this.dfManager = dfManager;
		this.facManager = facManager;
		this.facPlayerManager = facPlayerManager;
	}
	@EventHandler
	public void xpAddPlayer(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item != null && item.getType() != Material.AIR) {
				NBTItem i = new NBTItem(item);
				if(i.hasKey("XPBottle")) {
					int xpAdd = i.getInteger("XPBottle");
					DFPlayerXpGainEvent ev = new DFPlayerXpGainEvent(player, xpAdd, this.dfManager, this.board);
					Bukkit.getServer().getPluginManager().callEvent(ev);
					ev.proceed();
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 2, (float) 1.0);
					player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
				}
				else if(i.hasKey("FXPBottle")) {
					DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
					if(facPlayer != null) {
						if(facPlayer.getFactionId() != null) {
							DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
							if(faction != null) {
								int xpAdd = i.getInteger("FXPBottle");
								DFFactonXpGainEvent ev = new DFFactonXpGainEvent(player, faction.getFactionId(), xpAdd, this.facManager, this.facPlayerManager);
								Bukkit.getServer().getPluginManager().callEvent(ev);
								ev.proceed();
								player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 2, (float) 1.0);
								player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
							}
						}
					}
				}
				else if(i.hasKey("RandomXPBottle")) {
					int xpMin = i.getInteger("Min");
					int xpMax = i.getInteger("Max");
					int xpAdd = new Random().nextInt(xpMax) + xpMin;
					DFPlayerXpGainEvent ev = new DFPlayerXpGainEvent(player, xpAdd, this.dfManager, this.board);
					Bukkit.getServer().getPluginManager().callEvent(ev);
					ev.proceed();
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 2, (float) 1.0);
					player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
				}
			}
		}
	}
}
