package me.WiebeHero.CustomEvents;

import java.util.ArrayList;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.destroystokyo.paper.Title;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Scoreboard.DFScoreboard;

public class DFPlayerLevelUpEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private DFPlayerManager manager;
	private DFPlayer dfPlayer;
	private DFScoreboard board;
	private int oldLevel;
	private int newLevel;
	private ArrayList<Integer> levels;
	
	public DFPlayerLevelUpEvent(Player leveled, DFPlayerManager manager, DFScoreboard board){
        this.player = leveled;
        this.manager = manager;
        this.board = board;
        this.levels = new ArrayList<Integer>();
        this.dfPlayer = this.manager.getEntity(player.getUniqueId());
        this.oldLevel = this.dfPlayer.getLevel();
        int times = this.dfPlayer.getExperience() / this.dfPlayer.getMaxExperience();
        for(int i = 1; i <= times; i++) {
	        if(this.dfPlayer.getLevel() < 100) {
				if(this.dfPlayer.getExperience() >= this.dfPlayer.getMaxExperience()) {
					this.dfPlayer.addLevel(1);
					this.levels.add(this.dfPlayer.getLevel());
					this.dfPlayer.setExperience(this.dfPlayer.getExperience() - this.dfPlayer.getMaxExperience());
					this.dfPlayer.setMaxExperience((int)(double)(this.dfPlayer.getMaxExperience() / 100.00 * (this.dfPlayer.getExperienceMultiplier() * 100.00)));
					this.dfPlayer.addSkillPoints(3);
				}
			}
        }
        this.newLevel = this.dfPlayer.getLevel();
        this.player.getWorld().playSound(this.player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, (float) 0.5);
		this.board.updateScoreboard(this.player);
        float barprogress = (float) this.dfPlayer.getExperience() / this.dfPlayer.getMaxExperience();
		if(barprogress > 1) {
			this.player.setLevel(this.dfPlayer.getLevel());
			this.player.setExp((float)barprogress - 1.0F);
		}
		else if(barprogress >= 0 && barprogress <= 1) {
			this.player.setLevel(this.dfPlayer.getLevel());
			this.player.setExp((float)barprogress);
		}
		else {
			this.player.setLevel(this.dfPlayer.getLevel());
			this.player.setExp(0.0F);
		}
		this.player.sendTitle(new Title(new CCT().colorize("&aLEVEL UP!"), new CCT().colorize("&7Player Level: &6" + this.oldLevel + " &7-> &6" + this.newLevel), 15, 80, 40));
		this.dfPlayer.resetAbilityStats();
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
	
	public Player getPlayer() {
		return this.player;
	}

	@Override public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
	
}
