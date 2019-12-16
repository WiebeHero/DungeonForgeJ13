package me.WiebeHero.CustomEnchantments;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;


public class CustomWeapons implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	public String colorize(String msg)
    {
        String coloredMsg = "";
        for(int i = 0; i < msg.length(); i++)
        {
            if(msg.charAt(i) == '&')
                coloredMsg += '§';
            else
                coloredMsg += msg.charAt(i);
        }
        return coloredMsg;
    }
	
	
	@EventHandler
	public void wepsStoneHammer(EntityDeathEvent event) {
		LivingEntity victim = (LivingEntity) event.getEntity();
		
		EntityDamageEvent ede = victim.getLastDamageCause();
		
		DamageCause dc = ede.getCause();
		List<String> newLore = new ArrayList<String>();
		
		
		if(event.getEntity().getKiller() instanceof Player && dc == DamageCause.ENTITY_ATTACK || dc == DamageCause.PROJECTILE){
			Player damager = (Player) event.getEntity().getKiller();
			for (int i=0; i<damager.getInventory().getItemInMainHand().getItemMeta().getLore().size(); i++) {
				for(String s1 : damager.getInventory().getItemInMainHand().getItemMeta().getLore()){
					if(ChatColor.stripColor(s1).contains("Upgrade Progress: ")) {
						ItemStack item1 = damager.getInventory().getItemInMainHand();
						ItemMeta im = item1.getItemMeta();
						List<String> lore = damager.getInventory().getItemInMainHand().getItemMeta().getLore();
						String llore = lore.get(i);
						Matcher matcher = Pattern.compile("(\\d+) / (\\d+)").matcher(llore);
						while(matcher.find()) {
						    int firstInt = Integer.parseInt(matcher.group(1));
						    int secondInt = Integer.parseInt(matcher.group(2));
						    plugin.getServer().getConsoleSender().sendMessage(firstInt  + "," + secondInt);
						    for(int lijn = 0; lijn < lore.size(); lijn++) {
						    	if(damager.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.stripColor("Stone Hammer"))) {
							    	if(lore.get(lijn).contains(ChatColor.stripColor("Upgrade Progress: "))) {
						    			if(firstInt >= secondInt) {
						    				if(firstInt >= 1000) {
								    			String colorizee = plugin.getConfig().getString(("Items.Common.Stone_Hammer.level2.name"));
								    			im.setDisplayName(new CCT().colorize(colorizee));
								    			ArrayList<String> lore1 = new ArrayList<String>();
								    			ArrayList<String> lore11 = (ArrayList<String>) plugin.getConfig().getStringList("Items.Common.Stone_Hammer.level2.lore");
								    			for(String s : lore11) {
								    				lore1.add(new CCT().colorize(s));
								    			}
								    			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
								    			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								    			im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
								    			im.setLore(lore1);
								    			item1.setItemMeta(im);
								    		}
								    		if(firstInt >= 4000) {
								    			String colorizee = plugin.getConfig().getString(("Items.Common.Stone_Hammer.level3.name"));
								    			im.setDisplayName(new CCT().colorize(colorizee));
								    			ArrayList<String> lore1 = new ArrayList<String>();
								    			ArrayList<String> lore11 = (ArrayList<String>) plugin.getConfig().getStringList("Items.Common.Stone_Hammer.level3.lore");
								    			for(String s : lore11) {
								    				lore1.add(new CCT().colorize(s));
								    			}
								    			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
								    			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								    			im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
								    			im.setLore(lore1);
								    			item1.setItemMeta(im);
								    		}
								    		if(firstInt >= 9000) {
								    			String colorizee = plugin.getConfig().getString(("Items.Common.Stone_Hammer.level4.name"));
								    			im.setDisplayName(new CCT().colorize(colorizee));
								    			ArrayList<String> lore1 = new ArrayList<String>();
								    			ArrayList<String> lore11 = (ArrayList<String>) plugin.getConfig().getStringList("Items.Common.Stone_Hammer.level4.lore");
								    			for(String s : lore11) {
								    				lore1.add(new CCT().colorize(s));
								    			}
								    			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
								    			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								    			im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
								    			im.setLore(lore1);
								    			item1.setItemMeta(im);
								    		}
								    		if(firstInt >= 14000) {
								    			String colorizee = plugin.getConfig().getString(("Items.Common.Stone_Hammer.level5.name"));
								    			im.setDisplayName(new CCT().colorize(colorizee));
								    			ArrayList<String> lore1 = new ArrayList<String>();
								    			ArrayList<String> lore11 = (ArrayList<String>) plugin.getConfig().getStringList("Items.Common.Stone_Hammer.level5.lore");
								    			for(String s : lore11) {
								    				lore1.add(new CCT().colorize(s));
								    			}
								    			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
								    			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								    			im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
								    			im.setLore(lore1);
								    			item1.setItemMeta(im);
								    		}
								    		if(firstInt >= 20000) {
								    			String colorizee = plugin.getConfig().getString(("Items.Common.Stone_Hammer.level6.name"));
								    			im.setDisplayName(new CCT().colorize(colorizee));
								    			ArrayList<String> lore1 = new ArrayList<String>();
								    			ArrayList<String> lore11 = (ArrayList<String>) plugin.getConfig().getStringList("Items.Common.Stone_Hammer.level6.lore");
								    			for(String s : lore11) {
								    				lore1.add(new CCT().colorize(s));
								    			}
								    			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
								    			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								    			im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
								    			im.setLore(lore1);
								    			item1.setItemMeta(im);
								    		}
								    		if(firstInt >= 27000) {
								    			String colorizee = plugin.getConfig().getString(("Items.Common.Stone_Hammer.level7.name"));
								    			im.setDisplayName(new CCT().colorize(colorizee));
								    			ArrayList<String> lore1 = new ArrayList<String>();
								    			ArrayList<String> lore11 = (ArrayList<String>) plugin.getConfig().getStringList("Items.Common.Stone_Hammer.level7.lore");
								    			for(String s : lore11) {
								    				lore1.add(new CCT().colorize(s));
								    			}
								    			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
								    			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								    			im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
								    			im.setLore(lore1);
								    			item1.setItemMeta(im);
								    		}
								    		if(firstInt >= 35000) {
								    			String colorizee = plugin.getConfig().getString(("Items.Common.Stone_Hammer.level8.name"));
								    			im.setDisplayName(new CCT().colorize(colorizee));
								    			ArrayList<String> lore1 = new ArrayList<String>();
								    			ArrayList<String> lore11 = (ArrayList<String>) plugin.getConfig().getStringList("Items.Common.Stone_Hammer.level8.lore");
								    			for(String s : lore11) {
								    				lore1.add(new CCT().colorize(s));
								    			}
								    			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
								    			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								    			im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
								    			im.setLore(lore1);
								    			item1.setItemMeta(im);
								    		}
								    		if(firstInt >= 45000) {
								    			String colorizee = plugin.getConfig().getString(("Items.Common.Stone_Hammer.level9.name"));
								    			im.setDisplayName(new CCT().colorize(colorizee));
								    			ArrayList<String> lore1 = new ArrayList<String>();
								    			ArrayList<String> lore11 = (ArrayList<String>) plugin.getConfig().getStringList("Items.Common.Stone_Hammer.level9.lore");
								    			for(String s : lore11) {
								    				lore1.add(new CCT().colorize(s));
								    			}
								    			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
								    			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								    			im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
								    			im.setLore(lore1);
								    			item1.setItemMeta(im);
								    		}
								    		if(firstInt >= 55000) {
								    			String colorizee = plugin.getConfig().getString(("Items.Common.Stone_Hammer.level10.name"));
								    			im.setDisplayName(new CCT().colorize(colorizee));
								    			ArrayList<String> lore1 = new ArrayList<String>();
								    			ArrayList<String> lore11 = (ArrayList<String>) plugin.getConfig().getStringList("Items.Common.Stone_Hammer.level10.lore");
								    			for(String s : lore11) {
								    				lore1.add(new CCT().colorize(s));
								    			}
								    			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
								    			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								    			im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
								    			im.setLore(lore1);
								    			item1.setItemMeta(im);
								    		}
								    		if(firstInt >= 65000) {
								    			String colorizee = plugin.getConfig().getString(("Items.Common.Stone_Hammer.level11.name"));
								    			im.setDisplayName(new CCT().colorize(colorizee));
								    			ArrayList<String> lore1 = new ArrayList<String>();
								    			ArrayList<String> lore11 = (ArrayList<String>) plugin.getConfig().getStringList("Items.Common.Stone_Hammer.level11.lore");
								    			for(String s : lore11) {
								    				lore1.add(new CCT().colorize(s));
								    			}
								    			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
								    			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								    			im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
								    			im.setLore(lore1);
								    			item1.setItemMeta(im);
								    		}
								    		if(firstInt >= 75000) {
								    			String colorizee = plugin.getConfig().getString(("Items.Common.Stone_Hammer.level12.name"));
								    			im.setDisplayName(new CCT().colorize(colorizee));
								    			ArrayList<String> lore1 = new ArrayList<String>();
								    			ArrayList<String> lore11 = (ArrayList<String>) plugin.getConfig().getStringList("Items.Common.Stone_Hammer.level12.lore");
								    			for(String s : lore11) {
								    				lore1.add(new CCT().colorize(s));
								    			}
								    			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
								    			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								    			im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
								    			im.setLore(lore1);
								    			item1.setItemMeta(im);
								    		}
								    		if(firstInt >= 90000) {
								    			String colorizee = plugin.getConfig().getString(("Items.Common.Stone_Hammer.level13.name"));
								    			im.setDisplayName(new CCT().colorize(colorizee));
								    			ArrayList<String> lore1 = new ArrayList<String>();
								    			ArrayList<String> lore11 = (ArrayList<String>) plugin.getConfig().getStringList("Items.Common.Stone_Hammer.level13.lore");
								    			for(String s : lore11) {
								    				lore1.add(new CCT().colorize(s));
								    			}
								    			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
								    			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								    			im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
								    			im.setLore(lore1);
								    			item1.setItemMeta(im);
								    		}
								    		if(firstInt >= 120000) {
								    			String colorizee = plugin.getConfig().getString(("Items.Common.Stone_Hammer.level14.name"));
								    			im.setDisplayName(new CCT().colorize(colorizee));
								    			ArrayList<String> lore1 = new ArrayList<String>();
								    			ArrayList<String> lore11 = (ArrayList<String>) plugin.getConfig().getStringList("Items.Common.Stone_Hammer.level14.lore");
								    			for(String s : lore11) {
								    				lore1.add(new CCT().colorize(s));
								    			}
								    			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
								    			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								    			im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
								    			im.setLore(lore1);
								    			item1.setItemMeta(im);
								    		}
								    		if(firstInt >= 150000) {
								    			String colorizee = plugin.getConfig().getString(("Items.Common.Stone_Hammer.level15.name"));
								    			im.setDisplayName(new CCT().colorize(colorizee));
								    			ArrayList<String> lore1 = new ArrayList<String>();
								    			ArrayList<String> lore11 = (ArrayList<String>) plugin.getConfig().getStringList("Items.Common.Stone_Hammer.level15.lore");
								    			for(String s : lore11) {
								    				lore1.add(new CCT().colorize(s));
								    			}
								    			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
								    			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								    			im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
								    			im.setLore(lore1);
								    			item1.setItemMeta(im);
								    		}
								    		
							    		else {
							    			int totalxpearned = firstInt + 5;
								    		lore.set(lijn, new CCT().colorize("&7Upgrade Progress: " + (totalxpearned) + " / " + secondInt));
								    		for(int lijn1 = 0; lijn1 < lore.size(); lijn1++) {
								    			if(lore.get(lijn1).contains(ChatColor.stripColor("["))) {
									    			double barprogress = (double) totalxpearned / (double) secondInt * 100.0;

									    			
									    			if(barprogress >= 2) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:&7:::::::::::::::::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 4) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::&7::::::::::::::::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 6) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::&7:::::::::::::::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 8) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::&7::::::::::::::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 10) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::&7:::::::::::::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 12) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::&7::::::::::::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 14) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::&7:::::::::::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 16) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::&7::::::::::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 18) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::&7:::::::::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 20) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::&7::::::::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 22) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::&7:::::::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 24) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::&7::::::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 26) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::&7:::::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 28) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::&7::::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 30) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::&7:::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 32) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::&7::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 34) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::&7:::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 36) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::&7::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 38) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::::&7:::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 40) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::::&7::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 42) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::::::&7:::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 44) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::::::&7::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 46) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::::::::&7:::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 48) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::::::::&7::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 50) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::::::::::&7:::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 52) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::::::::::&7::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 54) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::::::::::::&7:::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 56) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::::::::::::&7::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 58) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::::::::::::::&7:::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 60) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::::::::::::::&7::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 62) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::::::::::::::::&7:::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 64) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::::::::::::::::&7::::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 66) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::::::::::::::::::&7:::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 68) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::::::::::::::::::&7::::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 70) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::::::::::::::::::::&7:::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 72) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::::::::::::::::::::&7::::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 74) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::::::::::::::::::::::&7:::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 76) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::::::::::::::::::::::&7::::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 78) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::&7:::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 80) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::&7::::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 82) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::::&7:::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 84) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::::&7::::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 86) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::::::&7:::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 88) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::::::&7::::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 90) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::::::::&7:::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 92) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::::::::&7::::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 94) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::::::::::&7:::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 96) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::::::::::&7::&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 98) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a:::::::::::::::::::::::::::::::::::::::::::::::::&7:&7] &a" + barprogress + "%"));
									    			}
									    			if(barprogress >= 100) {
									    				lore.set(lijn1, new CCT().colorize("&7[&a::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a" + barprogress + "%"));
									    			
									    			}
								    			}
								    		}
								    		
								    		im.setLore(lore);
								    		item1.setItemMeta(im);
									    		
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
	}
}