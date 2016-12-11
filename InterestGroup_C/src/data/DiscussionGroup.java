package data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Ruoping Lin
 */

/**
 * Managers a discussion group
 * A list of discussion group is stored in DiscussionGroupList.json formatted as (group ID: group name)
 */
public class DiscussionGroup implements Serializable{

        private String groupName;           // Every group is identified by its unique group name
	private String groupID;             // For ?

        private ArrayList<Post> posts;      // Posts under this group
        private DataManager dataManager;
        
        /**
         * If a DiscussionGroup object is created by using the default constructor,
         * a group name must be specified by using the setter method
         */
	public DiscussionGroup() {
            
            groupName = null;              
            dataManager = new DataManager();
	}
        
        public DiscussionGroup(String groupName) {
            
            this.groupName = groupName;
            dataManager = new DataManager();
        }

        /**
         * Uses group name to identify each group
         * @return 
         */
        public String getGroupName() {
            return this.groupName;
        }
        
        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }
        
        /**
         * 
         * @return 
         */
        public String getGroupID() {
            return this.groupID;
        }
        public void setGroupiD(String groupID) {
            this.groupID = groupID;
        }
        
        /**
         * Gets all posts that belong to this group
         * @param groupName a specified group
         * @return a list of posts
         */
        public ArrayList<Post> getPosts() {
            posts = new ArrayList<>();
            
            // Loads all posts from data base (group name must be known)
            posts = dataManager.loadAllPosts();
            
            return posts;
        }
        
        /**
         * Makes a new post to a specific group
         * For subcommand rgp (post to group) of rg (read group)
         * @param post a new post
         */
        public void setNewPostToGroup(Post post) {
            this.posts.add(post);
        }
        
        
}