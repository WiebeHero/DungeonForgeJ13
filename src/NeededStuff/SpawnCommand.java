package NeededStuff;

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

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class SpawnCommand implements Listener,CommandExecutor{
	public String spawn = "spawn";
	
	public static ArrayList<String> spawning = new ArrayList<String>();
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			Location loc = player.getLocation();
			double locX = loc.getX();
			double locZ = loc.getZ();
			if(cmd.getName().equalsIgnoreCase(spawn)) {
				if(CombatTag.getCombatTag().get(player.getName()) == 0) {
					if(!(spawning.contains(player.getUniqueId().toString()))) {
						spawning.add(player.getUniqueId().toString());
						new BukkitRunnable() {
							int count = 10;
							@Override
							public void run() {
								if(player.getLocation().getX() == locX && player.getLocation().getZ() == locZ) {
									player.sendMessage(new ColorCodeTranslator().colorize("&aSending you to spawn in " + count + "..."));
									count--;
									if(count == 0) {
										player.sendMessage(new ColorCodeTranslator().colorize("&aTeleporting!"));
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
									player.sendMessage(new ColorCodeTranslator().colorize("&cCancelled teleporting because of you moving."));
									cancel();
									spawning.remove(player.getUniqueId().toString());
								}
							}	
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&cYou are already going to spawn!"));
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&cYou can't use this command! You are in combat!"));
				}
			}
		}
		return false;
	}
}