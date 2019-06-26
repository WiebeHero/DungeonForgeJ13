package Skills;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import Skills.Enums.Classes;


public class ClassC {
	SkillJoin join = new SkillJoin();
	public static HashMap<UUID, Classes> classes = new HashMap<UUID, Classes>();
	public void registerClass(Player p) {
		if(!classes.containsKey(p.getUniqueId())) {
			String className = join.getClassList().get(p.getUniqueId());
			switch(className) {
				case "Wrath":
					classes.put(p.getUniqueId(), Classes.WRATH);
				case "Lust":
					classes.put(p.getUniqueId(), Classes.LUST);
				case "Envy":
					classes.put(p.getUniqueId(), Classes.ENVY);
				case "Sloth":
					classes.put(p.getUniqueId(), Classes.SLOTH);
				case "Pride":
					classes.put(p.getUniqueId(), Classes.PRIDE);
				case "Gluttony":
					classes.put(p.getUniqueId(), Classes.GLUTTONY);
				case "Greed":
					classes.put(p.getUniqueId(), Classes.GREED);
			}
		}
	}
	public Classes getClass(Player p) {
		return classes.get(p.getUniqueId());
	}
	public HashMap<UUID, Classes> getClassList(){
		return classes;
	}
}
