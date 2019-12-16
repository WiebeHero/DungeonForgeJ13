package me.WiebeHero.CustomEnchantments;

import org.bukkit.plugin.Plugin;

public class CCT {
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	public CCT() {
		
	}
	public String colorize(String msg)
    {
        String coloredMsg = "";
        for(int i = 0; i < msg.length(); i++)
        {
            if(msg.charAt(i) == '&') {
            	coloredMsg += '§';
            }     
            else
                coloredMsg += msg.charAt(i);
        }
        return coloredMsg;
    }
}