package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Ruoping Lin
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

    public User() {
        
    }
    public User(String id) throws IOException {
        this.id = id;
        
        // initialize groups
        groups = new ArrayList<>();
        for(DiscussionGroup g : groups) {
            g = new DiscussionGroup();
        }
        subscribedGroups = new ArrayList<String>();
        manager = new DataManager(this);

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
    
    /*
    * add subscribed group to the subscribed group list
    * @param subscribedGroup: a group
    */
    public void addSubscribedGroup(String subscribedGroup) {
        this.subscribedGroups.add(subscribedGroup);
    }
    
    /*
    * load a new set of subscribed groups (initially, load to a new User obj)
    */
    public void setSubscribedGroups(ArrayList<String> subscribedGroups) {
        this.subscribedGroups = subscribedGroups;
    }
    
}
