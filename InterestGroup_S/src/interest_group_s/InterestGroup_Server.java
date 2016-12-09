package interest_group_s;

import com.fasterxml.jackson.core.*;
import interest_group_s.State;
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
import java.util.AbstractMap;

import java.util.ArrayList;
import java.util.HashMap;
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

    private static ServerSocket welcomeSocket;  // server socket 
    private static ArrayList<Socket> clients;
    private static ArrayList<DiscussionGroup> groups;
    private static HashMap<Integer, User> users;

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
            try 
            {
                output_to_client = new ObjectOutputStream(connectionSocket.getOutputStream());
                input_from_client = new ObjectInputStream(connectionSocket.getInputStream());
                
                
                User response;

                while(true){
                    /************************************
                        listen for request from client
                    ************************************/
                    clientRequest = input_from_client.readObject();
                    // testing 
                    System.out.println("Client request: " + clientRequest);

                    // // handles client's request
                    response = handleClientRequest(clientRequest);
                    // respond to client request
                    
                    output_to_client.writeObject(response);
//                    output_to_client.writeObject("testing");
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(InterestGroup_Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InterestGroup_Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    } /* end of worker server */
    /****************************************************/

    public static void main(String argv[]) throws IOException {
        // initialize server components on start
        init();

        while(true) {
            try {
                // create connection socket for every client connected
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println(">> New client from " + connectionSocket.toString());
                
                // add client to the list of clients
                //TODO: remove it from the list when it logs out 
                clients.add(connectionSocket);
                
                // make a worker server for each of the connected clients 
                WorkerServer workerServer = new WorkerServer(connectionSocket);
                workerServer.start();

            } catch(Exception e) {
                System.out.println("ERROR IN RECEIVING CLIENT CONNECTION");
            }
        } 
    }

    /**
     * parses clientRequest  - array list of objects in the format of:
     *              Format: State, command(as array list), user object
     * and handles the request 
     */
    public static User handleClientRequest(Object clientRequest) throws IOException {
        ArrayList<Object> clientRequestList = (ArrayList<Object>)clientRequest;
        
        // get state
        String state = (String)clientRequestList.get(0);  
        // get command in the form of array list 
        ArrayList<String> command = (ArrayList<String>)clientRequestList.get(1);
        // get user object 
        User user = (User)clientRequestList.get(2);
        
        if(/*state.equals(State.NOT_LOGGED_IN) && */command.get(0).equals(Constants.LOGIN)) {
            
            return(login_handler(command));
            
        } else if(command.get(0).equals(Constants.AG)) {
            //DEBUG
            System.out.println("DEBUG: client sent ag");
            //TODO: return handler's response to client
            if(command.size() == 2) {
                ag_handler(Integer.parseInt(command.get(1)));
            } else {
                ag_handler();
            }
            return null;
        } else if(command.get(0).equals(Constants.SG)) {
            System.out.println("DEBUG: client sent sg");
            //TODO: return handler's response to client
            if(command.size() == 2) {
                sg_handler(Integer.parseInt(command.get(1)));
            } else {
                sg_handler();
            }
            return null;
        } else if(command.get(0).equals(Constants.RG)) {
            System.out.println("DEBUG: client sent rg");
            //TODO: return handler's response to client
            if(command.size() == 3) {
                // 1-gname, 2-N
                rg_handler(command.get(1), Integer.parseInt(command.get(2)));
            } else {
                rg_handler(command.get(1));
            }
            
            return null;
        } else if(command.get(0).equals(Constants.LOGOUT)) {
            // first check if the client request is LOGOUT
            // if so, may want to update database and detach the thread
            return null;
        } else {
            return null;
        }
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }

    public User getUserById(int id) {
        return users.get(id);
        
    }    

    public void addUser(Integer id, User user) {
        users.put(id, user);
    }

    /**
     * Updates program info on every client change requests
     */
    static void update() {}

    /**
     * Handles login command from client. 
     * Creates new user if user does not exist in database
     * Load user if user exists in database
     * @param command login command, 0 is login, 1 is user id (assuming correctness of id)
     * @return user with corresponding id and data
     */
    static User login_handler(ArrayList<String> command) {
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
//            User user3 = new User("333");
            //FIXME: shouldnt use user.getID because user isnt constructed yet in client side 
            //       instead, use the command's id argument
//            File file = new File(DEFAULT_PATH + id + JSON_EXTENSION);
//            DataManager dm = new DataManager();
//            dm.loadUserData(user3, file.toPath());
            System.out.println("returning user " + id);
            User u = new User();
            
            return u;
    }

    /****************************************
     ag - Ruoping
     ****************************************/
    /**
     * ag: lists all existing groups, a default number of groups at a time 
     * This method gathers all discussion groups exist in the data base
     * 
     * @return set of all groups in data base as a list of string
     */
    static ArrayList<String> ag_handler() {
        // user should have a set of subscribed groups,
        ArrayList<String> allGroups = new ArrayList<>();
        // TODO: get add groups, add each one of their name to the list 
        // on client side, we need to check which group has been subscribed
        
        
        return allGroups;
                
    }

    /**
     * lists N groups at a time
     */
    static void ag_handler(int N){
        
    }

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
    static void sg_handler(int N) {}

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
    static void rg_handler(String gname) {}
    
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

    static void createUser(String id) {}
    
    /**
     * Initializes necessary components on start of server 
     */
    private static void init() {

        clients = new ArrayList<>();
        
        groups = new ArrayList<>();
        //TODO: load all groups from data manager 
        
        users = new HashMap<>();
        //TODO: load all users from data manager
        
        System.out.println("Starting server ...");
        try {
            welcomeSocket = new ServerSocket(6666);
        } catch (IOException ex) {
            Logger.getLogger(InterestGroup_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Listening for clients ...");
        
    }
    


}