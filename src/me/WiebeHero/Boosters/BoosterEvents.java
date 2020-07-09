package me.WiebeHero.Boosters;

import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.Boosters.BoosterTypes.BoosterType;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEvents.DFPlayerXpGainEvent;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Factions.DFFactionManager;
import me.WiebeHero.Factions.DFFactionPlayer;
import me.WiebeHero.Factions.DFFactionPlayerManager;

public class BoosterEvents implements Listener{
	
	private BoosterManager bManager;
	private DFFactionManager facManager;
	private DFFactionPlayerManager facPlayerManager;
	
	public BoosterEvents(BoosterManager bManager, DFFactionManager facManager, DFFactionPlayerManager facPlayerManager) {
		this.bManager = bManager;
		this.facManager = facManager;
		this.facPlayerManager = facPlayerManager;
	}
	
	@EventHandler
	public void activateBooster(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item != null) {
				NBTItem i = new NBTItem(item);
				if(i.hasKey("Booster")) {
					BoosterType type = i.getObject("BoosterType", BoosterType.class);
					UUID owner = player.getUniqueId();
					boolean inUse = false;
					String errorMessage = "";
					String returnMessage = "";
					switch(type) {
						case PERSONAL:
							if(this.bManager.isPersonalActive(owner)) {
								inUse = true;
								errorMessage = "&2&l[DungeonForge]: &cYou already have a personal booster in use!";
							}
							else {
								returnMessage = "&2&l[DungeonForge]: &aYou have activated a personal booster!";
							}
							break;
						case FACTION:
							DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(owner);
							if(facPlayer.getFactionId() != null) {
								DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
								if(this.bManager.isFactionActive(faction)) {
									inUse = true;
									errorMessage = "&2&l[DungeonForge]: &cThere already is a faction booster in use!";
								}
								else {
									returnMessage = "&2&l[DungeonForge]: &aYou have activated a personal booster!";
								}
							}
							break;
						case ALL:
							if(this.bManager.isAllActive()) {
								inUse = true;
								errorMessage = "&2&l[DungeonForge]: &cThere already is a public booster in use!";
							}
							else {
								returnMessage = "&2&l[DungeonForge]: &aYou have activated a personal booster!";
							}
							break;
						default: 
							break;
					}
					if(!inUse) {
						double increase = i.getDouble("Increase");
						long duration = i.getLong("Duration");
						Booster booster = new Booster(owner, type, increase, System.currentTimeMillis() + duration);
						bManager.addBooster(booster);
						player.sendMessage(new CCT().colorize(returnMessage));
						item.setAmount(item.getAmount() - 1);
						player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2.0F, 2.0F);
					}
					else {
						player.sendMessage(new CCT().colorize(errorMessage));
					}
				}
			}
		}
	}
	
	@EventHandler
	public void earnExperience(DFPlayerXpGainEvent event) {
		Player player = event.getPlayer();
		UUID owner = player.getUniqueId();
		double multiplier = event.getXPMultiplier();
		DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(owner);
		Booster personal = this.bManager.getPersonalBooster(owner);
		if(personal != null) {
			multiplier += personal.getMultiplier();
		}
		if(facPlayer.getFactionId() != null) {
			DFFaction faction = this.facManager.getFaction(facPlayer.getFactionId());
			Booster fac = this.bManager.getFactionBooster(faction);
			if(fac != null) {
				multiplier += fac.getMultiplier();
			}
		}
		Booster all = this.bManager.getAllBooster();
		if(all != null) {
			multiplier += all.getMultiplier();
		}
		event.setXPMultiplier(multiplier);
	}
	
	@EventHandler
	public void disableClickBoosterInv(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		InventoryView view = player.getOpenInventory();
		if(view.getTitle().contains("Active Boosters")) {
			event.setCancelled(true);
		}
	}
}
