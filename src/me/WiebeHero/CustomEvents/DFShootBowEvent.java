package me.WiebeHero.CustomEvents;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Arrow.PickupStatus;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.DFPlayerPackage.DFPlayer;

public class DFShootBowEvent extends Event implements Cancellable{
	
	private boolean isCancelled;
	private static final HandlerList handlers = new HandlerList();
	private Player shooter;
	private ItemStack bow;
	private Projectile pr;
	private float speed;
	
	public DFShootBowEvent(Player shooter, float speed){
        this.shooter = shooter;
        this.bow = shooter.getInventory().getItemInMainHand();
		this.speed = speed;
        this.pr = null;
        this.speed = speed;
        this.isCancelled = false;
    }
	
	public DFShootBowEvent(Player shooter, float speed, Projectile pr){
        this.shooter = shooter;
        this.bow = shooter.getInventory().getItemInMainHand();
		this.speed = speed;
        this.pr = pr;
        this.speed = speed;
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
	
	public float getAttackCharge() {
		return this.speed;
	}
	
	public void shootArrow(DFPlayer dfPlayer) {
		Location loc = this.shooter.getLocation();
        loc.add(0, 1.40, 0);
		Vector direction = loc.getDirection();
		direction.add(direction);
		direction.setY(direction.getY());
		direction.normalize();
		float speed = this.speed;
		Arrow arrow = shooter.getWorld().spawnArrow(loc, direction, speed * 2.55F, 0.0F);
		arrow.setPickupStatus(PickupStatus.ALLOWED);
		if(speed == 1.00) {
			arrow.setCritical(true);
		}
		NBTItem it = new NBTItem(this.bow);
		if(it.hasKey("Attack Damage")) {
			Bukkit.broadcastMessage(it.getDouble("Attack Damage") / 100 * (speed * 100) + "");
			arrow.setDamage(it.getDouble("Attack Damage") / 100 * (speed * 100));
		}
		else {
			arrow.setDamage(5.0);
		}
		arrow.setShooter(this.shooter);
		arrow.setMetadata("AttackStrength", new FixedMetadataValue(CustomEnchantments.getInstance(), speed));
		this.shooter.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
		this.pr = arrow;
		dfPlayer.attackSpeed();
		new BukkitRunnable() {
			public void run() {
				shooter.getInventory().setItemInMainHand(bow);
				dfPlayer.attackSpeed();
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 0L);
	}

	@Override public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
	
}
