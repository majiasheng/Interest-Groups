package interest_group_s;

import com.fasterxml.jackson.core.*;
import data.Constants;
import data.DiscussionGroup;

import java.util.ArrayList;

/**
 *
 * @author majiasheng
 */

/**
 * Server side
 */
public class InterestGroup_Server {

	private ArrayList<DiscussionGroup> groups;

	public InterestGroup_Server() {
		/* initialize discussion group array */
		groups = new ArrayList<>();


	}


	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		//TODO: spin and listen for client request
	}

	/**
	 * Handles the command by calling corresponding handlers
	 */
	static void handleCMD(String command) {
            if(command.equals(Constants.LOGOUT)){
            // send server a logout request 
            } else if(command.equals(Constants.LOGIN)) {
            // send server a login request 
            }
	}

	/**
	 * Updates program info on every client change requests
	 */
	static void update() {}


	static void login_handler() {}

	/****************************************
	 ag - Ruoping
	 ****************************************/
	/**
	 * lists all existing groups, a default number of groups at a time 
	 */
	static void ag_handler() {}

	/**
	 * lists N groups at a time
	 */
	static void ag_handler(int N){};

	/**
	 * - subscribe to groups
	 */
	static void s_handler_ag() {}

	/**
	 *	unsubscribe
	 */
	static void u_handler_ag() {}

	/**
	 *	lists the next N discussion groups
	 */
	static void n_handler_ag() {}

	/**
	 *	exit from ag command
	 */
	static void q_handler_ag() {}

	/****************************************
	 sg - Liwen
	 ****************************************/
	static void sg_handler() {}

	/**
	 *	marks a post as read
	 */
	static void r_handler_sg() {}

	/**
	 *	lists the next N discussion groups
	 */
	static void n_handler_sg() {}

	/**
	 *	post to the group
	 */
	static void p_handler_sg() {}

	/**
	 *	exit from sg command
	 */
	static void q_handler_sg() {}

	/****************************************
	 rg - Jia Sheng
	 ****************************************/


	/**
	 * read group
	 */
//	static void rg_handler(String gname) {
//		/* unread (new) posts should be displayed first*/
//		DiscussionGroup g;
//		g = getGroupByName(gname);
//
//	}

	static void rg_handler(String gname, int N) {


	}

	static void id_handler_rg() {

	}

	/**
	 * lists the next N posts.
	 * If all posts are displayed, the program exits from the rg command mode
	 */
	static void n_id_handler_rg(int N) {

	}

	/**
	 * would quit displaying the post content.
	 * The list of posts before opening the post is shown
	 * again with the post just opened marked as read.
	 */
	static void q_id_handler_rg() {

	}

	/**
	 * marks post in range of h to t as read
	 */
	static void r_handler_rg(int h, int t) {

	}

	/**
	 *	lists the next N discussion groups
	 */
	static void n_handler_rg() {}

	/**
	 *	post to the group
	 */
	static void p_handler_rg() {}

	/**
	 *	exit from rg command
	 */
	static void q_handler_rg() {}

	/**
	 * Returns the group specified by gname
	 */
//	static DiscussionGroup getGroupByName(String gname) {
//
//	}

	/****************************************
	 OTHER FUNCTIONS
	 ****************************************/

	void createUser() {}


}