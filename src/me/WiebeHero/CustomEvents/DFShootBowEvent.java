package me.WiebeHero.CustomEvents;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class DFShootBowEvent extends Event implements Cancellable{
	
	private boolean isCancelled;
	private static final HandlerList handlers = new HandlerList();
	private Player shooter;
	private ItemStack bow;
	private Projectile pr;
	
	public DFShootBowEvent(Player shooter, ItemStack bow, Projectile pr){
        this.shooter = shooter;
        this.bow = bow;
        this.pr = pr;
        this.isCancelled = false;
    }
	
	@Override
	public boolean isCancelled() {
		return this.isCancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.isCancelled = arg0;
	}
	
	public Player getShooter() {
		return this.shooter;
	}
	
	public ItemStack getBow() {
		return this.bow;
	}
	
	public Projectile getProjectile() {
		return this.pr;
	}

	@Override public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
	
}
