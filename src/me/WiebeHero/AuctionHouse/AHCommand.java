package me.WiebeHero.AuctionHouse;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.RankedPlayerPackage.RankedManager;
import me.WiebeHero.RankedPlayerPackage.RankedPlayer;

public class AHCommand implements CommandExecutor{
	private AHManager ahManager;
	private RankedManager rManager;
	private AHInventory ahInv;
	public AHCommand(AHManager ahManager, RankedManager rManager, AHInventory ahInv) {
		this.ahManager = ahManager;
		this.rManager = rManager;
		this.ahInv = ahInv;
	}
	public String ah = "ah";
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(ah)) {
				if(args.length == 0) {
					ahManager.refreshAH();
					ahInv.AHInv(player, 1);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_HORSE_SADDLE, 2.0F, 1.0F);
					player.setMetadata("AHPage", new FixedMetadataValue(CustomEnchantments.getInstance(), 1));
					new BukkitRunnable() {
						public void run() {
							InventoryView view = player.getOpenInventory();
							if(view != null) {
								if(ChatColor.stripColor(view.getTitle()).equals("Auction House")) {
									ahManager.refreshAHItems(player);
								}
								else if(ChatColor.stripColor(view.getTitle()).equals("Auction House: Your item's")) {
									ahManager.refreshYourItems(player);
								}
								else if(ChatColor.stripColor(view.getTitle()).equals("Auction House: Collection bin") || ChatColor.stripColor(view.getTitle()).equals("Auction House: Take off")) {
									ahManager.refreshConfirm(view);
								}
								else {
									cancel();
								}
							}
							else {
								cancel();
							}
						}
					}.runTaskTimerAsynchronously(CustomEnchantments.getInstance(), 0L, 20L);
				}
				else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("sell")) {
						RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
						if((ahManager.getItemSizeAh(player.getUniqueId()) + ahManager.getCollectionBinCount(player.getUniqueId())) < rPlayer.getAHCount()) {
							ItemStack item = player.getInventory().getItemInMainHand();
							if(item != null && item.getType() != Material.AIR) {
								double price = 0.00;
								try {
									price = Double.parseDouble(args[1]);
								}
								catch(NumberFormatException ex){
									System.out.print(ex);
								}
								if(price > 0.00) {
									AHItem ahItem = new AHItem(player.getUniqueId(), price, System.currentTimeMillis() + 172800000L, player.getName());
									ItemStack finalItem = ahManager.constructAHItem(item, ahItem);
									ahManager.addToAh(finalItem);
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.5F);
									player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have put your item on the AH for " + price + "$!"));
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid price."));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have nothing in your hand, hold the item you want to sell."));
							}
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage!: /ah sell (Price)"));
					}
				}
			}
		}
		return true;
	}
}
