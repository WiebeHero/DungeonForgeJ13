package me.WiebeHero.RankedPlayerPackage;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.MoreStuff.CombatTag;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Kit;

public class KitCommand implements CommandExecutor {
	public String kit = "kit";
	public String kits = "kits";
	private RankedManager rManager;
	private RankEnum rEnum;
	private KitMenu menu;
	public KitCommand(RankedManager rManager, RankEnum rEnum, KitMenu menu) {
		this.rManager = rManager;
		this.rEnum = rEnum;
		this.menu = menu;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
			if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
				if(cmd.getName().equalsIgnoreCase(kits)) {
					menu.Kits(player);
				}
				else if(cmd.getName().equalsIgnoreCase(kit)) {
					if(args.length == 1) {
						Kit kit = rEnum.getIfPresentKit(args[0].toUpperCase());
						if(kit != null) {
							if(rPlayer.getRanks().contains(rEnum.getIfPresent(args[0].toUpperCase()))) {
								if(System.currentTimeMillis() >= rPlayer.getKitCooldown(kit)) {
									kit.recieveKit(player);
									rPlayer.addKitCooldown(kit, System.currentTimeMillis() + kit.getCooldown());
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 2.0F, 1.0F);
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 1.5F);
								}
								else {
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2.0F, 1.0F);
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.5F);
									long timeLeft = rPlayer.getKitCooldown(kit) - System.currentTimeMillis();
									long diffSeconds = timeLeft / 1000 % 60;
							        long diffMinutes = timeLeft / (60 * 1000) % 60;
							        long diffHours = timeLeft / (60 * 60 * 1000) % 24;
							        long diffDays = timeLeft / (24 * 60 * 60 * 1000);
								    String time = diffDays + " " + diffHours + ":" + diffMinutes + ":" + diffSeconds;
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this kit for: &b" + time));
								}
							}
							else if(rPlayer.containsKitUnlock(kit)) {
								if(rPlayer.getKitUnlock(kit)) {
									if(System.currentTimeMillis() >= rPlayer.getKitCooldown(kit)) {
										kit.recieveKit(player);
										rPlayer.addKitCooldown(kit, System.currentTimeMillis() + kit.getCooldown());
										player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 2.0F, 1.0F);
										player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 1.5F);
									}
									else {
										player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2.0F, 1.0F);
										player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.5F);
										long timeLeft = rPlayer.getKitCooldown(kit) - System.currentTimeMillis();
										long diffSeconds = timeLeft / 1000 % 60;
								        long diffMinutes = timeLeft / (60 * 1000) % 60;
								        long diffHours = timeLeft / (60 * 60 * 1000) % 24;
								        long diffDays = timeLeft / (24 * 60 * 60 * 1000);
									    String time = diffDays + " " + diffHours + ":" + diffMinutes + ":" + diffSeconds;
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this kit for: &b" + time));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have this kit unlocked yet!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have the rank &6" + kit.toString()));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis kit doesn't exist!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Usage: /kit (Kit name)"));
					}
				}
				else if(cmd.getName().equalsIgnoreCase(kits)) {
					
				}
			}
			else {
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou cannot use this command while in combat!"));
			}
		}
		return true;
	}
}
