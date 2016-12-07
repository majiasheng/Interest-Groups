 package interest_group_c;

/**
 *
 * @author Jia Sheng Ma
 */

import data.Constants;
import data.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * client side 
 */
public class InterestGroup_Client {
    static User user;
    static State state;     // user state in the session
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // get hostmachine and port number 
        String hostmachine = args[1];
        int portnumber = Integer.parseInt(args[2]);
        
        // user is not logged in yet 
        state = State.NOT_LOGGED_IN;
            try{
                // create socket with hostmachine and portnumber 
                Socket socket = new Socket(hostmachine, portnumber);
                // feedback: connected to hostmachine 
                System.out.println(">> Connected to " + hostmachine + "\n");

                // input buffer (to server)
                BufferedReader input_from_server = new BufferedReader( new InputStreamReader(socket.getInputStream()));
                // output buffer (from server)
                PrintWriter output_to_server = new PrintWriter( new OutputStreamWriter(socket.getOutputStream()));
                // user input 
                Scanner user_input = new Scanner(System.in);
                String command; // user command 
                // server response
                String response;
                
                /*****************************
                    listen for user command 
                *****************************/
                do {
                    /*****************************
                        get user command
                    *****************************/
                    System.out.print(">> ");
                    command = user_input.nextLine();

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
                    if(state == State.NOT_LOGGED_IN) {
                        if(!command.equals(Constants.LOGIN)){
                            // feedback: not logged in
                            System.out.println(">> Please log in \n");
                        } else {
                            // send login command to server
                            output_to_server.println(command);
                        }
                    } else {
                        formatCMD(command);
                        output_to_server.println();
                    }
                    
                    output_to_server.flush();
                    
                    /*****************************
                        get response from server
                     *****************************/
                    response = input_from_server.readLine();
                    handleServerResponse(response, input_from_server, output_to_server, user_input);
                } while (true);

            } catch(IOException ioe) {
                System.out.println("FAILED TO CONNECT TO HOST");
            }

    }
    
    /**
     * Handles server's response
     * @param response
     * @param input_from_server
     * @param output_to_server
     * @param user_input 
     */
    public static void handleServerResponse(String response, BufferedReader input_from_server, 
                                            PrintWriter output_to_server, Scanner user_input) {
        String[] responseTokens;
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
        if(responseTokens[0].equals(Constants.AG)) {
            System.out.println("################################");
            System.out.println("#          all groups          #");
            System.out.println("################################");
            //TODO: set current state as "ag"
            
            do { // listen for user commands 
                
            } while(true);
        } else if(responseTokens[0].equals(Constants.SG)) {
            System.out.println("################################");
            System.out.println("#       subscribed groups      #");
            System.out.println("################################");
            //TODO: set current state as "sg"
            
            do { // listen for user commands 
                
            } while(true);
        } else if(responseTokens[0].equals(Constants.RG)) {
            System.out.println("################################");
            System.out.println("#          read groups         #");
            System.out.println("################################");
            //TODO: set current state as "rg"
            
            do { // listen for user commands 
                
            } while(true);
        } else if(responseTokens[0].equals(Constants.LOGIN)) {
            /* TODO: create user, store user info locally if it is a new user
                                  load user info if it is in the data bases
            */
            
        }
    }
    
    /**
     * Formats command to include all info needed by server 
     */
    public static String formatCMD(String command) {
        String formattedCMD;
        // format: user_id,
        formattedCMD = ""+user.getId()+",";
        
        return formattedCMD;
        
    }
    
    /***
     * Parses the formatted response message from server
     * @param response
     * @return all the info from the response as tokens
     */
    public static String[] parseResponse(String response) {
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
        System.out.println("# login USEID         log in                  #");
        System.out.println("# ag                  show all groups         #");
        System.out.println("# sg                  show subscribed groups  #");
        System.out.println("# rg                  read groups             #");
        System.out.println("###############################################");

    }
    
}
