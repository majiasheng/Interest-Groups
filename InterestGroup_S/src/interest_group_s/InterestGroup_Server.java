package interest_group_s;

//import com.fasterxml.jackson.core.*;
import data.Constants;
import static data.Constants.DEFAULT_PATH;
import static data.Constants.JSON_EXTENSION;
import data.DataManager;
import data.DiscussionGroup;
import data.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author majiasheng
 */

/**
 * Server side
 */
public class InterestGroup_Server {

    private static ArrayList<Socket> clients;
    private static ArrayList<DiscussionGroup> groups;
    private static Map<Integer, User> users;

    /*****************************************
    		worker server for clients
    *****************************************/
    static class WorkerServer extends Thread {
        private Socket connectionSocket;
        private Object clientRequest;

        /*	construct worker server dedicated to the 
                    specified socket (client) 
                    (worker server shares 
                    database and functions with 
                    all other servers)                       */
        public WorkerServer(Socket connectionSocket) {
            this.connectionSocket = connectionSocket;
        }

        public void run(){
            // create input/output channels 
            ObjectInputStream input_from_client;
            ObjectOutputStream output_to_client;
            try {
                
                System.out.println("creating in out stream");
                input_from_client = new ObjectInputStream(connectionSocket.getInputStream());
                output_to_client = new ObjectOutputStream(connectionSocket.getOutputStream());
                
                System.out.println("finish creating in out stream");
                Object response;

                while(true){
                    /************************************
                        listen for request from client
                    ************************************/
                    clientRequest = input_from_client.readObject();
                    // testing 
//                    System.out.println((String)clientRequest);

                    // // handles client's request
                     response = handleClientRequest(clientRequest);

                    // // respond to client request
                    // output_to_client.writeObject(response);
                }
            } catch (IOException ex) {
                Logger.getLogger(InterestGroup_Server.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ERROR");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InterestGroup_Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    } /* end of worker server */
    /****************************************************/

    public static void main(String argv[]) throws IOException {
        //TODO: how to clients disconnection
        //TODO: initialize data structures
        clients = new ArrayList<>();

        System.out.println("Starting server...");
        ServerSocket welcomeSocket = new ServerSocket(6666);
        System.out.println("Listening...");

        while(true) {
            try {
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("new client " + connectionSocket.toString());
                // make a worker server for each of the connected clients 
                WorkerServer workerServer = new WorkerServer(connectionSocket);
                workerServer.start();

            } catch(Exception e) {
                System.out.println("ERROR");
            }
        } 
    }

    /**
     * parses clientRequest  - array list of objects in the format of:
     *              Format: State, command(as array list), user object
     * and handles the request 
     */
    public static Object handleClientRequest(Object clientRequest) throws IOException {
        ArrayList<Object> clientRequestList = (ArrayList<Object>)clientRequest;
        
        // return value
        Object rtval = new User();
        
        // get state
        State state = (State)clientRequestList.get(0);  
        // get command in the form of array list 
        ArrayList<String> command = (ArrayList<String>)clientRequestList.get(1);
        // get user object 
        User user = (User)clientRequestList.get(2);
        
        if(state == State.NOT_LOGGED_IN) {
            // log user in 
            String cmd = command.get(0); // login
            String id = command.get(1); // user id
            
            //TODO: use id to load data 
            // after loading data
            /*      if user exits in db
                        contruct a user object, return obj to client 
                    else 
                        create an new user object, add it to the server lsit
                        and create a new entry for this user object in db
                        and return it to the client
            */
            User user3 = new User("333");
            File file = new File(DEFAULT_PATH + user.getId() + JSON_EXTENSION);
            DataManager dm = new DataManager();
            dm.loadUserData(user3, file.toPath());
            rtval = user3;
            
            
            
        } else if(state == State.IN_AG_CMD) {
            
        } else if(state == State.IN_SG_CMD) {
            
        } else if(state == State.IN_RG_CMD) {
            
        }
        
        // first check if the client request is LOGOUT
        // if so, may want to update database and detach the thread
        
        return rtval;

    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public User getUserById(int id) {
        return users.get(id);
        
    }    

    public void addUser(Integer id, User user) {
        users.put(id, user);
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