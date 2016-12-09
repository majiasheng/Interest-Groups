package data;

import java.io.Serializable;
import java.util.ArrayList;

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
        private String groupName;

        // list of user is probably not needed.. we will think about this later
	private ArrayList<User> users;
	private ArrayList<Post> posts;

	public DiscussionGroup() {

	}

        public String getGroupName() {
            return this.groupName;
        }
        
        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }
}