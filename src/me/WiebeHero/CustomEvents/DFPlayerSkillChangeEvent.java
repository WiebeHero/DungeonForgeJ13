package me.WiebeHero.CustomEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.SkillEnum.Skills;

public class DFPlayerSkillChangeEvent extends Event{
	
	private static final HandlerList handlers = new HandlerList();
	private DFPlayer dfPlayer;
	private int newLevel;
	private int oldLevel;
	private Skills skill;
	
	public DFPlayerSkillChangeEvent(DFPlayer dfPlayer, int newLevel, int oldLevel, Skills skill){
		this.dfPlayer = dfPlayer;
		this.newLevel = newLevel;
		this.oldLevel = oldLevel;
		this.skill = skill;
    }
	
	public int getOldLevel() {
		return this.oldLevel;
	}
	
	public int getNewLevel() {
		return this.newLevel;
	}
	
	public DFPlayer getDFPlayer() {
		return this.dfPlayer;
	}
	
	public Skills getSkill() {
		return this.skill;
	}

	@Override public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
}