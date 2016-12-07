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

    }
}
