
package interest_group_s;

import com.fasterxml.jackson.core.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author majiasheng
 */

/**
 * Server side
 */
public class InterestGroup_Server {

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

    // identify the command received from the clien
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
            //need to set login == true for futhur command
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
	/**
     * Updates program info on every client change requests
	*/
	void update() {}


	void login_handler() {}

	/****************************************
					ag
	****************************************/
	/**
	 * lists all existing groups, a default number of groups at a time
	 */
    void ag_handler() {}

	/**
	 * lists N groups at a time
	 */
	void ag_handler(int N){};

	/****************************************
					sg
	****************************************/
	void sg_handler() {}


	/****************************************
					rg
	****************************************/
	void rg_handler() {}

	/****************************************
				SUB COMMNADS
	****************************************/
	/**
	 * - subscribe to groups
	 */
	void s_handler() {}

	/**
	 *	unsubscribe
	 */
	void u_handler() {}

	/**
	 *	lists the next N discussion groups
	 */
	void n_handler() {}

	/**
	 *	exit from ag command
	 */
	void q_handler() {}

	/**
	 *	marks a post as read
	 */
	void r_handler() {}

	/**
	 *	post to the group
	 */
	void p_handler() {}
}
