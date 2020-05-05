package me.WiebeHero.Spawners;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;

public class DFMobHealth implements Listener{
	private DFPlayerManager dfManager;
	private HashMap<UUID, String> oldName = new HashMap<UUID, String>();
	private HashMap<UUID, BukkitTask> runnable = new HashMap<UUID, BukkitTask>();
	public DFMobHealth(DFPlayerManager dfManager) {
		this.dfManager = dfManager;
	}
	@EventHandler
	public void entityShowHealthBar(EntityDamageEvent event) {
		if(!event.isCancelled()) {
			if(event.getEntity() instanceof LivingEntity && !(event.getEntity() instanceof Player)) {
				LivingEntity ent = (LivingEntity) event.getEntity();
				if(!ent.isDead()) {
					if(!oldName.containsKey(ent.getUniqueId())) {
						oldName.put(ent.getUniqueId(), ent.getCustomName());
					}
					new BukkitRunnable() {
						public void run() {
							double barprogress = (double) ent.getHealth() / (double) ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * 100.0;
							String loreString = "&7[&a";
							boolean canStop = true;
							for(double x = 0.00; x <= 100.00; x+= 2.50) {
								if(barprogress >= x) {
									loreString = loreString + ":";
								}
								else if(canStop) {
									loreString = loreString + "&c:";
									canStop = false;
								}
								else {
									loreString = loreString + ":";
								}
								if(x == 100) {
									loreString = loreString + "&7]";
								}
							}
							ent.setCustomName(new CCT().colorize(loreString));
							BukkitTask task = null;
							if(!runnable.containsKey(ent.getUniqueId())) {
								task = new BukkitRunnable() {
									public void run() {
										ent.setCustomName(oldName.get(ent.getUniqueId()));
										oldName.remove(ent.getUniqueId());
										runnable.remove(ent.getUniqueId());
									}
								}.runTaskLater(CustomEnchantments.getInstance(), 100L);
								runnable.put(ent.getUniqueId(), task);
							}
							else {
								runnable.get(ent.getUniqueId()).cancel();
								task = new BukkitRunnable() {
									public void run() {
										ent.setCustomName(oldName.get(ent.getUniqueId()));
										oldName.remove(ent.getUniqueId());
										runnable.remove(ent.getUniqueId());
									}
								}.runTaskLater(CustomEnchantments.getInstance(), 100L);
								runnable.put(ent.getUniqueId(), task);
							}
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 1L);
				}
			}
		}
	}
}
