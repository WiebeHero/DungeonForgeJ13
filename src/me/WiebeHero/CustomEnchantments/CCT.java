package me.WiebeHero.CustomEnchantments;

import net.md_5.bungee.api.ChatColor;

public class CCT {
	public CCT() {
		
	}
	public String colorize(String msg)
    {
        String coloredMsg = ChatColor.translateAlternateColorCodes('&', msg);
        return coloredMsg;
    }
}