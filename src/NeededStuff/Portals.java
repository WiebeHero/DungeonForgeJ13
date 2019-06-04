package NeededStuff;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class Portals implements Listener,CommandExecutor{
	public static ArrayList<String> combatTagP = new ArrayList<String>();
	public static ArrayList<String> combatTagE = new ArrayList<String>();
	public void combatTagOn(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player || event.getDamager() instanceof Monster) {
			if(event.getEntity() instanceof Player) {
				Player damager = (Player) event.getDamager();
				Player victim = (Player) event.getEntity();
				combatTagP.add(damager.getName());
				combatTagP.add(victim.getName());
				
				if(!(combatTagP.contains(damager.getName()))) {
					new BukkitRunnable() {
						@Override
						public void run() {
							int counter = 20;
							counter--;
							if(counter == 0) {
								combatTagP.remove(damager.getName());
								cancel();
								counter = 20;
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
				}
				if(!(combatTagP.contains(victim.getName()))) {
					new BukkitRunnable() {
						@Override
						public void run() {
							int counter = 20;
							counter--;
							if(counter == 0) {
								combatTagP.remove(victim.getName());
								cancel();
								counter = 20;
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
				}
			}
			else if(event.getEntity() instanceof LivingEntity) {
				Player damager = (Player) event.getDamager();
				combatTagE.add(damager.getName());
				if(!(combatTagE.contains(damager.getName()))){
					new BukkitRunnable() {
						@Override
						public void run() {
							int counter = 10;
							counter--;
							if(counter == 0) {
								combatTagE.remove(damager.getName());
								cancel();
								counter = 10;
							}
						}
					}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
				}
			}
		}
	}
	public void leaveCombatTag(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(combatTagP.contains(player.getName())) {
			AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			attribute.setBaseValue(0.00);
		}
		else if(combatTagE.contains(player.getName())) {
			AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			attribute.setBaseValue(0.00);
		}
	}
	public ArrayList<String> userUsed = new ArrayList<String>();
	public String rtp = "rtp";
	
	@EventHandler
	public void teleportToFacWorld(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if(p.getLocation().getX() >= -1810 && p.getLocation().getX() <= -1807 && p.getLocation().getZ() <= 62 && p.getLocation().getZ() >= 61 && p.getLocation().getY() <= 73 && p.getLocation().getY() >= 71){
			Location loc = Bukkit.getWorld("FactionWorld-1").getSpawnLocation();
			int normal1 = new Random().nextInt(500);
			int normal = 0 - normal1;
			int x1 = new Random().nextInt(500);
			int z1 = new Random().nextInt(500);
			int x = x1 - normal;
			int z = z1 - normal;
			loc = Bukkit.getWorld("FactionWorld-1").getSpawnLocation().add(x, 0, z);
			for(double y1 = 256.00; y1 > 0; y1--) {
				loc.setY(y1);
				if(loc.getBlock().getType() != Material.AIR) {
					loc.setY(y1 + 2.00);
					p.teleport(loc);
					break;
				}
			}
		}
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			Location loc = player.getLocation();
			double locX = loc.getX();
			double locZ = loc.getZ();
			if(!(cmd.getName().equalsIgnoreCase(label)) || cmd.getName().equalsIgnoreCase(rtp)) {
				if(CombatTag.getCombatTag().get(player.getName()) == 0) {
					if(!(userUsed.contains(player.getUniqueId().toString()))) {
						new BukkitRunnable() {
							int count = 10;
							@Override
							public void run() {
								if(player.getLocation().getX() == locX && player.getLocation().getZ() == locZ) {
									player.sendMessage(new ColorCodeTranslator().colorize("&aRandom Teleporting in " + count + "..."));
									count--;
									if(count == 0) {
										userUsed.add(player.getUniqueId().toString());
										Location loc = Bukkit.getWorld("FactionWorld-1").getSpawnLocation();
										int normal1 = new Random().nextInt(500);
										int normal = 0 - normal1;
										int x1 = new Random().nextInt(500);
										int z1 = new Random().nextInt(500);
										int x = x1 - normal;
										int z = z1 - normal;
										loc = Bukkit.getWorld("FactionWorld-1").getSpawnLocation().add(x, 0, z);
										for(double y1 = 256.00; y1 > 0; y1--) {
											loc.setY(y1);
											if(loc.getBlock().getType() != Material.AIR) {
												loc.setY(y1 + 2.00);
												player.teleport(loc);
												break;
											}
										}
										cancel();
										player.sendMessage(new ColorCodeTranslator().colorize("&cTeleported!"));
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&cCancelled teleporting because of you moving."));
									cancel();
								}
							}
							
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
						new BukkitRunnable() {
							@Override
							public void run() {
								userUsed.remove(player.getUniqueId().toString());
							}
							
						}.runTaskLater(CustomEnchantments.getInstance(), 10000L);
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&cYou have already used this! Wait 1 minute!"));
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&cYou are in combat tag! You can not teleport!"));
				}
			}
		}
		return false;
	}
}
