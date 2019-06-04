package me.WiebeHero.LootChest;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;

public class SetChest implements Listener,CommandExecutor {
	public String getLast(Set<String> set) {
        return set.stream().skip(set.stream().count() - 1).findFirst().get();
	}
	public static HashMap<Integer, Location> chestLocations = new HashMap<Integer, Location>();
	public static HashMap<Integer, Integer> chestTier = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer> chestRadius = new HashMap<Integer, Integer>();
	public static HashMap<Integer, String> chestWorld = new HashMap<Integer, String>();
	int counter;
	public String cmdNovis = "Loot";
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.isOp()) {
				if(args.length == 3) {
					if(args[0].equalsIgnoreCase(ChatColor.stripColor("create"))) {
						int tier = -1;
						int radius = 0;
						try {
							tier = Integer.parseInt(args[1]);
						}
						catch(NumberFormatException ex){
							ex.printStackTrace();
						}
						try {
							radius = Integer.parseInt(args[2]);
						}
						catch(NumberFormatException ex){
							ex.printStackTrace();
						}
						if(tier >= 1 && tier <= 5) {
							if(radius >= 1) {
								chestLocations.put(chestLocations.size() + 1, player.getLocation());
								chestTier.put(chestTier.size() + 1, tier);
								chestRadius.put(chestRadius.size() + 1, radius);
								chestWorld.put(chestWorld.size() + 1, player.getLocation().getWorld().getName());
								player.getLocation().getBlock().setType(Material.CHEST);
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&cInvalid radius"));
							}
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&cInvalid tier"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&cInvalid command"));
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&cTo many/few arguments"));
				}
			}
			else {
				player.sendMessage(new ColorCodeTranslator().colorize("&cYou aren't op, so you can't use this."));
			}
		}
		return false;
	}
	public static HashMap<Integer, Integer> getChestTierList(){
		return SetChest.chestTier;
	}
	public static HashMap<Integer, Integer> getChestRadiusList(){
		return SetChest.chestRadius;
	}
	public static HashMap<Integer, Location> getChestLocationList(){
		return SetChest.chestLocations;
	}
	public static HashMap<Integer, String> getChestWorldsList(){
		return SetChest.chestWorld;
	}
}
