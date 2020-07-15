package me.WiebeHero.CustomEvents;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Factions.DFFactionManager;
import me.WiebeHero.Factions.DFFactionPlayerManager;

public class DFFactonXpGainEvent extends Event{
	
	private static final HandlerList handlers = new HandlerList();
	private UUID leveledFaction;
	private DFFactionManager manager;
	private DFFactionPlayerManager facPlayerManager;
	private int totalXP;
	private int xpBefore;
	private int xpAfter;
	private boolean leveled;
	private DFFaction faction;
	private double xpMultiplier;
	
	public DFFactonXpGainEvent(UUID leveledFaction, int totalXP, DFFactionManager manager, DFFactionPlayerManager facPlayerManager){
        this.leveledFaction = leveledFaction;
        this.manager = manager;
        this.totalXP = totalXP;
        this.xpMultiplier = 100.00;
        this.faction = manager.getFaction(this.leveledFaction);
        this.xpBefore = faction.getExperience();
        this.facPlayerManager = facPlayerManager;
    }
	
	public void proceed() {
		if(this.faction.getLevel() < 100) {
	        this.faction.addExperience((int)((double)totalXP / 100.00 * (double)this.xpMultiplier));
	        this.manager.add(this.faction);
	        if(this.faction.getExperience() >= this.faction.getMaxExperience()) {
	        	this.leveled = true;
	        	DFFactionLevelUpEvent event = new DFFactionLevelUpEvent(leveledFaction, manager, facPlayerManager);
				Bukkit.getServer().getPluginManager().callEvent(event);
	        }
        }
        else {
        	this.faction.setExperience(0);
        }
	}
	
	public int getXPBefore() {
		return this.xpBefore;
	}
	
	public int getXPAfter() {
		return this.xpAfter;
	}
	
	public int getXPEarned() {
		return this.totalXP;
	}
	
	public boolean leveledUp() {
		return this.leveled;
	}
	
	public UUID getPlayer() {
		return this.leveledFaction;
	}
	
	public DFFaction getFaction() {
		return this.faction;
	}
	
	public double getXPMultiplier() {
		return this.xpMultiplier;
	}
	
	public void setXPMultiplier(double xpMultiplier) {
		this.xpMultiplier = xpMultiplier;
	}

	@Override public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
}
