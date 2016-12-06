import java.io.*;
import java.net.*; 


import java.net.Socket;
import java.util.Scanner;

public class client{

    public static void main(String[] args) {
        int portNum = 6666;
        try{
            int userID = 0001;

            // start connection to server
            Socket socket = new Socket("localhost", 6666); 
            BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter( new OutputStreamWriter(socket.getOutputStream()));
            // send data to the server
            // send command to the server
            Scanner input = new Scanner(System.in);
            System.out.println("Client setup successfully"); //login
            do {
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
            } while (true);

        } catch (Exception e) {

        }
    }
}