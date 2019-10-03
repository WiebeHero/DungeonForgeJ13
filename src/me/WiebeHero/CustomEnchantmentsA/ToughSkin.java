package me.WiebeHero.CustomEnchantmentsA;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;

import me.WiebeHero.CustomMethods.MethodItemStack;
import me.WiebeHero.Skills.DFPlayer;
import me.WiebeHero.Skills.EffectSkills;

public class ToughSkin implements Listener{
	MethodItemStack it = new MethodItemStack(); 
	EffectSkills sk = new EffectSkills();
	@EventHandler
	public void armorSwitch1(PlayerArmorChangeEvent event) {
		Player p = event.getPlayer();
		ItemStack armorNew = event.getNewItem();
		ItemStack armorOld = event.getOldItem();
		if(armorNew != null) {
			if(armorNew.hasItemMeta()) {
				if(armorNew.getItemMeta().hasLore()) {
					String check = "";
					for(String s1 : armorNew.getItemMeta().getLore()){
						if(s1.contains(ChatColor.stripColor("Tough Skin"))) {
							check = ChatColor.stripColor(s1);
						}
					}
					if(check.contains("Tough Skin")){
						check = check.replaceAll("[^\\d.]", "");
						int level = Integer.parseInt(check);
						DFPlayer dfPlayer = new DFPlayer().getPlayer(p);
						dfPlayer.addAtkCal(1.50 * level, 0);
					}
				}
			}
		}
		if(armorOld != null) {
			if(armorOld.hasItemMeta()) {
				if(armorOld.getItemMeta().hasLore()) {
					String check = "";
					for(String s1 : armorOld.getItemMeta().getLore()){
						if(s1.contains(ChatColor.stripColor("Tough Skin"))) {
							check = ChatColor.stripColor(s1);
						}
					}
					if(check.contains("Tough Skin")){
						check = check.replaceAll("[^\\d.]", "");
						int level = Integer.parseInt(check);
						DFPlayer dfPlayer = new DFPlayer().getPlayer(p);
						dfPlayer.removeAtkCal(1.50 * level, 0);
					}
				}
			}
		}
	}
}
