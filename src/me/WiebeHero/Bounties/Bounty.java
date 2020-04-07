package me.WiebeHero.Bounties;

import java.util.ArrayList;
import java.util.UUID;

import me.WiebeHero.CustomEnchantments.CCT;

public class Bounty {
	
	private UUID uuid;
	private String bountyName;
	private ArrayList<String> bountyText;
	
	public Bounty(String name, ArrayList<String> bountyText, UUID uuid) {
		this.bountyName = new CCT().colorize(name);
		for(int i = 0; i < bountyText.size(); i++) {
			String s = bountyText.get(i);
			s = new CCT().colorize(s);
			bountyText.set(i, s);
		}
		this.bountyText = bountyText;
		this.uuid = uuid;
	}
	
	public void setBountyName(String name) {
		this.bountyName = new CCT().colorize(name);
	}
	
	public String getBountyName() {
		return this.bountyName;
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	public void setBountyText(ArrayList<String> bountyText) {
		for(int i = 0; i < bountyText.size(); i++) {
			String s = bountyText.get(i);
			s = new CCT().colorize(s);
			bountyText.set(i, s);
		}
		this.bountyText = bountyText;
	}
	
	public ArrayList<String> getBountyText() {
		return this.bountyText;
	}
}
