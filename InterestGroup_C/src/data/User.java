package data;

import java.util.ArrayList;

/**
 *
 * @author majiasheng
 */
public class User {
    private int id;
    private String name;
    
    private ArrayList<DiscussionGroup> groups;
    //TODO: groups have posts, and each posts has a read-or-not status

    public User(int id) {
        this.id = id;
        // initialize groups
        groups = new ArrayList<>();
        for(DiscussionGroup g : groups) {
            g = new DiscussionGroup();
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    
}
