package me.WiebeHero.Scoreboard;

import org.bukkit.World;
import org.bukkit.entity.LivingEntity;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;

public class WGMethods {
	public boolean isInZone(LivingEntity ent, String zone) {
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		World world = ent.getWorld();
		RegionManager regions = container.get(BukkitAdapter.adapt(world));
		if(regions.hasRegion(zone)) {
			if(regions.getRegion(zone).contains(ent.getLocation().getBlockX(), ent.getLocation().getBlockY(), ent.getLocation().getBlockZ())) {
				return true;
			}
		}
		return false;
	}
}
