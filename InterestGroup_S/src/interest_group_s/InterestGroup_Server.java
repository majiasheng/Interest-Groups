package interest_group_s;

import com.fasterxml.jackson.core.*;
//import com.fasterxml.jackson.databind.util.JSONPObject;
import interest_group_s.State;
import data.Constants;
import static data.Constants.DEFAULT_PATH;
import static data.Constants.JSON_EXTENSION;
import data.DataManager;
import data.DiscussionGroup;
import data.Post;
import data.User;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Liwen Fan, Jia Sheng Ma, Ruoping Lin
 */

/**
 * Server side
 */
public class InterestGroup_Server {

    private static ServerSocket welcomeSocket;  // server socket
    // private static ArrayList<Socket> clients;
    // private static ArrayList<DiscussionGroup> groups;
    private static HashMap<String, DiscussionGroup> gnames_groups_HashMap;
    private static HashMap<String, User> ids_users_HashMap;
    private static DataManager dataManager;

    /*****************************************
    		worker server for clients
    *****************************************/
    static class WorkerServer extends Thread {
        private final Socket connectionSocket;
        private Object clientRequest;
        Object response;

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
            ObjectOutputStream output_to_client;
            ObjectInputStream input_from_client;

            try {
                output_to_client = new ObjectOutputStream(connectionSocket.getOutputStream());
                input_from_client = new ObjectInputStream(connectionSocket.getInputStream());

                while(true){
                    /************************************
                        listen for request from client
                    ************************************/
                    clientRequest = input_from_client.readObject();
                    // testing
                    System.out.println("<< Client request: " + clientRequest);

                    // handles client's request
                    response = handleClientRequest(clientRequest, connectionSocket);
                    // respond to client request
                    output_to_client.writeObject(response);
                }
            } catch (IOException ex) {
                // Logger.getLogger(InterestGroup_Server.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("<< FAILED TO CONNECT TO A CLIENT");
                ex.getCause();
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                // Logger.getLogger(InterestGroup_Server.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("<< ERROR: OBJECT SENT FROM CLIENT CANNOT BE IDENTIFIED");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    } /* end of worker server */
    /****************************************************/

    public static void main(String argv[]) throws IOException, ParseException {
        // initialize server components on start
        init();

        while(true) {
            try {
                // create connection socket for every client connected
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("<< New client from " + connectionSocket.toString());

                // add client to the list of clients
                //TODO: remove it from the list when it logs out
                // clients.add(connectionSocket);

                // make a worker server for each of the connected clients
                WorkerServer workerServer = new WorkerServer(connectionSocket);
                workerServer.start();

            } catch(Exception e) {
                System.out.println("<< ERROR IN RECEIVING CLIENT CONNECTION");
            }
        }
    }

    /**
     * parses clientRequest  - array list of objects in the format of:
     *              Format: State, command(as array list), user object
     * and handles the request
     */
    public static Object handleClientRequest(Object clientRequest, Socket connectionSocket) throws IOException, ParseException {
        ArrayList<Object> clientRequestList = (ArrayList<Object>)clientRequest;

        // get state
        String state = (String)clientRequestList.get(0);
        // get command in the form of array list
        ArrayList<String> command = (ArrayList<String>)clientRequestList.get(1);
        // get user object
        User user = (User)clientRequestList.get(2);
        /*user = (User)clientRequestList.get(2);*/
        System.out.println(state+command.get(0)+user);
        if(/*state.equals(State.NOT_LOGGED_IN) && */command.get(0).equals(Constants.LOGIN)) {

            return(login_handler(command));

        } else if(command.get(0).equals(Constants.AG)) {
            // return handler's response (i.e. list of all groups) to client
            return ag_handler();
        } else if(command.get(0).equals(Constants.SG)) {
            System.out.println("here");
            // return handler's response (i.e. list of all groups) to client
            int argumentLength = command.size();
            int numberOfGroup = 10;
            System.out.println(argumentLength);
            if(argumentLength==3&&command.get(1).equals("u")){
                System.out.print("UUUU");
                ////////////////////////////////////
                int indexofgroup=Integer.parseInt(command.get(2));
                sg_u_handler(indexofgroup,user);
                return sg_handler(numberOfGroup, user);

            }else {
                if (argumentLength == 2) {
                    numberOfGroup = Integer.parseInt(command.get(1));
                    System.out.println("numberOfGroup" + numberOfGroup);
                }
                return sg_handler(numberOfGroup, user);
            }
            
        } else if(command.get(0).equals(Constants.RG)) {
            System.out.println("rg");
            //TODO: return handler's response to client
            
            return rg_handler(command.get(1),user);
            
        } else if(command.get(0).equals(Constants.LOGOUT)) {
            // first check if the client request is LOGOUT
            
            // remove client from list
            // clients.remove(connectionSocket);
            // close socket
            connectionSocket.close();
            
            // if so, may want to update database and detach the thread
            return null;
        } else {
            return null;
        }
    }

    private static void addUser(String id, User user) {
        ids_users_HashMap.put(id, user);
    }
    
    /**
     * Adds post to discussion group
     * @param post post to add
     * @param group discussion group to which the post add
     */
    private static void addPostToGroup(Post post, DiscussionGroup group) {
        //TODO: 
    }

    /**
     * Updates program info on every client change requests
     */
    static void update() {
        // TODO: update data base when ids_users made changes to data 
    }
    
    /**
     * Helper method for login_handler
     * @param id
     * @return if the user specified by the id exists in data base
     */
    private static boolean doesUserExist(String id) {
        return ids_users_HashMap.containsKey(id);
    }

    /**
     * Handles login command from client.
     * Creates new user if user does not exist in database
     * Load user if user exists in database
     * @param command login command, 0 is login, 1 is user id (assuming correctness of id)
     * @return user with corresponding id and data
     */
    static User login_handler(ArrayList<String> command) {
        String id = command.get(1); // user id
        /*      if user exits in db
                    return user obj to client
                else
                    create an new user object, add it to the server lsit
                    and create a new entry for this user object in db
                    and return it to the client
        */
        if(doesUserExist(id)) {
            System.out.println("Returning user " + id);
            return ids_users_HashMap.get(id);
        } else {
            User newUser = new User(id);
            addUser(id, newUser);
            //TODO: store user to database and server's list of ids_users 
            // dataManager.saveUserData(newUser, DEFAULT_PATH);
            return newUser;
        }
    } /* end of login_handler */

    /**
     * ag: lists all existing groups, a default number of groups at a time
     * This method gathers all discussion groups exist in the data base
     *
     * @return set of all groups in data base as a list of string
     */
    private static ArrayList<String> ag_handler() throws IOException {
        // user should have a set of subscribed groups,
        ArrayList<DiscussionGroup> allGroups = new ArrayList<>();
        // TODO: get add groups, add each one of their name to the list
        // on client side, we need to check which group has been subscribed
        dataManager = new DataManager();
        allGroups = dataManager.loadDiscussionGroups();     // get all groups
        
        ArrayList<String> allGroupNames = new ArrayList<String>();
        for (DiscussionGroup dg: allGroups)
            allGroupNames.add(dg.getGroupName());           // get all group names
        
        return allGroupNames;
    }

    /**
     * sg
     */
    private synchronized static List<List<String>> sg_handler(int numberOfGroup,User user) throws IOException, ParseException {
    // user should have a set of subscribed groups,
        //ArrayList<String> allGroups = new ArrayList<>();
        // TODO: get add groups, add each one of their name to the list
        // on client side, we need to check which group has been subscribed
        //gnames_groups_HashMap
        List<List<String>> res = new ArrayList<>();
        System.out.println(user.getSubscribedGroups().size());
        for(int i=0 ; i<user.getSubscribedGroups().size();i++){
            String groupName = user.getSubscribedGroups().get(i);
            DiscussionGroup group = gnames_groups_HashMap.get(groupName);
            System.out.println(group.getGroupName());
            System.out.println("post size"+group.getPosts().size());
            int counter = 0;
            //System.out.println("28"+group.getPosts().get(44).getPostID());
            for(int j=0;j<group.getPosts().size();j++){
                System.out.println("234234234");
                Post post = group.getPosts().get(j);
                System.out.println("j"+j);
                //System.out.println(post.getGroupName());

                if(user.getReadPosts().contains(post.getPostByID())==false){
                    counter++;
                }

            }

            ArrayList<String> temp = new ArrayList<>();
            temp.add(groupName);
            temp.add(Integer.toString(counter));
            System.out.println(groupName+" "+counter);
            System.out.println(groupName);
            res.add(temp);
        }

        return res;
    }
    private synchronized static void sg_u_handler(int indexofgroup, User user){
        System.out.println(user.getSubscribedGroups().get(indexofgroup-1));
        user.getSubscribedGroups().remove(indexofgroup-1);
        dataManager = new DataManager();
        dataManager.saveUserData(user);
    }
    
    /**
     * read group
     */
    private synchronized static List<List<String>> rg_handler(String gname,User user) throws IOException, ParseException {
            /* unread (new) posts should be displayed first*/
            String groupName=gname;
            System.out.println("32424242424"+groupName);
            List<List<String>> res = new ArrayList<>();

        DiscussionGroup group = gnames_groups_HashMap.get(groupName);
            for(int i=0;i<group.getPosts().size();i++){
                if(group.getPosts().get(i).getGroupName().contains(groupName)){
                    //System.out.println("name"+group.getPosts().get(i).getGroupName());
                    ArrayList<String> temp = new ArrayList<>();
                    temp.add(group.getPosts().get(i).getPostedDate());
                    temp.add(group.getPosts().get(i).getPostSubject());
                    temp.add(group.getPosts().get(i).getPostByID());
                    System.out.print(group.getPosts().get(i).getPostedDate());
                    System.out.println(group.getPosts().get(i).getPostSubject());
                    res.add(temp);
                }
            }
            for(int i=0;i<user.getReadPosts().size();i++){
                for(int j=0;j<res.size();j++) {
                    if (user.getReadPosts().get(i) ==res.get(j).get(2)) {
                        res.get(j).add("N");
                        System.out.print(res.get(j).get(3));
                        System.out.print(res.get(j).get(0));
                    }
                }
            }//end for

            return  res;
    }
    
    /**
     *	post to the group
     */
    static void p_handler_rg(/*TODO: add client request */) {
        //TODO: add post to group,call update() in addPostTogroup
        // addPostToGroup(null, null);
    }


    /****************************************
     OTHER FUNCTIONS
     ****************************************/

    /**
     * Initializes necessary components on start of server
     */
    private static void init() throws IOException, ParseException {
        // init data manager 
        dataManager = new DataManager();
        
        // init client(socket) list
        // clients = new ArrayList<>();

        // init hash map of gnames,groups
        gnames_groups_HashMap = new HashMap<>();
        // load all groups from data manager
        for(DiscussionGroup g : dataManager.loadDiscussionGroups()) {
            gnames_groups_HashMap.put(g.getGroupName(), g);
        }
        
        // init hash map of ids,users
        ids_users_HashMap = new HashMap<>();
        // load all ids_users from data manager
        for(User u : dataManager.loadAllUsers()) {
            ids_users_HashMap.put(u.getId(), u);
        }

        System.out.println(">> Starting server ...");
        try {
            welcomeSocket = new ServerSocket(6666);
        } catch (IOException ex) {
            Logger.getLogger(InterestGroup_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(">> Listening for clients ...");
    }
}
