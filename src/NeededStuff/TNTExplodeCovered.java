package NeededStuff;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class TNTExplodeCovered implements Listener{
	@EventHandler
	public void loopBlocksTNT(EntityExplodeEvent event) {
		if(event.getEntity() instanceof TNTPrimed) {
			boolean checkIt = false;
			TNTPrimed tnt = (TNTPrimed) event.getEntity();
			Block tntGetBlock = (Block) tnt.getLocation().getBlock();
			int radius = 0;
			for (int x = -(radius); x <= radius; x ++){
				for (int y = -(radius); y <= radius; y ++){
					for (int z = -(radius); z <= radius; z ++){
						Block gottenBlock = tntGetBlock.getRelative(x,y,z);
						if (gottenBlock.getType() == Material.SAND){
							checkIt = true;
						}
					}
				}
			}
			if(checkIt == true) {
				int radius1 = 4;
				for (int x = -(radius1); x <= radius1; x ++){
					for (int y = -(radius1); y <= radius1; y ++){
						for (int z = -(radius1); z <= radius1; z ++){
							Block gottenBlock = tntGetBlock.getRelative(x,y,z);
							BlockData state = gottenBlock.getBlockData();
							if (state instanceof Waterlogged && ((Waterlogged) state).isWaterlogged()) {
								gottenBlock.breakNaturally();
								gottenBlock.setType(Material.WATER);
							}
						}
					}
				}
			}
		}
	}
}
