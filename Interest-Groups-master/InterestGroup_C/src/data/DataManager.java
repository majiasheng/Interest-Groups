package data;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import com.fasterxml.jackson.core.*;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.ProcessBuilder.Redirect.to;
import java.nio.file.Files;

/**
 *
 * @author majiasheng, Ruoping Lin
 */

/**
 * This class manages user data. Every user has a DataManager object for saving
 * and loading his information
 */
public class DataManager {
    private ArrayList<User>    users;
    private File               userDataFile;
    private User               user;
    
    public static final String defaultPath = "InterestGroup_C/saved/";     
    public static final String jsonExtension = ".json";     
    public static final String USER_ID = "USER_ID";
    public static final String SUBSCRIBED_GROUPS = "SUBSCRIBED_GROUPS";
    public static final String POSTS = "POSTS";
    
    public DataManager() {
        
    }
    
    
    public DataManager(User user){
        this.user = user;
    }
    
    protected void saveUserData(Path saveTo) {
        JsonFactory jsonFactory = new JsonFactory();
        
        // create an array to store subscribed groups because JsonGenerator does not accept ArrayList
        String[] subscribedGroups = new String[user.getSubscribedGroups().size()]; 
        for (int i = 0; i < user.getSubscribedGroups().size(); i++){
            subscribedGroups[i] = user.getSubscribedGroups().get(i);
        }
        
        try (OutputStream out = Files.newOutputStream(saveTo)) {
            JsonGenerator generator = jsonFactory.createGenerator(out, JsonEncoding.UTF8);
            generator.writeStartObject();

            generator.writeStringField(USER_ID, user.getId());
            generator.writeStartArray();
            for (int groupIndex = 0; groupIndex < user.getSubscribedGroups().size(); groupIndex++) 
                generator.writeString(user.getSubscribedGroups().get(groupIndex));
            generator.writeEndArray();
            

            generator.writeEndObject();
            generator.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    protected void loadUserData(Path loadFrom) {
        
    }
    
    
}
