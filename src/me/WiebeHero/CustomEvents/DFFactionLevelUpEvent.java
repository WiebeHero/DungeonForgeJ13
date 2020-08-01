package me.WiebeHero.CustomEvents;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.destroystokyo.paper.Title;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Factions.DFFactionManager;
import me.WiebeHero.Factions.DFFactionPlayer;
import me.WiebeHero.Factions.DFFactionPlayerManager;

public class DFFactionLevelUpEvent extends Event{
	
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private UUID leveledFaction;
	private DFFactionManager manager;
	private DFFactionPlayerManager facPlayerManager;
	private DFFaction fac;
	private int oldLevel;
	private int newLevel;
	private ArrayList<Integer> levels;
	
	public DFFactionLevelUpEvent(Player p, UUID levelFaction, DFFactionManager manager, DFFactionPlayerManager facPlayerManager){
		this.player = p;
        this.leveledFaction = levelFaction;
        this.manager = manager;
        this.facPlayerManager = facPlayerManager;
        this.levels = new ArrayList<Integer>();
        this.fac = this.manager.getFaction(this.leveledFaction);
        if(this.fac != null) {
	        this.oldLevel = fac.getLevel();
	        int times = this.fac.getExperience() / this.fac.getMaxExperience();
	        for(int i = 1; i <= times; i++) {
		        if(this.fac.getLevel() < 50) {
					if(this.fac.getExperience() >= this.fac.getMaxExperience()) {
						this.fac.addLevel(1);
						this.levels.add(this.fac.getLevel());
						this.fac.setExperience(this.fac.getExperience() - this.fac.getMaxExperience());
						this.fac.setMaxExperience((int)(double)(this.fac.getMaxExperience() / 100.00 * (this.fac.getExperienceMultiplier() * 100.00)));
					}
				}
	        }
	        this.newLevel = this.fac.getLevel();
	        for(Entry<UUID, DFFactionPlayer> entry : this.facPlayerManager.getFactionPlayerMap().entrySet()) {
	        	Player player = Bukkit.getPlayer(entry.getKey());
	        	if(player != null) {
			        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, (float) 0.5);
			        player.sendTitle(new Title(new CCT().colorize("&aLEVEL UP!"), new CCT().colorize("&7Faction Level: &6" + this.oldLevel + " &7-> &6" + this.newLevel), 15, 80, 40));
	        	}
	        }
        }
    }
	
	public int getOldLevel() {
		return this.oldLevel;
	}
	
	public int getNewLevel() {
		return this.newLevel;
	}
	
	public ArrayList<Integer> getLevelsPassed() {
		return this.levels;
	}
	
	public DFFaction getFaction() {
		return this.fac;
	}
	
	public Player getPlayer() {
		return this.player;
	}

	@Override public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
}
