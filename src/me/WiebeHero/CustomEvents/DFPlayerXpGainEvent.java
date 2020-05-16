package me.WiebeHero.CustomEvents;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Scoreboard.DFScoreboard;

public class DFPlayerXpGainEvent extends Event{
	
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private DFPlayerManager manager;
	private int totalXP;
	private int xpBefore;
	private int xpAfter;
	private boolean leveled;
	private DFPlayer dfPlayer;
	private DFScoreboard board;
	private double xpMultiplier;
	
	public DFPlayerXpGainEvent(Player leveled, int totalXP, DFPlayerManager manager, DFScoreboard board){
        this.player = leveled;
        this.manager = manager;
        this.totalXP = totalXP;
        this.xpMultiplier = 100.00;
        this.dfPlayer = manager.getEntity(player.getUniqueId());
        this.xpBefore = dfPlayer.getExperience();
        this.board = board;
    }
	
	public void proceed() {
		if(this.dfPlayer.getLevel() < 100) {
	        this.dfPlayer.addExperience(totalXP / 100 * (int)this.xpMultiplier);
	        this.manager.addEntity(player.getUniqueId(), this.dfPlayer);
	        if(this.dfPlayer.getExperience() >= this.dfPlayer.getMaxExperience()) {
	        	this.leveled = true;
	        	DFPlayerLevelUpEvent event = new DFPlayerLevelUpEvent(player, manager, this.board);
				Bukkit.getServer().getPluginManager().callEvent(event);
	        }
	        else {
	        	float barprogress = (float) this.dfPlayer.getExperience() / this.dfPlayer.getMaxExperience();
	    		if(barprogress > 1) {
	    			player.setLevel(this.dfPlayer.getLevel());
	    			player.setExp((float)barprogress - 1.0F);
	    		}
	    		else if(barprogress >= 0 && barprogress <= 1) {
	    			player.setLevel(this.dfPlayer.getLevel());
	    			player.setExp((float)barprogress);
	    		}
	    		else {
	    			player.setLevel(this.dfPlayer.getLevel());
	    			player.setExp(0.0F);
	    		}
	        	this.leveled = false;
	        }
        }
        else {
        	this.dfPlayer.setExperience(0);
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
	
	public Player getPlayer() {
		return this.player;
	}
	
	public DFPlayer getDFPlayer() {
		return this.dfPlayer;
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