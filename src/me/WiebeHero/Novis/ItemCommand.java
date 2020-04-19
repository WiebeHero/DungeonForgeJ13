package me.WiebeHero.Novis;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.MoreStuff.CombatTag;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Rank;
import me.WiebeHero.RankedPlayerPackage.RankedManager;
import me.WiebeHero.RankedPlayerPackage.RankedPlayer;

public class ItemCommand  implements CommandExecutor{
	private NovisRewards rewards;
	private RankedManager rManager;
	public ItemCommand(NovisRewards rewards, RankedManager rManager) {
		this.rewards = rewards;
		this.rManager = rManager;
	}
	public String itemCmd = "customitem";
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(rManager.contains(player.getUniqueId())) {
				RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
				if(cmd.getName().equalsIgnoreCase(itemCmd)) {
					if(rPlayer.getHighestRank().rank >= Rank.ADMIN.rank) {
						if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
							if(args.length == 2) {
								HashMap<String, ItemStack> stacks = this.rewards.getItemList();
								if(stacks.containsKey(args[0])) {
									player.getInventory().addItem(stacks.get(args[0]));
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis item doesn't exist! Try to type the specific item in full lowercase and replace all spaces with _"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cWrong ussage: /customitem (Item Name) (Level)"));
							}
						}
					}
				}
			}
		}
		return true;
	}
}
