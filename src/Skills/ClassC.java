package Skills;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import Skills.Enums.Classes;


public class ClassC {
	SkillJoin join = new SkillJoin();
	public static HashMap<UUID, Classes> classes = new HashMap<UUID, Classes>();
	public void registerClass(UUID id) {
		if(!classes.containsKey(id)) {
			String className = join.getClassList().get(id);
			switch(className) {
				case "Wrath":
					classes.put(id, Classes.WRATH);
				case "Lust":
					classes.put(id, Classes.LUST);
				case "Envy":
					classes.put(id, Classes.ENVY);
				case "Sloth":
					classes.put(id, Classes.SLOTH);
				case "Pride":
					classes.put(id, Classes.PRIDE);
				case "Gluttony":
					classes.put(id, Classes.GLUTTONY);
				case "Greed":
					classes.put(id, Classes.GREED);
				default:
					classes.put(id, Classes.NONE);
			}
		}
	}
	public Classes getClass(UUID id) {
		return classes.get(id);
	}
	public HashMap<UUID, Classes> getClassList(){
		return classes;
	}
}
