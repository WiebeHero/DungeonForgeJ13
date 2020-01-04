package me.WiebeHero.CustomMethods;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.NodeEqualityPredicate;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;

public class MethodLuck {
	public void addParent(User user, String group) {
        user.data().add(InheritanceNode.builder(group).build());
    }
    
    public void removeParent(User user, String group) {
        user.data().clear(node -> NodeType.INHERITANCE.matches(node) && NodeType.INHERITANCE.cast(node).getGroupName().equals(group));
    }
    
    public boolean containsParrent(User user, String group) {
    	LuckPerms api = LuckPermsProvider.get();
    	Group g = api.getGroupManager().getGroup(group);
    	return user.data().contains(InheritanceNode.builder(g).build(), NodeEqualityPredicate.ONLY_KEY).asBoolean();
    }
    
    public User loadUser(UUID uuid) {
    	LuckPerms api = LuckPermsProvider.get();
		UserManager manager = api.getUserManager();
		User user = null;
		if(!manager.isLoaded(uuid)) {
			try {
				user = api.getUserManager().loadUser(uuid).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			user = manager.getUser(uuid);
		}
		return user;
    }
}
