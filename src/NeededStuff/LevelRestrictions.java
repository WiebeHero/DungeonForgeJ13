package NeededStuff;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.NBTTagDouble;
import net.minecraft.server.v1_13_R2.NBTTagInt;
import net.minecraft.server.v1_13_R2.NBTTagList;
import net.minecraft.server.v1_13_R2.NBTTagString;

public class LevelRestrictions implements Listener{
	File f =  new File("plugins/CustomEnchantments/playerskillsDF.yml");
	YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
	@EventHandler
	public void levelRestrictWeapons(EntityDamageByEntityEvent event) {
		try{
			yml.load(f);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if(player.getInventory().getItemInMainHand() != null) {
				if(player.getInventory().getItemInMainHand().hasItemMeta()) {
					if(player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
						List<String> loreList = player.getInventory().getItemInMainHand().getItemMeta().getLore();
						for (int i=0; i<player.getInventory().getItemInMainHand().getItemMeta().getLore().size(); i++) {
							if(loreList.get(i).contains(ChatColor.stripColor("Level Required:"))) {
								int level = yml.getInt("Skills.Players." + player.getUniqueId() + ".Level");
								String line = ChatColor.stripColor(loreList.get(i));
								Matcher matcher = Pattern.compile("Level Required: (\\d+)").matcher(ChatColor.stripColor(line));
								while(matcher.find()) {
								    int firstInt = Integer.parseInt(matcher.group(1));
				  					if(!(level >= firstInt)) {
				  						player.sendMessage(new ColorCodeTranslator().colorize("&cYou can't use this, you are to low level."));
				  						event.setCancelled(true);
				  					}
				  				}
							}
						}
					}
				}
			}
			if(player.getInventory().getItemInOffHand() != null) {
				if(player.getInventory().getItemInOffHand().getType() == Material.BOW) {
					if(player.getInventory().getItemInMainHand().hasItemMeta()) {
						if(player.getInventory().getItemInOffHand().getItemMeta().hasLore()) {
							List<String> loreList = player.getInventory().getItemInOffHand().getItemMeta().getLore();
							for (int i=0; i<player.getInventory().getItemInOffHand().getItemMeta().getLore().size(); i++) {
								if(loreList.get(i).contains(ChatColor.stripColor("Level Required:"))) {
									int level = yml.getInt("Skills.Players." + player.getUniqueId() + ".Level");
									String line = ChatColor.stripColor(loreList.get(i));
									Matcher matcher = Pattern.compile("Level Required: (\\d+)").matcher(ChatColor.stripColor(line));
									while(matcher.find()) {
									    int firstInt = Integer.parseInt(matcher.group(1));
					  					if(!(level >= firstInt)) {
					  						player.sendMessage(new ColorCodeTranslator().colorize("&cYou can't use this, you are to low level."));
					  						event.setCancelled(true);
					  					}
					  				}
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void levelRestrictArmor(PlayerInteractEvent event) {
		try{
			yml.load(f);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		Player player = event.getPlayer();
		Action action = event.getAction();
		if(action == Action.RIGHT_CLICK_AIR || action == Action.LEFT_CLICK_AIR) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item != null) {
				if(item.hasItemMeta()) {
					if(item.getItemMeta().hasLore()) {
						String check = "";
						for(String lore : item.getItemMeta().getLore()) {
							if(lore.contains(ChatColor.stripColor("Level Required:"))) {
								check = ChatColor.stripColor(lore);
							}
						}
						if(check.contains("Level Required:")) {
							event.setCancelled(true);
							int level = yml.getInt("Skills.Players." + player.getUniqueId() + ".Level");
							Matcher matcher = Pattern.compile("Level Required: (\\d+)").matcher(ChatColor.stripColor(check));
							int rLevel = 0;
							while(matcher.find()) {
							    rLevel = Integer.parseInt(matcher.group(1));
							}
		  					if(!(level >= rLevel)) {
		  						player.sendMessage(new ColorCodeTranslator().colorize("&cYou can't use this, you are to low level."));
		  						event.setCancelled(true);
		  					}
		  					else {
			  					ItemStack item1 = item;
								List<String> lore1 = item1.getItemMeta().getLore();
								net.minecraft.server.v1_13_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(item1);
						  	    NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
						  		NBTTagList modifiers = new NBTTagList();
						  		NBTTagCompound damage = new NBTTagCompound();
						  		NBTTagCompound speed = new NBTTagCompound();
						  		int index1 = 0;
						  		int index2 = 0;
						  		for(String s1 : item1.getItemMeta().getLore()){
						  			if(ChatColor.stripColor(s1).contains("Armor Defense: ")) {
						  				String llore = lore1.get(index1);  
						  				String stripped1 = ChatColor.stripColor(llore);
						  				Matcher matcher1 = Pattern.compile("Armor Defense: (\\d+)\\.(\\d+)").matcher(ChatColor.stripColor(stripped1));
						  				double firstInt = -1;
						  				while(matcher1.find()) {
						  					firstInt = Double.parseDouble(matcher1.group(1) + "." + matcher1.group(2));	    
											int defense = yml.getInt("Skills.Players." + player.getUniqueId() + ".Defense");
											double end = firstInt * ((double)defense / (double)75 + (double)1);
							  				damage.set("AttributeName", new NBTTagString("generic.armor"));
						  					damage.set("Name", new NBTTagString("generic.armor"));
						  					damage.set("Amount", new NBTTagDouble(end));
						  					damage.set("Operation", new NBTTagInt(0));
						  					if(item.getType() == Material.LEATHER_HELMET || item.getType() == Material.CHAINMAIL_HELMET || item.getType() == Material.IRON_HELMET || item.getType() == Material.GOLDEN_HELMET || item.getType() == Material.DIAMOND_HELMET) {
						  						damage.set("UUIDLeast", new NBTTagInt(1));
						  						damage.set("UUIDMost", new NBTTagInt(1));
						  						damage.set("Slot", new NBTTagString("head"));
						  					}
						  					else if(item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.CHAINMAIL_CHESTPLATE || item.getType() == Material.IRON_CHESTPLATE || item.getType() == Material.GOLDEN_CHESTPLATE || item.getType() == Material.DIAMOND_CHESTPLATE) {
						  						damage.set("UUIDLeast", new NBTTagInt(2));
						  						damage.set("UUIDMost", new NBTTagInt(1));
						  						damage.set("Slot", new NBTTagString("chest"));
						  					}
						  					else if(item.getType() == Material.LEATHER_LEGGINGS || item.getType() == Material.CHAINMAIL_LEGGINGS || item.getType() == Material.IRON_LEGGINGS || item.getType() == Material.GOLDEN_LEGGINGS || item.getType() == Material.DIAMOND_LEGGINGS) {
						  						damage.set("UUIDLeast", new NBTTagInt(1));
						  						damage.set("UUIDMost", new NBTTagInt(2));
						  						damage.set("Slot", new NBTTagString("legs"));
						  					}
						  					else if(item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.CHAINMAIL_BOOTS || item.getType() == Material.IRON_BOOTS || item.getType() == Material.GOLDEN_BOOTS || item.getType() == Material.DIAMOND_BOOTS) {
						  						damage.set("UUIDLeast", new NBTTagInt(2));
						  						damage.set("UUIDMost", new NBTTagInt(2));
						  						damage.set("Slot", new NBTTagString("feet"));
						  					}
						  					modifiers.add(damage);
						  					break;
						  				}
						  			}index1++;
						  		}
						  		for(String s2 : item1.getItemMeta().getLore()){
						  			if(ChatColor.stripColor(s2).contains("Armor Toughness: ")) {
						  				String llore = lore1.get(index2);  
						  				String stripped1 = ChatColor.stripColor(llore);
						  				Matcher matcher1 = Pattern.compile("Armor Toughness: (\\d+)\\.(\\d+)").matcher(ChatColor.stripColor(stripped1));
						  				double firstInt = -1;
						  				while(matcher1.find()) {
						  					firstInt = Double.parseDouble(matcher1.group(1) + "." + matcher1.group(2));	  
											int defense = yml.getInt("Skills.Players." + player.getUniqueId() + ".Defense");
											double end = firstInt * ((double)defense / (double)44 + (double)1);
							  				speed.set("AttributeName", new NBTTagString("generic.armorToughness"));
							  				speed.set("Name", new NBTTagString("generic.armorToughness"));
							  				speed.set("Amount", new NBTTagDouble(end));
							  				speed.set("Operation", new NBTTagInt(0));
							  				if(item.getType() == Material.LEATHER_HELMET || item.getType() == Material.CHAINMAIL_HELMET || item.getType() == Material.IRON_HELMET || item.getType() == Material.GOLDEN_HELMET || item.getType() == Material.DIAMOND_HELMET) {
						  						speed.set("UUIDLeast", new NBTTagInt(3));
						  						speed.set("UUIDMost", new NBTTagInt(2));
						  						speed.set("Slot", new NBTTagString("head"));
						  					}
						  					else if(item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.CHAINMAIL_CHESTPLATE || item.getType() == Material.IRON_CHESTPLATE || item.getType() == Material.GOLDEN_CHESTPLATE || item.getType() == Material.DIAMOND_CHESTPLATE) {
						  						speed.set("UUIDLeast", new NBTTagInt(2));
						  						speed.set("UUIDMost", new NBTTagInt(3));
						  						speed.set("Slot", new NBTTagString("chest"));
						  					}
						  					else if(item.getType() == Material.LEATHER_LEGGINGS || item.getType() == Material.CHAINMAIL_LEGGINGS || item.getType() == Material.IRON_LEGGINGS || item.getType() == Material.GOLDEN_LEGGINGS || item.getType() == Material.DIAMOND_LEGGINGS) {
						  						speed.set("UUIDLeast", new NBTTagInt(3));
						  						speed.set("UUIDMost", new NBTTagInt(3));
						  						speed.set("Slot", new NBTTagString("legs"));
						  					}
						  					else if(item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.CHAINMAIL_BOOTS || item.getType() == Material.IRON_BOOTS || item.getType() == Material.GOLDEN_BOOTS || item.getType() == Material.DIAMOND_BOOTS) {
						  						speed.set("UUIDLeast", new NBTTagInt(4));
						  						speed.set("UUIDMost", new NBTTagInt(3));
						  						speed.set("Slot", new NBTTagString("feet"));
						  					}
						  					modifiers.add(speed);
						  					compound.set("AttributeModifiers", modifiers);
											nmsStack.setTag(compound);
											nmsStack.save(compound);
											item = CraftItemStack.asCraftMirror(nmsStack);
						  					break;
						  				}
						  			}index2++;
						  		}
						  		if(item.getType() == Material.LEATHER_HELMET || item.getType() == Material.CHAINMAIL_HELMET || item.getType() == Material.IRON_HELMET || item.getType() == Material.GOLDEN_HELMET || item.getType() == Material.DIAMOND_HELMET) {
			  						if(player.getInventory().getHelmet() == null) {
			  							player.getInventory().setItemInMainHand(null);
			  							player.getInventory().setHelmet(item1);
			  						}
			  					}
			  					else if(item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.CHAINMAIL_CHESTPLATE || item.getType() == Material.IRON_CHESTPLATE || item.getType() == Material.GOLDEN_CHESTPLATE || item.getType() == Material.DIAMOND_CHESTPLATE) {
			  						if(player.getInventory().getChestplate() == null) {
			  							player.getInventory().setItemInMainHand(null);
			  							player.getInventory().setChestplate(item1);
			  						}
			  					}
			  					else if(item.getType() == Material.LEATHER_LEGGINGS || item.getType() == Material.CHAINMAIL_LEGGINGS || item.getType() == Material.IRON_LEGGINGS || item.getType() == Material.GOLDEN_LEGGINGS || item.getType() == Material.DIAMOND_LEGGINGS) {
			  						if(player.getInventory().getLeggings() == null) {
			  							player.getInventory().setItemInMainHand(null);
			  							player.getInventory().setLeggings(item1);;
			  						}
			  					}
			  					else if(item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.CHAINMAIL_BOOTS || item.getType() == Material.IRON_BOOTS || item.getType() == Material.GOLDEN_BOOTS || item.getType() == Material.DIAMOND_BOOTS) {
			  						if(player.getInventory().getBoots() == null) {
			  							player.getInventory().setItemInMainHand(null);
			  							player.getInventory().setBoots(item1);
			  						}
			  					}
		  					}
						}
					}
				}
			}
		}
	}
}
