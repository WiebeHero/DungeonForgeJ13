package me.WiebeHero.MoreStuff;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class CombatTag implements Listener{
	public ArrayList<UUID> activated = new ArrayList<UUID>();
	public static HashMap<UUID, Integer> combatTag = new HashMap<UUID, Integer>();
	
	@EventHandler
	public void combatTagActivate(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			if(event.getEntity() instanceof Monster) {
				Player damager = (Player) event.getDamager();
				combatTag.put(damager.getUniqueId(), 10);
				if(!activated.contains(damager.getUniqueId())) {
					damager.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
					activated.add(damager.getUniqueId());
					new BukkitRunnable() {
						@Override
						public void run() {
							int duration = combatTag.get(damager.getUniqueId());
							duration--;
							sendActionbar(damager, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
							combatTag.put(damager.getUniqueId(), duration);
							if(duration <= 0) {
								cancel();
								activated.remove(damager.getUniqueId());
								sendActionbar(damager, new ColorCodeTranslator().colorize("&aOut of combat!"));
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
				}
			}
		}
		else if(event.getDamager() instanceof Monster) {
			if(event.getEntity() instanceof Player) {
				Player victim = (Player) event.getEntity();
				combatTag.put(victim.getUniqueId(), 10);
				if(!activated.contains(victim.getUniqueId())) {
					victim.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
					activated.add(victim.getUniqueId());
					new BukkitRunnable() {
						@Override
						public void run() {
							int duration = combatTag.get(victim.getUniqueId());
							duration--;
							sendActionbar(victim, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
							combatTag.put(victim.getUniqueId(), duration);
							if(duration <= 0) {
								cancel();
								activated.remove(victim.getUniqueId());
								sendActionbar(victim, new ColorCodeTranslator().colorize("&aOut of combat!"));
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
				}
			}
		}
		else if(event.getDamager() instanceof Player) {
			if(event.getEntity() instanceof Player) {
				Player damager = (Player) event.getDamager();
				Player victim = (Player) event.getEntity();
				combatTag.put(damager.getUniqueId(), 20);
				combatTag.put(victim.getUniqueId(), 20);
				if(!activated.contains(damager.getUniqueId()) && !activated.contains(victim.getUniqueId())) {
					damager.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
					victim.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
					activated.add(damager.getUniqueId());
					activated.add(victim.getUniqueId());
					new BukkitRunnable() {
						@Override
						public void run() {
							int duration = combatTag.get(damager.getUniqueId());
							duration--;
							sendActionbar(damager, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
							combatTag.put(damager.getUniqueId(), duration);
							if(duration <= 0) {
								cancel();
								activated.remove(damager.getUniqueId());
								sendActionbar(damager, new ColorCodeTranslator().colorize("&aOut of combat!"));
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
					new BukkitRunnable() {
						@Override
						public void run() {
							int duration = combatTag.get(victim.getUniqueId());
							duration--;
							sendActionbar(victim, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
							combatTag.put(victim.getUniqueId(), duration);
							if(duration <= 0) {
								cancel();
								activated.remove(victim.getUniqueId());
								sendActionbar(victim, new ColorCodeTranslator().colorize("&aOut of combat!"));
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
				}
			}
		}
		else if(event.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getDamager();
			if(arrow.getShooter() instanceof Player) {
				Player damager = (Player) arrow.getShooter();
				if(event.getEntity() instanceof LivingEntity) {
					combatTag.put(damager.getUniqueId(), 20);
					if(!activated.contains(damager.getUniqueId())) {
						damager.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
						activated.add(damager.getUniqueId());
						new BukkitRunnable() {
							@Override
							public void run() {
								int duration = combatTag.get(damager.getUniqueId());
								duration--;
								sendActionbar(damager, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
								combatTag.put(damager.getUniqueId(), duration);
								if(duration <= 0) {
									cancel();
									activated.remove(damager.getUniqueId());
									sendActionbar(damager, new ColorCodeTranslator().colorize("&aOut of combat!"));
								}
							}
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
					}
				}
				else if(event.getEntity() instanceof Player) {
					Player victim = (Player) event.getEntity();
					combatTag.put(damager.getUniqueId(), 20);
					combatTag.put(victim.getUniqueId(), 20);
					if(!activated.contains(damager.getUniqueId()) && !activated.contains(victim.getUniqueId())) {
						damager.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
						victim.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
						activated.add(damager.getUniqueId());
						activated.add(victim.getUniqueId());
						new BukkitRunnable() {
							@Override
							public void run() {
								int duration = combatTag.get(damager.getUniqueId());
								duration--;
								sendActionbar(damager, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
								combatTag.put(damager.getUniqueId(), duration);
								if(duration <= 0) {
									cancel();
									activated.remove(damager.getUniqueId());
									sendActionbar(damager, new ColorCodeTranslator().colorize("&aOut of combat!"));
								}
							}
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
						new BukkitRunnable() {
							@Override
							public void run() {
								int duration = combatTag.get(victim.getUniqueId());
								duration--;
								sendActionbar(victim, new ColorCodeTranslator().colorize("&cCombat Tag: &" + duration));
								combatTag.put(victim.getUniqueId(), duration);
								if(duration <= 0) {
									cancel();
									activated.remove(victim.getUniqueId());
									sendActionbar(victim, new ColorCodeTranslator().colorize("&aOut of combat!"));
								}
							}
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
					}
				}
			}
			else {
				if(arrow.getShooter() instanceof Player) {
					Player damager1 = (Player) arrow.getShooter();
					if(event.getEntity() instanceof LivingEntity) {
						combatTag.put(damager1.getUniqueId(), 20);
						if(!activated.contains(damager1.getUniqueId())) {
							damager1.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
							activated.add(damager1.getUniqueId());
							new BukkitRunnable() {
								@Override
								public void run() {
									int duration = combatTag.get(damager1.getUniqueId());
									duration--;
									sendActionbar(damager1, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
									combatTag.put(damager1.getUniqueId(), duration);
									if(duration <= 0) {
										cancel();
										activated.remove(damager1.getUniqueId());
										sendActionbar(damager1, new ColorCodeTranslator().colorize("&aOut of combat!"));
									}
								}
							}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
						}
					}
					else if(event.getEntity() instanceof Player) {
						Player victim = (Player) event.getEntity();
						combatTag.put(damager1.getUniqueId(), 20);
						combatTag.put(victim.getUniqueId(), 20);
						if(!activated.contains(damager1.getUniqueId()) && !activated.contains(victim.getUniqueId())) {
							damager1.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
							victim.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
							activated.add(damager1.getUniqueId());
							activated.add(victim.getUniqueId());
							new BukkitRunnable() {
								@Override
								public void run() {
									int duration = combatTag.get(damager1.getUniqueId());
									duration--;
									sendActionbar(damager1, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
									combatTag.put(damager1.getUniqueId(), duration);
									if(duration <= 0) {
										cancel();
										activated.remove(damager1.getUniqueId());
										sendActionbar(damager1, new ColorCodeTranslator().colorize("&aOut of combat!"));
									}
								}
							}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
							new BukkitRunnable() {
								@Override
								public void run() {
									int duration = combatTag.get(victim.getUniqueId());
									duration--;
									sendActionbar(victim, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
									combatTag.put(victim.getUniqueId(), duration);
									if(duration <= 0) {
										cancel();
										activated.remove(victim.getUniqueId());
										sendActionbar(victim, new ColorCodeTranslator().colorize("&aOut of combat!"));
									}
								}
							}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
						}
					}
				}
				else if(arrow.getShooter() instanceof LivingEntity) {
					
				}
			}
		}
	}
	@EventHandler
	public void combatTagLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(combatTag.containsKey(player.getUniqueId())) {
			if(combatTag.get(player.getUniqueId()) != 0) {
				player.setHealth(0.00);
				combatTag.put(player.getUniqueId(), 0);
			}
		}
	}
	@EventHandler
	public void combatTagResetDeath(PlayerDeathEvent event) {
		new BukkitRunnable() {
			public void run() {
				Player player = event.getEntity();
				combatTag.put(player.getUniqueId(), 0);
				player.teleport(Bukkit.getWorld("DFWarzone-1").getSpawnLocation());
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 1L);
	}
	@EventHandler
	public void combatTagResetDeath(PlayerRespawnEvent event) {
		new BukkitRunnable() {
			public void run() {
				Player player = event.getPlayer();
				combatTag.put(player.getUniqueId(), 0);
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 1L);
	}
	@EventHandler
	public void combatTagRegister(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		combatTag.put(player.getUniqueId(), 0);
	}
	public static HashMap<UUID, Integer> getCombatTag(){
		return combatTag;
	}
	public static void sendActionbar(Player player, String msg) {
        try {
            Constructor<?> constructor = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), getNMSClass("ChatMessageType"));
               
            Object icbc = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + msg + "\"}");
            Object packet = constructor.newInstance(icbc, getNMSClass("ChatMessageType").getEnumConstants()[2]);
            Object entityPlayer= player.getClass().getMethod("getHandle").invoke(player);
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
    	if(combatTag.containsKey(uuid)) {
    		inCombat = true;
    	}
    	return inCombat;
    }
}
