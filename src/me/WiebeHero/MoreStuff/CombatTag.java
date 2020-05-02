package me.WiebeHero.MoreStuff;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class CombatTag implements Listener {
	public ArrayList<UUID> activated = new ArrayList<UUID>();
	public static HashMap<UUID, Integer> combatTag = new HashMap<UUID, Integer>();
	public static HashMap<UUID, BukkitTask> combatRunnable = new HashMap<UUID, BukkitTask>();
	
	@EventHandler
	public void activateTag(EntityDamageByEntityEvent event) {
		if(!event.isCancelled()) {
			if(event.getDamager() instanceof Entity && event.getEntity() instanceof LivingEntity) {
				Entity damager = (Entity) event.getDamager();
				LivingEntity victim = (LivingEntity) event.getEntity();
				boolean dPlayer = false;
				boolean vPlayer = false;
				if(victim instanceof Player) {
					vPlayer = true;
				}
				if(damager instanceof Player) {
					dPlayer = true;
				}
				else if(damager instanceof Projectile) {
					Projectile proj = (Projectile) damager;
					if(proj.getShooter() instanceof Player) {
						damager = (Player)proj.getShooter();
						dPlayer = true;
					}
				}
				if(dPlayer && vPlayer) {
					Player p1 = (Player) damager;
					Player p2 = (Player) victim;
					if(combatRunnable.containsKey(p1.getUniqueId()) && combatRunnable.containsKey(p2.getUniqueId())) {
						combatTag.put(p1.getUniqueId(), 11);
						combatTag.put(p2.getUniqueId(), 11);
					}
					else {
						combatTag.put(p1.getUniqueId(), 11);
						combatTag.put(p2.getUniqueId(), 11);
						BukkitTask run1 = new BukkitRunnable() {
							@Override
							public void run() {
								int duration = combatTag.get(p1.getUniqueId());
								duration--;
								sendActionbar(p1, new CCT().colorize("&2&l[DungeonForge]: &cCombat Tag: &6" + duration));
								combatTag.put(p1.getUniqueId(), duration);
								if (duration <= 0) {
									cancel();
									combatRunnable.remove(p1.getUniqueId());
									sendActionbar(p1, new CCT().colorize("&2&l[DungeonForge]: &aOut of combat!"));
								}
							}
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
						combatRunnable.put(p1.getUniqueId(), run1);
						BukkitTask run2 = new BukkitRunnable() {
							@Override
							public void run() {
								int duration = combatTag.get(p2.getUniqueId());
								duration--;
								sendActionbar(p2, new CCT().colorize("&2&l[DungeonForge]: &cCombat Tag: &6" + duration));
								combatTag.put(p2.getUniqueId(), duration);
								if (duration <= 0) {
									cancel();
									combatRunnable.remove(p2.getUniqueId());
									sendActionbar(p2, new CCT().colorize("&2&l[DungeonForge]: &aOut of combat!"));
								}
							}
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
						combatRunnable.put(p2.getUniqueId(), run2);
					}
				}
			}
		}
	}
	@EventHandler
	public void combatTagLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (combatTag.containsKey(player.getUniqueId())) {
			if(combatTag.get(player.getUniqueId()) != 0) {
				if(combatRunnable.containsKey(player.getUniqueId())) {
					combatRunnable.get(player.getUniqueId()).cancel();
					combatRunnable.remove(player.getUniqueId());
				}
				combatTag.put(player.getUniqueId(), 0);
				sendActionbar(player, new CCT().colorize("&2&l[DungeonForge]:&aOut of combat!"));
				player.setHealth(0.00);
			}
		}
	}
	@EventHandler
	public void combatTagResetDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		player.teleport(Bukkit.getWorld("DFWarzone-1").getSpawnLocation());
		new BukkitRunnable() {
			public void run() {
				player.spigot().respawn();
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 1L);
		if(combatRunnable.containsKey(player.getUniqueId())) {
			combatRunnable.get(player.getUniqueId()).cancel();
			combatRunnable.remove(player.getUniqueId());
		}
		combatTag.put(player.getUniqueId(), 0);
		sendActionbar(player, new CCT().colorize("&2&l[DungeonForge]: &aOut of combat!"));
	}

	@EventHandler
	public void combatTagRegister(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		combatTag.put(player.getUniqueId(), 0);
	}

	public static HashMap<UUID, Integer> getCombatTag() {
		return combatTag;
	}

	public static void sendActionbar(Player player, String msg) {
		try {
			Constructor<?> constructor = getNMSClass("PacketPlayOutChat")
					.getConstructor(getNMSClass("IChatBaseComponent"), getNMSClass("ChatMessageType"));

			Object icbc = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
					.invoke(null, "{\"text\":\"" + msg + "\"}");
			Object packet = constructor.newInstance(icbc, getNMSClass("ChatMessageType").getEnumConstants()[2]);
			Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);

			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Class<?> getNMSClass(String name) {
		try {
			return Class.forName("net.minecraft.server." + getVersion() + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	}

	public boolean inCombat(UUID uuid) {
		boolean inCombat = false;
		if (combatTag.containsKey(uuid)) {
			inCombat = true;
		}
		return inCombat;
	}
}
