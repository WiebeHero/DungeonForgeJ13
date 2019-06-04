package me.WiebeHero.Novis;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;

public class NovisCommands implements Listener,CommandExecutor {
	public String cmdNovis = "Novis";
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(cmdNovis)) {
				if(args.length == 1) {
					if(player.isOp()) {
						if(args[0].equalsIgnoreCase("create")) {
							Location loc = player.getLocation();
							MagmaCube mob = (MagmaCube) player.getWorld().spawnEntity(loc, EntityType.MAGMA_CUBE);
							mob.setAI(false);
							mob.setCustomName(new ColorCodeTranslator().colorize("&6Novis"));
							mob.setSize(4);
						}
						else if(args[0].equalsIgnoreCase("delete")) {
							for(Entity entity : player.getNearbyEntities(10, 10, 10)) {
								if(entity instanceof MagmaCube) {
									if(entity.getCustomName().contains(new ColorCodeTranslator().colorize("Novis"))) {
										entity.remove();
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
}
