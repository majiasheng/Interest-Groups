
package interest_group_s;

import com.fasterxml.jackson.core.*;
import data.DiscussionGroup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

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

		try {

			ServerSocket s = new ServerSocket(6666);
			while (true) {
				Socket incoming = s.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
				PrintWriter out = new PrintWriter( new OutputStreamWriter(incoming.getOutputStream()));
				out.println("Hello! ....");
				out.println("Enter BYE to exit.");
				out.flush();
				while (true) {
					//receive a command from the server
					String command = in.readLine();
					if (command == null) {
						break; // client closed connection
					} else {
						//identify the command
						commandMenu(command);
						out.println("Echo: " + command);
						out.flush();
						if (command.trim().equals("BYE"))
							break;
					}
				}
				incoming.close();
			}
		} catch (Exception e) {}
	}

	// identify the command received from the client
	public static void commandMenu (String command){
		//System.out.println("HERE");
		if(command.startsWith("login")==true){
			System.out.println("It's login");
			//token string, get user id, and pass it to login function
			StringTokenizer multiTokenizer = new StringTokenizer(command, " ");
			String user = null;
			while (multiTokenizer.hasMoreTokens()){
				user = multiTokenizer.nextToken();
			}
			loginFunction(user);
			//need to set login == true for further command
		}
	}

	//login function
    /* check user ID if exsit from local user data JSON file*/
	public static void loginFunction(String user){
		System.out.print("userID:"+user);
		String checkuser = '"'+user+'"';
		//System.out.println(checkuser);
		//read json
		//FileReader fileReader = new FileReader("user_info.json");
		//check id if in the user_info json file


	}

    	//TODO: spin and listen for client request


    /**
     * Handles the command by calling corresponding handlers
     */
    static void handleCMD(String cmd) {
    	
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
	static void rg_handler(String gname) {
		/* unread (new) posts should be displayed first*/
		DiscussionGroup g;
		g = getGroupByName(gname);

	}

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
	static DiscussionGroup getGroupByName(String gname) {

	}

	/****************************************
				OTHER FUNCTIONS
	****************************************/
	
	void createUser() {}

	
}
