
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
    public  static void main(String[] args) throws Exception{
        String clientSentence;
        String capitalizedSentence;

        // create a server socket (TCP)
        ServerSocket welcomeSocket = new ServerSocket(6666);

        // loop infinitely (process clients sequentially)
        while(true) {
            // Wait and accept client connection
            Socket connectionSocket = welcomeSocket.accept();

            //create an input stream from the socket input steram
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            // create an output stream from the socket output steram
            DataOutputStream  outToClient =
                    new DataOutputStream(connectionSocket.getOutputStream());

            // read a line form the input stream
            clientSentence = inFromClient.readLine();

            // capitalize the sentence
            capitalizedSentence = clientSentence.toUpperCase() + '\n';

            // send the capitalized sentence back to the  client
            outToClient.writeBytes(capitalizedSentence);

            // close the connection socket
            connectionSocket.close();
        }

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
