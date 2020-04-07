package me.WiebeHero.Bounties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CollectionBounty extends Bounty{
	
	public ArrayList<ItemStack> rewards;
	public int xp;
	public HashMap<Material, Integer> progress;
	public HashMap<Material, Integer> required;
	
	public CollectionBounty(String bountyName, ArrayList<String> bountyText, int xp, UUID uuid, HashMap<Material, Integer> progress, HashMap<Material, Integer> required) {
		super(bountyName, bountyText, uuid);
		this.xp = xp;
		this.progress = progress;
		this.required = required;
	}
	
	public ArrayList<ItemStack> getRewards() {
		return rewards;
	}

	public void setRewards(ArrayList<ItemStack> rewards) {
		this.rewards = rewards;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public HashMap<Material, Integer> getRequired() {
		return required;
	}

	public void setRequired(HashMap<Material, Integer> required) {
		this.required = required;
	}
	
	public void addRequired(Material mat, int amount) {
		this.required.put(mat, amount);
	}
	
	public void removeRequired(Material mat) {
		if(this.required.containsKey(mat)) {
			this.required.remove(mat);
		}
	}

	public HashMap<Material, Integer> getProgress() {
		return progress;
	}

	public void setProgress(HashMap<Material, Integer> progress) {
		this.progress = progress;
	}
	
	public void addProgress(Material mat, int amount) {
		this.progress.put(mat, amount);
	}
	
	public void removeProgress(Material mat) {
		if(this.progress.containsKey(mat)) {
			this.progress.remove(mat);
		}
	}

	public void setXP(int xp) {
		this.xp = xp;
	}
	
	public int getXP() {
		return this.xp;
	}
	
	
}
