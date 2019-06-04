package NeededStuff;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.PacketPlayOutChat;

public class ChatItem implements Listener{
	@EventHandler
	public void chatItemEvent(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		ItemStack item1 = player.getInventory().getItemInMainHand();
		if(message.contains("[i]")) {
			if(item1 != null) {
				if(item1.hasItemMeta()) {
					if(item1.getItemMeta().hasLore()) {
						File f =  new File("plugins/CustomEnchantments/factionsConfig.yml");
						YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
						File f1 =  new File("plugins/CustomEnchantments/playerskillsDF.yml");
						YamlConfiguration yml1 = YamlConfiguration.loadConfiguration(f1);
						event.setCancelled(true);
						String split[] = message.split(Pattern.quote("[i]"));
						String textBefore = null;
						String textAfter = null;
						if(split.length > 0) {
							textBefore = split[0];
						}
						if(split.length > 1) {
							textAfter = split[1];
						}
						Set<String> set = yml.getConfigurationSection("Factions.List").getKeys(false);
						ArrayList<String> facCheck = new ArrayList<String>();
						facCheck.addAll(set);
						String get = "";
						String facN = "";
						boolean check = false;
						int level = 0;
						String id = player.getUniqueId().toString();
						ArrayList<String> chec = new ArrayList<String>();
						for(int i = 0; i < facCheck.size(); i++) {
							get = facCheck.get(i);
							if(get == null) {
								continue;
							}
							Set<String> check1 = yml.getConfigurationSection("Factions.List." + get + ".Members").getKeys(false);
							if(!(check1.contains(id))) {
								chec.addAll(yml.getStringList("Factions.List." + get + ".Chunk List"));	
							}
							else {
								facN = get;
								check = true;
							}
						}
						String levels = yml1.getString("Skills.Players." + player.getUniqueId() + ".Level");
						Matcher matcher = Pattern.compile("(\\d+)").matcher(ChatColor.stripColor(levels));
						while(matcher.find()) {
						    level = Integer.parseInt(matcher.group(1));
						}
						ItemStack item = player.getInventory().getItemInMainHand();
						net.minecraft.server.v1_13_R2.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(item);
				        NBTTagCompound compound = new NBTTagCompound();
				        nmsItemStack.save(compound);
				        String json = compound.toString();
				        BaseComponent[] hoverEventComponents = new BaseComponent[]{
				            new TextComponent(json) // The only element of the hover events basecomponents is the item json
				        };
				        HoverEvent hover_event = new HoverEvent(HoverEvent.Action.SHOW_ITEM, hoverEventComponents);
				        TextComponent component = new TextComponent(item.getItemMeta().getDisplayName());
				        component.setHoverEvent(hover_event);
				        
				        TextComponent componentBefore = new TextComponent();
				        TextComponent componentAfter = new TextComponent();
				        if(check == true) {
							if(textBefore != null) {
								componentBefore.setText(new ColorCodeTranslator().colorize("&6" + facN + "&a | &b&l"  + level + "&a | &7" + player.getName() + ": " + textBefore));
						        componentBefore.setColor(ChatColor.GRAY);
								componentBefore.setHoverEvent(null);
							}
							else {
								componentBefore.setText(new ColorCodeTranslator().colorize("&6" + facN + "&a | &b&l"  + level + "&a | &7" + player.getName() + ": "));
								componentBefore.setColor(ChatColor.GRAY);
						        componentBefore.setHoverEvent(null);
							}
							componentBefore.addExtra(component);
							if(textAfter != null) {
								componentAfter.setText(textAfter);
								componentAfter.setColor(ChatColor.GRAY);
						        componentAfter.setHoverEvent(null);
						        componentBefore.addExtra(componentAfter);
							}
							else {
								componentAfter.setText("");
								componentAfter.setColor(ChatColor.GRAY);
						        componentAfter.setHoverEvent(null);
						        componentBefore.addExtra(componentAfter);
							}
						}
				        else {
				        	if(textBefore != null) {
								componentBefore.setText(new ColorCodeTranslator().colorize("&b&l"  + level + "&a | &7" + player.getName() + ": " + textBefore));
						        componentBefore.setHoverEvent(null);
							}
							else {
								componentBefore.setText(new ColorCodeTranslator().colorize("&b&l"  + level + "&a | &7" + player.getName() + ": "));
						        componentBefore.setHoverEvent(null);
							}
							componentBefore.addExtra(component);
							if(textAfter != null) {
								componentAfter.setText(textAfter);
								componentAfter.setColor(ChatColor.GRAY);
						        componentAfter.setHoverEvent(null);
						        componentBefore.addExtra(componentAfter);
							}
							else {
								componentAfter.setText("");
								componentAfter.setColor(ChatColor.GRAY);
						        componentAfter.setHoverEvent(null);
						        componentBefore.addExtra(componentAfter);
							}
				        }
				        for (Player recipients : event.getRecipients()) {
				            recipients.spigot().sendMessage(componentBefore);
				        }
					}
				}
			}
		}
	}
	public static void send(Player player, IChatBaseComponent chat) {
		PacketPlayOutChat packet = new PacketPlayOutChat(chat);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	public IChatBaseComponent bukkitStackToChatComponent(ItemStack stack) {
		net.minecraft.server.v1_13_R2.ItemStack nms = CraftItemStack.asNMSCopy(stack);
	    return nms.A();
	}
}
