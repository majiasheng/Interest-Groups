 package interest_group_c;

/**
 *
 * @author Jia Sheng Ma
 */
import interest_group_c.State;
import data.Constants;
import data.DataManager;
import data.DiscussionGroup;
import data.User;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * client side 
 */
public class InterestGroup_Client {
    private static DataManager dataManager;
    private static String state;     // user state in the session
    private static String prompt;
    
    private static String command;  // user command
    private static ArrayList<String> cmdTokens;
    private static Object response; // server response
    
    private static Socket socket;
    private static ObjectOutputStream output_to_server;
    private static ObjectInputStream input_from_server;
    
    private static String hostmachine;
    private static int portnumber;
    private static User user;
    private static Scanner user_input_scn;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // get hostmachine and port number 
        // hostmachine = args[1];
        hostmachine = "localhost";
        
        // portnumber = Integer.parseInt(args[2]);
        portnumber = 6666;
        
        // user is not logged in yet 
        state = State.NOT_LOGGED_IN;
        user = null;
        
            try{
                // user input scanner
                user_input_scn = new Scanner(System.in);
                // create socket
                socket = new Socket(hostmachine, portnumber);
                System.out.println(">> Connected to localhost at 6666\n");
                
                // create socket with hostmachine and portnumber 
                // Socket socket = new Socket(hostmachine, portnumber);
                // feedback: connected to hostmachine 
                // System.out.println(">> Connected to " + hostmachine + "\n");

                // output buffer (to server)
                output_to_server = new ObjectOutputStream(socket.getOutputStream());
                // input buffer (from server)
                input_from_server = new ObjectInputStream(socket.getInputStream());
                
                /*****************************
                    listen for user command 
                *****************************/
                do {
                    /*****************************
                        get user command
                    *****************************/
                    printPrompt();
                    command = user_input_scn.nextLine();
                    // tokenize command
                    cmdTokens = tokenizeCMD(command);
                    
                    // prints help menu
                    if(command.equals(Constants.HELP)) {
                        printHelpMenu();
                        continue;
                    }
                    if(command.equals(Constants.LOGOUT)) {
                        System.out.println("Logging out ...");
                        socket.close();
                        System.exit(0);
                    }
                    /*****************************
                        send server command 
                            package user command before sending it to server
                            but if it is not logged in, just send the command
                    *****************************/                        
                    if(state.equals(State.NOT_LOGGED_IN)) {
                        
                        if(!cmdTokens.get(0).equals(Constants.LOGIN)){
                            // feedback: not logged in
                            System.out.println(">> Please log in first\n");
                            continue;
                        } else {
                            // send login command to server
                            if(validateCMD()) {
                                output_to_server.writeObject((Object)(formatCMD(command)));
                                output_to_server.flush();
                            } else {
                                continue;
                            }
                        }
                    } else {
                        if(isCMDValid(command)) {
                            // further validates command (check illegal arguments)
                            if(validateCMD()) {
                                output_to_server.writeObject((Object)(formatCMD(command)));
                                output_to_server.flush();
                            } else {
                                continue;
                            }
                        } else {
                            System.out.println(">> No such command. Type \"help\" to see available commands");
                            continue;
                        }
                    }
                    
                    /*****************************
                        get response from server
                     *****************************/
                    // System.out.println(">> Contacting server...");
                    response = input_from_server.readObject();
                    // System.out.println("200 OK ");
                    handleServerResponse(response);
                    
                } while (true);

            } catch(IOException ioe) {
                System.out.println("<< FAILED TO CONNECT TO HOST");
            } catch(ClassNotFoundException cnf) {
                cnf.printStackTrace();
            }
    }
    
    /**
     * Handles server's response
     * @param response
     * @param input_from_server
     * @param output_to_server 
     */
    public static void handleServerResponse(Object response) {
        
        /* server response is just an object depending one what 
           request the client sent to the server 
           1. if login cmd is sent: response = user : User
           2. if ag    cmd is sent: response = allGroups : ArrayList<String>
           3. //TODO: 
        */
        
        String cmd = cmdTokens.get(0);
        
        if(cmd.equals(Constants.AG)) {
            ag_mode();
        } else if(cmd.equals(Constants.SG)) {
            sg_mode();
        } else if(cmd.equals(Constants.RG)) {
            rg_mode();
        } else if(cmd.equals(Constants.LOGIN)) {            
            handleLoginResponse();
        } 
        // else if(cmd.equals(Constants.LOGOUT)) {
        //    try {
                // close sockets and exit
        //        socket.close();
        //    } catch (IOException ex) {
        //        Logger.getLogger(InterestGroup_Client.class.getName()).log(Level.SEVERE, null, ex);
        //    }
        //    System.exit(0);
        // }
    }
    
    /**
     * Enables sub commands available under "ag"
     */
    private static void ag_mode() {
        System.out.println("################################");
        System.out.println("#          all groups          #");
        System.out.println("################################");
        
        ArrayList<String> allGroups = (ArrayList<String>)response;
        /*TODO: print all groups N at a time 
                (if N is not specified, use a default value) */
        
        // update current state as "ag"
        state = State.IN_AG;
        
        do { // listen for user commands 
            printPrompt();
            command = user_input_scn.nextLine();
            if(command.equals("q")) {
                state = State.LOGGED_IN;
                printMainMenuHeader();
                break;
            } else if(command.equals("s") || command.equals("u") || command.equals("n")) {
                ag_handler(command);
            } else {
                System.out.println("ERROR: NO SUCH COMMAND");
                printSubCMDMenu_AG();
            }
        } while(true);
    } /* end of ag_mode*/
    
    /**
     * Enables sub commands available under "sg"
     */
    private static void sg_mode() {
        System.out.println("################################");
        System.out.println("#       subscribed groups      #");
        System.out.println("################################");
        // update current state as "sg"
        state = State.IN_SG;
        
        do { // listen for user commands 
            printPrompt();
            command = user_input_scn.nextLine();
            if(command.equals("q")) {
                printMainMenuHeader();
                state = State.LOGGED_IN;
                break;
            } else if(command.equals("u") || command.equals("n")) {
                sg_handler(command);
            } else {
                System.out.println("ERROR: NO SUCH COMMAND");
                printSubCMDMenu_SG();
            }

        } while(true);
    } /* end of sg_mode */
    
    /**
     * Enables sub commands available under "rg"
     */
    private static void rg_mode() {
        
        /* if server returns null, i.e. no such group exist, 
           then give feedback to user
        */ 
        if(response == null) {
            System.out.println("<< ERROR: Group: \"" + cmdTokens.get(1) 
                             + "\" NOT FOUND IN RECORD");
            return;
        }
        // now we have a group, we can run queries on it 
        DiscussionGroup group = (DiscussionGroup)response;
        
        // max number of post to be displayed at a time
        int N;
        if(cmdTokens.size() > 2) {
            N = Integer.parseInt(cmdTokens.get(2));
        } else {
            N = Constants.N;
        }
        
        System.out.println("################################");
        System.out.println("#          read groups         #");
        System.out.println("################################");
        // update current state as "rg"
        state = State.IN_RG;
        
        updatePrompt();
        do { // listen for user commands 
            printPrompt();
            command = user_input_scn.nextLine();
            if(command.equals("q")) {
                printMainMenuHeader();
                state = State.LOGGED_IN;
                break;
            } 
            //TODO: the subcommand can be a number as an id, if it is a number, 
            // a sub sub command interface should be displayed
            else if(command.equals("r") || command.equals("n") || command.equals("p")) {
                rg_handler(command, N);
            } else {
                try {
                    int postid = Integer.parseInt(command);
                    id_handler_rg(postid, N);
                } catch(NumberFormatException nfe) {
                    System.out.println("ERROR: NO SUCH COMMAND");
                    printSubCMDMenu_RG();
                }
            }
        } while(true);
    } /* end of rg_mode */
    
    /**
     * Tokenizes command 
     * @param command
     * @return tokens
     */
    public static ArrayList<String> tokenizeCMD(String command) {
        ArrayList<String> cmdTokens = new ArrayList<>();
        for(String token : command.split(" ")) {
            cmdTokens.add(token);
        }
        return cmdTokens;
    }
    
    /**
     * Formats command to include all info needed by server 
     * Format: State, command(as array list), user object
     * @param command
     * @return a formatted client request
     */
    public static ArrayList<Object> formatCMD(String command) {
        ArrayList<Object> formattedCMD = new ArrayList<>();
        ArrayList<String> cmdTokens = tokenizeCMD(command);
        
        formattedCMD.add(state);
        formattedCMD.add(cmdTokens);
        formattedCMD.add(user);
        
        return formattedCMD;
    }

    /**
     * 
     * @param response server response - the user object
     */
    private static void handleLoginResponse() {
        state = State.LOGGED_IN;
        /*TODO: store user info locally (if user data already exists, 
        overwrite it)*/
        user = (User)response;

        System.out.println("<< Logged successfully into server");
    }
    
    /***********************************************
                      ag
     ***********************************************/
    /**
     * Takes "s" "u" or "n" as argument, performs operations pertaining to each command
     * @param command 
     */
    private static void ag_handler(String command) {
        //TODO
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
    
    /***********************************************
                      sg
     ***********************************************/
    private static void sg_handler(String command) {
        //TODO
    }
    /**
     * marks posts in range of [h,t] as read 
     * @param h head of range
     * @param t tail of range
     */
    static void r_handler_sg(int h, int t) {
    
    }

    /**
     *	lists the next N discussion groups
     */
    static void n_handler_sg(int N) {}
    
    /***********************************************
                      rg
     ***********************************************/
    private static void rg_handler(String command, int N) {
        //TODO: 
        if(command.equals("r")){
            
        } else if(command.equals("n")) {
            
        } else if(command.equals("p")) {
            /*TODO: 
                The client program prompts the user for a line denoting the post subject, 
                and then the content of the post, until some special character sequence, 
                such as “\n.\n” – a dot by itself on a line, which denotes the end of post, 
                is entered by the user. The post is then submitted to the server. 
                Afterwards, a new list of N posts should be displayed, 
                including the newly submitted post which is shown as unread.
            */
            p_handler_rg();
        }
        
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
    private static void id_handler_rg(int postid, int N) {
        // set state, update prompt 
        // update current state as "rg"
        state = State.IN_RG_ID;
        
        updatePrompt();
        do { // listen for user commands 
            printPrompt();
            command = user_input_scn.nextLine();
            if(command.equals("q")) {
                System.out.println("################################");
                System.out.println("#          read groups         #");
                System.out.println("################################");
                state = State.IN_RG;
                break;
            } 
            else if(command.equals("n")) {
                //TODO: display at most N more lines of the post content. what is N
                n_id_handler_rg(N);
            } else {
                System.out.println("ERROR: NO SUCH COMMAND");
                //TODO: print list of available commands: q and n 
            }
        } while(true);
        
    }


    /**
     * marks post in range of h to t as read
     */
    static void r_handler_rg(int h, int t) {
        /*TODO: for each post in range h to t
                    readPost()
        */
        
        
    }

    /**
     *	lists the next N discussion groups
     */
    static void n_handler_rg() {
        //TODO: lists the next N discussion groups
    }
    
    /**
     *	post to the group
     */
    static void p_handler_rg() {
        //TODO: construct a post, send it to server
        
    }

    
    /**
     * Checks each command's usage to validate if the command is valid.
     * @param command
     * @return true if valid, false otherwise
     */
    private static boolean isCMDValid(String command) {
        
        String cmd = cmdTokens.get(0);
        if((cmd.equals(Constants.AG)) || (cmd.equals(Constants.SG))
        || (cmd.equals(Constants.RG)) || (cmd.equals(Constants.HELP))
        || (cmd.equals(Constants.LOGOUT) || cmd.equals(Constants.LOGIN)) ) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Further validates command (check illegal arguments)
     * @param command
     * @return 
     */
    private static boolean validateCMD() {
        String cmd = cmdTokens.get(0);
        if(cmd.equals(Constants.LOGIN)) {
            if(state.equals(State.LOGGED_IN)) {
                System.out.println("<< Error: Already logged in with user: " + user.getId());
                return false;
            } else if(cmdTokens.get(1).length() > 9) {
                // if the second argument is not in 0 - 999999999, return  false 
                System.out.println("<< Error: Invalid ID");
                return false;
            } else { // if the id argument is not valid digits, return false
                try {
                    Integer.parseInt(cmdTokens.get(1));
                    return true;
                } catch(NumberFormatException nfe) {
                    System.out.println("<< Error: Invalid ID");
                    return false;
                }
            }
        } else if(cmd.equals(Constants.AG) || cmd.equals(Constants.SG) || cmd.equals(Constants.RG)) {
            try {
                if(Integer.parseInt(cmdTokens.get(1)) > 0) {
                    return true;
                } else {
                    System.out.println("<< Error: Invalid number");
                    return false;
                }
            } catch(NumberFormatException nfe) {
                System.out.println("<< Error: Invalid number");
                return false;
            }
        }
        return true;
    }
    
    /**
     * Updates prompt for each command
     */
    private static void updatePrompt() {
        String name;
        String mode;
        /* if user is not in ag, rg, sg mode or their sub mode, then prompt
           should be of the form: */
        if(state.equals(State.NOT_LOGGED_IN)) {
            name = "GUEST";
            mode = "Main Menu";
        } else {
            name = user.getId();
            if(state.equals(State.IN_AG)) {
                mode = "ag";
            } else if(state.equals(State.IN_SG)) {
                mode = "sg";
            } else if(state.equals(State.IN_RG)){
                mode = "rg";
            } else if(state.equals(State.IN_RG_ID)) {
                mode = "rg-[id]";
            } 
            // if(state.equals(State.LOGGED_IN)) {
            else {
                name = user.getId();
                mode = "Main Menu";
            }
        }        
        prompt = mode + ": " + name + "@" + hostmachine + " >> ";        
    }
    
    private static void printPrompt() {
        updatePrompt();
        System.out.print(prompt);
    }
    
    private static void printMainMenuHeader() {
        System.out.println("################################");
        System.out.println("#          main menu           #");
        System.out.println("################################");
    }    
    
    /**
     * Prints help menu
     */
    public static void printHelpMenu() {
        // TODO: format help menu, add sub commands
        System.out.println("###############################################");
        System.out.println("# help                print this menu         #");
        System.out.println("# login USERID        log in with user id     #");
        System.out.println("# ag                  show all groups         #");
        System.out.println("# sg                  show subscribed groups  #");
        System.out.println("# rg                  read groups             #");
        System.out.println("###############################################");
    }

    private static void printSubCMDMenu_AG() {
        System.out.println("s HEAD [TAIL] – subscribe to groups in range of HEAD and TAIL (TAIL is optional)\n"
                         + "u HEAD [TAIL] – unsubscribe\n"
                         + "n – lists the next N discussion groups\n" 
                         + "q – exits from the ag command\n");
    }
        
    private static void printSubCMDMenu_SG() {
        //TODO
    }

    private static void printSubCMDMenu_RG() {
        //TODO
    }

}
