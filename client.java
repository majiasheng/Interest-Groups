
package cse310;

import java.io.*;
import java.net.*; 


import java.net.Socket;
import java.util.Scanner;

public class client{

    public static void main(String[] args) {
        int portNum = 6666;
        try{
            int userID = 0001;
            /*
            if (args.length > 0) {
                userID = args[0];
            }else{
                System.out.printf("no input user ID");
                System.out.println();
            }
                    */
            Socket socket = new Socket("localhost", 6666); 
            BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter( new OutputStreamWriter(socket.getOutputStream()));
            // send data to the server
            // send command to the server
            Scanner input = new Scanner(System.in);
            System.out.println("login function"); //login
            String command = input.nextLine();  
            out.println(command);
            out.flush();
            
            out.println("BYE");
            out.flush();
            // receive data from the server
            while (true) {
                String str = in.readLine();
                if (str == null) {
                    break;
                } else {
                    System.out.println(str);
                }
            }
        }catch (Exception e) {}
    }
}