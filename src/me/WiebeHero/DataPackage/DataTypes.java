package me.WiebeHero.DataPackage;

import com.google.common.base.Enums;

public class DataTypes {
	public DataType getIfPresent(String data) {
		return Enums.getIfPresent(DataType.class, data).or(DataType.OTHER);
	}
	public enum DataType{
		MODERATION,
		OTHER;
	}
}
