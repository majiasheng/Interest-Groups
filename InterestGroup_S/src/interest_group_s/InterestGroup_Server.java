package interest_group_s;

import com.fasterxml.jackson.core.*;
import data.Constants;
import data.DiscussionGroup;
import data.User;

import java.util.ArrayList;

/**
 *
 * @author majiasheng
 */

/**
 * Server side
 */
public class InterestGroup_Server {

	
	private static ArrayList<DiscussionGroup> groups;
    private static ArrayList<User> users;

    /*****************************************
    		worker server for clients
    *****************************************/
	class WorkerServer extends Thread {
	    private Socket connectionSocket;
	    private Object clientRequest;

	    public WorkerServer(Socket connectionSocket) {
	        this.socket = socconnectionSocketket;
	    }

	    public void run(){
	    	// create input/output channels 
	    	ObjectInputStream input_from_client = new ObjectInputStream(new InputStreamReader(connectionSocket.getInputStream()));
			ObjectOutputStream output_to_client = new ObjectOutputStream(connectionSocket.getOutputStream());
			
			Object response;

			while(true){
				/************************************
                    listen for request from client
                 ************************************/
				clientRequest = input_from_client.readObject();
				
				// handles client's request
				response = handleClientRequest(clientRequest);
				

				// 
				output_to_client.writeObject(response);

			}
	    }
	} /* end of worker server */

	public static void main(String argv[]) {

		System.out.println("Starting server...");
		ServerSocket welcomeSocket = new ServerSocket(6789);
		System.out.println("Listening...");

		while(true) {
				try {
					Socket connectionSocket = welcomeSocket.accept();
					
					// make a worker server for each of the connected clients 
					WorkerServer workerServer = WorkerServer(connectionSocket);


				} catch(Exception e) {
					System.out.println("ERROR");
				}
			} 
		}

	/**
	 * parses clientRequest (array of objects) and handles the request 
	 */
	public static void handleClientRequest(Object clientRequest) {
		// first check if the client request is LOGOUT
		// if so, may want to update database and detach the thread

	}

	public ArrayList<User> getUsers() {
        return users;
    }

	public User getUserById(int id) {

        return users;
    }    

    public void addUser(User user) {
        users.add(user);
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