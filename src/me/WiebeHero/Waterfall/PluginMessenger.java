package me.WiebeHero.Waterfall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class PluginMessenger implements PluginMessageListener{
	
	private CustomEnchantments main = CustomEnchantments.getInstance();
	private ArrayList<String> servers;
	private HashMap<String, Pair<String, Integer>> serverIps;
	
	public PluginMessenger() {
		this.servers = new ArrayList<String>();
		this.serverIps = new HashMap<String, Pair<String, Integer>>();
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if(channel.equals("BungeeCord")) {
			ByteArrayDataInput input = ByteStreams.newDataInput(message);
			String subchannel = input.readUTF();
			if(subchannel.equals("GetServers")) {
				String serverList[] = input.readUTF().split(", ");
				this.servers = new ArrayList<String>(Arrays.asList(serverList));
				for(int i = 0; i < this.servers.size(); i++) {
					ByteArrayDataOutput output = ByteStreams.newDataOutput();
					output.writeUTF("ServerIP");
					output.writeUTF(this.servers.get(i));
					player.sendPluginMessage(this.main, "BungeeCord", output.toByteArray());
				}
			}
			else if(subchannel.equals("ServerIP")) {
				String serverName = input.readUTF();
				String ip = input.readUTF();
				int port = input.readUnsignedShort();
				this.serverIps.put(serverName, new Pair<String, Integer>(ip, port));
			}
		}
	}
	
	public void connect(Player player, String server) {
		ByteArrayDataOutput output = ByteStreams.newDataOutput();
		output.writeUTF("Connect");
		output.writeUTF(server);
		player.sendPluginMessage(this.main, "BungeeCord", output.toByteArray());
	}
	
	public void getServers(Player player) {
		ByteArrayDataOutput output = ByteStreams.newDataOutput();
		output.writeUTF("GetServers");
		player.sendPluginMessage(this.main, "BungeeCord", output.toByteArray());
	}
	
	public void getServerIP(Player player, String serverName) {
		ByteArrayDataOutput output = ByteStreams.newDataOutput();
		output.writeUTF("ServerIP");
		output.writeUTF(serverName);
		player.sendPluginMessage(this.main, "BungeeCord", output.toByteArray());
	}
	
	public ArrayList<String> getServers(){
		return this.servers;
	}
	
	public HashMap<String, Pair<String, Integer>> getServerIps(){
		return this.serverIps;
	}
	
	public Pair<String, Integer> getServerIp(String serverName){
		return this.serverIps.get(serverName);
	}
}
