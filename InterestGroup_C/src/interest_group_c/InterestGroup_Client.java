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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        User user;

        // get hostmachine and port number 
        String hostmachine = args[1];
        int portnumber = Integer.parseInt(args[2]);
        
            try{
                // create socket with hostmachine and portnumber 
                Socket socket = new Socket(hostmachine, portnumber);
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
                    command = user_input.nextLine();
                    
                    /*****************************
                         send server command 
                    *****************************/
                    output_to_server.println(command);
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
     * Handlers server response
     */
    public static void handleServerResponse(String response, BufferedReader input_from_server, 
                                            PrintWriter output_to_server, Scanner user_input) {
        
        if(response.equals(Constants.AG)) {
            System.out.println("################################");
            System.out.println("#          all groups          #");
            System.out.println("################################");
            //TODO: set current state as "ag"
            
            do { // listen for user commands 
                
            } while(true);
        } else if(response.equals(Constants.SG)) {
            System.out.println("################################");
            System.out.println("#       subscribed groups      #");
            System.out.println("################################");
            //TODO: set current state as "sg"
            
            do { // listen for user commands 
                
            } while(true);
        } else if(response.equals(Constants.RG)) {
            System.out.println("################################");
            System.out.println("#          read groups         #");
            System.out.println("################################");
            //TODO: set current state as "rg"
            
            do { // listen for user commands 
                
            } while(true);
        } else if(response.equals(Constants.LOGIN)) {
            // create user 
        }
    }
    
}
