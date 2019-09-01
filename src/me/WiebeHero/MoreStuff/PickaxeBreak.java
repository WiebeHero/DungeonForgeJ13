package me.WiebeHero.MoreStuff;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;

public class PickaxeBreak {
	public void pickaxeBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Location loc = block.getLocation();
		for(int x = -1;x <= 1;x++){
		  for(int y = -1;y <= 1;y++){
		    for(int z = -1;z <= 1;z++){
		      loc.getWorld().getBlockAt((int)(loc.getX() + x), (int)(loc.getX() + x), (int)(loc.getX() + x));
		    }
		  }
		}
	}
}
