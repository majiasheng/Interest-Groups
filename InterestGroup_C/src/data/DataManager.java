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
    private String             userID;
    
    public static final String defaultPath = "src/saved/";     
    public static final String jsonExtension = ".json";     
    public static final String USER_ID = "USER_ID";
    public static final String SUBSCRIBED_GROUPS = "SUBSCRIBED_GROUPS";
    public static final String POSTS = "POSTS";
    
    public DataManager() {
        
    }
    
    
    public DataManager(User user){
        this.user = user;
        
        userDataFile = new File(defaultPath + user.getId() + jsonExtension);
        //@todo: check the existence of user data file, create one if not
        saveUserData(userDataFile.toPath());
        
    }
    
    private void checkExistenceOfUser() {
        
    }
    
    protected void saveUserData(Path saveTo) {
        JsonFactory jsonFactory = new JsonFactory();
       
        try (OutputStream out = Files.newOutputStream(saveTo)) {
            JsonGenerator generator = jsonFactory.createGenerator(out, JsonEncoding.UTF8);
            generator.writeStartObject();

            // store ID
            generator.writeStringField(USER_ID, user.getId());
            // store subscribed groups
            generator.writeFieldName(SUBSCRIBED_GROUPS);
            generator.writeStartArray(user.getSubscribedGroups().size());
            for (String c : user.getSubscribedGroups()) 
                generator.writeString(c);
            
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
