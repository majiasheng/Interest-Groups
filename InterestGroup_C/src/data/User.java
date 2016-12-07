package data;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author majiasheng
 */
public class User {
    private String id;
    private String name;
    private File   userDataFile;
    private DataManager manager;
    private ArrayList<String> subscribedGroups;
    //TODO: groups have posts, and each posts has a read-or-not status
    
    private ArrayList<DiscussionGroup> groups;
    //TODO: groups have posts, and each posts has a read-or-not status

    public static void main(String[] args) {
        
        User user = new User("333");
        
    }
    
    public User() {
        
    }
    public User(String id) {
        this.id = id;
        manager = new DataManager(this);
        // initialize groups
        groups = new ArrayList<>();
        for(DiscussionGroup g : groups) {
            g = new DiscussionGroup();
        }

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
        subscribedGroups = new ArrayList<String>();
        subscribedGroups.add("Java Group");
        subscribedGroups.add("Javascript Group");
        subscribedGroups.add("C Group");
        return subscribedGroups;
    }
    
    public void setSubscribedGroups(ArrayList<String> subscribedGroups) {
        this.subscribedGroups = subscribedGroups;
    }
    
}
