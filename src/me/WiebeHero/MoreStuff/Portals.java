package me.WiebeHero.MoreStuff;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class Portals implements Listener,CommandExecutor{
	public ArrayList<String> userUsed = new ArrayList<String>();
	public String rtp = "rtp";
	
	@EventHandler
	public void teleportToFacWorld(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if(p.getWorld().getName().equals("DFWarzone-1")) {
			if(p.getLocation().getX() >= 167 && p.getLocation().getX() <= 168 && p.getLocation().getZ() >= 862 && p.getLocation().getZ() <= 865 && p.getLocation().getY() >= 64 && p.getLocation().getY() <= 67){
				Location loc = Bukkit.getWorld("FactionWorld-1").getSpawnLocation();
				int x = new Random().nextInt(250) - 250;
				int z = new Random().nextInt(250) - 250;
				loc = Bukkit.getWorld("FactionWorld-1").getSpawnLocation().add(x, 0, z);
				for(double y1 = 256.00; y1 > 0;) {
					y1--;
					loc.setY(y1);
					if(loc.getBlock().getType() != Material.AIR) {
						loc.setY(y1 + 2.00);
						if(loc.getBlock().getType() == Material.AIR) {
							p.teleport(loc);
							break;
						}
						else {
							y1 = 256.00;
							int tempX = new Random().nextInt(250) - 250;
							int tempZ = new Random().nextInt(250) - 250;
							loc.set(tempX, y1, tempZ);
						}
					}
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
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(!(userUsed.contains(player.getUniqueId().toString()))) {
						new BukkitRunnable() {
							int count = 10;
							@Override
							public void run() {
								if(player.getLocation().getX() == locX && player.getLocation().getZ() == locZ) {
									player.sendMessage(new CCT().colorize("&aRandom Teleporting in " + count + "..."));
									count--;
									if(count == 0) {
										userUsed.add(player.getUniqueId().toString());
										Location loc = Bukkit.getWorld("FactionWorld-1").getSpawnLocation();
										int x = new Random().nextInt(250) - 250;
										int z = new Random().nextInt(250) - 250;
										loc = Bukkit.getWorld("FactionWorld-1").getSpawnLocation().add(x, 0, z);
										for(double y1 = 256.00; y1 > 0;) {
											y1--;
											loc.setY(y1);
											if(loc.getBlock().getType() != Material.AIR) {
												loc.setY(y1 + 2.00);
												if(loc.getBlock().getType() == Material.AIR) {
													player.teleport(loc);
													break;
												}
												else {
													y1 = 256.00;
													int tempX = new Random().nextInt(250) - 250;
													int tempZ = new Random().nextInt(250) - 250;
													loc.set(tempX, y1, tempZ);
												}
											}
										}
										cancel();
										player.sendMessage(new CCT().colorize("&cTeleported!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&cCancelled teleporting because of you moving."));
									cancel();
								}
							}
							
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
						new BukkitRunnable() {
							@Override
							public void run() {
								userUsed.remove(player.getUniqueId().toString());
							}
							
						}.runTaskLater(CustomEnchantments.getInstance(), 1200L);
					}
					else {
						player.sendMessage(new CCT().colorize("&cYou have already used this! Wait 1 minute!"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&cYou are in combat tag! You can not teleport!"));
				}
			}
		}
		return false;
	}
}
