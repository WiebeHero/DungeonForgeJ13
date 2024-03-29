package me.WiebeHero.Chat;

import java.util.regex.Pattern;

import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Factions.DFFactionManager;
import me.WiebeHero.Factions.DFFactionPlayer;
import me.WiebeHero.Factions.DFFactionPlayerManager;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Rank;
import me.WiebeHero.RankedPlayerPackage.RankedManager;
import me.WiebeHero.RankedPlayerPackage.RankedPlayer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_13_R2.NBTTagCompound;

public class ChatEvents implements Listener{
	private MSGManager msgManager;
	private DFPlayerManager dfManager;
	private DFFactionPlayerManager facPlayerManager;
	private DFFactionManager facManager;
	private RankedManager rManager;
	public ChatEvents(DFPlayerManager dfManager, MSGManager msgManager, DFFactionManager facManager, DFFactionPlayerManager facPlayerManager, RankedManager rManager) {
		this.dfManager = dfManager;
		this.msgManager = msgManager;
		this.facPlayerManager = facPlayerManager;
		this.facManager = facManager;
		this.rManager = rManager;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void chatFeature(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		DFPlayer dfPlayer = dfManager.getEntity(player);
		String totalMessage = event.getMessage();
		totalMessage = totalMessage.replace("%", "%%");
		int level = dfPlayer.getLevel();
		RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
		DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(player);
		DFFaction faction = facManager.getFaction(facPlayer.getFactionId());
		MSG myMsg = msgManager.get(player.getUniqueId());
		String format = "";
		if(!myMsg.getStaffChat()) {
			if(faction != null) {
				format += "�6" + faction.getName() + " �a| ";
			}
			format += "�b�l"  + level;
			if(rPlayer.getHighestRank().getRank() > Rank.USER.getRank()) {
				format += "�a | " + rPlayer.getHighestRank().getDisplay() + "�a | �f" + player.getName() + ": ";
			}
			else {
				format += "�a | �7" + player.getName() + ": ";
			}
			format += totalMessage;
		}
		else {
			format += "�aStaff chat | " + rPlayer.getHighestRank().getDisplay() + "�a | �f" + player.getName() + ": ";
			format += totalMessage;
		}
		event.setFormat(format);
		for(Player p : event.getRecipients()) {
			if(p != null) {
				MSG msg = msgManager.get(p.getUniqueId());
				RankedPlayer oPlayer = rManager.getRankedPlayer(p.getUniqueId());
				if(msg != null) {
					if(myMsg.getStaffChat()) {
						if(oPlayer.getHighestRank().getRank() < Rank.HELPER.getRank()) {
							event.getRecipients().remove(p);
						}
					}
					else if(msg.isInIgnore(player.getUniqueId())) {
						event.getRecipients().remove(p);
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void chatItemEvent(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		String format = event.getFormat();
		ItemStack item1 = player.getInventory().getItemInMainHand();
		if(message.contains("[i]")) {
			if(item1 != null) {
				event.setCancelled(true);
				String split[] = format.split(Pattern.quote("[i]"));
				net.minecraft.server.v1_13_R2.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(item1);
		        NBTTagCompound compound = new NBTTagCompound();
		        nmsItemStack.save(compound);
		        String json = compound.toString();
		        BaseComponent[] hoverEventComponents = new BaseComponent[]{
		            new TextComponent(json) // The only element of the hover events basecomponents is the item json
		        };
		        HoverEvent hover_event = new HoverEvent(HoverEvent.Action.SHOW_ITEM, hoverEventComponents);
		        TextComponent component = new TextComponent(TextComponent.fromLegacyText(CraftItemStack.asNMSCopy(item1).getName().getText() + " �f" + item1.getAmount() + "x"));
		        component.setHoverEvent(hover_event);
		        TextComponent before = new TextComponent(TextComponent.fromLegacyText(split.length < 1 ? "" : split[0]));
		        before.setHoverEvent(null);
		        TextComponent after = new TextComponent(TextComponent.fromLegacyText(split.length < 2 ? "" : split[1]));
		        after.setHoverEvent(null);
		        after.setColor(before.getColor());
		        before.addExtra(component);
		        before.addExtra(after);
		        component.setHoverEvent(hover_event);
		        for(Player p : event.getRecipients()) {
		        	p.sendMessage(before);
		        }
			}
		}
	}
}
