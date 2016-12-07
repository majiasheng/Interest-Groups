/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import com.fasterxml.jackson.core.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Liwen Fan, Jia Sheng Ma, Ruoping Lin
 */
public class server {
    public  static void main(String[] args) {
//        try {
//            ServerSocket s = new ServerSocket(6666);
//            while (true) {
//                // starting connecting to client
//                Socket incoming = s.accept();
//                BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
//                PrintWriter out = new PrintWriter( new OutputStreamWriter(incoming.getOutputStream()));
//                out.println("Server Setup Successfully");
//                out.println("Please enter your user ID to login");
//                out.flush();
//                while (true) {
//                    // receive a command from the server
//                    String command = in.readLine();
//
//                    if (command.equals("logout")) {
//                        break; // server closes connection when the user enters "logout" command
//                    } else {
                        // match user ID with existing IDs. if fails to match, create a new user
//                        File userDataFile = new File("InterestGroup_S/Users/user_info.json");
                        File userDataFile = new File("/Users/jiashengma/Desktop/CSE310_FP/310fp_repo/InterestGroups/InterestGroup_S/Users");

//                        Path path = Paths.get("Group Project/InterestGroup_S/Users");
//                        File file = new File("Group Project/InterestGroup_S/Users/1111.json");
                        if (userDataFile.exists())
                            System.out.println("file exists");
                        else
                            System.out.println("file does not exist");


//
////                        commandMenu(command);
//                        out.println("Echo: " + command);
//                        out.flush();
////                        if (command.trim().equals("BYE"))
////                            break;
//                    }
//                }
//                incoming.close();
//            }
//        } catch (Exception e) {
//            System.out.println("failed to create file");
//        }
    }

    private static void readUserData() {

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
