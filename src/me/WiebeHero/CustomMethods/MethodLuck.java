package me.WiebeHero.CustomMethods;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
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
    	return user.data().contains(InheritanceNode.builder(g).build(), NodeEqualityPredicate.IGNORE_EXPIRY_TIME_AND_VALUE).asBoolean();
    }
}
