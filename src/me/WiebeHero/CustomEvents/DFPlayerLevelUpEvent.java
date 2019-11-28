package me.WiebeHero.CustomEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DFPlayerLevelUpEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	
	public DFPlayerLevelUpEvent(Player leveled){
        this.player = leveled;
    }
	
	public Player getPlayer() {
		return this.player;
	}

	@Override public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
	
}
