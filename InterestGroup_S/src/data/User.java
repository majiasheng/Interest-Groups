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
    private ArrayList<Post> posts;
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

    /**
     * For ag command, get all existing groups
     * @return 
     */
    public ArrayList<DiscussionGroup> getGroups() {
        return groups;
    }

    public void addGroup(DiscussionGroup group) {
        groups.add(group);
    }
    
    /**
     * For rg command, read all posts under the specified group
     * @param group, read the posts under the given group 
     */
    public void getPosts(DiscussionGroup group) {
        
    }
    
    /**
     * For sg command, get all subscribed groups
     * @return 
     */
    public ArrayList<String> getSubscribedGroups() {
        return subscribedGroups;
    }
    
    /**
     * Load a new set of subscribed groups (initially, load to a new User obj)
     * @param subscribedGroups 
     */
    public void setSubscribedGroups(ArrayList<String> subscribedGroups) {
        this.subscribedGroups = subscribedGroups;
    }
    
    /**
     * For sg command
     * Subscribe to a group
     * @param subscribedGroup: add this group to the subscribed group list
     **/
    public void subscribedGroup(String subscribedGroup) {
        this.subscribedGroups.add(subscribedGroup);
    }
    
    /**
     * For sg command
     * @param group, remove this group from the subscribed group list
     */
    public void unsubscribedGroup(String group) {
        this.subscribedGroups.remove(group);
    }
    
    
    
}
