package me.WiebeHero.MoreStuff;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class SpawnCommand implements Listener,CommandExecutor{
	public String spawn = "spawn";
	
	public static ArrayList<String> spawning = new ArrayList<String>();
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(spawn)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(!(spawning.contains(player.getUniqueId().toString()))) {
						spawning.add(player.getUniqueId().toString());
						Location loc = player.getLocation();
						new BukkitRunnable() {
							int count = 200;
							int temp = 0;
							@Override
							public void run() {
								if(loc.getWorld().getName().equals(player.getWorld().getName())) {
									if(loc.distance(player.getLocation()) <= 0.1) {
										if(temp == 0) {
											temp = temp - 20;
											if(count / 20 != 0) {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aSending you to spawn in " + (count / 20) + "..."));
											}
										}
										if(count == 0) {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTeleporting!"));
											World world = Bukkit.getWorld("DFWarzone-1");
											double x = Bukkit.getWorld("DFWarzone-1").getSpawnLocation().getX();
											double y = Bukkit.getWorld("DFWarzone-1").getSpawnLocation().getY();
											double z = Bukkit.getWorld("DFWarzone-1").getSpawnLocation().getZ();
											Location loc = new Location(world, x, y, z, 180.00F, 00.00F);
											player.teleport(loc);
											count = 10;
											spawning.remove(player.getUniqueId().toString());
											cancel();
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cCancelled teleporting because of you moving."));
										cancel();
										spawning.remove(player.getUniqueId().toString());
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cCancelled teleporting because of you moving."));
									cancel();
									spawning.remove(player.getUniqueId().toString());
								}
								count--;
								temp++;
							}	
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are already going to spawn!"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command! You are in combat!"));
				}
			}
		}
		return false;
	}
}