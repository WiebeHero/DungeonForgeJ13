package me.WiebeHero.DataPackage;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.WiebeHero.DataPackage.DataTypes.DataType;

public class DataObtainEvent extends Event{
	
	private static final HandlerList handlers = new HandlerList();
	private String data;
	private DataType type;
	
	//-------------------------------------------
	//Data Obtain Event
	//Description: an event that is called when
	//data from another server is recieved. Data
	//Syntax through string is as the following:
	//Argument 0: Data Type. View DataType enum
	//for more details.
	//Argument 1 - Infinite: Data that is
	//contained within the string.
	//Seperator in data is &&
	
	public DataObtainEvent(String data){
        this.data = data;
        String args[] = this.data.split(",,");
        this.type = new DataTypes().getIfPresent(args[0]);
    }
	
	public String getData() {
		return this.data;
	}
	
	public DataType getDataType() {
		return this.type;
	}

	@Override public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
}
