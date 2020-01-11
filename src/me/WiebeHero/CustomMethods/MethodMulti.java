package me.WiebeHero.CustomMethods;

import org.bukkit.plugin.Plugin;

import com.onarandombox.MultiverseCore.MultiverseCore;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class MethodMulti {
	public MultiverseCore getMultiverseCore() {
        Plugin plugin = CustomEnchantments.getInstance().getServer().getPluginManager().getPlugin("Multiverse-Core");
 
        if (plugin instanceof MultiverseCore) {
            return (MultiverseCore) plugin;
        }
 
        throw new RuntimeException("MultiVerse not found!");
    }
}
