 package interest_group_c;

/**
 *
 * @author Jia Sheng Ma
 */
import interest_group_c.State;
import data.Constants;
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

/**
 * client side 
 */
public class InterestGroup_Client {
    static User user;
    static String state;     // user state in the session
    static String command;  // user command
    static Object response; // server response
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ObjectInputStream input_from_server;
        ObjectOutputStream output_to_server;
//        Socket socket;
        Scanner user_input_scn;
        
        // get hostmachine and port number 
//        String hostmachine = args[1];
//        int portnumber = Integer.parseInt(args[2]);
        
        // user is not logged in yet 
        state = State.NOT_LOGGED_IN;
        user = null;
        
            try{
                // user input scanner
                user_input_scn = new Scanner(System.in);
                // create socket
                Socket socket = new Socket("localhost", 6666);
                System.out.println(">> Connected to localhost at 6666\n");
                
//                // create socket with hostmachine and portnumber 
//                Socket socket = new Socket(hostmachine, portnumber);
//                // feedback: connected to hostmachine 
//                System.out.println(">> Connected to " + hostmachine + "\n");

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
                    System.out.print(">> ");
                    command = user_input_scn.nextLine();

                    // prints help menu
                    if(command.equals(Constants.HELP)) {
                        printHelpMenu();
                        continue;
                    }
                    /*****************************
                        send server command 
                            package user command before sending it to server
                            but if it is not logged in, just send the command
                    *****************************/                        
                    if(state.equals(State.NOT_LOGGED_IN)) {
                        // tokenize command and check the first token 
                        String cmdTokens = tokenizeCMD(command).get(0);
                        if(!cmdTokens.equals(Constants.LOGIN)){
                            // feedback: not logged in
                            System.out.println(">> Please log in first\n");
                            continue;
                        } else {
                            // send login command to server
                            output_to_server.writeObject((Object)(formatCMD(command)));
                            output_to_server.flush();
                        }
                    } else {
                        if(isCMDValid(command)) {
                            output_to_server.writeObject((Object)(formatCMD(command)));
                            output_to_server.flush();
                        } else {
                            System.out.println(">> Type \"help\" to see available commands");
                            continue;
                        }
                    }
                    
                    /*****************************
                        get response from server
                     *****************************/
                    response = input_from_server.readObject();
                    System.out.println("200 OK ");
                    handleServerResponse(response, input_from_server, output_to_server, user_input_scn);
                } while (true);

            } catch(IOException ioe) {
                System.out.println("FAILED TO CONNECT TO HOST");
            } 
            catch(ClassNotFoundException cnf) {
                cnf.printStackTrace();
            }

    }
    
    /**
     * Handles server's response
     * @param response
     * @param input_from_server
     * @param output_to_server
     * @param user_input_scn 
     */
    public static void handleServerResponse(Object response, ObjectInputStream input_from_server, 
                                            ObjectOutputStream output_to_server, Scanner user_input_scn) {
        ArrayList<Object> responseTokens;
        //TODO: using Object[] should work
        responseTokens = parseResponse(response);
        //TODO: need to discuss about the format for response
        /* a sample response format: 
                | user_command | data for user's request     | state
                |--------------+-----------------------------+--------
                |  login       | user's info(in a format..)  | IN_AG_CMD
            responseTokens[0] should be user_command
            responseTokens[1] should be data for user's request
            etc.. 
        */
        String cmd = tokenizeCMD(command).get(0);
        
        if(cmd.equals(Constants.AG)) {
            System.out.println("################################");
            System.out.println("#          all groups          #");
            System.out.println("################################");
            // update current state as "ag"
            state = State.IN_AG_CMD;
            
            do { // listen for user commands 
                System.out.print(">> ");
                command = user_input_scn.nextLine();
                if(command.equals("q")) {
                    System.out.println("################################");
                    System.out.println("#          main menu           #");
                    System.out.println("################################");
                    state = State.LOGGED_IN;
                    break;
                } else if(command.equals("s") || command.equals("u") || command.equals("n")) {
                    ag_handler(command);
                } else {
                    System.out.println("ERROR: NO SUCH COMMAND");
                    printSubcmd_AG();
                }
            } while(true);
            
        } else if(cmd.equals(Constants.SG)) {
            System.out.println("################################");
            System.out.println("#       subscribed groups      #");
            System.out.println("################################");
            // update current state as "sg"
            state = State.IN_SG_CMD;
            
            do { // listen for user commands 
                System.out.print(">> ");
                command = user_input_scn.nextLine();
                if(command.equals("q")) {
                    System.out.println("################################");
                    System.out.println("#          main menu           #");
                    System.out.println("################################");
                    state = State.LOGGED_IN;
                    break;
                } else if(command.equals("u") || command.equals("n")) {
                    sg_handler(command);
                } else {
                    System.out.println("ERROR: NO SUCH COMMAND");
                    printSubcmd_SG();
                }
                
            } while(true);
        } else if(cmd.equals(Constants.RG)) {
            System.out.println("################################");
            System.out.println("#          read groups         #");
            System.out.println("################################");
            // update current state as "rg"
            state = State.IN_RG_CMD;
            
            do { // listen for user commands 
                System.out.print(">> ");
                command = user_input_scn.nextLine();
                if(command.equals("q")) {
                    System.out.println("################################");
                    System.out.println("#          main menu           #");
                    System.out.println("################################");
                    state = State.LOGGED_IN;
                    break;
                } 
                //TODO: the subcommand can be a number as an id, if it is a number, 
                // a sub sub command interface should be displayed
                else if(command.equals("r") || command.equals("n") || command.equals("p")) {
                    rg_handler(command);
                } else {
                    System.out.println("ERROR: NO SUCH COMMAND");
                    printSubcmd_RG();
                }
                
            } while(true);
        } else if(cmd.equals(Constants.LOGIN)) {
            /* TODO: create user, store user info locally if it is a new user
                                  load user info if it is in the data bases
            */
            state = State.LOGGED_IN;
//            user = (User)responseTokens.get(0);
            
            user = (User)response;
            
            /*TODO: store user info locally (if user data already exists, 
            overwrite it)*/
            
            System.out.println("User " /*+ user.getId() */+ " created" );
        } else if(cmd.equals(Constants.LOGOUT)) {
            //TODO: close sockets and exit
            
        }
    }
    
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
     */
    public static ArrayList<Object> formatCMD(String command) {
        ArrayList<Object> formattedCMD = new ArrayList<>();
        ArrayList<String> cmdTokens = tokenizeCMD(command);
        
        formattedCMD.add(state);
        //testing         
//        formattedCMD.add(null);
        
        formattedCMD.add(cmdTokens);
        formattedCMD.add(user);
        
        return formattedCMD;
        
    }
    
    /***
     * Parses the formatted response message from server
     * @param response
     * @return all the info from the response as tokens
     */
    public static ArrayList<Object> parseResponse(Object response) {
    // String[] responseTokens = new String[];
        
        return null;
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

    private static void printSubcmd_AG() {
        System.out.println("s HEAD [TAIL] – subscribe to groups in range of HEAD and TAIL (TAIL is optional)\n"
                         + "u HEAD [TAIL] – unsubscribe\n"
                         + "n – lists the next N discussion groups\n" 
                         + "q – exits from the ag command\n");
    }
    /**
     * Takes "s" "u" or "n" as argument, performs operations pertaining to each command
     * @param command 
     */
    private static void ag_handler(String command) {
        //TODO:
    }

    private static void printSubcmd_SG() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void sg_handler(String command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void printSubcmd_RG() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void rg_handler(String command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static boolean isCMDValid(String command) {
        //TODO: probably need to check if the specify command is valid
        ArrayList<String> cmdTokens = tokenizeCMD(command);
        String cmd = cmdTokens.get(0);
        if((cmd.equals(Constants.AG)) || (cmd.equals(Constants.SG))
        || (cmd.equals(Constants.RG)) || (cmd.equals(Constants.HELP))
        || (cmd.equals(Constants.LOGOUT)) ) {
            return true;
        } else if(cmd.equals(Constants.LOGIN)) {
            //TODO:
            if(cmdTokens.size() != 2 /*|| cmdTokens.get(1)*/) {
                // if the second argument is not valid digits in 0 - 999999999, return  false 
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }
    
}
