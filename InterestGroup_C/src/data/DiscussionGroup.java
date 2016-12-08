package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author majiasheng
 */

/**
 * Discussion Group
 */
public class DiscussionGroup  implements Serializable{

    private int id;
    private String name;

    private ArrayList<Post> posts;

    public DiscussionGroup() {

        posts = new ArrayList<>();
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


    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

}