package data;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * @author majiasheng
 */
public class User {
    private String id;
    private String name;
    
    private ArrayList<DiscussionGroup> groups;
    private DataManager manager;
    private ArrayList<String> subscribedGroups;
    //TODO: groups have posts, and each posts has a read-or-not status

    public User() { }
    
    public User(String id) {
        manager = new DataManager(this);      // manager managers (has save & load methods)
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DiscussionGroup> getGroups() {
        return groups;
    }

    public void addGroup(DiscussionGroup group) {
        groups.add(group);
    }
    
    public ArrayList<String> getSubscribedGroups() {
        
        return subscribedGroups;
    }
    
    public void setSubscribedGroups(ArrayList<String> subscribedGroups) {
        this.subscribedGroups = subscribedGroups;
    }
    
}
