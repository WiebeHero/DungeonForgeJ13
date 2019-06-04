package me.WiebeHero.DFShops;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;

public class PayCommand implements Listener,CommandExecutor{
	MoneyCreate m = new MoneyCreate();
	public String command = "pay";
	public String balance = "balance";
	public String money = "money";
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(command)) {
				if(args.length > 0) {
					if(args.length > 1) {
						Player newPlayer = null;
						UUID uuid1 = player.getUniqueId();
						UUID uuid2 = null;
						for(Player p : Bukkit.getOnlinePlayers()) {
							if(!p.getName().equals(player.getName())) {
								if(p.getName().equals(args[0])) {
									uuid2 = p.getUniqueId();
									newPlayer = p;
								}
							}
						}
						File f =  new File("plugins/CustomEnchantments/moneyConfig.yml");
						YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
						try{
							yml.load(f);
				        }
				        catch(IOException e){
				            e.printStackTrace();
				        } 
						catch (InvalidConfigurationException e) {
							e.printStackTrace();
						}
						if(uuid2 != null) {
							double moneyMe = m.getMoneyList().get(uuid1);
							double moneyThem = m.getMoneyList().get(uuid2);
							String sAmount = args[1];
							int amount = 0;
							Matcher matcher6 = Pattern.compile("(\\d+)").matcher(ChatColor.stripColor(sAmount));
							while(matcher6.find()) {
								amount = Integer.parseInt(matcher6.group(1)); 
							}
							if(amount > 0) {
								double newMoneyMe = moneyMe - amount;
								double newMoneyThem = moneyThem + amount;
								yml.set("List." + uuid1 + ".Money", newMoneyMe);
								yml.set("List." + uuid2 + ".Money", newMoneyThem);
								player.sendMessage(new ColorCodeTranslator().colorize("&aYou have paid &6" + amount + " &a&lto " + newPlayer.getName() + "!"));
								newPlayer.sendMessage(new ColorCodeTranslator().colorize("&a&l" + player.getName() + " &aHas paid you &6" + amount));
								try{
									yml.save(f);
							    }
							    catch(IOException e){
							        e.printStackTrace();
							    }	
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&cNot a valid amount."));
							}
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&cPlayer not found, or its yourself silly ;)"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&cYou need to type the amount you want to pay the player."));
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&cYou need to type the players name after 'pay'"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(balance) || cmd.getName().equalsIgnoreCase(money)) {
				if(args.length == 0) {
					File f =  new File("plugins/CustomEnchantments/moneyConfig.yml");
					YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
					try{
						yml.load(f);
			        }
			        catch(IOException e){
			            e.printStackTrace();
			        } 
					catch (InvalidConfigurationException e) {
						e.printStackTrace();
					}
					UUID uuid1 = player.getUniqueId();
					double moneyMe = m.getMoneyList().get(uuid1);
					player.sendMessage(new ColorCodeTranslator().colorize("&aYou're balance is: &6" + moneyMe + "$"));
				}
				else if(args.length == 1) {
					File f =  new File("plugins/CustomEnchantments/moneyConfig.yml");
					YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
					try{
						yml.load(f);
			        }
			        catch(IOException e){
			            e.printStackTrace();
			        } 
					catch (InvalidConfigurationException e) {
						e.printStackTrace();
					}
					Player target = Bukkit.getPlayer(args[0]);
					if(target != null) {
						UUID uuid1 = target.getUniqueId();
						double moneyMe = m.getMoneyList().get(uuid1);
						player.sendMessage(new ColorCodeTranslator().colorize("&a" + target.getName() + " balance is: &6" + moneyMe + "$"));
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&cThis player has not logged online, or is offline!"));
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&cThere are to many arguments! Use: /bal (Player Name)"));
				}
			}
		}
		return false;
	}
}
