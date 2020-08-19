package me.WiebeHero.DataPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.WiebeHero.DataPackage.DataTypes.DataType;
import me.WiebeHero.Moderation.Punish;
import me.WiebeHero.Moderation.PunishManager;

public class ProcessData implements Listener{
	
	private PunishManager punishManager;
	
	public ProcessData(PunishManager punishManager) {
		this.punishManager = punishManager;
	}

	@EventHandler
	public void dataRecieve(DataObtainEvent event) {
		if(event.getDataType() == DataType.MODERATION) {
			Bukkit.broadcastMessage(event.getData());
			String data[] = event.getData().split(",,");
			UUID uuid = UUID.fromString(data[1]);
			Punish punish = new Punish(uuid);
			//Mute Data
			Boolean mutePerm = Boolean.valueOf(data[2]);
			Long muteTime = Long.valueOf(data[3]);
			ArrayList<String> muteReasons = new ArrayList<String>();
			if(!data[4].equals(" ")) {
				muteReasons.addAll(Arrays.asList(data[4].split(",")));
			}
			ArrayList<String> mutedWhen = new ArrayList<String>();
			if(!data[5].equals(" ")) {
				mutedWhen.addAll(Arrays.asList(data[5].split(",")));
			}
			ArrayList<String> mutedBy = new ArrayList<String>();
			if(!data[6].equals(" ")) {
				mutedBy.addAll(Arrays.asList(data[6].split(",")));
			}
			Boolean banPerm = Boolean.valueOf(data[7]);
			Long banTime = Long.valueOf(data[8]);
			ArrayList<String> banReasons = new ArrayList<String>();
			if(!data[9].equals(" ")) {
				banReasons.addAll(Arrays.asList(data[9].split(",")));
			}
			ArrayList<String> bannedWhen = new ArrayList<String>();
			if(!data[10].equals(" ")) {
				bannedWhen.addAll(Arrays.asList(data[10].split(",")));
			}
			ArrayList<String> bannedBy = new ArrayList<String>();
			if(!data[11].equals(" ")) {
				bannedBy.addAll(Arrays.asList(data[11].split(",")));
			}
			punish.setMutePerm(mutePerm);
			punish.setMuteTime(muteTime);
			punish.setMuteReason(muteReasons);
			punish.setMuteDate(mutedWhen);
			punish.setMutedBy(mutedBy);
			punish.setBanPerm(banPerm);
			punish.setBanTime(banTime);
			punish.setBanReason(banReasons);
			punish.setBanDate(bannedWhen);
			punish.setBannedBy(bannedBy);
			this.punishManager.add(uuid, punish);
		}
	}
	
}
