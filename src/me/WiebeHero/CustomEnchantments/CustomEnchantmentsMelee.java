package me.WiebeHero.CustomEnchantments;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class CustomEnchantmentsMelee implements Listener{
	public Items ci = new Items();
	
	   

	public void onEnable() {
	        Bukkit.getServer().getPluginManager().registerEvents(this, (Plugin) this);
	        System.out.println(">> CustomEnchants has been Enabled! <<");
	        System.out.println(">> Plugin by WiebeHero <<");
	    }
	   
	public void onDisable() {
	        System.out.println("<< CustomEnchants has been Disabled! >>");
	        System.out.println("<< Plugin by WiebeHero >>");
	    }


}
	   
 
	