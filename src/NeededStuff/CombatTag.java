package NeededStuff;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

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
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class CombatTag implements Listener{
	public ArrayList<String> activated = new ArrayList<String>();
	public static HashMap<String, Integer> combatTag = new HashMap<String, Integer>();
	
	@EventHandler
	public void combatTagActivate(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			if(event.getEntity() instanceof Monster) {
				Player damager = (Player) event.getDamager();
				combatTag.put(damager.getName(), 10);
				if(!activated.contains(damager.getName())) {
					damager.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
					activated.add(damager.getName());
					new BukkitRunnable() {
						@Override
						public void run() {
							int duration = combatTag.get(damager.getName());
							duration--;
							sendActionbar(damager, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
							combatTag.put(damager.getName(), duration);
							if(duration == 0) {
								cancel();
								activated.remove(damager.getName());
								sendActionbar(damager, "&aOut of combat!");
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
				}
			}
		}
		else if(event.getDamager() instanceof Monster) {
			if(event.getEntity() instanceof Player) {
				Player victim = (Player) event.getEntity();
				combatTag.put(victim.getName(), 10);
				if(!activated.contains(victim.getName())) {
					victim.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
					activated.add(victim.getName());
					new BukkitRunnable() {
						@Override
						public void run() {
							int duration = combatTag.get(victim.getName());
							duration--;
							sendActionbar(victim, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
							combatTag.put(victim.getName(), duration);
							if(duration == 0) {
								cancel();
								activated.remove(victim.getName());
								sendActionbar(victim, "&aOut of combat!");
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
				combatTag.put(damager.getName(), 20);
				combatTag.put(victim.getName(), 20);
				if(!activated.contains(damager.getName()) && !activated.contains(victim.getName())) {
					damager.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
					victim.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
					activated.add(damager.getName());
					activated.add(victim.getName());
					new BukkitRunnable() {
						@Override
						public void run() {
							int duration = combatTag.get(damager.getName());
							duration--;
							sendActionbar(damager, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
							combatTag.put(damager.getName(), duration);
							if(duration == 0) {
								cancel();
								activated.remove(damager.getName());
								sendActionbar(damager, "&aOut of combat!");
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
					new BukkitRunnable() {
						@Override
						public void run() {
							int duration = combatTag.get(victim.getName());
							duration--;
							sendActionbar(victim, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
							combatTag.put(victim.getName(), duration);
							if(duration == 0) {
								cancel();
								activated.remove(victim.getName());
								sendActionbar(victim, "&aOut of combat!");
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
					combatTag.put(damager.getName(), 20);
					if(!activated.contains(damager.getName())) {
						damager.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
						activated.add(damager.getName());
						new BukkitRunnable() {
							@Override
							public void run() {
								int duration = combatTag.get(damager.getName());
								duration--;
								sendActionbar(damager, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
								combatTag.put(damager.getName(), duration);
								if(duration == 0) {
									cancel();
									activated.remove(damager.getName());
									sendActionbar(damager, "&aOut of combat!");
								}
							}
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
					}
				}
				else if(event.getEntity() instanceof Player) {
					Player victim = (Player) event.getEntity();
					combatTag.put(damager.getName(), 20);
					combatTag.put(victim.getName(), 20);
					if(!activated.contains(damager.getName()) && !activated.contains(victim.getName())) {
						damager.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
						victim.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
						activated.add(damager.getName());
						activated.add(victim.getName());
						new BukkitRunnable() {
							@Override
							public void run() {
								int duration = combatTag.get(damager.getName());
								duration--;
								sendActionbar(damager, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
								combatTag.put(damager.getName(), duration);
								if(duration == 0) {
									cancel();
									activated.remove(damager.getName());
									sendActionbar(damager, "&aOut of combat!");
								}
							}
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
						new BukkitRunnable() {
							@Override
							public void run() {
								int duration = combatTag.get(victim.getName());
								duration--;
								sendActionbar(victim, new ColorCodeTranslator().colorize("&cCombat Tag: &" + duration));
								combatTag.put(victim.getName(), duration);
								if(duration == 0) {
									cancel();
									activated.remove(victim.getName());
									sendActionbar(victim, "&aOut of combat!");
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
						combatTag.put(damager1.getName(), 20);
						if(!activated.contains(damager1.getName())) {
							damager1.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
							activated.add(damager1.getName());
							new BukkitRunnable() {
								@Override
								public void run() {
									int duration = combatTag.get(damager1.getName());
									duration--;
									sendActionbar(damager1, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
									combatTag.put(damager1.getName(), duration);
									if(duration == 0) {
										cancel();
										activated.remove(damager1.getName());
										sendActionbar(damager1, "&aOut of combat!");
									}
								}
							}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
						}
					}
					else if(event.getEntity() instanceof Player) {
						Player victim = (Player) event.getEntity();
						combatTag.put(damager1.getName(), 20);
						combatTag.put(victim.getName(), 20);
						if(!activated.contains(damager1.getName()) && !activated.contains(victim.getName())) {
							damager1.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
							victim.sendMessage(new ColorCodeTranslator().colorize("&cYou have entered combat tag!"));
							activated.add(damager1.getName());
							activated.add(victim.getName());
							new BukkitRunnable() {
								@Override
								public void run() {
									int duration = combatTag.get(damager1.getName());
									duration--;
									sendActionbar(damager1, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
									combatTag.put(damager1.getName(), duration);
									if(duration == 0) {
										cancel();
										activated.remove(damager1.getName());
										sendActionbar(damager1, "&aOut of combat!");
									}
								}
							}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
							new BukkitRunnable() {
								@Override
								public void run() {
									int duration = combatTag.get(victim.getName());
									duration--;
									sendActionbar(victim, new ColorCodeTranslator().colorize("&cCombat Tag: &6" + duration));
									combatTag.put(victim.getName(), duration);
									if(duration == 0) {
										cancel();
										activated.remove(victim.getName());
										sendActionbar(victim, "&aOut of combat!");
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
		if(combatTag.containsKey(player.getName())) {
			if(combatTag.get(player.getName()) != 0) {
				player.setHealth(0.00);
				combatTag.put(player.getName(), 0);
			}
		}
	}
	@EventHandler
	public void combatTagResetDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		combatTag.put(player.getName(), 0);
	}
	@EventHandler
	public void combatTagRegister(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		combatTag.put(player.getName(), 0);
	}
	public static HashMap<String, Integer> getCombatTag(){
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
}
