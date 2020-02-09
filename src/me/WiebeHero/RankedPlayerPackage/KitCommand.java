package me.WiebeHero.RankedPlayerPackage;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.MoreStuff.CombatTag;

public class KitCommand {
	private ItemStackBuilder builder = new ItemStackBuilder();
	public String kit = "kit";
	private RankedManager rManager = CustomEnchantments.getInstance().rankedManager;
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("bronze")) {
						this.bronzeKit(player);
					}
				}
			}
			else {
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use this command while in combat!"));
			}
		}
		return true;
	}
	public void bronzeKit(Player p) {
		builder.constructItem(
			Material.NETHER_STAR,
			2,
			"&7Common Crystal",
			new ArrayList<String>(Arrays.asList(
				"&7Bring me to &6&lNOVIS &7to get some",
				"&7really nice rewards!",
				"&7Rarity: &7Common"
			))
		);
	}
}
