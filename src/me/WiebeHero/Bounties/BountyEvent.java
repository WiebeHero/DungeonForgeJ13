package me.WiebeHero.Bounties;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BountyEvent implements Listener{
	
	private BountyManager manager;
	
	public BountyEvent(BountyManager manager) {
		this.manager = manager;
	}
	
	@EventHandler
	public void pickupEvent(BlockBreakEvent event) {
		if(!event.isCancelled()) {
			if(event.getBlock() != null) {
				Player player = event.getPlayer();
				if(this.manager.getBountyList(player.getUniqueId()) != null) {
					ArrayList<Bounty> bountyList = this.manager.getBountyList(player.getUniqueId());
					for(int i = 0; i < bountyList.size(); i++) {
						Bounty b = bountyList.get(i);
						if(b instanceof CollectionBounty) {
							CollectionBounty bounty = (CollectionBounty) b;
							Material mat = event.getBlock().getType();
							if(bounty.getRequired().containsKey(mat)) {
								int progress = 0;
								int required = bounty.getRequired().get(mat);
								if(bounty.getProgress().containsKey(mat)) {
									progress = bounty.getProgress().get(mat);
								}
								if(progress < required) {
									progress++;
									bounty.getProgress().put(mat, progress);
								}
							}
						}
					}
				}
			}
		}
	}
}
