/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse310;
import java.io.*;
import java.net.*; 
import java.util.StringTokenizer;
/*
JSON import
*/

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author green
 */
public class server {
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
                

}
