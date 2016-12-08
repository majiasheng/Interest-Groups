package data;

import java.util.ArrayList;

/**
 *
 * @author majiasheng
 */

/**
 * Discussion Group
 */
public class DiscussionGroup {

	private int id;
	private String name;
        private String groupName;

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