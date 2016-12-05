 package interest_group_c;

/**
 *
 * @author majiasheng
 */

import java.io.BufferedReader;
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
        System.out.println("test merge command");

        int portNum = 6666;

            try{
                int userID = 0001;

                Socket socket = new Socket("localhost", 6666);
                BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter( new OutputStreamWriter(socket.getOutputStream()));
                // send data to the server
                // send command to the server
                Scanner input = new Scanner(System.in);
                System.out.println("login function"); //login

                do {
                    String command = input.nextLine();
                    if (command.equals("log out"))
                        break;
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

            }catch (Exception e) {

            }

    }
    
    /**
     * Handlers command line arguments
     */
    void handleCMD(String[] args) {}
    
    void login() {}
    
    void help() {}
    
    void ag() {}
    
    void sg() {}
    
    void rg() {}
    
    void logout() {}
    
}
