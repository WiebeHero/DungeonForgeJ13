package me.WiebeHero.Skills;

import java.util.HashMap;

import me.WiebeHero.DFPlayerPackage.Enums.Classes;

public class DFClassManager {
	
	private HashMap<Classes, DFClass> classList;
	
	public DFClassManager() {
		this.classList = new HashMap<Classes, DFClass>();
		this.classList.put(Classes.WRATH, new DFClass() {
			@Override
			public void ability() {
				
			}
		});
	}
}
